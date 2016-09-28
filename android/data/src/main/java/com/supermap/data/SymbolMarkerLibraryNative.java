package com.supermap.data;

class SymbolMarkerLibraryNative {
	private SymbolMarkerLibraryNative() {
		
	}
	
	public native static long jni_New();
	
	public native static void jni_Delete(long instance);
}
