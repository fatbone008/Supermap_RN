package com.supermap.data;

class DataImportNative {
	private DataImportNative() {

	}

	public native static long jni_New();

	public native static boolean jni_ImportData(long handle, long handle2,
			int value, int[] values, long handle3);

	public native static void jni_Delete(long handle);

	public native static void jni_SetMapsAndDatasets(long handle,
			String[] datasetsOld, String[] datasetsNew, String[] mapOld,
			String[] mapNew);

	public native static boolean jni_ImportData2(long handle, long handle2,
			long handle3, long handle4);

	public native static boolean jni_ImportData3(long handle, long handle2,
			long handle3, long handle4);

	public native static boolean jni_ImportData4(long handle, long handle2,
			int i, int[] values, long handle3);

	public native static long jni_NewSelfEventHandle(long handle,DataImport import1) ;

	public native static void jni_DeleteSelfEventHandle(long eventHandle) ;
	
	public native static String[] jni_GetSucImportDTNames(long handle);
	
	public native static String[] jni_GetSucImportMapNames(long handle);

}
