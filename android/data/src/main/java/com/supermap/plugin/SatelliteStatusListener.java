package com.supermap.plugin;

import java.util.List;

import com.supermap.plugin.LocationManagePlugin.Satellite;

/**
 * 位置变化监听器，用于返回最新的卫星信息
 *
 */
public interface SatelliteStatusListener {

	/**
	 * 卫星信息变化回调
	 * 
	 * @param satelliteList   卫星列表,可以获得卫星相关信息{@link com.supermap.plugin.LocationManagePlugin.Satellite}
	 */
	public void onSatelliteStatusChanged(List<Satellite> satelliteList);
}
