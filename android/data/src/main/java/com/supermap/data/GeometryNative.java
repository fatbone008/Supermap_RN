package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author SuperMap Objects Java Team
 * @version 2.0
 */
class GeometryNative {
	private GeometryNative() {

	}

	/**
	 *
	 * @param instance long
	 * @param params double[]
	 * 返回结果参数对应如下
	 *  params[0] =left
	 params[1] =right
	 params[2] =bottom
	 params[3] =top
	 */
	public native static void jni_GetBounds(long instance, double[] params);

	public native static void jni_GetInnerPoint(long instance, double[] params);
	
	public native static void jni_GetHandles(long instance, int index, double[] params);
	
	public native static int jni_GetHandleCount(long instance);
	
	public native static int jni_GetID(long instance);

	public native static void jni_SetStyle(long instance, long styleHandle);

	public native static long jni_GetStyle(long insance);

	public native static int jni_GetType(long instance);

	public native static boolean jni_HitTest(long instance, double x, double y,
			double tolerance);

	public native static void jni_Offset(long instance, double dx, double dy);

	public native static boolean jni_Resize(long instance, double left,
			double top, double right, double bottom);

	public native static void jni_Rotate(long instance, double x, double y,
			double angle);

	public native static void jni_SetBoundsDirty(long instance, boolean value);
	
	public native static boolean jni_FromXML(long instance, String xml);
	
	public native static String jni_ToXML(long instance);
}
