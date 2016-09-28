package com.supermap.data;

public class TextPart3D extends InternalHandleDisposable{
	private Point3D m_anchorPoint = null;

	private GeoText3D m_geoText = null; // ���ڼ�¼��TextPart��User���Եõ���TextPart��User�е�����

	/**
	 * ��ʼ������ʵ����RotationΪ0.0��TextΪString.Empty��LocationΪPoint2D.Empty(��X,Y��Ϊ0.0)
	 */
	public TextPart3D() {
		long handle = TextPart3DNative.jni_New();
		this.setHandle(handle, true);
		reset();
	}

	/**
	 * �������캯
	 * 
	 * @param textPart
	 *            TextPart
	 */
	public TextPart3D(TextPart3D part) {
		if (part.getHandle() == 0) {
			String message = InternalResource.loadString("part",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long partHandle = 0;
		if (part.m_geoText != null) {
			int index = part.m_geoText.getTextPart3DsList().indexOf(part);
			// ������Ϊ-1ʱ����˵�������ѱ��Ƴ�
			if (index == -1) {
				String message = InternalResource.loadString(
						"TextPart(TextPart part)",
						InternalResource.GlobalArgumentObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			partHandle = GeoText3DNative.jni_GetSubHandle(part.m_geoText
					.getHandle(), index);
		} else {
			partHandle = part.getHandle();
		}
		long handle = TextPart3DNative.jni_Clone(partHandle);
		this.setHandle(handle, true);
		this.setAnchorPoint(part.getAnchorPoint());
	}

	/**
	 * ����ָ���Ĳ�����ʼ������ʵ��
	 * 
	 * @param text
	 *            String ָ����TextPartʵ�����ı�����
	 * @param anchorPoint
	 *            Point2D ָ����TextPartʵ����ê��
	 */
	public TextPart3D(String text, Point3D anchorPoint) {
		long handle = TextPart3DNative.jni_New();
		this.setHandle(handle, true);
		reset(anchorPoint, text);
	}

	// /**
	// * ����ָ���Ĳ�����ʼ������ʵ��
	// * @param text String ָ������ʵ�����ı�����
	// * @param anchorPoint Point2D ָ������ʵ����ê��
	// * @param rotation Double ָ���ı�����ת�Ƕȣ��Զ�Ϊ��λ����ʱ��Ϊ������
	// */
	// public TextPart3D(String text, Point2D anchorPoint, double rotation) {
	// long handle = TextPart3DNative.jni_New();
	// this.setHandle(handle, true);
	// reset(anchorPoint, rotation, text);
	// }

	/**
	 * ����ָ���Ĳ�����ʼ������ʵ��
	 * 
	 * @param text
	 *            String ָ������ʵ�����ı�����
	 * @param x
	 *            Double ָ������ʵ��ê���X��ֵ
	 * @param y
	 *            Double ָ������ʵ��ê���Y��ֵ
	 * @param rotation
	 *            Double ָ���ı�����ת�Ƕȣ��Զ�Ϊ��λ����ʱ��Ϊ������
	 */
	public TextPart3D(String text, double x, double y, double z) {
		long handle = TextPart3DNative.jni_New();
		this.setHandle(handle, true);
		Point3D anchorPoint = new Point3D(x, y, z);
		reset(anchorPoint, text);
	}

	// �ڲ�ʹ�õ��Ĺ��캯�� ����Ҫ����GeoText3D ����m_textParts�����TextPart
	TextPart3D(GeoText3D geoText, int index) {
		if (geoText.getHandle() == 0) {
			String message = InternalResource.loadString("geoText",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoText3DNative
				.jni_GetSubHandle(geoText.getHandle(), index);
		this.setHandle(handle, false);
		// this.setHandle(handle, false);
		// this.setAnchorPoint(pt);
		this.m_geoText = geoText;
	}

	/**
	 * ���մ˶���
	 */
	public void dispose() {
		// ֻ�ͷ��Լ������TextPart
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			TextPart3DNative.jni_Delete(getHandle());
			this.setHandle(0);
		}
	}

	/**
	 * ���ش�TextPartʵ�����ı����ݡ�
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
		if (this.m_geoText != null) {
			int index = m_geoText.getTextPart3DsList().indexOf(this);
			// ������Ϊ-1ʱ����˵�������ѱ��Ƴ�
			if (index == -1) {
				String message = InternalResource.loadString("getText()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			return GeoText3DNative.jni_GetSubText(m_geoText.getHandle(), index);
		} else {
			return TextPart3DNative.jni_GetText(getHandle());
		}
	}

	/**
	 * ���ô�TextPartʵ�����ı����ݡ�
	 * 
	 * @param text
	 *            String
	 */
	public void setText(String text) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setText(String text)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (text == null) {
			text = "";
		}

		if (this.m_geoText != null) {
			int index = m_geoText.getTextPart3DsList().indexOf(this);
			// ������Ϊ-1ʱ����˵�������ѱ��Ƴ�
			if (index == -1) {
				String message = InternalResource.loadString("setText()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			GeoText3DNative.jni_SetSubText(m_geoText.getHandle(), text, index);
		} else {
			TextPart3DNative.jni_SetText(getHandle(), text);
		}
	}

	/**
	 * ���ش�TextPartʵ����ê��
	 * 
	 * @return Point2D
	 */
	public Point3D getAnchorPoint() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getAnchorPoint()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_geoText != null) {
			int index = m_geoText.getTextPart3DsList().indexOf(this);
			// ������Ϊ-1ʱ����˵�������ѱ��Ƴ�
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}

			double pt[] = new double[3];
			// ��WrapJ�ж�pt��ֵ
			// ע������TextPart��UGC��û�����AnchorPoint�Ľӿڡ����Դ˴�ֱ�Ӳ�������GeoText
			TextPart3DNative.jni_GetSubAnchor(m_geoText.getHandle(), pt, index);
			return new Point3D(pt[0], pt[1] ,pt[2]);
		} else {
			return m_anchorPoint;
		}
	}

	/**
	 * ���ô�TextPartʵ����ê��
	 * 
	 * @param anchorPoint
	 *            Point2D
	 */
	public void setAnchorPoint(Point3D anchorPoint) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setAnchorPoint(Point2D anchorPoint)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_geoText != null) {
			int index = m_geoText.getTextPart3DsList().indexOf(this);
			// ������Ϊ-1ʱ����˵�������ѱ��Ƴ�
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			// ע������TextPart��UGC��û�����AnchorPoint�Ľӿڡ����Դ˴�ֱ�Ӳ�������GeoText
			TextPart3DNative.jni_SetSubAnchor(m_geoText.getHandle(), anchorPoint
					.getX(), anchorPoint.getY(), anchorPoint.getZ() ,index);
		} else {
			this.m_anchorPoint = (Point3D) anchorPoint.clone();
		}
	}

	/**
	 * ���ô�TextPartʵ��ê��ĺ����ꡣ
	 * 
	 * @return double
	 */
	public double getX() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getX()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_geoText != null) {
			int index = m_geoText.getTextPart3DsList().indexOf(this);
			// ������Ϊ-1ʱ����˵�������ѱ��Ƴ�
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			double pt[] = new double[3];
			// ��WrapJ�ж�pt��ֵ
			// ע������TextPart��UGC��û�����AnchorPoint�Ľӿڡ����Դ˴�ֱ�Ӳ�������GeoText
			TextPart3DNative.jni_GetSubAnchor(m_geoText.getHandle(), pt, index);
			return pt[0];
		} else {
			return m_anchorPoint.getX();
		}

	}

	/**
	 * ���ش�TextPartʵ��ê��������ꡣ
	 * 
	 * @return double
	 */
	public double getY() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getY()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_geoText != null) {
			int index = m_geoText.getTextPart3DsList().indexOf(this);
			// ������Ϊ-1ʱ����˵�������ѱ��Ƴ�
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}

			double pt[] = new double[3];
			// ��WrapJ�ж�pt��ֵ
			// ע������TextPart��UGC��û�����AnchorPoint�Ľӿڡ����Դ˴�ֱ�Ӳ�������GeoText3D
			TextPart3DNative.jni_GetSubAnchor(m_geoText.getHandle(), pt, index);
			return pt[1];
		} else {
			return m_anchorPoint.getY();
		}
	}

	/**
	 * ���ô�TextPartʵ��ê��ĺ����ꡣ
	 * 
	 * @param x
	 *            double
	 */
	public void setX(double x) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setX(double x)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_geoText != null) {
			int index = m_geoText.getTextPart3DsList().indexOf(this);
			// ������Ϊ-1ʱ����˵�������ѱ��Ƴ�
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			// �˴��ɸ�Ϊֱ�ӵ���JNI
			// ע������TextPart��UGC��û�����AnchorPoint�Ľӿڡ����Դ˴�ֱ�Ӳ�������GeoText3D
			TextPart3DNative.jni_SetSubAnchor(m_geoText.getHandle(), x, getY(),getZ(),
					index);
		} else {
			m_anchorPoint.setX(x);
		}
	}

	/**
	 * ���ô�TextPartʵ��ê��������ꡣ
	 * 
	 * @param y
	 *            double
	 */
	public void setY(double y) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setY(double y)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_geoText != null) {
			int index = m_geoText.getTextPart3DsList().indexOf(this);
			// ������Ϊ-1ʱ����˵�������ѱ��Ƴ�
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			// �˴��ɸ�Ϊֱ�ӵ���JNI
			// ע������TextPart��UGC��û�����AnchorPoint�Ľӿڡ����Դ˴�ֱ�Ӳ�������GeoText3D
			TextPart3DNative.jni_SetSubAnchor(m_geoText.getHandle(), getX(), y,getZ(),
					index);
		} else {
			m_anchorPoint.setY(y);
		}
	}

	/**
	 * ���ش�TextPartʵ����ת�ĽǶȡ���ת�ķ���Ϊ��ʱ�룬��λΪ�ȡ�
	 * 
	 * @return double
	 */
	public double getZ() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getX()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_geoText != null) {
			int index = m_geoText.getTextPart3DsList().indexOf(this);
			// ������Ϊ-1ʱ����˵�������ѱ��Ƴ�
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			double pt[] = new double[3];
			// ��WrapJ�ж�pt��ֵ
			// ע������TextPart��UGC��û�����AnchorPoint�Ľӿڡ����Դ˴�ֱ�Ӳ�������GeoText
			TextPart3DNative.jni_GetSubAnchor(m_geoText.getHandle(), pt, index);
			return pt[2];
		} else {
			return m_anchorPoint.getZ();
		}
	}

	/**
	 * ���ô�TextPartʵ����ת�ĽǶȡ���ת�ķ���Ϊ��ʱ�룬��λΪ�ȡ�
	 * 
	 * @param rotation
	 *            double
	 */
	public void setZ(double z) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setY(double y)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_geoText != null) {
			int index = m_geoText.getTextPart3DsList().indexOf(this);
			// ������Ϊ-1ʱ����˵�������ѱ��Ƴ�
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			// �˴��ɸ�Ϊֱ�ӵ���JNI
			// ע������TextPart��UGC��û�����AnchorPoint�Ľӿڡ����Դ˴�ֱ�Ӳ�������GeoText3D
			TextPart3DNative.jni_SetSubAnchor(m_geoText.getHandle(), getX(), getY(), z,
					index);
		} else {
			m_anchorPoint.setZ(z);
		}
	}

	/**
	 * ����TextPart��ê�����ָ��������
	 * 
	 * @param dx
	 *            double
	 * @param dy
	 *            double
	 */
	 void offset(double dx, double dy, double dz) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"offset(double dx, double dy)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double x = this.getX() + dx;
		double y = this.getY() + dy;
		double z = this.getZ() + dz;
		if (this.m_geoText != null) {
			int index = m_geoText.getTextPart3DsList().indexOf(this);
			// ������Ϊ-1ʱ����˵�������ѱ��Ƴ�
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			// ע������TextPart��UGC��û�����AnchorPoint�Ľӿڡ����Դ˴�ֱ�Ӳ�������GeoText
			TextPart3DNative.jni_SetSubAnchor(m_geoText.getHandle(), x, y, z, index);
		} else {
			this.m_anchorPoint.setX(x);
			this.m_anchorPoint.setY(y);
			this.m_anchorPoint.setZ(z);
		}
	}

	/**
	 * ��¡�ö���
	 * 
	 * @return TextPart
	 */
	public TextPart3D clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_geoText != null) {
			int index = m_geoText.getTextPart3DsList().indexOf(this);
			// ������Ϊ-1ʱ����˵�������ѱ��Ƴ�
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
		}
		// ����ж��ڿ������캯����
		return new TextPart3D(this);
	}

	void reset() {
		reset(new Point3D(0, 0,0), "");
	}

	//
	void reset(Point3D anchorPoint, String text) {
		// ע�����ڵ�Ϊnew�����Ķ���ʱ�Ż����Reset��ֻ��setText��setRotation�ŵ���Wrapj�������������´���
		this.setAnchorPoint(anchorPoint);		
		this.setText(text);
	}
}
