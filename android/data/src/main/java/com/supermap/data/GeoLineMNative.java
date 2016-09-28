package com.supermap.data;

/**
 * <p>Title: GeoLineM Jni·â×°</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ÕÅ¼ÌÄÏ
 * @version 2.0
 */
class GeoLineMNative {
    public GeoLineMNative() {
    }
    

    public native static long jni_New();

    public native static void jni_Delete(long instance);

    public static native int jni_GetPartCount(long instance);

    public static native double jni_GetLength(long instance);

    public static native int jni_AddPart(long instance, double[] xs,
                                         double[] ys,double[] ms);

    public static native boolean jni_RemovePart(long instance, int index);

    public static native long jni_ConvertToRegion(long instance);

    public static native void jni_FindPointOnLineByDistance(long instance,
            double distance, double[] point);

    public static native int jni_GetPartPointCount(long instance, int index);

    public static native void jni_GetPart(long instance, int index, double[] xs,
                                          double[] ys,double[] ms);


    public static native boolean jni_InsertPart(long instance, int index,
                                                double[] xs, double[] ys,double[] ms);

    public static native boolean jni_Reverse(long instance);

    public static native boolean jni_SetPart(long instance, int index,
                                             double[] xs, double[] ys,double[] ms);

    public static native long jni_Clone(long instance);

    public static native void jni_Clear(long instance);

    public static native double jni_GetMaxM(long instance);

    public static native double jni_GetMinM(long instance);
    
    public static native long jni_ConvertToLine(long instance);
    
    //added by shanqc from 2010-12 to 2011-01
    public static native boolean jni_ReverseMOrder(long instance);
    
    public static native void jni_GetPointAtM(long instance,double[] point,
    					double measure,  double dOffset,boolean bIgnoreGap);
    
    public static native void jni_GetPointAtDistance(long instance,double[] point,
    											double distance, boolean bIgnoreGap);
    
    public static native double jni_GetMAtDistance(long instance, double distance,
    											 boolean bIgnoreGap);
    public static native double jni_GetMAtDistanceSub(long instance, double distance,
    											   int subindex, boolean bIgnoreGap);
    
    public static native double jni_GetDistanceAtM(long instance, double distance,
			 									   boolean bIgnoreGap);
    public static native double jni_GetDistanceAtMSub(long instance, double distance,
    		 										  int subindex, boolean bIgnoreGap);
    
    public static native int jni_SetMAtPoint(long instance, double[] point, double measure,
    										double tolorence,int wheretoCalibrate);
    
    public static native void jni_SetMAsDistance(long instance, double originM, 
    											 double scale, boolean isIgnoreGap);
    public static native double jni_GetMAtPoint(long instance,double[] point,
    											double tolorence,boolean isIgnoreGap);
    
    public static native long jni_GetSubCurveAtM(long instance,double fromMeasure,double toMeasure);
     
    //public static native boolean jni_Joint(long lineminstance, long otherlinem);
    
    //public static native boolean jni_Union(long lineminstance, long otherlinem);
    
	public static native long jni_Clip(long lineminstance, long clipGeoregion);
	
	public  static native boolean jni_Split(long instance, double[] point,
											long result1,long result2);
	
	public static native boolean jni_CalibrateLineM(long instance,
												    double[] xs, double[] ys,double[] ms,
												    int method, boolean bIgnoreGap);
	
	public static  native boolean jni_UpdateM(long instance,
											  double[]  fromPoint,double[] toPoint,
											  double fromMeasure, double toMeasure,
											  double tolerance, 
											  int wheretocalibrate,int method);
	
	public static  native boolean jni_UpdateMIndex(long instance,
											  	   int fromIndex, int toIndex,
											  	   double fromMeasure, double toMeasure,
											  	   int wheretocalibrate,int method);
	
	public  static native boolean jni_InterpolateM(long instance,
												   double[] fromPoint,double[] toPoint, 
												   double fromMeasure, double toMeasure,
												   double tolerance, int method);
	public  static native boolean jni_ExtrapolateM(long instance,
												   double[] fromPoint,double[] toPoint, 
												   double fromMeasure, double toMeasure,
												   double tolerance, int method);
	public  static native long jni_MakeLineM(long instanceLine,
											 double[] xs, double[] ys,double[] ms);
	
	public  static native void jni_OffsetMeasure(long instance,double offset);
	
	public  static native void jni_CalculateNoM(long instance, boolean bIgnoreGap );
	//added by shanqc from 2010-12 to 2011-01


	public static native long jni_GetLineMAtM(long instance, double startMeasure, double endMeasure, double offset, boolean isIgnoreGap);
}
