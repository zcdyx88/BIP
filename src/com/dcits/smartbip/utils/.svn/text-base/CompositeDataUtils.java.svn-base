package com.dcits.smartbip.utils;

import com.dcits.smartbip.engine.impl.ParamRepository;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.exception.management.ErrorCodeMapping;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by vincentfxz on 16/5/25.
 */
public class CompositeDataUtils {
    private static final Log log = LogFactory.getLog(CompositeDataUtils.class);

    public static void copyProperty(ICompositeData src, ICompositeData target, String srcPath, String destPath) {
        String[] srcPathItems = srcPath.split("/");

    }

    public static List<ICompositeData> getByPath(ICompositeData src, String path) {
    	 List<ICompositeData> child = null;
    	try{
    	    ICompositeData target = src;
            String[] srcPathItems = path.split("/");           
            for (int i = 0; i < srcPathItems.length; i++) {
                child = target.getChild(srcPathItems[i]);
                if (child != null) {
                    if (child.size() > 1) {
                        if (i != srcPathItems.length - 1) {
                            log.error("数组节点[" + srcPathItems[i] + "]需要映射!");
                        }
                    } else if (child.size() == 0) {
                        log.debug("节点[" + srcPathItems[i] + "]不存在!");
                    } else {
                        target = child.get(0);
                    }
                } else {
                    log.debug("节点[" + srcPathItems[i] + "]不存在!");
                }
            }

        } catch (Exception e) {
            log.error(e);
        }
        return child;
    }
    

    public static String getValue(ICompositeData src, String path) {
        String value = null;
        List<ICompositeData> datas = getByPath(src, path);
        if (null != datas) 
        {
        	if(datas.size() == 1)
        	{
        		if(null != datas.get(0).getValue())
        		{
        			value = datas.get(0).getValue().trim();//添加trim
        		}        		
        	}
        	else
        	{
        		log.warn("数组直接取值未实现");
        	}            
        }     
        return value;
    }

    public static String getValueNoNull(ICompositeData src, String path) {
        String value = "";
        List<ICompositeData> datas = getByPath(src, path);
        if (null != datas) 
        {
        	if(datas.size() == 1)
        	{
        		if(null != datas.get(0).getValue())
        		{
        			value = datas.get(0).getValue().trim();//添加trim
        		}        		
        	}
        	else
        	{
        		log.error("数组直接取值未实现");
        	}            
        }     
        return value==null?"":value;
    }
    
    public static void setValue(ICompositeData src, String path, String value){
        ICompositeData compositeData = mkNodeNotExist(src, path);
        compositeData.setValue(value);
    }

	public static void copy(ICompositeData src, ICompositeData target, String srcPath, String targetPath) {
		if (null != src && null != target) {
			List<ICompositeData> srcCompositeDatas = getByPath(src, srcPath);
			List<ICompositeData> compositeDatas = getByPath(src, srcPath);
			String itemId = targetPath;
			if (targetPath.indexOf("/") > 0) {
				String targetParentPath = StringUtils.substringBeforeLast(targetPath, "/");
				target = mkNodeNotExist(target, targetParentPath);
				itemId = StringUtils.substringAfterLast(targetPath, "/");
			}

			List<ICompositeData> targetChildren = target.getChild(itemId);
			for (int i = 0; i < compositeDatas.size(); i++) {
				String valueToCopy = compositeDatas.get(i).getValue();
				if (i > targetChildren.size() - 1) {
					ICompositeData compositeData = new SoapCompositeData();
					compositeData.setId(itemId);
					compositeData.setValue(valueToCopy);
					target.setChild(itemId, compositeData);
				} else {
					target.getChild(itemId).get(i).setValue(valueToCopy);
				}
			}
		}
		else
		{
			log.error(srcPath+"  ->  "+targetPath+"的数据映射无法完成！");
		}
	}

    public static ICompositeData mkNodeNotExist(ICompositeData src, String nodePath) {
        ICompositeData targetNode = null;
        String[] nodePathItems = nodePath.split("/");
        for (int i = 0; i < nodePathItems.length; i++) {
        		 List<ICompositeData> childNodes = src.getChild(nodePathItems[i]);
                 if (childNodes.size() > 0) {
                     src = childNodes.get(0);
                 } else {
                     ICompositeData compositeData = new SoapCompositeData();
                     compositeData.setId(nodePathItems[i]);
                     src.setChild(nodePathItems[i], compositeData);
                     src = compositeData;
                 }
        }
        return src;
    }
    
    
    /**
     * 此流水會發送到後端系統、送核心不能超過16位
     * @param dest
     * @param seqXpath
     */
    public static void setSeqNo(ICompositeData dest, String seqXpath) {
        //渠道
    	String channel = "";
		try {
			channel = ParamRepository.getRepository().get("MachineName") == null ? 
					"BP" : ParamRepository.getRepository().get("MachineName");
		} catch (InstanceNotFoundException e) {
			log.error("SEQ_NO生成失败!",e);
		}
    	//日期
/*	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
	    simpleDateFormat.applyPattern("yyyyMMddhhmmss");	    
	    String dateStr = simpleDateFormat.format(new Date());*/
	    String dateStr = String.valueOf(System.currentTimeMillis());
	    //8位随机数
	    String randomNumber = generateNumber();
    	String seqNo = channel + dateStr + randomNumber;    	
        //用於記錄到到流水表
        SessionContext.getContext().setValue("SEQ_NO",seqNo);
    	CompositeDataUtils.setValue(dest, seqXpath, seqNo);
    }
    
