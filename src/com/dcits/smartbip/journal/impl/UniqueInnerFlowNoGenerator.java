package com.dcits.smartbip.journal.impl;

import com.dcits.smartbip.engine.impl.ParamRepository;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.journal.FlowNoGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vincentfxz on 16/8/7.
 */
public class UniqueInnerFlowNoGenerator implements FlowNoGenerator {

    private static final Log log = LogFactory.getLog(UniqueInnerFlowNoGenerator.class);
    private static final String SERIAL_NUMBER = "XXXX"; // 流水号格式
    private static AtomicInteger counter;
    private String MachineName = "BIP-";
    private static UniqueInnerFlowNoGenerator uniqueInnerFlowNoGenerator = null;

    private UniqueInnerFlowNoGenerator() {
        counter = new AtomicInteger(0);
        try {
            String name = ParamRepository.getRepository().get("MachineName") == null ? "" : ParamRepository.getRepository().get("MachineName");
            MachineName = MachineName + name + "-";
        } catch (InstanceNotFoundException e) {
            log.error(e, e);
        }
    }

    /**
     * 取得PrimaryGenerater的单例实现
     *
     * @return
     */
    public static UniqueInnerFlowNoGenerator getInstance() {
        if (uniqueInnerFlowNoGenerator == null) {
            synchronized (UniqueInnerFlowNoGenerator.class) {
                if (uniqueInnerFlowNoGenerator == null) {
                    uniqueInnerFlowNoGenerator = new UniqueInnerFlowNoGenerator();
                }
            }
        }
        return uniqueInnerFlowNoGenerator;
    }

    /**
     * 生成下一个编号
     */
    @Override
    public String generate() {
        String id = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        int count = SERIAL_NUMBER.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("0");
        }
        DecimalFormat df = new DecimalFormat("0000");
        // & 0x1FFF最大值为8191当单路TPS为8191以上时候,流水号会重复
        StringBuilder idBuilder = new StringBuilder();
        idBuilder.append(MachineName).append(formatter.format(date)).append(df.format(counter.getAndAdd(1) & 0x1FFF));
     /*   id = MachineName + formatter.format(date)
                + df.format(counter.getAndAdd(1) & 0x1FFF);*/
        String flowId = idBuilder.toString();
        return flowId;
    }

    public static void main(String[] args) {
        System.out.println(UniqueInnerFlowNoGenerator.getInstance().generate());
        System.out.println(UniqueInnerFlowNoGenerator.getInstance().generate());
        System.out.println(UniqueInnerFlowNoGenerator.getInstance().generate());
        System.out.println(UniqueInnerFlowNoGenerator.getInstance().generate());
    }
}
