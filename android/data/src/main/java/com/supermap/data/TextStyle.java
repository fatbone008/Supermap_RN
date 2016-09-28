package com.supermap.data;


/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:注记风格类型，用于设置GeoText类实例的风格
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
 * @author 孔令亮
 * @version 2.0
 */
public class TextStyle extends InternalHandleDisposable {

	/**
	 * 以默认值的方式初始化一个新的TextStyle对象实例
	 */
	public TextStyle() {
		long handle = TextStyleNative.jni_New();
		this.setHandle(handle, true);
		reset();
	}

	/**
	 * 根据传入的textStyle初始化一个与之相同的TextStyle新实例
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

	// 内部使用到的构造函数
	TextStyle(long handle) {
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
			TextStyleNative.jni_Delete(getHandle());
			this.setHandle(0);
		}
	}

	/**
	 * 返回注记的对齐方式。 当取出的值错误时，抛出EnumConstantNotPresentException异常
	 * 
	 * @return int 返回类型为TextAlignment
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
	 * 设置注记的对齐方式。 当设置的值错误时，抛出EnumConstantNotPresentException异常
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
	 * 返回注记的背景色。
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
	 * 设置注记的背景色。
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
	 * 返回注记是否为粗体字，True 表示为粗体
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
	 * 设置注记是否为粗体字，True 表示为粗体
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
	 * 返回注记的前景色
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
	 * 设置注记的前景色
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
	 * 返回注记是否为固定尺寸.
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
	 * 设置注记是否为固定尺寸.
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
	 * 返回注记字体的高度
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
	 * 设置注记字体的高度
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
	 * 返回注记的宽度
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
	 * 设置注记的宽度
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
	 * 返回注记字体的名称
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
	 * 设置注记字体的名称
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
	 * 返回注记是否采用斜体，True 表示采用斜体。
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
	 * 设置注记是否采用斜体，True 表示采用斜体。
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
	 * 正负度之间，目前只对标签专题图有效，传入的是.1度为单位
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
	 * 正负度之间，目前只对标签专题图有效，传入的是.1度为单位
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
	 * 设置背景透明度
	 * @param value 0~255；0为透明，255为不透明
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
	 * 获取背景半透明度
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
	 * 返回是否以轮廓的方式来显示注记的背景。
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
	 * 克隆该对象
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
	 * 设置是否以轮廓的方式来显示注记的背景。
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
	 * 返回注记旋转的角度。方向为逆时针方向，单位为度。 精确到0.1度
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
	 * 设置注记旋转的角度。方向为逆时针方向，单位为度。
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
	 * 返回注记是否有阴影。True 表示给注记增加阴影。
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
	 * 设置注记是否有阴影。True 表示给注记增加阴影。
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
	 * 设置注记字体是否加删除线，True 表示加删除线。
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
	 * 设置注记字体是否加删除线，True 表示加删除线。
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
	 * 返回注记背景是否透明
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
	 * 设置注记背景是否透明
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
	 * 返回注记字体是否加下划线，True 表示加下划线。
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
	 * 设置注记字体是否加下划线，True 表示加下划线。
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
	 * 返回注记字体的磅数，表示粗体的具体数值
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
	 * 设置注记字体的磅数，表示粗体的具体数值 取值为0～900中的整百数，如100，200等，具体值的定义请参考微软
	 * LogFont中关于LFWeight的说明
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
	//FontScale和OpaqueRate为3D特有
  

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
	 * data包使用
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

	// 主要用于一些底层没有的对象，设置Style后保持对象不变。
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
		// 当直接改变handle时，要将原来的handle对象先释放调，否则会内存泄露。
		// 此情况下，该对象必须本质上是自己在Java层中new出来的。不可是UGC中其他对象的成员。
		if (this.getHandle() != 0) {
			TextStyleNative.jni_Delete(getHandle());
		}
		this.setHandle(handle, false);
	}

	protected static void refreshHandle(TextStyle style, long handle) {
		style.refreshHandle(handle);
	}

	/**
	 * 与changeHandle的区别是不去作Delete操作，只负责改变对应的Handle
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
// * 返回固定注记的大小尺寸
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
// * 设置固定注记的大小尺寸
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
