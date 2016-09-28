package com.supermap.data;

class GeoPoint3DNative {
	private GeoPoint3DNative() {

	}

	public native static long jni_New();

	public native static void jni_Delete(long instance);

	public native static double jni_GetX(long instance);

	public native static double jni_GetY(long instance);
	
	public native static double jni_GetZ(long instance);

	public native static void jni_SetX(long instance, double x);

	public native static void jni_SetY(long instance, double y);
	
	public native static void jni_SetZ(long instance, double z);

	public native static long jni_Clone(long instance);

	//public native static void jni_Clear(long instance);

}
