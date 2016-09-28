package com.supermap.data;

class GeoText3DNative {
	private GeoText3DNative() {

	}

	public native static long jni_New();

	public native static void jni_Delete(long instance);

	public native static long jni_Clone(long instance);

	public native static String jni_GetContent(long instance);

	public native static int jni_GetPartCount(long instance);

	public native static long jni_GetTextStyle(long instance);

	public native static void jni_SetTextStyle(long instance, long textStyle);

	public native static int jni_AddPart(long instance, long subHandle,
			double x, double y ,double z);


	public native static boolean jni_InsertPart(long instance, int index,
			long subHandle, double x, double y ,double z);

	public native static boolean jni_RemovePart(long instance, int index);

	public native static boolean jni_SetPart(long instance, int index,
			long subHandle, double x, double y ,double z);

	public native static void jni_Clear(long instance);

//	public native static double jni_GetSubRotation(long geoTextInstance,
//			int index);
//
//	public native static void jni_SetSubRotation(long geoTextInstance,
//			double rotation, int index);

	public native static String jni_GetSubText(long geoTextInstance, int index);

	public native static void jni_SetSubText(long geoTextInstance, String text,
			int index);

	public native static long jni_GetSubHandle(long geoTextInstance, int index);
}
