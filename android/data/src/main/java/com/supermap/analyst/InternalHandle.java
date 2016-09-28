package com.supermap.analyst;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author not attributable
 * @version 2.0
 */
abstract class InternalHandle extends com.supermap.data.InternalHandle {
    protected InternalHandle() {
    }

    public static long getHandle(com.supermap.data.InternalHandle obj) {
        return com.supermap.data.InternalHandle.getHandle(obj);
    }

    protected static void setHandle(com.supermap.data.InternalHandle obj, long handle) {
        com.supermap.data.InternalHandle.setHandle(obj, handle);
    }
}
