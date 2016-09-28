package com.supermap.data;

import android.graphics.Bitmap;

class SymbolLineNative {
	private SymbolLineNative() {

	}

	public static native void jni_Delete(long handle);
	
	public static native int jni_GetCount(long handle);
	
	public static native boolean jni_Draw(long handle,Bitmap bmp, long geo);

	public static native long jni_Get(long handle, int index);
}
