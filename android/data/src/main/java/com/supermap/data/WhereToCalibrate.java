/**
 * <p>Title: ·�ɣ���·������ѡ��У��λ��</p>
 * <p>Description: ����ѡ�����У��λ��</p>
 <p>Copyright: Copyright (c) 2011</p> 
 * <p> Company: SuperMap Software Co., Ltd.</p>
 * @author ���쳬
 * @version 6.0
 */
package com.supermap.data;

/**
 * @author shanqc
 * @data 2011-01-25
 *
 */
public final class WhereToCalibrate extends Enum {
	private WhereToCalibrate(int value, int ugcValue) {
        super(value, ugcValue);
    }
	//! ��ȥ����
	public static final WhereToCalibrate NOTTOCOMPUTE=new WhereToCalibrate(0,0);
	//! ֻ�������֮ǰ��Section
	public static final WhereToCalibrate BEFORE=new WhereToCalibrate(1,1);
	//! ֻ�����յ�֮���Section
	public static final WhereToCalibrate AFTER =new WhereToCalibrate(2,2);
	//! ֻ�������֮ǰ + �յ�֮ǰ��Section
	public static final WhereToCalibrate BEFOREANDAFTER=new WhereToCalibrate(3,3);
	//! ֻ�������֮����յ�֮ǰ��Section
	public static final WhereToCalibrate INTERVAL=new WhereToCalibrate(4,4);
	//! �����֮ǰ+���֮����յ�֮ǰ
	public static final WhereToCalibrate BEFOREANDINTERVAL=new WhereToCalibrate(5,5);
	//! �����֮����յ�֮ǰ+�յ�֮ǰ
	public static final WhereToCalibrate INTERVALANDAFTER=new WhereToCalibrate(6,6);
	//! ȫ��Section
	public static final WhereToCalibrate ALLLINE =new WhereToCalibrate(7,7);

}
