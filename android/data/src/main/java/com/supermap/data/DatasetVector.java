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
 * @author 李云锦
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
	 * 数据追加
	 * @param recordset 要追加的记录集
	 * @return 成功为true否则为false
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
	 * 数据集追加
	 * @param recordset 要追加的记录集
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
	 * 通过ID数组查询记录集
	 * @param id ID数组
	 * @param cursorType 查询模式
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
		// 褰撳弬鏁癱ursorType涓簄ull鏃讹紝鍒欓粯璁ゅ鐞嗕负锛欳ursorType.STATIC
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

	// 字段数目
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

	// 返回矢量数据集的子数据集。
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
	
	// 字段信息集合
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
				//http://192.168.115.2:8090/browse/UGOVI-124 数据集Close后再重新获取一遍，底层数据集fieldinfos地址不会变
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
	
	// 通过设置查询条件对矢量数据集进行查询。
//	public Recordset query(QueryParameter queryParameter) {
//		// luhao 2010-6-1  外部频繁调用时非常耗时，注释之
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
//		// UGCQueryDef的指针
//		long defHandle = 0;
//		// 查询模式，属性查询还是各种空间查询.
//		int ugcQueryMode = -1;
//		// 因为有可能会修改已有的Parameter，所以克隆份，使用完后dispose掉
//		QueryParameter queryDef = null;
//		// 空间查询对象的类别,如果没有设置该属性就为-1
//		int ugcSpatialObjectType = -1;
//		// 空间查询对象的指针
//		long ugcSpatialObjectHandle = 0;
//		// 查询后返回的指针
//		long handle = 0;
//		// 查询模式
//		String strPattern = "";
//
//		// copy一份
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
//            		// 空间查询模式为相交查询，而搜索对象为Rectangle2D时，直接采用Bounds查询以提高查询速度
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
//		// 如果为-1就只执行属性查询,否则，根据相关的情况执行不同的查询
//		ugcQueryMode = queryDef.getSpatialQueryMode().getUGCValue();
//		// strPattern = SpatialComparePattern.toUGCString(queryDef
//		// .getSpatialFilter());
//		// 获取查询几何对象的类别
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
//		// 这里的逻辑不正确
//		Recordset recordset = null;
//		if (handle == 0) {
//			recordset = this.getRecordset(true, queryParameter.getCursorType());
//			// modified by not attributable at 2008年5月20日 下午05时00分46秒
//			// reason:只有当查询失败，才设置setQueryParameter
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
			// 先用上面本身的查询方法查询，如果查出来的recordset 中的记录数为0，且是属性查询，
			// 且查询条件中设置了分组条件GroupBy，则使用以下方法再查一次：先使用SQLite数据库查询SmID，
			// 查询出的SmID存为一个int数组，然后调用通过id查询，将查出的记录集返回。
			// 解决移动端分组查询调用底层的方法，一个组中的对象数量大于1w8时查询不出来，
			// 用SQLite数据库直接查可以查出的问题(added by hp 2014/5/10)
			String[] strList = queryDef.getGroupBy();
			if(ugcQueryMode == -1 && strList.length > 0){
				// 使用数据库直接查询表相关代码
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
	            
	            // 构造查询语句,将分组查询结果的SMID查出来,前n-1个字段后面加“,”
	            // 最后一个字段不加“,”
	            String strTable = this.getName();
	            String strPar = " SELECT SMID FROM " +  strTable +" GROUP BY ";
	            for(int n = 0; n < strList.length - 1; n++){
	                strPar = strPar + strList[n] + ",";
	            }
	            
	            strPar = strPar + strList[strList.length - 1];
	        	
	            // 用查出来的SMID，构造查询id数组
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
					
					// 用构造的查询id和将分组条件GroupBy置为空的查询参数再次查询
					// 将分组条件GroupBy置为空，否则又回到上面的调用底层的方法大于1w8时查询不出来的老路
					int nUgcValue = CursorType.STATIC.getUGCValue();
					queryDef.setGroupBy(null);
					defHandle = queryDef.getHandle();
					handle1 = DatasetVectorNative.jni_Query7(getHandle(),
							id, defHandle, nUgcValue);
				}
				
				// 用完之后要将数据库关闭
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
		// 当参数cursorType为null时，则默认处理为：CursorType.STATIC
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
	
	// 对指定字段进行诸如最大值、最小值、平均值、总和、标准差和方差的统计。
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

	// 对指定字段进行诸如最大值、最小值、平均值、总和、标准差和方差的统计。
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
		// @modified by 李云锦 at 2007年10月12日 下午02时06分46秒
		// @reason 这里忘记释放子类的了
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
	 * 从GeoJSON字符串中获取几何对象，并将其存入数据集中
	 * <p>仅支持点、线、面和CAD数据集，获取点、线、面对象
	 * @param geoJSON     GeoJSON格式的字符串
	 * @return            获取成功，返回true;否则，返回false
	 */
	public boolean fromGeoJSON(String geoJSON){
		DatasetType type = this.getType();
		if(type != DatasetType.POINT && type != DatasetType.LINE && type != DatasetType.REGION && type != DatasetType.CAD){
			Log.e("DatasetVector", "不支持的数据集类型：" + type);
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
	 * 将数据集中指定起止SmID的对象，转换成GeoJSON格式的字符串
	 * <p>仅支持点、线、面和CAD数据集，转换点、线、面对象.hasAtrributte为true时，结果中包含属性值;hasAtrribute为false时，只有几何对象。
	 * @param hasAttributte     是否包含属性值
	 * @param startID           起始SmID
	 * @param endID             结束SmID
	 * @return                  返回GeoJSON格式的字符串，没有转换成功，返回null;
	 * 
	 * <p><strong>注</strong>从startID开始计算,该方法最多能一次转换10条记录,在到达endID指定的记录之前,已转换的记录达到10,则不再继续转换。
	 * 如果startID对应的记录不存在,那么直接返回null。
	 */
	public String toGeoJSON(boolean hasAttributte, int startID, int endID) {
		DatasetType type = this.getType();
		if(type != DatasetType.POINT && type != DatasetType.LINE && type != DatasetType.REGION && type != DatasetType.CAD){
			Log.e("DatasetVector", "不支持的数据集类型：" + type);
			return null;
		}
		
		if(startID < 0 || endID < 0 || startID > endID){
			Log.e("DatasetVector", "startID和 endID必须大于0，且endID不小于startID");
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
			Log.e("DatasetVector", "Not found start SmID: " + startID);  // 增加失败说明
		}
		recordset.dispose();
		return geoJSON;
	}
	
	/**
	* 属性查询，查询指定字段之中包含关键字的记录
	* @param fieldName    查询的字段名，如Name, Name_PY, Name_PYSZM,即名称字段，名称拼音字段，名称拼音首字母字段
	* @param keywords     对查询字段做查询的关键字
	* @param geoRegion    可以指定范围，如果为NULL，则表示全范围查询
	* @param count       返回的查询结果个数，默认10个，超过100按100算
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
	* 空间查询，查询指定空间范围内符合字段条件的记录
	* @param attributeFilter  查询的条件，比如说 Kind=2008
	* @param geoRegion        查询的区域
	* @param count            返回的查询结果个数，默认10个，超过100按100算
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
	 *  设置异步的回调函数，用于{@link #queryByFilter(String, Geometry, int)}和{@link #queryByKeyword(String, String, Geometry, int)}查询结果的监听
	 * @param listener  查询结果监听器{@link QueryListener}
	 */
	public void setQueryListener (QueryListener listener){
		m_QueryListener = listener;
	}

	// 异步查询
	private void queryTask(final Dataset dataset, final String fieldName, final String attributeFilter, final Geometry geoRegion){
		
		Runnable task = new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
					QueryParameter param = new QueryParameter();
					if (geoRegion != null) {
						param.setSpatialQueryObject(geoRegion);
						param.setSpatialQueryMode(SpatialQueryMode.CONTAIN); // 指定区域中包含的点
					}
					param.setCursorType(CursorType.STATIC);
					param.setAttributeFilter(attributeFilter); // 查询fieldName0指定字段中包含keywords的记录
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
	 * 从指定文件(txt)中读取dataset的GeoJSON数据，并导入到当前dataset中.导入时，导入一条记录的SmID已经存在，那么就修改这条记录，否则增加记录。
	 * 支持的数据集类型：点、线、面、CAD数据集，几何类型：点、线、面(即GeoPoint, GeoLine, GeoRegion)。
	 * 注：该方法与{@link #toGeoJSON(File)}配合使用，读取文件也是根据{@link #toGeoJSON(File)}而设定的，
	 * 若是使用的文件与{@link #toGeoJSON(File)}导出文件的格式不一致，很可能会导入失败。
	 * @param   file  存储dataset的GeoJSON数据，通常为txt文件
	 * @return  count 成功导入的记录数
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
	 * 将当前数据集中的所有记录都转成GeoJSON字符串，并存入指定的文件(txt)中，并且每一行为一条记录，以"\n"结尾。
	 * 支持的数据集类型：点、线、面、CAD数据集，几何类型：点、线、面(即GeoPoint, GeoLine, GeoRegion)。
	 * @param  file   用于存储GeoJSON字符串的文件，如果文件存在，则在其后追加，如果不存在，会新建一个文件。
	 * @return count  成功导出的记录数
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
	*  将点数据集中的所有点转换成线对象,仅支持点数据集
	*  <p>若不是点数据集，会抛出异常。若点数据集中少于两个点，则返回null</p>
	*  @return GeoLine 转换成功，返回转换后的线对象；否则返回null
	*/
	public GeoLine convertToLine(){
		GeoLine geoLine = null;
		if (this.getType() == DatasetType.POINT) {

			Recordset recordset = this.getRecordset(false, CursorType.STATIC);
			// 查询失败，返回null
			if(recordset == null)
				return null;
			
			int count = recordset.getRecordCount();
			// 查询结果少于1个点，返回null
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
			throw new IllegalArgumentException("数据集：" +this.getName() + "不是点数据集");
		}
		return geoLine;
	}

	/**
	*  将点数据集中的有相同字段值的点转换成线对象,仅支持点数据集
	*  <p>若不是点数据集，会抛出异常。若点数据集中不存在指定的字段或没有包含该字段值的点，以及匹配结果少于两个点，则返回null</p>
	*  @param fieldName   字段名
	*  @param fieldValue  字段值
	*  @return GeoLine    转换成功，返回转换后的线对象；否则返回null
	*/
	public GeoLine convertToLine(String fieldName, String fieldValue){
		GeoLine geoLine = null;
		// 会导致查询不到指定结果的，都返回null
		if (this.getType() == DatasetType.POINT) {
			// 参数为null,不用查询，返回null
            if(fieldName == null || fieldValue == null)
            	return null;
            
            FieldInfos fieldInfos = this.getFieldInfos();
            FieldInfo fieldInfo = fieldInfos.get(fieldName);
            // 不存在指定的字段，返回null
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
			// 查询失败，返回null
			if(recordset == null)
				return null;
			
			int count = recordset.getRecordCount();
			// 查询结果少于1个点，返回null
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
			throw new IllegalArgumentException("数据集：" +this.getName() + "不是点数据集");
		}
		return geoLine;
	}
	
	// 获取空间索引类型
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

	// 返回矢量数据集中空间索引是否需要重建。因为在修改数据后，可能需要重建空间索引。
	// 1.当矢量数据集无空间索引时，如果其记录条数已达到建立空间索引的要求，则返回true，建议用户创建空间索引；否则返回false
	// 2.如果矢量数据集已有空间索引（图库索引除外），但其记录条数已经不能达到建立空间索引的要求时，返回true。
	// 需要重建索引，返回true；否则返回false
	public boolean isSpatialIndexDirty() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return DatasetVectorNative.jni_GetIsSpatialIndexDirty(this.getHandle());
	}

	// 判断当前数据集是否支持指定的类型的空间索引
	// spatialIndexType 指定的空间索引的类型
	// 如果支持，返回值为true，否则为false
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

	// 根据给定的空间索引信息来为矢量数据集创建空间索引。
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

	// 根据给定的空间索引类型来为矢量数据集创建空间索引。
	// 1.数据库中的点数据集不支持四叉树索引和r树索引
	// 2.网络数据集不支持任何类型的空间索引
	// 3.属性数据集不支持任何类型的空间索引
	// 4.路由数据集不支持图幅索引(TILE)
	// 5.复合数据集不支持多级网格索引
	// 6.数据库记录要大于1000条时才可以创建索引
	// 本操作需要在数据集关闭状态时进行，如当前数据集仍然打开，则重建空间索引失败
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
	 * // 在原有的空间索引基础上进行重建，如果原来的空间索引被破坏，那么重建成功后还可以继续使用。
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

	// 删除空间索引
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

	// 重新计算数据集的空间范围
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
