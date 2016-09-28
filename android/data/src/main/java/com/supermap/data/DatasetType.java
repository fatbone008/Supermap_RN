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
public class DatasetType  extends Enum{
    private DatasetType(int value,int ugcValue) {
        super(value,ugcValue);
    }

    public static final DatasetType TABULAR = new DatasetType(0,0);
    public static final DatasetType POINT = new DatasetType(1,1);
    public static final DatasetType LINE = new DatasetType(3,3);
    public static final DatasetType REGION = new DatasetType(5,5);
    public static final DatasetType TEXT = new DatasetType(7,7);
    public static final DatasetType IMAGE = new DatasetType(81,81);
    public static final DatasetType CAD = new DatasetType(149,149);
    public static final DatasetType NETWORK = new DatasetType(4,4);
    public static final DatasetType NETWORK3D = new DatasetType(205,205);
    
    public static final DatasetType NdfVector	= new DatasetType(500,500);
    
    public static final DatasetType GRID = new DatasetType(83,83);   
    
    public static final DatasetType WMS = new DatasetType(86,86);

    public static final DatasetType WCS = new DatasetType(87,87);
    
    public static final DatasetType WFS = new DatasetType(151,151);
    
    public static final DatasetType POINT3D = new DatasetType(101,101);
    public static final DatasetType LINE3D = new DatasetType(103,103);
    public static final DatasetType REGION3D = new DatasetType(105,105);
  //内部使用
    public static final DatasetType DEM = new DatasetType(84,84);
}
