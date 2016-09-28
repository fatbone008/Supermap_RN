package com.supermap.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.supermap.imb.jsonlib.SiJsonObject;

import android.util.Log;


/**
 * <p>Title: 二维点对象</p>
 *
 * <p>Description:用于表示精度为Double的点对象 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 李云锦
 * @version 2.0
 */
public class Point2D {

    private double x;
    private double y;

    /**
     * 构造函数
     */
    public Point2D() {
        this.x = Toolkit.DBL_MIN_VALUE;
        this.y = Toolkit.DBL_MIN_VALUE;
    }

    /**
     * 构造函数
     * @param x double X值
     * @param y double Y值
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 拷贝构造函函数
     * @param pt Point2D
     */
    public Point2D(Point2D pt) {
        this(pt.getX(), pt.getY());
    }

    /**
     * 返回空的点对象
     * @return Point2D
     */
    public static final Point2D getEMPTY() {
        return new Point2D(Toolkit.DBL_MIN_VALUE, Toolkit.DBL_MIN_VALUE);
    }


    /**
     * 判断是否为空的Point2D对象
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
     * 返回二维点对象的X坐标
     * @return double 二维点对象的X坐标
     */
    public double getX() {
        return x;
    }

    /**
     * 设置二维点对象的X坐标
     * @param value double 二维点对象的X坐标
     */
    public void setX(double value) {
        x = value;
    }

    /**
     * 返回二维点对象的Y坐标
     * @return double 二维点对象的Y坐标
     */
    public double getY() {
        return y;
    }

    /**
     * 设置二维点对象的Y坐标
     * @param value double 二维点对象的Y坐标
     */
    public void setY(double value) {
        y = value;
    }

    /**
     * 克隆对象
     * @return Point2D
     */
    public Point2D clone() {
        return new Point2D(this);
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
     * 通过将 Point2D 的坐标值舍入到与其接近的较大整数值，并以整数值构造一个新的Point2D返回
     * @param pt Point2D 需要转换的二维点对象
     * @return Point 转换后的二维点对象
     */
    public static Point2D ceiling(Point2D pt) {
        double x, y;
        x = Math.ceil(pt.getX());
        y = Math.ceil(pt.getY());
        Point2D point = new Point2D(x, y);
        return point;
    }

    /**
     * 通过将 Point2D 的坐标值舍入到最接近的整数值，并以整数值为坐标构造一个新的Point2D返回
     * @param pt Point2D 需要转换的二维点对象
     * @return Point 转换后的二维点对象
     */
    public static Point2D round(Point2D pt) {
        double x, y;
        x = Math.round(pt.getX());
        y = Math.round(pt.getY());
        Point2D point = new Point2D(x, y);
        return point;
    }

    /**
     * 返回小于或等于比pt坐标值的最大整数对构造的Point2D
     * @param pt Point2D 需要转换的二维点对象
     * @return Point 转换后的二维点对象
     */
    public static Point2D floor(Point2D pt) {
        double x, y;
        x = Math.floor(pt.getX());
        y = Math.floor(pt.getY());
        Point2D point = new Point2D(x, y);
        return point;
    }

    /**
     * 将此 Point2D平移指定的量
     * @param dx double X方向的偏移量
     * @param dy double Y方向的偏移量
     */
    public void offset(double dx, double dy) {
        x += dx;
        y += dy;
    }
    
    /**
     * 返回二维点对象的字符串表示
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
