/**
 * 
 */
package com.supermap.data;

/**
 * @author konglingliang
 * 
 */
public abstract class Geometry3D extends Geometry {

	private GeoStyle3D m_style3D = null;

	private boolean m_setStyle = false;

	/**
	 * ���캯��,�����ܵ���
	 */
	protected Geometry3D() {

	}

	// ����ê�����ά����
	public Point3D getPosition() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getPosition()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] params = new double[3];
		Geometry3DNative.jni_GetPosition(this.getHandle(), params);
		Point3D point = new Point3D(params[0], params[1], params[2]);
		return point;
	}

	// ����ê�����ά����
	public void setPosition(Point3D point) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setPosition(Point3D point)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double x = point.getX();
		double y = point.getY();
		double z = point.getZ();
		Geometry3DNative.jni_SetPosition(this.getHandle(), x, y, z);
	}

	public GeoStyle3D getStyle3D() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getStyle3D()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

//		// �ײ��StyleĬ��Ϊnull������û������������Ժ���ܵõ�����
//		if (!this.m_setStyle) {
//			return null;
//		}
		if (this.m_style3D == null) {
			// modified by liyj: ֧������,ֱ�ӷ��ض���
			long stylePtr = Geometry3DNative.jni_GetStyle(getHandle());
			if (stylePtr != 0) {
				this.m_style3D = GeoStyle3D.createInstance(stylePtr);
			}
		}
		return this.m_style3D;
	}

	/**
	 * ���ü��ζ������ʽ
	 * 
	 * @param value
	 *            GeoStyle
	 */
	public void setStyle3D(GeoStyle3D value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setStyle(GeoStyle value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (value == null) {
			if (m_style3D != null) {
				m_style3D.clearHandle();
				m_style3D = null;
			}
			Geometry3DNative.jni_SetStyle(getHandle(), 0);
		} else {
			if (value.getHandle() == 0) {
				String message = InternalResource.loadString("value",
						InternalResource.GlobalArgumentObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			// ��¡�¶���,����ָ��ΪUGStyle���ͣ��ײ�Ͳ����ж���
			GeoStyle3D style = (GeoStyle3D) value.clone();
			Geometry3DNative.jni_SetStyle(getHandle(), style.getHandle());
		}
		this.m_setStyle = true;
	}

	// ������ά���ζ�����X�᷽�����ת�Ƕ�
	public double getRotationX() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getRotationX()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] rotations = new double[3];
		Geometry3DNative.jni_GetRotation(this.getHandle(), rotations);
		return rotations[0];

	}

	// ����
	public void setRotationX(double x) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setRotationX(double x)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Geometry3DNative.jni_SetRotationX(this.getHandle(), x);
	}

	// ������ά���ζ�����Y�᷽�����ת�Ƕ�
	public double getRotationY() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getRotationY()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] rotations = new double[3];
		Geometry3DNative.jni_GetRotation(this.getHandle(), rotations);
		return rotations[1];
	}

	// ����
	public void setRotationY(double y) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setRotationY(double y)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Geometry3DNative.jni_SetRotationY(this.getHandle(), y);
	}

	// ������ά���ζ�����Z�᷽�����ת�Ƕ�
	public double getRotationZ() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getRotationZ()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] rotations = new double[3];
		Geometry3DNative.jni_GetRotation(this.getHandle(), rotations);
		return rotations[2];
	}

	// ����
	public void setRotationZ(double z) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setRotationZ(double z)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Geometry3DNative.jni_SetRotationZ(this.getHandle(), z);
	}

	// ������ά���ζ�����X�᷽������ű���
	public double getScaleX() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getScaleX()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] scales = new double[3];
		Geometry3DNative.jni_GetScale(this.getHandle(), scales);
		return scales[0];
	}

	// ����
	public void setScaleX(double x) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setScaleX(double x)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// add by xuzw 2008-12-19
		if (x <= 0) {
			String message = InternalResource.loadString("x",
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Geometry3DNative.jni_SetScaleX(this.getHandle(), x);
	}

	// ������ά���ζ�����Y�᷽������ű���
	public double getScaleY() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getScaleY()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] scales = new double[3];
		Geometry3DNative.jni_GetScale(this.getHandle(), scales);
		return scales[1];
	}

	// ����
	public void setScaleY(double y) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setScaleY(double y)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// add by xuzw 2008-12-19
		if (y <= 0) {
			String message = InternalResource.loadString("y",
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Geometry3DNative.jni_SetScaleY(this.getHandle(), y);
	}

	// ������ά���ζ�����Z�᷽������ű���
	public double getScaleZ() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getScaleZ()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] scales = new double[3];
		Geometry3DNative.jni_GetScale(this.getHandle(), scales);
		return scales[2];
	}

	// ����
	public void setScaleZ(double z) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setScaleZ(double z)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// add by xuzw 2008-12-19
		if (z <= 0) {
			String message = InternalResource.loadString("z",
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Geometry3DNative.jni_SetScaleZ(this.getHandle(), z);
	}

	public Point3D getInnerPoint3D() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getInnerPoint3D()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] params = new double[3];
		Geometry3DNative.jni_GetInnerPoint3D(this.getHandle(), params);
		Point3D point = new Point3D(params[0], params[1], params[2]);
		return point;
	}

	 double getVolume() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getVolume()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return Geometry3DNative.jni_GetVolume(this.getHandle());
	}
    //UGC��ʵ��
	public void offset(double dx, double dy, double dz) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"offset(double dx, double dy, double dz)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Geometry3DNative.jni_Offset(this.getHandle(), dx, dy, dz);
	}

	// ��֧������
	public GeoModel getGeoModel(int slices, int stacks) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getGeoModel(int slices, int stacks)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (slices <= 0 || stacks <= 0) {
			String message = InternalResource.loadString(
					"getGeoModel(int slices, int stacks)",
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		long handle = Geometry3DNative.jni_GetGeoModel(this.getHandle(),
				slices, stacks);
		GeoModel geoModel = null;
		if (handle != 0) {
			geoModel = new GeoModel(handle);
		}

		return geoModel;
	}
}
