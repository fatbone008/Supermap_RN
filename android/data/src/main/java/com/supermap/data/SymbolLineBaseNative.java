package com.supermap.data;

class SymbolLineBaseNative {
	private SymbolLineBaseNative() {
	}
	public static native int jni_GetCode(long handle);
	public static native void jni_Delete(long handle);
	public static native void jni_SetCode(long handle,int asciiCode);
	
}
