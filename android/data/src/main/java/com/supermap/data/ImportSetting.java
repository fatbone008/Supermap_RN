package com.supermap.data;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;

import com.supermap.data.DatasetType;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EncodeType;
import com.supermap.data.FieldType;
import com.supermap.data.InternalHandle;
import com.supermap.data.InternalHandleDisposable;
//import com.supermap.data.License;
import com.supermap.data.PrjCoordSys;
//import com.supermap.data.ProductType;
import com.supermap.data.Charset;
import com.supermap.data.FileType;
abstract class ImportSetting extends InternalHandleDisposable {

	private DatasourceConnectionInfo m_datasourceConnectionInfo;

	private PrjCoordSys m_prjCoordSys;

	private DataType m_dataType;

	private ArrayList<Boolean> m_states;

	private Datasource m_datasource;
	
	ImportDataInfos m_dataInfos;
	
	private static int m_licFMEVecotrCode  = -1;
	private static int m_licFMEEsriCode    = -1;
	private static int m_licFMERasterCode  = -1;
	private static int m_licFMEOtherCode   = -1;
	
//	static {
//		m_licFMEVecotrCode = InternalToolkiConversion.GetFMELicenseCode(InternalToolkiConversion.getFMEVectorProducts());
//
//		m_licFMEEsriCode   = InternalToolkiConversion.GetFMELicenseCode(InternalToolkiConversion.getFMEEsriProducts());
//		
//		m_licFMERasterCode = InternalToolkiConversion.GetFMELicenseCode(InternalToolkiConversion.getFMERasterProducts());
//		
//		m_licFMEOtherCode  = InternalToolkiConversion.GetFMELicenseCode(InternalToolkiConversion.getFMEOtherProducts());
//	}

	//	private String m_filePath;

	//	private 

