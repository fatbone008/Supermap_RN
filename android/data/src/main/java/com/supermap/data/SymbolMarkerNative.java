package com.supermap.data;

import android.graphics.Bitmap;

class SymbolMarkerNative {
	private SymbolMarkerNative() {

	}

	public static native long jni_New();

	public static native void jni_Delete(long handle);
	
	public static native boolean jni_Draw(long handle, Bitmap bmp, long geo);

	public static native int jni_ComputeDisplaySize(long handle, int symbolSize);

	public static native int jni_ComputeSymbolSize(long handle, int displaySize);

	public static native void jni_GetOrigin(long instance, int[] pt);
	
	public static native void jni_SetOrigin(long instance, int x, int y);
	
	public static native int jni_GetStrokesCount(long instance);
	
	public static native void jni_GetStrokeHandle(long instance, long[] strokeHandles);

	public static native void jni_GetMatrialStream(long handle, String libPath,int index);

}
