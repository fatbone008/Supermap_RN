package com.supermap.data;

class GeoArcNative {
	private GeoArcNative() {
		
	}
	
	public native static long jni_New();
	
	public native static long jni_Clone(long instance);
	
	public native static long jni_New2(double x, double y, double radius,
			double startAngle, double endAngle);
	
	public native static long jni_New3(double xStart, double yStart, 
			double xMiddle,double yMiddle, double xEnd, double yEnd);
	
	public native static void jni_GetCenter(long instance, double[] pt2D);
	
	public native static boolean jni_SetArc(long instance, double x, double y,
			double radius, double startAngle, double endAngle);
	
	public native static double jni_GetRadius(long instance);
	
//	public native static boolean jni_SetRadius(long instance, double x, double y,
//			double radius, double startAngle, double endAngle);
	
	public native static double jni_GetStartAngle(long instance);
	
//	public native static boolean jni_SetStartAngle(long instance, double x, double y,
//			double radius, double startAngle, double endAngle);
	
	public native static double jni_GetEndAngle(long instance);
	
//	public native static boolean jni_SetEndAngle(long instance, double x, double y,
//			double radius, double startAngle, double endAngle);
	
	public native static double jni_GetLength(long instance);
	
	public native static long jni_ConvertToLine(long instance, int segmentCount);
	
	public native static void jni_Delete(long instance);
	
	public native static double jni_GetSweepAngle(long instance);
}
