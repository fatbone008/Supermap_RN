package com.supermap.data;

import com.supermap.data.Enum;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author not attributable
 * @version 2.0
 */
class InternalVariantType extends Enum {

    private InternalVariantType(int value, int ugcValue) {
        super(value, ugcValue);
    }

    public static final InternalVariantType NULL = new InternalVariantType(0,0); //! 未定义的类型
    public static final InternalVariantType BYTE = new InternalVariantType(1,1); //! 单字节类型

    public static final InternalVariantType SHORT = new InternalVariantType(2,2); //! 双字节整型类型

    public static final InternalVariantType INTEGER = new InternalVariantType(3,3); //! 四字节整型类型

    public static final InternalVariantType LONG = new InternalVariantType(4,4); //! 八字节整型类型

    public static final InternalVariantType FLOAT = new InternalVariantType(5,5); //! 四字节浮点类型

    public static final InternalVariantType DOUBLE = new InternalVariantType(6,6); //! 八字节浮点类型

    public static final InternalVariantType TIME = new InternalVariantType(7,7); //! 时间类型

    public static final InternalVariantType BINARY = new InternalVariantType(8,8); //! 二进制类型

    public static final InternalVariantType STRING = new InternalVariantType(9,9); //! 字符串

    public static final InternalVariantType DATE = new InternalVariantType(10,10); //! 日期类型
    
    public static final InternalVariantType TIMESTAMP = new InternalVariantType(11,11); //! 时间戳类型
    
    public static final InternalVariantType BOOLEAN = new InternalVariantType(12,12); //! 布尔类型
}
