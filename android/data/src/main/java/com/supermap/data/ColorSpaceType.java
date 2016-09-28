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
    
  //显示系统使用
    public static final ColorSpaceType UNKNOW = new ColorSpaceType(0, 0);
    
    //显示系统使用
    public static final ColorSpaceType RGB = new ColorSpaceType(1, 1);
    
    //印刷系统使用(Cyan,Magenta,Yellow,Black)
    public static final ColorSpaceType CMYK = new ColorSpaceType(4, 4);
    //显示系统使用，其中的A用于控制透明度
    public static final ColorSpaceType RGBA = new ColorSpaceType(2, 2);
    //印刷系统使用(Cyan,Magenta,Yellow)
    public static final ColorSpaceType CMY = new ColorSpaceType(3, 3);
    //北美电视系统使用(NTSC,Y:Luminance,Chrominance)
    public static final ColorSpaceType YIQ = new ColorSpaceType(5, 5);
    //欧洲电视系统使用(PAL)
    public static final ColorSpaceType YUV = new ColorSpaceType(6, 6);
    //与YUV类似,JPEG使用
    public static final ColorSpaceType YCC = new ColorSpaceType(7, 7);
}
