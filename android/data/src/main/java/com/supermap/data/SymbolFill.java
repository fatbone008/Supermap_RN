package com.supermap.data;

import android.graphics.Bitmap;


public class SymbolFill extends Symbol {

	// public SymbolFill() {
	// this.setHandle(SymbolFillNative.jni_New(), true);
	// }

	SymbolFill(long handle) {
		this.setHandle(handle, false);
	}

	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			SymbolFillNative.jni_Delete(getHandle());
			setHandle(0);
			clearHandle();
		}
	}
	
	public boolean draw(Bitmap bmp) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"draw(Graphics graphics, Geometry geometry)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (bmp == null) {
			String message = InternalResource.loadString("draw",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		
		GeoRegion region = new GeoRegion();
		
		Point2Ds pts = new Point2Ds();
		pts.add(new Point2D(width*0.05, height*0.05));
		pts.add(new Point2D(width*0.05, height*0.95));
		pts.add(new Point2D(width*0.95, height*0.95));
		pts.add(new Point2D(width*0.95, height*0.05));
		region.addPart(pts);
		
		
		GeoStyle style = new GeoStyle();
		if (m_geoStyle == null) {
			// 如果用户没有设置符号风格，用默认风格绘制
			style.setFillBackColor(new com.supermap.data.Color(255, 255, 255));
			style.setFillForeColor(new com.supermap.data.Color(13, 80, 143));//Color.argb(255, 13, 50, 143)
			style.setFillSymbolID(getID());
			region.setStyle(style);
	
		} else {
			m_geoStyle.setFillSymbolID(getID()); // 防止误操作改了符号id
			region.setStyle(m_geoStyle);
		}

		SymbolLibrary symbolLibrary = getLibrary();
		long libHander = 0;
		if(symbolLibrary != null)
		{
			libHander = symbolLibrary.getHandle();
		}
		boolean result = SymbolFillNative.jni_Draw(this.getHandle(), bmp, region.getHandle(),libHander);

		style.dispose();
		region.dispose();
		
		return result;

	}

	@Override
	public SymbolType getType() {
		return SymbolType.FILL;
	}
}
