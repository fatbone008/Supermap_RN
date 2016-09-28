package com.supermap.data;

import com.supermap.data.Point2Ds.UserType;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author zhangjinan
 * @version 2.0
 */
public final class CoordSysTranslator {
    public static boolean forward(Point2Ds points, PrjCoordSys prjCoordSys) {
        if (points == null) {
            String message = InternalResource.loadString("points",
                    InternalResource.
                    GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        //在点进行转换的时候不区分类型，先记下，返回时设置回去
        UserType ptsType = points.getUserType();
        points.setUserType(UserType.NONE);
        int length = points.getCount();
        if (length < 1) {
            String message = InternalResource.loadString("points",
                    InternalResource.
                    Point2DsIsEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (prjCoordSys == null || prjCoordSys.getHandle() == 0) {
            String message = InternalResource.loadString("prjCoordSys",
                    InternalResource.
                    GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        double[] xs = new double[length];
        double[] ys = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
            if (xs[i] > 180 || xs[i] < -180 || ys[i] > 90 || ys[i] < -90) {
                String message = InternalResource.loadString("points",
                        InternalResource.
                        InvalidLongitudeLatitudeCoord,
                        InternalResource.BundleName);
                throw new IllegalArgumentException(message);
            }
        }
        boolean result = CoordSysTranslatorNative.jni_Forward(xs, ys,
                prjCoordSys.getHandle());
        for (int i = 0; i < length; i++) {
            points.setItem(i,new Point2D(xs[i],ys[i]));
        }
        //还原Point2ds的类型
        points.setUserType(ptsType);
        return result;
    }

    public static boolean inverse(Point2Ds points, PrjCoordSys prjCoordSys) {
        if (points == null) {
            String message = InternalResource.loadString("points",
                    InternalResource.
                    GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        //在点进行转换的时候不区分类型，先记下，返回时设置回去
        UserType ptsType = points.getUserType();
        points.setUserType(UserType.NONE);
        int length = points.getCount();
        if (length < 1) {
            String message = InternalResource.loadString("points",
                    InternalResource.
                    Point2DsIsEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (prjCoordSys == null || prjCoordSys.getHandle() == 0) {
            String message = InternalResource.loadString("prjCoordSys",
                    InternalResource.
                    GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        double[] xs = new double[length];
        double[] ys = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        boolean result = CoordSysTranslatorNative.jni_Inverse(xs, ys,
                prjCoordSys.getHandle());
        for (int i = 0; i < length; i++) {
            points.setItem(i,new Point2D(xs[i],ys[i]));
        }
        //还原Point2ds的类型
        points.setUserType(ptsType);
        return result;
    }

    public static boolean convert(Point2Ds points,
                                  PrjCoordSys srcPrjCoordSys,
                                  PrjCoordSys desPrjCoordSys,
                                  CoordSysTransParameter coordSysTransParameter,
                                  CoordSysTransMethod coordSysTransMethod) {
        if (points == null) {
            String message = InternalResource.loadString("points",
                    InternalResource.
                    GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        //在点进行转换的时候不区分类型，先记下，返回时设置回去
        UserType ptsType = points.getUserType();
        points.setUserType(UserType.NONE);
        int length = points.getCount();
        if (length < 1) {
            String message = InternalResource.loadString("points",
                    InternalResource.
                    Point2DsIsEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (srcPrjCoordSys == null || srcPrjCoordSys.getHandle() == 0) {
            String message = InternalResource.loadString("srcPrjCoordSys",
                    InternalResource.
                    GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (desPrjCoordSys == null || desPrjCoordSys.getHandle() == 0) {
            String message = InternalResource.loadString("targetPrjCoordSys",
                    InternalResource.
                    GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (coordSysTransParameter == null ||
            coordSysTransParameter.getHandle() == 0) {
            // 不抛出异常，新new一个
            coordSysTransParameter = new CoordSysTransParameter();
        }
        if (coordSysTransMethod == null) {
            String message = InternalResource.loadString("coordSysTransMethod",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        double[] xs = new double[length];
        double[] ys = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        boolean result = CoordSysTranslatorNative.jni_ConvertPoints(
                xs, ys,
                srcPrjCoordSys.getHandle(),
                desPrjCoordSys.getHandle(),
                coordSysTransParameter.getHandle(),
                coordSysTransMethod.getUGCValue());
        for (int i = 0; i < length; i++) {
            points.setItem(i,new Point2D(xs[i],ys[i]));
        }
        //还原Point2ds的类型
        points.setUserType(ptsType);
        return result;
    }

    public static boolean convert(Point3Ds points,
            PrjCoordSys srcPrjCoordSys,
            PrjCoordSys desPrjCoordSys,
            CoordSysTransParameter coordSysTransParameter,
            CoordSysTransMethod coordSysTransMethod) {
    	
			if (points == null) {
				String message = InternalResource.loadString("points",
				InternalResource.
				GlobalArgumentNull,
				InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			int length = points.getCount();
			if (length < 1) {
				String message = InternalResource.loadString("points",
				InternalResource.
				Point2DsIsEmpty,
				InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			if (srcPrjCoordSys == null || srcPrjCoordSys.getHandle() == 0) {
				String message = InternalResource.loadString("srcPrjCoordSys",
				InternalResource.
				GlobalArgumentNull,
				InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			if (desPrjCoordSys == null || desPrjCoordSys.getHandle() == 0) {
				String message = InternalResource.loadString("targetPrjCoordSys",
				InternalResource.
				GlobalArgumentNull,
				InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			if (coordSysTransParameter == null ||
				coordSysTransParameter.getHandle() == 0) {
				// 不抛出异常，新new一个
				coordSysTransParameter = new CoordSysTransParameter();
			}
			if (coordSysTransMethod == null) {
				String message = InternalResource.loadString("coordSysTransMethod",
				InternalResource.GlobalArgumentNull,
				InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			double[] xs = new double[length];
			double[] ys = new double[length];
			double[] zs = new double[length];
			for (int i = 0; i < length; i++) {
				xs[i] = points.getItem(i).getX();
				ys[i] = points.getItem(i).getY();
				zs[i] = points.getItem(i).getZ();
			}
			boolean result = CoordSysTranslatorNative.jni_ConvertPoint3Ds(
				xs, ys, zs,
				srcPrjCoordSys.getHandle(),
				desPrjCoordSys.getHandle(),
				coordSysTransParameter.getHandle(),
				coordSysTransMethod.getUGCValue());
			for (int i = 0; i < length; i++) {
				points.setItem(i,new Point3D(xs[i],ys[i], zs[i]));
			}
			return result;
	}

    public static boolean convert(Geometry geometry,
            PrjCoordSys srcPrjCoordSys,
            PrjCoordSys desPrjCoordSys,
            CoordSysTransParameter coordSysTransParameter,
            CoordSysTransMethod coordSysTransMethod) {
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
			InternalResource.
			GlobalArgumentNull,
			InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		if (srcPrjCoordSys == null || srcPrjCoordSys.getHandle() == 0) {
			String message = InternalResource.loadString("srcPrjCoordSys",
			InternalResource.
			GlobalArgumentNull,
			InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (desPrjCoordSys == null || desPrjCoordSys.getHandle() == 0) {
			String message = InternalResource.loadString("desPrjCoordSys",
			InternalResource.
			GlobalArgumentNull,
			InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		if (coordSysTransParameter == null ||
			coordSysTransParameter.getHandle() == 0) {
			// 不抛出异常，新new一个
			coordSysTransParameter = new CoordSysTransParameter();
		}
		
		if (coordSysTransMethod == null) {
			String message = InternalResource.loadString("coordSysTransMethod",
			InternalResource.GlobalArgumentNull,
			InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		boolean result = CoordSysTranslatorNative.jni_ConvertGeometry(
			geometry.getHandle(),
			srcPrjCoordSys.getHandle(),
			desPrjCoordSys.getHandle(),
			coordSysTransParameter.getHandle(),
			coordSysTransMethod.getUGCValue());
		//{{added by zhengyl 如果是geoRegion对象，则需要更新一下parts列表
		if(geometry.getType() == GeometryType.GEOREGION)
		{
			((GeoRegion)geometry).refrashPartsList();
		}
		////}}added by zhengyl 如果是geoRegion对象，则需要更新一下parts列表
		return result;
	}
 
}
