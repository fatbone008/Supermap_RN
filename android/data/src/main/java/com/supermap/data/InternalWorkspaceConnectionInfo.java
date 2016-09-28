package com.supermap.data;

import com.supermap.data.WorkspaceConnectionInfo;


class InternalWorkspaceConnectionInfo extends WorkspaceConnectionInfo {
	private InternalWorkspaceConnectionInfo() {

	}

	public static final void clearHandle(WorkspaceConnectionInfo connectionInfo) {
		WorkspaceConnectionInfo.clearHandle(connectionInfo);
//		InternalHandleDisposable.makeSureNativeObjectLive(connectionInfo);
	}

	public static final WorkspaceConnectionInfo createInstance(long handle) {
		return WorkspaceConnectionInfo.createInstance(handle);
	}
}
