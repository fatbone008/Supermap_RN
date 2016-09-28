package com.supermap.data;

import java.io.File;

import com.supermap.data.Dataset;
import com.supermap.data.Charset;
class ExportSetting {

	String m_targetFilePath;

	boolean m_overwrite;

	FileType m_type;

	Object m_sourceData;
	Charset m_targetCharset;

	public ExportSetting() {
		//		long handle = ExportSettingNative.jni_New();
		//		this.setHandle(handle, true);
		m_targetFilePath = "";

		m_overwrite = false;

		m_type = FileType.NONE;

		m_sourceData = null;
		m_targetCharset = Charset.DEFAULT;
	}

	public ExportSetting(ExportSetting exportSetting) {
		if (exportSetting == null) {
			String message = InternalResource.loadString("exportSetting",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		//		long handle = InternalHandleDisposable.getHandle(exportSetting);
		//		if (handle == 0) {
		//			String message = InternalResource.loadString("exportSetting",
		//					InternalResource.HandleObjectHasBeenDisposed,
		//					InternalResource.BundleName);
		//			throw new IllegalArgumentException(message);
		//		}
		//		long cloneHandle = ExportSettingNative.jni_Clone(handle);
		//		setHandle(cloneHandle, true);
		//�Ժ��ΪFromXML��ToXML
		this.setSourceData(exportSetting.getSourceData());
		this.setTargetFilePath(exportSetting.getTargetFilePath());
		this.setTargetFileType(exportSetting.getTargetFileType());
		this.setOverwrite(exportSetting.isOverwrite());
		this.setTargetFileCharset(exportSetting.getTargetFileCharset());
	}

	public ExportSetting(Object sourceData, String targetFilePath,
			FileType targetFileType) {
		this();
		this.setSourceData(sourceData);
		this.setTargetFilePath(targetFilePath);
		this.setTargetFileType(targetFileType);
		this.setTargetFileCharset(Charset.DEFAULT);

	}

	//	���û��ȡ������Ŀ���ļ���·����Ϣ��
	//	���԰�������Ŀ���ļ��ĺ�׺����Ҳ���Բ�������׺��
	public String getTargetFilePath() {
		//		if (this.getHandle() == 0) {
		//			String message = InternalResource.loadString("getTargetFilePath()",
		//					InternalResource.HandleObjectHasBeenDisposed,
		//					InternalResource.BundleName);
		//			throw new IllegalStateException(message);
		//		}
		//		return ExportSettingNative.jni_GetTargetFilePath(getHandle());
		return m_targetFilePath;
	}

	public void setTargetFilePath(String filePath) {
		//		if (this.getHandle() == 0) {
		//			String message = InternalResource.loadString(
		//					"setTargetFilePath(String filePath)",
		//					InternalResource.HandleObjectHasBeenDisposed,
		//					InternalResource.BundleName);
		//			throw new IllegalStateException(message);
		//		}
		if (!isDirectoryExisted(filePath)) {
			String message = InternalResource.loadString(
					"filePath:" + filePath,
					InternalResource.GlobalPathIsNotValid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		//		ExportSettingNative.jni_SetTargetFilePath(getHandle(), filePath);
		m_targetFilePath = filePath;
	}

	//	���û��ȡ����Ŀ¼�д���ͬ���ļ�ʱ���Ƿ�ǿ�Ƹ��ǡ�Ĭ��Ϊfalse������ִ�е�������������ǿ�Ƹ��ơ�
	public boolean isOverwrite() {
		//		if (this.getHandle() == 0) {
		//			String message = InternalResource.loadString("isOverwrite",
		//					InternalResource.HandleObjectHasBeenDisposed,
		//					InternalResource.BundleName);
		//			throw new IllegalStateException(message);
		//		}
		//		return ExportSettingNative.jni_IsOverwrite(getHandle());
		return m_overwrite;
	}

	public void setOverwrite(boolean value) {
		//		if (this.getHandle() == 0) {
		//			String message = InternalResource.loadString("setOverwrite",
		//					InternalResource.HandleObjectHasBeenDisposed,
		//					InternalResource.BundleName);
		//			throw new IllegalStateException(message);
		//		}
		//		ExportSettingNative.jni_SetOverwrite(getHandle(), value);
		m_overwrite = value;
	}

	//	WOR��MapInfo �����ռ��ļ���, RAW(raw�ļ�)���������ļ���֧�ֵ���
	public FileType getTargetFileType() {
		//		if (this.getHandle() == 0) {
		//			String message = InternalResource.loadString("getTargetFileType()",
		//					InternalResource.HandleObjectHasBeenDisposed,
		//					InternalResource.BundleName);
		//			throw new IllegalStateException(message);
		//		}
		//		int value = ExportSettingNative.jni_GetTargetFileType(getHandle());
		//		FileType type = (FileType) FileType.parse(FileType.class, value);
		return m_type;
	}

	public void setTargetFileType(FileType type) {
		//		if (this.getHandle() == 0) {
		//			String message = InternalResource.loadString(
		//					"setTargetFileType(QFileType; type)",
		//					InternalResource.HandleObjectHasBeenDisposed,
		//					InternalResource.BundleName);
		//			throw new IllegalStateException(message);
		//		}
		//
		//		ExportSettingNative.jni_SetTargetFileType(getHandle(), type.value());
		m_type = type;
	}
	
	//	WOR��MapInfo �����ռ��ļ���, RAW(raw�ļ�)���������ļ���֧�ֵ���
	public Charset getTargetFileCharset() {
		//		if (this.getHandle() == 0) {
		//			String message = InternalResource.loadString("getTargetFileType()",
		//					InternalResource.HandleObjectHasBeenDisposed,
		//					InternalResource.BundleName);
		//			throw new IllegalStateException(message);
		//		}
		//		int value = ExportSettingNative.jni_GetTargetFileType(getHandle());
		//		FileType type = (FileType) FileType.parse(FileType.class, value);
		return m_targetCharset;
	}

	public void setTargetFileCharset(Charset charset) {
		//		if (this.getHandle() == 0) {
		//			String message = InternalResource.loadString(
		//					"setTargetFileType(QFileType; type)",
		//					InternalResource.HandleObjectHasBeenDisposed,
		//					InternalResource.BundleName);
		//			throw new IllegalStateException(message);
		//		}
		//
		//		ExportSettingNative.jni_SetTargetFileType(getHandle(), type.value());
		m_targetCharset = charset;
	}

	//���û��ȡ��Ҫ���������ݼ���
	public Object getSourceData() {
		return m_sourceData;
	}

	public void setSourceData(Object value) {
		//		if (this.getHandle() == 0) {
		//			String message = InternalResource.loadString(
		//					"setSourceData(Object value)",
		//					InternalResource.HandleObjectHasBeenDisposed,
		//					InternalResource.BundleName);
		//			throw new IllegalStateException(message);
		//		}
		//		if (value == null) {
		//			String message = InternalResource.loadString("value",
		//					InternalResource.GlobalArgumentNull,
		//					InternalResource.BundleName);
		//			throw new IllegalArgumentException(message);
		//		}
		//		long handle = InternalHandle.getHandle(value);
		//		if (handle == 0) {
		//			String message = InternalResource.loadString("value",
		//					InternalResource.GlobalInvalidConstructorArgument,
		//					InternalResource.BundleName);
		//			throw new IllegalArgumentException(message);
		//		}
		if (value != null && !(value instanceof Dataset)) {
			String message = InternalResource.loadString("value",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		m_sourceData = value;
	}

	//	public void dispose() {
	//		if (!this.getIsDisposable()) {
	//			String message = InternalResource.loadString("dispose()",
	//					InternalResource.HandleUndisposableObject,
	//					InternalResource.BundleName);
	//			throw new UnsupportedOperationException(message);
	//		} else if (this.getHandle() != 0) {
	//			ExportSettingNative.jni_Delete(getHandle());
	//			this.setHandle(0);
	//			clearHandle();
	//		}
	//
	//	}

	//	������ǰ������ΪXML�ļ������ڱ��浼��Ĳ�������,�����û��־û���
	//	XML��ʵ�����������ɣ�����ĸ�ʽ������
	@Deprecated
	public String toXML() {
		return "";
	}
	@Deprecated
	public boolean fromXML(String xml) {
		return false;
	}
	
	/**
	 * ��ȡ���õ����ݼ��ܹ���������������
	 * @return
	 */
	public FileType[] getSupportedFileType() {
		Dataset dataset = (Dataset) this.getSourceData();
		FileType[] result = null;
		if (dataset != null) {
			long handle = InternalHandle.getHandle(dataset);
			int[] values = DataExportNative.jni_GetSupportedFileType(handle);
			result = new FileType[values.length];
			for (int i = 0; i < values.length; i++) {
				result[i] = (FileType)InternalEnum.parseUGCValue(FileType.class, values[i]);
			}
		}
		return result;
	}

	// �ж�Ŀ¼�Ƿ���ڡ�
	private boolean isDirectoryExisted(String fileName) {
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

	//��֤���setting�Ƿ�Ϸ�
	boolean check() {
		boolean result = true;
		if (m_targetFilePath.length() == 0 || m_type.equals(FileType.NONE)
				|| m_sourceData == null) {
			result = false;
		}
		return result;
	}
}
