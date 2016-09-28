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
 * @author 李云锦
 * @version 2.0
 */
class InternalUGCType extends Enum {
    private InternalUGCType(int value, int ugcValue) {
        super(value, ugcValue);
    }
    /**
     * @todo 等全部开发完成后调整该枚举顺序
     * @reason: 其中包括许多无用的值；顺序也需要修改
     */
    public static final InternalUGCType UGMap = new InternalUGCType(1,1);
    public static final InternalUGCType UGMapWnd = new InternalUGCType(2,2);
    public static final InternalUGCType UGStyle = new InternalUGCType(3,3);
    public static final InternalUGCType UGDsConnection = new InternalUGCType(4,4);
    public static final InternalUGCType UGDataSource = new InternalUGCType(5,5);
    public static final InternalUGCType UGGeoLine = new InternalUGCType(6,6);
    public static final InternalUGCType UGGeoRegion = new InternalUGCType(7,7);
    public static final InternalUGCType UGGeometry = new InternalUGCType(8,8);
    public static final InternalUGCType UGSelection = new InternalUGCType(9,9);
    public static final InternalUGCType UGThemeLabelBackgroundStyle = new InternalUGCType(10,10);
    public static final InternalUGCType UGTrackingLayer = new InternalUGCType(11,11);
    public static final InternalUGCType UGLayer = new InternalUGCType(12,12);
    public static final InternalUGCType UGMapBackgroundStyle = new InternalUGCType(13,13);
    public static final InternalUGCType UGGeoText = new InternalUGCType(14,14);
    public static final InternalUGCType UGSubPart = new InternalUGCType(15,15);
    public static final InternalUGCType UGTextStyle = new InternalUGCType(16,16);
    public static final InternalUGCType UGThemeLabel = new InternalUGCType(17,17);
    public static final InternalUGCType UGThemeGraphItem = new InternalUGCType(18,18);
    public static final InternalUGCType UGThemeGraphAxes = new InternalUGCType(19,19);
    public static final InternalUGCType UGDatasetVector = new InternalUGCType(20,20);
    public static final InternalUGCType UGFieldInfo = new InternalUGCType(21,21);
    public static final InternalUGCType UGLayerSelection = new InternalUGCType(22,22);
    public static final InternalUGCType UGFieldInfos = new InternalUGCType(23,23);
    public static final InternalUGCType UGQueryDef = new InternalUGCType(24,24);
    public static final InternalUGCType UGRecordset = new InternalUGCType(25,25);
    public static final InternalUGCType UGThemeUnique = new InternalUGCType(26,26);
    public static final InternalUGCType UGThemeItem = new InternalUGCType(27,27);
    public static final InternalUGCType UGJoinItem = new InternalUGCType(28,28);
    public static final InternalUGCType UGLinkItem = new InternalUGCType(29,29);
    public static final InternalUGCType UGThemeLabelTextStyleAt = new InternalUGCType(30,30);
    public static final InternalUGCType UGLayerThemeLabelTextStyleAt = new InternalUGCType(31,31);

}
