package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description:�α����ͳ��� </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ������
 * @version 2.0
 */
public final class CursorType extends Enum {
    private CursorType(int value, int ugcValue) {
        super(value, ugcValue);
    }

    // @modified by ���ƽ� at 2007��7��23�� ����01ʱ53��04��
    // @reason  ���ĵ�ͬ�� ɾ������ö��ֵ

    //��̬�α�
    public static final CursorType DYNAMIC = new CursorType(2, 2);

    //��̬�α�
    public static final CursorType STATIC = new CursorType(3, 3);
}
