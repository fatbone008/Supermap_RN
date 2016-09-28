package com.supermap.data;

public class GeoBSpline extends Geometry{
	private Point2Ds m_point2Ds = null;
	
	/**
	 * 默认构造函数
	 */
	public GeoBSpline() {
		long handle = GeoBSplineNative.jni_New();
		this.setHandle(handle, true);
	}
	
	/**
	 * 拷贝构造函数
	 * @param geoBSpline GeoBSpline
	 */
	public GeoBSpline(GeoBSpline geoBSpline) {
		if(geoBSpline == null) {
			String message = InternalResource.loadString("geoBSpline",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long geoCardinalHandle = InternalHandleDisposable.getHandle(geoBSpline);
		if(geoCardinalHandle == 0) {
			String message = InternalResource.loadString("geoBSpline",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long cloneHandle = GeoBSplineNative.jni_Clone(geoCardinalHandle);
		setHandle(cloneHandle, true);
		
		m_point2Ds = new Point2Ds(geoBSpline.getControlPoints(), this);
	}
	
	/**
	 * 根据控制点串创建一个二维B样条曲线对象
	 * @param controlPoints Point2Ds
	 */
	public GeoBSpline(Point2Ds controlPoints) {
		int count = controlPoints.getCount();
		if (count < 4) {
            String message = InternalResource.loadString("controlPoints",
                    InternalResource.GeoBSplineControlPointsLengthShouldNotLessThanFour,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
		}
        double[] xs = new double[count];
        double[] ys = new double[count];
        for (int i = 0; i < count; i++) {
            xs[i] = controlPoints.getItem(i).getX();
            ys[i] = controlPoints.getItem(i).getY();
        }
        long geoBSpline = GeoBSplineNative.jni_New2(xs, ys);
        this.setHandle(geoBSpline, true);
        //同步更新m_point2Ds
        m_point2Ds = new Point2Ds(controlPoints, this);
	}
	
	GeoBSpline(long handle) {
		this.setHandle(handle, false);
	}
	
	/**
	 * 返回二维B样条曲线对象的长度
	 * @return double
	 */
	public double getLength() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getLength()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoBSplineNative.jni_GetLength(this.getHandle());
	}
	
	/**
	 * 返回二维B样条曲线是否为空
	 * @return boolean
	 */
	public boolean isEmpty(){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("isEmpty()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoBSplineNative.jni_IsEmpty(getHandle());
	}
	
	/**
	 * 将二维B样条曲线清空
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
		GeoBSplineNative.jni_SetEmpty(getHandle());
	}
	
	/**
	 * 返回控制点集合
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
		int count = GeoBSplineNative.jni_GetPartPointCount(getHandle());
		
		if (count >= 4) {
			double[] xs = new double[count];
			double[] ys = new double[count];
			GeoBSplineNative.jni_GetControlPoints(this.getHandle(), xs, ys);
			
			Point2Ds pts = new Point2Ds();
			for (int i = 0; i < count; i++) {
				pts.add(new Point2D(xs[i], ys[i]));
			}
//			m_point2Ds = new Point2Ds(pts, this);
			
			//2008-12-22 这里做了修改，连点要保证同一对象，而不是每次都new
			//m_point2Ds不是null的时候，先把底层获取的点加上，然后把以前的都移除
			if (m_point2Ds == null) {
				m_point2Ds = new Point2Ds(pts, this);
			} else {
				for (int i = 0; i < count; i++) {
					m_point2Ds.add(new Point2D(xs[i], ys[i]));
				}
				int newCount = m_point2Ds.getCount();
				m_point2Ds.removeRange(0, newCount - count);
			}
		}
		return m_point2Ds;
	}
	
	/**
	 * 设置控制点的集合
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
		
		if(count < 4) {
            String message = InternalResource.loadString("controlPoints",
                    InternalResource.GeoBSplineControlPointsLengthShouldNotLessThanFour,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
		}
		double[] xs = new double[count];
		double[] ys = new double[count];
		for (int i = 0; i < count; i++) {
			xs[i] = controlPoints.getItem(i).getX();
			ys[i] = controlPoints.getItem(i).getY();
		}
		GeoBSplineNative.jni_SetControlPoints(this.getHandle(), xs, ys);
	}
	
	/**
	 * 转换为线对象
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
		long geoLineHandle = GeoBSplineNative.jni_ConvertToLine(this.getHandle(), pointCountPerSegment);
		
		GeoLine geoLine = null;
		//当前的GeoArc若为空，返回一个空的GeoLine
		if(geoLineHandle != 0) {
            geoLine = new GeoLine(geoLineHandle);
            //注意：每次都得到一个新的GeoLine，为可释放对象。
            geoLine.setIsDisposable(true);
		}
		return geoLine;
	}
	@Override
	public GeoBSpline clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoBSpline(this);
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
