package com.supermap.data;

/**
 * 定义数据源加密类型
 * 
 * @author XingJun
 * @date 2015.11.20
 *
 */
public class DatasourceEncrytionType extends Enum {

	private DatasourceEncrytionType(int value, int ugcValue) {
		super(value, ugcValue);
	}

	/**
	 * 默认的数据源加密类型
	 */
	public static final DatasourceEncrytionType DEFAULT = new DatasourceEncrytionType(0, 0);
	
	/**
	 * AES加密类型
	 */
	public static final DatasourceEncrytionType AES = new DatasourceEncrytionType(1, 1);
}
