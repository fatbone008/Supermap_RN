package com.supermap.data;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ���ƽ�
 * @version 2.0
 */
class FieldInfoNative {
    private FieldInfoNative() {
    }

    public native static long jni_New();

    public native static void jni_Delete(long instance);

    public native static boolean jni_GetIsZeroLengthAllowed(long instance);

    public native static void jni_SetIsZeroLengthAllowed(long instance, boolean value);

    public native static String jni_GetCaption(long instance,long dtHandle);

    /**
     *
     * @param instance long
     * @param value String
     * @param isDtField boolean �Ƿ�Ϊ���ݼ����ֶ�
     */
    public native static void jni_SetCaption(long instance, String value,long dtHandle);

    public native static String jni_GetDefaultValue(long instance);

    public native static void jni_SetDefaultValue(long instance, String value);

    public native static String jni_GetName(long instance);

    public native static void jni_SetName(long instance, String value);

    public native static boolean jni_GetIsRequired(long instance);

    public native static void jni_SetIsRequired(long instance, boolean value);

    public native static int jni_GetMaxLength(long instance);

    public native static void jni_SetMaxLength(long instance, int value);

    public native static int jni_GetType(long instance);

    public native static void jni_SetType(long instance, int value);

    public native static boolean jni_GetIsSystemField(long instance);
    
    /**
     * �ж��Ƿ�Ϊ��Ч���ֶ���
     * @param name String
     * @return int
     */
    public native static int jni_IsValidFieldName(String name);

    /**
     * �ж��Ƿ�Ϊ��Ч�ı���
     * ����������sm��ͷ����������ͬ�ֶε���������
     * @param name String
     * @return int
     */
    public native static int jni_IsValidTableName(String name);

    public native static void jni_Reset(long instance);
}
