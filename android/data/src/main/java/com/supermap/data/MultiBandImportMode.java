/**
 * 
 */
package com.supermap.data;

/**
 * @author Administrator
 *
 */
class MultiBandImportMode extends com.supermap.data.Enum {
	
	private MultiBandImportMode(int value, int ugcValue) {
		super(value, ugcValue);
	}

	//���ನ�����ݵ���Ϊ������������ݼ�
//	public static final MultiBandImportMode SINGLEBAND = new MultiBandImportMode(0, 0);

	//���ನ�����ݵ���Ϊһ���ನ�����ݼ�
	public static final MultiBandImportMode MULTIBAND = new MultiBandImportMode(1, 1);

//	//���ನ�����ݵ���Ϊһ�����������ݼ���Ŀǰ������ֻ������RGB������8λ�����ݵ���Ϊһ��RGB������24λ�����ݼ�
//	public static final MultiBandImportMode COMPOSITE = new MultiBandImportMode(2, 2);
}
