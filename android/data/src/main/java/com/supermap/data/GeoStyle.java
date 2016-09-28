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
public class GeoStyle extends InternalHandleDisposable {

	/**
	 * 构造函数
	 */
	public GeoStyle() {
		super.setHandle(GeoStyleNative.jni_New(), true);
		reset();
	}

	/**
	 * 构造函数
	 * 
	 * @param style
	 *            GeoStyle 参数为空或已释放都会抛出异常
	 */
	public GeoStyle(GeoStyle style) {
		if (style.getHandle() == 0) {
			String message = InternalResource.loadString("style",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoStyleNative.jni_Clone(style.getHandle());
		super.setHandle(handle, true);
	}

	/**
	 * 内部使用的构造函数
	 * 
	 * @param handle
	 *            long
	 */
	GeoStyle(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		setHandle(handle, false);
	}

	/**
	 * 返回填充背景颜色
	 * 
	 * @return Color
	 */
	public Color getFillBackColor() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Color color = new Color(GeoStyleNative
				.jni_GetFillBackColor(getHandle()));
		return color;
	}

	/**
	 * 设置填充背景颜色
	 * 
	 * @param value
	 *            Color
	 */
	public void setFillBackColor(Color value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyleNative.jni_SetFillBackColor(getHandle(), value.getRGB());
	}

	/**
	 * 返回填充背景是否透明
	 * 
	 * @return boolean
	 */
	public boolean getFillBackOpaque() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetIsBackTransparent(getHandle());
	}

	/**
	 * 设置填充背景是否透明
	 * 
	 * @param value
	 *            boolean
	 */
	public void setFillBackOpaque(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyleNative.jni_SetIsBackTransparent(getHandle(), value);
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
		Color color = new Color(GeoStyleNative
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
		GeoStyleNative.jni_SetFillForeColor(getHandle(), value.getRGB());
	}

	/**
	 * 返回填充的角度，以度为单位
	 * 
	 * @return double
	 */
	public double getFillGradientAngle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return GeoStyleNative.jni_GetFillAngle(getHandle());
	}

	/**
	 * 设置填充的角度，以度为单位
	 * 
	 * @param value
	 *            double
	 */
	public void setFillGradientAngle(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		GeoStyleNative.jni_SetFillAngle(getHandle(), value);
	}

