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
//    //! �ռ�����
//    enum IndexType
//    {
//            IdxNone		= 1,		//û������
//            IdxRTree	= 2,		//Rtree����
//            IdxQTree	= 3,		//�Ĳ�������
//            IdxTile		= 4,		//ͼ������ UGC�ﲻ֧�ֱ༭
//            IdxDynamic  = 5,		//multi-level Grid
//};
    /**
     * û������
     */
    public static final SpatialIndexType NONE  = new SpatialIndexType(1, 1);

    /**
     * R������
     */
    public static final SpatialIndexType RTREE= new SpatialIndexType(2, 2);

    /**
     * �Ĳ�������
     */
    public static final SpatialIndexType QTREE = new SpatialIndexType(3, 3);

    /**
     * ͼ������
     */
    public static final SpatialIndexType TILE  = new SpatialIndexType(4, 4);

    /**
     * �༶��������
     */
    public static final SpatialIndexType MULTI_LEVEL_GRID = new SpatialIndexType(5, 5);
}
