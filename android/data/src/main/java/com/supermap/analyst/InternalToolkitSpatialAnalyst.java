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
 * @author 张继南
 * @version 2.0
 */
class InternalToolkitSpatialAnalyst extends Toolkit {

	public InternalToolkitSpatialAnalyst() {
	}

	public static boolean isDirectoryExisted(String fileName) {
		return Toolkit.isDirectoryExisted(fileName);
	}

	//  内部使用，将Object类型转成Double
	static double objectToDouble(Object obj) {
		if (obj == null) {
			return 0;
		}
		double result;
		if (obj instanceof Double) {
			result = (Double) obj;
		}
		//如果obj的类型不合适，在BufferAnalystParameter中已经抛出异常
		result = Double.valueOf(obj.toString());
		return result;
	}

	//内部使用，边缓冲类型
	static class BufferSideType extends com.supermap.data.Enum {
		private BufferSideType(int value, int ugcValue) {
			super(value, ugcValue);
		}

		//无边类型
		public static final BufferSideType NONESIDE = new BufferSideType(-1, -1);

		//左右均缓冲（缓冲半径相同）
		public static final BufferSideType FULL = new BufferSideType(0, 0);

		//左边缓冲
		public static final BufferSideType LEFT = new BufferSideType(1, 1);

		//右边缓冲
		public static final BufferSideType RIGHT = new BufferSideType(2, 2);

		//左右均缓冲（缓冲半径不相同）
		public static final BufferSideType FULLDIFFR = new BufferSideType(3, 3);
	}

	// add by xuzw 2009-04-23 锁
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
