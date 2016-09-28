package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: ��׼ģʽ</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ¬��
 * @version 6.0
 */
public final class TransformationMode extends Enum {
    private TransformationMode(int value, int ugcValue) {
        super(value, ugcValue);
    }
    
    //������׼
    public static final TransformationMode RECT = new TransformationMode(0,0);

    //������׼
    public static final TransformationMode LINEAR = new TransformationMode(1,1);

    //����ʽ��׼
    public static final TransformationMode SQUARE = new TransformationMode(2,2);

    //ƫ����׼
    public static final TransformationMode OFFSET = new TransformationMode(4,4);
}
