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
 * @author 李云锦
 * @version 2.0
 */
public class JoinItem extends InternalHandleDisposable {

    //根据指定的信息初始化对象实例
    //默认链接为内链接
    public JoinItem() {
        setHandle(JoinItemNative.jni_New(), true);
        reset();
    }

    JoinItem(JoinItem joinItem) {
        long handle = JoinItemNative.jin_Clone(joinItem.getHandle());
        //创建失败
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


    //返回/设置外部表名
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
     * 暂时不做任何检查
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

    //返回/设置外部连接条件
    public String getJoinFilter() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return JoinItemNative.jni_GetJoinFilter(getHandle());
    }

    //返回/设置外部连接条件
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

    //返回/设置连接类型
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

    //返回/设置连接类型
    public void setJoinType(JoinType value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        JoinItemNative.jni_SetJoinType(getHandle(), value.getUGCValue());
    }

    //返回/设置名称
    public String getName() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return JoinItemNative.jni_GetName(getHandle());
    }

    //返回/设置名称
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
