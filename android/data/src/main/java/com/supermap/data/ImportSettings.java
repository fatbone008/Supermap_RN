package com.supermap.data;

import java.util.ArrayList;

class ImportSettings {
	ArrayList<ImportSetting> m_settings;

	ImportSettings() {
		m_settings = new ArrayList<ImportSetting>();
	}

	public int getCount() {
		return m_settings.size();
	}

	public ImportSetting get(int index) {
		return m_settings.get(index);
	}

	//�ɹ�����������ʧ�ܷ���-1
	public int add(ImportSetting importSetting) {
		//TODO clone
		int result = -1;
		checkInvalidSetting(importSetting);
		if (m_settings.add(importSetting)) {
			result = m_settings.size() - 1;
		}
//		InternalHandleDisposable.makeSureNativeObjectLive(importSetting);
		return result;

	}

	public boolean insert(int index, ImportSetting importSetting) {
		//TODO clone
		int count = m_settings.size();
		m_settings.add(index, importSetting);
//		InternalHandleDisposable.makeSureNativeObjectLive(importSetting);
		return count + 1 == m_settings.size();		
	}

	public boolean remove(int index) {
		int count = m_settings.size();
		m_settings.remove(index);
		return count - 1 == m_settings.size();
	}

	public void clear() {
		m_settings.clear();
	}
	
	//Added By HELH �жϵ�ǰ�Ƿ���IMG��TIF����Ϊ�ನ��GRIDӰ�����ΪTrue
	//��������Ϊ�����ε��룬��Ϊ�����ʱ�����ṩGRID�ನ�����ݼ�
	private void checkInvalidSetting(ImportSetting importSetting){
//		if(importSetting instanceof ImportSettingIMG){
//			ImportSettingIMG importSettingIMG = (ImportSettingIMG)importSetting;
//			if(importSettingIMG.isImportingAsGrid() && importSettingIMG.getMultiBandImportMode().equals(MultiBandImportMode.MULTIBAND)){
//				String message = InternalResource.loadString("importSettingIMG",
//						InternalResource.ImportSetting_GridCannotSuportMultiBandImportMode,
//						InternalResource.BundleName);
//				throw new IllegalArgumentException(message);
//			}
//		}
		if(importSetting instanceof ImportSettingTIF){
			ImportSettingTIF importSettingTIF = (ImportSettingTIF)importSetting;
			if(importSettingTIF.isImportingAsGrid() && importSettingTIF.getMultiBandImportMode().equals(MultiBandImportMode.MULTIBAND)){
				String message = InternalResource.loadString("importSettingTIF",
						InternalResource.ImportSetting_GridCannotSuportMultiBandImportMode,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}
//		if(importSetting instanceof ImportSettingBMP){
//			ImportSettingBMP importSettingBMP = (ImportSettingBMP)importSetting;
//			if(importSettingBMP.isImportingAsGrid() && importSettingBMP.getMultiBandImportMode().equals(MultiBandImportMode.MULTIBAND)){
//				String message = InternalResource.loadString("importSettingBMP",
//						InternalResource.ImportSetting_GridCannotSuportMultiBandImportMode,
//						InternalResource.BundleName);
//				throw new IllegalArgumentException(message);
//			}
//		}
//		if(importSetting instanceof ImportSettingPNG){
//			ImportSettingPNG importSettingPNG = (ImportSettingPNG)importSetting;
//			if(importSettingPNG.isImportingAsGrid() && importSettingPNG.getMultiBandImportMode().equals(MultiBandImportMode.MULTIBAND)){
//				String message = InternalResource.loadString("importSettingPNG",
//						InternalResource.ImportSetting_GridCannotSuportMultiBandImportMode,
//						InternalResource.BundleName);
//				throw new IllegalArgumentException(message);
//			}
//		}
//		if(importSetting instanceof ImportSettingJPG){
//			ImportSettingJPG importSettingJPG = (ImportSettingJPG)importSetting;
//			if(importSettingJPG.isImportingAsGrid() && importSettingJPG.getMultiBandImportMode().equals(MultiBandImportMode.MULTIBAND)){
//				String message = InternalResource.loadString("importSettingJPG",
//						InternalResource.ImportSetting_GridCannotSuportMultiBandImportMode,
//						InternalResource.BundleName);
//				throw new IllegalArgumentException(message);
//			}
//		}
////		if(importSetting instanceof ImportSettingFileGDBRaster){
////			ImportSettingFileGDBRaster importSettingGDBRaster = (ImportSettingFileGDBRaster)importSetting;
////			if(importSettingGDBRaster.isImportingAsGrid() && importSettingGDBRaster.getMultiBandImportMode().equals(MultiBandImportMode.MULTIBAND)){
////				String message = InternalResource.loadString("importSettingGDBRaster",
////						InternalResource.ImportSetting_GridCannotSuportMultiBandImportMode,
////						InternalResource.BundleName);
////				throw new IllegalArgumentException(message);
////			}
////		}
////		if(importSetting instanceof ImportSettingSDERaster){
////			ImportSettingSDERaster importSettingSDERaster = (ImportSettingSDERaster)importSetting;
////			if(importSettingSDERaster.isImportingAsGrid() && importSettingSDERaster.getMultiBandImportMode().equals(MultiBandImportMode.MULTIBAND)){
////				String message = InternalResource.loadString("importSettingSDERaster",
////						InternalResource.ImportSetting_GridCannotSuportMultiBandImportMode,
////						InternalResource.BundleName);
////				throw new IllegalArgumentException(message);
////			}
////		}
//		InternalHandleDisposable.makeSureNativeObjectLive(importSetting);
	}
}
