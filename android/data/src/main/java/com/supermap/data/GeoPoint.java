package com.supermap.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.supermap.imb.jsonlib.SiJsonObject;

import android.util.Log;

import java.util.ArrayList;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
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
 * @author 孔令亮 jiangwh
 * @version 2.0
 */
public class GeoPoint extends Geometry {

	/**
	 * 默认无参构造器，初始化GeoPoint的一个新实例，此时x,y均为0
	 */
	public GeoPoint() {
		long handle = GeoPointNative.jni_New();
		this.setHandle(handle, true);
		reset();
	}

	/**
	 * 从 x和 y初始化 GeoPoint 的新实例
	 * 
	 * @param x
	 *            double
	 * @param y
	 *            double
	 */
	public GeoPoint(double x, double y) {
		this();
		this.setX(x);
		this.setY(y);
	}

	/**
	 * 拷贝构造函数
	 * 
	 * @param geoPoint
	 *            GeoPoint
	 */
	public GeoPoint(GeoPoint point) {
		if (point.getHandle() == 0) {
			String message = InternalResource.loadString("point",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);

		}
		long handle = GeoPointNative.jni_Clone(point.getHandle());
		this.setHandle(handle, true);
	}

	public GeoPoint(Point2D point) {
		this(point.getX(), point.getY());
	}

	// 内部使用
	GeoPoint(long handle) {
		this.setHandle(handle, false);
	}

	/**
	 * 回收此对象
	 */
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoPointNative.jni_Delete(getHandle());
			this.setHandle(0);
			super.clearHandle();
		}
	}

	/**
	 * 返回此GeoPoint对象的X坐标
	 * 
	 * @return double
	 */
	public double getX() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getX()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoPointNative.jni_GetX(getHandle());
	}

	/**
	 * 返回此GeoPoint对象的Y坐标
	 * 
	 * @return double
	 */
	public double getY() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getY()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return GeoPointNative.jni_GetY(getHandle());
	}

	/**
	 * 设置此GeoPoint对象的X坐标
	 * 
	 * @param x
	 *            double
	 * 
	 */
	public void setX(double x) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setX()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoPointNative.jni_SetX(getHandle(), x);
	}

	/**
	 * 设置此GeoPoint对象的Y坐标
	 * 
	 * @param y
	 *            double
	 */
	public void setY(double y) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setY()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoPointNative.jni_SetY(getHandle(), y);
	}

	public boolean isEmpty() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getIsEmpty()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean isEmpty = Toolkit.isZero(this.getX() - Toolkit.DBL_MIN_VALUE,
				Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
				Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION)
				&& Toolkit.isZero(this.getY() - Toolkit.DBL_MIN_VALUE,
						Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
						Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION);
		return isEmpty;
	}
	
	/**
	 * 复制一个二维点对象
	 * 
	 * @return GeoPoint
	 */
	public GeoPoint clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoPoint(this);
	}

	void reset() {
		this.setX(Toolkit.DBL_MIN_VALUE);
		this.setY(Toolkit.DBL_MIN_VALUE);
	}

	@Override
	public boolean fromJson(String json) {
		SiJsonObject obj = new SiJsonObject(json);
		boolean ret = fromJson(obj);
		obj.dispose();
		return ret;
	}
	
	@Override
	public boolean fromJson(SiJsonObject json) {
		if(super.fromJson(json)){
			double x = json.getJsonArray("points").getJsonObject(0).getDouble("x");
			double y = json.getJsonArray("points").getJsonObject(0).getDouble("y");
			this.setX(x);
			this.setY(y);
			return true;
		}
		
		return false;
	}

	@Override
	public String toJson() {
		StringBuilder sb = new StringBuilder(super.toJson());
		sb.deleteCharAt(sb.length()-1);
		sb.append(",");
		sb.append(" \"parts\" :" + "[1]" + ",");
		sb.append(" \"type\" :" + "\"POINT\"" + ",");
		sb.append(" \"points\" : " + "[" + new Point2D(this.getX(), this.getY()).toJson()+ "]");
		sb.append("}");
		return sb.toString();
	}
	
    /**
	 * 将Geometry转换成GeoJSON格式的字符串
	 * @return      返回GeoJSON字符串
	 */
	@Override
	public String toGeoJSON(){
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("{\"type\": \"Point\",");
		strBuilder.append("\"coordinates\":[");
		strBuilder.append(this.getX() + "," + this.getY());
		strBuilder.append("]}");
		return strBuilder.toString();
	}
	
	/**
	 * 从GeoJSON格式的字符串中获取Geometry
	 * @param geoJSON  GeoJSON字符串
	 * @return         返回是否转换成功
	 */
	@Override
	public boolean fromGeoJSON(String geoJSON){
		if (!geoJSON.contains("Point")) {
			Log.e("GeoPoint", "Not match the type of Point");
			return false;
		}
		ArrayList<Point2Ds> ptsList = getPointsFromGeoJSON(geoJSON);
		if (ptsList.size() > 0) {
			this.setX(ptsList.get(0).getItem(0).getX());
			this.setY(ptsList.get(0).getItem(0).getY());
		}
		return true;
	}
}
