package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * �Ƿ��ֶλ������ԭ��
 *
 * �ڲ�ʹ�ã������⹫������Ҫ������ȷ�Ϸ���ԭ�����׳���Ӧ���쳣
 *
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ���ƽ�
 * @version 2.0
 */
class InternalInvalidState extends Enum {
    private InternalInvalidState(int value, int ugcValue) {
        super(value, ugcValue);
    }

    public static final InternalInvalidState ISEMPTY = new InternalInvalidState(0, 0);
    public static final InternalInvalidState BEYONDLIMIT = new InternalInvalidState(1, 1);
    public static final InternalInvalidState INVALIDCHAR = new InternalInvalidState(2, 2);
    public static final InternalInvalidState HASAGAINNAME = new InternalInvalidState(3, 3);
    public static final InternalInvalidState PREFIXISSM = new InternalInvalidState(4, 4);
    public static final InternalInvalidState PREFIXERROR = new InternalInvalidState(5, 5);
    public static final InternalInvalidState AGAINSYSTEMNAME = new InternalInvalidState(6, 6);

}
