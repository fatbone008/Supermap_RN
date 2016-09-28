package com.supermap.data;

import java.util.ArrayList;

import java.util.HashMap;

//import javax.swing.event.RowSorterEvent.Type;

import com.supermap.data.FieldInfo;
import com.supermap.data.FieldInfos;
import com.supermap.data.FieldType;
import com.supermap.data.Rectangle2D;
import com.supermap.data.Charset;

class ImportDataInfo extends InternalHandle {
	
	private String m_filePath;

	private FileType m_fileType;
	
	FieldInfos m_sourceFieldInfos;

	FieldInfos m_targetFieldInfos;
	
	// 维护原始和目标字段数组对应关系
	HashMap<String, String> m_dictTargetFieldNames;
	
	//存放不导入的字段的索引
	ArrayList<Integer> m_importFieldState;

	//存放修改名字的字段的字典
	HashMap<Integer, String> m_changeName;
	
	//存放修改名字的字段的字典
	HashMap<Integer, FieldType> m_changeFieldType;

	//存放交换位置前的索引
	ArrayList<Integer> m_sourceIndex;

	//存放交换位置后的索引
    ArrayList<Integer> m_targetIndex;
	

	protected ImportDataInfo() {

	}

	ImportDataInfo(long handle, String filePath, FileType fileType,
			String targetName) {
		this.setHandle(handle);
		this.m_filePath = filePath;
		this.m_fileType = fileType;
		if(m_fileType.equals(FileType.MIF) || m_fileType.equals(FileType.TAB)
				|| m_fileType.equals(FileType.DXF)
				|| m_fileType.equals(FileType.DWG)
				|| m_fileType.equals(FileType.DGN)
				|| m_fileType.equals(FileType.SHP)
				|| m_fileType.equals(FileType.COVERAGE)
				|| m_fileType.equals(FileType.TEMSBuildingVector)
				|| m_fileType.equals(FileType.TEMSVector)
				|| m_fileType.equals(FileType.ENCS57)
				|| m_fileType.equals(FileType.KML)
				|| m_fileType.equals(FileType.KMZ)
				|| m_fileType.equals(FileType.MAPGIS)
				|| m_fileType.equals(FileType.LIDAR)
				|| m_fileType.equals(FileType.SCV)
				|| m_fileType.equals(FileType.CSV)
				|| m_fileType.equals(FileType.GML)
				|| m_fileType.equals(FileType.E00)
				|| m_fileType.equals(FileType.SDEVector)
				|| m_fileType.equals(FileType.FileGDBVector))
		{
			initSourceAndTargetFields();
			m_importFieldState = new ArrayList<Integer>();
			m_changeName = new HashMap<Integer, String>();
			m_sourceIndex = new ArrayList<Integer>();
			m_targetIndex = new ArrayList<Integer>();
			m_changeFieldType = new HashMap<Integer, FieldType>();
		}
	}
	
	private void initSourceAndTargetFields() {
		m_sourceFieldInfos = new FieldInfos();
		m_targetFieldInfos = new FieldInfos();
		m_dictTargetFieldNames = new HashMap<String, String>();
		int count = ImportDataInfoNative.jni_GetFieldCount(getHandle());
		long[] handles = new long[count];
		ImportDataInfoNative.jni_GetSourceFieldInfos(getHandle(), handles);
		for (int i = 0; i < count; i++) {
			FieldInfo fileInfo = InternalFieldInfo.createInstance(handles[i]);
			boolean bCanBeAdd = false;
			if(m_sourceFieldInfos.get(fileInfo.getName()) == null)
			{
				bCanBeAdd = true;
			}
			if(!fileInfo.isSystemField() && !fileInfo.getName().toUpperCase().startsWith("SM") && bCanBeAdd)
			{
				m_sourceFieldInfos.add((FieldInfo)fileInfo.clone() );
				m_targetFieldInfos.add((FieldInfo)fileInfo.clone() );
				m_dictTargetFieldNames.put(fileInfo.getName(), fileInfo.getName());
			}
		}
	}

