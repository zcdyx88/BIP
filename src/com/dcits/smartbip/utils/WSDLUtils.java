package com.dcits.smartbip.utils;

import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Created by vincentfxz on 16/5/8.
 */
public class WSDLUtils {
    public static DefaultWsdl11Definition createWsdlDefinition(String portType, String locationUri, String targetNamespace, XsdSchema xsdSchema){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName(portType);
        wsdl11Definition.setLocationUri(locationUri);
        wsdl11Definition.setTargetNamespace(targetNamespace);
        wsdl11Definition.setSchema(xsdSchema);
        return wsdl11Definition;
    }
}
