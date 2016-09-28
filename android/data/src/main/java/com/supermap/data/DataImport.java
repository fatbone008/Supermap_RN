package com.supermap.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

//import com.supermap.data.License;
//import com.supermap.data.ProductType;

class DataImport extends InternalHandleDisposable {
//	private static License m_license;

	static {
//		ArrayList<ProductType> products = InternalToolkiConversion
//				.managerProducts(InternalToolkiConversion
//						.getConversionProducts());
//		m_license = InternalToolkiConversion.verifyLicense(products);
	}
	private ImportSettings m_importSettings;

	transient Vector m_steppedListeners;

	private int m_completed;

	private long m_selfEventHandle;

	public DataImport() {
		m_importSettings = new ImportSettings();
		m_completed = 0;
		this.setHandle(DataImportNative.jni_New(), true);
	}

	// 1. wsInfo 2.ws 3.dsInfo 4. ds
	// 1.3. 现有处理
	// 1.4. 不合法
	// 2.3. 处理 刷新数据源，刷新数据集。 ,刷新工作空间内的地图
	// 2.4. 处理要求ds属于ws ，刷新ds ,刷新工作空间内的地图

	//
	public ImportResult run() {
		verifyLicense();
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("importData()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// 开启事件
		m_selfEventHandle = DataImportNative.jni_NewSelfEventHandle(this
				.getHandle(), this);
		m_completed = 0;
		int count = m_importSettings.getCount();
		ArrayList<ImportSetting> m_succedsettingsList = new ArrayList<ImportSetting>();
		ArrayList<ImportSetting> m_failedsettingsList = new ArrayList<ImportSetting>();
		ArrayList<String[]> m_ArrSucDtNamesList = new ArrayList<String[]>();
		ArrayList<String[]> m_ArrSucMapNamesList = new ArrayList<String[]>();

		for (int i = 0; i < count; i++) {
			ArrayList<Boolean> states = m_importSettings.get(i).getStates();
			if (states == null) {
				states = new ArrayList<Boolean>();
			}
			int[] values = new int[states.size()];
			for (int j = 0; j < values.length; j++) {
				if (states.get(j)) {
					values[j] = 1;
				} else {
					values[j] = 0;
				}
			}
			boolean success = false;
			if (m_importSettings.get(i).getDataType().equals(DataType.WOR)) {
				if ((((ImportSettingWOR) m_importSettings.get(i))
						.getTargetWorkspaceConnectionInfo() != null || ((ImportSettingWOR) m_importSettings
						.get(i)).getTargetWorkspace() != null)
						&& (((ImportSettingWOR) m_importSettings.get(i))
								.getTargetDatasourceConnectionInfo() != null || ((ImportSettingWOR) m_importSettings
								.get(i)).getTargetDatasource() != null)) {
					ImportSettingWOR settingWor = (ImportSettingWOR) m_importSettings
							.get(i);
					ImportDataInfoWOR wor = (ImportDataInfoWOR) settingWor
							.getTargetDataInfos("").get(0);
					String[] datasetsOld = new String[wor.getDatasetNames()
							.size()];
					String[] datasetsNew = new String[wor.getDatasetNames()
							.size()];
					String[] mapOld = new String[wor.getMapNames().size()];
					String[] mapNew = new String[wor.getMapNames().size()];
					Set datasetSet = wor.getDatasetNames().keySet();
					int m = 0;
					if (datasetSet.size() > 0) {
						for (Iterator iter = datasetSet.iterator(); iter
								.hasNext();) {
							datasetsOld[m] = (String) iter.next();
							datasetsNew[m] = wor.getDatasetNames().get(
									datasetsOld[m]);
							++m;
						}
					}
					Set mapSet = wor.getMapNames().keySet();
					int n = 0;
					if (mapSet.size() > 0) {
						for (Iterator iter = mapSet.iterator(); iter.hasNext();) {
							mapOld[n] = (String) iter.next();
							mapNew[n] = wor.getMapNames().get(mapOld[n]);
							++n;
						}
					}

					DataImportNative.jni_SetMapsAndDatasets(InternalHandle
							.getHandle(m_importSettings.get(i)), datasetsOld,
							datasetsNew, mapOld, mapNew);
					// //1.3.，这个不改以前的实现了，以后有时间再重构。
					if (((ImportSettingWOR) m_importSettings.get(i))
							.getTargetWorkspaceConnectionInfo() != null
							&& ((ImportSettingWOR) m_importSettings.get(i))
									.getTargetDatasourceConnectionInfo() != null) {
						// 现用现设，用完释放。防止独占。
						ImportSettingWORNative
								.jni_setWsInfo(
										InternalHandle
												.getHandle(m_importSettings
														.get(i)),
										InternalHandle
												.getHandle(((ImportSettingWOR) m_importSettings
														.get(i))
														.getTargetWorkspaceConnectionInfo()));

						success = DataImportNative.jni_ImportData(this
								.getHandle(), InternalHandle
								.getHandle(m_importSettings.get(i)),
								m_importSettings.get(i).getDataType().value(),
								values,
								InternalHandle.getHandle(m_importSettings
										.get(i)
										.getTargetDatasourceConnectionInfo()));

					}

					// //1.4.不合法 跳过
					// //2.3. 处理 刷新数据源，刷新数据集。 刷新工作空间内的地图
					if (((ImportSettingWOR) m_importSettings.get(i))
							.getTargetWorkspace() != null
							&& ((ImportSettingWOR) m_importSettings.get(i))
									.getTargetDatasourceConnectionInfo() != null) {
						success = DataImportNative
								.jni_ImportData2(
										this.getHandle(),
										InternalHandle
												.getHandle(m_importSettings
														.get(i)),
										InternalHandle
												.getHandle(((ImportSettingWOR) m_importSettings
														.get(i))
														.getTargetWorkspace()),
										InternalHandle
												.getHandle(m_importSettings
														.get(i)
														.getTargetDatasourceConnectionInfo()));

						// TODO 刷新
						InternalWorkspace
								.refresh(((ImportSettingWOR) m_importSettings
										.get(i)).getTargetWorkspace());
					}
					// /2.4.处理要求ds属于ws ，刷新数据集 ,刷新工作空间内的地图
					if (((ImportSettingWOR) m_importSettings.get(i))
							.getTargetWorkspace() != null
							&& ((ImportSettingWOR) m_importSettings.get(i))
									.getTargetDatasource() != null) {
						int count2 = ((ImportSettingWOR) m_importSettings
								.get(i)).getTargetWorkspace().getDatasources()
								.getCount();
						for (int j = 0; j < count2; j++) {
							if (((ImportSettingWOR) m_importSettings.get(i))
									.getTargetWorkspace().getDatasources().get(
											j).equals(
											m_importSettings.get(i)
													.getTargetDatasource())) {
								success = true;
								break;
							}
						}
						if (success) {
							success = DataImportNative
									.jni_ImportData3(
											this.getHandle(),
											InternalHandle
													.getHandle(m_importSettings
															.get(i)),
											InternalHandle
													.getHandle(((ImportSettingWOR) m_importSettings
															.get(i))
															.getTargetWorkspace()),
											InternalHandle
													.getHandle(m_importSettings
															.get(i)
															.getTargetDatasource()));

							// TODO 刷新
							InternalWorkspace
									.refresh(((ImportSettingWOR) m_importSettings
											.get(i)).getTargetWorkspace());
						}
					}
				}
			} else {
				if (m_importSettings.get(i).getTargetDatasourceConnectionInfo() != null) {
					success = DataImportNative.jni_ImportData(this.getHandle(),
							InternalHandle.getHandle(m_importSettings.get(i)),
							m_importSettings.get(i).getDataType().value(),
							values,
							InternalHandle.getHandle(m_importSettings.get(i)
									.getTargetDatasourceConnectionInfo()));
				} else if (m_importSettings.get(i).getTargetDatasource() != null) {
					success = DataImportNative.jni_ImportData4(
							this.getHandle(), InternalHandle
									.getHandle(m_importSettings.get(i)),
							m_importSettings.get(i).getDataType().value(),
							values, InternalHandle.getHandle(m_importSettings
									.get(i).getTargetDatasource()));
					//modified by huangkj 组件层的数据集集合在这里同步类库
					if(success){
						m_importSettings.get(i).getTargetDatasource().getDatasets().reset();
					}
					// TODO 刷新
					InternalDatasource.refresh(m_importSettings.get(i)
							.getTargetDatasource());
				}
			}
			m_completed++;
			int percent = m_completed * 100 / count;
			ImportSetting currentTask = getImportSettings()
					.get(m_completed - 1);
			
			ImportSteppedEvent event = new ImportSteppedEvent(this, percent,
					100, currentTask, count, false);
			
			this.fireStepped(event);
			
			if (event.getCancel()) {
				break;
			}
			if (success) {
				m_succedsettingsList.add(m_importSettings.get(i));
//				m_ArrSucDtNamesList.add(getSucImportDtNames());
//				m_ArrSucMapNamesList.add(getSucImportMapNames());
				
			} else {
				m_failedsettingsList.add(m_importSettings.get(i));

			}
		}
		//  导入完成关闭事件
		this.clearSelfEventHandle();
		
		ImportSetting[] succeedSettings = new ImportSetting[m_succedsettingsList
				.size()];
		ImportSetting[] failedSettings = new ImportSetting[m_failedsettingsList
				.size()];
		m_succedsettingsList.toArray(succeedSettings);
		m_failedsettingsList.toArray(failedSettings);

		return new ImportResult(succeedSettings, failedSettings, m_ArrSucDtNamesList, m_ArrSucMapNamesList);
	}
	
	private String[] getSucImportDtNames() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getImportSettings()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DataImportNative.jni_GetSucImportDTNames(getHandle());
	}
	
