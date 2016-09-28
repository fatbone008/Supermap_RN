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
 * @author 李云锦
 * @version 2.0
 */
public class Recordset extends InternalHandleDisposable {
	private DatasetVector m_datasetVector = null;

//	private DatasetRelationship m_datasetRel = null;

	// 空间查询时需记录以下字段，用于用户再次获取QueryParameter
	private Object m_spatialQueryObject = null;

	// private SpatialComparePattern[][] m_spatialFilter = null;
	private SpatialQueryMode m_queryMode = SpatialQueryMode.NONE;

	// 字段与制图表达
	// 这里面存储的全是副本
	// private Map<String, RepresentationElement> elementMap = null;

	private BatchEditor m_batchEdit = null;

	private boolean m_begin = false;

	private int m_fieldCount;

	private boolean m_isQueryCursorTypeStatic;

	private FieldInfos m_fieldInfos;

	// 用于标识当前查询字段是否为null,如果当前记录集处于BOF和EOF时，字段值也为null
	private boolean[] m_flag = new boolean[] { false };

	// private boolean m_batchEdit;

	transient Vector m_steppedListeners;

	protected long m_selfEventHandle;


	// @added by 孔令亮 at 2007年8月14日 上午09时17分47秒
	// @reason:添加了protected构造函数供继承，添加了createInstance方法
	protected Recordset() {

	}

	/**
	 * 内部使用 默认的该对象是可以被释放的
	 *
	 * @param handle
	 *            long Recordset指针
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
	 * 内部使用 默认的该对象是可以被释放的
	 *
	 * @param handle
	 *            long Recordset指针
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

	// 得到记录集的属性数据表中所有记录对应的几何对象的外接矩形。
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

	// 获取记录集对应的数据集
	public DatasetVector getDataset() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return this.m_datasetVector;
	}


	// 获取查询定义
	public QueryParameter getQueryParameter() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		long queryHandle = RecordsetNative.jni_GetQueryParameter(getHandle());
		// 修改为返回对象的拷贝,拷贝对象可以释放
		QueryParameter queryParameter = new QueryParameter(queryHandle,
				this.m_spatialQueryObject, this.m_queryMode);
		queryParameter.setIsDisposable(true);
		return queryParameter;
	}

	/**
	 * 得到记录集里的字段集合对象 每次返回对象的拷贝 因此谨慎调用该方法
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
		// 始终返回的是底层对象的拷贝，因此是可以被释放的
		// fieldInfos.setIsDisposable(true);
		return new FieldInfos(m_fieldInfos);
	}

	// 返回记录集中字段（Field）的数目。
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

	// 判断记录集是否已经关闭。
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

		// 如果已经释放，将自身的Handle置为0
		if (isClosed) {
			this.setHandle(0);
		}
		return isClosed;
	}

	// 判断\u00A0Recordset\u00A0对象集合的记录指针是否位于起始位置，位于起始位置则返回\u00A0True\u00A0，否则返回False。
	public boolean isBOF() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_GetIsBOF(getHandle());
	}

	// 判断\u00A0Recordset\u00A0对象集合的记录指针是否位于最后位置。位于最后位置则返回\u00A0True\u00A0，否则返回False
	public boolean isEOF() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_GetIsEOF(getHandle());
	}

	// 判断记录集中是否有记录，True\u00A0表示该记录集中无数据，此时，RecordCount\u00A0的值应为-1。
	public boolean isEmpty() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_GetIsEmpty(getHandle());
	}

	// 返回记录集是否只读，True 表示允许。
	public boolean isReadOnly() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// @modified by 孔令亮 at 2007年12月17日 下午01时16分02秒
		// @reason:不在从Wrapj中调用。二是由DatasetVectro来决定是否只读。
		// return RecordsetNative.jni_GetIsReadOnly(getHandle());

		// 当Recordset是通过CursorType.STATIC查询出来的，作为readOnly处理。
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

	// 返回记录集中记录的总数。
	public int getRecordCount() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return RecordsetNative.jni_GetRecordCount(getHandle());
	}

	// 若添加成功，返回值为新加入的记录对应的几何对象的ID号（从0开始），失败则返回为-1。
	public boolean addNew(Geometry geometry) {
		//如果是关系类数据集直接返回
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
				// @modified by 孔令亮 at 2007年11月21日 下午07时58分30秒
				// @reason:addNew,当DatasetType为TABULAR时才有可能返回True
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
				// @modified by 孔令亮 at 2007年11月21日 下午07时58分30秒
				// @reason:addNew,当DatasetType"不"为TABULAR时才有可能返回True
				if (m_datasetVector.getType() != DatasetType.TABULAR) {
					bResult = RecordsetNative.jni_AddNew(getHandle(),
							geometryHandle);
				}
			}
		} else {
			//Modified by Pan zhibin 2010-10-12
			if (temp == null) {
				// @modified by 孔令亮 at 2007年11月21日 下午07时58分30秒
				// @reason:addNew,当DatasetType为TABULAR时才有可能返回True
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
	 * 添加几何对象及设置相关的字段值
	 *
	 * @param geometry
	 *            需要添加的几何对象
	 * @param map
	 *            需要设置的属性值，键为字段名称，值为对应字段的值。
	 * @return
	 */
	public boolean addNew(Geometry geometry, java.util.Map<String, Object> map) {
		//如果是关系类数据集直接返回
//		if(m_datasetRel != null)
//		{
//			return false;
//		}
		// 如果map为null就直接调用上面的addNew
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

		// 两个数组存键值
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
			// 先不可释放，保证传入Wrapj，该函数结束前再释放
			variants[i].setIsDisposable(false);
			handles[i] = variants[i].getHandle();
		}

