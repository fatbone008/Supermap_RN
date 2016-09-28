package com.supermap.data;

import android.graphics.Bitmap;

class SymbolFillNative {
	private SymbolFillNative() {

	}


	public static native void jni_Delete(long handle);
	
	
	public static native boolean jni_Draw(long handle, Bitmap bmp,long geo, long lib);

}