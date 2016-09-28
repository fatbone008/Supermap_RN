package com.supermap.data;

class GeoRectangleNative {
	public native static long jni_New();

	public native static long jni_New1(double centerX, double centerY,
			double width, double height, double angle);

	public native static long jni_New2(double left, double right, double top,
			double buttom ,double rotation);

	public native static void jni_GetCenter(long instatnce, double[] center);

	public native static void jni_SetCenter(long instatnce, double centerX,
			double centerY);

	public native static double jni_GetWidth(long instatnce);

	public native static void jni_SetWidth(long instatnce, double value);

	public native static double jni_GetHeight(long instatnce);

	public native static void jni_SetHeight(long instatnce, double value);

	public native static double jni_GetAngle(long instatnce);

	public native static void jni_SetAngle(long instatnce, double value);

	public native static long jni_Clone(long instatnce);

	public native static long jni_ConvertToRegion(long instatnce);

	public native static long jni_ConvertToLine(long instatnce);

	public native static void jni_Delete(long instatnce);

	public native static void jni_Clear(long instatnce);
	
	public native static double jni_getPerimeter(long instatnce);
	
	public native static double jni_getArea(long instatnce);
	
	
}
