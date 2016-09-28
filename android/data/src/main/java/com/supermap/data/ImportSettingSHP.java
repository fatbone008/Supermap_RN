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
 * ArcView Shape�ĵ���Ĳ�������
 Shp���ݲ��ܵ����CAD��û��EmptyGeometry

 */
class ImportSettingSHP extends ImportSetting {

	// private ImportDataInfos m_importDataInfos;
	private SpatialIndexInfo m_spatialIndexInfo;

	/**
	 * Ĭ�Ϲ��캯��������һ���µ�ImportSettingMIF����
	 */
	public ImportSettingSHP() {
		long handle = ImportSettingSHPNative.jni_New();
		this.setHandle(handle, true);
		super.setDataType(DataType.VECTOR);
		this.setSpatialIndex(null);
	}

	/**
	 * �������캯��������һ��������Ϣ��ͬ��ImportSettingMIF����
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
	 * ��ȡ�Ƿ����Ϊ3D���ݼ�Ĭ��Ϊfalse ���������Ϊ3D���ݼ�
	 * �������ó�ΪTRUE��ʱ�򣬵����Ϊ3D���ݼ�
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
	 * �����Ƿ����Ϊ3d���ݼ�
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
	public ImportSettingSHP(String sourceFilePath,
			DatasourceConnectionInfo targetConnectionInfo) {
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasourceConnectionInfo(targetConnectionInfo);
//		InternalHandleDisposable.makeSureNativeObjectLive(targetConnectionInfo);
	}

	//	/**
	//	 * SHP���ܵ���ΪCAD
	//	 * 
	//	 * @return �Ƿ�ΪCAD����
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
	//	 * ���õ���ģʽ����Ŀ�����ݼ����ͣ�������ΪCAD���ݼ�, ����Ϊ���ݶ�Ӧ���͵ļ�ʸ�����ݼ�
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
		return ImportSettingSHPNative.jni_IsAttributeIgnored(getHandle());
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
