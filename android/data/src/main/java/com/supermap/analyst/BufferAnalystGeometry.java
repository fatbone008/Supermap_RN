package com.supermap.analyst;

import com.supermap.analyst.InternalToolkit.BufferSideType;
import com.supermap.data.*;

public class BufferAnalystGeometry {

	private BufferAnalystGeometry() {
	}

	/**
	 * ���ݼ��ζ��󴴽����������ɹ�����һ�������ʧ���򷵻ؿ�ֵ
	 * 
	 * @param geometry
	 *            Geometry ���ζ���
	 * @param bufferAnalystParameter
	 *            BufferAnalystParameter ���������������
	 * @return GeoRegion
	 */
	public static GeoRegion createBuffer(Geometry geometry, 
									     BufferAnalystParameter bufferAnalystParameter,
									     PrjCoordSys prjCoordSys) {
		//�����������
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
        
		//���ж϶�������ƽͷ����Բͷ
		if (endType.equals(BufferEndType.FLAT)) {
			
			//ƽͷ�������һ������ͬʱΪnull,���쳣
			if(objLeftDistance == null && objRightDistance == null) {
	            String message = InternalResource.loadString(
	            		"objLeftDistance and objRightDistance",
	                    InternalResource.GlobalArgumentNull,
	                    InternalResource.BundleName);
	            throw new NullPointerException(message);
			}
	        //ƽͷ���壬��������߶��󣬷����׳��쳣
			if (geoType != GeometryType.GEOLINE) {
	            String message = InternalResource.loadString("geometry",
	                    InternalResource.BufferAnalystBufferGeometryTypeShouldBeLine,
	                    InternalResource.BundleName);
	            throw new IllegalArgumentException(message);
	        }

			
			//���ݼ��ζ��󴴽�������ʱ������������ַ���������������Ч���߻����ж�����
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
			//����������������
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
	        
			//ȷ��BufferSideType
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
	        
	        //{{by chengxi 2011��7��15��
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
		
		//Բͷ����ʹ����������:�㡢�ߡ���
		if(endType.equals(BufferEndType.ROUND)) {
			if(geoType != GeometryType.GEOPOINT &&
			   geoType != GeometryType.GEOLINE &&
			   geoType != GeometryType.GEOREGION) {
	            String message = InternalResource.loadString("geometry",
	                    InternalResource.BufferAnalystBufferGeometryTypeInvalid,
	                    InternalResource.BundleName);
	            throw new IllegalArgumentException(message);
			}
			//��������ʽ�Ĵ���ͬ�ϣ����ǲ����һ������
			if (objLeftDistance instanceof String) {
	            String message = InternalResource.loadString("LeftDistance",
	                    InternalResource.BufferAnalystDistenceOfStringTypeIsInvalidForGeometryBuffer,
	                    InternalResource.BundleName);
	            throw new IllegalArgumentException(message);
			}
			
			double leftDistance = InternalToolkit.objectToDouble(objLeftDistance);
			
			//Բͷ���壬ֻҪ�󻺳���null�������쳣
			if (objLeftDistance == null) {
	            String message = InternalResource.loadString(
	            		"objLeftDistance",
	                    InternalResource.GlobalArgumentNull,
	                    InternalResource.BundleName);
	            throw new NullPointerException(message);
			}
			
			//�������Բͷ���壬�����������������������Բͷ���壬������벻Ϊ0
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
			
			//Բͷ�߻��壬��ΪBufferSideTypeΪFULL����Բͷ���滺����ΪBufferSideTypeΪNONESIDE
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
