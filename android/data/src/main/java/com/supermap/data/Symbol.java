package com.supermap.data;

import android.graphics.Bitmap;

public abstract class Symbol extends InternalHandleDisposable {

	private SymbolLibrary m_lib;

	private SymbolGroup m_group;
	
	protected GeoStyle m_geoStyle;

	protected Symbol() {

	}

	public String getName() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getName()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return SymbolNative.jni_getName(this.getHandle());

	}

	// public void setID(int value) {
	// if (this.getHandle() == 0) {
	// String message = InternalResource.loadString("SetID(int value)",
	// InternalResource.HandleObjectHasBeenDisposed,
	// InternalResource.BundleName);
	// throw new IllegalStateException(message);
	// }
	// if (value < 0) {
	// String message = InternalResource.loadString("SetID(int value)",
	// InternalResource.SymbolIDShouldNotBeNegative,
	// InternalResource.BundleName);
	// throw new IllegalStateException(message);
	// }
	// if (m_lib != null) {
	// if (m_lib.contains(value)) {
	// String message = InternalResource.loadString("value",
	// InternalResource.SymbolLibraryHasContaintID,
	// InternalResource.BundleName);
	// throw new IllegalArgumentException(message);
	// }
	// }
	// SymbolNative.jni_setID(this.getHandle(),m_lib.getHandle(),m_group.getHandle(),
	// value);
	// }

	public int getID() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getID()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return SymbolNative.jni_getID(this.getHandle());
	}

	public SymbolLibrary getLibrary() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getLibrary()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return m_lib;
	}

	public abstract SymbolType getType();
	
	public abstract boolean draw(Bitmap bmp);

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{Name=");
		buffer.append(this.getName());
		buffer.append(",ID=");
		buffer.append(this.getID());
		buffer.append("}");
		return buffer.toString();
	}
	
	static final Symbol createInstance(Symbol symbol){
		long newSymbolHandle = SymbolNative.createInstance(symbol.getHandle());
		return createInstance(newSymbolHandle);
	}

	// 内部使用，返回Geometry
	static final Symbol createInstance(long symbolHandle) {
		if (symbolHandle == 0) {
			return null;
		}
		int ugcType = SymbolNative.jni_GetType(symbolHandle);

		Symbol symbol = null;
		// 此处用不能用switch case,故采用的是if else
		if (ugcType == 0) {
			symbol = new SymbolMarker(symbolHandle);
		} else if ((ugcType == 1) || (ugcType == 4)) { // 1为线型，4为三维线型
			symbol = new SymbolLine(symbolHandle);
		} else if (ugcType == 2) {
			symbol = new SymbolFill(symbolHandle);
		}
		
		if (symbol != null) {
			// 目前所用到的都是需要释放的
			symbol.setIsDisposable(true);
		}
		return symbol;
	}

	// 内部使用
	void setLibrary(SymbolLibrary lib) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setLibrary()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		m_lib = lib;
	}

	// 内部使用
	protected void setGroup(SymbolGroup group) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setLibrary()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		m_group = group;
	}

	protected SymbolGroup getGroup() {
		return m_group;
	}
	
	public void setSymbolStyle(GeoStyle style) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setSymbolStyle(GeoStyle style)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		if (style == null) {
			if (m_geoStyle != null) {
				m_geoStyle.clearHandle();
				m_geoStyle = null;
			}
		}
		m_geoStyle = style;
	}
	
	protected void clearHandle() {
		if (m_geoStyle != null) {
			m_geoStyle.clearHandle();
			m_geoStyle = null;
		}
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
		clearHandle();
	}
}
