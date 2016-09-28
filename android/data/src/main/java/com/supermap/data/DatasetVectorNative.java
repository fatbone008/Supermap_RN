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
class DatasetVectorNative {
	private DatasetVectorNative() {
	}

	public native static boolean jni_Append(long instance,long recordset,String tileName);
	
	public native static long jni_GetFieldInfos(long instance);

	public native static double jni_Statistic(long instance, String name,
			int mode);

	public native static long jni_GetAllRecordsByRecordset(long instance,
			int cursorType);
	
	public native static long jni_GetEmptyRecordset(long instance,
			int cursorType);
			
//	public native static long jni_Query5(long instance, long defHandle,
//			int ugcQueryMode, long ugcSpatialObjectHandle,
//			int ugcSpatialObjectType, String strPattern);
//	
//	// add by xuzw 2009-08-05 空间查询模式为相交模式，采用新实现
//	public native static long jni_Query6(long instance, double left, double bottom,
//			double right, double top, long defHandle);
	
	public native static long jni_Query(long instance, long queryInstance);
	
	public native static long jni_QueryById(long instance, int[] id, int type);
	
//	public native static long jni_Query1(long instance, 
//			long defHandle,int queryMode, long objHandle, int objType, String pattern);
//	
//	// 空间查询模式为相交模式，采用新实现
//	public native static long jni_Query2(long instance, double left,
//			double bottom, double right, double top, long defHandle);
	
	
	
	public native static long jni_Query1(long instance, String attributeFilter,
			int cursorType);

	public native static long jni_Query2(long instance, long geometryHandle,
			double bufferDistance, String attributeFilter, int cursorType);

	public native static long jni_Query3(long instance, double left,
			double bottom, double right, double top, String attributeFilter,
			int cursorType);

	public native static long jni_Query4(long instance, int[] id, int cursorType);

	public native static long jni_Query5(long instance, long defHandle,
			int queryMode, long objHandle, int objType, String strPattern);

	// add by xuzw 2009-08-05 空间查询模式为相交模式，采用新实现
	public native static long jni_Query6(long instance, double left,
			double bottom, double right, double top, long defHandle);
	
	// 添加通过ids和QueryParameter来查询的方法(2014/5/10 add by hp)
	public native static long jni_Query7(long instance, int[] id, 
			long defHandle, int type);
	
	public native static long jni_GetParentDataset(long instance);
	
	public native static long jni_GetChildDataset(long instance);
	
	public native static boolean jni_IsAvailableFieldName(long instance,String name);
	

	public native static boolean jni_BuildSpatialIndex1(long instance, int type);

	public native static boolean jni_BuildSpatialIndex2(long instance,
			long spatialIndexInfoHandle);

	public native static boolean jni_DropSpatialIndex(long instance);

	public native static boolean jni_IsSpatialIndexTypeSupported(long instance,
			int type);
	
	public native static boolean jni_ReBuildSpatialIndex(long instance);

	public native static int jni_GetSpatialIndexType(long instance);

	public native static boolean jni_GetIsSpatialIndexDirty(long instance);

	public native static void jni_ComputeBounds(long instance, double[] buffer);
}
