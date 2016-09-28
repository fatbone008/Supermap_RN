package com.supermap.data;

import java.util.ArrayList;

public class GeoText3D extends Geometry3D{
	private ArrayList m_TextPart3Ds = null;

	private TextStyle m_textStyle = null;

	/**
	 * ���캯��
	 */
	public GeoText3D() {
		long handle = GeoText3DNative.jni_New();
		this.setHandle(handle, true);
		reset();
		m_TextPart3Ds = new ArrayList();
	}

	/**
	 * ���캯��
	 * 
	 * @param GeoText3D
	 *            GeoText3Do
	 */
	public GeoText3D(GeoText3D geoText3D) {
		if (geoText3D == null) {
			String message = InternalResource.loadString("GeoText3D",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geoText3D.getHandle() == 0) {
			String message = InternalResource.loadString("GeoText3D",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		long handle = GeoText3DNative.jni_Clone(geoText3D.getHandle());
		this.setHandle(handle, true);
		m_TextPart3Ds = new ArrayList();
		int size = geoText3D.getTextPart3DsList().size();
		for (int i = 0; i < size; i++) {
			// ��ArrayList������ȿ���
			// TextPart3D TextPart3D = (TextPart3D) GeoText3D.getTextPart3DsList().get(i);
			TextPart3D newPart = new TextPart3D(this, i);
			m_TextPart3Ds.add(newPart);
		}

	}

	/**
	 * ���캯��
	 * 
	 * @param part
	 *            TextPart3D
	 */
	public GeoText3D(TextPart3D part) {
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
		long handle = GeoText3DNative.jni_New();
		this.setHandle(handle, true);
		reset();
		m_TextPart3Ds = new ArrayList();

		// addPart�ж�part��Handle�����ж�
		addPart(part);
	}

	/**
	 * ���캯��
	 * 
	 * @param part
	 *            TextPart3D
	 * @param textStyle
	 *            TextStyle
	 */
	public GeoText3D(TextPart3D part, TextStyle textStyle) {
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
		long handle = GeoText3DNative.jni_New();
		this.setHandle(handle, true);
		m_TextPart3Ds = new ArrayList();

		addPart(part);
		setTextStyle(textStyle);
	}

	/**
	 * ��������ڲ�ʹ��
	 * 
	 * @param handle
	 *            long
	 */
	GeoText3D(long handle) {
		this.setHandle(handle, false);

		// ����m_TextPart3Ds �����ݡ�
		m_TextPart3Ds = new ArrayList();
		this.refreshTextPart3DsList();
	}

	protected static GeoText3D creatInstance(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new GeoText3D(handle);

	}

	/**
	 * �õ������ַ�
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
		return GeoText3DNative.jni_GetContent(getHandle());
	}

	/**
	 * �����Ӷ�����Ŀ
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
		return GeoText3DNative.jni_GetPartCount(getHandle());
	}

	/**
	 * �����ı���ʽ
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
			// ֧������
			long textStylePtr = GeoText3DNative.jni_GetTextStyle(getHandle());
			if (textStylePtr != 0) {
				this.m_textStyle = TextStyle.createInstance(textStylePtr);
			}
		}
		return this.m_textStyle;
	}

	/**
	 * ���ñ�����ʽ
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
		// ��¡�󷵻ص���typeΪUGTextStyle�Ĵ�TextStyle
		GeoText3DNative.jni_SetTextStyle(getHandle(), newStyle.getHandle());
		// //����m_textStyle
		// this.m_textStyle = TextStyle.createInstance(getHandle());

	}

	/**
	 * ���ض����Ƿ�Ϊ��
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getIsEmpty()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoText3DNative.jni_GetPartCount(getHandle()) == 0;
	}

	/**
	 * ����Ӷ���
	 * 
	 * @param part
	 *            TextPart3D
	 * @return int
	 */
	public int addPart(TextPart3D part) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"addPart(TextPart3D part)",
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
		// @modified by ������ at 2007��8��27�� ����03ʱ32��49��
		// @reason:��UGC��AddSub��ӵĲ����Ƕ��󣬶��Ƕ������ֵ�������ڴ˲��ÿ�¡ part
		int index = GeoText3DNative.jni_AddPart(getHandle(), part.getHandle(),
				part.getX(), part.getY() ,part.getZ());

		// ������GeoText3D����newһ��TextPart3D��ռ��һ�������á���λ�á�
		TextPart3D newPart = new TextPart3D(this, index);

		// ����m_TextPart3Ds
		this.m_TextPart3Ds.add(newPart);
		
		return index;
	}

	/**
	 * �����Ӷ���
	 * 
	 * @param index
	 *            int
	 * @return TextPart3D
	 */
	public TextPart3D getPart(int index) {
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

		TextPart3D part = (TextPart3D) m_TextPart3Ds.get(index);
		return part;
	}

	/**
	 * �����Ӷ���
	 * 
	 * @param index
	 *            int
	 * @param part
	 *            TextPart3D
	 * @return boolean
	 */
	public boolean insertPart(int index, TextPart3D part) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"insertPart(int index, TextPart3D part)",
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

		// ������count��λ�ò���
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
		// @modefied ,����Ҫ��index == getPartCount()����ж�
		// if (index == getPartCount()) {
		// int newIndex = GeoText3DNative.jni_AddPart(getHandle(),
		// part.getHandle(),
		// part.getX(), part.getY());
		// bResult = (newIndex != -1);
		// } else {
		bResult = GeoText3DNative.jni_InsertPart(getHandle(), index, part
				.getHandle(), part.getX(), part.getY(),part.getZ());
		// }
		if (bResult) {
			TextPart3D newPart = new TextPart3D(this, index);

			// ������ɹ��󣬸���m_TextPart3Ds
			m_TextPart3Ds.add(index, newPart);
		}
		
		return bResult;
	}

	public int indexOf(TextPart3D part) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"indexOf(TextPart3D part)",
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
	
		return m_TextPart3Ds.indexOf(part);
	}

