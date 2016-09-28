package com.supermap.data;

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
 * @author not attributable
 * @version 2.0
 */
class GeometristNative {
	private GeometristNative() {
	}

	public native static boolean jni_IsIdentical(long geometry1, long geometry2);

	public native static boolean jni_IsDisjointed(long geometry1, long geometry2);

	public native static boolean jni_HasIntersection(long geometry1,
			long geometry2);

	public native static boolean jni_HasTouch(long geometry1, long geometry2);

	public native static boolean jni_HasOverlap(long geometry1, long geometry2);

	public native static boolean jni_HasCross(long geometry1, long geometry2);

	public native static boolean jni_IsWithin(long geometry1, long geometry2);

	public native static boolean jni_CanContain(long geometry1, long geometry2);

	// public native static boolean jni_HasRelation(
	// long geometry1,
	// long geometry2);

	public native static boolean jni_HasCommonPoint(long geometry1,
			long geometry2);

	public native static boolean jni_HasCommonLine(long geometry1,
			long geometry2);

	// public native static boolean jni_HasAreaIntersection(
	// long geometry1, long geometry2);

	public native static long jni_Intersect(long geometry1, long geometry2);

	public native static long jni_Union(long geometry1, long geometry2);

	public native static long jni_Erase(long geometry1, long geometry2);

	public native static long jni_Update(long geometry1, long geometry2);

	public native static long jni_Identity(long geometry1, long geometry2);

	public native static long jni_XOR(long geometry1, long geometry2);

	public native static long jni_Clip(long geometry, long clipGeometry);

	public native static long jni_Resample(long geometry, double tolerance);
	
	// fansc add
	public native static long jni_Resample1(long geometry, int type, double tolerance);

	public native static double jni_Distance(long geometry1, long geometry2);

	// 下面都是新增的by jiangwh

	public native static boolean jni_IsParallel(double sp1x, double sp1y,
			double ep1x, double ep1y, double sp2x, double sp2y, double ep2x,
			double ep2y);

	public native static boolean jni_IsPerpendicular(double sp1x, double sp1y,
			double ep1x, double ep1y, double sp2x, double sp2y, double ep2x,
			double ep2y);

	public native static void jni_ComputePerpendicularPosition(double px,
			double py, double spx, double spy, double epx, double epy,
			double[] point);

	public native static boolean jni_IsPointOnLine(double px, double py,
			double spx, double spy, double epx, double epy, boolean isExtended);

	public native static boolean jni_IntersectLine(double sp1x, double sp1y,
			double ep1x, double ep1y, double sp2x, double sp2y, double ep2x,
			double ep2y, boolean isExtended, double[] point);

	public native static long jni_ComputeConvexHull(long geometry);

	public native static long jni_ComputeConvexHullPoints(double[] xs,
			double[] ys, int count);

	public native static boolean jni_SplitRegion(long soureRegion,
			long splitGeometry, long targetGeoRegion1, long targetGeoRegion2);

	public native static long jni_ComputeFillet(double sp1x, double sp1y,
			double ep1x, double ep1y, double sp2x, double sp2y, double ep2x,
			double ep2y, double radius);

	public native static long jni_ComputeParallel(long geometry, double distance);

	public native static double jni_ComputeGeodesicDistance(double[] values1,
			double[] values2, double r, double e);
	
	public native static double jni_ComputeGeodesicArea(long geometry, long prjCoordSys);

	public native static double[][] jni_IntersectPolyLine(double[] x1,
			double[] y1, int count1, double[] x2, double[] y2, int count2);

	public native static long jni_Smooth(long handle, int smoothness);

	public native static long jni_ClipRect(long geometry, double left,
			double top, double right, double bottom);

	public native static long[] jni_SplitLine(long sourceLine,
			long splitGeometry, double tolerance);

	public native static boolean jni_HasHollow(long geometry);
	
	// 将JNI层NearestPointToVertex方法封装出来(added by hp 2014/8/14)
	native static boolean jni_NearestPointToVertex(double dx, double dy,
			long geometry, double[] dInf);
	native static double jni_GetSpheroidDistance(double dPntFromX,double dPntFromY,
			double dPntToX, double dPntToY);

	public native static boolean jni_IsSelfIntersect(long handle);
}
