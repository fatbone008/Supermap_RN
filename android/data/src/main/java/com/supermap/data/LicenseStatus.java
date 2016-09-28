package com.supermap.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.text.format.DateFormat;

public class LicenseStatus {
	/**
	 * 是否注册成功
	 */
	private boolean isRegister = false;
	
	/**
	 * 是否试用许可
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
	 * 版本
	 */
	private long version = 0;
	
	/**
	 * 开始日期
	 */
	private Date startDate = null;
	
	/**
	 * 终止日期
	 */
	private Date expireDate = null;
	
	/**
	 * 是否包含许可
	 */
	private boolean isLicenseExsit = false;

	/**
	 * 许可是否有效
	 * @return
	 */
	public boolean isLicenseValid() {
		return isRegister;
	}
	
	/**
	 * 是否是试用许可
	 * @return
	 */
	public boolean isTrailLicense() {
		return isTrailLicense;
	}

	/**
	 * 获取许可版本
	 * @return
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * 获取许可开始日期
	 * @return
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * 获取许可终止日期
	 * @return
	 */
	public Date getExpireDate() {
		return expireDate;
	}
	
	/**
	 * 是否存在许可
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
