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
 * @author 张继南
 * @version 2.0
 */
class SpatialIndexInfoNative {

    private SpatialIndexInfoNative() {
    }

    public native static long jni_New();

    public native static long jni_New2(int type);

    public native static long jni_New3(int leafObjectCount);

    public native static long jni_New4(String tileField);

    public native static long jni_New5(double tileWidth,double tileHeight);

    public native static long jni_New6(double gridCenterX, double gridCenterY,
                                       double gridSize0, double gridSize1,
                                       double gridSize2);
    // 拷贝构造函数调用
    public native static long jni_New7(long spatialIndexInfoHandle);

    public native static void jni_Delete(long instance);

    public native static int jni_GetType(long instance);

    public native static void jni_SetType(long instance,int value);

    public native static int jni_GetLeafObjectCount(long instance);

    public native static void jni_SetLeafObjectCount(long instance,int value);

    public native static String jni_GetTileField(long instance);

    public native static void jni_SetTileField(long instance,String value);

    public native static double jni_GetTileWidth(long instance);

    public native static void jni_SetTileWidth(long instance,double value);

    public native static double jni_GetTileHeight(long instance);

    public native static void jni_SetTileHeight(long instance,double value);

    public native static double[] jni_GetGridCenter(long instance);

    public native static void jni_SetGridCenter(long instance,double x,double y);

    public native static double jni_GetGridSize0(long instance);

    public native static void jni_SetGridSize0(long instance,double value);

    public native static double jni_GetGridSize1(long instance);

    public native static void jni_SetGridSize1(long instance,double value);

    public native static double jni_GetGridSize2(long instance);

    public native static void jni_SetGridSize2(long instance,double value);

}
