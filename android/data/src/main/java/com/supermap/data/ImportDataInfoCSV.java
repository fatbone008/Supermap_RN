package com.supermap.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.supermap.data.DatasetType;
import com.supermap.data.FieldInfo;
import com.supermap.data.FieldInfos;
import com.supermap.data.FieldType;

class ImportDataInfoCSV extends ImportDataInfo {
	
	private boolean m_bIsSetTargetFieldInfoByUse;
	
	ImportDataInfoCSV(long handle, String filePath, FileType fileType, String prefixName) {
		super(handle, filePath, fileType, prefixName);
		m_bIsSetTargetFieldInfoByUse = false;
	}
	
	private void Init()
	{	
		m_sourceFieldInfos.clear();
		m_targetFieldInfos.clear();
		m_importFieldState.clear();
		m_changeName.clear();
		m_changeFieldType.clear();
		
		int count = ImportDataInfoNative.jni_GetFieldCount(getHandle());
		long[] handles = new long[count];
		ImportDataInfoNative.jni_GetSourceFieldInfos(getHandle(), handles);
		for (int i = 0; i < count; i++) {
			FieldInfo fileInfo = InternalFieldInfo.createInstance(handles[i]);
			boolean bCanBeAdd = false;
			if(m_sourceFieldInfos.get(fileInfo.getName()) == null)
			{
				bCanBeAdd = true;
			}
			if(!fileInfo.isSystemField() && !fileInfo.getName().toUpperCase().startsWith("SM") && bCanBeAdd)
			{
				m_sourceFieldInfos.add((FieldInfo)fileInfo.clone() );
				m_targetFieldInfos.add((FieldInfo)fileInfo.clone() );
				m_dictTargetFieldNames.put(fileInfo.getName(), fileInfo.getName());
			}
		}
	}
	/**
	 * ��ȡ����ʸ����������
	 * 
	 * @return ʸ����������
	 */
	public DatasetType getDatasetType() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getDatasetType()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		int ugcDatasetType = ImportDataInfoCSVNative
				.jni_GetDatasetType(getHandle());
		DatasetType datasetType = (DatasetType) InternalEnum.parse(
				DatasetType.class, ugcDatasetType);
		return datasetType;
	}
	
	/**
	 * ���õ���ʸ����������
	 * 
	 * @return 
	 */
	public void setDatasetType(DatasetType dataType){
		
		if (getHandle() == 0) {
			String message = InternalResource.loadString("setDatasetType()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}		
		
		int ndataType = dataType.value();		
		ImportDataInfoCSVNative.jni_SetDatasetType(getHandle(), ndataType);
		
	}

	/**
	 * ��ȡ����ʸ�����ݵ�Ҫ�ظ���
	 * 
	 * @return Ҫ�ظ���
	 */
	public int getRecordCount() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getRecordCount()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return ImportDataInfoCSVNative.jni_GetRecordCount(getHandle());
	}
	
	

	/**
	 * ��ȡԴ�����е��ֶ���Ϣ
	 * 
	 * @return Դ�����е��ֶ���Ϣ
	 */
	
	public FieldInfos getTargetFieldInfos() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"getTargetFieldInfos()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return m_targetFieldInfos;
	}


	/**
	 * ����Ŀ�������е��ֶ���Ϣ
	 * 
	 * @return 
	 */
	@Deprecated
	public void setTargetFieldInfos(FieldInfos fieldinfos) {
		 if (fieldinfos == null) {
	            String message = InternalResource.loadString("fieldInfos",
	                    InternalResource.GlobalInvalidConstructorArgument,
	                    InternalResource.BundleName);
	            throw new IllegalArgumentException(message);
	        }		
		
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"getTargetFieldInfos()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		m_bIsSetTargetFieldInfoByUse = true;
		
		long fieldhandle = InternalFieldInfos.getHandle(fieldinfos);
		// ��fieldInfos���õ�C++��,���JNI����		
		ImportDataInfoCSVNative.jni_SetTargetFieldInfos(getHandle(),
				fieldhandle);
		
		//  ���³�ʼ��
		Init();
	}
	
	/**
	 * ��ȡԴ�����е��ֶ���Ϣ
	 * 
	 * @return Դ�����е��ֶ���Ϣ
	 */
	public FieldInfos getSourceFieldInfos() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"getSourceFieldInfos()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return m_sourceFieldInfos;
	}
	
	/**
	 * �����ֶ�����֮�󣬼�ʱ����TargetFieldInfos����SourceFieldInfos�ǲ����
	 * 
	 * @param oldName
	 * @param newName
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
	 * �����ʹ��һ��HashMap������������Ҫ�ų����ֶ���,��DataImport�н��д������µ��ײ���
	 * 
	 * @param fieldName
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
	
	/**
	 * �����ֶ�����֮�󣬼�ʱ����TargetFieldInfos����SourceFieldInfos�ǲ����
	 * 
	 * @param oldName
	 * @param newName
	 * @return �����Ƿ�ɹ�
	 */
	public boolean changeFieldType(String oldName, FieldType fieldType) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"ChangeFieldName(String oldName, String newName)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return changeFieldType_base(oldName, fieldType);
	}


	public boolean getImportFieldState(String fieldName) {
		if (getHandle() == 0) {
			String message = InternalResource
					.loadString(
							"getImportFieldState(String fieldname)",
							InternalResource.HandleObjectHasBeenDisposed,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return getImportFieldState_base(fieldName);
	}
	
	boolean GetTargetFieldInfosByUse()
	{
		return m_bIsSetTargetFieldInfoByUse;
	}
	
	
	protected void clearHandle() {
		super.clearHandle();
	}


}
