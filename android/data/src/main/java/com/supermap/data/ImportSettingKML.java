package com.supermap.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EncodeType;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.SpatialIndexInfo;

public class ImportSettingKML extends ImportSetting {
	/**
	 * Ĭ�Ϲ��캯��������һ���µ�ImportSettingKML����
	 * 
	 */
	public ImportSettingKML()
	{
		long handle = ImportSettingKMLNative.jni_New();
		this.setHandle(handle, true);
		super.setDataType(DataType.VECTOR);
		
	}
	/**
	 * �������캯��������һ���µ�ImportSettingKML����
	 * 
	 */	
	public ImportSettingKML(ImportSettingKML importsettingKML)
	{
		if (importsettingKML == null) 
		{
		String message = InternalResource.loadString("ImportSettingKML",
				InternalResource.GlobalArgumentNull,
				InternalResource.BundleName);
		throw new IllegalArgumentException(message);
		}
		long handle = importsettingKML.getHandle();
		if (handle == 0)
		{
			String message = InternalResource.loadString("ImportSettingKML",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long thisHandle = ImportSettingKMLNative.jni_Clone(handle);
		this.setHandle(thisHandle, true);
		this.setTargetDatasourceConnectionInfo(importsettingKML.getTargetDatasourceConnectionInfo());
		this.setTargetDatasource(importsettingKML.getTargetDatasource());
		this.setUnvisibleObjectIgnored(importsettingKML.isUnvisibleObjectIgnored());
		this.setImportingAsCAD(importsettingKML.isImportingAsCAD());
		super.setDataType(DataType.VECTOR);
//		InternalHandleDisposable.makeSureNativeObjectLive(importsettingKML);
	}
	/**
	 * 
	 * @param sourceFilePath
	 *            Դ�ļ�ȫ·��
	 * @param targetConnectionInfo
	 *            Ŀ������Դ������Ϣ
	 */
	public ImportSettingKML(String sourceFilePath,
			DatasourceConnectionInfo targetConnectionInfo) {
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasourceConnectionInfo(targetConnectionInfo);
		super.setDataType(DataType.VECTOR);
//		InternalHandleDisposable.makeSureNativeObjectLive(targetConnectionInfo);
	}	
	public ImportSettingKML(String sourceFilePath, Datasource targetDatasource)
	{
		this();
		this.setSourceFilePath(sourceFilePath);
		this.setTargetDatasource(targetDatasource);
		super.setDataType(DataType.VECTOR);
	}
	/**
	 * 
	 * @param sourceFilePath
	 *            Դ�ļ�ȫ·��
	 * @param targetConnectionInfo
	 *            Ŀ������Դ������Ϣ
	 * @param importingAsCAD
	 *            �Ƿ���CAD��ʽ��������
	 */
	public ImportSettingKML(String sourceFilePath,
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
	 * ��ȡ����ģʽ����Ŀ�����ݼ����ͣ�Ĭ��Ϊtrue��
	 * ������ΪCAD���ݼ�,����Ϊ���ݶ�Ӧ���͵ļ�ʸ�����ݼ�
	 * 
	 * @return �Ƿ���CAD����
	 */
	public boolean isImportingAsCAD() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("isImportingAsCAD()",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return ImportSettingKMLNative.jni_IsImportingAsCAD(getHandle());
	}

	/**
	 * ���õ���ģʽ��true��ʹ��CAD���룬falseʹ�ü����ݼ����͵���
	 * @param value
	 */
	public void setImportingAsCAD(boolean value) 
	{
		if (getHandle() == 0)
		{
			String message = InternalResource.loadString(
					"setImportingAsCAD(boolean value)",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		ImportSettingKMLNative.jni_SetImportingAsCAD(getHandle(), value);
	}
	
	/**
	 * ��ȡ�Ƿ���ɼ������ʶ
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
		return ImportSettingKMLNative.jni_GetUnvisibleObjectIgnored(getHandle());
	}
	/**
	 * �����Ƿ���ɼ������ʶ
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
		ImportSettingKMLNative.jni_SetUnvisibleObjectIgnored(getHandle(),bvisible);
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
			ImportSettingKMLNative.jni_Delete(getHandle());
			this.setHandle(0);
			clearHandle();
		}
	}
	protected void clearHandle()
	{
		super.clearHandle();
	}
}
