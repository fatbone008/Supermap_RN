package com.supermap.data;

import java.io.File;


public class DataConversion {

	public DataConversion(){
		
	}
	
	//�ַ������ͣ�Ĭ��ΪGB18030
	static Charset m_sCharset = Charset.GB18030;
	
	/**
	 * ����Ҫ����/�������ļ�ԭʼ�ַ������ͣ��Ǵ洢�ַ�����
	 * @param type
	 */
	public static void setConvertCharset(Charset charset){
		
		//�ַ���Ϊ��
		if(charset == null){
			String message = InternalResource.loadString("charset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		m_sCharset = charset;
	}
	
	public static boolean importSHP(String filepath,Datasource datasource) throws Exception{
	
		//·����ָ�����ļ��Ƿ����
		File file = new File(filepath); 
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//����Դ�Ƿ�Ϊ��
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//�Ƿ�Ϊshp��������
		String[] strArray = filepath.split("\\.");//ת���ַ�
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
		
		//����shp�������ö���,��������Դ����������·��
    	ImportSettingSHP importSShp = new ImportSettingSHP();
    	importSShp.setTargetDatasource(datasource);
    	importSShp.setSourceFilePath(filepath);
    	importSShp.setSourceFileCharset(m_sCharset);
    
    	//����DataImport�������ù����ĵ���Shp���ö���
    	DataImport dtImportShp = new DataImport();
    	ImportSettings importSettingsShp = dtImportShp.getImportSettings();
    	importSettingsShp.add(importSShp);
      	
      	//ִ��Shp���룬�õ�������
      	ImportResult importResultShp = dtImportShp.run();
      	int nsets = importResultShp.getSucceedSettings().length;
    	if(importResultShp.getSucceedSettings().length <= 0)
    		return false;
    	else
    		return true;
    	
	}
	
	public static boolean importMIF(String filepath,Datasource datasource) throws Exception{
		
		//·����ָ�����ļ��Ƿ����
		File file = new File(filepath); 
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//����Դ�Ƿ�Ϊ��
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//�Ƿ�Ϊmif��������
		String[] strArray = filepath.split("\\.");//ת���ַ�
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
		
        //����Mif�������ö���,��������Դ����������·��
		ImportSettingMIF importMif = new ImportSettingMIF();
		importMif.setTargetDatasource(datasource);
		importMif.setSourceFilePath(filepath);
		importMif.setSourceFileCharset(m_sCharset);
		
    	//����DataImport�������ù����ĵ���Mif���ö���
    	DataImport dtImportMif = new DataImport();
    	ImportSettings importSettingsMif = dtImportMif.getImportSettings();
    	importSettingsMif.add(importMif);
    		
        //ִ��Mif���룬�õ�������
      	ImportResult importResultMif = dtImportMif.run();
      	int nResultMif = importResultMif.getSucceedSettings().length;
    	if(nResultMif <= 0)
    		return false;
    	else
    		return true;
    	
	}
	
	public static boolean exportSHP(String filepath,Dataset dataset) throws Exception{
		
		//Ŀ¼�Ƿ����
		if (!isDirectoryExisted(filepath)) {
			String message = InternalResource.loadString(
					"filePath:" + filepath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		//���ݼ��Ƿ�Ϊ��	
		if (dataset == null) {
			String message = InternalResource.loadString("dataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//shp�ļ�·����Ч�Լ��
		String[] strArray = filepath.split("\\.");//ת���ַ�
		if(strArray.length != 2 ){
			
			//����ָ��Ŀ¼���ļ������ļ�·��
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

		//ֻ֧�ֵ������¼������͵����ݼ�
		if(dataset.getType() != DatasetType.POINT && dataset.getType() != DatasetType.LINE && dataset.getType() != DatasetType.REGION
				&& dataset.getType() != DatasetType.CAD && dataset.getType() != DatasetType.TEXT){
			String message = InternalResource.loadString("DatasetType Error",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//��������shp������,������ز���
    	ExportSetting exportSettingShp = new ExportSetting();
    	exportSettingShp.setSourceData(dataset);
    	exportSettingShp.setTargetFilePath(filepath);
    	exportSettingShp.setTargetFileType(FileType.SHP);
    	exportSettingShp.setOverwrite(true);
    	exportSettingShp.setTargetFileCharset(m_sCharset);
    	
		//����shp������
    	DataExport dtExportShp = new DataExport();
    	ExportSettings exportSettingsShp = dtExportShp.getExportSettings();
    	exportSettingsShp.add(exportSettingShp);

    	//Shp�������
    	ExportResult exportResultShp = dtExportShp.run();
    	int nResultShp = exportResultShp.getSucceedSettings().length;
    	if( nResultShp <= 0)
    		return false;
    	else
    		return true;
    	
	}

	public static boolean exportMIF(String filepath,Dataset dataset) throws Exception{
		
		//Ŀ¼�Ƿ����
		if (!isDirectoryExisted(filepath)) {
			String message = InternalResource.loadString(
					"filePath:" + filepath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		//���ݼ��Ƿ�Ϊ��	
		if (dataset == null) {
			String message = InternalResource.loadString("dataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//mif�ļ�·����Ч�Լ��
		String[] strArray = filepath.split("\\.");//ת���ַ�
		if(strArray.length != 2 ){
			
			//����ָ��Ŀ¼���ļ������ļ�·��
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

		//ֻ֧�ֵ������¼������͵����ݼ�
		if(dataset.getType() != DatasetType.POINT && dataset.getType() != DatasetType.LINE && dataset.getType() != DatasetType.REGION
				&& dataset.getType() != DatasetType.CAD && dataset.getType() != DatasetType.TEXT){
			String message = InternalResource.loadString("DatasetType Error",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
    	//��������mif������,������ز���
    	ExportSetting exportSettingMif = new ExportSetting();
    	exportSettingMif.setSourceData(dataset);
    	exportSettingMif.setTargetFilePath(filepath);
    	exportSettingMif.setTargetFileType(FileType.MIF);
    	exportSettingMif.setOverwrite(true);
    	exportSettingMif.setTargetFileCharset(m_sCharset);
    	
    	//����mif������
    	DataExport dtExportMif = new DataExport();
    	ExportSettings exportSettingsMif = dtExportMif.getExportSettings();
    	exportSettingsMif.add(exportSettingMif);
    	
    	//mif�������
    	ExportResult exportResultMif = dtExportMif.run();
    	int nResultMif = exportResultMif.getSucceedSettings().length;
    	if ( nResultMif <= 0)
    		return false;
    	else
    		return true;
		
	}
	
	
	//�ж���һ��Ŀ¼�Ƿ���ڡ�
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
	
	// !\param inverseBlackWhite �Ƿ�ʹ�úڰ׷�ɫģʽ
	// !\param importAsCAD �Ƿ���Ϊcad���ݼ�
	public static boolean importDWG(String filepath,Datasource datasource, boolean inverseBlackWhite, boolean importAsCAD)  throws Exception{
		
		//·����ָ�����ļ��Ƿ����
		File file = new File(filepath); 
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//����Դ�Ƿ�Ϊ��
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//�Ƿ�ΪDWG��������
		String[] strArray = filepath.split("\\.");//ת���ַ�
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
		
        //����dwg�������ö���,��������Դ����������·��
		ImportSettingDWG importDWG = new ImportSettingDWG();
		importDWG.setTargetDatasource(datasource);
		importDWG.setSourceFilePath(filepath);
		importDWG.setSourceFileCharset(m_sCharset);
		importDWG.setIsBlackWhiteInverse(inverseBlackWhite);
		importDWG.setImportingAsCAD(importAsCAD);
		
    	//����DataImport�������ù����ĵ���Mif���ö���
    	DataImport dtImportDWG = new DataImport();
    	ImportSettings importSettingsDWG = dtImportDWG.getImportSettings();
    	importSettingsDWG.add(importDWG);
    		
        //ִ��Mif���룬�õ�������
      	ImportResult importResultDWG = dtImportDWG.run();
      	int nResultDWG = importResultDWG.getSucceedSettings().length;
    	if(nResultDWG <= 0) {
       		return false;
    	}
     	else {
    		return true;
     	}
	}
	
	// !\param inverseBlackWhite �Ƿ�ڰ׷�ɫ
	// !\param importAsCAD �Ƿ���Ϊcad���ݼ�
	public static boolean importDXF(String filepath,Datasource datasource, boolean inverseBlackWhite, boolean importAsCAD) throws Exception{
		
		//·����ָ�����ļ��Ƿ����
		File file = new File(filepath); 
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//����Դ�Ƿ�Ϊ��
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//�Ƿ�Ϊmif��������
		String[] strArray = filepath.split("\\.");//ת���ַ�
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
		
        //����Mif�������ö���,��������Դ����������·��
		ImportSettingDXF importDXF = new ImportSettingDXF();
		importDXF.setTargetDatasource(datasource);
		importDXF.setSourceFilePath(filepath);
		importDXF.setSourceFileCharset(m_sCharset);
		importDXF.setIsBlackWhiteInverse(inverseBlackWhite);
		importDXF.setImportingAsCAD(importAsCAD);
		
    	//����DataImport�������ù����ĵ���Mif���ö���
    	DataImport dtImportDXF = new DataImport();
    	ImportSettings importSettingsDXF = dtImportDXF.getImportSettings();
    	importSettingsDXF.add(importDXF);
    		
        //ִ��Mif���룬�õ�������
      	ImportResult importResultDXF = dtImportDXF.run();
      	int nResultDXF = importResultDXF.getSucceedSettings().length;
    	if(nResultDXF <= 0) {
       		return false;
    	}
    	else {
    		return true;
    	}
    	
	}
	

	// !\param inverseBlackWhite �Ƿ�ڰ׷�ɫ
	public static boolean importDXF(String filepath,Datasource datasource, boolean inverseBlackWhite) throws Exception{
		return importDXF(filepath, datasource, inverseBlackWhite, true);
	}


	public static boolean exportDXF(String filepath,Dataset dataset) throws Exception{
		
		//Ŀ¼�Ƿ����
		if (!isDirectoryExisted(filepath)) {
			String message = InternalResource.loadString(
					"filePath:" + filepath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		//���ݼ��Ƿ�Ϊ��	
		if (dataset == null) {
			String message = InternalResource.loadString("dataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//DXF�ļ�·����Ч�Լ��
		String[] strArray = filepath.split("\\.");//ת���ַ�
		if(strArray.length != 2 ){
			
			//����ָ��Ŀ¼���ļ������ļ�·��
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

		//ֻ֧�ֵ������¼������͵����ݼ�
		if(dataset.getType() != DatasetType.POINT && dataset.getType() != DatasetType.LINE && dataset.getType() != DatasetType.REGION
				&& dataset.getType() != DatasetType.CAD && dataset.getType() != DatasetType.TEXT){
			String message = InternalResource.loadString("DatasetType Error",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//��������DXF������,������ز���
    	ExportSetting exportSettingDXF = new ExportSetting();
    	exportSettingDXF.setSourceData(dataset);
    	exportSettingDXF.setTargetFilePath(filepath);
    	exportSettingDXF.setTargetFileType(FileType.DXF);
    	exportSettingDXF.setOverwrite(true);
    	exportSettingDXF.setTargetFileCharset(m_sCharset);
    	
		//����DXF������
    	DataExport dtExportDXF = new DataExport();
    	ExportSettings exportSettingsDXF = dtExportDXF.getExportSettings();
    	exportSettingsDXF.add(exportSettingDXF);

    	//DXF�������
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
		
		//Ŀ¼�Ƿ����
		if (!isDirectoryExisted(filepath)) {
			String message = InternalResource.loadString(
					"filePath:" + filepath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		//���ݼ��Ƿ�Ϊ��	
		if (dataset == null) {
			String message = InternalResource.loadString("dataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//DWG�ļ�·����Ч�Լ��
		String[] strArray = filepath.split("\\.");//ת���ַ�
		if(strArray.length != 2 ){
			
			//����ָ��Ŀ¼���ļ������ļ�·��
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

		//ֻ֧�ֵ������¼������͵����ݼ�
		if(dataset.getType() != DatasetType.POINT && dataset.getType() != DatasetType.LINE && dataset.getType() != DatasetType.REGION
				&& dataset.getType() != DatasetType.CAD && dataset.getType() != DatasetType.TEXT){
			String message = InternalResource.loadString("DatasetType Error",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//��������DWG������,������ز���
    	ExportSetting exportSettingDWG = new ExportSetting();
    	exportSettingDWG.setSourceData(dataset);
    	exportSettingDWG.setTargetFilePath(filepath);
    	exportSettingDWG.setTargetFileType(FileType.DWG);
    	exportSettingDWG.setOverwrite(true);
    	exportSettingDWG.setTargetFileCharset(m_sCharset);
    	
		//����DWG������
    	DataExport dtExportDWG = new DataExport();
    	ExportSettings exportSettingsDWG = dtExportDWG.getExportSettings();
    	exportSettingsDWG.add(exportSettingDWG);

    	//DWG�������
    	ExportResult exportResultDWG = dtExportDWG.run();
    	int nResultDWG = exportResultDWG.getSucceedSettings().length;
    	if( nResultDWG <= 0) {
    		return false;
    	}
    	else {
    		return true;
    	}
    	
	}

	
	// ����tifӰ������Դ
	public static boolean importTIF(String filepath,Datasource datasource) throws Exception{
		return importTIF(filepath, datasource, false);
	}
	
	// bImportingAsGrid �Ƿ���Ϊդ�����ݼ�
	// wordFile ����ο��ļ�
	private static boolean importTIF(String filepath,Datasource datasource, boolean bImportingAsGrid/*, String wordFile*/) throws Exception{
		
		//·����ָ�����ļ��Ƿ����
		File file = new File(filepath); 
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//����Դ�Ƿ�Ϊ��
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//�Ƿ�ΪTif��������
		String[] strArray = filepath.split("\\.");//ת���ַ�
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
		
        //����tif�������ö���,��������Դ����������·��
		ImportSettingTIF importTIF = new ImportSettingTIF();
		importTIF.setTargetDatasource(datasource);
		importTIF.setSourceFilePath(filepath);
		importTIF.setSourceFileCharset(Charset.ANSI);
		
		if (bImportingAsGrid) {
			importTIF.setImportingAsGrid(true);
		}
		// ����ȫ������Ϊ�ನ�Σ�������ݱ���Ϊ�����Σ����ڲ���תΪ�����ε���
		importTIF.setMultiBandImportMode(MultiBandImportMode.MULTIBAND);
//		importTIF.setWorldFilePath(wordFile);
		importTIF.setTargetEncodeType(EncodeType.DCT); // Ĭ��Ϊdct���룬����ѹ�����ٶȽϿ�
		
    	//����DataImport�������ù����ĵ���tif���ö���
    	DataImport dtImportTIF = new DataImport();
    	ImportSettings importSettingsTIF = dtImportTIF.getImportSettings();
    	importSettingsTIF.add(importTIF);
    		
        //ִ��Mif���룬�õ�������
      	ImportResult importResultTIF = dtImportTIF.run();
      	int nResultTIF = importResultTIF.getSucceedSettings().length;
    	if(nResultTIF <= 0) {
       		return false;
    	} else {
    		return true;
    	}
    	
	}

	public static boolean exportTIF(String filepath,Dataset dataset) throws Exception{
		
		//Ŀ¼�Ƿ����
		if (!isDirectoryExisted(filepath)) {
			String message = InternalResource.loadString(
					"filePath:" + filepath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		//���ݼ��Ƿ�Ϊ��	
		if (dataset == null) {
			String message = InternalResource.loadString("dataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//shp�ļ�·����Ч�Լ��
		String[] strArray = filepath.split("\\.");//ת���ַ�
		if(strArray.length != 2 ){
			
			//����ָ��Ŀ¼���ļ������ļ�·��
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

		//ֻ֧�ֵ������¼������͵����ݼ�
		if(dataset.getType() != DatasetType.GRID && dataset.getType() != DatasetType.IMAGE){
			String message = InternalResource.loadString("DatasetType Error",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//��������TIF������,������ز���
    	ExportSetting exportSettingTIF = new ExportSetting();
    	exportSettingTIF.setSourceData(dataset);
    	exportSettingTIF.setTargetFilePath(filepath);
    	exportSettingTIF.setTargetFileType(FileType.TIF);
    	exportSettingTIF.setOverwrite(true);
    	exportSettingTIF.setTargetFileCharset(m_sCharset);
    	
		//����TIF������
    	DataExport dtExportTIF = new DataExport();
    	ExportSettings exportSettingsTIF = dtExportTIF.getExportSettings();
    	exportSettingsTIF.add(exportSettingTIF);

    	//TIF�������
    	ExportResult exportResultTIF = dtExportTIF.run();
    	int nResultTIF = exportResultTIF.getSucceedSettings().length;
     	if(nResultTIF <= 0) {
       		return false;
    	}
    	else {
    		return true;
    	}
	}

	// ����kml�ļ�
	// param filepath �����ļ�·����
	// param datasource Ŀ������Դ
	// param targetDatasetName Ŀ�����ݼ�����
	// param importAsCAD �Ƿ���Ϊ�������ݼ��� Ĭ�ϵ���Ϊ�������ݼ�
	public static boolean importKML(String filepath, Datasource datasource, String targetDatasetName, boolean importAsCAD) throws Exception{
		
		//·����ָ�����ļ��Ƿ����
		File file = new File(filepath); 
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//����Դ�Ƿ�Ϊ��
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//�Ƿ�Ϊkml��������
		String[] strArray = filepath.split("\\.");//ת���ַ�
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
		
		// Ŀ�����ݼ������Ƿ�Ϸ�
		if (targetDatasetName == null) {
			String message = InternalResource.loadString("targetDatasetName",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
        //����kml�������ö���,��������Դ����������·��
		ImportSettingKML importKML = new ImportSettingKML();
		importKML.setTargetDatasource(datasource);
		importKML.setSourceFilePath(filepath);
		importKML.setSourceFileCharset(Charset.ANSI);
		
		if (importAsCAD) {
			importKML.setImportingAsCAD(true);
		}
		
		// ���Ŀ������Դ���Ʋ�Ϊ�գ�������Ϊ�û��Զ������ݼ�����
		if (!targetDatasetName.isEmpty()) {
			importKML.setTargetDatasetName(targetDatasetName);
		}
		
    	//����DataImport�������ù����ĵ���KML���ö���
    	DataImport dtImportKML = new DataImport();
    	ImportSettings importSettingsKML = dtImportKML.getImportSettings();
    	importSettingsKML.add(importKML);
    		
        //ִ��KML���룬�õ�������
      	ImportResult importResultKML = dtImportKML.run();
      	int nResultKML = importResultKML.getSucceedSettings().length;
    	if(nResultKML <= 0) {
       		return false;
    	} else {
    		return true;
    	}
    	
	}

	// ����kml�ļ�
	// KMLֻ֧��ʸ�����ݼ����е���
	// ���ݼ�ͶӰ����Ϊ��������ϵWGS-1984
	// param filepath �����ļ�·����
	// param dataset Ŀ�����ݼ�
	public static boolean exportKML(String filepath, Dataset dataset) throws Exception{
		
		//Ŀ¼�Ƿ����
		if (!isDirectoryExisted(filepath)) {
			String message = InternalResource.loadString(
					"filePath:" + filepath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		//���ݼ��Ƿ�Ϊ��	
		if (dataset == null) {
			String message = InternalResource.loadString("dataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//KML�ļ�·����Ч�Լ��
		String[] strArray = filepath.split("\\.");//ת���ַ�
		if(strArray.length != 2 ){
			//����ָ��Ŀ¼���ļ������ļ�·��
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

		//ֻ֧�ֵ���ʸ�����ݼ�
		if(Dataset.isVector(dataset) == false){
			String message = InternalResource.loadString("DatasetType Error",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		// ֻ֧��wgs1984����ϵ�����ݼ�����
		if (dataset.getPrjCoordSys().getType() != PrjCoordSysType.PCS_EARTH_LONGITUDE_LATITUDE
				|| dataset.getPrjCoordSys().getGeoCoordSys().getType() != GeoCoordSysType.GCS_WGS_1984) {
			String message = InternalResource.loadString("dataset prcoordsys must be wgs1984!",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//��������KML������,������ز���
    	ExportSetting exportSettingKML = new ExportSetting();
    	exportSettingKML.setSourceData(dataset);
    	exportSettingKML.setTargetFilePath(filepath);
    	exportSettingKML.setTargetFileType(FileType.KML);
    	exportSettingKML.setOverwrite(true);
    	exportSettingKML.setTargetFileCharset(m_sCharset);
    	
		//����KML������
    	DataExport dtExportKML = new DataExport();
    	ExportSettings exportSettingsKML = dtExportKML.getExportSettings();
    	exportSettingsKML.add(exportSettingKML);

    	//KML�������
    	ExportResult exportResultKML = dtExportKML.run();
    	int nResultKML = exportResultKML.getSucceedSettings().length;
     	if(nResultKML <= 0) {
       		return false;
    	}
    	else {
    		return true;
    	}
	}

	// ����kmz�ļ�
	// param filepath �����ļ�·����
	// param datasource Ŀ������Դ
	// param targetDatasetName Ŀ�����ݼ�����
	// param importAsCAD �Ƿ���Ϊ�������ݼ��� Ĭ�ϵ���Ϊ�������ݼ�
	public static boolean importKMZ(String filepath, Datasource datasource, String targetDatasetName, boolean importAsCAD) throws Exception{
		
		//·����ָ�����ļ��Ƿ����
		File file = new File(filepath); 
		if (!file.exists()) {
			String message = InternalResource.loadString(file.getAbsolutePath(),
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//����Դ�Ƿ�Ϊ��
		if (datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//�Ƿ�Ϊkml��������
		String[] strArray = filepath.split("\\.");//ת���ַ�
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
		
		// Ŀ�����ݼ������Ƿ�Ϸ�
		if (targetDatasetName == null) {
			String message = InternalResource.loadString("targetDatasetName",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
        //����kmz�������ö���,��������Դ����������·��
		ImportSettingKMZ importKMZ = new ImportSettingKMZ();
		importKMZ.setTargetDatasource(datasource);
		importKMZ.setSourceFilePath(filepath);
		importKMZ.setSourceFileCharset(Charset.ANSI);
		
		if (importAsCAD) {
			importKMZ.setImportingAsCAD(true);
		}
		
		// ���Ŀ������Դ���Ʋ�Ϊ�գ�������Ϊ�û��Զ������ݼ�����
		if (!targetDatasetName.isEmpty()) {
			importKMZ.setTargetDatasetName(targetDatasetName);
		}
		
    	//����DataImport�������ù����ĵ���KMZ���ö���
    	DataImport dtImportKMZ = new DataImport();
    	ImportSettings importSettingsKMZ = dtImportKMZ.getImportSettings();
    	importSettingsKMZ.add(importKMZ);
    		
        //ִ��KMZ���룬�õ�������
      	ImportResult importResultKMZ = dtImportKMZ.run();
      	int nResultKMZ = importResultKMZ.getSucceedSettings().length;
    	if(nResultKMZ <= 0) {
       		return false;
    	} else {
    		return true;
    	}
    	
	}

	// ����kmz�ļ�
	// KMZֻ֧��ʸ�����ݼ����е���
	// ���ݼ�ͶӰ����Ϊ��������ϵWGS-1984
	// param filepath �����ļ�·����
	// param dataset Ŀ�����ݼ�
	public static boolean exportKMZ(String filepath, Dataset dataset) throws Exception{
		
		//Ŀ¼�Ƿ����
		if (!isDirectoryExisted(filepath)) {
			String message = InternalResource.loadString(
					"filePath:" + filepath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		//���ݼ��Ƿ�Ϊ��	
		if (dataset == null) {
			String message = InternalResource.loadString("dataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//KMZ�ļ�·����Ч�Լ��
		String[] strArray = filepath.split("\\.");//ת���ַ�
		if(strArray.length != 2 ){
			//����ָ��Ŀ¼���ļ������ļ�·��
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

		//ֻ֧�ֵ���ʸ�����ݼ�
		if(Dataset.isVector(dataset) == false){
			String message = InternalResource.loadString("DatasetType Error",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		// ֻ֧��wgs1984����ϵ�����ݼ�����
		if (dataset.getPrjCoordSys().getType() != PrjCoordSysType.PCS_EARTH_LONGITUDE_LATITUDE
				|| dataset.getPrjCoordSys().getGeoCoordSys().getType() != GeoCoordSysType.GCS_WGS_1984) {
			String message = InternalResource.loadString("dataset prcoordsys must be wgs1984!",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		//��������KMZ������,������ز���
    	ExportSetting exportSettingKMZ = new ExportSetting();
    	exportSettingKMZ.setSourceData(dataset);
    	exportSettingKMZ.setTargetFilePath(filepath);
    	exportSettingKMZ.setTargetFileType(FileType.KMZ);
    	exportSettingKMZ.setOverwrite(true);
    	exportSettingKMZ.setTargetFileCharset(m_sCharset);
    	
		//����KML������
    	DataExport dtExportKMZ = new DataExport();
    	ExportSettings exportSettingsKMZ = dtExportKMZ.getExportSettings();
    	exportSettingsKMZ.add(exportSettingKMZ);

    	//KML�������
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
