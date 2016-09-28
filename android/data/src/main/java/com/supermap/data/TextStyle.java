package com.supermap.data;


/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:ע�Ƿ�����ͣ���������GeoText��ʵ���ķ��
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
 * @author ������
 * @version 2.0
 */
public class TextStyle extends InternalHandleDisposable {

	/**
	 * ��Ĭ��ֵ�ķ�ʽ��ʼ��һ���µ�TextStyle����ʵ��
	 */
	public TextStyle() {
		long handle = TextStyleNative.jni_New();
		this.setHandle(handle, true);
		reset();
	}

	/**
	 * ���ݴ����textStyle��ʼ��һ����֮��ͬ��TextStyle��ʵ��
	 * 
	 * @param textStyle
	 *            TextStyle
	 */
	public TextStyle(TextStyle textStyle) {
		if (textStyle.getHandle() == 0) {
			String message = InternalResource.loadString("textStyle",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = TextStyleNative.jni_Clone(textStyle.getHandle());
		this.setHandle(handle, true);
	}

	// �ڲ�ʹ�õ��Ĺ��캯��
	TextStyle(long handle) {
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
			TextStyleNative.jni_Delete(getHandle());
			this.setHandle(0);
		}
	}

	/**
	 * ����ע�ǵĶ��뷽ʽ�� ��ȡ����ֵ����ʱ���׳�EnumConstantNotPresentException�쳣
	 * 
	 * @return int ��������ΪTextAlignment
	 */
	public TextAlignment getAlignment() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getAlignment()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugcType = TextStyleNative.jni_GetTextAlignment(getHandle());
		return (TextAlignment) Enum.parseUGCValue(TextAlignment.class, ugcType);
	}

	/**
	 * ����ע�ǵĶ��뷽ʽ�� �����õ�ֵ����ʱ���׳�EnumConstantNotPresentException�쳣
	 * 
	 * @param value
	 *            int
	 */
	public void setAlignment(TextAlignment textAlignment) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setAlignment(TextAlignment value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (textAlignment == null) {
			String message = InternalResource.loadString("textAlignment",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		int ugcType = textAlignment.getUGCValue();
		TextStyleNative.jni_SetTextAlignment(getHandle(), ugcType);
	}

	/**
	 * ����ע�ǵı���ɫ��
	 * 
	 * @return Color
	 */
	public Color getBackColor() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getBackColor()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int rgb = TextStyleNative.jni_GetBackColor(getHandle());
		return new Color(rgb);
	}

	/**
	 * ����ע�ǵı���ɫ��
	 * 
	 * @param value
	 *            Color
	 */
	public void setBackColor(Color value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setBackColor(Color value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetBackColor(getHandle(), value.getRGB());
	}

	/**
	 * ����ע���Ƿ�Ϊ�����֣�True ��ʾΪ����
	 * 
	 * @return boolean
	 */
	public boolean isBold() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getBold()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetBold(getHandle());
	}

	/**
	 * ����ע���Ƿ�Ϊ�����֣�True ��ʾΪ����
	 * 
	 * @param value
	 *            boolean
	 */
	public void setBold(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setBold(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetBold(getHandle(), value);
	}

	/**
	 * ����ע�ǵ�ǰ��ɫ
	 * 
	 * @return Color
	 */
	public Color getForeColor() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getForeColor()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int rgb = TextStyleNative.jni_GetForeColor(getHandle());
		return new Color(rgb);
	}

	/**
	 * ����ע�ǵ�ǰ��ɫ
	 * 
	 * @param value
	 *            Color
	 */
	public void setForeColor(Color value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setForeColor(Color value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetForeColor(getHandle(), value.getRGB());
	}

	/**
	 * ����ע���Ƿ�Ϊ�̶��ߴ�.
	 * 
	 * @return boolean
	 */
	public boolean isSizeFixed() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getIsSizeFixed()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetIsSizeFixed(getHandle());
	}

	/**
	 * ����ע���Ƿ�Ϊ�̶��ߴ�.
	 * 
	 * @param value
	 *            boolean
	 */
	public void setSizeFixed(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setIsSizeFixed(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetSizeFixed(getHandle(), value);
	}

	/**
	 * ����ע������ĸ߶�
	 * 
	 * @return double
	 */
	public double getFontHeight() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getFontHeight()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetFontHeight(getHandle());
	}

	/**
	 * ����ע������ĸ߶�
	 * 
	 * @param value
	 *            double
	 */
	public void setFontHeight(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setFontHeight(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <= 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.TextStyleTheValueOfFontHeightShouldBePositive,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		TextStyleNative.jni_SetFontHeight(getHandle(), value);
	}

	/**
	 * ����ע�ǵĿ��
	 * 
	 * @return double
	 */
	public double getFontWidth() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getFontWidth()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetFontWidth(getHandle());
	}

	/**
	 * ����ע�ǵĿ��
	 * 
	 * @param value
	 *            double
	 */
	public void setFontWidth(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setFontWidth(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.TextStyleTheValueOfFontWidthShouldBePositive,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		TextStyleNative.jni_SetFontWidth(getHandle(), value);
	}

	/**
	 * ����ע�����������
	 * 
	 * @return String
	 */
	public String getFontName() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getFontName()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetFontName(getHandle());
	}

	/**
	 * ����ע�����������
	 * 
	 * @param value
	 *            String
	 */
	public void setFontName(String value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setFontName(String value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value == null) {
			value = "";
		}
		TextStyleNative.jni_SetFontName(getHandle(), value);
	}

	/**
	 * ����ע���Ƿ����б�壬True ��ʾ����б�塣
	 * 
	 * @return boolean
	 */
	public boolean getItalic() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getItalic()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetItalic(getHandle());
	}

