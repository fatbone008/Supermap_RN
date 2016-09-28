package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author not attributable
 * @version 2.0
 */
class DatasourceConnectionInfoNative {

    public native static String jni_GetAlias(long instatnce);

    public native static void jni_SetAlias(long instatnce, String value);

    public native static int jni_GetEngineType(long instatnce);

    public native static void jni_SetEngineType(long instatnce, int value);

    public native static String jni_GetServer(long instatnce);

    public native static void jni_SetServer(long instatnce, String value);

    public native static String jni_GetUser(long instatnce);

    public native static void jni_SetUser(long instatnce, String value);

    public native static String jni_GetPassword(long instatnce);

//    public native static void jni_SetPassword(long instatnce, String value);

    public native static boolean jni_GetReadOnly(long instatnce);

    public native static void jni_SetReadOnly(long instatnce, boolean value);

    public native static long jni_New();
    
    public native static String jni_GetDriver(long instatnce);

    public native static void jni_SetDriver(long instatnce, String value);

    public native static String jni_GetWebCoordinate(long instance);
    
    public native static void jni_SetWebCoordinate(long instance, String value);

    //add by huangkj 2016-1-27
	////////////////////////////////////////////////////////////////////////////
    public native static String jni_GetWebVersion(long instance);
    
    public native static void jni_SetWebVersion(long instance, String value);
    
    public native static String jni_GetWebFormat(long instance);
    
    public native static void jni_SetWebFormat(long instance, String value);
    
    public native static String jni_GetWebLayers(long instance);
    
    public native static void jni_SetWebLayers(long instance, String value);
    
	public native static void jni_GetWebBBOX(long instance, double[] params);

	public native static void jni_SetWebBBOX(long instance, double left,
			double bottom, double right, double top);
	
	public native static String jni_GetWebExtendParam(long instance);
	
	public native static void jni_SetWebExtendParam(long instance, String value);
	////////////////////////////////////////////////////////////////////////////
	
    public native static void jni_Delete(long instatnce);

    public native static void jni_Reset(long instance, String server,
                                        String driver, String database,
                                        String alias,
                                        String user, String password,
                                        int engineType);

	public static native void jni_SetPassword(long handle, String value, int typeValue);

	public static native int jni_GetEncryptionType(long handle);
}
