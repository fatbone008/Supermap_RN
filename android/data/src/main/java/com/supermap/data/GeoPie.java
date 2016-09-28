package com.supermap.data;

public class GeoPie extends Geometry {
	// 椭圆饼对象。 （二维）
	// 从椭圆对象上从椭圆心到椭圆边,用两刀切出来一块饼
	// 该对象主要用于 CAD 图层，是Geometry对象的子对象

	public GeoPie() {
		this.setHandle(GeoPieNative.jni_New(), true);
		// this.setCenter(new Point2D(0,0));
		// this.setStartAngle(0);
		// this.setEndAngle(180);
		// this.setAngle(0);
		// this.setSemimajorAxis(1);
		// this.setSemiminorAxis(1);
		reset(0, 0, 1, 1, 0, 180, 0);
	}

	public GeoPie(GeoPie geoPie) {
		if (geoPie == null) {
			String message = InternalResource.loadString("geoPie",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long geoPieHandle = InternalHandle.getHandle(geoPie);
		if (geoPieHandle == 0) {
			String message = InternalResource.loadString("geoPie",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoPieNative.jni_Clone(geoPieHandle);
		super.setHandle(handle, true);
	}

	// 椭圆饼所在椭圆的圆心
	// 椭圆饼所在椭圆的长半轴
	// 椭圆饼所在椭圆的短半轴
	// 椭圆饼对应的椭圆弧的起始角度
	// 椭圆饼对应的椭圆弧的终止角度
	// 椭圆饼的旋转角度

	public GeoPie(Point2D center, double semimajorAxis, double semiminorAxis,
			double startAngle, double sweepAngle, double angle) {
		this();
		if (semimajorAxis <= 0) {
			String message = InternalResource.loadString("semimajorAxis",
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (semiminorAxis <= 0) {
			String message = InternalResource.loadString("semiminorAxis",
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (sweepAngle <= -360 || sweepAngle >= 360) {
			String message = InternalResource.loadString("sweepAngle",
					InternalResource.GeoPieSweepAngleRange,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (sweepAngle == 0) {
			String message = InternalResource.loadString("sweepAngle",
					InternalResource.GeoPieSweepAngleShouldNotBeZero,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		reset(center.getX(), center.getY(), semimajorAxis, semiminorAxis,
				startAngle, startAngle + sweepAngle, angle);
	}

	GeoPie(long handle) {
		setHandle(handle, false);
	}

	// 返回/设置椭圆饼所在椭圆的圆心
	public Point2D getCenter() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getCenter()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] values = new double[2];
		GeoPieNative.jni_GetCenter(this.getHandle(), values);
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
		GeoPieNative
				.jni_SetCenter(this.getHandle(), value.getX(), value.getY());
	}

	// 返回/设置椭圆饼所在椭圆的长半轴
	public double getSemimajorAxis() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSemimajorAxis()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoPieNative.jni_GetSemimajorAxis(this.getHandle());
	}

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
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoPieNative.jni_SetSemimajorAxis(this.getHandle(), value);
	}

	// 返回/设置椭圆饼所在椭圆的短半轴。
	public double getSemiminorAxis() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSemiminorAxis()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoPieNative.jni_GetSemiminorAxis(this.getHandle());
	}

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
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoPieNative.jni_SetSemiminorAxis(this.getHandle(), value);
	}

	// 返回/设置椭圆饼对应的椭圆弧起始角度。
	public double getStartAngle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getStartAngle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoPieNative.jni_GetStartAngle(this.getHandle());
	}

	public void setStartAngle(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setStartAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoPieNative.jni_SetStartAngle(this.getHandle(), value);
	}

	// 返回/设置椭圆饼对应的椭圆弧终止角度。
	public double getSweepAngle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSweepAngle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoPieNative.jni_GetSweepAngle(this.getHandle());
	}

	public void setSweepAngle(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setSweepAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <= -360 || value >= 360) {
			String message = InternalResource.loadString("value",
					InternalResource.GeoPieSweepAngleRange,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (value == 0) {
			String message = InternalResource.loadString("value",
					InternalResource.GeoPieSweepAngleShouldNotBeZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoPieNative.jni_SetSweepAngle(this.getHandle(), value);
	}

	public double getRotation() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getAngle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoPieNative.jni_GetAngle(this.getHandle());
	}

	public void setRotation(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoPieNative.jni_SetAngle(this.getHandle(), value);
	}

	// TODO 底层未实现
	// // 返回椭圆饼的周长。
	// public double getPerimeter() {
	// if (this.getHandle() == 0) {
	// String message = InternalResource.loadString("getPerimeter()",
	// InternalResource.HandleObjectHasBeenDisposed,
	// InternalResource.BundleName);
	// throw new IllegalStateException(message);
	// }
	// return GeoPieNative.jni_GetPerimeter(this.getHandle());
	// }
	//
	// // 返回椭圆饼的面积。
	// public double getArea() {
	// if (this.getHandle() == 0) {
	// String message = InternalResource.loadString("getArea()",
	// InternalResource.HandleObjectHasBeenDisposed,
	// InternalResource.BundleName);
	// throw new IllegalStateException(message);
	// }
	// return GeoPieNative.jni_GetArea(this.getHandle());
	// }

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

		long handle = GeoPieNative.jni_ConvertToLine(this.getHandle(),
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
			throw new NullPointerException(message);
		}

		long handle = GeoPieNative.jni_ConvertToRegion(this.getHandle(),
				segmentCount);
		GeoRegion geoRegion = null;
		if (handle != 0) {
			geoRegion = new GeoRegion(handle);
			geoRegion.setIsDisposable(true);
		}
		return geoRegion;
	}

	public double getArea() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getArea()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoPieNative.jni_GetArea(this.getHandle());
	}

	public double getPerimeter() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getPerimeter()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoPieNative.jni_GetPerimeter(this.getHandle());
	}

	public GeoPie clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoPie(this);
	}

	@Override
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoPieNative.jni_Delete(getHandle());
			setHandle(0);
			clearHandle();
		}

	}

	void reset(double x, double y, double semimajorAxis, double semiminorAxis,
			double startAngle, double endAngle, double angle) {
		GeoPieNative.jni_Reset(this.getHandle(), x, y, semimajorAxis,
				semiminorAxis, startAngle, endAngle, angle);
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
