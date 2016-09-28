package com.supermap.data;

class SymbolGroupNative {
    private SymbolGroupNative(){
    	
    }
    public native static String jni_GetName(long instance);
    public native static int jni_GetCount(long instance);
    public native static int jni_IndexOf(long instance,int id);
    public native static boolean jni_moveTo(long instance,int index ,long handle);
    
    public native static void jni_GetSymbols(long instance,long [] handles);
    
    public native static boolean jni_RemoveByID(long instance,int id);
}
