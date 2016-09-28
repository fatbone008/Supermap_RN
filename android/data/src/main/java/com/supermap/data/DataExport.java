package com.supermap.data;

import java.util.ArrayList;
import java.util.Vector;

import com.supermap.data.Charset;
import com.supermap.data.Dataset;
//import com.supermap.data.License;
//import com.supermap.data.ProductType;

class DataExport extends InternalHandleDisposable {
//	private static License m_license;

	static {
//		ArrayList<ProductType> products = InternalToolkiConversion
//				.managerProducts(InternalToolkiConversion
//						.getConversionProducts());
//		m_license = InternalToolkiConversion.verifyLicense(products);
	}

	private ExportSettings m_ExportSettings;

	private long m_selfEventHandle;

	private int m_completed;

	transient Vector m_steppedListeners;

	public DataExport() {
		m_completed = 0;
		m_ExportSettings = new ExportSettings();
		this.setHandle(DataExportNative.jni_New(), true);
	}

	public ExportResult run() {
		verifyLicense();
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("run()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// 注册事件
		m_selfEventHandle = DataExportNative.jni_NewSelfEventHandle(this.getHandle(),this);
		m_completed = 0;
		int count = m_ExportSettings.getCount();
		ArrayList<ExportSetting> m_succedsettingsList = new ArrayList<ExportSetting>();
		ArrayList<ExportSetting> m_failedsettingsList = new ArrayList<ExportSetting>();
		if (count != 0) {

			for (int i = 0; i < count; i++) {
				//验证是否合法
				if (!m_ExportSettings.get(i).check()) {
					m_failedsettingsList.add(m_ExportSettings.get(i));
					continue;
				}

				//通用导出参数
				long objHandle = InternalHandle
						.getHandle(((Dataset) m_ExportSettings.get(i)
								.getSourceData()));
				String targetFilePath = m_ExportSettings.get(i)
						.getTargetFilePath();
				int targetFileTypeValue = m_ExportSettings.get(i)
						.getTargetFileType().value();
				boolean isOverwrite = m_ExportSettings.get(i).isOverwrite();
				Charset ugCharset = m_ExportSettings.get(i).getTargetFileCharset();

				boolean success = false;

				success = DataExportNative.jni_ExportDataNormal(this
						.getHandle(), objHandle, targetFilePath,
						targetFileTypeValue, isOverwrite,ugCharset.value());
				
				m_completed++;
				int percent = m_completed * 100 / count;
				
				ExportSetting currentTask = getExportSettings().get(
						m_completed-1);
				
				ExportSteppedEvent event = new ExportSteppedEvent(this, percent,
						100, currentTask, count, false);
				
				this.fireStepped(event);
				
				if(event.getCancel()) {
					break;
				}
				if (success) {
					m_succedsettingsList.add(m_ExportSettings.get(i));
				} else {
					m_failedsettingsList.add(m_ExportSettings.get(i));

				}
			}
		}
	    
		// 导出结束 清除事件
		this.clearSelfEventHandle();
		
		ExportSetting[] succeedSettings = new ExportSetting[m_succedsettingsList
				.size()];

		ExportSetting[] failedSettings = new ExportSetting[m_failedsettingsList
				.size()];
		m_succedsettingsList.toArray(succeedSettings);
		m_failedsettingsList.toArray(failedSettings);

		return new ExportResult(succeedSettings, failedSettings);

	}

	public ExportSettings getExportSettings() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getExportSettings()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return m_ExportSettings;
	}

	public void setExportSettings(ExportSettings settings) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setExportSettings(ExportSettings settings)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		//TODO clone
		m_ExportSettings = settings;
	}

	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			DataExportNative.jni_Delete(getHandle());
			clearHandle();
		}
	}

	protected void clearHandle() {
		setHandle(0);
		clearSelfEventHandle();
	}

	public synchronized void addExportSteppedListener(ExportSteppedListener l) {
		if (m_steppedListeners == null) {
			m_steppedListeners = new Vector();
		}

		if (!m_steppedListeners.contains(l)) {
			m_steppedListeners.add(l);
		}
	}

	public synchronized void removeExportSteppedListener(ExportSteppedListener l) {
		if (m_steppedListeners != null && m_steppedListeners.contains(l)) {
			m_steppedListeners.remove(l);
		}

	}

	protected void fireStepped(ExportSteppedEvent event) {
		if (m_steppedListeners != null) {
			Vector listeners = m_steppedListeners;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((ExportSteppedListener) listeners.elementAt(i)).stepped(event);
			}
		}
	}

	static void exportSteppedCallBack(DataExport dataExport, int nPercent,
			long cancelHandle) {
	
		int subPercent = nPercent;
		int count = dataExport.getExportSettings().getCount();
		int completed = dataExport.m_completed;
		ExportSetting currentTask = dataExport.getExportSettings().get(
				completed);
		if( completed >= count)
			return ;
		
		int percent = completed * 100 / count;

		Boolean cancel = InternalToolkiConversion
				.getHandleBooleanValue(cancelHandle);

		ExportSteppedEvent event = new ExportSteppedEvent(dataExport, percent,
				subPercent, currentTask, count, cancel);
		dataExport.fireStepped(event);
		InternalToolkiConversion.setHandleBooleanValue(cancelHandle, event
				.getCancel());
		
		
//		InternalHandleDisposable.makeSureNativeObjectLive(dataExport);

		
	}

	private void clearSelfEventHandle() {
		if (m_selfEventHandle != 0) {
			DataExportNative.jni_DeleteSelfEventHandle(m_selfEventHandle);
			m_selfEventHandle = 0;
		}
	}

	private static void verifyLicense() {
//		// 验证一下锁
//		int code = -1;
//		synchronized (m_license) {
//			code = m_license.verify();
//		}
//		if (code != 0) {
//			String message = License.getErrorMessage(code);
//			throw new IllegalStateException(message);
//		}
	}
}
