package com.supermap.data;

class SymbolNative {

	public static native String jni_getName(long instance);

	public static native int jni_getID(long instance);

	public static native int jni_GetType(long instance);

	//android 目前还不支持三维的符号
//	public static native boolean jni_Is3D(long symbolHandle) ;

	public static native long createInstance(long handle);

}
