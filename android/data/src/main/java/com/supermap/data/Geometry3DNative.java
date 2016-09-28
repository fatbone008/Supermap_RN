package com.supermap.data;

class Geometry3DNative {
	private Geometry3DNative() {

	}

	public native static void jni_GetPosition(long instance, double[] params);

	public native static void jni_SetPosition(long instance, double x,
			double y, double z);

	public native static void jni_SetRotationX(long instance, double x);

	public native static void jni_SetRotationY(long instance, double y);

	public native static void jni_SetRotationZ(long instance, double z);

	public native static void jni_SetScaleX(long instance, double x);

	public native static void jni_SetScaleY(long instance, double y);

	public native static void jni_SetScaleZ(long instance, double z);

	public native static void jni_GetScale(long instance, double[] handles);

	public native static void jni_GetRotation(long instance, double[] handles);

	public native static long jni_GetStyle(long instance);

	public native static void jni_SetStyle(long instance, long value);

	public native static long jni_GetBoundingBox(long instance);

	public native static void jni_GetInnerPoint3D(long isntance,
			double[] handles);

	public native static double jni_GetVolume(long instance);

	public native static void jni_Offset(long instance, double dx, double dy,
			double dz);

	public native static long jni_GetGeoModel(long instance, double slices,
			double stacks);

}
