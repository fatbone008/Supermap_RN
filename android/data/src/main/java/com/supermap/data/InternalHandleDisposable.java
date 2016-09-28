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
 * @author not attributable
 * @version 2.0
 */
public abstract class InternalHandleDisposable extends InternalHandle implements
        IDisposable {
    protected InternalHandleDisposable() {
    }

    /**
     * �������Handle�����setHandle(long handle, boolean disposable)
     * �ú���ֻ����dispose������ʱ������handleΪ0
     * @param handle long
     */
    protected void setHandle(long handle) {
        if (handle != 0) {
            String message = InternalResource.loadString("setHandle()",
                    InternalResource.HandleDisposableCantCreate,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        //���û����Ϊ0
        super.setHandle(0);
    }

    /**
     * ���ö����ָ��
     * �ú����ڲ�ʹ��
     * 1.֮ǰ����Ϊ�գ����óɹ�
     * 2.֮ǰ����Ϊ���ͷţ�����δ�ͷţ�������ΪHandle�׳��쳣
     * 3.֮ǰ����Ϊ���ͷţ����Ѿ��ͷţ������óɹ�
     */
    protected void setHandle(long handle, boolean disposable) {
        if (this.getIsDisposable() && !(getHandle() == 0)) {
            String message = InternalResource.loadString("setHandle()",
                    InternalResource.HandleOriginalObjectHasNotBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        this.setIsDisposable(disposable);
        super.setHandle(handle);
    }

    /**
     * �����Ƿ����ͷ�
     * @return boolean
     */
    protected boolean getIsDisposable() {
        return this.m_disposable;
    }

    protected void finalize() {
        if (this.getIsDisposable() && !(getHandle() == 0)) {
//���ʱ��û׼,�������ͷŻ����Ұָ�����,��Ҫ�������ط�ά�� add by huangkj 2015-11-9		
//            dispose();
        }
    }

    /**
     * ���ö����Ƿ��ܱ��ͷ�
     * 1.֮ǰ����Ϊ�գ����óɹ�
     * 2.֮ǰ����Ϊ���ͷţ�����δ�ͷţ�������Ϊfalse�׳��쳣
     * 3.֮ǰ����Ϊ���ͷţ����Ѿ��ͷţ���������ֵΪtrue/false
     * 4.֮ǰ���󲻿��ͷţ�������Ϊtrue�׳��쳣
     */
    protected void setIsDisposable(boolean disposable) {
        this.m_disposable = disposable;
    }


    protected static void setIsDisposable(InternalHandleDisposable obj,
                                          boolean disposable) {
        obj.setIsDisposable(disposable);
    }

    private boolean m_disposable = true;
}
