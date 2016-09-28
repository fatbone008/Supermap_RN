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
 * @����
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
    
    //modified by xuzw 2009-02-09 ������ϸ��Ƹ���ɾ��Currency��Decimal
    //public static final FieldType CURRENCY = new FieldType(5,5);
    
    public static final FieldType SINGLE = new FieldType(6,6);
    public static final FieldType DOUBLE = new FieldType(7,7);
    //modified by ������ at 2008��3��12�� ����09ʱ18��31��
    //reason:��UGC�н�Date ��Time��TimeStamp����ΪTimeStamp����
    public static final FieldType DATETIME = new FieldType(23,23);
    public static final FieldType LONGBINARY = new FieldType(11,11);
    public static final FieldType TEXT = new FieldType(10,10);
    
    
    //modified by xuzw 2009-02-10 ������ϸ��Ƹ���ɾ��Int64��NVarchar������Char����ʾ�����ı���
    //public static final FieldType INT64 = new FieldType(16,16);
    public static final FieldType CHAR = new FieldType(18, 18);
    
//  modified by xuzw 2009-04-02 ������ϸ��Ƹ�������WText����ʾ���ַ���
    public static final FieldType WTEXT = new FieldType(127, 127);
    
    //public static final FieldType CHAR = new FieldType(18,18);
    //public static final FieldType DECIMAL = new FieldType(20,20);
    // @modified by ������ at 2008��1��4�� ����11ʱ21��15��
    // @reason:NVARCHAR��ӦUGC�е�127
//    public static final FieldType NVARCHAR = new FieldType(130,130);
    //public static final FieldType NVARCHAR = new FieldType(127,127);
}
