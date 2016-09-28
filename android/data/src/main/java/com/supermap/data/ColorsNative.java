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
 * @author ���ƽ�
 * @version 2.0
 */
class ColorsNative {
    private ColorsNative() {
    }


    public native static void jni_Set(long instance, int index, int value);

    public native static int jni_Add(long instance, int color);

    public native static boolean jni_Insert(long instance, int index, int color);

    public native static boolean jni_Remove(long instance, int index);

    public native static long jni_MakeRandom(int count);

    public native static long jni_MakeGradient1(int count, int[] intervalColors);

    public native static long jni_MakeGradient2(int count, int type, boolean reverse);

    public native static long jni_New();

    public native static void jni_Delete(long instance);

    // @modified by ������ at 2007��8��23�� ����02ʱ33��54��
    // @reason: �����شӵײ�ȥֵ�����ݡ�
    public native static int jni_GetCount(long instance);

    public native static int jni_GetItem(long instance ,int index);

    public native static void jni_Clear(long instance);

    public native static int jni_AddRange(long instance,int[] colorsRGb);
    
    // @added by zhengyl at 2011-2-21
    // @reason: ����ͨ������ɫ�ͼ�����������ɫ�Ĺ��ܡ�
   public native static long jni_MakeRandom1(int count, int[] intervalColors);
}
