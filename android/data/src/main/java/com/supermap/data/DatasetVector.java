package com.supermap.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/* </p>
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
 * @author ���ƽ�
 * @version 2.0
 */
public class DatasetVector extends Dataset {
	
	private FieldInfos m_fieldInfos = null;
	private DatasetVector m_childDataset = null;
	private Dataset m_parentDataset = null;
	private static Integer m_lock = new Integer(0);
	private QueryListener m_QueryListener = null;
	protected DatasetVector() {
	}

	DatasetVector(long handle, Datasource datasource) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		if (datasource.getHandle() == 0) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		setHandle(handle);
		this.m_datasource = datasource;
	}
	
	
	/**
	 * ����׷��
	 * @param recordset Ҫ׷�ӵļ�¼��
	 * @return �ɹ�Ϊtrue����Ϊfalse
	 */
	public boolean append(Recordset recordset) {
//		verifyLicense();

		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.isReadOnly()) {
			String message = InternalResource
					.loadString(
							"",
							InternalResource.DatasetVectorTheDatasourceOrDatasetIsReadOnly,
							InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (recordset == null) {
			String message = InternalResource.loadString("recordset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		if (recordset.getHandle() == 0) {
			String message = InternalResource.loadString("recordset",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return append(recordset, "");
	}
	
	/**
	 * ���ݼ�׷��
	 * @param recordset Ҫ׷�ӵļ�¼��
	 * @param tileName TileName
	 * @return
	 */
	public boolean append(Recordset recordset, String tileName) {
//		verifyLicense();

		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.isReadOnly()) {
			String message = InternalResource
					.loadString(
							"",
							InternalResource.DatasetVectorTheDatasourceOrDatasetIsReadOnly,
							InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (recordset == null) {
			String message = InternalResource.loadString("recordset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		if (recordset.getHandle() == 0) {
			String message = InternalResource.loadString("recordset",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return DatasetVectorNative.jni_Append(getHandle(), recordset
				.getHandle(), tileName);
	}
	
	public boolean deleteRecords(int[] id) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.isReadOnly()) {
			String message = InternalResource
					.loadString(
							"",
							InternalResource.DatasetVectorTheDatasourceOrDatasetIsReadOnly,
							InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (id == null || id.length == 0) {
			return true;
		}

		boolean result = true;
		if (!this.open()) {
			result = this.open();
		}
		if (result) {
			Recordset recordset = this.query(id, CursorType.DYNAMIC);
			result = recordset.deleteAll();
			recordset.close();
		}
		return result;
	}
	
	/**
	 * ͨ��ID�����ѯ��¼��
	 * @param id ID����
	 * @param cursorType ��ѯģʽ
	 * @return
	 */
	public Recordset query(int[] id, CursorType cursorType) {

		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (id == null) {
			String message = InternalResource.loadString("id",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		// 当参数cursorType为null时，则默认处理为：CursorType.STATIC
		if (cursorType == null) {
			cursorType = CursorType.STATIC;
		}

		int ugcValue = cursorType.getUGCValue();
		long handle = DatasetVectorNative.jni_QueryById(getHandle(), id, ugcValue);
		if (handle == 0) {
			return this.getRecordset(true, cursorType);
		}
		Recordset recordset = new Recordset(handle, this);
		return recordset;
	}
	
	public boolean isAvailableFieldName(String fieldName) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean result = false;
		if (fieldName.startsWith("Sm") || fieldName.startsWith("SM")
				|| fieldName.startsWith("sm")) {
			return result;
		}
		if (fieldName != null && fieldName.trim().length() != 0) {
			result = DatasetVectorNative.jni_IsAvailableFieldName(getHandle(),
					fieldName);
		}
		return result;
	}

	// �ֶ���Ŀ
	public int getFieldCount() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return this.getFieldInfos().getCount();
	}

	// ����ʸ�����ݼ��������ݼ���
	public DatasetVector getChildDataset() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (this.m_childDataset == null) {
			long handle = DatasetVectorNative.jni_GetChildDataset(getHandle());
			if (handle != 0) {
				this.m_childDataset = new DatasetVector(handle,
						this.m_datasource);
			}
		}
		return this.m_childDataset;
	}
	
	// �ֶ���Ϣ����
	public FieldInfos getFieldInfos() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		synchronized (m_lock) {
			if (this.m_fieldInfos == null) {
				long handle = DatasetVectorNative.jni_GetFieldInfos(getHandle());
				if (handle == 0) {
					return null;
				}
				this.m_fieldInfos = new FieldInfos(handle, this);
				this.m_fieldInfos.setIsDisposable(false);
			}
			else if(!this.isOpen()){
				//http://192.168.115.2:8090/browse/UGOVI-124 ���ݼ�Close�������»�ȡһ�飬�ײ����ݼ�fieldinfos��ַ�����
				DatasetVectorNative.jni_GetFieldInfos(getHandle());
			}
			return this.m_fieldInfos;
		}
	}

	public Recordset query(String attributeFilter, CursorType cursorType) {

		// luhao 2010-6-1 
		// verifyLicense();

		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (attributeFilter == null) {
			attributeFilter = "";
		}
		
		if (cursorType == null) {
			cursorType = CursorType.STATIC;
		}

		int ugcValue = cursorType.getUGCValue();
		long handle = DatasetVectorNative.jni_Query1(getHandle(),
				attributeFilter, ugcValue);
		if (handle == 0) {
			return this.getRecordset(true, cursorType);
		}
		Recordset recordset = new Recordset(handle, this);
		return recordset;
	}

	public Recordset query(Geometry geometry, double bufferDistance,
			CursorType cursorType) {
		// luhao 2010-6-1 
		// verifyLicense();

		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.getType().equals(DatasetType.TABULAR)) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetVector_TabularUnsupport,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometry == null) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		if (geometry.getType().equals(GeometryType.GEOTEXT)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.DatasetVectorGeoTextIsUnsupported,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		 
		if (cursorType == null) {
			cursorType = CursorType.STATIC;
		}
		if (geometry.isEmpty()) {

			return this.getRecordset(true, cursorType);
		}
		if (bufferDistance < 0) {
			String message = InternalResource.loadString("bufferDistance",
					InternalResource.DatasetVectorQueryBufferInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		Recordset recordset = query(geometry, bufferDistance, "", cursorType);
		return recordset;
	}

	public Recordset query(Geometry geometry, double bufferDistance,
			String attributeFilter, CursorType cursorType) {
		// luhao 2010-6-1 
		// verifyLicense();

		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.getType().equals(DatasetType.TABULAR)) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetVector_TabularUnsupport,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometry == null) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 
		if (geometry.getType().equals(GeometryType.GEOTEXT)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.DatasetVectorGeoTextIsUnsupported,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (geometry.isEmpty()) {
			// String message = InternalResource.loadString("geometry",
			// InternalResource.GeometryShouldNotBeEmpty,
			// InternalResource.BundleName);
			// throw new IllegalArgumentException(message);
			return this.getRecordset(true, cursorType);
		}

		if (bufferDistance < 0) {
			String message = InternalResource.loadString("bufferDistance",
					InternalResource.DatasetVectorQueryBufferInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (attributeFilter == null) {
			attributeFilter = "";
		}
		
		if (cursorType == null) {
			cursorType = CursorType.STATIC;
		}


		int ugcValue = cursorType.getUGCValue();
		long handle = DatasetVectorNative
				.jni_Query2(getHandle(), geometry.getHandle(), bufferDistance,
						attributeFilter, ugcValue);
		if (handle == 0) {
			return this.getRecordset(true, cursorType);
		}
		Recordset recordset = new Recordset(handle, this);
		return recordset;
	}

	
	public Recordset query(Rectangle2D bounds, CursorType cursorType) {
		// luhao 2010-6-1 
		// verifyLicense();

		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.getType().equals(DatasetType.TABULAR)) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetVector_TabularUnsupport,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (bounds == null) {
			String message = InternalResource.loadString("bounds",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		
		if (cursorType == null) {
			cursorType = CursorType.STATIC;
		}

		Recordset recordset = query(bounds, "", cursorType);
		return recordset;
	}

	
	public Recordset query(Rectangle2D bounds, String attributeFilter,
			CursorType cursorType) {
		// luhao 2010-6-1 
		// verifyLicense();

		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.getType().equals(DatasetType.TABULAR)) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetVector_TabularUnsupport,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (bounds == null) {
			String message = InternalResource.loadString("bounds",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		if (attributeFilter == null) {
			attributeFilter = "";
		}
		
		if (cursorType == null) {
			cursorType = CursorType.STATIC;
		}

		int ugcValue = cursorType.getUGCValue();
		long handle = DatasetVectorNative.jni_Query3(getHandle(),
				bounds.getLeft(), bounds.getBottom(), bounds.getRight(),
				bounds.getTop(), attributeFilter, ugcValue);
		if (handle == 0) {
			return this.getRecordset(true, cursorType);
		}

		Recordset recordset = new Recordset(handle, this);
		return recordset;
	}
	
	// ͨ�����ò�ѯ������ʸ�����ݼ����в�ѯ��
//	public Recordset query(QueryParameter queryParameter) {
//		// luhao 2010-6-1  �ⲿƵ������ʱ�ǳ���ʱ��ע��֮
//		// verifyLicense();
//
//		if (getHandle() == 0) {
//			String message = InternalResource.loadString("",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//
//		if (queryParameter == null) {
//			String message = InternalResource.loadString("queryParameter",
//					InternalResource.GlobalArgumentNull,
//					InternalResource.BundleName);
//			throw new NullPointerException(message);
//		}
//		
//		if (this.getType().equals(DatasetType.TABULAR)
//				&& !queryParameter.getSpatialQueryMode().equals(
//						SpatialQueryMode.NONE)) {
//			String message = InternalResource.loadString("",
//					InternalResource.DatasetVector_TabularUnsupport,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//
//		// UGCQueryDef��ָ��
//		long defHandle = 0;
//		// ��ѯģʽ�����Բ�ѯ���Ǹ��ֿռ��ѯ.
//		int ugcQueryMode = -1;
//		// ��Ϊ�п��ܻ��޸����е�Parameter�����Կ�¡�ݣ�ʹ�����dispose��
//		QueryParameter queryDef = null;
//		// �ռ��ѯ��������,���û�����ø����Ծ�Ϊ-1
//		int ugcSpatialObjectType = -1;
//		// �ռ��ѯ�����ָ��
//		long ugcSpatialObjectHandle = 0;
//		// ��ѯ�󷵻ص�ָ��
//		long handle = 0;
//		// ��ѯģʽ
//		String strPattern = "";
//
//		// copyһ��
//		queryDef = new QueryParameter(queryParameter);
//
//		Object oldObject = queryDef.getSpatialQueryObject();
//		if (oldObject != null) {
//			if (oldObject.getClass().equals(Rectangle2D.class)) {
//            	if (!queryDef.getSpatialQueryMode().equals(SpatialQueryMode.INTERSECT)) {
//                    Rectangle2D rect = (Rectangle2D) oldObject;
//                    if (Toolkit.isZero(rect.getWidth())
//                        || Toolkit.isZero(rect.getHeight())) {
//                        return this.getRecordset(true, queryParameter.getCursorType());
//                    }
//                    Point2Ds pts = new Point2Ds();
//                    pts.add(new Point2D(rect.getLeft(), rect.getBottom()));
//                    pts.add(new Point2D(rect.getLeft(), rect.getTop()));
//                    pts.add(new Point2D(rect.getRight(), rect.getTop()));
//                    pts.add(new Point2D(rect.getRight(), rect.getBottom()));
//                    GeoRegion region = new GeoRegion(pts);
//                    queryDef.setSpatialQueryObject(region);
//            	} else {
//            		// add by xuzw 2009-08-05
//            		// �ռ��ѯģʽΪ�ཻ��ѯ������������ΪRectangle2Dʱ��ֱ�Ӳ���Bounds��ѯ����߲�ѯ�ٶ�
//					Rectangle2D rect = (Rectangle2D)oldObject;
//					handle = DatasetVectorNative.jni_Query6(this.getHandle(), 
//							rect.getLeft(), rect.getBottom(), rect.getRight(), rect.getTop(), 
//							queryDef.getHandle());
//			        Recordset recordset = null;
//			        if (handle == 0) {
//			            recordset = this.getRecordset(true, queryParameter.getCursorType());
//			            recordset.setQueryParameter(queryParameter);
//			        } else {
//			            recordset = new Recordset(handle, this);
//			        }
//			        queryDef.dispose();
//			        queryDef = null;
//
//			        return recordset;
//            	}
//			} else if (oldObject.getClass().equals(Point2D.class)) {
//				Point2D point = (Point2D) oldObject;
//				if (point.isEmpty()) {
//					return this.getRecordset(true, queryParameter
//							.getCursorType());
//				}
//				GeoPoint pt = new GeoPoint(point.getX(), point.getY());
//				queryDef.setSpatialQueryObject(pt);
//			} else if (oldObject.getClass().getSuperclass().getClass().equals(
//					Geometry.class)) {
//				Geometry oldGeometry = (Geometry) oldObject;
//				if (oldGeometry.isEmpty()) {
//					return this.getRecordset(true, queryParameter
//							.getCursorType());
//				}
//			}
//		}
//		defHandle = queryDef.getHandle();
//		if (queryDef.getSpatialQueryObject() != null) {
//			ugcSpatialObjectHandle = ((InternalHandle) queryDef
//					.getSpatialQueryObject()).getHandle();
//		}
//
//		// ���Ϊ-1��ִֻ�����Բ�ѯ,���򣬸�����ص����ִ�в�ͬ�Ĳ�ѯ
//		ugcQueryMode = queryDef.getSpatialQueryMode().getUGCValue();
//		// strPattern = SpatialComparePattern.toUGCString(queryDef
//		// .getSpatialFilter());
//		// ��ȡ��ѯ���ζ�������
//		InternalUGCType ugcType = null;
//		if (queryDef.getSpatialQueryObject() != null) {
//			if (queryDef.getSpatialQueryObject().getClass().equals(
//					DatasetVector.class)) {
//				ugcType = InternalUGCType.UGDatasetVector;
//			} else if (queryDef.getSpatialQueryObject().getClass().equals(
//					Recordset.class)) {
//				ugcType = InternalUGCType.UGRecordset;
//			} else {
//				ugcType = InternalUGCType.UGGeometry;
//			}
//		}
//		if (ugcType != null) {
//			ugcSpatialObjectType = ugcType.getUGCValue();
//		}
//		handle = DatasetVectorNative.jni_Query5(getHandle(), defHandle,
//				ugcQueryMode, ugcSpatialObjectHandle, ugcSpatialObjectType,
//				strPattern);
//		// ������߼�����ȷ
//		Recordset recordset = null;
//		if (handle == 0) {
//			recordset = this.getRecordset(true, queryParameter.getCursorType());
//			// modified by not attributable at 2008��5��20�� ����05ʱ00��46��
//			// reason:ֻ�е���ѯʧ�ܣ�������setQueryParameter
//			recordset.setQueryParameter(queryParameter);
//		} else {
//			recordset = new Recordset(handle, this);
//		}
//		queryDef.dispose();
//		queryDef = null;
//
//		return recordset;
//
//	}
	
	public Recordset query(QueryParameter queryParameter){
		// kangweibo 2012-5-3 15:18:11 
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (queryParameter == null) {
			String message = InternalResource.loadString("queryParameter",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (this.getType().equals(DatasetType.TABULAR)
				&& !queryParameter.getSpatialQueryMode().equals(
						SpatialQueryMode.NONE)) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetVector_TabularUnsupport,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// UGCQueryDef
		long defHandle = 0;
		//
		int ugcQueryMode = -1;
		// 
		QueryParameter queryDef = null;
		// 
		int ugcSpatialObjectType = -1;
		// 
		long ugcSpatialObjectHandle = 0;
		// 
		long handle = 0;
		// 
		String strPattern = "";

		// copy
		queryDef = new QueryParameter(queryParameter);

		Object oldObject = queryDef.getSpatialQueryObject();
		if (oldObject != null) {
			if (oldObject.getClass().equals(Rectangle2D.class)) {
				if (!queryDef.getSpatialQueryMode().equals(
						SpatialQueryMode.INTERSECT)) {
					Rectangle2D rect = (Rectangle2D) oldObject;
					if (Toolkit.isZero(rect.getWidth())
							|| Toolkit.isZero(rect.getHeight())) {
						return this.getRecordset(true, queryParameter
								.getCursorType());
					}
					Point2Ds pts = new Point2Ds();
					pts.add(new Point2D(rect.getLeft(), rect.getBottom()));
					pts.add(new Point2D(rect.getLeft(), rect.getTop()));
					pts.add(new Point2D(rect.getRight(), rect.getTop()));
					pts.add(new Point2D(rect.getRight(), rect.getBottom()));
					GeoRegion region = new GeoRegion(pts);
					queryDef.setSpatialQueryObject(region);
				} else {
					// add by xuzw 2009-08-05
					// 
					Rectangle2D rect = (Rectangle2D) oldObject;
					handle = DatasetVectorNative.jni_Query6(this.getHandle(),
							rect.getLeft(), rect.getBottom(), rect.getRight(),
							rect.getTop(), queryDef.getHandle());
					Recordset recordset = null;
					if (handle == 0) {
						recordset = this.getRecordset(true, queryParameter
								.getCursorType());
						//recordset.setQueryParameter(queryParameter);
					} else {
						recordset = new Recordset(handle, this);
					}
					queryDef.dispose();
					queryDef = null;

					return recordset;
				}
			} else if (oldObject.getClass().equals(Point2D.class)) {
				Point2D point = (Point2D) oldObject;
				if (point.isEmpty()) {
					return this.getRecordset(true, queryParameter
							.getCursorType());
				}
				GeoPoint pt = new GeoPoint(point.getX(), point.getY());
				queryDef.setSpatialQueryObject(pt);
			} else if (oldObject.getClass().getSuperclass().getClass().equals(
					Geometry.class)) {
				Geometry oldGeometry = (Geometry) oldObject;
				if (oldGeometry.isEmpty()) {
					return this.getRecordset(true, queryParameter
							.getCursorType());
				}
			}
		}
		defHandle = queryDef.getHandle();
		if (queryDef.getSpatialQueryObject() != null) {
			ugcSpatialObjectHandle = ((InternalHandle) queryDef
					.getSpatialQueryObject()).getHandle();
		}

		// 
		ugcQueryMode = queryDef.getSpatialQueryMode().getUGCValue();
		// strPattern = SpatialComparePattern.toUGCString(queryDef
		// .getSpatialFilter());
		InternalUGCType ugcType = null;
		if (queryDef.getSpatialQueryObject() != null) {
			if (queryDef.getSpatialQueryObject().getClass().equals(
					DatasetVector.class)) {
				ugcType = InternalUGCType.UGDatasetVector;
			} else if (queryDef.getSpatialQueryObject().getClass().equals(
					Recordset.class)) {
				ugcType = InternalUGCType.UGRecordset;
			} else {
				ugcType = InternalUGCType.UGGeometry;
			}
		}
		if (ugcType != null) {
			ugcSpatialObjectType = ugcType.getUGCValue();
		}

		//checkSpatialQueryDim(queryDef.getSpatialQueryObject(), queryDef
		//		.getSpatialQueryMode());

		handle = DatasetVectorNative.jni_Query5(getHandle(), defHandle,
				ugcQueryMode, ugcSpatialObjectHandle, ugcSpatialObjectType,
				strPattern);
		// 
		Recordset recordset = null;
		if (handle == 0) {
			// �������汾��Ĳ�ѯ������ѯ������������recordset �еļ�¼��Ϊ0���������Բ�ѯ��
			// �Ҳ�ѯ�����������˷�������GroupBy����ʹ�����·����ٲ�һ�Σ���ʹ��SQLite���ݿ��ѯSmID��
			// ��ѯ����SmID��Ϊһ��int���飬Ȼ�����ͨ��id��ѯ��������ļ�¼�����ء�
			// ����ƶ��˷����ѯ���õײ�ķ�����һ�����еĶ�����������1w8ʱ��ѯ��������
			// ��SQLite���ݿ�ֱ�Ӳ���Բ��������(added by hp 2014/5/10)
			String[] strList = queryDef.getGroupBy();
			if(ugcQueryMode == -1 && strList.length > 0){
				// ʹ�����ݿ�ֱ�Ӳ�ѯ����ش���
				String strPathUdb = this.m_datasource.getConnectionInfo().getServer();
				String strPathUdd = strPathUdb.replace(".udb", ".udd");
	            SQLiteDatabase db = Environment.getContext().openOrCreateDatabase(strPathUdd,
	            		Environment.getContext().MODE_PRIVATE, null);
	            if (db == null) {
	            	String message = InternalResource.loadString("SQLiteDatabase",
	    					InternalResource.GlobalArgumentNull,
	    					InternalResource.BundleName);
	    			throw new NullPointerException(message);
	    		}
	            
	            // �����ѯ���,�������ѯ�����SMID�����,ǰn-1���ֶκ���ӡ�,��
	            // ���һ���ֶβ��ӡ�,��
	            String strTable = this.getName();
	            String strPar = " SELECT SMID FROM " +  strTable +" GROUP BY ";
	            for(int n = 0; n < strList.length - 1; n++){
	                strPar = strPar + strList[n] + ",";
	            }
	            
	            strPar = strPar + strList[strList.length - 1];
	        	
	            // �ò������SMID�������ѯid����
				Cursor cur = db.rawQuery(strPar, null);
				int nCount = cur.getCount();
				long handle1 = 0;
				if(nCount > 0){
					int[] id = new int[nCount];
					int nSmidIndex = cur.getColumnIndex("SmID");
					cur.moveToFirst();
					for(int n = 0; n < nCount; n++){
						id[n] = cur.getInt(nSmidIndex);
					    cur.moveToNext();
				    }
					
					// �ù���Ĳ�ѯid�ͽ���������GroupBy��Ϊ�յĲ�ѯ�����ٴβ�ѯ
					// ����������GroupBy��Ϊ�գ������ֻص�����ĵ��õײ�ķ�������1w8ʱ��ѯ����������·
					int nUgcValue = CursorType.STATIC.getUGCValue();
					queryDef.setGroupBy(null);
					defHandle = queryDef.getHandle();
					handle1 = DatasetVectorNative.jni_Query7(getHandle(),
							id, defHandle, nUgcValue);
				}
				
				// ����֮��Ҫ�����ݿ�ر�
				if(db != null && db.isOpen()){
					db.close();
				}
				
				if (handle1 == 0) {
					recordset = this.getRecordset(true, CursorType.STATIC);
				} else {
					recordset = new Recordset(handle1, this);
				}
			}else{
				recordset = this.getRecordset(true, queryParameter.getCursorType());
				// 
				// 
				//recordset.setQueryParameter(queryParameter);
			}
		} else {
			recordset = new Recordset(handle, this);
		}
		queryDef.dispose();
		queryDef = null;

		return recordset;

	}

	public Recordset getRecordset(boolean isEmptyRecordset,
			CursorType cursorType) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		long recordsetHandle = 0;
		// ������cursorTypeΪnullʱ����Ĭ�ϴ���Ϊ��CursorType.STATIC
		if (cursorType == null) {
			cursorType = CursorType.STATIC;
		}
		int ugcValue = cursorType.getUGCValue();
		if (isEmptyRecordset) {
			recordsetHandle = DatasetVectorNative.jni_GetEmptyRecordset(
					getHandle(), ugcValue);
		} else {
			recordsetHandle = DatasetVectorNative.jni_GetAllRecordsByRecordset(
					getHandle(), ugcValue);
		}
		Recordset recordset = null;
		if (recordsetHandle != 0) {
			recordset = new Recordset(recordsetHandle, this);
			recordset.setIsDisposable(true);
		}
		return recordset;
	}
	
	// ��ָ���ֶν����������ֵ����Сֵ��ƽ��ֵ���ܺ͡���׼��ͷ����ͳ�ơ�
//	public double statistic(String fieldName, StatisticMode mode) {
//		if (getHandle() == 0) {
//			String message = InternalResource.loadString("",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (fieldName == null || fieldName.trim().length() == 0) {
//			String message = InternalResource.loadString("fieldName",
//					InternalResource.GlobalStringIsNullOrEmpty,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		int index = getFieldInfos().indexOf(fieldName);
//		if (index == -1) {
//			String message = InternalResource.loadString("fieldName",
//					InternalResource.DatasetVectorFieldIsNotExsit,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		if (mode == null) {
//			String message = InternalResource.loadString("mode",
//					InternalResource.GlobalArgumentNull,
//					InternalResource.BundleName);
//			throw new NullPointerException(message);
//		}
//		int ugcValue = mode.getUGCValue();
//		return DatasetVectorNative.jni_Statistic(getHandle(), fieldName,
//				ugcValue);
//
//	}

	// ��ָ���ֶν����������ֵ����Сֵ��ƽ��ֵ���ܺ͡���׼��ͷ����ͳ�ơ�
//	public double statistic(int fieldIndex, StatisticMode mode) {
//		if (getHandle() == 0) {
//			String message = InternalResource.loadString("",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//
//		if (fieldIndex < 0 || fieldIndex >= this.getFieldCount()) {
//			String message = InternalResource.loadString("fieldIndex",
//					InternalResource.GlobalIndexOutOfBounds,
//					InternalResource.BundleName);
//			throw new IndexOutOfBoundsException(message);
//		}
//
//		if (mode == null) {
//			String message = InternalResource.loadString("mode",
//					InternalResource.GlobalArgumentNull,
//					InternalResource.BundleName);
//			throw new NullPointerException(message);
//		}
//		String name = getFieldInfos().get(fieldIndex).getName();
//		return statistic(name, mode);
//	}

		
	protected static DatasetVector createInstance(long handle,
			Datasource datasource) {
		return new DatasetVector(handle, datasource);
	}
	
	protected final void clearHandle() {
		// @modified by ���ƽ� at 2007��10��12�� ����02ʱ06��46��
		// @reason ���������ͷ��������
		// this.m_fieldInfos = null;
		if (this.m_fieldInfos != null) {
			this.m_fieldInfos.clearHandle();
			this.m_fieldInfos = null;
		}
		if (this.m_childDataset != null) {
			this.m_childDataset.clearHandle();
			this.m_childDataset = null;
		}
		if (this.m_parentDataset != null) {
			this.m_parentDataset.clearHandle();
			this.m_parentDataset = null;
		}

		setHandle(0);
	}
	
	/**
	 * ��GeoJSON�ַ����л�ȡ���ζ��󣬲�����������ݼ���
	 * <p>��֧�ֵ㡢�ߡ����CAD���ݼ�����ȡ�㡢�ߡ������
	 * @param geoJSON     GeoJSON��ʽ���ַ���
	 * @return            ��ȡ�ɹ�������true;���򣬷���false
	 */
	public boolean fromGeoJSON(String geoJSON){
		DatasetType type = this.getType();
		if(type != DatasetType.POINT && type != DatasetType.LINE && type != DatasetType.REGION && type != DatasetType.CAD){
			Log.e("DatasetVector", "��֧�ֵ����ݼ����ͣ�" + type);
			return false;
		}
		Recordset recordset = null; 
		boolean result = false;
		recordset = this.getRecordset(false, CursorType.DYNAMIC);
		result = recordset.fromGeoJSON(geoJSON);
		recordset.dispose();
		return result;
	}

	/**
	 * �����ݼ���ָ����ֹSmID�Ķ���ת����GeoJSON��ʽ���ַ���
	 * <p>��֧�ֵ㡢�ߡ����CAD���ݼ���ת���㡢�ߡ������.hasAtrributteΪtrueʱ������а�������ֵ;hasAtrributeΪfalseʱ��ֻ�м��ζ���
	 * @param hasAttributte     �Ƿ��������ֵ
	 * @param startID           ��ʼSmID
	 * @param endID             ����SmID
	 * @return                  ����GeoJSON��ʽ���ַ�����û��ת���ɹ�������null;
	 * 
	 * <p><strong>ע</strong>��startID��ʼ����,�÷��������һ��ת��10����¼,�ڵ���endIDָ���ļ�¼֮ǰ,��ת���ļ�¼�ﵽ10,���ټ���ת����
	 * ���startID��Ӧ�ļ�¼������,��ôֱ�ӷ���null��
	 */
	public String toGeoJSON(boolean hasAttributte, int startID, int endID) {
		DatasetType type = this.getType();
		if(type != DatasetType.POINT && type != DatasetType.LINE && type != DatasetType.REGION && type != DatasetType.CAD){
			Log.e("DatasetVector", "��֧�ֵ����ݼ����ͣ�" + type);
			return null;
		}
		
		if(startID < 0 || endID < 0 || startID > endID){
			Log.e("DatasetVector", "startID�� endID�������0����endID��С��startID");
			return null;
		}
		Recordset recordset = null; 
		String geoJSON = null;
		int count = endID - startID + 1;
		recordset = this.getRecordset(false, CursorType.DYNAMIC);
		boolean isSeeked = recordset.seekID(startID);
		if (isSeeked){
			geoJSON = recordset.toGeoJSON(hasAttributte, count, endID);
		} else {
			Log.e("DatasetVector", "Not found start SmID: " + startID);  // ����ʧ��˵��
		}
		recordset.dispose();
		return geoJSON;
	}
	
	/**
	* ���Բ�ѯ����ѯָ���ֶ�֮�а����ؼ��ֵļ�¼
	* @param fieldName    ��ѯ���ֶ�������Name, Name_PY, Name_PYSZM,�������ֶΣ�����ƴ���ֶΣ�����ƴ������ĸ�ֶ�
	* @param keywords     �Բ�ѯ�ֶ�����ѯ�Ĺؼ���
	* @param geoRegion    ����ָ����Χ�����ΪNULL�����ʾȫ��Χ��ѯ
	* @param count       ���صĲ�ѯ���������Ĭ��10��������100��100��
	*/
	public void queryByKeyword (String fieldName, String keywords, Geometry geoRegion, int count) {
		StringBuilder attrFilterBuilder = new StringBuilder();
		attrFilterBuilder.append(fieldName + " like \"%" + keywords + "\"%");
		int queryCount = 0;
		if(count < 1){
			queryCount = 10;
		}else if(count > 100){
			queryCount = 100;
		}
		attrFilterBuilder.append(" limit " + queryCount);
		queryTask(this, fieldName, attrFilterBuilder.toString(), geoRegion);
	}
	
	/**
	* �ռ��ѯ����ѯָ���ռ䷶Χ�ڷ����ֶ������ļ�¼
	* @param attributeFilter  ��ѯ������������˵ Kind=2008
	* @param geoRegion        ��ѯ������
	* @param count            ���صĲ�ѯ���������Ĭ��10��������100��100��
	 */
	public void queryByFilter (String attributeFilter, Geometry geoRegion, int count) {
		StringBuilder attrFilterBuilder = new StringBuilder();
		attrFilterBuilder.append(attributeFilter);
		int queryCount = 0;
		if(count < 1){
			queryCount = 10;
		}else if(count > 100){
			queryCount = 100;
		}
		attrFilterBuilder.append(" limit " + queryCount);
		queryTask(this, attributeFilter, attrFilterBuilder.toString(), geoRegion);
	}
	
	/**
	 *  �����첽�Ļص�����������{@link #queryByFilter(String, Geometry, int)}��{@link #queryByKeyword(String, String, Geometry, int)}��ѯ����ļ���
	 * @param listener  ��ѯ���������{@link QueryListener}
	 */
	public void setQueryListener (QueryListener listener){
		m_QueryListener = listener;
	}

	// �첽��ѯ
	private void queryTask(final Dataset dataset, final String fieldName, final String attributeFilter, final Geometry geoRegion){
		
		Runnable task = new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
					QueryParameter param = new QueryParameter();
					if (geoRegion != null) {
						param.setSpatialQueryObject(geoRegion);
						param.setSpatialQueryMode(SpatialQueryMode.CONTAIN); // ָ�������а����ĵ�
					}
					param.setCursorType(CursorType.STATIC);
					param.setAttributeFilter(attributeFilter); // ��ѯfieldName0ָ���ֶ��а���keywords�ļ�¼
					Recordset recordset = ((DatasetVector) dataset).query(param);
					
					Vector<Integer> IDs = new Vector<Integer>();
					if (recordset != null) {
						recordset.moveFirst();
						while (!recordset.isEOF()) {
							IDs.add(recordset.getID());
							recordset.moveNext();
						}
						recordset.close();
						recordset.dispose();
					
					m_QueryListener.queryResult(dataset, fieldName, IDs);
				}
			}
		};
		
		Thread thread = new Thread(task);
		thread.start();
	}
	
	/**
	 * ��ָ���ļ�(txt)�ж�ȡdataset��GeoJSON���ݣ������뵽��ǰdataset��.����ʱ������һ����¼��SmID�Ѿ����ڣ���ô���޸�������¼���������Ӽ�¼��
	 * ֧�ֵ����ݼ����ͣ��㡢�ߡ��桢CAD���ݼ����������ͣ��㡢�ߡ���(��GeoPoint, GeoLine, GeoRegion)��
	 * ע���÷�����{@link #toGeoJSON(File)}���ʹ�ã���ȡ�ļ�Ҳ�Ǹ���{@link #toGeoJSON(File)}���趨�ģ�
	 * ����ʹ�õ��ļ���{@link #toGeoJSON(File)}�����ļ��ĸ�ʽ��һ�£��ܿ��ܻᵼ��ʧ�ܡ�
	 * @param   file  �洢dataset��GeoJSON���ݣ�ͨ��Ϊtxt�ļ�
	 * @return  count �ɹ�����ļ�¼��
	 */
	public int fromGeoJSON(File file){
		if(file == null){
			String message = InternalResource.loadString("file",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}			
		if (!file.exists() || file.isDirectory()) {
			String message = InternalResource.loadString("file",
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		int count = 0;
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			boolean isImported = false;
			while((line = bufferedReader.readLine()) != null){
				isImported = fromGeoJSON(line);
				if(isImported)
					count ++;
			}
			
			bufferedReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return count;
	}
	
	/**
	 * ����ǰ���ݼ��е����м�¼��ת��GeoJSON�ַ�����������ָ�����ļ�(txt)�У�����ÿһ��Ϊһ����¼����"\n"��β��
	 * ֧�ֵ����ݼ����ͣ��㡢�ߡ��桢CAD���ݼ����������ͣ��㡢�ߡ���(��GeoPoint, GeoLine, GeoRegion)��
	 * @param  file   ���ڴ洢GeoJSON�ַ������ļ�������ļ����ڣ��������׷�ӣ���������ڣ����½�һ���ļ���
	 * @return count  �ɹ������ļ�¼��
	 */
	public int toGeoJSON(File file){
		if(file == null){
			String message = InternalResource.loadString("file",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}			
		if (file.isDirectory()) {
			String message = InternalResource.loadString("file",
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}

		FileWriter fileWriter = null;
		int count = 0;
		try {
			Recordset recordset = null; 
			String geoJSON = null;
			
			recordset = this.getRecordset(false, CursorType.STATIC);
			recordset.moveFirst();
			while (!recordset.isEOF()) {
				geoJSON = recordset.toGeoJSON(true, 1);
				if (geoJSON != null) {
					fileWriter = new FileWriter(file, true);
					fileWriter.write(geoJSON + "\n");
					fileWriter.close();
					count++;
				}
				recordset.moveNext();
			}
			recordset.dispose();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	
	/**
	*  �������ݼ��е����е�ת�����߶���,��֧�ֵ����ݼ�
	*  <p>�����ǵ����ݼ������׳��쳣���������ݼ������������㣬�򷵻�null</p>
	*  @return GeoLine ת���ɹ�������ת������߶��󣻷��򷵻�null
	*/
	public GeoLine convertToLine(){
		GeoLine geoLine = null;
		if (this.getType() == DatasetType.POINT) {

			Recordset recordset = this.getRecordset(false, CursorType.STATIC);
			// ��ѯʧ�ܣ�����null
			if(recordset == null)
				return null;
			
			int count = recordset.getRecordCount();
			// ��ѯ�������1���㣬����null
			if(count >1){
				GeoPoint geoPoint = null;
				Point2Ds points = new Point2Ds();
			 	geoLine = new GeoLine();
				recordset.moveFirst();
				while(!recordset.isEOF()){
					geoPoint = (GeoPoint)recordset.getGeometry();
					points.add(geoPoint.getInnerPoint());
					geoPoint.dispose();
					recordset.moveNext();
				}
				geoLine.addPart(points);
			}
			recordset.close();
			recordset.dispose();
		} else {
			throw new IllegalArgumentException("���ݼ���" +this.getName() + "���ǵ����ݼ�");
		}
		return geoLine;
	}

	/**
	*  �������ݼ��е�����ͬ�ֶ�ֵ�ĵ�ת�����߶���,��֧�ֵ����ݼ�
	*  <p>�����ǵ����ݼ������׳��쳣���������ݼ��в�����ָ�����ֶλ�û�а������ֶ�ֵ�ĵ㣬�Լ�ƥ�������������㣬�򷵻�null</p>
	*  @param fieldName   �ֶ���
	*  @param fieldValue  �ֶ�ֵ
	*  @return GeoLine    ת���ɹ�������ת������߶��󣻷��򷵻�null
	*/
	public GeoLine convertToLine(String fieldName, String fieldValue){
		GeoLine geoLine = null;
		// �ᵼ�²�ѯ����ָ������ģ�������null
		if (this.getType() == DatasetType.POINT) {
			// ����Ϊnull,���ò�ѯ������null
            if(fieldName == null || fieldValue == null)
            	return null;
            
            FieldInfos fieldInfos = this.getFieldInfos();
            FieldInfo fieldInfo = fieldInfos.get(fieldName);
            // ������ָ�����ֶΣ�����null
            if(fieldInfo == null)
            	return null;
            
            FieldType fieldType = fieldInfo.getType();
            String queryAttribute = null;
            queryAttribute = fieldName + "=" + fieldValue;
            if(fieldType == FieldType.TEXT || fieldType == FieldType.WTEXT || fieldType == FieldType.CHAR)
            	queryAttribute = fieldName + "=" + "'" + fieldValue +  "'";
            if(fieldType == FieldType.DATETIME)
            	queryAttribute = fieldName + "=" + "to_date(" + fieldValue + ")";
           
			Recordset recordset = this.query(queryAttribute, CursorType.STATIC);
			// ��ѯʧ�ܣ�����null
			if(recordset == null)
				return null;
			
			int count = recordset.getRecordCount();
			// ��ѯ�������1���㣬����null
			if(count >1){
				GeoPoint geoPoint = null;
				Point2Ds points = new Point2Ds();
				geoLine = new GeoLine();
				recordset.moveFirst();
				while(!recordset.isEOF()){
					geoPoint = (GeoPoint)recordset.getGeometry();
					points.add(geoPoint.getInnerPoint());
					geoPoint.dispose();
					recordset.moveNext();
				}
				geoLine.addPart(points);
			}
			recordset.close();
			recordset.dispose();
		} else {
			throw new IllegalArgumentException("���ݼ���" +this.getName() + "���ǵ����ݼ�");
		}
		return geoLine;
	}
	
	// ��ȡ�ռ���������
	public SpatialIndexType getSpatialIndexType() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugType = DatasetVectorNative.jni_GetSpatialIndexType(this
				.getHandle());
		return (SpatialIndexType) Enum.parseUGCValue(SpatialIndexType.class,
				ugType);
	}

	// ����ʸ�����ݼ��пռ������Ƿ���Ҫ�ؽ�����Ϊ���޸����ݺ󣬿�����Ҫ�ؽ��ռ�������
	// 1.��ʸ�����ݼ��޿ռ�����ʱ��������¼�����Ѵﵽ�����ռ�������Ҫ���򷵻�true�������û������ռ����������򷵻�false
	// 2.���ʸ�����ݼ����пռ�������ͼ���������⣩�������¼�����Ѿ����ܴﵽ�����ռ�������Ҫ��ʱ������true��
	// ��Ҫ�ؽ�����������true�����򷵻�false
	public boolean isSpatialIndexDirty() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return DatasetVectorNative.jni_GetIsSpatialIndexDirty(this.getHandle());
	}

	// �жϵ�ǰ���ݼ��Ƿ�֧��ָ�������͵Ŀռ�����
	// spatialIndexType ָ���Ŀռ�����������
	// ���֧�֣�����ֵΪtrue������Ϊfalse
	public boolean isSpatialIndexTypeSupported(SpatialIndexType spatialIndexType) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (spatialIndexType == null) {
			String message = InternalResource.loadString("spatialIndexType",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		return DatasetVectorNative.jni_IsSpatialIndexTypeSupported(
				this.getHandle(), spatialIndexType.getUGCValue());
	}

	// ���ݸ����Ŀռ�������Ϣ��Ϊʸ�����ݼ������ռ�������
	public boolean buildSpatialIndex(SpatialIndexInfo spatialIndexInfo) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		SpatialIndexType type = spatialIndexInfo.getType();
		if (!this.isSpatialIndexTypeSupported(type)) {
			String message = InternalResource
					.loadString(
							"spatialIndexInfo",
							InternalResource.DatasetVectorBuildSpatialIndexUnsupportTheSpatialIndexType,
							InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (spatialIndexInfo == null || spatialIndexInfo.getHandle() == 0) {
			String message = InternalResource.loadString("spatialIndexInfo",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		boolean result = DatasetVectorNative.jni_BuildSpatialIndex2(this.getHandle(),
				spatialIndexInfo.getHandle());
//		InternalHandleDisposable.makeSureNativeObjectLive(spatialIndexInfo);
		return result;
	}

	// ���ݸ����Ŀռ�����������Ϊʸ�����ݼ������ռ�������
	// 1.���ݿ��еĵ����ݼ���֧���Ĳ���������r������
	// 2.�������ݼ���֧���κ����͵Ŀռ�����
	// 3.�������ݼ���֧���κ����͵Ŀռ�����
	// 4.·�����ݼ���֧��ͼ������(TILE)
	// 5.�������ݼ���֧�ֶ༶��������
	// 6.���ݿ��¼Ҫ����1000��ʱ�ſ��Դ�������
	// ��������Ҫ�����ݼ��ر�״̬ʱ���У��統ǰ���ݼ���Ȼ�򿪣����ؽ��ռ�����ʧ��
	public boolean buildSpatialIndex(SpatialIndexType spatialIndexType) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (spatialIndexType == null) {
			String message = InternalResource.loadString("spatialIndexType",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (!this.isSpatialIndexTypeSupported(spatialIndexType)) {
			String message = InternalResource
					.loadString(
							"spatialIndexType",
							InternalResource.DatasetVectorBuildSpatialIndexUnsupportTheSpatialIndexType,
							InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		
		return DatasetVectorNative.jni_BuildSpatialIndex1(this.getHandle(),
				spatialIndexType.getUGCValue());
	}

	/**
	 * // ��ԭ�еĿռ����������Ͻ����ؽ������ԭ���Ŀռ��������ƻ�����ô�ؽ��ɹ��󻹿��Լ���ʹ�á�
	 *
	 * @return
	 */
	public boolean reBuildSpatialIndex() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"reBuildSpatialIndex()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetVectorNative.jni_ReBuildSpatialIndex(this.getHandle());
	}

	// ɾ���ռ�����
	public boolean dropSpatialIndex() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetVectorNative.jni_DropSpatialIndex(getHandle());
	}

	// ���¼������ݼ��Ŀռ䷶Χ
	public Rectangle2D computeBounds() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// left bottom right top
		double[] buffer = new double[4];
		DatasetVectorNative.jni_ComputeBounds(getHandle(), buffer);
		Rectangle2D rect = new Rectangle2D(buffer[0], buffer[1], buffer[2],
				buffer[3]);
		return rect;
	}
}