	/**
	 * 获取导入数据的地理范围
	 * @return
	 */
	public Rectangle2D getBounds() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getBounds()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] temp = new double[4];
		if (m_fileType.equals(FileType.MIF) || m_fileType.equals(FileType.TAB)
				|| m_fileType.equals(FileType.DXF)
				|| m_fileType.equals(FileType.DWG)
				|| m_fileType.equals(FileType.SHP)
				|| m_fileType.equals(FileType.COVERAGE)
				|| m_fileType.equals(FileType.TEMSBuildingVector)
				|| m_fileType.equals(FileType.TEMSVector)
				|| m_fileType.equals(FileType.ENCS57)
				|| m_fileType.equals(FileType.KML)
				|| m_fileType.equals(FileType.KMZ)
				|| m_fileType.equals(FileType.MAPGIS)
				|| m_fileType.equals(FileType.LIDAR)
				|| m_fileType.equals(FileType.SCV)
				|| m_fileType.equals(FileType.CSV)
				|| m_fileType.equals(FileType.GML)
				|| m_fileType.equals(FileType.E00)
				|| m_fileType.equals(FileType.SDEVector)
				|| m_fileType.equals(FileType.FileGDBVector)
				|| m_fileType.equals(FileType.DGN)) {

			//矢量的通过UGFeatureClassDefn取Bounds
			ImportDataInfoNative.jni_GetBounds(this.getHandle(), temp);
		} else if (m_fileType.equals(FileType.PNG)
				|| m_fileType.equals(FileType.TIF)
				|| m_fileType.equals(FileType.IMG)
				|| m_fileType.equals(FileType.GRD)
				|| m_fileType.equals(FileType.GIF)
				|| m_fileType.equals(FileType.JPG)
				|| m_fileType.equals(FileType.RAW)
				|| m_fileType.equals(FileType.SIT)
				|| m_fileType.equals(FileType.BMP)
				|| m_fileType.equals(FileType.GBDEM)
				|| m_fileType.equals(FileType.USGSDEM)
				|| m_fileType.equals(FileType.BIL)
				|| m_fileType.equals(FileType.BIP)
				|| m_fileType.equals(FileType.BSQ)
				|| m_fileType.equals(FileType.TEMSClutter)
				|| m_fileType.equals(FileType.SDERaster)
				|| m_fileType.equals(FileType.FileGDBRaster)
				||  m_fileType.equals(FileType.ARCINFO_BINGRID)){
			//栅格的通过RasterInfo取Bounds
			ImportDataInfoNative.jni_GetBounds2(this.getHandle(), temp);
		}
		Rectangle2D bounds = new Rectangle2D(temp[0], temp[1], temp[2], temp[3]);
		return bounds;
	}

	/**
	 * 获取结果数据集的名称
	 * @return
	 */
	public String getTargetName() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getTargetName()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		String result = "";
		if (m_fileType.equals(FileType.MIF) || m_fileType.equals(FileType.TAB)
				|| m_fileType.equals(FileType.DXF)
				|| m_fileType.equals(FileType.DWG)
				|| m_fileType.equals(FileType.SHP)
				|| m_fileType.equals(FileType.COVERAGE)
				|| m_fileType.equals(FileType.TEMSBuildingVector)
				|| m_fileType.equals(FileType.TEMSVector)
				|| m_fileType.equals(FileType.ENCS57)
				|| m_fileType.equals(FileType.KML)
				|| m_fileType.equals(FileType.KMZ)
				|| m_fileType.equals(FileType.MAPGIS)
				|| m_fileType.equals(FileType.LIDAR)
				|| m_fileType.equals(FileType.SCV)
				|| m_fileType.equals(FileType.CSV)
				|| m_fileType.equals(FileType.GML)
				|| m_fileType.equals(FileType.E00)
				|| m_fileType.equals(FileType.SDEVector)
				|| m_fileType.equals(FileType.FileGDBVector)
				|| m_fileType.equals(FileType.DGN)) {
			//矢量的通过UGFeatureClassDefn取结果数据集名
			result = ImportDataInfoNative.jni_GetTargetName(this.getHandle());
		} else if (m_fileType.equals(FileType.PNG)
				|| m_fileType.equals(FileType.TIF)
				|| m_fileType.equals(FileType.IMG)
				|| m_fileType.equals(FileType.GRD)
				|| m_fileType.equals(FileType.GIF)
				|| m_fileType.equals(FileType.JPG)
				|| m_fileType.equals(FileType.RAW)
				|| m_fileType.equals(FileType.SIT)
				|| m_fileType.equals(FileType.BMP)
				|| m_fileType.equals(FileType.GBDEM)
				|| m_fileType.equals(FileType.USGSDEM)
				|| m_fileType.equals(FileType.BIL)
				|| m_fileType.equals(FileType.BIP)
				|| m_fileType.equals(FileType.BSQ)
				|| m_fileType.equals(FileType.TEMSClutter)
				|| m_fileType.equals(FileType.SDERaster)
				|| m_fileType.equals(FileType.FileGDBRaster)
				|| m_fileType.equals(FileType.ARCINFO_BINGRID)) {
			//栅格的直接返回从UGRasterImportParams中取出的结果栅格数据集名
			result = ImportDataInfoNative.jni_GetTargetNameRaster(this.getHandle());
		}
		return result;
	}

	/**
	 * 设置结果数据集的名称
	 * @param targetName
	 */
	public void setTargetName(String targetName) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTargetName(String targetName)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (m_fileType.equals(FileType.MIF) || m_fileType.equals(FileType.TAB)
				|| m_fileType.equals(FileType.DXF)
				|| m_fileType.equals(FileType.DWG)
				|| m_fileType.equals(FileType.SHP)
				|| m_fileType.equals(FileType.COVERAGE)
				|| m_fileType.equals(FileType.TEMSBuildingVector)
				|| m_fileType.equals(FileType.TEMSVector)
				|| m_fileType.equals(FileType.ENCS57)
				|| m_fileType.equals(FileType.KML)
				|| m_fileType.equals(FileType.KMZ)
				|| m_fileType.equals(FileType.MAPGIS)
				|| m_fileType.equals(FileType.LIDAR)
				|| m_fileType.equals(FileType.SCV)
				|| m_fileType.equals(FileType.CSV)
				|| m_fileType.equals(FileType.GML)
				|| m_fileType.equals(FileType.E00)
				|| m_fileType.equals(FileType.SDEVector)
				|| m_fileType.equals(FileType.FileGDBVector)
				|| m_fileType.equals(FileType.DGN)){
			//矢量的通过UGFeatureClassDefn设置结果数据集名
			ImportDataInfoNative
					.jni_SetTargetName(this.getHandle(), targetName);
		} else if (m_fileType.equals(FileType.PNG)
				|| m_fileType.equals(FileType.TIF)
				|| m_fileType.equals(FileType.IMG)
				|| m_fileType.equals(FileType.GRD)
				|| m_fileType.equals(FileType.GIF)
				|| m_fileType.equals(FileType.JPG)
				|| m_fileType.equals(FileType.RAW)
				|| m_fileType.equals(FileType.SIT)
				|| m_fileType.equals(FileType.BMP)
				|| m_fileType.equals(FileType.GBDEM)
				|| m_fileType.equals(FileType.USGSDEM)
				|| m_fileType.equals(FileType.BIL)
				|| m_fileType.equals(FileType.BIP)
				|| m_fileType.equals(FileType.BSQ)
				|| m_fileType.equals(FileType.TEMSClutter)
				|| m_fileType.equals(FileType.SDERaster)
				|| m_fileType.equals(FileType.FileGDBRaster)
				|| m_fileType.equals(FileType.ARCINFO_BINGRID)) {
			ImportDataInfoNative
			.jni_SetTargetNameRaster(this.getHandle(), targetName);
		}
	}
	
	/**
	 * 获取当前数据集信息对应的源文件
	 * @return
	 */
	public String getSourceFile() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSourceFile()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return m_filePath;
	}
	
	/**
	 * 获取源数据中的字段信息
	 * 
	 * @return 源数据中的字段信息
	 */
	FieldInfo[] getSourceFieldInfos_base(){
		if(getHandle()==0){
			String message=InternalResource.loadString("getSourceFieldInfos()",
					       InternalResource.HandleObjectHasBeenDisposed,
					       InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return m_sourceFieldInfos.toArray();
	}
	
	/**
	 * 获取目标数据中的字段信息
	 * 
	 * @return 目标数据中的字段信息
	 */
	FieldInfo[] getTargetFieldInfos_base(){
		if(getHandle()==0){
			String message=InternalResource.loadString("getTargetFieldInfo()",
					       InternalResource.HandleObjectHasBeenDisposed,
					       InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return m_targetFieldInfos.toArray();
	}
	
	/**
	 * 设置字段名称之后，及时更新TargetFieldInfos，而SourceFieldInfos是不变的
	 * 
	 * @param oldName 原始字段名称
	 * @param newName 目标字段名称
	 * @return 更新是否成功
	 */
    boolean changeFieldName_base(String oldName, String newName) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"ChangeFieldName(String oldName, String newName)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (oldName == null || oldName.trim().length() == 0) {
			String message = InternalResource.loadString("oldName",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (newName == null || newName.trim().length() == 0) {
			String message = InternalResource.loadString("newName",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (oldName.equalsIgnoreCase(newName)) {
			return true;
		}
		int indexSource = m_sourceFieldInfos.indexOf(oldName);
		if (indexSource == -1) {
			String message = InternalResource.loadString("oldName",
					InternalResource.ImportDataInfoFieldNameIsNotExist,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		String targetName = m_dictTargetFieldNames.get(oldName);
		int indexTarget = m_targetFieldInfos.indexOf(targetName);
		if (indexTarget == -1) { // 此字段都已经不导入了
			String message = InternalResource.loadString("targetName",
					InternalResource.ImportDataInfoFieldNameIsNotExist,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		String temp = getAvailableName(newName);
		m_targetFieldInfos.get(indexTarget).setName(temp);
		m_targetFieldInfos.get(indexTarget).setCaption(temp);
		m_changeName.put(indexSource, temp);
		m_dictTargetFieldNames.put(oldName, temp);
		return true;
	}
	
	
	/**
	 * 设置字段类型之后，及时更新TargetFieldInfos，而SourceFieldInfos是不变的
	 * 
	 * @param oldName 原始字段名称
	 * @param newName 目标字段类型
	 * @return 更新是否成功
	 */
	  boolean changeFieldType_base(String oldName, FieldType fieldType) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"changeFieldType(String oldName, FieldType fieldType)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (oldName == null || oldName.trim().length() == 0) {
			String message = InternalResource.loadString("oldName",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
	
		int indexSource = m_sourceFieldInfos.indexOf(oldName);
		if (indexSource == -1) {
			String message = InternalResource.loadString("oldName",
					InternalResource.ImportDataInfoFieldNameIsNotExist,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		String targetName = m_dictTargetFieldNames.get(oldName);
		int indexTarget = m_targetFieldInfos.indexOf(targetName);
		if (indexTarget == -1) {
			String message = InternalResource.loadString("targetName",
					InternalResource.ImportDataInfoFieldNameIsNotExist,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		m_targetFieldInfos.get(indexTarget).setType(fieldType);
		m_targetFieldInfos.get(indexTarget).setType(fieldType);
		m_changeFieldType.put(indexSource, fieldType);
		return true;
	}

	/**
	 * 设置指定字段是否导入，当excludeField为true时，不包含该字段
	 * 组件层使用一个HashMap来保存所有需要排除的字段名,在ImportSetting的setTargetDataInfos中进行处理，更新到底层中
	 * 
	 * @param oldName 原始字段名称
	 * @param excludeField
	 * @return 设置是否成功
	 */
	 boolean setImportFieldState_base(String oldName, boolean excludeField) {
		if (getHandle() == 0) {
			String message = InternalResource
					.loadString(
							"setImportFieldState(String fieldname,Boolean excludeField)",
							InternalResource.HandleObjectHasBeenDisposed,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (oldName == null || oldName.trim().length() == 0) {
			String message = InternalResource.loadString("oldName",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		if(!m_dictTargetFieldNames.containsKey(oldName))
		{
			String message = InternalResource.loadString("oldName",
					InternalResource.ImportDataInfoFieldNameIsNotExist,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		int indexInTheSourceFieldInfos = m_sourceFieldInfos.indexOf(oldName);
		if (indexInTheSourceFieldInfos == -1) {
			String message = InternalResource.loadString("oldName",
					InternalResource.ImportDataInfoFieldNameIsNotExist,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		String targetName = m_dictTargetFieldNames.get(oldName);
		int indexInTheTargetFieldInofs = m_targetFieldInfos.indexOf(targetName);
		if (excludeField) {
			if (indexInTheTargetFieldInofs != -1) {
				m_targetFieldInfos.remove(indexInTheTargetFieldInofs);
				m_importFieldState.add(indexInTheSourceFieldInfos);
			}
		} else {
			// added by jiangrs 只有当目标数据集里头已经是不导入了 然后又导入
			if (indexInTheTargetFieldInofs == -1) {
				FieldInfo fieldInfo = (FieldInfo) m_sourceFieldInfos.get(
						indexInTheSourceFieldInfos).clone();
				String str = getAvailableName(oldName);
				fieldInfo.setName(str);
				fieldInfo.setCaption(str);
				m_targetFieldInfos.insert(indexInTheSourceFieldInfos, fieldInfo);
				m_importFieldState.remove((Object)indexInTheSourceFieldInfos);
			}
		}
		return true;
	}
	 
	/** 
	 * 获取指定字段是否导入
	 * 
	 * 
	 * @param oldName 原始字段名称
	 * @return 获取是否导入该字段
	 */
    boolean getImportFieldState_base(String oldName) {
		if (getHandle() == 0) {
			String message = InternalResource
					.loadString(
							"getImportFieldState(String oldName)",
							InternalResource.HandleObjectHasBeenDisposed,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (oldName == null || oldName.trim().length() == 0) {
			String message = InternalResource.loadString("oldName",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		if(!m_dictTargetFieldNames.containsKey(oldName))
		{
			String message = InternalResource.loadString("oldName",
					InternalResource.ImportDataInfoFieldNameIsNotExist,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		String targetName = m_dictTargetFieldNames.get(oldName);
		
		int indexInTheTargetFieldInofs = m_targetFieldInfos.indexOf(targetName);
		if (indexInTheTargetFieldInofs == -1) {
			return false;
		}
		FieldInfo fieldInfo = (FieldInfo)m_targetFieldInfos.get(
				indexInTheTargetFieldInofs).clone();
		if(fieldInfo!=null && fieldInfo.getName().trim().length() >0)
			return true;
		else
			return false;
		
	}
    
	/**
	 * 交换目标字段的顺序
	 * 
	 * @param tagFieldName1 目标字段名称1
	 * @param tagFieldName2 目标字段名称2
	 * @return 设置是否成功
	 */
	boolean exchangeFieldOrder_base(String tagFieldName1, String tagFieldName2) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"exchangeFieldOrder(String tagFieldName1, String tagFieldName2)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (tagFieldName1 == null || tagFieldName1.trim().length() == 0) {
			String message = InternalResource.loadString("tagFieldName1",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (tagFieldName2 == null || tagFieldName2.trim().length() == 0) {
			String message = InternalResource.loadString("tagFieldName2",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		int fieldName1Index = m_targetFieldInfos.indexOf(tagFieldName1);
		if (fieldName1Index == -1) {
			String message = InternalResource.loadString("tagFieldName1",
					InternalResource.ImportDataInfoFieldNameIsNotExist,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		int fieldName2Index = m_targetFieldInfos.indexOf(tagFieldName2);
		if (fieldName2Index == -1) {
			String message = InternalResource.loadString("tagFieldName2",
					InternalResource.ImportDataInfoFieldNameIsNotExist,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		// 获取目标字段名称的原始字段名称
		String oldName1 = "", oldNames2 = "";
		Object[] keyArray = m_dictTargetFieldNames.keySet().toArray();
		int size = m_dictTargetFieldNames.size();
		for (int j = 0; j < size; j++) {
			String strFieldName = (String) m_dictTargetFieldNames.get(keyArray[j]);
			if(strFieldName.compareToIgnoreCase(tagFieldName1) == 0)
			{
				oldName1 = (String)keyArray[j];
			}
			if(strFieldName.compareToIgnoreCase(tagFieldName2) == 0)
			{
				oldNames2 = (String)keyArray[j];
			}
		}
		
		// 获取目标字段名称在原始字段名称的字段索引
		int oldNameIndex1 = m_sourceFieldInfos.indexOf(oldName1);
		if (oldNameIndex1 == -1) {
			String message = InternalResource.loadString("tagFieldName1",
					InternalResource.ImportDataInfoFieldNameIsNotExist,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		int oldNameIndex2 = m_sourceFieldInfos.indexOf(oldNames2);
		if (oldNameIndex2 == -1) {
			String message = InternalResource.loadString("tagFieldName2",
					InternalResource.ImportDataInfoFieldNameIsNotExist,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		m_targetFieldInfos.exchange(fieldName1Index, fieldName2Index);
		m_sourceIndex.add(oldNameIndex1);
		m_targetIndex.add(oldNameIndex2);
		
		return true;
	}
	
	ArrayList<Integer> getFieldState() {
		return m_importFieldState;
	}

	HashMap<Integer, String> getChangeName() {
		return m_changeName;
	}

	ArrayList<Integer> getSourceIndex() {
		return m_sourceIndex;
	}

	ArrayList<Integer> getTargetIndex() {
		return m_targetIndex;
	}
	
	HashMap<Integer, FieldType> getChangeFieldType() {
		return m_changeFieldType;
	}
    
	private String getAvailableName(String srcName) {
		if (m_targetFieldInfos.indexOf(srcName) == -1) {
			return srcName;
		} else if (srcName.lastIndexOf('_') == -1) {
			return getAvailableName(srcName + "_" + 1);
		} else {
			String tempName = srcName.substring(0, srcName.lastIndexOf('_'));
			String numStr = srcName.substring(srcName.lastIndexOf('_') + 1);
			if (!isNumStr(numStr)) {
				return getAvailableName(srcName + "_" + 1);
			} else {
				return getAvailableName(tempName + "_"
						+ (Integer.parseInt(numStr) + 1));
			}
		}
	}
	
	

	private boolean isNumStr(String str) {
		StringBuffer buffer = new StringBuffer(str);
		for (int i = 0; i < buffer.length(); i++) {
			if (buffer.charAt(i) < '0' && buffer.charAt(i) > '9') {
				return false;
			}
		}
		return true;
	}

	protected void clearHandle() {	
		super.clearHandle();
		m_sourceFieldInfos = null;
		m_targetFieldInfos = null;
	}
}
