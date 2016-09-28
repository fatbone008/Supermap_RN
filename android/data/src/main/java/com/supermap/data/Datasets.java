package com.supermap.data;

import java.util.ArrayList;

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
public class Datasets {

	private ArrayList m_datasets = null;
	private Datasource m_datasource = null;

	
	protected Datasets() {

	}

	Datasets(Datasource datasource) {
			clearHandle();
			this.m_datasets = new ArrayList();
			this.m_datasource = datasource;
			reset();
	}

	public DatasetVector create(DatasetVectorInfo datasetInfo) {
		
		if (!this.isValid()) {
			String message = InternalResource.loadString(
					"create(DatasetVectorInfo datasetInfo)",
					InternalResource.DatasetsParentIsNotValid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		// 只读数据源不能进行该操作
		if (this.m_datasource.isReadOnly()) {
			String message = InternalResource.loadString(
					"create(DatasetVectorInfo datasetInfo)",
					InternalResource.DatasetsDatasourceIsReadOnly,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		}

		if (datasetInfo == null) {
			String message = InternalResource.loadString("datasetInfo",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (datasetInfo.getHandle() == 0) {
			String message = InternalResource.loadString("datasetInfo",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

//		// add by xuzw 2009-06-17 网络数据集名称长的话抛出异常
//		// 类库即将调整最大长度为64，改为统一调isAvailableDatasetName方法
//		if (datasetInfo.getType().equals(DatasetType.NETWORK)) {
//			String name = datasetInfo.getName();
//			String value = name + "_node";
//			if (!this.isAvailableDatasetName(value)) {
//				String message = InternalResource
//						.loadString(
//								"networkDatasetName",
//								InternalResource.DatasetsCreateDatasetNameLengthMoreThanThirty,
//								InternalResource.BundleName);
//				throw new IllegalArgumentException(message);
//			}
//		}

		if (!Datasets.isCreatableVectorType(datasetInfo.getType())) {
			String message = InternalResource.loadString("type",
					InternalResource.DatasetsFailToCreateBecauseOfDatasetType,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (!Datasets.isCreatableEnCodeType(datasetInfo.getType(), datasetInfo
				.getEncodeType())) {
			String message = InternalResource.loadString("encodeType",
					InternalResource.DatasetsFailToCreateBecauseOfEncodeType,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		DatasetVector datasetVector = null;
		// 对名称进行检查
		if (datasetInfo.getName().trim().equals("")
				|| !isAvailableDatasetName(datasetInfo.getName())) {
			String message = InternalResource.loadString("name",
					InternalResource.DatasetsNameIsOccupied,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 检查type是否属于适量的范畴
		long dtHandle = DatasetsNative.jni_CreateDatasetVector(
				this.m_datasource.getHandle(), datasetInfo.getHandle());
		if (dtHandle != 0) {
			datasetVector = new DatasetVector(dtHandle, m_datasource);
			add(datasetVector);
		}

		return datasetVector;
	}	

	/**
	 * 用于删除指定序号的数据集
	 * 
	 * @param index
	 *            int
	 */
	public boolean delete(int index) {
		
		if (!this.isValid()) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetsParentIsNotValid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		// 只读数据源不能进行该操作
		if (this.m_datasource.isReadOnly()) {
			String message = InternalResource.loadString("delete(int index)",
					InternalResource.DatasetsDatasourceIsReadOnly,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		}

		if (index < 0 || index >= this.getCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}

		// modified by zhengyl 这里全部改成用name删除，这是基于数据集名字的唯一性考虑的，避免index组件层与类库层不一致导致的删除失败问题
		Dataset dataset = get(index);
		String name = dataset.getName();
		// 从底层删除
		boolean bResult = DatasetsNative.jni_DeleteDataset2(this.m_datasource
				.getHandle(), name);
		if (bResult) {
			// 上层需要释放handle

			dataset.clearHandle();
			this.m_datasets.remove(index);

		}
		return bResult;
	}
	
	/**
	 * 用于删除指定名称的数据集
	 * 
	 * @param name
	 *            String
	 */
	public boolean delete(String name) {
		
		if (!this.isValid()) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetsParentIsNotValid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		// 只读数据源不能进行该操作
		if (this.m_datasource.isReadOnly()) {
			String message = InternalResource.loadString("delete(String name)",
					InternalResource.DatasetsDatasourceIsReadOnly,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		}
		
		int index = this.indexOf(name);
		if(-1 == index){
			return false;
		}
		
		// 从底层删除
		boolean bResult = DatasetsNative.jni_DeleteDataset2(this.m_datasource
				.getHandle(), name);
		if (bResult) {
			// 上层需要释放handle
			Dataset dataset = get(index);
			dataset.clearHandle();
			this.m_datasets.remove(index);

		}
		return bResult;
	}
	
	/**
	 * 返回数据集个数
	 * 
	 * @return int
	 */
	public int getCount() {
		if (!this.isValid()) {
			String message = InternalResource.loadString("getCount()",
					InternalResource.DatasetsParentIsNotValid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return this.m_datasets.size();
	}

	/**
	 * 返回指定名称的数据集。
	 * 
	 * @param name
	 *            String
	 * @return Dataset
	 */
	public Dataset get(String name) {
		if (!this.isValid()) {
			String message = InternalResource.loadString("get(String name)",
					InternalResource.DatasetsParentIsNotValid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		Dataset dataset = null;
		int index = this.indexOf(name);
		if (index != -1) {
			dataset = get(index);
		}
		return dataset;
	}

	/**
	 * 返回指定索引数据集。
	 * 
	 * @param index
	 *            int
	 * @return Dataset
	 */
	public Dataset get(int index) {
		if (!this.isValid()) {
			String message = InternalResource.loadString("get(int index)",
					InternalResource.DatasetsParentIsNotValid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (index < 0 || index >= this.getCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
			
		return (Dataset) this.m_datasets.get(index);
	}
	
	/**
	 * 用于取得一个数据源中没被使用的数据集的名称。
	 * 
	 * @param datasetName
	 *            String
	 * @return String
	 */
	public String getAvailableDatasetName(String name) {
		if (!this.isValid()) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetsParentIsNotValid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (name == null) {
			name = "";
		}

		return DatasetsNative.jni_GetUnoccupiedDatasetName(this.m_datasource
				.getHandle(), name);
	}
	
	/**
	 * 数据集名是否可用
	 * 
	 * @param name
	 *            String
	 * @return boolean
	 */
	public boolean isAvailableDatasetName(String name) {
		if (!this.isValid()) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetsParentIsNotValid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean result = false;
		if (name != null && name.trim().length() != 0) {
			result = DatasetsNative.jni_IsAvailableDatasetName(m_datasource
					.getHandle(), name);
		}
		return result;
	}
	
	/**
	 * 检查当前数据源中是否包含指定名称的数据集
	 * 
	 * @param datasetName
	 *            String
	 * @return boolean
	 */
	public boolean contains(String name) {
		boolean bResult = false;
		if (this.indexOf(name) != -1) {
			bResult = true;
		}
		return bResult;
	}

	public int indexOf(String name) {
		if (!this.isValid()) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetsParentIsNotValid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		int index = -1;
		if (name != null && name.trim().length() != 0) {
			// 此处每次都要去通过调用 JNI的getName来比较，效率比较低。以后可修改
			int count = this.getCount();
			for (int i = 0; i < count; i++) {
				if (name.equalsIgnoreCase(this.get(i).getName())) {
					return i;
				}
			}
		}
		return index;
	}

	/**
	 * 清空数据 当datasource调用close后，datasets应该清空
	 */
	void clearHandle() {
		if (this.m_datasets != null) {
			int count = this.m_datasets.size();
			for (int i = 0; i < count; i++) {
				// 设置handle就可以了，此后dataset不可用
				if (this.m_datasets.get(i) != null) {
					((Dataset) this.m_datasets.get(i)).clearHandle();
				}
			}
			this.m_datasets.clear();
			this.m_datasets = null;
		}

		this.m_datasource = null;
	}

	// 这里处理底层多对1的问题
	// 数据集getType在Java_com_supermap_data_DatasetNative_jni_1GetType里面处理
	// 将Java层的Type转化到底层还没做判断,原因:没有调用
	void reset() {
		this.m_datasets.clear();
		int count = DatasourceNative.jni_GetDatasetsCount(m_datasource.getHandle());
		if(count<0){
			return;
		}
		long[] handles = new long[count];
		int[] types = new int[count];
		// @modified by 李云锦 at 2007年7月17日 下午02时54分11秒
		// @reason: 删除Names,记录指针，不需要记录Name
		DatasourceNative.jni_GetDatasets(m_datasource.getHandle(), handles,
				types);
		for (int i = 0; i < count; i++) {
			// 根据不同的数据集类型构造不同的数据集
			// @modified by 孔令亮 at 2007年10月11日 上午08时43分19秒
			// @reason:Dataset不认识的类型,就不添加到Datasets里面去
			DatasetType javaType;
			try {
				javaType = (DatasetType) Enum.parseUGCValue(DatasetType.class,
						types[i]);
			} catch (RuntimeException e) {
				if (types[i] == 88) {// MBImage
					javaType = DatasetType.IMAGE;
				} else {
					// 可能有目前不支持的枚举,此处代码有待修改
//					 System.out.println("不支持的数据集类型:" + types[i]);
					continue;
				}
			}

			try {
				Dataset dataset = Dataset.createInstance(handles[i], javaType,
						m_datasource);

				if (dataset != null) {
					this.m_datasets.add(dataset);
				}
			} catch (IllegalArgumentException ex) {
				continue;
			}
		}
	}

	/**
	 * 内部调用 传入Name是为了避免调用jni
	 * 
	 * @param dataset
	 *            Dataset
	 */
	void add(Dataset dataset) {
			
		String name = dataset.getName();
		// dataset没有重写equals，因此contains方法调用的是object比较引用
		// 不能调用底层的is...那个肯定返回false，因为已经添加到底层咯
		if (!this.contains(name)) {
			this.m_datasets.add(dataset);	
		}	
	}
	

	protected static void add(Datasets datasets, Dataset dataset) {
		if (datasets != null) {
			datasets.add(dataset);
		}
	}
	
	private boolean isValid() {
		boolean bIsValid = true;
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0
				|| this.m_datasource.getWorkspace() == null
				|| this.m_datasource.getWorkspace().getHandle() == 0) {
			bIsValid = false;
		}
		return bIsValid;
	}
	
	static boolean isCreatableVectorType(DatasetType type) {
		boolean bResult = true;
		if (type.equals(DatasetType.IMAGE)) {
			bResult = false;
		}
		return bResult;
	}

	static boolean isCreatableEnCodeType(DatasetType type, EncodeType encodeType) {
		boolean creatable = true;
		// 表格不能用任何编码,点数据集也不能用任何编码。
		if (type.equals(DatasetType.TABULAR) || type.equals(DatasetType.POINT)) {
			creatable = creatable && encodeType.equals(EncodeType.NONE);
		} else if (isCreatableVectorType(type)) {
			// 其他不能用以下编码
			creatable = creatable && !encodeType.equals(EncodeType.DCT);
			creatable = creatable && !encodeType.equals(EncodeType.SGL);
			creatable = creatable && !encodeType.equals(EncodeType.LZW);
		} else if (type.equals(DatasetType.IMAGE)) {
			creatable = (encodeType.equals(EncodeType.NONE)
					|| encodeType.equals(EncodeType.DCT)
					|| encodeType.equals(EncodeType.SGL) || encodeType
					.equals(EncodeType.LZW));
		}
		return creatable;
	}
}
