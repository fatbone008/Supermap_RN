package com.supermap.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.supermap.imb.jsonlib.SiJsonObject;

import android.util.Log;


/**
 * <p>Title: ��ά�����</p>
 *
 * <p>Description:���ڱ�ʾ����ΪDouble�ĵ���� </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ���ƽ�
 * @version 2.0
 */
public class Point2D {

    private double x;
    private double y;

    /**
     * ���캯��
     */
    public Point2D() {
        this.x = Toolkit.DBL_MIN_VALUE;
        this.y = Toolkit.DBL_MIN_VALUE;
    }

    /**
     * ���캯��
     * @param x double Xֵ
     * @param y double Yֵ
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * �������캯����
     * @param pt Point2D
     */
    public Point2D(Point2D pt) {
        this(pt.getX(), pt.getY());
    }

    /**
     * ���ؿյĵ����
     * @return Point2D
     */
    public static final Point2D getEMPTY() {
        return new Point2D(Toolkit.DBL_MIN_VALUE, Toolkit.DBL_MIN_VALUE);
    }


    /**
     * �ж��Ƿ�Ϊ�յ�Point2D����
     * @return boolean
     */
    public boolean isEmpty() {
        boolean isEmpty = false;
        if (Toolkit.isZero(x - Toolkit.DBL_MIN_VALUE,
                           Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
                           Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION) &&
            Toolkit.isZero(y - Toolkit.DBL_MIN_VALUE,
                           Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
                           Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION)) {
            isEmpty = true;
        }
        return isEmpty;
    }

    /**
     * ���ض�ά������X����
     * @return double ��ά������X����
     */
    public double getX() {
        return x;
    }

    /**
     * ���ö�ά������X����
     * @param value double ��ά������X����
     */
    public void setX(double value) {
        x = value;
    }

    /**
     * ���ض�ά������Y����
     * @return double ��ά������Y����
     */
    public double getY() {
        return y;
    }

    /**
     * ���ö�ά������Y����
     * @param value double ��ά������Y����
     */
    public void setY(double value) {
        y = value;
    }

    /**
     * ��¡����
     * @return Point2D
     */
    public Point2D clone() {
        return new Point2D(this);
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
        if (!(obj instanceof Point2D)) {
            return false;
        }
        Point2D pt = (Point2D) obj;
        if (Toolkit.isZero(x - pt.getX()) &&
            Toolkit.isZero(y - pt.getY())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equals(Point2D pt) {
        if (pt == null) {
            return false;
        }
        if (Toolkit.isZero(x - pt.getX()) &&
            Toolkit.isZero(y - pt.getY())) {
            return true;
        } else {
            return false;
        }
    }


    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(getX());
        bits ^= java.lang.Double.doubleToLongBits(getY()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    /**
     * ͨ���� Point2D ������ֵ���뵽����ӽ��Ľϴ�����ֵ����������ֵ����һ���µ�Point2D����
     * @param pt Point2D ��Ҫת���Ķ�ά�����
     * @return Point ת����Ķ�ά�����
     */
    public static Point2D ceiling(Point2D pt) {
        double x, y;
        x = Math.ceil(pt.getX());
        y = Math.ceil(pt.getY());
        Point2D point = new Point2D(x, y);
        return point;
    }

    /**
     * ͨ���� Point2D ������ֵ���뵽��ӽ�������ֵ����������ֵΪ���깹��һ���µ�Point2D����
     * @param pt Point2D ��Ҫת���Ķ�ά�����
     * @return Point ת����Ķ�ά�����
     */
    public static Point2D round(Point2D pt) {
        double x, y;
        x = Math.round(pt.getX());
        y = Math.round(pt.getY());
        Point2D point = new Point2D(x, y);
        return point;
    }

    /**
     * ����С�ڻ���ڱ�pt����ֵ����������Թ����Point2D
     * @param pt Point2D ��Ҫת���Ķ�ά�����
     * @return Point ת����Ķ�ά�����
     */
    public static Point2D floor(Point2D pt) {
        double x, y;
        x = Math.floor(pt.getX());
        y = Math.floor(pt.getY());
        Point2D point = new Point2D(x, y);
        return point;
    }

    /**
     * ���� Point2Dƽ��ָ������
     * @param dx double X�����ƫ����
     * @param dy double Y�����ƫ����
     */
    public void offset(double dx, double dy) {
        x += dx;
        y += dy;
    }
    
    /**
     * ���ض�ά�������ַ�����ʾ
     * @return string
     */
    public String toString() {
        return "{X=" + x + ",Y=" + y +"}";
    }
    
    public String toJson(){
    	return "{ \"x\" :" + x + ",\"y\" :" + y +"}";
    }
    
    public boolean fromJson(String json){
    	return fromJson(new SiJsonObject(json));
    }
    
    public boolean fromJson(SiJsonObject json){
			double x = json.getDouble("x");
			double y = json.getDouble("y");
			
			this.setX(x);
			this.setY(y);
			return true;
    }
}
