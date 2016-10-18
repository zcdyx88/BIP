package com.dcits.smartbip.engine;

import com.dcits.smartbip.runtime.model.IProcessInstance;

import java.util.concurrent.ExecutorService;

/**
 * Created by vincentfxz on 16/4/18.
 * 流程处理引擎接口
 */
public interface IProcessEngine<T extends IProcessInstance> extends ExecutorService {
}
