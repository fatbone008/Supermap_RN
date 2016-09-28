package com.supermap.data;

public class StrokeType extends Enum{

	protected StrokeType(int value, int ugcValue) {
		super(value, ugcValue);
	}

	public static final StrokeType ST_POINT = new StrokeType(0,0);
	public static final StrokeType ST_POLYLINE = new StrokeType(1,1);
	public static final StrokeType ST_ARC = new StrokeType(2,2);
	public static final StrokeType ST_POLYGON = new StrokeType(3,3);
	public static final StrokeType ST_ELLIPSE = new StrokeType(4,4);
	public static final StrokeType ST_RECTANGLE = new StrokeType(5,5);
	public static final StrokeType ST_ROUNDRECT = new StrokeType(6,6);
	public static final StrokeType ST_TEXT = new StrokeType(7,7);
	public static final StrokeType ST_PIE = new StrokeType(8,8);
	public static final StrokeType ST_CHORD = new StrokeType(9,9);
	public static final StrokeType ST_BITMAP = new StrokeType(10,10);
	public static final StrokeType ST_ICON = new StrokeType(11,11);
}
