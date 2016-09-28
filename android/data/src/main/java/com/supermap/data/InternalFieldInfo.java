package com.supermap.data;

import com.supermap.data.FieldInfo;

class InternalFieldInfo extends FieldInfo {
	
    private InternalFieldInfo() {
    	
    }

    public static final FieldInfo createInstance(long handle) {
        return FieldInfo.createInstance(handle);
    }

}
