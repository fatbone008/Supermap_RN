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
class DatasetGridNative {
	private DatasetGridNative() {
	}

	public native static boolean jni_IsMultiBand(long instance);

	public native static int jni_GetWidth(long instance);

	public native static int jni_GetHeight(long instance);

	public native static void jni_SetBounds(long instance, double left,
			double bottom, double right, double top);

	public native static int jni_GetPixelFormat(long instance);

	public native static int jni_GetBlockSize(long instance);

	public native static int jni_GetRowBlockCount(long instance);

	public native static int jni_GetColumnBlockCount(long instance);

	public native static double jni_GetNoValue(long instance);

	public native static void jni_SetNoValue(long instance, double value);

	public native static double jni_GetMinValue(long instance);

	public native static double jni_GetMaxValue(long instance);

	public native static long jni_GetClipRegion(long instance);

	public native static void jni_SetClipRegion(long instance, long regionHandle);

	public native static boolean jni_GetHasPyramid(long instance);

	// 初始化裁剪区域
	public native static long jni_InitClipRegion(long instance);

	public native static boolean jni_CalculateExtremum(long instance);

	public native static double jni_GetValue(long instance, int x, int y);

	public native static double jni_SetValue(long instance, int x, int y,
			double value);

	public native static void jni_GridToXY(long instance, int x, int y,
			double[] buffer);

	public native static void jni_XYToGrid(long instance, double x, double y,
			int[] buffer);

	public native static boolean jni_BuildPyramid(long instance);

	public native static boolean jni_RemovePyramid(long instance);

	public native static int jni_addBand(long instance, long handle);

	public native static boolean jni_deleteBand(long instance, int index);

	public native static String jni_getItem(long instance, int index);

	public native static void jni_setItem(long instance, int index, String value);

	public native static int jni_getBandCount(long instance);

	public native static boolean jni_isContain(long instance, String value);

	public native static int jni_getCurrentBandIndex(long instance);

	public native static int jni_indexOf(long instance, String name);

	public native static void jni_setCurrentBandIndex(long instance,
			int indexBand);

	public native static int jni_addBandIndexs(long instance, long handle,
			int[] indexs);

	public native static boolean jni_deleteBands(long instance, int start,
			int count);
	
	public native static int jni_GetColorSpaceType(long instance);
	
	public native static void jni_SetColorSpaceType(long instance,int value);
	
	public native static int jni_addBand1(long instance,String fileName);
	
	public native static long jni_statictic(long instance);
	
	public native static long jni_GetRasterStatisticsResult(long instance);
	
	public native static long jni_BuildValueTable(long instance,long datasourceHandle, String strName);
	
	public native static long jni_BuildStatistics(long instance);
	
//	public native static long jni_NewSelfEventHandle(DatasetGrid datasetGrid);
	
//	public native static void jni_DeleteSelfEventHandle(long handle);
	
	public native static long jni_GetColorTable(long instance);
	
	public native static void jni_SetColorTable(long instance, long handle);
	
	// add by zhangshuo
	public native static long jni_GetDatasetNetCDFVarInfo(long instance);
	public native static void jni_SetDatasetNetCDFVarInfo(long instance, long handle);
	
	public native static boolean jni_Update(long instance, long handle);
	
	public native static boolean jni_UpdatePyramid(long instance,double left,double bottom,double right,double top);
	
}
