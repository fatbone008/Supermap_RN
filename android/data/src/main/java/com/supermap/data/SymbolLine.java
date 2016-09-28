package com.supermap.data;

import android.graphics.Bitmap;


public class SymbolLine extends Symbol {

	// public SymbolLine() {
	// this.setHandle(SymbolLineNative.jni_New(), true);
	// }

	protected SymbolLine(long handle) {
		this.setHandle(handle, false);

	}


	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			SymbolLineNative.jni_Delete(getHandle());
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
		
		GeoLine line = new GeoLine();
		Point2Ds pts = new Point2Ds();
		pts.add(new Point2D(width*0.1, height/2));
		pts.add(new Point2D(width*0.9, height/2));
		line.addPart(pts);
		
		GeoStyle style = new GeoStyle();
		if (m_geoStyle == null) {
			// 如果用户没有设置符号风格，用默认风格绘制
			style.setLineColor(new Color(13, 80, 143));
			style.setLineWidth(0.5);
			style.setLineSymbolID(getID());
			line.setStyle(style);
	
		} else {
			m_geoStyle.setLineSymbolID(getID()); // 防止误操作改了符号id
			line.setStyle(m_geoStyle);
		}
		
		boolean result = SymbolLineNative.jni_Draw(this.getHandle(), bmp, line.getHandle());
		
		style.dispose();
		line.dispose();
		
		return result;

	}

	@Override
	public SymbolType getType() {
		// TODO Auto-generated method stub
		return SymbolType.LINE;
	}

	int getCount(){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"draw(Graphics graphics, Geometry geometry)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return SymbolLineNative.jni_GetCount(getHandle());
	}
	
	SymbolLineBase get(int index){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"draw(Graphics graphics, Geometry geometry)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if(index>=getCount()||index<0)
			return null;
		long handle =  SymbolLineNative.jni_Get(getHandle(),index);
		if(handle == 0)
			return null;	
		return new SymbolLineBase(handle);
	}
}
