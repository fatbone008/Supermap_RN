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
 * CSV�ĵ���Ĳ�������
 CSV����ֻ�ܵ���ɵ��CAD���ݼ�
*
 */
class ImportSettingCSV extends ImportSetting {

	// private ImportDataInfos m_importDataInfos;
	private SpatialIndexInfo m_spatialIndexInfo;

	/**
	 * Ĭ�Ϲ��캯��������һ���µ�ImportSettingCVS����
	 */
	public ImportSettingCSV() {
		long handle = ImportSettingCSVNative.jni_New();
		this.setHandle(handle, true);
		super.setDataType(DataType.VECTOR);
		this.setSpatialIndex(null);
	}

	/**
	 * �������캯��������һ��������Ϣ��ͬ��ImportSettingCVS����
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
	 * ͨ��ָ��Դ�ļ�������һ��ImportSettingCSV����
	 * 
	 * @param sourceFilePath
	 *            Դ�ļ�ȫ·��
	 * @param sourceFileType
	 *            Դ�ļ�����
	 * @param targetConnectionInfo
	 *            Ŀ������Դ������Ϣ
	 * @param targetNamePrefix
	 *            Ŀ����������ǰ׺�����ĳ�����͵ļ�д�磺P,L,R�ֱ��Ӧ������
	 */
	public ImportSettingCSV(String sourceFilePath,
			DatasourceConnectionInfo targetConnectionInfo) {
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasourceConnectionInfo(targetConnectionInfo);
//		InternalHandleDisposable.makeSureNativeObjectLive(targetConnectionInfo);
	}

	/**
	 * ��ȡ�ռ�������Ϣ
	 * @return �ռ�������Ϣ
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
		//��ԭ����ֵ��Ϊ��
		m_spatialIndexInfo = null;
//		InternalHandleDisposable.makeSureNativeObjectLive(value);
	}

	/*
	 *  ��ȡ�ı��ļ���ʹ�õķָ���
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
	 *  �����ı��ļ���ʹ�õķָ���
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
	 * ��ȡ��һ���Ƿ�����ֶ�����
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
	 * ���õ�һ���Ƿ�����ֶ�����
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
