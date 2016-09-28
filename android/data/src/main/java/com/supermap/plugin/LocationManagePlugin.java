package com.supermap.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.supermap.data.Point2D;

import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 *  
 * 位置管理插件，目前仅支持从GPS设备中获取当前位置
 * @attention 使用导航功能时必须使用此类来获取GPSData来进行真实导航和巡航
 * 
 */
public  class LocationManagePlugin
{
	public static  class GPSData implements Serializable 
	{
		/**
		 * 纬度，单位是度。
		 */
		public double dLatitude;  				
		/**
		 * 经度，单位是度。
		 */
		public double dLongitude; 				//!< 经度，单位是度。
		/**
		 * 南北半球标识。0表示南半球；1表示北半球。
		 */
		public int nNorthing; 
		/**
		 * 东西半球标识。0表示西半球；1表示东半球。
		 */
		public int nEasting;
		/**
		 * GPS状态标识。0表示GPS未定位；1表示GPS定位
		 */
		public int nQualityIndicator;            
		/**
		 * 定位模式标识。0表示GPS未定位；1表示2D定位；2表示3D定位。
		 */
		public int nFixMode;                    //!< 定位模式标识。0表示GPS未定位；1表示定位
		/**
		 * 可见卫星数目。
		 */
		public int nSatellites;                 //!< 可见卫星数目。
		/**
		 * 方位角，表示行进的方向，单位是度。正北为0，顺时针方向，值域为0-360。
		 */
		public double dBearing;                 //!< 方位角，表示行进的方向，单位是度。正北为0，顺时针方向，值域为0-360。
		/**
		 * 行进速度，单位是米/秒。
		 */
		public double dSpeed;                   //!< 行进速度，单位是米/秒。
		/**
		 * 高程，用高于平均海平面即海拔表示。单位是米。
		 */
		public double dAltitude; 				//!< 高程，用高于平均海平面即海拔表示。单位是米。
		
		/**
		 * 年。
		 */
		public int nYear;		 				//!< 年。
		/**
		 * 月。
		 */
		public int nMonth;						//!< 月。
		/**
		 * 日。
		 */
		public int nDay;						//!< 日。
		/**
		 * 时。
		 */
		public int nHour;						//!< 时。	
		/**
		 * 分。
		 */
		public int nMinute;						//!< 分。
		/**
		 * 秒。
		 */
		public int nSecond;						//!< 秒。
		/**
		 *时间值,毫秒为单位。
		 */
		public long lTime;						//!< 时间值,毫秒为单位。
		
		public double dAccuracy; //精确度
		
		@Override
		protected Object clone() {
			// TODO Auto-generated method stub
			GPSData newData = new GPSData();
			newData.dLatitude = this.dLatitude;
			newData.dLongitude = this.dLongitude;
			newData.nNorthing = this.nNorthing;
			newData.nEasting = this.nEasting;
			newData.nQualityIndicator = this.nQualityIndicator;
			newData.nFixMode = this.nFixMode;
			newData.nSatellites = this.nSatellites;
			newData.dBearing = this.dBearing;
			newData.dSpeed = this.dSpeed;
			newData.dAltitude = this.dAltitude;
			newData.nYear = this.nYear;
			newData.nMonth = this.nMonth;
			newData.nDay = this.nDay;
			newData.nHour = this.nHour;
			newData.nMinute = this.nMinute;
			newData.nSecond = this.nSecond;
			newData.lTime = this.lTime;
			newData.dAccuracy = this.dAccuracy;
			return newData;
		}
		
		@Override
		public boolean equals(Object o) {
			// TODO Auto-generated method stub
			if(o instanceof GPSData){
				return false;
			}
			GPSData gps = (GPSData)o;
			if(this.dLatitude == gps.dLatitude && this.dLongitude == gps.dLongitude){
				return true;
			}
				
			return super.equals(o);
		}

	};
	
	/**
	 * 由GPS获取到得GPS数据
	 */
	private GPSData mGPSData = new GPSData();
	
	private   int    nSatellites;   // !< 卫星数目。
	 
	private   GPSNaviListener  mGPSListener =null; 
	private   LocationManager  mLocMgr = null;
	
	/**
	 * 位置改变监听器列表
	 */
	private Vector<LocationChangedListener> mLocationChangedListener = null;
	
