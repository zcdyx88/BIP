package com.dcits.smartbip.protocol.tcp;

import com.dcits.smartbip.protocol.ProtocolUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by vincentfxz on 16/9/19.
 */
public class TCPHandler {

    private static final Log log = LogFactory.getLog(TCPHandler.class);
    //TCP 读取的Buffer长度
    private static final int MAX_SIZE = 8192;
    //
    private static final int[] limits = new int[]{9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};
    //TCP socket
    private Socket socket;
    //读取策略
    private StreamPolicy readPolicy;
    //写策略
    private StreamPolicy writePolicy;

    private boolean closedFlag;

    public TCPHandler(Socket socket, StreamPolicy readPolicy, StreamPolicy writePolicy) {
        this.socket = socket;
        this.readPolicy = readPolicy;
        this.writePolicy = writePolicy;
    }

    /**
     * read data from socket
     *
     * @param socket
     * @return
     * @throws IOException
     */
    public byte[] read(Socket socket) throws IOException {
        byte[] bytes = null;
        if (log.isDebugEnabled()) {
            log.debug("Read Policy is '" + readPolicy.getPolicy() + "'");
        }
        switch (readPolicy.getCategory()) {
            case StreamPolicy.LENUNKNOWN:
                bytes = readByDefault(socket);
                break;
            case StreamPolicy.LENFIXED:
            case StreamPolicy.LENDYNAMIC:
            case StreamPolicy.LENNET:
                bytes = readBylength(socket);
//                System.out.println(new String(bytes));
//                bytes = formatXML(new String(bytes),"UTF-8");
                break;
            default:
                throw new IOException("Unrecgonized read policy: " + readPolicy.getPolicy());
        }
        if (log.isDebugEnabled()) {
            log.debug("Length of received bytes  is " + (bytes == null ? "0" : bytes.length));
//            log.debug(ProtocolUtil.printToHex("Received bytes is", bytes));
//            log.debug(new String(bytes));
//            log.debug("请求长度===>"+new String(bytes).length());
        }
        return bytes;
    }
    
