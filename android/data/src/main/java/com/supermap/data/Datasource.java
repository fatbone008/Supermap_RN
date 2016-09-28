package com.supermap.data;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.Timer;

/**
 * @author 李云锦
 * @version 2.0
 */
public class Datasource extends InternalHandle {
	private Workspace m_workspace;

	private Datasets m_datasets;
	
	private PrjCoordSys m_prjCoordSys;

	private DatasourceConnectionInfo m_datasourceConnectionInfo;

	private static Integer m_lock = new Integer(0);
	
	protected long m_selfEventHandle;
	
//	private static Timer m_Timer = new Timer(5000, null);
//	ActionListener m_Listener = null;
//	boolean m_connected = true;

//	private static Integer m_lock = new Integer(0);

	// add by xuzw 回调函数若引用
//	private long m_selfEventHandle;

	protected Datasource() {
	}

	Datasource(long handle, Workspace workspace) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (workspace == null || workspace.getHandle() == 0) {
			String message = InternalResource.loadString("workspace",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		this.m_workspace = workspace;
		setHandle(handle);

		long ugcHandle = DatasourceNative.jni_GetConnectionInfo(getHandle());
		m_datasourceConnectionInfo = new DatasourceConnectionInfo(this,
				ugcHandle);
		
		m_datasets = new Datasets(this);

		ugcHandle = DatasourceNative.jni_GetPrjCoordSys(getHandle());
		m_prjCoordSys = new PrjCoordSys(ugcHandle, false);
	
		// add by xuzw 2010-04-27
//		m_selfEventHandle = DatasourceNative.jni_NewSelfEventHandle(this);
//
//		if (m_Listener == null) {
//			m_Listener = new ActionListener() {
//
//				public void actionPerformed(ActionEvent arg0) {
//					if (!Datasource.this.connect()) {
//						if (m_connected) {
//							m_connected = false;
//						}
//					} else {
//						m_connected = true;
//					}
//				}
//			};
//
//		}
//		m_Timer.addActionListener(m_Listener);
	}

	public DatasourceConnectionInfo getConnectionInfo() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (m_workspace == null || m_workspace.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasourceTheWorkspaceIsInvalid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return m_datasourceConnectionInfo;
	}

	public Datasets getDatasets() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (m_workspace == null || m_workspace.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasourceTheWorkspaceIsInvalid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return this.m_datasets;
	}
	
	public PrjCoordSys getPrjCoordSys() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (m_workspace == null || m_workspace.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasourceTheWorkspaceIsInvalid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return m_prjCoordSys;
	}

	public String getDescription() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (m_workspace == null || m_workspace.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasourceTheWorkspaceIsInvalid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return DatasourceNative.jni_GetDescription(getHandle());
	}

	public void setDescription(String value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (m_workspace == null || m_workspace.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasourceTheWorkspaceIsInvalid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (value == null) {
			value = "";
		}

		DatasourceNative.jni_SetDescription(getHandle(), value);
	}

//	public boolean isConnected() {
//		if (getHandle() == 0) {
//			String message = InternalResource.loadString("",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//
//		if (m_workspace == null || m_workspace.getHandle() == 0) {
//			String message = InternalResource.loadString("",
//					InternalResource.DatasourceTheWorkspaceIsInvalid,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		return DatasourceNative.jni_GetIsConnected(getHandle());
//	}
	
	public boolean isModified() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (m_workspace == null || m_workspace.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasourceTheWorkspaceIsInvalid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return DatasourceNative.jni_GetIsModified(getHandle());
	}

	/**
	 * 用于连接数据源，连接成功返回 True，否则返回 False。
	 * 
	 * @return boolean
	 */
//	public boolean connect() {
//
//		if (getHandle() == 0) {
//			String message = InternalResource.loadString("",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (m_workspace == null || m_workspace.getHandle() == 0) {
//			String message = InternalResource.loadString("",
//					InternalResource.DatasourceTheWorkspaceIsInvalid,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		
//		boolean bResult = false;
//		synchronized (m_lock) {
//			if (isConnected()) {
//				return true;
//			}
//			
//			bResult = DatasourceNative.jni_Connect(getHandle());
//			
//			if (bResult) {
//				// 先清空m_datasets
////				this.m_datasets.clearHandle();
//				// 重新构造m_datasets
//				this.m_datasets = new Datasets(this);
//			}
//		}
//		
//		return bResult;
//	}
	
	public Dataset copyDataset(Dataset srcDataset, String desDatasetName,
			EncodeType encodeType) {

		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (m_workspace == null || m_workspace.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasourceTheWorkspaceIsInvalid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (this.isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.DatasourceTheDatasourceIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (srcDataset == null || srcDataset.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (desDatasetName == null || desDatasetName.trim().length() == 0) {
			String message = InternalResource.loadString("desDatasetName",
					InternalResource.GlobalStringIsNullOrEmpty,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (!this.getDatasets().isAvailableDatasetName(desDatasetName)) {
			String message = InternalResource.loadString("desDatasetName",
					InternalResource.DatasourceDatasetNameIsInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (!Toolkit
				.isValidEncodeType(Dataset.isVector(srcDataset), encodeType)) {
			String message = InternalResource.loadString("encodeType",
					InternalResource.GlobalUnsupportedEncodeType,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		synchronized (m_lock) {
			int ugEncodeType = encodeType.getUGCValue();
			// 当是CAD数据集时，自动把编码设置为NONE.
			if (srcDataset.getType().equals(DatasetType.CAD)) {
				ugEncodeType = 0;
			}

			//m_senderMethodName = "copyDataset";
			m_selfEventHandle = DatasourceNative.jni_NewSelfEventHandle(this);

			long dtHandle = DatasourceNative.jni_CopyDataset(getHandle(),
					srcDataset.getHandle(), desDatasetName, ugEncodeType,
					m_selfEventHandle);

			this.clearSelfEventHandle();

			if (dtHandle == 0) {
				return null;
			}

			Dataset destDataset = Dataset.createInstance(dtHandle,
					srcDataset.getType(), this);
			this.m_datasets.add(destDataset);
			return destDataset;
		}
	}
	
	public Dataset copyDataset(Dataset srcDataset, String desDatasetName,
			EncodeType encodeType, Charset nCharset) {
	
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (m_workspace == null || m_workspace.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasourceTheWorkspaceIsInvalid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (this.isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.DatasourceTheDatasourceIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (srcDataset == null || srcDataset.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (desDatasetName == null || desDatasetName.trim().length() == 0) {
			String message = InternalResource.loadString("desDatasetName",
					InternalResource.GlobalStringIsNullOrEmpty,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (!this.getDatasets().isAvailableDatasetName(desDatasetName)) {
			String message = InternalResource.loadString("desDatasetName",
					InternalResource.DatasourceDatasetNameIsInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (!Toolkit
				.isValidEncodeType(Dataset.isVector(srcDataset), encodeType)) {
			String message = InternalResource.loadString("encodeType",
					InternalResource.GlobalUnsupportedEncodeType,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		synchronized (m_lock) {
			int ugEncodeType = encodeType.getUGCValue();
			// 当是CAD数据集时，自动把编码设置为NONE.
			if (srcDataset.getType().equals(DatasetType.CAD)) {
				ugEncodeType = 0;
			}
			int ugCharset = nCharset.getUGCValue();

			//m_senderMethodName = "copyDataset";
			m_selfEventHandle = DatasourceNative.jni_NewSelfEventHandle(this);

			long dtHandle = DatasourceNative.jni_CopyDataset2(getHandle(),
					srcDataset.getHandle(), desDatasetName, ugEncodeType, ugCharset,
					m_selfEventHandle);

			this.clearSelfEventHandle();

			if (dtHandle == 0) {
				return null;
			}

			Dataset destDataset = Dataset.createInstance(dtHandle,
					srcDataset.getType(), this);
			this.m_datasets.add(destDataset);
			return destDataset;
		}
	}
	
	protected void clearSelfEventHandle() {
		if (m_selfEventHandle != 0) {
			DatasourceNative.jni_DeleteSelfEventHandle(m_selfEventHandle);
			m_selfEventHandle = 0;
		}
	}
	
	public Workspace getWorkspace() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (m_workspace == null || m_workspace.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasourceTheWorkspaceIsInvalid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return this.m_workspace;
	}

	public String getAlias() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (m_workspace == null || m_workspace.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasourceTheWorkspaceIsInvalid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return DatasourceNative.jni_GetAlias(getHandle());
	}
	
	public boolean isReadOnly() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (m_workspace == null || m_workspace.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasourceTheWorkspaceIsInvalid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasourceNative.jni_GetIsReadOnly(getHandle());
	}
	
	/**
	 * 修改当前数据源的密码
	 * @param oldPassword   原始密码,不能为null
	 * @param newPassword   新密码,不能为null
	 * @param type          新密码使用的加密类型
	 * @return              返回boolean类型，成功返回true;否则返回false
	 */
	public boolean changePassword(String oldPassword, String newPassword, DatasourceEncrytionType type) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"changePassword(String oldPassword, String newPassword)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (oldPassword == null || newPassword == null) {
			String message = InternalResource.loadString("password",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		
		int typeVule = 0;
		if(type != null)
			typeVule = type.getUGCValue();
		
		if(this.getConnectionInfo().getEncrytionType() == DatasourceEncrytionType.AES){
			oldPassword = DatasourceConnectionInfo.formatPassword(oldPassword);
		}
		
		if(type == DatasourceEncrytionType.AES){
			newPassword = DatasourceConnectionInfo.formatPassword(newPassword);
		}
		String tempPassword = this.getConnectionInfo().getPasswordDefault();
		boolean bResult = false;
		if (oldPassword.equals(tempPassword)) {
			bResult = DatasourceNative.jni_ChangePassword(this.getHandle(), newPassword, typeVule);
		}
		return bResult;
	}

}