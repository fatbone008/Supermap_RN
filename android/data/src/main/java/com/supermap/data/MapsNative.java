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
class MapsNative {
    private MapsNative() {
    }

    public native static int jni_Add(long instance, String name, String xml);
    
    public native static int jni_GetCount(long instance);

    public native static String jni_GetItem(long instance, int index);
    
    public native static void jni_Clear(long instance);
    
    public native static boolean jni_Remove(long instance, int index);
    
    public native static int jni_IndexOf(long instance, String name);
    
    public native static boolean jni_SetMapXML(long instance, int index, String xml);
}