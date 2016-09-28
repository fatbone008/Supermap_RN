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
 * ��ɹ����࣬���ڹ���ǰ�豸�������Ϣ
 * ������ɵķ�ʽ��3�֣�
 * 1���������ļ���ɣ����û��ֶ�������ļ�������Environment.getLicense()��Ŀ¼��
 * 2��ͨ�������뼤��û����뵽�˼����룬���뼤���뼤�����
 * 3��ͨ���û����кţ����߼������
 */
public class LicenseManager {
	public static class Module extends Enum{
		protected Module(int value, int ugcValue) {
			super(value, ugcValue);
			// TODO Auto-generated constructor stub
		}
		
		/**
		 * ���Ŀ���ģ�飬�������ݹ�����ͼ����ͱ༭������GIS���ܵ�
		 */
		public static Module Core_Dev = new Module(0x01, 0x01);
		
		/**
		 * ��������ģ�飬�������ݹ�����ͼ����ͱ༭������GIS���ܵ�
		 */
		public static Module Core_Runtime = new Module(0x02, 0x02);
		
//		/**
//		 * ����ģ�飬������Rest��ͼ����Ĳ�ѯ�����ݷ�����ϴ������ص�,�������
//		 */
//		public static Module Service = new Module(0x02, 0x02);
		
		/**
		 * ��������ģ�飬����·���滮������������
		 */
		public static Module Navigation_Dev = new Module(0x04, 0x04);
		
		/**
		 * ��������ģ�飬����·���滮������������
		 */
		public static Module Navigation_Runtime = new Module(0x08, 0x08);
		
		/**
		 * ��ά����ģ��
		 */
		public static Module Realspace_Dev = new Module(0x10, 0x10);
		
		/**
		 * ��ά����ģ��
		 */
		public static Module Realspace_Runtime = new Module(0x20, 0x20);
		
		/**
		 * ̬�Ʊ�濪��ģ��
		 */
		public static Module Plot_Dev = new Module(0x40, 0x40);
		
		/**
		 * ̬�Ʊ������ģ��
		 */
		public static Module Plot_Runtime = new Module(0x80, 0x80);
		
		/**
		 * ��ȡö��ֵ��һ��ö��ֵ����һ�������������ö������Ψһ�ı�ʾ�����ڲ�ʹ��
		 */
		int getEnumValue(){
			return this.getUGCValue();
		}
	}
	
	/**
	 * ���������������
	 */
	private HttpClient mClient = new DefaultHttpClient();
	
	/**
	 * ����
	 */
	private HttpPost mRequest = new HttpPost();
	
	/**
	 * ������ɼ���ĵ�ַ
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
				//�����������
				for(LicenseActivationCallback callback:mActivationCallbacks){
					callback.activateFailed((String)msg.obj);
				}
				break;
			case ERROR_USER_NO:
				//���кŲ���ȷ
				for(LicenseActivationCallback callback:mActivationCallbacks){
					callback.activateFailed((String)msg.obj);
				}
				break;
			case ACTIVE_FAILED:
				//����ʧ�ܣ��������״̬
				for(LicenseActivationCallback callback:mActivationCallbacks){
					callback.activateFailed((String)msg.obj);
				}
				break;
			case ACTIVE_SUCCESS:
				//����ɹ������ؼ��������״̬
				for(LicenseActivationCallback callback:mActivationCallbacks){
					callback.activateSuccess((LicenseStatus)msg.obj);
				}
				break;
			case OTHER_ERRORS:
				//����ȵĴ���
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
	 * �������
	 */
	private List<NameValuePair> mRequestParam ;
	
	/**
	 * ��ȡ���״̬
	 * @return ���ص�ǰ�����״̬
	 */
	public LicenseStatus getLicenseStatus(){
		return Environment.getLicenseStatus();
	}
	
	/**
	 * ���ü���ص�
	 * @param callback ��ɼ���ص�
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
	 * ʵ��
	 */
	private static LicenseManager mInstance = null;
	
	private LicenseManager(){
		mClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 120000); //��ʱ����
		mClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT,120000); //���ӳ�ʱ
		mRequest.setURI(URI.create(mUrl));
