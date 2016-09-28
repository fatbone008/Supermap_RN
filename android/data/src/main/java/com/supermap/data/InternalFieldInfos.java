package com.supermap.data;

import com.supermap.data.FieldInfos;

class InternalFieldInfos extends FieldInfos{
	
	public static long getHandle(FieldInfos fieldInfos) {
		long  fieldinfoshandle = FieldInfos.getHandle(fieldInfos);
//		InternalHandleDisposable.makeSureNativeObjectLive(fieldInfos);
		return fieldinfoshandle;
	}

}
