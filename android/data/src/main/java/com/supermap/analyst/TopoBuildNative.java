package com.supermap.analyst;

class TopoBuildNative {
	private TopoBuildNative() {
	}

	//线数据集拓扑生成面数据集
	public static native boolean jni_TopoBuildRegion(long datasetLineHandle, long datasetRegionHande);
	
}
