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
 * @陈勇
 * @version 2.0
 */
public final class FieldType extends Enum{
    private FieldType(int value,int ugcValue){
        super(value,ugcValue);
    }
    public static final FieldType BOOLEAN = new FieldType(1,1);
    public static final FieldType BYTE = new FieldType(2,2);
    public static final FieldType INT16 = new FieldType(3,3);
    public static final FieldType INT32 = new FieldType(4,4);
    public static final FieldType INT64 = new FieldType(16,16);//added by zengwh 2010-9-17
    
    //modified by xuzw 2009-02-09 根据详细设计更新删除Currency和Decimal
    //public static final FieldType CURRENCY = new FieldType(5,5);
    
    public static final FieldType SINGLE = new FieldType(6,6);
    public static final FieldType DOUBLE = new FieldType(7,7);
    //modified by 孔令亮 at 2008年3月12日 上午09时18分31秒
    //reason:在UGC中将Date 、Time、TimeStamp都作为TimeStamp处理。
    public static final FieldType DATETIME = new FieldType(23,23);
    public static final FieldType LONGBINARY = new FieldType(11,11);
    public static final FieldType TEXT = new FieldType(10,10);
    
    
    //modified by xuzw 2009-02-10 根据详细设计更新删除Int64和NVarchar，新增Char（表示定长文本）
    //public static final FieldType INT64 = new FieldType(16,16);
    public static final FieldType CHAR = new FieldType(18, 18);
    
//  modified by xuzw 2009-04-02 根据详细设计更新新增WText（表示宽字符）
    public static final FieldType WTEXT = new FieldType(127, 127);
    
    //public static final FieldType CHAR = new FieldType(18,18);
    //public static final FieldType DECIMAL = new FieldType(20,20);
    // @modified by 孔令亮 at 2008年1月4日 上午11时21分15秒
    // @reason:NVARCHAR对应UGC中的127
//    public static final FieldType NVARCHAR = new FieldType(130,130);
    //public static final FieldType NVARCHAR = new FieldType(127,127);
}
