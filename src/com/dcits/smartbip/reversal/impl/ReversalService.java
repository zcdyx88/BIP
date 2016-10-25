package com.dcits.smartbip.reversal.impl;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import com.dcits.smartbip.journal.impl.UniqueInnerFlowNoGenerator;
import com.dcits.smartbip.reversal.IReversalService;
import com.dcits.smartbip.reversal.entity.BipReversalInfoEntity;
import com.dcits.smartbip.reversal.service.BipReversalInfoService;
import com.dcits.smartbip.runtime.model.IContext;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.ApplicationUtils;

public class ReversalService implements IReversalService {
	@Override
	public void insertCompositeTrans(String serialNum, String serviceId) {
		//
		
	}

	@Override
	public void updateCompositeTrans(String serialNum, String returnCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertBuszzTrans(String serialNum, String serviceId, String compSerialNum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBuzzTrans(String serialNum, String returnCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertReversalInfo(String buszzSerialNum, String serviceId, IContext context, String mapper,String returnCodeField ,String succReturncode) {
		BipReversalInfoService infoService = (BipReversalInfoService)ApplicationUtils.getInstance().getBean("bipReversalInfoService");
		BipReversalInfoEntity infoEntity = new BipReversalInfoEntity();
		infoEntity.setId(UniqueInnerFlowNoGenerator.getInstance().generate());
		infoEntity.setBuszzSerialNum(buszzSerialNum);
		infoEntity.setBuzzServiceID(serviceId);		
		infoEntity.setBuzzServiceMapper(mapper);
		infoEntity.setReversalField(returnCodeField);
		infoEntity.setReversalSuccCode(succReturncode);
		infoEntity.setFlowContext(ObjectToByte(context));
		infoService.save(infoEntity);
	}

	private  byte[] ObjectToByte(Object obj) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			bytes = bo.toByteArray();
			bo.close();
			oo.close();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
			e.printStackTrace();
		}
		return bytes;
	}

}
