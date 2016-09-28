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
class JoinItemNative {
    private JoinItemNative() {
    }

    public native static long jni_New();

    public native static void jni_Delete(long instance);

    public native static String jni_GetForeignTable(long instatnce);

    public native static void jni_SetForeignTable(long instatnce, String value);

    public native static String jni_GetJoinFilter(long instatnce);

    public native static void jni_SetJoinFilter(long instatnce, String value);

    public native static int jni_GetJoinType(long instatnce);

    public native static void jni_SetJoinType(long instatnce, int value);

    public native static String jni_GetName(long instatnce);

    public native static void jni_SetName(long instatnce, String value);

    public native static long jin_Clone(long handle);

    public native static void jni_Reset(long instance);
}
