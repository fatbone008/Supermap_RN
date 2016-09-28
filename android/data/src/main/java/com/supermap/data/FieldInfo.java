package com.supermap.data;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * ����field��fieldinfos���Ǵ洢��ջ�ϣ���ˣ�fieldinfo�б�����datasetvector��ָ��
 * ���⣬�������¼��Ψһ��ʶ����name
 * ������û��Զ���ģ���һ��ʶ���Ž������ԣ��������ڴӵײ�ȡ����
 * ע�⣺Name�������ظ���Caption�����ظ�
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @ ����
 * @version 2.0
 */
public class FieldInfo extends InternalHandleDisposable {
    /**
     * ��¼FieldInfo������FieldInfos
     */
    private FieldInfos m_fieldInfos = null;

    /**
     * �û��Զ����
     * �չ��캯��,ֻ�Ƿ���ռ�
     *
     */
    public FieldInfo() {
        this.setHandle(FieldInfoNative.jni_New(), true);
        reset();
    }

    /**
     * �������캯��,IsSystemField���Բ�����,���Ǹ����ֶ������жϵ�
     * ϵͳ�ֶβ��ܿ���
     * ���ÿ��������û��Լ�new�Ķ���
     * �ײ�û���ṩ�������캯����Ҳû�С�=��������
     * @param fieldInfo FieldInfo
     */
    public FieldInfo(FieldInfo fieldInfo) {
        if (fieldInfo == null || fieldInfo.getHandle() == 0) {
            String message = InternalResource.loadString("fieldInfo",
                    InternalResource.GlobalInvalidConstructorArgument,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        this.setHandle(FieldInfoNative.jni_New(), true);

        this.setZeroLengthAllowed(fieldInfo.isZeroLengthAllowed());
        this.setCaption(fieldInfo.getCaption());
        this.setDefaultValue(fieldInfo.getDefaultValue());
//        this.setFormat(fieldInfo.getFormat());

        //Դ�ֶ���Ϣ��ҪôΪ��Ҫô���ǺϷ���
        if (fieldInfo.getName().compareTo("") != 0) {
            this.setName(fieldInfo.getName());
        }
//        if (fieldInfo.getPrecision() > 0) {
//            this.setPrecision(fieldInfo.getPrecision());
//        }

        this.setRequired(fieldInfo.isRequired());

//        if (fieldInfo.getScale() > 0) {
//            this.setScale(fieldInfo.getScale());
//        }

        if (fieldInfo.getMaxLength() > 0) {
            this.setMaxLength(fieldInfo.getMaxLength());
        }
//        this.setSourceField(fieldInfo.getSourceField());
//        this.setSourceTable(fieldInfo.getSourceTable());
        this.setType(fieldInfo.getType());
    }
    
    /**
     * ����ָ�������Ƽ����͹���һ��
     * @param name �ֶε�����
     * @param type �ֶ�����
     */
    public FieldInfo(String name, FieldType type) {
    	this();
    	this.setName(name);
    	this.setType(type);
    }

    /**
     * �ڲ�ʹ�õĹ��캯��
     * �øú�������Ķ��󲻿��Ա��ͷ�
     * @param handle long
     * @param fieldInfos FieldInfos
     */
    FieldInfo(long handle, FieldInfos fieldInfos) {
        //���ڼ��϶����FieldInfo�����Ա��ͷ�
        this.setHandle(handle, false);
        this.m_fieldInfos = fieldInfos;
    }
    
    /**
     * �ڲ�ʹ�ù��캯��������ת���л��õ�
     * @param handle
     */
    FieldInfo(long handle) {
    	this.setHandle(handle, false);
    }


    /**
     * �жϵ�ǰ�ֶ��Ƿ�����Ϊ0����,ֻ���ı��ֶ���Ч
     * @return boolean
     */
    public boolean isZeroLengthAllowed() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        resetUGCHandle();
        if (this.m_fieldInfos != null && this.m_fieldInfos.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return FieldInfoNative.jni_GetIsZeroLengthAllowed(getHandle());
    }

    /**
     * �����ֶ��Ƿ�����0����,ֻ���ı��ֶ���Ч
     * @param value boolean
     */
    public void setZeroLengthAllowed(boolean value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        resetUGCHandle();
        //������ֶ���Ϣ�������ݼ�
        if (m_fieldInfos != null && m_fieldInfos.getDataset().getHandle() != 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        FieldInfoNative.jni_SetIsZeroLengthAllowed(getHandle(), value);
    }

    /**
     * �õ��ֶ���ʾ����
     * @return String
     */
    public String getCaption() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        resetUGCHandle();
        if (this.m_fieldInfos != null && this.m_fieldInfos.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return FieldInfoNative.jni_GetCaption(getHandle(), 0);
    }


    /**
     * �����ֶ���ʾ����
     * ���ֶο�������
     * @param value String
     */
    public void setCaption(String value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (value == null || value.trim().length() == 0) {
            String message = InternalResource.loadString("value",
                    InternalResource.GlobalStringIsNullOrEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        resetUGCHandle();
        //������ֶ���Ϣ�������ݼ�,��Ҫ�������ݼ�������,����ֱ�Ӵ�����
        if (this.m_fieldInfos != null&& m_fieldInfos.getDataset()!=null &&
            m_fieldInfos.getDataset().getHandle() != 0) {
            FieldInfoNative.jni_SetCaption(getHandle(), value,
                                           m_fieldInfos.getDataset().getHandle());
        } else {
            FieldInfoNative.jni_SetCaption(getHandle(), value, 0);
        }
    }

    /**
     * �õ��ֶ�Ĭ��ֵ
     * @return String
     */
    public String getDefaultValue() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        resetUGCHandle();
        if (this.m_fieldInfos != null && this.m_fieldInfos.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return FieldInfoNative.jni_GetDefaultValue(getHandle());
    }

    /**
     * �����ֶ�Ĭ��ֵ
     * @param value String
     */
    public void setDefaultValue(String value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        resetUGCHandle();
        //������ֶ���Ϣ�������ݼ�
        if (m_fieldInfos != null && m_fieldInfos.getDataset().getHandle() != 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (value == null) {
            value = "";
        }
        FieldInfoNative.jni_SetDefaultValue(getHandle(), value);
    }


    /**
     * �õ��ֶ�����
     * @return String
     */
    public String getName() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        resetUGCHandle();
        if (this.m_fieldInfos != null && this.m_fieldInfos.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return FieldInfoNative.jni_GetName(getHandle());
    }

    /**
     * �����ֶ�����
     * ��FieldInfo����DatasetVector��ʱ��ż���Ƿ���sm��ͷ���Ƿ�ϵͳ�ֶ�,���򲻼��
     * @param value String
     */
    public void setName(String value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (value == null || value.trim().length() == 0) {
            String message = InternalResource.loadString("value",
                    InternalResource.GlobalStringIsNullOrEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        resetUGCHandle();

        //����Ƚ�����
        //�������Fieldinfos,���ֲ�����DatasetVector,��ֻ��Ҫ��������Ƿ�����Ҫ�󼴿��޸�
        //�������Fieldinfos,������DatasetVector,�����޸�
        //������������޸�
        if (this.m_fieldInfos != null) {
            if (this.m_fieldInfos.getHandle() == 0) {
                String message = InternalResource.loadString("",
                        InternalResource.FieldInfoFieldInfosIsInvalid,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            } else {
                if (m_fieldInfos.getDataset() == null) {
                    if (this.m_fieldInfos.indexOf(value) != -1) {
                        String message = InternalResource.loadString("value",
                                InternalResource.FieldInfoNameIsNotAvaliable,
                                InternalResource.BundleName);
                        throw new IllegalArgumentException(message);
                    }
                } else {
                    String message = InternalResource.loadString("",
                            InternalResource.FieldInfoFieldInfosIsInvalid,
                            InternalResource.BundleName);
                    throw new IllegalStateException(message);

                }
            }
        }
        //����ȹر��ж� by  liucq 2012-08-09
//        if (value.toUpperCase().startsWith("SM")) {
//			String message = InternalResource.loadString("setName(String value)",
//					InternalResource.FieldInfosUnsupported,
//					InternalResource.BundleName);
//			throw new UnsupportedOperationException(message);
//		}

        FieldInfoNative.jni_SetName(getHandle(), value);
    }


    /**
     * �ֶ��Ƿ�Ϊ�����ֶ�
     * @return boolean
     */
    public boolean isRequired() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        resetUGCHandle();
        if (this.m_fieldInfos != null && this.m_fieldInfos.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return FieldInfoNative.jni_GetIsRequired(getHandle());
    }

    /**
     * �����ֶ��Ƿ�Ϊ�����ֶΣ�����Ǳ����ֶΣ�����Ӽ�¼��ʱ�򣬸��ֶα��븳ֵ
     * @param value boolean
     */
    public void setRequired(boolean value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        resetUGCHandle();
        //������ֶ���Ϣ�������ݼ�
        if (m_fieldInfos != null && m_fieldInfos.getDataset().getHandle() != 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        FieldInfoNative.jni_SetIsRequired(getHandle(), value);
    }


    /**
     * �ı��ֶ���󳤶�
     * @return int
     */
    public int getMaxLength() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_fieldInfos != null && this.m_fieldInfos.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        resetUGCHandle();
        return FieldInfoNative.jni_GetMaxLength(getHandle());
    }

    /**
     * �����ı��ֶ���󳤶�
     * @param value int
     */
    public void setMaxLength(int value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (value < 0) {
            String message = InternalResource.loadString("value:" +
                    String.valueOf(value),
                    InternalResource.FieldInfoMaxLengthShouldntBeNegative,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        resetUGCHandle();
        //������ֶ���Ϣ�������ݼ�
        if (m_fieldInfos != null && m_fieldInfos.getDataset().getHandle() != 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        FieldInfoNative.jni_SetMaxLength(getHandle(), value);
    }

    /**
     * �ֶ����ͣ��ο�FieldType�������ö��
     * @return int
     */
    public FieldType getType() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        resetUGCHandle();
        if (this.m_fieldInfos != null && this.m_fieldInfos.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        int ugcType = FieldInfoNative.jni_GetType(getHandle());
        //����SFC�ı�ע����
        if(ugcType==12){
        	ugcType =10;
        }
        return (FieldType) Enum.parseUGCValue(FieldType.class, ugcType);
    }

    /**
     * �����ֶ�����
     * @param value int
     */
    public void setType(FieldType value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        resetUGCHandle();
        //������ֶ���Ϣ�������ݼ�
        if (m_fieldInfos != null && m_fieldInfos.getDataset().getHandle() != 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        FieldInfoNative.jni_SetType(getHandle(), value.getUGCValue());
    }

    /**
     * ���ֶ��Ƿ�ΪSuperMapϵͳ�ֶΣ�����name�жϣ���Sm��ͷ�ģ���ȥSmUserID������Ϊ��
     * @return boolean
     */
    public boolean isSystemField() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        resetUGCHandle();
        if (this.m_fieldInfos != null && this.m_fieldInfos.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return FieldInfoNative.jni_GetIsSystemField(getHandle());
    }

    /**
     * �ͷŸö���ķ��й���Դ
     */
    public void dispose() {
        //ֻ�ͷ��Լ������Style
        if (!super.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (!(super.getHandle() == 0)) {
            resetUGCHandle();
            FieldInfoNative.jni_Delete(super.getHandle());
            setHandle(0);
        }
    }

    /**
     * ��fieldInfos��dispose��ʱ���remove�ĵ���
     * ֻ����ֶΣ����ͷ��ڴ�
     */
    protected void clearHandle() {
        this.m_fieldInfos = null;
        this.setHandle(0);
    }

    void reset() {
        if (getHandle() != 0) {
            FieldInfoNative.jni_Reset(getHandle());
            this.setMaxLength(255);
        }
    }

    //����fieldInfo��UGC������Array��ŵģ���ȥ����ѯDatasetVectorʱ����fieldInfo��ָ����ܾ����ˡ�
    //����Ҫ�ڵ���ÿһ������ǰ�����µõ�һ��fieldInfo��ָ�롣
    private void resetUGCHandle() {
        if (m_fieldInfos != null && m_fieldInfos.getDataset() != null &&
            m_fieldInfos.getDataset().getHandle() != 0) {
            int index = m_fieldInfos.indexOf(this);
            long infosHandle = m_fieldInfos.getHandle();
            long newHandle = FieldInfosNative.jni_GetFieldInfoByIndex(
                    infosHandle, index);
            if (newHandle != 0) {
                //ע���˴���fieldInfo����m_fieldInfos�õ��ģ������ͷš�
                this.setHandle(newHandle, false);
            }
        }
    }
    
    //��ӿ�¡�ֶνӿ�(2014/7/23 added by huangkj)
    /**
     * ���Ƶ�ǰ�ֶζ���
     * ϵͳ�ֶβ������¡
     * @return FieldInfo
     */
    public FieldInfo clone() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        resetUGCHandle();
        return new FieldInfo(this);
    }
    
    protected static FieldInfo createInstance(long handle){
    	return new FieldInfo(handle);
    }
}
