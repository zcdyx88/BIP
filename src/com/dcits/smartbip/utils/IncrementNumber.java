package com.dcits.smartbip.utils;

public class IncrementNumber {
	private static IncrementNumber obj = null;
	private int maxNum;
	
	public static IncrementNumber getInstance() {
		if(obj==null){
			synchronized (IncrementNumber.class){
				obj = new IncrementNumber();
				obj.setMaxNum(0);
			}
		}
		return obj;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public synchronized void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	
}
