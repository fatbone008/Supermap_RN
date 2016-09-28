package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author not attributable
 * @version 2.0
 */
class DatasetsNative {

    public native static long jni_CreateDatasetVector(long instance, long infoHandle);

    public native static long jni_CreateDatasetRaster(long instance, long infoHandle,boolean isMultiBand);

    public native static long jni_CreateDatasetFromTemplate(long instance, String datasetName, long templateDataset);

    public native static boolean jni_DeleteDataset(long instance, int index);
    
    public native static boolean jni_DeleteDataset2(long instance, String datasetName);
    
    public native static String jni_GetUnoccupiedDatasetName(long instance, String datasetName);

    public native static boolean jni_IsAvailableDatasetName(long instatnce, String name);

    public native static int jni_IndexOf(long instance, String name);
}
