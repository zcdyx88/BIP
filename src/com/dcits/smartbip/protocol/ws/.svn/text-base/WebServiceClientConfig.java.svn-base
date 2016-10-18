package com.dcits.smartbip.protocol.ws;

import com.dcits.smartbip.engine.impl.ServiceRepository;
import com.dcits.smartbip.protocol.ws.client.WSClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * Created by vincentfxz on 16/5/8.
 */
@Configuration
public class WebServiceClientConfig {

    @Bean(name = "WSClient3002100000103")
    public WSClient get3002100000103Client() {
        WSClient client = new WSClient();
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.dcitsbiz.esb.services._30021000001");
        client.setDefaultUri("http://localhost:18080/ws/3002100000103");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean(name = "WSClient3002101000103")
    public WSClient get3002101000103Client() {
        WSClient client = new WSClient();
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.dcitsbiz.esb.services._30021000001");
        client.setDefaultUri("http://localhost:8080/ws/3002101000103");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean(name = "WSClient4001100000401")
    public WSClient get4001100000401Client() {
        WSClient client = new WSClient();
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.dcitsbiz.esb.services._40011000004");
        client.setDefaultUri("http://localhost:8080/ws/40011000004");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
