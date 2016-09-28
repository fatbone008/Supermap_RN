package com.supermap.data;

import java.util.Vector;

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
public class DatasetGrid extends Dataset {
	private GeoRegion m_clipRegion = null;
	
	// 2009-08-15 yunwy 为buildPyramid方法添加进度条相关
//	private long m_selfEventHandle;
//
//	transient Vector m_steppedListeners;
//	
	//private static String m_senderMethodName;
	// private ArrayList<String> m_bands = null;

	// private int m_current;
	
	private static Integer m_lock = new Integer(0);
	
	private Colors m_colors;
	
	private DatasetVector m_childDataset = null;
	
	protected DatasetGrid() {
		m_senderMethodName = "buildPyramid";
	}

	DatasetGrid(long handle, Datasource datasource) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		if (datasource.getHandle() == 0) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		setHandle(handle);
		this.m_datasource = datasource;
		// this.m_bands = new ArrayList<String>();
		// this.m_current = -1;
		
	}

//	public ColorSpaceType getColorSpace() {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString("getColorSpace()",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		// 判断是否是多波段，不是多波段则不支持该操作。
////		if (!this.isMultiBand()) {
////			String message = InternalResource
////					.loadString(
////							"getColorSpace()",
////							InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
////							InternalResource.BundleName);
////			throw new UnsupportedOperationException(message);
////		}
//		int ugcType = DatasetGridNative.jni_GetColorSpaceType(getHandle());
//		return (ColorSpaceType) Enum.parseUGCValue(ColorSpaceType.class,
//				ugcType);
//	}

//	public void setColorSpace(ColorSpaceType value) {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"setColorSpace(ColorSpaceType value)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
////		// 判断是否是多波段，不是多波段则不支持该操作。
////		if (!this.isMultiBand()) {
////			String message = InternalResource
////					.loadString(
////							"setColorSpace(ColorSpaceType value)",
////							InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
////							InternalResource.BundleName);
////			throw new UnsupportedOperationException(message);
////		}
//		DatasetGridNative.jni_SetColorSpaceType(getHandle(), value
//				.getUGCValue());
//	}

//	public boolean isMultiBand() {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString("isMultiBand()",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		return DatasetGridNative.jni_IsMultiBand(getHandle());
//	}

	/**
	 * 返回波段的个数。
	 * 
	 * @return 返回波段的个数。
	 */
//	public int getBandCount() {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString("GetBandCount()",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		// 判断是否是多波段，不是多波段则不支持该操作。
//		if (!this.isMultiBand()) {
//			String message = InternalResource
//					.loadString(
//							"getBandCount()",
//							InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
//							InternalResource.BundleName);
//			throw new UnsupportedOperationException(message);
//		}
//		return DatasetGridNative.jni_getBandCount(getHandle());
//	}

	/**
	 * 返回指定序号的波段的名称。
	 * 
	 * @param index
	 *            指定的波段的序号。
	 * @return 返回指定序号的波段的名称。
	 */
//	public String get(int index) {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString("Get(int index)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		// 判断是否是多波段，不是多波段则不支持该操作。
//		if (!this.isMultiBand()) {
//			String message = InternalResource
//					.loadString(
//							"get(int index)",
//							InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
//							InternalResource.BundleName);
//			throw new UnsupportedOperationException(message);
//		}
//
//		if (index < 0 || index >= getBandCount()) {
//			String message = InternalResource.loadString("index",
//					InternalResource.GlobalIndexOutOfBounds,
//					InternalResource.BundleName);
//			throw new IndexOutOfBoundsException(message);
//		}
//		return DatasetGridNative.jni_getItem(getHandle(), index);
//	}

	/**
	 * 设置指定序号的波段的名称。
	 * 
	 * @param index
	 *            指定的波段的序号。
	 * @param value
	 *            指定序号的波段的名称。
	 */
