package com.supermap.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EncodeType;
import com.supermap.data.FieldInfo;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.SpatialIndexInfo;

class ImportSettingTAB extends ImportSetting {

	// private ImportDataInfos m_importDataInfos;
	private SpatialIndexInfo m_spatialIndexInfo;

	/**
	 * Ĭ�Ϲ��캯��������һ���µ�ImportSettingMIF����
	 */
	public ImportSettingTAB() {
		long handle = ImportSettingTABNative.jni_New();
		this.setHandle(handle, true);
		super.setDataType(DataType.VECTOR);
		this.setSpatialIndex(null);
	}

	/**
	 * �������캯��������һ��������Ϣ��ͬ��ImportSettingMIF����
	 * 
	 * @param importSettingMIF
	 */
	public ImportSettingTAB(ImportSettingTAB importSettingTAB) {
		if (importSettingTAB == null) {
			String message = InternalResource.loadString("importSettingTAB",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = InternalHandle.getHandle(importSettingTAB);
		if (handle == 0) {
			String message = InternalResource.loadString("importSettingTAB",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long thisHandle = ImportSettingTABNative.jni_Clone(handle);
		this.setHandle(thisHandle, true);
		this.setTargetDatasourceConnectionInfo(importSettingTAB
				.getTargetDatasourceConnectionInfo());
		this.setTargetDatasource(importSettingTAB.getTargetDatasource());
		this.setTargetPrjCoordSys(importSettingTAB.getTargetPrjCoordSys());
		super.setDataType(DataType.VECTOR);
		this.setSpatialIndex(importSettingTAB.getSpatialIndex());
//		InternalHandleDisposable.makeSureNativeObjectLive(importSettingTAB);
	}

	/**
	 * ͨ��ָ��Դ�ļ�������һ��ImportSettingMIF����
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
	public ImportSettingTAB(String sourceFilePath,
			DatasourceConnectionInfo targetConnectionInfo) {
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasourceConnectionInfo(targetConnectionInfo);
//		InternalHandleDisposable.makeSureNativeObjectLive(targetConnectionInfo);
	}

	public ImportSettingTAB(String sourceFilePath, Datasource targetDatasource) {
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
			String message = InternalResource.loadString("isImportAsCAD()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return ImportSettingTABNative.jni_IsImportingAsCAD(getHandle());
	}

	/**
	 * ���õ���ģʽ����Ŀ�����ݼ����ͣ�������ΪCAD���ݼ�, ����Ϊ���ݶ�Ӧ���͵ļ�ʸ�����ݼ�
	 * 
	 * @param value
	 */
	public void setImportingAsCAD(boolean value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"setImportAsCAD(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		ImportSettingTABNative.jni_SetImportingAsCAD(getHandle(), value);
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
		//long handle = ImportSettingTABNative.jni_GetSpatialIndex(getHandle());
		//return InternalSpatialIndexInfo.createInstance(handle);
		if( m_spatialIndexInfo == null ){
			long handle = ImportSettingTABNative
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
		ImportSettingTABNative.jni_SetSpatialIndex(getHandle(), handle);
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
		return ImportSettingTABNative.jni_IsAttributeIgnored(getHandle());
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
		ImportSettingTABNative.jni_SetAttributeIgnored(getHandle(), value);
	}

//	//���ÿ����ݼ����� deleted by jiangrs ����Ҫ�� 2012/7/13
//	public void setIgnoreNoGeometryDataset(boolean value){
//		if(getHandle()==0){
//			String message = InternalResource.loadString(
//					"setIgnoreNoGeometryDataset(boolean value)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		ImportSettingTABNative.jni_SetIgnoreNoGeometryDataset(getHandle(), value);
//	}
	
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
		return ImportSettingTABNative.jni_GetStyleMapFilePath(getHandle());
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
		
		ImportSettingTABNative.jni_SetStyleMapFilePath(getHandle(), value);
	}
	
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			ImportSettingTABNative.jni_Delete(getHandle());
			clearHandle();
		}
	}

	protected void clearHandle() {
		super.clearHandle();
	}
}
