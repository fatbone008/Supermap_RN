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
 * @author ���ƽ�
 * @version 2.0
 */
public class GeoStyle extends InternalHandleDisposable {

	/**
	 * ���캯��
	 */
	public GeoStyle() {
		super.setHandle(GeoStyleNative.jni_New(), true);
		reset();
	}

	/**
	 * ���캯��
	 * 
	 * @param style
	 *            GeoStyle ����Ϊ�ջ����ͷŶ����׳��쳣
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
	 * �ڲ�ʹ�õĹ��캯��
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
	 * ������䱳����ɫ
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
	 * ������䱳����ɫ
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
	 * ������䱳���Ƿ�͸��
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
	 * ������䱳���Ƿ�͸��
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
	 * ���������ɫ
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
	 * ���������ɫ
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
	 * �������ĽǶȣ��Զ�Ϊ��λ
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
	 * �������ĽǶȣ��Զ�Ϊ��λ
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
	 * ����������ĵ��ˮƽƫ����
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
	 * ����������ĵ��ˮƽƫ����
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
	 * ����������ĵ�Ĵ�ֱƫ����
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
	 * ����������ĵ�Ĵ�ֱƫ����
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
	 * ���ؽ�����������
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
	 * ���ý�����������
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
	 * ������䲻͸���ȣ�Ϊһ��0--100����ֵ��Ϊ0��ʾ����䣻Ϊ100��ʾ��ȫ��͸��
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
	 * ������䲻͸���ȣ�Ϊһ��0--100����ֵ��Ϊ0��ʾ����䣻Ϊ100��ʾ��ȫ��͸��
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
	 * �������ģʽ
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
	 * �������ģʽ
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
	 * ���ر��ߵ���ɫ
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
	 * ���ñ��ߵ���ɫ
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
	 * ���ر��ߵ�����
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
	 * ���ñ��ߵ�����
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
	 * ���ر��߿��
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
	 * ���ñ��߿��
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
	 * ���ط��ŵ���ת�Ƕȣ���ת�ķ���Ϊ��ʱ�뷽�򣬵�λΪ��
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
	 * ���÷��ŵ���ת�Ƕȣ���ת�ķ���Ϊ��ʱ�뷽�򣬵�λΪ��
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
	 * ���ط��ŵĴ�С����λΪ0.1����
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
	 * ���÷��ŵĴ�С����λΪ0.1����
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
	 * ���ص����ķ��ŷ��
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
	 * ���õ����ķ��ŷ��
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
	 * ��¡����
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
	 * �ͷŶ���
	 */
	public void dispose() {
		// ֻ�ͷ��Լ������Style
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
	 * data��ʹ�õ�
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
		// ��ֱ�Ӹı�handleʱ��Ҫ��ԭ����handle�������ͷŵ���������ڴ�й¶��
		// ������£��ö�����뱾�������Լ���Java����new�����ġ�������UGC����������ĳ�Ա��
		if (this.getHandle() != 0) {
			GeoStyleNative.jni_Delete(getHandle());
		}
		this.setHandle(handle, false);
	}
	// ��Ҫ����һЩ�ײ�û�еĶ�������Style�󱣳ֶ��󲻱䡣
	protected static void changeHandle(GeoStyle style, long handle) {
		style.changeHandle(handle);
	}
	protected static void reset(GeoStyle style) {
		style.reset();
	}

	/**
	 * ��changeHandle�������ǲ�ȥ��delete�Ĳ�����
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
