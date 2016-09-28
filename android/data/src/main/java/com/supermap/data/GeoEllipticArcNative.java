package com.supermap.data;

class GeoEllipticArcNative {
	public native static long jni_New();

	public native static long jni_Clone(long instatnce);

	public native static void jni_Delete(long instatnce);

	public native static long jni_New1(double centerX, double centerY,
			double semimajorAxis, double semiminorAxis, double startEngle,
			double sweepAngle, double angle);

	public native static void jni_getCenter(long handle, double[] pt);

	public native static void jni_setCenter(long handle, double centerX,
			double centerY);

	public native static double jni_getSemimajorAxis(long handle);

	public native static void jni_setSemimajorAxis(long handle, double value);

	public native static double jni_getSemiminorAxis(long handle);

	public native static void jni_setSemiminorAxis(long handle, double value);

	public native static void jni_setStartAngle(long handle, double start,double end);

//	public native static void jni_setSweepAngle(long handle, double value);

	public native static double jni_getAngle(long handle);

	public native static void jni_setAngle(long handle, double value);

	public native static double jni_getLength(long handle);

	public native static long jni_convertToLine(long handle, int segmentCount);

	public native static void jni_findPointOnArc(long handle, double value,
			double[] pt);
	
	public native static double jni_getStartAngle(long handle);
	
	public native static double jni_getSweepAngle(long handle);
}
