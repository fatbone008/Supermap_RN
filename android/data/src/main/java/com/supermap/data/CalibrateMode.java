/**
 * <p>Title: 路由（线路）对象的校正模式</p>
 * <p>Description: 用于选择路由（线路）对象的校正</p>
 <p>Copyright: Copyright (c) 2011</p> 
 * <p> Company: SuperMap Software Co., Ltd.</p>
 * @author 单庆超
 * @version 6.0
 */
package com.supermap.data;
/**
 * @author shanqc
 * @data 2011-01-25
 */
public final class CalibrateMode extends Enum {
    private CalibrateMode(int value, int ugcValue) {
        super(value, ugcValue);
    }
    // @added by shanqc at 2011年01月25日 

    //通过距离校正线路
    public static final CalibrateMode BYDISTANCE = new CalibrateMode(1, 1);

    //通过里程(M值)校正线路
    public static final CalibrateMode BYMEASURE  = new CalibrateMode(2, 2);
}
