package com.supermap.data;

/**
 * <p>
 * Title:��������������
 * </p>
 * 
 * <p>
 * Description�����������л����ļ������㣬���磺�жϵ��ߡ�����֮��Ĺ�ϵ������һ����֪�ߺ�һ����������ƽ�С���ֱ�ȣ��õ�����Ҫ���߻��ȵȡ�
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * 
 * <p>
 * Company: SuperMap GIS Technologies Inc.
 * </p>
 * 
 * @author jiangwh
 * @version 6.0
 */
public class Geometrist {
	/**
	 * ˽�й��캯�������ܴ���
	 */
	private Geometrist() {

	}

	static {
		Environment.LoadWrapJ();
	}

	/**
	 * �жϲ�ѯ�����Ŀ������Ƿ���ȫ��ȣ����ͱ�����ͬ�����˳��Ҳ����һ��
	 * 
	 * @param geometrySearch
	 *            Geometry ��ѯ����֧�ֵ㡢�ߡ�������
	 * @param geometryTarget
	 *            Geometry Ŀ�����֧�ֵ㡢�ߡ�������
	 * @return boolean ��ѯ�����Ŀ������Ƿ���ȫ���ʱ����true,���򷵻�false
	 */
	public static boolean isIdentical(Geometry geometrySearch,
			Geometry geometryTarget) {
		if (geometrySearch == null || geometrySearch.getHandle() == 0) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometrySearch == null || geometrySearch.getHandle() == 0) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ����쳣��Ŀ�����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometryTarget, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return GeometristNative.jni_IsIdentical(geometrySearch.getHandle(),
				geometryTarget.getHandle());
	}

	/**
	 * �жϲ�ѯ�����Ŀ������Ƿ�����
	 * 
	 * @param geometrySearch
	 *            Geometry ��ѯ����֧�ֵ㡢�ߡ�������
	 * @param geometryTarget
	 *            Geometry Ŀ�����֧�ֵ㡢�ߡ�������
	 * @return boolean ��ѯ�����Ŀ��������뷵��true,�����뷵��false
	 */
	public static boolean isDisjointed(Geometry geometrySearch,
			Geometry geometryTarget) {
		if (geometrySearch == null || geometrySearch.getHandle() == 0) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometryTarget == null || geometryTarget.getHandle() == 0) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣��Ŀ�����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometryTarget, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return GeometristNative.jni_IsDisjointed(geometrySearch.getHandle(),
				geometryTarget.getHandle());

	}

	/**
	 * �жϲ�ѯ�����Ŀ������Ƿ�������ཻ,��ѯ�����Ŀ����������һ��Ϊ�����
	 * 
	 * @param geometrySearch
	 *            Geometry ��ѯ����֧�ֵ㡢�ߡ�������
	 * @param geometryTarget
	 *            Geometry Ŀ�����֧�ֵ㡢�ߡ�������
	 * @return boolean ��ѯ�����Ŀ������Ƿ�������ཻ����ture,��֮����false
	 */
	public static boolean hasIntersection(Geometry geometrySearch,
			Geometry geometryTarget) {
		if (geometrySearch == null || geometrySearch.getHandle() == 0) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometryTarget == null || geometryTarget.getHandle() == 0) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧�ֵ㡢�ߡ�������

		// luhao 2010-8-26 ��֧���ı�����
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION,
				GeometryType.GEOTEXT)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣��Ŀ�����֧�ֵ㡢�ߡ�������
		// luhao 2010-8-26 ��֧���ı�����
		if (!hasAssignedGeometryTypes(geometryTarget, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION,
				GeometryType.GEOTEXT)) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// modify by zhangjn �������������һ�����棬�����׳��쳣
		if (geometrySearch.getType() != GeometryType.GEOREGION
				&& geometryTarget.getType() != GeometryType.GEOREGION) {
			String message = InternalResource.loadString(
					"geometrySearch&geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return GeometristNative.jni_HasIntersection(geometrySearch.getHandle(),
				geometryTarget.getHandle());

	}

	/**
	 * �жϲ�ѯ�����Ŀ������Ƿ�Ӵ�
	 * 
	 * @param geometrySearch
	 *            Geometry ��ѯ����֧�ֵ㡢�ߡ�������
	 * @param geometryTarget
	 *            Geometry Ŀ�����֧�ֵ㡢�ߡ�������
	 * @return boolean �жϲ�ѯ�����Ŀ������Ƿ�Ӵ����Ӵ��򷵻�true,��֮����false
	 */
	public static boolean hasTouch(Geometry geometrySearch,
			Geometry geometryTarget) {
		if (geometrySearch == null || geometrySearch.getHandle() == 0) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometryTarget == null || geometryTarget.getHandle() == 0) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣��Ŀ�����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometryTarget, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return GeometristNative.jni_HasTouch(geometrySearch.getHandle(),
				geometryTarget.getHandle());
	}

	/**
	 * �жϲ�ѯ������Ŀ������Ƿ񽻵�����ѯ������Ŀ������ά��Ҫ����ͬ
	 * 
	 * @param geometrySearch
	 *            Geometry ��ѯ����ֻ֧���ߡ�������
	 * @param geometryTarget
	 *            Geometry Ŀ�����ֻ֧���ߡ�������
	 * @return boolean �жϲ�ѯ������Ŀ������Ƿ񽻵����н�������true����֮����false
	 */
	public static boolean hasOverlap(Geometry geometrySearch,
			Geometry geometryTarget) {
		if (geometrySearch == null || geometrySearch.getHandle() == 0) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometryTarget == null || geometryTarget.getHandle() == 0) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ��ѯ����ֻ֧���ߡ�������
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// Ŀ�����ֻ֧���ߡ�������
		if (!hasAssignedGeometryTypes(geometryTarget, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ��ѯ������Ŀ����������Ҫ����ͬ
		if (geometryTarget.getType() != geometryTarget.getType()) {
			String message = InternalResource.loadString(
					"geometrySearch&geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);

		}
		return GeometristNative.jni_HasOverlap(geometrySearch.getHandle(),
				geometryTarget.getHandle());
	}

	/**
	 * �жϲ�ѯ�����Ƿ�ԽĿ����� Ŀ�����Ϊ��ʱ��Ҫ���ѯ�����ڲ���Ŀ�������ڲ��Ľ�����Ϊ���Ҳ�ѯ������ڲ���Ŀ�������ⲿ�Ľ�����Ϊ�գ�
	 * Ŀ�����Ϊ��ʱ��Ҫ���ѯ�����Ŀ������ڲ���һ����벢��Ŀ������ڲ���ĳ����һ�ഩ����
	 * 
	 * @param geometrySearch
	 *            Geometry ��ѯ����ֻ֧��������
	 * @param geometryTarget
	 *            Geometry Ŀ�����ֻ֧���ߡ�������
	 * @return boolean �жϲ�ѯ�����Ƿ�ԽĿ����󣬴�Խ����true,��֮����false
	 */
	public static boolean hasCross(Geometry geometrySearch,
			Geometry geometryTarget) {

		if (geometrySearch == null || geometrySearch.getHandle() == 0) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometryTarget == null || geometryTarget.getHandle() == 0) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ��ѯ����ֻ֧�������� by zhangjn
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOLINE)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// Ŀ�����ֻ֧���ߡ�������
		if (!hasAssignedGeometryTypes(geometryTarget, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return GeometristNative.jni_HasCross(geometrySearch.getHandle(),
				geometryTarget.getHandle());

	}

	/**
	 * �жϲ�ѯ�����Ƿ���Ŀ������� ��CanContain��������
	 * 
	 * @param geometrySearch
	 *            Geometry ��ѯ����֧�ֵ㡢�ߡ�������
	 * @param geometryTarget
	 *            Geometry Ŀ�����֧�ֵ㡢�ߡ�������
	 * @return boolean �жϲ�ѯ�����Ƿ���Ŀ������ڣ������ڷ���true,��֮����false
	 */
	public static boolean isWithin(Geometry geometrySearch,
			Geometry geometryTarget) {
		if (geometrySearch == null || geometrySearch.getHandle() == 0) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometryTarget == null || geometryTarget.getHandle() == 0) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ����쳣��Ŀ�����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometryTarget, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return GeometristNative.jni_IsWithin(geometrySearch.getHandle(),
				geometryTarget.getHandle());

	}

	/**
	 * �жϲ�ѯ�����Ƿ���ȫ����Ŀ����� ��IsWithin��������
	 * 
	 * @param geometrySearch
	 *            Geometry ��ѯ����֧�ֵ㡢�ߡ�������
	 * @param geometryTarget
	 *            Geometry Ŀ�����֧�ֵ㡢�ߡ�������
	 * @return boolean ��ѯ������ȫ����Ŀ����󷵻�true,��֮����false
	 */
	public static boolean canContain(Geometry geometrySearch,
			Geometry geometryTarget) {
		if (geometrySearch == null || geometrySearch.getHandle() == 0) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometryTarget == null || geometryTarget.getHandle() == 0) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ����쳣��Ŀ�����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometryTarget, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ��ѯ�����ά��>Ŀ��Ե�ά��,�������Ȳ��ж��ˣ��ڵײ������жϣ�by zhangjn
		return GeometristNative.jni_CanContain(geometrySearch.getHandle(),
				geometryTarget.getHandle());

	}

	/**
	 * �жϲ�ѯ�����Ŀ������Ƿ��й�ͬ�ڵ�
	 * 
	 * @param geometrySearch
	 *            Geometry ��ѯ����֧�ֵ㡢�ߡ�������
	 * @param geometryTarget
	 *            Geometry Ŀ�����֧�ֵ㡢�ߡ�������
	 * @return boolean ��ѯ�����Ŀ������й�ͬ�ڵ㷵��ture,��֮����false
	 */
	public static boolean hasCommonPoint(Geometry geometrySearch,
			Geometry geometryTarget) {
		if (geometrySearch == null || geometrySearch.getHandle() == 0) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometryTarget == null || geometryTarget.getHandle() == 0) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ����쳣��Ŀ�����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometryTarget, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return GeometristNative.jni_HasCommonPoint(geometrySearch.getHandle(),
				geometryTarget.getHandle());

	}

	/**
	 * �жϲ�ѯ������Ŀ������Ƿ��й�ͬ�߶�
	 * 
	 * @param geometrySearch
	 *            Geometry ��ѯ����ֻ֧���ߡ�������
	 * @param geometryTarget
	 *            Geometry Ŀ�����ֻ֧���ߡ�������
	 * @return boolean ��ѯ������Ŀ������й����߶η���true,��֮����false
	 */
	public static boolean hasCommonLine(Geometry geometrySearch,
			Geometry geometryTarget) {
		if (geometrySearch == null || geometrySearch.getHandle() == 0) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometryTarget == null || geometryTarget.getHandle() == 0) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ����쳣����ѯ����֧���ߡ�������
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ����쳣��Ŀ�����֧���ߡ�������
		if (!hasAssignedGeometryTypes(geometryTarget, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return GeometristNative.jni_HasCommonLine(geometrySearch.getHandle(),
				geometryTarget.getHandle());

	}

	/**
	 * �����(��ά����)�ü�
	 * 
	 * @param geometry
	 *            Geometry ���ü��Ķ���֧���ߺ�������
	 * @param clipGeometry
	 *            Geometry ���ڲü��Ķ��󣬱����������
	 * @return Geometry ���زü��������
	 */
	public static Geometry clip(Geometry geometry, Geometry clipGeometry) {
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (clipGeometry == null || clipGeometry.getHandle() == 0) {
			String message = InternalResource.loadString("clipGeometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧���ߺ�������
		if (!hasAssignedGeometryTypes(geometry, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣��Ŀ�����ֻ֧��������
		if (!hasAssignedGeometryTypes(clipGeometry, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("clipGeometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		long ptr = GeometristNative.jni_Clip(geometry.getHandle(), clipGeometry
				.getHandle());
		Geometry resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = Geometry.createInstance(ptr);
		}
		
		return resultGeometry;
	}

	/**
	 * ��ά���βü�
	 * 
	 * @param geometry
	 *            Geometry ���ü��Ķ���֧���ߺ�������
	 * @param clipRectangle
	 *            Rectangle2D ���ڲü��Ķ�ά���ζ���
	 * @return Geometry ���زü��������
	 */
	public static Geometry clip(Geometry geometry, Rectangle2D clipRectangle) {
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (clipRectangle == null) {
			String message = InternalResource.loadString("clipRectangle",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (clipRectangle.isEmpty()) {
			String message = InternalResource.loadString("clipRectangle",
					InternalResource.GeometryShouldNotBeEmpty,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (!hasAssignedGeometryTypes(geometry, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		long ptr = GeometristNative.jni_ClipRect(geometry.getHandle(),
				clipRectangle.getLeft(), clipRectangle.getTop(), clipRectangle
						.getRight(), clipRectangle.getBottom());

		Geometry resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = Geometry.createInstance(ptr);
		}
		
		return resultGeometry;
	}

	/**
	 * �ж�������Ƿ��ж�
	 * 
	 * @param geometry
	 *            Geometry �����жϵ��������ʱֻ֧�ֶ�ά�����GeoRegion
	 * @return boolean �Ƿ��ж�
	 */
	public static boolean hasHollow(Geometry geometry) {
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (!hasAssignedGeometryTypes(geometry, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return GeometristNative.jni_HasHollow(geometry.getHandle());
	}
	@Deprecated
	public static boolean HasHollow(Geometry geometry) {
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (!hasAssignedGeometryTypes(geometry, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return GeometristNative.jni_HasHollow(geometry.getHandle());
	}

	/**
	 * ���������㣬�����������ζ���Ľ��� ������������������ζ��������ͬ���͵ģ������ǵ�㡢���߻�������
	 * ĿǰUGC�׶εĿ����Եڶ����ζ���ֻ֧�������ͣ��ڡ����ӷ���ר���ĵ����еڶ����ζ���ҲҪ��Ϊ������
	 * 
	 * @param geometry1
	 *            Geometry ����������ĵ�һ�����ζ���
	 * @param geometry2
	 *            Geometry ����������ĵڶ������ζ���
	 * @return Geometry �����������ζ���Ľ���
	 */
	public static Geometry intersect(Geometry geometry1, Geometry geometry2) {
		if (geometry1 == null || geometry1.getHandle() == 0) {
			String message = InternalResource.loadString("geometry1",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometry2 == null || geometry2.getHandle() == 0) {
			String message = InternalResource.loadString("geometry2",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧���ߺ�������
		if (!hasAssignedGeometryTypes(geometry1, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry1",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣��Ŀ�����ֻ֧��������
		if (!hasAssignedGeometryTypes(geometry2, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry2",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (!geometry1.getType().equals(geometry2.getType())) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long ptr = GeometristNative.jni_Intersect(geometry1.getHandle(),
				geometry2.getHandle());
		Geometry resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = Geometry.createInstance(ptr);
		}
		
		return resultGeometry;
	}

	/**
	 * ����������һ�����ζ��� �������ȫ���������ˣ��򷵻ص�Geometry��IsEmpty����Ϊtrue
	 * 
	 * @param geometry
	 *            Geometry �������ļ��ζ���֧�ֵ㡢�ߡ����������
	 * @param eraseGeometry
	 *            Geometry ���ڲ������漸�ζ��󣬱���Ϊ���������
	 * @return Geometry ���ز�����Ľ�����ζ���
	 */
	public static Geometry erase(Geometry geometry, Geometry eraseGeometry) {
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (eraseGeometry == null || eraseGeometry.getHandle() == 0) {
			String message = InternalResource.loadString("eraseGeometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧�ֵ�,�ߺ�������

		if (!hasAssignedGeometryTypes(geometry, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣��Ŀ�����ֻ֧��������
		if (!hasAssignedGeometryTypes(eraseGeometry, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("eraseGeometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		long ptr = GeometristNative.jni_Erase(geometry.getHandle(),
				eraseGeometry.getHandle());
		Geometry resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = Geometry.createInstance(ptr);
		} 
		
		return resultGeometry;
	}

	/**
	 * ���������㣬�����������ζ���Ĳ��� ������������������ζ��������ͬ���͵ģ������ǵ�㡢���߻�������
	 * ĿǰUGC�׶εĿ���֧����������󲢣��ڡ����ӷ���ר���ĵ����еĺϲ������˵��Ҳд����ֻ֧����������
	 * 
	 * @param geometry1
	 *            Geometry ����������ĵ�һ�����ζ���
	 * @param geometry2
	 *            Geometry ����������ĵ�һ�����ζ���
	 * @return Geometry ����ֵΪ�����󲢼��ζ���Ĳ���
	 */
	public static Geometry union(Geometry geometry1, Geometry geometry2) {
		if (geometry1 == null || geometry1.getHandle() == 0) {
			String message = InternalResource.loadString("geometry1",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometry2 == null || geometry2.getHandle() == 0) {
			String message = InternalResource.loadString("geometry2",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣������������ĵ�һ�����ζ���ֻ֧��������
		if (!hasAssignedGeometryTypes(geometry1, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry1",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣������������ĵ�һ�����ζ���ֻ֧��������
		if (!hasAssignedGeometryTypes(geometry2, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry2",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		long ptr = GeometristNative.jni_Union(geometry1.getHandle(), geometry2
				.getHandle());
		Geometry resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = Geometry.createInstance(ptr);
		} 
		
		return resultGeometry;
	}

	/**
	 * �����������ζ����������� �������������������ζ��������ͬ���͵ģ������ǵ�㡢���߻�������
	 * 
	 * @param geometry1
	 *            Geometry �����������ĵ�һ�����ζ���
	 * @param geometry2
	 *            Geometry �����������ĵڶ������ζ���
	 * @return Geometry ����ֵΪ�����������Ľ�����ζ���
	 */
	public static Geometry xOR(Geometry geometry1, Geometry geometry2) {
		if (geometry1 == null || geometry1.getHandle() == 0) {
			String message = InternalResource.loadString("geometry1",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometry2 == null || geometry2.getHandle() == 0) {
			String message = InternalResource.loadString("geometry2",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometry1, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry1",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣��Ŀ�����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometry2, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry2",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ���ͱ�����ͬ
		if (geometry1.getType() != geometry2.getType()) {
			String message = InternalResource.loadString("geometry1&geometry2",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);

		}
		long ptr = GeometristNative.jni_XOR(geometry1.getHandle(), geometry2
				.getHandle());
		Geometry resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = Geometry.createInstance(ptr);
		}
		
		return resultGeometry;
	}

	/**
	 * �Ա����µļ��ζ���������һ�����ζ�����и��¼��㣬�����¼���Ľ������
	 * �����������ø��¼����滻�뱻���¼��ζ�����غϲ��֣���һ���Ȳ�����ճ���Ĺ���
	 * 
	 * @param geometry
	 *            Geometry �����µļ��ζ��󣬱���Ϊ�����
	 * @param UpdateGeometry
	 *            Geometry ���ڽ��и�������ļ��ζ��󣬱���Ϊ�����
	 * @return Geometry ���ظ�������Ľ�����ζ���
	 */
	public static Geometry update(Geometry geometry, Geometry updateGeometry) {
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (updateGeometry == null || updateGeometry.getHandle() == 0) {
			String message = InternalResource.loadString("updateGeometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����ֻ֧��������
		if (!hasAssignedGeometryTypes(geometry, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����ֻ֧��������
		if (!hasAssignedGeometryTypes(updateGeometry, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("updateGeometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long ptr = GeometristNative.jni_Update(geometry.getHandle(),
				updateGeometry.getHandle());
		Geometry resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = Geometry.createInstance(ptr);
		}
		
		return resultGeometry;
	}

	/**
	 * ���������ζ������ͬһ���� ͬһ������ǵ�һ���ζ�����ڶ����ζ������󽻣�Ȼ���󽻽�������һ���ζ����󲢵�����
	 * 
	 * @param geometry
	 *            Geometry ��ͬһ����ļ��ζ��󣬼���һ���ζ���֧�ֵ㡢�ߡ������
	 * @param IdentityGeometry
	 *            Geometry ����ͬһ����ļ��ζ��󣬼��ڶ����ζ��󣬱���Ϊ�����
	 * @return Geometry ����ͬһ����Ľ�����ζ���
	 */
	public static Geometry identity(Geometry geometry, Geometry identityGeometry) {
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (identityGeometry == null || identityGeometry.getHandle() == 0) {
			String message = InternalResource.loadString("identityGeometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometry, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����ֻ֧��������
		if (!hasAssignedGeometryTypes(identityGeometry, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("identityGeometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		long ptr = GeometristNative.jni_Identity(geometry.getHandle(),
				identityGeometry.getHandle());
		Geometry resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = Geometry.createInstance(ptr);
		} 

		return resultGeometry;
	}

	/**
	 * ����һ���ľ������޶Լ��ζ�������ز���
	 * 
	 * @param geometry
	 *            Geometry ���ز����ļ��ζ���
	 * @param tolerance
	 *            double �����ز����ľ�������
	 * @return Geometry �����ز����Ľ�����ζ���
	 */
	@Deprecated
	public static Geometry resample(Geometry geometry, double tolerance) {
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ����쳣����ѯ����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometry, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (tolerance <= 0) {
			String message = InternalResource.loadString("tolerance",
					InternalResource.GlobalToleranceShouldGreaterThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		long ptr = GeometristNative.jni_Resample(geometry.getHandle(),
				tolerance);
		Geometry resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = Geometry.createInstance(ptr);
		} 
		
		return resultGeometry;
	}

	/**
	 * ����һ���ľ������޶Լ��ζ�������ز���
	 * 
	 * @param geometry
	 *            Geometry ���ز����ļ��ζ���
	 * @param tolerance
	 *            ����ResampeType.RTBEND �� ResampeType.RTGENERAL��toleranceΪ��������
	 * @return Geometry �����ز����Ľ�����ζ���
	 */
	public static Geometry resample(Geometry geometry,
			ResampleType resampleType, double tolerance) {
		
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ����쳣����ѯ����֧�ֵ㡢�ߡ�������
		if (!hasAssignedGeometryTypes(geometry, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (tolerance <= 0) {
			String message = InternalResource.loadString("tolerance",
					InternalResource.GlobalToleranceShouldGreaterThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		int nResampleType = resampleType.getUGCValue();
		
		long ptr = GeometristNative.jni_Resample1(geometry.getHandle(),nResampleType,
				tolerance);
		
		Geometry resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = Geometry.createInstance(ptr);
		} 
		
		return resultGeometry;
		
	}
	/**
	 * ���������ζ���֮��ľ���
	 * 
	 * @param geometry1
	 *            Geometry
	 * @param geometry2
	 *            Geometry
	 * @return distance double
	 */
	public static double distance(Geometry geometry1, Geometry geometry2) {
		// @added by �ż��� at 2007��10��30�� ����11ʱ44��29��
		// @reason:Ϊ����iServer��������
		if (geometry1 == null || geometry1.getHandle() == 0) {
			String message = InternalResource.loadString("geometry1",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometry2 == null || geometry2.getHandle() == 0) {
			String message = InternalResource.loadString("geometry2",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		double distance = GeometristNative.jni_Distance(geometry1.getHandle(),
				geometry2.getHandle());
		return distance;
	}

	// ////////////////////////////////////////////////////////////////
	// /////////////////����Ϊ6.0�����ӿ�///////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * ����㼯��͹�հ�������С��Ӷ���Ρ�����һ����͹�����
	 * 
	 * @param points
	 *            Point2Ds �㼯
	 * @return Geometry ������С��Ӷ����
	 */
	public static GeoRegion computeConvexHull(Point2Ds points) {

		int length = points.getCount();
		// ����ε���Ӧ����3������
		if (length < 3) {
			String message = InternalResource.loadString("points",
					InternalResource.GeoRegionInvalidPointsLength,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// {{ luhao 2010-8-18 �����ô����ֱ�ӹ����أ�
		// return new GeoRegion(points);

		double[] xs = new double[length];
		double[] ys = new double[length];
		for (int i = 0; i < length; i++) {
			xs[i] = points.getItem(i).getX();
			ys[i] = points.getItem(i).getY();
		}

		long ptr = GeometristNative.jni_ComputeConvexHullPoints(xs, ys, length);
		Geometry resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = Geometry.createInstance(ptr);
		}

		return (GeoRegion) resultGeometry;
		// }} luhao 2010-8-18
	}

	/**
	 * ���㼸�ζ����͹�հ�������С���͹����Ρ�����һ����͹�����
	 * 
	 * @param geometry
	 *            Geometry ���ζ���
	 * @return Geometry ������С��Ӷ����
	 */
	public static GeoRegion computeConvexHull(Geometry geometry) {
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		long ptr = GeometristNative.jni_ComputeConvexHull(geometry.getHandle());
		Geometry resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = Geometry.createInstance(ptr);
		} else {
			resultGeometry = Geometry.createInstance(geometry.getType());
		}
		return (GeoRegion) resultGeometry;
	}

	/**
	 * ���߻��漸�ζ���ָ��漸�ζ��󣬼���splitGeometryȥ�ָ�sourceGeoRegion����
	 * 
	 * @param soureRegion
	 *            GeoRegion ���ָ�������
	 * @param splitGeometry
	 *            Geometry ���ڷָ�ļ��ζ��󣬿������߻��漸�ζ���
	 * @param targetGeoRegion1
	 *            GeoRegion ���طָ��ĵ�һ�������
	 * @param targetGeoRegion2
	 *            GeoRegion ���طָ��ĵ�һ�������
	 * @return boolean
	 */
	public static boolean splitRegion(GeoRegion soureRegion,
			Geometry splitGeometry, GeoRegion targetGeoRegion1,
			GeoRegion targetGeoRegion2) {
		if (soureRegion == null || soureRegion.getHandle() == 0) {
			String message = InternalResource.loadString("soureRegion",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (splitGeometry == null || splitGeometry.getHandle() == 0) {
			String message = InternalResource.loadString("splitGeometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (targetGeoRegion1 == null || targetGeoRegion1.getHandle() == 0) {
			String message = InternalResource.loadString("targetGeoRegion1",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (targetGeoRegion2 == null || targetGeoRegion2.getHandle() == 0) {
			String message = InternalResource.loadString("targetGeoRegion2",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧��������
		if (!hasAssignedGeometryTypes(soureRegion, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("soureRegion",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ����쳣����ѯ����֧���ߺ�������
		if (!hasAssignedGeometryTypes(splitGeometry, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("splitGeometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ����쳣����ѯ����֧��������
		if (!hasAssignedGeometryTypes(targetGeoRegion1, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("targetGeoRegion1",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧��������
		if (!hasAssignedGeometryTypes(targetGeoRegion2, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("targetGeoRegion2",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (splitGeometry.getType().equals(GeometryType.GEOPOINT)) {
			String message = InternalResource.loadString("splitGeometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		boolean success = GeometristNative.jni_SplitRegion(soureRegion
				.getHandle(), splitGeometry.getHandle(), targetGeoRegion1
				.getHandle(), targetGeoRegion2.getHandle());
		if (success) {
			targetGeoRegion1.refrashPartsList();
			targetGeoRegion2.refrashPartsList();
		}
		return success;
	}

	/**
	 * ���������ߵĵ�Բ�ǣ����ص�Բ������Ӧ�Ļ��Ρ� ���в��� startPoint1�� endPoint1���ɵ�һ���߶Σ�
	 * startPoint2��endPoint2���ɵڶ����߶Σ� �������߶ε�����Բ��������Բ�İ뾶����������� radius������ֵ��
	 * ��ʱ���ڿ��������߽��������������꽫�����仯�� ��Ϊ����Բ���������е�����ꡣ�õ��Ļ��ν����ĸ���Ϳ��������Բ������ϡ�
	 * 
	 * @param startPoint1
	 *            Point2D ��һ���ߵ����
	 * @param endPoint1
	 *            Point2D ��һ���ߵ��յ�
	 * @param startPoint2
	 *            Point2D �ڶ����ߵ����
	 * @param endPoint2
	 *            Point2D �ڶ����ߵ��յ�
	 * @param radius
	 *            double Բ���İ뾶
	 * @return GeoArc ���ص�Բ������Ӧ�Ļ���
	 */
	public static GeoArc computeFillet(Point2D startPoint1, Point2D endPoint1,
			Point2D startPoint2, Point2D endPoint2, double radius) {

		if (radius <= 0) {
			String message = InternalResource.loadString("radius",
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// �ж������Ƿ��ظ�
		if (startPoint1.equals(endPoint1)) {
			String message = InternalResource.loadString(
					"startPoint1,endPoint1",
					InternalResource.TwoPointsShouldNotBeEqual,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);

		}
		if (startPoint2.equals(endPoint2)) {

			String message = InternalResource.loadString(
					"startPoint2,endPoint2",
					InternalResource.TwoPointsShouldNotBeEqual,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long ptr = GeometristNative.jni_ComputeFillet(startPoint1.getX(),
				startPoint1.getY(), endPoint1.getX(), endPoint1.getY(),
				startPoint2.getX(), startPoint2.getY(), endPoint2.getX(),
				endPoint2.getY(), radius);

		Geometry resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = Geometry.createInstance(ptr);
		} 
		
		return (GeoArc) resultGeometry;
	}

	/**
	 * �ж�����ֱ���Ƿ�ƽ��
	 * 
	 * @param startPoint1
	 *            Point2D ��һ���ߵ����
	 * @param endPoint1
	 *            Point2D ��һ���ߵ��յ�
	 * @param startPoint2
	 *            Point2D �ڶ����ߵ����
	 * @param endPoint2
	 *            Point2D �ڶ����ߵ��յ�
	 * @return GeoArc �ж�����ֱ���Ƿ�ƽ�У�ƽ�з���True�����򷵻�False
	 */
	public static boolean isParallel(Point2D startPoint1, Point2D endPoint1,
			Point2D startPoint2, Point2D endPoint2) {

		// �ж������Ƿ��ظ�
		if (startPoint1.equals(endPoint1)) {
			String message = InternalResource.loadString(
					"startPoint1,endPoint1",
					InternalResource.TwoPointsShouldNotBeEqual,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);

		}
		if (startPoint2.equals(endPoint2)) {

			String message = InternalResource.loadString(
					"startPoint2,endPoint2",
					InternalResource.TwoPointsShouldNotBeEqual,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return GeometristNative.jni_IsParallel(startPoint1.getX(), startPoint1
				.getY(), endPoint1.getX(), endPoint1.getY(),
				startPoint2.getX(), startPoint2.getY(), endPoint2.getX(),
				endPoint2.getY());

	}

	/**
	 * �󾭹�ָ��������ֱ֪��ƽ�е�ֱ��
	 * 
	 * @param point
	 *            Point2D ֱ���������һ��
	 * @param startPoint
	 *            Point2D ֱ�ߵ�һ��
	 * @param endPoint
	 *            Point2D ֱ�ߵ���һ��
	 * @return GeoLine ����ƽ����
	 */
	public static GeoLine computeParallel(Point2D point, Point2D startPoint,
			Point2D endPoint) {

		// �ж������Ƿ��ظ�
		if (startPoint.equals(endPoint)) {
			String message = InternalResource.loadString("startPoint,endPoint",
					InternalResource.TwoPointsShouldNotBeEqual,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);

		}
		Point2Ds point2Ds = new Point2Ds();
		point2Ds.add(startPoint);
		point2Ds.add(endPoint);
		GeoLine line = new GeoLine(point2Ds);
		if (!isPointOnLine(point, startPoint, endPoint, true)) {
			line = computePerpendicular(point, startPoint, endPoint);
			line.rotate(point, -90);
		}
		return line;

	}

	/**
	 * ���ݾ�������֪���ߵ�ƽ���ߣ�����ƽ����
	 * 
	 * @param geoLine
	 *            GeoLine ��֪���߶���
	 * @param distance
	 *            double ����ƽ���߼�ľ���
	 * 
	 * @return GeoLine ����ƽ����
	 */
	public static GeoLine computeParallel(GeoLine geoLine, double distance) {
		if (geoLine == null || geoLine.getHandle() == 0) {
			String message = InternalResource.loadString("geoLine",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long ptr = GeometristNative.jni_ComputeParallel(geoLine.getHandle(),
				distance);
		Geometry resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = Geometry.createInstance(ptr);
		} 
		
		return (GeoLine) resultGeometry;
	}

	/**
	 * �ж�����ֱ���Ƿ�ֱ
	 * 
	 * @param startPoint1
	 *            Point2D ��һ���ߵ����
	 * @param endPoint1
	 *            Point2D ��һ���ߵ��յ�
	 * @param startPoint2
	 *            Point2D �ڶ����ߵ����
	 * @param endPoint2
	 *            Point2D �ڶ����ߵ��յ�
	 * @return boolean �ж�����ֱ���Ƿ�ֱ����ֱ����True�����򷵻�False
	 */
	public static boolean isPerpendicular(Point2D startPoint1,
			Point2D endPoint1, Point2D startPoint2, Point2D endPoint2) {

		// �ж������Ƿ��ظ�
		if (startPoint1.equals(endPoint1)) {
			String message = InternalResource.loadString(
					"startPoint1,endPoint1",
					InternalResource.TwoPointsShouldNotBeEqual,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);

		}
		if (startPoint2.equals(endPoint2)) {

			String message = InternalResource.loadString(
					"startPoint2,endPoint2",
					InternalResource.TwoPointsShouldNotBeEqual,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return GeometristNative.jni_IsPerpendicular(startPoint1.getX(),
				startPoint1.getY(), endPoint1.getX(), endPoint1.getY(),
				startPoint2.getX(), startPoint2.getY(), endPoint2.getX(),
				endPoint2.getY());

	}

	/**
	 * ������֪�㵽��֪�ߵĴ���
	 * 
	 * @param point
	 *            Point2D ��֪һ��
	 * @param startPoint
	 *            Point2D ֱ���ϵ�һ��
	 * @param endPoint
	 *            Point2D ֱ���ϵ���һ��
	 * @return GeoLine ���ش���
	 */
	public static GeoLine computePerpendicular(Point2D point,
			Point2D startPoint, Point2D endPoint) {

		// �ж������Ƿ��ظ�
		if (startPoint.equals(endPoint)) {
			String message = InternalResource.loadString("startPoint,endPoint",
					InternalResource.TwoPointsShouldNotBeEqual,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);

		}
		Point2Ds point2Ds = new Point2Ds();
		point2Ds.add(startPoint);
		point2Ds.add(endPoint);
		GeoLine line = new GeoLine(point2Ds);

		Point2D basePoint = computePerpendicularPosition(point, startPoint,
				endPoint);
		line.rotate(basePoint, 90);

		return line;
	}

	// ������֪�㵽��֪�ߵĴ���
	public static Point2D computePerpendicularPosition(Point2D point,
			Point2D startPoint, Point2D endPoint) {
		// �ж������Ƿ��ظ�
		if (startPoint.equals(endPoint)) {
			String message = InternalResource.loadString("startPoint,endPoint",
					InternalResource.TwoPointsShouldNotBeEqual,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);

		}
		double[] values = new double[2];
		// if (!isPointOnLine(point, startPoint, endPoint, true)) {
		GeometristNative.jni_ComputePerpendicularPosition(point.getX(), point
				.getY(), startPoint.getX(), startPoint.getY(), endPoint.getX(),
				endPoint.getY(), values);
		return new Point2D(values[0], values[1]);
		// }
		// return null;
	}

	/**
	 * �ж���֪���Ƿ�����֪�߶Σ�ֱ�ߣ��ϣ��������Ϸ��� True�� ���򷵻� False��
	 * 
	 * @param point
	 *            Point2D ��֪��
	 * @param startPoint
	 *            Point2D ��֪�߶ε����
	 * @param endPoint
	 *            Point2D ��֪�߶ε��յ�
	 * @param isExtended
	 *            boolean �Ƿ��߶ν����ӳ����㣬��� isExtended = True���Ͱ�ֱ�߼��㣬�����߶μ���
	 * @return boolean �ж���֪���Ƿ�����֪�߶Σ�ֱ�ߣ��ϣ��������Ϸ��� True�� ���򷵻� False��
	 */
	public static boolean isPointOnLine(Point2D point, Point2D startPoint,
			Point2D endPoint, boolean isExtended) {

		// �ж������Ƿ��ظ�
		if (startPoint.equals(endPoint)) {
			String message = InternalResource.loadString("startPoint,endPoint",
					InternalResource.TwoPointsShouldNotBeEqual,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);

		}

		return GeometristNative.jni_IsPointOnLine(point.getX(), point.getY(),
				startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint
						.getY(), isExtended);
	}

	/**
	 * ���������߶Σ�ֱ�ߣ��Ľ���
	 * 
	 * @param startPoint1
	 *            Point2D ��һ���ߵ����
	 * @param endPoint1
	 *            Point2D ��һ���ߵ��յ�
	 * @param startPoint2
	 *            Point2D �ڶ����ߵ����
	 * @param endPoint2
	 *            Point2D �ڶ����ߵ��յ�
	 * 
	 * @param isExtended
	 *            boolean �Ƿ��߶ν����ӳ����㣬��� isExtended = True���Ͱ�ֱ�߼��㣬�����߶μ���
	 * @return boolean ���������߶Σ�ֱ�ߣ��Ľ���
	 */
	public static Point2D intersectLine(Point2D startPoint1, Point2D endPoint1,
			Point2D startPoint2, Point2D endPoint2, boolean isExtended) {

		// �ж������Ƿ��ظ�
		if (startPoint1.equals(endPoint1)) {
			String message = InternalResource.loadString(
					"startPoint1,endPoint1",
					InternalResource.TwoPointsShouldNotBeEqual,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);

		}
		if (startPoint2.equals(endPoint2)) {

			String message = InternalResource.loadString(
					"startPoint2,endPoint2",
					InternalResource.TwoPointsShouldNotBeEqual,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ����˼·����newһ��Point2D����Ȼ����x��y���һ��point���鴫��JNI����JNI���޸�x��y��ֵ��Ȼ�󴫳���
		double[] returnPoint = new double[2];
		boolean result = GeometristNative.jni_IntersectLine(startPoint1.getX(),
				startPoint1.getY(), endPoint1.getX(), endPoint1.getY(),
				startPoint2.getX(), startPoint2.getY(), endPoint2.getX(),
				endPoint2.getY(), isExtended, returnPoint);
		if (result) {

			return new Point2D(returnPoint[0], returnPoint[1]);
		}
		return null;
	}

	// �������ߵĳ���
	// Point2Ds ���ɲ���ߵļ򵥵㴮��
	// double ���������������ĳ���
	// double ���������������ı��ʡ�
	// double dPrimeAxis = 6378137.0, double dFlattening = 0.003352811
	public static double computeGeodesicDistance(Point2Ds points,
			double majorAxis, double flatten) {
		if (points.getCount() < 2) {
			String message = InternalResource.loadString("points",
					InternalResource.Point2DsPointCountShouldMoreThanTwo,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (majorAxis <= 0) {
			String message = InternalResource.loadString("majorAxis",
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (flatten <= 0) {
			String message = InternalResource.loadString("flatten",
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (flatten >= 1) {
			String message = InternalResource.loadString("flatten",
					InternalResource.GlobalArgumentOutOfBounds,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		double[] valuesX = new double[points.getCount()];
		double[] valuesY = new double[points.getCount()];
		for (int i = 0; i < points.getCount(); i++) {
			valuesX[i] = points.getItem(i).getX();
			valuesY[i] = points.getItem(i).getY();
		}

		return GeometristNative.jni_ComputeGeodesicDistance(valuesX, valuesY,
				majorAxis, flatten);

	}
	
	// ���㾭γ�����	
	//geometry��Ҫ��������ļ��ζ���
	//PrjCoorSys:ͶӰ����ϵ���͡�ͶӰ����ϵͳ�ɵ�ͼͶӰ��ʽ��ͶӰ���������굥λ�͵�������ϵ��ɡ�
	public static double computeGeodesicArea(Geometry geometry, PrjCoordSys prjCoordSys ) {
			
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		if (prjCoordSys == null || prjCoordSys.getHandle() == 0) {
	           String message = InternalResource.loadString("prjCoordSys",
	                   InternalResource.GlobalArgumentNull,
	                   InternalResource.BundleName);
	           throw new IllegalArgumentException(message);
	       }
		
		if (geometry.getType() != GeometryType.GEOREGION) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		return GeometristNative.jni_ComputeGeodesicArea(geometry.getHandle(), prjCoordSys.getHandle());
	}

	/**
	 * ����㴮���ɵ����ߵĽ��㡣
	 * 
	 * @param points1
	 * @param points2
	 * @return
	 */
	public static Point2D[] intersectPolyLine(Point2Ds points1, Point2Ds points2) {
		int count1 = points1.getCount();
		if (count1 < 2) {
			String message = InternalResource.loadString("points1",
					InternalResource.Point2DsPointCountShouldMoreThanTwo,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		int count2 = points2.getCount();
		if (count2 < 2) {
			String message = InternalResource.loadString("points2",
					InternalResource.Point2DsPointCountShouldMoreThanTwo,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		double[] x1 = new double[count1];
		double[] y1 = new double[count1];
		for (int i = 0; i < count1; i++) {
			x1[i] = points1.getItem(i).getX();
			y1[i] = points1.getItem(i).getY();
		}

		double[] x2 = new double[count2];
		double[] y2 = new double[count2];
		for (int i = 0; i < count2; i++) {
			x2[i] = points2.getItem(i).getX();
			y2[i] = points2.getItem(i).getY();
		}

		double[][] result = GeometristNative.jni_IntersectPolyLine(x1, y1,
				count1, x2, y2, count2);
		double[] resultX = result[0];
		double[] resultY = result[1];
		Point2D[] pts = new Point2D[resultX.length];
		for (int i = 0; i < pts.length; i++) {
			Point2D pt = new Point2D();
			pt.setX(resultX[i]);
			pt.setY(resultY[i]);
			pts[i] = pt;
		}
		return pts;
	}

	/**
	 * �Ե㴮���й⻬����
	 * 
	 * @param points
	 *            ��Ҫ���й⻬����ĵ㴮
	 * @param smoothness
	 *            �⻬ϵ��������ֵ[2,10]
	 * @return
	 */
	public static Point2Ds smooth(Point2Ds points, int smoothness) {
		if (points == null) {
			String message = InternalResource.loadString("points",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (points.getCount() < 4) {
			String message = InternalResource
					.loadString(
							"points",
							InternalResource.GeometristSmoothPointsCountShouldNotSmallThanFour,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// Modified by fansc �㷨�ײ㲻��smoothness�������ƣ���������ֵ������.Net�����һֱû�������ƣ��ӿڲ�ͳһ
		// if (smoothness < 2 || smoothness > 10) {
		if (smoothness < 2) {
			String message = InternalResource.loadString("smoothness",
					InternalResource.GlobalArgumentShouldNotSmallerThanTwo,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����ṩ�Ľӿڲ����б�ΪGeometry������Ͳ�ȡ��GeoLine��
		GeoLine geoLine = new GeoLine(points);
		InternalHandleDisposable.setIsDisposable(geoLine, false);
		long handle = geoLine.getHandle();

		long resultHandle = GeometristNative.jni_Smooth(handle, smoothness);
		Point2Ds result = null;
		if (resultHandle != 0) {
			GeoLine resultLine = new GeoLine(resultHandle);
			int count = resultLine.getPart(0).getCount();
			Point2D[] point2Ds = new Point2D[count];
			for (int i = 0; i < count; i++) {
				Point2D pt2D = resultLine.getPart(0).getItem(i);
				point2Ds[i] = pt2D;
			}
			result = new Point2Ds(point2Ds);
			// ���ڵײ������GeoLine�ڴ��ͷ�
			InternalHandleDisposable.setIsDisposable(resultLine, true);
			resultLine.dispose();
		}
		return result;
	}

	/**
	 * ���߶����õ���д��
	 * 
	 * @param sourceLine
	 *            ��Ҫ���д�ϵ��߶���
	 * @param splitGeometry
	 *            ������ϵĶ���Ŀǰ֧�ֵ㡢�ߡ���
	 * @param tolerance
	 *            ����
	 * @return
	 */
	public static GeoLine[] splitLine(GeoLine sourceLine,
			Geometry splitGeometry, double tolerance) {
		if (sourceLine == null || sourceLine.getHandle() == 0) {
			String message = InternalResource.loadString("sourceLine",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (splitGeometry == null || splitGeometry.getHandle() == 0) {
			String message = InternalResource.loadString("splitGeometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (!hasAssignedGeometryTypes(sourceLine, GeometryType.GEOLINE)) {
			String message = InternalResource.loadString("sourceLine",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (!hasAssignedGeometryTypes(splitGeometry, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("splitGeometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (splitGeometry.getType().equals(GeometryType.GEOPOINT)
				&& tolerance <= 0) {
			String message = InternalResource.loadString("tolerance",
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		long[] BreakLinehandles = GeometristNative.jni_SplitLine(sourceLine
				.getHandle(), splitGeometry.getHandle(), tolerance);
		// ����ָ�ʧ��
		if (BreakLinehandles == null) {
			return null;
		}
		
		int length = BreakLinehandles.length;
		GeoLine[] result = new GeoLine[length];

		for (int i = 0; i < length; i++) {
			result[i] = (GeoLine) Geometry.createInstance(BreakLinehandles[i]);
		}

		return result;
	}

	// �ڲ����ã�ֻҪ���ж�geometry�������Ƿ���types�е�һ��,�򷵻�Ϊtrue,���򷵻�false
	private static boolean hasAssignedGeometryTypes(Geometry geometry,
			GeometryType... types) {
		boolean result = false;
		for (GeometryType e : types) {
			result = result || (geometry.getType() == e);
		}
		return result;
	}
	
	/**
	 * ����㵽���ߵľ������Сֵ�Լ���Ӧ�ĵ�
	 * 
	 * @param pt2D ����ĵ㣬Ϊ��γ�ȵ�
	 * @param geoLine ���������
	 * @param dInf ���صĴ���͵㵽�ߵĴ��߾��룬���뵥λΪ��
	 * @return 
	 */
	private static boolean nearestPointToVertex(Point2D pt2D,
			GeoLine geoLine, double[] dInf) {
		if (geoLine == null || geoLine.getHandle() == 0) {
			String message = InternalResource.loadString("geoLine",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (!hasAssignedGeometryTypes(geoLine, GeometryType.GEOLINE)) {
			String message = InternalResource.loadString("sourceLine",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		boolean bGet = false;
		bGet = GeometristNative.jni_NearestPointToVertex(pt2D.getX(), pt2D.getY(), 
				geoLine.getHandle(), dInf);
		return bGet;
	}
	
	/**
	 * �������������Ĵ�Բ���ϵ���̾���.pnt1 �� pnt2���ڴ�Բ������������������Ǿ�γ�ȵ�,����ֵ��λΪ��
	 * 
	 * @param pt2DFrom ��ʼ��
	 * @param pt2DTo   ��ֹ��
	 * @return 
	 */
	private static double GetSpheroidDistance(Point2D ptFrom,
			Point2D ptTo) {
		double dDistance = 0;
		dDistance = GeometristNative.jni_GetSpheroidDistance(ptFrom.getX(), ptFrom.getY(), 
				ptTo.getX(), ptTo.getY());
		return dDistance;
	}

	/**
	 * �ж�ָ��Geometry�Ƿ����ཻ,���߻�������Ƿ����߶��ཻ
	 * @param geometry  ����ļ��ζ���
	 * @return   �߻������ཻ������true;������Ϊnull���������ͷš������߻�����󣬷���false
	 */
	public static boolean isSelfIntersect(Geometry geometry){
		if(geometry != null && geometry.getHandle() != 0){
			return GeometristNative.jni_IsSelfIntersect(geometry.getHandle());
		}
		return false;
	}

}