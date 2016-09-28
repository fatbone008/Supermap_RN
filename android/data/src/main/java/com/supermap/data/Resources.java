package com.supermap.data;

public class Resources extends InternalHandleDisposable {
    Workspace m_workspace;
    SymbolLineLibrary m_symbolLineLibrary;
    SymbolMarkerLibrary m_symbolMarkerLibrary;
    SymbolFillLibrary m_symbolFillLibrary;
	public Resources() {
		this.setHandle(ResourcesNative.jni_New(),true);
	}

	Resources(Workspace workspace ,long handle) {
		this.m_workspace = workspace;
		this.setHandle(handle,false);
		
	}

	public SymbolLineLibrary getLineLibrary() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getLineLibrary()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		if(m_symbolLineLibrary==null){
			long handle = ResourcesNative.jni_GetLineLibrary(this.getHandle());
			m_symbolLineLibrary = new SymbolLineLibrary(handle);
		}
		return m_symbolLineLibrary;
	}
	public SymbolMarkerLibrary getMarkerLibrary() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getMarkerLibrary()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		if(m_symbolMarkerLibrary==null){
			long handle = ResourcesNative.jni_GetMarkerLibrary(this.getHandle());
			m_symbolMarkerLibrary = new SymbolMarkerLibrary(handle);
		}
		return m_symbolMarkerLibrary;
	}
	public SymbolFillLibrary  getFillLibrary() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getFillLibrary()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		if(m_symbolFillLibrary==null){
			long handle = ResourcesNative.jni_GetFillLibrary(this.getHandle());
			m_symbolFillLibrary = new SymbolFillLibrary(handle);
		}
		return m_symbolFillLibrary;
	}
	
	public Workspace getWorkspace() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getWorkspace()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return this.m_workspace;
	}
	
	protected void clearHandle(){
		this.m_workspace =null;
		if(m_symbolLineLibrary!=null){
			m_symbolLineLibrary.clearHandle();
			m_symbolLineLibrary=null;
		}
		if(m_symbolMarkerLibrary!=null){
			m_symbolMarkerLibrary.clearHandle();
			m_symbolMarkerLibrary=null;
		}
		if(m_symbolFillLibrary!=null){
			m_symbolFillLibrary.clearHandle();
			m_symbolFillLibrary=null;
		}
        setHandle(0);
    }
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			ResourcesNative.jni_Delete(getHandle());
			setHandle(0);
			clearHandle();
		}

	}
	
	//add by xuzw 2009-02-12 
	/**
	 * 内部使用，Map类中会用到
	 * @param Workspace Workspace
	 * @param handle long
	 * @return Resources
	 */
	protected static Resources createInstance(Workspace workspace, long handle) {
		if (workspace == null || workspace.getHandle() == 0) {
			String message = InternalResource.loadString("workspace",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new Resources(workspace, handle);
	}
	
	/**
	 * 内部使用，Map类中会用到
	 * 
	 * @param handle
	 *            long
	 */
	private void refreshHandle(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		this.setHandle(handle, false);
	}
	
	/**
	 * 内部使用，Map类中会用到
	 * @param resources Resources
	 */
	protected static void clearHandle(Resources resources) {
		resources.clearHandle();
	}
	
	/**
	 * 内部使用，Map类中会用到
	 * @param resources Resources
	 * @param handle long
	 */
	protected static void refreshHandle(Resources resources, long handle) {
		resources.refreshHandle(handle);
	}
}