	/**
	 * ɾ���Ӷ���
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
		boolean bResult = GeoText3DNative.jni_RemovePart(getHandle(), index);
		if (bResult) {
			// ��ɾ���ɹ��󣬸���m_TextPart3Ds
			m_TextPart3Ds.remove(index);
		}
		return bResult;
	}

	/**
	 * �����Ӷ���
	 * 
	 * @param index
	 *            int
	 * @param part
	 *            TextPart3D
	 * @return boolean
	 */
	public boolean setPart(int index, TextPart3D part) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setPart(int index, TextPart3D part)",
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

		boolean bResult = GeoText3DNative.jni_SetPart(getHandle(), index, part
				.getHandle(), part.getX(), part.getY(), part.getZ());
		if (bResult) {
			TextPart3D newPart = new TextPart3D(this, index);
			// ����m_TextPart3Ds
			m_TextPart3Ds.set(index, newPart);
		}
		return bResult;
	}

	/**
	 * ��¡ע��
	 * 
	 * @return Geometry
	 */
	public GeoText3D clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// the constructor will check the handle
		return new GeoText3D(this);
	}

	public void setEmpty() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setEmpty()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoText3DNative.jni_Clear(getHandle());
		// ����m_TextPart3Ds
		this.m_TextPart3Ds.clear();
	}

	/**
	 * �ͷŶ���
	 */
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoText3DNative.jni_Delete(getHandle());
			this.setHandle(0);

			clearHandle();
		}
	}

	protected void clearHandle() {
		super.clearHandle();
		if (m_TextPart3Ds != null) {
			m_TextPart3Ds.clear();
			m_TextPart3Ds = null;
		}
		if (m_textStyle != null) {
			m_textStyle.clearHandle();
			m_textStyle = null;
		}
	}

	ArrayList getTextPart3DsList() {
		return this.m_TextPart3Ds;
	}

	void reset() {

		// TextStyle��Ĭ��ֵ
		this.getTextStyle().reset();
	}

	// ����m_TextPart3Ds �����ݡ�
	private void refreshTextPart3DsList() {
		m_TextPart3Ds.clear();
		int count = this.getPartCount();
		for (int i = 0; i < count; i++) {
			// double[] params = new double[2];
			// long ptr = GeoText3DNative.jni_GetPart(getHandle(), i, params);
			// Point2D pt = new Point2D(params[0], params[1]);
			// WrapJ�Ѿ���¡
			TextPart3D part = new TextPart3D(this, i);
			m_TextPart3Ds.add(part);
		}
	}

}

// private TextPart3D getPartFromUGC(int index) {
// //the getPartCount will check the handle
// ExceptionManager.checkValidIndex(index, getPartCount(),
// ExceptionManager.
// GlobalIndexOutOfBounds);
// double[] params = new double[2];
// long ptr = GeoText3DNative.jni_GetPart(handle, index, params);
// Point2D pt = new Point2D(params[0], params[1]);
// //WrapJ�Ѿ���¡
// TextPart3D part = new TextPart3D(ptr, pt);
//
// return part;