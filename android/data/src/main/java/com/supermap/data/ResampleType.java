package com.supermap.data;

public class ResampleType extends com.supermap.data.Enum{
	
	// 重采样类型
	private ResampleType(int value, int ugcValue)
	{
		super(value, ugcValue);
	}
	//逐点采样算法
	public static final ResampleType RTBEND = new ResampleType(1,1);
	// 道格拉斯算法
	public static final ResampleType RTGENERAL = new ResampleType(2,2);
	// 角度采样
	//public static final ResampleType RTANGLE = new ResampleType(3,3);
}