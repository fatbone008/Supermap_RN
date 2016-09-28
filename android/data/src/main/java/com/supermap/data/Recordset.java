package com.supermap.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

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
public class Recordset extends InternalHandleDisposable {
	private DatasetVector m_datasetVector = null;

//	private DatasetRelationship m_datasetRel = null;

	// �ռ��ѯʱ���¼�����ֶΣ������û��ٴλ�ȡQueryParameter
	private Object m_spatialQueryObject = null;

	// private SpatialComparePattern[][] m_spatialFilter = null;
	private SpatialQueryMode m_queryMode = SpatialQueryMode.NONE;

	// �ֶ�����ͼ���
	// ������洢��ȫ�Ǹ���
	// private Map<String, RepresentationElement> elementMap = null;

	private BatchEditor m_batchEdit = null;

	private boolean m_begin = false;

	private int m_fieldCount;

	private boolean m_isQueryCursorTypeStatic;

	private FieldInfos m_fieldInfos;

	// ���ڱ�ʶ��ǰ��ѯ�ֶ��Ƿ�Ϊnull,�����ǰ��¼������BOF��EOFʱ���ֶ�ֵҲΪnull
	private boolean[] m_flag = new boolean[] { false };

	// private boolean m_batchEdit;

	transient Vector m_steppedListeners;

	protected long m_selfEventHandle;


	// @added by ������ at 2007��8��14�� ����09ʱ17��47��
	// @reason:�����protected���캯�����̳У������createInstance����
	protected Recordset() {

	}

	/**
	 * �ڲ�ʹ�� Ĭ�ϵĸö����ǿ��Ա��ͷŵ�
	 *
	 * @param handle
	 *            long Recordsetָ��
	 * @param datasetVector
	 *            DatasetVector
	 */
	Recordset(long handle, DatasetVector datasetVector) {
		this.setHandle(handle, true);
		this.m_datasetVector = datasetVector;
		m_isQueryCursorTypeStatic = RecordsetNative
				.jni_IsCursorTypeStatic(getHandle());
		m_fieldInfos = new FieldInfos(RecordsetNative
				.jni_GetFieldInfos(getHandle()));
		m_fieldCount = m_fieldInfos.getCount();
	}

	/**
	 * �ڲ�ʹ�� Ĭ�ϵĸö����ǿ��Ա��ͷŵ�
	 *
	 * @param handle
	 *            long Recordsetָ��
	 * @param datasetRel
	 *            DatasetRelationship
	 */
//	Recordset(long handle, DatasetRelationship datasetRel) {
//		this.setHandle(handle, true);
//		this.m_datasetRel = datasetRel;
//		m_isQueryCursorTypeStatic = RecordsetNative
//				.jni_IsCursorTypeStatic(getHandle());
//		m_fieldInfos = new FieldInfos(RecordsetNative
//				.jni_GetFieldInfos(getHandle()));
//		m_fieldCount = m_fieldInfos.getCount();
//	}

	// �õ���¼�����������ݱ������м�¼��Ӧ�ļ��ζ������Ӿ��Ρ�
	public Rectangle2D getBounds() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		double[] buffer = new double[4];
		RecordsetNative.jni_GetBounds(getHandle(), buffer);
		Rectangle2D bounds = new Rectangle2D(buffer[0], buffer[1], buffer[2],
				buffer[3]);
		return bounds;
	}

	// ��ȡ��¼����Ӧ�����ݼ�
	public DatasetVector getDataset() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return this.m_datasetVector;
	}


	// ��ȡ��ѯ����
	public QueryParameter getQueryParameter() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		long queryHandle = RecordsetNative.jni_GetQueryParameter(getHandle());
		// �޸�Ϊ���ض���Ŀ���,������������ͷ�
		QueryParameter queryParameter = new QueryParameter(queryHandle,
				this.m_spatialQueryObject, this.m_queryMode);
		queryParameter.setIsDisposable(true);
		return queryParameter;
	}

	/**
	 * �õ���¼������ֶμ��϶��� ÿ�η��ض���Ŀ��� ��˽������ø÷���
	 *
	 * @return
	 */
	public FieldInfos getFieldInfos() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		// long handle = RecordsetNative.jni_GetFieldInfos(getHandle());
		// if (handle == 0) {
		// return null;
		// }

		// FieldInfos fieldInfos = new FieldInfos(m_fieldInfos);
		// ʼ�շ��ص��ǵײ����Ŀ���������ǿ��Ա��ͷŵ�
		// fieldInfos.setIsDisposable(true);
		return new FieldInfos(m_fieldInfos);
	}

	// ���ؼ�¼�����ֶΣ�Field������Ŀ��
	public int getFieldCount() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		m_fieldCount = RecordsetNative.jni_GetFieldCount(this.getHandle());
		return m_fieldCount;
	}

	// �жϼ�¼���Ƿ��Ѿ��رա�
	public boolean isClosed() {
		boolean isClosed = false;
		if(m_datasetVector != null)
		{
			isClosed = (this.getHandle() == 0
					|| this.m_datasetVector.getHandle() == 0 || !this.m_datasetVector
					.isOpen());
		}
//		else if(m_datasetRel != null)
//		{
//			isClosed = (this.getHandle() == 0
//					|| this.m_datasetRel.getHandle() == 0 || !this.m_datasetRel
//					.isOpen());
//		}
		else
		{
			isClosed = true;
		}

		// ����Ѿ��ͷţ��������Handle��Ϊ0
		if (isClosed) {
			this.setHandle(0);
		}
		return isClosed;
	}

	// �ж�\u00A0Recordset\u00A0���󼯺ϵļ�¼ָ���Ƿ�λ����ʼλ�ã�λ����ʼλ���򷵻�\u00A0True\u00A0�����򷵻�False��
	public boolean isBOF() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_GetIsBOF(getHandle());
	}

	// �ж�\u00A0Recordset\u00A0���󼯺ϵļ�¼ָ���Ƿ�λ�����λ�á�λ�����λ���򷵻�\u00A0True\u00A0�����򷵻�False
	public boolean isEOF() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_GetIsEOF(getHandle());
	}

	// �жϼ�¼�����Ƿ��м�¼��True\u00A0��ʾ�ü�¼���������ݣ���ʱ��RecordCount\u00A0��ֵӦΪ-1��
	public boolean isEmpty() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_GetIsEmpty(getHandle());
	}

	// ���ؼ�¼���Ƿ�ֻ����True ��ʾ����
	public boolean isReadOnly() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// @modified by ������ at 2007��12��17�� ����01ʱ16��02��
		// @reason:���ڴ�Wrapj�е��á�������DatasetVectro�������Ƿ�ֻ����
		// return RecordsetNative.jni_GetIsReadOnly(getHandle());

		// ��Recordset��ͨ��CursorType.STATIC��ѯ�����ģ���ΪreadOnly����
		// boolean isQueryCursorTypeStatic = RecordsetNative
		// .jni_IsCursorTypeStatic(getHandle());
//		if(m_datasetVector != null)
//		{
			return m_isQueryCursorTypeStatic || m_datasetVector.isReadOnly();
