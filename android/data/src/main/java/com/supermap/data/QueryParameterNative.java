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
class QueryParameterNative {
    private QueryParameterNative() {
    }

    public native static int jni_GetCursorType(long instance);
    
    public native static int jni_GetIDsNumber(long instance);
    public native static void jni_GetQueryIDs(long instance, int[] ids);
    public native static void jni_SetQueryIDs(long instance, int[] ids);
    
    public native static void jni_GetQueryBounds(long instance, double[] bounds);
    public native static void jni_SetQueryBounds(long instance, double[] bounds);

    public native static int jni_GetQueryType(long instance);
    public native static void jni_SetQueryType(long instance, int ugcValue);
    
    public native static void jni_SetCursorType(long instance, int value);

    public native static String jni_GetAttributeFilter(long instance);

    public native static void jni_SetAttributeFilter(long instance,
            String value);

    public native static boolean jni_GetHasGeometry(long instance);

    public native static void jni_SetHasGeometry(long instance, boolean value);

    public native static String jni_GetOrderBy(long instance);

    public native static void jni_SetOrderBy(long instance, String value);

    public native static String jni_GetGroupBy(long instance);

    public native static void jni_SetGroupBy(long instance, String value);

    public native static long jni_GetJoinItems(long instance);

    public native static long jni_GetLinkItems(long instance);

    public native static String jni_GetResultFields(long instance);

    public native static void jni_SetResultFields(long instance, String value);

    public native static long jni_New();

    public native static void jni_Delete(long instance);

    public native static void jni_Reset(long instance);

}
