package com.supermap.data;

import java.io.File;


public class DataConversion {

	public DataConversion(){
		
	}
	
	//字符集类型，默认为GB18030
	static Charset m_sCharset = Charset.GB18030;
	
	/**
	 * 设置要导入/导出的文件原始字符集类型（非存储字符集）
	 * @param type
	 */
	public static void setConvertCharset(Charset charset){
		
		//字符集为空
		if(charset == null){
			String message = InternalResource.loadString("charset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		m_sCharset = charset;
	}
	
	public static boolean importSHP(String filepath,Datasource datasource) throws Exception{
	
		//路径所指定的文件是否存在
		File file = new File(filepath); 
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//数据源是否为空
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//是否为shp数据类型
		String[] strArray = filepath.split("\\.");//转义字符
		if(strArray.length != 2){
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathSplitNumNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		else{
			if(strArray[1].compareTo("shp") != 0){
				String message = InternalResource.loadString(file.getAbsolutePath(),
						InternalResource.GlobalPathSplitTypeNotValid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}
		
		//构建shp导入设置对象,设置数据源、导入数据路径
    	ImportSettingSHP importSShp = new ImportSettingSHP();
    	importSShp.setTargetDatasource(datasource);
    	importSShp.setSourceFilePath(filepath);
    	importSShp.setSourceFileCharset(m_sCharset);
    
    	//构建DataImport对象，设置构建的导入Shp设置对象
    	DataImport dtImportShp = new DataImport();
    	ImportSettings importSettingsShp = dtImportShp.getImportSettings();
    	importSettingsShp.add(importSShp);
      	
      	//执行Shp导入，得到导入结果
      	ImportResult importResultShp = dtImportShp.run();
      	int nsets = importResultShp.getSucceedSettings().length;
    	if(importResultShp.getSucceedSettings().length <= 0)
    		return false;
    	else
    		return true;
    	
	}
	
	public static boolean importMIF(String filepath,Datasource datasource) throws Exception{
		
		//路径所指定的文件是否存在
		File file = new File(filepath); 
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//数据源是否为空
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//是否为mif数据类型
		String[] strArray = filepath.split("\\.");//转义字符
		if(strArray.length != 2){
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathSplitNumNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		else{
			if(strArray[1].compareTo("mif") != 0){
				String message = InternalResource.loadString(file.getAbsolutePath(),
						InternalResource.GlobalPathSplitTypeNotValid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}
		
        //构建Mif导入设置对象,设置数据源、导入数据路径
		ImportSettingMIF importMif = new ImportSettingMIF();
		importMif.setTargetDatasource(datasource);
		importMif.setSourceFilePath(filepath);
		importMif.setSourceFileCharset(m_sCharset);
		
    	//构建DataImport对象，设置构建的导入Mif设置对象
    	DataImport dtImportMif = new DataImport();
    	ImportSettings importSettingsMif = dtImportMif.getImportSettings();
    	importSettingsMif.add(importMif);
    		
        //执行Mif导入，得到导入结果
      	ImportResult importResultMif = dtImportMif.run();
      	int nResultMif = importResultMif.getSucceedSettings().length;
    	if(nResultMif <= 0)
    		return false;
    	else
    		return true;
    	
	}
	
	public static boolean exportSHP(String filepath,Dataset dataset) throws Exception{
		
		//目录是否存在
		if (!isDirectoryExisted(filepath)) {
			String message = InternalResource.loadString(
					"filePath:" + filepath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		//数据集是否为空	
		if (dataset == null) {
			String message = InternalResource.loadString("dataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//shp文件路径有效性检查
		String[] strArray = filepath.split("\\.");//转义字符
		if(strArray.length != 2 ){
			
			//构造指定目录和文件名的文件路径
			String strDir = dataset.getName() + ".shp";
			strArray[0] += strDir;
			filepath = strArray[0];
		}
		else{
			if(strArray[1].compareTo("shp") != 0){
				String message = InternalResource.loadString(filepath,
						InternalResource.GlobalPathSplitTypeNotValid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}

		//只支持导出以下几种类型的数据集
		if(dataset.getType() != DatasetType.POINT && dataset.getType() != DatasetType.LINE && dataset.getType() != DatasetType.REGION
				&& dataset.getType() != DatasetType.CAD && dataset.getType() != DatasetType.TEXT){
			String message = InternalResource.loadString("DatasetType Error",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//创建导出shp参数类,设置相关参数
    	ExportSetting exportSettingShp = new ExportSetting();
    	exportSettingShp.setSourceData(dataset);
    	exportSettingShp.setTargetFilePath(filepath);
    	exportSettingShp.setTargetFileType(FileType.SHP);
    	exportSettingShp.setOverwrite(true);
    	exportSettingShp.setTargetFileCharset(m_sCharset);
    	
		//创建shp导出类
    	DataExport dtExportShp = new DataExport();
    	ExportSettings exportSettingsShp = dtExportShp.getExportSettings();
    	exportSettingsShp.add(exportSettingShp);

    	//Shp导出结果
    	ExportResult exportResultShp = dtExportShp.run();
    	int nResultShp = exportResultShp.getSucceedSettings().length;
    	if( nResultShp <= 0)
    		return false;
    	else
    		return true;
    	
	}

	public static boolean exportMIF(String filepath,Dataset dataset) throws Exception{
		
		//目录是否存在
		if (!isDirectoryExisted(filepath)) {
			String message = InternalResource.loadString(
					"filePath:" + filepath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		//数据集是否为空	
		if (dataset == null) {
			String message = InternalResource.loadString("dataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//mif文件路径有效性检查
		String[] strArray = filepath.split("\\.");//转义字符
		if(strArray.length != 2 ){
			
			//构造指定目录和文件名的文件路径
			String strDir = dataset.getName() + ".mif";
			strArray[0] += strDir;
			filepath = strArray[0];
		}
		else{
			if(strArray[1].compareTo("mif") != 0){
				String message = InternalResource.loadString(filepath,
						InternalResource.GlobalPathSplitTypeNotValid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}

		//只支持导出以下几种类型的数据集
		if(dataset.getType() != DatasetType.POINT && dataset.getType() != DatasetType.LINE && dataset.getType() != DatasetType.REGION
				&& dataset.getType() != DatasetType.CAD && dataset.getType() != DatasetType.TEXT){
			String message = InternalResource.loadString("DatasetType Error",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
    	//创建导出mif参数类,设置相关参数
    	ExportSetting exportSettingMif = new ExportSetting();
    	exportSettingMif.setSourceData(dataset);
    	exportSettingMif.setTargetFilePath(filepath);
    	exportSettingMif.setTargetFileType(FileType.MIF);
    	exportSettingMif.setOverwrite(true);
    	exportSettingMif.setTargetFileCharset(m_sCharset);
    	
    	//创建mif导出类
    	DataExport dtExportMif = new DataExport();
    	ExportSettings exportSettingsMif = dtExportMif.getExportSettings();
    	exportSettingsMif.add(exportSettingMif);
    	
    	//mif导出结果
    	ExportResult exportResultMif = dtExportMif.run();
    	int nResultMif = exportResultMif.getSucceedSettings().length;
    	if ( nResultMif <= 0)
    		return false;
    	else
    		return true;
		
	}
	
	
	//判断上一级目录是否存在。
	private static boolean isDirectoryExisted(String fileName) {
		File file = new File(fileName);
		boolean bResult = false;
		if (file.getParent() == null) {
			bResult = true;
		} else {
			File newFile = new File(file.getParent());
			if (newFile.exists()) {
				bResult = true;
			}
		}
		return bResult;
	}
	
	public static boolean importDWG(String filepath,Datasource datasource, boolean inverseBlackWhite) throws Exception{
		return importDWG(filepath, datasource, inverseBlackWhite, true);
	}
	
	// !\param inverseBlackWhite 是否使用黑白反色模式
	// !\param importAsCAD 是否导入为cad数据集
	public static boolean importDWG(String filepath,Datasource datasource, boolean inverseBlackWhite, boolean importAsCAD)  throws Exception{
		
		//路径所指定的文件是否存在
		File file = new File(filepath); 
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//数据源是否为空
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//是否为DWG数据类型
		String[] strArray = filepath.split("\\.");//转义字符
		if(strArray.length != 2){
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathSplitNumNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		else{
			if(strArray[1].compareToIgnoreCase("dwg") != 0){
				String message = InternalResource.loadString(file.getAbsolutePath(),
						InternalResource.GlobalPathSplitTypeNotValid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}
		
        //构建dwg导入设置对象,设置数据源、导入数据路径
		ImportSettingDWG importDWG = new ImportSettingDWG();
		importDWG.setTargetDatasource(datasource);
		importDWG.setSourceFilePath(filepath);
		importDWG.setSourceFileCharset(m_sCharset);
		importDWG.setIsBlackWhiteInverse(inverseBlackWhite);
		importDWG.setImportingAsCAD(importAsCAD);
		
    	//构建DataImport对象，设置构建的导入Mif设置对象
    	DataImport dtImportDWG = new DataImport();
    	ImportSettings importSettingsDWG = dtImportDWG.getImportSettings();
    	importSettingsDWG.add(importDWG);
    		
        //执行Mif导入，得到导入结果
      	ImportResult importResultDWG = dtImportDWG.run();
      	int nResultDWG = importResultDWG.getSucceedSettings().length;
    	if(nResultDWG <= 0) {
       		return false;
    	}
     	else {
    		return true;
     	}
	}
	
	// !\param inverseBlackWhite 是否黑白反色
	// !\param importAsCAD 是否导入为cad数据集
	public static boolean importDXF(String filepath,Datasource datasource, boolean inverseBlackWhite, boolean importAsCAD) throws Exception{
		
		//路径所指定的文件是否存在
		File file = new File(filepath); 
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//数据源是否为空
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//是否为mif数据类型
		String[] strArray = filepath.split("\\.");//转义字符
		if(strArray.length != 2){
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathSplitNumNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		else{
			if(strArray[1].compareToIgnoreCase("dxf") != 0){
				String message = InternalResource.loadString(file.getAbsolutePath(),
						InternalResource.GlobalPathSplitTypeNotValid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}
		
        //构建Mif导入设置对象,设置数据源、导入数据路径
		ImportSettingDXF importDXF = new ImportSettingDXF();
		importDXF.setTargetDatasource(datasource);
		importDXF.setSourceFilePath(filepath);
		importDXF.setSourceFileCharset(m_sCharset);
		importDXF.setIsBlackWhiteInverse(inverseBlackWhite);
		importDXF.setImportingAsCAD(importAsCAD);
		
    	//构建DataImport对象，设置构建的导入Mif设置对象
    	DataImport dtImportDXF = new DataImport();
    	ImportSettings importSettingsDXF = dtImportDXF.getImportSettings();
    	importSettingsDXF.add(importDXF);
    		
        //执行Mif导入，得到导入结果
      	ImportResult importResultDXF = dtImportDXF.run();
      	int nResultDXF = importResultDXF.getSucceedSettings().length;
    	if(nResultDXF <= 0) {
       		return false;
    	}
    	else {
    		return true;
    	}
    	
	}
	

	// !\param inverseBlackWhite 是否黑白反色
	public static boolean importDXF(String filepath,Datasource datasource, boolean inverseBlackWhite) throws Exception{
		return importDXF(filepath, datasource, inverseBlackWhite, true);
	}


	public static boolean exportDXF(String filepath,Dataset dataset) throws Exception{
		
		//目录是否存在
		if (!isDirectoryExisted(filepath)) {
			String message = InternalResource.loadString(
					"filePath:" + filepath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		//数据集是否为空	
		if (dataset == null) {
			String message = InternalResource.loadString("dataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//DXF文件路径有效性检查
		String[] strArray = filepath.split("\\.");//转义字符
		if(strArray.length != 2 ){
			
			//构造指定目录和文件名的文件路径
			String strDir = dataset.getName() + ".dxf";
			strArray[0] += strDir;
			filepath = strArray[0];
		}
		else{
			if(strArray[1].compareToIgnoreCase("dxf") != 0){
				String message = InternalResource.loadString(filepath,
						InternalResource.GlobalPathSplitTypeNotValid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}

		//只支持导出以下几种类型的数据集
		if(dataset.getType() != DatasetType.POINT && dataset.getType() != DatasetType.LINE && dataset.getType() != DatasetType.REGION
				&& dataset.getType() != DatasetType.CAD && dataset.getType() != DatasetType.TEXT){
			String message = InternalResource.loadString("DatasetType Error",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//创建导出DXF参数类,设置相关参数
    	ExportSetting exportSettingDXF = new ExportSetting();
    	exportSettingDXF.setSourceData(dataset);
    	exportSettingDXF.setTargetFilePath(filepath);
    	exportSettingDXF.setTargetFileType(FileType.DXF);
    	exportSettingDXF.setOverwrite(true);
    	exportSettingDXF.setTargetFileCharset(m_sCharset);
    	
		//创建DXF导出类
    	DataExport dtExportDXF = new DataExport();
    	ExportSettings exportSettingsDXF = dtExportDXF.getExportSettings();
    	exportSettingsDXF.add(exportSettingDXF);

    	//DXF导出结果
    	ExportResult exportResultDXF = dtExportDXF.run();
    	int nResultDXF = exportResultDXF.getSucceedSettings().length;
     	if(nResultDXF <= 0) {
       		return false;
    	}
    	else {
    		return true;
    	}
	}

	public static boolean exportDWG(String filepath,Dataset dataset) throws Exception{
		
		//目录是否存在
		if (!isDirectoryExisted(filepath)) {
			String message = InternalResource.loadString(
					"filePath:" + filepath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		//数据集是否为空	
		if (dataset == null) {
			String message = InternalResource.loadString("dataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//DWG文件路径有效性检查
		String[] strArray = filepath.split("\\.");//转义字符
		if(strArray.length != 2 ){
			
			//构造指定目录和文件名的文件路径
			String strDir = dataset.getName() + ".dwg";
			strArray[0] += strDir;
			filepath = strArray[0];
		}
		else{
			if(strArray[1].compareToIgnoreCase("dwg") != 0){
				String message = InternalResource.loadString(filepath,
						InternalResource.GlobalPathSplitTypeNotValid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}

		//只支持导出以下几种类型的数据集
		if(dataset.getType() != DatasetType.POINT && dataset.getType() != DatasetType.LINE && dataset.getType() != DatasetType.REGION
				&& dataset.getType() != DatasetType.CAD && dataset.getType() != DatasetType.TEXT){
			String message = InternalResource.loadString("DatasetType Error",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//创建导出DWG参数类,设置相关参数
    	ExportSetting exportSettingDWG = new ExportSetting();
    	exportSettingDWG.setSourceData(dataset);
    	exportSettingDWG.setTargetFilePath(filepath);
    	exportSettingDWG.setTargetFileType(FileType.DWG);
    	exportSettingDWG.setOverwrite(true);
    	exportSettingDWG.setTargetFileCharset(m_sCharset);
    	
		//创建DWG导出类
    	DataExport dtExportDWG = new DataExport();
    	ExportSettings exportSettingsDWG = dtExportDWG.getExportSettings();
    	exportSettingsDWG.add(exportSettingDWG);

    	//DWG导出结果
    	ExportResult exportResultDWG = dtExportDWG.run();
    	int nResultDWG = exportResultDWG.getSucceedSettings().length;
    	if( nResultDWG <= 0) {
    		return false;
    	}
    	else {
    		return true;
    	}
    	
	}

	
	// 导入tif影像到数据源
	public static boolean importTIF(String filepath,Datasource datasource) throws Exception{
		return importTIF(filepath, datasource, false);
	}
	
	// bImportingAsGrid 是否导入为栅格数据集
	// wordFile 坐标参考文件
	private static boolean importTIF(String filepath,Datasource datasource, boolean bImportingAsGrid/*, String wordFile*/) throws Exception{
		
		//路径所指定的文件是否存在
		File file = new File(filepath); 
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//数据源是否为空
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//是否为Tif数据类型
		String[] strArray = filepath.split("\\.");//转义字符
		if(strArray.length != 2){
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathSplitNumNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		} else {
			if((strArray[1].compareToIgnoreCase("tif") != 0)
				&&	(strArray[1].compareToIgnoreCase("tiff") != 0)) {
				String message = InternalResource.loadString(file.getAbsolutePath(),
						InternalResource.GlobalPathSplitTypeNotValid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}
		
        //构建tif导入设置对象,设置数据源、导入数据路径
		ImportSettingTIF importTIF = new ImportSettingTIF();
		importTIF.setTargetDatasource(datasource);
		importTIF.setSourceFilePath(filepath);
		importTIF.setSourceFileCharset(Charset.ANSI);
		
		if (bImportingAsGrid) {
			importTIF.setImportingAsGrid(true);
		}
		// 这里全部设置为多波段，如果数据本身为单波段，则内部会转为单波段导入
		importTIF.setMultiBandImportMode(MultiBandImportMode.MULTIBAND);
//		importTIF.setWorldFilePath(wordFile);
		importTIF.setTargetEncodeType(EncodeType.DCT); // 默认为dct编码，有损压缩，速度较快
		
    	//构建DataImport对象，设置构建的导入tif设置对象
    	DataImport dtImportTIF = new DataImport();
    	ImportSettings importSettingsTIF = dtImportTIF.getImportSettings();
    	importSettingsTIF.add(importTIF);
    		
        //执行Mif导入，得到导入结果
      	ImportResult importResultTIF = dtImportTIF.run();
      	int nResultTIF = importResultTIF.getSucceedSettings().length;
    	if(nResultTIF <= 0) {
       		return false;
    	} else {
    		return true;
    	}
    	
	}

	public static boolean exportTIF(String filepath,Dataset dataset) throws Exception{
		
		//目录是否存在
		if (!isDirectoryExisted(filepath)) {
			String message = InternalResource.loadString(
					"filePath:" + filepath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		//数据集是否为空	
		if (dataset == null) {
			String message = InternalResource.loadString("dataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//shp文件路径有效性检查
		String[] strArray = filepath.split("\\.");//转义字符
		if(strArray.length != 2 ){
			
			//构造指定目录和文件名的文件路径
			String strDir = dataset.getName() + ".tif";
			strArray[0] += strDir;
			filepath = strArray[0];
		}
		else{
			if(strArray[1].compareToIgnoreCase("tif") != 0
				&&	(strArray[1].compareToIgnoreCase("tiff") != 0)){
				String message = InternalResource.loadString(filepath,
						InternalResource.GlobalPathSplitTypeNotValid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}

		//只支持导出以下几种类型的数据集
		if(dataset.getType() != DatasetType.GRID && dataset.getType() != DatasetType.IMAGE){
			String message = InternalResource.loadString("DatasetType Error",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//创建导出TIF参数类,设置相关参数
    	ExportSetting exportSettingTIF = new ExportSetting();
    	exportSettingTIF.setSourceData(dataset);
    	exportSettingTIF.setTargetFilePath(filepath);
    	exportSettingTIF.setTargetFileType(FileType.TIF);
    	exportSettingTIF.setOverwrite(true);
    	exportSettingTIF.setTargetFileCharset(m_sCharset);
    	
		//创建TIF导出类
    	DataExport dtExportTIF = new DataExport();
    	ExportSettings exportSettingsTIF = dtExportTIF.getExportSettings();
    	exportSettingsTIF.add(exportSettingTIF);

    	//TIF导出结果
    	ExportResult exportResultTIF = dtExportTIF.run();
    	int nResultTIF = exportResultTIF.getSucceedSettings().length;
     	if(nResultTIF <= 0) {
       		return false;
    	}
    	else {
    		return true;
    	}
	}

	// 导入kml文件
	// param filepath 导入文件路径名
	// param datasource 目标数据源
	// param targetDatasetName 目标数据集名称
	// param importAsCAD 是否导入为复合数据集， 默认导入为复合数据集
	public static boolean importKML(String filepath, Datasource datasource, String targetDatasetName, boolean importAsCAD) throws Exception{
		
		//路径所指定的文件是否存在
		File file = new File(filepath); 
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//数据源是否为空
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//是否为kml数据类型
		String[] strArray = filepath.split("\\.");//转义字符
		if(strArray.length != 2){
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathSplitNumNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		} else {
			if((strArray[1].compareToIgnoreCase("kml") != 0)) {
				String message = InternalResource.loadString(file.getAbsolutePath(),
						InternalResource.GlobalPathSplitTypeNotValid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}
		
		// 目标数据集名称是否合法
		if (targetDatasetName == null) {
			String message = InternalResource.loadString("targetDatasetName",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
        //构建kml导入设置对象,设置数据源、导入数据路径
		ImportSettingKML importKML = new ImportSettingKML();
		importKML.setTargetDatasource(datasource);
		importKML.setSourceFilePath(filepath);
		importKML.setSourceFileCharset(Charset.ANSI);
		
		if (importAsCAD) {
			importKML.setImportingAsCAD(true);
		}
		
		// 如果目标数据源名称不为空，则设置为用户自定义数据集名称
		if (!targetDatasetName.isEmpty()) {
			importKML.setTargetDatasetName(targetDatasetName);
		}
		
    	//构建DataImport对象，设置构建的导入KML设置对象
    	DataImport dtImportKML = new DataImport();
    	ImportSettings importSettingsKML = dtImportKML.getImportSettings();
    	importSettingsKML.add(importKML);
    		
        //执行KML导入，得到导入结果
      	ImportResult importResultKML = dtImportKML.run();
      	int nResultKML = importResultKML.getSucceedSettings().length;
    	if(nResultKML <= 0) {
       		return false;
    	} else {
    		return true;
    	}
    	
	}

	// 导出kml文件
	// KML只支持矢量数据集进行导出
	// 数据集投影必须为地理坐标系WGS-1984
	// param filepath 导出文件路径名
	// param dataset 目标数据集
	public static boolean exportKML(String filepath, Dataset dataset) throws Exception{
		
		//目录是否存在
		if (!isDirectoryExisted(filepath)) {
			String message = InternalResource.loadString(
					"filePath:" + filepath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		//数据集是否为空	
		if (dataset == null) {
			String message = InternalResource.loadString("dataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//KML文件路径有效性检查
		String[] strArray = filepath.split("\\.");//转义字符
		if(strArray.length != 2 ){
			//构造指定目录和文件名的文件路径
			String strDir = dataset.getName() + ".kml";
			strArray[0] += strDir;
			filepath = strArray[0];
		}
		else{
			if(strArray[1].compareToIgnoreCase("kml") != 0){
				String message = InternalResource.loadString(filepath,
						InternalResource.GlobalPathSplitTypeNotValid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}

		//只支持导出矢量数据集
		if(Dataset.isVector(dataset) == false){
			String message = InternalResource.loadString("DatasetType Error",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		// 只支持wgs1984坐标系的数据集导出
		if (dataset.getPrjCoordSys().getType() != PrjCoordSysType.PCS_EARTH_LONGITUDE_LATITUDE
				|| dataset.getPrjCoordSys().getGeoCoordSys().getType() != GeoCoordSysType.GCS_WGS_1984) {
			String message = InternalResource.loadString("dataset prcoordsys must be wgs1984!",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//创建导出KML参数类,设置相关参数
    	ExportSetting exportSettingKML = new ExportSetting();
    	exportSettingKML.setSourceData(dataset);
    	exportSettingKML.setTargetFilePath(filepath);
    	exportSettingKML.setTargetFileType(FileType.KML);
    	exportSettingKML.setOverwrite(true);
    	exportSettingKML.setTargetFileCharset(m_sCharset);
    	
		//创建KML导出类
    	DataExport dtExportKML = new DataExport();
    	ExportSettings exportSettingsKML = dtExportKML.getExportSettings();
    	exportSettingsKML.add(exportSettingKML);

    	//KML导出结果
    	ExportResult exportResultKML = dtExportKML.run();
    	int nResultKML = exportResultKML.getSucceedSettings().length;
     	if(nResultKML <= 0) {
       		return false;
    	}
    	else {
    		return true;
    	}
	}

	// 导入kmz文件
	// param filepath 导入文件路径名
	// param datasource 目标数据源
	// param targetDatasetName 目标数据集名称
	// param importAsCAD 是否导入为复合数据集， 默认导入为复合数据集
	public static boolean importKMZ(String filepath, Datasource datasource, String targetDatasetName, boolean importAsCAD) throws Exception{
		
		//路径所指定的文件是否存在
		File file = new File(filepath); 
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//数据源是否为空
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//是否为kml数据类型
		String[] strArray = filepath.split("\\.");//转义字符
		if(strArray.length != 2){
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathSplitNumNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		} else {
			if((strArray[1].compareToIgnoreCase("kmz") != 0)) {
				String message = InternalResource.loadString(file.getAbsolutePath(),
						InternalResource.GlobalPathSplitTypeNotValid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}
		
		// 目标数据集名称是否合法
		if (targetDatasetName == null) {
			String message = InternalResource.loadString("targetDatasetName",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
        //构建kmz导入设置对象,设置数据源、导入数据路径
		ImportSettingKMZ importKMZ = new ImportSettingKMZ();
		importKMZ.setTargetDatasource(datasource);
		importKMZ.setSourceFilePath(filepath);
		importKMZ.setSourceFileCharset(Charset.ANSI);
		
		if (importAsCAD) {
			importKMZ.setImportingAsCAD(true);
		}
		
		// 如果目标数据源名称不为空，则设置为用户自定义数据集名称
		if (!targetDatasetName.isEmpty()) {
			importKMZ.setTargetDatasetName(targetDatasetName);
		}
		
    	//构建DataImport对象，设置构建的导入KMZ设置对象
    	DataImport dtImportKMZ = new DataImport();
    	ImportSettings importSettingsKMZ = dtImportKMZ.getImportSettings();
    	importSettingsKMZ.add(importKMZ);
    		
        //执行KMZ导入，得到导入结果
      	ImportResult importResultKMZ = dtImportKMZ.run();
      	int nResultKMZ = importResultKMZ.getSucceedSettings().length;
    	if(nResultKMZ <= 0) {
       		return false;
    	} else {
    		return true;
    	}
    	
	}

	// 导出kmz文件
	// KMZ只支持矢量数据集进行导出
	// 数据集投影必须为地理坐标系WGS-1984
	// param filepath 导出文件路径名
	// param dataset 目标数据集
	public static boolean exportKMZ(String filepath, Dataset dataset) throws Exception{
		
		//目录是否存在
		if (!isDirectoryExisted(filepath)) {
			String message = InternalResource.loadString(
					"filePath:" + filepath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		//数据集是否为空	
		if (dataset == null) {
			String message = InternalResource.loadString("dataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//KMZ文件路径有效性检查
		String[] strArray = filepath.split("\\.");//转义字符
		if(strArray.length != 2 ){
			//构造指定目录和文件名的文件路径
			String strDir = dataset.getName() + ".kmz";
			strArray[0] += strDir;
			filepath = strArray[0];
		}
		else{
			if(strArray[1].compareToIgnoreCase("kmz") != 0){
				String message = InternalResource.loadString(filepath,
						InternalResource.GlobalPathSplitTypeNotValid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}

		//只支持导出矢量数据集
		if(Dataset.isVector(dataset) == false){
			String message = InternalResource.loadString("DatasetType Error",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		// 只支持wgs1984坐标系的数据集导出
		if (dataset.getPrjCoordSys().getType() != PrjCoordSysType.PCS_EARTH_LONGITUDE_LATITUDE
				|| dataset.getPrjCoordSys().getGeoCoordSys().getType() != GeoCoordSysType.GCS_WGS_1984) {
			String message = InternalResource.loadString("dataset prcoordsys must be wgs1984!",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//创建导出KMZ参数类,设置相关参数
    	ExportSetting exportSettingKMZ = new ExportSetting();
    	exportSettingKMZ.setSourceData(dataset);
    	exportSettingKMZ.setTargetFilePath(filepath);
    	exportSettingKMZ.setTargetFileType(FileType.KMZ);
    	exportSettingKMZ.setOverwrite(true);
    	exportSettingKMZ.setTargetFileCharset(m_sCharset);
    	
		//创建KML导出类
    	DataExport dtExportKMZ = new DataExport();
    	ExportSettings exportSettingsKMZ = dtExportKMZ.getExportSettings();
    	exportSettingsKMZ.add(exportSettingKMZ);

    	//KML导出结果
    	ExportResult exportResultKMZ = dtExportKMZ.run();
    	int nResultKMZ = exportResultKMZ.getSucceedSettings().length;
     	if(nResultKMZ <= 0) {
       		return false;
    	}
    	else {
    		return true;
    	}
	}

}
