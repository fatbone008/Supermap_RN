package com.supermap.data;

class GeoModelNative {
	private GeoModelNative() {

	}

	public native static long jni_New();

	public native static void jni_Delete(long instance);

	public native static String jni_GetName(long instance);

	public native static void jni_SetName(long instance, String value);

	public static native long jni_Clone(long instance);

	public native static boolean jni_FromFile(long instance, String value);

	public native static boolean jni_ToSGM(long instance, String value);
	
	public native static boolean jni_ToSGZ(long instance, String value);

	public native static void jni_ComputeBoundingBox(long instance);
	
	public native static void jni_SetBoundingBox(long instance, long value);
	
	public native static long jni_GetAnimation(long instance);

	public native static boolean jni_ToStreamFile(long instance, String strStreamFile);
}
