package com.supermap.data;

class SymbolLineLibraryNative {
	private SymbolLineLibraryNative() {

    }

    public static native void jni_Delete(long instance);

	public static native long jni_GetInlineMarkerLib(long instance);
}
