package com.dcits.smartbip.runtime.model.impl;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.engine.impl.MapperRepository;
import com.dcits.smartbip.engine.impl.ServiceRepository;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.journal.impl.JournalConstants;
import com.dcits.smartbip.parser.model.ProcessDefinitionConstants;
import com.dcits.smartbip.parser.model.ServiceDefinitionConstants;
import com.dcits.smartbip.runtime.model.IAsyncProcessInstance;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IContext;
import com.dcits.smartbip.runtime.model.IMapper;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.transaction.entity.ServiceTransaction;
import com.dcits.smartbip.transaction.service.ServiceTransactionService;
import com.dcits.smartbip.utils.ApplicationUtils;
import com.dcits.smartbip.utils.CompositeDataUtils;

/**
 * Created by vincentfxz on 16/4/26.
 * 异步流程的抽象类,即成异步流程接口.实现:
 * 1.实现Runnable接口中的run方法.
 * 2.定义resultHook接口,resultHook为该抽象类的实现类留下结果集处理的钩子
 */
public abstract class AbstractAsyncProcessInstance implements IAsyncProcessInstance {

    private final Log log = LogFactory.getLog(AbstractAsyncProcessInstance.class);
    protected final Map<String, String> serviceMappings = new ConcurrentHashMap<String, String>();

    private String id;
    //TODO 在异步流程中,线程变量需要本地化
    private IContext sessionContext = SessionContext.getContext();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void init() {
        Stack<IService> stack = new Stack<IService>();
        getContext().setValue(ProcessDefinitionConstants.SERVICE_STACK, stack);
    }

    @Override
    public void run() {
        execute();
        resultHook();
    }

    protected void resultHook() {

    }

    @Override
    public ExecutorService getThreadPool() {
        return Executors.newFixedThreadPool(10);
    }