	/**
	 * 卫星信息编号监听
	 */
	private Vector<SatelliteStatusListener> mSatelliteStatusListener = null;
	
	/**
	 * 卫星信息
	 */
	public static class Satellite {
		/**
		 * 卫星ID
		 */
		public  int     nSatelliteID;
		/**
		 * 海拔
		 */
		public  float   nElevation;
		/**
		 * 方位角
		 */
		public  float   nAzimuth;
		/**
		 * 信号强度
		 */
		public  float   nSignal;
		/**
		 * 是否定位
		 */
		public  boolean bFix;
	}
	
	// 时间间隔,单位为毫秒
    private long mTimerInterval;
    // 设置时间间隔
    public void setTimeInterval(long interval){
    	mTimerInterval = interval;
    }
    
    public long getTimeInterval(){
    	return mTimerInterval;
    }
	
	// 卫星信息
	public   List<Satellite> listStatellites = null; 
	
	public LocationManagePlugin() {
		// 初始化，避免每次new对象
		listStatellites = new ArrayList<Satellite>();
		for (int i = 0; i < 14; i++) {
			listStatellites.add(new Satellite());
		}
		mLocationChangedListener = new Vector<LocationChangedListener>();
		mTimerInterval = 1000L;
		
		mSatelliteStatusListener = new Vector<SatelliteStatusListener>();
	}

	/**
	 * 开启GPS设备
	 * @param loc android 系统的位置管理器
	 * @return
	 */
	public boolean openGpsDevice(LocationManager loc) {
		//先判断GPS是否可用
		if(!loc.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			Log.e("GPSManager", "GPS模块不可用!");
			return false;
		}

		mGPSListener = new GPSNaviListener();
		mLocMgr = loc;

		if (!mLocMgr.addGpsStatusListener(mGPSListener)) {
			return false;
		}

		mLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, mTimerInterval, 0.0f, mGPSListener);

