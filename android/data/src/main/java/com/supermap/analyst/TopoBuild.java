package com.supermap.analyst;

import com.supermap.data.*;

/**
 * <p>
 * Title:���˷�����
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
 * @author huangkj
 * @version 1.0
 */
public class TopoBuild {

	private TopoBuild() {

	}

	
	/**
	 * @author huangkj
	 * @mark �����ݼ��������������ݼ�
	 * @param Dataset  ԭʼ�����ݼ�
	 * @param Dataset  �������ɵ������ݼ�
	 * @return boolean �����Ƿ�ɹ�
	 */
	public static boolean topoBuildRegion(Dataset datasetLine, Dataset datasetRegion){
		boolean bTopoBuild = false;
		
		//��Ч����
		if (datasetLine == null || com.supermap.data.InternalHandle.getHandle(datasetLine) == 0) {
			return false;
		}
		if(datasetRegion == null || com.supermap.data.InternalHandle.getHandle(datasetRegion) == 0){
			return false;
		}
		
		//ת����DatasetVector
		DatasetVector datasetVectorLine = (DatasetVector)datasetLine;
		DatasetVector datasetVectorRegion = (DatasetVector)datasetRegion;
		
		//�����ݼ��������������ݼ�
		bTopoBuild = TopoBuildNative.jni_TopoBuildRegion(datasetVectorLine.getHandle(), datasetVectorRegion.getHandle());
		
		return bTopoBuild;
	}
	

}
