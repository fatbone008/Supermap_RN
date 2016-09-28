package com.supermap.data;

import com.supermap.data.PrjCoordSys;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ’≈ºÃƒœ
 * @version 2.0
 */
class InternalPrjCoordSys extends PrjCoordSys {
    private InternalPrjCoordSys() {
    }

    public static final PrjCoordSys createInstance(long handle,
            boolean disposable) {
        return PrjCoordSys.createInstance(handle, disposable);
    }

    public static final void clearHandle(PrjCoordSys prjCoordSys) {
        PrjCoordSys.clearHandle(prjCoordSys);
//        InternalHandleDisposable.makeSureNativeObjectLive(prjCoordSys);
    }
}
