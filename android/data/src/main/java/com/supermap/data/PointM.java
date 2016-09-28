package com.supermap.data;


/**
 * <p>Title: ��ά·�ߵ����</p>
 *
 * <p>Description: ����ΪDouble��·�ߵ����,M����·�ߵ�Ķ���ֵ��Measure value��</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author �ż���
 * @version 2.0
 */
public class PointM {

    /**
     * ���ؿյ�·�ߵ����
     * @return PointM
     */
    public static final PointM getEMPTY() {
        return new PointM(Toolkit.DBL_MIN_VALUE, Toolkit.DBL_MIN_VALUE, Toolkit.DBL_MIN_VALUE);
    }

    /**
     * ���캯��
     */
    public PointM() {
        this.x = Toolkit.DBL_MIN_VALUE;
        this.y = Toolkit.DBL_MIN_VALUE;
        this.m = Toolkit.DBL_MIN_VALUE;
    }

    /**
     * ���캯��
     * @param x double Xֵ
     * @param y double Yֵ
     * @param m double Mֵ
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
     * ���ض�ά·�ߵ�����X����
     * @return double ��ά·�ߵ�����X����
     */
    public double getX() {
        return x;
    }

    /**
     * ���ö�ά·�ߵ�����X����
     * @param value double ��ά·�ߵ�����X����
     */
    public void setX(double value) {
        x = value;
    }

    /**
     * ���ض�ά·�ߵ�����Y����
     * @return double ��ά·�ߵ�����Y����
     */
    public double getY() {
        return y;
    }

    /**
     * ���ö�ά·�ߵ�����Y����
     * @param value double ��ά·�ߵ�����Y����
     */
    public void setY(double value) {
        y = value;
    }

    /**
     * ���ض�ά·�ߵ�����Mֵ
     * @return double ��ά·�ߵ�����Mֵ
     */
    public double getM() {
        return m;
    }

    /**
     * ���ö�ά·�ߵ�����Mֵ
     * @param value double ��ά·�ߵ�����Mֵ
     */
    public void setM(double value) {
        this.m = value;
    }


    /**
     * �������캯����
     * @param pt PointM
     */
    public PointM(PointM pointM) {
        this(pointM.getX(), pointM.getY(), pointM.getM());
    }

    /**
     * ��¡����
     * @return PointM
     */
    public PointM clone() {
        return new PointM(this);
    }

    /**
     * ��д�����equals����
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
     * ���ض�ά·�ߵ������ַ�����ʾ
     * @return string
     */
    public String toString() {
        return "{X=" + x + ",Y=" + y + ",M=" + m+"}";
    }


    private double x;
    private double y;
    private double m;
}

