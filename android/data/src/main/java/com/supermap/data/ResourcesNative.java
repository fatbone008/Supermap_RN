package com.supermap.data;

class ResourcesNative {
	private ResourcesNative() {

	}

	public static native long jni_New();

	public static native long jni_GetLineLibrary(long instance);

	public static native long jni_GetMarkerLibrary(long instance);

	public static native long jni_GetFillLibrary(long instance);

	public static native void jni_Delete(long instance);

}
