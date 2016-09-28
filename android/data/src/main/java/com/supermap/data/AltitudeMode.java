package com.supermap.data;

public class AltitudeMode extends Enum{
	 private AltitudeMode(int value, int ugcValue) {
	        super(value, ugcValue);
	    }

      //���ж����Zֵ��������
	    public static final AltitudeMode CLAMP_TO_GROUND = new AltitudeMode(0, 0);
      //Zֵ��ָ����ڵ��εĸ߶�
	    public static final AltitudeMode RELATIVE_TO_GROUND = new AltitudeMode(1, 1);
	    //���θ߶�
	    public static final AltitudeMode ABSOLUTE = new AltitudeMode(2, 2);
	    //��Ե��µ�ģʽ
	    public static final AltitudeMode RELATIVE_TO_UNDERGROUND = new AltitudeMode(3, 3);
	    public static final AltitudeMode RELATIVE_UNDER_GROUND = new AltitudeMode(3, 3);
		//���¾��Ը߶�ģʽ
		public static final AltitudeMode ABSOLUTE_UNDER_GROUND = new AltitudeMode(4, 4);
}
