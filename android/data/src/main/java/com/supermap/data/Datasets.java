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
 * @author ���ƽ�
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

		// ֻ������Դ���ܽ��иò���
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

//		// add by xuzw 2009-06-17 �������ݼ����Ƴ��Ļ��׳��쳣
//		// ��⼴��������󳤶�Ϊ64����Ϊͳһ��isAvailableDatasetName����
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
		// �����ƽ��м��
		if (datasetInfo.getName().trim().equals("")
				|| !isAvailableDatasetName(datasetInfo.getName())) {
			String message = InternalResource.loadString("name",
					InternalResource.DatasetsNameIsOccupied,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// ���type�Ƿ����������ķ���
		long dtHandle = DatasetsNative.jni_CreateDatasetVector(
				this.m_datasource.getHandle(), datasetInfo.getHandle());
		if (dtHandle != 0) {
			datasetVector = new DatasetVector(dtHandle, m_datasource);
			add(datasetVector);
		}

		return datasetVector;
	}	

	/**
	 * ����ɾ��ָ����ŵ����ݼ�
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

		// ֻ������Դ���ܽ��иò���
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

		// modified by zhengyl ����ȫ���ĳ���nameɾ�������ǻ������ݼ����ֵ�Ψһ�Կ��ǵģ�����index����������㲻һ�µ��µ�ɾ��ʧ������
		Dataset dataset = get(index);
		String name = dataset.getName();
		// �ӵײ�ɾ��
		boolean bResult = DatasetsNative.jni_DeleteDataset2(this.m_datasource
				.getHandle(), name);
		if (bResult) {
			// �ϲ���Ҫ�ͷ�handle

			dataset.clearHandle();
			this.m_datasets.remove(index);

		}
		return bResult;
	}
	
	/**
	 * ����ɾ��ָ�����Ƶ����ݼ�
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

		// ֻ������Դ���ܽ��иò���
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
		
		// �ӵײ�ɾ��
		boolean bResult = DatasetsNative.jni_DeleteDataset2(this.m_datasource
				.getHandle(), name);
		if (bResult) {
			// �ϲ���Ҫ�ͷ�handle
			Dataset dataset = get(index);
			dataset.clearHandle();
			this.m_datasets.remove(index);

		}
		return bResult;
	}
	
	/**
	 * �������ݼ�����
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
	 * ����ָ�����Ƶ����ݼ���
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
	 * ����ָ���������ݼ���
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
	 * ����ȡ��һ������Դ��û��ʹ�õ����ݼ������ơ�
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
	 * ���ݼ����Ƿ����
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
	 * ��鵱ǰ����Դ���Ƿ����ָ�����Ƶ����ݼ�
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
			// �˴�ÿ�ζ�Ҫȥͨ������ JNI��getName���Ƚϣ�Ч�ʱȽϵ͡��Ժ���޸�
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
	 * ������� ��datasource����close��datasetsӦ�����
	 */
	void clearHandle() {
		if (this.m_datasets != null) {
			int count = this.m_datasets.size();
			for (int i = 0; i < count; i++) {
				// ����handle�Ϳ����ˣ��˺�dataset������
				if (this.m_datasets.get(i) != null) {
					((Dataset) this.m_datasets.get(i)).clearHandle();
				}
			}
			this.m_datasets.clear();
			this.m_datasets = null;
		}

		this.m_datasource = null;
	}

	// ���ﴦ��ײ���1������
	// ���ݼ�getType��Java_com_supermap_data_DatasetNative_jni_1GetType���洦��
	// ��Java���Typeת�����ײ㻹û���ж�,ԭ��:û�е���
	void reset() {
		this.m_datasets.clear();
		int count = DatasourceNative.jni_GetDatasetsCount(m_datasource.getHandle());
		if(count<0){
			return;
		}
		long[] handles = new long[count];
		int[] types = new int[count];
		// @modified by ���ƽ� at 2007��7��17�� ����02ʱ54��11��
		// @reason: ɾ��Names,��¼ָ�룬����Ҫ��¼Name
		DatasourceNative.jni_GetDatasets(m_datasource.getHandle(), handles,
				types);
		for (int i = 0; i < count; i++) {
			// ���ݲ�ͬ�����ݼ����͹��첻ͬ�����ݼ�
			// @modified by ������ at 2007��10��11�� ����08ʱ43��19��
			// @reason:Dataset����ʶ������,�Ͳ���ӵ�Datasets����ȥ
			DatasetType javaType;
			try {
				javaType = (DatasetType) Enum.parseUGCValue(DatasetType.class,
						types[i]);
			} catch (RuntimeException e) {
				if (types[i] == 88) {// MBImage
					javaType = DatasetType.IMAGE;
				} else {
					// ������Ŀǰ��֧�ֵ�ö��,�˴������д��޸�
//					 System.out.println("��֧�ֵ����ݼ�����:" + types[i]);
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
	 * �ڲ����� ����Name��Ϊ�˱������jni
	 * 
	 * @param dataset
	 *            Dataset
	 */
	void add(Dataset dataset) {
			
		String name = dataset.getName();
		// datasetû����дequals�����contains�������õ���object�Ƚ�����
		// ���ܵ��õײ��is...�Ǹ��϶�����false����Ϊ�Ѿ���ӵ��ײ㿩
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
		// ��������κα���,�����ݼ�Ҳ�������κα��롣
		if (type.equals(DatasetType.TABULAR) || type.equals(DatasetType.POINT)) {
			creatable = creatable && encodeType.equals(EncodeType.NONE);
		} else if (isCreatableVectorType(type)) {
			// �������������±���
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
