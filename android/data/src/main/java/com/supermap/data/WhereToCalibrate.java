/**
 * <p>Title: 路由（线路）对象选择校正位置</p>
 * <p>Description: 用于选择对象校正位置</p>
 <p>Copyright: Copyright (c) 2011</p> 
 * <p> Company: SuperMap Software Co., Ltd.</p>
 * @author 单庆超
 * @version 6.0
 */
package com.supermap.data;

/**
 * @author shanqc
 * @data 2011-01-25
 *
 */
public final class WhereToCalibrate extends Enum {
	private WhereToCalibrate(int value, int ugcValue) {
        super(value, ugcValue);
    }
	//! 不去计算
	public static final WhereToCalibrate NOTTOCOMPUTE=new WhereToCalibrate(0,0);
	//! 只外推起点之前的Section
	public static final WhereToCalibrate BEFORE=new WhereToCalibrate(1,1);
	//! 只外推终点之后的Section
	public static final WhereToCalibrate AFTER =new WhereToCalibrate(2,2);
	//! 只外推起点之前 + 终点之前的Section
	public static final WhereToCalibrate BEFOREANDAFTER=new WhereToCalibrate(3,3);
	//! 只外推起点之后和终点之前的Section
	public static final WhereToCalibrate INTERVAL=new WhereToCalibrate(4,4);
	//! 推起点之前+起点之后和终点之前
	public static final WhereToCalibrate BEFOREANDINTERVAL=new WhereToCalibrate(5,5);
	//! 推起点之后和终点之前+终点之前
	public static final WhereToCalibrate INTERVALANDAFTER=new WhereToCalibrate(6,6);
	//! 全部Section
	public static final WhereToCalibrate ALLLINE =new WhereToCalibrate(7,7);

}
