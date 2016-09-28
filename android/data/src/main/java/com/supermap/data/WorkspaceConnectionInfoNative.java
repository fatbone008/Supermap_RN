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
class WorkspaceConnectionInfoNative {
    private WorkspaceConnectionInfoNative() {
    }

    public native static String jni_GetName(long instance);

    public native static void jni_SetName(long instance, String value);

    public native static int jni_GetType(long instance);

    public native static void jni_SetType(long instance, int value);

    public native static String jni_GetServer(long instance);

    public native static void jni_SetServer(long instance, String value);

    public native static String jni_GetUser(long instance);

    public native static void jni_SetUser(long instance, String value);

    public native static String jni_GetPassword(long instance);

    public native static void jni_SetPassword(long instance, String value);

    public native static int jni_GetVersion(long instance);

    public native static void jni_SetVersion(long instance, int value);

    public native static long jni_New();
    
    public native static void jni_Delete(long instance);
    
    public native static long jni_Clone(long instance);

    //赋值操作，调用底层的"="
    public native static void jni_SetValue(long instance, long value);

    public native static void jni_Reset(long instance, String name,
                                        int type, String server,
                                        String database, String user,
                                        String password,
                                        String driver, int version);
}
