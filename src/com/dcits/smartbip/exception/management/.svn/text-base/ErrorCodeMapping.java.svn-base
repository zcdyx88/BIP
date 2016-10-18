package com.dcits.smartbip.exception.management;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.dcits.smartbip.exception.CodeDesc;
import com.dcits.smartbip.exception.ErrorCodes;

public class ErrorCodeMapping {
	
	static private  Map<String,CodeDesc> codeDescMap = new HashMap<String,CodeDesc>();
	
    private static void loadErrorCode()
    {    	
		Field[] fields = ErrorCodes.class.getFields();
		if (fields != null) {
			for (int i = 0, len = fields.length; i < len; i++) {
				CodeDesc an = fields[i].getAnnotation(CodeDesc.class);
				codeDescMap.put(an.code(), an);
			}
		}
	}    

	public static String getErrorMsg(String code) {
		String errorCodeDesc = "";
		if(code == null || code.length() == 0) {
			return errorCodeDesc;
		}		
		if(codeDescMap.size() < 1)
		{
			loadErrorCode();
		}		
		CodeDesc an = codeDescMap.get(code);		
		if (null != an ) {
			errorCodeDesc = codeDescMap.get(code).description();
		}
		return errorCodeDesc;	
	}
	
}
