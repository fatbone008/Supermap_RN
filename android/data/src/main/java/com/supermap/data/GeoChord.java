package com.supermap.data;

public class GeoChord extends Geometry {

	/**
	 * 默认构造函数
	 */
	public GeoChord() {
		long handle = GeoChordNative.jni_New();
		this.setHandle(handle, true);
	}
	
	/**
	 * 拷贝构造函数
	 * @param geoChord GeoChord
	 */
	public GeoChord(GeoChord geoChord) {
		if(geoChord == null) {
			String message = InternalResource.loadString("geoChord",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long geoChordHandle = InternalHandleDisposable.getHandle(geoChord);
		if(geoChordHandle == 0) {
			String message = InternalResource.loadString("geoChord",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long cloneHandle = GeoChordNative.jni_Clone(geoChordHandle);
		setHandle(cloneHandle, true);
	}
	
	/**
	 * 构造函数，通过弓形所在椭圆的圆心、长半轴、短半轴、
	 * 弓形对应的椭圆弧的起始角度、终止角度和旋转角度来构建一个弓形
	 * @param center
	 * @param semimajorAxis
	 * @param semiminorAxis
	 * @param startEngle
	 * @param endEngle
	 * @param angle
	 */
	public GeoChord(Point2D center,double semimajorAxis,double semiminorAxis,double startAngle,double sweepAngle,double angle) {
		double x = center.getX();
		double y = center.getY();
		if (semimajorAxis <= 0) {
			String message = InternalResource.loadString("semimajorAxis",
					InternalResource.GeoChordSemiMajorAxisShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (semiminorAxis <= 0) {
			String message = InternalResource.loadString("semiminorAxis",
					InternalResource.GeoChordSemiMinorAxisShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

//		if (endAngle < 0 || endAngle > 360) {
//			String message = InternalResource.loadString("endAngle",
//					InternalResource.GeoChordArgumentOutOfBounds,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
		if (sweepAngle <= -360 || sweepAngle >= 360) {
			String message = InternalResource.loadString("sweepAngle",
					InternalResource.GeoChordSweepAngleRange,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (sweepAngle == 0) {
			String message = InternalResource.loadString("sweepAngle",
					InternalResource.GeoChordSweepAngleShouldNotBeZero,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		long handle = GeoChordNative.jni_New2(x, y, semimajorAxis, 
				semiminorAxis, startAngle, sweepAngle, angle);
//		if (handle == 0) {
//			String message = InternalResource.loadString(
//					"GeoChord(Point2D center,double semimajorAxis,double semiminorAxis,double startEngle,double endEngle,double angle)", 
//					InternalResource.FailConstruct,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
		setHandle(handle, true);
	}
	
	/**
	 * 内部使用构造函数
	 * @param handle
	 */
	GeoChord(long handle) {
		this.setHandle(handle, false);
	}
	
	/**
	 * 返回弓形所在椭圆的圆心
	 * @return Point2D
	 */
	public Point2D getCenter() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getCenter()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] pt = new double[2];
		GeoChordNative.jni_GetCenter(this.getHandle(), pt);
		Point2D point = new Point2D();
		point.setX(pt[0]);
		point.setY(pt[1]);
		return point;
	}
	/**
	 * 设置弓形所在椭圆的圆心
	 * @param center Point2D
	 */
	public void setCenter(Point2D center) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setCenter(Point2D center)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double x = center.getX();
		double y = center.getY();
		GeoChordNative.jni_SetCenter(this.getHandle(), x, y);
	}
	
	/**
	 * 返回弓形所在椭圆的长半轴
	 * @return double
	 */
	public double getSemimajorAxis() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getSemimajorAxis()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoChordNative.jni_GetSemimajorAxis(this.getHandle());
	}
	
	/**
	 * 设置弓形所在椭圆的长半轴
	 * @param value double
	 */
	public void setSemimajorAxis(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setSemimajorAxis(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <= 0) {
			String message = InternalResource.loadString("value",
					InternalResource.GeoChordSemiMajorAxisShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoChordNative.jni_SetSemimajorAxis(this.getHandle(), value);
	}
	
	/**
	 * 返回弓形所在椭圆的短半轴
	 * @return double
	 */
	public double getSemiminorAxis() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getSemiminorAxis()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoChordNative.jni_GetSemiminorAxis(this.getHandle());
	}
	
	/**
	 * 设置弓形所在椭圆的短半轴
	 * @param value double
	 */
	public void setSemiminorAxis(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setSemiminorAxis(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <= 0) {
			String message = InternalResource.loadString("value",
					InternalResource.GeoChordSemiMinorAxisShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoChordNative.jni_SetSemiminorAxis(this.getHandle(), value);
	}
	
	/**
	 * 返回弓形对应的椭圆弧的起始角度
	 * @return double
	 */
	public double getStartAngle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getStartAngle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		return GeoChordNative.jni_GetStartAngle(this.getHandle());
	}
	
	/**
	 * 设置弓形对应的椭圆弧的起始角度
	 * @param value double
	 */
	public void setStartAngle(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setStartAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoChordNative.jni_SetStartAngle(this.getHandle(), value);
	}
	
	/**
	 * 返回弓形对应椭圆弧扫过的角度
	 * @return double
	 */
	public double getSweepAngle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSweepAngle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoChordNative.jni_GetSweepAngle(this.getHandle());
	}
	
	/**
	 * 设置弓形对应椭圆弧扫过的角度
	 * @param value
	 */
	public void setSweepAngle(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setSweepAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <= -360 || value >= 360) {
			String message = InternalResource.loadString("sweepAngle",
					InternalResource.GeoChordSweepAngleRange,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value == 0) {
			String message = InternalResource.loadString("sweepAngle",
					InternalResource.GeoChordSweepAngleShouldNotBeZero,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoChordNative.jni_SetSweepAngle(this.getHandle(), value);
	}
	
//	/**
//	 * 返回弓形对应的椭圆弧终止角度
//	 * @return double
//	 */
//	public double getEndAngle() {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString("getEndAngle()",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		return GeoChordNative.jni_GetEndAngle(this.getHandle());
//	}
//	
//	/**
//	 * 设置弓形对应的椭圆弧终止角度
//	 * @param value double
//	 */
//	public void setEndAngle(double value) {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"setEndAngle(double value)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if(value < 0 || value > 360) {
//			String message = InternalResource.loadString("value",
//					InternalResource.GeoChordArgumentOutOfBounds,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		GeoChordNative.jni_SetEndAngle(this.getHandle(), value);
//	}
	
	/**
	 * 返回弓形的旋转角度
	 * @return double
	 */
	public double getRotation() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getRotation()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoChordNative.jni_GetRotation(this.getHandle());
	}
	
	/**
	 * 设置弓形的旋转角度
	 * @param value double
	 */
	public void setRotation(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoChordNative.jni_SetRotation(this.getHandle(), value);
	}
	
	/**
	 * 返回弓形的周长
	 * @return double
	 */
	public double getPerimeter() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getPerimeter()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoChordNative.jni_GetPerimeter(this.getHandle());
	}
	
	/**
	 * 返回弓形的面积
	 * @return double
	 */
	public double getArea() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getArea()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoChordNative.jni_GetArea(this.getHandle());
	}
	
	/**
	 * 转换为线对象
	 * @param segmentCount int
	 * @return GeoLine
	 */
	public GeoLine convertToLine(int segmentCount) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("convertToLine(int segmentCount)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (segmentCount < 2) {
			String message = InternalResource.loadString("segmentCount",
					InternalResource.GlobalArgumentShouldNotSmallerThanOne,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long geoLineHandle = GeoChordNative.jni_ConvertToLine(getHandle(), segmentCount);
		//当前的GeoChord若为空，返回一个空的GeoLine
		GeoLine geoLine = null;
		if(geoLineHandle != 0) {
            geoLine = new GeoLine(geoLineHandle);
            //注意：每次都得到一个新的GeoLine，为可释放对象。
            geoLine.setIsDisposable(true);
		}
		return geoLine;
	}
	
	/**
	 * 转换为面对象
	 * @param segmentCount int
	 * @return GeoRegion
	 */
	public GeoRegion convertToRegion(int segmentCount) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("convertToRegion(int segmentCount)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (segmentCount < 2) {
			String message = InternalResource.loadString("segmentCount",
					InternalResource.GlobalArgumentShouldNotSmallerThanTwo,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long geoRegionHandle = GeoChordNative.jni_ConvertToRegion(getHandle(), segmentCount);
		//当前的GeoChord若为空，返回一个空的GeoLine
		GeoRegion geoRegion = null;
		if(geoRegionHandle != 0) {
            geoRegion = new GeoRegion(geoRegionHandle);
            //注意：每次都得到一个新的GeoRegion，为可释放对象。
            geoRegion.setIsDisposable(true);
		}
		return geoRegion;
	}
	@Override
	public GeoChord clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoChord(this);
	}

	@Override
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoChordNative.jni_Delete(getHandle());
			setHandle(0);
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
