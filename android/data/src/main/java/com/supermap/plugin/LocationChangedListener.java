package com.supermap.plugin;

import com.supermap.plugin.LocationManagePlugin.GPSData;

/**
 * 位置变化监听器，用于返回最新的位置信息
 *
 */
public interface LocationChangedListener {
	/**
	 * 位置变化回调
	 * @param oldGps 原有位置信息
	 * @param newGps 新的位置信息
	 */
	public void locationChanged(GPSData oldGps,GPSData newGps);
	
	/**
	 * 位置变化回调
	 * @param oldGps 原有位置信息
	 * @param newGps 新的位置信息
	 * @param isGPSPointValid GPS点信息是否有效
	 */
	public void locationChanged(GPSData oldGps,GPSData newGps,boolean isGPSPointValid);
	
}