    public static byte[] formatXML(String reqStr,String encoding){
		SAXReader reader = new SAXReader();
		//创建一个串的字符输入流
		StringReader in = new StringReader(reqStr);
		try {
			Document doc = reader.read(in);
			OutputFormat formater = OutputFormat.createPrettyPrint(); 
			formater.setEncoding(encoding);
			StringWriter out   = new StringWriter();
			XMLWriter writer = new XMLWriter(out, formater);
			writer.write(doc);
			return out.toString().getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    /**
     * 默认的Socket读取方式 注意 只适用于短连接
     *
     * @param socket
     * @return
     * @throws IOException
     */
    private byte[] readByDefault(Socket socket) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());
        byte[] receiveReply = null;
        int byteRead;
        byte[] tmp = new byte[MAX_SIZE];
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            while ((byteRead = inputStream.read(tmp)) != -1) {
                baos.write(tmp, 0, byteRead);
            }
            receiveReply = baos.toByteArray();
        } finally {
            if (baos != null) {
                baos.close();
            }
            if (null != inputStream) {
                inputStream.close();
            }
            if (null != socket) {
                socket.close();;
            }
        }
        return receiveReply;
    }

    /**
     * 按照指定长度读取数据 适用短连接和长连接
     *
     * @param socket
     * @param socket
     * @return
     * @throws IOException
     */
    private byte[] readBylength(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        int length = 0;
        ByteBuffer byteBuffer = null;
        // fixed length
        read:
        {
            if (readPolicy.getCategory() == StreamPolicy.LENFIXED) {
                byteBuffer = ByteBuffer.allocate(length = readPolicy.getFixedLen());
            }
            if (readPolicy.getCategory() == StreamPolicy.LENNET) {
                int netLength = readPolicy.getHexLength();
                int lastLength = readPolicy.getDLength();
                byte[] tmp = new byte[netLength];
                //读取网络字节长度数据
                inputStream.read(tmp, 0, netLength);
                //将网络字节长度转换为int类型的长度
                length = ProtocolUtil.byte2Int(tmp, 0);
                if (log.isDebugEnabled()) {
                    log.debug("The all length of bytes is " + length);
                }
                byteBuffer = ByteBuffer.allocate(length);
                byteBuffer.put(tmp);
                //计算剩余报文的长度
                length = length - lastLength;
                if (log.isDebugEnabled()) {
                    log.debug("The last length of bytes is " + length);
                }
            }
            // dynamic length
            else {
                int len1 = readPolicy.getBeginIndex();
                byte[] beforeLenHeader = null;
                if (len1 > 0) {
                    beforeLenHeader = new byte[len1];
                    if (check(inputStream.read(beforeLenHeader), len1, socket).isNoMatch()) {
                        throw new IOException("Read bytes before beginIndex("
                                + readPolicy.getBeginIndex() + ") failed!");
                    }
                }

                int len2 = readPolicy.getEndIndex() - readPolicy.getBeginIndex() + 1;
                byte[] lenHeader = new byte[len2];
                CheckResult result = check(inputStream.read(lenHeader), len2, socket);
                if (result.isNoMatch()) {
                    throw new IOException("Read length header failed!");
                }
                if (result.isEnd()) {
                    break read;
                }
            	try {
            		//计算实际的长度头
					String num = new String(lenHeader);
					length = Integer.parseInt(num);
					if (length < 0)
						length = 0;
				} catch (Exception e) {
					log.error("Invalid length header: ", e);
					dropExtra(inputStream);
					return null;
				}
                // 不读入长度头
                byteBuffer = ByteBuffer.allocate(len1 + length);
                if (len1 > 0) {
                    byteBuffer.put(beforeLenHeader);
                }

                if (log.isDebugEnabled()) {
                    log.debug("The required lenth of read bytes is " + length);
                }
            }
            if (length < 8192) {
                int readLen = 0;
                byte[] temp = new byte[length];
                int count = 0;
                while (readLen != length) {
                    if (count == -1) {
                        // 接受到的报文实际长度小于协议中定义的长度。
                        throw new IOException("Length defined in protocol is " + length
                                + ",but real length of package been read is " + readLen + ".");
                    }
                    count = inputStream.read(temp, readLen, length - readLen);
                    if (count >= 0)
                        readLen += count;
                }
                byteBuffer.put(temp, 0, readLen);
                dropExtra(inputStream);
            } else {
                int n = length / 8192;
                for (int g = 0; g < n; g++) {
                    int readLen = 0;
                    byte[] temp = new byte[8192];
                    while (readLen != 8192) {
                        readLen += inputStream.read(temp, readLen, 8192 - readLen);
                    }
                    byteBuffer.put(temp);
                }
                int mn = (length - 8192 * n); // Get residual
                if (mn > 0) {
                    byte[] temp = new byte[mn];
                    int readLen = 0;
                    while (readLen != mn) {
                        readLen += inputStream.read(temp, readLen, mn - readLen);
                    }
                    byteBuffer.put(temp);
                }
                dropExtra(inputStream);
            }
            byte[] receiveReply = byteBuffer.array();
            byteBuffer.clear();
            return receiveReply;
        }
        return null;
    }

    private CheckResult check(int readLen, int requiredLen, Socket socket) {
        CheckResult result = new CheckResult();
        if (readLen == -1) {
            result.setEnd(true);
            log.warn("Reach the end of inputstream");
            try {
                this.closedFlag = true;
                socket.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        } else if (readLen != requiredLen) {
            result.setNoMatch(true);
            log.error("The length of read bytes is less than required");
        }
        return result;
    }


    /**
     * 按照buffer大小，只读一次。
     *
     * @param socket
     * @param buffersize
     * @return
     * @throws IOException
     */
    public byte[] readByOnce(Socket socket, int buffersize) throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] buffer = new byte[buffersize];
        int byteRead;
        byteRead = inputStream.read(buffer);
        if (byteRead == -1) {
            String s = "Client should not shutdown outputstream before response received.";
            log.error(s);
            throw new IOException(s);
        }
        byte[] req = new byte[byteRead];
        System.arraycopy(buffer, 0, req, 0, byteRead);
        if (log.isDebugEnabled())
            log.debug("ReadByOnce finish.The size of buffer is [" + buffersize
                    + "].The length of bytes been read is [" + byteRead + "]");
        return req;
    }

    /**
     * Write object to socket, after that close the socket
     *
     * @param obj
     * @throws IOException
     */
    public void writeAndShutdown(byte[] obj) throws IOException {
        write(this.socket, obj, true);
    }

    /**
     * Write object to socket and don't close socket
     *
     * @param obj
     * @throws IOException
     */
    public void writeAndKeepAlive(byte[] obj) throws IOException {
        write(this.socket, obj, false);
    }

    /**
     * write data to socket according to the write policy
     *
     * @param socket
     * @param bytes
     * @throws IOException
     */
    private void write(Socket socket, byte[] bytes, boolean isShutdown) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("Write policy is '" + writePolicy.getPolicy() + "'");
