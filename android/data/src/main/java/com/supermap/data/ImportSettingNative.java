package com.supermap.data;

class ImportSettingNative {
	
    private ImportSettingNative() {
    	
    }
    
    public native static String jni_GetSourceFilePath(long instance);
    
    public native static void jni_SetSourceFilePath(long instance, String path);
    
    public native static int jni_GetSourceFileType(long instance); 
    
    public native static int jni_GetEncodeType(long instance);
    
    public native static void jni_SetEncodeType(long instance, int value);
    
    public native static int jni_GetFileCharset(long instance);
    
    public native static void jni_SetFileCharset(long instance, int value);
    
    public native static long jni_GetTargetPrjCoordSys(long instance);
    
    public native static void jni_SetTargetPrjCoordSys(long instance, long value);
    
    public native static long jni_GetTargetDataInfos(long instance, String value);
    
    public static native long jni_GetTargetDataInfos2(long instance, 
			String targetNamePrefix, int ugcValue, long handle);
    
    public native static void jni_SetTargetDataInfos(long instance, long value);
    
    public native static boolean jni_IsOverwrite(long instance);
    
    public native static void jni_SetOverwrite(long instance, boolean value);

    public native static boolean jni_OpenDatasource(long instance, long handle);
    
    public native static long jni_CloneDatasourceConnectionInfo(long instance);
    
    public native static int jni_GetImportMode(long instance);
    
    public native static void jni_SetImportMode(long instance, int value);

    
    public native static boolean jni_GetIsUseFME(long instance);
    
    public native static void jni_SetIsUseFME(long instance, boolean value);
    
    public native static String jni_GetTargetDatasetName(long instance);
    
    public native static void jni_SetTargetDatasetName(long instance, String path);

}
