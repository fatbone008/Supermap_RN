package com.supermap.data;

import com.supermap.imb.jsonlib.SiJsonObject;

/**
 * <p>
 * Title: 三维点对象
 * </p>
 * 
 * <p>
 * Description: 用于表示精度为Double的点对象
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: SuperMap GIS Technologies Inc.
 * </p>
 * 
 * @author zhikk
 * @version 2.0
 */
public class Point3D {
	private double x;

	private double y;

	private double z;

//	/**
//	 * 返回空的三维点对象,注意不是(0,0,0)
//	 * 
//	 * @return Point3D
//	 */
//	public static final Point3D getEMPTY() {
//		return new Point3D(Toolkit.DBL_MIN_VALUE, Toolkit.DBL_MIN_VALUE,
//				Toolkit.DBL_MIN_VALUE);
//	}

	public Point3D() {
		this.x = Toolkit.DBL_MIN_VALUE;
		this.y = Toolkit.DBL_MIN_VALUE;
		this.z = Toolkit.DBL_MIN_VALUE;
	}

	// 注意不是(0,0,0)
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point3D(Point3D pt) {
		// this.x=pt.getX();
		// this.y=pt.getY();
		// this.z=pt.getZ();
		this(pt.getX(), pt.getY(), pt.getZ());
	}

	public double getX() {
		return x;
	}

	public void setX(double value) {
		x = value;
	}

	public double getY() {
		return y;
	}

	public void setY(double value) {
		y = value;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double value) {
		z = value;
	}

//	public boolean isEmpty() {
//		boolean isEmpty = false;
//		if (Toolkit.isZero(getX() - Toolkit.DBL_MIN_VALUE,
//				Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
//				Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION)
//				&& Toolkit.isZero(getY() - Toolkit.DBL_MIN_VALUE,
//						Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
//						Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION)
//				&& Toolkit.isZero(getZ() - Toolkit.DBL_MIN_VALUE,
//						Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
//						Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION)) {
//			isEmpty = true;
//		}
//		return isEmpty;
//	}
//
	/**
	 * 克隆对象
	 * 
	 * @return Point3D
	 */
	public Point3D clone() {
		return new Point3D(this);
	}
//
//	/**
//	 * 通过将 Point3D 的坐标值舍入到与其接近的较大整数值，并以整数值构造一个新的Point3D返回
//	 * 
//	 * @param pt
//	 *            Point3D 需要转换的三维点对象
//	 * @return Point 转换后的三维点对象
//	 */
//
//	public static Point3D ceiling(Point3D pt) {
//		double x, y, z;
//		x = Math.ceil(pt.getX());
//		y = Math.ceil(pt.getY());
//		z = Math.ceil(pt.getZ());
//		Point3D point = new Point3D(x, y, z);
//		return point;
//	}
//
//	/**
//	 * 返回小于或等于比pt坐标值的最大整数对构造的Point3D
//	 * 
//	 * @param pt
//	 *            Point3D 需要转换的三维点对象
//	 * @return Point 转换后的三维点对象
//	 */
//
//	public static Point3D floor(Point3D pt) {
//		double x, y, z;
//		x = Math.floor(pt.getX());
//		y = Math.floor(pt.getY());
//		z = Math.floor(pt.getZ());
//		Point3D point = new Point3D(x, y, z);
//		return point;
//	}
//
//	/**
//	 * 将此 Point3D平移指定的量
//	 * 
//	 * @param dx
//	 *            double X方向的偏移量
//	 * @param dy
//	 *            double Y方向的偏移量
//	 * @param dz
//	 *            double Z方向的偏移量
//	 */
//	public void offset(double dx, double dy, double dz) {
//		x += dx;
//		y += dy;
//		z += dz;
//	}
//
//	/**
//	 * 通过将 Point3D 的坐标值舍入到最接近的整数值，并以整数值为坐标构造一个新的Point3D返回
//	 * 
//	 * @param pt
//	 *            Point3D 需要转换的三维点对象
//	 * @return Point3D 转换后的三维点对象
//	 */
//	public static Point3D round(Point3D pt) {
//		double x, y, z;
//		x = Math.round(pt.getX());
//		y = Math.round(pt.getY());
//		z = Math.round(pt.getZ());
//		Point3D point = new Point3D(x, y, z);
//		return point;
//	}
//
//	/**
//	 * 返回三维点对象的字符串表示
//	 * 
//	 * @return string
//	 */
//	public String toString() {
//		return "{X=" + x + ",Y=" + y + ",Z=" + z + "}";
//	}
//
//	/**
//	 * 重写基类的equals方法
//	 * 
//	 * @param obj
//	 *            Object
//	 * @return boolean
//	 */
//
//	public boolean equals(Object obj) {
//		if (obj == null) {
//			return false;
//		}
//		if (!(obj instanceof Point3D)) {
//			return false;
//		}
//		Point3D point3D = (Point3D) obj;
//		if (Toolkit.isZero(x - point3D.getX())
//				&& Toolkit.isZero(y - point3D.getY())
//				&& Toolkit.isZero(z - point3D.getZ())) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	public boolean equals(Point3D point3D) {
//		if (point3D == null) {
//			return false;
//		}
//		if (Toolkit.isZero(x - point3D.getX())
//				&& Toolkit.isZero(y - point3D.getY())
//				&& Toolkit.isZero(z - point3D.getZ())) {
//
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	// 实现hashCode
//	public int hashCode() {
//		return (int) ((((java.lang.Double.doubleToLongBits(getX())) ^ (((java.lang.Double
//				.doubleToLongBits(getY())) << 13) | ((java.lang.Double
//				.doubleToLongBits(getY())) >> 0x13))) ^ (((java.lang.Double
//				.doubleToLongBits(getZ())) << 0x1a) | ((java.lang.Double
//				.doubleToLongBits(getZ())) >> 6))));
//
//	}
    
	
	  /**
     * 返回三维点对象的字符串表示
     * @return string
     */
    public String toString() {
        return "{X=" + x + ",Y=" + y + ",Z=" + z +"}";
    }
	
    public String toJson(){
    	return "{ \"x\" :" + x + ",\"y\" :" + y + ",\"z\" :" + z +"}";
    }
    
    public boolean fromJson(String json){
    	return fromJson(new SiJsonObject(json));
    }
    
    public boolean fromJson(SiJsonObject json){
			double x = json.getDouble("x");
			double y = json.getDouble("y");
			double z = json.getDouble("z");
			
			this.setX(x);
			this.setY(y);
			this.setZ(z);
			return true;
    }
}
