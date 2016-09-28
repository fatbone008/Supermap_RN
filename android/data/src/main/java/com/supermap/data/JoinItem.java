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
public class JoinItem extends InternalHandleDisposable {

    //����ָ������Ϣ��ʼ������ʵ��
    //Ĭ������Ϊ������
    public JoinItem() {
        setHandle(JoinItemNative.jni_New(), true);
        reset();
    }

    JoinItem(JoinItem joinItem) {
        long handle = JoinItemNative.jin_Clone(joinItem.getHandle());
        //����ʧ��
        if (handle == 0) {
            String message = InternalResource.loadString("handle",
                    InternalResource.GlobalInvalidConstructorArgument,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        setHandle(handle, true);
    }

    JoinItem(long handle) {
        if (handle == 0) {
            String message = InternalResource.loadString("handle",
                    InternalResource.GlobalInvalidConstructorArgument,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        setHandle(handle, false);
    }


    //����/�����ⲿ����
    public String getForeignTable() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return JoinItemNative.jni_GetForeignTable(getHandle());
    }

    /**
     * ��ʱ�����κμ��
     * @param value String
     */
    public void setForeignTable(String value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (value == null) {
            value = "";
        }
        JoinItemNative.jni_SetForeignTable(getHandle(), value);
    }

    //����/�����ⲿ��������
    public String getJoinFilter() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return JoinItemNative.jni_GetJoinFilter(getHandle());
    }

    //����/�����ⲿ��������
    public void setJoinFilter(String value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (value == null) {
            value = "";
        }
        JoinItemNative.jni_SetJoinFilter(getHandle(), value);
    }

    //����/������������
    public JoinType getJoinType() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int ugcValue = JoinItemNative.jni_GetJoinType(getHandle());
        return (JoinType) Enum.parseUGCValue(JoinType.class, ugcValue);
    }

    //����/������������
    public void setJoinType(JoinType value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        JoinItemNative.jni_SetJoinType(getHandle(), value.getUGCValue());
    }

    //����/��������
    public String getName() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return JoinItemNative.jni_GetName(getHandle());
    }

    //����/��������
    public void setName(String value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (value == null) {
            value = "";
        }
        JoinItemNative.jni_SetName(getHandle(), value);
    }

    public void dispose() {
        if (!super.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (super.getHandle() != 0) {
            JoinItemNative.jni_Delete(super.getHandle());
            setHandle(0);
        }
    }

    public String toString() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("{Name = ");
        buffer.append(this.getName());
        buffer.append(",ForeignTable = ");
        buffer.append(this.getForeignTable());
        buffer.append(",JoinFilter = ");
        buffer.append(this.getJoinFilter());
        buffer.append(",JoinType = ");
        buffer.append(this.getJoinType().name());
        buffer.append("}\n");
        return buffer.toString();
    }

    protected void clearHandle() {
        this.setHandle(0);
    }

    void reset() {
        if (getHandle() != 0) {
            JoinItemNative.jni_Reset(getHandle());
        }
    }
}
