package com.supermap.plugin;

import java.util.List;

import com.supermap.plugin.LocationManagePlugin.Satellite;

/**
 * λ�ñ仯�����������ڷ������µ�������Ϣ
 *
 */
public interface SatelliteStatusListener {

	/**
	 * ������Ϣ�仯�ص�
	 * 
	 * @param satelliteList   �����б�,���Ի�����������Ϣ{@link com.supermap.plugin.LocationManagePlugin.Satellite}
	 */
	public void onSatelliteStatusChanged(List<Satellite> satelliteList);
}
