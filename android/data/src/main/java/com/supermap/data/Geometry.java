package com.supermap.data;

import java.util.ArrayList;
import java.util.Vector;

import com.supermap.imb.jsonlib.SiJsonObject;
import com.supermap.plot.GeoGraphicObject;

/**
 * <p>
 * Title:几何对象
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
 * @author 李云锦
 * @version 2.0
 */
public abstract class Geometry extends InternalHandleDisposable {
	private GeoStyle m_style = null;
	private boolean m_setStyle = false;
	transient static Vector m_customGeometryCreateListeners;

	/**
	 * 构造函数,子类能调用
	 */
	protected Geometry() {
	}

	/**
	 * 获得几何对象的空间范围
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
	 * 获取内点
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
	 * 获取锚点
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
	 * 获取锚点与节点个数
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
	 * 获取ID
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
	 * 子类提供
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		return false;
	}

	/**
	 * 获取几何对象的样式
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
			// modified by liyj: 支持连点,直接返回对象
			long stylePtr = GeometryNative.jni_GetStyle(getHandle());
			if (stylePtr != 0) {
				this.m_style = GeoStyle.createInstance(stylePtr);
			}
		}
		return this.m_style;
	}

	/**
	 * 设置几何对象的样式
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
		// 几何文本不支持该属性
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
			// 克隆下对象,对象指针为UGStyle类型，底层就不用判断了
			GeoStyle style = (GeoStyle) value.clone();
			GeometryNative.jni_SetStyle(getHandle(), style.getHandle());
			style.dispose();
		}
		this.m_setStyle = true;
	}

	/**
	 * 获取几何对象的类型
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
	 * 克隆几何对象
	 * 
	 * @return Geometry
	 */
	public abstract Geometry clone();

	/**
	 * 点击测试
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
	 * 偏移
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
	 * 改变几何对象的大小 当宽度为负数时对象水平翻转，当高度为负数时，对象垂直翻转
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
		// 如果是GeoText类型，则Resize后要处理一下textStyle的width、height。
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
	 * 旋转几何对象
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
	 * 清空几何对象,暂时不开
	 */
	private void setEmpty() {

	}
	
	protected static final Geometry internalCreateInstance2(long geoHandle,
			Workspace workspace) {
		return Geometry.createInstance2(geoHandle, workspace);
	}
	
	// 内部使用，返回Geometry
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
			// 此处用不能用switch case,故采用的是if else
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
			// 目前所用到的都是需要释放的
			geo.setIsDisposable(true);
		}
		return geo;
	}
	
	/**
	 * 所有子类都必须实现dispose方法
	 */
	public abstract void dispose();

	protected void clearHandle() {
		if (m_style != null) {
			m_style.clearHandle();
			m_style = null;
		}
		this.setHandle(0);
	}

	// addby xuzw 2009-02-04 添加clearHandle静态方法，在Feature3D中会用到
	protected static void clearHandle(Geometry geometry) {
		geometry.clearHandle();
	}

	static final Geometry createInstance(GeometryType geoType) {
		Geometry geometry = null;
		// 此处用不能用switch case,故采用的是if else
		if (geoType.equals(GeometryType.GEOPOINT)) {
			geometry = new GeoPoint();
		} else if (geoType.equals(GeometryType.GEOLINE)) {
			geometry = new GeoLine();
		} else if (geoType.equals(GeometryType.GEOREGION)) {
			geometry = new GeoRegion();
		} else if (geoType.equals(GeometryType.GEOTEXT)) {
			geometry = new GeoText();
		} else {
			throw new RuntimeException("$$$相关类型还未实现");
		}
		
		// 用户创建的对象必须是可释放的
		geometry.setIsDisposable(true);
		return geometry;
	}
	
	// 内部使用，返回Geometry
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
			// 此处用不能用switch case,故采用的是if else
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
			// 目前所用到的都是需要释放的
			geo.setIsDisposable(true);
		}
		return geo;
	}
	
	/**
	 * 从XML加载几何对象
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
	 * 返回几何对象的XML表示
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
	 * 将Geometry转换成GeoJSON格式的字符串;具体实现由子类完成
	 * @return      返回GeoJSON字符串
	 */
	public String toGeoJSON(){
		return null;
	}
	
	/**
	 * 从GeoJSON格式的字符串中获取Geometry;具体实现由子类完成
	 * @param geoJSON  GeoJSON字符串
	 * @return         返回是否转换成功
	 */
	public boolean fromGeoJSON(String geoJSON){
		return false;
	}
	
	/**
	 * 将GeoJSON字符串中的coordinates的值转换成点串(Point2Ds)
	 * @param geoJSON    GeoJSON字符串
	 * @return           返回点串,Point2Ds
	 */
	protected  ArrayList<Point2Ds> getPointsFromGeoJSON(final String geoJSON) {
		// 获取coordinates的值，并去除所有空格
		final String coordinates = geoJSON.substring(geoJSON.indexOf("["), geoJSON.lastIndexOf("]") + 1).replace(" ", "");
		char c = 0;
		ArrayList<Point2Ds> ptsList = new ArrayList<Point2Ds>();
		Point2Ds pts = new Point2Ds();
		Point2D pt = new Point2D(0,0);
		StringBuilder value = new StringBuilder();
		
		String markerStr = null; // "],": 表示一个点结束；"]],": 表示一条线结束；"]]],": 表示一个面结束；
		for (int i = 0, length = coordinates.length(); i < length; i++) {
			c = coordinates.charAt(i);

			switch (c) {
			case ' ':     // 空格
				break;
			case ',':     // 逗号
				if (value.length() > 0) {
					pt.setX(Double.parseDouble(value.toString()));
					value.delete(0, value.length());
				}
				markerStr += ",";
				if(checkLineEnd(markerStr)){
					ptsList.add(pts);                        // 添加一个点串
					pts = new Point2Ds();                    // 新建一个点串
				}
				markerStr = "";                              // 每次遇到逗号都将markerStr置为空串
				break;
			case '[':     // 左坊括号[
				break;
			case ']':     // 右方括号]
				if (value.length() > 0) {
					pt.setY(Double.parseDouble(value.toString()));
					value.delete(0, value.length());         // 清空value
					pts.add(pt);                             // 将获取的点加入点串,仅在此处加点
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
		ptsList.add(pts);                                    // 添加最后一个点串
		return ptsList;
	}
	
	// 检查点串结束标记
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
