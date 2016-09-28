package com.supermap.data;

import java.util.ArrayList;

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
public class JoinItems extends InternalHandleDisposable {
    private ArrayList m_joinItems = null;

    public JoinItems() {
        this.m_joinItems = new ArrayList();
        setHandle(JoinItemsNative.jni_New(), true);
    }

    //
    public JoinItems(JoinItem[] joinItems) {
        if (joinItems == null) {
            String message = InternalResource.loadString("joinItems",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new NullPointerException(message);
        }
        this.m_joinItems = new ArrayList();
        setHandle(JoinItemsNative.jni_New(), true);
        for (int i = 0; i < joinItems.length; i++) {
            this.add(joinItems[i]);
        }
    }

    //
    public JoinItems(JoinItems joinItems) {
        if (joinItems == null) {
            String message = InternalResource.loadString("joinItems",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new NullPointerException(message);
        }
        if (joinItems.getHandle() == 0) {
            String message = InternalResource.loadString("joinItems",
                    InternalResource.GlobalArgumentObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        long handle = JoinItemsNative.jni_Clone(joinItems.getHandle());
        this.m_joinItems = new ArrayList();
        setHandle(handle, true);
        int len = joinItems.getCount();
        for (int i = 0; i < len; i++) {
            this.add(joinItems.get(i));
        }
    }


    JoinItems(long handle) {
        // @modified by 李云锦 at 2007年8月30日 下午06时04分49秒
        // @reason  这里就把数组new出来了，可是内容却没有，而且设置的对象不可以释放，这样是不对的
//        this.m_joinItems = new ArrayList();
//        setHandle(handle, false);

        //默认还是设置为不可释放的吧，也就是让 QueryParameter用
        //Clone也需要调用这个函数，那就手动把Disposable属性该为true吧
        setHandle(handle, false);
        this.m_joinItems = new ArrayList();
        int count = JoinItemsNative.jni_GetCount(getHandle());
        long[] handles = new long[count];
        JoinItemsNative.jni_GetHandles(getHandle(), handles);

        for (int i = 0; i < count; i++) {
            this.m_joinItems.add(new JoinItem(handles[i]));
        }
    }


    //
    public int getCount() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return this.m_joinItems.size();
    }

    //
    public JoinItem get(int index) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (index < 0 || index >= getCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        return (JoinItem)this.m_joinItems.get(index);
    }

    //
    public void set(int index, JoinItem value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (index < 0 || index >= getCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

        if (value.getHandle() == 0) {
            String message = InternalResource.loadString("value",
                    InternalResource.JoinItemsJoinItemDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        JoinItemsNative.jni_Set(get(index).getHandle(), value.getHandle());
    }

    //
    public JoinItems clone() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return new JoinItems(this);

//        long handle = JoinItemsNative.jni_Clone(getHandle());
//        if (handle == 0) {
//            return null;
//        }
//        JoinItems items = new JoinItems(handle);
//        //默认的为不可释放的，一定要设为可释放，否则会有内存泄露
//        items.setIsDisposable(true);
//
//        return items;
    }

    //
    public int add(JoinItem joinItem) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (joinItem.getHandle() == 0) {
            String message = InternalResource.loadString("joinItem",
                    InternalResource.JoinItemsJoinItemDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        long handle = JoinItemsNative.jni_Add(getHandle(), joinItem.getHandle());

        if (handle != 0) {
            JoinItem newItem = new JoinItem(handle);
            this.m_joinItems.add(newItem);
            return getCount() - 1;
        } else {
            //添加失败
            return -1;
        }
    }

    //
    public int addRange(JoinItem[] joinItems) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int success = 0;
        int length = joinItems.length;
        for (int k = 0; k < length; k++) {
            JoinItem joinItem = joinItems[k];
            if (joinItem == null) {
                String message = InternalResource.loadString("joinItems[" + k +
                        "]",
                        InternalResource.GlobalArgumentNull,
                        InternalResource.BundleName);
                throw new IllegalArgumentException(message);
            }
            if (joinItem.getHandle() == 0) {
                String message = InternalResource.loadString("joinItems[" + k +
                        "]",
                        InternalResource.GlobalArgumentObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalArgumentException(message);
            }
        }

        for (int i = 0; i < length; i++) {
            add(joinItems[i]);
            success++;
        }
        return success;
    }

    //
    public boolean insert(int index, JoinItem joinItem) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (index < 0 || index > getCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

        if (joinItem.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "insert(int index, JoinItem joinItem)",
                    InternalResource.JoinItemsJoinItemDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        //添加到最后
        if (index == getCount()) {
            return (add(joinItem) != -1);
        }
        long handle = JoinItemsNative.jni_Insert(getHandle(), index,
                                                 joinItem.getHandle());
        if (handle == 0) {
            return false;
        }
        JoinItem newItem = new JoinItem(handle);
        this.m_joinItems.add(index, newItem);
        return true;
    }

    //
    public int insertRange(int index, JoinItem[] joinItems) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (index < 0 || index > getCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        int length = joinItems.length;
        for (int k = 0; k < length; k++) {
            JoinItem joinItem = joinItems[k];
            if (joinItem == null) {
                String message = InternalResource.loadString("joinItems[" + k +
                        "]",
                        InternalResource.GlobalArgumentNull,
                        InternalResource.BundleName);
                throw new IllegalArgumentException(message);
            }
            if (joinItem.getHandle() == 0) {
                String message = InternalResource.loadString("joinItems[" + k +
                        "]",
                        InternalResource.GlobalArgumentObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalArgumentException(message);
            }
        }
        int success = 0;
        boolean result = false;

        for (int i = 0; i < length; i++) {
            result = insert(index + success, joinItems[i]);
            if (result) {
                success++;
            }
        }
        return success;
    }

    //
    public boolean remove(int index) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (index < 0 || index >= getCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

        JoinItemsNative.jni_Remove(getHandle(), index);
        JoinItem joinItem = get(index);
        joinItem.clearHandle();
        this.m_joinItems.remove(index);
        return true;
    }

    //
    public int removeRange(int index, int count) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (index < 0 || index >= getCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

        if (count < 0 || count > getCount() - index) {
            String message = InternalResource.loadString("count",
                    InternalResource.JoinItemsRemoveRangeCountInvalid,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        int success = 0;
        boolean result = false;
        for (int i = index + count - 1; i >= index; i--) {
            try {
                result = remove(i);
                if (result) {
                    success++;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return success;
    }

    //
    public void clear() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int count = getCount();
        /**
         * @todo 需要修改 多次调用了JNI函数
         * @reason 考虑先一次删除，再clearHandle
         */
        for (int i = count - 1; i >= 0; i--) {
            remove(i);
        }
    }


    public JoinItem[] toArray() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int len = this.m_joinItems.size();
        JoinItem[] joinItems = new JoinItem[len];
        for (int i = 0; i < len; i++) {
            joinItems[i] = new JoinItem((JoinItem)this.get(i));
        }
        return joinItems;
    }

    public void dispose() {
        if (!super.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (super.getHandle() != 0) {
            JoinItemsNative.jni_Delete(getHandle());
            clearHandle();
        }

    }

    /**
     * 内部使用，只负责清空handle，不管内存的释放
     */
    protected void clearHandle() {
        if (this.m_joinItems != null) {
            int len = this.m_joinItems.size();
            for (int i = 0; i < len; i++) {
                JoinItem joinItem = get(i);
                joinItem.clearHandle();
            }
            this.m_joinItems.clear();
            this.m_joinItems = null;
        }
        setHandle(0);
    }
}
