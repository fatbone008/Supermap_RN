package com.supermap.data;

class SymbolMarkerStrokeNative {
	
	private SymbolMarkerStrokeNative(){
		
	}
	
	public static native long jni_New();
	
	public static native int jni_GetType(long instance);
	
	public static native void jni_Delete(long instance);
}
