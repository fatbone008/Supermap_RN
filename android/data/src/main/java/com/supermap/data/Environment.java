package com.supermap.data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.R.raw;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: SuperMap GIS Technologies Inc.
 * </p>
 * 
 * @author not attributable
 * @version 2.0
 */
public class Environment {
	
	/**
	 * �����ļ�·��
	 */
	private static final String XML_CONFIG_DIRECTORY = android.os.Environment.getExternalStorageDirectory()+"/SuperMap/config/";
	
	/**
	 * �����ͼ���ݴ��·��
	 */
	private static final String WEB_CACHE_DIRECTOTY = android.os.Environment.getExternalStorageDirectory()+"/SuperMap/data/";
	
	/**
	 * ��ʱĿ¼
	 */
	private static final String TEMPORARY_PATH = android.os.Environment.getExternalStorageDirectory()+"/SuperMap/temp/";
	
	/**
	 * Ĭ�ϵ����·��
	 */
	private static final String LICENSE_PATH = android.os.Environment.getExternalStorageDirectory()+"/SuperMap/license/";

	private static String UGCConfigDirectory = "";
	private static String UGCWebCacheDirectory = "";
	private static String UGCTemporaryDirectory = "";
	private static String UGCLicenseDirectory = "";
	private static String UGCFontDirectory = "/system/fonts/";
	private static String UGCODACharMapperInitFile = "/oda/adinit.dat";
	//�����ļ�����
	private static final int CONFIGCOUNT = 5;
	private static final String SUPERMPXML = "SuperMap.xml";
	private static final String PRJCONFIG = "PrjConfig.xml";
	private static final String GRAPHICSMEMFONTS = "GraphicsMemFonts.xml";
	private static final String CODETRANSITION = "CodeTransition.xml";
	private static final String LOGRESOURCE = "resource.xml";
	//������Դ
	private static final String VOICEFILE = "/voice/resource.irf";
	
	private static boolean isSuportSpeeking = false;
	
	/**
	 * Ĭ�ϵ��ж�һ����ֵ�Ƿ�Ϊ0��������
	 * 
	 */
	public static final double DEFAULT_MAX_EQUAL_ZERO_PRECISION = 1e-10;

	/**
	 * Ĭ�ϵ��ж�һ����ֵ�Ƿ�Ϊ0�ĸ�����
	 */
	public static final double DEFAULT_MIN_EQUAL_ZERO_PRECISION = -1e-10;

	private static double maxEqualZeroPrecision = DEFAULT_MAX_EQUAL_ZERO_PRECISION;
	
	private static double minEqualZeroPrecision = DEFAULT_MIN_EQUAL_ZERO_PRECISION;
	
	private static boolean isWrapJLoaded = false;
	
	private static boolean hasInitUGCEnv = false;
	
	private static Context mContext = null;
	
	private static DeviceInfo mDeviceInfo = new DeviceInfo();
	
	private static boolean mIsOpenGLMode = true;
	
	static {
		UGCConfigDirectory = XML_CONFIG_DIRECTORY;
		
		UGCWebCacheDirectory = WEB_CACHE_DIRECTOTY;
		
		UGCTemporaryDirectory = TEMPORARY_PATH;
		
		UGCLicenseDirectory = LICENSE_PATH;
//		Environment.LoadWrapJ();
	}

	// @modified by ������ at 2007��8��6�� ����09ʱ44��27��
	// @reason: �޸�Ϊprotected,��UI���м̳У�ʵ��MapControl��LoadWrapj();
	protected Environment() {
	}
	
	/**
	 * ����һ���ڲ��Ľṹ�壬���ڱ��ֵ�ǰ�豸��һЩӲ����Ϣ
	 */
	static class DeviceInfo{
		/**
		 * �豸�ͺ�
		 */
		public String Model = "";
		
		/**
		 * ϵͳ�汾
		 */
		public String SystemVersion = "";
		
		/**
		 * Mac��ַ
		 */
		public String MacAddress = "";
		
		/**
		 * IMEI,PAD��Ʒû��IMEI����Ҫ���ֻ���
		 */
		public String IMEI = "none";
	}

	/**
	 * �õ��ж�һ����ֵ�Ƿ�Ϊ0��������
	 * 
	 * @return double
	 */
	public static double getMaxEqualZeroPrecision() {
		return maxEqualZeroPrecision;
	}
	
	
	/**
	 * �õ��ж�һ����ֵ�Ƿ�Ϊ0�ĸ�����
	 * 
	 * @return double
	 */
	public static double getMinEqualZeroPrecision() {
		return minEqualZeroPrecision;
	}
	
	/**
	 * ��ȡ��ʱ�ļ�·��
	 * @return ·��
	 * @attention Ĭ��Ϊ/sdcard/SuperMap/temp/,���û�������setTemporaryPath�򷵻��û����õ�ֵ
	 */
	public static String getTemporaryPath(){
		return jni_getTempPath();
	}
	