//		}
//		else
//		{
//			return m_isQueryCursorTypeStatic || m_datasetRel.isReadOnly();
//		}
	}

	// ���ؼ�¼���м�¼��������
	public int getRecordCount() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return RecordsetNative.jni_GetRecordCount(getHandle());
	}

	// ����ӳɹ�������ֵΪ�¼���ļ�¼��Ӧ�ļ��ζ����ID�ţ���0��ʼ����ʧ���򷵻�Ϊ-1��
	public boolean addNew(Geometry geometry) {
		//����ǹ�ϵ�����ݼ�ֱ�ӷ���
//		if(m_datasetRel != null)
//		{
//			return false;
//		}
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		if(geometry==null)
		    enableEditBulk(true);
		Geometry temp = geometry;
		boolean bResult = false;
		if (!m_begin) {
			if (temp == null) {
				// @modified by ������ at 2007��11��21�� ����07ʱ58��30��
				// @reason:addNew,��DatasetTypeΪTABULARʱ���п��ܷ���True
				if (m_datasetVector.getType() == DatasetType.TABULAR) {
					bResult = RecordsetNative.jni_AddNew(getHandle(), 0);
				}
			} else {
				long geometryHandle = temp.getHandle();
				if (geometryHandle == 0) {
					String message = InternalResource
							.loadString(
									"geometry",
									InternalResource.GlobalArgumentObjectHasBeenDisposed,
									InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
				if (temp.isEmpty()) {
					String message = InternalResource.loadString("geometry",
							InternalResource.RecordsetGeometryIsEmpty,
							InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
				// @modified by ������ at 2007��11��21�� ����07ʱ58��30��
				// @reason:addNew,��DatasetType"��"ΪTABULARʱ���п��ܷ���True
				if (m_datasetVector.getType() != DatasetType.TABULAR) {
					bResult = RecordsetNative.jni_AddNew(getHandle(),
							geometryHandle);
				}
			}
		} else {
			//Modified by Pan zhibin 2010-10-12
			if (temp == null) {
				// @modified by ������ at 2007��11��21�� ����07ʱ58��30��
				// @reason:addNew,��DatasetTypeΪTABULARʱ���п��ܷ���True
				if (m_datasetVector.getType() == DatasetType.TABULAR) {
					bResult = RecordsetNative.jni_AddNew1(getHandle(),
							0);
					//return bResult;
				}
			}
			else{
				long geometryHandle = temp.getHandle();
				if (geometryHandle == 0) {
					String message = InternalResource.loadString("geometry",
							InternalResource.GlobalArgumentObjectHasBeenDisposed,
							InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
				if (temp.isEmpty()) {
					String message = InternalResource.loadString("geometry",
							InternalResource.RecordsetGeometryIsEmpty,
							InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
				if (m_datasetVector.getType() != DatasetType.TABULAR) {
					bResult = RecordsetNative.jni_AddNew1(getHandle(),
							geometryHandle);
				}
			}
		}
		makeSureNativeObjectLive(temp);
		return bResult;
	}

	/**
	 * ��Ӽ��ζ���������ص��ֶ�ֵ
	 *
	 * @param geometry
	 *            ��Ҫ��ӵļ��ζ���
	 * @param map
	 *            ��Ҫ���õ�����ֵ����Ϊ�ֶ����ƣ�ֵΪ��Ӧ�ֶε�ֵ��
	 * @return
	 */
	public boolean addNew(Geometry geometry, java.util.Map<String, Object> map) {
		//����ǹ�ϵ�����ݼ�ֱ�ӷ���
//		if(m_datasetRel != null)
//		{
//			return false;
//		}
		// ���mapΪnull��ֱ�ӵ��������addNew
		if (map == null) {
			return this.addNew(geometry);
		}
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		Geometry temp = geometry;
		Object[] keyArray = map.keySet().toArray();
		int length = keyArray.length;

		// ����������ֵ
		String[] keys = new String[length];
		long[] handles = new long[length];
		InternalVariant[] variants = new InternalVariant[length];
		for (int i = 0; i < length; i++) {
			keys[i] = (String) keyArray[i];
			if (keys[i].equalsIgnoreCase("smid")) {
				String message = InternalResource.loadString("map",
						InternalResource.MAPCanNotContainSMID,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			if (map.get(keys[i]) != null) {
				if (!InternalVariant.isSupportedInstance(map.get(keys[i]))) {
					String message = InternalResource.loadString("value",
							InternalResource.GlobalArgumentTypeInvalid,
							InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
				variants[i] = new InternalVariant(map.get(keys[i]));
			} else {
				variants[i] = new InternalVariant();
			}
			// �Ȳ����ͷţ���֤����Wrapj���ú�������ǰ���ͷ�
			variants[i].setIsDisposable(false);
			handles[i] = variants[i].getHandle();
		}

		boolean bResult = false;
		if (!m_begin) {
			if (temp == null) {
				// @modified by ������ at 2007��11��21�� ����07ʱ58��30��
				// @reason:addNew,��DatasetTypeΪTABULARʱ���п��ܷ���True
				if (m_datasetVector.getType() == DatasetType.TABULAR) {
					bResult = RecordsetNative.jni_AddNew2(getHandle(), 0
							, keys, handles);
				}
			} else {
				long geometryHandle = temp.getHandle();
				if (geometryHandle == 0) {
					String message = InternalResource
							.loadString(
									"geometry",
									InternalResource.GlobalArgumentObjectHasBeenDisposed,
									InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
				if (temp.isEmpty()) {
					String message = InternalResource.loadString("geometry",
							InternalResource.RecordsetGeometryIsEmpty,
							InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
				// @modified by ������ at 2007��11��21�� ����07ʱ58��30��
				// @reason:addNew,��DatasetType"��"ΪTABULARʱ���п��ܷ���True
				if (m_datasetVector.getType() != DatasetType.TABULAR) {
					bResult = RecordsetNative.jni_AddNew2(getHandle(),
							geometryHandle, keys, handles);
				}
			}
		} else {
			//Modified by Pan zhibin 2010-10-12
			if (temp == null) {
				// @modified by ������ at 2007��11��21�� ����07ʱ58��30��
				// @reason:addNew,��DatasetTypeΪTABULARʱ���п��ܷ���True
				if (m_datasetVector.getType() == DatasetType.TABULAR) {
//					bResult = RecordsetNative.jni_AddNew1(getHandle(),
//							0);

					bResult = RecordsetNative.jni_AddNew3(getHandle(),
							0, keys, handles);

				}
			}
			else {
				long geometryHandle = temp.getHandle();
				if (geometryHandle == 0) {
					String message = InternalResource.loadString("geometry",
							InternalResource.GlobalArgumentObjectHasBeenDisposed,
							InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
				if (temp.isEmpty()) {
					String message = InternalResource.loadString("geometry",
							InternalResource.RecordsetGeometryIsEmpty,
							InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
				if (m_datasetVector.getType() != DatasetType.TABULAR) {
					bResult = RecordsetNative.jni_AddNew3(getHandle(),
							geometryHandle, keys, handles);
				}
			}
		}

		// ����ǰ�������еı���
		for (int i = 0; i < variants.length; i++) {
			variants[i].setIsDisposable(true);
			variants[i].dispose();
			variants[i] = null;
		}
		makeSureNativeObjectLive(temp);
		return bResult;
	}

	// �������༭ Recordset �ĵ�ǰ��¼���ɹ��򷵻� True��
	public boolean edit() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return RecordsetNative.jni_Edit(getHandle());
	}

	// �����ύ�Լ�¼�����޸ģ�������ӡ��༭��¼���޸��ֶ�ֵ�Ĳ�����
	public boolean update() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (m_begin) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetBatchEditorIsBeginning,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		enableEditBulk(false);
		// TODO �Ժ����֧��������ѯ������ķ���ֵ���д�ȷ��
		boolean result = RecordsetNative.jni_Update(getHandle());
		return result;
	}

	// ����ȡ���ڵ��� Update ����ǰ�Ե�ǰ��¼���¼�¼�������κθ��ģ��ɹ��򷵻� True��
	public boolean cancelUpdate() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return RecordsetNative.jni_CancelUpdate(getHandle());
	}

	// ���ڹرռ�¼����ʹ�����ݼ��Ժ�Ӧ��ʱ�رա�
	public void close() {
		if (!isClosed()) {
			RecordsetNative.jni_Close(getHandle());
			clearHandle();

			if (m_fieldInfos != null) {
				m_fieldInfos.setIsDisposable(true);
				m_fieldInfos.dispose();
				m_fieldInfos = null;
			}
		}
	}

	// ����ɾ�����ݼ��еĵ�ǰ��¼���ɹ��򷵻� True��
	public boolean delete() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return RecordsetNative.jni_Delete(getHandle());
	}

	// ������ɾ��ָ����¼���е����м�¼��
	public boolean deleteAll() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return RecordsetNative.jni_DeleteAll(getHandle());
	}

	private void checkExceptionForGetFieldValueByIndex(int index) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		if (index < 0 || index >= m_fieldCount) {
			String message = InternalResource.loadString("" + index,
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
	}

	/**
	 * ���ڻ�����ݼ����������ݱ��е�ǰ��¼ĳһ�ֶε�ֵ�� �Ժ����÷������Ч��
	 *
	 * @param index
	 *            int
	 * @return Object
	 */
	public Object getFieldValue(int index) {
		checkExceptionForGetFieldValueByIndex(index);
		FieldType type = m_fieldInfos.get(index).getType();
		if (type.equals(FieldType.INT32)) {
			Integer result = getInt32(index);
			if (m_flag[0] == true) {
				return null;
			}
			return result;
		} else if (type.equals(FieldType.INT64)) {
			Long result = getInt64(index);
			if (m_flag[0] == true) {
				return null;
			}
			return result;
		} else if (type.equals(FieldType.INT16)) {
			Short result = getInt16(index);
			if (m_flag[0] == true) {
				return null;
			}
			return result;
		} else if (type.equals(FieldType.BOOLEAN)) {
			Boolean result = getBoolean(index);
			if (m_flag[0] == true) {
				return null;
			}
			return result;
		} else if (type.equals(FieldType.BYTE)) {
			Byte result = getByte(index);
			if (m_flag[0] == true) {
				return null;
			}
			return result;
		} else if (type.equals(FieldType.CHAR)) {
			String result = getString(index);
			if (m_flag[0] == true) {
				return null;
			}
			return result;
		} else if (type.equals(FieldType.DATETIME)) {
			Date result = getDateTime(index);
			if (m_flag[0] == true) {
				return null;
			}
			return result;
		} else if (type.equals(FieldType.DOUBLE)) {
			Double result = getDouble(index);
			if (m_flag[0] == true) {
				return null;
			}
			return result;
		} else if (type.equals(FieldType.LONGBINARY)) {
			byte[] result = getLongBinary(index);
			if (m_flag[0] == true) {
				return null;
			}
			return result;
		} else if (type.equals(FieldType.SINGLE)) {
			Float result = getSingle(index);
			if (m_flag[0] == true) {
				return null;
			}
			return result;
		} else if (type.equals(FieldType.TEXT)) {
			String result = getString(index);
			if (m_flag[0] == true) {
				return null;
			}
			return result;
		} else {
			return null;
		}

	}

	public int getInt32(int index) {
		checkExceptionForGetFieldValueByIndex(index);
		int result = RecordsetNative.jni_GetFieldValueIntByIndex(getHandle(),
				index, m_flag);
		return result;
	}

	public int getInt32(String name) {
		return getInt32(m_fieldInfos.indexOf(name));
	}

	public short getInt16(int index) {
		checkExceptionForGetFieldValueByIndex(index);
		short result = RecordsetNative.jni_GetFieldValueShortByIndex(
				getHandle(), index, m_flag);
		return result;
	}

	public short getInt16(String name) {
		return getInt16(m_fieldInfos.indexOf(name));
	}

	public long getInt64(int index) {
		checkExceptionForGetFieldValueByIndex(index);
		long result = RecordsetNative.jni_GetFieldValueLongByIndex(getHandle(),
				index, m_flag);
		return result;
	}

	public long getInt64(String name) {
		return getInt64(m_fieldInfos.indexOf(name));
	}

	public boolean getBoolean(int index) {
		checkExceptionForGetFieldValueByIndex(index);
		boolean result = RecordsetNative.jni_GetFieldValueBooleanByIndex(
				getHandle(), index, m_flag);
		return result;
	}

	public boolean getBoolean(String name) {
		return getBoolean(m_fieldInfos.indexOf(name));
	}

	public String getString(int index) {
		checkExceptionForGetFieldValueByIndex(index);
//		boolean[] m_flag = new boolean[] { false };
		String result = RecordsetNative.jni_GetFieldValueTextByIndex(
				getHandle(), index, m_flag);
		return result;

	}

	public String getString(String name) {
		return getString(m_fieldInfos.indexOf(name));
	}

	public Date getDateTime(int index) {
		checkExceptionForGetFieldValueByIndex(index);
//		boolean[] m_flag = new boolean[] { false };
		Date result = getFieldValueDateTimeByIndex(index, m_flag);
		return result;
	}

	public Date getDateTime(String name) {
		int index = m_fieldInfos.indexOf(name);
		return getDateTime(index);
	}

	Date getFieldValueDateTimeByIndex(int index, boolean[] m_flag) {
		Pattern p = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		String temp = RecordsetNative.jni_GetFieldValueDateByIndex(getHandle(),
				index, m_flag);
		if(temp == null) {
			return null;
		}
		Date date = null;
		//if (p.matcher(temp).matches()) {
		String os = System.getProperty("os.name").toLowerCase();
//		if(os.equals("aix")){
			//�����������"yyyy-MM-dd HH:mm:ss" ��Ҳ�������� "yyyy-MM-dd"
			SimpleDateFormat sDate =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try{
				date = sDate.parse(temp);
			} catch (ParseException e) {
					try{
						SimpleDateFormat sDate1 =  new SimpleDateFormat("yyyy-MM-dd");
						date = sDate1.parse(temp);
					}catch (ParseException e1) {
					}
			}
//		}
//		else{
//			try {
//				date = DateFormat.getDateTimeInstance().parse(temp);
//			} catch (ParseException e) {
//				try{
//					date = DateFormat.getDateInstance().parse(temp);
//				}catch (ParseException e1) {
//				}
//			}
//		}
	//}
		return date;
	}

	public double getDouble(int index) {
		checkExceptionForGetFieldValueByIndex(index);
//		boolean[] m_flag = new boolean[] { false };
		double result = RecordsetNative.jni_GetFieldValueDoubleByIndex(
				getHandle(), index, m_flag);
		return result;
	}

	public double getDouble(String name) {
		int index = m_fieldInfos.indexOf(name);
		return getDouble(index);
	}

	public float getSingle(int index) {
		checkExceptionForGetFieldValueByIndex(index);
//		boolean[] m_flag = new boolean[] { false };
		float result = RecordsetNative.jni_GetFieldValueFloatByIndex(
				getHandle(), index, m_flag);
		return result;
	}

	public float getSingle(String name) {
		int index = m_fieldInfos.indexOf(name);
		return getSingle(index);
	}

	public byte getByte(int index) {
		checkExceptionForGetFieldValueByIndex(index);
//		boolean[] m_flag = new boolean[] { false };
		byte result = RecordsetNative.jni_GetFieldValueByteByIndex(getHandle(),
				index, m_flag);
		return result;
	}

	public byte getByte(String name) {
		int index = m_fieldInfos.indexOf(name);
		return getByte(index);
	}

	public byte[] getLongBinary(int index) {
		checkExceptionForGetFieldValueByIndex(index);
//		boolean[] m_flag = new boolean[] { false };
		byte[] result = RecordsetNative.jni_GetFieldValueLongBinaryByIndex(
				getHandle(), index, m_flag);
		return result;
	}

	public byte[] getLongBinary(String name) {
		int index = m_fieldInfos.indexOf(name);
		return getLongBinary(index);
	}

	private void checkExceptionForGetFieldValueByName(String name) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		if (name == null || name.trim().length() == 0) {
			String message = InternalResource.loadString("name",
					InternalResource.GlobalStringIsNullOrEmpty,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
	}

	// ���ڻ�����ݼ����������ݱ��е�ǰ��¼ĳһ�ֶε�ֵ��
	public Object getFieldValue(String name) {
		checkExceptionForGetFieldValueByName(name);
		int index = m_fieldInfos.indexOf(name);
		if (index < 0) {
			String message = InternalResource.loadString(name,
					InternalResource.RecordsetFieldIsNotExsit,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return getFieldValue(index);

	}

	// ���ڻ�����ݼ����������ݱ��е�ǰ��¼��Ӧ�ļ��ζ���
	public Geometry getGeometry() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		long handle = RecordsetNative.jni_GetGeometry(getHandle());
		if (handle == 0) {
			return null;
		}
		// @modified by ���ƽ� at 2007��8��15�� ����02ʱ10��41��
		// @reason ע�����ﷵ�صĶ�����Ҫ�ͷ�
		Geometry geo = Geometry.createInstance2(handle, this.getDataset()
				.getDatasource().getWorkspace());

		if (geo != null) // ��֧�ֵĶ��󣬿�ָ������
			geo.setIsDisposable(true);
		return geo;
	}

	// ������ݼ������Ա��е�ǰ��¼��Ӧ�ļ��ζ���� ID �ţ��� SmID �ֶε�ֵ����
	public int getID() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_GetID(getHandle());
	}

	// �����ƶ�����ָ�룬�ɹ����� True��
	public boolean move(int count) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_Move(getHandle(), count);
	}

	// �����ƶ� Recordset ����ļ�¼ָ�뵽��һ����¼���ɹ��򷵻� True��
	public boolean moveFirst() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_MoveFirst(getHandle());
	}

	// �ƶ� Recordset ����ļ�¼ָ�뵽���һ����¼���ɹ��򷵻� True��
	public boolean moveLast() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_MoveLast(getHandle());
	}

	// �ƶ� Recordset ����ļ�¼ָ�뵽��һ����¼���ɹ��򷵻� True��
	public boolean moveNext() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_MoveNext(getHandle());
	}

	// �ƶ� Recordset ����ļ�¼ָ�뵽��һ����¼���ɹ��򷵻� True��
	public boolean movePrev() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_MovePrev(getHandle());
	}

	// ���ڽ�����ָ���ƶ���ָ����λ�ã��ɹ��򷵻� True��
	public boolean moveTo(int position) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_MoveTo(getHandle(), position);
	}

	// ˢ�µ�ǰ��¼����������ӳ���ݼ��еı仯������ɹ����� True�����򷵻� False��
	public boolean refresh() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_Refresh(getHandle());
	}

	// �ڼ�¼������ָ�� ID �ŵļ�¼�����Ѽ�¼ָ�붨λ�ڸü�¼��
	public boolean seekID(int id) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean bResult = false;
		// �����������һ�£���id<0ʱ��ֱ�ӷ���false
		if (id < 0) {
			bResult = false;
		} else {
			bResult = RecordsetNative.jni_SeekID(getHandle(), id);
		}
		return bResult;
	}

	/**
	 * �����Ƿ��ֹdataset�޸�ʱʹ�ûص��޸Ķ�Ӧ���������ݼ���true��ʾ��ֹ
	 * @param enableEditBulk
	 */
	private void enableEditBulk(boolean enableEditBulk) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		Dataset dataset = this.getDataset();
		Datasource datasource = dataset.getDatasource();
		if(datasource.getDatasets().contains(dataset.getName() + "_Table"))
		    RecordsetNative.jni_SetEditBulk(getHandle(), enableEditBulk);
	}
	
	private void checkExcepitonForSetFieldValueByIndex(int index) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (index < 0 || index >= m_fieldCount) {
			String message = InternalResource.loadString("" + index,
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
	}

	// �����趨��¼����ĳһ�ֶε�ֵ���ɹ��򷵻� True��
	public boolean setFieldValue(int index, Object value) {
		checkExcepitonForSetFieldValueByIndex(index);
		// �˴��жϵ�Ч���д��Ľ�
		if (m_fieldInfos.get(index).isSystemField()) {
			String message = InternalResource.loadString("index",
					InternalResource.RecordsetSystemFieldIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		boolean bResult = false;
		if (value == null) {
			bResult = this.setFieldValueNull(index);
		} else {
			bResult = setFieldValue(m_fieldInfos.get(index).getName(), value);
		}
		return bResult;
	}

	private void checkExceptionForSetFieldValueByName(String name) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (name == null || name.trim().length() == 0) {
			String message = InternalResource.loadString("name",
					InternalResource.GlobalStringIsNullOrEmpty,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
	}

	// �����趨��¼����ĳһ�ֶε�ֵ���ɹ��򷵻� True��
	public boolean setFieldValue(String name, Object value) {
		FieldInfo info = m_fieldInfos.get(name);
		if (info == null) {
			String message = InternalResource.loadString(name,
					InternalResource.RecordsetFieldIsNotExsit,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// �˴��жϵ�Ч���д��Ľ�
		if (info.isSystemField()) {
			String message = InternalResource.loadString("name",
					InternalResource.RecordsetSystemFieldIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		boolean bResult = false;
		FieldType type = info.getType();
		if (value == null) {
			bResult = this.setFieldValueNull(name);
		} else {
			String strValue = value.toString();//UGOVI-1574 modify by guohang

			if (type.equals(FieldType.BOOLEAN)) {
				bResult = setBoolean(name, (Boolean) value);
			} else if (type.equals(FieldType.BYTE)) {
				bResult = setByte(name, (Byte) value);
			} else if (type.equals(FieldType.CHAR)) {
				bResult = setFieldValueChar(name, (String) value);
			} else if (type.equals(FieldType.DATETIME)) {
				Date date = null;
				if (value instanceof String) {
					//����������ʽ
					Pattern p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
					String temp = (String)value;
					if (p.matcher(temp).matches()) {
						//aix����DateFormat �����㲻������ʱ��SimpleDateFormat����
						String os = System.getProperty("os.name").toLowerCase();
						if(os.equals("aix")){
							try{
								SimpleDateFormat sDate =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								date = sDate.parse(temp);
							}
							catch (ParseException e) {
								try{
									SimpleDateFormat sDate1 =  new SimpleDateFormat("yyyy-MM-dd");
									date = sDate1.parse(temp);
								}catch (ParseException e1) {
								}
							}
						}
						else{
							try {
								date = DateFormat.getDateInstance().parse(temp);
							} catch (ParseException e) {
							}
						}
					}
				} else {
					date = (Date) value;
				}
				bResult = setDateTime(name, date);
			} else if (type.equals(FieldType.DOUBLE)) {
				if (strValue.length() == 0)
				{
					bResult = setDouble(name, 0);//ӦiServerҪ�󣬶������ֶθ����ַ���ʱ���Ϊ0����ͬ��
				}
				else
				{
					bResult = setDouble(name, Double.valueOf(strValue));
				}
			} else if (type.equals(FieldType.INT16)) {
				//bResult = setSingle(name, Short.valueOf(value.toString()));
				if (strValue.length() == 0)
				{
					bResult = setDouble(name, 0);
				}
				else
				{
					bResult = setInt16(name, Short.valueOf(strValue));
				}
			} else if (type.equals(FieldType.INT32)) {
				if (strValue.length() == 0)
				{
					bResult = setDouble(name, 0);
				}
				else
				{
					bResult = setInt32(name, Integer.valueOf(strValue));
				}
			} else if (type.equals(FieldType.INT64)) {
				if (strValue.length() == 0)
				{
					bResult = setDouble(name, 0);
				}
				else
				{
					bResult = setInt64(name, Long.valueOf(strValue));
				}
			} else if (type.equals(FieldType.LONGBINARY)) {
				if (strValue.length() == 0)
				{
					bResult = setDouble(name, 0);
				}
				else
				{
					bResult = setLongBinary(name, (byte[]) value);
				}
			} else if (type.equals(FieldType.SINGLE)) {
				if (strValue.length() == 0)
				{
					bResult = setDouble(name, 0);
				}
				else
				{
					bResult = setSingle(name, Float.valueOf(strValue));
				}
			} else if (type.equals(FieldType.TEXT)) {
				if (strValue.length() == 0 && !info.isZeroLengthAllowed())
				{
					bResult = false;
				}
				else
				{
					//bResult = setString(name, (String) value);
                    bResult = setString(name, String.valueOf(strValue));
				}
			} else
				bResult = false;
		}
		return bResult;
	}

	public boolean setByte(int index, byte value) {
		checkExcepitonForSetFieldValueByIndex(index);
		String name = m_fieldInfos.get(index).getName();
		return setByte(name, value);
	}

	public boolean setByte(String name, byte value) {
		checkExceptionForSetFieldValueByName(name);
		return RecordsetNative.jni_setFieldValueByteByName(getHandle(), name,
				value);
	}

	public boolean setInt32(String name, int value) {
		checkExceptionForSetFieldValueByName(name);
		return RecordsetNative.jni_setFieldValueIntByName(getHandle(), name,
				value);
	}

	public boolean setInt32(int index, int value) {
		checkExcepitonForSetFieldValueByIndex(index);
		String name = m_fieldInfos.get(index).getName();
		return RecordsetNative.jni_setFieldValueIntByName(getHandle(), name,
				value);
	}

	public boolean setInt16(String name, short value) {
		checkExceptionForSetFieldValueByName(name);
		return RecordsetNative.jni_setFieldValueShortByName2(getHandle(), name,
				value);
	}

	public boolean setInt16(int index, short value) {
		checkExcepitonForSetFieldValueByIndex(index);
		String name = m_fieldInfos.get(index).getName();
		return RecordsetNative.jni_setFieldValueShortByName2(getHandle(), name,
				value);
	}

	public boolean setInt64(String name, long value) {
		checkExceptionForSetFieldValueByName(name);
		return RecordsetNative.jni_setFieldValueLongByName(getHandle(), name,
				value);
	}

	public boolean setInt64(int index, long value) {
		checkExcepitonForSetFieldValueByIndex(index);
		String name = m_fieldInfos.get(index).getName();
		return RecordsetNative.jni_setFieldValueLongByName(getHandle(), name,
				value);
	}

	public boolean setDouble(String name, double value) {
		checkExceptionForSetFieldValueByName(name);
		return RecordsetNative.jni_setFieldValueDoubleByName(getHandle(), name,
				value);
	}

	public boolean setDouble(int index, double value) {
		checkExcepitonForSetFieldValueByIndex(index);
		String name = m_fieldInfos.get(index).getName();
		return RecordsetNative.jni_setFieldValueDoubleByName(getHandle(), name,
				value);
	}

	public boolean setSingle(String name, float value) {
		checkExceptionForSetFieldValueByName(name);
		return RecordsetNative.jni_setFieldValueFloatByname(getHandle(), name,
				value);
	}

	public boolean setSingle(int index, float value) {
		checkExcepitonForSetFieldValueByIndex(index);
		String name = m_fieldInfos.get(index).getName();
		return RecordsetNative.jni_setFieldValueFloatByname(getHandle(), name,
				value);
	}

	public boolean setBoolean(String name, boolean value) {
		checkExceptionForGetFieldValueByName(name);
		return RecordsetNative.jni_setFieldValueBooleanByName(getHandle(),
				name, value);
	}

	public boolean setBoolean(int index, boolean value) {
		checkExcepitonForSetFieldValueByIndex(index);
		String name = m_fieldInfos.get(index).getName();
		return RecordsetNative.jni_setFieldValueBooleanByName(getHandle(),
				name, value);
	}

	private boolean setFieldValueChar(String name, String value) {
		return RecordsetNative.jni_setFieldValueCharByName(getHandle(), name,
				value);
	}

	public boolean setString(String name, String value) {
		checkExceptionForSetFieldValueByName(name);
		return RecordsetNative.jni_setFieldValueTextByName(getHandle(), name,
				value);
	}

	public boolean setString(int index, String value) {
		checkExcepitonForSetFieldValueByIndex(index);
		String name = m_fieldInfos.get(index).getName();
		return RecordsetNative.jni_setFieldValueTextByName(getHandle(), name,
				value);
	}

	public boolean setLongBinary(String name, byte[] value) {
		checkExceptionForSetFieldValueByName(name);
		return RecordsetNative.jni_setFieldValueLongBinaryByName(getHandle(),
				name, value);
	}

	public boolean setLongBinary(int index, byte[] value) {
		checkExcepitonForSetFieldValueByIndex(index);
		String name = m_fieldInfos.get(index).getName();
		return RecordsetNative.jni_setFieldValueLongBinaryByName(getHandle(),
				name, value);
	}

	public boolean setDateTime(String name, Date value) {
		checkExceptionForSetFieldValueByName(name);
		GregorianCalendar calendar = new GregorianCalendar(java.util.Locale.getDefault());
		calendar.setTime(value);
		int year = calendar.get(GregorianCalendar.YEAR);
		int month = calendar.get(GregorianCalendar.MONTH) + 1;
		int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		int hour = calendar.get(GregorianCalendar.HOUR_OF_DAY);
		int minute = calendar.get(GregorianCalendar.MINUTE);
		int second = calendar.get(GregorianCalendar.SECOND);
		return RecordsetNative.jni_setFieldValueDateTimeByName(getHandle(),
				name, year, month, day, hour, minute, second);
	}

	public boolean setDateTime(int index, Date value) {
		checkExcepitonForSetFieldValueByIndex(index);
		String name = m_fieldInfos.get(index).getName();
		return setDateTime(name, value);
	}

	// �����趨��¼����ĳһ�ֶε�ֵΪ Null���ɹ��򷵻� True��
	public boolean setFieldValueNull(int index) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (index < 0 || index >= m_fieldCount) {
			String message = InternalResource.loadString("" + index,
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		// �˴��жϵ�Ч���д��Ľ�
		if (m_fieldInfos.get(index).isSystemField()) {
			String message = InternalResource.loadString("index",
					InternalResource.RecordsetSystemFieldIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (m_fieldInfos.get(index).isRequired()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRequiredFieldShouldNotBeNull,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return RecordsetNative.jni_SetFieldValueNullByIndex(getHandle(), index);
	}

	// �����趨��¼����ĳһ�ֶε�ֵΪ Null���ɹ��򷵻� True��
	public boolean setFieldValueNull(String name) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (name == null || name.trim().length() == 0) {
			String message = InternalResource.loadString("name",
					InternalResource.GlobalStringIsNullOrEmpty,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		int index = RecordsetNative.jni_GetFieldIndex(this.getHandle(), name);
		if (index < 0) {
			String message = InternalResource.loadString(name,
					InternalResource.RecordsetFieldIsNotExsit,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// �˴��жϵ�Ч���д��Ľ�
		if (m_fieldInfos.get(index).isSystemField()) {
			String message = InternalResource.loadString("name",
					InternalResource.RecordsetSystemFieldIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (m_fieldInfos.get(index).isRequired()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRequiredFieldShouldNotBeNull,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return RecordsetNative.jni_SetFieldValueNullByName(this.getHandle(),
				name);
	}

	// �����޸ļ�¼����ǰλ�õļ��ζ��󣬸���ԭ���ļ��ζ��󣬳ɹ��򷵻�
	// True���޸ļ�¼�ļ��ζ���ʱ��ϵͳ�Զ��޸�������йص�ϵͳ�ֶε�ֵ�������ζ����������߶ζ���ĳ��ȵȡ�
	public boolean setGeometry(Geometry geometry) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (geometry == null) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		DatasetType datasetType = this.getDataset().getType();
		GeometryType geometryType = geometry.getType();
		if (!checkDatasetAndGeometryType(datasetType, geometryType)) {
			String message = InternalResource
					.loadString(
							"geometry",
							InternalResource.RecordsetDatasetTypeAndGeometryTypeIsDifferent,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geometry.isEmpty()) {
			String message = InternalResource.loadString("geometry",
					InternalResource.RecordsetGeometryIsEmpty,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return RecordsetNative.jni_SetGeometry(getHandle(), geometry
				.getHandle());
	}

	// ��ָ���ֶν����������ֵ����Сֵ��ƽ��ֵ���ܺͣ���׼��ͷ���ȷ�ʽ��ͳ�ơ�
	public double statistic(int fieldIndex, StatisticMode mode) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		if (fieldIndex < 0 || fieldIndex >= m_fieldCount) {
			String message = InternalResource.loadString("fieldIndex",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		if (mode == null) {
			String message = InternalResource.loadString("mode",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		// Added again by Helh 2010-1-18
		// ֮ǰ������˸�ȱ�ݣ����Ǻ���˭�ύ����֮ǰ����û�и��´��룬��֮ǰ��ӵ��쳣�жϸ�������
		FieldType fieldType = m_fieldInfos.get(fieldIndex).getType();
		if (!(fieldType == FieldType.BOOLEAN || fieldType == FieldType.BYTE
				|| fieldType == FieldType.DOUBLE
				|| fieldType == FieldType.SINGLE
				|| fieldType == FieldType.INT16 || fieldType == FieldType.INT32)) {
			String message = InternalResource.loadString("FieldType",
					InternalResource.RecordsetStatisticUnsupprotFieldType,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return RecordsetNative.jni_StatisticByIndex(this.getHandle(),
				fieldIndex, mode.getUGCValue());
	}

	// ��ָ���ֶν����������ֵ����Сֵ��ƽ��ֵ���ܺͣ���׼��ͷ���ȷ�ʽ��ͳ�ơ�
	public double statistic(String fieldName, StatisticMode mode) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		if (fieldName == null || fieldName.trim().length() == 0) {
			String message = InternalResource.loadString("name",
					InternalResource.GlobalStringIsNullOrEmpty,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (mode == null) {
			String message = InternalResource.loadString("mode",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);

		}

		int index = RecordsetNative.jni_GetFieldIndex(this.getHandle(),
				fieldName);
		if (index < 0) {
			String message = InternalResource.loadString(fieldName,
					InternalResource.RecordsetFieldIsNotExsit,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// Added again by Helh 2010-1-18
		// ֮ǰ������˸�ȱ�ݣ����Ǻ���˭�ύ����֮ǰ����û�и��´��룬��֮ǰ��ӵ��쳣�жϸ�������
		FieldType fieldType = m_fieldInfos.get(index).getType();
		if (!(fieldType == FieldType.BOOLEAN || fieldType == FieldType.BYTE
				|| fieldType == FieldType.DOUBLE
				|| fieldType == FieldType.SINGLE
				|| fieldType == FieldType.INT16 || fieldType == FieldType.INT32)) {
			String message = InternalResource.loadString("FieldType",
					InternalResource.RecordsetStatisticUnsupprotFieldType,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return RecordsetNative.jni_StatisticByName(getHandle(), fieldName, mode
				.getUGCValue());
	}

	public void dispose() {
		if (!super.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			this.close();
		}
	}

	public BatchEditor getBatch() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetBatchEdit()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (m_batchEdit == null)
			m_batchEdit = new BatchEditor(this.getHandle());

		return m_batchEdit;
	}

	/**
	 * �ڲ�ʹ�� �ڲ�ѯʧ��ʱ���ȷ��ؿյļ�¼����Ȼ���޸�Parameter ��WrapJִ�п���
	 *
	 * @param queryParameter
	 */
	void setQueryParameter(QueryParameter queryParameter) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}
		// ����Ķ����ѱ��ͷ�
		if (queryParameter.getHandle() == 0) {
			String message = InternalResource.loadString("queryParameter",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		// ע�⣺�����ú�,ԭ��queryParameter�����ͷ��ˣ�����Ӱ�����µĶ���
		// ���̵߳�ʱ��ǵ�������
		if (queryParameter.getSpatialQueryObject() != null) {
			this.m_spatialQueryObject = queryParameter.getSpatialQueryObject();
			this.m_queryMode = queryParameter.getSpatialQueryMode();
			// this.m_spatialFilter = queryParameter.getSpatialFilter();
		}
		RecordsetNative.jni_SetQueryParameter(getHandle(), queryParameter
				.getHandle());
	}

	// //�����ݽ��ж��β�ѯ
	// public Recordset query(String attributeFilter, String orderBy,String
	// groupBy) {
	// if (getHandle() == 0) {
	// String message = InternalResource.loadString("",
	// InternalResource.HandleObjectHasBeenDisposed,
	// InternalResource.BundleName);
	// throw new IllegalStateException(message);
	// }
	// if (this.m_datasetVector == null ||
	// this.m_datasetVector.getHandle() == 0) {
	// String message = InternalResource.loadString("m_datasetVector",
	// InternalResource.GlobalOwnerHasBeenDisposed,
	// InternalResource.BundleName);
	// throw new IllegalStateException(message);
	// }
	//
	// if (attributeFilter == null) {
	// attributeFilter = "";
	// }
	// if (orderBy == null) {
	// orderBy = "";
	// }
	// if (groupBy == null) {
	// groupBy = "";
	// }
	// QueryParameter param = new QueryParameter();
	// param.setSpatialQueryObject(this);
	// param.setAttributeFilter(attributeFilter);
	// param.setOrderBy(new String[] {orderBy});
	// param.setGroupBy(new String[] {groupBy});
	// param.setSpatialQueryMode(SpatialQueryMode.INTERSECT);
	// return this.m_datasetVector.query(param);
	// }

	protected void clearHandle() {
		setHandle(0);
		this.m_datasetVector = null;
//		this.m_datasetRel = null;
		this.m_queryMode = null;
		// this.m_spatialFilter = null;
		this.m_spatialQueryObject = null;
		// if (elementMap != null) {
		// elementMap.clear();
		// }
		//
		// elementMap = null;
	}

	protected static Recordset createInstance(long handle,
			DatasetVector datasetVector) {
		return new Recordset(handle, datasetVector);
	}

	static boolean checkDatasetAndGeometryType(DatasetType datasetType,
			GeometryType geometryType) {
		boolean result = false;
		if (datasetType != null && geometryType != null) {
			if (datasetType == DatasetType.CAD) {
				result = true;
			}
			if ((datasetType == DatasetType.POINT && geometryType == GeometryType.GEOPOINT)
					|| (datasetType == DatasetType.LINE && geometryType == GeometryType.GEOLINE)
					|| (datasetType == DatasetType.REGION && geometryType == GeometryType.GEOREGION)
					|| (datasetType == DatasetType.TEXT && geometryType == GeometryType.GEOTEXT)) {
				result = true;
			}
		}
		return result;
	}


	/**
	 * �ж�֮ǰ��ѯ���ֶ�ֵ�Ƿ�Ϊnull,�ֶ�ֵΪnullʱ����true ��ǰRecordset�α괦��BOF��EOFʱҲ����Ϊtrue
	 *
	 * @return
	 */
	public boolean wasNull() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}
		return m_flag[0];
	}

	/**
	 * ͨ��ӳ����������ֶ�ֵ������ֶ��������ڣ����Ը��ֶΣ����óɹ�����true��
	 * ��UDB��������ô˽ӿڽ��������޸�
	 *
	 * batchEditor.begin();
	 * recordset.setValues(values);
	 * recordset.setValues(values);
	 * ......
	 * batchEditor.update();
	 *
	 * @param map ���ڱ���"�ֶ���=>�ֶ�ֵ"�Ե�ӳ���
	 * @return ���óɹ�ʱtrue
	 */
	public boolean setValues(Map<String, Object> map) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (!m_begin) {
			int successCount = 0;
			Set<Map.Entry<String, Object>> entrySet = map.entrySet();
			Iterator<Entry<String, Object>> iterator = entrySet.iterator();

			while (iterator.hasNext()) {
				Entry<String, Object> next = iterator.next();
				String key = next.getKey();
				int index = m_fieldInfos.indexOf(key);
				if (index != -1) {
					Object value = next.getValue();
					setFieldValue(index, value);
					successCount++;
				}
			}
			return successCount>0 ? true : false;
		}
		else {

//			//��ʱֻ֧��UDB����
//			if (! this.m_datasetVector.getDatasource().getEngineType().equals(EngineType.UDB)) {
//				return false;
//			}

			Object[] keyArray = map.keySet().toArray();
			int length = keyArray.length;

			// ����������ֵ
			String[] keys = new String[length];
			long[] handles = new long[length];
			InternalVariant[] variants = new InternalVariant[length];
			for (int i = 0; i < length; i++) {
				keys[i] = (String) keyArray[i];
				if (keys[i].equalsIgnoreCase("smid")) {
					String message = InternalResource.loadString("map",
							InternalResource.MAPCanNotContainSMID,
							InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
				if (map.get(keys[i]) != null) {
					if (!InternalVariant.isSupportedInstance(map.get(keys[i]))) {
						String message = InternalResource.loadString("value",
								InternalResource.GlobalArgumentTypeInvalid,
								InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}
					variants[i] = new InternalVariant(map.get(keys[i]));
				} else {
					variants[i] = new InternalVariant();
				}
				// �Ȳ����ͷţ���֤����Wrapj���ú�������ǰ���ͷ�
				variants[i].setIsDisposable(false);
				handles[i] = variants[i].getHandle();
			}
			boolean bResult = false;
			bResult = RecordsetNative.jni_SetGeometry1(getHandle(), 0, false, keys, handles);

			// ����ǰ�������еı���
			for (int i = 0; i < variants.length; i++) {
				variants[i].setIsDisposable(true);
				variants[i].dispose();
				variants[i] = null;
			}
			return bResult;
		}
	}

	/**
	 * ͨ��ӳ����Geometry�����õ�ǰ��¼������ֶ��������ڣ����Ը��ֶΣ����óɹ�����true
	 * ��UDB��������ô˽ӿڽ��������޸�
	 * batchEditor.begin();
	 * recordset.setValues(values��geometry);
	 * recordset.setValues(values��geometry);
	 * ......
	 * batchEditor.update();
	 *
	 * @param map ���ڱ���"�ֶ���=>�ֶ�ֵ"�Ե�ӳ���
	 * @return ���óɹ�ʱtrue
	 */
	public boolean setValues(Map<String, Object> map, Geometry geometry) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (geometry == null) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		if (!m_begin) {

			setGeometry(geometry);

			int successCount = 0;
			Set<Map.Entry<String, Object>> entrySet = map.entrySet();
			Iterator<Entry<String, Object>> iterator = entrySet.iterator();

			while (iterator.hasNext()) {
				Entry<String, Object> next = iterator.next();
				String key = next.getKey();
				int index = m_fieldInfos.indexOf(key);
				if (index != -1) {
					Object value = next.getValue();
					setFieldValue(index, value);
					successCount++;
				}
			}
			return successCount>0 ? true : false;
		}
		else {

			//��ʱֻ֧��UDB����
			if (! this.m_datasetVector.getDatasource().getConnectionInfo().getEngineType().equals(EngineType.UDB)) {
				return false;
			}

			Geometry temp = geometry;
			long geometryHandle = 0;
			if(temp != null){
				geometryHandle = temp.getHandle();
			}

			Object[] keyArray = map.keySet().toArray();
			int length = keyArray.length;
			// ����������ֵ
			String[] keys = new String[length];
			long[] handles = new long[length];
			InternalVariant[] variants = new InternalVariant[length];
			for (int i = 0; i < length; i++) {
				keys[i] = (String) keyArray[i];
				if (keys[i].equalsIgnoreCase("smid")) {
					String message = InternalResource.loadString("map",
							InternalResource.MAPCanNotContainSMID,
							InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
				if (map.get(keys[i]) != null) {
					if (!InternalVariant.isSupportedInstance(map.get(keys[i]))) {
						String message = InternalResource.loadString("value",
								InternalResource.GlobalArgumentTypeInvalid,
								InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}
					variants[i] = new InternalVariant(map.get(keys[i]));
				} else {
					variants[i] = new InternalVariant();
				}
				// �Ȳ����ͷţ���֤����Wrapj���ú�������ǰ���ͷ�
				variants[i].setIsDisposable(false);
				handles[i] = variants[i].getHandle();
			}
			boolean bResult = false;
			bResult = RecordsetNative.jni_SetGeometry1(getHandle(), geometryHandle, true, keys, handles);

			// ����ǰ�������еı���
			for (int i = 0; i < variants.length; i++) {
				variants[i].setIsDisposable(true);
				variants[i].dispose();
				variants[i] = null;
			}
			makeSureNativeObjectLive(temp);
			return bResult;
		}
	}

	/**
	 * ͨ����������������һ�����ֶε�ֵ������������Ҫ���ֶ�˳�򱣳���ͬ������˳��
	 * ��UDB��������ô˽ӿڽ��������޸�
	 * batchEditor.begin();
	 * recordset.setValues(values);
	 * recordset.setValues(values);
	 * ......
	 * batchEditor.update();
	 * @param values
	 * @return
	 */
	public boolean setValues(Object[] values){
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if(values.length != m_fieldCount){
			String message = InternalResource.loadString("",
					InternalResource.RecordsetValuesLengthShouldEqualsFieldCount,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (!m_begin) {
			for (int i = 0; i < values.length; i++) {
				if(m_fieldInfos.get(i).isSystemField()){
					continue;
				}else{
					boolean temp = setFieldValue(i, values[i]);
					if(!temp){
						return false;
					}
				}
			}
			return true;
		}
		else{

			//��ʱֻ֧��UDB����
			if (! this.m_datasetVector.getDatasource().getConnectionInfo().getEngineType().equals(EngineType.UDB)) {
				return false;
			}

			String[] keys = new String[values.length];
			long[] handles = new long[values.length];
			InternalVariant[] variants = new InternalVariant[values.length];
			for (int i = 0; i < values.length; i++) {
				if (m_fieldInfos.get(i).isSystemField()) {
					continue;
				} else {
					keys[i] = m_fieldInfos.get(i).getName();
					if (keys[i].equalsIgnoreCase("smid")) {
						String message = InternalResource.loadString("map",
								InternalResource.MAPCanNotContainSMID,
								InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}

					variants[i] = new InternalVariant(values[i]);
					// �Ȳ����ͷţ���֤����Wrapj���ú�������ǰ���ͷ�
					variants[i].setIsDisposable(false);
					handles[i] = variants[i].getHandle();
				}

			}
			boolean bResult = RecordsetNative.jni_SetGeometry1(getHandle(), 0, false, keys, handles);

			// ����ǰ�������еı���
			for (int i = 0; i < variants.length; i++) {
				variants[i].setIsDisposable(true);
				variants[i].dispose();
				variants[i] = null;
			}
			return bResult;
		}
	}

	/**
	 * ͨ����������ͼ��϶���������һ�����ֶε�ֵ������������Ҫ���ֶ�˳�򱣳���ͬ������˳��
	 * ��UDB��������ô˽ӿڽ��������޸�
	 * batchEditor.begin();
	 * recordset.setValues(values��geometry);
	 * recordset.setValues(values��geometry);
	 * ......
	 * batchEditor.update();
	 * @param values
	 * @return
	 */
	public boolean setValues(Object[] values, Geometry geometry){
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}
		if (isReadOnly()) {
			String message = InternalResource.loadString("",
					InternalResource.RecordsetRecordsetIsReadOnly,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if(values.length != m_fieldCount){
			String message = InternalResource.loadString("",
					InternalResource.RecordsetValuesLengthShouldEqualsFieldCount,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (geometry == null) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		if (!m_begin) {

			setGeometry(geometry);

			for (int i = 0; i < values.length; i++) {
				if(m_fieldInfos.get(i).isSystemField()){
					continue;
				}else{
					boolean temp = setFieldValue(i, values[i]);
					if(!temp){
						return false;
					}
				}
			}
			return true;
		}
		else{
			//��ʱֻ֧��UDB����
			if (! this.m_datasetVector.getDatasource().getConnectionInfo().getEngineType().equals(EngineType.UDB)) {
				return false;
			}


			Geometry temp = geometry;
			long geometryHandle = geometryHandle = temp.getHandle();

			String[] keys = new String[values.length];
			long[] handles = new long[values.length];
			InternalVariant[] variants = new InternalVariant[values.length];
			for (int i = 0; i < values.length; i++) {
				if (m_fieldInfos.get(i).isSystemField()) {
					continue;
				} else {
					keys[i] = m_fieldInfos.get(i).getName();
					if (keys[i].equalsIgnoreCase("smid")) {
						String message = InternalResource.loadString("map",
								InternalResource.MAPCanNotContainSMID,
								InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}

					variants[i] = new InternalVariant(values[i]);

					// �Ȳ����ͷţ���֤����Wrapj���ú�������ǰ���ͷ�
					variants[i].setIsDisposable(false);
					handles[i] = variants[i].getHandle();
				}

			}
			boolean bResult = RecordsetNative.jni_SetGeometry1(getHandle(), geometryHandle, true, keys, handles);

			// ����ǰ�������еı���
			for (int i = 0; i < variants.length; i++) {
				variants[i].setIsDisposable(true);
				variants[i].dispose();
				variants[i] = null;
			}
			makeSureNativeObjectLive(temp);
			return bResult;
		}
	}

	public Object[] getValues(){
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}
		Object[] result = new Object[m_fieldCount];
		for (int i = 0; i < result.length; i++) {
			result[i] = getFieldValue(i);
		}
		return result;
	}

	public boolean setObject(int index, Object value){
		return setFieldValue(index, value);
	}

	public boolean setObject(String name, Object value){
		return setFieldValue(name, value);
	}

	public Object getObject(int index){
		return getFieldValue(index);
	}


	public Object getObject(String name){
		return getFieldValue(name);
	}

	/**
	 * �÷�����Ҫ���ڱ�֤�ڱ��ط���ִ����֮�󣬲ſ��ܵ���GC�ͷű��ؿ��ͷŶ�����Ҫ���Geometry����
	 * @param obj ���ͷŵı��ض�����Geometry����
	 */
	private void makeSureNativeObjectLive(InternalHandleDisposable obj){
		if (obj != null) {
		obj.getHandle();
		}
	}

	public class BatchEditor {

		// // �����������²���������¼����
		// //�������µĲ�����¼�������������¼��ʱ����ִ�������ύ��
		private int m_maxRecordCount = 1024;

		long handle = 0;

		private BatchEditor(long handle) {
			this.handle = handle;
		}

		// �����������²�����ʼ���У����ø÷�����
		// ֮����еĸ��²��������������µķ�ʽ���У�
		// ���²�������������Ч����Ҫ�ȵ�����Update������ͳһ���¡�
		public void begin() {
			if (handle == 0) {
				String message = InternalResource.loadString("Begin()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			// �������ʧ�ܣ���ѷ���ԭʼֵ
			boolean result = RecordsetNative.jni_setMaxRecordCount(handle,
					m_maxRecordCount);
			boolean isBegin = RecordsetNative.jni_begin(handle, true);
			m_begin = true;
		}

		// �����������²�����ͳһ�ύ�����ø÷���ǰ��Ҫ����Begin��������ʾ�������²����Ŀ�ʼ��
		// Begin������Update����֮��Ĳ����������������µ�ģʽ�����ø�Update�����󣬽��еĸ��²���ͳһ��Ч��
		// ͬʱ����״̬����Ϊ�������£������Ҫ֮��Ĳ����������У������ٴε���Begin�ķ�����
		public void update() {
			if (handle == 0) {
				String message = InternalResource.loadString("Update()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			boolean isUpdate = RecordsetNative.jni_Update1(handle);
			if (isUpdate) {
				m_begin = false;
				RecordsetNative.jni_begin(handle, false);
			}
		}

		// ��������ȡ�����������ø÷�����֮ǰ���еĸ��²�������ȡ����ͬʱ����״̬����Ϊ�������£�
		// �����Ҫ֮��Ĳ����������У������ٴε���Begin�ķ���
		//udb��֧���������
		private void cancel() {
			if (handle == 0) {
				String message = InternalResource.loadString("Cancel()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			boolean isCancel = RecordsetNative.jni_CancelUpdate1(handle);
			if (isCancel) {
				m_begin = false;
				RecordsetNative.jni_begin(handle, false);
			}
		}

		// �����������²���������¼����
		// �������µĲ�����¼�������������¼��ʱ����ִ�������ύ��
		public void setMaxRecordCount(int value) {
			if (handle == 0) {
				String message = InternalResource.loadString(
						"SetMaxRecordCount(int value)",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			if (value < 0) {
				String message = InternalResource.loadString("value",
						InternalResource.GlobalArgumentShouldNotBeNegative,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			if (m_begin) {
				String message = InternalResource.loadString(
						"SetMaxRecordCount(int value)",
						InternalResource.SetMaxRecordCountMustBeforeBegin,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			m_maxRecordCount = value;
		}

		public int getMaxRecordCount() {
			return m_maxRecordCount;
		}
	}


	public synchronized void addSteppedListener(SteppedListener l) {
		if (m_steppedListeners == null) {
			m_steppedListeners = new Vector();
		}

		if (!m_steppedListeners.contains(l)) {
			m_steppedListeners.add(l);
		}
	}

	public synchronized void removeSteppedListener(SteppedListener l) {
		if (m_steppedListeners != null && m_steppedListeners.contains(l)) {
			m_steppedListeners.remove(l);
		}
	}

	protected void fireStepped(SteppedEvent event) {
		if (m_steppedListeners != null) {
			Vector listeners = m_steppedListeners;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((SteppedListener) listeners.elementAt(i)).stepped(event);
			}
		}
	}

	static void steppedCallBack(Recordset source, int percent,
			long remainTime, String title, String message, long cancelHandle) {
		if (source != null) {
			boolean bCancel = Toolkit.getHandleBooleanValue(cancelHandle);
			SteppedEvent event = new SteppedEvent(source, percent, remainTime,
					title, message, null, bCancel);
			source.fireStepped(event);

			Toolkit.setHandleBooleanValue(cancelHandle, event.getCancel());
		}
	}

	protected void clearSelfEventHandle() {
		if (m_selfEventHandle != 0) {
			RecordsetNative.jni_DeleteSelfEventHandle(m_selfEventHandle);
			m_selfEventHandle = 0;
		}
	}

	/**
	 * ��GeoJSON�е��ַ����л�ȡ���ζ��󣬼�����������ֵ����������µ����ݼ�
	 * <p>֧�ֵļ��ζ������Ͱ������㡢�ߡ��桢����
	 * @param geoJSON   GeoJSON��ʽ���ַ��������п��԰���һ�����ζ���Ҳ�����ǰ����������ļ���
	 * @return          ��ȡ�����м��ζ��󣬲����³ɹ�������true�����򣬷���false
	 */
	public boolean fromGeoJSON(String geoJSON){
		boolean result = false;
		if (geoJSON == null)
			return false;
		try {
			JSONObject featureCollectionJson = new JSONObject(geoJSON);
			Object type = featureCollectionJson.get("type");
			// ����GeoJSON���е�type���ͣ��ֱ����ת��
			if (type.equals("FeatureCollection")) {
				result = fromFeatureCollectionGeoJSON(geoJSON);
			} else if (type.equals("Feature")){
				result = fromFeatureGeoJSON(geoJSON);
			} else if (type.equals("GeometryCollection")){
				result = fromGeometryCollectionGeoJSON(geoJSON);
			} else if (type.equals("Point") || type.equals("LineString") || type.equals("Polygon")) {
				result = fromGeometryGeoJSON(geoJSON);
			} else{
				Log.e("Recordset", "Unsupported GeoJSON Object type: " + type);
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result =  false;
		}

		return result;
	}

	/**
	 * �ӵ�ǰ��¼�𣬽����ݼ��е�ָ����Ŀ�ļ�¼ת����GeoJSON��ʽ���ַ���
	 * <p>֧�ֵļ��ζ������Ͱ������㡢�ߡ��桢���ߣ��÷������֧�ֻ�ȡ��������10����¼��GeoJSON��.hasAttributteΪtrueʱ������Feature���͵��ַ�����hasAttributteΪfalseʱ������ֻ�����ζ����Geometry���͵��ַ���
	 * @param hasAttributte   �Ƿ��������ֵ
	 * @param count           ��Ҫ��ȡ�ļ�¼����, ��count>10,��10����
	 * @return                ���ػ�ȡ����GeoJSON��ʽ�ַ�������û�н��������null
	 */
	public String toGeoJSON(boolean hasAttributte, int count){
		String geoJSON = null;
		// �����Ƿ���Ҫ����ֵ���ֱ����ת��
		if (hasAttributte) {
			if (count == 1) {
				geoJSON = toFeatureGeoJSON();
			}
			if (count > 1) {  // ɾ�� ��&& count<=10��;����һ���������Ѿ���count>10����Ϊcount=10��, modified by xingjun, 2015.10.28
				geoJSON = toFeatureCollectionGeoJSON(count, -1);
			}

		} else {
			if (count == 1) {
				geoJSON = toGeometryGeoJSON();
			}
			if (count > 1) {  // ɾ�� ��&& count<=10��;����һ���������Ѿ���count>10����Ϊcount=10��, modified by xingjun, 2015.10.28
				geoJSON = toGeometryCollectionGeoJSON(count, -1);
			}
		}
		return geoJSON;
	}
	
	/**
	 * �ӵ�ǰ��¼�𣬽����ݼ��е�ָ����Ŀ�ļ�¼ת����GeoJSON��ʽ���ַ���
	 * <p>֧�ֵļ��ζ������Ͱ������㡢�ߡ��桢���ߣ��÷������֧�ֻ�ȡ��������10����¼��GeoJSON��.hasAttributteΪtrueʱ������Feature���͵��ַ�����hasAttributteΪfalseʱ������ֻ�����ζ����Geometry���͵��ַ���
	 * @param hasAttributte   �Ƿ��������ֵ
	 * @param count           ��Ҫ��ȡ�ļ�¼����, ��count>10,��10����
	 * @param endID           ����SmID
	 * @return                ���ػ�ȡ����GeoJSON��ʽ�ַ�������û�н��������null
	 */
	 String toGeoJSON(boolean hasAttributte, int count, int endID){
		String geoJSON = null;
		// Recordset�е���ؽӿڶ��Ǵӵ�ǰ��¼��ʼת����,����������count�Ǵ����,���ô����˵�Ҳ��count,��ָ������ʼλ��, modefied by Xingjun, 2016.09.07
//		int count = endID - startID;
		// �����Ƿ���Ҫ����ֵ���ֱ����ת��
		if (hasAttributte) {
			if (count == 1) {
				geoJSON = toFeatureGeoJSON();
			}
			if (count > 1) {  // ɾ�� ��&& count<=10��;����һ���������Ѿ���count>10����Ϊcount=10��, modified by xingjun, 2015.10.28
				geoJSON = toFeatureCollectionGeoJSON(count, endID);
			}

		} else {
			if (count == 1) {
				geoJSON = toGeometryGeoJSON();
			}
			if (count > 1) {  // ɾ�� ��&& count<=10��;����һ���������Ѿ���count>10����Ϊcount=10��, modified by xingjun, 2015.10.28
				geoJSON = toGeometryCollectionGeoJSON(count, endID);
			}
		}
		return geoJSON;
	}
	
	/********************************** private line ****************************************/
	
	/**
	 * �Ӽ��ζ����GeoJSON��ʽ�ַ�������ȡgeometry����������ӵ����ݼ���
	 * @param geoJSON  geometry��GeoJSON��ʽ�ַ���
	 * @return         �ɹ���ӵ����ݼ�������true;���򷵻�false
	 */
	private boolean fromGeometryGeoJSON(final String geoJSON) {
		boolean result = false;
		if (geoJSON == null)
			return false;
		try {
			JSONObject geo_Json = new JSONObject(geoJSON);
			Geometry geo = null;
			geo = getGeometryFromJSON(geo_Json);
			result = this.addNew(geo);
			geo.dispose();
			geo = null;

		} catch (Exception e) {
			Log.e("Recordset", "The geometry is " + e.toString());
			return false;
		}
		result = this.update();
		
		return result;
	}
	
	/**
	 * ����ǰ��¼�е�geometryת����GeoJSON��ʽ���ַ���
	 * @return  ����ת�����GeoJSON��ʽ�ַ���
	 */
	private String toGeometryGeoJSON(){
		String geoJSON = null;
		Geometry geometry = null;

		geometry = this.getGeometry();
		if (geometry != null) {
			geoJSON = geometry.toGeoJSON();
			if(geoJSON.isEmpty()){
				Log.e("Recordset", "SmID=" + this.getFieldValue("SmID").toString() + ", Unsupported geometry type: " + geometry.getType());
			}
			geometry.dispose(); // ת�����geometry
			geometry = null;
		}
		
		return geoJSON;
	}
	
	/**
	 * �ӵ�ǰ��¼��ʼ����ָ�������ļ�¼�е�geometryת����GeoJSON��ʽ���ַ���, ��typeΪGeometryCollection
	 * @param count  ��Ҫת���ļ�¼������count������10
	 * @param endID  ����SmID,�������0ʱ��Ч
	 * @return       ����ת�����GeoJSON��ʽ�ַ���
	 */
	private String toGeometryCollectionGeoJSON(final int count, int endID) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("{\"type\":\"GeometryCollection\",");

		strBuilder.append("\"geometries\":[");
		Geometry geometry = null;
		int nCounter = 0;
		int mCount = count>10 ? 10 : count;          // ����ת����¼����Ŀ��������10����¼
		if(mCount<0)
			mCount = 0;
		while (!this.isEOF() && nCounter < mCount) {
			geometry = this.getGeometry();
			if (geometry != null) {
				String geoJSON = geometry.toGeoJSON();
				if (!geoJSON.isEmpty()) {
					strBuilder.append(geoJSON);
					strBuilder.append(",");            // ÿһ��geometry GeoJSON�����һ������
				} else{
					Log.e("Recordset", "SmID=" + this.getFieldValue("SmID").toString() + ", Unsupported geometry type: " + geometry.getType());
				}
				geometry.dispose();                    // ת�����geometry
				geometry = null;
			}
		    // ��������ǵ�ǰ��ת���ļ�¼����endID������ʱ���ᵼ�¶�һ����¼
//			if(endID > 0 && (this.getID()) >= endID){
//				break;
//			}
			
			this.moveNext();
			nCounter++;
			
			// Ӧ�����һ����¼��SmID�Ƿ��ת��������SmID��,�������ܱ�֤ת���ļ�¼����ȷ,�ر���endID�ļ�¼������ʱ;
			// Modefied by Xingjun, 2016.09.08
			if (endID > 0 && (this.getID()) > endID) {
				break;
			}
		}

		if (strBuilder.lastIndexOf(",") == (strBuilder.length() - 1))
			strBuilder.deleteCharAt(strBuilder.length() - 1);        // ȥ�����һ������
		strBuilder.append("]}");
		return strBuilder.toString();
	}

	/**
	 * ��typeΪGeometryCollection��GeoJSON���ַ����У���ȡgeometry����������ӵ����ݼ���
	 * @param geoJSON   typeΪGeometryCollection��GeoJSON���ַ���
	 * @return          ��ȫ����ӳɹ�������true�����򣬷���false
	 */
	private boolean fromGeometryCollectionGeoJSON(final String geoJSON) {
		boolean result = false;
		if (geoJSON == null)
			return false;

		this.getBatch().begin();
		try {
			JSONObject geoCollectionJson = new JSONObject(geoJSON);
			Object type = geoCollectionJson.get("type");
			if (!type.equals("GeometryCollection")) {
				Log.e("Recordset", "Not match the type of GeometryCollection");
				return false;
			}
			// ��ȡÿһ��geometry
			JSONArray featureArray = geoCollectionJson.getJSONArray("geometries");
			Geometry geo = null;
			boolean resultTemp = false;
			for (int i = 0; i < featureArray.length(); i++) {
				try{
			    	geo = getGeometryFromJSON((JSONObject) featureArray.get(i));
				} catch(Exception e){
					Log.e("Recordset", "The " + i + "th geometry, " + e.toString());
					result = false;
				}
				
				if (geo != null) {
					resultTemp = this.addNew(geo);
					geo.dispose();
					geo = null;
				}
				if(i==0){
					result = resultTemp;
				}else{
					result = result && resultTemp;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getBatch().update();
			return false;
		}
		this.getBatch().update();
		return result;
	}

	/**
	 * ����ǰ��¼ת����typeΪFeature��GeoJSON�ַ��������а����˸ü�¼��geometry���ֶ���Ϣ
	 * @return   ����typeΪFeature��GeoJSON��ʽ�ַ���
	 */
	private String toFeatureGeoJSON() {
		StringBuilder strBuilder = new StringBuilder();
		Geometry geo = this.getGeometry();
		if(geo == null)
			return null;
		
		String strGeo = geo.toGeoJSON();
		geo.dispose();
		if (strGeo != null) {
			strBuilder.append("{\"type\":\"Feature\",");
			strBuilder.append("\"geometry\":");
			strBuilder.append(strGeo);
			strBuilder.append(",");

			strBuilder.append("\"properties\":{");
			for (int j = 0, fieldCount = this.getFieldCount(); j < fieldCount; j++) {
				String name = m_fieldInfos.get(j).getName();
				if(j==0)
					name = "SmID";

				strBuilder.append("\"" + name + "\":");

				// �ı��ͺ������͵�ֵ�ֱ���
				if (m_fieldInfos.get(j).getType() == FieldType.TEXT) {
					strBuilder.append("\"" + this.getFieldValue(name) + "\"");
				} else {
					strBuilder.append(this.getFieldValue(name));
				}
				strBuilder.append(",");
			}
			// ɾ��ĩβ�Ķ���
			if (strBuilder.lastIndexOf(",") == strBuilder.length() - 1) {
				strBuilder.deleteCharAt(strBuilder.length() - 1);
			}

			strBuilder.append("}}"); // properties ������Feature ����

			strGeo = null;
		} else {
			Log.e("Recordset", "SmID=" + this.getFieldValue("SmID").toString() + ", can not get geometry.");
		}
		
		return strBuilder.toString();
	}

	/**
	 * �ӵ�ǰ��¼��ʼ����ָ�������ļ�¼�е�geometry�����ֶ���Ϣת����GeoJSON��ʽ���ַ���, ��typeΪFeatureCollection
	 * @param count  ��Ҫת���ļ�¼������count������10
	 * @param endID  ����SmID,�������0ʱ��Ч
	 * @return       ����ת�����GeoJSON��ʽ�ַ���
	 */
	public String toFeatureCollectionGeoJSON(int count , int endID) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("{\"type\":\"FeatureCollection\",");
		strBuilder.append("\"features\":[");

		int nCounter = 0;
		int mCount = count>10 ? 10 : count;        // ����ת����¼����Ŀ��������10����¼
		if(mCount < 0)
			mCount = 0;
		String strFeature = null;

		while (!this.isEOF() && nCounter < mCount) {
			// ÿ����¼����һ��feature
			strFeature = this.toFeatureGeoJSON();
			if (strFeature != null) {
				strBuilder.append(strFeature);
				strBuilder.append(","); // Feature ����
			}
			strFeature = null;
			
		    // ��������ǵ�ǰ��ת���ļ�¼����endID������ʱ���ᵼ�¶�һ����¼
//			if(endID > 0 && (this.getID()) >= endID){
//				break;
//			}
			
			this.moveNext();
			nCounter++;
			
			// Ӧ�����һ����¼��SmID�Ƿ��ת��������SmID��,�������ܱ�֤ת���ļ�¼����ȷ,�ر���endID�ļ�¼������ʱ;
			// Modefied by Xingjun, 2016.09.08
			if (endID > 0 && (this.getID()) > endID) {
				break;
			}
		}
		// ɾ��ĩβ�Ķ���
		if (strBuilder.lastIndexOf(",") == strBuilder.length() - 1)
			strBuilder.deleteCharAt(strBuilder.length() - 1);

		strBuilder.append("]}");        // features ������FeatureCollection ����

		if(strBuilder.toString().isEmpty()){
			return null;
		}
		return strBuilder.toString();
	}

	/**
	 * ��typeΪFeature��GeoJSON���ַ����У���ȡgeometry�����ֶ���Ϣ����������µ����ݼ��У���
	 * @param geoJSON   typeΪFeature��GeoJSON���ַ���
	 * @return          ��ȡ�ɹ�������true�����򣬷���false
	 */
	private boolean fromFeatureGeoJSON(final String geoJSON) {
		boolean result = false;
		if (geoJSON == null)
			return false;

		int smID = -1;
		try {
			JSONObject featureJson = new JSONObject(geoJSON);
			Object type = featureJson.get("type");
			if (!type.equals("Feature")) {
				Log.e("Recordset", "Not match the type of Feature");
				return false;
			}
			JSONObject geoJson = (JSONObject) featureJson.get("geometry");
			JSONObject propertiesJson = (JSONObject) featureJson.get("properties");
			try {
				smID = ((Integer) propertiesJson.get("SmID")).intValue();
			} catch (Exception e) {
				e.printStackTrace();
				smID = -1;
			}
			
			Geometry geo =null;
			try {
				geo = getGeometryFromJSON((JSONObject) geoJson);
			} catch (Exception e) {
				Log.e("Recordset", "SMID=" + smID + ", the geometry is " + e.toString());
				result = false;
			}
			if (smID > 0) {
				result = this.seekID(smID);
				if(!result){
					result = this.addNew(geo);        // û��SmID��Ӧ�ļ�¼�������Ӽ�¼
				}else{
					this.edit();
					result = this.setGeometry(geo);   // ��¼���ƶ���ָ����IDʱ���޸ļ�¼
				}
				
			} else {
				result = this.addNew(geo);            // û��SmID�����Ӽ�¼
			}
			// ��ӻ�����geometry�ɹ����������ֶ���Ϣ
			if (result) {
				JSONArray nameArray = propertiesJson.names();
				String fieldName = null;
				for (int i = 0, length = nameArray.length(); i < length; i++) {
					fieldName = nameArray.get(i).toString();
					if (m_fieldInfos.indexOf(fieldName) > -1 && !m_fieldInfos.get(fieldName).isSystemField()) {
						this.setFieldValue(fieldName, propertiesJson.get(fieldName));
					}
				}

				result = this.update();
				geo.dispose();
				geo = null;
			} else {
				Log.e("Recordset", "��ӻ�����geometryʧ�ܣ�����SmID��geometry��ֵ��input GeoJSON:" + geoJSON);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return result;
	}

	/**
	 * ��typeΪFeatureCollection��GeoJSON���ַ����У���ȡgeometry�����ֶ���Ϣ����������µ����ݼ��У�
	 * @param geoJSON   typeΪFeatureCollection��GeoJSON���ַ���
	 * @return          ��ȫ�����³ɹ�������true�����򣬷���false
	 */
	private boolean fromFeatureCollectionGeoJSON(final String geoJSON) {
		boolean result = false;
		if (geoJSON == null)
			return false;
		try {
			JSONObject featureCollectionJson = new JSONObject(geoJSON);
			Object type = featureCollectionJson.get("type");
			if (!type.equals("FeatureCollection")) {
				Log.e("Recordset", "Not match the type of FeatureCollection");
				return false;
			}
			JSONArray featureArray = featureCollectionJson.getJSONArray("features");
			boolean resultTemp = false;
			for (int i = 0; i < featureArray.length(); i++) {
				resultTemp = fromFeatureGeoJSON(featureArray.get(i).toString());
				if (i == 0) {
					result = resultTemp;
				} else {
					result = result && resultTemp;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return result;
	}
	
	/**
	 * ��ȡ�����JSONOBject��������geometry
	 * @param json  ��geometry��GeoJSON��ʽ���ַ��������JSONObject
	 * @return      ������ȡ��Geometry
	 * @throws Exception
	 */
	private Geometry getGeometryFromJSON(JSONObject json) throws Exception {
		Geometry geo = null;
		if (json != null) {
			Object type = json.get("type");
			if (type.equals("Point")) {
				geo = new GeoPoint();
			}
			if (type.equals("LineString") || type.equals("MultiLineString")) {
				geo = new GeoLine();
			}
			if (type.equals("Polygon")) {
				geo = new GeoRegion();
			}
			if (geo != null) {
				geo.fromGeoJSON(json.toString());
			} else {
				throw new Exception("Unsupported GeoJSON Object type: " + type);
			}
		}
		return geo;
	}


}

