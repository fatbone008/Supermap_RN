package com.supermap.analyst;

class TopoBuildNative {
	private TopoBuildNative() {
	}

	//�����ݼ��������������ݼ�
	public static native boolean jni_TopoBuildRegion(long datasetLineHandle, long datasetRegionHande);
	
}
