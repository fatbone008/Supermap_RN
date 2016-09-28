package com.supermap.data;

/**
 * ��������Դ��������
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
	 * Ĭ�ϵ�����Դ��������
	 */
	public static final DatasourceEncrytionType DEFAULT = new DatasourceEncrytionType(0, 0);
	
	/**
	 * AES��������
	 */
	public static final DatasourceEncrytionType AES = new DatasourceEncrytionType(1, 1);
}
