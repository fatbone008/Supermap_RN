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
public class GeoStyle3D extends InternalHandleDisposable {

	/**
	 * ���캯��
	 */
	public GeoStyle3D() {
		super.setHandle(GeoStyle3DNative.jni_New(), true);
		reset();
	}

	/**
	 * ���캯��
	 * 
	 * @param style
	 *            GeoStyle ����Ϊ�ջ����ͷŶ����׳��쳣
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
	 * �ڲ�ʹ�õĹ��캯��
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
		Color color = new Color(GeoStyle3DNative
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
		GeoStyle3DNative.jni_SetFillForeColor(getHandle(), value.getRGBA());
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
		Color color = new Color(GeoStyle3DNative.jni_GetLineColor(getHandle()));
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
		GeoStyle3DNative.jni_SetLineColor(getHandle(), value.getRGB());
	}

	/**
	 * ���ر��߿��
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
	 * ���ñ��߿��
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

	// ��ά����ͼ����ļ�·��
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

	// ��ά����ͼ������ű���
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
	 * ���ط��ŵĴ�С����λΪ0.1����
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
	 * ���÷��ŵĴ�С����λΪ0.1����
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

	// �ײ��߳�
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

	// ����߶�
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
		// ���Ӧ�ÿ���Ϊ����
		GeoStyle3DNative.jni_SetExtendedHeight(this.getHandle(), value);
	}

	// ���������ļ�ȫ·������
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

	// ��������U���򣨺��򣩵��ظ�������ע�������������Double��������������С��
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

	// ��������V�������򣩵��ظ�������ע�������������Double��������������С��
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

	// ��������U���򣨺��򣩵��ظ�������ע�������������Double��������������С��
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

	// ��������V�������򣩵��ظ�������ע�������������Double��������������С��
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
	 * ��¡����
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

	// ��Ҫ����һЩ�ײ�û�еĶ�������Style�󱣳ֶ��󲻱䡣
	protected static void changeHandle(GeoStyle3D style, long handle) {
		style.changeHandle(handle);
	}

	protected static void refreshHandle(GeoStyle3D style, long handle) {
		style.refreshHandle(handle);
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
		// ��ֱ�Ӹı�handleʱ��Ҫ��ԭ����handle�������ͷŵ���������ڴ�й¶��
		// ������£��ö�����뱾�������Լ���Java����new�����ġ�������UGC����������ĳ�Ա��
		if (this.getHandle() != 0) {
			GeoStyle3DNative.jni_Delete(getHandle());
		}
		this.setHandle(handle, false);
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
