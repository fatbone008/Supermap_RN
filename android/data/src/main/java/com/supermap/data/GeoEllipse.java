package com.supermap.data;

/**
 * ��Բ�ࡣ
 * 
 * <p>
 * ������Ҫ���� CAD ͼ�㣬�� Geometry ������Ӷ���
 * 
 */
public class GeoEllipse extends Geometry {

	/**
	 * ����һ����Բ����¶���
	 */
	public GeoEllipse() {
		long handle = GeoEllipseNative.jni_New();
		this.setHandle(handle, true);
	}

	/**
	 * ���ݸ�������Բ������һ��������ȫ��ͬ����Բ����
	 * 
	 * @param geoEllipse
	 *            ��������Բ����
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
	 * ���ݸ�����Բ�ġ������ᡢ�̰������ת�Ƕ���������Բ����¶���
	 * 
	 * @param center
	 *            ��Բ��Բ�ġ�
	 * @param semimajorAxis
	 *            �����ᡣ
	 * @param semiminorAxis
	 *            �̰��ᡣ
	 * @param angle
	 *            ��ת�Ƕȡ�
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
	 * ���ݸ����Ķ�ά���μ��ζ�����������Բ����¶���
	 * <P>
	 * ��Բ�������ת�Ƕ�Ĭ��Ϊ GeoRectangle �������ת�Ƕȡ�
	 * 
	 * @param geoRectangle
	 *            ָ��������ȷ����Բ����ľ��Ρ�
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
	 * ���ݸ����ľ�����������Բ����¶���
	 * 
	 * @param rectangle
	 *            ָ��������ȷ����Բ����ľ��Ρ�
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
	 * ������Բ��Բ�ġ�
	 * 
	 * @return ������Բ��Բ�ġ�
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
	 * ������Բ��Բ�ġ�
	 * 
	 * @param point2D
	 *            ��Բ��Բ�ġ�
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
	 * ������Բ�ĳ����ᡣ
	 * 
	 * @return ������Բ�ĳ����ᡣ
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
	 * ������Բ�ĳ����ᡣ
	 * 
	 * @param value
	 *            ��Բ�ĳ����ᡣ
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
	 * ������Բ�Ķ̰��ᡣ
	 * 
	 * @return ������Բ�Ķ̰��ᡣ
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
	 * ������Բ�Ķ̰��ᡣ
	 * 
	 * @param value
	 *            ��Բ�Ķ̰��ᡣ
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
	 * ������Բ����ת�Ƕȡ�
	 * 
	 * @return ������Բ����ת�Ƕȡ�
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
	 * ������Բ����ת�Ƕȡ�
	 * 
	 * @param value
	 *            ��Բ����ת�Ƕȡ�
	 */
	public void setRotation(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setRotation(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
//		����UGON-1833��������
		GeoEllipseNative.jni_setAngle(this.getHandle(), value);
	}

	/**
	 * ������Բ���ܳ���
	 * 
	 * @return ������Բ���ܳ���
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
	 * ������Բ�������
	 * 
	 * @return ������Բ�������
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
	 * ����Բ����ת��Ϊ�߶���
	 * 
	 * @param segmentCount
	 *            �ȷ���Բ�Ķ�����
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
	 * ����Բ����ת��Ϊ�����
	 * 
	 * @param segmentCount
	 *            �ȷ���Բ�Ķ�����
	 * @return ���������
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
