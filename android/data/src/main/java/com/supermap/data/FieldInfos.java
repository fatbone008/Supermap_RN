package com.supermap.data;

import java.util.ArrayList;

// @modified by 李云锦 at 2007年7月20日 上午11时17分54秒
// @reason  底层的实现会修改

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @陈勇
 * @version 2.0
 */
public class FieldInfos extends InternalHandleDisposable {
    //由于修改数据集的字段必须通过DatasetVector
    private DatasetVector m_datasetVector = null;
   
  //添加关系类数据集，与矢量数据集处于同等地位 
//    private DatasetRelationship m_datasetRel = null;
    
    //为了处理连点的问题，上边还是需要存一个每个字段的数组
    private ArrayList m_fieldInfos = null;
    
    private static Integer m_lock = new Integer(0);


    /**
     * 初始化一个新的FieldInfos实例，该实例Count为0
     * 构造的对象可以被释放
     */
    public FieldInfos() {
        this.m_fieldInfos = new ArrayList();
        this.setHandle(FieldInfosNative.jni_New(), true);
    }

    /**
     * 拷贝构造函数
     * 拷贝原有的数据
     * 当克隆系统字段的时候不抛出异常
     * @param fieldInfos FieldInfos
     */
    public FieldInfos(FieldInfos fieldInfos) {
        if (fieldInfos == null || fieldInfos.getHandle() == 0) {
            String message = InternalResource.loadString("fieldInfos",
                    InternalResource.GlobalInvalidConstructorArgument,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        this.m_fieldInfos = new ArrayList();
        this.setHandle(FieldInfosNative.jni_New(), true);

        int len = fieldInfos.getCount();
        for (int i = 0; i < len; i++) {
            //add会克隆对象
            add(fieldInfos.get(i));
        }

    }

    /**
     * 根据指定参数初始化一个FieldInfos对象实例
     * @param fieldInfos FieldInfo[]
     */
    public FieldInfos(FieldInfo[] fieldInfos) {
        if (fieldInfos == null) {
            String message = InternalResource.loadString("fieldInfos",
                    InternalResource.GlobalInvalidConstructorArgument,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        this.m_fieldInfos = new ArrayList();
        this.setHandle(FieldInfosNative.jni_New(), true);

        int len = fieldInfos.length;
        for (int i = 0; i < len; i++) {
            //add会克隆对象
            add(fieldInfos[i]);
        }
    }

    //返回给定的字段信息集集合中元素的总数
    public int getCount() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return this.m_fieldInfos.size();
    }

    //字段信息集集合对象中某一个元素
    public FieldInfo get(String name) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        //如果index==-1表示不存在
        int index = indexOf(name);
        FieldInfo fieldInfo = null;
        if (index != -1) {
            fieldInfo = get(index);
        }
        return fieldInfo;
    }

    //字段信息集集合对象中某一个元素
    public FieldInfo get(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        //返回对象的引用
        return (FieldInfo)this.m_fieldInfos.get(index);

    }
    
    /**
     * 用于在字段信息集中加入一个元素
     * 添加成功返回index，否则返回-1
     * 数据集不容许添加字段
     * @param fieldInfo FieldInfo
     * @return int
     */
    public int add(FieldInfo fieldInfo) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (fieldInfo.getHandle() == 0) {
            String message = InternalResource.loadString("fieldInfo",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        //recordset的fieldinfos是只读的
//        if (this.m_recordset != null) {
//            String message = InternalResource.loadString(
//                    "add(FieldInfo fieldInfo)",
//                    InternalResource.FieldInfosUnsupported,
//                    InternalResource.BundleName);
//            throw new UnsupportedOperationException(message);
//        }
        int result = -1;
        synchronized (m_lock) {	
	        //字段名称已被使用
	        String name = fieldInfo.getName();
	        if (contains(name)) {
	            String message = InternalResource.loadString("fieldInfo",
	                    InternalResource.FieldInfosNameIsOccupied,
	                    InternalResource.BundleName);
	            throw new IllegalArgumentException(message);
	        }
	
	        //如果数据
	        if (this.m_datasetVector != null) {
				if (fieldInfo.isSystemField()) {
					String message = InternalResource.loadString(
							"add(FieldInfo fieldInfo)",
							InternalResource.FieldInfosUnsupported,
							InternalResource.BundleName);
					throw new UnsupportedOperationException(message);
				}
				if (fieldInfo.isRequired()
						&& fieldInfo.getDefaultValue().equals("")) {
					String message = InternalResource
							.loadString(
									"fieldInfo",
									InternalResource.FieldInfosRequiredFeildInfoMustBeSettedDefaultValue,
									InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
			}
	        
	      //如果是关系类数据集
//	        if (this.m_datasetRel != null) {
//				if (fieldInfo.isSystemField()) {
//					String message = InternalResource.loadString(
//							"add(FieldInfo fieldInfo)",
//							InternalResource.FieldInfosUnsupported,
//							InternalResource.BundleName);
//					throw new UnsupportedOperationException(message);
//				}
////				if (!this.m_datasetRel.isAvailableFieldName(fieldInfo.getName())) {
////					String message = InternalResource.loadString("fieldInfo",
////							InternalResource.FieldInfoNameIsNotAvaliable,
////							InternalResource.BundleName);
////					throw new IllegalArgumentException(message);
////				}
//				if (fieldInfo.isRequired()
//						&& fieldInfo.getDefaultValue().equals("")) {
//					String message = InternalResource
//							.loadString(
//									"fieldInfo",
//									InternalResource.FieldInfosRequiredFeildInfoMustBeSettedDefaultValue,
//									InternalResource.BundleName);
//					throw new IllegalArgumentException(message);
//				}
//			}

        	//如果是数据集,传数据集指针进去
       	    long datasetHandle = 0;
	        if (this.m_datasetVector != null) {
	            datasetHandle = this.m_datasetVector.getHandle();      
	        }
//	        if(this.m_datasetRel != null){
//	        	datasetHandle = this.m_datasetRel.getHandle();
//	        }
	        
	        long handle = FieldInfosNative.jni_Add(getHandle(), fieldInfo.getHandle(),
                    datasetHandle);
	        
//	                     为确保统一，需要创建字段的地方，统一成不抛异常，返回失败 [2010-9-21] zhangkai
	        if (handle != 0) {
	            FieldInfo fi = new FieldInfo(handle, this);
	            this.m_fieldInfos.add(fi);
	            
	            resetItemsHandle();
	        	result = this.getCount() - 1;
	        } 
        }
        
        return result;
    }
    
    
     // 添加插入字段接口(2014/7/19 added by hp)
    /**
     * 用于在字段信息集中插入一个元素
     * 添加成功返回true，否则返回false
     * 数据集不容许插入字段
     * @param fieldInfo FieldInfo
     * @return boolean
     */
    public boolean insert(int index, FieldInfo fieldInfo) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        if (fieldInfo.getHandle() == 0) {
            String message = InternalResource.loadString("fieldInfo",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        if (this.m_datasetVector != null) {
        	// 字段对应的数据集不为空时不允许插入，要抛出异常，与.NET组一致
    		String message = InternalResource.loadString("insert(int index)",
    				InternalResource.FieldInfosUnsupported,
    				InternalResource.BundleName);
    		throw new UnsupportedOperationException(message);
        }
        
        synchronized (m_lock) {	
        	boolean success = false;
        	long handle = FieldInfosNative.jni_Insert(getHandle(), 
            		index,fieldInfo.getHandle());
            
	        if (handle != 0) {
	            FieldInfo fi = new FieldInfo(handle, this);
	            // java 的 ArrayList 中的以下方法相当于insert。调类库的插入之后
	            // 也要调用一下上层的插入，即下面的方法，保证上下层的对象数目是一致的。
                this.m_fieldInfos.add(index, fi);
	            resetItemsHandle();
	            success = true;
	        } 
            return success;
        }   
    }
    
    //添加交换字段接口(2014/7/23 added by huangkj)
    /**
     * 用于交换字段信息集中两个元素的索引位置。
     * 字段名称部分大小写
     * @param index1 int
     * @param index2 int
     * @return void
     */
    public void exchange(int index1, int index2) {

        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_datasetVector != null) {
            String message = InternalResource.loadString(
                    "exchange(int index1, int index2)",
                    InternalResource.FieldInfosUnsupported,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }

        if (index1 < 0 || index1 >= this.getCount()) {
            String message = InternalResource.loadString("index1",
                    InternalResource.GlobalEnumInvalidDerivedClass,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

        if (index2 < 0 || index2 >= this.getCount()) {
            String message = InternalResource.loadString("index2",
                    InternalResource.GlobalEnumInvalidDerivedClass,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

        if (index1 == index2) {
            return;
        }

        synchronized (m_lock) {
        	//底层交换数据
	        FieldInfosNative.jni_Exchange(getHandle(), index1, index2);

	        //上层交换数据
	        FieldInfo fieldInfo1 = this.get(index1);
	        FieldInfo fieldInfo2 = this.get(index2);
	        this.m_fieldInfos.set(index1, fieldInfo2);
	        this.m_fieldInfos.set(index2, fieldInfo1);
	        resetItemsHandle();
        }
    }

    //添加拷贝字段数组接口(2014/7/23 added by huangkj)
    /**
     * ToArray执行拷贝
     * 可以toArray所有的字段
     * @return FieldInfo[]
     */
    public FieldInfo[] toArray() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        synchronized (m_lock) {
	        int len = getCount();
	        FieldInfo[] infos = new FieldInfo[len];
	        for (int i = 0; i < len; i++) {
	            infos[i] = new FieldInfo(this.get(i));
	        }
	        return infos;
        }
    }
    
    //添加晴空字段接口(2014/7/23 added by huangkj)
    /**
     * 清除所有字段
     */
    public void clear() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_datasetVector != null) {
            String message = InternalResource.loadString("clear()",
                    InternalResource.FieldInfosUnsupported,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }

        //底层数据清空
        synchronized (m_lock) {
	        FieldInfosNative.jni_Clear(getHandle());

	        //清空内容
	        FieldInfo fieldInfo;
	        if (this.m_fieldInfos != null) {
	            int size = this.m_fieldInfos.size();
	            for (int i = 0; i < size; i++) {
	                fieldInfo = (FieldInfo)this.m_fieldInfos.get(i);
	                fieldInfo.clearHandle();
	            }
	        }

	        if (this.m_fieldInfos != null) {
	        this.m_fieldInfos.clear();
	        }
        }
    }
    
    /**
     * 根据指定名称获得索引值
     * 字段名称部分大小写
     * 如果没找到返回-1
     * @param name String
     * @return int
     */
    public int indexOf(String name) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        synchronized (m_lock) {
	        int index = -1;
	        if (name != null && name.trim().length() != 0) {
	            index = FieldInfosNative.jni_IndexOf(getHandle(), name);
	        }
        return index;
        }
    }

    int indexOf(FieldInfo fieldInfo) {
        int index = -1;
        if (fieldInfo != null && fieldInfo.getHandle() != 0) {
            if (this.m_fieldInfos != null) {
                index = m_fieldInfos.indexOf(fieldInfo);
            }
        }
        return index;
    }

  //从字段信息集中移除Name属性为name的项
    public boolean remove(String name) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

//        if (this.m_recordset != null) {
//            String message = InternalResource.loadString("remove(String name)",
//                    InternalResource.FieldInfosUnsupported,
//                    InternalResource.BundleName);
//            throw new UnsupportedOperationException(message);
//        }
        synchronized (m_lock) {
	        int index = indexOf(name);
	        boolean result = false;
	        if (index != -1) {
	            result = remove(index);
	        }
	        return result;
        }
    }

    /**
     * 用于从字段信息集移除指定的索引处的项
     * @param index int
     * @return boolean
     */
    public boolean remove(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

//        if (this.m_recordset != null) {
//            String message = InternalResource.loadString("remove(int index)",
//                    InternalResource.FieldInfosUnsupported,
//                    InternalResource.BundleName);
//            throw new UnsupportedOperationException(message);
//        }
        synchronized (m_lock) {
	        FieldInfo fieldInfo = (FieldInfo)this.m_fieldInfos.get(index);

	        // 如果是数据集,传数据集指针进去
	        long datasetHandle = 0;
	        if (this.m_datasetVector != null) {
	        	// add by xuzw 2009-03-10 如果是系统字段的话要抛出异常，但SmUserID除外，与.NET组一致
	        	// modify by xuzw 2010-07-28 只当有数据集时才盼到系统字段的删除
	        	if (fieldInfo.isSystemField()) {
	        		String message = InternalResource.loadString("remove(int index)",
	        				InternalResource.FieldInfosUnsupported,
	        				InternalResource.BundleName);
	        		throw new UnsupportedOperationException(message);
	        	}
	            datasetHandle = this.m_datasetVector.getHandle();
	        }

//	        if (this.m_datasetRel != null) {
//	        	// 添加关系类数据集
//	        	if (fieldInfo.isSystemField()) {
//	        		String message = InternalResource.loadString("remove(int index)",
//	        				InternalResource.FieldInfosUnsupported,
//	        				InternalResource.BundleName);
//	        		throw new UnsupportedOperationException(message);
//	        	}
//	            datasetHandle = this.m_datasetRel.getHandle();
//	        }

	        boolean success = FieldInfosNative.jni_Remove(getHandle(), index,
	                datasetHandle);
	        if (success) {
	            //先要将对象设为不可用
	            fieldInfo.clearHandle();
	            this.m_fieldInfos.remove(index);
	        }
	        resetItemsHandle();
	        return success;
        }
    }

    /**
     * 如果是DatasetVector的,不能被释放
     */
    public void dispose() {
        if (m_datasetVector != null) {
            String message = InternalResource.loadString("dispose",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        //添加关系类 
//        if (m_datasetRel != null) {
//            String message = InternalResource.loadString("dispose",
//                    InternalResource.HandleUndisposableObject,
//                    InternalResource.BundleName);
//            throw new IllegalArgumentException(message);
//        }
        synchronized (m_lock) {
	        if (getHandle() != 0) {
	            FieldInfosNative.jni_Delete(getHandle());
	            clearHandle();
	        }
        }
    }

    /**
     * 使用该构造函数构造的对象是不可以被释放的
     * @param handle
     * @param datasetVector
     */
    FieldInfos(long handle, DatasetVector datasetVector) {
        if (datasetVector == null || datasetVector.getHandle() == 0) {
            String message = InternalResource.loadString("datasetVector",
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
        this.setIsDisposable(false);
        reset(handle);
        this.m_datasetVector = datasetVector;
    }

    /**
     * 返回的对象默认为可以被释放的
     * 使用者根据需要调整是否能被释放
     * @param handle
     */
    FieldInfos(long handle) {
        if (handle == 0) {
            String message = InternalResource.loadString("handle",
                    InternalResource.GlobalInvalidConstructorArgument,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        this.setIsDisposable(true);
        reset(handle);
    }

    DatasetVector getDataset() {
        return m_datasetVector;
    }

    /**
     * 清空数据
     * 只负责清空，不负责释放内存
     */
    protected void clearHandle() {
        if (this.m_fieldInfos != null) {
            int size = m_fieldInfos.size();
            for (int i = 0; i < size; i++) {
                FieldInfo fieldInfo = (FieldInfo) m_fieldInfos.get(i);
                fieldInfo.clearHandle();
            }
        }
        this.m_datasetVector = null;
//        this.m_datasetRel = null;
        this.setHandle(0);
    }

    /**
     * 初始化字段
     * 传入字段指针，根据该指针初始化FieldInfos
     * @param handle long
     */
    private void reset(long handle) {
        this.m_fieldInfos = new ArrayList();
        this.setHandle(handle, false);
        int count = FieldInfosNative.jni_GetCount(handle);
        long[] handles = new long[count];
        FieldInfosNative.jni_GetFieldInfos(handle, handles);
        FieldInfo fieldInfo = null;
        for (int i = 0; i < handles.length; i++) {
            fieldInfo = new FieldInfo(handles[i], this);
            this.m_fieldInfos.add(fieldInfo);
        }
    }
    /**
     * 由于底层使用UGArray进行存储UGFieldInfo对象
     * 当数组对象增多，由于数组连续存储，可能出现地址搬移
     * 此时组件层需要及时更新其对象所指向的底层地址值handle
     */
    private void resetItemsHandle(){
    	long[] newHandles = new long[getCount()];
    	FieldInfosNative.jni_GetFieldInfos(getHandle(), newHandles);
    	for (int i = 0; i < newHandles.length; i++) {
			FieldInfo fieldInfo = (FieldInfo)m_fieldInfos.get(i);
			fieldInfo.setHandle(newHandles[i], false);
    	}
    }

    private boolean contains(String name) {
        if (indexOf(name) != -1) {
            return true;
        } else {
            return false;
        }
    }
    
    protected static FieldInfos createInstance(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new FieldInfos(handle);
	}

	protected static void clearHandle(FieldInfos fieldInfos) {
		// TODO Auto-generated method stub
		fieldInfos.clearHandle();
	}
}
