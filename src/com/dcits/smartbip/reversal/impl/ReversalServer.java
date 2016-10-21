package com.dcits.smartbip.reversal.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.reversal.IReversalServer;

public class ReversalServer implements IReversalServer {
	private static final Log log = LogFactory.getLog(ReversalServer.class);
	static private ReversalServer instance;

	static public ReversalServer getInstance()
	{
		if(null == instance)
		{
			instance = new ReversalServer();
		}
		return instance;
	}
	
	@Override
	public void start() {
		
	}
	
	
	
	public class ReversalThread implements Runnable
	{		
		@Override
		public void run() {
			/*for()
			{
				
			}*/
			
		}
	}


	
	

}
