package com.dcits.smartbip.protocol;

import java.util.HashMap;
import java.util.Map;

public class ProtocolUtil {

    /**
     * 存储固定长度的byte[]能存放多大的int值
     */
    static Map<Integer, Integer> hexByteMaxMap = new HashMap<Integer, Integer>();

    static {
        hexByteMaxMap.put(1, 256);
        hexByteMaxMap.put(2, 256 * 256);
        hexByteMaxMap.put(3, 256 * 256 * 256);
        hexByteMaxMap.put(4, Integer.MAX_VALUE);
    }

    /**
     * byte数组转int类型
     *
     * @param b
     * @param offset
     * @return
     */
    public static int byte2Int(byte b[], int offset) {
        int intValue = 0;
        int len = b.length;
        for (int i = 0; i < len; i++)
            intValue += (b[offset + i] & 255) << 8 * (len - 1 - i);
        return intValue;
    }

    /**
     * int类型转byte数组
     *
     * @param intValue
     * @param b
     * @param offset   默认为0
     */
    public static void int2Byte(int intValue, byte b[], int offset) {
        int len = b.length;
        //增加对数据校验，检查入参的int值是否超过byte[]存储的范围
        if (intValue > hexByteMaxMap.get(len)) {
            throw new IllegalArgumentException("输入的int数值[" + intValue + "]太大，无法放入长度为[" + len + "]的byte[]中");
        }
        for (int i = 0; i < len; i++) {
            b[offset + i] = (byte) (intValue >> 8 * (len - 1 - i) & 255);
        }
    }

    /***************************************************************************************************/
    public static String printToHex(String title, byte[] data) {
        StringBuilder sb = new StringBuilder(title + "\n");
        sb.append("-Displace-   -0--1--2--3--4--5--6-HEX Value-A--B--C--D--E--F-  ---ASCII Code---\n");
        if (data == null || data.length == 0) {
            return sb.toString();
        }
        int idx = 0, len = 16, count = data.length / len;
        for (int i = 0; i < count; i++) {
            byte[] tmp = new byte[len];
            System.arraycopy(data, idx, tmp, 0, len);
            idx += len;
            sb.append(proc16Bytes(tmp, idx) + "\n");
        }
        int mod = data.length % len;
        if (mod > 0) {
            byte[] tmp = new byte[mod];
            System.arraycopy(data, idx, tmp, 0, mod);
            idx = data.length;
            sb.append(proc16Bytes(tmp, idx) + "\n");
        }
        return sb.toString();
    }

    public static String printToHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        sb.append("-Displace-   -0--1--2--3--4--5--6-HEX Value-A--B--C--D--E--F-  ---ASCII Code---\n");
        if (data == null || data.length == 0) {
            return sb.toString();
        }
        int idx = 0, len = 16, count = data.length / len;
        for (int i = 0; i < count; i++) {
            byte[] tmp = new byte[len];
            System.arraycopy(data, idx, tmp, 0, len);
            idx += len;
            sb.append(proc16Bytes(tmp, idx) + "\n");
        }
        int mod = data.length % len;
        if (mod > 0) {
            byte[] tmp = new byte[mod];
            System.arraycopy(data, idx, tmp, 0, mod);
            idx = data.length;
            sb.append(proc16Bytes(tmp, idx) + "\n");
        }
        return sb.toString();
    }

    private static String proc16Bytes(byte[] data, int idx) {
        StringBuilder sb = new StringBuilder("");

        //计算display位置的值
        String str1 = Integer.toHexString(idx);
        while (str1.length() < 4) {
            str1 = "0" + str1;
        }
        String str2 = String.valueOf(idx);
        while (str2.length() < 4) {
            str2 = "0" + str2;
        }
        sb.append(str1 + "(" + str2 + ")   ");
        //显示十六进制的数据
        for (int i = 0; i < data.length; i++) {
            String b = Integer.toHexString(data[i] & 0xff);
            while (b.length() < 2) {
                b = "0" + b;
            }
            sb.append(b.toUpperCase() + " ");
        }
        //补充长度
        while (sb.toString().length() < 61) {
            sb.append(" ");
        }
        sb.append("  ");
        //显示ascii的数据
        for (int i = 0; i < data.length; i++) {
            if (data[i] > 0x1f && data[i] < 0x7f) {
                sb.append((char) data[i]);
            } else {
                sb.append(".");
            }
        }
        return sb.toString();
    }
    /***************************************************************************************************/

}
