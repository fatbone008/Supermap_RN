package com.supermap.data;

/**
 * ��ά���μ����ࡣ
 * <p>
 * ������Ҫ���� CAD ͼ�㣬�� Geometry ������Ӷ���
 */
public class GeoRectangle extends Geometry {
	// �ö�����Ҫ���� CAD ͼ�㣬��Geometry������Ӷ���
	/**
	 * ����һ����ά���μ�������¶���
	 */
	public GeoRectangle() {
		long handle = GeoRectangleNative.jni_New();
		this.setHandle(handle, true);
	}

	/**
	 * ���ݸ����Ķ�ά���μ��ζ�����һ��������ȫ��ͬ�Ķ�ά���μ��ζ���
	 * 
	 * @param geoRectangle
	 *            �����Ķ�ά���μ��ζ���
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
	 * ���ݸ��������ĵ㡢��ά��ߺ���ת�Ƕ��������ά���μ�������¶���
	 * 
	 * @param center
	 *            ���ĵ㡣
	 * @param width
	 *            ��תǰ��ά���μ��ζ���Ŀ�ȡ�
	 * @param height
	 *            ��תǰ��ά���μ��ζ���ĸ߶ȡ�
	 * @param angle
	 *            ��ά���μ��ζ������ת�Ƕȡ�
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
//	 * ������תǰ���½ǵ������Ͷ�ά���μ��ζ���Ŀ�ȡ��߶��������ά���μ�������¶��󡣴�ʱ��ת�Ƕ�Ĭ��Ϊ0��
//	 * 
//	 * @param leftBottom
//	 *            ��תǰ��ά���μ��ζ�������½ǵ����ꡣ
//	 * @param width
//	 *            ��תǰ��ά���μ��ζ���Ŀ�ȡ�
//	 * @param height
//	 *            ��תǰ��ά���μ��ζ���ĸ߶ȡ�
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
//	 * ������תǰ���½ǵ����������Ͻǵ�������������ά���μ�������¶��󡣴�ʱ��ת�Ƕ�Ĭ��Ϊ0��
//	 * 
//	 * @param leftBottom
//	 *            ��תǰ��ά���μ��ζ������½ǵ�����ꡣ
//	 * @param rightTop
//	 *            ��תǰ��ά���μ��ζ������Ͻǵ�����ꡣ
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
//	 * ������תǰ����� X ������ֵ���ײ� Y ������ֵ���Ҳ� X ������ֵ���ϲ� Y ������ֵ�������ά���μ�������¶��󡣴�ʱ��ת�Ƕ�Ĭ��Ϊ0��
//	 * 
//	 * @param left
//	 *            ��ά���μ��ζ�����תǰ����� X ������ֵ��
//	 * @param bottom
//	 *            ��ά���μ��ζ�����תǰ�ĵײ� Y ������ֵ��
//	 * @param right
//	 *            ��ά���μ��ζ�����תǰ���Ҳ� X ������ֵ��
//	 * @param top
//	 *            ��ά���μ��ζ�����תǰ���ϲ� Y ������ֵ��
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
	 * ����һ�� Rectangle �����������ά���μ�������¶��󡣴�ʱ��ת�Ƕ�Ĭ��Ϊ0��
	 * 
	 * @param rectangle
	 *            ���ζ���
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
	 * ����ά���μ��ζ���ת��Ϊ�߶���
	 * 
	 * @return �����߶���
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
	 * ����ά���μ��ζ���ת��Ϊ�����
	 * 
	 * @return ���������
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
	 * ���ض�ά���μ��ζ�������ĵ㡣
	 * 
	 * @return ���ض�ά���μ��ζ�������ĵ㡣
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
	 * ���ö�ά���μ��ζ�������ĵ㡣
	 * 
	 * @param point2D
	 *            ��ά���μ��ζ�������ĵ㡣
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
	 * ���ض�ά���μ��ζ���Ŀ�ȣ���ָ���ζ�����תǰ�Ŀ�ȡ�
	 * 
	 * @return ���ض�ά���μ��ζ���Ŀ�ȡ�
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
	 * ���ö�ά���μ��ζ���Ŀ�ȣ���ָ���ζ�����תǰ�Ŀ�ȡ�
	 * 
	 * @param value
	 *            ��ά���μ��ζ���Ŀ�ȡ�
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
	 * ���ض�ά���μ��ζ���ĸ߶ȣ���ָ���ζ�����תǰ�ĸ߶ȡ�
	 * 
	 * @return ���ض�ά���μ��ζ���ĸ߶ȡ�
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
	 * ���ö�ά���μ��ζ���ĸ߶ȣ���ָ���ζ�����תǰ�ĸ߶ȡ�
	 * 
	 * @param value
	 *            ��ά���μ��ζ���ĸ߶ȡ�
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
	 * ���ض�ά���μ��ζ������ת�Ƕȡ�
	 * 
	 * @return ���ض�ά���μ��ζ������ת�Ƕȡ�
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
	 * ���ö�ά���μ��ζ������ת�Ƕȡ�
	 * 
	 * @param value
	 *            ��ά���μ��ζ������ת�Ƕȡ�
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
