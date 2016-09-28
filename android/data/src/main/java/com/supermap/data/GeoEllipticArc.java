package com.supermap.data;

/**
 * ��Բ���ࡣ
 * 
 * <p>
 * ������Ҫ���� CAD ͼ�㣬�� Geometry ������Ӷ���
 * 
 */
public class GeoEllipticArc extends Geometry {

	private double m_startAngle;
	
	private double m_sweepAngle;
	
	/**
	 * ����һ����Բ������¶���
	 */
	public GeoEllipticArc() {
		long handle = GeoEllipticArcNative.jni_New();
		this.setHandle(handle, true);
		this.m_startAngle = 0;
		this.m_sweepAngle  = 180;
	}

	/**
	 * ���ݸ�������Բ��������һ��������ȫ��ͬ����Բ������
	 * 
	 * @param geoEllipticArc
	 *            ��������Բ������
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
	 * ���ݸ�����Բ�ġ������ᡢ�̰��ᡢ��ʼ�Ƕȡ���ֹ�ǶȺ���ת�Ƕ���������Բ������¶���
	 * 
	 * @param center
	 *            ��Բ����Բ�ġ�
	 * @param semimajorAxis
	 *            �̰��ᡣ
	 * @param semiminorAxis
	 *            �����ᡣ
	 * @param startEngle
	 *            ��ʼ�Ƕȡ�
	 * @param endEngle
	 *            ��ֹ�Ƕȡ�
	 * @param angle
	 *            ��ת�Ƕȡ�
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
	 * ������Բ��������Բ��Բ�ġ�
	 * 
	 * @return ������Բ��������Բ��Բ�ġ�
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
	 * ������Բ��������Բ��Բ�ġ�
	 * 
	 * @param point2D
	 *            ��Բ��������Բ��Բ�ġ�
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
	 * ������Բ��������Բ�ĳ����ᡣ
	 * 
	 * @return ������Բ��������Բ�ĳ����ᡣ
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
	 * ������Բ��������Բ�ĳ����ᡣ
	 * 
	 * @param value
	 *            ��Բ��������Բ�ĳ����ᡣ
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
	 * ������Բ��������Բ�Ķ̰��ᡣ
	 * 
	 * @return ������Բ��������Բ�Ķ̰��ᡣ
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
	 * ������Բ��������Բ�Ķ̰��ᡣ
	 * 
	 * @param value
	 *            ��Բ��������Բ�Ķ̰��ᡣ
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
	 * ������Բ������ʼ�Ƕȡ�
	 * 
	 * @return ������Բ������ʼ�Ƕȡ�
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
	 * ������Բ������ʼ�Ƕȡ�
	 * 
	 * @param value
	 *            ��Բ������ʼ�Ƕȡ�
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
	 * ������Բ������ֹ�Ƕȡ�
	 * 
	 * @return ������Բ������ֹ�Ƕȡ�
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
	 * ������Բ������ֹ�Ƕȡ�
	 * 
	 * @param value
	 *            ��Բ������ֹ�Ƕȡ�
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
	 * ������Բ������ת�Ƕȡ�
	 * 
	 * @return ������Բ������ת�Ƕȡ�
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
	 * ������Բ������ת�Ƕȡ�
	 * 
	 * @param value
	 *            ��Բ������ת�Ƕȡ�
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
	 * ������Բ���Ļ�����
	 * 
	 * @return ������Բ���Ļ�����
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
	 * ����Բ��ת��Ϊ�߶���
	 * 
	 * @param segmentCount
	 *            �ȷ���Բ���Ķ�����
	 * @return �����߶���
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
	 * ���ݽǶ�ֵ������Բ���ϵ�ĳһ�㡣
	 * 
	 * @param angle
	 *            �Ƕ�ֵ���˴�ָ���ԽǶ�ֵ���Ƕ�ֵ������ʼ�ǶȺ���ֹ�Ƕȵļ䡣
	 * @return ���� Point2D ���͵Ķ���
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
	 * ����UGC�����UGO��ͬ���ʶ�UGO����Ĳ������д���
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
