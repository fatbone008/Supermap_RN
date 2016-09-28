package com.supermap.data;

import com.supermap.data.DatasourceConnectionInfo;

class InternalDatasourceConnectionInfo extends DatasourceConnectionInfo{
	private InternalDatasourceConnectionInfo() {
		
	}
	
    public static final void clearHandle(DatasourceConnectionInfo connectionInfo) {
    	DatasourceConnectionInfo.clearHandle(connectionInfo);
//    	InternalHandleDisposable.makeSureNativeObjectLive(connectionInfo);
    }
    
    public static final DatasourceConnectionInfo createInstance(long handle) {
        return DatasourceConnectionInfo.createInstance(handle);
    }
}
