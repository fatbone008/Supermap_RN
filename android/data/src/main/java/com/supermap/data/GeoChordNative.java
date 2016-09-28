package com.supermap.data;

class GeoChordNative {
	private GeoChordNative() {
		
	}
	
	public native static long jni_New();
	
	public native static long jni_Clone(long instance);
	
	public native static long jni_New2(double x, double y, double semimajorAxis,
			double semiminorAxis,double startEngle,double endEngle,double angle);
	
	public native static void jni_GetCenter(long instance, double[] pt);
	
	public native static void jni_SetCenter(long instance, double x, double y);
	
	public native static double jni_GetSemimajorAxis(long instance);
	
	public native static void jni_SetSemimajorAxis(long instance, double value);
	
	public native static double jni_GetSemiminorAxis(long instance);
	
	public native static void jni_SetSemiminorAxis(long instance, double value);
	
	public native static double jni_GetStartAngle(long instance);
	
	public native static void jni_SetStartAngle(long instance, double value);
	
	public native static double jni_GetEndAngle(long instance);
	
	public native static void jni_SetEndAngle(long instance, double value);
	
	public native static double jni_GetRotation(long instance);
	
	public native static void jni_SetRotation(long instance, double value);
	
	public native static double jni_GetPerimeter(long instance);
	
	public native static double jni_GetArea(long instance);
	
	public native static long jni_ConvertToLine(long instance, int segmentCount);
	
	public native static long jni_ConvertToRegion(long instance, int segmentCount);
	
	public native static void jni_Delete(long instance);
	
	
	
	
	public native static double jni_GetSweepAngle(long instance);
	public native static void jni_SetSweepAngle(long instance, double value);
}
