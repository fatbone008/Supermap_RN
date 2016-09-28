package com.supermap.data;

/**
 * <p>
 * Title:基本几何运算类
 * </p>
 * 
 * <p>
 * Description该类用来进行基本的几何运算，例如：判断点线、线线之间的关系，根据一条已知线和一定的条件（平行、垂直等）得到所需要的线或点等等。
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
	 * 私有构造函数，不能创建
	 */
	private Geometrist() {

	}

	static {
		Environment.LoadWrapJ();
	}

	/**
	 * 判断查询对象和目标对象是否完全相等，类型必须相同，点的顺序也必须一样
	 * 
	 * @param geometrySearch
	 *            Geometry 查询对象，支持点、线、面类型
	 * @param geometryTarget
	 *            Geometry 目标对象，支持点、线、面类型
	 * @return boolean 查询对象和目标对象是否完全相等时返回true,否则返回false
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
		// 检查异常，查询对象，支持点、线、面类型
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 检查异常，目标对象，支持点、线、面类型
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
	 * 判断查询对象和目标对象是否相离
	 * 
	 * @param geometrySearch
	 *            Geometry 查询对象，支持点、线、面类型
	 * @param geometryTarget
	 *            Geometry 目标对象，支持点、线、面类型
	 * @return boolean 查询对象和目标对象相离返回true,不相离返回false
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
		// 检查异常，查询对象，支持点、线、面类型
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 检查异常，目标对象，支持点、线、面类型
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
	 * 判断查询对象和目标对象是否有面积相交,查询对象和目标对象必须有一个为面对象
	 * 
	 * @param geometrySearch
	 *            Geometry 查询对象，支持点、线、面类型
	 * @param geometryTarget
	 *            Geometry 目标对象，支持点、线、面类型
	 * @return boolean 查询对象和目标对象是否有面积相交返回ture,反之返回false
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
		// 检查异常，查询对象，支持点、线、面类型

		// luhao 2010-8-26 还支持文本类型
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION,
				GeometryType.GEOTEXT)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 检查异常，目标对象，支持点、线、面类型
		// luhao 2010-8-26 还支持文本类型
		if (!hasAssignedGeometryTypes(geometryTarget, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION,
				GeometryType.GEOTEXT)) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// modify by zhangjn 传入参数至少有一个是面，否则抛出异常
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
	 * 判断查询对象和目标对象是否接触
	 * 
	 * @param geometrySearch
	 *            Geometry 查询对象，支持点、线、面类型
	 * @param geometryTarget
	 *            Geometry 目标对象，支持点、线、面类型
	 * @return boolean 判断查询对象和目标对象是否接触，接触则返回true,反之返回false
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
		// 检查异常，查询对象，支持点、线、面类型
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 检查异常，目标对象，支持点、线、面类型
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
	 * 判断查询对象与目标对象是否交叠，查询对象与目标对象的维数要求相同
	 * 
	 * @param geometrySearch
	 *            Geometry 查询对象，只支持线、面类型
	 * @param geometryTarget
	 *            Geometry 目标对象，只支持线、面类型
	 * @return boolean 判断查询对象与目标对象是否交叠，有交叠返回true，反之返回false
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

		// 查询对象，只支持线、面类型
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 目标对象，只支持线、面类型
		if (!hasAssignedGeometryTypes(geometryTarget, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 查询对象与目标对象的类型要求相同
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
	 * 判断查询对象是否穿越目标对象 目标对象为面时，要求查询对象内部与目标对象的内部的交集不为空且查询对象的内部与目标对象的外部的交集不为空；
	 * 目标对象为线时，要求查询对象从目标对象内部的一侧进入并在目标对象内部的某处另一侧穿出。
	 * 
	 * @param geometrySearch
	 *            Geometry 查询对象，只支持线类型
	 * @param geometryTarget
	 *            Geometry 目标对象，只支持线、面类型
	 * @return boolean 判断查询对象是否穿越目标对象，穿越返回true,反之返回false
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
		// 查询对象，只支持线类型 by zhangjn
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOLINE)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 目标对象，只支持线、面类型
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
	 * 判断查询对象是否在目标对象内 是CanContain的逆算子
	 * 
	 * @param geometrySearch
	 *            Geometry 查询对象，支持点、线、面类型
	 * @param geometryTarget
	 *            Geometry 目标对象，支持点、线、面类型
	 * @return boolean 判断查询对象是否在目标对象内，在其内返回true,反之返回false
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
		// 检查异常，查询对象，支持点、线、面类型
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 检查异常，目标对象，支持点、线、面类型
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
	 * 判断查询对象是否完全包含目标对象 是IsWithin的逆算子
	 * 
	 * @param geometrySearch
	 *            Geometry 查询对象，支持点、线、面类型
	 * @param geometryTarget
	 *            Geometry 目标对象，支持点、线、面类型
	 * @return boolean 查询对象完全包含目标对象返回true,反之返回false
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
		// 检查异常，查询对象，支持点、线、面类型
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 检查异常，目标对象，支持点、线、面类型
		if (!hasAssignedGeometryTypes(geometryTarget, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometryTarget",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 查询对象的维数>目标对的维数,在这里先不判断了，在底层做了判断，by zhangjn
		return GeometristNative.jni_CanContain(geometrySearch.getHandle(),
				geometryTarget.getHandle());

	}

	/**
	 * 判断查询对象和目标对象是否有共同节点
	 * 
	 * @param geometrySearch
	 *            Geometry 查询对象，支持点、线、面类型
	 * @param geometryTarget
	 *            Geometry 目标对象，支持点、线、面类型
	 * @return boolean 查询对象和目标对象有共同节点返回ture,反之返回false
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
		// 检查异常，查询对象，支持点、线、面类型
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 检查异常，目标对象，支持点、线、面类型
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
	 * 判断查询对象与目标对象是否有共同线段
	 * 
	 * @param geometrySearch
	 *            Geometry 查询对象，只支持线、面类型
	 * @param geometryTarget
	 *            Geometry 目标对象，只支持线、面类型
	 * @return boolean 查询对象与目标对象有公共线段返回true,反之返回false
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

		// 检查异常，查询对象，支持线、面类型
		if (!hasAssignedGeometryTypes(geometrySearch, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometrySearch",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 检查异常，目标对象，支持线、面类型
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
	 * 多边形(二维对象)裁剪
	 * 
	 * @param geometry
	 *            Geometry 被裁剪的对象，支持线和面类型
	 * @param clipGeometry
	 *            Geometry 用于裁剪的对象，必须是面对象
	 * @return Geometry 返回裁剪结果对象
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
		// 检查异常，查询对象，支持线和面类型
		if (!hasAssignedGeometryTypes(geometry, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 检查异常，目标对象，只支持面类型
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
	 * 二维矩形裁剪
	 * 
	 * @param geometry
	 *            Geometry 被裁剪的对象，支持线和面类型
	 * @param clipRectangle
	 *            Rectangle2D 用于裁剪的二维矩形对象
	 * @return Geometry 返回裁剪结果对象
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
	 * 判断面对象是否含有洞
	 * 
	 * @param geometry
	 *            Geometry 进行判断的面对象，暂时只支持二维面对象GeoRegion
	 * @return boolean 是否含有洞
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
	 * 进行求交运算，返回两个几何对象的交集 进行求交运算的两个几何对象必须是同类型的，可以是点点、线线或者面面
	 * 目前UGC阶段的开发对第二几何对象只支持面类型，在《叠加分析专题文档》中第二几何对象也要求为面类型
	 * 
	 * @param geometry1
	 *            Geometry 进行求交运算的第一个几何对象
	 * @param geometry2
	 *            Geometry 进行求交运算的第二个几何对象
	 * @return Geometry 返回两个几何对象的交集
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
		// 检查异常，查询对象，支持线和面类型
		if (!hasAssignedGeometryTypes(geometry1, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry1",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 检查异常，目标对象，只支持面类型
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
	 * 用面对象擦除一个几何对象 如果对象全部被擦除了，则返回的Geometry的IsEmpty属性为true
	 * 
	 * @param geometry
	 *            Geometry 被擦除的几何对象，支持点、线、面对象类型
	 * @param eraseGeometry
	 *            Geometry 用于擦除的面几何对象，必须为面对象类型
	 * @return Geometry 返回擦除后的结果几何对象
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
		// 检查异常，查询对象，支持点,线和面类型

		if (!hasAssignedGeometryTypes(geometry, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 检查异常，目标对象，只支持面类型
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
	 * 进行求并运算，返回两个几何对象的并集 进行求并运算的两个几何对象必须是同类型的，可以是点点、线线或者面面
	 * 目前UGC阶段的开发支持面与面的求并，在《叠加分析专题文档》中的合并运算的说明也写的是只支持面与面求并
	 * 
	 * @param geometry1
	 *            Geometry 进行求并运算的第一个几何对象
	 * @param geometry2
	 *            Geometry 进行求并运算的第一个几何对象
	 * @return Geometry 返回值为两个求并几何对象的并集
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
		// 检查异常，进行求并运算的第一个几何对象，只支持面类型
		if (!hasAssignedGeometryTypes(geometry1, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry1",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 检查异常，进行求并运算的第一个几何对象，只支持面类型
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
	 * 进行两个几何对象的异或运算 进行异或运算的两个几何对象必须是同类型的，可以是点点、线线或者面面
	 * 
	 * @param geometry1
	 *            Geometry 进行异或运算的第一个几何对象
	 * @param geometry2
	 *            Geometry 进行异或运算的第二个几何对象
	 * @return Geometry 返回值为进行异或运算的结果几何对象
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
		// 检查异常，查询对象，支持点、线、面类型
		if (!hasAssignedGeometryTypes(geometry1, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry1",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 检查异常，目标对象，支持点、线、面类型
		if (!hasAssignedGeometryTypes(geometry2, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry2",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 类型必须相同
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
	 * 对被更新的几何对象利用另一个几何对象进行更新计算，将更新计算的结果返回
	 * 更新运算是用更新几何替换与被更新几何对象的重合部分，是一个先擦除后粘贴的过程
	 * 
	 * @param geometry
	 *            Geometry 被更新的几何对象，必须为面对象
	 * @param UpdateGeometry
	 *            Geometry 用于进行更新运算的几何对象，必须为面对象
	 * @return Geometry 返回更新运算的结果几何对象
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
		// 检查异常，查询对象，只支持面类型
		if (!hasAssignedGeometryTypes(geometry, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 检查异常，查询对象，只支持面类型
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
	 * 对两个几何对象进行同一运算 同一运算就是第一几何对象与第二几何对象先求交，然后求交结果再与第一几何对象求并的运算
	 * 
	 * @param geometry
	 *            Geometry 被同一运算的几何对象，即第一几何对象，支持点、线、面对象
	 * @param IdentityGeometry
	 *            Geometry 用于同一运算的几何对象，即第二几何对象，必须为面对象
	 * @return Geometry 返回同一运算的结果几何对象
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
		// 检查异常，查询对象，支持点、线、面类型
		if (!hasAssignedGeometryTypes(geometry, GeometryType.GEOPOINT,
				GeometryType.GEOLINE, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 检查异常，查询对象，只支持面类型
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
	 * 按照一定的距离容限对几何对象进行重采样
	 * 
	 * @param geometry
	 *            Geometry 被重采样的几何对象
	 * @param tolerance
	 *            double 进行重采样的距离容限
	 * @return Geometry 返回重采样的结果几何对象
	 */
	@Deprecated
	public static Geometry resample(Geometry geometry, double tolerance) {
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 检查异常，查询对象，支持点、线、面类型
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
	 * 按照一定的距离容限对几何对象进行重采样
	 * 
	 * @param geometry
	 *            Geometry 被重采样的几何对象
	 * @param tolerance
	 *            对于ResampeType.RTBEND 和 ResampeType.RTGENERAL，tolerance为距离容限
	 * @return Geometry 返回重采样的结果几何对象
	 */
	public static Geometry resample(Geometry geometry,
			ResampleType resampleType, double tolerance) {
		
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 检查异常，查询对象，支持点、线、面类型
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
	 * 求两个几何对象之间的距离
	 * 
	 * @param geometry1
	 *            Geometry
	 * @param geometry2
	 *            Geometry
	 * @return distance double
	 */
	public static double distance(Geometry geometry1, Geometry geometry2) {
		// @added by 张继南 at 2007年10月30日 上午11时44分29秒
		// @reason:为满足iServer需求，新增
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
	// /////////////////以下为6.0新增接口///////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * 计算点集的凸闭包，即最小外接多边形。返回一个简单凸多边形
	 * 
	 * @param points
	 *            Point2Ds 点集
	 * @return Geometry 返回最小外接多边形
	 */
	public static GeoRegion computeConvexHull(Point2Ds points) {

		int length = points.getCount();
		// 多边形点数应该是3个以上
		if (length < 3) {
			String message = InternalResource.loadString("points",
					InternalResource.GeoRegionInvalidPointsLength,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// {{ luhao 2010-8-18 无语，怎么可以直接构造呢？
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
	 * 计算几何对象的凸闭包，即最小外接凸多边形。返回一个简单凸多边形
	 * 
	 * @param geometry
	 *            Geometry 几何对象
	 * @return Geometry 返回最小外接多边形
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
	 * 用线或面几何对象分割面几何对象，即用splitGeometry去分割sourceGeoRegion对象
	 * 
	 * @param soureRegion
	 *            GeoRegion 被分割的面对象
	 * @param splitGeometry
	 *            Geometry 用于分割的几何对象，可以是线或面几何对象
	 * @param targetGeoRegion1
	 *            GeoRegion 返回分割后的第一个面对象
	 * @param targetGeoRegion2
	 *            GeoRegion 返回分割后的第一个面对象
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
		// 检查异常，查询对象，支持面类型
		if (!hasAssignedGeometryTypes(soureRegion, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("soureRegion",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 检查异常，查询对象，支持线和面类型
		if (!hasAssignedGeometryTypes(splitGeometry, GeometryType.GEOLINE,
				GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("splitGeometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 检查异常，查询对象，支持面类型
		if (!hasAssignedGeometryTypes(targetGeoRegion1, GeometryType.GEOREGION)) {
			String message = InternalResource.loadString("targetGeoRegion1",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 检查异常，查询对象，支持面类型
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
	 * 计算线与线的倒圆角，返回倒圆角所对应的弧段。 其中参数 startPoint1和 endPoint1构成第一条线段，
	 * startPoint2和endPoint2构成第二条线段， 作两条线段的内切圆，该内切圆的半径必须满足参数 radius给定的值。
	 * 此时，在靠近两条线交点的两个点的坐标将发生变化， 变为内切圆和两条线切点的坐标。得到的弧段将是四个点和靠近交点的圆弧的组合。
	 * 
	 * @param startPoint1
	 *            Point2D 第一条线的起点
	 * @param endPoint1
	 *            Point2D 第一条线的终点
	 * @param startPoint2
	 *            Point2D 第二条线的起点
	 * @param endPoint2
	 *            Point2D 第二条线的终点
	 * @param radius
	 *            double 圆弧的半径
	 * @return GeoArc 返回倒圆角所对应的弧段
	 */
	public static GeoArc computeFillet(Point2D startPoint1, Point2D endPoint1,
			Point2D startPoint2, Point2D endPoint2, double radius) {

		if (radius <= 0) {
			String message = InternalResource.loadString("radius",
					InternalResource.GlobalArgumentShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 判断两点是否重复
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
	 * 判断两条直线是否平行
	 * 
	 * @param startPoint1
	 *            Point2D 第一条线的起点
	 * @param endPoint1
	 *            Point2D 第一条线的终点
	 * @param startPoint2
	 *            Point2D 第二条线的起点
	 * @param endPoint2
	 *            Point2D 第二条线的终点
	 * @return GeoArc 判断两条直线是否平行，平行返回True，否则返回False
	 */
	public static boolean isParallel(Point2D startPoint1, Point2D endPoint1,
			Point2D startPoint2, Point2D endPoint2) {

		// 判断两点是否重复
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
	 * 求经过指定点与已知直线平行的直线
	 * 
	 * @param point
	 *            Point2D 直线外的任意一点
	 * @param startPoint
	 *            Point2D 直线的一点
	 * @param endPoint
	 *            Point2D 直线的另一点
	 * @return GeoLine 返回平行线
	 */
	public static GeoLine computeParallel(Point2D point, Point2D startPoint,
			Point2D endPoint) {

		// 判断两点是否重复
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
	 * 根据距离求已知折线的平行线，返回平行线
	 * 
	 * @param geoLine
	 *            GeoLine 已知折线对象
	 * @param distance
	 *            double 所求平行线间的距离
	 * 
	 * @return GeoLine 返回平行线
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
	 * 判断两条直线是否垂直
	 * 
	 * @param startPoint1
	 *            Point2D 第一条线的起点
	 * @param endPoint1
	 *            Point2D 第一条线的终点
	 * @param startPoint2
	 *            Point2D 第二条线的起点
	 * @param endPoint2
	 *            Point2D 第二条线的终点
	 * @return boolean 判断两条直线是否垂直，垂直返回True，否则返回False
	 */
	public static boolean isPerpendicular(Point2D startPoint1,
			Point2D endPoint1, Point2D startPoint2, Point2D endPoint2) {

		// 判断两点是否重复
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
	 * 计算已知点到已知线的垂线
	 * 
	 * @param point
	 *            Point2D 已知一点
	 * @param startPoint
	 *            Point2D 直线上的一点
	 * @param endPoint
	 *            Point2D 直线上的另一点
	 * @return GeoLine 返回垂线
	 */
	public static GeoLine computePerpendicular(Point2D point,
			Point2D startPoint, Point2D endPoint) {

		// 判断两点是否重复
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

	// 计算已知点到已知线的垂足
	public static Point2D computePerpendicularPosition(Point2D point,
			Point2D startPoint, Point2D endPoint) {
		// 判断两点是否重复
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
	 * 判断已知点是否在已知线段（直线）上，点在线上返回 True， 否则返回 False。
	 * 
	 * @param point
	 *            Point2D 已知点
	 * @param startPoint
	 *            Point2D 已知线段的起点
	 * @param endPoint
	 *            Point2D 已知线段的终点
	 * @param isExtended
	 *            boolean 是否将线段进行延长计算，如果 isExtended = True，就按直线计算，否则按线段计算
	 * @return boolean 判断已知点是否在已知线段（直线）上，点在线上返回 True， 否则返回 False。
	 */
	public static boolean isPointOnLine(Point2D point, Point2D startPoint,
			Point2D endPoint, boolean isExtended) {

		// 判断两点是否重复
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
	 * 返回两条线段（直线）的交点
	 * 
	 * @param startPoint1
	 *            Point2D 第一条线的起点
	 * @param endPoint1
	 *            Point2D 第一条线的终点
	 * @param startPoint2
	 *            Point2D 第二条线的起点
	 * @param endPoint2
	 *            Point2D 第二条线的终点
	 * 
	 * @param isExtended
	 *            boolean 是否将线段进行延长计算，如果 isExtended = True，就按直线计算，否则按线段计算
	 * @return boolean 返回两条线段（直线）的交点
	 */
	public static Point2D intersectLine(Point2D startPoint1, Point2D endPoint1,
			Point2D startPoint2, Point2D endPoint2, boolean isExtended) {

		// 判断两点是否重复
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

		// 基本思路是先new一个Point2D对象，然后其x，y组成一个point数组传入JNI，在JNI中修改x，y的值，然后传出。
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

	// 计算测地线的长度
	// Point2Ds 构成测地线的简单点串。
	// double 测地线所在椭球体的长轴
	// double 测地线所在椭球体的扁率。
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
	
	// 计算经纬度面积	
	//geometry：要计算面积的几何对象
	//PrjCoorSys:投影坐标系类型。投影坐标系统由地图投影方式、投影参数、坐标单位和地理坐标系组成。
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
	 * 计算点串构成的折线的交点。
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
	 * 对点串进行光滑处理
	 * 
	 * @param points
	 *            需要进行光滑处理的点串
	 * @param smoothness
	 *            光滑系数，建议值[2,10]
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
		// Modified by fansc 算法底层不对smoothness做出限制，给出建议值。而且.Net组件中一直没有做限制，接口不统一
		// if (smoothness < 2 || smoothness > 10) {
		if (smoothness < 2) {
			String message = InternalResource.loadString("smoothness",
					InternalResource.GlobalArgumentShouldNotSmallerThanTwo,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 类库提供的接口参数列表为Geometry，组件就采取传GeoLine，
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
			// 将在底层申请的GeoLine内存释放
			InternalHandleDisposable.setIsDisposable(resultLine, true);
			resultLine.dispose();
		}
		return result;
	}

	/**
	 * 对线对象用点进行打断
	 * 
	 * @param sourceLine
	 *            需要进行打断的线对象
	 * @param splitGeometry
	 *            用来打断的对象，目前支持点、线、面
	 * @param tolerance
	 *            容限
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
		// 如果分割失败
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

	// 内部调用，只要是判断geometry的类型是否是types中的一种,则返回为true,否则返回false
	private static boolean hasAssignedGeometryTypes(Geometry geometry,
			GeometryType... types) {
		boolean result = false;
		for (GeometryType e : types) {
			result = result || (geometry.getType() == e);
		}
		return result;
	}
	
	/**
	 * 计算点到折线的距离的最小值以及对应的点
	 * 
	 * @param pt2D 计算的点，为经纬度点
	 * @param geoLine 计算的折线
	 * @param dInf 返回的垂点和点到线的垂线距离，距离单位为米
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
	 * 计算球面两点间的大圆弧上的最短距离.pnt1 和 pnt2所在大圆弧，两个点参数必须是经纬度点,返回值单位为米
	 * 
	 * @param pt2DFrom 起始点
	 * @param pt2DTo   终止点
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
	 * 判断指定Geometry是否自相交,即线或面对象是否有线段相交
	 * @param geometry  需检查的几何对象
	 * @return   线或面自相交，返回true;若参数为null、对象已释放、不是线或面对象，返回false
	 */
	public static boolean isSelfIntersect(Geometry geometry){
		if(geometry != null && geometry.getHandle() != 0){
			return GeometristNative.jni_IsSelfIntersect(geometry.getHandle());
		}
		return false;
	}

}