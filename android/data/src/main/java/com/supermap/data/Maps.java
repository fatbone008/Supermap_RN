package com.supermap.data;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 地图集合类
 * 存储地图名与地图所对应的XML字符串。
 * 该对象不可释放，实际存储的为底层的MapStorage指针
 * @author 李云锦
 * @version 2.0
 */
public class Maps {

    private Workspace m_workspace;

    transient CopyOnWriteArrayList<MapAddedListener> m_mapAddedListeners;
    
    Maps(Workspace workspace) {
        m_workspace = workspace;
    }

    public int add(String name, String xml) {
        if (m_workspace == null || m_workspace.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (name == null || name.trim().length() == 0) {
            String message = InternalResource.loadString("name",
                    InternalResource.GlobalStringIsNullOrEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        if (xml == null) {
            xml = "";
        }

        if (this.indexOf(name) >= 0) {
            String message = InternalResource.loadString("name",
                    InternalResource.MapsNameIsOcuupied,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        int index = -1;
        index = MapsNative.jni_Add(m_workspace.getHandle(), name, xml);
        if (index > -1) {
            MapAddedEvent event = new MapAddedEvent(this, name);
            fireAdded(event);
        }
        return index;
    }
    
    public int getCount() {
        if (m_workspace == null || m_workspace.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return MapsNative.jni_GetCount(m_workspace.getHandle());
    }

    public String get(int index) {
        if (m_workspace == null || m_workspace.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (index < 0 || index >= this.getCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

        return MapsNative.jni_GetItem(m_workspace.getHandle(), index);
    }
    
    public boolean remove(int index) {
        if (m_workspace == null || m_workspace.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (index < 0 || index >= this.getCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        String mapName = get(index);
        boolean result = false;
        result = MapsNative.jni_Remove(m_workspace.getHandle(), index);
        return result;
    }

    public boolean remove(String name) {
        if (m_workspace == null || m_workspace.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        boolean result = false;
        int index = this.indexOf(name);
        if (index != -1) {
//            result = MapsNative.jni_Remove(m_workspace.getHandle(), index);
        	// 应该触发删除前和删除后事件，modified by weicd 2010.3.3 12:51
        	result = this.remove(index);
        }
        return result;
    }

    public void clear() {
        if (m_workspace == null || m_workspace.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        MapsNative.jni_Clear(m_workspace.getHandle());
    }
 
    public boolean setMapXML(int index, String xml) {
        if (m_workspace == null || m_workspace.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (index < 0 || index >= this.getCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

        if (xml == null) {
            xml = "";
        }

        return MapsNative.jni_SetMapXML(m_workspace.getHandle(), index, xml);
    }
    
    public int indexOf(String name) {
        if (m_workspace == null || m_workspace.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int index = -1;
        if (name != null && name.trim().length() != 0) {
            index = MapsNative.jni_IndexOf(m_workspace.getHandle(), name);
        }
        return index;
    }
    
    protected void clearHandle() {
        m_workspace = null;
    }
    
    protected void fireAdded(MapAddedEvent event) {
        if (m_mapAddedListeners != null) {
            CopyOnWriteArrayList<MapAddedListener> listeners = m_mapAddedListeners;
            Iterator<MapAddedListener> iter = listeners.iterator();
            while (iter.hasNext()){
                MapAddedListener listener = iter.next();
                listener.mapAdded(event);
            }
        }
    }
}
