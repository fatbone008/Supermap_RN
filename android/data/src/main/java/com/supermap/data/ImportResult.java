package com.supermap.data;

import java.util.ArrayList;

class ImportResult {
	ImportSetting[] m_succeedSettings;
	
	ArrayList<String[]> m_ArrSucDtNames;
	
	ArrayList<String[]>m_ArrSucMapNames;

	ImportSetting[] m_failedSettings;

	ImportResult(ImportSetting[] succeedSettings, ImportSetting[] failedSettings) {
		m_succeedSettings = succeedSettings;
		m_failedSettings = failedSettings;
	}
	
	ImportResult(ImportSetting[] succeedSettings, ImportSetting[] failedSettings,ArrayList<String[]> succeedDtNames, ArrayList<String[]> succeedMapNames) {
		m_succeedSettings = succeedSettings;
		m_failedSettings = failedSettings;
		m_ArrSucDtNames = succeedDtNames;
		m_ArrSucMapNames = succeedMapNames;
	}

	public ImportSetting[] getSucceedSettings() {
		return m_succeedSettings;
	}

	public ImportSetting[] getFailedSettings() {
		return m_failedSettings;
	}
	
//	// 获取导入后ImportSetting对应的数据集名称数组
//	public String[] getSucceedDatasetNames(ImportSetting succeedSetting)
//	{
//		for(int i = 0; i < m_succeedSettings.length; ++i){
//			if(m_succeedSettings[i] == succeedSetting){
//				if(m_ArrSucDtNames.get(i) != null)
//				{
//					return m_ArrSucDtNames.get(i);
//				}
//			}
//		}
//		return null;
//	}
	
//	// 获取导入后ImportSetting对应的地图名称数组
//	public String[] getSucceedMapNames(ImportSetting succeedSetting)
//	{
//		for(int i = 0; i < m_succeedSettings.length; ++i){
//			if(m_succeedSettings[i] == succeedSetting){
//				if(m_ArrSucMapNames.get(i) != null)
//				{
//					return m_ArrSucMapNames.get(i);
//				}
//			}
//		}
//		return null;
//	}
}
