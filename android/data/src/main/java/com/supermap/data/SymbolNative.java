package com.supermap.data;

class SymbolNative {

	public static native String jni_getName(long instance);

	public static native int jni_getID(long instance);

	public static native int jni_GetType(long instance);

	//android Ŀǰ����֧����ά�ķ���
//	public static native boolean jni_Is3D(long symbolHandle) ;

	public static native long createInstance(long handle);

}