//		mRequest.setHeader("Range","bytes="+"");          // û�б�Ҫ�����ã� modified by xingjun, 2015.07.08 
		mRequestParam = new ArrayList<NameValuePair>();
	}
	
	/**
	 * ��ȡ��ɹ������ʵ��
	 * @return ��ɹ�����ʵ��
	 */
	public static LicenseManager getInstance(){
		if(mInstance == null){
			mInstance = new LicenseManager();
		}
		return mInstance;
	}
	
	/**
	 * ���߼����豸
	 * @param userSerialNumber �û����к�
	 * @param modules ��Ҫ�����ģ���б�
	 * @return
	 */
	public boolean activateDevice(final String userSerialNumber ,final ArrayList<Module> modules){
		new Thread(){
			public void run() {
				try {
					//����������
					makeRequest(userSerialNumber, "", modules);
					mRequest.setEntity(new UrlEncodedFormEntity(mRequestParam, "utf-8"));
					
					HttpResponse response = mClient.execute(mRequest);
					
					if(response.getStatusLine().getStatusCode() != 200){
						//�ڴ˴λ�ȡ������Ϣ
						Message msg = mHangler.obtainMessage(OTHER_ERRORS);
						String errorInfo = "Error Code " + response.getStatusLine().getStatusCode();
						msg.obj = errorInfo;
						mHangler.sendMessage(msg);
						return;
					}
					String strRespose = EntityUtils.toString(response.getEntity());
					
					/**
					 * ����������������Ϣ��
					 * -1�����кź�ģ��IDȥSERP���޷�������
					 * -2�������������������
					 * -5����������Ժ�ȥ����SERP��ʧ�ܣ�û�з��������Ĳ�Ʒ
					 */
					if(strRespose.equals("-2")){
						//�������Խ��
						Message msg = mHangler.obtainMessage(OUT_OF_LIC_COUNT);
						String errorInfo = "iMobileError: Out of total license";
						msg.obj = errorInfo;
						mHangler.sendMessage(msg);
						return;
					}else if(strRespose.equals("-1")){
						//�û����кŴ���
						Message msg = mHangler.obtainMessage(ERROR_USER_NO);
						String errorInfo = "iMobileError: Invalid User Serial Number";
						msg.obj = errorInfo;
						mHangler.sendMessage(msg);
						return;
					}else if(strRespose.equals("-5")){
						//�û����кŴ���
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
		//����������Ϣǰ��������
		mRequestParam.clear();
		String strModules = "|";  // �����ʱģ�������жϴ�������⣬modified by xingjun, 2015.07.08
		if(!modules.contains(Module.Core_Runtime) && !modules.contains(Module.Core_Dev)){
			throw new IllegalArgumentException("modules must contain Core_Runtime or Core_Dev Module");
		}
		for(Module m:modules){
			//ģ�����ظ��Ļ�������
			if(strModules.contains("|" + String.valueOf(m.getEnumValue()) + "|")){ 
				continue;
			}
			strModules += m.getEnumValue()+"|";
		}
		strModules = strModules.substring(1,strModules.length()-1);
		
		//��������
		mRequestParam.add(new BasicNameValuePair("method", "activation"));
		//�����û���Ϣ
		mRequestParam.add(new BasicNameValuePair("sn", encrypt(usrerName+pwd)));
		//ģ����Ϣ
		mRequestParam.add(new BasicNameValuePair("module", strModules));
		//ģ����Ϣ
		mRequestParam.add(new BasicNameValuePair("identifyCode", Environment.getDeviceID()));
		
		//����Ӳ����Ϣ
		com.supermap.data.Environment.DeviceInfo deviceInfo = Environment.getDeviceInfo();
		//����ԭʼ��Ӳ����Ϣ
		String deviceDes = "Model-"+deviceInfo.Model+
				"|SystemVersion-"+deviceInfo.SystemVersion+
				"|MacAddress-"+deviceInfo.MacAddress+
				"|IMEI-"+deviceInfo.IMEI;
		//�����豸�ͺ�
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
		end = in.indexOf(key) -2;//���к�2���ַ��Ļ��з�
		licCode += "#UserSerialID#"+in.substring(start, end);
		
		start = end + key.length() + 3;
		key = "Version";
		end = in.indexOf(key) -2;//���к�2���ַ��Ļ��з�
		licCode += "#Modules#"+in.substring(start, end);
		
		start = end + key.length() + 3;
		key = "StartDate";
		end = in.indexOf(key)-2;//���к�2���ַ��Ļ��з�;
		licCode += "#Version#"+in.substring(start, end);
		
		start = end + key.length() + 3;
		key = "ExpiredDate";
		end = in.indexOf(key)-2;//���к�2���ַ��Ļ��з�;
		licCode += "#StartDate#"+in.substring(start, end);
		
		start = end + key.length() + 3;
		key = "#";
		end = in.indexOf(key)-2;//���к�2���ַ��Ļ��з�;
		licCode += "#ExpiredDate#"+in.substring(start, end);
		
		//Ӳ����
		start = end + key.length() + 2;
		key = "#";
		end = start+16; //16λ��Ӳ����
		//Ŀǰʹ�õ���MAC��ַ�ķ�ʽȥʶ���
		licCode += "#DeviceIDType#MAC";
		licCode += "#DeviceID#"+in.substring(start, end);
		
		//�����
		start = end + 1;
		licCode += "#LicCode#"+in.substring(start,start+32);//32λ�������
		
		return licCode;
	}
	
	/**
	 * ����������Ϣ������˽��ܵ������Ϣ�󣬿۳����ݿ��е��������
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