	/**
	 * 返回填充中心点的水平偏移量
	 * 
	 * @return double
	 */
	public double getFillGradientOffsetRatioX() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return GeoStyleNative.jni_GetFillCenterOffsetX(getHandle());
	}

	/**
	 * 设置填充中心点的水平偏移量
	 * 
	 * @param value
	 *            double
	 */
	public void setFillGradientOffsetRatioX(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		GeoStyleNative.jni_SetFillCenterOffsetX(getHandle(), value);
	}

	/**
	 * 返回填充中心点的垂直偏移量
	 * 
	 * @return double
	 */
	public double getFillGradientOffsetRatioY() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetFillCenterOffsetY(getHandle());
	}

	/**
	 * 设置填充中心点的垂直偏移量
	 * 
	 * @param value
	 *            double
	 */
	public void setFillGradientOffsetRatioY(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyleNative.jni_SetFillCenterOffsetY(getHandle(), value);
	}

	/**
	 * 返回渐变填充的类型
	 * 
	 * @return int
	 */
	public FillGradientMode getFillGradientMode() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugType = GeoStyleNative.jni_GetFillGradientType(getHandle());
		return (FillGradientMode) Enum.parseUGCValue(FillGradientMode.class,
				ugType);
	}

	/**
	 * 设置渐变填充的类型
	 * 
	 * @param value
	 *            int
	 */
	public void setFillGradientMode(FillGradientMode fillGradientMode) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (fillGradientMode == null) {
			String message = InternalResource.loadString("fillGradientMode",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		int ugType = fillGradientMode.getUGCValue();
		GeoStyleNative.jni_SetFillGradientType(getHandle(), ugType);
	}

	/**
	 * 返回填充不透明度，为一个0--100的数值。为0表示空填充；为100表示完全不透明
	 * 
	 * @return int
	 */
	public int getFillOpaqueRate() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetFillOpaqueRate(getHandle());
	}

	/**
	 * 设置填充不透明度，为一个0--100的数值。为0表示空填充；为100表示完全不透明
	 * 
	 * @param value
	 *            int
	 */
	public void setFillOpaqueRate(int value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		value = value < 0 ? 0 : value;
		value = value > 100 ? 100 : value;
		GeoStyleNative.jni_SetFillOpaqueRate(getHandle(), value);
	}

	/**
	 * 返回填充模式
	 * 
	 * @return int
	 */
	public int getFillSymbolID() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetFillStyle(getHandle());
	}

	/**
	 * 设置填充模式
	 * 
	 * @param value
	 *            int
	 */
	public void setFillSymbolID(int value) {
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
							InternalResource.GeoStyleTheValueOfSymbolIDShouldNotBeNegative,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyleNative.jni_SetFillStyle(getHandle(), value);
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
		Color color = new Color(GeoStyleNative.jni_GetLineColor(getHandle()));
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
		GeoStyleNative.jni_SetLineColor(getHandle(), value.getRGB());
	}

	/**
	 * 返回边线的线型
	 * 
	 * @return int
	 */
	public int getLineSymbolID() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetLineStyle(getHandle());
	}

	/**
	 * 设置边线的线型
	 * 
	 * @param value
	 *            int
	 */
	public void setLineSymbolID(int value) {
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
							InternalResource.GeoStyleTheValueOfSymbolIDShouldNotBeNegative,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		GeoStyleNative.jni_SetLineStyle(getHandle(), value);
	}

	/**
	 * 返回边线宽度
	 * 
	 * @return double
	 */
	public double getLineWidth() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetLineWidth(getHandle());
	}

	/**
	 * 设置边线宽度
	 * 
	 * @param value
	 *            double
	 */
	public void setLineWidth(double value) {
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
							InternalResource.GeoStyleArgumentOfLineWidthShouldBePositive,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyleNative.jni_SetLineWidth(getHandle(), value);
	}

	/**
	 * 返回符号的旋转角度，旋转的方向为逆时针方向，单位为度
	 * 
	 * @return double
	 */
	public double getMarkerAngle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetMarkerAngle(getHandle());
	}

	/**
	 * 设置符号的旋转角度，旋转的方向为逆时针方向，单位为度
	 * 
	 * @param value
	 *            double
	 */
	public void setMarkerAngle(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyleNative.jni_SetMarkerAngle(getHandle(), value);
	}

	/**
	 * 返回符号的大小，单位为0.1毫米
	 * 
	 * @return double
	 */
	public Size2D getMarkerSize() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] params = new double[2];
		GeoStyleNative.jni_GetMarkerSize(getHandle(), params);
		if (params[0] < 0 || params[1] < 0) {
			params[0] = 0;
			params[1] = 0;
		}
		return new Size2D(params[0], params[1]);
	}

	/**
	 * 设置符号的大小，单位为0.1毫米
	 * 
	 * @param value
	 *            double
	 */
	public void setMarkerSize(Size2D value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value.getWidth() < 0 && value.getHeight() < 0) {
			String message = InternalResource.loadString("value",
					InternalResource.GeoStyleTheValueOfMarkerSizeIsNotValid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyleNative.jni_SetMarkerSize(getHandle(), value.getWidth(), value
				.getHeight());
	}

	/**
	 * 返回点对象的符号风格
	 * 
	 * @return int
	 */
	public int getMarkerSymbolID() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetMarkerStyle(getHandle());
	}

	/**
	 * 设置点对象的符号风格
	 * 
	 * @param value
	 *            int
	 */
	public void setMarkerSymbolID(int value) {
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
							InternalResource.GeoStyleTheValueOfSymbolIDShouldNotBeNegative,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyleNative.jni_SetMarkerStyle(getHandle(), value);
	}

	/**
	 * 克隆对象
	 * 
	 * @return GeoStyle
	 */
	public GeoStyle clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoStyle(this);
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
			GeoStyleNative.jni_Delete(super.getHandle());
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
		buffer.append("{FillBackColor = ");
		buffer.append(this.getFillBackColor().toString());
		buffer.append(",FillForeColor = ");
		buffer.append(this.getFillForeColor().toString());
		buffer.append(",FillGradientAngle = ");
		buffer.append(this.getFillGradientAngle());
		buffer.append(",FillGradientOffsetRatioX = ");
		buffer.append(this.getFillGradientOffsetRatioX());
		buffer.append(",FillGradientOffsetRatioY = ");
		buffer.append(this.getFillGradientOffsetRatioY());
		buffer.append(",FillGradientMode = ");
		buffer.append(this.getFillGradientMode().name());
		buffer.append(",FillOpaqueRate = ");
		buffer.append(this.getFillOpaqueRate());
		buffer.append(",FillSymbolID = ");
		buffer.append(this.getFillSymbolID());
		buffer.append(",LineColor = ");
		buffer.append(this.getLineColor().toString());
		buffer.append(",LineSymbolID = ");
		buffer.append(this.getLineSymbolID());
		buffer.append(",LineWidth = ");
		buffer.append(this.getLineWidth());
		buffer.append(",MarkerAngle = ");
		buffer.append(this.getMarkerAngle());
		buffer.append(",MarkerSize = ");
		buffer.append(this.getMarkerSize());
		buffer.append(",MarkerSymbolID = ");
		buffer.append(this.getMarkerSymbolID());
		buffer.append("}\n");
		return buffer.toString();
	}
	
	public boolean fromXML(String xml){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSymbolFill()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		boolean result = false;		
		if (xml != null && xml.trim().length() != 0) {
			result = GeoStyleNative.jni_FromXML(this.getHandle(), xml);
		}
		
		return result;
	}
	
	public String toXML(){
		
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSymbolFill()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}		
		
		String result = GeoStyleNative.jni_ToXML(this.getHandle());
		
		return result;
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
	protected static GeoStyle createInstance(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new GeoStyle(handle);
	}
	
	protected void clearHandle() {
		setHandle(0);
	}

	protected static void clearHandle(GeoStyle style) {
		style.clearHandle();
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
			GeoStyleNative.jni_Delete(getHandle());
		}
		this.setHandle(handle, false);
	}
	// 主要用于一些底层没有的对象，设置Style后保持对象不变。
	protected static void changeHandle(GeoStyle style, long handle) {
		style.changeHandle(handle);
	}
	protected static void reset(GeoStyle style) {
		style.reset();
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
	
	protected static void refreshHandle(GeoStyle style, long handle) {
		style.refreshHandle(handle);
	}
	
	void reset() {
		if (getHandle() != 0) {
			GeoStyleNative.jni_Reset(getHandle());
		}
	}
	
	public String toJson(){
		return "";
	}
	
	public boolean fromJson(String json){
		return false;
	}
}
