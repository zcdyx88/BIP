package com.dcits.smartbip.reversal.impl;

import com.dcits.smartbip.reversal.IReversalService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;

public class ReversalService implements IReversalService {
	
	@Override
	public void insertCompositeTrans(String serialNum, String serviceId) {
		// TODO Auto-generated method stub
		
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
	public void insertReversalInfo(String buszzSerialNum, String serviceId, SessionContext context, String mapper,String returnCodeField ,String succReturncode) {
		// TODO Auto-generated method stub
		
	}



}
