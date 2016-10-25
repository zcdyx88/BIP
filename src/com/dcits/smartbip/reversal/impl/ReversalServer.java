package com.dcits.smartbip.reversal.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.engine.impl.MapperRepository;
import com.dcits.smartbip.engine.impl.ServiceRepository;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.reversal.IReversalServer;
import com.dcits.smartbip.reversal.entity.BipReversalInfoDetailEntity;
import com.dcits.smartbip.reversal.entity.BipReversalInfoEntity;
import com.dcits.smartbip.reversal.entity.BipReversalInfoHistoryEntity;
import com.dcits.smartbip.reversal.service.BipReversalInfoDetailService;
import com.dcits.smartbip.reversal.service.BipReversalInfoHistoryService;
import com.dcits.smartbip.reversal.service.BipReversalInfoService;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IMapper;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.ApplicationUtils;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class ReversalServer implements IReversalServer {
	private static final Log log = LogFactory.getLog(ReversalServer.class);
	static private ReversalServer instance;
	//冲正策略因为只支持一种，暂时放在数据库中
	private ReversalConfig config;
	private BipReversalInfoService reversalInfoService;
	private BipReversalInfoDetailService reversalInfoDetailService;
	private BipReversalInfoHistoryService reversalInfoHistoryService;
	private final int REVERSALMAXCOUNT = 3;

	static public ReversalServer getInstance()
	{
		if(null == instance)
		{
			instance = new ReversalServer();
		}
		return instance;
	}
	
	private ReversalServer()
	{
	      config = ReversalConfig.getInstance();		
	      reversalInfoService = (BipReversalInfoService) ApplicationUtils.getInstance()
					.getBean("bipReversalInfoService");
	      reversalInfoDetailService = (BipReversalInfoDetailService) ApplicationUtils.getInstance()
					.getBean("bipReversalInfoDetailService");
	      reversalInfoHistoryService = (BipReversalInfoHistoryService) ApplicationUtils.getInstance()
					.getBean("bipReversalInfoHistoryService");
	}
	
	@Override
	public void start() {
  
		//定时任务，每隔一分钟执行一次
		TimerTask timerTask = new TimerTask(){
			@Override
			public void run() {
				reversal();
			}			
		};
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 60000);
        
        /*ReversalService rs = new ReversalService();
        SessionContext.getContext().setValue("TEST1", "VALUE1");
        String id = "3002101000103";
        SoapCompositeData testComposite = new SoapCompositeData();
        testComposite.setId(id);
        testComposite.setxPath("/" + id);
        SessionContext.getContext().setValue("Req" + id, testComposite);
        ICompositeData bodyCompositeData = CompositeDataUtils.mkNodeNotExist(testComposite, "body");
        CompositeDataUtils.setValue(bodyCompositeData, "path1", "value1");
        CompositeDataUtils.setValue(bodyCompositeData, "path2", "value2");        
        rs.insertReversalInfo("12345", "3002100000106", SessionContext.getContext(), "M3002101000103_3002100000106", "Reply_Msg/Body/returncode", "000000");*/
	}
	
	public void reversal()
	{

		SimpleDateFormat dateFormate = new SimpleDateFormat("hhmmss"); 
		String nowTime = dateFormate.format(new Date());
		if(Integer.valueOf(nowTime) > Integer.valueOf(config.getConfig(ReversalConstants.REVERSAL_STARTTIME)) 
				&& Integer.valueOf(nowTime) < Integer.valueOf(config.getConfig(ReversalConstants.REVERSAL_ENDTIME)))
		{			
			String strMaxCount = config.getConfig(ReversalConstants.REVERSAL_MAXCOUNT);
//			List<BipReversalInfoEntity> reversalInfoList = reversalInfoService.getRepository().queryReverInfo(Integer.valueOf(strMaxCount));
			List<BipReversalInfoEntity> reversalInfoList = (List<BipReversalInfoEntity>)reversalInfoService.getRepository().findAll();
			
			for(BipReversalInfoEntity infoEntity:reversalInfoList)
			{				
				try
				{
					int maxCount = strMaxCount==null ? REVERSALMAXCOUNT : Integer.valueOf(config.getConfig(ReversalConstants.REVERSAL_MAXCOUNT));
					
					if( maxCount>= infoEntity.getCount() )
					{
						if(checkReversalOrNot(infoEntity))
						{
							infoEntity.setCount(infoEntity.getCount()+1);
							reversalInfoService.save(infoEntity);
							sendMessage(infoEntity);
						}
					}
					else
					{			
						reversalFail(infoEntity);					
					}
				}
				catch(Exception e)
				{
					log.error("",e);
				}
			}			
		}else
		{
			if(log.isInfoEnabled())
			{
				log.info("当前时间段不进行冲正");
			}
		}	
	}	
	
	/**
	 * 检查冲正条件是否满足
	 * （1）当前时间大于下一次冲正时间
	 * @return
	 */
	private boolean checkReversalOrNot(BipReversalInfoEntity infoEntity)
	{		
		Date now = new Date();
		Date nextReversalDate = infoEntity.getNextReversalTime();
		if(nextReversalDate == null || now.after(nextReversalDate))
		{
			return true;
		}
		else{
			return false;
		}	
	}
	
	private void sendMessage(BipReversalInfoEntity infoEntity)
	{
		byte[] buffer = infoEntity.getFlowContext();
		ObjectInputStream in;
		boolean succFlag = false;
		String returnCode = null;
		try
		{
			try {
				in = new ObjectInputStream(new ByteArrayInputStream(buffer));
				SessionContext sessionContext = (SessionContext)in.readObject();
				SessionContext.getContext().setContext(sessionContext);			
				String serviceId = infoEntity.getBuzzServiceID();			
				IService service = ServiceRepository.getRepository().get(serviceId);
				String id = "Req" + serviceId;
		       ICompositeData reqObj = (SoapCompositeData) SessionContext.getContext().getValue(id);
		       if (null == reqObj) {
		           //生成请求对象
		           reqObj = new SoapCompositeData();
		           reqObj.setId(id);
		           reqObj.setxPath("/" + id);
		           //请求对象放入上下文
		           SessionContext.getContext().setValue("Req" + serviceId, reqObj);
		       }
		       //获取映射对象
		       //进行请求的数据映射
		       IMapper mapper = MapperRepository.getRepository().get(infoEntity.getBuzzServiceMapper());
		       if (null != mapper) {
		           mapper.mapReq();
		       }
		       //调用服务获得返回
		       if (log.isInfoEnabled()) {
		           log.info("开始调用服务[" + serviceId + "],输入为[" + reqObj + "]");
		       }		       
		       Object rspObj = service.execute(reqObj);	       
	       
		       String returnCodeField = infoEntity.getReversalField();
		       returnCode = CompositeDataUtils.getValue((ICompositeData)rspObj, returnCodeField);
		       String expectReturnCode = infoEntity.getReversalSuccCode();
		       
		       if(returnCode.equalsIgnoreCase(expectReturnCode))
		       {
		    	   succFlag = true;
		       }	      
			} catch (IOException e) {
				log.error("",e);
			} catch (ClassNotFoundException e) {
				log.error("",e);
			} catch (InvokeException e) {
				log.error("",e);
			} catch (InstanceNotFoundException e) {
				log.error("",e);
			}catch(Exception e)
			{
				log.error("",e);
			}
		
			try{
				BipReversalInfoDetailEntity infoDetail = new BipReversalInfoDetailEntity();
				infoDetail.setCount(infoEntity.getCount());	
				infoDetail.setReturnCode(returnCode);
				infoDetail.setReveralInfoId(String.valueOf(infoEntity.getId()));
				infoDetail.setReversalTime(new Date());
				reversalInfoDetailService.save(infoDetail);
			}
			catch(Exception e)
			{
				log.error("记录冲正明细时报错",e);
			}
		}
		finally
		{			
			if(succFlag)
		       {
		    	   reversalSuccess(infoEntity);
		       }
		       else{
		    	   if(log.isDebugEnabled())
		    	   {
		    		   StringBuilder sb = new StringBuilder();
		    		   sb.append("原交易流水号=").append(infoEntity.getBuszzSerialNum());
		    		   sb.append("，冲正交易").append(infoEntity.getBuzzServiceID());
		    		   sb.append("，第").append(infoEntity.getCount()).append("次冲正失败，拒绝码=").append(returnCode);
		    		   log.debug(sb.toString());
		    	   }
		    	   reversalFail(infoEntity);
		       }		
		}
	}	
	
	private void reversalSuccess(BipReversalInfoEntity infoEntity)
	{
		BipReversalInfoHistoryEntity hisEntity = infoEntity.cloneHistoryEntity();
		hisEntity.setReversalResult(ReversalConstants.REVERSAL_RESULT_SUCCESS);
		reversalInfoHistoryService.save(hisEntity);
		reversalInfoService.delete(infoEntity);
	}	
	
	private void reversalFail(BipReversalInfoEntity infoEntity)
	{
		if(infoEntity.getCount() >= Integer.valueOf(config.getConfig(ReversalConstants.REVERSAL_MAXCOUNT)))
		{

	    	log.debug("原交易流水号="+infoEntity.getBuszzSerialNum()+",冲正次数已达到最大冲正次数，冲正交易失败！");
	    	BipReversalInfoHistoryEntity hisEntity = infoEntity.cloneHistoryEntity();			
			hisEntity.setReversalResult(ReversalConstants.REVERSAL_RESULT_FAIL);
			reversalInfoHistoryService.save(hisEntity);
			reversalInfoService.delete(infoEntity);
		}
		else{
			Date now = new Date();
			long nextTime = now.getTime()+Long.valueOf(config.getConfig(ReversalConstants.REVERSAL_INTERVAL))*1000;
			Date nextDate = new Date(nextTime);
			infoEntity.setNextReversalTime(nextDate);
			reversalInfoService.save(infoEntity);
		}
	}
}
