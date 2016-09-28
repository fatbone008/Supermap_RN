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
	 * ��ȡ����ʸ����������
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
	 * ��ȡ����ʸ�����ݵ�Ҫ�ظ���
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
	 * ��ȡԴ�����е��ֶ���Ϣ
	 * 
	 * @return Դ�����е��ֶ���Ϣ
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
	 * ��ȡĿ�������е��ֶ���Ϣ
	 * 
	 * @return Ŀ�������е��ֶ���Ϣ
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
	 * �����ֶ�����֮�󣬼�ʱ����TargetFieldInfos����SourceFieldInfos�ǲ����
	 * 
	 * @param oldName ԭʼ�ֶ�����
	 * @param newName Ŀ���ֶ�����
	 * @return �����Ƿ�ɹ�
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
	 * ����ָ���ֶ��Ƿ��룬��excludeFieldΪtrueʱ�����������ֶ�
	 * �����ʹ��һ��HashMap������������Ҫ�ų����ֶ���,��ImportSetting��setTargetDataInfos�н��д��������µ��ײ���
	 * 
	 * @param oldName ԭʼ�ֶ�����
	 * @param excludeField
	 * @return �����Ƿ�ɹ�
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
	 * ����Ŀ���ֶε�˳��
	 * 
	 * @param tagFieldName1 Ŀ���ֶ�����1
	 * @param tagFieldName2 Ŀ���ֶ�����2
	 * @return �����Ƿ�ɹ�
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