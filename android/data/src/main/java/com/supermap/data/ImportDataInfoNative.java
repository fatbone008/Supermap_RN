package com.supermap.data;

class ImportDataInfoNative {
	
	private ImportDataInfoNative() {
		
	}
	
	public native static void jni_GetBounds(long instance, double[] bounds);
	
	public native static void jni_GetBounds2(long instance, double[] bounds);
	
	public native static String jni_GetTargetName(long instance);
	
	public native static void jni_SetTargetName(long instance, String name);
	
	public native static String jni_GetTargetNameRaster(long instance);
	
	public native static void jni_SetTargetNameRaster(long instance, String name);
	
	public static native int  jni_GetFieldCount(long instance);
	
	public static native void jni_GetSourceFieldInfos(long instance, long[] handles);
	
	public native static void jni_SetTargetFieldInfos2(long instance, int[] fieldState,
			int[] indexes, String[] names, int[] source, int[] target);
	
	public native static void jni_SetTargetFieldInfos3(long instance, int[] fieldState,
			int[] indexes, String[] names, int[] indexTypes, int[] fieldTypes, int[] source, int[] target);
}	
