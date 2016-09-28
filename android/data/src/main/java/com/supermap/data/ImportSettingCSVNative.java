package com.supermap.data;

 class ImportSettingCSVNative {
	
	private ImportSettingCSVNative() {
		
	}
	
	public static native long jni_New();
	
	public static native long jni_Clone(long instance);
	
	public static native void jni_Delete(long handle);
	
	public native static String jni_GetSeparator(long instance);
	
	public static native void jni_SetSeparator(long instance, String separator);
	
	public static native void jni_SetImportingAsCAD(long instance, boolean value);
	
	public static native boolean jni_GetHasFieldNameInFirstRow(long instance);
	
	public static native void jni_SetHasFieldNameInFirstRow(long instance, boolean value);
	
	public static native long jni_GetSpatialIndex(long instance);
	
	public static native void jni_SetSpatialIndex(long instance, long handle);
	
	public static native long jni_GetTargetDataInfos(long instance, String value);
	
	public static native long jni_GetTargetDataInfos2(long instance, 
			String targetNamePrefix, int ugcValue, long handle);
	
	public static native void jni_SetIsTargetFieldInfosByUse(long instance, boolean value);
	
	public static native void jni_SetTargetDataInfos(long instance, long handle);
	
}
