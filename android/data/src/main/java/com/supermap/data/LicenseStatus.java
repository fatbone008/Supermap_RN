package com.supermap.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.text.format.DateFormat;

public class LicenseStatus {
	/**
	 * �Ƿ�ע��ɹ�
	 */
	private boolean isRegister = false;
	
	/**
	 * �Ƿ��������
	 */
	private boolean isTrailLicense = false;
	
	@Override
	public String toString() {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String trial = InternalResource.loadString("", InternalResource.TrialLicense, InternalResource.BundleName);
		String formal = InternalResource.loadString("", InternalResource.FormalLicense, InternalResource.BundleName);
		String type = isTrailLicense?trial:formal;
		String status;
		if(isRegister)
		{
			status = InternalResource.loadString("", InternalResource.ValidLicense, InternalResource.BundleName);
		}else{
			if(isLicenseExsit){
				status = InternalResource.loadString("", InternalResource.InvaliLicense, InternalResource.BundleName);
			}else{
				status = InternalResource.loadString("", InternalResource.LicenseNotExsit, InternalResource.BundleName);
			}
		}
		return "LicenseStatus["
				+ "\nStatus = " + status
				+ "\nType = " + type
				+ "\nVersion = " + version
				+ "\nSartDate = " + formater.format(startDate)
				+ "\nExpireDate = " + formater.format(expireDate)
				+ "\n]";
 	}

	/**
	 * �汾
	 */
	private long version = 0;
	
	/**
	 * ��ʼ����
	 */
	private Date startDate = null;
	
	/**
	 * ��ֹ����
	 */
	private Date expireDate = null;
	
	/**
	 * �Ƿ�������
	 */
	private boolean isLicenseExsit = false;

	/**
	 * ����Ƿ���Ч
	 * @return
	 */
	public boolean isLicenseValid() {
		return isRegister;
	}
	
	/**
	 * �Ƿ����������
	 * @return
	 */
	public boolean isTrailLicense() {
		return isTrailLicense;
	}

	/**
	 * ��ȡ��ɰ汾
	 * @return
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * ��ȡ��ɿ�ʼ����
	 * @return
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * ��ȡ�����ֹ����
	 * @return
	 */
	public Date getExpireDate() {
		return expireDate;
	}
	
	/**
	 * �Ƿ�������
	 * @return
	 */
	public boolean isLicenseExsit(){
		return isLicenseExsit;
	}

	LicenseStatus(boolean isRegister, boolean isTrailLicense, long version,
			Date startDate, Date expireDate,boolean isLicenseExsit) {
		super();
		this.isRegister = isRegister;
		this.isTrailLicense = isTrailLicense;
		this.version = version;
		this.startDate = startDate;
		this.expireDate = expireDate;
		this.isLicenseExsit = isLicenseExsit;
	}
	
}
