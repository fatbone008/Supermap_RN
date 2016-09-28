package com.supermap.analyst;

import java.util.ArrayList;

//import com.supermap.data.License;
//import com.supermap.data.ProductType;
import com.supermap.data.Toolkit;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author �ż���
 * @version 2.0
 */
class InternalToolkitSpatialAnalyst extends Toolkit {

	public InternalToolkitSpatialAnalyst() {
	}

	public static boolean isDirectoryExisted(String fileName) {
		return Toolkit.isDirectoryExisted(fileName);
	}

	//  �ڲ�ʹ�ã���Object����ת��Double
	static double objectToDouble(Object obj) {
		if (obj == null) {
			return 0;
		}
		double result;
		if (obj instanceof Double) {
			result = (Double) obj;
		}
		//���obj�����Ͳ����ʣ���BufferAnalystParameter���Ѿ��׳��쳣
		result = Double.valueOf(obj.toString());
		return result;
	}

	//�ڲ�ʹ�ã��߻�������
	static class BufferSideType extends com.supermap.data.Enum {
		private BufferSideType(int value, int ugcValue) {
			super(value, ugcValue);
		}

		//�ޱ�����
		public static final BufferSideType NONESIDE = new BufferSideType(-1, -1);

		//���Ҿ����壨����뾶��ͬ��
		public static final BufferSideType FULL = new BufferSideType(0, 0);

		//��߻���
		public static final BufferSideType LEFT = new BufferSideType(1, 1);

		//�ұ߻���
		public static final BufferSideType RIGHT = new BufferSideType(2, 2);

		//���Ҿ����壨����뾶����ͬ��
		public static final BufferSideType FULLDIFFR = new BufferSideType(3, 3);
	}

	// add by xuzw 2009-04-23 ��
//	protected static License verifyLicense(ArrayList<ProductType> products) {
//		return Toolkit.verifyLicense(products);
//	}
//
//	protected static ArrayList<ProductType> managerProducts(ArrayList<ProductType> pros) {
//		return Toolkit.managerProducts(pros);
//	}
//	
//	protected static ArrayList<ProductType> getSpatialAnalystProducts() {
//		return Toolkit.getSpatialAnalystProducts();
//	}
	
	protected static void setHandleBooleanValue(long handle, boolean value) {
    	Toolkit.setHandleBooleanValue(handle, value);
    }
	protected static boolean getHandleBooleanValue(long handle) {
		return Toolkit.getHandleBooleanValue(handle);
	}
}
