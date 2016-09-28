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
 * @author 李云锦
 * @version 2.0
 */
public class Charset extends Enum {
    private Charset(int value, int ugcValue) {
        super(value, ugcValue);
    }

    public static final Charset ANSI = new Charset(0,0);
    public static final Charset  DEFAULT = new Charset(1,1);
    public static final Charset SYMBOL = new Charset(2,2);
    public static final Charset MAC = new Charset(77,77);
    public static final Charset SHIFTJIS = new Charset(128,128);
    public static final Charset  HANGEUL  = new Charset(129,129);
    public static final Charset JOHAB  = new Charset(130,130);
    public static final Charset GB18030 = new Charset(134,134);
    public static final Charset CHINESEBIG5 = new Charset(136,136);
    public static final Charset GREEK  = new Charset(161,161);
    public static final Charset TURKISH  = new Charset(162,162);
    public static final Charset VIETNAMESE  = new Charset(163,163);
    public static final Charset HEBREW  = new Charset(177,177);
    public static final Charset ARABIC  = new Charset(178,178);
    public static final Charset BALTIC  = new Charset(186,186);
    public static final Charset RUSSIAN  = new Charset(204,204);
    public static final Charset  THAI = new Charset(222,222);
    public static final Charset EASTEUROPE  = new Charset(238,238);
    public static final Charset OEM = new Charset(255,255);
    
    //add by xuzw 2009-02-09 6.0新增
    // modify by xuzw 2010-5-24 UTF8类库对应值为250
    public static final Charset UTF8 = new Charset(250,250); //UGC将UTF8注释了
    public static final Charset UTF7 = new Charset(7, 7);
    public static final Charset UTF32 = new Charset(8, 8);
    public static final Charset WINDOWS1252 = new Charset(137, 137);
    public static final Charset KOREAN = new Charset(131, 131);
    public static final Charset UNICODE = new Charset(132, 132);
    public static final Charset CYRILLIC = new Charset(135, 135);
    public static final Charset XIA5 = new Charset(3, 3);
    public static final Charset XIA5GERMAN = new Charset(4, 4);
    public static final Charset XIA5SWEDISH = new Charset(5, 5);
    public static final Charset XIA5NORWEGIAN = new Charset(6, 6);
   
}
