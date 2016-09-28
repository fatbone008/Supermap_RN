package com.supermap.data;

import com.supermap.data.Enum;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author not attributable
 * @version 2.0
 */
class InternalVariantType extends Enum {

    private InternalVariantType(int value, int ugcValue) {
        super(value, ugcValue);
    }

    public static final InternalVariantType NULL = new InternalVariantType(0,0); //! δ���������
    public static final InternalVariantType BYTE = new InternalVariantType(1,1); //! ���ֽ�����

    public static final InternalVariantType SHORT = new InternalVariantType(2,2); //! ˫�ֽ���������

    public static final InternalVariantType INTEGER = new InternalVariantType(3,3); //! ���ֽ���������

    public static final InternalVariantType LONG = new InternalVariantType(4,4); //! ���ֽ���������

    public static final InternalVariantType FLOAT = new InternalVariantType(5,5); //! ���ֽڸ�������

    public static final InternalVariantType DOUBLE = new InternalVariantType(6,6); //! ���ֽڸ�������

    public static final InternalVariantType TIME = new InternalVariantType(7,7); //! ʱ������

    public static final InternalVariantType BINARY = new InternalVariantType(8,8); //! ����������

    public static final InternalVariantType STRING = new InternalVariantType(9,9); //! �ַ���

    public static final InternalVariantType DATE = new InternalVariantType(10,10); //! ��������
    
    public static final InternalVariantType TIMESTAMP = new InternalVariantType(11,11); //! ʱ�������
    
    public static final InternalVariantType BOOLEAN = new InternalVariantType(12,12); //! ��������
}