	/**
	 * ������ʱĿ¼��SuperMap Objects for Android ��ʹ�ù����л����һЩ��ʱ���ݣ�
	 * ʹ�ø÷������Խ���ʱ���ݴ���ڴˣ��Ƽ�ʹ�ð�װ·���µ�cache�ļ���
	 * @param tempPath ��ʱĿ¼
	 * @attention Ĭ��Ϊ/sdcard/SuperMap/temp/
	 */
	public static void setTemporaryPath(String tempPath){
		//����һЩ�û���ʹ�øýӿڵ�ʱ�����·�����ǺϷ���·���������ײ�����֤��
		if(!tempPath.startsWith("/")){
			tempPath = "/" + tempPath; //����Ӹ�·����ʼ
		}
		if(!tempPath.endsWith("/"))
		{
			tempPath = tempPath + "/"; //������"/"����
		}
		//ҪУ��һ�¸�·���Ƿ�Ϸ�
		File file = new File(tempPath);
		
		if(file.exists()){
			if(!file.isDirectory()){
				throw new IllegalArgumentException(tempPath + " is not a correct directory");
			}
		}else{
			//�����ڵĻ����ȴ�����·��,���������ɹ����ǷǷ�·��
			if(!file.mkdirs()){
				throw new IllegalArgumentException(tempPath + " is not a correct directory");
			}
		}
		
		UGCTemporaryDirectory = tempPath;
		if(isWrapJLoaded){
			jni_setTempPath(UGCTemporaryDirectory);
		}
	}
	
	
	/**
	 * �ڲ�ʹ�õĽӿڣ����ڻ�ȡӲ����Ϣ
	 * @return
	 */
	/*package*/ static DeviceInfo getDeviceInfo(){
		if(!hasInitUGCEnv()){
        	throw new IllegalStateException("Please call com.supermap.data.Environment.initialization() firstly");
        }
		return mDeviceInfo;
	}
	/**
	 * ��ȡ�����ļ�·��
	 * @return �����ļ�·��
	 * @attention Ĭ��Ϊ/sdcard/SuperMap/config
	 */
	@Deprecated
	public static String getConfigFileDirectory(){
		return jni_getConfigDir();
	}
	
	/**
	 * ���õ���ģʽ
	 * @attention �����ڵ���ʱʹ�ã�ϵͳ���������ļ�·��getTemporaryPath()ͬ��·���²���log�ļ��У��������ں������������־
	 * @param isDebugMode
	 */
	public static void setDebugMode(boolean isDebugMode){
		jni_SetDebugMode(isDebugMode);
	}
	
	/**
	 * ���������ļ�·�������ڴ��SuperMap Objects for Android ����������ļ�
	 * @param config
	 * @attention Ĭ��Ϊ/sdcard/SuperMap/config
	 */
	@Deprecated
	public static void setConfigFileDirectory(String config){
		//����һЩ�û���ʹ�øýӿڵ�ʱ�����·�����ǺϷ���·���������ײ�����֤��
		if(!config.startsWith("/")){
			config = "/" + config; //����Ӹ�·����ʼ
		}
		if(!config.endsWith("/"))
		{
			config = config + "/"; //������"/"����
		}
		//ҪУ��һ�¸�·���Ƿ�Ϸ�
		File file = new File(config);
		
		if(file.exists()){
			if(!file.isDirectory()){
				throw new IllegalArgumentException(config + " is not a correct directory");
			}
		}else{
			//�����ڵĻ����ȴ�����·��,���������ɹ����ǷǷ�·��
			if(!file.mkdirs()){
				throw new IllegalArgumentException(config + " is not a correct directory");
			}
		}
		//����ӿڷ����ˣ���������
//		UGCConfigDirectory = config;
//		
//		jni_setConfigDir(UGCConfigDirectory);
	}
	
	/**
	 * ��ȡ��·��ͼ�����ŵ�·��
	 * @return ��·��ͼ�����ŵ�·��
	 */
	public static String getWebCacheDirectory(){
		return jni_getWebCacheDir();
	}
	
