package com.supermap.data;

class ImportSettingKMLNative 
{
	public static native long jni_New();
	public static native long jni_Clone(long instance);
	public static native void jni_Delete(long instance);
	public static native boolean jni_IsImportingAsCAD(long instance);
	public static native void jni_SetImportingAsCAD(long instance, boolean value);
	public static native long jni_GetTargetDataInfos(long instance, String value);
	public static native long jni_GetTargetDataInfos2(long instance,String targetNamePrefix, int ugcValue, long handle);	
	public static native void jni_SetTargetDataInfos(long instance, long handle);
	public static native boolean jni_GetUnvisibleObjectIgnored(long instancer);
	public static native void jni_SetUnvisibleObjectIgnored(long instancer,boolean value);
}
