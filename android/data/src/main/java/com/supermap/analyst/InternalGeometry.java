package com.supermap.analyst;

import com.supermap.data.Geometry;
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
abstract class InternalGeometry extends Geometry {
    private InternalGeometry() {
    }
    public static final Geometry createInstance(long handle){
        return Geometry.internalCreateInstance(handle);
    }
}
