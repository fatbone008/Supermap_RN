package com.supermap.data;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.NameValueTable;
import android.util.Log;


/**
 * 许可管理类，用于管理当前设备的许可信息
 * 激活许可的方式有3种：
 * 1）申请了文件许可，由用户手动将许可文件拷贝到Environment.getLicense()的目录下
 * 2）通过激活码激活，用户申请到了激活码，输入激活码激活许可
 * 3）通过用户序列号，在线激活许可
 */
public class LicenseManager {
	public static class Module extends Enum{
		protected Module(int value, int ugcValue) {
			super(value, ugcValue);
			// TODO Auto-generated constructor stub
		}
		
		/**
		 * 核心开发模块，包括数据管理、地图浏览和编辑、基础GIS功能等
		 */
		public static Module Core_Dev = new Module(0x01, 0x01);
		
		/**
		 * 核心运行模块，包括数据管理、地图浏览和编辑、基础GIS功能等
		 */
		public static Module Core_Runtime = new Module(0x02, 0x02);
		
//		/**
//		 * 服务模块，包括对Rest地图服务的查询，数据服务的上传和下载等,服务免费
//		 */
//		public static Module Service = new Module(0x02, 0x02);
		
		/**
		 * 导航开发模块，包括路径规划、导航引导等
		 */
		public static Module Navigation_Dev = new Module(0x04, 0x04);
		
		/**
		 * 导航运行模块，包括路径规划、导航引导等
		 */
		public static Module Navigation_Runtime = new Module(0x08, 0x08);
		
		/**
		 * 三维开发模块
		 */
		public static Module Realspace_Dev = new Module(0x10, 0x10);
		
		/**
		 * 三维运行模块
		 */
		public static Module Realspace_Runtime = new Module(0x20, 0x20);
		
		/**
		 * 态势标绘开发模块
		 */
		public static Module Plot_Dev = new Module(0x40, 0x40);
		
		/**
		 * 态势标绘运行模块
		 */
		public static Module Plot_Runtime = new Module(0x80, 0x80);
		
		/**
		 * 获取枚举值，一个枚举值就是一个整数，是这个枚举类中唯一的标示符，内部使用
		 */
		int getEnumValue(){
			return this.getUGCValue();
		}
	}
	
	/**
	 * 用于与服务器交户
	 */
	private HttpClient mClient = new DefaultHttpClient();
	
	/**
	 * 请求
	 */
	private HttpPost mRequest = new HttpPost();
	
	/**
	 * 用于许可激活的地址
	 */
	private final String mUrl = "http://istore.supermap.com.cn/OnlineStore/SMLicenseOperation.ashx";
	//?method=activation&sn=fdsafdsaf&identifyCode=test&module=1|4";
	
	private Vector<LicenseActivationCallback> mActivationCallbacks = new Vector<LicenseActivationCallback>();
	
	private static final int OUT_OF_LIC_COUNT = 12315;
	private static final int ERROR_USER_NO  = 12316;
	private static final int ACTIVE_FAILED = 12317;
	private static final int ACTIVE_SUCCESS = 12318;
	private static final int OTHER_ERRORS = 12319;
	private Handler mHangler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case OUT_OF_LIC_COUNT:
				//许可数量不足
				for(LicenseActivationCallback callback:mActivationCallbacks){
					callback.activateFailed((String)msg.obj);
				}
				break;
			case ERROR_USER_NO:
				//序列号不正确
				for(LicenseActivationCallback callback:mActivationCallbacks){
					callback.activateFailed((String)msg.obj);
				}
				break;
			case ACTIVE_FAILED:
				//激活失败，返回许可状态
				for(LicenseActivationCallback callback:mActivationCallbacks){
					callback.activateFailed((String)msg.obj);
				}
				break;
			case ACTIVE_SUCCESS:
				//激活成功，返回激活后的许可状态
				for(LicenseActivationCallback callback:mActivationCallbacks){
					callback.activateSuccess((LicenseStatus)msg.obj);
				}
				break;
			case OTHER_ERRORS:
				//网络等的错误
				for(LicenseActivationCallback callback:mActivationCallbacks){
					callback.activateFailed((String)msg.obj);
				}
				break;

