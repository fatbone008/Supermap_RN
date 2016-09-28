package com.supermap.data;

import java.util.ArrayList;

import android.graphics.Bitmap;


public class SymbolMarker extends Symbol {
	
	private ArrayList<SymbolMarkerStroke> m_arrayList;

	// public SymbolMarker() {
	// this.setHandle(SymbolMarkerNative.jni_New(), true);
	// }

	SymbolMarker(long handle) {
		this.setHandle(handle, false);
		refreshStrokeList(handle);
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
				
		Geometry geometry = new GeoPoint(width/2, height/2);

		GeoStyle style = new GeoStyle();
		if (m_geoStyle == null) {
			// 如果用户没有设置符号风格，用默认风格绘制
			style.setMarkerSymbolID(getID());
			style.setMarkerSize(new Size2D(width*0.9, height*0.9));
			geometry.setStyle(style);			
		} else {
			m_geoStyle.setMarkerSymbolID(getID()); // 防止误操作改了符号id
			geometry.setStyle(m_geoStyle);
		}

		boolean result = SymbolMarkerNative.jni_Draw(this.getHandle(),bmp, geometry.getHandle());
		style.dispose();
		geometry.dispose();
		
		return result;

	}

	private void refreshStrokeList(long handle) {
		if (m_arrayList != null) {
			for (int i = 0; i < m_arrayList.size(); i++) {
				SymbolMarkerStroke markerStroke = m_arrayList.get(i);
				markerStroke.clearHandle();
			}
			m_arrayList.clear();
		}
		else {
			m_arrayList = new ArrayList<SymbolMarkerStroke>();
		}
		int count = SymbolMarkerNative.jni_GetStrokesCount(handle);
		if (count > 0) {
			long[] strokeHandles = new long[count];
			SymbolMarkerNative.jni_GetStrokeHandle(handle, strokeHandles);
			for (int i = 0; i < count; i++) {
				if (strokeHandles[i] != 0) {
					SymbolMarkerStroke markerStroke = new SymbolMarkerStroke(strokeHandles[i]);
					m_arrayList.add(markerStroke);
				}
			}
		}
	}
	
	/*public*/ SymbolMarker() {
		long handle = SymbolMarkerNative.jni_New();
		this.setHandle(handle, true);
		this.setLibrary(null);
		refreshStrokeList(handle);
	}

	/**
	 * 计算出传入的符号大小在显示时的大小
	 * 
	 * @param symbolSize
	 * @return 符号大小在显示时的大小
	 */
	public int computeDisplaySize(int symbolSize) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"computeDisplaySize(int symbolSize)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (symbolSize <= 0) {
			String message = InternalResource.loadString(
					"computeDisplaySize(int symbolSize)",
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return SymbolMarkerNative.jni_ComputeDisplaySize(getHandle(),
				symbolSize);
	}

	public int computeSymbolSize(int displaySize) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"computeSymbolSize(int displaySize)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (displaySize <= 0) {
			String message = InternalResource.loadString(
					"computeSymbolSize(int displaySize)",
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return SymbolMarkerNative.jni_ComputeSymbolSize(getHandle(),
				displaySize);
	}

	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			SymbolMarkerNative.jni_Delete(getHandle());
			setHandle(0);
			clearHandle();
		}
	}
	
//	public int fromGeometry(Geometry geometry, Rectangle2D bounds) {
//		long handle = getHandle();
//		if (handle == 0) {
//			String message = InternalResource.loadString(
//					"computeSymbolSize(int displaySize)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (geometry == null) {
//			String message = InternalResource.loadString("geometry",
//					InternalResource.GlobalArgumentNull,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		long valueHandle = InternalHandle.getHandle(geometry);
//		if (valueHandle == 0) {
//			String message = InternalResource.loadString("geometry",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		
//		SymbolMarkerNative.jni_FromGeometry(handle, valueHandle, bounds.getLeft(),
//				bounds.getBottom(), bounds.getRight(), bounds.getTop());
//		this.refreshStrokeList(handle);
//		return this.getID();
//	}

	@Override
	public SymbolType getType() {
		// TODO Auto-generated method stub
		return SymbolType.MARKER;
	}
	
	/**
	 * 
	 * @return
	 */
	public Point getOrigin() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getOrigin()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int[] pt = new int[2];
		SymbolMarkerNative.jni_GetOrigin(this.getHandle(), pt);
		return new Point(pt[0], pt[1]);
	}
	
	/**
	 * 
	 * @param value
	 */
	public void setOrigin(Point value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setOrigin(Point value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value == null) {
			String message = InternalResource.loadString("value",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		SymbolMarkerNative.jni_SetOrigin(this.getHandle(), value.getX(), value.getY());
	}
	
	/**
	 * 获取一个值，标示指定索引处的画笔
	 * @param index
	 * @return
	 */
	public SymbolMarkerStroke get(int index) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("get(int index)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// 判断索引是否越界
		if (index < 0 || index >= m_arrayList.size()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		GetMatrialStream(index);
		return m_arrayList.get(index);
	}
	
	private void GetMatrialStream(int index) {
		SymbolMarkerNative.jni_GetMatrialStream(this.getHandle(),getLibrary().getLibPath(),index);
	}

	/**
	 * 获取一个值，标示点符号对象的画笔总数
	 * @return
	 */
	public int getCount() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getCount()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return m_arrayList.size();
	}
	
	/**
	 * 获取一个值，判断当前符号是否是栅格符号，true表示是
	 * @return
	 */
//	public boolean isGrid() {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString("isGrid()",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		boolean bResult = false;
//		if (this.getCount() == 1) {
//			SymbolMarkerStroke markerStroke = this.get(0);
//			if (markerStroke.getType().equals(StrokeType.ST_BITMAP)
//				|| markerStroke.getType().equals(StrokeType.ST_ICON)) {
//				
//				bResult = true;
//			}
//		}
//		
//		return bResult;
//	}
	
	protected void clearHandle() {
		if (m_arrayList != null) {
			for (int i = 0; i < m_arrayList.size(); i++) {
				SymbolMarkerStroke markerStroke = m_arrayList.get(i);
				markerStroke.clearHandle();
			}
			m_arrayList.clear();
		}
		this.setHandle(0);
	}
}
