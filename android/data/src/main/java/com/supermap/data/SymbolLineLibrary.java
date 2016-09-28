package com.supermap.data;


public class SymbolLineLibrary extends SymbolLibrary {
	/**
	 * ���캯��
	 */
//	public SymbolLineLibrary() {
//		long handle = SymbolLineLibraryNative.jni_New();
//		this.setHandle(handle, true);
//	}
//	
	/**
	 * �ڲ�ʹ�ù��캯��
	 * @param handle long
	 */
	SymbolLineLibrary(long handle) {
		this.setHandle(handle,false);
	
	}

	/**
	 * �ͷŶ���
	 */
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			SymbolLineLibraryNative.jni_Delete(getHandle());
			setHandle(0);
			clearHandle();
		}
		
	}
	
	/*public*/ SymbolMarkerLibrary getInlineMarkerLib(){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("inlineMarkerLib()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		long handle = SymbolLineLibraryNative.jni_GetInlineMarkerLib(getHandle());
		if(handle==0)
			return null;
		SymbolMarkerLibrary sMlib = new SymbolMarkerLibrary(handle);
		return sMlib;
	}
	
	
}