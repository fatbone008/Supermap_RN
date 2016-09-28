package com.supermap.data;

import java.util.Locale;

/**
 * @author 李云锦
 * @version 2.0
 */
public class WorkspaceConnectionInfo extends InternalHandleDisposable {

    //该对象是否为只读
    private boolean m_readOnly;


    /**
     * 用于处理外面创建的对象
     */
    WorkspaceConnectionInfo(long handle,boolean disposable,boolean readOnly){
        initialize(handle,disposable,readOnly);
    }
    
    /**
     * 用于打开文件型工作空间的构造函数
     * 注意：以前文件名对应到name字段，现调整对应到Server字段
     */
    public WorkspaceConnectionInfo(String file) {
        long handle = WorkspaceConnectionInfoNative.jni_New();

        initialize(handle,true,false);
        if(file == null){
            file = "";
        }
        WorkspaceType type = WorkspaceType.SXW;
        WorkspaceVersion version = WorkspaceVersion.UGC20;
        if (file.endsWith(".sxw")) {
			type = WorkspaceType.SXW;
			version = WorkspaceVersion.UGC20;
		} 
        if (file.endsWith(".smw")) {
			type = WorkspaceType.SMW;
			version = WorkspaceVersion.UGC20;
		}
        if(file.endsWith(".smwu")){
        	type = WorkspaceType.SMWU;
        	version = WorkspaceVersion.UGC60;
        }
        
        reset("", type, file, "", "", "", "",version);
    }

    /**
     * 创建外部传入对象的实例
     */
	protected static WorkspaceConnectionInfo createInstance(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new WorkspaceConnectionInfo(handle ,true ,false);
	} 
	
	/**
     * 清空句柄
     */
    protected void clearHandle() {
        this.setHandle(0);
    }
    
    /**
     * 清空指定工作空间连接信息的句柄
     */
    protected static void clearHandle(WorkspaceConnectionInfo connectionInfo){
        connectionInfo.clearHandle();
    }
    
    /**
     * 默认的构造函数
     */
    public WorkspaceConnectionInfo() {
        long handle = WorkspaceConnectionInfoNative.jni_New();
        setHandle(handle, true);
        reset();
//        this.reset();
    }

    public String getName() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return WorkspaceConnectionInfoNative.jni_GetName(getHandle());
    }

    /**
     * 设置工作空间的名称
     * 如果该对象隶属于某个工作空间，则调用该函数会抛出异常
     */
    public void setName(String value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        //只读的当然不能修改
        if (m_readOnly) {
            String message = InternalResource.loadString(
                    "setName(String value)",
                    InternalResource.WorkspaceConnectionInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if(value == null){
            value = "";
        }
        WorkspaceConnectionInfoNative.jni_SetName(getHandle(), value);
    }

    public WorkspaceType getType() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        int ugcType = WorkspaceConnectionInfoNative.jni_GetType(getHandle());
        return (WorkspaceType) Enum.parseUGCValue(WorkspaceType.class, ugcType);
    }

    /**
     * 目前支持的类型有SXW、ORACLE、SQL
     * 打开工作空间必须设置该属性
     */
    public void setType(WorkspaceType value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString(
                    "setType(WorkspaceType value)",
                    InternalResource.WorkspaceConnectionInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }

        WorkspaceConnectionInfoNative.jni_SetType(getHandle(),
                                                  value.getUGCValue());
    }

    /**
     * 返回数据库服务器名
     * 对于文件型工作空间，获取的值为文件路径
     */
    public String getServer() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return WorkspaceConnectionInfoNative.jni_GetServer(getHandle());
    }

    /**
     * 设置数据库服务器名
     * 各种类型都必须设置该属性
     */
    public void setServer(String value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString(
                    "setServer(String value)",
                    InternalResource.WorkspaceConnectionInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if(value == null){
            value = "";
        }
    	if(Locale.getDefault().equals(Locale.JAPANESE)){
    		value = value.replaceAll("\\\\","/");
    	}
        WorkspaceConnectionInfoNative.jni_SetServer(getHandle(), value);
    }

    public String getUser() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return WorkspaceConnectionInfoNative.jni_GetUser(getHandle());
    }

    /**
     * 设置登录数据库的用户名
     * SXW可以不设置该属性，其它的必须设置
     */
    public void setUser(String value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString(
                    "setUser(String value)",
                    InternalResource.WorkspaceConnectionInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if(value == null){
            value = "";
        }
        WorkspaceConnectionInfoNative.jni_SetUser(getHandle(), value);
    }

    public String getPassword() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return WorkspaceConnectionInfoNative.jni_GetPassword(getHandle());
    }

    /**
     * 设置登录数据源连接的口令
     * SXW不支持密码
     */
    public void setPassword(String value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString(
                    "setPassword(String value)",
                    InternalResource.WorkspaceConnectionInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if(value == null){
            value = "";
        }
        WorkspaceConnectionInfoNative.jni_SetPassword(getHandle(), value);
    }

    /**
     * 获取工作空间的版本
     * 版本的不同可能会导致打开、保存的失败，默认为UGC2.0
     */
    public WorkspaceVersion getVersion() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        int ugcValue = WorkspaceConnectionInfoNative.jni_GetVersion(getHandle());

        return (WorkspaceVersion) Enum.parseUGCValue(WorkspaceVersion.class,
                ugcValue);
    }

    public void setVersion(WorkspaceVersion value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString(
                    "setDriver(String value)",
                    InternalResource.WorkspaceConnectionInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        WorkspaceConnectionInfoNative.jni_SetVersion(getHandle(),
                value.getUGCValue());
    }

    public String toString() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        String str = "{Name=\"" + getName() + "\",Type=\"" + getType() +
                     "\",Server=\"" + getServer() + "\",User=\"" + getUser() 
                     + "\",Password=\"" + getPassword() + "\" }";
        return str;

    }

    public void dispose() {
        if (!getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (super.getHandle() != 0) {
            WorkspaceConnectionInfoNative.jni_Delete(getHandle());
            setHandle(0);
        }
    }

    void reset() {
        reset("", WorkspaceType.DEFAULT, "", "", "", "", "",
              WorkspaceVersion.UGC20);
    }

    //为了不多次调用JNI,做一个JNI来干这个
    void reset(String name, WorkspaceType type, String server,
               String database, String user, String password,
               String driver, WorkspaceVersion version) {
    	
    	if(Locale.getDefault().equals(Locale.JAPANESE)){
    		server = server.replaceAll("\\\\","/");
    	}
        WorkspaceConnectionInfoNative.jni_Reset(getHandle(), name, type.value(),
                                                server, database, user,
                                                password, driver, version.value());
    }

    void initialize(long handle,boolean disposable,boolean readOnly){
        setHandle(handle,disposable);
        m_readOnly = readOnly;
    }
    
    /**
     * Workspace在open的时候会用到，临时去掉只读属性
     * @param value
     */
    protected void setReadOnly(boolean value) {
    	m_readOnly = value;
    }

}
