package com.dcits.smartbip.runtime.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class CardExchange extends AbstractBaseService implements IService {

	private static final Log log = LogFactory.getLog(CardExchange.class);

	@Override
	public String getId() {
		return "CardExchange";
	}

	@Override
	public String getType() {
		return "base";

	}

	@Override
	public void setType(String type) {

	}

	@Override
	public Object execute(Object req) throws InvokeException {

		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[CardExchange]......");
		}

		// 获得综合签约请求数据对象
		ICompositeData Req2002201000101 = (ICompositeData) SessionContext
				.getContext().getValue("Req2002201000101");
		ICompositeData Req3001200000402 = (ICompositeData) SessionContext
		.getContext().getValue("Req3001200000402");
		if (null == Req3001200000402)
		{
			Req3001200000402  = new SoapCompositeData();
			Req3001200000402.setId("Req3001200000402");
			Req3001200000402.setxPath("/Req3001200000402");
			getContext().setValue("Req3001200000402",Req3001200000402);
		}
		
		List<String> signCodeTypes = new ArrayList<String>();
		List<ICompositeData> CBSFinanceGeneralSignArray = CompositeDataUtils.getByPath(
				Req2002201000101, "ReqAppBody/CBSFinanceGeneralSignReq/CBSFinanceGeneralSignArray");
		
		for (ICompositeData signComposite : CBSFinanceGeneralSignArray) {
			
			String chapdaih = CompositeDataUtils.getValue(signComposite,"chapdaih");
			
			
			//卡内活转定
			if(chapdaih.equalsIgnoreCase("220012"))
			{
				//核心localhead中需要 加密种子
				CompositeDataUtils.copy(signComposite, Req3001200000402,"shoqguiy", "ReqLocalHead/shoqguiy");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"shoqmima", "ReqLocalHead/shoqmima");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"sqqrenbz", "ReqLocalHead/sqqrenbz");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"syspinsd", "ReqLocalHead/syspinsd");
				
//				CompositeDataUtils.copy(signComposite, Req3001200000402,"qsqqczbz", "ReqAppBody/qsqqczbz");
				CompositeDataUtils.setValue(Req3001200000402, "ReqAppBody/qsqqczbz", "0");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"kehuzhao", "ReqAppBody/kehuzhao");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"jiaoymma", "ReqAppBody/jiaoymma");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"zhhuzwmc", "ReqAppBody/zhhuzwmc");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"chapdaih", "ReqAppBody/chapdaih");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"jujijine", "ReqAppBody/jujijine");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"znckzhlx", "ReqAppBody/znckzhlx");				
				CompositeDataUtils.copy(signComposite, Req3001200000402,"znckzcgz", "ReqAppBody/znckzcgz");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"qyxieyih", "ReqAppBody/qyxieyih");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"zhhaoxuh", "ReqAppBody/zhhaoxuh");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"zhfutojn", "ReqAppBody/zhfutojn");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"kehuleix", "ReqAppBody/kehuleix");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"sfxkkhzh", "ReqAppBody/sfxkkhzh");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"qicunjie", "ReqAppBody/qicunjie");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"huazhyuz", "ReqAppBody/huazhyuz");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"yznkhzbz", "ReqAppBody/yznkhzbz");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"llfdonbz", "ReqAppBody/llfdonbz");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"lilvfdbl", "ReqAppBody/lilvfdbl");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"lcdqclfs", "ReqAppBody/lcdqclfs");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"zdhzbzhi", "ReqAppBody/zdhzbzhi");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"shishbbz", "ReqAppBody/shishbbz");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"sshzjine", "ReqAppBody/sshzjine");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"chufleix", "ReqAppBody/chufleix");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"lchuglfs", "ReqAppBody/lchuglfs");
				CompositeDataUtils.copy(signComposite, Req3001200000402,"mimazlei", "ReqAppBody/mimazlei");
				
				CompositeDataUtils.copy(signComposite, Req3001200000402, "llistinfo", "ReqAppBody/llistinfo");
				List<ICompositeData> srcArray1= new ArrayList<ICompositeData>();
				List<ICompositeData> destArray1= new ArrayList<ICompositeData>();
				srcArray1 = CompositeDataUtils.getByPath(signComposite, "llistinfo");
				destArray1 = CompositeDataUtils.getByPath(Req3001200000402, "ReqAppBody/llistinfo");
				for (int i1 = 0; i1 < srcArray1.size(); i1++) {
					CompositeDataUtils.copy(srcArray1.get(i1), destArray1.get(i1), "cunqiiii", "cunqiiii");
					CompositeDataUtils.copy(srcArray1.get(i1), destArray1.get(i1), "baifenbi", "baifenbi");
				}
				
			}
		}


		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CardExchange]......");
		}

		return null;
	}

}
