package com.supermap.data;

import java.util.ArrayList;

class ExportSettings {
	ArrayList<ExportSetting> m_settings;

	public ExportSettings() {
		m_settings = new ArrayList<ExportSetting>();
	}

	public int getCount() {
		return m_settings.size();
	}

	public ExportSetting get(int index) {
		return m_settings.get(index);
	}

	//成功返回索引，失败返回-1
	public int add(ExportSetting exportSetting) {
		//TODO clone
		int result = -1;
		
		//ExportSetting clone = new ExportSetting(exportSetting);
		if (m_settings.add(exportSetting)) {
			result = m_settings.size() - 1;
		}
		return result;

	}

	public boolean insert(int index, ExportSetting ExportSetting) {
		//TODO clone
		int count = m_settings.size();
		m_settings.add(index, ExportSetting);
		return count + 1 == m_settings.size();
	}

	public boolean remove(int index) {
		int count = m_settings.size();
		m_settings.remove(index);
		return count - 1 == m_settings.size();
	}

	public void clear() {
		m_settings.clear();
	}
}
