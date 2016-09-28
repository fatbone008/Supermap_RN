package com.supermap.data;

class ScenesNative {
	private ScenesNative() {
		
	}
	
	public native static String jni_GetItem(long instance, int index);
	
	public native static int jni_GetCount(long instance);
	
	public native static int jni_Add(long instance, String name, String xml);
	
	public native static boolean jni_Insert(long instance, int index, String name, String xml);
	
	public native static boolean jni_SetSceneXML(long instance, int index, String xml);
	
	public native static String jni_GetSceneXML(long instance, int index);
	
	public native static boolean jni_Remove(long instance, int index);
	
	public native static void jni_Clear(long instance);
	
	public native static int jni_IndexOf(long instance, String name);
	
	public native static String jni_GetAvailableSceneName(long instance, String name);
	
	public native static boolean jni_Rename(long instance, String oldName, String newName);
}
