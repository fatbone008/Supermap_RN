package com.supermap.data;

public class TextPart3D extends InternalHandleDisposable{
	private Point3D m_anchorPoint = null;

	private GeoText3D m_geoText = null; // 用于记录此TextPart的User，以得到此TextPart在User中的索引

	/**
	 * 初始化对象实例，Rotation为0.0，Text为String.Empty，Location为Point2D.Empty(即X,Y均为0.0)
	 */
	public TextPart3D() {
		long handle = TextPart3DNative.jni_New();
		this.setHandle(handle, true);
		reset();
	}

	/**
	 * 拷贝构造函
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
			// 当索引为-1时，则说明对象已被移出
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
	 * 根据指定的参数初始化对象实例
	 * 
	 * @param text
	 *            String 指定此TextPart实例的文本内容
	 * @param anchorPoint
	 *            Point2D 指定此TextPart实例的锚点
	 */
	public TextPart3D(String text, Point3D anchorPoint) {
		long handle = TextPart3DNative.jni_New();
		this.setHandle(handle, true);
		reset(anchorPoint, text);
	}

	// /**
	// * 根据指定的参数初始化对象实例
	// * @param text String 指定对象实例的文本内容
	// * @param anchorPoint Point2D 指定对象实例的锚点
	// * @param rotation Double 指定文本的旋转角度，以度为单位，逆时针为正方向
	// */
	// public TextPart3D(String text, Point2D anchorPoint, double rotation) {
	// long handle = TextPart3DNative.jni_New();
	// this.setHandle(handle, true);
	// reset(anchorPoint, rotation, text);
	// }

	/**
	 * 根据指定的参数初始化对象实例
	 * 
	 * @param text
	 *            String 指定对象实例的文本内容
	 * @param x
	 *            Double 指定对象实例锚点的X的值
	 * @param y
	 *            Double 指定对象实例锚点的Y的值
	 * @param rotation
	 *            Double 指定文本的旋转角度，以度为单位，逆时针为正方向
	 */
	public TextPart3D(String text, double x, double y, double z) {
		long handle = TextPart3DNative.jni_New();
		this.setHandle(handle, true);
		Point3D anchorPoint = new Point3D(x, y, z);
		reset(anchorPoint, text);
	}

	// 内部使用到的构造函数 ，主要用于GeoText3D 中向m_textParts中添加TextPart
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
	 * 回收此对象
	 */
	public void dispose() {
		// 只释放自己定义的TextPart
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
	 * 返回此TextPart实例的文本内容。
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
			// 当索引为-1时，则说明对象已被移出
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
	 * 设置此TextPart实例的文本内容。
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
			// 当索引为-1时，则说明对象已被移出
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
	 * 返回此TextPart实例的锚点
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
			// 当索引为-1时，则说明对象已被移出
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}

