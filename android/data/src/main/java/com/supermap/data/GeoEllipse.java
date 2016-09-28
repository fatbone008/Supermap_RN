package com.supermap.data;

/**
 * 椭圆类。
 * 
 * <p>
 * 该类主要用于 CAD 图层，是 Geometry 对象的子对象。
 * 
 */
public class GeoEllipse extends Geometry {

	/**
	 * 构造一个椭圆类的新对象。
	 */
	public GeoEllipse() {
		long handle = GeoEllipseNative.jni_New();
		this.setHandle(handle, true);
	}

	/**
	 * 根据给定的椭圆对象构造一个与其完全相同的椭圆对象。
	 * 
	 * @param geoEllipse
	 *            给定的椭圆对象。
	 */
	public GeoEllipse(GeoEllipse geoEllipse) {
		if (geoEllipse == null) {
			String message = InternalResource.loadString("geoEllipse",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = InternalHandle.getHandle(geoEllipse);
		if (handle == 0) {
			String message = InternalResource.loadString(
					"GeoEllipse geoEllipse",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		long clone = GeoEllipseNative.jni_Clone(handle);
		this.setHandle(clone, true);
	}

	/**
	 * 根据给定的圆心、长半轴、短半轴和旋转角度来构造椭圆类的新对象。
	 * 
	 * @param center
	 *            椭圆的圆心。
	 * @param semimajorAxis
	 *            长半轴。
	 * @param semiminorAxis
	 *            短半轴。
	 * @param angle
	 *            旋转角度。
	 */
	public GeoEllipse(Point2D center, double semimajorAxis,
			double semiminorAxis, double rotation) {
		if (semimajorAxis <= 0) {
			String message = InternalResource.loadString("semimajorAxis",
					InternalResource.GeoEllipseSemiMajorAxisShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (semiminorAxis <= 0) {
			String message = InternalResource.loadString("semiminorAxis",
					InternalResource.GeoEllipseSemiMinorAxisShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoEllipseNative.jni_New1(center.getX(), center.getY(),
				semimajorAxis, semiminorAxis, rotation);
		this.setHandle(handle, true);

	}

	/**
	 * 根据给定的二维矩形几何对象来构造椭圆类的新对象。
	 * <P>
	 * 椭圆对象的旋转角度默认为 GeoRectangle 对象的旋转角度。
	 * 
	 * @param geoRectangle
	 *            指定的用于确定椭圆对象的矩形。
	 * @see GeoRectangle
	 */
	public GeoEllipse(GeoRectangle geoRectangle) {
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

		long thisHandle = GeoEllipseNative.jni_New2(handle);
		this.setHandle(thisHandle, true);
	}

	/**
	 * 根据给定的矩形来构造椭圆类的新对象。
	 * 
	 * @param rectangle
	 *            指定的用于确定椭圆对象的矩形。
	 */
	public GeoEllipse(Rectangle2D rectangle) {
		if (rectangle == null) {
			String message = InternalResource.loadString("rectangle",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long thisHandle = GeoEllipseNative
				.jni_New3(rectangle.getLeft(), rectangle.getRight(), rectangle
						.getTop(), rectangle.getBottom());
		this.setHandle(thisHandle, true);
	}

	GeoEllipse(long handle) {
		setHandle(handle, false);
	}

	/**
	 * 返回椭圆的圆心。
	 * 
	 * @return 返回椭圆的圆心。
	 */
	public Point2D getCenter() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetCenter()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] center = new double[2];
		GeoEllipseNative.jni_getCenter(this.getHandle(), center);
		return new Point2D(center[0], center[1]);
	}

	/**
	 * 设置椭圆的圆心。
	 * 
	 * @param point2D
	 *            椭圆的圆心。
	 */
	public void setCenter(Point2D point2D) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"SetCenter(Point2D point2D)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoEllipseNative.jni_setCenter(this.getHandle(), point2D.getX(),
				point2D.getY());
	}

	/**
	 * 返回椭圆的长半轴。
	 * 
	 * @return 返回椭圆的长半轴。
	 */
	public double getSemimajorAxis() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetSemimajorAxis()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoEllipseNative.jni_getSemimajorAxis(this.getHandle());
	}

	/**
	 * 设置椭圆的长半轴。
	 * 
	 * @param value
	 *            椭圆的长半轴。
	 */
	public void setSemimajorAxis(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"SetSemimajorAxis(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <= 0) {
			String message = InternalResource.loadString("value",
					InternalResource.GeoEllipseSemiMajorAxisShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoEllipseNative.jni_setSemimajorAxis(this.getHandle(), value);
	}

	/**
	 * 返回椭圆的短半轴。
	 * 
	 * @return 返回椭圆的短半轴。
	 */
	public double getSemiminorAxis() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetSemiminorAxis()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoEllipseNative.jni_getSemiminorAxis(this.getHandle());

	}

	/**
	 * 设置椭圆的短半轴。
	 * 
	 * @param value
	 *            椭圆的短半轴。
	 */
	public void setSemiminorAxis(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"SetSemiminorAxis(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <= 0) {
			String message = InternalResource.loadString("value",
					InternalResource.GeoEllipseSemiMinorAxisShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoEllipseNative.jni_setSemiminorAxis(this.getHandle(), value);
	}

	/**
	 * 返回椭圆的旋转角度。
	 * 
	 * @return 返回椭圆的旋转角度。
	 */
	public double getRotation() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getRotation()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoEllipseNative.jni_getAngle(this.getHandle());
	}

	/**
	 * 设置椭圆的旋转角度。
	 * 
	 * @param value
	 *            椭圆的旋转角度。
	 */
	public void setRotation(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setRotation(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
//		根据UGON-1833做出调整
		GeoEllipseNative.jni_setAngle(this.getHandle(), value);
	}

	/**
	 * 返回椭圆的周长。
	 * 
	 * @return 返回椭圆的周长。
	 */
	public double getPerimeter() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetPerimeter()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoEllipseNative.jni_getPerimeter(this.getHandle());

	}

	/**
	 * 返回椭圆的面积。
	 * 
	 * @return 返回椭圆的面积。
	 */
	public double getArea() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetArea()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoEllipseNative.jni_getArea(this.getHandle());
	}

	/**
	 * 将椭圆对象转换为线对象。
	 * 
	 * @param segmentCount
	 *            等分椭圆的段数。
	 * @return 返回线对象。
	 */
	public GeoLine convertToLine(int segmentCount) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"ConvertToLine(int segmentCount)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (segmentCount < 2){
			String message = InternalResource.loadString(
					"ConvertToLine(int segmentCount)",
					InternalResource.GeoEllipseGeoLineSegmentCountShouldGreaterThanOne,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		long handle = GeoEllipseNative.jni_convertToLine(this.getHandle(),
				segmentCount);
		GeoLine geoLine =null;
		if (handle != 0) {
			 geoLine = new GeoLine(handle);
			geoLine.setHandle(handle, true);
		}
		return geoLine;
	}

	/**
	 * 将椭圆对象转换为面对象。
	 * 
	 * @param segmentCount
	 *            等分椭圆的段数。
	 * @return 返回面对象。
	 */
	public GeoRegion convertToRegion(int segmentCount) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"ConvertToRegion(int segmentCount)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (segmentCount < 2){
			String message = InternalResource.loadString(
					"convertToRegion(int segmentCount)",
					InternalResource.GeoEllipseGeoRegionSegmentCountShouldGreaterThanOne,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		long handle = GeoEllipseNative.jni_convertToRegion(this.getHandle(),
				segmentCount);
		GeoRegion geoRegion =null;
		if (handle != 0) {
			geoRegion = new GeoRegion(handle);
			geoRegion.setHandle(handle, true);
		} 
		return geoRegion;
	}

	@Override
	public GeoEllipse clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("Clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoEllipse(this);
	}

	@Override
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoEllipseNative.jni_Delete(getHandle());
			this.setHandle(0);
			clearHandle();
		}
	}

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
