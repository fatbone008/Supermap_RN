package com.supermap.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EncodeType;
import com.supermap.data.FieldInfo;
import com.supermap.data.FieldType;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.DatasetType;
import com.supermap.data.SpatialIndexInfo;

/*
 * CSV的导入的参数类型
 CSV数据只能导入成点或CAD数据集
*
 */
class ImportSettingCSV extends ImportSetting {

	// private ImportDataInfos m_importDataInfos;
	private SpatialIndexInfo m_spatialIndexInfo;

	/**
	 * 默认构造函数，创建一个新的ImportSettingCVS对象
	 */
	public ImportSettingCSV() {
		long handle = ImportSettingCSVNative.jni_New();
		this.setHandle(handle, true);
		super.setDataType(DataType.VECTOR);
		this.setSpatialIndex(null);
	}

	/**
	 * 拷贝构造函数，返回一个设置信息相同的ImportSettingCVS对象
	 * 
	 * @param importSettingCSV
	 */
	public ImportSettingCSV(ImportSettingCSV ImportSettingCSV) {
		if (ImportSettingCSV == null) {
			String message = InternalResource.loadString("ImportSettingCSV",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		long handle = InternalHandle.getHandle(ImportSettingCSV);
		if (handle == 0) {
			String message = InternalResource.loadString("ImportSettingCSV",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		long thisHandle = ImportSettingCSVNative.jni_Clone(handle);
		this.setHandle(thisHandle, true);
		this.setTargetDatasourceConnectionInfo(ImportSettingCSV
				.getTargetDatasourceConnectionInfo());
		this.setTargetDatasource(ImportSettingCSV.getTargetDatasource());
		this.setTargetPrjCoordSys(ImportSettingCSV.getTargetPrjCoordSys());
		super.setDataType(DataType.VECTOR);
		this.setSpatialIndex(ImportSettingCSV.getSpatialIndex());
//		InternalHandleDisposable.makeSureNativeObjectLive(ImportSettingCSV);
	}

	public ImportSettingCSV(String sourceFilePath, Datasource targetDatasource) {
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasource(targetDatasource);
	}

	/**
	 * 通过指定源文件来构造一个ImportSettingCSV对象
	 * 
	 * @param sourceFilePath
	 *            源文件全路径
	 * @param sourceFileType
	 *            源文件类型
	 * @param targetConnectionInfo
	 *            目标数据源连接信息
	 * @param targetNamePrefix
	 *            目标数据名的前缀，后接某个类型的简写如：P,L,R分别对应点线面
	 */
	public ImportSettingCSV(String sourceFilePath,
			DatasourceConnectionInfo targetConnectionInfo) {
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasourceConnectionInfo(targetConnectionInfo);
//		InternalHandleDisposable.makeSureNativeObjectLive(targetConnectionInfo);
	}

	/**
	 * 获取空间索引信息
	 * @return 空间索引信息
	 */
	public SpatialIndexInfo getSpatialIndex() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getSpatialIndex()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//long handle = ImportSettingCSVNative.jni_GetSpatialIndex(getHandle());
		//return InternalSpatialIndexInfo.createInstance(handle);
		if( m_spatialIndexInfo == null ){
			long handle = ImportSettingCSVNative
					.jni_GetSpatialIndex(getHandle());
			m_spatialIndexInfo = InternalSpatialIndexInfo
					.createInstance(handle);
		}
		
		return m_spatialIndexInfo;
	}

	public void setSpatialIndex(SpatialIndexInfo value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"setSpatialIndex(SpatialIndexInfo value)",
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
		
		ImportSettingCSVNative.jni_SetSpatialIndex(getHandle(),handle);
		//将原来的值置为空
		m_spatialIndexInfo = null;
//		InternalHandleDisposable.makeSureNativeObjectLive(value);
	}

	/*
	 *  获取文本文件中使用的分隔符
	 */
	public String getSeparator() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"getSeparator()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}		
		return ImportSettingCSVNative.jni_GetSeparator(getHandle());
	}

	/*
	 *  设置文本文件中使用的分隔符
	 */
	public void setSeparator(String separator) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"setSeparator()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}		
		
		if (separator == null || separator.trim().length() == 0) {
			String message = InternalResource.loadString("path",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}		
		
		ImportSettingCSVNative.jni_SetSeparator(getHandle(), separator);
	}

	/**
	 * 获取第一行是否包含字段名称
	 * 
	 * @return 
	 */
	public boolean getFirstRowIsField() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"HasFieldNameInFirstLine()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return ImportSettingCSVNative.jni_GetHasFieldNameInFirstRow(getHandle());
	}

	/**
	 * 设置第一行是否包含字段名称
	 * 
	 * @param value
	 */
	public void setFirstRowIsField(boolean value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"setAttributeIgnored(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		ImportSettingCSVNative.jni_SetHasFieldNameInFirstRow(getHandle(), value);
	}

	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			ImportSettingCSVNative.jni_Delete(getHandle());
			clearHandle();
		}
	}
	
	protected void clearHandle() {
		super.clearHandle();
	}
}
