package com.supermap.data;

import java.util.Locale;

/**
 * @author ���ƽ�
 * @version 2.0
 */
public class WorkspaceConnectionInfo extends InternalHandleDisposable {

    //�ö����Ƿ�Ϊֻ��
    private boolean m_readOnly;


    /**
     * ���ڴ������洴���Ķ���
     */
    WorkspaceConnectionInfo(long handle,boolean disposable,boolean readOnly){
        initialize(handle,disposable,readOnly);
    }
    
    /**
     * ���ڴ��ļ��͹����ռ�Ĺ��캯��
     * ע�⣺��ǰ�ļ�����Ӧ��name�ֶΣ��ֵ�����Ӧ��Server�ֶ�
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
     * �����ⲿ��������ʵ��
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
     * ��վ��
     */
    protected void clearHandle() {
        this.setHandle(0);
    }
    
    /**
     * ���ָ�������ռ�������Ϣ�ľ��
     */
    protected static void clearHandle(WorkspaceConnectionInfo connectionInfo){
        connectionInfo.clearHandle();
    }
    
    /**
     * Ĭ�ϵĹ��캯��
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
     * ���ù����ռ������
     * ����ö���������ĳ�������ռ䣬����øú������׳��쳣
     */
    public void setName(String value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        //ֻ���ĵ�Ȼ�����޸�
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
     * Ŀǰ֧�ֵ�������SXW��ORACLE��SQL
     * �򿪹����ռ�������ø�����
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
     * �������ݿ��������
     * �����ļ��͹����ռ䣬��ȡ��ֵΪ�ļ�·��
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
     * �������ݿ��������
     * �������Ͷ��������ø�����
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
     * ���õ�¼���ݿ���û���
     * SXW���Բ����ø����ԣ������ı�������
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
     * ���õ�¼����Դ���ӵĿ���
     * SXW��֧������
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
     * ��ȡ�����ռ�İ汾
     * �汾�Ĳ�ͬ���ܻᵼ�´򿪡������ʧ�ܣ�Ĭ��ΪUGC2.0
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

    //Ϊ�˲���ε���JNI,��һ��JNI�������
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
     * Workspace��open��ʱ����õ�����ʱȥ��ֻ������
     * @param value
     */
    protected void setReadOnly(boolean value) {
    	m_readOnly = value;
    }

}
