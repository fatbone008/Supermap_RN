package com.supermap.data;

class TransformationNative {
	private TransformationNative() {
    }

	public native static long jni_New();
	
	public native static void jni_Delete(long instance);
	
    public native static boolean jni_SetOriginalControlPoints(long instance, double[] xs, double[] ys);
    
    public native static boolean jni_SetTargetControlPoints(long instance, double[] xs, double[] ys);
    
	public native static boolean jni_TransformPoint2Ds(long instatnce, 
			double[] xs, double[] ys, int transformationMode);

}
