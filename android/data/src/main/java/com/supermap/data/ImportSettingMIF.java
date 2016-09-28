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

	// �ռ�����
	private SpatialIndexInfo m_spatialIndexInfo;
	
	/**
	 * Ĭ�Ϲ��캯��������һ���µ�ImportSettingMIF����
	 */
	public ImportSettingMIF() {
		long handle = ImportSettingMIFNative.jni_New();
        //Modified by Wangbg 2010-9-19
		this.setHandle(handle, true);
		this.setSpatialIndex(null);
		super.setDataType(DataType.VECTOR);
		
	}

	/**
	 * �������캯��������һ��������Ϣ��ͬ��ImportSettingMIF����
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
	 * ͨ��ָ��Դ�ļ�������һ��ImportSettingMIF����
	 * 
	 * @param sourceFilePath
	 *            Դ�ļ�ȫ·��
	 * @param targetConnectionInfo
	 *            Ŀ������Դ������Ϣ
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
	 * ��ȡĿ�����ݼ������Ƿ�ΪCAD���ͣ�Ĭ��Ϊtrue
	 * 
	 * @return �Ƿ�ΪCAD����
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
	 * ���õ���ģʽ����Ŀ�����ݼ����ͣ�������ΪCAD���ݼ�, ����Ϊ���ݶ�Ӧ���͵ļ�ʸ�����ݼ�
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
	 * ��ȡ�ռ��������ͣ���isSpatialIndexAutoBuilt����Ϊtrueʱ��Ч
	 * 
	 * @return �ռ���������
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
	 * ���ÿռ���������
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
		//��ԭ����ֵ��Ϊ��
		m_spatialIndexInfo = null;
//		InternalHandleDisposable.makeSureNativeObjectLive(value);
	}

	/**
	 * ��ȡ�Ƿ��������
	 * 
	 * @return �Ƿ��������
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
	 * �����Ƿ��������
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
	 * ��ȡ������·��
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
	 * ���÷�����·��
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