		boolean bResult = false;
		if (!m_begin) {
			if (temp == null) {
				// @modified by 孔令亮 at 2007年11月21日 下午07时58分30秒
				// @reason:addNew,当DatasetType为TABULAR时才有可能返回True
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
				// @modified by 孔令亮 at 2007年11月21日 下午07时58分30秒
				// @reason:addNew,当DatasetType"不"为TABULAR时才有可能返回True
				if (m_datasetVector.getType() != DatasetType.TABULAR) {
					bResult = RecordsetNative.jni_AddNew2(getHandle(),
							geometryHandle, keys, handles);
				}
			}
		} else {
			//Modified by Pan zhibin 2010-10-12
			if (temp == null) {
				// @modified by 孔令亮 at 2007年11月21日 下午07时58分30秒
				// @reason:addNew,当DatasetType为TABULAR时才有可能返回True
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

		// 结束前析构所有的变体
		for (int i = 0; i < variants.length; i++) {
			variants[i].setIsDisposable(true);
			variants[i].dispose();
			variants[i] = null;
		}
		makeSureNativeObjectLive(temp);
		return bResult;
	}

	// 锁定并编辑 Recordset 的当前记录，成功则返回 True。
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

	// 用于提交对记录集的修改，包括添加、编辑记录、修改字段值的操作。
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
		// TODO 以后如果支持批量查询，这里的返回值还有待确定
		boolean result = RecordsetNative.jni_Update(getHandle());
		return result;
	}

	// 用于取消在调用 Update 方法前对当前记录或新记录所做的任何更改，成功则返回 True。
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

	// 用于关闭记录集，使用数据集以后应及时关闭。
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

	// 用于删除数据集中的当前记录，成功则返回 True。
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

	// 物理性删除指定记录集中的所有记录。
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
	 * 用于获得数据集的属性数据表中当前记录某一字段的值。 以后利用反射测试效率
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
			//外面可以设置"yyyy-MM-dd HH:mm:ss" ，也可以设置 "yyyy-MM-dd"
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

	// 用于获得数据集的属性数据表中当前记录某一字段的值。
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

	// 用于获得数据集的属性数据表中当前记录对应的几何对象。
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
		// @modified by 李云锦 at 2007年8月15日 下午02时10分41秒
		// @reason 注意这里返回的对象需要释放
		Geometry geo = Geometry.createInstance2(handle, this.getDataset()
				.getDatasource().getWorkspace());

