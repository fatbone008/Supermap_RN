package com.supermap.data;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: SuperMap GIS Technologies Inc.
 * </p>
 * 
 * @author ø◊¡Ó¡¡
 * @version 2.0
 */
class DatasourcesNative {
	public native static long jni_Open(long instance,
			long connectionInfoHandle, String[] temps);
	
	public native static long jni_Create(long instance,
			long connectionInfoHandle);
	
	public static native int jni_GetCount(long instance);

	public static native void jni_GetDatasources(long instance, long[] handles);

	public native static boolean jni_Close(long instance, int index);
	
	public native static int jni_IndexOf(long instance, String alias);
	
	public native static void jni_SetFilePathRequest(long instance, String filePathRequest);
}
