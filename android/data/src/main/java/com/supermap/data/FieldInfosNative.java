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
 class FieldInfosNative {
    private FieldInfosNative() {
    }


    public native static long jni_Add(long instance, long fieldInfoHandle,long datasetHandle);

    public native static void jni_Clear(long instance);

    public native static boolean jni_Remove(long instance, int index,long datasetVectorHandle);
    
    public native static long jni_New();

    public native static void jni_Delete(long instance);

    public native static int jni_IndexOf(long instacne, String name);

    public native static int jni_GetCount(long instance);

    public native static void jni_GetFieldInfos(long instance, long[] handles);
    
    // 添加插入字段接口(2014/7/19 added by hp)
    public native static long jni_Insert(long instance, int index, long fieldInfoHandle);
    
    // 添加交换字段接口(2014/7/23 added by huangkj)
    public native static void jni_Exchange(long instance, int index1, int index2);
    
    public native static long jni_GetFieldInfoByIndex(long instance,int index);
}
