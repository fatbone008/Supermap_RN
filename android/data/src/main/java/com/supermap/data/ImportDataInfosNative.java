package com.supermap.data;

class ImportDataInfosNative {
	
	private ImportDataInfosNative() {
		
	}
	
	public native static int jni_GetCount(long instance);
	
	public native static long jni_GetItem(long instance, int index);
	
	public native static int jni_GetRasterCount(long instance);
	
	public native static long jni_GetRasterItem(long instance, int index);
	
	public native static String jni_GetRasterName(long instance, int index);
	
	public native static void jni_Delete(long instance);
	
	public native static void jni_Delete2(long instance);
}