			default:
				break;
			}
		};
	};
	
		
	
	/**
	 * 请求参数
	 */
	private List<NameValuePair> mRequestParam ;
	
	/**
	 * 获取许可状态
	 * @return 返回当前的许可状态
	 */
	public LicenseStatus getLicenseStatus(){
		return Environment.getLicenseStatus();
	}
	
	/**
	 * 设置激活回调
	 * @param callback 许可激活回调
	 * @return
	 */
	public boolean setActivateCallback(LicenseActivationCallback callback){
		if(mActivationCallbacks.contains(callback)){
			return false;
		}
		mActivationCallbacks.add(callback);
		return true;
	}
	
	/**
	 * 实例
	 */
	private static LicenseManager mInstance = null;
	
	private LicenseManager(){
		mClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 120000); //超时设置
		mClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT,120000); //连接超时
		mRequest.setURI(URI.create(mUrl));
//		mRequest.setHeader("Range","bytes="+"");          // 没有必要的设置， modified by xingjun, 2015.07.08 
		mRequestParam = new ArrayList<NameValuePair>();
	}
	
	/**
	 * 获取许可管理类的实例
	 * @return 许可管理类实例
	 */
	public static LicenseManager getInstance(){
		if(mInstance == null){
			mInstance = new LicenseManager();
		}
		return mInstance;
	}
	
	/**
	 * 在线激活设备
	 * @param userSerialNumber 用户序列号
	 * @param modules 需要申请的模块列表
	 * @return
	 */
	public boolean activateDevice(final String userSerialNumber ,final ArrayList<Module> modules){
		new Thread(){
			public void run() {
				try {
					//构建请求体
					makeRequest(userSerialNumber, "", modules);
					mRequest.setEntity(new UrlEncodedFormEntity(mRequestParam, "utf-8"));
					
					HttpResponse response = mClient.execute(mRequest);
					
					if(response.getStatusLine().getStatusCode() != 200){
						//在此次获取错误信息
						Message msg = mHangler.obtainMessage(OTHER_ERRORS);
						String errorInfo = "Error Code " + response.getStatusLine().getStatusCode();
						msg.obj = errorInfo;
						mHangler.sendMessage(msg);
						return;
					}
					String strRespose = EntityUtils.toString(response.getEntity());
					
					/**
					 * 正常情况返回许可信息；
					 * -1：序列号和模块ID去SERP库无法查出结果
					 * -2：超出申请许可数量；
					 * -5：申请许可以后去更新SERP库失败，没有符合条件的产品
					 */
					if(strRespose.equals("-2")){
						//许可数量越界
						Message msg = mHangler.obtainMessage(OUT_OF_LIC_COUNT);
						String errorInfo = "iMobileError: Out of total license";
						msg.obj = errorInfo;
						mHangler.sendMessage(msg);
						return;
					}else if(strRespose.equals("-1")){
						//用户序列号错误
						Message msg = mHangler.obtainMessage(ERROR_USER_NO);
						String errorInfo = "iMobileError: Invalid User Serial Number";
						msg.obj = errorInfo;
						mHangler.sendMessage(msg);
						return;
					}else if(strRespose.equals("-5")){
						//用户序列号错误
						Message msg = mHangler.obtainMessage(OTHER_ERRORS);
						String errorInfo = "iMobileError: Has no such a product";
						msg.obj = errorInfo;
						mHangler.sendMessage(msg);
						return;
					}
					
					String licCode = makeLicCode(strRespose);
					
					if(Environment.verifyLicenseCode(licCode).isLicenseValid()){
						
						Message msg = null;
						msg = mHangler.obtainMessage(ACTIVE_SUCCESS);
						LicenseStatus licenseStatus = Environment.getLicenseStatus();
						msg.obj = licenseStatus;
						mHangler.sendMessage(msg);
					}else{
						Message msg = mHangler.obtainMessage(ACTIVE_FAILED);
						LicenseStatus licenseStatus = Environment.getLicenseStatus();
						msg.obj = licenseStatus.toString();
						mHangler.sendMessage(msg);
					}
					
				} catch (Exception e) {
					Message msg = mHangler.obtainMessage(OTHER_ERRORS);
					msg.obj = e.toString();
					mHangler.sendMessage(msg);
					return;
				}
			};
		}.start();
		return true;
	}
	
	private void makeRequest(String usrerName,String pwd,ArrayList<Module> modules) throws JSONException{
		//生成请求信息前先清理下
		mRequestParam.clear();
		String strModules = "|";  // 解决有时模块重名判断错误的问题，modified by xingjun, 2015.07.08
		if(!modules.contains(Module.Core_Runtime) && !modules.contains(Module.Core_Dev)){
			throw new IllegalArgumentException("modules must contain Core_Runtime or Core_Dev Module");
		}
		for(Module m:modules){
			//模块名重复的话不加入
			if(strModules.contains("|" + String.valueOf(m.getEnumValue()) + "|")){ 
				continue;
			}
			strModules += m.getEnumValue()+"|";
		}
		strModules = strModules.substring(1,strModules.length()-1);
		
		//激活请求
		mRequestParam.add(new BasicNameValuePair("method", "activation"));
		//加入用户信息
		mRequestParam.add(new BasicNameValuePair("sn", encrypt(usrerName+pwd)));
		//模块信息
		mRequestParam.add(new BasicNameValuePair("module", strModules));
		//模块信息
		mRequestParam.add(new BasicNameValuePair("identifyCode", Environment.getDeviceID()));
		
		//生成硬件信息
		com.supermap.data.Environment.DeviceInfo deviceInfo = Environment.getDeviceInfo();
		//生成原始的硬件信息
		String deviceDes = "Model-"+deviceInfo.Model+
				"|SystemVersion-"+deviceInfo.SystemVersion+
				"|MacAddress-"+deviceInfo.MacAddress+
				"|IMEI-"+deviceInfo.IMEI;
		//设置设备型号
		mRequestParam.add(new BasicNameValuePair("Des", deviceDes));
	}
	
	private String encrypt(String in){
		return in;
	}
	
	private String makeLicCode(String in){
		String licCode ="";// "#UserSerialID#imobile#Modules#7#Version#100#StartDate#20130129#ExpiredDate#20130429#DeviceIDType#MAC#DeviceID#3BDA3399295C49AD#LicCode#A0E9523C646787FF103E811B7EFF8005";
		int start = -1;
		int end = -1;
		String key = "UserSerialID";
		start = in.indexOf(key) + key.length()+1;
		key = "Modules";
		end = in.indexOf(key) -2;//串中含2个字符的换行符
		licCode += "#UserSerialID#"+in.substring(start, end);
		
		start = end + key.length() + 3;
		key = "Version";
		end = in.indexOf(key) -2;//串中含2个字符的换行符
		licCode += "#Modules#"+in.substring(start, end);
		
		start = end + key.length() + 3;
		key = "StartDate";
		end = in.indexOf(key)-2;//串中含2个字符的换行符;
		licCode += "#Version#"+in.substring(start, end);
		
		start = end + key.length() + 3;
		key = "ExpiredDate";
		end = in.indexOf(key)-2;//串中含2个字符的换行符;
		licCode += "#StartDate#"+in.substring(start, end);
		
		start = end + key.length() + 3;
		key = "#";
		end = in.indexOf(key)-2;//串中含2个字符的换行符;
		licCode += "#ExpiredDate#"+in.substring(start, end);
		
		//硬件号
		start = end + key.length() + 2;
		key = "#";
		end = start+16; //16位的硬件码
		//目前使用的是MAC地址的方式去识别的
		licCode += "#DeviceIDType#MAC";
		licCode += "#DeviceID#"+in.substring(start, end);
		
		//许可码
		start = end + 1;
		licCode += "#LicCode#"+in.substring(start,start+32);//32位的许可码
		
		return licCode;
	}
	
	/**
	 * 生成握手信息，服务端接受到这个信息后，扣除数据库中的许可数量
	 * @return
	 */
	private String makeHandInfo(String urserName,String pwd){
		return urserName + pwd;
	}
	
	public interface LicenseActivationCallback{
		public void activateSuccess(LicenseStatus newLicStatus);
		
		public void activateFailed(String errorInfo);
		
	}


	
}
