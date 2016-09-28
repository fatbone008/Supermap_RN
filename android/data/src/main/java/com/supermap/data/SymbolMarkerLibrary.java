package com.supermap.data;

public class SymbolMarkerLibrary extends SymbolLibrary{
//	public SymbolMarkerLibrary() {
//		long handle = SymbolMarkerLibraryNative.jni_New();
//		this.setHandle(handle, true);
//	}

	/**
	 * 内部使用构造函数 
	 * @param handle long
	 */
	SymbolMarkerLibrary(long handle) {
		this.setHandle(handle,false);
	}

	/**
	 * 释放对象
	 */
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			SymbolMarkerLibraryNative.jni_Delete(getHandle());
			setHandle(0);
			clearHandle();
		}	
	}
}
