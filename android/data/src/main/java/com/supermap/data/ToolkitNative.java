package com.supermap.data;

import android.graphics.Bitmap;

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
 * @author ’≈ºÃƒœ
 * @version 2.0
 */
class ToolkitNative {
    private ToolkitNative(){
    }
    public static native void jni_SetBooleanHandle(long handle,boolean value);
    
    public static native boolean jni_GetBooleanHandle(long handle);
    
    public static native long jni_creatStandardMargin(long datasourceHandle, 
    													 String datasetName, 
    													 long standardMarginHandle);
    public static native boolean jni_DrawGeometry(long geometryHandle, long resourcesHandle,Bitmap bmp);
    
    public static native boolean jni_DrawGeometryToPNG(long geometryHandle, long resourcesHandle,String fileName,int width,int height);
    
    public static native boolean jni_CompactDatasource(long datasourceHandle);
    
    public static native int jni_GetErrorCount();
    
    public static native void jni_GetErrorInfos(int[] threadIDs, String[] markers, String[] messages, int errorCount, int count); 
    
    public static native void jni_ClearErrors(); 
    
    public static native String jni_GetLastError();
    
    public static native void jni_GetAllErrors(int[] threadIDs, String[] markers, String[] messages);
    
    public static native void jni_SetHandleDoubleValue(long handle, double value);
    
    public static native double jni_GetHandleDoubleValue(long handle);
    
    public static native double jni_CalcLength(double[] x,double[] y,long prj);
    
    public static native double jni_CalcArea(double[] x,double[] y,long prj);
    
    public static native boolean jni_ExtractCacheFile(String sourceFileName, String targetFolderName, String password);
	
    public static native boolean jni_Compress7Zip(String[] fileNames,
			String targetFileName, String password) ;
	
    public static native boolean jni_UnCompress7Zip(String srcFileName,
			String targetFolder, String password);

	public static native double jni_MeasureAngle(double x, double y, double x2, double y2, double x3, double y3);
}