	/**
	 * ���������ͼ�����ŵ�·��
	 */
	public static void setWebCacheDirectory(String webCache){
		//����һЩ�û���ʹ�øýӿڵ�ʱ�����·�����ǺϷ���·���������ײ�����֤��
		if(!webCache.startsWith("/")){
			webCache = "/" + webCache; //����Ӹ�·����ʼ
		}
		if(!webCache.endsWith("/"))
		{
			webCache = webCache + "/"; //������"/"����
		}
		//ҪУ��һ�¸�·���Ƿ�Ϸ�
		File file = new File(webCache);
		
		if(file.exists()){
			if(!file.isDirectory()){
				throw new IllegalArgumentException(webCache + " is not a correct directory");
			}
		}else{
			//�����ڵĻ����ȴ�����·��,���������ɹ����ǷǷ�·��
			if(!file.mkdirs()){
				throw new IllegalArgumentException(webCache + " is not a correct directory");
			}
		}
		
		UGCWebCacheDirectory = webCache;
		if(isWrapJLoaded){
			jni_setWebCacheDir(webCache);
		}
	}
	/**
	 * �����������·��
	 */
	public static void setFontsPath(String fontsPath){
		if(!hasInitUGCEnv()){
        	throw new IllegalStateException("Please call com.supermap.data.Environment.initialization() firstly");
        }
		//����һЩ�û���ʹ�øýӿڵ�ʱ�����·�����ǺϷ���·���������ײ�����֤��
		if(!fontsPath.startsWith("/")){
			fontsPath = "/" + fontsPath; //����Ӹ�·����ʼ
		}
		if(!fontsPath.endsWith("/"))
		{
			fontsPath = fontsPath + "/"; //������"/"����
		}
		//ҪУ��һ�¸�·���Ƿ�Ϸ�
		File file = new File(fontsPath);
		
		if(file.exists()){
			if(!file.isDirectory()){
				throw new IllegalArgumentException(fontsPath + " is not a correct directory");
			}
		}else{
			//�����ڵĻ����ȴ�����·��,���������ɹ����ǷǷ�·��
			if(!file.mkdirs()){
				throw new IllegalArgumentException(fontsPath + " is not a correct directory");
			}
		}
//		TTFParser ttfParser = new TTFParser();
//		FontsInfoManager fontsManager = new FontsInfoManager();
//		String[] ttfs = file.list();
//		for(String ttf:ttfs){
//			if(ttf.endsWith(".ttf")||ttf.endsWith(".TTF")){
//				try {
//					ttfParser.parse(fontsPath+"/"+ttf);
//					String fontName = ttfParser.getFontName();
//					fontsManager.addFontInfo(fontName, ttf);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				
//			}
//		}
//		fontsManager.save();
		
		UGCFontDirectory = fontsPath;
		if(isWrapJLoaded){
			jni_setFontsPath(fontsPath);
		}
	}
	
	/**
	 * ����JNI��̬��
	 */
	static void LoadWrapJ() {
		if (!isWrapJLoaded) {
			// Androidƽ̨ʹ��
			System.loadLibrary("imb");
						
			isWrapJLoaded = true;
		}		
	}
	
	public static String getDeviceID(){
		if(!hasInitUGCEnv()){
        	throw new IllegalStateException("Please call com.supermap.data.Environment.initialization() firstly");
        }
		return jni_getDeviceID();
	}
	
//	public static String getUDBPassword(String name){
//		if(name == null){
//        	throw new IllegalArgumentException(name + " is null");
//        }
//		
//		return jni_getUDBPassword(name);
//	}
//	
//	private static native String jni_getUDBPassword(String name);
	
	public static String getLicensePath(){
		return jni_getLicensePath();
	}
	
	protected static boolean hasInitUGCEnv() {
		return hasInitUGCEnv;
	}
	
	/**
	 * ��������ļ����·��
	 * @param path
	 */
	public static void setLicensePath(String path){
		//����һЩ�û���ʹ�øýӿڵ�ʱ�����·�����ǺϷ���·���������ײ�����֤��
		if(!path.startsWith("/")){
			path = "/" + path; //����Ӹ�·����ʼ
		}
		if(!path.endsWith("/"))
		{
			path = path + "/"; //������"/"����
		}
		//ҪУ��һ�¸�·���Ƿ�Ϸ�
		File file = new File(path);
		
		if(file.exists()){
			if(!file.isDirectory()){
				throw new IllegalArgumentException(path + " is not a correct directory");
			}
		}else{
			//�����ڵĻ����ȴ�����·��,���������ɹ����ǷǷ�·��
			if(!file.mkdirs()){
				throw new IllegalArgumentException(path + " is not a correct directory");
			}
		}
		UGCLicenseDirectory = path;
		if(isWrapJLoaded){
			jni_setLicensePath(path);
		}
	}
	
	/**
	 * ��ȡmContext,�ڷ����ѯʱʹ��(added by hp 2014/5/10)
	 * @param path
	 */
	static Context getContext(){
		return mContext;
	}
	/**
	 * ��ʼ������
	 * @param context android�����Ļ���
	 * @return ״̬�Ƿ�����
	 * @attention �û����������߳��е��ø÷������������ú�SuperMap Objects for Android�Ļ���
	 */
	public static boolean initialization(Context context){
		if(hasInitUGCEnv){
			return hasInitUGCEnv;
		}
		
		// ���ⲿ��context�������������ѯʱҪʹ��,(2014/5/9 added by hp)
		mContext = context;
		
		//����so��,���հ汾�������������ʶ��            modified by huangkj 2015-11-9
		checkAndLoadLibrary(context);
		

		//��ȡϵͳ���ļ�·��
		UGCConfigDirectory = context.getFilesDir().getAbsolutePath() + "/config/";
		//�ȼ����apk��ѹ������Դ
		checkAPK(context);
		
		//��������ļ��Ƿ����
		if(checkConfigFile()){
			hasInitUGCEnv = true;
		}else{
			//�����ļ���ʧ���߲����ھ����³�asserts���ϴ�һ��
			FileUtil util = new FileUtil();
			util.upLoadConfigFile(context, UGCConfigDirectory);
			
			//�ϴ���ϣ������ڼ���±ȽϺ�
			hasInitUGCEnv = checkConfigFile();
		}
		
	
		
		//���ȼ�����û��豸�����ã�����һЩ������ȡ����		
		checkDevice(context);
		
		jni_JniLoading(context);
		
		// ������ʱĿ¼
		setTemporaryPath(UGCTemporaryDirectory);
		
		// ���������ͼ����·��
		setWebCacheDirectory(UGCWebCacheDirectory);
		
		//����Ĭ�ϵ����·��
		setLicensePath(UGCLicenseDirectory);
		
		
		//�����ļ���ú��ٴ����������ļ�·������֤���õĺ��Զ����õ���ײ�һ��
		if(hasInitUGCEnv){
			jni_setConfigDir(UGCConfigDirectory);
		}else{
			throw new IllegalStateException("Environment initialize Failed");
		}
		
		// ������øú����Գ�ʼ����ǰ�Ļ���
		jni_InitEnvironment();
//		//������ʼ����ɺ������ҵ�������Ϣ�����û�������
//		FontsInfoManager fontsManager = new FontsInfoManager();
//		fontsManager.clear();
//		fontsManager.save();

		//����Ĭ�ϵ�����·��   add by huangkj 2015-11-9
		setFontsPath(UGCFontDirectory);
		
		// ΪUGC����Ӧ�û���Ŀ¼ , added by Xingjun, 2016.06.20
		jni_setAppCachePath(context.getCacheDir().toString());
		
		return hasInitUGCEnv;
	}
	
