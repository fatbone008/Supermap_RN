/**
 * 
 */
package com.supermap.data;

/**
 * @author zhengyl
 *
 */
class ImportSettingTIFNative {
	private ImportSettingTIFNative() {
		
	}
	
	public static native long jni_New();

	public static native long jni_Clone(long instance);

	public static native void jni_Delete(long handle);

	public static native boolean jni_IsImportingAsGrid(long instance);

	public static native void jni_SetImportingAsGrid(long instance, boolean value);

//	public static native boolean jni_IsPyramidBuilt(long instance);
//
//	public static native void jni_SetPyramidBuilt(long instance, boolean value);
	
	public static native String jni_GetWorldFilePath(long instance);

	public static native void jni_SetWorldFilePath(long instance, String value);

	public static native int jni_GetMultiBandImportMode(long instance);

	public static native void jni_SetMultiBandImportMode(long instance, int value);

	public static native String jni_GetTargetName(long instance);	
	
//    public static native void jni_SetIgnorMode(long instance , int value);
//	
//	public static native int jni_GetIgnorMode(long instance);
//	
//	public static native int jni_GetIgnoreValueCount(long instance);
//	
//	public static native  void jni_SetIgnoreValues(long instance,double[]ignoreValues);
//	
//	public static native  void jni_GetIgnoreValues(long instance,double[]ignoreValues);

}
