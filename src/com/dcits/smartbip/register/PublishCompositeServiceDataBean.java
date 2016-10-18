package com.dcits.smartbip.register;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by vincentfxz on 16/5/7.
 */
public class PublishCompositeServiceDataBean implements Serializable {
    private static final long serialVersionUID = 1L;
    String serviceId;
    String serviceName;
    String serviceDesc;
    String namespace;
    String endpoint;
    String serviceSchema;
    String metadataSchema;
    Map<String, String> flowSchema;
	public Object processContent;

    public PublishCompositeServiceDataBean(String serviceId, String serviceName, String serviceDesc, String namespace,
                                           String endpoint, String serviceSchema, String metadataSchema, Map<String, String> flowSchema) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceDesc = serviceDesc;
        this.namespace = namespace;
        this.endpoint = endpoint;
        this.serviceSchema = serviceSchema;
        this.metadataSchema = metadataSchema;
        this.flowSchema = flowSchema;
    }


}
