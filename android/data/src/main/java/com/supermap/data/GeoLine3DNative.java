/**
 * 
 */
package com.supermap.data;

/**
 * @author konglingliang
 * 
 */
class GeoLine3DNative {
	private GeoLine3DNative() {

	}

	public native static long jni_New();

	public native static void jni_Delete(long instance);

	public static native int jni_GetPartCount(long instance);

	public static native double jni_GetLength(long instance);

	public static native int jni_AddPart(long instance, double[] xs,
			double[] ys, double[] zs);

	public static native boolean jni_RemovePart(long instance, int index);

	public static native int jni_GetPartPointCount(long instance, int index);

	public static native void jni_GetPart(long instance, int index,
			double[] xs, double[] ys, double[] zs);

	public static native boolean jni_InsertPart(long instance, int index,
			double[] xs, double[] ys, double[] zs);

	public static native boolean jni_SetPart(long instance, int index,
			double[] xs, double[] ys, double[] zs);

	public static native long jni_Clone(long instance);

}
