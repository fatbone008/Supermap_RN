package com.supermap.data;

public class SymbolMarkerStroke extends InternalHandleDisposable{

	/**
	 * ���캯��
	 * @param data
	 */
	/*public*/ SymbolMarkerStroke(Geometry data) {
		if(data == null) {
			String message = InternalResource.loadString("data",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long dataHandle = InternalHandleDisposable.getHandle(data);
		if(dataHandle == 0) {
			String message = InternalResource.loadString("data",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		long handle = SymbolMarkerStrokeNative.jni_New();
		this.setHandle(handle, true);
	}
	
	/**
	 * �ڲ�ʹ�ù��캯��
	 * @param handle
	 */
	SymbolMarkerStroke(long handle) {
		this.setHandle(handle, false);
	}
	
	/**
	 * ��ȡһ��ֵ����ʾ�ʻ�����������
	 * @return
	 */
	public StrokeType getType() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getType()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugcValue = SymbolMarkerStrokeNative.jni_GetType(this.getHandle());
		return (StrokeType) Enum.parseUGCValue(StrokeType.class, ugcValue);
	}
	
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			SymbolMarkerStrokeNative.jni_Delete(getHandle());
			this.clearHandle();
		}
		
	}

}