//	public void set(int index, String value) {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"set(int index, String value)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		// 判断是否是多波段，不是多波段则不支持该操作。
//		if (!this.isMultiBand()) {
//			String message = InternalResource
//					.loadString(
//							"set(int index, String value)",
//							InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
//							InternalResource.BundleName);
//			throw new UnsupportedOperationException(message);
//		}
//
//		if (index < 0 || index >= getBandCount()) {
//			String message = InternalResource.loadString("index",
//					InternalResource.GlobalIndexOutOfBounds,
//					InternalResource.BundleName);
//			throw new IndexOutOfBoundsException(message);
//		}
//		if (value == null || value.trim().length() == 0) {
//			String message = InternalResource.loadString("value",
//					InternalResource.DatasetGridBandNameIsNotValid,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		boolean isContain = DatasetGridNative.jni_isContain(getHandle(), value);
//		if (isContain) {
//			String message = InternalResource.loadString("value",
//					InternalResource.DatasetGridBandNameIsNotValid,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		DatasetGridNative.jni_setItem(getHandle(), index, value);
//	}

	/**
	 * 返回当前波段的索引。
	 * 
	 * @return 返回当前波段的索引。
	 */
//	public int getCurrentBandIndex() {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"getCurrentBandIndex()",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//
//		// 判断是否是多波段，不是多波段则不支持该操作。
//		if (!this.isMultiBand()) {
//			String message = InternalResource
//					.loadString(
//							"getCurrentBandIndex()",
//							InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
//							InternalResource.BundleName);
//			throw new UnsupportedOperationException(message);
//		}
//		return DatasetGridNative.jni_getCurrentBandIndex(this.getHandle());
//	}

	/**
	 * 设置当前波段的索引。
	 * 
	 * @param value
	 *            当前波段的索引。
	 */