		if (geo != null) // 不支持的对象，空指针问题
			geo.setIsDisposable(true);
		return geo;
	}

	// 获得数据集的属性表中当前记录对应的几何对象的 ID 号（即 SmID 字段的值）。
	public int getID() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_GetID(getHandle());
	}

	// 用于移动数据指针，成功返回 True。
	public boolean move(int count) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_Move(getHandle(), count);
	}

	// 用于移动 Recordset 对象的记录指针到第一条记录，成功则返回 True。
	public boolean moveFirst() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_MoveFirst(getHandle());
	}

	// 移动 Recordset 对象的记录指针到最后一条记录，成功则返回 True。
	public boolean moveLast() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_MoveLast(getHandle());
	}

	// 移动 Recordset 对象的记录指针到下一条记录，成功则返回 True。
	public boolean moveNext() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_MoveNext(getHandle());
	}

	// 移动 Recordset 对象的记录指针到上一条记录，成功则返回 True。
	public boolean movePrev() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_MovePrev(getHandle());
	}

	// 用于将数据指针移动到指定的位置，成功则返回 True。
	public boolean moveTo(int position) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_MoveTo(getHandle(), position);
	}

	// 刷新当前记录集，用来反映数据集中的变化。如果成功返回 True，否则返回 False。
	public boolean refresh() {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);

		}

		return RecordsetNative.jni_Refresh(getHandle());
	}

	// 在记录中搜索指定 ID 号的记录，并把记录指针定位于该记录。
	public boolean seekID(int id) {
		if (isClosed()) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean bResult = false;
		// 组件层来处理一下，当id<0时，直接返回false
		if (id < 0) {
			bResult = false;
		} else {
			bResult = RecordsetNative.jni_SeekID(getHandle(), id);
		}
		return bResult;
	}

	/**
	 * 设置是否禁止dataset修改时使用回调修改对应的属性数据集，true表示禁止
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

	// 用于设定记录集中某一字段的值，成功则返回 True。
	public boolean setFieldValue(int index, Object value) {
		checkExcepitonForSetFieldValueByIndex(index);
		// 此处判断的效率有待改进
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

	// 用于设定记录集中某一字段的值，成功则返回 True。
	public boolean setFieldValue(String name, Object value) {
		FieldInfo info = m_fieldInfos.get(name);
		if (info == null) {
			String message = InternalResource.loadString(name,
					InternalResource.RecordsetFieldIsNotExsit,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 此处判断的效率有待改进
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
					//日期正则表达式
					Pattern p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
					String temp = (String)value;
					if (p.matcher(temp).matches()) {
						//aix下用DateFormat 解析搞不定，暂时用SimpleDateFormat处理
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
					bResult = setDouble(name, 0);//应iServer要求，对整形字段赋空字符串时结果为0，下同；
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

	// 用于设定记录集中某一字段的值为 Null，成功则返回 True。
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
		// 此处判断的效率有待改进
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

	// 用于设定记录集中某一字段的值为 Null，成功则返回 True。
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
		// 此处判断的效率有待改进
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

	// 用于修改记录集当前位置的几何对象，覆盖原来的几何对象，成功则返回
	// True。修改记录的几何对象时，系统自动修改与对象有关的系统字段的值，如多边形对象的面积，线段对象的长度等。
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

	// 对指定字段进行诸如最大值、最小值、平均值，总和，标准差和方差等方式的统计。
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
		// 之前处理过了该缺陷，但是后来谁提交代码之前可能没有更新代码，将之前添加的异常判断给覆盖了
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

	// 对指定字段进行诸如最大值、最小值、平均值，总和，标准差和方差等方式的统计。
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
		// 之前处理过了该缺陷，但是后来谁提交代码之前可能没有更新代码，将之前添加的异常判断给覆盖了
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
	 * 内部使用 在查询失败时，先返回空的记录集，然后修改Parameter 在WrapJ执行拷贝
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
		// 传入的对象已被释放
		if (queryParameter.getHandle() == 0) {
			String message = InternalResource.loadString("queryParameter",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		// 注意：在设置后,原来queryParameter可以释放了，并不影响以下的对象
		// 多线程的时候记得锁起来
		if (queryParameter.getSpatialQueryObject() != null) {
			this.m_spatialQueryObject = queryParameter.getSpatialQueryObject();
			this.m_queryMode = queryParameter.getSpatialQueryMode();
			// this.m_spatialFilter = queryParameter.getSpatialFilter();
		}
		RecordsetNative.jni_SetQueryParameter(getHandle(), queryParameter
				.getHandle());
	}

	// //对数据进行二次查询
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
	 * 判断之前查询的字段值是否为null,字段值为null时返回true 当前Recordset游标处于BOF和EOF时也返回为true
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
	 * 通过映射表来设置字段值，如果字段名不存在，忽略该字段，设置成功返回true。
	 * 对UDB引擎可以用此接口进行批量修改
	 *
	 * batchEditor.begin();
	 * recordset.setValues(values);
	 * recordset.setValues(values);
	 * ......
	 * batchEditor.update();
	 *
	 * @param map 用于保存"字段名=>字段值"对的映射表
	 * @return 设置成功时true
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

//			//暂时只支持UDB引擎
//			if (! this.m_datasetVector.getDatasource().getEngineType().equals(EngineType.UDB)) {
//				return false;
//			}

			Object[] keyArray = map.keySet().toArray();
			int length = keyArray.length;

			// 两个数组存键值
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
				// 先不可释放，保证传入Wrapj，该函数结束前再释放
				variants[i].setIsDisposable(false);
				handles[i] = variants[i].getHandle();
			}
			boolean bResult = false;
			bResult = RecordsetNative.jni_SetGeometry1(getHandle(), 0, false, keys, handles);

			// 结束前析构所有的变体
			for (int i = 0; i < variants.length; i++) {
				variants[i].setIsDisposable(true);
				variants[i].dispose();
				variants[i] = null;
			}
			return bResult;
		}
	}

	/**
	 * 通过映射表和Geometry来设置当前记录，如果字段名不存在，忽略该字段，设置成功返回true
	 * 对UDB引擎可以用此接口进行批量修改
	 * batchEditor.begin();
	 * recordset.setValues(values，geometry);
	 * recordset.setValues(values，geometry);
	 * ......
	 * batchEditor.update();
	 *
	 * @param map 用于保存"字段名=>字段值"对的映射表
	 * @return 设置成功时true
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

			//暂时只支持UDB引擎
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
			// 两个数组存键值
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
				// 先不可释放，保证传入Wrapj，该函数结束前再释放
				variants[i].setIsDisposable(false);
				handles[i] = variants[i].getHandle();
			}
			boolean bResult = false;
			bResult = RecordsetNative.jni_SetGeometry1(getHandle(), geometryHandle, true, keys, handles);

			// 结束前析构所有的变体
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
	 * 通过对象数组来设置一整行字段的值，对象数组需要和字段顺序保持相同的索引顺序
	 * 对UDB引擎可以用此接口进行批量修改
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

			//暂时只支持UDB引擎
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
					// 先不可释放，保证传入Wrapj，该函数结束前再释放
					variants[i].setIsDisposable(false);
					handles[i] = variants[i].getHandle();
				}

			}
			boolean bResult = RecordsetNative.jni_SetGeometry1(getHandle(), 0, false, keys, handles);

			// 结束前析构所有的变体
			for (int i = 0; i < variants.length; i++) {
				variants[i].setIsDisposable(true);
				variants[i].dispose();
				variants[i] = null;
			}
			return bResult;
		}
	}

	/**
	 * 通过对象数组和集合对象来设置一整行字段的值，对象数组需要和字段顺序保持相同的索引顺序
	 * 对UDB引擎可以用此接口进行批量修改
	 * batchEditor.begin();
	 * recordset.setValues(values，geometry);
	 * recordset.setValues(values，geometry);
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
			//暂时只支持UDB引擎
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

					// 先不可释放，保证传入Wrapj，该函数结束前再释放
					variants[i].setIsDisposable(false);
					handles[i] = variants[i].getHandle();
				}

			}
			boolean bResult = RecordsetNative.jni_SetGeometry1(getHandle(), geometryHandle, true, keys, handles);

			// 结束前析构所有的变体
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
	 * 该方法主要用于保证在本地方法执行完之后，才可能调用GC释放本地可释放对象，主要针对Geometry对象
	 * @param obj 可释放的本地对象，如Geometry对象
	 */
	private void makeSureNativeObjectLive(InternalHandleDisposable obj){
		if (obj != null) {
		obj.getHandle();
		}
	}

	public class BatchEditor {

		// // 设置批量更新操作的最大记录数。
		// //批量更新的操作记录数等于这个最大记录数时即刻执行批量提交。
		private int m_maxRecordCount = 1024;

		long handle = 0;

		private BatchEditor(long handle) {
			this.handle = handle;
		}

		// 设置批量更新操作开始进行，调用该方法后，
		// 之后进行的更新操作将以批量更新的方式进行，
		// 更新操作不是立即生效而是要等到调用Update操作后统一更新。
		public void begin() {
			if (handle == 0) {
				String message = InternalResource.loadString("Begin()",
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			// 如果设置失败，则把返回原始值
			boolean result = RecordsetNative.jni_setMaxRecordCount(handle,
					m_maxRecordCount);
			boolean isBegin = RecordsetNative.jni_begin(handle, true);
			m_begin = true;
		}

		// 进行批量更新操作的统一提交。调用该方法前需要调用Begin方法来表示批量更新操作的开始，
		// Begin方法和Update方法之间的操作将采用批量更新的模式，调用该Update方法后，进行的更新操作统一生效，
		// 同时更新状态将变为单条更新，如果需要之后的操作批量进行，还需再次调用Begin的方法。
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

		// 批量更新取消操作，调用该方法后，之前进行的更新操作均被取消，同时更新状态将变为单条更新，
		// 如果需要之后的操作批量进行，还需再次调用Begin的方法
		//udb不支持这个东西
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

		// 设置批量更新操作的最大记录数。
		// 批量更新的操作记录数等于这个最大记录数时即刻执行批量提交。
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
	 * 从GeoJSON中的字符串中获取几何对象，及其所以属性值，并将其更新到数据集
	 * <p>支持的几何对象类型包括：点、线、面、多线
	 * @param geoJSON   GeoJSON格式的字符串，其中可以包含一个几何对象，也可以是包含多个对象的集合
	 * @return          获取到所有几何对象，并更新成功，返回true；否则，返回false
	 */
	public boolean fromGeoJSON(String geoJSON){
		boolean result = false;
		if (geoJSON == null)
			return false;
		try {
			JSONObject featureCollectionJson = new JSONObject(geoJSON);
			Object type = featureCollectionJson.get("type");
			// 根据GeoJSON串中的type类型，分别进行转换
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
	 * 从当前记录起，将数据集中的指定数目的记录转换成GeoJSON格式的字符串
	 * <p>支持的几何对象类型包括：点、线、面、多线，该方法最多支持获取包含连续10条记录的GeoJSON串.hasAttributte为true时，返回Feature类型的字符串；hasAttributte为false时，返回只含几何对象的Geometry类型的字符串
	 * @param hasAttributte   是否包含属性值
	 * @param count           需要获取的记录条数, 若count>10,则按10处理
	 * @return                返回获取到的GeoJSON格式字符串，若没有结果，返回null
	 */
	public String toGeoJSON(boolean hasAttributte, int count){
		String geoJSON = null;
		// 根据是否需要属性值，分别进行转换
		if (hasAttributte) {
			if (count == 1) {
				geoJSON = toFeatureGeoJSON();
			}
			if (count > 1) {  // 删除 “&& count<=10”;因下一个方法中已经将count>10处理为count=10了, modified by xingjun, 2015.10.28
				geoJSON = toFeatureCollectionGeoJSON(count, -1);
			}

		} else {
			if (count == 1) {
				geoJSON = toGeometryGeoJSON();
			}
			if (count > 1) {  // 删除 “&& count<=10”;因下一个方法中已经将count>10处理为count=10了, modified by xingjun, 2015.10.28
				geoJSON = toGeometryCollectionGeoJSON(count, -1);
			}
		}
		return geoJSON;
	}
	
	/**
	 * 从当前记录起，将数据集中的指定数目的记录转换成GeoJSON格式的字符串
	 * <p>支持的几何对象类型包括：点、线、面、多线，该方法最多支持获取包含连续10条记录的GeoJSON串.hasAttributte为true时，返回Feature类型的字符串；hasAttributte为false时，返回只含几何对象的Geometry类型的字符串
	 * @param hasAttributte   是否包含属性值
	 * @param count           需要获取的记录条数, 若count>10,则按10处理
	 * @param endID           结束SmID
	 * @return                返回获取到的GeoJSON格式字符串，若没有结果，返回null
	 */
	 String toGeoJSON(boolean hasAttributte, int count, int endID){
		String geoJSON = null;
		// Recordset中的相关接口都是从当前记录开始转换的,因此这里计算count是错误的,调用处传人的也是count,并指向了起始位置, modefied by Xingjun, 2016.09.07
//		int count = endID - startID;
		// 根据是否需要属性值，分别进行转换
		if (hasAttributte) {
			if (count == 1) {
				geoJSON = toFeatureGeoJSON();
			}
			if (count > 1) {  // 删除 “&& count<=10”;因下一个方法中已经将count>10处理为count=10了, modified by xingjun, 2015.10.28
				geoJSON = toFeatureCollectionGeoJSON(count, endID);
			}

		} else {
			if (count == 1) {
				geoJSON = toGeometryGeoJSON();
			}
			if (count > 1) {  // 删除 “&& count<=10”;因下一个方法中已经将count>10处理为count=10了, modified by xingjun, 2015.10.28
				geoJSON = toGeometryCollectionGeoJSON(count, endID);
			}
		}
		return geoJSON;
	}
	
	/********************************** private line ****************************************/
	
	/**
	 * 从几何对象的GeoJSON格式字符串，获取geometry，并将其添加到数据集中
	 * @param geoJSON  geometry的GeoJSON格式字符串
	 * @return         成功添加到数据集，返回true;否则返回false
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
	 * 将当前记录中的geometry转换成GeoJSON格式的字符串
	 * @return  返回转换后的GeoJSON格式字符串
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
			geometry.dispose(); // 转换后的geometry
			geometry = null;
		}
		
		return geoJSON;
	}
	
	/**
	 * 从当前记录开始，将指定数量的记录中的geometry转换成GeoJSON格式的字符串, 其type为GeometryCollection
	 * @param count  需要转换的记录数量，count不大于10
	 * @param endID  结束SmID,当其大于0时有效
	 * @return       返回转换后的GeoJSON格式字符串
	 */
	private String toGeometryCollectionGeoJSON(final int count, int endID) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("{\"type\":\"GeometryCollection\",");

		strBuilder.append("\"geometries\":[");
		Geometry geometry = null;
		int nCounter = 0;
		int mCount = count>10 ? 10 : count;          // 限制转换记录的数目，不超过10条记录
		if(mCount<0)
			mCount = 0;
		while (!this.isEOF() && nCounter < mCount) {
			geometry = this.getGeometry();
			if (geometry != null) {
				String geoJSON = geometry.toGeoJSON();
				if (!geoJSON.isEmpty()) {
					strBuilder.append(geoJSON);
					strBuilder.append(",");            // 每一个geometry GeoJSON串后加一个逗号
				} else{
					Log.e("Recordset", "SmID=" + this.getFieldValue("SmID").toString() + ", Unsupported geometry type: " + geometry.getType());
				}
				geometry.dispose();                    // 转换后的geometry
				geometry = null;
			}
		    // 这里检查的是当前已转换的记录，在endID不存在时，会导致多一条记录
//			if(endID > 0 && (this.getID()) >= endID){
//				break;
//			}
			
			this.moveNext();
			nCounter++;
			
			// 应检查下一条记录的SmID是否比转换结束的SmID大,这样才能保证转换的记录数正确,特别是endID的记录不存在时;
			// Modefied by Xingjun, 2016.09.08
			if (endID > 0 && (this.getID()) > endID) {
				break;
			}
		}

		if (strBuilder.lastIndexOf(",") == (strBuilder.length() - 1))
			strBuilder.deleteCharAt(strBuilder.length() - 1);        // 去掉最后一个逗号
		strBuilder.append("]}");
		return strBuilder.toString();
	}

	/**
	 * 从type为GeometryCollection的GeoJSON的字符串中，获取geometry，并将其添加到数据集中
	 * @param geoJSON   type为GeometryCollection的GeoJSON的字符串
	 * @return          若全部添加成功，返回true；否则，返回false
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
			// 获取每一个geometry
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
	 * 将当前记录转换成type为Feature的GeoJSON字符串，其中包含了该记录的geometry和字段信息
	 * @return   返回type为Feature的GeoJSON格式字符串
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

				// 文本型和数字型的值分别处理
				if (m_fieldInfos.get(j).getType() == FieldType.TEXT) {
					strBuilder.append("\"" + this.getFieldValue(name) + "\"");
				} else {
					strBuilder.append(this.getFieldValue(name));
				}
				strBuilder.append(",");
			}
			// 删除末尾的逗号
			if (strBuilder.lastIndexOf(",") == strBuilder.length() - 1) {
				strBuilder.deleteCharAt(strBuilder.length() - 1);
			}

			strBuilder.append("}}"); // properties 结束，Feature 结束

			strGeo = null;
		} else {
			Log.e("Recordset", "SmID=" + this.getFieldValue("SmID").toString() + ", can not get geometry.");
		}
		
		return strBuilder.toString();
	}

	/**
	 * 从当前记录开始，将指定数量的记录中的geometry及其字段信息转换成GeoJSON格式的字符串, 其type为FeatureCollection
	 * @param count  需要转换的记录数量，count不大于10
	 * @param endID  结束SmID,当其大于0时有效
	 * @return       返回转换后的GeoJSON格式字符串
	 */
	public String toFeatureCollectionGeoJSON(int count , int endID) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("{\"type\":\"FeatureCollection\",");
		strBuilder.append("\"features\":[");

		int nCounter = 0;
		int mCount = count>10 ? 10 : count;        // 限制转换记录的数目，不超过10条记录
		if(mCount < 0)
			mCount = 0;
		String strFeature = null;

		while (!this.isEOF() && nCounter < mCount) {
			// 每条记录构建一个feature
			strFeature = this.toFeatureGeoJSON();
			if (strFeature != null) {
				strBuilder.append(strFeature);
				strBuilder.append(","); // Feature 结束
			}
			strFeature = null;
			
		    // 这里检查的是当前已转换的记录，在endID不存在时，会导致多一条记录
//			if(endID > 0 && (this.getID()) >= endID){
//				break;
//			}
			
			this.moveNext();
			nCounter++;
			
			// 应检查下一条记录的SmID是否比转换结束的SmID大,这样才能保证转换的记录数正确,特别是endID的记录不存在时;
			// Modefied by Xingjun, 2016.09.08
			if (endID > 0 && (this.getID()) > endID) {
				break;
			}
		}
		// 删除末尾的逗号
		if (strBuilder.lastIndexOf(",") == strBuilder.length() - 1)
			strBuilder.deleteCharAt(strBuilder.length() - 1);

		strBuilder.append("]}");        // features 结束，FeatureCollection 结束

		if(strBuilder.toString().isEmpty()){
			return null;
		}
		return strBuilder.toString();
	}

	/**
	 * 从type为Feature的GeoJSON的字符串中，获取geometry及其字段信息，并将其更新到数据集中；若
	 * @param geoJSON   type为Feature的GeoJSON的字符串
	 * @return          获取成功，返回true；否则，返回false
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
					result = this.addNew(geo);        // 没有SmID对应的记录，就增加记录
				}else{
					this.edit();
					result = this.setGeometry(geo);   // 记录集移动到指定的ID时，修改记录
				}
				
			} else {
				result = this.addNew(geo);            // 没有SmID就增加记录
			}
			// 添加或设置geometry成功后，设置其字段信息
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
				Log.e("Recordset", "添加或设置geometry失败，请检查SmID和geometry的值，input GeoJSON:" + geoJSON);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return result;
	}

	/**
	 * 从type为FeatureCollection的GeoJSON的字符串中，获取geometry及其字段信息，并将其更新到数据集中；
	 * @param geoJSON   type为FeatureCollection的GeoJSON的字符串
	 * @return          若全部更新成功，返回true；否则，返回false
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
	 * 获取传入的JSONOBject所包含的geometry
	 * @param json  由geometry的GeoJSON格式的字符串构造的JSONObject
	 * @return      返回提取的Geometry
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

