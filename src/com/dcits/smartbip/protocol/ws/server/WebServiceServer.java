package com.dcits.smartbip.protocol.ws.server;

import org.reficio.ws.builder.SoapBuilder;
import org.reficio.ws.builder.SoapOperation;
import org.reficio.ws.builder.core.Wsdl;
import org.reficio.ws.common.ResourceUtils;
import org.reficio.ws.common.XmlUtils;
import org.reficio.ws.server.core.SoapServer;
import org.reficio.ws.server.responder.AbstractResponder;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.transform.Source;
import java.net.URL;

/**
 * Created by vincentfxz on 16/5/25.
 */
public class WebServiceServer {

    public void start(){
        SoapServer server = SoapServer.builder()
                .httpPort(9090)
                .build();
        server.start();
        URL wsdlUrl = ResourceUtils.getResourceWithAbsolutePackagePath("/", "ws/service/30021010001/30021010001.wsdl");
        Wsdl parser = Wsdl.parse(wsdlUrl);
        final SoapBuilder builder = parser.binding().localPart("ESBServerSoapBinding").find();
        AbstractResponder customResponder = new AbstractResponder(builder) {
            @Override
            public Source respond(SoapOperation invokedOperation, SoapMessage message) {
                try {
//                    message.getPayloadResult()
                    // build the response using builder
                    message.getDocument();

                    String response = builder.buildOutputMessage(invokedOperation);
                    // here you can tweak the response -> for example with XSLT
                    //...
                    return XmlUtils.xmlStringToSource(response);
                } catch (Exception e) {
                    // will automatically generate SOAP-FAULT
                    throw new RuntimeException("my custom error", e);
                }
            }
        };
        server.registerRequestResponder("/service", customResponder);
    }

    public static void main(String[] args) {
        WebServiceServer webServiceServer = new WebServiceServer();
        webServiceServer.start();
    }
}
