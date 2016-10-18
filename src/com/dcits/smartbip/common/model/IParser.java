package com.dcits.smartbip.common.model;

import com.dcits.smartbip.exception.ConfigurationParseException;

/**
 * Created by vincentfxz on 16/4/18.
 */
public interface IParser <DT,RT> {
    RT parse(IConfiguration config) throws ConfigurationParseException;
    RT parse(DT dt) throws ConfigurationParseException;
}
