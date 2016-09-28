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
public class DatasetVectorInfo extends InternalHandleDisposable {
    //
    public DatasetVectorInfo() {
        setHandle(DatasetVectorInfoNative.jni_New(), true);
        reset();
    }

    public DatasetVectorInfo(String name, DatasetType type) {
        if (name == null || name.trim().length() == 0) {
            String message = InternalResource.loadString("name",
                    InternalResource.GlobalStringIsNullOrEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        InternalInvalidState is = Dataset.isValidDatasetName(name);
        if (is != null) {
            String key = "";
            if (is.equals(InternalInvalidState.ISEMPTY)) {
                key = InternalResource.DatasetNameIsEmpty;
            } else if (is.equals(InternalInvalidState.AGAINSYSTEMNAME)) {
                key = InternalResource.DatasetNameAgainstSys;
            } else if (is.equals(InternalInvalidState.BEYONDLIMIT)) {
                key = InternalResource.DatasetNameBeyondLimit;
            } else if (is.equals(InternalInvalidState.INVALIDCHAR)) {
                key = InternalResource.DatasetNameIncludeInvalidChar;
            } else if (is.equals(InternalInvalidState.PREFIXERROR)) {
                key = InternalResource.DatasetNameErrorPrefix;
            }
            String message = InternalResource.loadString("value", key,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        if (type == null) {
            String message = InternalResource.loadString("type",
                    InternalResource.GlobalArgumentNull,
                             InternalResource.BundleName);
            throw new NullPointerException(message);
        }

        setHandle(DatasetVectorInfoNative.jni_New(), true);
        reset();
        setName(name);
        setType(type);
    }

    public DatasetVectorInfo(DatasetVectorInfo datasetVectorInfo) {
        if (datasetVectorInfo == null) {
            String message = InternalResource.loadString("datasetVectorInfo",
                    InternalResource.GlobalArgumentNull,
                             InternalResource.BundleName);
            throw new NullPointerException(message);
        }
        if (datasetVectorInfo.getHandle() == 0) {
            String message = InternalResource.loadString("datasetVectorInfo",
                    InternalResource.GlobalArgumentObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = DatasetVectorInfoNative.jni_Clone(datasetVectorInfo.
                getHandle());
        setHandle(handle, true);
    }
    
    public DatasetVectorInfo(String name, DatasetVector templateDataset) {
		if (templateDataset == null) {
			String message = InternalResource.loadString("templateDataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long templateDatasetHandle = templateDataset.getHandle();
		if (templateDatasetHandle == 0) {
			String message = InternalResource.loadString("templateDataset",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = DatasetVectorInfoNative.jni_New2(templateDatasetHandle);
		this.setHandle(handle, true);
		this.setName(name);
    }

    //数据集名称
    public String getName() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return DatasetVectorInfoNative.jni_GetName(getHandle());
    }

    //数据集名称
    public void setName(String value) {
        if (getHandle() == 0) {
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
        InternalInvalidState is = Dataset.isValidDatasetName(value);
        if (is != null) {
            String key = "";
            if (is.equals(InternalInvalidState.ISEMPTY)) {
                key = InternalResource.DatasetNameIsEmpty;
            } else if (is.equals(InternalInvalidState.AGAINSYSTEMNAME)) {
                key = InternalResource.DatasetNameAgainstSys;
            } else if (is.equals(InternalInvalidState.BEYONDLIMIT)) {
                key = InternalResource.DatasetNameBeyondLimit;
            } else if (is.equals(InternalInvalidState.INVALIDCHAR)) {
                key = InternalResource.DatasetNameIncludeInvalidChar;
            } else if (is.equals(InternalInvalidState.PREFIXERROR)) {
                key = InternalResource.DatasetNameErrorPrefix;
            }
            String message = InternalResource.loadString("value", key,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        DatasetVectorInfoNative.jni_SetName(getHandle(), value);
    }

    //返回/设置数据源连接的引擎类型。
    public DatasetType getType() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int ugcValue = DatasetVectorInfoNative.jni_GetType(getHandle());
        return (DatasetType) Enum.parseUGCValue(DatasetType.class, ugcValue);
    }

    //返回/设置数据源连接的引擎类型。
    public void setType(DatasetType value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (value == null) {
            String message = InternalResource.loadString("value",
                    InternalResource.GlobalArgumentNull,
                             InternalResource.BundleName);
            throw new NullPointerException(message);
        }
        //检查数据集类型
//        if (!Datasets.isCreatableVectorType(value)) {
//            String message = InternalResource.loadString("value",
//                    InternalResource.DatasetVectorInfoIllegalDatasetType,
//                    InternalResource.BundleName);
//            throw new IllegalArgumentException(message);
//        }
        DatasetVectorInfoNative.jni_SetType(getHandle(), value.getUGCValue());
    }

    //
    public EncodeType getEncodeType() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int ugcValue = DatasetVectorInfoNative.jni_GetEncodeType(getHandle());
        return (EncodeType) Enum.parseUGCValue(EncodeType.class, ugcValue);
    }

    //
    public void setEncodeType(EncodeType value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (value == null) {
            String message = InternalResource.loadString("value",
                    InternalResource.GlobalArgumentNull,
                             InternalResource.BundleName);
            throw new NullPointerException(message);
        }
        DatasetVectorInfoNative.jni_SetEncodeType(getHandle(),
                                                  value.getUGCValue());
    }

    //
//    public boolean isFileCache() {
//        if (getHandle() == 0) {
//            String message = InternalResource.loadString("",
//                    InternalResource.HandleObjectHasBeenDisposed,
//                    InternalResource.BundleName);
//            throw new IllegalStateException(message);
//        }
//        return DatasetVectorInfoNative.jni_GetIsFileCache(getHandle());
//    }
//
//    //
//    public void setFileCache(boolean value) {
//        if (getHandle() == 0) {
//            String message = InternalResource.loadString("",
//                    InternalResource.HandleObjectHasBeenDisposed,
//                    InternalResource.BundleName);
//            throw new IllegalStateException(message);
//        }
//
//        DatasetVectorInfoNative.jni_SetIsFileCache(getHandle(), value);
//    }

    //
    public String toString() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

//        String str = "{Name=\"" + this.getName() + "\",Type=" +
//                     this.getType().name() + ",EncodeType=" +
//                     this.getEncodeType().name() +
//                     ",IsFileCache=" + this.isFileCache() + "}";
        
        String str = "{Name=\"" + this.getName() + "\",Type=" +
        this.getType().name() + ",EncodeType=" +
        this.getEncodeType().name() + "}";
        
        return str;
    }

    public void dispose() {
        if (!super.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (super.getHandle() != 0) {
            DatasetVectorInfoNative.jni_Delete(super.getHandle());
            clearHandle();
        }
    }

    protected void clearHandle() {
        setHandle(0);
    }

    void reset() {
        if (getHandle() != 0) {
            DatasetVectorInfoNative.jni_Reset(getHandle());
        }
    }
}

