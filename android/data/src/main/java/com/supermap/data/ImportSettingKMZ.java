package com.supermap.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EncodeType;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.SpatialIndexInfo;

public class ImportSettingKMZ extends ImportSetting {
	/**
	 * 默认构造函数，创建一个新的ImportSettingKMZ对象
	 * 
	 */
	public ImportSettingKMZ()
	{
		long handle = ImportSettingKMZNative.jni_New();
		this.setHandle(handle, true);
		super.setDataType(DataType.VECTOR);
		
	}
	/**
	 * 拷贝构造函数，创建一个新的ImportSettingKMZ对象
	 * 
	 */	
	public ImportSettingKMZ(ImportSettingKMZ importsettingKMZ)
	{
		if (importsettingKMZ == null) 
		{
		String message = InternalResource.loadString("ImportSettingKMZ",
				InternalResource.GlobalArgumentNull,
				InternalResource.BundleName);
		throw new IllegalArgumentException(message);
		}
		long handle = importsettingKMZ.getHandle();
		if (handle == 0)
		{
			String message = InternalResource.loadString("ImportSettingKMZ",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long thisHandle = ImportSettingKMZNative.jni_Clone(handle);
		this.setHandle(thisHandle, true);
		this.setTargetDatasourceConnectionInfo(importsettingKMZ.getTargetDatasourceConnectionInfo());
		this.setTargetDatasource(importsettingKMZ.getTargetDatasource());
		this.setUnvisibleObjectIgnored(importsettingKMZ.isUnvisibleObjectIgnored());
		this.setImportingAsCAD(importsettingKMZ.isImportingAsCAD());
		super.setDataType(DataType.VECTOR);
//		InternalHandleDisposable.makeSureNativeObjectLive(importsettingKMZ);
	}
	/**
	 * 
	 * @param sourceFilePath
	 *            源文件全路径
	 * @param targetConnectionInfo
	 *            目标数据源连接信息
	 */
	public ImportSettingKMZ(String sourceFilePath,
			DatasourceConnectionInfo targetConnectionInfo) {
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasourceConnectionInfo(targetConnectionInfo);
		super.setDataType(DataType.VECTOR);
//		InternalHandleDisposable.makeSureNativeObjectLive(targetConnectionInfo);
	}	
	public ImportSettingKMZ(String sourceFilePath, Datasource targetDatasource)
	{
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasource(targetDatasource);
		super.setDataType(DataType.VECTOR);
	}
	/**
	 * 
	 * @param sourceFilePath
	 *            源文件全路径
	 * @param targetConnectionInfo
	 *            目标数据源连接信息
	 * @param importingAsCAD
	 *            是否以CAD形式导入数据
	 */
	public ImportSettingKMZ(String sourceFilePath,
			DatasourceConnectionInfo targetConnectionInfo,
			boolean importingAsCAD) {
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasourceConnectionInfo(targetConnectionInfo);
		this.setImportingAsCAD(importingAsCAD);
		super.setDataType(DataType.VECTOR);
//		InternalHandleDisposable.makeSureNativeObjectLive(targetConnectionInfo);
	}	
	/**
	 * 获取导入模式，即目标数据集类型，默认为true，
	 * 即导入为CAD数据集,否则为数据对应类型的简单矢量数据集
	 * 
	 * @return 是否以CAD导入
	 */
	public boolean isImportingAsCAD() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("isImportingAsCAD()",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return ImportSettingKMZNative.jni_IsImportingAsCAD(getHandle());
	}

	/**
	 * 设置导入模式，true则使用CAD导入，false使用简单数据集类型导入
	 * @param value
	 */
	public void setImportingAsCAD(boolean value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"setImportingAsCAD(boolean value)",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		ImportSettingKMZNative.jni_SetImportingAsCAD(getHandle(), value);
	}

	/**
	 * 获取是否导入可见对象标识
	 * 
	 *
	 */
	public boolean isUnvisibleObjectIgnored()
	{
		if(getHandle() == 0)
		{
			String message = InternalResource.loadString(
					"getVisibleFlag()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);			
		}
		return ImportSettingKMZNative.jni_GetUnvisibleObjectIgnored(getHandle());
	}
	/**
	 * 设置是否导入可见对象标识
	 * 
	 *
	 */
	public void setUnvisibleObjectIgnored(boolean bvisible)
	{
		if(getHandle() == 0)
		{
			String message = InternalResource.loadString(
					"setVisibleFlag()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);			
		}
		ImportSettingKMZNative.jni_SetUnvisibleObjectIgnored(getHandle(),bvisible);
	}
	public void dispose() 
	{
		// TODO Auto-generated method stub
		if (!this.getIsDisposable()) 
		{
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		}
		else if (this.getHandle() != 0) 
		{
			ImportSettingKMZNative.jni_Delete(getHandle());
			this.setHandle(0);
			clearHandle();
		}
	}
	protected void clearHandle()
	{
		super.clearHandle();
	}
}
