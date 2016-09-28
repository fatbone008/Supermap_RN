package com.supermap.data;

/**
 * 二维矩形几何类。
 * <p>
 * 该类主要用于 CAD 图层，是 Geometry 对象的子对象。
 */
public class GeoRectangle extends Geometry {
	// 该对象主要用于 CAD 图层，是Geometry对象的子对象
	/**
	 * 构造一个二维矩形几何类的新对象。
	 */
	public GeoRectangle() {
		long handle = GeoRectangleNative.jni_New();
		this.setHandle(handle, true);
	}

	/**
	 * 根据给定的二维矩形几何对象构造一个与其完全相同的二维矩形几何对象。
	 * 
	 * @param geoRectangle
	 *            给定的二维矩形几何对象。
	 */
	public GeoRectangle(GeoRectangle geoRectangle) {
		if (geoRectangle == null) {
			String message = InternalResource.loadString("geoRectangle",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = InternalHandle.getHandle(geoRectangle);
		if (handle == 0) {
			String message = InternalResource.loadString(
					"GeoRectangle geoRectangle",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		long thisHandle = GeoRectangleNative.jni_Clone(handle);
		this.setHandle(thisHandle, true);
	}

	/**
	 * 根据给定的中心点、二维宽高和旋转角度来构造二维矩形几何类的新对象。
	 * 
	 * @param center
	 *            中心点。
	 * @param width
	 *            旋转前二维矩形几何对象的宽度。
	 * @param height
	 *            旋转前二维矩形几何对象的高度。
	 * @param angle
	 *            二维矩形几何对象的旋转角度。
	 */
	public GeoRectangle(Point2D center, double width, double height,
			double angle) {
		if (width <= 0) {
			String message = InternalResource.loadString("width",
					InternalResource.GeoRectangleWidthShouldPositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (height <= 0) {
			String message = InternalResource.loadString("height",
					InternalResource.GeoRectangleHeightShouldPositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoRectangleNative.jni_New1(center.getX(), center.getY(),
				width, height, angle);
		this.setHandle(handle, true);
	}
//
//	/**
//	 * 根据旋转前左下角点的坐标和二维矩形几何对象的宽度、高度来构造二维矩形几何类的新对象。此时旋转角度默认为0。
//	 * 
//	 * @param leftBottom
//	 *            旋转前二维矩形几何对象的左下角点坐标。
//	 * @param width
//	 *            旋转前二维矩形几何对象的宽度。
//	 * @param height
//	 *            旋转前二维矩形几何对象的高度。
//	 */
//	public GeoRectangle(Point2D point, double width, double height) {
//		if (width <= 0) {
//			String message = InternalResource.loadString("width",
//					InternalResource.GeoRectangleWidthShouldPositive,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		if (height <= 0) {
//			String message = InternalResource.loadString("height",
//					InternalResource.GeoRectangleHeightShouldPositive,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		Rectangle2D rectangle = new Rectangle2D(point, width, height);
//		long handle = GeoRectangleNative
//				.jni_New2(rectangle.getLeft(), rectangle.getRight(), rectangle
//						.getTop(), rectangle.getBottom());
//		this.setHandle(handle, true);
//	}
//
//	/**
//	 * 根据旋转前左下角点的坐标和右上角点的坐标来构造二维矩形几何类的新对象。此时旋转角度默认为0。
//	 * 
//	 * @param leftBottom
//	 *            旋转前二维矩形几何对象左下角点的坐标。
//	 * @param rightTop
//	 *            旋转前二维矩形几何对象右上角点的坐标。
//	 */
//	public GeoRectangle(Point2D leftBottom, Point2D rightTop) {
//		if(leftBottom.getX()>=rightTop.getX()){
//			String message = InternalResource.loadString("width",
//					InternalResource.GeoRectangleWidthShouldPositive,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		if(leftBottom.getY()>=rightTop.getY()){
//			String message = InternalResource.loadString("height",
//					InternalResource.GeoRectangleHeightShouldPositive,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		Rectangle2D rectangle = new Rectangle2D(leftBottom, rightTop);
//		long handle = GeoRectangleNative
//				.jni_New2(rectangle.getLeft(), rectangle.getRight(), rectangle
//						.getTop(), rectangle.getBottom());
//		setHandle(handle, true);
//
//	}
//
//	/**
//	 * 根据旋转前的左侧 X 轴坐标值，底侧 Y 轴坐标值，右侧 X 轴坐标值，上侧 Y 轴坐标值来构造二维矩形几何类的新对象。此时旋转角度默认为0。
//	 * 
//	 * @param left
//	 *            二维矩形几何对象旋转前的左侧 X 轴坐标值。
//	 * @param bottom
//	 *            二维矩形几何对象旋转前的底侧 Y 轴坐标值。
//	 * @param right
//	 *            二维矩形几何对象旋转前的右侧 X 轴坐标值。
//	 * @param top
//	 *            二维矩形几何对象旋转前的上侧 Y 轴坐标值。
//	 */
//	public GeoRectangle(double left, double bottom, double right, double top) {
//		if(left>=right){
//			String message = InternalResource.loadString("width",
//					InternalResource.GeoRectangleWidthShouldPositive,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		if(bottom>=top){
//			String message = InternalResource.loadString("height",
//					InternalResource.GeoRectangleHeightShouldPositive,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		long handle = GeoRectangleNative.jni_New2(left, right, top, bottom);
//		setHandle(handle, true);
//	}

	/**
	 * 根据一个 Rectangle 对象来构造二维矩形几何类的新对象。此时旋转角度默认为0。
	 * 
	 * @param rectangle
	 *            矩形对象。
	 */
	public GeoRectangle(Rectangle2D rectangle,double rotation) {
		if(rectangle.getWidth()<=0){
			String message = InternalResource.loadString("rectangle width",
					InternalResource.GeoRectangleWidthShouldPositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if(rectangle.getHeight()<=0){
			String message = InternalResource.loadString("rectangle height",
					InternalResource.GeoRectangleWidthShouldPositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoRectangleNative
				.jni_New2(rectangle.getLeft(), rectangle.getRight(), rectangle
						.getTop(), rectangle.getBottom(),rotation);
		setHandle(handle, true);
	}

	GeoRectangle(long handle) {
		this.setHandle(handle, false);
	}

	/**
	 * 将二维矩形几何对象转换为线对象。
	 * 
	 * @return 返回线对象。
	 */
	public GeoLine convertToLine() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("convertToLine()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		long handle = GeoRectangleNative.jni_ConvertToLine(this.getHandle());
		GeoLine geoLine  = null;
		if (handle != 0) {
			geoLine = new GeoLine(handle);
			geoLine.setIsDisposable(true);
		} 
		return geoLine;
	}

	/**
	 * 将二维矩形几何对象转换为面对象。
	 * 
	 * @return 返回面对象。
	 */
	public GeoRegion convertToRegion() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("convertToRegion()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		long handle = GeoRectangleNative.jni_ConvertToRegion(this.getHandle());
		GeoRegion region  = null;
		if (handle != 0) {
			region = new GeoRegion(handle);
			region.setIsDisposable(true);
		} 
		return region;
	}

	/**
	 * 返回二维矩形几何对象的中心点。
	 * 
	 * @return 返回二维矩形几何对象的中心点。
	 */
	public Point2D getCenter() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getCenter()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] center = new double[2];
		GeoRectangleNative.jni_GetCenter(this.getHandle(), center);
		return new Point2D(center[0], center[1]);
	}

	/**
	 * 设置二维矩形几何对象的中心点。
	 * 
	 * @param point2D
	 *            二维矩形几何对象的中心点。
	 */
	public void setCenter(Point2D value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setCenter(Point2D value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoRectangleNative.jni_SetCenter(this.getHandle(), value.getX(), value
				.getY());
	}

	/**
	 * 返回二维矩形几何对象的宽度，是指矩形对象旋转前的宽度。
	 * 
	 * @return 返回二维矩形几何对象的宽度。
	 */
	public double getWidth() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getWidth()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoRectangleNative.jni_GetWidth(getHandle());
	}

	/**
	 * 设置二维矩形几何对象的宽度，是指矩形对象旋转前的宽度。
	 * 
	 * @param value
	 *            二维矩形几何对象的宽度。
	 */
	public void setWidth(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setWidth(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <= 0) {
			String message = InternalResource.loadString("value",
					InternalResource.GeoRectangleWidthShouldPositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoRectangleNative.jni_SetWidth(getHandle(), value);
	}

	/**
	 * 返回二维矩形几何对象的高度，是指矩形对象旋转前的高度。
	 * 
	 * @return 返回二维矩形几何对象的高度。
	 */
	public double getHeight() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getHeight()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoRectangleNative.jni_GetHeight(this.getHandle());
	}

	/**
	 * 设置二维矩形几何对象的高度，是指矩形对象旋转前的高度。
	 * 
	 * @param value
	 *            二维矩形几何对象的高度。
	 */
	public void setHeight(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setHeight(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <= 0) {
			String message = InternalResource.loadString("value",
					InternalResource.GeoRectangleHeightShouldPositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoRectangleNative.jni_SetHeight(this.getHandle(), value);
	}

	/**
	 * 返回二维矩形几何对象的旋转角度。
	 * 
	 * @return 返回二维矩形几何对象的旋转角度。
	 */
	public double getRotation() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getRotation()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoRectangleNative.jni_GetAngle(this.getHandle());
	}

	/**
	 * 设置二维矩形几何对象的旋转角度。
	 * 
	 * @param value
	 *            二维矩形几何对象的旋转角度。
	 */
	public void setRotation(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setRotation(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoRectangleNative.jni_SetAngle(this.getHandle(), value);
	}

	public double getArea(){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getArea()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoRectangleNative.jni_getArea(this.getHandle());
	}
	public double getPerimeter(){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getPerimeter()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoRectangleNative.jni_getPerimeter(this.getHandle());
	}
	@Override
	public GeoRectangle clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("Clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoRectangle(this);
	}

	@Override
	public void dispose() {

		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoRectangleNative.jni_Delete(getHandle());
			this.setHandle(0);
			clearHandle();
		}
	}

	// @Override
	// public boolean isEmpty() {
	// if (this.getHandle() == 0) {
	// String message = InternalResource.loadString("IsEmpty()",
	// InternalResource.HandleObjectHasBeenDisposed,
	// InternalResource.BundleName);
	// throw new IllegalStateException(message);
	// }
	// if( (this.getAngle()==0)
	// &&this.getCenter().getX()==0
	// &&this.getCenter().getY()==0
	// &&this.getWidth()==0
	// &&this.getHeight()==0){
	// return true;
	//			
	// }
	// else{
	// return false;
	// }
	// }
	//
	// @Override
	// public void setEmpty() {
	// if (this.getHandle() == 0) {
	// String message = InternalResource.loadString("setEmpty()",
	// InternalResource.HandleObjectHasBeenDisposed,
	// InternalResource.BundleName);
	// throw new IllegalStateException(message);
	// }
	// GeoRectangleNative.jni_Clear(getHandle());
	// }

	protected void clearHandle() {
		super.clearHandle();
	}

	@Override
	public boolean fromJson(String json) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}
}
