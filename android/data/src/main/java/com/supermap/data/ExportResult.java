package com.supermap.data;

class ExportResult {
	ExportSetting[] m_succeedSettings;

	ExportSetting[] m_failedSettings;

	ExportResult(ExportSetting[] succeedSettings, ExportSetting[] failedSettings) {
		m_succeedSettings = succeedSettings;
		m_failedSettings = failedSettings;
	}

	public ExportSetting[] getSucceedSettings() {
		return m_succeedSettings;
	}

	public ExportSetting[] getFailedSettings() {
		return m_failedSettings;
	}
}