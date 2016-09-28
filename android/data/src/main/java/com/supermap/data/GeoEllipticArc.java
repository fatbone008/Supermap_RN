package com.supermap.data;

/**
 * 椭圆弧类。
 * 
 * <p>
 * 该类主要用于 CAD 图层，是 Geometry 对象的子对象。
 * 
 */
public class GeoEllipticArc extends Geometry {

	private double m_startAngle;
	
	private double m_sweepAngle;
	
	/**
	 * 构造一个椭圆弧类的新对象。
	 */
	public GeoEllipticArc() {
		long handle = GeoEllipticArcNative.jni_New();
		this.setHandle(handle, true);
		this.m_startAngle = 0;
		this.m_sweepAngle  = 180;
	}

	/**
	 * 根据给定的椭圆弧对象构造一个与其完全相同的椭圆弧对象。
	 * 
	 * @param geoEllipticArc
	 *            给定的椭圆弧对象。
	 */
	public GeoEllipticArc(GeoEllipticArc geoEllipticArc) {
		if (geoEllipticArc == null) {
			String message = InternalResource.loadString("geoEllipticArc",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long geoEllipticArchandle = InternalHandle.getHandle(geoEllipticArc);
		if (geoEllipticArchandle == 0) {
			String message = InternalResource.loadString("geoEllipticArc",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		this.m_startAngle = geoEllipticArc.getStartAngle();
		this.m_sweepAngle = geoEllipticArc.getSweepAngle();
		
		long handle = GeoEllipticArcNative.jni_Clone(geoEllipticArchandle);
		this.setHandle(handle, true);
	}

	/**
	 * 根据给定的圆心、长半轴、短半轴、起始角度、终止角度和旋转角度来构造椭圆弧类的新对象。
	 * 
	 * @param center
	 *            椭圆弧的圆心。
	 * @param semimajorAxis
	 *            短半轴。
	 * @param semiminorAxis
	 *            长半轴。
	 * @param startEngle
	 *            起始角度。
	 * @param endEngle
	 *            终止角度。
	 * @param angle
	 *            旋转角度。
	 */
	public GeoEllipticArc(Point2D center, double semimajorAxis,
			double semiminorAxis, double startAngle, double sweepAngle,
			double rotation) {
		if (semimajorAxis <= 0) {
			String message = InternalResource.loadString("semimajorAxis",
					InternalResource.GeoEllipticArcSemiMajorAxisShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (semiminorAxis <= 0) {
			String message = InternalResource.loadString("semiminorAxis",
					InternalResource.GeoEllipticArcSemiMinorAxisShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
//		if (startAngle < 0||startAngle>360) {
//			String message = InternalResource.loadString("value",
//					InternalResource.GeoEllipticArcStartAngleShouldBe0_360,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
		if (sweepAngle <=-360||sweepAngle>=360) {
			String message = InternalResource.loadString("sweepAngle",
					InternalResource.GeoEllipticArcSweepAngleRange,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		if(sweepAngle==0){
			String message = InternalResource.loadString("sweepAngle",
					InternalResource.GeoEllipticArcStartAngleShouldNotBeZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		this.m_startAngle  = startAngle;
		this.m_sweepAngle = sweepAngle;
		
		double start = 0;
		double end = 0;
		if(sweepAngle<0){
			start = startAngle + sweepAngle;
			end = startAngle;
		}
		else{
			start = startAngle;
			end  = startAngle + sweepAngle;
		}
		long handle = GeoEllipticArcNative.jni_New1(center.getX(), center
				.getY(), semimajorAxis, semiminorAxis, start, end,
				rotation);
		this.setHandle(handle, true);
	}

	GeoEllipticArc(long handle) {
		this.setHandle(handle, false);
		this.m_startAngle = GeoEllipticArcNative.jni_getStartAngle(handle);
		this.m_sweepAngle = GeoEllipticArcNative.jni_getSweepAngle(handle);
	}

	/**
	 * 返回椭圆弧所在椭圆的圆心。
	 * 
	 * @return 返回椭圆弧所在椭圆的圆心。
	 */
	public Point2D getCenter() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetCenter()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] center = new double[2];
		GeoEllipticArcNative.jni_getCenter(this.getHandle(), center);
		return new Point2D(center[0], center[1]);
	}

	/**
	 * 设置椭圆弧所在椭圆的圆心。
	 * 
	 * @param point2D
	 *            椭圆弧所在椭圆的圆心。
	 */
	public void setCenter(Point2D point2D) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"SetCenter(Point2D point2D)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoEllipticArcNative.jni_setCenter(this.getHandle(), point2D.getX(),
				point2D.getY());
	}

	/**
	 * 返回椭圆弧所在椭圆的长半轴。
	 * 
	 * @return 返回椭圆弧所在椭圆的长半轴。
	 */
	public double getSemimajorAxis() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetSemimajorAxis()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoEllipticArcNative.jni_getSemimajorAxis(this.getHandle());
	}

	/**
	 * 设置椭圆弧所在椭圆的长半轴。
	 * 
	 * @param value
	 *            椭圆弧所在椭圆的长半轴。
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
		GeoEllipticArcNative.jni_setSemimajorAxis(this.getHandle(), value);
	}

	/**
	 * 返回椭圆弧所在椭圆的短半轴。
	 * 
	 * @return 返回椭圆弧所在椭圆的短半轴。
	 */
	public double getSemiminorAxis() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetSemiminorAxis()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoEllipticArcNative.jni_getSemiminorAxis(this.getHandle());
	}

	/**
	 * 设置椭圆弧所在椭圆的短半轴。
	 * 
	 * @param value
	 *            椭圆弧所在椭圆的短半轴。
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
		GeoEllipticArcNative.jni_setSemiminorAxis(this.getHandle(), value);
	}

	/**
	 * 返回椭圆弧的起始角度。
	 * 
	 * @return 返回椭圆弧的起始角度。
	 */
	public double getStartAngle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetStartAngle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return this.m_startAngle;
	}

	/**
	 * 设置椭圆弧的起始角度。
	 * 
	 * @param value
	 *            椭圆弧的起始角度。
	 */
	public void setStartAngle(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"SetStartAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
//		if (value < 0||value>360) {
//			String message = InternalResource.loadString("value",
//					InternalResource.GeoEllipticArcStartAngleShouldBe0_360,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
		double ugoSweep = this.getSweepAngle();
		double ugcStart = 0.0;
		double ugcEnd = 0.0;
		double[] result = valueToUGC(value, ugoSweep, ugcStart, ugcEnd);
		
		GeoEllipticArcNative.jni_setStartAngle(this.getHandle(), result[0],result[1]);
		m_startAngle = value;
	}

	/**
	 * 返回椭圆弧的终止角度。
	 * 
	 * @return 返回椭圆弧的终止角度。
	 */
	public double getSweepAngle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSweepAngle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return this.m_sweepAngle;
	}

	/**
	 * 设置椭圆弧的终止角度。
	 * 
	 * @param value
	 *            椭圆弧的终止角度。
	 */
	public void setSweepAngle(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setSweepAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <=-360||value>=360) {
			String message = InternalResource.loadString("value",
					InternalResource.GeoEllipticArcSweepAngleRange,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if(value==0){
			String message = InternalResource.loadString("value",
					InternalResource.GeoEllipticArcStartAngleShouldNotBeZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		double ugoStart = this.getStartAngle();
		double ugcStart = 0.0;
		double ugcEnd = 0.0;
		double[] result = valueToUGC(ugoStart, value, ugcStart, ugcEnd);
		
		GeoEllipticArcNative.jni_setStartAngle(this.getHandle(), result[0],result[1]);
		this.m_sweepAngle = value;
	}

	/**
	 * 返回椭圆弧的旋转角度。
	 * 
	 * @return 返回椭圆弧的旋转角度。
	 */
	public double getRotation() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getRotation()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoEllipticArcNative.jni_getAngle(this.getHandle());
	}

	/**
	 * 设置椭圆弧的旋转角度。
	 * 
	 * @param value
	 *            椭圆弧的旋转角度。
	 */
	public void setRotation(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setRotation(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoEllipticArcNative.jni_setAngle(this.getHandle(), value);
	}

	/**
	 * 返回椭圆弧的弧长。
	 * 
	 * @return 返回椭圆弧的弧长。
	 */
	 public double getLength() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetLength()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoEllipticArcNative.jni_getLength(this.getHandle());
	}
	/**
	 * 将椭圆弧转换为线对象。
	 * 
	 * @param segmentCount
	 *            等分椭圆弧的段数。
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
					InternalResource.GeoEllipticArcGeoLineSegmentCountShouldNotBeNeagtive,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		long handle = GeoEllipticArcNative.jni_convertToLine(this.getHandle(),
				segmentCount);
		GeoLine geoLine = null;
		if (handle != 0) {
			geoLine = new GeoLine(handle);
			geoLine.setHandle(handle, true);
		} 
		return geoLine;
	}

	/**
	 * 根据角度值返回椭圆弧上的某一点。
	 * 
	 * @param angle
	 *            角度值，此处指绝对角度值，角度值介于起始角度和终止角度的间。
	 * @return 返回 Point2D 类型的对象。
	 */
	public Point2D findPointOnArc(double sweepAngle) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"FindPointOnArc(double sweepAngle)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (sweepAngle > this.getSweepAngle()||sweepAngle<0) {
			String message = InternalResource.loadString(
					"FindPointOnArc(double sweepAngle)",
					InternalResource.GeoEllipticArcArgumentOutOfBounds,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] point = new double[2];
		GeoEllipticArcNative.jni_findPointOnArc(this.getHandle(), sweepAngle, point);
		return new Point2D(point[0], point[1]);
	}

	@Override
	public GeoEllipticArc clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("Clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoEllipticArc(this);
	}

	@Override
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoEllipticArcNative.jni_Delete(getHandle());
			setHandle(0);
			clearHandle();
		}
	}
	
	/**
	 * 由于UGC设计与UGO不同，故对UGO传入的参数进行处理
	 * @param ugoStart
	 * @param ugoSweep
	 * @param ugcStart
	 * @param ugcEnd
	 * @return
	 */
	protected static double[] valueToUGC(double ugoStart, double ugoSweep, double ugcStart, double ugcEnd) {
		int start = (int)ugoStart;
		int n = start / 360;
		ugoStart -= 360 * n;
		
		if (ugoSweep < 0) {
			ugcStart = ugoStart + ugoSweep;
			ugcEnd = ugoStart;
		} else {
			ugcStart = ugoStart;
			ugcEnd = ugoStart + ugoSweep;
		}
		double[] result = {ugcStart, ugcEnd};
		return result;
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
