package com.supermap.data;

import java.util.ArrayList;

class ImportDataInfos extends InternalHandle {

	private ArrayList<ImportDataInfo> m_importDataInfos;

	private ArrayList<Boolean> m_states;

	private DataType m_DataType;

	/**
	 * 内部使用构造函数
	 * 
	 * @param handle
	 */
	ImportDataInfos(long handle, FileType fileType, DataType dataType,
			String filePath, String targetName) {
		m_importDataInfos = new ArrayList<ImportDataInfo>();
		m_states = new ArrayList<Boolean>();
		this.m_DataType = dataType;
		this.setHandle(handle);

		// dataType为WOR类型时传 setting的指针
		if (m_DataType.equals(DataType.WOR)) {
			String[] datasetNames = ImportSettingWORNative
					.jni_GetDataInfosDatasetNames(this.getHandle());
			String[] mapNames = ImportSettingWORNative
					.jni_GetDataInfosMapNames(this.getHandle());

			ImportDataInfoWOR wor = new ImportDataInfoWOR(datasetNames,
					mapNames, filePath);
			m_importDataInfos.add(wor);
			m_states.add(false);
		} 
		else if (m_DataType.equals(DataType.VECTOR)) {
			int count = ImportDataInfosNative.jni_GetCount(handle);
			for (int i = 0; i < count; i++) {
				long dataInfoHandle = ImportDataInfosNative.jni_GetItem(handle,
						i);
				if (dataInfoHandle != 0) {
					ImportDataInfo dataInfo = createInstance(dataInfoHandle,
							filePath, fileType, targetName);
					m_importDataInfos.add(dataInfo);
					m_states.add(false);
				}
			}
		} 
		else {
			int count = ImportDataInfosNative.jni_GetRasterCount(handle);
			for(int i = 0;i<count;i++){
				long dataInfoHandle = ImportDataInfosNative.jni_GetRasterItem(handle, i);
				if(dataInfoHandle != 0){
					ImportDataInfo dataInfo = createInstance(dataInfoHandle, filePath,
							fileType, targetName);
					m_importDataInfos.add(dataInfo);
					m_states.add(false);
				}
			}
		}
	}

