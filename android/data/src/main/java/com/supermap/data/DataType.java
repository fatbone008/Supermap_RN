package com.supermap.data;

import com.supermap.data.Enum;

 class DataType extends Enum {
	private DataType(int value, int ugcValue) {
		super(value, ugcValue);
	}

	//������3�����ͣ�����������ӡ�
	public static final DataType VECTOR = new DataType(1, 1);

	public static final DataType RASTER = new DataType(2, 2);

	public static final DataType WOR = new DataType(3, 3);

}
