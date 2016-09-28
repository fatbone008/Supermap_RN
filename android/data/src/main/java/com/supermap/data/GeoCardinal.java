package com.supermap.data;


public class GeoCardinal extends Geometry{
	
	private Point2Ds m_point2Ds = null; // ���ڴ��GeoCardinal�е�point2D

	/**
	 * Ĭ�Ϲ��캯��
	 */
	public GeoCardinal() {
		long handle = GeoCardinalNative.jni_New();
//		m_point2Ds = new Point2Ds();
		this.setHandle(handle, true);
	}
	
	/**
	 * �������캯��
	 * @param geoCardinal GeoCardinal
	 */
	public GeoCardinal(GeoCardinal geoCardinal) {
		if(geoCardinal == null) {
			String message = InternalResource.loadString("geoCardinal",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long geoCardinalHandle = InternalHandleDisposable.getHandle(geoCardinal);
		if(geoCardinalHandle == 0) {
			String message = InternalResource.loadString("geoCardinal",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long cloneHandle = GeoCardinalNative.jni_Clone(geoCardinalHandle);
		setHandle(cloneHandle, true);
		
		//ͬ������m_point2Ds
		m_point2Ds = new Point2Ds(geoCardinal.getControlPoints(), this);
	}
	
	/**
	 * ���캯��
	 * @param controlPoints Point2Ds
	 */
	public GeoCardinal(Point2Ds controlPoints) {
		int count = controlPoints.getCount();
        //�ߵ��Ӷ���ĵ���Ӧ������������
        if (count < 2) {
            String message = InternalResource.loadString("controlPoints",
                    InternalResource.GeoCardinalControlPointsLengthShouldNotLessThanTwo,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        double[] xs = new double[count];
        double[] ys = new double[count];
        for (int i = 0; i < count; i++) {
            xs[i] = controlPoints.getItem(i).getX();
            ys[i] = controlPoints.getItem(i).getY();
        }
        long geoCardinalHandle = GeoCardinalNative.jni_New2(xs, ys);
        this.setHandle(geoCardinalHandle, true);
        //ͬ������m_point2Ds
        m_point2Ds = new Point2Ds(controlPoints, this);
	}
	
	GeoCardinal(long handle) {
		this.setHandle(handle, false);
		m_point2Ds = new Point2Ds(this.getControlPoints(), this);
	}
	
	/**
	 * ���ض�άCardinal�������߶���ĳ���
	 * @return double
	 */
	public double getLength() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getLength()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoCardinalNative.jni_GetLength(this.getHandle());
	}
	/**
	 * ���ض�άCardinal���������Ƿ�Ϊ��
	 * @return boolean
	 */
	public boolean isEmpty(){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("isEmpty()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoCardinalNative.jni_IsEmpty(getHandle());
	}
	
	/**
	 * ����άCardinal�����������
	 */
	public void setEmpty(){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setEmpty()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		m_point2Ds.clear();
		m_point2Ds = null;
		GeoCardinalNative.jni_SetEmpty(getHandle());
	}
	
	/**
	 * ���ؿ��Ƶ㼯��
	 * @return Point2Ds
	 */
	public Point2Ds getControlPoints() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getControlPoints()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int count = GeoCardinalNative.jni_GetPartPointCount(getHandle());
			
	    if (count >= 2) {
			double[] xs = new double[count];
			double[] ys = new double[count];
			GeoCardinalNative.jni_GetControlPoints(this.getHandle(), xs, ys);
			Point2Ds pts = new Point2Ds();
			for (int i = 0; i < count; i++) {
				pts.add(new Point2D(xs[i], ys[i]));
			}
//			m_point2Ds = new Point2Ds(pts, this);
			
			//2008-12-22 ���������޸ģ�����Ҫ��֤ͬһ���󣬶�����ÿ�ζ�new
			//m_point2Ds����null��ʱ���Ȱѵײ��ȡ�ĵ���ϣ�Ȼ�����ǰ�Ķ��Ƴ�
			if (m_point2Ds == null) {
				m_point2Ds = new Point2Ds(pts, this);
			} else {
				for (int i = 0; i < count; i++) {
					m_point2Ds.add(new Point2D(xs[i], ys[i]));
				}
				int newCount = m_point2Ds.getCount();
				m_point2Ds.removeRange(0,newCount - count);
			}
		}
		return this.m_point2Ds;
	}
	
	/**
	 * ���ÿ��Ƶ㼯��
	 * @param controlPoints Point2Ds
	 */
	public void setControlPoints(Point2Ds controlPoints) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setControlPoints(Point2Ds controlPoints)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int count = controlPoints.getCount();
		if (count < 2) {
            String message = InternalResource.loadString("controlPoints",
                    InternalResource.GeoCardinalControlPointsLengthShouldNotLessThanTwo,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
		}
		double[] xs = new double[count];
		double[] ys = new double[count];
		for (int i = 0; i < ys.length; i++) {
            xs[i] = controlPoints.getItem(i).getX();
            ys[i] = controlPoints.getItem(i).getY();
		}
		GeoCardinalNative.jni_SetControlPoints(this.getHandle(), xs, ys);
//		this.m_point2Ds.clear();
//		this.m_point2Ds.addRange(controlPoints.toArray());
	}
	
	/**
	 * ת��Ϊ�߶���
	 * @param pointCountPerSegment int
	 * @return GeoLine
	 */
	public GeoLine convertToLine(int pointCountPerSegment) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("convertToLine(int segmentCount)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (pointCountPerSegment < 2) {
			String message = InternalResource.loadString("pointCountPerSegment",
					InternalResource.GlobalArgumentShouldNotSmallerThanTwo,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long geoLineHandle = GeoCardinalNative.jni_ConvertToLine(this.getHandle(), pointCountPerSegment);
		//��ǰ��GeoArc��Ϊ�գ�����һ���յ�GeoLine
		GeoLine geoLine = null;
		if(geoLineHandle != 0) {
            geoLine = new GeoLine(geoLineHandle);
            //ע�⣺ÿ�ζ��õ�һ���µ�GeoLine��Ϊ���ͷŶ���
            geoLine.setIsDisposable(true);
        }
		return geoLine;
	}
	@Override
	public GeoCardinal clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoCardinal(this);
	}

	@Override
	public void dispose() {
        if (!this.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
        	GeoCardinalNative.jni_Delete(getHandle());
            this.setHandle(0);
            this.clearHandle();
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
