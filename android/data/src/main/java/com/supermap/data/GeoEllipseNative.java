package com.supermap.data;

class GeoEllipseNative {
	public native static long jni_New();

	public native static long jni_Clone(long instatnce);

	public native static void jni_Delete(long instatnce);

	public native static long jni_New1(double centerX, double centerY,
			double semimajorAxis, double semiminorAxis, double angle);

	public native static long jni_New2(long handle);

	public native static long jni_New3(double left, double right, double top,
			double buttom);

	public native static void jni_getCenter(long handle, double[] pt);

	public native static void jni_setCenter(long handle, double centerX,
			double centerY);

	public native static double jni_getSemimajorAxis(long handle);

	public native static void jni_setSemimajorAxis(long handle, double value);

	public native static double jni_getSemiminorAxis(long handle);

	public native static void jni_setSemiminorAxis(long handle, double value);

	public native static double jni_getAngle(long handle);

	public native static void jni_setAngle(long handle, double value);

	public native static double jni_getPerimeter(long handle);

	public native static double jni_getArea(long handle);

	 public native static long jni_convertToLine(long handle,int
	 segmentCount);
		
	 public native static long jni_convertToRegion(long handle,int
	 segmentCount);

	// public native static void jni_Clear(long instatnce);
}
