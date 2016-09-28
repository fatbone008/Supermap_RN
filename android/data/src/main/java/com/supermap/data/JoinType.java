package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 孔令亮
 * @version 2.0
 */
public final class JoinType extends Enum {
    private JoinType(int value, int ugcValue) {
        super(value, ugcValue);
    }

//    public static final JoinType INVALID = new JoinType(0,0);
    //内连接
    public static final JoinType INNERJOIN = new JoinType(0,0);

    //左连接
    public static final JoinType LEFTJOIN = new JoinType(1,1);

//    //右连接
//    public static final JoinType RIGHTJOIN = new JoinType(2,2);
//
//    //全连接
//    public static final JoinType FULLJOIN = new JoinType(3,3);
}
