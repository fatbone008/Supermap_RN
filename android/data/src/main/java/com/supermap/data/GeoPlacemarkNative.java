package com.supermap.data;

class GeoPlacemarkNative {
	public native static long jni_New();

	public native static long jni_Clone(long instance);

	public native static long jni_New1(String name, long value);

	public native static void jni_Delete(long instance);

	public native static String jni_getName(long instance);

	public native static void jni_setName(long instance, String value);

	public native static long jni_getGeometry(long instance);

	public native static void jni_setGeometry(long instance, long value);

	public native static void jni_setNameStyle(long instance, long value);

	public native static long jni_getNameStyle(long instance);
}
