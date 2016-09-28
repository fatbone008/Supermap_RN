package com.supermap.data;

import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EncodeType;
import com.supermap.data.InternalHandle;
import com.supermap.data.InternalHandleDisposable;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;

class ImportSettingWOR extends ImportSetting {
	WorkspaceConnectionInfo m_info;

	Workspace m_workspace;

	public ImportSettingWOR() {
		this.setHandle(ImportSettingWORNative.jni_New(), true);
		super.setDataType(DataType.WOR);
	}

	public ImportSettingWOR(ImportSettingWOR importSettingWOR) {
		this();
		super.setSourceFilePath(importSettingWOR.getSourceFilePath());
		this.setTargetWorkspaceConnectionInfo(importSettingWOR
				.getTargetWorkspaceConnectionInfo());
		super.setTargetDatasourceConnectionInfo(importSettingWOR
				.getTargetDatasourceConnectionInfo());
//		InternalHandleDisposable.makeSureNativeObjectLive(importSettingWOR);

	}

	public ImportSettingWOR(String sourceFilePath,
			DatasourceConnectionInfo targetConnectionInfo,
			WorkspaceConnectionInfo targetWorkspaceConnectionInfo) {
		this();
		super.setSourceFilePath(sourceFilePath);
		this.setTargetWorkspaceConnectionInfo(targetWorkspaceConnectionInfo);
		super.setTargetDatasourceConnectionInfo(targetConnectionInfo);
//		InternalHandleDisposable.makeSureNativeObjectLive(targetConnectionInfo);
//		InternalHandleDisposable.makeSureNativeObjectLive(targetWorkspaceConnectionInfo);
	}

	public ImportSettingWOR(String sourceFilePath, Datasource targetDatasource,
			Workspace targetWorkspace) {
		this();
		super.setSourceFilePath(sourceFilePath);
		this.setTargetWorkspace(targetWorkspace);
		super.setTargetDatasource(targetDatasource);
//		InternalHandleDisposable.makeSureNativeObjectLive(targetWorkspace);
	}

	//	���û��ȡ��������ռ��������Ϣ��
	//	���������ռ������Դ�Ĵ���λ���ǲ����Ƶģ�
	//	�������SuperMap�Ĺ����ռ���֯�ṹ��ͬ��
	public WorkspaceConnectionInfo getTargetWorkspaceConnectionInfo() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getTargetWorkspaceConnectionInfo()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return m_info;
	}

	public void setTargetWorkspaceConnectionInfo(WorkspaceConnectionInfo info) {
		if (this.getHandle() == 0) {
			String message = InternalResource
					.loadString(
							"setTargetWorkspaceConnectionInfo(WorkspaceConnectionInfo info)",
							InternalResource.HandleObjectHasBeenDisposed,
							InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (info == null) {
			if (m_info != null) {
				InternalWorkspaceConnectionInfo.clearHandle(m_info);
				m_info = null;
			}
		} else {
			this.setTargetWorkspace(null);
			m_info = this.cloneWorkspaceConnectionInfo(info);
		}
//		InternalHandleDisposable.makeSureNativeObjectLive(info);
	}

	public Workspace getTargetWorkspace() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getTargetWorkspace()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return this.m_workspace;
	}

	public void setTargetWorkspace(Workspace workspace) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTargetWorkspace(Workspace workspace)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		m_workspace = workspace;
		if (m_workspace != null) {
			this.setTargetWorkspaceConnectionInfo(null);
		}
//		InternalHandleDisposable.makeSureNativeObjectLive(workspace);
	}

	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			ImportSettingWORNative.jni_Delete(getHandle());
			clearHandle();
		}
	}

	@Override
	public ImportDataInfos getTargetDataInfos(String targetNamePrefix) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getSourceDataInfos()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (m_dataInfos == null) {
			m_dataInfos = new ImportDataInfos(this.getHandle(), this
					.getSourceFileType(), DataType.WOR, this
					.getSourceFilePath(), "");
		}
		return m_dataInfos;
	}

	@Override
	public ImportDataInfos getTargetDataInfos(String targetNamePrefix,
			EncodeType targetEncodeType, PrjCoordSys targetPrjCoordSy) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getSourceDataInfos()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (m_dataInfos == null) {
			m_dataInfos = new ImportDataInfos(this.getHandle(), this
					.getSourceFileType(), DataType.WOR, this
					.getSourceFilePath(), "");
		}		
		return m_dataInfos;
	}

	@Override
	//WOR���ͻ���֧������
	public void setTargetDataInfos(ImportDataInfos targetDataInfos) {

		m_dataInfos = targetDataInfos;

	}
	
	@Deprecated
	public PrjCoordSys getTargetPrjCoordSys() {
		return null;
	}
	
	@Deprecated
	public void setTargetPrjCoordSys(PrjCoordSys prjCoordSys) {
	}

	/**
	 * ����Ҫ���ɵ����ݼ��ı�������
	 * @return
	 */
	@Deprecated
	public EncodeType getEncodeType() {
		return null;
	}

	/**
	 * ����Ҫ���ɵ����ݼ��ı�������
	 * @param type
	 */
	@Deprecated
	public void setEncodeType(EncodeType type) {
	}

	/**
	 * �ڲ�ʹ�÷�������������Դ������Ϣ
	 * @param connectionInfo
	 * @return
	 */
	WorkspaceConnectionInfo cloneWorkspaceConnectionInfo(
			WorkspaceConnectionInfo connectionInfo) {
		long handle = InternalHandle.getHandle(connectionInfo);
		long cloneHandle = ImportSettingWORNative
				.jni_CloneWorkspaceConnectionInfo(handle);
		WorkspaceConnectionInfo clone = InternalWorkspaceConnectionInfo
				.createInstance(cloneHandle);
		InternalHandleDisposable.setIsDisposable(clone, true);
//		InternalHandleDisposable.makeSureNativeObjectLive(connectionInfo);
//		InternalHandleDisposable.makeSureNativeObjectLive(clone);
		return clone;
	}

}
