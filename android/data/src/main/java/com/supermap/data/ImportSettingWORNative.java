package com.supermap.data;

class ImportSettingWORNative {
	public native static long jni_New();

	public native static void jni_Delete(long handle);

	public native static void jni_setWsInfo(long handle, long handle2);

	public native static String[] jni_GetDataInfosDatasetNames(long handle);

	public native static String[] jni_GetDataInfosMapNames(long handle);

	public native static long jni_CloneWorkspaceConnectionInfo(long handle);
}
