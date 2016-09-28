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
 * λ�ù�������Ŀǰ��֧�ִ�GPS�豸�л�ȡ��ǰλ��
 * @attention ʹ�õ�������ʱ����ʹ�ô�������ȡGPSData��������ʵ������Ѳ��
 * 
 */
public  class LocationManagePlugin
{
	public static  class GPSData implements Serializable 
	{
		/**
		 * γ�ȣ���λ�Ƕȡ�
		 */
		public double dLatitude;  				
		/**
		 * ���ȣ���λ�Ƕȡ�
		 */
		public double dLongitude; 				//!< ���ȣ���λ�Ƕȡ�
		/**
		 * �ϱ������ʶ��0��ʾ�ϰ���1��ʾ������
		 */
		public int nNorthing; 
		/**
		 * ���������ʶ��0��ʾ������1��ʾ������
		 */
		public int nEasting;
		/**
		 * GPS״̬��ʶ��0��ʾGPSδ��λ��1��ʾGPS��λ
		 */
		public int nQualityIndicator;            
		/**
		 * ��λģʽ��ʶ��0��ʾGPSδ��λ��1��ʾ2D��λ��2��ʾ3D��λ��
		 */
		public int nFixMode;                    //!< ��λģʽ��ʶ��0��ʾGPSδ��λ��1��ʾ��λ
		/**
		 * �ɼ�������Ŀ��
		 */
		public int nSatellites;                 //!< �ɼ�������Ŀ��
		/**
		 * ��λ�ǣ���ʾ�н��ķ��򣬵�λ�Ƕȡ�����Ϊ0��˳ʱ�뷽��ֵ��Ϊ0-360��
		 */
		public double dBearing;                 //!< ��λ�ǣ���ʾ�н��ķ��򣬵�λ�Ƕȡ�����Ϊ0��˳ʱ�뷽��ֵ��Ϊ0-360��
		/**
		 * �н��ٶȣ���λ����/�롣
		 */
		public double dSpeed;                   //!< �н��ٶȣ���λ����/�롣
		/**
		 * �̣߳��ø���ƽ����ƽ�漴���α�ʾ����λ���ס�
		 */
		public double dAltitude; 				//!< �̣߳��ø���ƽ����ƽ�漴���α�ʾ����λ���ס�
		
		/**
		 * �ꡣ
		 */
		public int nYear;		 				//!< �ꡣ
		/**
		 * �¡�
		 */
		public int nMonth;						//!< �¡�
		/**
		 * �ա�
		 */
		public int nDay;						//!< �ա�
		/**
		 * ʱ��
		 */
		public int nHour;						//!< ʱ��	
		/**
		 * �֡�
		 */
		public int nMinute;						//!< �֡�
		/**
		 * �롣
		 */
		public int nSecond;						//!< �롣
		/**
		 *ʱ��ֵ,����Ϊ��λ��
		 */
		public long lTime;						//!< ʱ��ֵ,����Ϊ��λ��
		
		public double dAccuracy; //��ȷ��
		
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
	 * ��GPS��ȡ����GPS����
	 */
	private GPSData mGPSData = new GPSData();
	
	private   int    nSatellites;   // !< ������Ŀ��
	 
	private   GPSNaviListener  mGPSListener =null; 
	private   LocationManager  mLocMgr = null;
	
	/**
	 * λ�øı�������б�
	 */
	private Vector<LocationChangedListener> mLocationChangedListener = null;
	
	/**
	 * ������Ϣ��ż���
	 */
	private Vector<SatelliteStatusListener> mSatelliteStatusListener = null;
	
	/**
	 * ������Ϣ
	 */
	public static class Satellite {
		/**
		 * ����ID
		 */
		public  int     nSatelliteID;
		/**
		 * ����
		 */
		public  float   nElevation;
		/**
		 * ��λ��
		 */
		public  float   nAzimuth;
		/**
		 * �ź�ǿ��
		 */
		public  float   nSignal;
		/**
		 * �Ƿ�λ
		 */
		public  boolean bFix;
	}
	
	// ʱ����,��λΪ����
    private long mTimerInterval;
    // ����ʱ����
    public void setTimeInterval(long interval){
    	mTimerInterval = interval;
    }
    
    public long getTimeInterval(){
    	return mTimerInterval;
    }
	
	// ������Ϣ
	public   List<Satellite> listStatellites = null; 
	
