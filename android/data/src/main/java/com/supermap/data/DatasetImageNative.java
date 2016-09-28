package com.supermap.data;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: SuperMap GIS Technologies Inc.
 * </p>
 * 
 * @author 李云锦
 * @version 2.0
 */
class DatasetImageNative {
	private DatasetImageNative() {
	}

	public native static int jni_GetWidth(long instance);

	public native static int jni_GetHeight(long instance);

	public native static int jni_GetPixelFormat(long instance);

	public native static int jni_GetPixel(long instance, int x, int y);

	public native static boolean jni_GetHasPyramid(long instance);

	public native static void jni_ImageToXY(long instance, int x, int y,
			double[] buffer);

	public native static void jni_XYToImage(long instance, double x, double y,
			int[] buffer);

	public native static int jni_GetBandCount(long instance);

	public native static boolean jni_IsMultiBand(long instance);

	public native static int jni_GetPixelByIndexes(long instance, int x, int y,
			int[] indexes);

	public native static int jni_GetColorSpaceType(long instance);

//	public native static void jni_DeleteSelfEventHandle(long handle);
	
	public native static boolean jni_BuildPyramid(long instance);
	
	public native static boolean jni_RemovePyramid(long instance);
	
	public native static boolean jni_UpdatePyramid(long instance,double left,double bottom,double right,double top);
	
//	public native static long jni_NewSelfEventHandle(DatasetImage datasetImage);
	
	
	// 预缓存功能相关接口
	 public native static void jni_StartDownload(long instance, double left,double bottom,double right,double top);
	 
	 public native static void jni_StartDownloadWithScale(long instance, double maxScale, double minScale, double left,double bottom,double right,double top);
	 
	 public native static void jni_StopDownload(long instance);
	 
	 public native static void jni_RegisterAllEvent(long instance, Object datasetImageHandle);
	 
	 public native static void jni_RenameCache(long instance);
	 
	 public native static void jni_RemoveCache(long instance);
	 
	 public native static void jni_RemoveCacheWithScaleBounds(long instance, double scale, double left,double bottom,double right,double top);

}
