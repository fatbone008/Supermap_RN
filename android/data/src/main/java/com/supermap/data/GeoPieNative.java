package com.supermap.data;
class GeoPieNative {
   private GeoPieNative(){
	   
   }

	public native static long jni_New();

	public native static void jni_Delete(long instance);
	public native static void jni_GetCenter(long instance,double[] values);
	public native static void jni_SetCenter(long instance, double x, double y);
	public native static double jni_GetSemimajorAxis(long instance);
	public native static void jni_SetSemimajorAxis(long instance,double value);
	public native static double jni_GetSemiminorAxis(long instance);
	public native static void jni_SetSemiminorAxis(long instance,double value);
	public native static double jni_GetStartAngle(long instance);
	public native static void jni_SetStartAngle(long instance,double value);
	public native static double jni_GetSweepAngle(long instance);
	public native static void jni_SetSweepAngle(long instance,double value);
	public native static double jni_GetAngle(long instance);
	public native static void jni_SetAngle(long instance,double value);
	public native static double jni_GetPerimeter(long instance);
	public native static double jni_GetArea(long instance);
	public native static long jni_ConvertToLine(long isntance,int count);
	public native static long jni_ConvertToRegion(long isntance,int count);
	
	public static native long jni_Clone(long instance);
	public static native void jni_Reset(long instance,double x, double y, double semimajorAxis,double semiminorAxis,
			double startAngle , double endAngle,double angle);
}
