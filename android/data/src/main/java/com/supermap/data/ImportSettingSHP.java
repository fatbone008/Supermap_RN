package com.supermap.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EncodeType;
import com.supermap.data.FieldInfo;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.SpatialIndexInfo;

/*
 * ArcView Shape的导入的参数类型
 Shp数据不能导入成CAD，没有EmptyGeometry

 */
class ImportSettingSHP extends ImportSetting {

	// private ImportDataInfos m_importDataInfos;
	private SpatialIndexInfo m_spatialIndexInfo;

	/**
	 * 默认构造函数，创建一个新的ImportSettingMIF对象
	 */
	public ImportSettingSHP() {
		long handle = ImportSettingSHPNative.jni_New();
		this.setHandle(handle, true);
		super.setDataType(DataType.VECTOR);
		this.setSpatialIndex(null);
	}

	/**
	 * 拷贝构造函数，返回一个设置信息相同的ImportSettingMIF对象
	 * 
	 * @param importSettingMIF
	 */
	public ImportSettingSHP(ImportSettingSHP ImportSettingSHP) {
		if (ImportSettingSHP == null) {
			String message = InternalResource.loadString("ImportSettingSHP",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = InternalHandle.getHandle(ImportSettingSHP);
		if (handle == 0) {
			String message = InternalResource.loadString("ImportSettingSHP",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long thisHandle = ImportSettingSHPNative.jni_Clone(handle);
		this.setHandle(thisHandle, true);
		this.setTargetDatasourceConnectionInfo(ImportSettingSHP
				.getTargetDatasourceConnectionInfo());
		this.setTargetDatasource(ImportSettingSHP.getTargetDatasource());
		this.setTargetPrjCoordSys(ImportSettingSHP.getTargetPrjCoordSys());
		super.setDataType(DataType.VECTOR);
		this.setSpatialIndex(ImportSettingSHP.getSpatialIndex());
//		InternalHandleDisposable.makeSureNativeObjectLive(ImportSettingSHP);
	}

	public ImportSettingSHP(String sourceFilePath, Datasource targetDatasource) {
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasource(targetDatasource);
	}
    
	

	/**
	 * 获取是否导入成为3D数据集默认为false 即不导入成为3D数据集
	 * 否则设置成为TRUE的时候，导入成为3D数据集
	 * @return
	 */
	public boolean isImporttingAs3D()
	{
		if(getHandle()==0)
		{
			String message = InternalResource.loadString("isImporttingAs3D()", 
					InternalResource.HandleObjectHasBeenDisposed
					,InternalResource.BundleName);
			
			throw new IllegalArgumentException(message);
		}
		
		return ImportSettingSHPNative.jni_IsImportingAs3D(getHandle());
	}
	
	/*
	 * 设置是否导入成为3d数据集
	 */
	public void setImporttingAs3D(boolean value)
	{
		if(getHandle()==0)
		{
			String message = InternalResource.loadString("setImporttingAs3D()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		ImportSettingSHPNative.jni_SetImportingAs3D(getHandle(), value);
	}
	
	
	
	
	/**
	 * 通过指定源文件来构造一个ImportSettingMIF对象
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
	public ImportSettingSHP(String sourceFilePath,
			DatasourceConnectionInfo targetConnectionInfo) {
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasourceConnectionInfo(targetConnectionInfo);
//		InternalHandleDisposable.makeSureNativeObjectLive(targetConnectionInfo);
	}

	//	/**
	//	 * SHP不能导出为CAD
	//	 * 
	//	 * @return 是否为CAD类型
	//	 */
	//	public boolean isImportingAsCAD() {
	//		//		if (getHandle() == 0) {
	//		//			String message = InternalResource.loadString("isImportAsCAD()",
	//		//					InternalResource.HandleObjectHasBeenDisposed,
	//		//					InternalResource.BundleName);
	//		//			throw new IllegalArgumentException(message);
	//		//		}
	//		//		return ImportSettingSHPNative.jni_IsImportingAsCAD(getHandle());
	//		return false;
	//	}
	//
	//	/**
	//	 * 设置导入模式，即目标数据集类型，即导入为CAD数据集, 否则为数据对应类型的简单矢量数据集
	//	 * 
	//	 * @param value
	//	 */
	//	public void setImportingAsCAD(boolean value) {
	//		if (getHandle() == 0) {
	//			String message = InternalResource.loadString(
	//					"setImportAsCAD(boolean value)",
	//					InternalResource.HandleObjectHasBeenDisposed,
	//					InternalResource.BundleName);
	//			throw new IllegalArgumentException(message);
	//		}
	//		String message = InternalResource.loadString(
	//				"setImportAsCAD(boolean value)",
	//				InternalResource.ImportSettingSHPUnsupportedOperation,
	//				InternalResource.BundleName);
	//		throw new IllegalArgumentException(message);
	////		ImportSettingSHPNative.jni_SetImportingAsCAD(getHandle(), value);
	//	}

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
		//long handle = ImportSettingSHPNative.jni_GetSpatialIndex(getHandle());
		//return InternalSpatialIndexInfo.createInstance(handle);
		if( m_spatialIndexInfo == null ){
			long handle = ImportSettingSHPNative
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
		ImportSettingSHPNative.jni_SetSpatialIndex(getHandle(),handle);
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
		return ImportSettingSHPNative.jni_IsAttributeIgnored(getHandle());
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
		ImportSettingSHPNative.jni_SetAttributeIgnored(getHandle(), value);
	}

	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			ImportSettingSHPNative.jni_Delete(getHandle());
			clearHandle();
		}
	}

	protected void clearHandle() {
		super.clearHandle();
	}
}
