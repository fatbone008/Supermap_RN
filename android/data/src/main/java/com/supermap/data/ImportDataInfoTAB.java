package com.supermap.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.supermap.data.DatasetType;
import com.supermap.data.FieldInfo;
import com.supermap.data.FieldInfos;

class ImportDataInfoTAB extends ImportDataInfo {

	ImportDataInfoTAB(long handle, String filePath, FileType fileType, String targetName) {
		super(handle, filePath, fileType, targetName);
	}

	/**
	 * 获取导入矢量数据类型
	 * @return
	 */
	public DatasetType getDatasetType() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getDatasetType()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugcValue = ImportDataInfoTABNative.jni_GetDatasetType(this
				.getHandle());
		return (DatasetType) InternalEnum.parseUGCValue(DatasetType.class,
				ugcValue);
	}

	/**
	 * 获取导入矢量数据的要素个数
	 * @return
	 */
	public int getRecordCount() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getRecordCount()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return ImportDataInfoTABNative.jni_GetRecordCount(this.getHandle());
	}

	/**
	 * 获取源数据中的字段信息
	 * 
	 * @return 源数据中的字段信息
	 */
	public FieldInfo[] getSourceFieldInfos() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"getSourceFieldInfos()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return getSourceFieldInfos_base();
	}

	/**
	 * 获取目标数据中的字段信息
	 * 
	 * @return 目标数据中的字段信息
	 */
	public FieldInfo[] getTargetFieldInfos() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"getTargetFieldInfos()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return getTargetFieldInfos_base();
	}

	/**
	 * 设置字段名称之后，及时更新TargetFieldInfos，而SourceFieldInfos是不变的
	 * 
	 * @param oldName 原始字段名称
	 * @param newName 目标字段类型
	 * @return 更新是否成功
	 */
	public boolean changeFieldName(String oldName, String newName) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"ChangeFieldName(String oldName, String newName)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return changeFieldName_base(oldName, newName);
	}

	/**
	 * 设置指定字段是否导入，当excludeField为true时，不包含该字段
	 * 组件层使用一个HashMap来保存所有需要排除的字段名,在ImportSetting的setTargetDataInfos中进行处理，更新到底层中
	 * 
	 * @param oldName 原始字段名称
	 * @param excludeField
	 * @return 设置是否成功
	 */
	public boolean setImportFieldState(String fieldName, boolean excludeField) {
		if (getHandle() == 0) {
			String message = InternalResource
					.loadString(
							"setImportFieldState(String fieldname,Boolean excludeField)",
							InternalResource.HandleObjectHasBeenDisposed,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return setImportFieldState_base(fieldName, excludeField);
	}

	/**
	 * 交换目标字段的顺序
	 * 
	 * @param tagFieldName1 目标字段名称1
	 * @param tagFieldName2 目标字段名称2
	 * @return 设置是否成功
	 */
	public boolean exchangeFieldOrder(String fieldName1, String fieldName2) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"exchangeFieldOrder(String fieldName1, String fieldName2)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return exchangeFieldOrder_base(fieldName1, fieldName2);
	}
	
	protected void clearHandle() {
		super.clearHandle();
	}
}
