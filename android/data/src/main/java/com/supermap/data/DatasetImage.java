package com.supermap.data;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


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
 * @author ���ƽ� ������
 * @version 2.0
 */
public class DatasetImage extends Dataset {
	
	private GeoRegion m_clipRegion = null;
	private boolean m_issetCurrentIndex = false;
	private static Integer m_lock = new Integer(0);
	
	private MapCacheService m_mapCacheService = new MapCacheService();

	DatasetImage(long handle, Datasource datasource) {
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
		
	}

	// ���ظ������ݼ��ĸ������
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
		return DatasetImageNative.jni_GetWidth(getHandle());
	}

	// ���ظ������ݼ��ĸ����߶�
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

		return DatasetImageNative.jni_GetHeight(getHandle());
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

		int ugcValue = DatasetImageNative.jni_GetPixelFormat(getHandle());
		return (PixelFormat) Enum.parseUGCValue(PixelFormat.class, ugcValue);
	}

	

	public Color getPixel(int column, int row) {
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
					InternalResource.DatasetImageColumnIsOutOfRange,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (row < 0 || row >= this.getHeight()) {
			String message = InternalResource.loadString("row",
					InternalResource.DatasetImageRowIsOutOfRange,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		int color = DatasetImageNative.jni_GetPixel(getHandle(), column, row);
		return new Color(color);
	}
	
	private Point2D imageToXY(Point point) {
		/**
		 * @todo ��Ҫ�޸� ע�⣬���ʧ�ܣ�������Ϊ�޸ĵײ�ĺ����ķ���Ȩ�������µ�
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
		DatasetImageNative.jni_ImageToXY(getHandle(), point.getX(), point.getY(), buffer);
		return new Point2D(buffer[0],buffer[1]);
	}

	//
	private Point xyToImage(Point2D point) {
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
		DatasetImageNative.jni_XYToImage(getHandle(), point.getX(), point
				.getY(), buffer);
		return new Point(buffer[0], buffer[1]);
	}

	/**
	 * ����ָ�����������ڵ������������Լ������ţ��������ص���ɫ��
	 * 
	 * @param column
	 *            ָ�����������ڵ�������
	 * @param row
	 *            ָ�����������ڵ�������
	 * @param indexes
	 *            ָ�������ص����кš�
	 * @return ����ָ�����ص���ɫ��
	 */
	private Color getPixel(int column, int row, int[] indexes) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"addBand(DatasetImage dataset)",
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
		// �ж��Ƿ��Ƕನ�Σ����Ƕನ����֧�ָò�����
		if (!this.isMultiBand()) {
			String message = InternalResource
					.loadString(
							"addBand(DatasetImage dataset)",
							InternalResource.DatasetImageThisOperationIsAvailableForMultibandsDataOnly,
							InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		}
		if (column < 0 || column >= this.getWidth()) {
			String message = InternalResource.loadString("column",
					InternalResource.DatasetImageColumnIsOutOfRange,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (row < 0 || row >= this.getHeight()) {
			String message = InternalResource.loadString("row",
					InternalResource.DatasetImageRowIsOutOfRange,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// �ж�������������ڲ��θ�����Χ�ڡ�
		int expectedLength = 0;
		ColorSpaceType type = this.getColorSpace();
		int value = type.getUGCValue();
		switch (value) {
		case 4:
		case 2: {
			expectedLength = 4;
		}
			break;
		case 1:
		case 3:
		case 5:
		case 6:
		case 7: {
			expectedLength = 3;
		}
			break;
		default:
			break;
		}
		if (indexes.length != expectedLength) {
			String message = InternalResource.loadString("indexes",
					InternalResource.GlobalArgumentOutOfBounds,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		int bandCount = this.getBandCount();
		int indexesCount = indexes.length;
		for (int i = 0; i < indexesCount; i++) {
			if (0 > indexes[i] || bandCount <= indexes[i]) {
				String message = InternalResource.loadString("indexes",
						InternalResource.GlobalIndexOutOfBounds,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}
		int colorRGB = DatasetImageNative.jni_GetPixelByIndexes(getHandle(),
				column, row, indexes);
		return new Color(colorRGB);
	}

	private boolean isMultiBand() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("isMultiBand()",
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
		return DatasetImageNative.jni_IsMultiBand(getHandle());
	}
	
	public ColorSpaceType getColorSpace() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getColorSpace()",
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
		// �ж��Ƿ��Ƕನ�Σ����Ƕನ����֧�ָò�����
		if (!this.isMultiBand()) {
			String message = InternalResource
					.loadString(
							"getColorSpace()",
							InternalResource.DatasetImageThisOperationIsAvailableForMultibandsDataOnly,
							InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		}
		int ugcType = DatasetImageNative.jni_GetColorSpaceType(getHandle());
		return (ColorSpaceType) Enum.parseUGCValue(ColorSpaceType.class,
				ugcType);
	}

	/**
	 * ���ز��εĸ�����
	 * 
	 * @return ���ز��εĸ�����
	 */
	public int getBandCount() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetBandCount()",
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
		// �ж��Ƿ��Ƕನ�Σ����Ƕನ����֧�ָò�����
		if (!this.isMultiBand()) {
			String message = InternalResource
					.loadString(
							"getBandCount()",
							InternalResource.DatasetImageThisOperationIsAvailableForMultibandsDataOnly,
							InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		}

		return DatasetImageNative.jni_GetBandCount(getHandle());
	}

	public boolean getHasPyramid() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetImageNative.jni_GetHasPyramid(getHandle());
	}

	// ����������
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
					InternalResource.DatasetImageShouldBeClosedBeforeBuildPyramid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean bResult = false;
		if (this.getHasPyramid()) {
			bResult = true;
		} else {
			bResult = DatasetImageNative.jni_BuildPyramid(getHandle());
		}
		return bResult;
	}

	// ���½�����
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

		bResult = DatasetImageNative.jni_UpdatePyramid(getHandle(),value.getLeft(), value
				.getBottom(), value.getRight(), value.getTop());
			
		return bResult;
	}
	
	// ���������
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

		return DatasetImageNative.jni_RemovePyramid(getHandle());
	}
	
	protected void clearHandle() {
		if (this.m_clipRegion != null) {
			this.m_clipRegion.clearHandle();
			this.m_clipRegion = null;
		}
		setHandle(0);
	}
	
	public MapCacheService getMapCacheService() {
		return m_mapCacheService;
	}
	
	public class MapCacheService {
		Vector<MapCacheListener> mMapCacheListeners = new Vector<MapCacheListener>();
		
		private ExecutorService executor;
		
		public MapCacheService(){
		}
		
		@Deprecated //���ñ�ʶ�����С�����ߺ�bounds�Ľӿ�
		public void startDownload(Rectangle2D rcBounds) {
			if (getHandle() == 0) {
				String message = InternalResource.loadString("",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			if (rcBounds == null || rcBounds.isEmpty()) {
				String message = InternalResource.loadString("rcBounds",
						InternalResource.GlobalArgumentNull,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			DatasetImageNative.jni_RegisterAllEvent(getHandle(), DatasetImage.MapCacheService.this);
			
			DatasetImageNative.jni_StartDownload(getHandle(), rcBounds.getLeft(), rcBounds
					.getBottom(), rcBounds.getRight(), rcBounds.getTop());
		}
		
		public void stopDownload() {
			if (getHandle() == 0) {
				String message = InternalResource.loadString("",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			DatasetImageNative.jni_StopDownload(getHandle());
		}
		
		public void setListener(MapCacheListener listener) {
			if(listener != null){
				if(!mMapCacheListeners.contains(listener)){
					mMapCacheListeners.add(listener);
				}
			}
		}
		
		public void removeListener(MapCacheListener listener) {
			if (mMapCacheListeners != null
					&& mMapCacheListeners.contains(listener)) {
				mMapCacheListeners.remove(listener);
			}
		}
		
		
		/**
		 * �ײ�ص�,�ص������ķ���Ȩ��һ������д��private��
		 */
		protected void onCheckedCallBack(){
			if(mMapCacheListeners != null ){
				Vector<MapCacheListener> listener = mMapCacheListeners;
				int count = listener.size();
				for(int i = 0;i < count; i++){
					((MapCacheListener)listener.elementAt(i)).onChecked();
				}
			}
		}

		/**
		 * �ײ�ص�,�ص������ķ���Ȩ��һ������д��private��
		 */
		protected void onCompleteCallBack(int failedCount) {
			if(mMapCacheListeners != null ){
				Vector<MapCacheListener> listener = mMapCacheListeners;
				int count = listener.size();
				for(int i = 0;i < count; i++){
					((MapCacheListener)listener.elementAt(i)).onComplete(failedCount);
				}
			}
		}
		
		/**
		 * �ײ�ص�,�ص������ķ���Ȩ��һ������д��private��
		 */
		protected void onProgressCallBack(int step){
			if(mMapCacheListeners != null ){
				Vector<MapCacheListener> listener = mMapCacheListeners;
				int count = listener.size();
				for(int i = 0;i < count; i++){
					((MapCacheListener)listener.elementAt(i)).onProgress(step);
				}
			}
		}
		
		/**
		 * �ײ�ص�,�ص������ķ���Ȩ��һ������д��private��
		 */
		protected void onCacheStatusCallBack(int downloadCount, long totalCount){
			if(mMapCacheListeners != null ){
				Vector<MapCacheListener> listener = mMapCacheListeners;
				int count = listener.size();
				for(int i = 0;i < count; i++){
					((MapCacheListener)listener.elementAt(i)).onCacheStatus(downloadCount, totalCount);
				}
			}
		}

		// �����������ļ���
		private void renameCache() {
			if (getHandle() == 0) {
				String message = InternalResource.loadString("removeCache(Rectangle2D rcBounds)",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
		
			DatasetImageNative.jni_RenameCache(getHandle());
		}

		// ɾ�������ļ�
		private void deleteCache() {
			if (getHandle() == 0) {
				String message = InternalResource.loadString("removeCache(Rectangle2D rcBounds)",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
		
			DatasetImageNative.jni_RemoveCache(getHandle());
		}
		
		// ɾ�����ػ���
		public void removeCache() {
			renameCache();
			
			if (executor == null) {
				// ����һ�����̵߳��̳߳�
				executor = Executors.newSingleThreadExecutor();
			}
			
			executor.execute(new Runnable() {
				
				//@Override
				public void run() {
					// TODO Auto-generated method stub
					deleteCache();
				}
			});
		}
		
		// ɾ�����ػ���
		public void removeCache(double scale, Rectangle2D bounds) {
			if (getHandle() == 0) {
				String message = InternalResource.loadString("removeCache(double scale, Rectangle2D bounds)",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			if (bounds == null) {
				String message = InternalResource.loadString("bounds",
						InternalResource.GlobalOwnerHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			if (scale == 0) {
				String message = InternalResource.loadString("scale",
						InternalResource.GlobalArgumentShouldMoreThanZero,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			DatasetImageNative.jni_RemoveCacheWithScaleBounds(getHandle(), scale, bounds.getLeft(), 
					bounds.getBottom(), bounds.getRight(), bounds.getTop());
		}

		// Ԥ����ӿڣ�������û����õ������С�����ߣ��ҵ���Ӧ�Ļ��漶����л���
		// ���ĳһ�㼶���������Ƭ���� > 100��  �ţ���ֱ���˳���
		//! \param maxScale ��������
		//! \param minScale ��С������
		//! \param rcBounds �����淶Χ
		public void startDownload(double maxScale, double minScale, Rectangle2D rcBounds) {
			if (getHandle() == 0) {
				String message = InternalResource.loadString("",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			if (rcBounds == null || rcBounds.isEmpty()) {
				String message = InternalResource.loadString("rcBounds",
						InternalResource.GlobalArgumentNull,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			
			if (maxScale <= 0 || minScale <= 0) {
				String message = InternalResource.loadString("maxScale, minScale",
						InternalResource.GlobalArgumentShouldNotBeNegative,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			
			if (maxScale < minScale) {
				String message = "maxScale must be not less than minScale!";
				throw new IllegalArgumentException(message);
			}
			DatasetImageNative.jni_RegisterAllEvent(getHandle(), DatasetImage.MapCacheService.this);
			
			DatasetImageNative.jni_StartDownloadWithScale(getHandle(), maxScale, minScale, rcBounds.getLeft(),
					rcBounds.getBottom(), rcBounds.getRight(), rcBounds.getTop());
		}
	}

}
