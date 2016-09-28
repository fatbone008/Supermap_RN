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
 * @author SuperMap Objects Java Team
 * @version 2.0
 */
class GeoPointNative {
    private GeoPointNative() {
    }
    public native static long jni_New();

    public native static void jni_Delete(long instance);

    public native static double jni_GetX(long instance);

    public native static double jni_GetY(long instance);

    public native static void jni_SetX(long instance,double x);

    public native static void jni_SetY(long instance,double y);

    public native static long jni_Clone(long instance);
}
