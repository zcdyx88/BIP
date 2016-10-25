package com.dcits.smartbip.reversal;

import com.dcits.smartbip.runtime.model.IContext;
import com.dcits.smartbip.runtime.model.impl.SessionContext;

/**
 * Created by zhangcheng on 16/8/16.
 */
public interface IReversalService {
	
	/**
	 * 组合服务交易入库
	 * @param serialNum  全局流水号
	 * @param serviceId  服务ID
	 */
    public void insertCompositeTrans(String serialNum, String serviceId);
    
    /**
     * 根据流水号更新组合服务响应码
     * @param serialNum
     * @param returnCode
     */
    public void updateCompositeTrans(String serialNum, String returnCode);
    
    /**
     * 业务服务入库
     * @param serialNum
     * @param serviceId
     * @param compSerialNum 该业务服务所属的组合服务流水号
     */
    public void insertBuszzTrans(String serialNum, String serviceId,String compSerialNum);
    
    /**
     * 根据流水号更新业务服务响应码
     * @param serialNum
     * @param returnCode
     */
    public void updateBuzzTrans(String serialNum, String returnCode);
    
    /**
     * 插入冲正记录，冲正服务器将根据该表中记录去发起冲正
     * @param buszzSerialNum 被冲正交易的流水号,用来关联业务服务交易表
     * @param serviceId 冲正交易服务ID
     * @param context  冲正交易需要用到的上下文，为冲正报文的发送提供数据
     * @param mapper  冲正报文所使用的映射关系
     * @param returnCodeField  判断冲正交易是否成功的响应码域名称，请填写compositeData的路径
     * @param succReturncode  冲正交易成功时的预期响应码
     */
    public void insertReversalInfo(String buszzSerialNum,String serviceId,IContext context,String mapper,String returnCodeField ,String succReturncode);
    
    
}