		return true;
	}
	
	/**
	 * 关闭GPS设备
	 */
	public void closeGpsDevice() {

		if (mLocMgr != null) {
			mLocMgr.removeGpsStatusListener(mGPSListener);

			mLocMgr.removeUpdates(mGPSListener);
		}

	}

	/**
	 * 添加位置改变监听器
	 * @param locationChangedListener
	 * @return
	 */
	public boolean addLocationChangedListener(LocationChangedListener locationChangedListener){
		if(locationChangedListener != null){
			mLocationChangedListener.add(locationChangedListener);
			return true;
		}
		return false;
	}
	
	/**
	 * 添加卫星信息变化监听器
	 * @param   satelliteStatusListener  卫星信息变化监听器
	 * @return  添加的监听为null，返回false;否则返回true。
	 */
	public boolean addSatelliteStatusListener(SatelliteStatusListener satelliteStatusListener){
		if(satelliteStatusListener == null)
			return false;
		if(mSatelliteStatusListener.contains(satelliteStatusListener))
			return true;
		mSatelliteStatusListener.add(satelliteStatusListener);
		return true;
	}
	
	/**
	 * 移除指定的位置改变监听器
	 * @param locationChangedListener 
	 * @return
	 */
	public boolean removeLocationChangedListener(LocationChangedListener locationChangedListener){
		if(mLocationChangedListener.contains(locationChangedListener)){
			mLocationChangedListener.remove(locationChangedListener);
			return true;
		}
		return false;
	}

	/**
	 * 移除指定的卫星信息变化监听器
	 * @param   satelliteStatusListener 卫星信息变化监听器
	 * @return  如果找到指定的listener对象，正常移除，返回true; 否则返回false;
	 */
	public boolean removeSatelliteStatusListener(SatelliteStatusListener satelliteStatusListener){
		
		if(satelliteStatusListener == null)
			return false;
			
		return mSatelliteStatusListener.remove(satelliteStatusListener);
		
	}
	
	/*--------------------GPS Listener-----------------------------------*/
	private class GPSNaviListener implements LocationListener, GpsStatus.Listener {
		private GPSFilter mGpsGilter = null;
		public GPSNaviListener(){
			mGpsGilter = new GPSFilter();
		}

		public void onLocationChanged(Location location) { 
			GPSData oldGData =  mGPSData;
			// 这个地方必须每次都new一个,否则共用一个指针，后面的点进来会将前面的点的参数改了
			// 导致定位错误(added by hp 2014/10/22)
			mGPSData = new GPSData();
			mGPSData.dAltitude  = location.getAltitude();
			mGPSData.dLongitude = location.getLongitude();
			mGPSData.dLatitude  = location.getLatitude();
			mGPSData.dBearing   = location.getBearing();
			mGPSData.dSpeed     = location.getSpeed();
			mGPSData.dAccuracy  = location.getAccuracy();
			//long nTime      = location.getTime();
			//Date date = new Date(nTime);
			//mGPSData.nYear = date.getYear();
			//mGPSData.nMonth = date.getMonth();
			//mGPSData.nDay =  date.getDay();
			//mGPSData.nHour = date.getHours();
			//mGPSData.nMinute = date.getMinutes();
			//mGPSData.nSecond = date.getSeconds();
		
			// 轨迹功能要用到采集点的时间，将时间信息开出来,date.getYear()等方法已过时,用新的Calendar来取时间
			// 测试的时候发现,用上面的Date方式获取时间的话，date.getDay()日期获取到的是属于哪一周的日期，而不是
			// 获取到属于哪一月的时间,比如2014/8/22,周五,date.getDay()获取到的日期为5,不是22.Calendar获取年月日
			// 时分秒的方式一定要用以下的方式,不然会出错(added by hp 2014/8/22)
			// 经过和少杰讨论，时间字段直接将GPS的long值传出来，不要转换成年月日时分秒等格式，
			// 因为用户的时区等不确定
//			Calendar cale = Calendar.getInstance();
//			cale.setTimeInMillis(location.getTime());
//			mGPSData.nYear = cale.get(Calendar.YEAR);
//			mGPSData.nMonth = cale.get(Calendar.MONTH);
//			mGPSData.nDay =  cale.get(Calendar.DAY_OF_MONTH);
//			mGPSData.nHour = cale.get(Calendar.HOUR_OF_DAY);
//			mGPSData.nMinute = cale.get(Calendar.MINUTE);
//			mGPSData.nSecond = cale.get(Calendar.SECOND);
			mGPSData.lTime = location.getTime();
			
			mGPSData.nFixMode = 1;
			mGPSData.nSatellites = nSatellites;
			
			if (mLocationChangedListener != null) {
				for(LocationChangedListener listener:mLocationChangedListener){
					listener.locationChanged(oldGData, mGPSData);
					listener.locationChanged(oldGData, mGPSData,mGpsGilter.isGPSValid(mGPSData));
				}
			}
		}

		public void onProviderDisabled(String provider) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		public void onGpsStatusChanged(int event) {
			UpdateSatellite();
			if(mSatelliteStatusListener != null){
				for(SatelliteStatusListener listener : mSatelliteStatusListener){
					listener.onSatelliteStatusChanged(listStatellites);
				}
			}
		}

		private void UpdateSatellite() {

			int nCount = 0;

			Iterable<GpsSatellite> itGpsStatellites = mLocMgr.getGpsStatus(null).getSatellites();
			Iterator<GpsSatellite> it = itGpsStatellites.iterator();
			while (it.hasNext() && nCount < 14) {

				GpsSatellite gpsS = (GpsSatellite) it.next();
				Satellite satellite = listStatellites.get(nCount++);

				// 获取GPS卫星信息
				satellite.bFix = gpsS.usedInFix();
				satellite.nSatelliteID = gpsS.getPrn();
				satellite.nAzimuth = gpsS.getAzimuth();
				satellite.nElevation = gpsS.getElevation();
				satellite.nSignal = gpsS.getSnr();
			}
			nSatellites = nCount;
		}

	}
	
	/**
	 * GPS过滤类，用于检查采集过程中获取的GPS是否有效
	 */
	private static class GPSFilter{
		/**
		 * 当前后两点距离超过150米时就是坏的GPS
		 */
		private final double OutlierTolerance = 150.0;
		private final double COE_180_PI =  0.017453292519943295769236907684886;
		//地球半径
		private final double R_EARTH  = 6371118;
		private final double RTOD = 57.295779513082320876798154814;
		private GPSData mPreData = null;
	    private double m_dAngle = Double.NaN;
	    
	    private boolean m_bInitNode = false;
		
		public boolean isGPSValid(GPSData currentData){
			//  要是第一次获取到GPS则认为是无效的
			if ( !m_bInitNode )
			{	// 没有参考点，将此点作为参考点，返回作为参考点的宏
				m_bInitNode = true;	
				mPreData = (GPSData) currentData.clone();
				m_dAngle = 1000.0;

				return false;
			}
				//当前车辆位置和之前参考点的距离
			double dDistTemp = computeDistance( mPreData, currentData );
			if ( isStop(currentData.dSpeed ) )
			{	// 静止，不进行匹配，返回前一个匹配点
				if( currentData.dSpeed>0.01 && dDistTemp>0.1)
				{
					if(currentData.equals(mPreData))
					{
						return false;
					}
					double dHeadAngle = computerAzimuth(mPreData, currentData);
					if(Double.isNaN(m_dAngle)){
						m_dAngle = dHeadAngle;
						mPreData = (GPSData) currentData.clone();
						return false;
					}
					double dHeadAngleDif = dHeadAngle - m_dAngle;
					// dAngle范围控制在-180.0--180.0
					if ( dHeadAngleDif<-180.0 )
					{
						dHeadAngleDif += 360.0;
					}
					if ( dHeadAngleDif>180.0)
					{
						dHeadAngleDif -= 360.0;
					}
					if(dHeadAngleDif>-75.0 && dHeadAngleDif<75.0)
					{
						m_dAngle = dHeadAngle;
						mPreData = (GPSData) currentData.clone();
						return false;
					}
					m_dAngle = dHeadAngle;
				}

				mPreData = (GPSData) currentData.clone();

				return true;
			} 
			if ( CheckOutlier( dDistTemp ) )
			{	// 漂移，不进行匹配，同时，将参考点信息去掉，下次点位置重新作为参考点					
				m_bInitNode = false; 
				mPreData = (GPSData) currentData.clone();
				return false;
			}

		//{{ mahb 2010-3-26 16:38:13 屏蔽惯性方向无效的点，避免匹配点倒退以及跳跃。
			double dHead = computerAzimuth(mPreData, currentData);
			double dYawDif = dHead - currentData.dBearing;
			if(dYawDif<-180.0)
			{
				dYawDif += 360.0;
			}
			if(dYawDif>180.0)
			{
				dYawDif -= 360.0;
			}
			if(dYawDif>45.0 || dYawDif<-45.0)
			{
				m_dAngle = dHead;
				mPreData = (GPSData) currentData.clone();				
				return false;
			}
			m_dAngle = dHead;
			mPreData = (GPSData) currentData.clone();
			return true;
		}
		
		private double computeDistance(GPSData preData,GPSData currentData)
		{
			double dx1 = preData.dLongitude*COE_180_PI;
			double dx2 = currentData.dLongitude*COE_180_PI;
			//精度弧度
			double dy1 = preData.dLatitude*COE_180_PI;
			double dy2 = currentData.dLatitude*COE_180_PI;
			double dTemp = Math.sin(dx1)*Math.sin(dx2) + Math.cos(dx1)*Math.cos(dx2)*Math.cos(dy1-dy2);
			if(dTemp<1.0)
			{
				return R_EARTH*(Math.acos(dTemp));
			}

			return 0.0;
		}
		
		private boolean isStop(double dVehicleSpeed )
		{
			if(dVehicleSpeed<1.11 /*|| dDistMoved<5.0*/)
			{//车辆速度<=1.11*3.6 = 4.0KM/H时，认为静止
				return true;
			}
			return false;
		}
		
		private double computerAzimuth(GPSData preData,GPSData currentData)
		{
			double dAngle=0;
			double dDistx=currentData.dLongitude-preData.dLongitude;
			double dDisty=currentData.dLatitude-preData.dLatitude;
			dAngle = Math.atan2(dDisty,dDistx);
			
			dAngle = Math.PI / 2 - dAngle;
			
			if (dAngle < 0)
			{
				dAngle += 2*Math.PI;
			}
			
			return dAngle* RTOD;
		}
		
		private boolean CheckOutlier( double dDistMoved )
		{
			if ( dDistMoved >OutlierTolerance) 
			{
				return true;
			}

			return false;
		}
	}

}

