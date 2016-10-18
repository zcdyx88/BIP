package com.dcits.smartbip.runtime.impl;
import com.dcits.smartbip.runtime.model.IMapper;
import com.dcits.smartbip.runtime.model.impl.SimpleObjectMapper;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
public class M5001400003102_000001 extends com.dcits.smartbip.runtime.model.impl.SimpleObjectMapper implements com.dcits.smartbip.runtime.model.IMapper{
private Log log = LogFactory.getLog(getClass());

public M5001400003102_000001(){}public void mapReq (){
try{ 

Object temp = null;

ICompositeData destArrayParent = null;

List<ICompositeData> destArray = null;

List<ICompositeData> tmpArray = null;

ICompositeData tmpArrayCompositeData = null;

ICompositeData Req000001 = (ICompositeData)SessionContext.getContext().getValue("Req000001"); 
if (null == Req000001)
{
Req000001  = new SoapCompositeData();
Req000001.setId("Req000001");
Req000001.setxPath("/"+Req000001);
SessionContext.getContext().setValue("Req000001",Req000001);
}
CompositeDataUtils.setValue(Req000001, "Sys_Head/TRAN_CODG", "000001");

CompositeDataUtils.setValue(Req000001, "Body/ACCNO", "11001051200011205");

CompositeDataUtils.setValue(Req000001, "Body/PASSWORD", "201912310020112");

ICompositeData Req010103 = (ICompositeData)SessionContext.getContext().getValue("Req010103"); 
if (null == Req010103)
{
Req010103  = new SoapCompositeData();
Req010103.setId("Req010103");
Req010103.setxPath("/"+Req010103);
SessionContext.getContext().setValue("Req010103",Req010103);
}
CompositeDataUtils.setValue(Req010103, "Body/BIZ_TYPE", "2016");

CompositeDataUtils.setValue(Req010103, "Body/TRAN_AMT", "Z");

CompositeDataUtils.setValue(Req010103, "Body/INPUT_BAL_TYPE", "000000115724");

CompositeDataUtils.setValue(Req010103, "Body/TRANSFER_MODE", "00000028041070");
}catch(Exception e){ 
log.error(e,e);
SessionContext.getContext().setValue("FLOW-CODE", "F");
}finally{
}
}
public void mapRsp (){
try{ 

Object temp = null;

ICompositeData destArrayParent = null;

List<ICompositeData> destArray = null;

List<ICompositeData> tmpArray = null;

ICompositeData tmpArrayCompositeData = null;

ICompositeData Rsp000001 = (ICompositeData)SessionContext.getContext().getValue("Rsp000001"); 
if (null == Rsp000001)
{
Rsp000001  = new SoapCompositeData();
Rsp000001.setId("Rsp000001");
Rsp000001.setxPath("/"+Rsp000001);
SessionContext.getContext().setValue("Rsp000001",Rsp000001);
}
ICompositeData Rsp5001400003102 = (ICompositeData)SessionContext.getContext().getValue("Rsp5001400003102"); 
if (null == Rsp5001400003102)
{
Rsp5001400003102  = new SoapCompositeData();
Rsp5001400003102.setId("Rsp5001400003102");
Rsp5001400003102.setxPath("/"+Rsp5001400003102);
SessionContext.getContext().setValue("Rsp5001400003102",Rsp5001400003102);
}
CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/TRAN_TIMESTAMP", "Sys_Head/TRAN_TIMESTAMP");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/USER_ID", "Sys_Head/USER_ID");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/SOURCE_BRANCH_NO", "Sys_Head/SOURCE_BRANCH_NO");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/BRANCH_ID", "Sys_Head/BRANCH_ID");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/CUSTOMER_ID", "Sys_Head/CUSTOMER_ID");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/DEST_BRANCH_NO", "Sys_Head/DEST_BRANCH_NO");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/TRAN_DATE", "Sys_Head/TRAN_DATE");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/SEQ_NO", "Sys_Head/SEQ_NO");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/TRAN_MODE", "Sys_Head/TRAN_MODE");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/SOURCE_TYPE", "Sys_Head/SOURCE_TYPE");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/WS_ID", "Sys_Head/WS_ID");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/TRAN_CODE", "Sys_Head/TRAN_CODE");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/SERVER_ID", "Sys_Head/SERVER_ID");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/SERVICE_CODE", "Sys_Head/SERVICE_CODE");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/CONSUMER_ID", "Sys_Head/CONSUMER_ID");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/RET_STATUS", "Sys_Head/RET_STATUS");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/HX_ARRAY", "Sys_Head/HX_ARRAY");
List<ICompositeData> srcArray1= new ArrayList<ICompositeData>();
List<ICompositeData> destArray1= new ArrayList<ICompositeData>();
List<ICompositeData> srcArray2= new ArrayList<ICompositeData>();
List<ICompositeData> destArray2= new ArrayList<ICompositeData>();
srcArray1 = CompositeDataUtils.getByPath(Rsp000001, "Sys_Head/HX_ARRAY");
destArray1 = CompositeDataUtils.getByPath(Rsp5001400003102, "Sys_Head/HX_ARRAY");
for (int i1 = 0; i1 < srcArray1.size(); i1++) {
CompositeDataUtils.copy(srcArray1.get(i1), destArray1.get(i1), "Row", "Row");
srcArray2 = CompositeDataUtils.getByPath(srcArray1.get(i1), "Row");
destArray2 = CompositeDataUtils.getByPath(destArray1.get(i1), "Row");
for (int i2 = 0; i2 < srcArray2.size(); i2++) {
CompositeDataUtils.copy(srcArray2.get(i2), destArray2.get(i2), "RET_MSG", "RET_MSG");

CompositeDataUtils.copy(srcArray2.get(i2), destArray2.get(i2), "RET_CODE", "RET_CODE");

}

}

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Body/USER_CODE", "Body/USER_CODE");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Body/USER_NAME", "Body/USER_NAME");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Body/ADDRESS", "Body/ADDRESS");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Body/OFF_ADDRESS", "Body/OFF_ADDRESS");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Body/NOW_PEACE", "Body/NOW_PEACE");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Body/PAY_DATE", "Body/PAY_DATE");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Body/PAY_PROJ", "Body/PAY_PROJ");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Body/PAY_BZ", "Body/PAY_BZ");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Body/PAY_AMT", "Body/PAY_AMT");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Body/BREAK_PRO_AMT", "Body/BREAK_PRO_AMT");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Body/BILL_NO", "Body/BILL_NO");

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/RET_ARRAY", "Sys_Head/RET_ARRAY");
srcArray1 = CompositeDataUtils.getByPath(Rsp000001, "Sys_Head/RET_ARRAY");
destArray1 = CompositeDataUtils.getByPath(Rsp5001400003102, "Sys_Head/RET_ARRAY");
for (int i1 = 0; i1 < srcArray1.size(); i1++) {
CompositeDataUtils.copy(srcArray1.get(i1), destArray1.get(i1), "Row", "Row");
srcArray2 = CompositeDataUtils.getByPath(srcArray1.get(i1), "Row");
destArray2 = CompositeDataUtils.getByPath(destArray1.get(i1), "Row");
for (int i2 = 0; i2 < srcArray2.size(); i2++) {
CompositeDataUtils.copy(srcArray2.get(i2), destArray2.get(i2), "RET_MSG", "RET_MSG");

CompositeDataUtils.copy(srcArray2.get(i2), destArray2.get(i2), "RET_CODE", "RET_CODE");

}

}

CompositeDataUtils.copy(Rsp000001, Rsp5001400003102, "Sys_Head/HX_ARRAY", "Sys_Head/HX_ARRAY");
srcArray1 = CompositeDataUtils.getByPath(Rsp000001, "Sys_Head/HX_ARRAY");
destArray1 = CompositeDataUtils.getByPath(Rsp5001400003102, "Sys_Head/HX_ARRAY");
for (int i1 = 0; i1 < srcArray1.size(); i1++) {
CompositeDataUtils.copy(srcArray1.get(i1), destArray1.get(i1), "Row", "Row");
srcArray2 = CompositeDataUtils.getByPath(srcArray1.get(i1), "Row");
destArray2 = CompositeDataUtils.getByPath(destArray1.get(i1), "Row");
for (int i2 = 0; i2 < srcArray2.size(); i2++) {
CompositeDataUtils.copy(srcArray2.get(i2), destArray2.get(i2), "RET_MSG", "RET_MSG");

CompositeDataUtils.copy(srcArray2.get(i2), destArray2.get(i2), "RET_CODE", "RET_CODE");

}

}
}catch(Exception e){ 
log.error(e,e);
SessionContext.getContext().setValue("FLOW-CODE", "F");
}finally{
}
}
}