	/**
	 * 返回给定的导入设置信息集合中元素的总数
	 * 
	 * @return
	 */
	public int getCount() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getCount()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return m_importDataInfos.size();
	}

	/**
	 * 根据Index获取导入设置信息集合对象中的导入设置信息对象
	 * 
	 * @param index
	 * @return
	 */
	public ImportDataInfo get(int index) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"get(enclosing_method_argumenttypes_and_arguments)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (index < 0 || index >= this.getCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		return m_importDataInfos.get(index);
	}

	/**
	 * 用于从导入设置信息集合移除指定的索引处的项
	 * 
	 * @param index
	 * @param excludeDataInfo
	 */
	public void setImportState(int index, boolean excludeDataInfo) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setImportState(int index, boolean excludeDataInfo)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (!m_DataType.equals(DataType.WOR)) {
			m_states.set(index, excludeDataInfo);
		}

	}

	public boolean getImportState(int index) {
		return m_states.get(index);

	}

	ArrayList<Boolean> getStates() {
		return this.m_states;
	}

	void setStates(ArrayList<Boolean> states) {
		this.m_states = (ArrayList<Boolean>) states.clone();
	}

	protected void clearHandle() {
		int size = m_importDataInfos.size();
		if (!m_DataType.equals(DataType.WOR)) {
			for (int i = 0; i < size; i++) {
				ImportDataInfo dataInfo = m_importDataInfos.get(i);
				dataInfo.clearHandle();
			}
		}
		m_importDataInfos.clear();
		m_importDataInfos = null;
	}

	protected void finalize() {		if (this.getHandle() != 0) {
			if (m_DataType.equals(DataType.VECTOR)) {
				ImportDataInfosNative.jni_Delete(this.getHandle());
			} 
			else if (m_DataType.equals(DataType.RASTER)) {
				ImportDataInfosNative.jni_Delete2(this.getHandle());
			}
		}
	}

	private ImportDataInfo createInstance(long handle, String filePath,
			FileType fileType, String targetName) {
		ImportDataInfo dataInfo = null;
		if (fileType.equals(FileType.MIF)) {
			dataInfo = new ImportDataInfoMIF(handle, filePath, fileType,
					targetName);
		} else if (fileType.equals(FileType.TAB)) {
//			dataInfo = new ImportDataInfoTAB(handle, filePath, fileType,
//					targetName);
		} else if (fileType.equals(FileType.PNG)) {
//			dataInfo = new ImportDataInfoPNG(handle, filePath, fileType,
//					targetName);
		} else if (fileType.equals(FileType.TIF)) {
//			dataInfo = new ImportDataInfoTIF(handle, filePath, fileType,
//					targetName);
		} else if (fileType.equals(FileType.IMG)) {
//			dataInfo = new ImportDataInfoIMG(handle, filePath, fileType,
//					targetName);
		} else if (fileType.equals(FileType.GRD)) {
//			dataInfo = new ImportDataInfoGRD(handle, filePath, fileType,
//					targetName);
		} else if (fileType.equals(FileType.SHP)) {
			dataInfo = new ImportDataInfoSHP(handle, filePath, fileType,
					targetName);
		} else if(fileType.equals(FileType.COVERAGE)){
//			dataInfo = new ImportDataInfoCoverage(handle, filePath, fileType,
//					targetName);
		}else if (fileType.equals(FileType.DWG)) {
//			dataInfo = new ImportDataInfoDWG(handle, filePath, fileType,
//					targetName);
		} else if (fileType.equals(FileType.DXF)) {
//			dataInfo = new ImportDataInfoDXF(handle, filePath, fileType,
//					targetName);
		} else if (fileType.equals(FileType.KML)) {
//			dataInfo = new ImportDataInfoKML(handle, filePath, fileType,
//					targetName);
		} else if (fileType.equals(FileType.KMZ)) {
//			dataInfo = new ImportDataInfoKMZ(handle, filePath, fileType,
//					targetName);	
		} else if (fileType.equals(FileType.MAPGIS)) {
//			dataInfo = new ImportDataInfoMAPGIS(handle, filePath, fileType,
//					targetName);			
		} else if (fileType.equals(FileType.GIF)) {
//			dataInfo = new ImportDataInfoGIF(handle, filePath, fileType,
//					targetName);
		} else if (fileType.equals(FileType.JPG)) {
//			dataInfo = new ImportDataInfoJPG(handle, filePath, fileType,
//					targetName);
		} else if (fileType.equals(FileType.RAW)) {
//			dataInfo = new ImportDataInfoRAW(handle, filePath, fileType,
//					targetName);
		} else if (fileType.equals(FileType.SIT)) {
//			dataInfo = new ImportDataInfoSIT(handle, filePath, fileType,
//					targetName);
		} else if (fileType.equals(FileType.BMP)) {
//			dataInfo = new ImportDataInfoBMP(handle, filePath, fileType,
//					targetName);
		} else if (fileType.equals(FileType.TEMSBuildingVector)){
//			dataInfo = new ImportDataInfoTEMSBuildingVector(handle ,filePath ,fileType,
//					targetName);		
		} else if (fileType.equals(FileType.TEMSVector)){
//			dataInfo = new ImportDataInfoTEMSVector(handle ,filePath ,fileType,
//					targetName);
		} else if (fileType.equals(FileType.TEMSClutter)){
//			dataInfo = new ImportDataInfoTEMSClutter(handle ,filePath ,fileType,
//					targetName);
		} 
//			else if (fileType.equals(FileType.ENCS57)){
//			dataInfo = new ImportDataInfoENCS57(handle ,filePath ,fileType ,
//					targetName);
//		}
		else if (fileType.equals(FileType.CSV)){
			dataInfo = new ImportDataInfoCSV(handle ,filePath ,fileType ,
					targetName); 
		}else if (fileType.equals(FileType.SCV)){
//			dataInfo = new ImportDataInfoSCV(handle ,filePath ,fileType ,
//					targetName); 
		} else if(fileType.equals(FileType.GML)){
//			dataInfo = new ImportDataInfoGML(handle ,filePath ,fileType ,
//					targetName);
		}
		else if(fileType.equals(FileType.E00)) {
//			dataInfo = new ImportDataInfoE00(handle ,filePath ,fileType ,
//					targetName); 		
		}
		else if(fileType.equals(FileType.SDEVector)) {
//			dataInfo = new ImportDataInfoSDEVector(handle ,filePath ,fileType ,
//					targetName); 		
		}
//		else if(fileType.equals(FileType.SDERaster)){
//			dataInfo = new ImportDataInfoSDERaster(handle ,filePath ,fileType ,
//					targetName); 
//		}
		else if(fileType.equals(FileType.FileGDBVector)) {
//			dataInfo = new ImportDataInfoFileGDBVector(handle ,filePath ,fileType ,
//					targetName); 		
		}
//		else if(fileType.equals(FileType.FileGDBRaster)){
//			dataInfo = new ImportDataInfoFileGDBRaster(handle ,filePath ,fileType ,
//					targetName); 	
//		}
		else if(fileType.equals(FileType.DGN)){
//			dataInfo = new ImportDataInfoDGN(handle ,filePath ,fileType ,
//					targetName); 	
		}
		else if(fileType.equals(FileType.USGSDEM)){
//			dataInfo = new ImportDataInfoUSGSDEM(handle ,filePath ,fileType ,
//					targetName); 	
		}
		else if(fileType.equals(FileType.GBDEM)){
//			dataInfo = new ImportDataInfoGBDEM(handle ,filePath ,fileType ,
//					targetName); 	
		}
		else if(fileType.equals(FileType.BIL))
		{
//			dataInfo = new ImportDataInfoBIL(handle,filePath,fileType,targetName);
		}
		else if (fileType.equals(FileType.BIP))
		{
//			dataInfo = new ImportDataInfoBIP(handle,filePath,fileType,targetName);
		}
		else if(fileType.equals(FileType.BSQ))
		{
//			dataInfo = new ImportDataInfoBSQ(handle, filePath,fileType,targetName);
		}
		else if(fileType.equals(FileType.LIDAR)){
//			dataInfo = new ImportDataInfoLIDAR(handle ,filePath ,fileType ,
//					targetName); 	
		}
		else if(fileType.equals(FileType.ARCINFO_BINGRID)){
//			dataInfo = new ImportDataInfoAiBinGrid(handle, filePath, fileType, targetName);
		}
		else {
			throw new RuntimeException("$$$相关类型还未实现");
		}
		return dataInfo;
	}
}
