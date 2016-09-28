package com.supermap.data;

class DataExportNative {
	private DataExportNative() {

	}

	public native static long jni_New();

	public native static void jni_Delete(long handle);

	public native static boolean jni_ExportData(long handle, long handle2);

	public native static boolean jni_ExportDataNormal(long handle,
			long handle2, String targetFilePath, int i, boolean b,int charset);

	public native static boolean jni_ExportDataPNG(long handle, long handle2,
			String targetFilePath, boolean isOverWrite, String worldFilePath);

	public native static boolean jni_ExportDataTIF(long handle, long handle2,
			String targetFilePath, boolean isOverWrite, boolean isExportingPRJFile,
			boolean isExportingGeoTransformFile);

	public native static boolean jni_ExportDataBMP(long handle, long handle2,
			String targetFilePath, boolean isOverWrite, String worldFilePath);

	public native static boolean jni_ExportDataDWG(long handle, long objHandle,
			String targetFilePath, boolean isOverwrite, int i, boolean b,boolean bXrecord,boolean bExpBorder);

	public native static boolean jni_ExportDataDXF(long handle, long objHandle,

			String targetFilePath, boolean isOverwrite, int i, boolean b,boolean bXrecord,boolean bExpBorder);

	public native static long jni_NewSelfEventHandle(long handle, DataExport export) ;

	public native static void jni_DeleteSelfEventHandle(long eventHandle);
	
	public native static int[] jni_GetSupportedFileType(long datasetHandle);
	
	public native static boolean jni_ExportDataJPG(long handle, long objHandle,
			String targetFilePath, boolean isOverwrite, String worldPath, int compression);
	
	public native static boolean jni_ExportDataX(long handle, long objHandle,
			String targetFilePath, boolean isOverwrite);
	
	public native static boolean jni_ExportDataSIT(long handle, long objHandle,
			String targetFilePath, String strPassword, boolean isOverwrite);
	
	public native static boolean jni_ExportDataGIF(long handle, long handle2,
			String targetFilePath, boolean isOverWrite, String worldFilePath);
	
	public native static boolean jni_ExportDataTEMSVector(long handle, long[] handle2,
			String targetFilePath, boolean isOverWrite,int charset);
	
	public native static boolean jni_ExportDataTEMSBuildingVector(long handle, long[] handle2,
			String targetFilePath, boolean isOverWrite,int charset);
	
	public native static boolean jni_ExportDataENCS57(long handle, long[] handle2,
			String targetFilePath, boolean isOverWrite);
	
	public native static boolean jni_ExportDataKML(long handle, long[] handle2,			
			String targetFilePath, boolean isOverWrite);
	
	public native static boolean jni_ExportDataKMZ(long handle, long[] handle2,
			String targetFilePath, boolean isOverWrite);	
}
