package com.supermap.data;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * 由于field与fieldinfos都是存储在栈上，因此，fieldinfo中必须存放datasetvector的指针
 * 此外，还必须记录其唯一标识符号name
 * 如果是用户自定义的，这一标识符号将被忽略，否则用于从底层取数据
 * 注意：Name不可以重复，Caption可以重复
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @ 陈勇
 * @version 2.0
 */
public class FieldInfo extends InternalHandleDisposable {
    /**
     * 记录FieldInfo所属的FieldInfos
     */
    private FieldInfos m_fieldInfos = null;

    /**
     * 用户自定义的
     * 空构造函数,只是分配空间
     *
     */
    public FieldInfo() {
        this.setHandle(FieldInfoNative.jni_New(), true);
        reset();
    }

    /**
     * 拷贝构造函数,IsSystemField属性不能设,它是根据字段名来判断的
     * 系统字段不能拷贝
     * 调用拷贝属于用户自己new的对象
     * 底层没有提供拷贝构造函数，也没有“=”操作符
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

        //源字段信息名要么为空要么就是合法的
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
     * 根据指定的名称及类型构造一个
     * @param name 字段的名称
     * @param type 字段类型
     */
    public FieldInfo(String name, FieldType type) {
    	this();
    	this.setName(name);
    	this.setType(type);
    }

    /**
     * 内部使用的构造函数
     * 用该函数构造的对象不可以被释放
     * @param handle long
     * @param fieldInfos FieldInfos
     */
    FieldInfo(long handle, FieldInfos fieldInfos) {
        //属于集合对象的FieldInfo不可以被释放
        this.setHandle(handle, false);
        this.m_fieldInfos = fieldInfos;
    }
    
    /**
     * 内部使用构造函数，数据转换中会用到
     * @param handle
     */
    FieldInfo(long handle) {
    	this.setHandle(handle, false);
    }


    /**
     * 判断当前字段是否允许为0长度,只对文本字段有效
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
     * 设置字段是否允许0长度,只对文本字段有效
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
        //如果该字段信息属于数据集
        if (m_fieldInfos != null && m_fieldInfos.getDataset().getHandle() != 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        FieldInfoNative.jni_SetIsZeroLengthAllowed(getHandle(), value);
    }

    /**
     * 得到字段显示名称
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
     * 设置字段显示名称
     * 该字段可以设置
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
        //如果该字段信息属于数据集,需要调用数据集来处理,否则直接处理即可
        if (this.m_fieldInfos != null&& m_fieldInfos.getDataset()!=null &&
            m_fieldInfos.getDataset().getHandle() != 0) {
            FieldInfoNative.jni_SetCaption(getHandle(), value,
                                           m_fieldInfos.getDataset().getHandle());
        } else {
            FieldInfoNative.jni_SetCaption(getHandle(), value, 0);
        }
    }

    /**
     * 得到字段默认值
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
     * 设置字段默认值
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
        //如果该字段信息属于数据集
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
     * 得到字段名称
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
     * 设置字段名称
     * 当FieldInfo属于DatasetVector的时候才检查是否以sm打头和是否系统字段,否则不检查
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

        //这里比较特殊
        //如果属于Fieldinfos,而又不属于DatasetVector,则只需要检查名字是否满足要求即可修改
        //如果属于Fieldinfos,又属于DatasetVector,则不能修改
        //其他情况可以修改
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
        //这个先关闭判断 by  liucq 2012-08-09
//        if (value.toUpperCase().startsWith("SM")) {
//			String message = InternalResource.loadString("setName(String value)",
//					InternalResource.FieldInfosUnsupported,
//					InternalResource.BundleName);
//			throw new UnsupportedOperationException(message);
//		}

        FieldInfoNative.jni_SetName(getHandle(), value);
    }


    /**
     * 字段是否为必填字段
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
     * 设置字段是否为必填字段，如果是必填字段，在添加记录的时候，该字段必须赋值
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
        //如果该字段信息属于数据集
        if (m_fieldInfos != null && m_fieldInfos.getDataset().getHandle() != 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        FieldInfoNative.jni_SetIsRequired(getHandle(), value);
    }


    /**
     * 文本字段最大长度
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
     * 设置文本字段最大长度
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
        //如果该字段信息属于数据集
        if (m_fieldInfos != null && m_fieldInfos.getDataset().getHandle() != 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        FieldInfoNative.jni_SetMaxLength(getHandle(), value);
    }

    /**
     * 字段类型，参考FieldType类型里的枚举
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
        //兼容SFC的备注类型
        if(ugcType==12){
        	ugcType =10;
        }
        return (FieldType) Enum.parseUGCValue(FieldType.class, ugcType);
    }

    /**
     * 设置字段类型
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
        //如果该字段信息属于数据集
        if (m_fieldInfos != null && m_fieldInfos.getDataset().getHandle() != 0) {
            String message = InternalResource.loadString("",
                    InternalResource.FieldInfoFieldInfosIsInvalid,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        FieldInfoNative.jni_SetType(getHandle(), value.getUGCValue());
    }

    /**
     * 该字段是否为SuperMap系统字段，根据name判断，以Sm打头的（除去SmUserID）都认为是
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
     * 释放该对象的非托管资源
     */
    public void dispose() {
        //只释放自己定义的Style
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
     * 由fieldInfos在dispose的时候和remove的调用
     * 只清空字段，不释放内存
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

    //由于fieldInfo在UGC中是用Array存放的，而去当查询DatasetVector时，则fieldInfo的指针可能就乱了。
    //故需要在调用每一个方法前都重新得到一下fieldInfo的指针。
    private void resetUGCHandle() {
        if (m_fieldInfos != null && m_fieldInfos.getDataset() != null &&
            m_fieldInfos.getDataset().getHandle() != 0) {
            int index = m_fieldInfos.indexOf(this);
            long infosHandle = m_fieldInfos.getHandle();
            long newHandle = FieldInfosNative.jni_GetFieldInfoByIndex(
                    infosHandle, index);
            if (newHandle != 0) {
                //注：此处的fieldInfo是由m_fieldInfos得到的，不可释放。
                this.setHandle(newHandle, false);
            }
        }
    }
    
    //添加克隆字段接口(2014/7/23 added by huangkj)
    /**
     * 复制当前字段对象
     * 系统字段不容许克隆
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
