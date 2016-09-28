package com.supermap.data;

class SymbolGroupsNative {
     private SymbolGroupsNative(){
    	 
     }
     public native static int jni_IndexOf(long handle ,String value);
     public native static long jni_Create(long handle ,String value);
     public native static int jni_GetCount(long instance);
     public native static void jni_GetSymbolGroups(long insatnce , long []handles);
     public native static boolean jni_Remove(long instance , int index);
}
