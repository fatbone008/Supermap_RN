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
class DatasetNative{
        public native static void jni_GetBounds(long instance,double[] params);
        
        public native static boolean jni_GetIsReadOnly(long instance);
        
        public native static boolean jni_GetIsOpen(long instance);
        
        public native static long jni_GetDatasource(long instance);
        
        public native static String jni_GetDescription(long instance);
        
        public native static void jni_SetDescription(long instance,String value);
        
        public native static String jni_GetName(long instance);
        
        public native static int jni_GetType(long instance);
        
        public native static int jni_GetEncodeType(long instance);
        
        public native static void jni_Close(long instance);
        
        public native static boolean jni_Open(long instance);
        
        public native static void jni_SetReadOnly(long instance,boolean readOnly);
        
        public native static boolean jni_GetIsVector(long instance);
        
        //ÐÂÔö 
        public native static boolean jni_setPrjCoordSys(long instance,long prjHandle);
        
        public native static void jni_SetExtInfo(long instance,String info);
        
        public native static String jni_GetExtInfo(long instance);
        
        public native static long jni_getPrjCoordSys(long instance);
        
        public native static boolean jni_hasVersion(long instance);
        //Added by HELH
        public native static void jni_UnSetPrjCoordSys(long instance);
        
        public native static long jni_NewSelfEventHandle(Dataset dataset);
  }
