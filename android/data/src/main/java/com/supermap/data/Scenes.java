package com.supermap.data;

public class Scenes {
	
	private Workspace m_workspace;

	Scenes(Workspace workspace) {
		m_workspace = workspace;
	}
	
	/**
	 * 返回三维场景集合对象中指定索引的三维场景的名字
	 * @param index
	 * @return
	 */
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
		return ScenesNative.jni_GetItem(m_workspace.getHandle(), index);
	}
	
	/**
	 * 返回给定的三维场景集合中三维场景对象的总数
	 * @return
	 */
	public int getCount() {
        if (m_workspace == null || m_workspace.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return ScenesNative.jni_GetCount(m_workspace.getHandle());
	}
	
	/**
	 * 采用用户传入的名称作为三维场景名
	 * @param name
	 * @param xml
	 * @return
	 */
	public int add(String name,String xml) {
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
                    InternalResource.ScenesNameIsOcuupied,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        int index = -1;
        index = ScenesNative.jni_Add(m_workspace.getHandle(), name, xml);
//        if (index > -1) {
//        	SceneAddedEvent addedEvent = new SceneAddedEvent(this, name);
//        	this.fireAdded(addedEvent);
//        }
        return index;
	}
	
	/**
	 * 采用用户传入的名称作为三维场景名
	 * @param index
	 * @param name
	 * @param xml
	 * @return
	 */
	public boolean insert(int index,String name,String xml) {
        if (m_workspace == null || m_workspace.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (index < 0 || index > this.getCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
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
                    InternalResource.ScenesNameIsOcuupied,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        return ScenesNative.jni_Insert(m_workspace.getHandle(), index, name, xml);
	}
	
	/**
	 * 只修改指定序号三维场景的内容，不修改其名字
	 * @param index
	 * @param xml
	 * @return
	 */
	public boolean setSceneXML(int index,String xml) {
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
        return ScenesNative.jni_SetSceneXML(m_workspace.getHandle(), index, xml);
	}
	
	/**
	 * 只修改指定名称三维场景的内容
	 * @param name
	 * @param xml
	 * @return
	 */
	public boolean setSceneXML(String name,String xml) {
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

        int index = this.indexOf(name);
        if (index == -1) {
            String message = InternalResource.loadString("name",
                    InternalResource.SceneNameIsNotInScenes,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        return this.setSceneXML(index, xml);
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public String getSceneXML(int index) {
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
        
        return ScenesNative.jni_GetSceneXML(m_workspace.getHandle(), index);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public String getSceneXML(String name) {
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
        int index = this.indexOf(name);
        if (index == -1) {
            String message = InternalResource.loadString("name",
                    InternalResource.SceneNameIsNotInScenes,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        
        return ScenesNative.jni_GetSceneXML(m_workspace.getHandle(), index);
	}
	
	/**
	 * 删除工作空间中保存的某个三维场景。成功返回 True，否则返回 False。
	 * 该方法并不删除数据，不会影响数据源和数据集。
	 * @param index
	 * @return
	 */
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
//        String sceneName = this.get(index);
//        SceneRemovingEvent removingEvent = new SceneRemovingEvent(this, sceneName);
//        this.fireRemoving(removingEvent);
//        if (removingEvent.getCancel()) {
//        	return false;
//        }
        
        boolean bResult = false;
        bResult = ScenesNative.jni_Remove(m_workspace.getHandle(), index);
//        if (bResult) {
//        	SceneRemovedEvent removedEvent = new SceneRemovedEvent(this, sceneName);
//        	this.fireRemoved(removedEvent);
//        }
        return bResult;
	}
	
	/**
	 * 删除工作空间中保存的某个三维场景。成功返回 True，否则返回 False。
	 * 该方法并不删除数据，不会影响数据源和数据集。
	 * @param name
	 * @return
	 */
	public boolean remove(String name) {
        if (m_workspace == null || m_workspace.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        boolean bResult = false;
        int index = this.indexOf(name);
        if (index != -1) {
        	bResult = this.remove(index);
        }
        return bResult;
	}
	
	/**
	 * 删除工作空间所有保存的三维场景
	 * 该方法并不删除数据，也不会影响数据源和数据集。
	 */
	public void clear() {
        if (m_workspace == null || m_workspace.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
//        SceneClearingEvent clearingEvent = new SceneClearingEvent(this);
//        this.fireClearing(clearingEvent);
//        if (clearingEvent.getCancel()) {
//        	return;
//        }
//        int count = this.getCount();
        ScenesNative.jni_Clear(m_workspace.getHandle());
//        SceneClearedEvent clearedEvent = new SceneClearedEvent(this, count);
//        this.fireCleared(clearedEvent);
	}
	
	/**
	 * 得到指定名称三维场景的索引
	 * @param name
	 * @return
	 */
	public int indexOf(String name) {
        if (m_workspace == null || m_workspace.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int index = -1;
        if (name != null && name.trim().length() != 0) {
            index = ScenesNative.jni_IndexOf(m_workspace.getHandle(), name);
        }
        return index;
	}
	
	/**
	 * 根据给定的名称生成一个在地图集里没有使用的地图名字
	 * @param name
	 * @return
	 */
	public String getAvailableSceneName(String name) {
        if (m_workspace == null || m_workspace.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (name == null) {
            name = "";
        }
        return ScenesNative.jni_GetAvailableSceneName(m_workspace.getHandle(), name);
	}
    
    protected void clearHandle() {
        m_workspace = null;
    }
	
}
