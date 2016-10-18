package com.dcits.smartbip.protocol.http.server;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

/**
 * Created by vincentfxz on 16/5/23.
 */
@Configuration
public class HttpServerConfig {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        HttpServerServlet serverServlet = new HttpServerServlet();
        return new ServletRegistrationBean(serverServlet, "/services/*");
    }
}
