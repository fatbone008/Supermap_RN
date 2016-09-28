package com.supermap.plugin;

import com.supermap.data.Enum;

public class Speaker extends Enum {

	protected Speaker(int value, int ugcValue) {
		super(value, ugcValue);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * YUNXIA,Ů��������
	 */
	public static final Speaker	YUNXIA	= new Speaker(1, 1);
	
	/**
	 * YATOU,Ů�������ˣ�������
	 */
	public static final Speaker	YATOU	= new Speaker(15, 15);
	
	/**
	 * DOLLARS,Ů����ͯ
	 */
	public static final Speaker	DOLLARS	= new Speaker(55, 55);
	
	/**
	 * KANGKANG,�У������ˣ�������
	 */
	public static final Speaker	KANGKANG	= new Speaker(56, 56);
	
	/**
	 * CONGLE,�У�������
	 */
	public static final Speaker	CONGLE	= new Speaker(4, 4);
	
	/**
	 * ROB,�У���ͯ
	 */
	public static final Speaker ROB	= new Speaker(54, 54);
	
	
	
	/**
	 * ��ȡö��ֵ
	 * @return
	 */
	int getValue(){
		return internalGetUGCValue(this);
	}

}
