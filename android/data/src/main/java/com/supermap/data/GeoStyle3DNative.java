package com.supermap.data;

class GeoStyle3DNative {
	private GeoStyle3DNative() {
	}

	public native static long jni_New();

	public native static void jni_Delete(long instance);

	public native static int jni_GetFillBackColor(long instance);

	public native static void jni_SetFillBackColor(long instance, int value);

	public native static int jni_GetFillForeColor(long instance);

	public native static void jni_SetFillForeColor(long instance, int value);

	public native static int jni_GetLineColor(long instance);

	public native static void jni_SetLineColor(long instance, int value);

	public native static double jni_GetLineWidth(long instance);

	public native static void jni_SetLineWidth(long instance, double value);

	public native static String jni_GetMarkerIconFile(long instance);

	public native static void jni_SetMarkerIconFile(long instance, String value);

	public native static double jni_GetMarkerIconScale(long instance);

	public native static void jni_SetMarkerIconScale(long instance, double value);

	public native static int jni_GetMarkerColor(long instance);

	public native static void jni_SetMarkerColor(long instance, double value);

	public native static int jni_GetFill3DMode(long instance);

	public native static void jni_SetFill3DMode(long instance, int vlaue);

	public native static double jni_GetMarkerSize(long instance);

	public native static void jni_SetMarkerSize(long instance, double value);

	public native static double jni_GetBottomAltitude(long instance);

	public native static void jni_SetBottomAltitude(long instance, double value);

	public native static double jni_GetExtendedHeight(long instance);

	public native static void jni_SetExtendedHeight(long instance, double value);

	public native static double jni_GetTilingU(long instance);

	public native static void jni_SetTilingU(long instance, double value);

	public native static double jni_GetTilingV(long instance);

	public native static void jni_SetTilingV(long instance, double value);

	public native static double jni_GetTopTilingU(long instance);

	public native static void jni_SetTopTilingU(long instance, double value);

	public native static double jni_GetTopTilingV(long instance);

	public native static void jni_SetTopTilingV(long instance, double value);
	
	public native static int jni_GetTextureRepeatMode(long instance);

	public native static void jni_SetTextureRepeatMode(long instance, int value);
	
	public native static int jni_GetTopTextureRepeatMode(long instance);

	public native static void jni_SetTopTextureRepeatMode(long instance, int value);

	public native static String jni_GetTopTextureFile(long instance);

	public native static void jni_SetTopTextureFile(long instance, String value);

	public native static int jni_GetAltitudeMode(long instance);

	public native static void jni_SetAltitudeMode(long instance, int value);

	public native static long jni_Clone(long instance);

	public native static void jni_Reset(long insance);

	// add by xuzw 2009-05-08
	public native static String jni_GetSideTextureFiles(long instance);

	public native static void jni_SetSideTextureFiles(long instance,
			String value);

	public native static boolean jni_FromXML(long instance, String xml);

	public native static String jni_ToXML(long instance);

	// ///////////////////////////////////////////////////////////////////////////

	public native static int jni_GetMarkerBillboardMode(long instance);

	public native static void jni_SetMarkerBillboardMode(long instance,
			int value);

	public native static boolean jni_GetIsMarkerSizeFixed(long instance);

	public native static void jni_SetMarkerSizeFixed(long instance,
			boolean value);

	public native static int jni_GetMarkerSymbolID(long instance);

	public native static void jni_SetMarkerSymbolID(long instance, int value);

	public native static int jni_GetLineSymbolID(long instance);

	public native static void jni_SetLineSymbolID(long instance, int value);

	public native static double jni_GetSymbolRotationX(long instance);

	public native static void jni_SetSymbolRotationX(long instance, double value);

	public native static double jni_GetSymbolRotationY(long instance);

	public native static void jni_SetSymbolRotationY(long instance, double value);

	public native static double jni_GetSymbolRotationZ(long instance);

	public native static void jni_SetSymbolRotationZ(long instance, double value);

	public native static double jni_GetSymbolScaleX(long instance);

	public native static void jni_SetSymbolScaleX(long instance, double value);

	public native static double jni_GetSymbolScaleY(long instance);

	public native static void jni_SetSymbolScaleY(long instance, double value);

	public native static double jni_GetSymbolScaleZ(long instance);

	public native static void jni_SetSymbolScaleZ(long instance, double value);

	public native static int jni_GetLineSymbolSegmentCount(long instance);

	public native static void jni_SetLineSymbolSegmentCount(long instance,
			int value);

	public native static int jni_GetTopFillSymbolID(long instance);

	public native static void jni_SetTopFillSymbolID(long instance, int value);

	public native static int jni_GetSymbolMarker3D(long instance);

	public native static void jni_SetSymbolMarker3D(long instance, long value);

	public native static int[] jni_GetSideFillSymbolIDs(long instance);

	public native static void jni_SetSideFillSymbolIDs(long instance,
			int[] value);

	public native static void jni_GetMarkerAnchorPoint(long instance,
			double[] values);

	public native static void jni_SetMarkerAnchorPoint(long instance, double x,
			double y);

	public native static boolean jni_IsMarker3D(long handle);

	public native static void jni_SetMarker3D(long handle, boolean value);
	
	public native static int jni_GetTubeSides(long handle);
	
	public native static void jni_SetTubeSides(long handle, int value);
	
	public native static boolean jni_IsFiletEnabled(long handle);
	
	public native static void jni_SetFiletEnabled(long handle, boolean value);
	
	public native static boolean jni_IsTessellated(long handle);
	
	public native static void jni_SetTessellated(long handle, boolean value);
	
	
	
}
