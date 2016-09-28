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
 * @author ¿Ó‘∆Ωı
 * @version 2.0
 */
class DatasourceNative {

	public native static String jni_GetAlias(long instance);
	
    public native static boolean jni_GetIsConnected(long instance);

    public native static void jni_GetDatasets(long instance, long[] buffer, int[] types);

    public native static int jni_GetDatasetsCount(long instance);

    public native static String jni_GetDescription(long instance);

    public native static void jni_SetDescription(long instance, String value);

    public native static boolean jni_GetIsModified(long instance);

    public native static boolean jni_Connect(long instance);

    public native static long jni_GetConnectionInfo(long instance);
		
	public static native long jni_NewSelfEventHandle(Datasource datasource);
	
	public native static long jni_GetPrjCoordSys(long instance);
	
    public native static boolean jni_GetIsReadOnly(long instance);
    
	public static native void jni_DeleteSelfEventHandle(long handle);
	
    public native static long jni_CopyDataset(long instance, long srcDataset,
            String desDatasetName,
            int encodeType,
            long eventHandle);
    
    public static native long jni_CopyDataset2(long instance, long srcDataset,
            String desDatasetName,
            int encodeType,
            int nCharset,
            long eventHandle);

	public static native boolean jni_ChangePassword(long handle, String newPassword, int typeValue);
}
