package com.supermap.analyst;

import com.supermap.analyst.InternalToolkit.BufferSideType;
import com.supermap.data.*;

public class BufferAnalystGeometry {

	private BufferAnalystGeometry() {
	}

	/**
	 * 根据几何对象创建缓冲区，成功返回一个面对象，失败则返回空值
	 * 
	 * @param geometry
	 *            Geometry 几何对象
	 * @param bufferAnalystParameter
	 *            BufferAnalystParameter 缓冲分析参数对象
	 * @return GeoRegion
	 */
	public static GeoRegion createBuffer(Geometry geometry, 
									     BufferAnalystParameter bufferAnalystParameter,
									     PrjCoordSys prjCoordSys) {
		//将参数类解析
		BufferEndType endType = bufferAnalystParameter.getEndType();
		int semicircleLineSegment = bufferAnalystParameter.getSemicircleLineSegment();	
		Object objLeftDistance = bufferAnalystParameter.getLeftDistance();
		Object objRightDistance = bufferAnalystParameter.getRightDistance();
		BufferRadiusUnit radiusUnit = bufferAnalystParameter.getRadiusUnit();
//		double leftDistance = objectToDouble(objLeftDistance);
//		double rightDistance = objectToDouble(objRightDistance);

		long ugPrjHandle = InternalHandle.getHandle(prjCoordSys);
		
		if (geometry == null) {
            String message = InternalResource.loadString("geometry",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new NullPointerException(message);
        }	
		long geometryHandle = InternalHandle.getHandle(geometry);
        if (geometryHandle == 0) {
            String message = InternalResource.loadString("geometry",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        int ugcBufferSideType = -1;
        
        GeoRegion geoRegion = null;
        
        GeometryType geoType = geometry.getType();
        
		//先判断端类型是平头还是圆头
		if (endType.equals(BufferEndType.FLAT)) {
			
			//平头缓冲左右缓冲距离同时为null,抛异常
			if(objLeftDistance == null && objRightDistance == null) {
	            String message = InternalResource.loadString(
	            		"objLeftDistance and objRightDistance",
	                    InternalResource.GlobalArgumentNull,
	                    InternalResource.BundleName);
	            throw new NullPointerException(message);
			}
	        //平头缓冲，则必须是线对象，否则抛出异常
			if (geoType != GeometryType.GEOLINE) {
	            String message = InternalResource.loadString("geometry",
	                    InternalResource.BufferAnalystBufferGeometryTypeShouldBeLine,
	                    InternalResource.BundleName);
	            throw new IllegalArgumentException(message);
	        }

			
			//根据几何对象创建缓冲区时，缓冲距离是字符串输入类型则无效，线缓冲判断两个
			if ((objLeftDistance instanceof String) || 
					(objRightDistance instanceof String)) {
	            String message = InternalResource.loadString(
	            		"LeftDistance and RightDistance",
	                    InternalResource.BufferAnalystDistenceOfStringTypeIsInvalidForGeometryBuffer,
	                    InternalResource.BundleName);
	            throw new IllegalArgumentException(message);
			}
			double leftDistance = InternalToolkit.objectToDouble(objLeftDistance);
			double rightDistance = InternalToolkit.objectToDouble(objRightDistance);
			//缓冲距离必须是正数
			if(objLeftDistance != null && leftDistance <= 0) {
	            String message = InternalResource.loadString("leftDistance",
	            		InternalResource.BufferAnalystBufferDistanceShouldBePositive,
	            		InternalResource.BundleName);
	            throw new IllegalArgumentException(message);
			}
			if(objRightDistance != null && rightDistance <= 0) {
	            String message = InternalResource.loadString("rightDistance",
	            		InternalResource.BufferAnalystBufferDistanceShouldBePositive,
	            		InternalResource.BundleName);
	            throw new IllegalArgumentException(message);
			}
	        
			//确定BufferSideType
			if (objLeftDistance == null) {
				ugcBufferSideType = BufferSideType.RIGHT.value();
			}
			else if (objRightDistance == null) {
				ugcBufferSideType = BufferSideType.LEFT.value();
			}
			else if (leftDistance == rightDistance) {
				ugcBufferSideType = BufferSideType.FULL.value();
			}
			else if (leftDistance != rightDistance) {
				ugcBufferSideType = BufferSideType.FULLDIFFR.value();
			} 

	        if (geometry.isEmpty()) {
	            return new GeoRegion();
	        }
	        
	        int ugcBufferEndType = BufferEndType.FLAT.value();
	        
	        //{{by chengxi 2011年7月15日
//	        long geoRegionPtr = BufferAnalystNative.jni_CreateLineBuffer(
//	                geometryHandle, leftDistance, rightDistance,
//	                semicircleLineSegment, ugcBufferSideType, ugcBufferEndType);
	        long geoRegionPtr = BufferAnalystGeometryNative.jni_CreateLineBuffer(
	        		geometryHandle, ugPrjHandle, leftDistance, rightDistance, radiusUnit.value(),
	        		semicircleLineSegment, ugcBufferSideType, ugcBufferEndType);
	        //}}
	        
	        if (geoRegionPtr != 0) {
	            geoRegion = (GeoRegion) InternalGeometry.createInstance(
	                    geoRegionPtr);
	        } else {
	        	geoRegion = new GeoRegion();
	        }
		}
		
		//圆头缓冲就存在三种情况:点、线、面
		if(endType.equals(BufferEndType.ROUND)) {
			if(geoType != GeometryType.GEOPOINT &&
			   geoType != GeometryType.GEOLINE &&
			   geoType != GeometryType.GEOREGION) {
	            String message = InternalResource.loadString("geometry",
	                    InternalResource.BufferAnalystBufferGeometryTypeInvalid,
	                    InternalResource.BundleName);
	            throw new IllegalArgumentException(message);
			}
			//对输入形式的处理同上，但是不管右缓冲距离
			if (objLeftDistance instanceof String) {
	            String message = InternalResource.loadString("LeftDistance",
	                    InternalResource.BufferAnalystDistenceOfStringTypeIsInvalidForGeometryBuffer,
	                    InternalResource.BundleName);
	            throw new IllegalArgumentException(message);
			}
			
			double leftDistance = InternalToolkit.objectToDouble(objLeftDistance);
			
			//圆头缓冲，只要左缓冲是null，就抛异常
			if (objLeftDistance == null) {
	            String message = InternalResource.loadString(
	            		"objLeftDistance",
	                    InternalResource.GlobalArgumentNull,
	                    InternalResource.BundleName);
	            throw new NullPointerException(message);
			}
			
			//不是面的圆头缓冲，缓冲距离必须是正数；是面的圆头缓冲，缓冲距离不为0
			if(!geoType.equals(GeometryType.GEOREGION) && leftDistance <= 0) {
	            String message = InternalResource.loadString("",
	                    InternalResource.BufferAnalystBufferDistanceShouldBePositive,
	                    InternalResource.BundleName);
	            throw new IllegalArgumentException(message);
			} else if (geoType.equals(GeometryType.GEOREGION) && Toolkit.isZero(leftDistance)) {
	            String message = InternalResource.loadString("",
	                    InternalResource.BufferAnalystBufferDistanceShouldNotBeZero,
	                    InternalResource.BundleName);
	            throw new IllegalArgumentException(message);
			}
			
			//圆头线缓冲，认为BufferSideType为FULL，而圆头点面缓冲认为BufferSideType为NONESIDE
			if(geoType.equals(GeometryType.GEOLINE)) {
				ugcBufferSideType = BufferSideType.FULL.value();
			}
			
	        if (geometry.isEmpty()) {
	            return new GeoRegion();
	        }
	        
	        int ugcBufferEndType = BufferEndType.ROUND.value();
	        
	        long geoRegionPtr = BufferAnalystGeometryNative.jni_CreateGeometryBuffer(
	                geometryHandle, ugPrjHandle, leftDistance, radiusUnit.value(), semicircleLineSegment,
	                ugcBufferSideType, ugcBufferEndType);
	        if (geoRegionPtr != 0) {
	            geoRegion = (GeoRegion) InternalGeometry.createInstance(
	                    geoRegionPtr);
	        } else {
	        	geoRegion = new GeoRegion();
	        }
		}
		return geoRegion;
	}


}
