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
 * @author ¿Ó‘∆Ωı
 * @version 2.0
 */
class RecordsetNative {
	private RecordsetNative() {
	}

	public native static void jni_GetBounds(long instance, double[] buffer);

	public native static long jni_GetDataset(long instance);

	public native static boolean jni_GetIsBOF(long instance);

	public native static boolean jni_GetIsEOF(long instance);

	public native static boolean jni_GetIsEmpty(long instance);

	public native static boolean jni_GetIsReadOnly(long instance);

	public native static long jni_GetFieldInfos(long instance);

	public native static int jni_GetRecordCount(long instance);

	public native static boolean jni_AddNew(long instance, long geometry);

	public native static boolean jni_AddNew1(long instance, long geometry);

	public native static boolean jni_Edit(long instance);

	public native static boolean jni_Update(long instance);

	public native static boolean jni_CancelUpdate(long instance);

	public native static void jni_Close(long instance);

	public native static boolean jni_Delete(long instance);

	public native static boolean jni_DeleteAll(long instance);

	public native static long jni_GetGeometry(long instance);

	public native static int jni_GetID(long instance);

	public native static boolean jni_Move(long instance, int count);

	public native static boolean jni_MoveFirst(long instance);

	public native static boolean jni_MoveLast(long instance);

	public native static boolean jni_MoveNext(long instance);

	public native static boolean jni_MovePrev(long instance);

	public native static boolean jni_MoveTo(long instance, int position);

	public native static boolean jni_Refresh(long instance);

	public native static boolean jni_SeekID(long instance, int id);

	public native static boolean jni_SetGeometry(long instance,
			long geometryHandle);
	
	public native static boolean jni_SetGeometry1(long instance, long geometry,
			boolean modifyGeometry, String[] keys, long[] handles);

	public native static long jni_GetQueryParameter(long instance);

	public native static void jni_SetQueryParameter(long instance,
			long parammeterHandle);

	public native static int jni_GetFieldCount(long instance);

	public native static int jni_GetFieldIndex(long instance, String name);

	public native static long jni_GetFieldValueByIndex(long instance, int index);

	public native static long jni_GetFieldValueByName(long instance, String name);

	public native static double jni_StatisticByName(long instance,
			String fieldName, int mode);

	public native static double jni_StatisticByIndex(long instance, int index,
			int mode);

	public native static boolean jni_SetFieldValueNullByIndex(long instance,
			int index);

	public native static boolean jni_SetFieldValueNullByName(long instance,
			String name);

	public native static boolean jni_SetFieldValueByIndex(long instance,
			int index, long varHandle);

	public native static boolean jni_SetFieldValueByName(long instance,
			String name, long varHandle);

	public native static boolean jni_IsCursorTypeStatic(long instance);

	public native static boolean jni_begin(long instance,
			boolean bIsEditBulkOperate);

	public native static boolean jni_setMaxRecordCount(long instance, int value);

	public native static boolean jni_Update1(long instance);

	public native static boolean jni_CancelUpdate1(long instance);

	public native static boolean jni_GetRepresentationElement(long instance,
			String name, long[] values);

	public native static boolean jni_SetRepresentationElement(long isntnece,
			String name, long geometryHandle, int type, long curtomValuehandle);

	public native static boolean jni_SetRepresentationElementStyle(
			long isntnece, String name, long styleHandle, long curtomValuehandle);

	public native static boolean jni_SetRepresentationElementTextStyle(
			long isntnece, String name, long textstyleHandle,
			long curtomValuehandle);

	public native static boolean jni_AddNew2(long instance, long geometry,
			String[] keys, long[] handles);

	public native static boolean jni_AddNew3(long instance, long geometry,
			String[] keys, long[] handles);

	public native static int jni_GetFieldValueIntByIndex(long instance,
			int index, boolean[] flag);

	public native static short jni_GetFieldValueShortByIndex(long handle,
			int index, boolean[] flag);
	
	public native static long jni_GetFieldValueLongByIndex(long handle,
			int index, boolean[] flag);

	public native static boolean jni_GetFieldValueBooleanByIndex(long handle,
			int index, boolean[] flag);

	public native static String jni_GetFieldValueCharByIndex(long handle,
			int index, boolean[] flag);

	public native static String jni_GetFieldValueDateByIndex(long handle,
			int index, boolean[] flag);

	public native static double jni_GetFieldValueDoubleByIndex(long handle,
			int index, boolean[] flag);

	public native static float jni_GetFieldValueFloatByIndex(long handle,
			int index, boolean[] flag);

	public native static byte jni_GetFieldValueByteByIndex(long handle,
			int index, boolean[] flag);

	public native static byte[] jni_GetFieldValueLongBinaryByIndex(long handle,
			int index, boolean[] flag);

	public native static String jni_GetFieldValueTextByIndex(long handle,
			int index, boolean[] flag);

	public native static boolean jni_setFieldValueIntByName(long handle,
			String name, int value);
	
	public native static boolean jni_setFieldValueLongByName(long handle,
			String name, long value);

	public native static boolean jni_setFieldValueDoubleByName(long handle,
			String name, double value);

	public native static boolean jni_setFieldValueFloatByname(long handle,
			String name, float value);

	public native static boolean jni_setFieldValueBooleanByName(long handle,
			String name, boolean value);

	public native static boolean jni_setFieldValueCharByName(long handle,
			String name, String value);

	public native static boolean jni_setFieldValueTextByName(long handle,
			String name, String value);

	public native static boolean jni_setFieldValueLongBinaryByName(long handle,
			String name, byte[] value);

	public native static boolean jni_setFieldValueShortByName(long handle,
			String name, short value);

	public native static boolean jni_setFieldValueDateTimeByName(long handle,
			String name, int year, int month, int day, int hour, int minute,
			int second);

	public native static boolean jni_setFieldValueByteByName(long handle,
			String name, byte value);
	
	public native static boolean jni_setFieldValueShortByName2(long handle, String name, short value);
	
	public native static long[] jni_ValidateRelation(long handle);
	
	public native static long[] jni_ValidateAllRelation(long handle);
	
	public native static long jni_GetFeature(long handle);
	
	public native static boolean jni_UpdateFields1(long instance,
			long srcHandle, int nSpatialRelationType,
			int nAttributeStatisticType, String[] srcFields,
			String[] tagFields, boolean borderIsInside, String errorName,
			boolean isShowProgress, long handle);
	
	public native static boolean jni_UpdateFields2(long instance,
			long srcHandle, int nSpatialRelationType,
			int nAttributeStatisticType, String[] srcFields,
			String[] tagFields, boolean borderIsInside, String errorName,
			boolean isShowProgress, long handle);
	
	public native static int[] jni_GetIDsByGeoRelation1(long instance,
			long srcHandle, int nSpatialRelationType, boolean borderIsInside, 
			boolean isShowProgress, long handle);
	
	public native static int[] jni_GetIDsByGeoRelation2(long instance,
			long srcHandle, int nSpatialRelationType, boolean borderIsInside, 
			boolean isShowProgress, long handle);
	
	public native static void jni_DeleteSelfEventHandle(long handle);
	
	public native static long jni_NewSelfEventHandle(Recordset recordset);
	
	public native static void jni_SetEditBulk(long instance, boolean enableEditBulk);
}
