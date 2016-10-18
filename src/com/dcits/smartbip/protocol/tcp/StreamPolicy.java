package com.dcits.smartbip.protocol.tcp;


//import com.dc.esb.container.launcher.precompiler.PreCompilerHeler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Wrapped info of stream read/write policy
 *
 * @author chentao
 *
 *         Created at 2011 2011-3-24 上午09:34:12
 */
public class StreamPolicy {
    private static Log log = LogFactory.getLog(StreamPolicy.class);
    public static final int SIGN = 0;
    public static final int LENUNKNOWN = 1;
    public static final int LENFIXED = 2;
    public static final int LENDYNAMIC = 3;
    public static final int LENNET = 4;

    private int category = LENUNKNOWN;
    private String sign = "";
    private int beginIndex, endIndex, fixedLength,hexLength,dLength;
    private String policy = "";
    private static final String _format = "Format: (length:(unknown|(s=num1,e=num2)|num|hl=num1,dl=num2))|(sign:string)";

    public StreamPolicy(String policy) {
        this.policy = policy;
        if ("length:unknown".equals(policy) || "".equals(policy)) {
            category = LENUNKNOWN;
            this.policy = "length:unknown";
        } else if(policy!=null && policy.startsWith("length:hl")){
            //hl=2表示报文前2个十六进制字节是长度，
            //dl=2表示hl的长度包含自身长度，读取后面报文的时候需要减去自身长度2
            //length:hl=2,dl=2
            category = LENNET;
            String[] strs = policy.split(":");
            if (strs.length != 2) {
                throwIllegalException();
            }
            strs = strs[1].split(",");
            hexLength = Integer.parseInt(strs[0].split("=")[1]);
            dLength = Integer.parseInt(strs[1].split("=")[1]);
        }else {
            String[] strs = policy.split(":");
            if (strs.length != 2) {
                throwIllegalException();
            }
            if (strs[0].equals("length")) {
                try {
                    int index = strs[1].indexOf(",");
                    if (index == -1) {
                        fixedLength = Integer.parseInt(strs[1]);
                        category = LENFIXED;
                    }
                    else {
                        category = LENDYNAMIC;
                        String _beginStr = strs[1].substring(0, index);
                        String _endStr = strs[1].substring(index + 1);
                        beginIndex = Integer.parseInt(_beginStr.substring(_beginStr.indexOf("s=") + 2));
                        endIndex = Integer.parseInt(_endStr.substring(_endStr.indexOf("e=") + 2));
                    }
                } catch (Exception e) {
                    String str = "Illegal policy, make sure the policy in format of: " + _format;
                    log.error(str,e);
                    new IllegalArgumentException(str);
                }

            }
            else if (strs[0].equals("sign")) {
                category = SIGN;
                sign = strs[1];
            }
            else {
                throwIllegalException();
            }


        }
    }

    public int getCategory() {
        return category;
    }

    public String getSign() {
        return sign;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public int getFixedLen() {
        return fixedLength;
    }

    public String getPolicy() {
        return policy;
    }

    public int getHexLength(){
        return this.hexLength;
    }

    public int getDLength(){
        return this.dLength;
    }

    private void throwIllegalException() {
        throw new IllegalArgumentException(
                "Illegal policy, make sure the policy in format of: "
                        + _format);
    }
}
