package com.supermap.data;

public class SymbolLineBase extends InternalHandleDisposable {
	
	SymbolLineBase(long handle){
		setHandle(handle,false);
	}
	
//	public SymbolLineBase() {
//		long handle = SymbolLineBaseNative.jni_New();
//		setHandle(handle, true);
//	}

	public int getCode(){
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getCode",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return SymbolLineBaseNative.jni_GetCode(getHandle());
	}
	
	public void setCode(int asciiCode){
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getCode",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		SymbolLineBaseNative.jni_SetCode(getHandle(),asciiCode);
	}
	
	public void dispose() {	
		if (!getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (getHandle() != 0) {
			SymbolLineBaseNative.jni_Delete(getHandle());
			clearHandle();
		}	
	}
}
