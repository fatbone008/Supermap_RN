/**
 * 
 */
package com.supermap.analyst;

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
 * @author konglingliang
 * @version 6.0
 */
class BufferAnalystGeometryNative {
	private BufferAnalystGeometryNative() {
	}

	public native static long jni_CreateGeometryBuffer(long geometryHandle, long ugPrjHandle,
			double leftDistance, int ugcBufferRadiusUnit, int semicircleLineSegment,
			int ugcBufferSideType, int ugcBufferEndType);
	
	public native static long jni_CreateLineBuffer(long geometryHandle,long ugPrjHandle,
	   		double leftDistance, double rightDistance, int ugcBufferRadiusUnit,
	        int semicircleLineSegment, int ugcBufferSideType, int ugcBufferEndType);
	   
}
