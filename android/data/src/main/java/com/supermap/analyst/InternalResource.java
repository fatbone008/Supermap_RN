package com.supermap.analyst;

import java.util.ResourceBundle;

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
 * @author not attributable
 * @version 2.0
 */
class InternalResource {
	private InternalResource() {
	}

	/**
	 * ����Դ�м����ַ���
	 * 
	 * @param key
	 *            String
	 * @param message
	 *            String �Լ��������Ϣ
	 * @param baseBundleName
	 *            String
	 * @return String
	 */
	static String loadString(String message, String key, String baseBundleName) {
		ResourceBundle bundle;

		String preMsg = "";
		if (message != null && !message.trim().equals("")) {
			preMsg = message.trim() + "\n";
		}
		try {
			bundle = ResourceBundle.getBundle(baseBundleName);
			return preMsg + bundle.getString(key);
		} catch (Exception e) {
			return preMsg;
		}
	}

	// Bundle�� protected
	// ----------------------------------------------------------------------------------------
	public static final String BundleName = "spatialanalyst_resources";

	// ȫ��Key,public
	// ----------------------------------------------------------------------------------------

	/**
	 * ����������Χ
	 */
	public static final String GlobalIndexOutOfBounds = "Global_IndexOutOfBounds";

	/**
	 * Enum ֵ����
	 */
	public static final String GlobalEnumValueIsError = "Global_EnumValueIsError";

	// ������Ч��·��
	public static final String GlobalPathIsNotValid = "Global_PathIsNotValid";

	// ������Ч��ö���࣬Ҫ�����е�ö���඼������Enum
	public static final String GlobalEnumInvalidDerivedClass = "Global_EnumInvalidDerivedClass";

	// ����Ĳ��������Ѿ��ͷŵ���
	public static final String GlobalArgumentObjectHasBeenDisposed = "Global_ArgumentObjectHasBeenDisposed";

	// ���������Ͳ���ȷ
	public static final String GlobalArgumentTypeInvalid = "Global_ArgumentTypeInvalid";

	// ����Ϊ��
	public static final String GlobalArgumentNull = "Global_ArgumentNull";

	// �����λ�ò���ȷ�������indexֻ��Ϊ0-count(����count)
	public static final String GlobalInvalidInsertPosition = "Global_InvalidInsertPosition";

	// ���������Ķ����Ѿ��ͷ�
	public static final String GlobalOwnerHasBeenDisposed = "Global_OwnerHasBeenDisposed";

	// Handle
	// ---------------------------------------------------------------------------------------
	// InternalHandleDisposable����ͨ������setHandle����ʼ������
	public static final String HandleDisposableCantCreate = "Handle_DisposableCantCreate";

	// ԭ������δ�ͷ�
	public static final String HandleOriginalObjectHasNotBeenDisposed = "Handle_OriginalObjectHasNotBeenDisposed";

	/**
	 * �����ܱ��ͷţ����ܵ���Dispose����
	 */
	public static final String HandleUndisposableObject = "Handle_UndisposableObject";

	/**
	 * �����Ѿ����ͷ�
	 */
	public static final String HandleObjectHasBeenDisposed = "Handle_ObjectHasBeenDisposed";

	// ��������IsDisposable����
	public static final String HandleCantSetIsDisposable = "Handle_CantSetIsDisposable";

	// ____________________����Ϊ����������Դ_____________________________

	// �����������뾶Ҫ����0
	public static final String BufferAnalystBufferDistanceShouldBePositive = "BufferAnalyst_BufferDistanceShouldBePositive";

	// ��������뾶���ܵ���0
	public static final String BufferAnalystBufferDistanceShouldNotBeZero = "BufferAnalyst_BufferDistanceShouldNotBeZero";

	// ��������ļ��ζ������Ͳ��Ϸ�
	public static final String BufferAnalystBufferGeometryTypeInvalid = "BufferAnalyst_BufferGeometryTypeInvalid";

	// ��������ļ��ζ����������
	public static final String BufferAnalystBufferGeometryTypeShouldBeLine = "BufferAnalyst_BufferGeometryTypeShouldBeLine";

	// ������������ݼ����Ͳ��Ϸ�
	public static final String BufferAnalystBufferDatasetTypeInvalid = "BufferAnalyst_BufferDatasetTypeInvalid";

	// ������������ݼ����ͱ�������
	public static final String BufferAnalystBufferDatasetTypeShouldBeLine = "BufferAnalyst_BufferDatasetTypeShouldBeLine";

	// �������������ݼ����Ͳ��Ϸ�
	public static final String BufferAnalystResultDatasetTypeInvalid = "BufferAnalyst_ResultDatasetTypeInvalid";

	// ���ӷ���������ݼ����Ͳ��Ϸ�
	public static final String OverlayAnalystResultDatasetTypeInvalid = "OverlayAnalyst_ResultDatasetTypeInvalid";

	// ��Բ���߶θ���Ҫ���ڵ���4
	public static final String BufferAnalystSemicircleLineSegmentShouldEqualsOrGreaterThanFour = "BufferAnalyst_SemicircleLineSegmentShouldEqualsOrGreaterThanFour";

