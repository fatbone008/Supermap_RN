package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * 非法字段或表名的原因
 *
 * 内部使用，不对外公开，主要用于明确合法的原因以抛出相应的异常
 *
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 李云锦
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
