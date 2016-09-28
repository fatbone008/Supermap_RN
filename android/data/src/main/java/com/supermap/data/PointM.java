package com.supermap.data;


/**
 * <p>Title: 二维路线点对象</p>
 *
 * <p>Description: 精度为Double的路线点对象,M代表路线点的度量值（Measure value）</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 张继南
 * @version 2.0
 */
public class PointM {

    /**
     * 返回空的路线点对象
     * @return PointM
     */
    public static final PointM getEMPTY() {
        return new PointM(Toolkit.DBL_MIN_VALUE, Toolkit.DBL_MIN_VALUE, Toolkit.DBL_MIN_VALUE);
    }

    /**
     * 构造函数
     */
    public PointM() {
        this.x = Toolkit.DBL_MIN_VALUE;
        this.y = Toolkit.DBL_MIN_VALUE;
        this.m = Toolkit.DBL_MIN_VALUE;
    }

    /**
     * 构造函数
     * @param x double X值
     * @param y double Y值
     * @param m double M值
     */
    public PointM(double x, double y, double m) {
        this.x = x;
        this.y = y;
        this.m = m;
    }

    public boolean isEmpty() {
        boolean isEmpty = false;
        if (Toolkit.isZero(getX() - Toolkit.DBL_MIN_VALUE,
                           Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
                           Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION) &&
            Toolkit.isZero(getY() - Toolkit.DBL_MIN_VALUE,
                           Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
                           Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION) &&
            Toolkit.isZero(getM() - Toolkit.DBL_MIN_VALUE,
                           Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
                           Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION)) {
            isEmpty = true;
        }
        return isEmpty;
    }


    /**
     * 返回二维路线点对象的X坐标
     * @return double 二维路线点对象的X坐标
     */
    public double getX() {
        return x;
    }

    /**
     * 设置二维路线点对象的X坐标
     * @param value double 二维路线点对象的X坐标
     */
    public void setX(double value) {
        x = value;
    }

    /**
     * 返回二维路线点对象的Y坐标
     * @return double 二维路线点对象的Y坐标
     */
    public double getY() {
        return y;
    }

    /**
     * 设置二维路线点对象的Y坐标
     * @param value double 二维路线点对象的Y坐标
     */
    public void setY(double value) {
        y = value;
    }

    /**
     * 返回二维路线点对象的M值
     * @return double 二维路线点对象的M值
     */
    public double getM() {
        return m;
    }

    /**
     * 设置二维路线点对象的M值
     * @param value double 二维路线点对象的M值
     */
    public void setM(double value) {
        this.m = value;
    }


    /**
     * 拷贝构造函函数
     * @param pt PointM
     */
    public PointM(PointM pointM) {
        this(pointM.getX(), pointM.getY(), pointM.getM());
    }

    /**
     * 克隆对象
     * @return PointM
     */
    public PointM clone() {
        return new PointM(this);
    }

    /**
     * 重写基类的equals方法
     * @param obj Object
     * @return boolean
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PointM)) {
            return false;
        }
        PointM ptM = (PointM) obj;
        if (Toolkit.isZero(x - ptM.getX()) &&
            Toolkit.isZero(y - ptM.getY()) &&
            Toolkit.isZero(m - ptM.getM())) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {

        return (int) ((((java.lang.Double.doubleToLongBits(getX())) ^
                        (((java.lang.Double.doubleToLongBits(getY())) << 13) |
                         ((java.lang.Double.doubleToLongBits(getY())) >> 0x13))) ^
                       (((java.lang.Double.doubleToLongBits(getM())) << 0x1a) |
                        ((java.lang.Double.doubleToLongBits(getM())) >> 6))));

    }

    /**
     * 返回二维路线点对象的字符串表示
     * @return string
     */
    public String toString() {
        return "{X=" + x + ",Y=" + y + ",M=" + m+"}";
    }


    private double x;
    private double y;
    private double m;
}

