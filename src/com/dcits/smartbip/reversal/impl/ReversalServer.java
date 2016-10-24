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

import com.dcits.smartbip.common.model.ReversalAble;
import com.dcits.smartbip.engine.impl.MapperRepository;
import com.dcits.smartbip.engine.impl.ServiceRepository;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.reversal.IReversalServer;
import com.dcits.smartbip.reversal.entity.BipReversalInfoEntity;
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
		reversalInfoService = (BipReversalInfoService) ApplicationUtils.getInstance()
					.getBean("bipReversalInfoService");
	      config = ReversalConfig.getInstance();
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

	}
	
	public void reversal()
	{		
		SimpleDateFormat dateFormate = new SimpleDateFormat("hhmmss"); 
		String nowTime = dateFormate.format(new Date());
		if(Integer.valueOf(nowTime) > Integer.valueOf(config.getConfig(ReversalConstants.REVERSAL_STARTTIME)) 
				&& Integer.valueOf(nowTime) < Integer.valueOf(config.getConfig(ReversalConstants.REVERSAL_ENDTIME)))
		{			
			String maxCount = config.getConfig(ReversalConstants.REVERSAL_MAXCOUNT);
			List<BipReversalInfoEntity> reversalInfoList = reversalInfoService.getRepository().queryReverInfo(Integer.valueOf(maxCount));
			for(BipReversalInfoEntity infoEntity:reversalInfoList)
			{
				if(checkReversalOrNot(infoEntity))
				{
					infoEntity.setCount(infoEntity.getCount()+1);
					reversalInfoService.save(infoEntity);
					sendMessage(infoEntity);
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
		if(now.after(nextReversalDate))
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
	       String returnCode = CompositeDataUtils.getByPath((ICompositeData)rspObj, returnCodeField).get(0).getValue();
	       String expectReturnCode = infoEntity.getReversalSuccCode();
	       if(returnCode.equalsIgnoreCase(expectReturnCode))
	       {
	    	   reversalSuccess(infoEntity);
	       }
	       else{
	    	   if(log.isDebugEnabled())
	    	   {
	    		   log.debug("冲正交易"+serviceId+"本次响应码returnCode=" + returnCode + ",被拒绝！");
	    	   }
	    	   reversalFail(infoEntity);
	       }		       
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvokeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}	
	
	private void reversalSuccess(BipReversalInfoEntity infoEntity)
	{
		infoEntity.setReversalResult(ReversalConstants.REVERSAL_RESULT_SUCCESS);
		reversalInfoService.save(infoEntity);
	}	
	
	private void reversalFail(BipReversalInfoEntity infoEntity)
	{
		if(infoEntity.getCount() >= Integer.valueOf(config.getConfig(ReversalConstants.REVERSAL_MAXCOUNT)))
		{

	    	log.debug("原交易流水号="+infoEntity.getBuszzSerialNum()+",冲正次数已达到最大冲正次数，冲正交易失败！");
			infoEntity.setReversalResult(ReversalConstants.REVERSAL_RESULT_FAIL);
			reversalInfoService.save(infoEntity);
		}
		else{
			Date now = new Date();
			long nextTime = now.getTime();
			Date nextDate = new Date(nextTime);
			infoEntity.setNextReversalTime(nextDate);
			reversalInfoService.save(infoEntity);
		}
	}
}
