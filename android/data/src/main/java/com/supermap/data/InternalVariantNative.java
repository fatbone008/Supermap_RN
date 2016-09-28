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
 * @author ¿Ó‘∆Ωı
 * @version 2.0
 */
class InternalVariantNative {
    private InternalVariantNative() {
    }

    public native static long jni_New();

    public native static void jni_Delete(long instance);

    public native static void jni_SetValueByte(long instance, byte value);

    public native static void jni_SetValueInt(long instance, int value);

    public native static void jni_SetValueLong(long instance, long value);

    public native static void jni_SetValueShort(long instance, short value);

    public native static void jni_SetValueBoolean(long instance, boolean value);

    public native static void jni_SetValueFloat(long instance, float value);

    public native static void jni_SetValueDouble(long instance, double value);

    public native static void jni_SetValueString(long instance, String value);

    public native static byte jni_GetValueByte(long instance);

    public native static int jni_GetValueInt(long instance);

    public native static long jni_GetValueLong(long instance);

    public native static short jni_GetValueShort(long instance);

    public native static boolean jni_GetValueBoolean(long instance);

    public native static float jni_GetValueFloat(long instance);

    public native static double jni_GetValueDouble(long instance);

    public native static String jni_GetValueString(long instance);

    public native static int jni_GetType(long instance);

    public native static void jni_SetValueBinary(long instance, byte[] value);

    public native static byte[] jni_GetValueBinary(long instance);

    public native static void jni_SetValueTime(long instance, int year,
                                               int month, int day, int hours,
                                               int minutes, int seconds);
}
