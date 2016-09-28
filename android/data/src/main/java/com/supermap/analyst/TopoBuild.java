package com.supermap.analyst;

import com.supermap.data.*;

/**
 * <p>
 * Title:拓扑分析类
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
	 * @mark 线数据集拓扑生成面数据集
	 * @param Dataset  原始线数据集
	 * @param Dataset  拓扑生成的面数据集
	 * @return boolean 生成是否成功
	 */
	public static boolean topoBuildRegion(Dataset datasetLine, Dataset datasetRegion){
		boolean bTopoBuild = false;
		
		//无效参数
		if (datasetLine == null || com.supermap.data.InternalHandle.getHandle(datasetLine) == 0) {
			return false;
		}
		if(datasetRegion == null || com.supermap.data.InternalHandle.getHandle(datasetRegion) == 0){
			return false;
		}
		
		//转换成DatasetVector
		DatasetVector datasetVectorLine = (DatasetVector)datasetLine;
		DatasetVector datasetVectorRegion = (DatasetVector)datasetRegion;
		
		//线数据集拓扑生成面数据集
		bTopoBuild = TopoBuildNative.jni_TopoBuildRegion(datasetVectorLine.getHandle(), datasetVectorRegion.getHandle());
		
		return bTopoBuild;
	}
	

}
