package com.supermap.data;

import com.supermap.imb.jsonlib.SiJsonObject;

/**
 * <p>
 * Title: ��ά�����
 * </p>
 * 
 * <p>
 * Description: ���ڱ�ʾ����ΪDouble�ĵ����
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
//	 * ���ؿյ���ά�����,ע�ⲻ��(0,0,0)
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

	// ע�ⲻ��(0,0,0)
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
	 * ��¡����
	 * 
	 * @return Point3D
	 */
	public Point3D clone() {
		return new Point3D(this);
	}
//
//	/**
//	 * ͨ���� Point3D ������ֵ���뵽����ӽ��Ľϴ�����ֵ����������ֵ����һ���µ�Point3D����
//	 * 
//	 * @param pt
//	 *            Point3D ��Ҫת������ά�����
//	 * @return Point ת�������ά�����
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
//	 * ����С�ڻ���ڱ�pt����ֵ����������Թ����Point3D
//	 * 
//	 * @param pt
//	 *            Point3D ��Ҫת������ά�����
//	 * @return Point ת�������ά�����
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
//	 * ���� Point3Dƽ��ָ������
//	 * 
//	 * @param dx
//	 *            double X�����ƫ����
//	 * @param dy
//	 *            double Y�����ƫ����
//	 * @param dz
//	 *            double Z�����ƫ����
//	 */
//	public void offset(double dx, double dy, double dz) {
//		x += dx;
//		y += dy;
//		z += dz;
//	}
//
//	/**
//	 * ͨ���� Point3D ������ֵ���뵽��ӽ�������ֵ����������ֵΪ���깹��һ���µ�Point3D����
//	 * 
//	 * @param pt
//	 *            Point3D ��Ҫת������ά�����
//	 * @return Point3D ת�������ά�����
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
//	 * ������ά�������ַ�����ʾ
//	 * 
//	 * @return string
//	 */
//	public String toString() {
//		return "{X=" + x + ",Y=" + y + ",Z=" + z + "}";
//	}
//
//	/**
//	 * ��д�����equals����
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
//	// ʵ��hashCode
//	public int hashCode() {
//		return (int) ((((java.lang.Double.doubleToLongBits(getX())) ^ (((java.lang.Double
//				.doubleToLongBits(getY())) << 13) | ((java.lang.Double
//				.doubleToLongBits(getY())) >> 0x13))) ^ (((java.lang.Double
//				.doubleToLongBits(getZ())) << 0x1a) | ((java.lang.Double
//				.doubleToLongBits(getZ())) >> 6))));
//
//	}
    
	
	  /**
     * ������ά�������ַ�����ʾ
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
