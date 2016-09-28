package com.supermap.data;

public final class SpatialQueryMode extends Enum {
	private SpatialQueryMode(int value, int ugcValue) {
        super(value, ugcValue);
    }
	
	//�޿ռ��ѯ
    public static final SpatialQueryMode NONE = new SpatialQueryMode(-1,-1);

    //�غ�
    public static final SpatialQueryMode IDENTITY = new SpatialQueryMode(0,0);

    //����
    public static final SpatialQueryMode DISJOINT = new SpatialQueryMode(1,1);

    //�ཻ
    public static final SpatialQueryMode INTERSECT = new SpatialQueryMode(2,2);

    //�ڽ�
    public static final SpatialQueryMode TOUCH = new SpatialQueryMode(3,3);

    //����
    public static final SpatialQueryMode OVERLAP = new SpatialQueryMode(4,4);

    //����
    public static final SpatialQueryMode CROSS = new SpatialQueryMode(5,5);

    //������
    public static final SpatialQueryMode WITHIN = new SpatialQueryMode(6,6);

    //����
    public static final SpatialQueryMode CONTAIN = new SpatialQueryMode(7,7);
}
