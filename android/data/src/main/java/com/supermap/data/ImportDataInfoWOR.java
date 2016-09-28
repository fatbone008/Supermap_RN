package com.supermap.data;

import java.util.Hashtable;
import java.util.Map;

import com.supermap.data.Rectangle2D;

class ImportDataInfoWOR extends ImportDataInfo {
	Map<String, String> m_datasetNames;

	Map<String, String> m_mapNames;

	String m_path;

	ImportDataInfoWOR(String[] datasetNames, String[] mapNames, String path) {
		m_datasetNames = new Hashtable<String, String>();
		for (int i = 0; i < datasetNames.length; i++) {
			m_datasetNames.put(datasetNames[i], datasetNames[i]);
		}
		m_mapNames = new Hashtable<String, String>();
		for (int i = 0; i < mapNames.length; i++) {
			m_mapNames.put(mapNames[i], mapNames[i]);
		}
		this.m_path = path;
	}

	//	//获取原数据集名称数组
	//	public String[] getDatasetNames() {
	//		return this.m_datasetNames;
	//	}

	//	//	/获取原地图名称数组
	//	public String[] getMapNames() {
	//		return this.m_mapNames;
	//	}
	public Map<String, String> getDatasetNames() {
        return this.m_datasetNames;
	}

	public void setDatasetNames(Map<String, String> value) {
          this.m_datasetNames = value;
	}

	public Map<String, String> getMapNames() {
         return this.m_mapNames;
	}

	public void setMapNames(Map<String, String> value) {
         this.m_mapNames = value;
	}

	/**
	 * 获取导入数据的地理范围
	 * @return
	 */
	public Rectangle2D getBounds() {
		return null;
	}

	/**
	 * 获取结果数据集的名称
	 * @return
	 */
	public String getTargetName() {
		return null;
	}

	/**
	 * 设置结果数据集的名称
	 * @param targetName
	 */
	public void setTargetName(String targetName) {

	}

	/**
	 * 获取当前数据集信息对应的源文件
	 * @return
	 */
	public String getSourceFile() {
		return m_path;
	}
	
	@Deprecated
	public String getSrouceFile() {
		return m_path;
	}
}
