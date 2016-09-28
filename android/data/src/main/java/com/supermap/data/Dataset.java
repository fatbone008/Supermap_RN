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
 * @author ���ƽ�
 * @version 2.0
 */
public abstract class Dataset extends InternalHandle {
	protected Datasource m_datasource = null;

	protected long m_selfEventHandle;
	
	protected static String m_senderMethodName;

	private PrjCoordSys m_prjCoordSys;

	protected Dataset() {
		m_senderMethodName = "PJConvert";
	}

	/**
	 * �ж����ݼ����Ƿ�Ϸ�
	 * 
	 * @param name
	 *            String
	 * @return InternalInvalidState ����Ϸ��򷵻�null�����Ϸ����ز��Ϸ��ľ�����Ϣ
	 */
	static InternalInvalidState isValidDatasetName(String name) {
		int ugcValue = FieldInfoNative.jni_IsValidTableName(name);
		if (ugcValue == -1) {
			return null;
		} else {
			return (InternalInvalidState) Enum.parseUGCValue(
					InternalInvalidState.class, ugcValue);
		}
	}
	
	public void setPrjCoordSys(PrjCoordSys value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"SetPrjCoordSys(PrjCoordSys value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		//Modified by HELH ��valueΪnullʱ������ͶӰ��Ϣ
		if (value == null) {
			DatasetNative.jni_UnSetPrjCoordSys(getHandle());
			if(m_prjCoordSys != null){
				m_prjCoordSys.clearHandle();
				m_prjCoordSys = null;
			}
		} else {
			long valueHandle = InternalHandle.getHandle(value);
			if (valueHandle == 0) {
				String message = InternalResource.loadString("value",
						InternalResource.GlobalInvalidConstructorArgument,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			m_prjCoordSys = (PrjCoordSys) value.clone();
			DatasetNative.jni_setPrjCoordSys(this.getHandle(), valueHandle);
		}
	}

	public PrjCoordSys getPrjCoordSys() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetPrjCoordSys()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// Added by Helh 2010-5-10
		// Reason: ��֤���ݼ��Ѵ�
		// {
		if(!isOpen()){
			open();
		}
		//}
		if (m_prjCoordSys == null) {
			long handle = DatasetNative.jni_getPrjCoordSys(this.getHandle());
			if (handle != 0) {
				m_prjCoordSys = PrjCoordSys.createInstance(handle, false);
			}
		}
		return m_prjCoordSys;
	}

	private boolean hasVersion() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("hasVersion()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetNative.jni_hasVersion(this.getHandle());
	}
	
	/**
	 * �������ݼ��ĸ�����Ϣ��
	 * 
	 * @return String
	 */
	public String getExtInfo() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetNative.jni_GetExtInfo(getHandle());
	}

	/**
	 * �������ݼ��ĸ�����Ϣ��
	 * 
	 * @param value
	 *            String
	 */
	public void setExtInfo(String value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (value == null) {
			value = "";
		}
		
		if(value.length() > 2047)
		{
			throw new IllegalStateException("The length is outof range");
		}

		// ֻ�����ݼ�Ҳ��������������Ϣ
		DatasetNative.jni_SetExtInfo(getHandle(), value);
	}

