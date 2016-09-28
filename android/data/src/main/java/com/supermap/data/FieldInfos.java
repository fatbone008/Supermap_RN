package com.supermap.data;

import java.util.ArrayList;

// @modified by ���ƽ� at 2007��7��20�� ����11ʱ17��54��
// @reason  �ײ��ʵ�ֻ��޸�

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @����
 * @version 2.0
 */
public class FieldInfos extends InternalHandleDisposable {
    //�����޸����ݼ����ֶα���ͨ��DatasetVector
    private DatasetVector m_datasetVector = null;
   
  //��ӹ�ϵ�����ݼ�����ʸ�����ݼ�����ͬ�ȵ�λ 
//    private DatasetRelationship m_datasetRel = null;
    
    //Ϊ�˴�����������⣬�ϱ߻�����Ҫ��һ��ÿ���ֶε�����
    private ArrayList m_fieldInfos = null;
    
    private static Integer m_lock = new Integer(0);


    /**
     * ��ʼ��һ���µ�FieldInfosʵ������ʵ��CountΪ0
     * ����Ķ�����Ա��ͷ�
     */
    public FieldInfos() {
        this.m_fieldInfos = new ArrayList();
        this.setHandle(FieldInfosNative.jni_New(), true);
    }

    /**
     * �������캯��
     * ����ԭ�е�����
     * ����¡ϵͳ�ֶε�ʱ���׳��쳣
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
            //add���¡����
            add(fieldInfos.get(i));
        }

    }

    /**
     * ����ָ��������ʼ��һ��FieldInfos����ʵ��
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
            //add���¡����
            add(fieldInfos[i]);
        }
    }

    //���ظ������ֶ���Ϣ��������Ԫ�ص�����
    public int getCount() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return this.m_fieldInfos.size();
    }

    //�ֶ���Ϣ�����϶�����ĳһ��Ԫ��
    public FieldInfo get(String name) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        //���index==-1��ʾ������
        int index = indexOf(name);
        FieldInfo fieldInfo = null;
        if (index != -1) {
            fieldInfo = get(index);
        }
        return fieldInfo;
    }

    //�ֶ���Ϣ�����϶�����ĳһ��Ԫ��
    public FieldInfo get(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        //���ض��������
        return (FieldInfo)this.m_fieldInfos.get(index);

    }
    
    /**
     * �������ֶ���Ϣ���м���һ��Ԫ��
     * ��ӳɹ�����index�����򷵻�-1
     * ���ݼ�����������ֶ�
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
        //recordset��fieldinfos��ֻ����
//        if (this.m_recordset != null) {
//            String message = InternalResource.loadString(
//                    "add(FieldInfo fieldInfo)",
//                    InternalResource.FieldInfosUnsupported,
//                    InternalResource.BundleName);
//            throw new UnsupportedOperationException(message);
//        }
        int result = -1;
        synchronized (m_lock) {	
	        //�ֶ������ѱ�ʹ��
	        String name = fieldInfo.getName();
	        if (contains(name)) {
	            String message = InternalResource.loadString("fieldInfo",
	                    InternalResource.FieldInfosNameIsOccupied,
	                    InternalResource.BundleName);
	            throw new IllegalArgumentException(message);
	        }
	
	        //�������
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
	        
	      //����ǹ�ϵ�����ݼ�
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

        	//��������ݼ�,�����ݼ�ָ���ȥ
       	    long datasetHandle = 0;
	        if (this.m_datasetVector != null) {
	            datasetHandle = this.m_datasetVector.getHandle();      
	        }
//	        if(this.m_datasetRel != null){
//	        	datasetHandle = this.m_datasetRel.getHandle();
//	        }
	        
	        long handle = FieldInfosNative.jni_Add(getHandle(), fieldInfo.getHandle(),
                    datasetHandle);
	        
//	                     Ϊȷ��ͳһ����Ҫ�����ֶεĵط���ͳһ�ɲ����쳣������ʧ�� [2010-9-21] zhangkai
	        if (handle != 0) {
	            FieldInfo fi = new FieldInfo(handle, this);
	            this.m_fieldInfos.add(fi);
	            
	            resetItemsHandle();
	        	result = this.getCount() - 1;
	        } 
        }
        
        return result;
    }
    
    
     // ��Ӳ����ֶνӿ�(2014/7/19 added by hp)
    /**
     * �������ֶ���Ϣ���в���һ��Ԫ��
     * ��ӳɹ�����true�����򷵻�false
     * ���ݼ�����������ֶ�
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
        	// �ֶζ�Ӧ�����ݼ���Ϊ��ʱ��������룬Ҫ�׳��쳣����.NET��һ��
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
	            // java �� ArrayList �е����·����൱��insert�������Ĳ���֮��
	            // ҲҪ����һ���ϲ�Ĳ��룬������ķ�������֤���²�Ķ�����Ŀ��һ�µġ�
                this.m_fieldInfos.add(index, fi);
	            resetItemsHandle();
	            success = true;
	        } 
            return success;
        }   
    }
    
    //��ӽ����ֶνӿ�(2014/7/23 added by huangkj)
    /**
     * ���ڽ����ֶ���Ϣ��������Ԫ�ص�����λ�á�
     * �ֶ����Ʋ��ִ�Сд
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
        	//�ײ㽻������
	        FieldInfosNative.jni_Exchange(getHandle(), index1, index2);

	        //�ϲ㽻������
	        FieldInfo fieldInfo1 = this.get(index1);
	        FieldInfo fieldInfo2 = this.get(index2);
	        this.m_fieldInfos.set(index1, fieldInfo2);
	        this.m_fieldInfos.set(index2, fieldInfo1);
	        resetItemsHandle();
        }
    }

    //��ӿ����ֶ�����ӿ�(2014/7/23 added by huangkj)
    /**
     * ToArrayִ�п���
     * ����toArray���е��ֶ�
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
    
    //�������ֶνӿ�(2014/7/23 added by huangkj)
    /**
     * ��������ֶ�
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

        //�ײ��������
        synchronized (m_lock) {
	        FieldInfosNative.jni_Clear(getHandle());

	        //�������
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
     * ����ָ�����ƻ������ֵ
     * �ֶ����Ʋ��ִ�Сд
     * ���û�ҵ�����-1
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

  //���ֶ���Ϣ�����Ƴ�Name����Ϊname����
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
     * ���ڴ��ֶ���Ϣ���Ƴ�ָ��������������
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

	        // ��������ݼ�,�����ݼ�ָ���ȥ
	        long datasetHandle = 0;
	        if (this.m_datasetVector != null) {
	        	// add by xuzw 2009-03-10 �����ϵͳ�ֶεĻ�Ҫ�׳��쳣����SmUserID���⣬��.NET��һ��
	        	// modify by xuzw 2010-07-28 ֻ�������ݼ�ʱ���ε�ϵͳ�ֶε�ɾ��
	        	if (fieldInfo.isSystemField()) {
	        		String message = InternalResource.loadString("remove(int index)",
	        				InternalResource.FieldInfosUnsupported,
	        				InternalResource.BundleName);
	        		throw new UnsupportedOperationException(message);
	        	}
	            datasetHandle = this.m_datasetVector.getHandle();
	        }

//	        if (this.m_datasetRel != null) {
//	        	// ��ӹ�ϵ�����ݼ�
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
	            //��Ҫ��������Ϊ������
	            fieldInfo.clearHandle();
	            this.m_fieldInfos.remove(index);
	        }
	        resetItemsHandle();
	        return success;
        }
    }

    /**
     * �����DatasetVector��,���ܱ��ͷ�
     */
    public void dispose() {
        if (m_datasetVector != null) {
            String message = InternalResource.loadString("dispose",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        //��ӹ�ϵ�� 
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
     * ʹ�øù��캯������Ķ����ǲ����Ա��ͷŵ�
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
     * ���صĶ���Ĭ��Ϊ���Ա��ͷŵ�
     * ʹ���߸�����Ҫ�����Ƿ��ܱ��ͷ�
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
     * �������
     * ֻ������գ��������ͷ��ڴ�
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
     * ��ʼ���ֶ�
     * �����ֶ�ָ�룬���ݸ�ָ���ʼ��FieldInfos
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
     * ���ڵײ�ʹ��UGArray���д洢UGFieldInfo����
     * ������������࣬�������������洢�����ܳ��ֵ�ַ����
     * ��ʱ�������Ҫ��ʱ�����������ָ��ĵײ��ֵַhandle
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
