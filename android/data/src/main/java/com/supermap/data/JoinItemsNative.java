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
class JoinItemsNative {
    private JoinItemsNative() {
    }

    public native static int jni_GetCount(long instance);

    /**
     *
     * @param instance long
     * @param joinItemHandle long
     * @return long 返回新添加的函数指针
     */
    public native static long jni_Add(long instance, long joinItemHandle);

    public native static long jni_Insert(long instance, int index, long joinItemHandle);

    public native static boolean jni_Remove(long instance, int index);

    public native static long jni_New();

    public native static void jni_Delete(long instance);

    public native static void jni_Set(long desHandle, long valueHandle);

    public native static long jni_Clone(long instance);

    public native static void jni_GetHandles(long instance,long[] handles);

}
