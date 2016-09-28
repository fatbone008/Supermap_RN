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
	 * 获取导入矢量数据类型
	 * 
	 * @return 矢量数据类型
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
	 * 设置导入矢量数据类型
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
	 * 获取导入矢量数据的要素个数
	 * 
	 * @return 要素个数
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
	 * 获取源数据中的字段信息
	 * 
	 * @return 源数据中的字段信息
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
	 * 设置目标数据中的字段信息
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
		// 把fieldInfos设置到C++层,添加JNI方法		
		ImportDataInfoCSVNative.jni_SetTargetFieldInfos(getHandle(),
				fieldhandle);
		
		//  重新初始化
		Init();
	}
	
	/**
	 * 获取源数据中的字段信息
	 * 
	 * @return 源数据中的字段信息
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
	 * 设置字段名称之后，及时更新TargetFieldInfos，而SourceFieldInfos是不变的
	 * 
	 * @param oldName
	 * @param newName
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
	 * 组件层使用一个HashMap来保存所有需要排除的字段名,在DataImport中进行处理，更新到底层中
	 * 
	 * @param fieldName
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
	 * 设置字段名称之后，及时更新TargetFieldInfos，而SourceFieldInfos是不变的
	 * 
	 * @param oldName
	 * @param newName
	 * @return 更新是否成功
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
