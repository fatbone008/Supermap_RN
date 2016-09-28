package com.supermap.data;

import java.util.ArrayList;

import com.supermap.data.*;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 孔令亮
 * @version 2.0
 */
class InternalToolkiConversion extends Toolkit {
    public InternalToolkiConversion() {
    }

    protected static void setHandleBooleanValue(long handle,
                                                boolean value) {
        Toolkit.setHandleBooleanValue(handle, value);
    }
    
	protected static boolean getHandleBooleanValue(long handle) {
		return Toolkit.getHandleBooleanValue(handle);
	}

    protected static Dataset toMangedDataset(Workspace workspace,
                                             long datasetHandle) {
    	
    	Dataset dt = Toolkit.toManageDataset(workspace, datasetHandle); 
//    	InternalHandleDisposable.makeSureNativeObjectLive(workspace);
    	return  dt;
    }
    
    static double DBL_MAX_VALUE = Toolkit.DBL_MAX_VALUE;
    static double DBL_MIN_VALUE = Toolkit.DBL_MIN_VALUE;
    static float FLT_MAX_VALUE = Toolkit.FLT_MAX_VALUE;
    static float FLT_MIN_VALUE = Toolkit.FLT_MIN_VALUE;
    
    //add by xuzw 2009-02-12 Layer中会用到
    protected static String[] splitString(String value, String splitString) {
    	return Toolkit.splitString(value, splitString);
    }
    
    protected static String joinString(String[] arr, String connectString) {
    	return Toolkit.joinString(arr, connectString);
    }
    
//    // add by xuzw 2009-04-23 锁
//    protected static License verifyLicense(ArrayList<ProductType> products) {
//    	return Toolkit.verifyLicense(products);
//    }
//    protected static ArrayList<ProductType> managerProducts(ArrayList<ProductType> pros) {
//    	return Toolkit.managerProducts(pros);
//    }
//    protected static ArrayList<ProductType> getConversionProducts() {
//    	return Toolkit.getConversionProducts();
//    }
//    protected static ArrayList<ProductType> getFMEVectorProducts() {
//    	return Toolkit.getFMEVectorProducts();
//    }
//    protected static ArrayList<ProductType> getFMEEsriProducts() {
//    	return Toolkit.getFMEEsriProducts();
//    }
//    protected static ArrayList<ProductType> getFMERasterProducts() {
//    	return Toolkit.getFMERasterProducts();
//    }
//    protected static ArrayList<ProductType> getFMEOtherProducts() {
//    	return Toolkit.getFMEOtherProducts();
//    }
//    protected static int GetFMELicenseCode(ArrayList<ProductType> products) {
//		int code = -1;
//		License license = new License();
//		for (ProductType t : products) {
//
//			code = license.connect(t);
//			if (code == 0) {
//				break;
//			}
//		}
//		if (code == 0) {
//			code = license.verify();
//		}
//		return code;
//    }
}