			double pt[] = new double[3];
			// 在WrapJ中对pt赋值
			// 注：由于TextPart在UGC中没有设计AnchorPoint的接口。所以此处直接操作的是GeoText
			TextPart3DNative.jni_GetSubAnchor(m_geoText.getHandle(), pt, index);
			return new Point3D(pt[0], pt[1] ,pt[2]);
		} else {
			return m_anchorPoint;
		}
	}

	/**
	 * 设置此TextPart实例的锚点
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
			// 当索引为-1时，则说明对象已被移出
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			// 注：由于TextPart在UGC中没有设计AnchorPoint的接口。所以此处直接操作的是GeoText
			TextPart3DNative.jni_SetSubAnchor(m_geoText.getHandle(), anchorPoint
					.getX(), anchorPoint.getY(), anchorPoint.getZ() ,index);
		} else {
			this.m_anchorPoint = (Point3D) anchorPoint.clone();
		}
	}

	/**
	 * 设置此TextPart实例锚点的横坐标。
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
			// 当索引为-1时，则说明对象已被移出
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			double pt[] = new double[3];
			// 在WrapJ中对pt赋值
			// 注：由于TextPart在UGC中没有设计AnchorPoint的接口。所以此处直接操作的是GeoText
			TextPart3DNative.jni_GetSubAnchor(m_geoText.getHandle(), pt, index);
			return pt[0];
		} else {
			return m_anchorPoint.getX();
		}

	}

	/**
	 * 返回此TextPart实例锚点的纵坐标。
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
			// 当索引为-1时，则说明对象已被移出
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}

			double pt[] = new double[3];
			// 在WrapJ中对pt赋值
			// 注：由于TextPart在UGC中没有设计AnchorPoint的接口。所以此处直接操作的是GeoText3D
			TextPart3DNative.jni_GetSubAnchor(m_geoText.getHandle(), pt, index);
			return pt[1];
		} else {
			return m_anchorPoint.getY();
		}
	}

	/**
	 * 设置此TextPart实例锚点的横坐标。
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
			// 当索引为-1时，则说明对象已被移出
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			// 此处可改为直接调用JNI
			// 注：由于TextPart在UGC中没有设计AnchorPoint的接口。所以此处直接操作的是GeoText3D
			TextPart3DNative.jni_SetSubAnchor(m_geoText.getHandle(), x, getY(),getZ(),
					index);
		} else {
			m_anchorPoint.setX(x);
		}
	}

	/**
	 * 设置此TextPart实例锚点的纵坐标。
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
			// 当索引为-1时，则说明对象已被移出
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			// 此处可改为直接调用JNI
			// 注：由于TextPart在UGC中没有设计AnchorPoint的接口。所以此处直接操作的是GeoText3D
			TextPart3DNative.jni_SetSubAnchor(m_geoText.getHandle(), getX(), y,getZ(),
					index);
		} else {
			m_anchorPoint.setY(y);
		}
	}

	/**
	 * 返回此TextPart实例旋转的角度。旋转的方向为逆时针，单位为度。
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
			// 当索引为-1时，则说明对象已被移出
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			double pt[] = new double[3];
			// 在WrapJ中对pt赋值
			// 注：由于TextPart在UGC中没有设计AnchorPoint的接口。所以此处直接操作的是GeoText
			TextPart3DNative.jni_GetSubAnchor(m_geoText.getHandle(), pt, index);
			return pt[2];
		} else {
			return m_anchorPoint.getZ();
		}
	}

	/**
	 * 设置此TextPart实例旋转的角度。旋转的方向为逆时针，单位为度。
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
			// 当索引为-1时，则说明对象已被移出
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			// 此处可改为直接调用JNI
			// 注：由于TextPart在UGC中没有设计AnchorPoint的接口。所以此处直接操作的是GeoText3D
			TextPart3DNative.jni_SetSubAnchor(m_geoText.getHandle(), getX(), getY(), z,
					index);
		} else {
			m_anchorPoint.setZ(z);
		}
	}

	/**
	 * 将此TextPart的锚点调整指定的量。
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
			// 当索引为-1时，则说明对象已被移出
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			// 注：由于TextPart在UGC中没有设计AnchorPoint的接口。所以此处直接操作的是GeoText
			TextPart3DNative.jni_SetSubAnchor(m_geoText.getHandle(), x, y, z, index);
		} else {
			this.m_anchorPoint.setX(x);
			this.m_anchorPoint.setY(y);
			this.m_anchorPoint.setZ(z);
		}
	}

	/**
	 * 克隆该对象
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
			// 当索引为-1时，则说明对象已被移出
			if (index == -1) {
				String message = InternalResource.loadString(
						"getAnchorPoint()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
		}
		// 相关判断在拷贝构造函数中
		return new TextPart3D(this);
	}

	void reset() {
		reset(new Point3D(0, 0,0), "");
	}

	//
	void reset(Point3D anchorPoint, String text) {
		// 注：由于当为new出来的对象时才会调用Reset，只有setText和setRotation才调用Wrapj，所以先作如下处理。
		this.setAnchorPoint(anchorPoint);		
		this.setText(text);
	}
}
