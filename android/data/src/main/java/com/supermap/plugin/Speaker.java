package com.supermap.plugin;

import com.supermap.data.Enum;

public class Speaker extends Enum {

	protected Speaker(int value, int ugcValue) {
		super(value, ugcValue);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * YUNXIA,女，成年人
	 */
	public static final Speaker	YUNXIA	= new Speaker(1, 1);
	
	/**
	 * YATOU,女，成年人，讲粤语
	 */
	public static final Speaker	YATOU	= new Speaker(15, 15);
	
	/**
	 * DOLLARS,女，儿童
	 */
	public static final Speaker	DOLLARS	= new Speaker(55, 55);
	
	/**
	 * KANGKANG,男，成年人，讲粤语
	 */
	public static final Speaker	KANGKANG	= new Speaker(56, 56);
	
	/**
	 * CONGLE,男，成年人
	 */
	public static final Speaker	CONGLE	= new Speaker(4, 4);
	
	/**
	 * ROB,男，儿童
	 */
	public static final Speaker ROB	= new Speaker(54, 54);
	
	
	
	/**
	 * 获取枚举值
	 * @return
	 */
	int getValue(){
		return internalGetUGCValue(this);
	}

}
