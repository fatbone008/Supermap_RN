package com.supermap.analyst;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ’≈ºÃƒœ
 * @version 2.0
 */
class BufferAnalystNative {

    private BufferAnalystNative() {
    }
    
    //add by xuzw 2008-12-23
    public native static long jni_CreateLineBuffer(long geometryHandle,
    		double leftDistance, double rightDistance,
            int semicircleLineSegment, int ugcBufferSideType, int ugcBufferEndType);
    

    
    public native static boolean jni_CreateLineDatasetBuffer(
    		long sourceDatasetHandle, long sourceRecordsetHandle, long resultDatasetHandle, 
    		double leftDistance, double rightDistance, int ugcBufferRadiusUnit,
            int semicircleLineSegment, int ugcBufferSideType, int ugcBufferEndType, 
            boolean isUnion, boolean isAttributeRetained);
    
    public native static boolean jni_CreateLineDatasetBuffer2(
    		long sourceDatasetHandle, long sourceRecordsetHandle, long resultDatasetHandle, 
    		String strLeftDistance, String strRightDistance, int ugcBufferRadiusUnit,
            int semicircleLineSegment, int ugcBufferSideType, int ugcBufferEndType, 
            boolean isUnion, boolean isAttributeRetained);
    
    public native static boolean jni_CreateDatasetBuffer(
    		long sourceDatasetHandle, long sourceRecordsetHandle, long resultDatasetHandle, 
    		double leftDistance, int ugcBufferRadiusUnit, 
            int semicircleLineSegment, int ugcBufferSideType, int ugcBufferEndType, 
            boolean isUnion, boolean isAttributeRetained);
    
    public native static boolean jni_CreateDatasetBuffer2(
    		long sourceDatasetHandle, long sourceRecordsetHandle, long resultDatasetHandle, 
    		String strLeftDistance, int ugcBufferRadiusUnit,
            int semicircleLineSegment, int ugcBufferSideType, int ugcBufferEndType, 
            boolean isUnion, boolean isAttributeRetained);
    
    public native static boolean jni_CreateDatasetMultiBuffer(
    		long sourceDatasetHandle, long resultDatasetHandle,     		
            double[] aryRadius, int ugcBufferRadiusUnit,
            int semicircleLineSegment, boolean isUnion, boolean isAttributeRetained, boolean isRing);
    
    public native static boolean jni_CreateRecordsetMultiBuffer(
    		long sourceRecordsetHandle, long resultDatasetHandle, 
    		double[] aryRadius, int ugcBufferRadiusUnit,
            int semicircleLineSegment, boolean isUnion, boolean isAttributeRetained, boolean isRing);
    
    public native static boolean jni_CreateDatasetLineOneSideMultiBuffer(
    		long sourceDatasetHandle, long resultDatasetHandle,     		
            double[] aryRadius, int ugcBufferRadiusUnit,
            int semicircleLineSegment, boolean isLeft, boolean isUnion, boolean isAttributeRetained, boolean isRing);
    
    public native static boolean jni_CreateRecordsetLineOneSideMultiBuffer(
    		long sourceRecordsetHandle, long resultDatasetHandle,     		
            double[] aryRadius, int ugcBufferRadiusUnit,
            int semicircleLineSegment, boolean isLeft, boolean isUnion, boolean isAttributeRetained, boolean isRing);  
    
}
