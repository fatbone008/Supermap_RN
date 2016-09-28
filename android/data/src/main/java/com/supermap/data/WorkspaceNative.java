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
 * @author ø◊¡Ó¡¡
 * @version 2.0
 */
class WorkspaceNative {
    private WorkspaceNative() {
    }

    public static native long jni_New();

    public static native void jni_Delete(long instance);

    public static native void jni_Close(long instance);

    public static native String jni_GetCaption(long instance);

    public static native void jni_SetCaption(long instance, String value);

    public static native boolean jni_GetIsModified(long instance);

    public static native String jni_GetDescription(long instance);

    public static native void jni_SetDescription(long instance, String value);

    public static native boolean jni_Open(long instance, long connHandle);

    public static native boolean jni_Save(long instance);

    public static native void jni_Reset(long instance);

    public static native long jni_GetConnectionInfo(long instance);
    
    public static native long jni_GetResources(long instance);

	public static native boolean jni_SaveAs(long workspaceHandle, long connectionInfoHandle);
}
