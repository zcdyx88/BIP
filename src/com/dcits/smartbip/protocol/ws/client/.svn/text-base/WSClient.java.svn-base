package com.dcits.smartbip.protocol.ws.client;

import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IServiceProxy;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 * Created by vincentfxz on 16/5/9.
 */
public class WSClient extends WebServiceGatewaySupport implements IServiceProxy{
    public Object invoke( Object req) {
        return getWebServiceTemplate().marshalSendAndReceive(getDefaultUri(), req);
    }

	@Override
	public String pack(ICompositeData compositeData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompositeData unpack(String msg) {
		// TODO Auto-generated method stub
		return null;
	}
}