	/**
	 * 获取需要导入的数据路径信息
	 * @return
	 */
	public String getSourceFilePath() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSourceFilePath()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return ImportSettingNative.jni_GetSourceFilePath(this.getHandle());
	}

	/**
	 * 设置需要导入的数据路径信息
	 * @param path
	 */
	public void setSourceFilePath(String path) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setSourceFilePath(String path)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (path == null || path.trim().length() == 0) {
			String message = InternalResource.loadString("path",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		// 通过正则表达式实现通配符功能
		File file = new File(path);
		//		String name = file.getName();
		//		name = name.replace('.', '#');
		//		name = name.replaceAll("#", "\\\\.");
		//		name = name.replace('*', '#');
		//		name = name.replaceAll("#", ".*");
		//		name = name.replace('?', '#');
		//		name = name.replaceAll("#", ".?");
		//		name = "^" + name + "$";
		//		final Pattern pattern = Pattern.compile(name);
		//		File parent = file.getParentFile();
		//		if (parent == null) {
		//			String message = InternalResource.loadString("",
		//					InternalResource.ImportSettingTheParentPathIsNotValide,
		//					InternalResource.BundleName);
		//			throw new IllegalArgumentException(message);
		//		}
		//		File[] results = parent.listFiles(new FilenameFilter() {
		//			public boolean accept(File dir, String name) {
		//				return pattern.matcher(name).matches();
		//			}
		//		}); 

		//		for (int i = 0; i < results.length; i++) {
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		ImportSettingNative.jni_SetSourceFilePath(this.getHandle(), file
				.getPath());
		//		}

	}
	
	/**
	 * 获取目标的数据集名称
	 * @return
	 */
	public String getTargetDatasetName() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSourceFilePath()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return ImportSettingNative.jni_GetTargetDatasetName(this.getHandle());
	}

	/**
	 * 设置目标数据集名称
	 * @param path
	 */
	public void setTargetDatasetName(String targetDatasetName) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setSourceFilePath(String path)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (targetDatasetName == null || targetDatasetName.trim().length() == 0) {
			String message = InternalResource.loadString("path",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		ImportSettingNative.jni_SetTargetDatasetName(this.getHandle(), targetDatasetName);

	}

	/**
	 * 获取需要导入数据的类型。
	 * @return
	 */
	public FileType getSourceFileType() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSourceFileType()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugcValue = ImportSettingNative.jni_GetSourceFileType(this
				.getHandle());
		return (FileType) InternalEnum.parseUGCValue(FileType.class, ugcValue);
	}

	/**
	 * 获取需要导入的数据源连接信息
	 * @return
	 */
	public DatasourceConnectionInfo getTargetDatasourceConnectionInfo() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getTargetDatasourceConnectionInfo()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return m_datasourceConnectionInfo;
	}

	/**
	 * 设置需要导入的数据源连接信息
	 * @param connectionInfo
	 */
	public void setTargetDatasourceConnectionInfo(
			DatasourceConnectionInfo connectionInfo) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTargetDatasourceConnectionInfo()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (connectionInfo == null) {
			if (m_datasourceConnectionInfo != null) {
				InternalDatasourceConnectionInfo
						.clearHandle(m_datasourceConnectionInfo);
				m_datasourceConnectionInfo = null;
			}
		} else {
			long handle = InternalHandle.getHandle(connectionInfo);
			if (handle == 0) {
				String message = InternalResource
						.loadString(
								"setTargetDatasourceConnectionInfo(DatasourceConnectionInfo connectionInfo)",
								InternalResource.DatasourcesConnectionInfoIsInvalid,
								InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
			m_datasourceConnectionInfo = this.cloneDatasourceConnectionInfo(connectionInfo);
			this.setTargetDatasource(null);
		}
	}

	//TargetDatasource 与 TargetDatasourceConnectionInfo 两个属性相互冲突，
	//设置完一个后，之前对应设置的就会被设置为null
	public Datasource getTargetDatasource() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getTargetDatasource()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return m_datasource;
	}

	public void setTargetDatasource(Datasource datasource) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTargetDatasource(Datasource datasource)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		this.m_datasource = datasource;
		if (m_datasource != null) {
			//Modified by Wangbg 2010-9-16
			this.setTargetDatasourceConnectionInfo(null);
//			this.setTargetDatasourceConnectionInfo(m_datasource.getConnectionInfo());
		}
	}

	/**
	 * 设置要生成的数据集的编码类型
	 * @return
	 */
	public EncodeType getTargetEncodeType() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getEncodeType()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugcValue = ImportSettingNative.jni_GetEncodeType(this.getHandle());
		return (EncodeType) InternalEnum.parseUGCValue(EncodeType.class,
				ugcValue);
	}
	
	
	/**
	 * 设置要生成的数据集的编码类型
	 * @return
	 */
	@Deprecated
	public EncodeType getEncodeType() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getEncodeType()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugcValue = ImportSettingNative.jni_GetEncodeType(this.getHandle());
		return (EncodeType) InternalEnum.parseUGCValue(EncodeType.class,
				ugcValue);
	}

	/**
	 * 设置要生成的数据集的编码类型
	 * @param type
	 */
	@Deprecated
	public void setEncodeType(EncodeType type) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setEncodeType(EncodeType type)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		ImportSettingNative.jni_SetEncodeType(this.getHandle(), type.value());
	}
	
	/**
	 * 获取要导入的文件的字符集类型
	 * @return
	 */
	public Charset getSourceFileCharset() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getEncodeType()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugcValue = ImportSettingNative.jni_GetFileCharset(this.getHandle());
		return (Charset) InternalEnum.parseUGCValue(Charset.class,
				ugcValue);
	}

	/**
	 * 设置要导入的文件的字符集类型
	 * @param type
	 */
	public void setSourceFileCharset(Charset charset) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setEncodeType(EncodeType type)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		ImportSettingNative.jni_SetFileCharset(this.getHandle(), charset.value());
	}

	/**
	 * 设置要生成的数据集的编码类型
	 * @param type
	 */
	public void setTargetEncodeType(EncodeType type) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setEncodeType(EncodeType type)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		ImportSettingNative.jni_SetEncodeType(this.getHandle(), type.value());
	}

	/**
	 * 获取导入后目标数据的坐标系
	 * @return
	 */
	public PrjCoordSys getTargetPrjCoordSys() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getTargetPrjCoordSys()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		//		if (m_prjCoordSys == null) {
		//			long handle = ImportSettingNative.jni_GetTargetPrjCoordSys(this
		//					.getHandle());
		//			if (handle != 0) {
		//				m_prjCoordSys = InternalPrjCoordSys.createInstance(handle,
		//						false);
		//			}
		//		}
		return m_prjCoordSys;
	}

	/**
	 * 设置导入后目标数据的坐标系
	 * @param prjCoordSys
	 */
	public void setTargetPrjCoordSys(PrjCoordSys prjCoordSys) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTargetPrjCoordSys(PrjCoordSys prjCoordSys)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (prjCoordSys == null) {
			if (m_prjCoordSys != null) {
				InternalPrjCoordSys.clearHandle(m_prjCoordSys);
				m_prjCoordSys = null;
			}
		} else {
			m_prjCoordSys = (PrjCoordSys) prjCoordSys.clone();
			long cloneHandle = InternalHandle.getHandle(m_prjCoordSys);
			if (cloneHandle == 0) {
				String message = InternalResource.loadString("prjCoordSys",
						InternalResource.GlobalInvalidConstructorArgument,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			ImportSettingNative.jni_SetTargetPrjCoordSys(this.getHandle(),
					cloneHandle);
			
//			InternalHandleDisposable.makeSureNativeObjectLive(prjCoordSys);
		}
	}

	/**
	 * 获取当同名数据集存在时导入的模式
	 * @return
	 */
	public ImportMode getImportMode() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getImportMode()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugcValue = ImportSettingNative.jni_GetImportMode(this.getHandle());
		return (ImportMode) InternalEnum.parseUGCValue(ImportMode.class,
				ugcValue);
	}

	/**
	 * 设置当同名数据集存在时，导入的模式
	 * @param importMode
	 */
	public void setImportMode(ImportMode importMode) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setImportMode(ImportMode importMode)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugcValue = importMode.value();
		ImportSettingNative.jni_SetImportMode(this.getHandle(), ugcValue);
	}

	//	导出当前的设置为XML文件，用于保存导入的参数设置,方便用户持久化。
	//	XML的实现由组件层完成，具体的格式待定。
	@Deprecated
	public String toXML() {
		return "";
	}

	@Deprecated
	public boolean fromXML(String xml) {
		return false;
	}

	/**
	 * 获取导入数据信息集合
	 * @param targetNamePrefix 导入数据集名称的前缀，null和空字符串就不加前缀
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ImportDataInfos  getTargetDataInfos(String targetNamePrefix){
		// 判断一下许可和是否需要使用FME
		CheakFMELicense(); 
		
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getTargetDataInfos(String targetNamePrefix)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		ImportDataInfos dataInfos = null;
		long handle = ImportSettingNative.jni_GetTargetDataInfos(this
				.getHandle(), targetNamePrefix);
		if(handle != 0){
			if(this.m_dataType == DataType.VECTOR){
				dataInfos = new ImportDataInfos(handle, this.getSourceFileType(), DataType.VECTOR, this.getSourceFilePath(), "");
				if (this.getStates() == null) {
					this.setStates((ArrayList<Boolean>) dataInfos.getStates().clone());
				} else {
					dataInfos.setStates(this.getStates());
				}
			}
			else if(this.m_dataType == DataType.RASTER){
				dataInfos = new ImportDataInfos(handle, this.getSourceFileType(),
						DataType.RASTER, this.getSourceFilePath(), "");
				if (this.getStates() == null) {
					this.setStates((ArrayList<Boolean>) dataInfos.getStates()
							.clone());
				}
				else {
					dataInfos.setStates(this.getStates());
				}
			}
			else{
				m_dataInfos = new ImportDataInfos(this.getHandle(), this
						.getSourceFileType(), DataType.WOR, this
						.getSourceFilePath(), "");
				dataInfos = m_dataInfos;
			}
		}
		
		return dataInfos;
	}

	/**
	 * 获取导入数据信息集合
	 * @param targetNamePrefix 导入数据集名称的前缀，null和空字符串就不加前缀
	 * @param targetEncodeType 导入数据集的编码类型
	 * @param targetPrjCoordSy 导入数据集的目标坐标系
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  ImportDataInfos getTargetDataInfos(String targetNamePrefix,
			EncodeType targetEncodeType, PrjCoordSys targetPrjCoordSy){
		// 判断一下许可和是否需要使用FME
		CheakFMELicense();
		
		if (this.getHandle() == 0) {
			String message = InternalResource
					.loadString(
							"getTargetDataInfos(String targetNamePrefix,EncodeType targetEncodeType, PrjCoordSys targetPrjCoordSy)",
							InternalResource.HandleObjectHasBeenDisposed,
							InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (targetEncodeType == null) {
			String message = InternalResource.loadString("targetEncodeType",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (targetPrjCoordSy == null) {
			String message = InternalResource.loadString("targetPrjCoordSy",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long targetPrjCoordSyHandle = InternalHandle
				.getHandle(targetPrjCoordSy);
		if (targetPrjCoordSyHandle == 0) {
			String message = InternalResource.loadString("targetPrjCoordSy",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		ImportDataInfos dataInfos = null;
		int typeValue = targetEncodeType.value();
		long handle = ImportSettingNative.jni_GetTargetDataInfos2(this
				.getHandle(), targetNamePrefix, typeValue,
				targetPrjCoordSyHandle);
		if (handle != 0) {
			if(this.m_dataType == DataType.VECTOR){
				dataInfos = new ImportDataInfos(handle,this.getSourceFileType(), DataType.VECTOR, this.getSourceFilePath(), "");
				if (this.getStates() == null) {
					this.setStates((ArrayList<Boolean>) dataInfos.getStates().clone());
				}
				else {
					dataInfos.setStates((ArrayList<Boolean>) dataInfos.getStates().clone());
				}
			}
			else if(this.m_dataType == DataType.RASTER){
				dataInfos = new ImportDataInfos(handle, this.getSourceFileType(),
						DataType.RASTER, this.getSourceFilePath(), "");
				if (this.getStates() == null) {
					this.setStates((ArrayList<Boolean>) dataInfos.getStates()
							.clone());
				}
				else {
					dataInfos.setStates((ArrayList<Boolean>) dataInfos.getStates()
							.clone());
				}			
			}
			else{
				m_dataInfos = new ImportDataInfos(this.getHandle(), this
						.getSourceFileType(), DataType.WOR, this
						.getSourceFilePath(), "");
				dataInfos = m_dataInfos;
			}
		}
		
		return dataInfos;
	}

	/**
	 * 设置导入目标数据集信息
	 * @param targetDataInfos
	 */
	@SuppressWarnings("unchecked")
	public void setTargetDataInfos(ImportDataInfos targetDataInfos)
	{
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTargetDataInfos(ImportDataInfos targetDataInfos)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (targetDataInfos == null) {
			String message = InternalResource.loadString("targetDataInfos",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long targetDataInfosHandle = InternalHandle.getHandle(targetDataInfos);
		if (targetDataInfosHandle == 0) {
			String message = InternalResource.loadString("targetDataInfos",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if(this.m_dataType == DataType.VECTOR)
		{
			for (int i = 0; i < targetDataInfos.getCount(); i++) {
				ImportDataInfo dataInfo = targetDataInfos.get(i);
				ArrayList<Integer> states;
				
				if(this.getSourceFileType() == FileType.CSV)
				{
					ImportDataInfoCSV dataInfoCSV = (ImportDataInfoCSV)dataInfo;
					
					boolean bIsSetTargetFieldInfosbyUse = dataInfoCSV.GetTargetFieldInfosByUse();
					
					ImportSettingCSVNative.jni_SetIsTargetFieldInfosByUse(this.getHandle(),
							bIsSetTargetFieldInfosbyUse);
					//字段状态数组
					states = dataInfo.getFieldState();
					if(DatasetType.POINT == dataInfoCSV.getDatasetType() && !bIsSetTargetFieldInfosbyUse)
					{	
						states.add(0);  // 第一个字段为X坐标不导入
						states.add(1);  // 第一个字段为Y坐标不导入
					}
				}else	{
					//字段状态数组
					states = dataInfo.getFieldState();
				}
				
				// 修改字段是否导入的状态
				int[] fieldState = new int[states.size()];
				for (int j = 0; j < fieldState.length; j++) {
					fieldState[j] = states.get(j);
				}
	
				//修改名字的字段的数组
				HashMap<Integer, String> changeNames = dataInfo.getChangeName();
				Object[] keyArray = changeNames.keySet().toArray();
				int size = changeNames.size();
				int[] indexes = new int[size];
				String[] names = new String[size];
				for (int j = 0; j < changeNames.size(); j++) {
					indexes[j] = (Integer) keyArray[j];
					names[j] = (String) changeNames.get(indexes[j]);
				}
				
				//修改类型的字段的数组 目前仅CSV中需要
				HashMap<Integer, FieldType> changeFieldTypes = dataInfo.getChangeFieldType();
				keyArray = changeFieldTypes.keySet().toArray();
				size = changeFieldTypes.size();
				int[] indexeFieldTypes = new int[size];
				int[] fieldTypes = new int[size];
				for (int j = 0; j < size; j++) {
					indexeFieldTypes[j] = (Integer) keyArray[j];
					FieldType  fieldType= changeFieldTypes.get(indexeFieldTypes[j]);
					fieldTypes[j] = fieldType.value();
				}
	
				//交换字段起始索引的数组
				ArrayList<Integer> sourceIndex = dataInfo.getSourceIndex();
				int[] source = new int[sourceIndex.size()];
				for (int j = 0; j < source.length; j++) {
					source[j] = sourceIndex.get(j);
				}
	
				//交换字段目标索引的数组
				ArrayList<Integer> targetIndex = dataInfo.getTargetIndex();
				int[] target = new int[targetIndex.size()];
				for (int j = 0; j < target.length; j++) {
					target[j] = targetIndex.get(j);
				}

				ImportDataInfoNative.jni_SetTargetFieldInfos3(InternalHandle
						.getHandle(dataInfo), fieldState, indexes, names, indexeFieldTypes, fieldTypes,
						source, target);
			}
			ImportSettingNative.jni_SetTargetDataInfos(this.getHandle(),
					targetDataInfosHandle);
			this.setStates((ArrayList<Boolean>) targetDataInfos.getStates()
							.clone());
		}
		else if(this.m_dataType == DataType.RASTER)
		{
			ImportSettingNative.jni_SetTargetDataInfos(this.getHandle(),
					targetDataInfosHandle);
			this.setStates((ArrayList<Boolean>) targetDataInfos.getStates()
							.clone());
		}else{
			m_dataInfos = targetDataInfos;//wor 不支持设置
		}
	}

	/**
	 * 获取源数据的坐标系，目前只有TIF、SIT、IMG、SHP、MIF、TAB支持坐标,其他的为null
	 * @return
	 */
	public PrjCoordSys getSourcePrjCoordSys() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getSourcePrjCoordSys()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		//		return ImportSettingNative.jni_GetSourcePrjCoordSys(this.getHandle());
		return null;
	}

	protected void clearHandle() {
		if (m_prjCoordSys != null) {
			InternalPrjCoordSys.clearHandle(m_prjCoordSys);
			m_prjCoordSys = null;
		}
		this.m_datasourceConnectionInfo = null;
		setHandle(0);
	}

	/**
	 * 内部使用方法，拷贝数据源链接信息
	 * @param connectionInfo
	 * @return
	 */
	protected DatasourceConnectionInfo cloneDatasourceConnectionInfo(
			DatasourceConnectionInfo connectionInfo) {
		long handle = InternalHandle.getHandle(connectionInfo);
		long cloneHandle = ImportSettingNative
				.jni_CloneDatasourceConnectionInfo(handle);
		DatasourceConnectionInfo clone = InternalDatasourceConnectionInfo
				.createInstance(cloneHandle);
		InternalHandleDisposable.setIsDisposable(clone, true);
//		InternalHandleDisposable.makeSureNativeObjectLive(clone);
//		InternalHandleDisposable.makeSureNativeObjectLive(connectionInfo);
		return clone;
	}

	DataType getDataType() {
		return m_dataType;
	}

	/**
	 * 子类构造时必须调用该方法
	 * @param type
	 */
	void setDataType(DataType type) {
		this.m_dataType = type;
	}

	ArrayList<Boolean> getStates() {
		return m_states;
	}

	void setStates(ArrayList<Boolean> states) {
		this.m_states = states;
	}
	
	public boolean isUseFME()
	{	
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"isUseFME()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return ImportSettingNative.jni_GetIsUseFME(this.getHandle());
	}
	
	public void setUseFME(boolean value)
	{
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setUseFME()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		ImportSettingNative.jni_SetIsUseFME(this.getHandle(), value);
	}
	
	boolean hasFMEVecotrLicense() {	
		if(m_licFMEVecotrCode != 0)	{
			return false;
		}
		return true;
	}
	
	void verifyFMEVecotrLicense() {	
		if (m_licFMEVecotrCode != 0) {
//			String errorMessage = License.getErrorMessage(m_licFMEVecotrCode);
//			throw new IllegalStateException(errorMessage);
		}
	}
	
	boolean hasFMEEsriLicense() {	
		if(m_licFMEEsriCode != 0)	{
			return false;
		}
		return true;
	}
	
	void verifyFMEEsriLicense() {	
		if (m_licFMEEsriCode != 0) {
//			String errorMessage = License.getErrorMessage(m_licFMEEsriCode);
//			throw new IllegalStateException(errorMessage);
		}
	}
	
	boolean hasFMERasterLicense() {	
		if(m_licFMERasterCode != 0)	{
			return false;
		}
		return true;
	}
	
	void verifyFMERasterLicense() {	
		if (m_licFMERasterCode != 0) {
//			String errorMessage = License.getErrorMessage(m_licFMERasterCode);
//			throw new IllegalStateException(errorMessage);
		}
	}
	
	boolean hasFMEOtherLicense() {	
		if(m_licFMEOtherCode != 0)	{
			return false;
		}
		return true;
	}
	
	void verifyFMEOtherLicense() {	
		if (m_licFMEOtherCode != 0) {
//			String errorMessage = License.getErrorMessage(m_licFMEOtherCode);
//			throw new IllegalStateException(errorMessage);
		}
	}
	
	boolean hasLicenseBySpecialType(int fileType)
	{
		switch(fileType)
		{
		case 8:   // FileType.SHP
		{
			if(hasFMEVecotrLicense() || hasFMEEsriLicense()) {
				return true;
			}
		}
		break;
		case 161: // FileType.RAW
		{
			if(hasFMEEsriLicense() || hasFMERasterLicense()) {
				return true;
			}		
		}
		break;
		default:
		}
		return false;
	}
	
	boolean hasFMElicense()
	{ 
		int fileType = this.getSourceFileType().value();
		switch(fileType)
		{
		case 8:    // FileType.SHP
		case 161:  // FileType.RAW
			if(hasLicenseBySpecialType(fileType))
			{
				return true;
			}
			else
			{   
				String message = InternalResource.loadString("isUnFMESupported()",
						InternalResource.Fme_Lack_FmeLicense,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
		case 2:    // FileType.DWG
		case 3:    // FileType.DXF
		case 11:   // FileType.TAB
		case 12:   // FileType.MIF
		case 53:   // FileType.KML
		case 54:   // FileType.KMZ
			if(hasFMEVecotrLicense())
			{
				return true;
			}
			else
			{
			   String message = InternalResource.loadString("isUnFMESupported()", 
					   InternalResource.Fme_Lack_FmeLicense,
					   InternalResource.BundleName);
			   throw new IllegalStateException(message);		   
			}
//		case FileType.PNG:
//		case FileType.DXF:
//			bIsHasLicense = hasFMEEsriLicense();
//			break;
		case 121:  // FileType.BMP
		case 123:  // FileType.PNG
		case 103:  // FileType.TIF
		case 122:  // FileType.JPG
		case 124:  // FileType.GIF
		case 101:  // FileType.IMG
			if(hasFMERasterLicense())
			{
				return true;
			}
			else
			{
				 String message = InternalResource.loadString("isUnFMESupported()", 
					   InternalResource.Fme_Lack_FmeLicense,
					   InternalResource.BundleName);
			   throw new IllegalStateException(message);
				
			}
		
//		case :
//			bIsHasLicense = hasFMEOtherLicense();
//			break;
		case 51:   // FileType.GML
		case 16:   // FileType.DGN
			try{
				verifyFMEVecotrLicense();
				return true;
			}
			catch(IllegalStateException e)
			{
				 String message = InternalResource.loadString("isUnFMESupported()", 
						   InternalResource.Fme_Lack_FmeLicense,
						   InternalResource.BundleName);
				   throw new IllegalStateException(message);		
			}			 
		case 6:    // FileType.COVERAGE
		case 7:    // FileType.E00
		case 68:   // FileType.SDEVector
//	    目前的OEM许可中没有FileGDBRaster和SDERaster的许可 先关闭 deleted by jiangrs  2012/7/9
//		case 69:   // FileType.SDERaster
		case 70:   // FileType.GDBFVector
//		case 71:   // FileType.GDBFRaster
			try{
				verifyFMEEsriLicense();
				return true;
			}
			catch(IllegalStateException e)
			{
				 String message = InternalResource.loadString("isUnFMESupported()", 
						   InternalResource.Fme_Lack_FmeLicense,
						   InternalResource.BundleName);
				   throw new IllegalStateException(message);
			}
//		case :
//			verifyFMERasterLicense();
//			break;
//		case :
//			verifyFMEOtherLicense();
//			break;
		default:
			 String message = InternalResource.loadString("isUnFMESupported()", 
					   InternalResource.Fme_UnSupportedFileType,
					   InternalResource.BundleName);
			   throw new IllegalStateException(message);
		}
	}
	
	void CheakFMELicense()
	{  
		//是否有fme许可，没有许可抛出异常；
		//有fme许可，但不支持的类型抛出不支持异常；
		if(isUseFME())
		{
		  //fme 方式导入	
			 hasFMElicense();
		}
		else
		{    
		    //非fme方式导入
			isUnFMESupported();
		}
	}
	
	boolean isUnFMESupported()
	{
		 int filetype = this.getSourceFileType().value();
			switch (filetype)
			{
			case 40://FileType.ENCS57:
			case 142://FileType.GRD:
			case 143://FileType.CNDEM:
			case 144://FileType.USGSDEM:
			case 103://FileType.TIF:
			case 53://FileType.KML:
			case 122://jpg
			case 121://BMP:
			case 123://PNG:
			case 124://GIF:
			case 101://img
			case 54://KMZ:
			case 55://MAPGIS:
			case 17://LiDar:
			case 12://MIF:
			case 11://FileType.TAB:
			case 63://FileType.SCV:
			case 64://FileType.CSV:
			case 8://SHP:
			case 161://raw
			case 42://FileType.TEMSBuildingVector:
			case 146://FileType.TEMSClutter:
			case 41://FileType.TEMSVector:
			case 13://woR
			case 204://sit
			case 2: //dwg
			case 3: //dxf
			case 141: //bil
			case 148: //bip
			case 149: //bsq
			case 505: //ModelOSG
			case 145: //Arc/Info binary grid文件
		    return  true;
			default:
				String message = InternalResource.loadString("isUnFMESupported()",
						InternalResource.UnFme_UnSupportedFileType,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
			}
	}

}