	// ���ݼ����¼�����Ͳ��Ϸ�������Ϊ��
	public static final String OverlayAnalystDatsetOrRecordsetTypeInvalidShouldBeRegion = "OverlayAnalyst_DatsetOrRecordsetTypeInvalidShouldBeRegion";

	// Բͷ������������֧�����һ�����붼����0�Ҳ���ȵ����
	public static final String BufferAnalystRoundBufferNotSupportDifferBufferDistance = "BufferAnalyst_RoundBufferNotSupportDifferentLeftRightBufferDistance";

	// ���������ʽ����Ϊ�ջ��߿��ַ���
	public static final String BufferAnalystBufferDistanceExpressionShouldNotBeNullOrEmpty = "BufferAnalyst_BufferDistanceExpressionShouldNotBeNullOrEmpty";

	// Sting ִ��trim��Ϊ��
	public static final String GlobalStringIsEmpty = "Global_StringIsEmpty";

	// StringΪnull����Ϊ��
	public static final String GlobalStringIsNullOrEmpty = "Global_StringIsNullOrEmpty";

	// ���ޱ�����ڵ���0
	public static final String OverlayAnalystParameterToleranceShouldEqualsOrGreaterThanZero = "OverlayAnalystParameter_ToleranceShouldEqualsOrGreaterThanZero";

	// ���ӷ���ʱԴ���ݼ���������ݼ���������ͬ�����ݼ�
	public static final String OverlayAnalystSourceDatasetAndOperationDatasetShouldBeDifferent = "OverlayAnalyst_SourceDatasetAndOperationDatasetShouldBeDifferent";

	// �ļ�������
	public static final String GlobalFileNotFound = "Global_FileNotFound";

	// �������0
	public static final String GlobalShouldGreaterThanZero = "Global_ShouldGreaterThanZero";

	// ���ĵ�ַģ��ƥ�������ҪLoad
	public static final String AddressMatchLoadNeeded = "AddressMatch_LoadNeeded";

	// ����ĳ��ȱ������0
	public static final String GlobalArrayLengthShouldGreaterThanZero = "Global_ArrayLengthShouldGreaterThanZero";

	// SearchData�Ѿ�����
	public static final String AddressMatchSettingSearchDataAlreadyExist = "AddressMatchSetting_SearchDataAlreadyExist";

	// ָ�����ļ��в�����
	public static final String GlobalDirectoryNotFound = "GlobalDirectoryNotFound";

	// ָ���ĵ����Ѿ�����
	public static final String AddressDictionaryWordAlreadyExist = "AddressDictionary_WordAlreadyExist";

	/*------------------------>
	 |
	 |
	 |   ���ϵ���ԴΪ2.0��ʽ�汾�ģ��Ѿ���������
	 |   �Ժ���ӵĲ��ַŵ����棬���Ժ�ͳһ����
	 |
	 |
	 ��
	 */

	// ֵ����Ϊ�ǵ�����
	public static final String ProximityAnalystNotPointType = "ProximityAnalystNotPointType";

	// ���ݼ����Ʋ��Ϸ�
	public static final String GlobalDatasetNameInvalid = "GlobalDatasetNameInvalid";

	// add by xuzw 2008-12-23
	// �������ò�����Ҫ��
	// ������String����ֵ����
	public static final String BufferAnalystParameterInvalidObject = "BufferAnalystParameter_InvalidObject";

	public static final String BufferAnalystDistenceOfStringTypeIsInvalidForGeometryBuffer = "BufferAnalyst_DistenceOfStringTypeIsInvalidForGeometryBuffer";

	public static final String BufferAnalystLeftDistenceAndRightDistanceMustBeTheSameType = "BufferAnalyst_LeftDistenceAndRightDistanceMustBeTheSameType";

	// ��Դ����
	// ֵ��ΧӦ����[0��5]֮��
	public static final String SurfaceExtractParameter_BetweenZeroAndFive = "SurfaceExtractParameter_BetweenZeroAndFive";

	// ���ݼ����Ʋ��Ϸ�
	public static final String GlobalDatasetNameInvalide = "GlobalDatasetNameInvalide";

	// �ֶ����Ʋ�����
	public static final String GlobalSpecifiedNameNotExists = "GlobalSpecifiedNameNotExists";

	// ֵ�������0
	public static final String GlobalGreaterThanZero = "GlobalGreaterThanZero";

	// �����Ѿ��ͷ�
	public static final String GlobalHandleObjectHasBeenDisposed = "GlobalHandleObjectHasBeenDisposed";

	// ��֧�ֵ�����
	public static final String GlobalUnsportedType = "GlobalUnsportedType";

	// =============================��
	// ���ϲ�����Դ��08.05.30����

	// ֵ����Ϊ�ǵ�����
	public static final String SurfaceAnalystNotPointType = "SurfaceAnalystNotPointType";

//	 ���ζ������Ͳ��Ϸ�������Ϊ��
	public static final String OverlayAnalystGeometryTypeInvalidShouldBeRegion = "OverlayAnalyst_GeometryTypeInvalidShouldBeRegion";
}
