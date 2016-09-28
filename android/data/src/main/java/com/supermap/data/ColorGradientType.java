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
public class ColorGradientType extends Enum {
    private ColorGradientType(int value, int ugcValue) {
        super(value, ugcValue);
    }

    public static final ColorGradientType BLACKWHITE = new ColorGradientType(0, 0); //��->��
    public static final ColorGradientType REDWHITE = new ColorGradientType(1, 1); //��->��
    public static final ColorGradientType GREENWHITE = new ColorGradientType(2, 2); //��->��
    public static final ColorGradientType BLUEWHITE = new ColorGradientType(3, 3); //��->��
    public static final ColorGradientType YELLOWWHITE = new ColorGradientType(4, 4); //��->��
    public static final ColorGradientType PINKWHITE = new ColorGradientType(5, 5); //�ۺ�->��
    public static final ColorGradientType CYANWHITE = new ColorGradientType(6, 6); //��->��
    public static final ColorGradientType REDBLACK = new ColorGradientType(7, 7); //��->��
    public static final ColorGradientType GREENBLACK = new ColorGradientType(8, 8); //��->��
    public static final ColorGradientType BLUEBLACK = new ColorGradientType(9, 9); //��->��
    public static final ColorGradientType YELLOWBLACK = new ColorGradientType(10, 10); //��->��
    public static final ColorGradientType PINKBLACK = new ColorGradientType(11, 11); //�ۺ�->��
    public static final ColorGradientType CYANBLACK = new ColorGradientType(12, 12); //��->��
    public static final ColorGradientType YELLOWRED = new ColorGradientType(13, 13); //��->��
    public static final ColorGradientType YELLOWGREEN = new ColorGradientType(14, 14); //��->��
    public static final ColorGradientType YELLOWBLUE = new ColorGradientType(15, 15); //��->��
    public static final ColorGradientType GREENBLUE = new ColorGradientType(16, 16); //��->��
    public static final ColorGradientType GREENRED = new ColorGradientType(17, 17); //��->��
    public static final ColorGradientType BLUERED = new ColorGradientType(18, 18); //��->��
    public static final ColorGradientType PINKRED = new ColorGradientType(19, 19); //��->��
    public static final ColorGradientType PINKBLUE = new ColorGradientType(20, 20); //��->��
    public static final ColorGradientType CYANBLUE = new ColorGradientType(21, 21); //��->��
    public static final ColorGradientType CYANGREEN = new ColorGradientType(22, 22); //��->��
    public static final ColorGradientType RAINBOW = new ColorGradientType(23, 23); //��->��
    public static final ColorGradientType GREENORANGEVIOLET = new ColorGradientType(24, 24); //��->�ۻ�->������
    public static final ColorGradientType TERRAIN = new ColorGradientType(25, 25); //���ν���,������ά��ʾЧ���Ϻ�
    public static final ColorGradientType SPECTRUM = new ColorGradientType(26, 26); //���׽���

}