	/**
	 * ����ע���Ƿ����б�壬True ��ʾ����б�塣
	 * 
	 * @param value
	 *            boolean
	 */
	public void setItalic(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setItalic(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetItalic(getHandle(), value);
	}

	/**
	 * ������֮�䣬Ŀǰֻ�Ա�ǩר��ͼ��Ч���������.1��Ϊ��λ
	 * 
	 * @return double
	 */
	public double getItalicAngle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getItalicAngle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetItalicAngle(getHandle());
	}

	/**
	 * ������֮�䣬Ŀǰֻ�Ա�ǩר��ͼ��Ч���������.1��Ϊ��λ
	 * 
	 * @param value
	 *            double
	 */
	public void setItalicAngle(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setItalicAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetItalicAngle(getHandle(), value);
	}
	
	/**
	 * ���ñ���͸����
	 * @param value 0~255��0Ϊ͸����255Ϊ��͸��
	 */
	public void setBackTransparency(int value){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setItalicAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
			
		}
		if(value >=0 && value<=255){
			TextStyleNative.jni_SetBackTransparency(getHandle(), value);
		}
	}
	
	/**
	 * ��ȡ������͸����
	 * @return
	 */
	public int getBackTransparency(){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setItalicAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetBackTransparency(getHandle());
	}

	/**
	 * �����Ƿ��������ķ�ʽ����ʾע�ǵı�����
	 * 
	 * @return boolean
	 */
	public boolean getOutline() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getOutline()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetOutline(getHandle());
	}

	/**
	 * ��¡�ö���
	 * 
	 * @return TextStyle
	 */
	public TextStyle clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new TextStyle(this);
	}
	
	/**
	 * �����Ƿ��������ķ�ʽ����ʾע�ǵı�����
	 * 
	 * @param value
	 *            boolean
	 */
	public void setOutline(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setOutline(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetOutline(getHandle(), value);
	}

	/**
	 * ����ע����ת�ĽǶȡ�����Ϊ��ʱ�뷽�򣬵�λΪ�ȡ� ��ȷ��0.1��
	 * 
	 * @return double
	 */
	public double getRotation() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getRotation()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetRotation(getHandle());
	}

	/**
	 * ����ע����ת�ĽǶȡ�����Ϊ��ʱ�뷽�򣬵�λΪ�ȡ�
	 * 
	 * @param value
	 *            double
	 */
	public void setRotation(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setRotation(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetRotation(getHandle(), value);
	}

	/**
	 * ����ע���Ƿ�����Ӱ��True ��ʾ��ע��������Ӱ��
	 * 
	 * @return boolean
	 */
	public boolean getShadow() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getShadow()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetShadow(getHandle());
	}

	/**
	 * ����ע���Ƿ�����Ӱ��True ��ʾ��ע��������Ӱ��
	 * 
	 * @param value
	 *            boolean
	 */
	public void setShadow(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setShadow(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetShadow(getHandle(), value);
	}

	/**
	 * ����ע�������Ƿ��ɾ���ߣ�True ��ʾ��ɾ���ߡ�
	 * 
	 * @return boolean
	 */
	public boolean getStrikeout() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getStrikeout()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetStrikeout(getHandle());
	}

	/**
	 * ����ע�������Ƿ��ɾ���ߣ�True ��ʾ��ɾ���ߡ�
	 * 
	 * @param value
	 *            boolean
	 */
	public void setStrikeout(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setStrikeout(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetStrikeout(getHandle(), value);
	}

	/**
	 * ����ע�Ǳ����Ƿ�͸��
	 * 
	 * @return boolean
	 */
	public boolean isBackOpaque() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getBackOpaque()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetBackOpaque(getHandle());
	}

	/**
	 * ����ע�Ǳ����Ƿ�͸��
	 * 
	 * @param value
	 *            boolean
	 */
	public void setBackOpaque(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setBackOpaque(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetBackOpaque(getHandle(), value);
	}

	/**
	 * ����ע�������Ƿ���»��ߣ�True ��ʾ���»��ߡ�
	 * 
	 * @return boolean
	 */
	public boolean getUnderline() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getUnderline()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetUnderLine(getHandle());
	}

	/**
	 * ����ע�������Ƿ���»��ߣ�True ��ʾ���»��ߡ�
	 * 
	 * @param value
	 *            boolean
	 */
	public void setUnderline(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setUnderline(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetUnderLine(getHandle(), value);
	}

	/**
	 * ����ע������İ�������ʾ����ľ�����ֵ
	 * 
	 * @return int
	 */
	@Deprecated
	public int getWeight() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getWeight()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetWeight(getHandle());
	}

	/**
	 * ����ע������İ�������ʾ����ľ�����ֵ ȡֵΪ0��900�е�����������100��200�ȣ�����ֵ�Ķ�����ο�΢��
	 * LogFont�й���LFWeight��˵��
	 * 
	 * @param value
	 *            int
	 */
	@Deprecated
	public void setWeight(int value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setWeight(int value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_setWeight(getHandle(), value);
	}
	//FontScale��OpaqueRateΪ3D����
  

	public String toString() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("toString()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("{Alignment = ");
		buffer.append(this.getAlignment().name());
		buffer.append(",BackColor = ");
		buffer.append(this.getBackColor().toString());
		buffer.append(",BackOpaque = ");
		buffer.append(this.isBackOpaque());
		buffer.append(",Bold = ");
		buffer.append(this.isBold());
		// buffer.append(",FixedTextSize = ");
		// buffer.append(this.getFixedTextSize());
		buffer.append(",FontName = ");
		buffer.append(this.getFontName());
		buffer.append(",FontHeight = ");
		buffer.append(this.getFontHeight());
		buffer.append(",FontWidth = ");
		buffer.append(this.getFontWidth());
		buffer.append(",FontColor = ");
		buffer.append(this.getForeColor().toString());
		buffer.append(",IsSizeFixed = ");
		buffer.append(this.isSizeFixed());
		buffer.append(",Italic = ");
		buffer.append(this.getItalic());
		buffer.append(",Outline = ");
		buffer.append(this.getOutline());
		buffer.append(",Rotation = ");
		buffer.append(this.getRotation());
		buffer.append(",Shadow = ");
		buffer.append(this.getShadow());
		buffer.append(",Strikeout = ");
		buffer.append(this.getStrikeout());
		buffer.append(",Underline = ");
		buffer.append(this.getUnderline());
		buffer.append(",Weight = ");
		buffer.append(this.getWeight());
		buffer.append("}\n");
		return buffer.toString();
	}

	/**
	 * data��ʹ��
	 * 
	 * @param getHandle()
	 *            long
	 * @param handleType
	 *            int
	 * @return TextStyle
	 */
	protected static TextStyle createInstance(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new TextStyle(handle);
	}

	protected void clearHandle() {
		setHandle(0);
	}

	// ��Ҫ����һЩ�ײ�û�еĶ�������Style�󱣳ֶ��󲻱䡣
	protected static void changeHandle(TextStyle style, long handle) {
		style.changeHandle(handle);
	}

	protected static void clearHandle(TextStyle style) {
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
			TextStyleNative.jni_Delete(getHandle());
		}
		this.setHandle(handle, false);
	}

	protected static void refreshHandle(TextStyle style, long handle) {
		style.refreshHandle(handle);
	}

	/**
	 * ��changeHandle�������ǲ�ȥ��Delete������ֻ����ı��Ӧ��Handle
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

	void reset() {
		TextStyleNative.jni_Reset(getHandle());
	}
	
}
// /**
// * ���ع̶�ע�ǵĴ�С�ߴ�
// * @return double
// */
// public double getFixedTextSize() {
// if (this.getHandle() == 0) {
// String message = InternalResource.loadString("getFixedTextSize()",
// InternalResource.HandleObjectHasBeenDisposed,
// InternalResource.BundleName);
// throw new IllegalStateException(message);
// }
// return TextStyleNative.jni_GetFixedTextSize(getHandle());
// }
//
// /**
// * ���ù̶�ע�ǵĴ�С�ߴ�
// * @param value double
// */
// public void setFixedTextSize(double value) {
// if (this.getHandle() == 0) {
// String message = InternalResource.loadString(
// "setFixedTextSize(double value)",
// InternalResource.HandleObjectHasBeenDisposed,
// InternalResource.BundleName);
// throw new IllegalStateException(message);
// }
// TextStyleNative.jni_SetFixedTextSize(getHandle(), value);
// }
