package com.supermap.data;

class StatisticsResultNative {
	public native static void jni_Delete(long instance);

	public native static boolean jni_GetIsDirty(long instance);

	public native static double jni_getMaxValue(long instance);

	public native static double jni_getMinValue(long instance);

	public native static double jni_getAverage(long instance);

	public native static double jni_getVariance(long instance);

	public native static double jni_getStdDeviation(long instance);

	public native static double jni_getMedianValue(long instance);

	public native static double[] jni_getMinority(long instance);

	public native static double[] jni_getMajority(long instance);

}
