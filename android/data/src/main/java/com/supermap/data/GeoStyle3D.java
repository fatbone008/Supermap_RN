package com.supermap.data;

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
 * @author 李云锦
 * @version 2.0
 */
public class GeoStyle3D extends InternalHandleDisposable {

	/**
	 * 构造函数
	 */
	public GeoStyle3D() {
		super.setHandle(GeoStyle3DNative.jni_New(), true);
		reset();
	}

	/**
	 * 构造函数
	 * 
	 * @param style
	 *            GeoStyle 参数为空或已释放都会抛出异常
	 */
	public GeoStyle3D(GeoStyle3D style) {
		if (style.getHandle() == 0) {
			String message = InternalResource.loadString("style",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoStyle3DNative.jni_Clone(style.getHandle());
		super.setHandle(handle, true);
	}

	/**
	 * 内部使用的构造函数
	 * 
	 * @param handle
	 *            long
	 */
	GeoStyle3D(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		setHandle(handle, false);
	}

	/**
	 * 返回填充颜色
	 * 
	 * @return Color
	 */
	public Color getFillForeColor() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Color color = new Color(GeoStyle3DNative
				.jni_GetFillForeColor(getHandle()));
		return color;
	}

	/**
	 * 设置填充颜色
	 * 
	 * @param value
	 *            Color
	 */
	public void setFillForeColor(Color value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyle3DNative.jni_SetFillForeColor(getHandle(), value.getRGBA());
	}

	/**
	 * 返回边线的颜色
	 * 
	 * @return Color
	 */
	public Color getLineColor() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Color color = new Color(GeoStyle3DNative.jni_GetLineColor(getHandle()));
		return color;
	}

	/**
	 * 设置边线的颜色
	 * 
	 * @param value
	 *            Color
	 */
	public void setLineColor(Color value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyle3DNative.jni_SetLineColor(getHandle(), value.getRGB());
	}

