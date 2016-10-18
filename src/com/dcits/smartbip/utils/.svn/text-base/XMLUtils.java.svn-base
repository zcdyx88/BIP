package com.dcits.smartbip.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.dcits.smartbip.runtime.entity.BipProductsTab;
import com.dcits.smartbip.runtime.entity.Production;
import com.dcits.smartbip.runtime.property.ProductionsService;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vincentfxz on 16/4/23.
 */
public class XMLUtils {

    private static final Log log = LogFactory.getLog(XMLUtils.class);

    public static Document getDocFromBytes(byte[] bytes) {
        Document doc = null;
        SAXReader saxReader = new SAXReader();
        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(bytes));
            doc = saxReader.read(bufferedInputStream);
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if (null != bufferedInputStream) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    log.error(e, e);
                }
            }
        }
        return doc;
    }

    public static Document getDocFromFile(File file) {
        Document doc = null;
        if (null != file) {
            SAXReader reader = new SAXReader();
            BufferedInputStream bufferedInputStream = null;
            try {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                doc = reader.read(bufferedInputStream);
            } catch (Exception e) {
                log.error(e, e);
            } finally {
                if (null != bufferedInputStream) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e) {
                        log.error(e, e);
                    }
                }
            }
        }
        return doc;
    }

    public static String getAttribute(Node node, String attrName){
        String attr = null;
        Node attrNode = node.selectSingleNode("@" + attrName);
        if(null != attrNode){
            attr = attrNode.getText();
        }
        return attr;
    }

    public static String getChildElementText(Element element, String tag){
        String text = null;
        Element childElement = element.element(tag);
        text = childElement.getText();
        return text;
    }

    public static Map<String, String> elementToMap(Element element){
        Map<String, String> map = new HashMap<String, String>();
        List<Element> childElements = element.elements();
        for(Element childElement : childElements){
            String key = childElement.getName();
            String value = childElement.getText();
            map.put(key, value);
        }
        return map;
    }
    
    /**
	 * 获取所有对私/对公签约产品信息
	 * @param privyCorpFlg	:	个人对公标志：0-个人，1-对公
	 * @return	对私/对公签约产品信息
	 */
    public static Map<String,Production> getSignCodeInfo(String privyCorpFlg){
		return ProductionsService.getInstance().getProductMap(privyCorpFlg);
	}
    /**
     * 获取所有产品参数信息
     * @return
     */
    public static int deleteById(String signVarCd,String privyCorpFlg){
    	return ProductionsService.getInstance().deleteById(signVarCd, privyCorpFlg);
    }
    /**
     * 获取某个产品参数信息
     * @return
     */
    public static Production getOneProduct(String productCode,String privyCorpFlg){
    	return ProductionsService.getInstance().getProduct(productCode, privyCorpFlg);
    }
    /**
     * 更新产品参数信息 
     * @param bipProducts
     */
    public static void saveProducts(BipProductsTab bipProducts){
    	ProductionsService.getInstance().saveProducts(bipProducts);
    }
}
