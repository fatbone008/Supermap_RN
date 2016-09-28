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
public class PixelFormat extends Enum {
    private PixelFormat(int value, int ugcValue) {
        super(value, ugcValue);
    }

    public static final PixelFormat UBIT1 = new PixelFormat(1,1);
    public static final PixelFormat UBIT4 = new PixelFormat(4,4);
    public static final PixelFormat UBIT8 = new PixelFormat(8,8);
    public static final PixelFormat BIT16 = new PixelFormat(16,16);
    public static final PixelFormat UBIT24 = new PixelFormat(24,24);
    public static final PixelFormat BIT32 = new PixelFormat(320,320);
    public static final PixelFormat UBIT32 = new PixelFormat(32,32);
//    public static final PixelFormat BIT48 = new PixelFormat(48,48);
    public static final PixelFormat BIT64 = new PixelFormat(64,64);
    public static final PixelFormat SINGLE = new PixelFormat(3200,3200);

    // @modified by not attributable at 2007��12��14�� ����04ʱ41��20��
    // @reason:��UGC��double��Ӧ��ֵӦΪ��6400
    public static final PixelFormat DOUBLE = new PixelFormat(6400,6400);

}