	/**
	 * �������ݼ��а������ж������С��Ӿ���
	 * 
	 * @return Rectangle2D
	 */
	public Rectangle2D getBounds() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] params = new double[4];

		DatasetNative.jni_GetBounds(getHandle(), params);
		Rectangle2D bounds = new Rectangle2D(params[0], params[1], params[2],
				params[3]);
		return bounds;
	}

	/**
	 * ���������Ƿ�ֻ��
	 * 
	 * @return boolean
	 */
	public boolean isReadOnly() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		// @modified by ������ at 2007��12��17�� ����11ʱ12��35��
		// @reason:��Dataset������DatasourceΪֻ��ʱ����Ӧ�÷��ص���ֻ���ġ�
		boolean isDatasourceReadOnly = m_datasource.isReadOnly();
		boolean isDatasetReadOnly = DatasetNative
				.jni_GetIsReadOnly(getHandle());
		return isDatasourceReadOnly || isDatasetReadOnly;
	}

	/**
	 * �ж����ݼ��Ƿ��Ѿ���
	 * 
	 * @return boolean
	 */
	public boolean isOpen() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetNative.jni_GetIsOpen(getHandle());
	}

	/**
	 * ���ظ�Dataset����ʵ����������Դ��
	 * 
	 * @return Datasource
	 */
	public Datasource getDatasource() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return this.m_datasource;
	}

	/**
	 * �������ݼ���������Ϣ��
	 * 
	 * @return String
	 */
	public String getDescription() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetNative.jni_GetDescription(getHandle());
	}

	/**
	 * �������ݼ���������Ϣ��
	 * 
	 * @param value
	 *            String
	 */
	public void setDescription(String value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (value == null) {
			value = "";
		}

		// ֻ�����ݼ�Ҳ��������������Ϣ
		DatasetNative.jni_SetDescription(getHandle(), value);
	}

	/**
	 * ���ش�Dataset�����ơ�
	 * 
	 * @return String
	 */
	public String getName() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetNative.jni_GetName(getHandle());
	}

	/**
	 * �������ݼ�������
	 * 
	 * @return int
	 */
	public DatasetType getType() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugcType = DatasetNative.jni_GetType(getHandle());
		DatasetType javaType = null;
		try {
			// @modified by ���ƽ� at 2007��8��13�� ����12ʱ46��38��
			// @reason ���ΪGrid���������쳣��ΪDEM��ʱ���׳��쳣�����д���
			javaType = (DatasetType) Enum.parseUGCValue(DatasetType.class,
					ugcType);
		} catch (RuntimeException ex) {
			throw ex;
		}
		return javaType;
	}

	/**
	 * �õ����ݼ����ݴ洢ʱ�ı��뷽ʽ
	 * 
	 * @return int
	 */
	public EncodeType getEncodeType() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		int ugcType = DatasetNative.jni_GetEncodeType(getHandle());
		return (EncodeType) Enum.parseUGCValue(EncodeType.class, ugcType);
	}

	/**
	 * ���ڹر����ݼ���
	 */
	public void close() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		DatasetNative.jni_Close(getHandle());
	}

	/**
	 * ���ڴ����ݼ���׼�������ݼ����в�����
	 */
	public boolean open() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetNative.jni_Open(getHandle());
	}

	/**
	 * �ڲ�ʹ�� ���ײ��ָ�������ͷź���Ҫ���ø÷�������Ӧ���ֶ���� ���ܻ�������recordSet�ȶ���Ҳ��Ҫ�ͷţ���������
	 */
	protected void clearHandle() {
		this.m_datasource = null;
		this.m_prjCoordSys = null;
		setHandle(0);
	}

	protected static boolean isVector(Dataset dataset) {
		boolean isVector = true;
		if (dataset == null || dataset.getHandle() == 0) {
			isVector = false;
		} else {
			isVector = dataset.isVector();
		}
		return isVector;
	}

	// @modified by ������ at 2007��8��9�� ����11ʱ21��35��
	// @reason: �޸�Ϊ protected����Layer��getDatasetʹ�á�
	protected static Dataset createInstance(long dtHandle,
			DatasetType javaType, Datasource datasource) {
		if (dtHandle == 0) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Dataset dataset = null;
		if (javaType.equals(DatasetType.IMAGE) || javaType.equals(DatasetType.WMS) || javaType.equals(DatasetType.WCS) ) {//�ƶ�������WMS�������ͣ�֧�ַ�������ʵʱ��ͼ  add by huangkj 2015-10-28
			dataset = new DatasetImage(dtHandle, datasource);
		} else if (
				javaType.equals(DatasetType.LINE)
				|| javaType.equals(DatasetType.POINT)
				|| javaType.equals(DatasetType.REGION)
				|| javaType.equals(DatasetType.TABULAR)
				|| javaType.equals(DatasetType.POINT3D)
				|| javaType.equals(DatasetType.LINE3D)
				|| javaType.equals(DatasetType.REGION3D)
				|| javaType.equals(DatasetType.CAD)
				|| javaType.equals(DatasetType.NETWORK)
				|| javaType.equals(DatasetType.NETWORK3D)
				|| javaType.equals(DatasetType.TEXT)
				|| javaType.equals(DatasetType.NdfVector)
				|| javaType.equals(DatasetType.WFS)) {
			dataset = new DatasetVector(dtHandle, datasource);
		}else if (javaType.equals(DatasetType.GRID)
					|| javaType.equals(DatasetType.DEM)) {
				dataset = new DatasetGrid(dtHandle, datasource);
		}else {
			// Layer������Ӧ�����ݼ��ǲ���ʶ������,��Layer�ܷ��ʵ�,������DatasetΪNULL,���Ҹ�Layer������ʾ
			dataset = null;

		}
		return dataset;
	}
	
	// @added by ������ at 2007��8��13�� ����10ʱ07��18��
	// @reason:�ڲ�ʹ��
	private boolean isVector() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return DatasetNative.jni_GetIsVector(getHandle());
	}

//	protected void clearSelfEventHandle() {
//		if (m_selfEventHandle != 0) {
//			DatasetImageNative.jni_DeleteSelfEventHandle(m_selfEventHandle);
//			m_selfEventHandle = 0;
//		}
//	}
	
	// �������ݼ��Ƿ�ֻ��
	//! \param readOnly Ϊtrue���������ݼ�Ϊֻ��
	public void setReadOnly(boolean readOnly) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_datasource == null || this.m_datasource.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.DatasetDatasoureIsEmpty,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		DatasetNative.jni_SetReadOnly(getHandle(), readOnly);

	}
}