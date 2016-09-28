package com.supermap.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.supermap.imb.jsonlib.SiJsonArray;
import com.supermap.imb.jsonlib.SiJsonObject;

import android.util.Log;

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
 * @author 李云锦 孔令亮
 * @version 2.0
 */
public class GeoText extends Geometry {
	private ArrayList m_textParts = null;

	private TextStyle m_textStyle = null;

	/**
	 * 构造函数
	 */
	public GeoText() {
		long handle = GeoTextNative.jni_New();
		this.setHandle(handle, true);
		reset();
		m_textParts = new ArrayList();
	}

	/**
	 * 构造函数
	 * 
	 * @param geoText
	 *            GeoTexto
	 */
	public GeoText(GeoText geoText) {
		if (geoText == null) {
			String message = InternalResource.loadString("geoText",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geoText.getHandle() == 0) {
			String message = InternalResource.loadString("geoText",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		long handle = GeoTextNative.jni_Clone(geoText.getHandle());
		this.setHandle(handle, true);
		m_textParts = new ArrayList();
		int size = geoText.getTextPartsList().size();
		for (int i = 0; i < size; i++) {
			// 对ArrayList进行深度拷贝
			// TextPart textPart = (TextPart) geoText.getTextPartsList().get(i);
			TextPart newPart = new TextPart(this, i);
			m_textParts.add(newPart);
		}
		// m_textStyle = (TextStyle) geoText.m_textStyle.clone();
	}

	/**
	 * 构造函数
	 * 
	 * @param part
	 *            TextPart
	 */
	public GeoText(TextPart part) {
		if (part == null) {
			String message = InternalResource.loadString("part",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (part.getHandle() == 0) {
			String message = InternalResource.loadString("part",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoTextNative.jni_New();
		this.setHandle(handle, true);
		reset();
		m_textParts = new ArrayList();

		// addPart中对part的Handle进行判断
		addPart(part);
	}

	/**
	 * 构造函数
	 * 
	 * @param part
	 *            TextPart
	 * @param textStyle
	 *            TextStyle
	 */
	public GeoText(TextPart part, TextStyle textStyle) {
		if (part == null) {
			String message = InternalResource.loadString("part",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (textStyle == null) {
			String message = InternalResource.loadString("textStyle",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (part.getHandle() == 0) {
			String message = InternalResource.loadString("part",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (textStyle.getHandle() == 0) {
			String message = InternalResource.loadString("textStyle",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoTextNative.jni_New();
		this.setHandle(handle, true);
		m_textParts = new ArrayList();

		addPart(part);
		setTextStyle(textStyle);
	}

	public boolean isEmpty() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getIsEmpty()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoTextNative.jni_GetPartCount(getHandle()) == 0;
	}
	
	/**
	 * 构造对象，内部使用
	 * 
	 * @param handle
	 *            long
	 */
	GeoText(long handle) {
		this.setHandle(handle, false);

		// 更新m_textParts 等内容。
		m_textParts = new ArrayList();
		this.refreshTextPartsList();
	}

	protected static GeoText creatInstance(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new GeoText(handle);

	}

	/**
	 * 得到所有字符
	 * 
	 * @return String
	 */
	public String getText() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getText()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoTextNative.jni_GetContent(getHandle());
	}

	/**
	 * 返回子对象数目
	 * 
	 * @return int
	 */
	public int getPartCount() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getPartCount()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoTextNative.jni_GetPartCount(getHandle());
	}

	/**
	 * 返回文本样式
	 * 
	 * @return TextStyle
	 */
	public TextStyle getTextStyle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getTextStyle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_textStyle == null) {
			// 支持连点
			long textStylePtr = GeoTextNative.jni_GetTextStyle(getHandle());
			if (textStylePtr != 0) {
				this.m_textStyle = TextStyle.createInstance(textStylePtr);
			}
		}
		return this.m_textStyle;
	}

	/**
	 * 设置本文样式
	 * 
	 * @param textStyle
	 *            TextStyle
	 */
	public void setTextStyle(TextStyle textStyle) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTextStyle(TextStyle textStyle)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (textStyle.getHandle() == 0) {
			String message = InternalResource.loadString("textStyle",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		TextStyle newStyle = (TextStyle) textStyle.clone();
		// 克隆后返回的是type为UGTextStyle的纯TextStyle
		GeoTextNative.jni_SetTextStyle(getHandle(), newStyle.getHandle());
		// //跟新m_textStyle
		// this.m_textStyle = TextStyle.createInstance(getHandle());
	}

	/**
	 * 添加子对象
	 * 
	 * @param part
	 *            TextPart
	 * @return int
	 */
	public int addPart(TextPart part) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"addPart(TextPart part)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (part == null) {
			String message = InternalResource.loadString("part",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (part.getHandle() == 0) {
			String message = InternalResource.loadString("part",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// @modified by 孔令亮 at 2007年8月27日 下午03时32分49秒
		// @reason:由UGC的AddSub添加的并不是对象，而是对象的数值，所以在此不用克隆 part
		int index = GeoTextNative.jni_AddPart(getHandle(), part.getHandle(),
				part.getX(), part.getY());

		// 仅根据GeoText对象new一个TextPart，占有一个“引用”的位置。
		TextPart newPart = new TextPart(this, index);

		// 更新m_textParts
		this.m_textParts.add(newPart);
		return index;
	}

	/**
	 * 返回子对象
	 * 
	 * @param index
	 *            int
	 * @return TextPart
	 */
	public TextPart getPart(int index) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getPart(int index)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (index < 0 || index >= getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}

		TextPart part = (TextPart) m_textParts.get(index);
		return part;
	}

	/**
	 * 插入子对象
	 * 
	 * @param index
	 *            int
	 * @param part
	 *            TextPart
	 * @return boolean
	 */
	public boolean insertPart(int index, TextPart part) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"insertPart(int index, TextPart part)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (part == null) {
			String message = InternalResource.loadString("part",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 可以在count的位置插入
		if (index < 0 || index > getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}

		if (part.getHandle() == 0) {
			String message = InternalResource.loadString("part",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		boolean bResult = false;
		// @modefied ,不需要有index == getPartCount()这个判断
		// if (index == getPartCount()) {
		// int newIndex = GeoTextNative.jni_AddPart(getHandle(),
		// part.getHandle(),
		// part.getX(), part.getY());
		// bResult = (newIndex != -1);
		// } else {
		bResult = GeoTextNative.jni_InsertPart(getHandle(), index, part
				.getHandle(), part.getX(), part.getY());
		// }
		if (bResult) {
			TextPart newPart = new TextPart(this, index);

			// 当插入成功后，更新m_textParts
			m_textParts.add(index, newPart);
		}
		return bResult;
	}

	/**
	 * 删除子对象
	 * 
	 * @param index
	 *            int
	 * @return boolean
	 */
	public boolean removePart(int index) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"removePart(int index)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (index < 0 || index >= getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		boolean bResult = GeoTextNative.jni_RemovePart(getHandle(), index);
		if (bResult) {
			// 当删除成功后，更新m_textParts
			m_textParts.remove(index);
		}
		return bResult;
	}

	/**
	 * 设置子对象
	 * 
	 * @param index
	 *            int
	 * @param part
	 *            TextPart
	 * @return boolean
	 */
	public boolean setPart(int index, TextPart part) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setPart(int index, TextPart part)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (part == null) {
			String message = InternalResource.loadString("part",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (index < 0 || index >= getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		if (part.getHandle() == 0) {
			String message = InternalResource.loadString("part",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		boolean bResult = GeoTextNative.jni_SetPart(getHandle(), index, part
				.getHandle(), part.getX(), part.getY());
		if (bResult) {
			TextPart newPart = new TextPart(this, index);
			// 更新m_textParts
			m_textParts.set(index, newPart);
		}
		return bResult;
	}


	/**
	 * 克隆注记
	 * 
	 * @return Geometry
	 */
	public GeoText clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// the constructor will check the handle
		return new GeoText(this);
	}

	/**
	 * 释放对象
	 */
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoTextNative.jni_Delete(getHandle());
			this.setHandle(0);

			clearHandle();
		}
	}

	protected void clearHandle() {
		super.clearHandle();
		if (m_textParts != null) {
			m_textParts.clear();
			m_textParts = null;
		}
		if (m_textStyle != null) {
			m_textStyle.clearHandle();
			m_textStyle = null;
		}
	}

	ArrayList getTextPartsList() {
		return this.m_textParts;
	}

	void reset() {

		// TextStyle的默认值
		this.getTextStyle().reset();
	}

	// 更新m_textParts 等内容。
	private void refreshTextPartsList() {
		m_textParts.clear();
		int count = this.getPartCount();
		for (int i = 0; i < count; i++) {
			// double[] params = new double[2];
			// long ptr = GeoTextNative.jni_GetPart(getHandle(), i, params);
			// Point2D pt = new Point2D(params[0], params[1]);
			// WrapJ已经克隆
			TextPart part = new TextPart(this, i);
			m_textParts.add(part);
		}
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
			SiJsonArray points = json.getJsonArray("points");
			SiJsonArray texts = json.getJsonArray("texts");
			
			int count = points.getArraySize();
			for(int i=0;i<count;i++){
				TextPart tp = new TextPart();
				Point2D pt = new Point2D();
				SiJsonObject ptJson = points.getJsonObject(i);
				if(pt.fromJson(ptJson)){
					tp.setAnchorPoint(pt);
					tp.setText(texts.getString(i));
				}
				this.addPart(tp);
				ptJson.dispose();
			}
			points.dispose();
			texts.dispose();
			return true;
		}
		return false;
	}

	@Override
	public String toJson() {
		StringBuilder sb = new StringBuilder(super.toJson());
		sb.deleteCharAt(sb.length()-1);
		sb.append(",");
		
		String parts = "";
		sb.append(" \"parts\": " + "["+ parts+"]" + ",");
		
		sb.append(" \"type\": " + "\"TEXT\"" + ",");
		String points = "";
		int count = this.getPartCount();
		for(int i=0;i<count;i++){
			points += this.getPart(i).getAnchorPoint().toJson();
			if(i != count-1){
				points += ",";
			}
		}
		sb.append(" \"points\" :" + "[" + points + "]" + ",");
		
		String text = "";
		for(int i=0;i<count;i++){
			text += this.getPart(i).getText();
			if(i != count-1){
				points += ",";
			}
		}
		sb.append(" \"texts\" :" + "[" + text + "]");
		
		
		sb.append("}");
		return sb.toString();
	}

}

