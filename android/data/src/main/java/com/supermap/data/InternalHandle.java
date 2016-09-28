package com.supermap.data;



/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ���ƽ�
 * @version 2.0
 */
public abstract class InternalHandle {
    static {
        Environment.LoadWrapJ();
        
        if(!Environment.hasInitUGCEnv()){
        	throw new IllegalStateException("Please call com.supermap.data.Environment.initialization() firstly");
        }
    }

    protected InternalHandle() {

    }

    public long getHandle() {
        return m_handle;
    }

    protected void setHandle(long handle) {
        this.m_handle = handle;
    }

    public static long getHandle(InternalHandle obj) {
        return obj.getHandle();
    }

    protected static void setHandle(InternalHandle obj, long handle) {
        obj.setHandle(handle);
    }

    /**
     * ���ڴ��ȱ��ͷţ�������øú����Ա�Ƕ���Ϊ������
     */
    protected void clearHandle(){
        setHandle(0);
    }

    private long m_handle;

}
