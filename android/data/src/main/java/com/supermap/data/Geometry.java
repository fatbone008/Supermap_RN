package com.supermap.data;

import java.util.ArrayList;
import java.util.Vector;

import com.supermap.imb.jsonlib.SiJsonObject;
import com.supermap.plot.GeoGraphicObject;

/**
 * <p>
 * Title:���ζ���
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
 * @author ���ƽ�
 * @version 2.0
 */
public abstract class Geometry extends InternalHandleDisposable {
	private GeoStyle m_style = null;
	private boolean m_setStyle = false;
	transient static Vector m_customGeometryCreateListeners;

	/**
	 * ���캯��,�����ܵ���
	 */
	protected Geometry() {
	}

	/**
	 * ��ü��ζ���Ŀռ䷶Χ
	 * @return Rectangle2D
	 */
	public Rectangle2D getBounds() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getBounds()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		double[] bounds = new double[4];
		GeometryNative.jni_GetBounds(getHandle(), bounds);

		Rectangle2D rcBounds = new Rectangle2D(bounds[0], bounds[1], bounds[2],
				bounds[3]);
		return rcBounds;
	}

	/**
	 * ��ȡ�ڵ�
	 * 
	 * @return Point2D
	 */
	public Point2D getInnerPoint() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getInnerPoint()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		double[] params = new double[2];
		GeometryNative.jni_GetInnerPoint(getHandle(), params);
		Point2D point = new Point2D(params[0], params[1]);
		return point;
	}

	/**
	 * ��ȡê��
	 * 
	 * @return Point2D
	 */
	public Point2D getHandles(int index) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getInnerPoint()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		double[] params = new double[2];
		GeometryNative.jni_GetHandles(getHandle(), index, params);
		Point2D point = new Point2D(params[0], params[1]);
		return point;
	}
	
	/**
	 * ��ȡê����ڵ����
	 * 
	 * @return Point2D
	 */
	public int getHandleCount() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getInnerPoint()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		int handleCount = GeometryNative.jni_GetHandleCount(getHandle());
		return handleCount;
	}
	
	/**
	 * ��ȡID
	 * 
	 * @return int
	 */
	public int getID() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getID()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return GeometryNative.jni_GetID(getHandle());
	}

	/**
	 * �����ṩ
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		return false;
	}

	/**
	 * ��ȡ���ζ������ʽ
	 * 
	 * @return GeoStyle
	 */
	public GeoStyle getStyle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getStyle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (this.m_style == null) {
			// modified by liyj: ֧������,ֱ�ӷ��ض���
			long stylePtr = GeometryNative.jni_GetStyle(getHandle());
			if (stylePtr != 0) {
				this.m_style = GeoStyle.createInstance(stylePtr);
			}
		}
		return this.m_style;
	}

	/**
	 * ���ü��ζ������ʽ
	 * 
	 * @param value
	 *            GeoStyle
	 */
	public void setStyle(GeoStyle value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setStyle(GeoStyle value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeometryType type = getType();
		// �����ı���֧�ָ�����
		if (type == GeometryType.GEOTEXT) {
			String message = InternalResource.loadString("value",
					InternalResource.GeoTextUnsupprotStyle,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		}

		if (value == null) {
			if (m_style != null) {
				m_style.clearHandle();
				m_style = null;
			}
			GeometryNative.jni_SetStyle(getHandle(), 0);
		} else {
			if (value.getHandle() == 0) {
				String message = InternalResource.loadString("value",
						InternalResource.GlobalArgumentObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			// ��¡�¶���,����ָ��ΪUGStyle���ͣ��ײ�Ͳ����ж���
			GeoStyle style = (GeoStyle) value.clone();
			GeometryNative.jni_SetStyle(getHandle(), style.getHandle());
			style.dispose();
		}
		this.m_setStyle = true;
	}

	/**
	 * ��ȡ���ζ��������
	 * 
	 * @return int
	 */
	public GeometryType getType() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getType()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		int ugcType = GeometryNative.jni_GetType(getHandle());
		try {
			return (GeometryType) Enum.parseUGCValue(GeometryType.class, ugcType);
		} catch (Exception e) {
			return new GeometryType(ugcType, ugcType);
		}
	}

	/**
	 * ��¡���ζ���
	 * 
	 * @return Geometry
	 */
	public abstract Geometry clone();

	/**
	 * �������
	 * 
	 * @param pt
	 *            Point2D
	 * @param tolerance
	 *            double
	 * @return boolean
	 */
	public boolean hitTest(Point2D point, double tolerance) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"hitTest(Point2D point, double tolerance)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (tolerance < 0) {
			String message = InternalResource.loadString("tolerance",
					InternalResource.GeometryInvalidTolerance,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return GeometryNative.jni_HitTest(getHandle(), point.getX(), point
				.getY(), tolerance);
	}

	/**
	 * ƫ��
	 * 
	 * @param dx
	 *            double
	 * @param dy
	 *            double
	 */
	public void offset(double dx, double dy) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"offset(double dx, double dy)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		GeometryNative.jni_Offset(getHandle(), dx, dy);
	}

	/**
	 * �ı伸�ζ���Ĵ�С �����Ϊ����ʱ����ˮƽ��ת�����߶�Ϊ����ʱ������ֱ��ת
	 * 
	 * @param bounds
	 *            Rectangle2D
	 */
	public void resize(Rectangle2D bounds) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"resize(Rectangle2D bounds)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (this.getType() == GeometryType.GEOREGION) {
			if (bounds.getWidth() == 0) {
				String message = InternalResource.loadString("bounds",
						InternalResource.GeometryResizeBoundsWidthIsZero,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			if (bounds.getHeight() == 0) {
				String message = InternalResource.loadString("bounds",
						InternalResource.GeometryResizeBoundsHeightIsZero,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}
		GeometryNative.jni_Resize(getHandle(), bounds.getLeft(), bounds
				.getTop(), bounds.getRight(), bounds.getBottom());
		// �����GeoText���ͣ���Resize��Ҫ����һ��textStyle��width��height��
		if (this.getType() == GeometryType.GEOTEXT) {
			GeoText geoText = (GeoText) this;
			double width = geoText.getTextStyle().getFontWidth();
			double height = geoText.getTextStyle().getFontHeight();
			if (width < 0) {
				geoText.getTextStyle().setFontWidth(Math.abs(width));
			}
			if (height < 0) {
				geoText.getTextStyle().setFontHeight(Math.abs(height));
			}
		}
		else if (this.getType() == GeometryType.GEOREGION)
		{
			GeoRegion geoRegion = (GeoRegion) this;
			geoRegion.refrashPartsList();
		}
	}

	/**
	 * ��ת���ζ���
	 * 
	 * @param basePoint
	 *            Point2D
	 * @param angle
	 *            double
	 */
	public void rotate(Point2D basePoint, double angle) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"rotate(Point2D basePoint, double angle)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		GeometryNative.jni_Rotate(getHandle(), basePoint.getX(), basePoint
				.getY(), angle);
	}

	/**
	 * ��ռ��ζ���,��ʱ����
	 */
	private void setEmpty() {

	}
	
	protected static final Geometry internalCreateInstance2(long geoHandle,
			Workspace workspace) {
		return Geometry.createInstance2(geoHandle, workspace);
	}
	
	// �ڲ�ʹ�ã�����Geometry
	static final Geometry createInstance(long geoHandle) {
		if (geoHandle == 0) {
			return null;
		}
		Geometry geo = null;
		int ugcType = GeometryNative.jni_GetType(geoHandle);
		int[] values = Enum.getValues(GeometryType.class);
		boolean isOtherType = true;
		for (int i = 0; i < values.length; i++) {
			if (ugcType == values[i]) {
				isOtherType = false;
				break;
			}
		}
		if(!isOtherType) {
			GeometryType geoType = (GeometryType) Enum.parseUGCValue(
					GeometryType.class, ugcType);
			// �˴��ò�����switch case,�ʲ��õ���if else
			if (geoType.equals(GeometryType.GEOPOINT)) {
				geo = new GeoPoint(geoHandle);
			} else if (geoType.equals(GeometryType.GEOLINE)) {
				geo = new GeoLine(geoHandle);
			} else if (geoType.equals(GeometryType.GEOREGION)) {
				geo = new GeoRegion(geoHandle);
			} else if (geoType.equals(GeometryType.GEOTEXT)) {
				geo = new GeoText(geoHandle);
			} else if (geoType.equals(GeometryType.GEOLINEM)) {
				geo = new GeoLineM(geoHandle);
			} else if (geoType.equals(GeometryType.GEOPOINT3D)) {
				geo = new GeoPoint3D(geoHandle);
			} else if (geoType.equals(GeometryType.GEOLINE3D)) {
				geo = new GeoLine3D(geoHandle);
			} else if (geoType.equals(GeometryType.GEOREGION3D)) {
				geo = new GeoRegion3D(geoHandle);
			} else if (geoType.equals(GeometryType.GEOPLACEMARK)) {
				geo = new GeoPlacemark(geoHandle);
			} 
		}

		if (geo != null) {
			// Ŀǰ���õ��Ķ�����Ҫ�ͷŵ�
			geo.setIsDisposable(true);
		}
		return geo;
	}
	
	/**
	 * �������඼����ʵ��dispose����
	 */
	public abstract void dispose();

	protected void clearHandle() {
		if (m_style != null) {
			m_style.clearHandle();
			m_style = null;
		}
		this.setHandle(0);
	}

	// addby xuzw 2009-02-04 ���clearHandle��̬��������Feature3D�л��õ�
	protected static void clearHandle(Geometry geometry) {
		geometry.clearHandle();
	}

	static final Geometry createInstance(GeometryType geoType) {
		Geometry geometry = null;
		// �˴��ò�����switch case,�ʲ��õ���if else
		if (geoType.equals(GeometryType.GEOPOINT)) {
			geometry = new GeoPoint();
		} else if (geoType.equals(GeometryType.GEOLINE)) {
			geometry = new GeoLine();
		} else if (geoType.equals(GeometryType.GEOREGION)) {
			geometry = new GeoRegion();
		} else if (geoType.equals(GeometryType.GEOTEXT)) {
			geometry = new GeoText();
		} else {
			throw new RuntimeException("$$$������ͻ�δʵ��");
		}
		
		// �û������Ķ�������ǿ��ͷŵ�
		geometry.setIsDisposable(true);
		return geometry;
	}
	
	// �ڲ�ʹ�ã�����Geometry
	static final Geometry createInstance2(long geoHandle, Workspace workspace) {
		if (geoHandle == 0) {
			return null;
		}
		Geometry geo = null;
		int ugcType = GeometryNative.jni_GetType(geoHandle);
		int[] values = Enum.getValues(GeometryType.class);
		boolean isOtherType = true;
		for (int i = 0; i < values.length; i++) {
			if (ugcType == values[i]) {
				isOtherType = false;
				break;
			}
		}
		if (!isOtherType) {
			GeometryType geoType = (GeometryType) Enum.parseUGCValue(
					GeometryType.class, ugcType);
			// �˴��ò�����switch case,�ʲ��õ���if else
			if (geoType.equals(GeometryType.GEOPOINT)) {
				geo = new GeoPoint(geoHandle);
			} else if (geoType.equals(GeometryType.GEOLINE)) {
				geo = new GeoLine(geoHandle);
			} else if (geoType.equals(GeometryType.GEOREGION)) {
				geo = new GeoRegion(geoHandle);
			} else if (geoType.equals(GeometryType.GEOTEXT)) {
				geo = new GeoText(geoHandle);
			} else if (geoType.equals(GeometryType.GEOPIE)) {
				geo = new GeoPie(geoHandle);
			} else if (geoType.equals(GeometryType.GEOCIRCLE)) {
				geo = new GeoCircle(geoHandle);
			} else if (geoType.equals(GeometryType.GEORECTANGLE)) {
				geo = new GeoRectangle(geoHandle);
			} else if (geoType.equals(GeometryType.GEOELLIPSE)) {
				geo = new GeoEllipse(geoHandle);
			} else if (geoType.equals(GeometryType.GEOELLIPTICARC)) {
				geo = new GeoEllipticArc(geoHandle);
			} else if (geoType.equals(GeometryType.GEOARC)) {
				geo = new GeoArc(geoHandle);
			} else if (geoType.equals(GeometryType.GEOBSPLINE)) {
				geo = new GeoBSpline(geoHandle);
			} else if (geoType.equals(GeometryType.GEOCARDINAL)) {
				geo = new GeoCardinal(geoHandle);
			} else if (geoType.equals(GeometryType.GEOCHORD)) {
				geo = new GeoChord(geoHandle);
			} else if (geoType.equals(GeometryType.GEOCURVE)) {
				geo = new GeoCurve(geoHandle);
			} else if (geoType.equals(GeometryType.GEOLINEM)) {
				geo = new GeoLineM(geoHandle);
			} else if (geoType.equals(GeometryType.GEOPOINT3D)) {
				geo = new GeoPoint3D(geoHandle);
			} else if (geoType.equals(GeometryType.GEOLINE3D)) {
				geo = new GeoLine3D(geoHandle);
			} else if (geoType.equals(GeometryType.GEOREGION3D)) {
				geo = new GeoRegion3D(geoHandle);
			} else if (geoType.equals(GeometryType.GEOPLACEMARK)) {
				geo = new GeoPlacemark(geoHandle);
			} else if (geoType.equals(GeometryType.GEOGRAPHICOBJECT)) {
				geo = new GeoGraphicObject(geoHandle);
			}
		}else {
		}

		if (geo != null) {
			// Ŀǰ���õ��Ķ�����Ҫ�ͷŵ�
			geo.setIsDisposable(true);
		}
		return geo;
	}
	
	/**
	 * ��XML���ؼ��ζ���
	 * 
	 * @param xml
	 *            String
	 * @return boolean
	 */
	public boolean fromXML(String xml) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("fromXML(String xml)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean result = false;
		if (xml != null && xml.trim().length() != 0) {
			result = GeometryNative.jni_FromXML(getHandle(), xml);
		}
		return result;
	}
	/**
	 * ���ؼ��ζ����XML��ʾ
	 * 
	 * @return String
	 */
	public String toXML() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("toXML()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeometryNative.jni_ToXML(getHandle());
	}
	
	protected static final Geometry internalCreateInstance(long geoHandle) {
		return Geometry.createInstance(geoHandle);
	}
	
	public String toJson(){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
		sb.append(" \"id\" : " + this.getID() + ",");
		sb.append(" \"center\" :{ " + "\"x\" :" + this.getInnerPoint().getX() + ", \"y\" : "+ this.getInnerPoint().getY() + "}" + ",");
		GeoStyle style = this.getStyle();
		String strStyle = "null";
		if(style != null){
			strStyle = style.toJson();
		}
		sb.append(" \"style\" :" + strStyle);
		
		sb.append("}");
		return sb.toString();
	}
	
	public boolean fromJson(String json){
		SiJsonObject jsonObj = new SiJsonObject(json);
		
		boolean ret =  fromJson(jsonObj);
		jsonObj.dispose();
		return ret;
	}
	
	public boolean fromJson(SiJsonObject jsonObj){ 
//		if(!jsonObj.getString("style").equals("null")){
//			SiJsonObject style = jsonObj.getJsonObject("style");
//			GeoStyle geoStyle = new GeoStyle();
//			if(geoStyle.fromJson(style.toString())){
//				this.setStyle(geoStyle);
//			}
//			style.dispose();
//		}
		return true;
	}
	
	/**
	 * ��Geometryת����GeoJSON��ʽ���ַ���;����ʵ�����������
	 * @return      ����GeoJSON�ַ���
	 */
	public String toGeoJSON(){
		return null;
	}
	
	/**
	 * ��GeoJSON��ʽ���ַ����л�ȡGeometry;����ʵ�����������
	 * @param geoJSON  GeoJSON�ַ���
	 * @return         �����Ƿ�ת���ɹ�
	 */
	public boolean fromGeoJSON(String geoJSON){
		return false;
	}
	
	/**
	 * ��GeoJSON�ַ����е�coordinates��ֵת���ɵ㴮(Point2Ds)
	 * @param geoJSON    GeoJSON�ַ���
	 * @return           ���ص㴮,Point2Ds
	 */
	protected  ArrayList<Point2Ds> getPointsFromGeoJSON(final String geoJSON) {
		// ��ȡcoordinates��ֵ����ȥ�����пո�
		final String coordinates = geoJSON.substring(geoJSON.indexOf("["), geoJSON.lastIndexOf("]") + 1).replace(" ", "");
		char c = 0;
		ArrayList<Point2Ds> ptsList = new ArrayList<Point2Ds>();
		Point2Ds pts = new Point2Ds();
		Point2D pt = new Point2D(0,0);
		StringBuilder value = new StringBuilder();
		
		String markerStr = null; // "],": ��ʾһ���������"]],": ��ʾһ���߽�����"]]],": ��ʾһ���������
		for (int i = 0, length = coordinates.length(); i < length; i++) {
			c = coordinates.charAt(i);

			switch (c) {
			case ' ':     // �ո�
				break;
			case ',':     // ����
				if (value.length() > 0) {
					pt.setX(Double.parseDouble(value.toString()));
					value.delete(0, value.length());
				}
				markerStr += ",";
				if(checkLineEnd(markerStr)){
					ptsList.add(pts);                        // ���һ���㴮
					pts = new Point2Ds();                    // �½�һ���㴮
				}
				markerStr = "";                              // ÿ���������Ŷ���markerStr��Ϊ�մ�
				break;
			case '[':     // ������[
				break;
			case ']':     // �ҷ�����]
				if (value.length() > 0) {
					pt.setY(Double.parseDouble(value.toString()));
					value.delete(0, value.length());         // ���value
					pts.add(pt);                             // ����ȡ�ĵ����㴮,���ڴ˴��ӵ�
					pt.setX(0);
					pt.setY(0);
				}
				markerStr += "]";
				break;

			default:
				value.append(c);
				break;
			}

		}
		ptsList.add(pts);                                    // ������һ���㴮
		return ptsList;
	}
	
	// ���㴮�������
	private boolean checkLineEnd(String markerStr) {
		if(markerStr != null){
			if(markerStr.equals("]],")){
				return true;
			}else{
				return false;
			}
		}else {
			return false;
		}
	}
}