	/**
	 * 返回边线宽度
	 * 
	 * @return double
	 */
	double getLineWidth() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetLineWidth(getHandle());
	}

	/**
	 * 设置边线宽度
	 * 
	 * @param value
	 *            double
	 */
	void setLineWidth(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <= 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.GeoStyle3DArgumentOfLineWidthShouldBePositive,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetLineWidth(getHandle(), value);
	}

	// 三维符号图标的文件路径
	 String getMarkerFile() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getMarkerFile()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return GeoStyle3DNative.jni_GetMarkerIconFile(this.getHandle());
	}

	public void setMarkerFile(String value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setMarkerFile(String value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		GeoStyle3DNative.jni_SetMarkerIconFile(this.getHandle(), value);
	}

	// 三维符号图标的缩放比率
	 double getMarkerScale() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getMarkerIconScale()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetMarkerIconScale(this.getHandle());
	}

	 void setMarkerScale(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setMarkerIconScale(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <= 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.GeoStyle3DArgumentOfMarkerIconScaleShouldBePositive,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetMarkerIconScale(this.getHandle(), value);
	}

	Color getMarkerColor() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getMarkerColor()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// R,G,B,A
		Color color = new Color(GeoStyle3DNative.jni_GetMarkerColor(this
				.getHandle()));
		return color;
	}

	void setMarkerColor(Color value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setMarkerColor(Color value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyle3DNative.jni_SetMarkerColor(this.getHandle(), value.getRGB());
	}

	/**
	 * 返回符号的大小，单位为0.1毫米
	 * 
	 * @return double
	 */
	double getMarkerSize() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetMarkerSize(getHandle());
	}

	/**
	 * 设置符号的大小，单位为0.1毫米
	 * 
	 * @param value
	 *            double
	 */
	void setMarkerSize(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.GeoStyle3DTheValueOfMarkerSizeShouldNotBeNeagtive,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetMarkerSize(getHandle(), value);
	}

	// 底部高程
	public double getBottomAltitude() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getBottomAltitude()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetBottomAltitude(this.getHandle());
	}

	public void setBottomAltitude(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setBottomAltitude(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyle3DNative.jni_SetBottomAltitude(this.getHandle(), value);
	}

	// 拉伸高度
	public double getExtendedHeight() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getExtendedHeight()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetExtendedHeight(this.getHandle());
	}

	public void setExtendedHeight(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setExtendedHeight(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// 这个应该可以为负吧
		GeoStyle3DNative.jni_SetExtendedHeight(this.getHandle(), value);
	}

	// 侧面纹理文件全路径数据
	 String[] getSideTextureFiles() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getSideTextureFiles()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		String ugValue = GeoStyle3DNative.jni_GetSideTextureFiles(this
				.getHandle());
		return Toolkit.splitString(ugValue, ";");
	}

	 void setSideTextureFiles(String[] value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setSideTextureFiles(String[] value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		String ugString = "";
		if (value != null) {
			ugString = Toolkit.joinString(value, ";");
		}
		GeoStyle3DNative.jni_SetSideTextureFiles(this.getHandle(), ugString);
	}

	// 侧面纹理U方向（横向）的重复次数，注意这里的类型是Double，即次数允许是小数
	 double getTilingU() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getTilingU()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetTilingU(this.getHandle());
	}

	 void setTilingU(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTilingU(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.GeoStyle3DTheValueOfTilingUShouldNotBeNeagtive,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetTilingU(this.getHandle(), value);
	}

	// 侧面纹理V方向（纵向）的重复次数，注意这里的类型是Double，即次数允许是小数
	 double getTilingV() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getTilingV()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetTilingV(this.getHandle());
	}

	 void setTilingV(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTilingV(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.GeoStyle3DTheValueOfTilingVShouldNotBeNeagtive,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetTilingV(this.getHandle(), value);
	}

	// 顶面纹理U方向（横向）的重复次数，注意这里的类型是Double，即次数允许是小数
	 double getTopTilingU() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getTopTilingU()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetTopTilingU(this.getHandle());
	}

	 void setTopTilingU(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTopTilingU(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.GeoStyle3DTheValueOfTopTilingUShouldNotBeNeagtive,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetTopTilingU(this.getHandle(), value);
	}

	// 顶面纹理V方向（纵向）的重复次数，注意这里的类型是Double，即次数允许是小数
	 double getTopTilingV() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getTopTilingV()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetTopTilingV(this.getHandle());
	}

	 void setTopTilingV(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTopTilingV(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.GeoStyle3DTheValueOfTopTilingVShouldNotBeNeagtive,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetTopTilingV(this.getHandle(), value);
	}
	
	public AltitudeMode getAltitudeMode() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getAltitudeMode()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugType = GeoStyle3DNative.jni_GetAltitudeMode(this.getHandle());
		return (AltitudeMode) Enum.parseUGCValue(AltitudeMode.class, ugType);
	}

	public void setAltitudeMode(AltitudeMode altitudeMode) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setAltitudeMode(AltitudeMode mode)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugType = altitudeMode.getUGCValue();
		GeoStyle3DNative.jni_SetAltitudeMode(this.getHandle(), ugType);
	}

	/**
	 * 克隆对象
	 * 
	 * @return GeoStyle
	 */
	public GeoStyle3D clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoStyle3D(this);
	}

	/**
	 * 释放对象
	 */
	public void dispose() {
		// 只释放自己定义的Style
		if (!super.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (super.getHandle() != 0) {
			GeoStyle3DNative.jni_Delete(super.getHandle());
			setHandle(0);
		}
	}

	 public String toString() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("toString()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("{FillForeColor = ");
		buffer.append(this.getFillForeColor().toString());
		buffer.append(",LineColor = ");
		buffer.append(this.getLineColor().toString());
		buffer.append(",LineWidth = ");
		buffer.append(this.getLineWidth());
		buffer.append(",MarkerColor = ");
		buffer.append(this.getMarkerColor());
		buffer.append(",MarkerSize = ");
		buffer.append(this.getMarkerSize());
		buffer.append(",BottomAltitude = ");
		buffer.append(this.getBottomAltitude());
		buffer.append(",ExtendedHeight = ");
		buffer.append(this.getExtendedHeight());
		buffer.append(",AltitudeMode = ");
		buffer.append(this.getAltitudeMode());
		buffer.append("}\n");
		return buffer.toString();
	}

	 String toXML() {

		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSymbolFill()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		String result = GeoStyle3DNative.jni_ToXML(this.getHandle());

		return result;
	}

	 boolean fromXML(String xml) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSymbolFill()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		boolean result = false;
		if (xml != null && xml.trim().length() != 0) {
			result = GeoStyle3DNative.jni_FromXML(this.getHandle(), xml);
		}

		return result;
	}

	// 主要用于一些底层没有的对象，设置Style后保持对象不变。
	protected static void changeHandle(GeoStyle3D style, long handle) {
		style.changeHandle(handle);
	}

	protected static void refreshHandle(GeoStyle3D style, long handle) {
		style.refreshHandle(handle);
	}

	/**
	 * data包使用的
	 * 
	 * @param handle
	 *            long
	 * @param key
	 *            String
	 * @return GeoStyle
	 */
	protected static GeoStyle3D createInstance(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new GeoStyle3D(handle);
	}

	protected void clearHandle() {
		setHandle(0);
	}

	protected static void clearHandle(GeoStyle3D style) {
		style.clearHandle();
	}

	protected static void reset(GeoStyle3D style) {
		style.reset();
	}

	void reset() {
		if (getHandle() != 0) {
			GeoStyle3DNative.jni_Reset(getHandle());
		}
	}

	private void changeHandle(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 当直接改变handle时，要将原来的handle对象先释放调，否则会内存泄露。
		// 此情况下，该对象必须本质上是自己在Java层中new出来的。不可是UGC中其他对象的成员。
		if (this.getHandle() != 0) {
			GeoStyle3DNative.jni_Delete(getHandle());
		}
		this.setHandle(handle, false);
	}

	/**
	 * 与changeHandle的区别是不去作delete的操作。
	 * 
	 * @param handle
	 *            long
	 */
	private void refreshHandle(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		this.setHandle(handle, false);
	}

	// /////////////////////////////////////////////////////////////////////

	 boolean isMarkerSizeFixed() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("isMarkerSizeFixed()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetIsMarkerSizeFixed(getHandle());
	}

	 void setMarkerSizeFixed(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setMarkerSizeFixed(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyle3DNative.jni_SetMarkerSizeFixed(getHandle(), value);
	}

	 int getMarkerSymbolID() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getMarkerSymbolID()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetMarkerSymbolID(this.getHandle());
	}

	 void setMarkerSymbolID(int value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setMarkerSymbolID(int value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource.loadString("value",
					InternalResource.MarkerSymbolIDShouldNotBeNeagtive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetMarkerSymbolID(this.getHandle(), value);
	}
	 boolean isMarker3D(){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"isMarker3D()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_IsMarker3D(this.getHandle());
	}
	 void setMarker3D(boolean value){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setMarker3D(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		GeoStyle3DNative.jni_SetMarker3D(this.getHandle(), value);
	}

	 int getLineSymbolID() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getLineSymbolID()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetLineSymbolID(this.getHandle());
	}

	 void setLineSymbolID(int value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setLineSymbolID(int value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource.loadString("value",
					InternalResource.LineSymbolIDShouldNotBeNeagtive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetLineSymbolID(this.getHandle(), value);
	}

	 double getMarker3DRotateX() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getMarker3DRotateX()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetSymbolRotationX(this.getHandle());
	}

	 void setMarker3DRotateX(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setMarker3DRotateX(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource.loadString("value",
					InternalResource.Marker3DRotationXShouldNotBeNeagtive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetSymbolRotationX(this.getHandle(), value);
	}

	 double getMarker3DRotateY() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getMarker3DRotateY()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetSymbolRotationY(this.getHandle());
	}

	 void setMarker3DRotateY(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getMarker3DRotateY(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource.loadString("value",
					InternalResource.Marker3DRotationYShouldNotBeNeagtive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetSymbolRotationY(this.getHandle(), value);
	}

	 double getMarker3DRotateZ() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getMarker3DRotateZ()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetSymbolRotationZ(this.getHandle());
	}

	 void setMarker3DRotateZ(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setMarker3DRotateZ(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource.loadString("value",
					InternalResource.Marker3DRotationZShouldNotBeNeagtive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetSymbolRotationZ(this.getHandle(), value);
	}

	 double getMarker3DScaleX() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getMarker3DScaleX()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetSymbolScaleX(this.getHandle());
	}

	 void setMarker3DScaleX(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setMarker3DScaleX(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource.loadString("value",
					InternalResource.Marker3DScaleXShouldNotBeNeagtive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetSymbolScaleX(this.getHandle(), value);
	}

	 double getMarker3DScaleY() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getMarker3DScaleY()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetSymbolScaleY(this.getHandle());
	}

	 void setMarker3DScaleY(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setMarker3DScaleY(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource.loadString("value",
					InternalResource.Marker3DScaleYShouldNotBeNeagtive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetSymbolScaleY(this.getHandle(), value);
	}

	 double getMarker3DScaleZ() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getMarker3DScaleZ()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetSymbolScaleZ(this.getHandle());
	}

	 void setMarker3DScaleZ(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setMarker3DScaleZ(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource.loadString("value",
					InternalResource.Marker3DScaleZShouldNotBeNeagtive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetSymbolScaleZ(this.getHandle(), value);
	}

	private int getLineSymbolSegmentCount() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getLineSymbolSegmentCount()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetLineSymbolSegmentCount(this.getHandle());
	}

	private void setLineSymbolSegmentCount(int value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setLineSymbolSegmentCount(int value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource.loadString("value",
					InternalResource.LineSymbolSegmentCountShouldNotBeNeagtive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetLineSymbolSegmentCount(this.getHandle(), value);
	}

	 Point2D getMarkerAnchorPoint() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getMarkerAnchorPoint()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] values = new double[2];
		GeoStyle3DNative.jni_GetMarkerAnchorPoint(this.getHandle(), values);
		return new Point2D(values[0], values[1]);
	}

	 void setMarkerAnchorPoint(Point2D value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setMarkerAnchorPoint(Point2D value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyle3DNative.jni_SetMarkerAnchorPoint(this.getHandle(), value
				.getX(), value.getY());
	}

	private int getTopFillSymbolID() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getTopFillSymbolID()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyle3DNative.jni_GetTopFillSymbolID(this.getHandle());
	}

	private void setTopFillSymbolID(int value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTopFillSymbolID(int value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource.loadString("value",
					InternalResource.TopFillSymbolIDShouldNotBeNeagtive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyle3DNative.jni_SetTopFillSymbolID(this.getHandle(), value);
	}

	private int[] getSideFillSymbolIDs() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getSideFillSymbolIDs()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// FacilityAnalyst
		return GeoStyle3DNative.jni_GetSideFillSymbolIDs(this.getHandle());

	}

	private void setSideFillSymbolIDs(int[] value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setSideFillSymbolIDs(int[] value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// DatasetImage
		GeoStyle3DNative.jni_SetSideFillSymbolIDs(this.getHandle(), value);
	}
	
	 int getTubeSides()	{
		
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getTubeSides()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		return GeoStyle3DNative.jni_GetTubeSides(this.getHandle());
	}
	
	 void setTubeSides(int value) {
		
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTubeSides(int value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		if (value < 0) {
			String message = InternalResource.loadString("value",
					InternalResource.TubeSidesCountShouldNotBeNeagtive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		GeoStyle3DNative.jni_SetTubeSides(this.getHandle(), value);
	}
	
	 boolean isFiletEnabled() {
		
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"isFiletEnabled()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		return GeoStyle3DNative.jni_IsFiletEnabled(this.getHandle());
	}
	
	 void setFiletEnabled(boolean value) {
		
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setFiletEnabled(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		GeoStyle3DNative.jni_SetFiletEnabled(this.getHandle(), value);
	}
	
	 boolean isTessellated() {
		
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"isTessellated()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		return GeoStyle3DNative.jni_IsTessellated(this.getHandle());
	}
	
	 void setTessellated(boolean value) {
		
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTessellated(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		GeoStyle3DNative.jni_SetTessellated(this.getHandle(), value);
	}
	 void setFillSymbolID(int value) {
		
	}
	
	 int getFillSymbolID() {
		return 0;
	}
	
	 void setFillBackColor(Color value) {
		
	}
	
	 Color getFillBackColor() {
		Color color = new Color(GeoStyle3DNative
				.jni_GetFillForeColor(getHandle()));
		return color;
	}

}