//	public void setCurrentBandIndex(int value) {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"setCurrentBandIndex(int value)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//
//		// 判断是否是多波段，不是多波段则不支持该操作。
//		if (!this.isMultiBand()) {
//			String message = InternalResource
//					.loadString(
//							"setCurrentBandIndex(int value)",
//							InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
//							InternalResource.BundleName);
//			throw new UnsupportedOperationException(message);
//		}
//
//		if (value < 0 || value >= this.getBandCount()) {
//			String message = InternalResource.loadString("value",
//					InternalResource.GlobalIndexOutOfBounds,
//					InternalResource.BundleName);
//			throw new IndexOutOfBoundsException(message);
//		}
//		DatasetGridNative.jni_setCurrentBandIndex(this.getHandle(), value);
//	}

	// 返回格网数据集的格网宽度
	public int getWidth() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridNative.jni_GetWidth(getHandle());
	}

	// 返回格网数据集的格网高度
	public int getHeight() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridNative.jni_GetHeight(getHandle());
	}

	// 设置格网数据集的地理范围
	public void setGeoReference(Rectangle2D value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (value == null) {
			String message = InternalResource.loadString("value",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		DatasetGridNative.jni_SetBounds(getHandle(), value.getLeft(), value
				.getBottom(), value.getRight(), value.getTop());
	}

	//
	public PixelFormat getPixelFormat() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugcValue = DatasetGridNative.jni_GetPixelFormat(getHandle());
		return (PixelFormat) Enum.parseUGCValue(PixelFormat.class, ugcValue);
	}

	//
	public int getBlockSize() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridNative.jni_GetBlockSize(getHandle());
	}

	//
	public int getRowBlockCount() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridNative.jni_GetRowBlockCount(getHandle());
	}

	//
	public int getColumnBlockCount() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridNative.jni_GetColumnBlockCount(getHandle());
	}

	//
	public double getNoValue() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridNative.jni_GetNoValue(getHandle());
	}

	//
	public void setNoValue(double value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		DatasetGridNative.jni_SetNoValue(getHandle(), value);
	}

	//
	public double getMinValue() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridNative.jni_GetMinValue(getHandle());
	}

	//
	public double getMaxValue() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridNative.jni_GetMaxValue(getHandle());
	}

	public boolean getHasPyramid() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridNative.jni_GetHasPyramid(getHandle());
	}

	//
	public GeoRegion getClipRegion() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (this.m_clipRegion == null) {
			long handle = DatasetGridNative.jni_GetClipRegion(getHandle());
			if (handle != 0) {
				m_clipRegion = (GeoRegion) Geometry.createInstance(handle);
			}
		}
		return this.m_clipRegion;
	}

	//
	public void setClipRegion(GeoRegion value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		// 注意：此处的参数可以为null
		if (value == null) {
			if (m_clipRegion != null) {
				m_clipRegion.clearHandle();
				m_clipRegion = null;
			}
			DatasetGridNative.jni_SetClipRegion(getHandle(), 0);
		} else {
			if (value.getHandle() == 0) {
				String message = InternalResource.loadString("value",
						InternalResource.GlobalArgumentObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			// 此处的参数虽然是以指针传入，但UGC中进行的是赋值操作，故不用clone了。
			DatasetGridNative.jni_SetClipRegion(getHandle(), value.getHandle());
			// 更新m_clipRegion中的内容。
			if (m_clipRegion != null) {
				m_clipRegion.fromXML(value.toXML());
			}
		}
	}

	//
	public boolean calculateExtremum() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridNative.jni_CalculateExtremum(getHandle());
	}
	
	
	
	//
	public double getValue(int column, int row) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (column < 0 || column >= this.getWidth()) {
			String message = InternalResource.loadString("column",
					InternalResource.DatasetGridColumnIsOutOfRange,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (row < 0 || row >= this.getHeight()) {
			String message = InternalResource.loadString("row",
					InternalResource.DatasetGridRowIsOutOfRange,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return DatasetGridNative.jni_GetValue(getHandle(), column, row);
	}

	//
	public double setValue(int column, int row, double value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.isReadOnly()) {
			String message = InternalResource
					.loadString(
							"",
							InternalResource.DatasetGridTheDatasourceOrDatasetIsReadOnly,
							InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (column < 0 || column >= this.getWidth()) {
			String message = InternalResource.loadString("column",
					InternalResource.DatasetGridColumnIsOutOfRange,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (row < 0 || row >= this.getHeight()) {
			String message = InternalResource.loadString("row",
					InternalResource.DatasetGridRowIsOutOfRange,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return DatasetGridNative.jni_SetValue(getHandle(), column, row, value);
	}

	public Point2D gridToXY(Point point) {
		/**
		 * @todo 需要修改 注意，如果失败，就是因为修改底层的函数的访问权限所导致的
		 * @reason
		 */
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] buffer = new double[2];
		DatasetGridNative.jni_GridToXY(getHandle(), point.getX(), point.getY(), buffer);
		return new Point2D(buffer[0], buffer[1]);
	}

	//
	public Point xyToGrid(Point2D point) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int[] buffer = new int[2];
		DatasetGridNative.jni_XYToGrid(getHandle(), point.getX(), point.getY(),
				buffer);
		return new Point(buffer[0], buffer[1]);
	}

	// 创建影像金字塔
	public boolean buildPyramid() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.isOpen()) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetGridShouldBeClosedBeforeBuildPyramid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean bResult = false;
		
		if (this.getHasPyramid()) {
			bResult = true;
		} else {
			

			bResult = DatasetGridNative.jni_BuildPyramid(getHandle());

		}
		
		return bResult;
	}

	// 更新金字塔
	public boolean updatePyramid(Rectangle2D value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean bResult = false;

		bResult = DatasetGridNative.jni_UpdatePyramid(getHandle(),value.getLeft(), value
				.getBottom(), value.getRight(), value.getTop());
			
		return bResult;
	}

	
	// 删除金字塔
	public boolean removePyramid() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridNative.jni_RemovePyramid(getHandle());
	}
	
	/**
	 * 获取Grid数据集用于显示时的默认颜色表，内存数据，关闭数据集时不保存
	 * @return
	 */
	public Colors getColorTable() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getColorTable()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (m_colors == null) {
			long colorsHandle = DatasetGridNative.jni_GetColorTable(this.getHandle());
			if (colorsHandle != 0) {
				m_colors = Colors.createInstance(colorsHandle, false);
			}
		}
		return m_colors;
	}
	
	/**
	 * 设置Grid数据集用于显示时的默认颜色表，内存数据，关闭数据集时不保存
	 * @param colors
	 */
	public void setColorTable(Colors colors) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("setColorTable(Colors colors)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (colors == null) {
			String message = InternalResource.loadString("colors",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long handle = InternalHandleDisposable.getHandle(colors);
		if(handle == 0) {
			String message = InternalResource.loadString("colors",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		Colors clone = new Colors(colors);
		long cloneHandle =  InternalHandleDisposable.getHandle(clone);
		DatasetGridNative.jni_SetColorTable(this.getHandle(), cloneHandle);
	}
	
	// {{ annotated by zhh 事件机制已经提到基类
//	public synchronized void addSteppedListener(SteppedListener l) {
//		if (m_steppedListeners == null) {
//			m_steppedListeners = new Vector();
//		}
//
//		if (!m_steppedListeners.contains(l)) {
//			m_steppedListeners.add(l);
//		}
//	}
//
//	public synchronized void removeSteppedListener(SteppedListener l) {
//		if (m_steppedListeners != null && m_steppedListeners.contains(l)) {
//			m_steppedListeners.remove(l);
//		}
//
//	}
//
//	protected void fireStepped(SteppedEvent event) {
//		if (m_steppedListeners != null) {
//			Vector listeners = m_steppedListeners;
//			int count = listeners.size();
//			for (int i = 0; i < count; i++) {
//				((SteppedListener) listeners.elementAt(i)).stepped(event);
//			}
//		}
//	}
//
//	static void steppedCallBack(DatasetGrid source, int percent,
//			long remainTime, String title, String message) {
//		if (source != null) {
//			m_senderMethodName = "buildPyramid";
//			SteppedEvent event = new SteppedEvent(source, percent, remainTime,
//					title, message, m_senderMethodName);
//			source.fireStepped(event);
//		}
//	}
//
//	protected void clearSelfEventHandle() {
//		if (m_selfEventHandle != 0) {
//			DatasetImageNative.jni_DeleteSelfEventHandle(m_selfEventHandle);
//			m_selfEventHandle = 0;
//		}
//	}
	// }}
	
	/**
	 * 返回栅格值属性表，其类型为属性表数据集类型，用于记录像素值及对应的像素数和属性。
	 * 
	 * @return 返回栅格值属性表。
	 */

	public DatasetVector buildValueTable(Datasource targetDatasource,String tableName){
		if(this.getHandle() == 0){
			String message = InternalResource.loadString(
					"buildValueTable(Datasource ds,String)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		if(targetDatasource == null){
			String message = InternalResource.loadString(
					"datasource",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		long handle =  DatasetGridNative.jni_BuildValueTable(this.getHandle(), targetDatasource.getHandle(), tableName);
		
		DatasetVector result =null;
		if(handle!=0){
			result = new DatasetVector(handle,targetDatasource);
		}
		return result;
	}

//	/**
//	 * 返回栅格数据集像素的统计结果，包括最大值、最小值、均值、中值、众数、稀数、方差、标准差。
//	 * 
//	 * @param mode
//	 *            统计方式。
//	 * @return 返回统计结果。
//	 * 接口已废弃，使用新的接口buildStatistics()
//	 */
//	@Deprecated
//	public DatasetGridStatisticResult statictic() {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"statictic(StatisticMode mode)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		
//		long handle =  DatasetGridNative.jni_statictic(this.getHandle());
//		
//		DatasetGridStatisticResult result =null;
//		if(handle!=0){
//			result = new DatasetGridStatisticResult(handle);
//		}
//		return result;
//	}

	/**
	 * 返回栅格数据集像素的统计结果，包括最大值、最小值、均值、中值、众数、稀数、方差、标准差。
	 * 
	 * @param mode
	 *            统计方式。
	 * @return 返回统计结果。
	 */
	public StatisticsResult getGridStatisticsResult() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"statictic(StatisticMode mode)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		long handle =  DatasetGridNative.jni_GetRasterStatisticsResult(this.getHandle());
		
		StatisticsResult result =null;
		if(handle!=0){
			result = new StatisticsResult(handle);
		}
		return result;
	}
	
	
	
	/**
	 * 返回栅格数据集像素的统计结果，包括最大值、最小值、均值、中值、众数、稀数、方差、标准差。
	 * 
	 * @param mode
	 *            统计方式。
	 * @return 返回统计结果。
	 */
	public StatisticsResult buildStatistics() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"statictic(StatisticMode mode)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		long handle =  DatasetGridNative.jni_BuildStatistics(this.getHandle());
		
		StatisticsResult result =null;
		if(handle!=0){
			result = new StatisticsResult(handle);
		}
		return result;
	}
	
//	// 根据多波段格网数据文件，向多波段格网数据中追加波段
//	public int addBand(String fileName) {
//		verifyLicense();
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"addBand(DatasetGrid dataset)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		// 判断是否是多波段，不是多波段则不支持该操作。
//		if (!this.isMultiBand()) {
//			String message = InternalResource
//					.loadString(
//							"addBand(DatasetGrid dataset)",
//							InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
//							InternalResource.BundleName);
//			throw new UnsupportedOperationException(message);
//		}
//
//		if (fileName == null || fileName.trim().length() == 0) {
//			String message = InternalResource.loadString("fileName",
//					InternalResource.GlobalStringIsNullOrEmpty,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		return DatasetGridNative.jni_addBand1(this.getHandle(), fileName);
//	}

	/**
	 * 向多波段栅格数据中追加波段。
	 * 
	 * @param dataset
	 *            栅格数据集。
	 * @return 返回添加后的索引。
	 */
//	public int addBand(DatasetGrid dataset) {
//		verifyLicense();
//
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"AddBand(DatasetGrid dataset)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		// 判断是否是多波段，不是多波段则不支持该操作。
//		if (!this.isMultiBand()) {
//			String message = InternalResource
//					.loadString(
//							"addBand(DatasetGrid dataset)",
//							InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
//							InternalResource.BundleName);
//			throw new UnsupportedOperationException(message);
//		}
//		if (dataset == null) {
//			String message = InternalResource.loadString("dataset",
//					InternalResource.GlobalArgumentNull,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		long datasetHandle = InternalHandle.getHandle(dataset);
//		if (datasetHandle == 0) {
//			String message = InternalResource.loadString("dataset",
//					InternalResource.GlobalArgumentObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		return DatasetGridNative.jni_addBand(this.getHandle(), datasetHandle);
//	}
//
//	/**
//	 * 向多波段栅格数据中追加多个波段。
//	 * 
//	 * @param datasets
//	 *            栅格数据集集合。
//	 * @return 返回添加的波段个数。
//	 */
//	public int addBand(DatasetGrid[] datasets) {
//		verifyLicense();
//
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"AddBand(DatasetGrid[] datasets)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		// 判断是否是多波段，不是多波段则不支持该操作。
//		if (!this.isMultiBand()) {
//			String message = InternalResource
//					.loadString(
//							"addBand(DatasetGrid[] datasets)",
//							InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
//							InternalResource.BundleName);
//			throw new UnsupportedOperationException(message);
//		}
//
//		if (datasets == null) {
//			String message = InternalResource.loadString("datasets",
//					InternalResource.GlobalArgumentNull,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		for (int i = 0; i < datasets.length; i++) {
//			long datasetHandle = InternalHandle.getHandle(datasets[i]);
//			if (datasetHandle == 0) {
//				String message = InternalResource.loadString("dataset",
//						InternalResource.GlobalInvalidConstructorArgument,
//						InternalResource.BundleName);
//				throw new IllegalArgumentException(message);
//			}
//		}
//		// 此处后面可以优化，减少JNI的调用次数 by kongll
//		int bandCount = this.getBandCount();
//		for (int k = 0; k < datasets.length; k++) {
//			addBand(datasets[k]);
//		}
//		int newBandCount = this.getBandCount();
//		return newBandCount - bandCount;
//	}

	/**
	 * 向多波段栅格数据中追加多个波段。
	 * 
	 * @param dataset
	 *            要追加的波段所在的栅格数据集。
	 * @param indexes
	 *            要追加的波段索引；即在 dataset 中的索引。
	 * @return 返回添加的波段个数。
	 */
//	public int addBand(DatasetGrid dataset, int[] indexes) {
//		verifyLicense();
//
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"AddBand(DatasetGrid dataset, int[] indexes)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		// 判断是否是多波段，不是多波段则不支持该操作。
//		if (!this.isMultiBand()) {
//			String message = InternalResource
//					.loadString(
//							"addBand(DatasetGrid dataset, int[] indexes)",
//							InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
//							InternalResource.BundleName);
//			throw new UnsupportedOperationException(message);
//		}
//
//		if (dataset == null) {
//			String message = InternalResource.loadString("dataset",
//					InternalResource.GlobalArgumentNull,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		long datasetHandle = InternalHandle.getHandle(dataset);
//		if (datasetHandle == 0) {
//			String message = InternalResource.loadString("indexes",
//					InternalResource.GlobalInvalidConstructorArgument,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
//		// 参数dataset必须是多波段，即isMultiBand = true。
//		if (!dataset.isMultiBand()) {
//			String message = InternalResource
//					.loadString(
//							"dataset",
//							InternalResource.DatasetGridTheArgumentMustBeMultibandsData,
//							InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//
//		}
//		// 判断索引数组必须在波段个数范围内。
//		int indexesCount = indexes.length;
//		int bandCount = dataset.getBandCount();
//		for (int i = 0; i < indexesCount; i++) {
//			if (bandCount <= indexes[i]) {
//				String message = InternalResource.loadString("indexes",
//						InternalResource.GlobalIndexOutOfBounds,
//						InternalResource.BundleName);
//				throw new IllegalArgumentException(message);
//			}
//		}
//
//		DatasetGridNative.jni_addBandIndexs(this.getHandle(), datasetHandle,
//				indexes);
//		return indexes.length;
//
//	}

	// /**
	// * 根据多波段栅格数据文件的名字，向指定的多波段栅格数据中追加波段。
	// * @param fileName 多波段栅格数据文件名字。
	// * @return 删除成功返回 true；否则返回 false。
	// */
	// public boolean addBand(String fileName){
	// if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
	// String message = InternalResource.loadString("m_datasource",
	// InternalResource.GlobalOwnerHasBeenDisposed,
	// InternalResource.BundleName);
	// throw new IllegalStateException(message);
	// }
	// // 判断是否是多波段，不是多波段则不支持该操作。
	// if (!this.isMultiBand()) {
	// String message = InternalResource
	// .loadString(
	// "addBand(String fileName)",
	// InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
	// InternalResource.BundleName);
	// throw new UnsupportedOperationException(message);
	// }
	// //TODO
	//	    	
	// return false;
	//	
	// }
	/**
	 * 根据指定的索引号来删除某个波段。
	 * 
	 * @param index
	 *            指定要删除波段的索引。
	 * @return 删除成功返回 true；否则返回 false。
	 */
//	public boolean deleteBand(int index) {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"DeleteBand(int index)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		// 判断是否是多波段，不是多波段则不支持该操作。
//		if (!this.isMultiBand()) {
//			String message = InternalResource
//					.loadString(
//							"deleteBand(int index)",
//							InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
//							InternalResource.BundleName);
//			throw new UnsupportedOperationException(message);
//		}
//		if (index < 0 || index >= getBandCount()) {
//			String message = InternalResource.loadString("index",
//					InternalResource.GlobalIndexOutOfBounds,
//					InternalResource.BundleName);
//			throw new IndexOutOfBoundsException(message);
//		}
//		return DatasetGridNative.jni_deleteBand(this.getHandle(), index);
//	}

	/**
	 * 根据指定的开始索引号和指定的删除个数来删除多个波段。
	 * 
	 * @param startIndex
	 *            指定删除波段的开始索引号。
	 * @param count
	 *            要删除的波段的个数。
	 * @return 删除成功返回 true；否则返回 false。
	 */
//	public boolean deleteBand(int startIndex, int count) {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"DeleteBand(int startIndex, int count)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		// 判断是否是多波段，不是多波段则不支持该操作。
//		if (!this.isMultiBand()) {
//			String message = InternalResource
//					.loadString(
//							"deleteBand(int startIndex, int count)",
//							InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
//							InternalResource.BundleName);
//			throw new UnsupportedOperationException(message);
//		}
//
//		if (startIndex < 0 || startIndex >= getBandCount()) {
//			String message = InternalResource.loadString("index",
//					InternalResource.GlobalIndexOutOfBounds,
//					InternalResource.BundleName);
//			throw new IndexOutOfBoundsException(message);
//		}
//
//		if (startIndex + count < startIndex
//				|| startIndex + count >= getBandCount()) {
//			String message = InternalResource.loadString("satrtIndex，count",
//					InternalResource.DatasetGridInvalidCount,
//					InternalResource.BundleName);
//			throw new IndexOutOfBoundsException(message);
//		}
//		return DatasetGridNative.jni_deleteBands(this.getHandle(), startIndex,
//				count);
//	}

	/**
	 * 根据波段名查询波段的索引号。
	 * 
	 * @param bandName
	 *            波段的名称。
	 * @return 返回波段的索引。
	 */
//	public int indexOf(String bandName) {
//		if (this.getHandle() == 0) {
//			String message = InternalResource.loadString(
//					"IndexOf(String bandName)",
//					InternalResource.HandleObjectHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
//			String message = InternalResource.loadString("m_datasource",
//					InternalResource.GlobalOwnerHasBeenDisposed,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		// 判断是否是多波段，不是多波段则不支持该操作。
//		if (!this.isMultiBand()) {
//			String message = InternalResource
//					.loadString(
//							"indexOf(String bandName)",
//							InternalResource.DatasetGridThisOperationIsAvailableForMultibandsDataOnly,
//							InternalResource.BundleName);
//			throw new UnsupportedOperationException(message);
//		}
//
//		if (bandName == null || bandName.trim().length() == 0) {
//			String message = InternalResource.loadString("value",
//					InternalResource.DatasetGridBandNameIsNotValid,
//					InternalResource.BundleName);
//			throw new IllegalStateException(message);
//		}
//		return DatasetGridNative.jni_indexOf(this.getHandle(), bandName);
//	}
	
	/**
	 * 根据指定的栅格数据集更新。
	 * 
	 * @param bandName
	 *            指定栅格数据集。
	 * @return 返回更新是否成功。
	 */
	public boolean update(DatasetGrid dataset) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"IndexOf(String bandName)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("m_datasource",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if(dataset.getHandle() == 0) {
			String message = InternalResource.loadString(
					"IndexOf(String bandName)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetGridNative.jni_Update(this.getHandle(), dataset.getHandle());
	}

	protected void clearHandle() {
		if (this.m_clipRegion != null) {
			this.m_clipRegion.clearHandle();
			this.m_clipRegion = null;
		}
		if (this.m_colors != null) {
			this.m_colors.clearHandle();
			this.m_colors = null;
		}
		setHandle(0);
	}

	protected static DatasetGrid createInstance(long handle,
			Datasource datasource) {
		return new DatasetGrid(handle, datasource);
	}
}
