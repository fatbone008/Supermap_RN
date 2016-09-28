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
class DatasetVectorInfoNative {
    private DatasetVectorInfoNative() {
    }

    public native static String jni_GetName(long instance);

    public native static void jni_SetName(long instance, String value);

    public native static int jni_GetType(long instance);

    public native static void jni_SetType(long instance, int value);

    public native static int jni_GetEncodeType(long instance);

    public native static void jni_SetEncodeType(long instance, int value);

    public native static boolean jni_GetIsMemoryCache(long instance);

    public native static void jni_SetIsMemoryCache(long instance, boolean value);

    public native static boolean jni_GetIsFileCache(long instance);

    public native static void jni_SetIsFileCache(long instance, boolean value);

 //   public native static boolean jni_GetIsLongTransaction(long instance);
 //   public native static void jni_SetIsLongTransaction(long instance, boolean value);

    public native static long jni_New();

    public native static void jni_Delete(long instance);

    public native static long jni_Clone(long instance);

    public native static void jni_Reset(long instance);
    
    public native static long jni_New2(long templateDatasetHandle);
}