//            log.debug(ProtocolUtil.printToHex("Write bytes is", bytes));
        }

        switch (writePolicy.getCategory()) {
            case StreamPolicy.LENUNKNOWN:
                writeByDefault(socket, bytes, isShutdown);
                break;
            case StreamPolicy.LENFIXED:
            case StreamPolicy.LENDYNAMIC:
                writeBylength(socket, bytes, isShutdown);
                break;
            case StreamPolicy.SIGN:
                writeBySign(socket, bytes, writePolicy.getSign(), isShutdown);
                break;
            default:
                throw new IOException("Unrecgonized write policy: " + writePolicy.getPolicy());
        }
    }

    private void writeBylength(Socket socket, byte[] bytes, boolean isShutdown) throws IOException {
        OutputStream os = socket.getOutputStream();
        if (log.isDebugEnabled()) {
            log.debug("Write by length, length of written bytes is " + (bytes.length));
        }
        int length = 0;
        if (writePolicy.getCategory() == StreamPolicy.LENFIXED) {
            length = writePolicy.getFixedLen();
            if (length > bytes.length) {
                log.error("The length of data is less than required, requried length is " + length
                        + ", and current length is " + bytes.length);
            } else {
                os.write(bytes, 0, length);
                os.flush();
            }
        } else {
            // 采用Buffer写入byte,然后一次写出,多次write可能收不全(内部机制待确认)
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            /*
             * 报文 的一部分定义了报文体的长度 例如RQ00512345, 005表示将要读取的后面的长度 对应的配置 s=2,e=4
             */
            int beginIndex = writePolicy.getBeginIndex();
            int endIndex = writePolicy.getEndIndex();
            if (beginIndex > bytes.length - 1 || beginIndex < 0 || endIndex < beginIndex) {
                throw new IOException("Length index is out of the bounds");
            }
            byte[] lengthBytes = null;
            int leftLen = bytes.length - beginIndex;
            int compLen = endIndex - beginIndex + 1 - getDigit(leftLen);
            if (compLen < 0) {
                throw new IOException("The max of length header is " + compLen
                        + ", not long enough to define the data length of " + leftLen);
            }
            if (compLen > 0) {
                // 左补上0, 作为长度描述
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < compLen; i++) {
                    sb.append('0');
                }
                sb.append(leftLen);
                lengthBytes = sb.toString().getBytes();
            } else {
                lengthBytes = String.valueOf(leftLen).getBytes();
            }
            if (beginIndex > 0) {
                baos.write(bytes, 0, beginIndex);
            }
            baos.write(lengthBytes);
            baos.write(bytes, beginIndex, bytes.length - beginIndex);
            baos.flush();
            os.write(baos.toByteArray());
            os.flush();
        }
        if (isShutdown) {
            socket.shutdownOutput();
        }
    }

    private void writeBySign(Socket socket, byte[] bytes, String endTag, boolean isShutdown)
            throws IOException {
        OutputStream os = socket.getOutputStream();
        byte[] endBytes = endTag.getBytes();
        byte[] wbytes = new byte[bytes.length + endBytes.length];
        System.arraycopy(bytes, 0, wbytes, 0, bytes.length);
        System.arraycopy(endBytes, 0, wbytes, bytes.length, endBytes.length);

        os.write(wbytes);
        if (log.isDebugEnabled()) {
            log.debug("Write by sign, length of written bytes is " + (wbytes.length));
        }
        os.flush();
        if (isShutdown) {
            socket.shutdownOutput();
            if (log.isDebugEnabled()) {
                log.debug("Shutdown Output.");
            }
        }
    }

    private void writeByDefault(Socket socket, byte[] bytes, boolean isShutdown) throws IOException {
        OutputStream os = socket.getOutputStream();
        os.write(bytes);
        if (log.isDebugEnabled()) {
            log.debug("Write by default, length of written bytes is " + bytes.length);
        }
        os.flush();

        if (isShutdown) {
            socket.shutdownOutput();
        }
    }

    /**
     * get digit of a integer
     *
     * @param num
     * @return
     */
    private int getDigit(int num) {
        if (num <= 0)
            return 0;
        // faster than log10
        for (int index = 0; index < limits.length; index++) {
            if (num <= limits[index]) {
                return index + 1;
            }
        }
        return limits.length;
    }

    private void dropExtra(InputStream is) throws IOException {
    }

    private class CheckResult {
        private boolean isEnd;
        private boolean isNoMatch;

        public boolean isEnd() {
            return isEnd;
        }

        public void setEnd(boolean isEnd) {
            this.isEnd = isEnd;
        }

        public boolean isNoMatch() {
            return isNoMatch;
        }

        public void setNoMatch(boolean isNoMatch) {
            this.isNoMatch = isNoMatch;
        }
    }
}
