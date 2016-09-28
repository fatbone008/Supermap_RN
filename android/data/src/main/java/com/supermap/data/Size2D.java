package com.supermap.data;


/**
 * <p>Title: </p>
 *
 * <p>Description: 存储有序双精度数对，通常为矩形的宽度和高度</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 孔令亮
 * @version 2.0
 */
public class Size2D {
    private double width, height;

    /**
     * Size2D 空构造函数。
     */
    public Size2D() {
        this.width = Toolkit.DBL_MIN_VALUE;
        this.height = Toolkit.DBL_MIN_VALUE;
    }

    /**
     * 用指定水平与垂直分量来初始化一个Size2D实例
     * @param width double  Size2D的Width的值
     * @param height double  Size2D的Height的值
     */
    public Size2D(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * 由一个已知的Size2D构造一个Size2D的新实例
     * @param pt Point2D
     */
    public Size2D(Size2D sz) {
        this.width = sz.getWidth();
        this.height = sz.getHeight();
    }

    public final static Size2D getEMPTY() {
        return new Size2D(Toolkit.DBL_MIN_VALUE,Toolkit.DBL_MIN_VALUE); //空Size2D对象， Width，Height值均为Toolkit.DBL_MIN_VALUE
    }

    /**
     * 获取此 Size2D 的垂直分量
     * @return double
     */
    public double getHeight() {
        return height;
    }

    /**
     * 获取此 Size2D 的水平分量.
     * @return double   Size2D 的水平分量
     */
    public double getWidth() {
        return width;
    }

    /**
     * 获取此 Size2D 的垂直分量
     * @param height double   Size2D 的垂直分量
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * 设置此 Size2D 的水平分量.
     * @param width double   Size2D 的水平分量
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Size2D是否为空，若为空,则 Width，Height值均为Toolkit.DBL_MIN_VALUE
     * @return boolean  为空则返回True，否则返回False
     */
    public boolean isEmpty() {
        boolean isEmpty = Toolkit.isZero(this.getWidth() - Toolkit.DBL_MIN_VALUE,
                                         Environment.
                                         DEFAULT_MIN_EQUAL_ZERO_PRECISION,
                                         Environment.
                                         DEFAULT_MAX_EQUAL_ZERO_PRECISION) &&
                          Toolkit.isZero(this.getHeight() - Toolkit.DBL_MIN_VALUE,
                                         Environment.
                                         DEFAULT_MIN_EQUAL_ZERO_PRECISION,
                                         Environment.
                                         DEFAULT_MAX_EQUAL_ZERO_PRECISION);
        return isEmpty;
    }
    
    /**
     * 重写基类equals
     * @param obj Object
     * @return boolean
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Size2D)) {
            return false;
        }
        Size2D sz = (Size2D) obj;
        boolean isEquals = Toolkit.isZero(this.height - sz.getHeight()) &&
                           Toolkit.isZero(this.width - sz.getWidth());
        return isEquals;
    }

    public boolean equals(Size2D sz) {
        if (sz == null) {
            return false;
        }

        boolean isEquals = Toolkit.isZero(this.height - sz.getHeight()) &&
                           Toolkit.isZero(this.width - sz.getWidth());
        return isEquals;
    }

    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(getWidth());
        bits ^= java.lang.Double.doubleToLongBits(getHeight()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));

    }

    /**
     * 将Size2D转换成Size2D ，舍去小数部分。例如：Size2D(2.3,6.8)->Size2D(2,6)
     * @param sz Size2D
     * @return Size2D
     */
    public static Size2D floor(Size2D sz) {
        double widthNew = Math.floor(sz.getWidth());
        double heightNew = Math.floor(sz.getHeight());
        return new Size2D(widthNew, heightNew);
    }

    /**
     * 通过将 Size2D 的Width,Height值舍入到与其接近的较大整数值，并以整数值构造一个新的Size2D返回
     * @param sz Size2D
     * @return Size2D
     */
    public static Size2D ceiling(Size2D sz) {
        double widthNew = Math.ceil(sz.getWidth());
        double heightNew = Math.ceil(sz.getHeight());
        return new Size2D(widthNew, heightNew);
    }

    /**
     * 将Size2D的分量进行四舍五入处理。如：Size2D(2.3,6.8)->Size2D(2,7)
     * @param sz Size2D
     * @return Size2D
     */
    public static Size2D round(Size2D sz) {
        double widthNew = Math.round(sz.getWidth());
        double heightNew = Math.round(sz.getHeight());
        return new Size2D(widthNew, heightNew);
    }

    /**
     *
     * @return String
     */
    public String toString() {
        return "Width=" + getWidth() + ",Height=" + getHeight();
    }

    /**
     * 克隆
     * @return Size2D
     */
    public Size2D clone() {
        return new Size2D(this);
    }
}
