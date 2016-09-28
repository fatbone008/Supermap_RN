package com.supermap.data;

class SymbolLibraryNative {
	private SymbolLibraryNative() {

	}

	public static native long jni_findGroup(long instance, int value);

	public static native boolean jni_Contains(long instance, int value);

	// public static native long jni_findSymbol(long instance, int value);

	// public static native long jni_findSymbol1(long instance, String name);

	public static native boolean jni_fromFile(long instance, String fileName,
			boolean overWrite);

	// public static native boolean jni_remove(long instance, int value);
	public native static long jni_add(long isntance, long handle);
	public static native void jni_clear(long instance);

	public native static long jni_GetRootGroup(long isntance);

	public native static String jni_GetLibPath(long handle);
}
