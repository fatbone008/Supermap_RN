package com.supermap.data;
 class ImportDataInfoCSVNative {

		public native static int jni_GetDatasetType(long instance);
		
		public native static void jni_SetDatasetType(long instance, int type);

		public native static int jni_GetRecordCount(long instance);
		
		public static native int jni_GetFieldCount(long instance);
		
		public static native void jni_GetSourceFieldInfos(long instance, long[] handles);

		public native static void jni_SetTargetFieldInfos(long instance, long handle);
		
		public native static void jni_SetTargetFieldInfos2(long instance, int[] fieldState,
				int[] indexeFieldNames, String[] names, int[] indexeFieldTypes, int[] types);
	}
