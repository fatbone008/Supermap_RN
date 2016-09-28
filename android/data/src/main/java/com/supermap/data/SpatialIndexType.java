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
 * @author not attributable
 * @version 2.0
 */
public class SpatialIndexType extends Enum {
    private SpatialIndexType(int value, int ugcValue) {
        super(value, ugcValue);
    }
//    //! 空间索引
//    enum IndexType
//    {
//            IdxNone		= 1,		//没有索引
//            IdxRTree	= 2,		//Rtree索引
//            IdxQTree	= 3,		//四叉树索引
//            IdxTile		= 4,		//图幅索引 UGC里不支持编辑
//            IdxDynamic  = 5,		//multi-level Grid
//};
    /**
     * 没有索引
     */
    public static final SpatialIndexType NONE  = new SpatialIndexType(1, 1);

    /**
     * R树索引
     */
    public static final SpatialIndexType RTREE= new SpatialIndexType(2, 2);

    /**
     * 四叉树索引
     */
    public static final SpatialIndexType QTREE = new SpatialIndexType(3, 3);

    /**
     * 图幅索引
     */
    public static final SpatialIndexType TILE  = new SpatialIndexType(4, 4);

    /**
     * 多级网格索引
     */
    public static final SpatialIndexType MULTI_LEVEL_GRID = new SpatialIndexType(5, 5);
}
