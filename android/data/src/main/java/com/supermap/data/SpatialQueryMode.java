package com.supermap.data;

public final class SpatialQueryMode extends Enum {
	private SpatialQueryMode(int value, int ugcValue) {
        super(value, ugcValue);
    }
	
	//无空间查询
    public static final SpatialQueryMode NONE = new SpatialQueryMode(-1,-1);

    //重合
    public static final SpatialQueryMode IDENTITY = new SpatialQueryMode(0,0);

    //分离
    public static final SpatialQueryMode DISJOINT = new SpatialQueryMode(1,1);

    //相交
    public static final SpatialQueryMode INTERSECT = new SpatialQueryMode(2,2);

    //邻接
    public static final SpatialQueryMode TOUCH = new SpatialQueryMode(3,3);

    //叠加
    public static final SpatialQueryMode OVERLAP = new SpatialQueryMode(4,4);

    //交叉
    public static final SpatialQueryMode CROSS = new SpatialQueryMode(5,5);

    //被包含
    public static final SpatialQueryMode WITHIN = new SpatialQueryMode(6,6);

    //包含
    public static final SpatialQueryMode CONTAIN = new SpatialQueryMode(7,7);
}