    protected void callReversal() {
        if (log.isInfoEnabled()) {
            log.info("开始调用冲正");
        }
        //获取服务调用栈
        Stack<IService> stack = (Stack<IService>) getContext().getValue(ProcessDefinitionConstants.SERVICE_STACK);
        IService service = null;
        try {
            while (!stack.isEmpty()) {
                service = stack.pop();
                String reversalServiceId = service.getReversalServiceId();
                IService reversalService = ServiceRepository.getRepository().get(reversalServiceId);
//                Object reversalReq = reversalService.getReqClazz().newInstance();
                //生成请求对象
                ICompositeData reversalReq = new SoapCompositeData();
                String id = "Req" + reversalServiceId;
                reversalReq.setId(id);
                reversalReq.setxPath("/" + id);
                getContext().setValue("Req" + reversalServiceId, reversalReq);
                IMapper mapper = MapperRepository.getRepository().get(getServiceMapping(reversalServiceId));
                mapper.mapReq();
                Object rspObj = reversalService.execute(getContext().getValue("Req" + reversalServiceId));
                getContext().setValue("Rsp" + reversalServiceId, rspObj);
                mapper.mapRsp();

                ServiceTransactionService serviceTransactionService = (ServiceTransactionService) ApplicationUtils.getInstance().getBean("serviceTransactionService");
                ServiceTransaction serviceTransaction = new ServiceTransaction();
                serviceTransaction.setReversalId(reversalServiceId);
                serviceTransaction.setCompServiceId(getId());
                serviceTransaction.setServiceId(service.getId());
                serviceTransaction.setReversalTime("1");
                serviceTransaction.setReversalTotal("5");
                serviceTransaction.setReversalPeriod("60000");
                serviceTransaction.setStatus("success");
                serviceTransaction.setFlowNo((String) getContext().getValue(JournalConstants.UNIQUE_FLOW_NO));
                serviceTransactionService.save(serviceTransaction);
            }
        } catch (Exception e) {
            //异步冲正暂时固定使用redis
            stack.push(service);

            final Stack<IService> timmerStack = stack;
            final String seqNo = (String) getContext().getValue(JournalConstants.UNIQUE_FLOW_NO);

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    log.info("异步冲正开始");
                    int count = 0;
                    IService service = null;
                    try {
                        while (!timmerStack.isEmpty()) {
                            service = timmerStack.pop();
                            String reversalServiceId = service.getReversalServiceId();
                            ServiceTransactionService serviceTransactionService = (ServiceTransactionService) ApplicationUtils.getInstance().getBean("serviceTransactionService");
                            ServiceTransaction serviceTransaction = serviceTransactionService.findOne(seqNo);
                            if (null == serviceTransaction) {
                                serviceTransaction = new ServiceTransaction();
                                serviceTransaction.setReversalId(reversalServiceId);
                                serviceTransaction.setCompServiceId(getId());
                                serviceTransaction.setServiceId(service.getId());
                                serviceTransaction.setReversalTime("0");
                                serviceTransaction.setReversalTotal("6");
                                serviceTransaction.setReversalPeriod("60000");
                                serviceTransaction.setStatus("success");
                                serviceTransaction.setFlowNo((String) getContext().getValue(JournalConstants.UNIQUE_FLOW_NO));
                            }
                            int reversalTime = Integer.parseInt(serviceTransaction.getReversalTime());
                            int total = Integer.parseInt(serviceTransaction.getReversalTotal());
                            count = total - reversalTime;
                            serviceTransaction.setReversalId(reversalServiceId);
                            serviceTransaction.setCompServiceId(getId());
                            serviceTransaction.setServiceId(service.getId());
                            serviceTransaction.setReversalTime(String.valueOf(reversalTime + 1));
                            serviceTransaction.setReversalTotal("6");
                            serviceTransaction.setReversalPeriod("60000");
                            serviceTransaction.setStatus("unknown");
                            serviceTransaction.setFlowNo(seqNo);
                            serviceTransactionService.save(serviceTransaction);
                            IService reversalService = ServiceRepository.getRepository().get(reversalServiceId);
//                          Object reversalReq = reversalService.getReqClazz().newInstance();
                            //生成请求对象
                            ICompositeData reversalReq = new SoapCompositeData();
                            String id = "Req" + reversalServiceId;
                            reversalReq.setId(id);
                            reversalReq.setxPath("/" + id);
                            getContext().setValue("Req" + reversalServiceId, reversalReq);
                            IMapper mapper = MapperRepository.getRepository().get(getServiceMapping(reversalServiceId));
                            mapper.mapReq();
                            Object rspObj = reversalService.execute(getContext().getValue("Req" + reversalServiceId));
                            getContext().setValue("Rsp" + reversalServiceId, rspObj);
                            mapper.mapRsp();
                            serviceTransaction.setStatus("success");
                            serviceTransactionService.save(serviceTransaction);

                        }
                    } catch (Exception e) {
                        if (count > 0) {
                            timmerStack.push(service);
                        }
                        log.error(e, e);
                    }

                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, 0, 60000);
            log.error("冲正失败,调用异步冲正", e);
        }
    }

    protected void callService(String serviceId, boolean persist) throws InvokeException {
        try {

            //获取服务
            IService service = ServiceRepository.getRepository().get(serviceId);
            //如果是需要持久化或者冲正的服务,需要记录服务信息,且服务需要入栈
            if (persist) {
                Stack<IService> serviceStack = (Stack<IService>) getContext().getValue(ProcessDefinitionConstants.SERVICE_STACK);
                serviceStack.push(service);
            }
            //如果是业务服务需要构造请求返回,和冲正处理
            if (ServiceDefinitionConstants.BUZZ_SERVICE_TYPE.equalsIgnoreCase(service.getType())) {
                String id = "Req" + serviceId;
                ICompositeData reqObj = (SoapCompositeData) getContext().getValue(id);
                if (null == reqObj) {
                    //生成请求对象
                    reqObj = new SoapCompositeData();
                    reqObj.setId(id);
                    reqObj.setxPath("/" + id);
                    //请求对象放入上下文
                    getContext().setValue("Req" + serviceId, reqObj);
                }
                //获取映射对象
                //进行请求的数据映射
                IMapper mapper = MapperRepository.getRepository().get(getServiceMapping(serviceId));
                if (null != mapper) {
                    mapper.mapReq();
                }
                //调用服务获得返回
                if (log.isInfoEnabled()) {
                    log.info("开始调用服务[" + getId() + "],输入为[" + reqObj + "]");
                }
                Object rspObj = service.execute(reqObj);
                if (null != getContext().getValue("Rsp" + serviceId)) {
                    //TODO 是否需要整合俩个对象中的数据？？
                }
                getContext().setValue("Rsp" + serviceId, rspObj);

                if (null != mapper) {
                    mapper.mapRsp();
                }


            }
            //如果是基础服务,直接运行基础服务获得返回
            else if (ServiceDefinitionConstants.BASE_SERVICE_TYPE.equalsIgnoreCase(service.getType())) {
                service.execute(null);
            }
        } catch (Exception e) {
            log.error("调用服务失败!", e);
            throw new InvokeException("服务[" + serviceId + "]调用失败!");
        }
    }

    protected boolean checkReversal(String serviceId, String retCode, List<String> retCodeValues) {
        boolean needReversal = false;
        //获取服务的返回
        try {
//            Object rspObj = getContext().getValue("Rsp" + serviceId);
//          String retCodeValue = BeanUtils.getProperty(rspObj, retCode);
            ICompositeData rspObj = (ICompositeData) getContext().getValue("Rsp" + serviceId);
            String retCodeValue = CompositeDataUtils.getValue(rspObj, retCode);

            if (retCodeValues.contains(retCodeValue)) {
                needReversal = true;
            }
        } catch (Exception e) {
            log.error("冲正检查失败!");
        }
        return needReversal;
    }

    //TODO 在异步流程中,线程变量需要本地化
    @Override
    public IContext getContext() {
        return SessionContext.getContext();
    }

    @Override
    public void setContext(IContext context) {
        sessionContext = context;
    }

    private String getServiceMapping(String serviceId) {
        return this.serviceMappings.get(serviceId);
    }
}
