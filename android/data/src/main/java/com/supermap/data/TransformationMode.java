package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: 配准模式</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 卢浩
 * @version 6.0
 */
public final class TransformationMode extends Enum {
    private TransformationMode(int value, int ugcValue) {
        super(value, ugcValue);
    }
    
    //矩形配准
    public static final TransformationMode RECT = new TransformationMode(0,0);

    //线性配准
    public static final TransformationMode LINEAR = new TransformationMode(1,1);

    //多项式配准
    public static final TransformationMode SQUARE = new TransformationMode(2,2);

    //偏移配准
    public static final TransformationMode OFFSET = new TransformationMode(4,4);
}
