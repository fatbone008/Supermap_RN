package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description:游标类型常量 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 孔令亮
 * @version 2.0
 */
public final class CursorType extends Enum {
    private CursorType(int value, int ugcValue) {
        super(value, ugcValue);
    }

    // @modified by 李云锦 at 2007年7月23日 下午01时53分04秒
    // @reason  与文档同步 删除部分枚举值

    //动态游标
    public static final CursorType DYNAMIC = new CursorType(2, 2);

    //静态游标
    public static final CursorType STATIC = new CursorType(3, 3);
}