	private String[] getSucImportMapNames() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getImportSettings()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DataImportNative.jni_GetSucImportMapNames(getHandle());
	}

	public ImportSettings getImportSettings() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getImportSettings()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return m_importSettings;
	}

	public void setImportSettings(ImportSettings settings) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setImportSettings(ImportSettings settings)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// TODO CLONE
		m_importSettings = settings;
	}

	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			DataImportNative.jni_Delete(getHandle());
			clearHandle();
		}
	}

	protected void clearHandle() {
		setHandle(0);
		clearSelfEventHandle();
	}

	public synchronized void addImportSteppedListener(ImportSteppedListener l) {
		if (m_steppedListeners == null) {
			m_steppedListeners = new Vector();
		}

		if (!m_steppedListeners.contains(l)) {
			m_steppedListeners.add(l);
		}
	}

	public synchronized void removeImportSteppedListener(ImportSteppedListener l) {
		if (m_steppedListeners != null && m_steppedListeners.contains(l)) {
			m_steppedListeners.remove(l);
		}

	}

	protected void fireStepped(ImportSteppedEvent event) {
		if (m_steppedListeners != null) {
			Vector listeners = m_steppedListeners;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((ImportSteppedListener) listeners.elementAt(i)).stepped(event);
			}
		}
	}

	static void importSteppedCallBack(DataImport dataImport, int nPercent,
			long cancelHandle) {
		
//		int subPercent = nPercent;
//
//		int count = dataImport.getImportSettings().getCount();
//
//		int completed = dataImport.m_completed;
//
//		ImportSetting currentTask = dataImport.getImportSettings().get(
//				completed);
//
//		int percent = completed * 100 / count;
//
//		Boolean cancel = InternalToolkiConversion
//				.getHandleBooleanValue(cancelHandle);
//
//		ImportSteppedEvent event = new ImportSteppedEvent(dataImport, percent,
//				subPercent, currentTask, count, cancel);
//		dataImport.fireStepped(event);
//		InternalToolkiConversion.setHandleBooleanValue(cancelHandle, event
//				.getCancel());
////		InternalHandleDisposable.makeSureNativeObjectLive(dataImport);
	}

	private void clearSelfEventHandle() {
		if (m_selfEventHandle != 0) {
			DataImportNative.jni_DeleteSelfEventHandle(m_selfEventHandle);
			m_selfEventHandle = 0;
		}
	}

	private void verifyLicense() {
//		// 验证一下锁
//		int code = -1;
//		synchronized (m_license) {
//			code = m_license.verify();
//		}
//		if (code != 0) {
//			String message = License.getErrorMessage(code);
//			throw new IllegalStateException(message);
//		}
//		// 判断和设置一下每个类型的许可和是否使用FME
//		int count = m_importSettings.getCount();
//		for(int i = 0; i < count; ++i)
//		{
//			ImportSetting importSetting = m_importSettings.get(i);
//			importSetting.CheakFMELicense();
//		}
	}
}
