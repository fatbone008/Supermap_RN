package com.supermap.data;

public class SymbolFillLibrary extends SymbolLibrary {
	/**
	 * ���캯��
	 */
//	public SymbolFillLibrary() {
//		long handle = SymbolFillLibraryNative.jni_New();
//		this.setHandle(handle, true);
//	}
//	
	/**
	 * �ڲ�ʹ�ù��캯��
	 * @param handle long
	 */
	SymbolFillLibrary(long handle) {
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
			SymbolFillLibraryNative.jni_Delete(getHandle());
			setHandle(0);
			clearHandle();
		}
		
	}
}
