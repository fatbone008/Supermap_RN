/**
 * <p>Title: ·�ɣ���·�������У��ģʽ</p>
 * <p>Description: ����ѡ��·�ɣ���·�������У��</p>
 <p>Copyright: Copyright (c) 2011</p> 
 * <p> Company: SuperMap Software Co., Ltd.</p>
 * @author ���쳬
 * @version 6.0
 */
package com.supermap.data;
/**
 * @author shanqc
 * @data 2011-01-25
 */
public final class CalibrateMode extends Enum {
    private CalibrateMode(int value, int ugcValue) {
        super(value, ugcValue);
    }
    // @added by shanqc at 2011��01��25�� 

    //ͨ������У����·
    public static final CalibrateMode BYDISTANCE = new CalibrateMode(1, 1);

    //ͨ�����(Mֵ)У����·
    public static final CalibrateMode BYMEASURE  = new CalibrateMode(2, 2);
}
