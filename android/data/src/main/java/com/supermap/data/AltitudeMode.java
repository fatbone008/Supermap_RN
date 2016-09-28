package com.supermap.data;

public class AltitudeMode extends Enum{
	 private AltitudeMode(int value, int ugcValue) {
	        super(value, ugcValue);
	    }

      //所有对象的Z值不起作用
	    public static final AltitudeMode CLAMP_TO_GROUND = new AltitudeMode(0, 0);
      //Z值是指相对于地形的高度
	    public static final AltitudeMode RELATIVE_TO_GROUND = new AltitudeMode(1, 1);
	    //海拔高度
	    public static final AltitudeMode ABSOLUTE = new AltitudeMode(2, 2);
	    //相对地下的模式
	    public static final AltitudeMode RELATIVE_TO_UNDERGROUND = new AltitudeMode(3, 3);
	    public static final AltitudeMode RELATIVE_UNDER_GROUND = new AltitudeMode(3, 3);
		//地下绝对高度模式
		public static final AltitudeMode ABSOLUTE_UNDER_GROUND = new AltitudeMode(4, 4);
}
