package com.dcits.smartbip.reversal;

/**
 * Created by vincentfxz on 16/9/1.
 */
public interface IReversalServer {
	/**
	 * 启动冲正服务器，主要是启动轮询线程定时查询数组库BIP_REVERSAL_INFO表里面的数据进行冲正
	 */
	public void start();
	
		
}
