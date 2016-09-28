package com.supermap.data;

class ImportDataInfoTABNative {

	public native static int jni_GetDatasetType(long instance);

	public native static int jni_GetRecordCount(long instance);

	public native static int jni_GetFieldCount(long instance);

	public native static void jni_GetSourceFieldInfos(long instance,
			long[] handles);
	public native static void jni_SetTargetFieldInfos(long instance, long [] handles);
	
	public native static void jni_SetTargetFieldInfos2(long instance, int[] fieldState,
			int[] indexes, String[] names, int[] source, int[] target);
}
