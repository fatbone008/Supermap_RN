package com.supermap.data;

class GeoCircleNative {
	   private GeoCircleNative(){
		   
	   }

		public native static long jni_New();
		public native static long jni_New2(double a,double b,double c,double d);
		public native static long jni_New3(double a,double b,double c,double d,double e,double f);
		public native static void jni_Delete(long instance);
		public native static void jni_GetCenter(long instance,double[] values);
		public native static void jni_SetCenter(long instance, double x, double y);
		
		public native static double jni_GetRadius(long instance);
		public native static void jni_SetRadius(long instance,double value);
		public native static double jni_GetPerimeter(long instance);
		public native static double jni_GetArea(long instance);
		public native static long jni_ConvertToLine(long isntance,int count);
		public native static long jni_ConvertToRegion(long isntance,int count);
		
		public static native long jni_Clone(long instance);
}