    /**
     * 设置二代的流水号，需要前三位用XND
     * @param dest
     * @param seqXpath
     */
    public static void setCnaps2SeqNo(ICompositeData dest, String seqXpath) {
    	String firstString = "XND";
    	//日期
	    String dateStr = String.valueOf(System.currentTimeMillis());
	    //8位随机数
	    String randomNumber = generateNumber();
    	String Cnaps2SeqNo = firstString + dateStr + randomNumber;    	
        //用於記錄到到流水表
    	CompositeDataUtils.setValue(dest, seqXpath, Cnaps2SeqNo);
    }
    
    /**
     * 8位递增序号
     * @return
     */
	public static String generateNumber() {
		int maxNum = IncrementNumber.getInstance().getMaxNum();
		if(maxNum <= 99999998){
			int curNum = maxNum + 1;
			IncrementNumber.getInstance().setMaxNum(curNum);
			String curNumStr = curNum + "";
			int length = 8 - curNumStr.length();
			for(int i = 1 ; i <= length;i++ ){
				curNumStr = "0" + curNumStr ;
			}
			return curNumStr;
		}else{
			 IncrementNumber.getInstance().setMaxNum(0);
			 return "00000001";
		}		
	}	
	/*
	 * 3位递增序号
	 * */
	
	public static String gethreeNumber() {
		int maxNum = IncrementNumber.getInstance().getMaxNum();
		if(maxNum <= 998){
			int curNum = maxNum + 1;
			IncrementNumber.getInstance().setMaxNum(curNum);
			String curNumStr = curNum + "";
			int length = 3 - curNumStr.length();
			for(int i = 1 ; i <= length;i++ ){
				curNumStr = "0" + curNumStr ;
			}
			return curNumStr;
		}else{
			 IncrementNumber.getInstance().setMaxNum(0);
			 return "001";
		}		
	}
	/*
	 * 8位数，规则：递增
	 * */
	public static void setEigRanNumber(ICompositeData dest,String seqXpath) {
		String curNumStr = "";
		curNumStr = generateNumber();
		CompositeDataUtils.setValue(dest, seqXpath, curNumStr);
	}
	
	
	/*
	 * 初始化服务期ip
	 * */
	public static void setDomainRef(ICompositeData dest,String seqXpath){
		try {
			String adder = InetAddress.getLocalHost().getHostAddress();
			CompositeDataUtils.setValue(dest, seqXpath, adder);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	/*
	 * 初始化生成到IPP请求流水 规则 ：AP1/AP2 + hhmmss + 3位随机数
	 * */
	public static void setIPPSeqNo(ICompositeData dest,String seqXpath) {
		String MachineName;
		try {
			MachineName = ParamRepository.getRepository().get("MachineName") == null ? "" : ParamRepository.getRepository().get("MachineName");
			Date newdate = new Date();
			DateFormat sbf = new SimpleDateFormat("HHmmss");
			CompositeDataUtils.setValue(dest, seqXpath, MachineName+sbf.format(newdate)+gethreeNumber());
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 设置返回
	 * @param serviceId  返回组合服务服务码
	 * @param retCode   错误码
	 * @param retMsg    自定义响应信息
	 */
	public static void setReturn(String serviceId,String retCode, String newMesg){
		ICompositeData RspServiceId = (ICompositeData) SessionContext
		.getContext().getValue("Rsp" + serviceId);
		if (null == RspServiceId)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			RspServiceId  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			RspServiceId.setId("Rsp" + serviceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			RspServiceId.setxPath("/Rsp" + serviceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			SessionContext.getContext().setValue("Rsp" + serviceId,RspServiceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		Date localDate = new Date();
		ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(RspServiceId, "RspSysHead");
		CompositeDataUtils.setValue(destArrayParent, "RespDate", getLocalTime(localDate, "yyyyMMdd"));
		CompositeDataUtils.setValue(destArrayParent, "RespTime", getLocalTime(localDate, "HHmmss"));
		CompositeDataUtils.setValue(destArrayParent, "TransStatus", "F");
		CompositeDataUtils.setValue(destArrayParent, "RetCode", (retCode == null) ? "" : retCode);
		StringBuffer retMsg = new StringBuffer("");
		String errorMsg = ErrorCodeMapping.getErrorMsg(retCode);
		if(!"".equals(newMesg) && newMesg.length()>0){
			if(errorMsg != null && !"".equals(errorMsg.trim())){
				retMsg.append(errorMsg).append(",").append(newMesg);
			}else{
				retMsg.append(newMesg);
			}
		}else{
			retMsg.append(errorMsg);
		}
		
		CompositeDataUtils.setValue(destArrayParent, "RetMsg", retMsg.toString());
	}
	
	/***
	 * 
	 * @param RspServiceId   响应对象
	 * @param retCode   错误码
	 * @param newMesg   自定义响应信息
	 */
	public static void setReturn(ICompositeData RspServiceId,String retCode, String newMesg){
		Date localDate = new Date();
		CompositeDataUtils.setValue(RspServiceId, "RspSysHead/RespDate", getLocalTime(localDate, "yyyyMMdd"));
		CompositeDataUtils.setValue(RspServiceId, "RspSysHead/RespTime", getLocalTime(localDate, "hhmmss"));
		CompositeDataUtils.setValue(RspServiceId, "RspSysHead/TransStatus", "F");
		CompositeDataUtils.setValue(RspServiceId, "RspSysHead/RetCode", (retCode == null) ? "" : retCode);
		StringBuffer retMsg = new StringBuffer("");
		String errorMsg = ErrorCodeMapping.getErrorMsg(retCode);
		if(!"".equals(newMesg) && newMesg.length()>0){
			if(errorMsg != null && !"".equals(errorMsg.trim())){
				retMsg.append(errorMsg).append(",").append(newMesg);
			}else{
				retMsg.append(newMesg);
			}
		}else{
			retMsg.append(errorMsg);
		}
		
		CompositeDataUtils.setValue(RspServiceId, "RspSysHead/RetMsg", retMsg.toString());
	}
	/**
	 * 获取时间
	 * @param localDate
	 * @param format
	 * @return
	 */
	public static String getLocalTime(Date localDate, String format){
		return new SimpleDateFormat(format).format(localDate);	
	}
	/**
	 * 设置成功返回
	 * @param serviceId  返回组合服务服务码
	 * @param retMsg    自定义响应信息
	 */
	public static void setSucReturn(String serviceId,String msg){
		ICompositeData RspServiceId = (ICompositeData) SessionContext
		.getContext().getValue("Rsp" + serviceId);
		if (null == RspServiceId)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			RspServiceId  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			RspServiceId.setId("Rsp" + serviceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			RspServiceId.setxPath("/Rsp" + serviceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			SessionContext.getContext().setValue("Rsp" + serviceId,RspServiceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		Date localDate = new Date();
		ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(RspServiceId, "RspSysHead");
		CompositeDataUtils.setValue(destArrayParent, "RespDate", getLocalTime(localDate, "yyyyMMdd"));
		CompositeDataUtils.setValue(destArrayParent, "RespTime", getLocalTime(localDate, "hhmmss"));
		CompositeDataUtils.setValue(destArrayParent, "TransStatus", "S");
		CompositeDataUtils.setValue(destArrayParent, "RetCode", "000000");
		CompositeDataUtils.setValue(destArrayParent, "RetMsg", (msg!=null&&!"".equals(msg.trim()))?msg:"交易成功");
	}
	/**
	 * 初始化返回头
	 * @param retCode
	 * @param retMsg
	 * @param tranStatus
	 */
	public static void setSysHead(String serviceId,String retCode,String retMsg,String tranStatus){
		ICompositeData reqICD = (ICompositeData) SessionContext.getContext().getValue("Req"+serviceId);
		ICompositeData RspICD = (ICompositeData) SessionContext.getContext().getValue("Rsp"+serviceId);
		if (null == RspICD)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			RspICD  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			RspICD.setId("Rsp"+serviceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			RspICD.setxPath("/Rsp"+serviceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			SessionContext.getContext().setValue("Rsp"+serviceId,RspICD);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		} 
		ICompositeData RspSysHead = CompositeDataUtils.mkNodeNotExist(RspICD, "RspSysHead");
		CompositeDataUtils.setValue(RspSysHead, "RetCode", retCode);
		CompositeDataUtils.setValue(RspSysHead, "RetMsg", retMsg);
		CompositeDataUtils.setValue(RspSysHead, "TransStatus", tranStatus);
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/ReqSeq", "ReqSeq");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/ServiceID", "ServiceID");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/ChannelID", "ChannelID");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/LegOrgID", "LegOrgID");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/ReqDate", "ReqDate");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/ReqTime", "ReqTime");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/Version", "Version");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/ReqSysID", "ReqSysID");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/DomainRef", "DomainRef");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/AcceptLang", "AcceptLang");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/GlobalSeq", "GlobalSeq");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/OrgSysID", "OrgSysID");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/SvcScn", "SvcScn");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/TmlCd", "TmlCd");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/TlrNo", "TlrNo");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/BrId", "BrId");
	}
}