	public LocationManagePlugin() {
		// ��ʼ��������ÿ��new����
		listStatellites = new ArrayList<Satellite>();
		for (int i = 0; i < 14; i++) {
			listStatellites.add(new Satellite());
		}
		mLocationChangedListener = new Vector<LocationChangedListener>();
		mTimerInterval = 1000L;
		
		mSatelliteStatusListener = new Vector<SatelliteStatusListener>();
	}

	/**
	 * ����GPS�豸
	 * @param loc android ϵͳ��λ�ù�����
	 * @return
	 */
	public boolean openGpsDevice(LocationManager loc) {
		//���ж�GPS�Ƿ����
		if(!loc.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			Log.e("GPSManager", "GPSģ�鲻����!");
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
	 * �ر�GPS�豸
	 */
	public void closeGpsDevice() {

		if (mLocMgr != null) {
			mLocMgr.removeGpsStatusListener(mGPSListener);

			mLocMgr.removeUpdates(mGPSListener);
		}

	}

	/**
	 * ���λ�øı������
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
	 * ���������Ϣ�仯������
	 * @param   satelliteStatusListener  ������Ϣ�仯������
	 * @return  ��ӵļ���Ϊnull������false;���򷵻�true��
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
	 * �Ƴ�ָ����λ�øı������
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
	 * �Ƴ�ָ����������Ϣ�仯������
	 * @param   satelliteStatusListener ������Ϣ�仯������
	 * @return  ����ҵ�ָ����listener���������Ƴ�������true; ���򷵻�false;
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
			// ����ط�����ÿ�ζ�newһ��,������һ��ָ�룬����ĵ�����Ὣǰ��ĵ�Ĳ�������
			// ���¶�λ����(added by hp 2014/10/22)
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
		
			// �켣����Ҫ�õ��ɼ����ʱ�䣬��ʱ����Ϣ������,date.getYear()�ȷ����ѹ�ʱ,���µ�Calendar��ȡʱ��
			// ���Ե�ʱ����,�������Date��ʽ��ȡʱ��Ļ���date.getDay()���ڻ�ȡ������������һ�ܵ����ڣ�������
			// ��ȡ��������һ�µ�ʱ��,����2014/8/22,����,date.getDay()��ȡ��������Ϊ5,����22.Calendar��ȡ������
			// ʱ����ķ�ʽһ��Ҫ�����µķ�ʽ,��Ȼ�����(added by hp 2014/8/22)
			// �������ٽ����ۣ�ʱ���ֶ�ֱ�ӽ�GPS��longֵ����������Ҫת����������ʱ����ȸ�ʽ��
			// ��Ϊ�û���ʱ���Ȳ�ȷ��
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

				// ��ȡGPS������Ϣ
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
	 * GPS�����࣬���ڼ��ɼ������л�ȡ��GPS�Ƿ���Ч
	 */
	private static class GPSFilter{
		/**
		 * ��ǰ��������볬��150��ʱ���ǻ���GPS
		 */
		private final double OutlierTolerance = 150.0;
		private final double COE_180_PI =  0.017453292519943295769236907684886;
		//����뾶
		private final double R_EARTH  = 6371118;
		private final double RTOD = 57.295779513082320876798154814;
		private GPSData mPreData = null;
	    private double m_dAngle = Double.NaN;
	    
	    private boolean m_bInitNode = false;
		
		public boolean isGPSValid(GPSData currentData){
			//  Ҫ�ǵ�һ�λ�ȡ��GPS����Ϊ����Ч��
			if ( !m_bInitNode )
			{	// û�вο��㣬���˵���Ϊ�ο��㣬������Ϊ�ο���ĺ�
				m_bInitNode = true;	
				mPreData = (GPSData) currentData.clone();
				m_dAngle = 1000.0;

				return false;
			}
				//��ǰ����λ�ú�֮ǰ�ο���ľ���
			double dDistTemp = computeDistance( mPreData, currentData );
			if ( isStop(currentData.dSpeed ) )
			{	// ��ֹ��������ƥ�䣬����ǰһ��ƥ���
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
					// dAngle��Χ������-180.0--180.0
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
			{	// Ư�ƣ�������ƥ�䣬ͬʱ�����ο�����Ϣȥ�����´ε�λ��������Ϊ�ο���					
				m_bInitNode = false; 
				mPreData = (GPSData) currentData.clone();
				return false;
			}

		//{{ mahb 2010-3-26 16:38:13 ���ι��Է�����Ч�ĵ㣬����ƥ��㵹���Լ���Ծ��
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
			//���Ȼ���
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
			{//�����ٶ�<=1.11*3.6 = 4.0KM/Hʱ����Ϊ��ֹ
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

