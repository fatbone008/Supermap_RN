/**
 * 
 */
package com.supermap.data;

/**
 * <p>Title:</p>
 * 
 * <p>Description:</p>
 * 
 * <p>Copyright: Copyright (c) 2007</p>
 * 
 * <p> Company: SuperMap GIS Technologies Inc.</p>
 * 
 * @author konglingliang
 * @version 6.0
 */
public class ColorSpaceType extends Enum {
    private ColorSpaceType(int value, int ugcValue) {
        super(value, ugcValue);
    }
    
  //��ʾϵͳʹ��
    public static final ColorSpaceType UNKNOW = new ColorSpaceType(0, 0);
    
    //��ʾϵͳʹ��
    public static final ColorSpaceType RGB = new ColorSpaceType(1, 1);
    
    //ӡˢϵͳʹ��(Cyan,Magenta,Yellow,Black)
    public static final ColorSpaceType CMYK = new ColorSpaceType(4, 4);
    //��ʾϵͳʹ�ã����е�A���ڿ���͸����
    public static final ColorSpaceType RGBA = new ColorSpaceType(2, 2);
    //ӡˢϵͳʹ��(Cyan,Magenta,Yellow)
    public static final ColorSpaceType CMY = new ColorSpaceType(3, 3);
    //��������ϵͳʹ��(NTSC,Y:Luminance,Chrominance)
    public static final ColorSpaceType YIQ = new ColorSpaceType(5, 5);
    //ŷ�޵���ϵͳʹ��(PAL)
    public static final ColorSpaceType YUV = new ColorSpaceType(6, 6);
    //��YUV����,JPEGʹ��
    public static final ColorSpaceType YCC = new ColorSpaceType(7, 7);
}
