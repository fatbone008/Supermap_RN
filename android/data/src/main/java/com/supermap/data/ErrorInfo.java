package com.supermap.data;

public class ErrorInfo extends InternalHandle {
	
	private int m_threadID;
	
	private String m_marker;
	
	private String m_message;
	
	ErrorInfo(int threadID, String marker, String message) {
		m_threadID = threadID;
		m_marker = marker;
		m_message = message;
	}

	public String getMarker() {
		return m_marker;
	}

	public String getMessage() {
		return m_message;
	}

	public int getThreadID() {
		return m_threadID;
	}

//	public int getThreadID() {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString("getThreadID()",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		return ErrorInfoNative.jni_GetThreadID(this.getHandle());
//	}
//
//	public String getMarker() {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString("getMarker()",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		return ErrorInfoNative.jni_GetMarker(this.getHandle());
//	}
//	
//	public String getMessage() {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"getMessage()",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		return ErrorInfoNative.jni_GetMessage(this.getHandle());
//	}
}
