package com.supermap.plugin;

import com.supermap.plugin.LocationManagePlugin.GPSData;

/**
 * λ�ñ仯�����������ڷ������µ�λ����Ϣ
 *
 */
public interface LocationChangedListener {
	/**
	 * λ�ñ仯�ص�
	 * @param oldGps ԭ��λ����Ϣ
	 * @param newGps �µ�λ����Ϣ
	 */
	public void locationChanged(GPSData oldGps,GPSData newGps);
	
	/**
	 * λ�ñ仯�ص�
	 * @param oldGps ԭ��λ����Ϣ
	 * @param newGps �µ�λ����Ϣ
	 * @param isGPSPointValid GPS����Ϣ�Ƿ���Ч
	 */
	public void locationChanged(GPSData oldGps,GPSData newGps,boolean isGPSPointValid);
	
}
