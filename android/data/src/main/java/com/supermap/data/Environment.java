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
	 * 配置文件路径
	 */
	private static final String XML_CONFIG_DIRECTORY = android.os.Environment.getExternalStorageDirectory()+"/SuperMap/config/";
	
	/**
	 * 网络地图数据存放路径
	 */
	private static final String WEB_CACHE_DIRECTOTY = android.os.Environment.getExternalStorageDirectory()+"/SuperMap/data/";
	
	/**
	 * 临时目录
	 */
	private static final String TEMPORARY_PATH = android.os.Environment.getExternalStorageDirectory()+"/SuperMap/temp/";
	
	/**
	 * 默认的许可路径
	 */
	private static final String LICENSE_PATH = android.os.Environment.getExternalStorageDirectory()+"/SuperMap/license/";

	private static String UGCConfigDirectory = "";
	private static String UGCWebCacheDirectory = "";
	private static String UGCTemporaryDirectory = "";
	private static String UGCLicenseDirectory = "";
	private static String UGCFontDirectory = "/system/fonts/";
	private static String UGCODACharMapperInitFile = "/oda/adinit.dat";
	//配置文件名称
	private static final int CONFIGCOUNT = 5;
	private static final String SUPERMPXML = "SuperMap.xml";
	private static final String PRJCONFIG = "PrjConfig.xml";
	private static final String GRAPHICSMEMFONTS = "GraphicsMemFonts.xml";
	private static final String CODETRANSITION = "CodeTransition.xml";
	private static final String LOGRESOURCE = "resource.xml";
	//语音资源
	private static final String VOICEFILE = "/voice/resource.irf";
	
	private static boolean isSuportSpeeking = false;
	
	/**
	 * 默认的判断一个数值是否为0的正精度
	 * 
	 */
	public static final double DEFAULT_MAX_EQUAL_ZERO_PRECISION = 1e-10;

	/**
	 * 默认的判断一个数值是否为0的负精度
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

	// @modified by 孔令亮 at 2007年8月6日 上午09时44分27秒
	// @reason: 修改为protected,供UI包中继承，实现MapControl的LoadWrapj();
	protected Environment() {
	}
	
	/**
	 * 定义一个内部的结构体，用于保持当前设备的一些硬件信息
	 */
	static class DeviceInfo{
		/**
		 * 设备型号
		 */
		public String Model = "";
		
		/**
		 * 系统版本
		 */
		public String SystemVersion = "";
		
		/**
		 * Mac地址
		 */
		public String MacAddress = "";
		
		/**
		 * IMEI,PAD产品没有IMEI，主要是手机的
		 */
		public String IMEI = "none";
	}

	/**
	 * 得到判断一个数值是否为0的正精度
	 * 
	 * @return double
	 */
	public static double getMaxEqualZeroPrecision() {
		return maxEqualZeroPrecision;
	}
	
	
	/**
	 * 得到判断一个数值是否为0的负精度
	 * 
	 * @return double
	 */
	public static double getMinEqualZeroPrecision() {
		return minEqualZeroPrecision;
	}
	
	/**
	 * 获取临时文件路径
	 * @return 路径
	 * @attention 默认为/sdcard/SuperMap/temp/,若用户调用了setTemporaryPath则返回用户设置的值
	 */
	public static String getTemporaryPath(){
		return jni_getTempPath();
	}
	
	/**
	 * 设置临时目录，SuperMap Objects for Android 在使用过程中会产生一些临时数据，
	 * 使用该方法可以将临时数据存放于此，推荐使用安装路径下的cache文件夹
	 * @param tempPath 临时目录
	 * @attention 默认为/sdcard/SuperMap/temp/
	 */
	public static void setTemporaryPath(String tempPath){
		//避免一些用户在使用该接口的时候传入的路径不是合法的路径，传到底层先验证下
		if(!tempPath.startsWith("/")){
			tempPath = "/" + tempPath; //必须从跟路径开始
		}
		if(!tempPath.endsWith("/"))
		{
			tempPath = tempPath + "/"; //必须以"/"结束
		}
		//要校验一下该路径是否合法
		File file = new File(tempPath);
		
		if(file.exists()){
			if(!file.isDirectory()){
				throw new IllegalArgumentException(tempPath + " is not a correct directory");
			}
		}else{
			//不存在的话，先创建出路径,若创建不成功则是非法路径
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
	 * 内部使用的接口，用于获取硬件信息
	 * @return
	 */
	/*package*/ static DeviceInfo getDeviceInfo(){
		if(!hasInitUGCEnv()){
        	throw new IllegalStateException("Please call com.supermap.data.Environment.initialization() firstly");
        }
		return mDeviceInfo;
	}
	/**
	 * 获取配置文件路径
	 * @return 配置文件路径
	 * @attention 默认为/sdcard/SuperMap/config
	 */
	@Deprecated
	public static String getConfigFileDirectory(){
		return jni_getConfigDir();
	}
	
	/**
	 * 设置调试模式
	 * @attention 建议在调试时使用，系统将在配置文件路径getTemporaryPath()同级路径下产生log文件夹，里面是内核运行输出的日志
	 * @param isDebugMode
	 */
	public static void setDebugMode(boolean isDebugMode){
		jni_SetDebugMode(isDebugMode);
	}
	
	/**
	 * 设置配置文件路径，用于存放SuperMap Objects for Android 所需的配置文件
	 * @param config
	 * @attention 默认为/sdcard/SuperMap/config
	 */
	@Deprecated
	public static void setConfigFileDirectory(String config){
		//避免一些用户在使用该接口的时候传入的路径不是合法的路径，传到底层先验证下
		if(!config.startsWith("/")){
			config = "/" + config; //必须从跟路径开始
		}
		if(!config.endsWith("/"))
		{
			config = config + "/"; //必须以"/"结束
		}
		//要校验一下该路径是否合法
		File file = new File(config);
		
		if(file.exists()){
			if(!file.isDirectory()){
				throw new IllegalArgumentException(config + " is not a correct directory");
			}
		}else{
			//不存在的话，先创建出路径,若创建不成功则是非法路径
			if(!file.mkdirs()){
				throw new IllegalArgumentException(config + " is not a correct directory");
			}
		}
		//这个接口废弃了，不起作用
//		UGCConfigDirectory = config;
//		
//		jni_setConfigDir(UGCConfigDirectory);
	}
	
	/**
	 * 获取网路地图缓存存放的路径
	 * @return 网路地图缓存存放的路径
	 */
	public static String getWebCacheDirectory(){
		return jni_getWebCacheDir();
	}
	
	/**
	 * 设置网络地图缓存存放的路径
	 */
	public static void setWebCacheDirectory(String webCache){
		//避免一些用户在使用该接口的时候传入的路径不是合法的路径，传到底层先验证下
		if(!webCache.startsWith("/")){
			webCache = "/" + webCache; //必须从跟路径开始
		}
		if(!webCache.endsWith("/"))
		{
			webCache = webCache + "/"; //必须以"/"结束
		}
		//要校验一下该路径是否合法
		File file = new File(webCache);
		
		if(file.exists()){
			if(!file.isDirectory()){
				throw new IllegalArgumentException(webCache + " is not a correct directory");
			}
		}else{
			//不存在的话，先创建出路径,若创建不成功则是非法路径
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
	 * 设置外挂字体路径
	 */
	public static void setFontsPath(String fontsPath){
		if(!hasInitUGCEnv()){
        	throw new IllegalStateException("Please call com.supermap.data.Environment.initialization() firstly");
        }
		//避免一些用户在使用该接口的时候传入的路径不是合法的路径，传到底层先验证下
		if(!fontsPath.startsWith("/")){
			fontsPath = "/" + fontsPath; //必须从跟路径开始
		}
		if(!fontsPath.endsWith("/"))
		{
			fontsPath = fontsPath + "/"; //必须以"/"结束
		}
		//要校验一下该路径是否合法
		File file = new File(fontsPath);
		
		if(file.exists()){
			if(!file.isDirectory()){
				throw new IllegalArgumentException(fontsPath + " is not a correct directory");
			}
		}else{
			//不存在的话，先创建出路径,若创建不成功则是非法路径
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
	 * 调入JNI动态库
	 */
	static void LoadWrapJ() {
		if (!isWrapJLoaded) {
			// Android平台使用
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
	 * 放置许可文件存放路径
	 * @param path
	 */
	public static void setLicensePath(String path){
		//避免一些用户在使用该接口的时候传入的路径不是合法的路径，传到底层先验证下
		if(!path.startsWith("/")){
			path = "/" + path; //必须从跟路径开始
		}
		if(!path.endsWith("/"))
		{
			path = path + "/"; //必须以"/"结束
		}
		//要校验一下该路径是否合法
		File file = new File(path);
		
		if(file.exists()){
			if(!file.isDirectory()){
				throw new IllegalArgumentException(path + " is not a correct directory");
			}
		}else{
			//不存在的话，先创建出路径,若创建不成功则是非法路径
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
	 * 获取mContext,在分组查询时使用(added by hp 2014/5/10)
	 * @param path
	 */
	static Context getContext(){
		return mContext;
	}
	/**
	 * 初始化环境
	 * @param context android上下文环境
	 * @return 状态是否正常
	 * @attention 用户必须在主线程中调用该方法，才能配置好SuperMap Objects for Android的环境
	 */
	public static boolean initialization(Context context){
		if(hasInitUGCEnv){
			return hasInitUGCEnv;
		}
		
		// 将外部的context存起来，分组查询时要使用,(2014/5/9 added by hp)
		mContext = context;
		
		//加载so库,按照版本号命名规则进行识别            modified by huangkj 2015-11-9
		checkAndLoadLibrary(context);
		

		//获取系统的文件路径
		UGCConfigDirectory = context.getFilesDir().getAbsolutePath() + "/config/";
		//先检查下apk中压缩的资源
		checkAPK(context);
		
		//检查配置文件是否存在
		if(checkConfigFile()){
			hasInitUGCEnv = true;
		}else{
			//配置文件丢失或者不存在就重新冲asserts中上传一份
			FileUtil util = new FileUtil();
			util.upLoadConfigFile(context, UGCConfigDirectory);
			
			//上传完毕，还是在检查下比较好
			hasInitUGCEnv = checkConfigFile();
		}
		
	
		
		//首先检查下用户设备的设置，避免一些参数获取不到		
		checkDevice(context);
		
		jni_JniLoading(context);
		
		// 设置临时目录
		setTemporaryPath(UGCTemporaryDirectory);
		
		// 设置网络地图缓存路径
		setWebCacheDirectory(UGCWebCacheDirectory);
		
		//设置默认的许可路径
		setLicensePath(UGCLicenseDirectory);
		
		
		//配置文件配好后再次设置配置文件路径，保证设置的和自动配置的与底层一致
		if(hasInitUGCEnv){
			jni_setConfigDir(UGCConfigDirectory);
		}else{
			throw new IllegalStateException("Environment initialize Failed");
		}
		
		// 必须调用该函数以初始化当前的环境
		jni_InitEnvironment();
//		//环境初始化完成后清除外挂的字体信息，由用户来更新
//		FontsInfoManager fontsManager = new FontsInfoManager();
//		fontsManager.clear();
//		fontsManager.save();

		//设置默认的字体路径   add by huangkj 2015-11-9
		setFontsPath(UGCFontDirectory);
		
		// 为UGC设置应用缓存目录 , added by Xingjun, 2016.06.20
		jni_setAppCachePath(context.getCacheDir().toString());
		
		return hasInitUGCEnv;
	}
	
	private static void checkDevice(Context context){
		//先获取MAC试试，要是获取不到就跳转到设置界面
		WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		int oldState = wifiMgr.getWifiState();
		//要是wifi不可用则，将其打开
		for(int i=0;i<20 && wifiMgr.getWifiState() != WifiManager.WIFI_STATE_ENABLED;i++){
			wifiMgr.setWifiEnabled(true);
			try {
				//最长时间等三秒
				Thread.sleep(150);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
		String temp = wifiInfo.getMacAddress();
		if(temp == null){
			//如果不能获取到Mac地址那可能就是模拟器了
			temp = "none";
		}
		//要是原来的wifi是关闭的那就关闭
		if(oldState == WifiManager.WIFI_STATE_DISABLED){
			
			wifiMgr.setWifiEnabled(false);
		} // 避免手机安全软件提示用户打开wifi后，wifi仍然处于关闭状态，故注释掉本段代码，
		mDeviceInfo.MacAddress = temp;
		
		TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if(telMgr != null)
		{
			//GSM手机的是IMEI,CDMA的手机是其他的
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
	 * 验证许可码，用于手动输入和在线注册
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
	
	// 检查配置文件是否存在
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
			
			//数量上要满足必须的配置文件
			if(count >= CONFIGCOUNT){
				//检查百度三维城市地图的配置文件
				File bdFile = new File(file.getAbsolutePath()+"/Resource/BMapCitySetting.xml");
				if (!bdFile.exists()) {
					return false;
				}
				
				//有导航的时候要判断是否加入语音资源
				if(isSuportSpeeking){
					File voice = new File(file.getAbsolutePath()+VOICEFILE);
					if(!voice.exists()){
						return false;
					}
				}
				
				//检查ICU dat
				File icu = new File(file.getParent()+"/icu/icudt50l.dat");
				if(!icu.exists()){
					return false;
				}
				
				File naviRes = new File(file.getAbsolutePath()+"/mapRes/map.jar");
				if(!naviRes.exists()){
					return false;
				}
				
				// 检查资源版本号
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
	 * 获取许可状态
	 * @return 返回当前的许可状态
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
		
		//先调用下这个，否则没法执行下面的东西
		isRegister = jni_IsLicenseValid();
		isLicenseExsit = jni_IsLicenseExsit();
		//如果连许可文件都不存在的话就直接返回个东西
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
		calendar.set(Calendar.MONTH,nMonth-1);//月份从0算起
		calendar.set(Calendar.DAY_OF_MONTH,nDay);
		startDate = new Date(calendar.getTimeInMillis());
		
		long nEndData = jni_GetExpireDate();
		nYear = (int)nEndData / 10000;
		nMonth = (int)(nEndData % 10000) / 100;
		nDay = (int)nEndData % 100;
		calendar.clear();
		calendar.set(Calendar.YEAR,nYear);
		calendar.set(Calendar.MONTH,nMonth-1);//月份从0算起
		calendar.set(Calendar.DAY_OF_MONTH,nDay);
		expireDate = new Date(calendar.getTimeInMillis());
		
		return new LicenseStatus(isRegister, isTrailLicense, version, startDate, expireDate, isLicenseExsit);
	
	}
	

	// 内部使用，用于被继承。
	protected static void InternalLoadWrapJ() {
		LoadWrapJ();
	}
	
	/**
	 * 用于判断环境中是否支持语音功能
	 */
	protected static boolean isSuportSpeeking(){
		return isSuportSpeeking;
	}
	
	/**
	 * 检查apk中的资源文件
	 * @return
	 */
	private static void checkAPK(Context context){
		
		boolean isHaveResource = false;
		boolean isHaveLib = false;
		
		// 检查声音文件
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
		
		// 检查语音库
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
	 * 初始化环境 设置当前环境为Windows Linux或者其它 如果不设置，在Graphics模块肯能会出现bug
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
	
	//获取许可状态的jni方法
	private static native boolean jni_IsLicenseValid();
	private static native boolean jni_IsTrailLicense();
	private static native boolean jni_IsLicenseExsit();
	private static native long jni_GetVersion();
	private static native long jni_GetStartDate();
	private static native long jni_GetExpireDate();
	
	private static native boolean jni_IsFiledMapperValid();
	private static native boolean jni_IsGeoSurveyValid();
	
	// 获取或设置UGC的应用缓存目录，这个目录是应用自身的，不需要申明读写权限;
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
				 
				 //拷贝wkt的配置文件
				 String[] wkts = null; 
				 wkts = configs = mgr.list("config4wkt");
				 for(String wkt:wkts){
					 String wktPath = "config4wkt/"+wkt;
					 
					 InputStream fis = mgr.open(wktPath);
					 
					 String configCsv = ugcConfig + "ProjectionMappingTable/"+wkt;
					 File temp = new File(configCsv);
					 
					 copyFile(fis, temp);
					 
				 }
				 
				 //拷贝三维配置文件
				 copy3DFile(context, ugcConfig);
				 
				//拷贝百度地图配置文件
				 File bdFile = new File(ugcConfig+"/Resource/BMapCitySetting.xml");
				 InputStream bdConfig = mgr.open("res/BMapCitySetting.xml.ogg");
				 copyFile(bdConfig, bdFile);
				 
				 //拷贝资源文件
				 File mapResFile = new File(ugcConfig+"/mapRes/map.jar");
				 InputStream mapResIs = mgr.open("mapRes/map.ogg");
				 copyFile(mapResIs, mapResFile);

				 //部署ICU
				 InputStream icuDat = mgr.open("icu/icudt50l.dat");
				 File icuPath = new File(context.getFileStreamPath("icu").getAbsoluteFile()+"/icudt50l.dat");
				 copyFile(icuDat, icuPath);

				 //oda init file
				 InputStream odaDat = mgr.open("oda/adinit.dat");
				 File odaPath = new File(context.getFileStreamPath("oda").getAbsoluteFile()+"/adinit.dat");
				 copyFile(odaDat, odaPath);
				 

				 //有导航模块的时候要加入语音资源
				 if(isSuportSpeeking){
					 //拷贝语音文件
					 File voiceFile = new File(ugcConfig+VOICEFILE);
					 InputStream voice = mgr.open("voice/resource.mp3");
					 copyFile(voice, voiceFile);
				 }
				 
				 //拷贝资源版本号文件
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
		    	//目标路径不存在的话就创建一个
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
						// 拷贝压缩文件
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
//		 * 清除所有内容
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
//		 * 保存文件
//		 */
//		public void save(){
//			try {
//				if(mFontsInfoFile.length()!=0){
//					clear();
//				}
//				//先写头
//				mFontsInfoFile.write(xmlHeader.getBytes());
//				Iterator ite = mWriterBuffer.entrySet().iterator();
//				while(ite.hasNext()){
//					Map.Entry<String, String> entry = (Entry)ite.next();
//					String fontName = entry.getKey();
//					String fontFileName = entry.getValue();
//					//剔除空格
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
	 * 加载so库,按照版本号命名规则进行识别
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

		
		//读取Assets文件的当前版本号
		String[] strlibNames = getlibName(context);		
		
		//字符串有效性检测
		if( strlibNames[0] != null && strlibNames[1] != null){
			libimb = strlibNames[0];
			libimb2d = strlibNames[1];
			libimbAutoCAD = strlibNames[2];
			
			//分解so文件,得到不带.so后缀的字符串
			String[] libimbs = libimb.split("\\.");
			String[] libimb2ds = libimb2d.split("\\.");
			String[] libimbAutoCADs = libimbAutoCAD.split("\\.");
	
			//过滤字符串的前"lib"三个字母
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
		//优先支持新的版本号命名库的加载
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
		//支持直接从服务器出的包加载
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

		//支持原来的库命名方式加载
		else if(arrays.contains("libimb7c.so")){
			System.loadLibrary("imb7c");
			isWrapJLoaded = true;
			bLoadLibrary = true;
		}else if(arrays.contains("libimb7c2d.so")){
			System.loadLibrary("imb7c2d");
			isWrapJLoaded = true;
			bLoadLibrary = true;
		}
		//加载库失败,抛一个异常出去
		if ( !bLoadLibrary ) {
			String message = InternalResource.loadString("so库和jar包版本不匹配", "", "");
			throw new IllegalArgumentException(message);
		}
		
	}
	
	/**
	 * 读取Assets文件里的当前版本号,存储在ver/ver.txt文本里面
	 * @param Context
	 */
	private static String[] getlibName(Context context){
		String[] strlibNames = new String[3];
		
		AssetManager assetManager = context.getAssets();
		try {
//这里通过获取编码转换一下,Android的字符默认编码是UTF-8的,而PC获取服务器上文件默认编码一般是GBK获GB-2312    add by huangkj 2015-6-25
//那么从文件读取出来的数据流需要转换为GBK表示的UTF-8编码,这样字符存储显示时才不会损失或乱码
//一般我们没发现问题是因为不带中文,英文或字母在GBK或UTF-8编码方式都是一致的,带中文的话就会有乱码
//			InputStream is = assetManager.open("ver/ver.txt");
//			String code = getCode(is);
//			is = assetManager.open("ver/ver.txt");
//			BufferedReader br = new BufferedReader(new InputStreamReader(is,code));
			
			//获得读取到的文本的输入流
			InputStream inputStream = assetManager.open("ver/ver.txt");
			//构造这个文本输入里的流缓存(每次只缓存一行)
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			//带三维库的名称
			strlibNames[0] = bufferedReader.readLine();
			//只带二维库的名称
			strlibNames[1] = bufferedReader.readLine();
			//包含AutoCAD功能的3d库的名称
			strlibNames[2] = bufferedReader.readLine();
		} catch (IOException e) {
			String message = InternalResource.loadString("版本号文件不存在或已损毁" + ", " + e, "", "");
			throw new IllegalArgumentException(message);
		}
		
		return strlibNames;
	}
	
	/**
	 * 获取当前文件流的编码格式
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
	 * 设置OpenGL显示模式
	 */
	public static void setOpenGLMode(boolean isOpenGLMode){
		mIsOpenGLMode = isOpenGLMode;
	}
	
	/**
	 * 是否是OpenGL显示模式
	 */
	public static boolean isOpenGLMode(){
		 return mIsOpenGLMode;
	}
	
	// 检查资源文件版本号
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
			//获得读取到的文本的输入流
			InputStream inputStream = assetManager.open("ver/res_ver.txt");
			//构造这个文本输入里的流缓存(每次只缓存一行)
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			//资源文件版本号（用资源文件修改时间作为版本号）
			strApkRecVer = bufferedReader.readLine();
		} catch (IOException e) {
			String message = InternalResource.loadString("资源版本号文件不存在或已损毁" + ", " + e, "", "");
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
