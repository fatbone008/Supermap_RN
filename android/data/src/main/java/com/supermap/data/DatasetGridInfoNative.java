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
 * @author ¿Ó‘∆Ωı
 * @version 2.0
 */
class DatasetGridInfoNative {
    private DatasetGridInfoNative() {
    }

    public native static String jni_GetName(long instance);

    public native static void jni_SetName(long instance, String value);

    public native static void jni_SetBounds(long instance, double left,double bottom,double right,double top);

    public native static void jni_GetBounds(long instance,double[] rect);

    public native static int jni_GetWidth(long instance);

    public native static void jni_SetWidth(long instance, int value);

    public native static int jni_GetHeight(long instance);

    public native static void jni_SetHeight(long instance, int value);

    public native static int jni_GetPixelFormat(long instance);

    public native static void jni_SetPixelFormat(long instance, int value);

    public native static int jni_GetBlockSize(long instance);

    public native static void jni_SetBlockSize(long instance, int value);

    public native static int jni_GetEncodeType(long instance);

    public native static void jni_SetEncodeType(long instance, int value);

    public native static double jni_GetNoValue(long instance);

    public native static void jni_SetNoValue(long instance, double value);

    public native static double jni_GetMinValue(long instance);

    public native static void jni_SetMinValue(long instance, double value);

    public native static double jni_GetMaxValue(long instance);

    public native static void jni_SetMaxValue(long instance, double value);

    public native static long jni_New();

    public native static void jni_Delete(long instance);

    public native static long jni_Clone(long instance);

    public native static void jni_Reset(long instance);
    
    public native static void jni_ResetIsMultiBand(long instance,boolean value);
    
    public native static long jni_New2(long templateDatasetHandle);
}
