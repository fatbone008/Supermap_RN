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
 * @author not attributable
 * @version 2.0
 */
public class StatisticMode extends Enum {
    private StatisticMode(int value, int ugcValue) {
        super(value, ugcValue);
    }

//    enum UGStatisticMode
//{
//        smMax				= 1,	//! 最大值
//        smMin				= 2,	//! 最小值
//        smAvg				= 3,	//! 平均值
//        smSum				= 4,	//! 总和
//        smStdev				= 5,	//! 标准差
//        smVar				= 6		//! 方差
//};

    public static final StatisticMode MAX = new StatisticMode(1, 1);
    public static final StatisticMode MIN = new StatisticMode(2, 2);
    public static final StatisticMode AVERAGE = new StatisticMode(3, 3);
    public static final StatisticMode SUM = new StatisticMode(4, 4);
    public static final StatisticMode STDDEVIATION = new StatisticMode(5, 5);
    public static final StatisticMode VARIANCE = new StatisticMode(6, 6);

}
