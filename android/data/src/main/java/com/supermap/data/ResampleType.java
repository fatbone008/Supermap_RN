package com.supermap.data;

public class ResampleType extends com.supermap.data.Enum{
	
	// �ز�������
	private ResampleType(int value, int ugcValue)
	{
		super(value, ugcValue);
	}
	//�������㷨
	public static final ResampleType RTBEND = new ResampleType(1,1);
	// ������˹�㷨
	public static final ResampleType RTGENERAL = new ResampleType(2,2);
	// �ǶȲ���
	//public static final ResampleType RTANGLE = new ResampleType(3,3);
}