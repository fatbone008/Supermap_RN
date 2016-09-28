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
 * @author ������ jiangwh
 * @version 2.0
 */
public class GeoPoint extends Geometry {

	/**
	 * Ĭ���޲ι���������ʼ��GeoPoint��һ����ʵ������ʱx,y��Ϊ0
	 */
	public GeoPoint() {
		long handle = GeoPointNative.jni_New();
		this.setHandle(handle, true);
		reset();
	}

	/**
	 * �� x�� y��ʼ�� GeoPoint ����ʵ��
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
	 * �������캯��
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

	// �ڲ�ʹ��
	GeoPoint(long handle) {
		this.setHandle(handle, false);
	}

	/**
	 * ���մ˶���
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
	 * ���ش�GeoPoint�����X����
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
	 * ���ش�GeoPoint�����Y����
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
	 * ���ô�GeoPoint�����X����
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
	 * ���ô�GeoPoint�����Y����
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
	 * ����һ����ά�����
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
	 * ��Geometryת����GeoJSON��ʽ���ַ���
	 * @return      ����GeoJSON�ַ���
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
	 * ��GeoJSON��ʽ���ַ����л�ȡGeometry
	 * @param geoJSON  GeoJSON�ַ���
	 * @return         �����Ƿ�ת���ɹ�
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
