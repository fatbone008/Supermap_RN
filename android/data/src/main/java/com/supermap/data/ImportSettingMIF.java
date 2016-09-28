package com.supermap.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EncodeType;
import com.supermap.data.FieldInfo;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.SpatialIndexInfo;
import com.supermap.data.SpatialIndexType;

class ImportSettingMIF extends ImportSetting {

	// 空间索引
	private SpatialIndexInfo m_spatialIndexInfo;
	
	/**
	 * 默认构造函数，创建一个新的ImportSettingMIF对象
	 */
	public ImportSettingMIF() {
		long handle = ImportSettingMIFNative.jni_New();
        //Modified by Wangbg 2010-9-19
		this.setHandle(handle, true);
		this.setSpatialIndex(null);
		super.setDataType(DataType.VECTOR);
		
	}

	/**
	 * 拷贝构造函数，返回一个设置信息相同的ImportSettingMIF对象
	 * 
	 * @param importSettingMIF
	 */
	public ImportSettingMIF(ImportSettingMIF importSettingMIF) {
		if (importSettingMIF == null) {
			String message = InternalResource.loadString("importSettingMIF",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = InternalHandle.getHandle(importSettingMIF);
		if (handle == 0) {
			String message = InternalResource.loadString(
					"importSettingMIF",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long thisHandle = ImportSettingMIFNative.jni_Clone(handle);
		this.setHandle(thisHandle, true);
		this.setTargetDatasourceConnectionInfo(importSettingMIF.getTargetDatasourceConnectionInfo());
		this.setTargetDatasource(importSettingMIF.getTargetDatasource());
		this.setTargetPrjCoordSys(importSettingMIF.getTargetPrjCoordSys());
		super.setDataType(DataType.VECTOR);
		this.setSpatialIndex(importSettingMIF.getSpatialIndex());
//		InternalHandleDisposable.makeSureNativeObjectLive(importSettingMIF);
	}

	/**
	 * 通过指定源文件来构造一个ImportSettingMIF对象
	 * 
	 * @param sourceFilePath
	 *            源文件全路径
	 * @param targetConnectionInfo
	 *            目标数据源连接信息
	 */
	public ImportSettingMIF(String sourceFilePath,
			DatasourceConnectionInfo targetConnectionInfo) {
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasourceConnectionInfo(targetConnectionInfo);
//		InternalHandleDisposable.makeSureNativeObjectLive(targetConnectionInfo);
	}
	public ImportSettingMIF(String sourceFilePath, Datasource targetDatasource) {
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasource(targetDatasource);
	}

	/**
	 * 获取目标数据集类型是否为CAD类型，默认为true
	 * 
	 * @return 是否为CAD类型
	 */
	public boolean isImportingAsCAD() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("isImportingAsCAD()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return ImportSettingMIFNative.jni_IsImportingAsCAD(getHandle());
	}

	/**
	 * 设置导入模式，即目标数据集类型，即导入为CAD数据集, 否则为数据对应类型的简单矢量数据集
	 * 
	 * @param value
	 */
	public void setImportingAsCAD(boolean value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"setImportingAsCAD(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		ImportSettingMIFNative.jni_SetImportingAsCAD(getHandle(), value);
	}

	/**
	 * 获取空间索引类型，当isSpatialIndexAutoBuilt属性为true时有效
	 * 
	 * @return 空间索引类型
	 */
	public SpatialIndexInfo getSpatialIndex() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"getSpatialIndex()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		//long handle = ImportSettingMIFNative
		//		.jni_GetSpatialIndex(getHandle());
		//return InternalSpatialIndexInfo.createInstance(handle);
		if( m_spatialIndexInfo == null ){
			long handle = ImportSettingMIFNative
					.jni_GetSpatialIndex(getHandle());
			m_spatialIndexInfo = InternalSpatialIndexInfo
					.createInstance(handle);
		}
		return m_spatialIndexInfo;
	}

	/**
	 * 设置空间索引类型
	 * 
	 * @param value
	 */
	public void setSpatialIndex(SpatialIndexInfo value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"setSpatialIndex(SpatialIndexType value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle;
		if(value == null){
			handle = 0;
		}
		else{
			handle = InternalHandle.getHandle(value);
		}
		ImportSettingMIFNative.jni_SetSpatialIndex(getHandle(),handle);
		//将原来的值置为空
		m_spatialIndexInfo = null;
//		InternalHandleDisposable.makeSureNativeObjectLive(value);
	}

	/**
	 * 获取是否忽略属性
	 * 
	 * @return 是否忽略属性
	 */
	public boolean isAttributeIgnored() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"IsAttributeIgnored()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return ImportSettingMIFNative.jni_IsAttributeIgnored(getHandle());
	}

	/**
	 * 设置是否忽略属性
	 * 
	 * @param value
	 */
	public void setAttributeIgnored(boolean value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"setAttributeIgnored(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		ImportSettingMIFNative.jni_SetAttributeIgnored(getHandle(), value);
	}

	/**
	 * 
	 * 获取风格对照路径
	 * @return
	 */
	public String getStyleMapFilePath(){
		if(getHandle()==0){
			String message = InternalResource.loadString(
					"getStyleMapFilePath()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return ImportSettingMIFNative.jni_GetStyleMapFilePath(getHandle());
	}
	
	/**
	 * 设置风格对照路径
	 * @param value
	 */
	public void setStyleMapFilePath(String value){
		if(getHandle()==0){
			String message = InternalResource.loadString(
					"setStyleMapFilePath(String value)()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		ImportSettingMIFNative.jni_SetStyleMapFilePath(getHandle(), value);
	}
	
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			ImportSettingMIFNative.jni_Delete(getHandle());
			this.setHandle(0);
			clearHandle();
		}
	}
}
