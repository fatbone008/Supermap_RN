/**
 * 
 */
package com.supermap.data;

import java.io.File;

import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;

class ImportSettingTIF extends ImportSetting{

	/**
	 * 默认构造函数，创建一个新的ImportSettingPNG对象
	 */
	public ImportSettingTIF() {
		long handle = ImportSettingTIFNative.jni_New();
		this.setHandle(handle, true);
		super.setDataType(DataType.RASTER);
	}

	/**
	 * 拷贝构造函数，返回一个设置信息相同的ImportSettingTIF对象
	 * 
	 * @param importSettingTIF
	 */
	public ImportSettingTIF(ImportSettingTIF importSettingTIF) {
		if (importSettingTIF == null) {
			String message = InternalResource.loadString("importSettingTIF",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = InternalHandle.getHandle(importSettingTIF);
		if (handle == 0) {
			String message = InternalResource.loadString("importSettingTIF",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long thisHandle = ImportSettingTIFNative.jni_Clone(handle);
		this.setHandle(thisHandle, true);
		this.setTargetDatasourceConnectionInfo(importSettingTIF.getTargetDatasourceConnectionInfo());
		this.setTargetDatasource(importSettingTIF.getTargetDatasource());
		this.setTargetPrjCoordSys(importSettingTIF.getTargetPrjCoordSys());
		super.setDataType(DataType.RASTER);
//		InternalHandleDisposable.makeSureNativeObjectLive(importSettingTIF);
	}

	/**
	 * 通过指定源文件来构造一个ImportSettingTIF对象
	 * 
	 * @param sourceFilePath
	 *            源文件全路径
	 * @param targetConnectionInfo
	 *            目标数据源连接信息
	 */
	public ImportSettingTIF(String sourceFilePath,
			DatasourceConnectionInfo targetConnectionInfo) {
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasourceConnectionInfo(targetConnectionInfo);
//		InternalHandleDisposable.makeSureNativeObjectLive(targetConnectionInfo);
	}
	
	public ImportSettingTIF(String sourceFilePath, Datasource targetDatasource) {
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasource(targetDatasource);
	}
	/**
	 * 获取是否导入为Grid数据集
	 * @return
	 */
	public boolean isImportingAsGrid() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getTargetDataInfos()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return ImportSettingTIFNative.jni_IsImportingAsGrid(this.getHandle());
	}

	/**
	 * 设置是否导入为Grid数据集
	 * @param value
	 */
	public void setImportingAsGrid(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setImportingAsGrid(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		ImportSettingTIFNative.jni_SetImportingAsGrid(this.getHandle(), value);
	}
	
//	/**
//	 * 获取影像文件的坐标参考文件
//	 * @return
//	 */
//	public String getWorldFilePath() {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString("getWorldFilePath()",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		return ImportSettingTIFNative.jni_GetWorldFilePath(this.getHandle());
//	}
//
//	/**
//	 * 设置影像文件的坐标参考文件
//	 * @param value
//	 */
//	public void setWorldFilePath(String value) {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"setWorldFilePath(String value)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		File file = new File(value);
//		if (!file.exists()) {
//			String message = InternalResource.loadString("",
//					InternalResource.GlobalPathIsNotValid,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		ImportSettingTIFNative.jni_SetWorldFilePath(this.getHandle(), value);
//	}
//
//	/**
//	 * 获取是否自动建立影像金字塔
//	 * @return
//	 */
//	public boolean isPyramidBuilt() {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString("isPyramidBuilt()",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		return ImportSettingTIFNative.jni_IsPyramidBuilt(this.getHandle());
//	}
//
//	/**
//	 * 设置是否自动建立影像金字塔
//	 * @param value
//	 */
//	public void setPyramidBuilt(boolean value) {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"setPyramidBuilt(boolean value)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		ImportSettingTIFNative.jni_SetPyramidBuilt(this.getHandle(), value);
//	}

	/**
	 * 获取多波段导入模式
	 * @return
	 */
	public MultiBandImportMode getMultiBandImportMode() {
		if (this.getHandle() == 0) {
			String message = InternalResource
					.loadString(
							"getMultiBandImportMode()",
							InternalResource.HandleObjectHasBeenDisposed,
							InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugcValue = ImportSettingTIFNative.jni_GetMultiBandImportMode(this.getHandle());
		return (MultiBandImportMode)InternalEnum.parseUGCValue(MultiBandImportMode.class, ugcValue);
	}
	
	/**
	 * 设置多波段导入模式
	 * @param mode
	 */
	public void setMultiBandImportMode(MultiBandImportMode mode) {
		if (this.getHandle() == 0) {
			String message = InternalResource
					.loadString(
							"setMultiBandImportMode(MultiBandImportMode mode)",
							InternalResource.HandleObjectHasBeenDisposed,
							InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (mode == null) {
			String message = InternalResource.loadString("mode",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		ImportSettingTIFNative.jni_SetMultiBandImportMode(this.getHandle(), mode.value());
	}

//	
//	public void setIgnoreMode(IgnoreMode ignoreMode)
//	{
//		if (this.getHandle() == 0) {
//			String message = InternalResource
//					.loadString(
//							"setIgnoreMode(int ignoreMode)",
//							InternalResource.HandleObjectHasBeenDisposed,
//							InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		
//		int ignoreValue = ignoreMode.value();
//		ImportSettingTIFNative.jni_SetIgnorMode(this.getHandle(), ignoreValue);
//	}
//	
//	public IgnoreMode getIgnoreMode()
//	{
//		if(this.getHandle()==0){
//			String message = InternalResource.loadString("getIgnoreMode()", InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		int ignoreValue =  ImportSettingTIFNative.jni_GetIgnorMode(this.getHandle());	
//	    return (IgnoreMode) InternalEnum.parseUGCValue(IgnoreMode.class,
//				ignoreValue);
//	}
//	
//	
//	public double[] getIgnoreValues()
//	{
//	  if (this.getHandle() == 0) {
//		   String message = InternalResource.loadString(
//				"getIgnoreValues()",
//				InternalResource.HandleObjectHasBeenDisposed,
//				InternalResource.BundleName);
//		  throw new IllegalStateException(message);
//	   }
//	    int count = ImportSettingTIFNative.jni_GetIgnoreValueCount(this.getHandle());
//		double[] ignoreValues = new double[count];
//		ImportSettingTIFNative.jni_GetIgnoreValues(this.getHandle(), ignoreValues);
//	    return ignoreValues;
//}
//	
//	public void setIgnoreValues(double []ignoreValues)
//	{
//		if (getHandle() == 0) {
//			String message = InternalResource.loadString("setIgnoreValues",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (ignoreValues == null) {
//			ignoreValues = new double[0];
//		}
//		ImportSettingTIFNative.jni_SetIgnoreValues(this.getHandle(), ignoreValues);
//	}
//
	
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			ImportSettingTIFNative.jni_Delete(getHandle());
			clearHandle();
		}
	}
}


