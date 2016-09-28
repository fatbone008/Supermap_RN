package com.supermap.data;

public class GeoCircle extends Geometry {

	public GeoCircle() {
		this.setHandle(GeoCircleNative.jni_New(), true);
		this.setRadius(1);
	}

	public GeoCircle(GeoCircle geoCircle) {
		if (geoCircle == null) {
			String message = InternalResource.loadString("GeoCircle",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long geoPieHandle = InternalHandle.getHandle(geoCircle);
		if (geoPieHandle == 0) {
			String message = InternalResource.loadString("GeoCircle",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoCircleNative.jni_Clone(geoPieHandle);
		super.setHandle(handle, true);
	}

	// 构造函数,根据圆心和半径构建一个圆
	public GeoCircle(Point2D center, double radius) {
		this();
		this.setCenter(center);		
		this.setRadius(radius);
	}

	// 构造函数,根据两个点创建一个圆，两个点分别为圆直径的两个端点
	public GeoCircle(Point2D point1, Point2D point2) {
		double a = point1.getX();
		double b = point1.getY();
		double c = point2.getX();
		double d = point2.getY();
		if (a == c && b == d) {
			String message = InternalResource.loadString("geoCircle",
					InternalResource.PointsAreSame,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		this.setHandle(GeoCircleNative.jni_New2(a, b, c, d), true);
	}

	// 构造函数,根据三点创建一个圆.
	// 根据几何学知识，由三点可确定一个圆，创建三点圆时，这三个点均为弧上的点，因此不能在同一直线上
	public GeoCircle(Point2D point1,Point2D point2,Point2D point3){
    	double a =point1.getX();
		double b = point1.getY();
		double c = point2.getX();
		double d = point2.getY();
		double e = point3.getX();
		double f = point3.getY();
		
		if((Math.abs(c-a)< 1.0e-10)&&(Math.abs(e-a)  < 1.0e-10)){
			String message = InternalResource.loadString("geoCircle",
					InternalResource.ThreePointsAreInOneLine,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if(Math.abs((f-b)-(d-b)/(c-a)*(e-a))<1.0e-10){
			String message = InternalResource.loadString("geoCircle",
					InternalResource.ThreePointsAreInOneLine,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		this.setHandle(GeoCircleNative.jni_New3(a,b,c,d,e,f), true);
    }
	GeoCircle(long handle) {
		setHandle(handle, false);
	}
	public Point2D getCenter() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getCenter()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] values = new double[2];
		GeoCircleNative.jni_GetCenter(this.getHandle(), values);
		return new Point2D(values[0], values[1]);
	}

	public void setCenter(Point2D value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setCenter(Point2D value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoCircleNative.jni_SetCenter(this.getHandle(), value.getX(), value
				.getY());
	}

	public double getRadius() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getRadius()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoCircleNative.jni_GetRadius(this.getHandle());
	}

	public void setRadius(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setRadius(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <= 0) {
			String message = InternalResource.loadString("setRadius()",
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoCircleNative.jni_SetRadius(this.getHandle(), value);
	}

	public double getPerimeter() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getPerimeter()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoCircleNative.jni_GetPerimeter(this.getHandle());
	}

	// 返回椭圆饼的面积。
	public double getArea() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getArea()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoCircleNative.jni_GetArea(this.getHandle());
	}

	// 转换为线对象
	public GeoLine convertToLine(int segmentCount) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"convertToLine(int segmentCount)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (segmentCount < 2) {
			String message = InternalResource.loadString("segmentCount",
					InternalResource.GlobalArgumentShouldNotSmallerThanTwo,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		long handle = GeoCircleNative.jni_ConvertToLine(this.getHandle(),
				segmentCount);
		GeoLine geoLine = null;
		if (handle != 0) {
			geoLine = new GeoLine(handle);
			geoLine.setIsDisposable(true);
		}
		return geoLine;
	}

	public GeoRegion convertToRegion(int segmentCount) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"convertToRegion(int segmentCount)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (segmentCount < 2) {
			String message = InternalResource.loadString("segmentCount",
					InternalResource.GlobalArgumentShouldNotSmallerThanTwo,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		long handle = GeoCircleNative.jni_ConvertToRegion(this.getHandle(),
				segmentCount);
		GeoRegion geoRegion = null;
		if (handle != 0) {
			geoRegion = new GeoRegion(handle);
			geoRegion.setIsDisposable(true);
		}
		return geoRegion;
	}

	@Override
	public GeoCircle clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoCircle(this);
	}

	@Override
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoCircleNative.jni_Delete(getHandle());
			setHandle(0);
			clearHandle();
		}

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
