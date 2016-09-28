package com.supermap.data;

/**
 * Բ������
 * @author xuzw
 *
 */
public class GeoArc extends Geometry {
	
	private double m_startAngle;
	
	private double m_sweepAngle;
	
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public GeoArc() {
		long handle = GeoArcNative.jni_New();
		this.setHandle(handle, true);
		
		this.m_startAngle = 0.0;
		this.m_sweepAngle = 180.0;
	}
	
	/**
	 * �������캯��
	 * @param geoArc GeoArc
	 */
	public GeoArc(GeoArc geoArc) {
		if(geoArc == null) {
			String message = InternalResource.loadString("geoArc",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long geoArcHandle = InternalHandleDisposable.getHandle(geoArc);
		if(geoArcHandle == 0) {
			String message = InternalResource.loadString("geoArc",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long cloneHandle = GeoArcNative.jni_Clone(geoArcHandle);
		this.m_startAngle = geoArc.getStartAngle();
		this.m_sweepAngle = geoArc.getSweepAngle();
		setHandle(cloneHandle, true);
	}
	
	/**
	 * �ڲ�ʹ�ù��캯��
	 * @param handle
	 */
	GeoArc(long handle) {
		this.setHandle(handle, false);
		this.m_startAngle = GeoArcNative.jni_GetStartAngle(handle);
		this.m_sweepAngle = GeoArcNative.jni_GetSweepAngle(handle);
	}
	
	/**
	 * ����Բ�ġ��뾶����ʼ����ֹ�Ƕȣ���λΪ�ȣ�������Բ������
	 * @param center Point2D
	 * @param radius double 
	 * @param startAngle double 
	 * @param endAngle double
	 */
	public GeoArc(Point2D center,double radius,double startAngle,double sweepAngle) {
		//�뾶С�ڵ���0������ʧ��
		if (radius <= 0) {
			String message = InternalResource.loadString("radius", 
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (sweepAngle <= -360 || sweepAngle >= 360) {
			String message = InternalResource.loadString("sweepAngle", 
					InternalResource.GeoArcSweepAngleRange,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (sweepAngle == 0) {
			String message = InternalResource.loadString("sweepAngle", 
					InternalResource.GeoArcSweepAngleShouldNotBeZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		double ugcStart = 0.0;
		double ugcEnd = 0.0;
		double[] result = valueToUGC(startAngle, sweepAngle, ugcStart, ugcEnd);
		
		double x = center.getX();
		double y = center.getY();
		long handle = GeoArcNative.jni_New2(x, y, radius, result[0], result[1]);

//		double startRadian = startAngle / 180 * Math.PI;
//		double sweepRadian = sweepAngle / 180 * Math.PI;
//		double endRadian = startRadian + sweepRadian;
//		
//		double xStart = center.getX() + radius * Math.cos(startRadian);
//		double yStart = center.getY() + radius * Math.sin(startRadian);
//		
//		double xMiddle = center.getX() + radius * Math.cos((startRadian + endRadian) / 2);
//		double yMiddle = center.getY() + radius * Math.sin((startRadian + endRadian) / 2);	
//			
//		double xEnd = center.getX() + radius * Math.cos(endRadian); 	
//		double yEnd = center.getY() + radius * Math.sin(endRadian);	
//		
//		long handle = GeoArcNative.jni_New3(xStart, yStart, 
//				xMiddle, yMiddle, xEnd, yEnd);
		if (handle == 0) {
			String message = InternalResource.loadString(
					"GeoArc(Point2D startPoint,Point2D middlePoint,Point2D endPoint)", 
					InternalResource.ThreePointsAreInOneLine,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		this.m_startAngle = startAngle;
		this.m_sweepAngle = sweepAngle;
		setHandle(handle, true);
	}
	
	/**
	 * ������ʼ�㡢�м�����ֹ�㴴��Բ������
	 * @param startPoint Point2D
	 * @param middlePoint Point2D
	 * @param endPoint Point2D
	 */
	public GeoArc(Point2D startPoint,Point2D middlePoint,Point2D endPoint) {
		//TODO ������캯��˳ʱ�뻹��������ģ��ȶ�����ʱ�봦��
		double xStart = startPoint.getX();
		double yStart = startPoint.getY();
		double xMiddle = middlePoint.getX();
		double yMiddle = middlePoint.getY();
		double xEnd = endPoint.getX();
		double yEnd = endPoint.getY();
		long handle = GeoArcNative.jni_New3(xStart, yStart, 
				xMiddle, yMiddle, xEnd, yEnd);
		if (handle == 0) {
			String message = InternalResource.loadString(
					"GeoArc(Point2D startPoint,Point2D middlePoint,Point2D endPoint)", 
					InternalResource.ThreePointsAreInOneLine,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		double ugcStart = GeoArcNative.jni_GetStartAngle(handle);
		double ugcEnd = GeoArcNative.jni_GetEndAngle(handle);
		if (ugcEnd - ugcStart < 0) {
			m_startAngle = ugcEnd;
			m_sweepAngle = -(360.0 - (ugcStart - ugcEnd));
		} else {
			m_startAngle = ugcStart;
			m_sweepAngle = ugcEnd - ugcStart;
		}
		setHandle(handle, true);
	}
	
	/**
	 * ����Բ����Բ��
	 * @return Point2D
	 */
	public Point2D getCenter() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getCenter()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] pt2D = new double[2];
		GeoArcNative.jni_GetCenter(getHandle(), pt2D);
		Point2D point2D = new Point2D();
		point2D.setX(pt2D[0]);
		point2D.setY(pt2D[1]);
		return point2D;
	}
	
	/**
	 * ����Բ����Բ��
	 * @param point2D
	 */
	public void setCenter(Point2D point2D) {	
		if(this.getHandle() == 0) {
			String message = InternalResource.loadString("setCenter(Point2D point2D)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double x = point2D.getX();
		double y = point2D.getY();
		//�˴�ʵ�����������⣬����ִ��get����Ҫ�������õײ㣬���ǲ���ô�������ڵײ���ָ��ӵ��ж�
		double radius = this.getRadius();
		double ugoStart = this.getStartAngle();
		double ugoSweep = this.getSweepAngle();
		
		double ugcStart = 0.0;
		double ugcEnd = 0.0;
		double[] result = valueToUGC(ugoStart, ugoSweep, ugcStart, ugcEnd);
		boolean bResult = GeoArcNative.jni_SetArc(getHandle(), x, y, radius, result[0], result[1]);
		if (!bResult) {
			String message = InternalResource.loadString("setCenter(Point2D point2D)", 
					InternalResource.GeoArcFailConstruct,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * ����Բ���İ뾶
	 * @return
	 */
	public double getRadius() {
		if(this.getHandle() == 0) {
			String message = InternalResource.loadString("getRadius()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoArcNative.jni_GetRadius(getHandle());
	}
	
	/**
	 * ����Բ���İ뾶
	 * @param value double
	 */
	public void setRadius(double value) {
		if(this.getHandle() == 0) {
			String message = InternalResource.loadString("setRadius(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Point2D center = this.getCenter();
		double x = center.getX();
		double y = center.getY();
		double ugoStart = this.getStartAngle();
		double ugoSweep = this.getSweepAngle();
		
		double ugcStart = 0.0;
		double ugcEnd = 0.0;
		double[] result = valueToUGC(ugoStart, ugoSweep, ugcStart, ugcEnd);
		
		boolean bResult = GeoArcNative.jni_SetArc(getHandle(), x, y, 
				value, result[0], result[1]);
		if (!bResult) {
			String message = InternalResource.loadString("setRadius(double value)", 
					InternalResource.GeoArcFailConstruct,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}	
	}
	
	/**
	 * ����Բ������ʼ�Ƕȡ���λΪ��
	 * @return double
	 */
	public double getStartAngle() {
		if(this.getHandle() == 0) {
			String message = InternalResource.loadString("getStartAngle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
//		return GeoArcNative.jni_GetStartAngle(getHandle());
		return this.m_startAngle;
	}
	
	/**
	 * ����Բ������ʼ�Ƕȡ���λΪ��
	 * @param value double
	 */
	public void setStartAngle(double value) {
		if(this.getHandle() == 0) {
			String message = InternalResource.loadString("setStartAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Point2D center = this.getCenter();
		double x = center.getX();
		double y = center.getY();
		double radius = this.getRadius();
		double ugoSweep = this.getSweepAngle();
		
		double ugcStart = 0.0;
		double ugcEnd = 0.0;
		double[] result = valueToUGC(value, ugoSweep, ugcStart, ugcEnd);
		
		boolean bResult = GeoArcNative.jni_SetArc(getHandle(), x, y, 
				radius, result[0], result[1]);
		if (!bResult) {
			String message = InternalResource.loadString("setStartAngle(double value)", 
					InternalResource.GeoArcFailConstruct,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		this.m_startAngle = value;
	}
	
	/**
	 * ����Բ������ʼ�Ƕȡ���λΪ��
	 * @return double
	 */
	public double getSweepAngle(){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getSweepAngle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
//		return GeoArcNative.jni_GetSweepAngle(this.getHandle());
		return this.m_sweepAngle;
	}
	
	/**
	 * ����Բ������ʼ�Ƕȡ���λΪ��
	 * @param value double
	 */
	public void setSweepAngle(double value) {	
		if(this.getHandle() == 0) {
			String message = InternalResource.loadString("setSweepAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <= -360 || value >= 360) {
			String message = InternalResource.loadString("value", 
					InternalResource.GeoArcSweepAngleRange,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (value == 0) {
			String message = InternalResource.loadString("setSweepAngle(double value)", 
					InternalResource.GeoArcSweepAngleShouldNotBeZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		Point2D center = this.getCenter();
		double x = center.getX();
		double y = center.getY();
		double radius = this.getRadius();
		double ugoStart = this.getStartAngle();
		
		double ugcStart = 0.0;
		double ugcEnd = 0.0;
		double[] result = valueToUGC(ugoStart, value, ugcStart, ugcEnd);
		
		boolean bResult = GeoArcNative.jni_SetArc(getHandle(), x, y, 
					radius, result[0], result[1]);
		
		if (!bResult) {
			String message = InternalResource.loadString("setSweepAngle(double value)", 
					InternalResource.GeoArcFailConstruct,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		this.m_sweepAngle = value;
	}
//	/**
//	 * ����Բ������ֹ�Ƕȡ���λΪ��
//	 * @return double 
//	 */
//	public double getEndAngle() {
//		if(this.getHandle() == 0) {
//			String message = InternalResource.loadString("getEndAngle()",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		return GeoArcNative.jni_GetEndAngle(getHandle());
//	}
//	
//	/**
//	 * ����Բ������ֹ�Ƕȡ���λΪ��
//	 * @param value double
//	 */
//	public void setEndAngle(double value) {
//		if(this.getHandle() == 0) {
//			String message = InternalResource.loadString("setEndAngle(double value)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		Point2D center = this.getCenter();
//		double x = center.getX();
//		double y = center.getY();
//		double radius = this.getRadius();
//		double startAngle = this.getStartAngle();
//		boolean bResult = GeoArcNative.jni_SetArc(getHandle(), x, y, 
//				radius, startAngle, value);
//		if (!bResult) {
//			String message = InternalResource.loadString("setEndAngle(double value)", 
//					InternalResource.FailConstruct,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//	}
	
	/**
	 * ����Բ���Ļ���
	 * @return double
	 */
	public double getLength() {
		if(this.getHandle() == 0) {
			String message = InternalResource.loadString("getLength()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoArcNative.jni_GetLength(getHandle());
	}
	
	/**
	 * ת��Ϊ�߶���
	 * @param segmentCount int �ȷֻ��εĶ���
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
		long geoLineHandle = GeoArcNative.jni_ConvertToLine(getHandle(), segmentCount);
		//��ǰ��GeoArc��Ϊ�գ�����һ���յ�GeoLine
		GeoLine geoLine = null;
		if(geoLineHandle != 0) {
            geoLine = new GeoLine(geoLineHandle);
            //ע�⣺ÿ�ζ��õ�һ���µ�GeoLine��Ϊ���ͷŶ���
            geoLine.setIsDisposable(true);
		}
		return geoLine;
	}
	
	/**
	 * ����Բ���ϵ�ĳһ��
	 * @param sweepAngle ɨ���ĽǶ�
	 * @return
	 */
	public Point2D findPointOnArc(double sweepAngle) {		
		if (this.getSweepAngle() < 0) {
			if (sweepAngle < this.getSweepAngle() || sweepAngle > 0) {
				String message = InternalResource.loadString("findPointOnArc(double sweepAngle)",
						InternalResource.GeoArcSweepAngleOutOfBounds,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
		}
		if (this.getSweepAngle() > 0) {
			if (sweepAngle > this.getSweepAngle() || sweepAngle < 0) {
				String message = InternalResource.loadString("findPointOnArc(double sweepAngle)",
						InternalResource.GeoArcSweepAngleOutOfBounds,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
		}
		int start = (int)this.getStartAngle();
		int n = start / 360;
		double newStartAngle = this.getStartAngle() - 360 * n;

		//TODO  sweepAngle����û�����ƽ�������ﲻ��ʵ��
		double pointAngle = newStartAngle + sweepAngle;
		
		double pointAngleToR = pointAngle / 180 * Math.PI;
		Point2D center = this.getCenter();
		double x = center.getX() + Math.cos(pointAngleToR) * this.getRadius();
		double y = center.getY() + Math.sin(pointAngleToR) * this.getRadius();
		Point2D point = new Point2D(x, y);
		return point;
	}
	
	@Override
	public GeoArc clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoArc(this);
	}

	@Override
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoArcNative.jni_Delete(getHandle());
			setHandle(0);
			this.clearHandle();
		}
	}
	
	protected void clearHandle() {
		super.clearHandle();
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
