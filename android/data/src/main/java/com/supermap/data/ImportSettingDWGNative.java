package com.supermap.data;

class ImportSettingDWGNative {
	public static native long jni_New();
	
	public static native long jni_Clone(long instance);
	
	public static native void jni_Delete(long handle);
	
	public static native boolean jni_IsImportingAsCAD(long instance);
	
	public static native void jni_SetImportingAsCAD(long instance, boolean value);
	
	public static native boolean jni_IsAttributeIgnored(long instance);
	
	public static native void jni_SetAttributeIgnored(long instance, boolean value);
	
	public static native long jni_GetSpatialIndex(long instance);
	
	public static native void jni_SetSpatialIndex(long instance, long value);
	
	public static native long jni_GetTargetDataInfos(long instance, String value);
	
	public static native long jni_GetTargetDataInfos2(long instance, 
			String targetNamePrefix, int ugcValue, long handle);
	
	public static native void jni_SetTargetDataInfos(long instance, long handle);
	
	public native static String jni_GetStyleMapFilePath(long instance);
    
	public native static void jni_SetStyleMapFilePath(long instance, String path);
	
	public native static void jni_SetIsBlackWhiteInverse(long instance, boolean value);
	
	public native static boolean jni_GetIsBlackWhiteInverse(long instance);
}
