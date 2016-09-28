package com.supermap.data;


/**
 * <p>Title: </p>
 *
 * <p>Description: �洢����˫�������ԣ�ͨ��Ϊ���εĿ�Ⱥ͸߶�</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ������
 * @version 2.0
 */
public class Size2D {
    private double width, height;

    /**
     * Size2D �չ��캯����
     */
    public Size2D() {
        this.width = Toolkit.DBL_MIN_VALUE;
        this.height = Toolkit.DBL_MIN_VALUE;
    }

    /**
     * ��ָ��ˮƽ�봹ֱ��������ʼ��һ��Size2Dʵ��
     * @param width double  Size2D��Width��ֵ
     * @param height double  Size2D��Height��ֵ
     */
    public Size2D(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * ��һ����֪��Size2D����һ��Size2D����ʵ��
     * @param pt Point2D
     */
    public Size2D(Size2D sz) {
        this.width = sz.getWidth();
        this.height = sz.getHeight();
    }

    public final static Size2D getEMPTY() {
        return new Size2D(Toolkit.DBL_MIN_VALUE,Toolkit.DBL_MIN_VALUE); //��Size2D���� Width��Heightֵ��ΪToolkit.DBL_MIN_VALUE
    }

    /**
     * ��ȡ�� Size2D �Ĵ�ֱ����
     * @return double
     */
    public double getHeight() {
        return height;
    }

    /**
     * ��ȡ�� Size2D ��ˮƽ����.
     * @return double   Size2D ��ˮƽ����
     */
    public double getWidth() {
        return width;
    }

    /**
     * ��ȡ�� Size2D �Ĵ�ֱ����
     * @param height double   Size2D �Ĵ�ֱ����
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * ���ô� Size2D ��ˮƽ����.
     * @param width double   Size2D ��ˮƽ����
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Size2D�Ƿ�Ϊ�գ���Ϊ��,�� Width��Heightֵ��ΪToolkit.DBL_MIN_VALUE
     * @return boolean  Ϊ���򷵻�True�����򷵻�False
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
     * ��д����equals
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
     * ��Size2Dת����Size2D ����ȥС�����֡����磺Size2D(2.3,6.8)->Size2D(2,6)
     * @param sz Size2D
     * @return Size2D
     */
    public static Size2D floor(Size2D sz) {
        double widthNew = Math.floor(sz.getWidth());
        double heightNew = Math.floor(sz.getHeight());
        return new Size2D(widthNew, heightNew);
    }

    /**
     * ͨ���� Size2D ��Width,Heightֵ���뵽����ӽ��Ľϴ�����ֵ����������ֵ����һ���µ�Size2D����
     * @param sz Size2D
     * @return Size2D
     */
    public static Size2D ceiling(Size2D sz) {
        double widthNew = Math.ceil(sz.getWidth());
        double heightNew = Math.ceil(sz.getHeight());
        return new Size2D(widthNew, heightNew);
    }

    /**
     * ��Size2D�ķ��������������봦���磺Size2D(2.3,6.8)->Size2D(2,7)
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
     * ��¡
     * @return Size2D
     */
    public Size2D clone() {
        return new Size2D(this);
    }
}
