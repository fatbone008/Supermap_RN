package com.supermap.analyst;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author CHENGXI
 * @version 2.0
 */
public class BufferRadiusUnit extends com.supermap.data.Enum{
	private BufferRadiusUnit(int value, int ugcValue){
		super(value, ugcValue);
	}
	
	/**
     * 毫米
     */
    public static final BufferRadiusUnit MiliMeter= new BufferRadiusUnit(10, 10);

    /**
     * 厘米
     */
    public static final BufferRadiusUnit CentiMeter = new BufferRadiusUnit(100, 100);
    
    /**
     * 分米
     */
    public static final BufferRadiusUnit DeciMeter= new BufferRadiusUnit(1000, 1000);
    
    /**
     * 米
     */
    public static final BufferRadiusUnit Meter= new BufferRadiusUnit(10000, 10000);
    
    /**
     * 千米
     */
    public static final BufferRadiusUnit KiloMeter= new BufferRadiusUnit(10000000, 10000000);

    /**
     * 码
     */
    public static final BufferRadiusUnit Yard= new BufferRadiusUnit(9144, 9144);
    
    /**
     * 英寸
     */
    public static final BufferRadiusUnit Inch= new BufferRadiusUnit(254, 254);
    
    /**
     * 英尺
     */
    public static final BufferRadiusUnit Foot= new BufferRadiusUnit(3048, 3048);
    
    /**
     * 英里
     */
    public static final BufferRadiusUnit Mile= new BufferRadiusUnit(16090000, 16090000);
}
