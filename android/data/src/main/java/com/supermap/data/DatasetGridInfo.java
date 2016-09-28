package com.supermap.data;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: SuperMap GIS Technologies Inc.
 * </p>
 * 
 * @author 李云锦
 * @version 2.0
 */
public class DatasetGridInfo extends InternalHandleDisposable {
	private boolean m_isMultiBand = false;

	public DatasetGridInfo() {
		setHandle(DatasetGridInfoNative.jni_New(), true);
		reset();
	}

	public DatasetGridInfo(DatasetGridInfo datasetGridInfo) {
		if (datasetGridInfo == null) {
			String message = InternalResource.loadString("datasetGridInfo",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		if (datasetGridInfo.getHandle() == 0) {
			String message = InternalResource.loadString("datasetGridInfo",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		long handle = DatasetGridInfoNative.jni_Clone(datasetGridInfo
				.getHandle());
		setHandle(handle, true);
	}

	public DatasetGridInfo(String name, int width, int height,
			PixelFormat pixelFormat, EncodeType encodeType) {
		if (name == null || name.trim().length() == 0) {
			String message = InternalResource.loadString("name",
					InternalResource.GlobalStringIsNullOrEmpty,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (pixelFormat == null) {
			String message = InternalResource.loadString("pixelFormat",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if(pixelFormat.equals(PixelFormat.UBIT24)||
				pixelFormat.equals(PixelFormat.UBIT32)||
				pixelFormat.equals(PixelFormat.BIT64)){
			String message = InternalResource.loadString("pixelFormat", 
					InternalResource.DatasetGridInfoUnSupportedPixelFormat,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (encodeType == null) {
			String message = InternalResource.loadString("encodeType",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		setHandle(DatasetGridInfoNative.jni_New(), true);
		reset();
		// 注意：此处没有统一在reset中一次调用jni，因为通过set各属性，可以很好的实现对各参数的判断。
		setName(name);
		setWidth(width);
		setHeight(height);
		setPixelFormat(pixelFormat);
		setEncodeType(encodeType);
	}

	public DatasetGridInfo(String name, int width, int height,
			PixelFormat pixelFormat, EncodeType encodeType, int blockSize) {
		if (name == null || name.trim().length() == 0) {
			String message = InternalResource.loadString("name",
					InternalResource.GlobalStringIsNullOrEmpty,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (pixelFormat == null) {
			String message = InternalResource.loadString("pixelFormat",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if(pixelFormat.equals(PixelFormat.UBIT24)||
				pixelFormat.equals(PixelFormat.UBIT32)||
				pixelFormat.equals(PixelFormat.BIT64)){
			String message = InternalResource.loadString("pixelFormat", 
					InternalResource.DatasetGridInfoUnSupportedPixelFormat,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (encodeType == null) {
			String message = InternalResource.loadString("encodeType",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		setHandle(DatasetGridInfoNative.jni_New(), true);
		reset();
		// 注意：此处没有统一在reset中一次调用jni，因为通过set各属性，可以很好的实现对各参数的判断。
		setName(name);
		setWidth(width);
		setHeight(height);
		setPixelFormat(pixelFormat);
		setBlockSize(blockSize);
		setEncodeType(encodeType);
	}
	
	/**
	 * 根据指定的名称及模板数据集构建一个新的对象。即除了名称外，其他属性与模板数据集相同
	 * @param name
	 * @param templateDataset
	 */
	public DatasetGridInfo(String name, DatasetGrid templateDataset) {
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
		long handle = DatasetGridInfoNative.jni_New2(templateDatasetHandle);
		this.setHandle(handle, true);
		this.setName(name);
	}

	//
	public String getName() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return DatasetGridInfoNative.jni_GetName(getHandle());
	}

	//
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
		DatasetGridInfoNative.jni_SetName(getHandle(), value);
	}

	public Rectangle2D getBounds() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] rect = new double[4];
		// 在Wrapj层对rect进行赋值
		DatasetGridInfoNative.jni_GetBounds(getHandle(), rect);
		return new Rectangle2D(rect[0], rect[1], rect[2], rect[3]);
	}

	// 设置格网数据集的地理范围
	public void setBounds(Rectangle2D value) {
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

		DatasetGridInfoNative.jni_SetBounds(getHandle(), value.getLeft(), value
				.getBottom(), value.getRight(), value.getTop());
	}

	//
	public int getWidth() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return DatasetGridInfoNative.jni_GetWidth(getHandle());
	}

	//
	public void setWidth(int value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (value <= 0) {
			String message = InternalResource.loadString("value",
					InternalResource.DatasetGridWidthShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		DatasetGridInfoNative.jni_SetWidth(getHandle(), value);
	}

	//
	public int getHeight() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridInfoNative.jni_GetHeight(getHandle());
	}

	//
	public void setHeight(int value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (value <= 0) {
			String message = InternalResource.loadString("value",
					InternalResource.DatasetGridHeightShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		DatasetGridInfoNative.jni_SetHeight(getHandle(), value);
	}

	//
	public PixelFormat getPixelFormat() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugcValue = DatasetGridInfoNative.jni_GetPixelFormat(getHandle());
		return (PixelFormat) Enum.parseUGCValue(PixelFormat.class, ugcValue);
	}

	//
	public void setPixelFormat(PixelFormat value) {
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
		if(value.equals(PixelFormat.UBIT24) || value.equals(PixelFormat.UBIT32) || value.equals(PixelFormat.BIT64)){
			String message = InternalResource.loadString("value",
					InternalResource.DatasetGridInfoUnSupportedPixelFormat,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		DatasetGridInfoNative.jni_SetPixelFormat(getHandle(), value
				.getUGCValue());
	}

	// 默认大小为128
	public int getBlockSize() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridInfoNative.jni_GetBlockSize(getHandle());
	}

	//
	public void setBlockSize(int value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (value <= 0) {
			String message = InternalResource.loadString("value",
					InternalResource.DatasetGridBlockSizeShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		DatasetGridInfoNative.jni_SetBlockSize(getHandle(), value);
	}

	//
	public EncodeType getEncodeType() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugcValue = DatasetGridInfoNative.jni_GetEncodeType(getHandle());
		return (EncodeType) Enum.parseUGCValue(EncodeType.class, ugcValue);
	}

	//
	public void setEncodeType(EncodeType encodeType) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (encodeType == null) {
			String message = InternalResource.loadString("encodeType",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (!Toolkit.isValidEncodeType(false, encodeType)) {
			String message = InternalResource.loadString("encodeType",
					InternalResource.GlobalUnsupportedEncodeType,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		DatasetGridInfoNative.jni_SetEncodeType(getHandle(), encodeType
				.getUGCValue());
	}

	// 默认值-9999
	public double getNoValue() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridInfoNative.jni_GetNoValue(getHandle());
	}

	//
	public void setNoValue(double value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		DatasetGridInfoNative.jni_SetNoValue(getHandle(), value);
	}

	//
	public double getMinValue() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridInfoNative.jni_GetMinValue(getHandle());
	}

	//
	public void setMinValue(double value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		DatasetGridInfoNative.jni_SetMinValue(getHandle(), value);
	}

	//
	public double getMaxValue() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridInfoNative.jni_GetMaxValue(getHandle());
	}

	//
	public void setMaxValue(double value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		DatasetGridInfoNative.jni_SetMaxValue(getHandle(), value);
	}

//	public boolean isMultiBand() {
//		if (getHandle() == 0) {
//			String message = InternalResource.loadString("",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		return m_isMultiBand;
//	}

//	public void setMultiBand(boolean value) {
//		if (getHandle() == 0) {
//			String message = InternalResource.loadString("",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		m_isMultiBand = value;
//		
//		//在Wrapj中，要設置info的類型。
//		DatasetGridInfoNative.jni_ResetIsMultiBand(getHandle(), value);
//	}

	//
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
		buffer.append(",Bounds = {");
		buffer.append(this.getBounds().toString());
		buffer.append("},Width = ");
		buffer.append(this.getWidth());
		buffer.append(",Height = ");
		buffer.append(this.getHeight());
		buffer.append(",PixelFormat = ");
		buffer.append(this.getPixelFormat().name());
		buffer.append(",BlockSize = ");
		buffer.append(this.getBlockSize());
		buffer.append(",EncodeType = ");
		buffer.append(this.getEncodeType().name());
		buffer.append(",NoValue = ");
		buffer.append(this.getNoValue());
		buffer.append(",MinValue = ");
		buffer.append(this.getMinValue());
		buffer.append(",MaxValue = ");
		buffer.append(this.getMaxValue());
//		buffer.append(",IsMultiBand = ");
//		buffer.append(this.isMultiBand());
		buffer.append("}\n");
		return buffer.toString();
	}

	public void dispose() {
		if (!super.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (super.getHandle() != 0) {
			DatasetGridInfoNative.jni_Delete(super.getHandle());
			clearHandle();
		}

	}

	protected void clearHandle() {
		setHandle(0);
	}

	void reset() {
		if (getHandle() != 0) {
			DatasetGridInfoNative.jni_Reset(getHandle());
		}
	}
}