	private static void checkDevice(Context context){
		//�Ȼ�ȡMAC���ԣ�Ҫ�ǻ�ȡ��������ת�����ý���
		WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		int oldState = wifiMgr.getWifiState();
		//Ҫ��wifi�������򣬽����
		for(int i=0;i<20 && wifiMgr.getWifiState() != WifiManager.WIFI_STATE_ENABLED;i++){
			wifiMgr.setWifiEnabled(true);
			try {
				//�ʱ�������
				Thread.sleep(150);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
		String temp = wifiInfo.getMacAddress();
		if(temp == null){
			//������ܻ�ȡ��Mac��ַ�ǿ��ܾ���ģ������
			temp = "none";
		}
		//Ҫ��ԭ����wifi�ǹرյ��Ǿ͹ر�
		if(oldState == WifiManager.WIFI_STATE_DISABLED){
			
			wifiMgr.setWifiEnabled(false);
		} // �����ֻ���ȫ�����ʾ�û���wifi��wifi��Ȼ���ڹر�״̬����ע�͵����δ��룬
		mDeviceInfo.MacAddress = temp;
		
		TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if(telMgr != null)
		{
			//GSM�ֻ�����IMEI,CDMA���ֻ���������
			temp = telMgr.getDeviceId();
			if(temp == null){
				temp = "none";
			}
		}
		mDeviceInfo.IMEI = temp;
		
		mDeviceInfo.Model = Build.MODEL;
		mDeviceInfo.SystemVersion = Build.VERSION.RELEASE;
		
	}
	
	/**
	 * ��֤����룬�����ֶ����������ע��
	 * @param licCode
	 * @return
	 */
	static LicenseStatus verifyLicenseCode(String licCode)
	{
		String [] errMsg = new String[1];
		errMsg[0] = new String();
		if(jni_VerifyLicenseCode(licCode,errMsg))
		{
			return getLicenseStatus();
		}
		else
		{
			throw new IllegalArgumentException(errMsg[0]);
		}
	}
	
	// ��������ļ��Ƿ����
	private static boolean checkConfigFile(){
		File file = new File(UGCConfigDirectory);
		File wkt = new File(UGCConfigDirectory+"/ProjectionMappingTable/");
		if(file.isDirectory() && wkt.exists()){
			File[] files = file.listFiles();
			int count = 0;
			for(File f:files){
				if(f.getName().equals(SUPERMPXML)
						||f.getName().equals(PRJCONFIG)
						||f.getName().equals(GRAPHICSMEMFONTS)
						||f.getName().equals(CODETRANSITION)
						||f.getName().equals(LOGRESOURCE)){
					count++;
				}
			}
			
			//������Ҫ�������������ļ�
			if(count >= CONFIGCOUNT){
				//���ٶ���ά���е�ͼ�������ļ�
				File bdFile = new File(file.getAbsolutePath()+"/Resource/BMapCitySetting.xml");
				if (!bdFile.exists()) {
					return false;
				}
				
				//�е�����ʱ��Ҫ�ж��Ƿ����������Դ
				if(isSuportSpeeking){
					File voice = new File(file.getAbsolutePath()+VOICEFILE);
					if(!voice.exists()){
						return false;
					}
				}
				
				//���ICU dat
				File icu = new File(file.getParent()+"/icu/icudt50l.dat");
				if(!icu.exists()){
					return false;
				}
				
				File naviRes = new File(file.getAbsolutePath()+"/mapRes/map.jar");
				if(!naviRes.exists()){
					return false;
				}
				
				// �����Դ�汾��
				if (!checkResVersion(file)) {
					return false;
				}
				
				// oda charmapper init file
				File adinit = new File(file.getParent() + UGCODACharMapperInitFile);
				if (!adinit.exists()) {
					return false;
				}
				return true;
			}			
		}
		return false;
	}
	
	/**
	 * ��ȡ���״̬
	 * @return ���ص�ǰ�����״̬
	 */
	public static LicenseStatus getLicenseStatus(){
		if(!hasInitUGCEnv()){
        	throw new IllegalStateException("Please call com.supermap.data.Environment.initialization() firstly");
        }
		
		return readLiceseState();
	}
	
	private static LicenseStatus readLiceseState(){
		boolean isRegister = false;
		boolean isTrailLicense = false;
		boolean isLicenseExsit = false;
		long version = 0;
		Date startDate = new Date();
		Date expireDate = new Date();
		
		//�ȵ��������������û��ִ������Ķ���
		isRegister = jni_IsLicenseValid();
		isLicenseExsit = jni_IsLicenseExsit();
		//���������ļ��������ڵĻ���ֱ�ӷ��ظ�����
		if(!isLicenseExsit)
		{
			return new LicenseStatus(isRegister, isTrailLicense, version, startDate, expireDate, isLicenseExsit);
		}
		
		isTrailLicense = jni_IsTrailLicense();
		version = jni_GetVersion();
		long nStartData = jni_GetStartDate();
		int nYear = (int)nStartData / 10000 ; 
		int nMonth = (int)(nStartData % 10000) / 100;
		int nDay = (int)nStartData % 100;
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR,nYear);
		calendar.set(Calendar.MONTH,nMonth-1);//�·ݴ�0����
		calendar.set(Calendar.DAY_OF_MONTH,nDay);
		startDate = new Date(calendar.getTimeInMillis());
		
		long nEndData = jni_GetExpireDate();
		nYear = (int)nEndData / 10000;
		nMonth = (int)(nEndData % 10000) / 100;
		nDay = (int)nEndData % 100;
		calendar.clear();
		calendar.set(Calendar.YEAR,nYear);
		calendar.set(Calendar.MONTH,nMonth-1);//�·ݴ�0����
		calendar.set(Calendar.DAY_OF_MONTH,nDay);
		expireDate = new Date(calendar.getTimeInMillis());
		
		return new LicenseStatus(isRegister, isTrailLicense, version, startDate, expireDate, isLicenseExsit);
	
	}
	

	// �ڲ�ʹ�ã����ڱ��̳С�
	protected static void InternalLoadWrapJ() {
		LoadWrapJ();
	}
	
	/**
	 * �����жϻ������Ƿ�֧����������
	 */
	protected static boolean isSuportSpeeking(){
		return isSuportSpeeking;
	}
	
	/**
	 * ���apk�е���Դ�ļ�
	 * @return
	 */
	private static void checkAPK(Context context){
		
		boolean isHaveResource = false;
		boolean isHaveLib = false;
		
		// ��������ļ�
		AssetManager assets = context.getAssets();
		String speekingFilePath = "voice";
		String speekingFileName = "resource.mp3";
		String[] voices;
		try {
			voices = assets.list(speekingFilePath);
			for(String str:voices){
				if(str.equals(speekingFileName)){
					isHaveResource = true;
					break;
				}
			}
		} catch (IOException e) {
			isHaveResource = false;
		}
		
		// ���������
		String libs = context.getFilesDir().getAbsolutePath().replace("files", "lib");
		String libAisound = "libAisound.so";
		File libsDir = new File(libs);
		String[] sos = libsDir.list();
		for(String so:sos)
		{
			if(so.equals(libAisound)){
				System.loadLibrary("Aisound");
				isHaveLib = true;
				break;
			}
		}
		
		if (isHaveLib && isHaveResource) {
			isSuportSpeeking = true;
		} else {
			isSuportSpeeking = false;
		}
	}
	
	/**
	 * ��ʼ������ ���õ�ǰ����ΪWindows Linux�������� ��������ã���Graphicsģ����ܻ����bug
	 */
	protected static native void jni_InitEnvironment();
	
	private static native String jni_getTempPath();
	
	private static native void jni_setTempPath(String tempPath);
	
	private static native String jni_getWebCacheDir();
	
	private static native void jni_setWebCacheDir(String webCache);
	
	private static native String jni_getConfigDir();
	
	private static native void jni_setConfigDir(String configDir);
	
	private static native void jni_setFontsPath(String fontsPath);
	
	private static native String jni_getDeviceID();
	
	private static native String jni_getLicensePath();
	
	private static native void jni_setLicensePath(String licPath);
		
	private static native void jni_JniLoading(Context context);
	
	private static native void jni_SetDebugMode(boolean isdebug);
	
	private static native boolean jni_VerifyLicenseCode(String licCode,String[] errMsg);
	
	//��ȡ���״̬��jni����
	private static native boolean jni_IsLicenseValid();
	private static native boolean jni_IsTrailLicense();
	private static native boolean jni_IsLicenseExsit();
	private static native long jni_GetVersion();
	private static native long jni_GetStartDate();
	private static native long jni_GetExpireDate();
	
	private static native boolean jni_IsFiledMapperValid();
	private static native boolean jni_IsGeoSurveyValid();
	
	// ��ȡ������UGC��Ӧ�û���Ŀ¼�����Ŀ¼��Ӧ������ģ�����Ҫ������дȨ��;
    private static native String jni_getAppCachePath();
	// cachePath = Context.getCacheDir();
	private static native void jni_setAppCachePath(String cachePath);
	
	protected static boolean isFiledMapperVaild() {
		return jni_IsFiledMapperValid();
	}
	protected static boolean isGeoSurveryVaild() {
		return jni_IsGeoSurveyValid();
	}
	
//	public static void setSuperMapCopyright(String copyright) {
//		jni_SetSuperMapCopyright(copyright);
//	}
//	
//	private static native void jni_SetSuperMapCopyright(String copyright);
	
	static class FileUtil{
		 public boolean upLoadConfigFile(Context context,String ugcConfig){
			 AssetManager mgr = context.getAssets();
			 try {
				String[] configs;
				 
				 configs = mgr.list("config");
				 
				 if(configs.length<CONFIGCOUNT){
					 throw new IllegalArgumentException("There's no config file in assets directory or config file which in assets directory has lost");
				 }
				 
				 for(String configFile:configs){
					 String path = "config/"+configFile;
					 InputStream fis = mgr.open(path);
					 
					 int end = configFile.indexOf(".ogg");
					 String configXml = ugcConfig+"/"+configFile.substring(0, end);
					 
					 File temp = new File(configXml);
					 
					 copyFile(fis, temp);
				 }
				 
				 //����wkt�������ļ�
				 String[] wkts = null; 
				 wkts = configs = mgr.list("config4wkt");
				 for(String wkt:wkts){
					 String wktPath = "config4wkt/"+wkt;
					 
					 InputStream fis = mgr.open(wktPath);
					 
					 String configCsv = ugcConfig + "ProjectionMappingTable/"+wkt;
					 File temp = new File(configCsv);
					 
					 copyFile(fis, temp);
					 
				 }
				 
				 //������ά�����ļ�
				 copy3DFile(context, ugcConfig);
				 
				//�����ٶȵ�ͼ�����ļ�
				 File bdFile = new File(ugcConfig+"/Resource/BMapCitySetting.xml");
				 InputStream bdConfig = mgr.open("res/BMapCitySetting.xml.ogg");
				 copyFile(bdConfig, bdFile);
				 
				 //������Դ�ļ�
				 File mapResFile = new File(ugcConfig+"/mapRes/map.jar");
				 InputStream mapResIs = mgr.open("mapRes/map.ogg");
				 copyFile(mapResIs, mapResFile);

				 //����ICU
				 InputStream icuDat = mgr.open("icu/icudt50l.dat");
				 File icuPath = new File(context.getFileStreamPath("icu").getAbsoluteFile()+"/icudt50l.dat");
				 copyFile(icuDat, icuPath);

				 //oda init file
				 InputStream odaDat = mgr.open("oda/adinit.dat");
				 File odaPath = new File(context.getFileStreamPath("oda").getAbsoluteFile()+"/adinit.dat");
				 copyFile(odaDat, odaPath);
				 

				 //�е���ģ���ʱ��Ҫ����������Դ
				 if(isSuportSpeeking){
					 //���������ļ�
					 File voiceFile = new File(ugcConfig+VOICEFILE);
					 InputStream voice = mgr.open("voice/resource.mp3");
					 copyFile(voice, voiceFile);
				 }
				 
				 //������Դ�汾���ļ�
				 File resVerFile = new File(ugcConfig+"/ver/res_ver.txt");
				 InputStream resVerIs = mgr.open("ver/res_ver.txt");
				 copyFile(resVerIs, resVerFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 return true;
		 }
		
		 public boolean copyFile(InputStream src,File des){
		    	//Ŀ��·�������ڵĻ��ʹ���һ��
		    	if(!des.getParentFile().exists()){
		    		des.getParentFile().mkdirs();
		    	}
		    	if(des.exists()){
	    			des.delete();
		    	}
		    	
		    	try{
		    		InputStream fis = src;
		    		FileOutputStream fos = new FileOutputStream(des);
		    		//1kb
		    		byte[] bytes = new byte[1024];
		    		int readlength = -1;
		    		while((readlength = fis.read(bytes))>0){
		    			fos.write(bytes, 0, readlength);
		    		}
		    		fos.flush();
		    		fos.close();
		    		fis.close();
		    	}catch(Exception e){
		    		return false;
		    	}
		    	return true;
		    }
		 
		 public boolean copy3DFile(Context context,String ugcConfig){
			 AssetManager mgr = context.getAssets();
			 try {
				String[] filelist = mgr.list("3dRes");
				for (int i = 0; i < filelist.length; i++) {
					if (filelist[i].contains("3dRes.zip")) {
						
						File zipFile = new File(ugcConfig+"/Resource/3dRes.zip");
						InputStream res3d = mgr.open("3dRes/3dRes.zip");
						// ����ѹ���ļ�
						if (copyFile(res3d, zipFile)) {
							Decompressor.UnZipFolder(ugcConfig+"/Resource/3dRes.zip", ugcConfig+"/Resource/");
							
							if(zipFile.exists())  
							{  
								zipFile.delete();
							} 
						}
						
						return true;
					}
				}
				
			 } catch (IOException e) {
		 		// TODO Auto-generated catch block
				e.printStackTrace();
			 }
			 
			 return false;
		 }		 
	}
	
//	private static class FontsInfoManager{
//		private File mFontsInfo = null;
//		private String xmlHeader = "<?xml version=\"1.0\" encoding=\"uft-8\"?> \r\n"+
//								   "<fontsname version=\"20071213\" Description=\"UGC setting file Created by SuperMap UGC 6.0\"> \r\n";
//		private String xmlEnd = "</fontsname>";
//		
//		private RandomAccessFile mFontsInfoFile;
//		
//		private boolean isOpen = false;
//		
//		private HashMap<String, String> mWriterBuffer = new HashMap<String, String>();
//		
//		public FontsInfoManager() {
//			init();
//		}
//		
//		private void init(){
//			String fontsXML = jni_getConfigDir()+"/" + GRAPHICSMEMFONTS;
// 
//			mFontsInfo = new File(fontsXML);
//			
//			try {
//				mFontsInfoFile = new RandomAccessFile(mFontsInfo, "rw");
//				isOpen = true;
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		public boolean addFontInfo(String fontName,String fontFileName){
//			if(mWriterBuffer.containsKey(fontName) || mWriterBuffer.containsValue(fontFileName) || !isOpen){
//				return false;
//			}
//			mWriterBuffer.put(fontName, fontFileName);
//			return true;
//		}
//		
//		/**
//		 * �����������
//		 */
//		public void clear(){
//			try {
//				mFontsInfoFile.setLength(0);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		/**
//		 * �����ļ�
//		 */
//		public void save(){
//			try {
//				if(mFontsInfoFile.length()!=0){
//					clear();
//				}
//				//��дͷ
//				mFontsInfoFile.write(xmlHeader.getBytes());
//				Iterator ite = mWriterBuffer.entrySet().iterator();
//				while(ite.hasNext()){
//					Map.Entry<String, String> entry = (Entry)ite.next();
//					String fontName = entry.getKey();
//					String fontFileName = entry.getValue();
//					//�޳��ո�
//					String[] temp = fontName.split(" ");
//					fontName = "";
//					for(String str:temp){
//						fontName += str;
//					}
//					String line = "<" + fontName +">" + fontFileName +"</" + fontName +">" + "\r\n";
//					mFontsInfoFile.write(line.getBytes());
//				}
//				mFontsInfoFile.write(xmlEnd.getBytes());
//				mWriterBuffer.clear();
//				mFontsInfoFile.close();
//				isOpen = false;
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
	/**
	 * ����so��,���հ汾�������������ʶ��
	 * @param Context
	 */
	private static void checkAndLoadLibrary(Context context){
		String libs = context.getFilesDir().getAbsolutePath().replace("files", "lib");
		
		boolean bLoadLibrary = false;
		String libimb = null;
		String libimb2d = null;
		String libimbLib = null;
		String libimb2dLib = null;
		String libimbAutoCAD = null;
		String libimbAutoCADLib = null;

		
		//��ȡAssets�ļ��ĵ�ǰ�汾��
		String[] strlibNames = getlibName(context);		
		
		//�ַ�����Ч�Լ��
		if( strlibNames[0] != null && strlibNames[1] != null){
			libimb = strlibNames[0];
			libimb2d = strlibNames[1];
			libimbAutoCAD = strlibNames[2];
			
			//�ֽ�so�ļ�,�õ�����.so��׺���ַ���
			String[] libimbs = libimb.split("\\.");
			String[] libimb2ds = libimb2d.split("\\.");
			String[] libimbAutoCADs = libimbAutoCAD.split("\\.");
	
			//�����ַ�����ǰ"lib"������ĸ
			libimbLib = libimbs[0].substring(3);
			libimb2dLib = libimb2ds[0].substring(3);
			libimbAutoCADLib = libimbAutoCADs[0].substring(3);
		}
		
		File libsDir = new File(libs);
		String[] sos = libsDir.list();

		ArrayList<String> arrays = new ArrayList<String>();
		for(String so:sos){
			arrays.add(so);
		}
		//����֧���µİ汾��������ļ���
		if(arrays.contains(libimbAutoCAD)){
			System.loadLibrary(libimbAutoCADLib);
			isWrapJLoaded = true;
			bLoadLibrary = true;
		} else if(arrays.contains(libimb)){
			System.loadLibrary(libimbLib);
			isWrapJLoaded = true;
			bLoadLibrary = true;
		} else if(arrays.contains(libimb2d)){
			System.loadLibrary(libimb2dLib);
			isWrapJLoaded = true;
			bLoadLibrary = true;
		}
		//֧��ֱ�Ӵӷ��������İ�����
		else if(arrays.contains("libimbAutoCAD.so")){
			System.loadLibrary("imbAutoCAD");
			isWrapJLoaded = true;
			bLoadLibrary = true;
		} else if(arrays.contains("libimb.so")){
			System.loadLibrary("imb");
			isWrapJLoaded = true;
			bLoadLibrary = true;
		} else if(arrays.contains("libimb2d.so")){
			System.loadLibrary("imb2d");
			isWrapJLoaded = true;
			bLoadLibrary = true;
		}

		//֧��ԭ���Ŀ�������ʽ����
		else if(arrays.contains("libimb7c.so")){
			System.loadLibrary("imb7c");
			isWrapJLoaded = true;
			bLoadLibrary = true;
		}else if(arrays.contains("libimb7c2d.so")){
			System.loadLibrary("imb7c2d");
			isWrapJLoaded = true;
			bLoadLibrary = true;
		}
		//���ؿ�ʧ��,��һ���쳣��ȥ
		if ( !bLoadLibrary ) {
			String message = InternalResource.loadString("so���jar���汾��ƥ��", "", "");
			throw new IllegalArgumentException(message);
		}
		
	}
	
	/**
	 * ��ȡAssets�ļ���ĵ�ǰ�汾��,�洢��ver/ver.txt�ı�����
	 * @param Context
	 */
	private static String[] getlibName(Context context){
		String[] strlibNames = new String[3];
		
		AssetManager assetManager = context.getAssets();
		try {
//����ͨ����ȡ����ת��һ��,Android���ַ�Ĭ�ϱ�����UTF-8��,��PC��ȡ���������ļ�Ĭ�ϱ���һ����GBK��GB-2312    add by huangkj 2015-6-25
//��ô���ļ���ȡ��������������Ҫת��ΪGBK��ʾ��UTF-8����,�����ַ��洢��ʾʱ�Ų�����ʧ������
//һ������û������������Ϊ��������,Ӣ�Ļ���ĸ��GBK��UTF-8���뷽ʽ����һ�µ�,�����ĵĻ��ͻ�������
//			InputStream is = assetManager.open("ver/ver.txt");
//			String code = getCode(is);
//			is = assetManager.open("ver/ver.txt");
//			BufferedReader br = new BufferedReader(new InputStreamReader(is,code));
			
			//��ö�ȡ�����ı���������
			InputStream inputStream = assetManager.open("ver/ver.txt");
			//��������ı��������������(ÿ��ֻ����һ��)
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			//����ά�������
			strlibNames[0] = bufferedReader.readLine();
			//ֻ����ά�������
			strlibNames[1] = bufferedReader.readLine();
			//����AutoCAD���ܵ�3d�������
			strlibNames[2] = bufferedReader.readLine();
		} catch (IOException e) {
			String message = InternalResource.loadString("�汾���ļ������ڻ������" + ", " + e, "", "");
			throw new IllegalArgumentException(message);
		}
		
		return strlibNames;
	}
	
	/**
	 * ��ȡ��ǰ�ļ����ı����ʽ
	 * @param InputStream
	 */
	private static String getCode(InputStream is){
		try {
			BufferedInputStream bin = new BufferedInputStream(is);
	
			String code = null;
			int p;
			p = (bin.read() << 8) + bin.read();
		    switch (p) {
		       case 0xefbb:
		           code = "UTF-8";
		           break;
		       case 0xfffe:
		           code = "Unicode";
		           break;
		       case 0xfeff:
		           code = "UTF-16BE";
		           break;
		       default:
		           code = "GBK";
		    }
			is.close();
			   
			return code;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����OpenGL��ʾģʽ
	 */
	public static void setOpenGLMode(boolean isOpenGLMode){
		mIsOpenGLMode = isOpenGLMode;
	}
	
	/**
	 * �Ƿ���OpenGL��ʾģʽ
	 */
	public static boolean isOpenGLMode(){
		 return mIsOpenGLMode;
	}
	
	// �����Դ�ļ��汾��
	private static boolean checkResVersion(File file){
		String strLocalRecVer = null;
		String strApkRecVer = null;;

		File resVerFile = new File(file.getAbsolutePath()+"/ver/res_ver.txt");
		if(!resVerFile.exists()){
			return false;
		}
		
		try {
			InputStream inputStream = new FileInputStream(resVerFile);
			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				
				strLocalRecVer = bufferedReader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AssetManager assetManager = mContext.getAssets();
		try {		
			//��ö�ȡ�����ı���������
			InputStream inputStream = assetManager.open("ver/res_ver.txt");
			//��������ı��������������(ÿ��ֻ����һ��)
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			//��Դ�ļ��汾�ţ�����Դ�ļ��޸�ʱ����Ϊ�汾�ţ�
			strApkRecVer = bufferedReader.readLine();
		} catch (IOException e) {
			String message = InternalResource.loadString("��Դ�汾���ļ������ڻ������" + ", " + e, "", "");
			throw new IllegalArgumentException(message);
		}
		
		if (strLocalRecVer == null || strApkRecVer == null) {
			return false;
		}
		
		if (strLocalRecVer.equals(strApkRecVer)) {
			return true;
		}
		
		return false;
	}
}
