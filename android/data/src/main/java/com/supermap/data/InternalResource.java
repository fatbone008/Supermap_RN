package com.supermap.data;

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
	// public static final String BundleName = "data_Resources";
	public static final String BundleName = "data_resources";

	// ȫ��Key,public
	// ----------------------------------------------------------------------------------------

	/**
	 * Enum ֵ����
	 */
	public static final String GlobalEnumValueIsError = "Global_EnumValueIsError";

	// ������Ч��·��
	public static final String GlobalPathIsNotValid = "Global_PathIsNotValid";
	
	//add by huangkj
	//·���ضϺ���Ŀ����
	public static final String GlobalPathSplitNumNotValid = "GlobalPathSplitNumNotValid";
	//·���ضϺ����Ͳ���
	public static final String GlobalPathSplitTypeNotValid = "GlobalPathSplitTypeNotValid";

	// ������Ч��ö���࣬Ҫ�����е�ö���඼������Enum
	public static final String GlobalEnumInvalidDerivedClass = "GlobalEnum_InvalidDerivedClass";

	// ���캯���Ĳ������Ϸ�
	public static final String GlobalInvalidConstructorArgument = "Global_InvalidConstructorArgument";

	// ����Խ��
	public static final String GlobalIndexOutOfBounds = "Global_IndexOutOfBounds";

	// ����Ĳ��������Ѿ��ͷŵ���
	public static final String GlobalArgumentObjectHasBeenDisposed = "GlobalArgument_ObjectHasBeenDisposed";

	// ���������Ͳ���ȷ
	public static final String GlobalArgumentTypeInvalid = "Global_ArgumentTypeInvalid";

	// ����Ϊ��
	public static final String GlobalArgumentNull = "Global_ArgumentNull";

	// �����λ�ò���ȷ�������indexֻ��Ϊ0-count(����count)
	public static final String GlobalInvalidInsertPosition = "Global_InvalidInsertPosition";

	// �ײ�Ĵ��󣬵��·��ؽ������
	public static final String GlobalUGCFailed = "Global_UGCFailed";

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

	// Geometry protected
	// ----------------------------------------------------------------------------------------
	/**
	 * �Ƿ�������ֵ������ֵӦ�ô��ڵ���0
	 */
	public static final String GeometryInvalidTolerance = "Geometry_InvalidTolerance";

	/**
	 * ������СʱĿ����ο��Ϊ0
	 */
	public static final String GeometryResizeBoundsWidthIsZero = "Geometry_ResizeBoundsWidthIsZero";

	/**
	 * ������СʱĿ����ο��Ϊ0
	 */
	public static final String GeometryResizeBoundsHeightIsZero = "Geometry_ResizeBoundsHeightIsZero";

	// the start point is equal to the end point when mirro the geometry
	public static final String GeometryMirroStartEqualToEnd = "Geometry_MirroStartEqualToEnd";

	// the points length should >= 3
	public static final String GeoRegionInvalidPointsLength = "GeoRegion_InvalidPointsLength";

	// geotext can't set style
	public static final String GeoTextUnsupprotStyle = "GeoText_UnsupprotStyle";

	// the points length shoudl >=2
	public static final String GeoLineInvalidPointsLength = "GeoLine_InvalidPointsLength";

	// the distance should >= 0
	public static final String GeoLineArgumentShouldNotBeNegative = "GeoLine_ArgumentShouldNotBeNegative";

	// geoLine.convertToRegion is InvalidOperation
	public static final String GeoLienUnsupportOperation = "GeoLine_UnsupportOperation";

	// �����ߵĿ��Ӧ��Ϊ������
	public static final String GeoStyleArgumentOfLineWidthShouldBePositive = "GeoStyle_TheArgumentOfLineWidthShouldBePositive";

	// ��Point2Ds��ΪGeoLine��part�ǣ�ִ���Ƴ��������ܸ�������С��2��
	public static final String Point2DsInvalidPointLength = "Point2Ds_InvalidPointsLength";

	// ��Point2Ds��GeoLine��GeoRegion��partʱ����ִ��clear��������
	public static final String Point2DsCannotDoClearOperation = "Point2Ds_CannotDoClearOperation";

	// �����Ĺ����ռ�Ϊ�գ������Ѿ��ͷ�
	public static final String DatasourcesWorkspaceIsInvalid = "Datasources_WorkspaceIsInvalid";

	// ����Դ������ϢΪ�ջ����Ѿ��ͷ�
	public static final String DatasourcesConnectionInfoIsInvalid = "Datasources_ConnectionInfoIsInvalid";

	// ָ������������Դ������
	public static final String DatasourcesAliasIsNotExsit = "Datasources_AliasIsNotExsit";

	// ����Դ����Ϊ��
	public static final String DatasourcesAliasIsEmpty = "Datasources_AliasIsEmpty";

	// ����Դ�����Ѿ���ռ��
	public static final String DatasourcesAliasIsAlreadyExsit = "Datasources_AliasIsAlreadyExsit";

	// ������Դʧ��
	public static final String DatasourcesFailToOpenDatasource = "Datasources_FailToOpenDatasource";

	// ��������Դʧ��
	public static final String DatasourcesFailToCreateDatasource = "Datasources_FailToCreateDatasource";

	// ����Դ�����Ĺ����ռ�Ϊ�ջ����Ѿ��ͷ�
	public static final String DatasourceTheWorkspaceIsInvalid = "Datasource_TheWorkspaceIsInvalid";

	// ���ݼ�����Ϊ�ջ����Ѿ���ռ��
	public static final String DatasourceDatasetNameIsInvalid = "Datasource_DatasetNameIsInvalid";

	// ������Info��������Դ����Щ���Բ��ܱ��޸�
	public static final String DatasourceConnInfoCantSetProperty = "DatasourceConnInfo_CantSetProperty";

	// ������Ч��ö���࣬Ҫ�����е�ö���඼������Enum
	public static final String EnumInvalidDerivedClass = "Enum_InvalidDerivedClass";

	// ���ܴ��������ݼ�����
	public static final String DatasetsUnsupportToCreate = "Datasets_UnsupportToCreate";

	// ���ݼ������Ĺ����ռ��������Դ�Ѿ����ͷŻ���Ϊ��
	public static final String DatasetsParentIsNotValid = "Datasets_ParentIsNotValid";

	// name is occupied
	public static final String DatasetsNameIsOccupied = "Datasets_NameIsOccupied";

	// name is empty
	public static final String DatasetsNameIsEmpty = "Datasets_NameIsEmpty";

	// ���ݼ��Ѿ�����
	public static final String DatasetsDatasetIsAlreadyExist = "Datasets_DatasetIsAlreadyExist";

	// �������ݼ����Ͳ���ȷ���������ݼ�ʧ��
	public static final String DatasetsFailToCreateBecauseOfDatasetType = "Datasets_FailToCreateBecauseOfType";

	// ���ڱ��뷽ʽ����ȷ�����ܳɹ�����
	public static final String DatasetsFailToCreateBecauseOfEncodeType = "Datasets_FailToCreateBecauseOfEncodeType";

	// ���ݼ����Ʋ���ȡ
	public static final String DatasetNameIsNotAvailabe = "Dataset_NameIsNotAvailabe";

	// ���ݼ���Ϊ��
	public static final String DatasetNameIsEmpty = "Dataset_NameIsEmpty";

	// ��ϵͳ�����ظ�
	public static final String DatasetNameAgainstSys = "Dataset_NameAgainstSys";

	// ��ϵͳ�����ظ�
	public static final String DatasetNameErrorPrefix = "Dataset_NameErrorPrefix";

	// ���ݼ������а����Ƿ��ַ�
	public static final String DatasetNameIncludeInvalidChar = "Dataset_NameIncludeInvalidChar";

	// ���ݼ����Ƴ�����30���ĳ�������
	public static final String DatasetNameBeyondLimit = "Dataset_NameBeyondLimit";

	// ���ݼ�Ϊֻ�������ܶ����ݼ����в���
	public static final String DatasetIsReadOnly = "Dataset_IsReadOnly";

	// ����������ԴΪ�ջ����Ѿ��ͷ�
	public static final String DatasetDatasoureIsEmpty = "Dataset_DatasoureIsEmpty";

	// �����Ĺ����ռ�Ϊ�ջ����Ѿ��ͷ�
	public static final String DatasetWorkspaceIsEmpty = "Dataset_WorkspaceIsEmpty";

	// ����ֶ�ʧ��
	public static final String FieldInfosFailToAdd = "FieldInfos_FailToAdd";

	// �����ֶ�ʧ��
	public static final String FieldInfosFailToInsert = "FieldInfos_FailToInsert";

	// �ֶεľ��Ȳ���
	public static final String FieldInfoPrecisionShouldntBeNegative = "FieldInfo_MaxLengthShouldntBeNegative";

	// �ֶε�С����λ������Ϊ����
	public static final String FieldInfoScaleShouldntBeNegative = "FieldInfo_MaxLengthShouldntBeNegative";

	// �ֶ��������ֶμ����Ѿ��ͷ�
	public static final String FieldInfoFieldInfosIsInvalid = "FieldInfo_FieldInfosIsInvalid";

	// �ֶε���󳤶Ȳ���Ϊ����
	public static final String FieldInfoMaxLengthShouldntBeNegative = "FieldInfo_MaxLengthShouldntBeNegative";

	// ���ֶ�����ĳ��datasetVector��ʱ�����������κ�����
	public static final String FieldInfoCantModifyTheProperty = "FieldInfo_CantModifyTheProperty";

	// ���ܿ�¡ϵͳ�ֶ�
	public static final String FieldInfoCantCloneSystemField = "FieldInfo_CantCloneSystemField";

	// �ֶ����Ʋ���ȡ
	public static final String FieldInfoNameIsNotAvaliable = "FieldInfo_NameIsNotAvaliable";

	// �����޸�ϵͳ�ֶ�
	public static final String FieldInfoCantModifySystemField = "FieldInfoCantModifySystemField";

	// �ֶ�����Ϊ��
	public static final String FieldInfoNameIsEmpty = "FieldInfo_NameIsEmpty";

	// �ֶ�����sm��ͷ
	public static final String FieldInfoNameBeginsWithSm = "FieldInfo_NameBeginsWithSm";

	// �ֶ���������������
	public static final String FieldInfoNameLengthBeyondLimit = "FieldInfo_NameLengthBeyondLimit";

	// �ֶ����а����Ƿ��ַ�
	public static final String FieldInfoNameContainInvalidChar = "FieldInfo_NameContainInvalidChar";

	// �ֶ��������ֻ��»��ߴ�ͷ
	public static final String FieldInfoNameInvalidPrefix = "FieldInfo_NameInvalidPrefix";

	// ������Ϊ��
	public static final String FieldInfoTableNameIsEmpty = "FieldInfo_TableNameIsEmpty";

	// ����������������
	public static final String FieldInfoTableNameLengthBeyondLimit = "FieldInfo_TableNameLengthBeyondLimit";

	// �����а����Ƿ��ַ�
	public static final String FieldInfoTableNameContainInvalidChar = "FieldInfo_TableNameContainInvalidChar";

	// ���������ֻ��»��ߴ�ͷ
	public static final String FieldInfoTableNameInvalidPrefix = "FieldInfo_TableNameInvalidPrefix";

	// ָ�����Ƶ��ֶβ�����
	public static final String FieldInfosNameIsNotExist = "FieldInfos_NameIsNotExist";

	// �����ѱ�ռ��
	public static final String FieldInfosNameIsOccupied = "FieldInfos_NameIsOccupied";

	// �ֶβ��ܱ�����������У�Ҫ��ӵ��ֶ������Ϸ�����Ϊϵͳ�ֶ�
	public static final String FieldInfosCantAddTheFieldInfo = "FieldInfos_CantAddTheFieldInfo";

	// fieldInfos��֧�ֵĲ�������inster exchange ��
	public static final String FieldInfosUnsupported = "FieldInfos_Unsupported";

	// �����õ��ֶ���
	public static final String DatasetVectorUnAvalibleFieldInfo = "DatasetVector_UnAvalibleFieldInfo";

	// ָ����Դ�ֶ���Ŀ���ֶβ�ƥ��
	public static final String DatasetVectorAppendDontMatch = "DatasetVector_AppendDontMatch";

	// ���Ӧ����0
	public static final String DatasetVectorBuildTileWidthInvalid = "DatasetVector_BuildTileWidthInvalid";

	// �߶�Ӧ����0
	public static final String DatasetVectorBuildTileHeightInvalid = "DatasetVector_BuildTileHeightInvalid";

	// ���ڽ����������ֶ���Ϊ��
	public static final String DatasetVectorBuildTileFieldNameIsEmpty = "DatasetVector_BuildTileFieldNameIsEmpty";

	// ���ڽ����������ֶβ�����
	public static final String DatasetVectorBuildTileFieldIsNotExsit = "DatasetVector_BuildTileFieldIsNotExsit";

	// ��������СӦ���ڻ����0
	public static final String DatasetVectorQueryBufferInvalid = "DatasetVector_QueryBufferInvalid";

	// �ز��������޲���Ϊ��
	public static final String DatasetVectorResampleToleranceInvalid = "DatasetVector_ResampleToleranceInvalid";

	// ָ���ֶβ�����
	public static final String DatasetVectorFieldIsNotExsit = "DatasetVector_FieldIsNotExsit";

	// �ռ�������Ϊ��
	public static final String DatasetVectorSpatialIndexIsEmpty = "DatasetVector_SpatialIndexIsEmpty";

	// ���������ĸ�����СΪ����
	public static final String DatasetVectorGirdInvalid = "DatasetVector_GirdInvalid";

	// ��һ���������ӦС��ǰһ�����
	public static final String DatasetVectorGridOrderError = "DatasetVector_GridOrderError";

	// ���Ǳ�������֧�ֵ�����
	public static final String InternalVariantUnsupportType = "InternalVariant_UnsupportType";

	// ���������Ķ����Ѿ��ͷ�
	public static final String ToleranceObjectIsDisposed = "Tolerance_ObjectIsDisposed";

	// �û����������޶����ܵ���setDefualt��ֻ������ĳһ���ݼ��ķ����ſ���
	public static final String ToleranceCantInvokeSetDefualt = "Tolerance_CantInvokeSetDefualt";

	// ��ͼ������
	public static final String MapsNameIsNotInMaps = "Maps_NameIsNotInMaps";

	// ��ͼ��Ϊ��
	public static final String MapsNameIsEmpty = "Maps_NameIsEmpty";

	// ��ͼ���ѱ�ռ��
	public static final String MapsNameIsOcuupied = "Maps_NameIsOcuupied";

	public static final String WorkspaceCantChangePassword = "Workspace_CantChangePassword";

	// ������������
	public static final String WorkspaceConnectionInfoCantSetProperty = "WorkspaceConnectionInfo_CantSetProperty";

	// cout�����˷�Χ��������û����ô��Ԫ��
	public static final String JoinItemsRemoveRangeCountInvalid = "JoinItems_RemoveRangeCountInvalid";

	// �����JoinItem�Ѿ��ͷ�
	public static final String JoinItemsJoinItemDisposed = "JoinItems_SetJoinItemDisposed";

	// cout�����˷�Χ��������û����ô��Ԫ��
	public static final String LinkItemsRemoveRangeCountInvalid = "LinkItems_RemoveRangeCountInvalid";

	// �����JoinItem�Ѿ��ͷ�
	public static final String LinkItemsLinkItemDisposed = "LinkItems_SetLinkItemDisposed";

	// �Զ���Ŀռ��ѯ���ʽ���Ϸ�
	// ����Ϊ3*3��SpatialComparePattern�ǿ�����
	public static final String QueryParameterInvalidSpatialFilter = "QueryParameter_InvalidSpatialFilter";

	// ��ѯ���󲻷���Ҫ��
	// ������Point2D,Rectangle2D,Geometry,DatasetVector,Recordset
	public static final String QueryParameterInvalidQueryObject = "QueryParameter_InvalidQueryObject";

	// ������ĳ��Recodset��ʱ��������������
	public static final String QueryParameterOfRecordsetCantSet = "QueryParameter_OfRecordsetCantSet";

	// ���ø����Ե�ʱ�򣬴���Ĳ����Ѿ��ͷ���
	public static final String QueryParameterJoinItemsDisposed = "QueryParameter_JoinItemsDisposed";

	// ��ɫ��Ӧ����0
	public static final String ColorsCountShouldBePositive = "Colors_CountShouldBePositive";

	// IntervalColors�ĳ�������Ϊ2
	public static final String ColorsIntervalColorsLengthInvalid = "Colors_IntervalColorsLengthInvalid";

	// Info���������ݼ��Ѿ��ͷ�
	public static final String DatasetVectorInfoDatasetIsDisposed = "DatasetVectorInfo_DatasetIsDisposed";

	// ��Info����ĳ�����ݼ���ʱ���ǲ��������õ�
	public static final String DatasetVectorInfoCantsetProperty = "DatasetVectorInfo_CantsetProperty";

	// ������ĳ�����ݼ�ʱ��������������
	public static final String DatasetGridInfoCantSetProperty = "DatasetGridInfoCantSetProperty";

	// width����ӦΪ����
	public static final String DatasetGridWidthShouldBePositive = "DatasetGrid_WidthShouldBePositive";

	// height����ӦΪ����
	public static final String DatasetGridHeightShouldBePositive = "DatasetGrid_HeightShouldBePositive";

	// blockSize����ӦΪ����
	public static final String DatasetGridBlockSizeShouldBePositive = "DatasetGrid_BlockSizeShouldBePositive";

	// ������ĳ�����ݼ�ʱ��������������
	public static final String DatasetImageInfoCantSetProperty = "DatasetImageInfoCantSetProperty";

	// width����ӦΪ����
	public static final String DatasetImageWidthShouldBePositive = "DatasetImage_WidthShouldBePositive";

	// height����ӦΪ����
	public static final String DatasetImageHeightShouldBePositive = "DatasetImage_HeightShouldBePositive";

	// blockSize����ӦΪ����
	public static final String DatasetImageBlockSizeShouldBePositive = "DatasetImage_BlockSizeShouldBePositive";

	// RollBackCountֻ��Ϊ>=-1
	public static final String LogFileRollBackCountIsInvalid = "LogFile_RollBackCountIsInvalid";

	// �ֶβ�����
	public static final String RecordsetFieldIsNotExsit = "Recordset_FieldIsNotExsit";

	/*------------------------>
	 //    ���ϵ���ԴΪAlpha�汾�ģ��Ѿ���������
	 */
	// ��PointMs��GeoLineM��partʱ��ִ��clear��������
	public static final String PointMsCannotDoClearOperation = "PointMs_CannotDoClearOperation";

	// �����ʸ�����ݼ�����
	public static final String DatasetVectorInfoIllegalDatasetType = "DatasetVectorInfo_IllegalDatasetType";

	// ����ı�������
	public static final String DatasetVectorInfoIllegalEncodeType = "DatasetVectorInfo_IllegalEncodeType";

	// ���ݼ�û���ڵ��˲�֧��InnerPoint To Dataset
	// ����Tabular��Linktable
	public static final String DatasourceDatasetHasNoInnerPoint = "Datasource_DatasetHasNoInnerPoint";

	// ���ݼ�������
	public static final String DatasetsDatasetIsNotExist = "Datasets_DatasetIsNotExist";

	// �ȴ����ݼ�����ִ��ָ���Ĳ���
	public static final String DatasetOpenItFirst = "Dataset_OpenItFirst";

	// ��������Զ������ͣ����������ø�����
	public static final String IfTypeNotUserDefinedCantSetProperty = "IfTypeNotUserDefinedCantSetProperty";

	// �����ƽ������ϵ�������Բ�֧��
	public static final String ThisPropertyNotSupportedIfSpatialRefTypeNoneEarth = "ThisPropertyNotSupportedIfSpatialRefTypeNoneEarth";

	// ����Դ��ֻ���ģ����ܽ�����Щ����
	public static final String DatasetsDatasourceIsReadOnly = "Datasets_DatasourceIsReadOnly";

	// Point2Ds�ĳ���Ӧ����0
	public static final String Point2DsIsEmpty = "Point2DsIsEmpty";

	// �����ͼ��xml�ַ���Ϊ��
	public static final String MapsXMLIsEmpty = "MapsXMLIsEmpty";

	// ��֧�ָ����ݼ�����
	public static final String RecordsetDatasetTypeIsNotSupported = "RecordsetDatasetTypeIsNotSupported";

	// ����ĸ߶�Ӧ��Ϊ������
	public static final String TextStyleTheValueOfFontHeightShouldBePositive = "TextStyle_TheValueOfFontHeightShouldBePositive";

	// ����Ŀ��Ӧ��Ϊ������
	public static final String TextStyleTheValueOfFontWidthShouldBePositive = "TextStyle_TheValueOfFontWidthShouldBePositive";

	// ���η�����ķ���ID����Ϊ������
	public static final String GeoStyleTheValueOfSymbolIDShouldNotBeNegative = "GeoStyle_TheValueOfSymbolIDShouldNotBeNegative";

	// ���ŵĴ�С����Ϊ������
	public static final String GeoStyleTheValueOfMarkerSizeShouldNotBeNeagtive = "GeoStyle_TheValueOfMarkerSizeShouldNotBeNeagtive";

	// Geometry����ΪEmpty
	public static final String GeometryShouldNotBeEmpty = "GeometryShouldNotBeEmpty";

	public static final String GlobalProductionVersion = "Global_ProductionVersion";

	public static final String GlobalProductionAdditionMessage = "Global_ProductionAdditionMessage";

	/*------------------------>
	 |   ���ϵ���ԴΪBeta�汾�ģ��Ѿ���������
	 ��
	 */

	// Sting ִ��trim��Ϊ��
	public static final String GlobalStringIsEmpty = "Global_StringIsEmpty";

	// StringΪnull����Ϊ��
	public static final String GlobalStringIsNullOrEmpty = "Global_StringIsNullOrEmpty";

	// GlobalSpecifiedNameNotExist,ָ�����ƵĶ��󲻴���
	public static final String GlobalSpecifiedNameNotExist = "Global_SpecifiedNameNotExist";

	// GlobalSpecifiedNameAlreadyExist,ָ�����ƵĶ����Ѿ�����
	public static final String GlobalSpecifiedNameAlreadyExist = "Global_SpecifiedNameAlreadyExist";

	// ���Ϸ��ľ�γ������
	public static final String InvalidLongitudeLatitudeCoord = "InvalidLongitudeLatitudeCoord";

	// ��Ӧ��fieldName��ϵͳ�ֶΣ�����ִ�и���
	public static final String DatasetVectorCannotUpdateSystemField = "DatasetVector_CannotUpdateSystemField";

	// ������ֻ���ģ������޸ġ�
	public static final String DatasourceTheDatasourceIsReadOnly = "Datasource_TheDatasourceIsReadOnly";

	// �����ݼ������ڵ�����Դ��ֻ���ģ������޸ġ�
	public static final String DatasetVectorTheDatasourceOrDatasetIsReadOnly = "DatasetVector_TheDatasourceOrDatasetIsReadOnly";

	// �����ݼ������ڵ�����Դ��ֻ���ģ������޸ġ�
	public static final String DatasetImageTheDatasourceOrDatasetIsReadOnly = "DatasetImage_TheDatasourceOrDatasetIsReadOnly";

	// �����ݼ������ڵ�����Դ��ֻ���ģ������޸ġ�
	public static final String DatasetGridTheDatasourceOrDatasetIsReadOnly = "DatasetGrid_TheDatasourceOrDatasetIsReadOnly";

	// ��¼����ֻ���ģ������޸ġ�
	public static final String RecordsetRecordsetIsReadOnly = "Recordset_RecordsetIsReadOnly";

	// ���ǿյ�
	public static final String Point2DIsEmpty = "Point2D_IsEmpty";

	// TileWidth�������0
	public static final String SpatialIndexInfoTileWidthShouldGreaterThanZero = "SpatialIndexInfo_TileWidthShouldGreaterThanZero";

	// TileHeight�������0
	public static final String SpatialIndexInfoTileHeightShouldGreaterThanZero = "SpatialIndexInfo_TileHeightShouldGreaterThanZero";

	// GridSize�������0
	public static final String SpatialIndexInfoGridSizeShouldGreaterThanZero = "SpatialIndexInfo_GridSizeShouldGreaterThanZero";

	// MaxConnectionӦ��Ϊ����
	public static final String DatasourceConnectionInfoMaxConnectionShouldBePositive = "DatasourceConnectionInfo_MaxConnectionShouldBePositive";

	// MinConnectionӦ��Ϊ����
	public static final String DatasourceConnectionInfoMinConnectionShouldBePositive = "DatasourceConnectionInfo_MinConnectionShouldBePositive";

	// ConnectionIncrementStep��Ӧ��Ϊ����
	public static final String DatasourceConnectionInfoConnectionIncrementStepShouldNotBeNegative = "DatasourceConnectionInfo_ConnectionIncrementStepShouldNotBeNegative";

	// ��֧�ָñ�������
	public static final String GlobalUnsupportedEncodeType = "Global_UnsupportedEncodeType";

	// column���ڷ�Χ��
	public static final String DatasetGridColumnIsOutOfRange = "DatasetGrid_ColumnIsOutOfRange";

	// row���ڷ�Χ��
	public static final String DatasetGridRowIsOutOfRange = "DatasetGrid_RowIsOutOfRange";

	// column���ڷ�Χ��
	public static final String DatasetImageColumnIsOutOfRange = "DataseImage_ColumnIsOutOfRange";

	// row���ڷ�Χ��
	public static final String DatasetImageRowIsOutOfRange = "DatasetImage_RowIsOutOfRange";

	// �ı����Ͳ�֧�־����ѯ������
	public static final String DatasetVectorGeoTextIsUnsupported = "DatasetVector_GeoTextIsUnsupported";

	// �������ݼ���֧��HasGeometry�Ĳ�ѯ��
	public static final String DatasetVector_TabularUnsupport = "DatasetVector_TabularUnsupport";

	// �������޲�ӦΪ����
	public static final String ToleranceDangleShouldNotBeNegative = "Tolerance_DangleShouldNotBeNegative";

	// Fuzzy��ӦΪ����
	public static final String ToleranceFuzzyShouldNotBeNegative = "Tolerance_FuzzyShouldNotBeNegative";

	// �������޲�ӦΪ����
	public static final String ToleranceGrainShouldNotBeNegative = "Tolerance_GrainShouldNotBeNegative";

	// ��㲶׽�����޲�ӦΪ����
	public static final String ToleranceNodeSnapShouldNotBeNegative = "Tolerance_NodeSnapShouldNotBeNegative";

	// ��С��������޲�ӦΪ����
	public static final String ToleranceSmallPolygonShouldNotBeNegative = "Tolerance_SmallPolygonShouldNotBeNegative";

	// �����ڸ��ֶ�
	public static final String DatasourceFieldIsNotExist = "Datasource_FieldIsNotExist";

	// ϵͳ�ֶ��ǲ������޸ĵ�
	public static final String RecordsetSystemFieldIsReadOnly = "Recordset_SystemFieldIsReadOnly";

	// ���ޱ������0
	public static final String GlobalToleranceShouldGreaterThanZero = "Global_ToleranceShouldGreaterThanZero";

	// �����ֶα�������Ĭ��ֵ
	public static final String FieldInfosRequiredFeildInfoMustBeSettedDefaultValue = "FieldInfos_RequiredFeildInfoMustBeSettedDefaultValue";

	// �󲢼��ζ������Ϊ��ͬ����
	public static final String GeometristTheTypeOfGeometryToUnionMustBeSame = "Geometrist_TheTypeOfGeometryToUnionMustBeSame";

	// �Ƿ����
	public static final String LicenseInvalid = "License_Invalid";

	// �Ϸ����
	public static final String LicenseValid = "License_Valid";

	// û���ҵ����������߼�������ļ�
	public static final String LicenseContainerNotFound = "License_ContainerNotFound";

	// û���ҵ����ģ��
	public static final String LicenseFeatureNotFound = "License_FeatureNotFound";

	// �����ֶβ���ΪNull
	public static final String RecordsetRequiredFieldShouldNotBeNull = "Recordset_RequiredFieldShouldNotBeNull";

	// ��֧�ֵ�PixelFormat����
	public static final String DatasetImageInfoUnSupportedPixelFormat = "DatasetImageInfo_UnSupportedPixelFormat";

	// ���ݼ��������뼸�ζ�������Ͳ���ͬ
	public static final String RecordsetDatasetTypeAndGeometryTypeIsDifferent = "Recordset_DatasetTypeAndGeometryTypeIsDifferent";

	// ���ζ���Ϊ��
	public static final String RecordsetGeometryIsEmpty = "Recordset_GeometryIsEmpty";

	// ����SuperMap Objects Java 2008
	public static final String AboutBoxWelcomeTitle = "AboutBox_WelcomeTitle";

	// Logo
	public static final String AboutBoxLogoRes = "AboutBox_LogoRes";

	/*------------------------>
	 |
	 |
	 |   ���ϵ���ԴΪ��ʽ�汾�ģ��Ѿ���������
	 |   �Ժ���ӵĲ��ַŵ����棬���Ժ�ͳһ����
	 |
	 |
	 ��
	 */
	public static final String Point3DsInvalidCount = "Point3Ds_TheCountIsInvalid";

	// the points length shoudl >=2
	public static final String GeoLine3DInvalidPointsLength = "GeoLine3D_InvalidPointsLength";

	// the points length should >= 3
	public static final String GeoRegion3DInvalidPointsLength = "GeoRegion3D_InvalidPointsLength";

	// ��Point3Ds��ΪGeoLine3D��part�ǣ�ִ���Ƴ��������ܸ�������С��2��
	public static final String Point3DsInvalidPointLength = "Point3Ds_InvalidPointsLength";

	// ��Point3Ds��GeoLine3D��GeoRegion3D��partʱ����ִ��clear��������
	public static final String Point3DsCannotDoClearOperation = "Point3Ds_CannotDoClearOperation";

	// GeoPoint3D
	public static final String GeoPoint3DUnsupprotStyle2D = "GeoPoint3D_UnsupprotStyle2D";

	// GeoLine3D can't set style
	public static final String GeoLine3DUnsupprotStyle2D = "GeoLine3D_UnsupprotStyle2D";

	// GeoRegion3D can't set style
	public static final String GeoRegion3DUnsupprotStyle2D = "GeoRegion3D_UnsupprotStyle2D";

	public static final String GeoPieCylinderUnsupprotStyle2D = "GeoPieCylinder_UnsupprotStyle2D";

	public static final String GeoSphereUnsupprotStyle2D = "GeoSphere_UnsupprotStyle2D";

	public static final String GeoHemiSphereUnsupprotStyle2D = "GeoHemiSphere_UnsupprotStyle2D";

	public static final String GeoEllipsoidUnsupprotStyle2D = "GeoEllipsoid_UnsupprotStyle2D";

	public static final String GeoPie3DUnsupprotStyle2D = "GeoPie3D_UnsupprotStyle2D";

	public static final String GeoCircle3DUnsupprotStyle2D = "GeoCircle3D_UnsupprotStyle2D";

	// �����ߵĿ��Ӧ��Ϊ������
	public static final String GeoStyle3DArgumentOfLineWidthShouldBePositive = "GeoStyle3D_TheArgumentOfLineWidthShouldBePositive";

	// ��άͼ������ű���Ӧ��Ϊ������
	public static final String GeoStyle3DArgumentOfMarkerIconScaleShouldBePositive = "GeoStyle3D_TheArgumentOfMarkerIconScaleShouldBePositive";

	// ���ŵĴ�С����Ϊ������
	public static final String GeoStyle3DTheValueOfMarkerSizeShouldNotBeNeagtive = "GeoStyle3D_TheValueOfMarkerSizeShouldNotBeNeagtive";

	// ��������U���򣨺��򣩵��ظ�������Ϊ������
	public static final String GeoStyle3DTheValueOfTilingUShouldNotBeNeagtive = "GeoStyle3D_TheValueOfTilingUShouldNotBeNeagtive";

	// ��������V�������򣩵��ظ�������Ϊ������
	public static final String GeoStyle3DTheValueOfTilingVShouldNotBeNeagtive = "GeoStyle3D_TheValueOfTilingVShouldNotBeNeagtive";
	
	// ��������U���򣨺��򣩵��ظ�������Ϊ������
	public static final String GeoStyle3DTheValueOfTopTilingUShouldNotBeNeagtive = "GeoStyle3D_TheValueOfTopTilingUShouldNotBeNeagtive";

	// ��������V�������򣩵��ظ�������Ϊ������
	public static final String GeoStyle3DTheValueOfTopTilingVShouldNotBeNeagtive = "GeoStyle3D_TheValueOfTopTilingVShouldNotBeNeagtive";

	// add by xuzw 2008-11-26
	// GeoArc���У�����GeoArcʧ��
	public static final String GeoArcFailConstruct = "GeoArc_FailConstruct";

	// add by xuzw 2008-12-03
	// GeoChord���У�sweepAngleԽ��
	public static final String GeoChordSweepAngleRange = "GeoChord_SweepAngleRange:(-360,0)||(0,360)";

	// GeoChord�е�SweepAngle����Ϊ0
	public static final String GeoChordSweepAngleShouldNotBeZero = "GeoChord_SweepAngleShouldNotBeZero";

	// GeoPie�У�SweepAngle�ķ�ΧӦ����(-360,0)||(0,360)
	public static final String GeoPieSweepAngleRange = "GeoPie_SweepAngleRange:(-360,0)||(0,360)";

	// GeoPie�е�SweepAngle����Ϊ0
	public static final String GeoPieSweepAngleShouldNotBeZero = "GeoPie_SweepAngleShouldNotBeZero";

	// GeoPie3D���У�sweepAngleԽ��
	public static final String GeoPie3DSweepAngleRange = "GeoPie3D_SweepAngleRange:(-360,0)||(0,360)";

	// GeoPie3D�е�SweepAngle����Ϊ0
	public static final String GeoPie3DSweepAngleShouldNotBeZero = "GeoGeoPie3D_SweepAngleShouldNotBeZero";

	// GeoPieCylinder���У�sweepAngleԽ��
	public static final String GeoPieCylinderSweepAngleRange = "GeoPieCylinder_SweepAngleRange:(-360,0)||(0,360)";

	// GeoPieCylinder�е�SweepAngle����Ϊ0
	public static final String GeoPieCylinderSweepAngleShouldNotBeZero = "GeoPieCylinder_SweepAngleShouldNotBeZero";

	// add by xuzw 2008-12-03
	// GeoCardinal���У����Ƶ���Ӧ�ô��ڵ���2
	public static final String GeoCardinalControlPointsLengthShouldNotLessThanTwo = "GeoCardinal_ControlPointsLengthShouldNotLessThanTwo";

	public static final String GeoCurveControlPointsLengthShouldNotLessThanSix = "GeoCurve_ControlPointsLengthShouldNotLessThanSix";

	// add by xuzw 2008-12-04
	// GeoBSpline���У����Ƶ���Ӧ�ô��ڵ���4
	public static final String GeoBSplineControlPointsLengthShouldNotLessThanFour = "GeoBSpline_ControlPointsLengthShouldNotLessThanFour";

	// ���غ�
	public static final String PointsAreSame = "PointsAreSame";

	// ���㹲��
	public static final String ThreePointsAreInOneLine = "ThreePointsAreInOneLine";

	// ���ο�ȱ���Ϊ����
	public static final String GeoRectangleWidthShouldPositive = "GeoRectangleWidthShouldPositive";

	// ���θ߶ȱ���Ϊ����
	public static final String GeoRectangleHeightShouldPositive = "GeoRectangleHeightShouldPositive";

	// Բ�Ǿ��ο�ȱ���Ϊ����
	public static final String GeoRoundRectangleWidthShouldBePositive = "GeoRoundRectangle_WidthShouldBePositive";

	// Բ�Ǿ��θ߶ȱ���Ϊ����
	public static final String GeoRoundRectangleHeightShouldBePositive = "GeoRoundRectangle_HeightShouldBePositive";

	// Բ�Ǿ���RadiusX����Ϊ����
	public static final String GeoRoundRectangleRadiusXShouldNotBeNeagtive = "GeoRoundRectangle_RadiusXShouldNotBeNeagtive";

	// Բ�Ǿ���RadiusY����Ϊ����
	public static final String GeoRoundRectangleRadiusYShouldNotBeNeagtive = "GeoRoundRectangle_RadiusYShouldNotBeNeagtive";

	// Բ�Ǿ��ο�ȱ���Ϊ>=2*RadiusX
	public static final String GeoRoundRectangleWidthShouldNotLessThan2RadiusX = "GeoRoundRectangle_WidthShouldNotLessThan2RadiusX";

	public static final String Rectangle2DWidthAndHeightShouldMoreThanZero = "Rectangle2DWidthAndHeightShouldMoreThanZero";

	// Բ�Ǿ��θ߶ȱ���Ϊ>=2*RadiusY
	public static final String GeoRoundRectangleHeightShouldNotLessThan2RadiusY = "GeoRoundRectangle_HeightShouldNotLessThan2RadiusY";

	// Բ�Ǿ���ת��GeoLine�Ķ���>1
	public static final String GeoRoundRectangleGeoLineSegmentCountShouldGreaterThanOne = "GeoRoundRectangle_GeoLineSegmentCountShouldGreaterThanOne";

	// Բ�Ǿ���ת��GeoRegion�Ķ���>1
	public static final String GeoRoundRectangleGeoRegionSegmentCountShouldGreaterThanOne = "GeoRoundRectangle_GeoRegionSegmentCountShouldGreaterThanOne";

	// ��Բ���������Ϊ����
	public static final String GeoEllipseSemiMajorAxisShouldBePositive = "GeoEllipse_SemimajorAxisShouldBePositive";

	// ��Բ�̰������Ϊ����
	public static final String GeoEllipseSemiMinorAxisShouldBePositive = "GeoEllipse_SemiMinorAxisShouldBePositive";

	// ��Բת��GeoLine�Ķ���>1
	public static final String GeoEllipseGeoLineSegmentCountShouldGreaterThanOne = "GeoEllipse_GeoLineSegmentCountShouldGreaterThanOne";

	// ��Բת��GeoRegion�Ķ���>1
	public static final String GeoEllipseGeoRegionSegmentCountShouldGreaterThanOne = "GeoEllipse_GeoRegionSegmentCountShouldGreaterThanOne";

	// ��Բ�����������Ϊ����
	public static final String GeoEllipticArcSemiMajorAxisShouldBePositive = "GeoEllipticArc_SemimajorAxisShouldBePositive";

	// ��Բ���̰������Ϊ����
	public static final String GeoEllipticArcSemiMinorAxisShouldBePositive = "GeoEllipticArc_SemiMinorAxisShouldBePositive";

	// ��Բ��ɨ��� -360~360
	public static final String GeoEllipticArcSweepAngleRange = "GeoEllipticArc_SweepAngleRange(-360,0)||(0,360)";

	public static final String GeoEllipticArcStartAngleShouldBe360_360 = "GeoEllipticArc_StartAngleShouldBe-360_360";

	public static final String GeoPieCylinderStartAngleShouldBe360_360 = "GeoPieCylinder_StartAngleShouldBe-360_360";

	public static final String GeoPieStartAngleShouldBe360_360 = "GeoPie_StartAngleShouldBe-360_360";

	public static final String GeoPie3DStartAngleShouldBe360_360 = "GeoPie3D_StartAngleShouldBe-360_360";

	// ��Բ��ɨ��� -360~360
	public static final String GeoEllipticArcStartAngleShouldNotBeZero = "GeoEllipticArc_StartAngleShouldNotBeZero";

	public static final String GeoPieCylinderStartAngleShouldNotBeZero = "GeoPieCylinderStartAngleShouldNotBeZero";

	public static final String GeoPieStartAngleShouldNotBeZero = "GeoPie_StartAngleShouldNotBeZero";

	public static final String GeoPie3DStartAngleShouldNotBeZero = "GeoPie3D_StartAngleShouldNotBeZero";

	// ��Բ��ת��GeoLine�Ķ���>=0
	public static final String GeoEllipticArcGeoLineSegmentCountShouldNotBeNeagtive = "GeoEllipticArc_GeoLineSegmentCountShouldNotBeNeagtive";

	// ���������ĳ�>0
	public static final String GeoBoxLengthShouldBePositive = "GeoBox_LengthShouldBePositive";

	// ���������Ŀ�>0
	public static final String GeoBoxWidthShouldBePositive = "GeoBox_WidthShouldBePositive";

	// ���������ĸ�>0
	public static final String GeoBoxHeightShouldBePositive = "GeoBox_HeightShouldBePositive";

	// Բ׶������Բ�İ뾶>0
	public static final String GeoConeBottomRadiusShouldBePositive = "GeoCone_BottomRadiusShouldBePositive";

	// Բ׶������>0
	public static final String GeoConeHeightShouldBePositive = "GeoCone_HeightShouldBePositive";

	// ��άͼƬ��>0
	public static final String GeoPictureWidthShouldBePositive = "GeoPicture_WidthShouldBePositive";

	// ��άͼƬ��>0
	public static final String GeoPictureHeightShouldBePositive = "GeoPicture_HeightShouldBePositive";

	// �� άͼƬ��>0
	public static final String GeoPicture3DWidthShouldBePositive = "GeoPicture3D_WidthShouldBePositive";

	// �� άͼƬ��>0
	public static final String GeoPicture3DHeightShouldBePositive = "GeoPicture3D_HeightShouldBePositive";

	// ��άͼƬ��>0
	public static final String GeoPictureFailConstruct = "GeoPicture_FailConstruct";

	// add by xuzw 2008-12-03
	// ���γ��������Ϊ����
	public static final String GeoChordSemiMajorAxisShouldBePositive = "GeoChord_SemimajorAxisShouldBePositive";

	// ���ζ̰������Ϊ����
	public static final String GeoChordSemiMinorAxisShouldBePositive = "GeoChord_SemiMinorAxisShouldBePositive";

	// Բ̨�Ķ�Բ�뾶����Ϊ����
	public static final String GeoCylinderTopRadiusShouldBePositive = "GeoCylinder_TopRadiusShouldBePositive";

	// Բ̨�ĵ�Բ�뾶����Ϊ����
	public static final String GeoCylinderBottomRadiusShouldBePositive = "GeoCylinder_BottomRadiusShouldBePositive";

	// Բ̨�ĸ߶ȱ���Ϊ����
	public static final String GeoCylinderHeightShouldBePositive = "GeoCylinder_HeightShouldBePositive";

	// add by xuzw 2008-12-04
	// ����׶�ĵ��泤�ȱ���Ϊ����
	public static final String GeoPyramidLengthShouldPositive = "GeoPyramid_LengthShouldPositive";

	// ����׶�ĵ����ȱ���Ϊ����
	public static final String GeoPyramidWidthShouldPositive = "GeoPyramid_WidthShouldPositive";

	// ����׶�ĸ߶ȱ���Ϊ����
	public static final String GeoPyramidHeightShouldPositive = "GeoPyramid_HeightShouldPositive";

	// ����Ĳ���Ӧ�÷Ǹ�>=0
	public static final String GlobalArgumentShouldNotBeNegative = "Global_ArgumentShouldNotBeNegative";

	// ����Ĳ���Ӧ��>0
	public static final String GlobalArgumentShouldMoreThanZero = "Global_ArgumentShouldMoreThanZero";

	// ����Ĳ���Ӧ��>=1
	public static final String GlobalArgumentShouldNotSmallerThanOne = "Global_ArgumentShouldNotSmallerThanOne";

	// ����Ĳ���Ӧ��>=2
	public static final String GlobalArgumentShouldNotSmallerThanTwo = "Global_ArgumentShouldNotSmallerThanTwo";

	// add by xuzw 2008-12-09
	// GeoArc���У�sweepAngle�ķ�Χ��(-360,0)||(0,360)
	public static final String GeoArcSweepAngleRange = "GeoArc_SweepAngleRange:(-360,0)||(0,360)";

	public static final String GeoArcSweepAngleShouldNotBeZero = "GeoArc_SweepAngleShouldNotBeZero";

	// GeoArc�У�FindPointOnArc��������Ƕ�Խ��
	public static final String GeoArcSweepAngleOutOfBounds = "GeoArc_SweepAngleOutOfBounds";

	// add by xuzw 2008-12-10
	// GeoCylinder can't set style
	public static final String GeoCylinderUnsupprotStyle2D = "GeoCylinder_UnsupprotStyle2D";

	// GeoPyramid can't set style
	public static final String GeoPyramidUnsupprotStyle2D = "GeoPyramid_UnsupprotStyle2D";

	// GeoEllipticArc���У��Ƕ�Խ���쳣
	public static final String GeoEllipticArcArgumentOutOfBounds = "GeoEllipticArc_ArgumentOutOfBounds";

	// ������Ӳ����У����������
	public static final String BatchAddOperationUnsupportedUndo = "BatchAddOperation_Unsupport_Undo";

	// ������Ӳ����У�������ǰ��
	public static final String BatchAddOperationUnsupportedRedo = "BatchAddOperation_Unsupport_Redo";

	// �����¼��������޷�����
	public static final String BatchHasBegun = "BatchHasBegun";

	// ���ſ�����˲�ͬ���͵ķ���
	public static final String SymbolLibraryUnsupportedType = "SymbolLibrary_Unsupported_Add";

	// ���ſ��Ѵ���ID
	public static final String SymbolLibraryHasContaintID = "SymbolLibrary_HasContaintID";

	// ���ſ�ID������ڵ��� 0
	public static final String SymbolIDShouldNotBeNegative = "Symbol_IDShouldNotBeNegative";

	public static final String TwoPointsShouldNotBeEqual = "TwoPoints_ShouldNotBeEqual";

	// �ļ�������
	public static final String GeoModelTheFileIsNotExist = "GeoModel_TheFileIsNotExist";

	public static final String GlobalArgumentOutOfBounds = "Global_ArgumentOutOfBounds";

	// ���ε����Ʋ��Ϸ�
	public static final String DatasetGridBandNameIsNotValid = "DatasetGrid_BandNameIsNotValid";

	// ���Ƕನ�Σ���֧�ָò���
	public static final String DatasetGridThisOperationIsAvailableForMultibandsDataOnly = "DatasetGrid_ThisOperationIsAvailableForMultibandsDataOnly";

	// ���������Ƕನ��
	public static final String DatasetGridTheArgumentMustBeMultibandsData = "DatasetGrid_TheArgumentMustBeMultibandsData";

	// ��������ȷ
	public static final String DatasetGridInvalidCount = "DatasetGrid_InvalidCount";

	// ���ε����Ʋ��Ϸ�
	public static final String DatasetImageBandNameIsNotValid = "DatasetImage_BandNameIsNotValid";

	// ���Ƕನ�Σ���֧�ָò���
	public static final String DatasetImageThisOperationIsAvailableForMultibandsDataOnly = "DatasetImage_ThisOperationIsAvailableForMultibandsDataOnly";

	// ���������Ƕನ��
	public static final String DatasetImageTheArgumentMustBeMultibandsData = "DatasetImage_TheArgumentMustBeMultibandsData";

	// ��������ȷ
	public static final String DatasetImageInvalidCount = "DatasetImage_InvalidCount";

	// GeoStyle size2D��С����ȫ��
	public static final String GeoStyleTheValueOfMarkerSizeIsNotValid = "GeoStyleTheValueOfMarkerSize_IsNotValid";

	// ��׼ͼ��ͼ���ͼ�������Ϸ�
	public static final String StandardMarginSheetNameIsNotValid = "StandardMargin_SheetName_IsNotValid";

	// ���˼����Դ���ݼ�����������Ԥ��������
	public static final String TopologyValidatingItemSourceDatasetMustBeInTopologyDatasetRelationItems = "TopologyValidatingItem_SourceDatasetMustBeInTopologyDatasetRelationItems";

	// ���˼����Ŀ�����ݼ�����������Ԥ��������
	public static final String TopologyValidatingItemValidatingDatasetMustBeInTopologyDatasetRelationItems = "TopologyValidatingItem_ValidatingDatasetMustBeInTopologyDatasetRelationItems";

	// ������������ݼ�����������ݼ��������������ݼ����ڵ�����Դ��
	public static final String TopologyDatasetRelationItemDatasetMustBeInTopologyDatasetDatasource = "TopologyDatasetRelationItem_DatasetMustBeInTopologyDatasetDatasource";

	// ������ݼ��Ѿ�����������
	public static final String TopologyDatasetRelationItemDatasetAttachTopo = "TopologyDatasetRelationItem_DatasetAttachTopo";

	// �������ݼ�����˳��ų�����Χ
	public static final String TopologyDatasetRelationItemPrecisionOrderOutOfBounds = "TopologyDatasetRelationItem_PrecisionOrderOutOfBounds";

	// ��ά�������ѱ�ռ��
	public static final String ScenesNameIsOcuupied = "Scenes_NameIsOcuupied";

	// ��ά����������
	public static final String SceneNameIsNotInScenes = "Scene_NameIsNotInScenes";

	// geoLine3D.convertToRegion is InvalidOperation
	public static final String GeoLine3DUnsupportOperation = "GeoLine3D_UnsupportOperation";

	// �汾ID������
	public static final String VersionIsNotExsit = "Version_IDIsNotExsit";

	// ����ɾ����ǰ�汾
	public static final String VersionManagerCurrentVersionCanNotBeDeleted = "VersionManager_CurrentVersionCanNotBeDeleted";

	// ����ɾ�����汾
	public static final String VersionManagerRootVersionCanNotBeDeleted = "VersionManager_RootVersionCanNotBeDeleted";

	// ��ǰ�汾�����Ӱ汾������ɾ��
	public static final String VersionManagerCurrentVersionHasChildVersionCanNotBeDeleted = "VersionManager_CurrentVersionHasChildVersionCanNotBeDeleted";

	// ���ݼ�����������Դ��
	public static final String DatasetMustContainInDatasource = "DatasetMustContainInDatasource";

	// Ŀ��汾���ǵ�ǰ�汾�ĸ��汾
	public static final String TagetVersionIsNotCurrentVersionParent = "TagetVersionIsNotCurrentVersionParent";

	// ��ǰ�汾���������ط����ڱ༭״̬,����Э��
	public static final String CurrentVersionIsBeingEdited = "CurrentVersionIsBeingEdited";

	// ��ǰ�汾���ڱ�Э��,����Э��
	public static final String CurrentVersionIsBeingReconciled = "CurrentVersionIsBeingReconciled";

	// Ŀ��汾����Э��,����Э��
	public static final String TagetVersionIsBeingReconciled = "TagetVersionIsBeingReconciled";

	public static final String QueryParameterMustBeStaticCursor = "QueryParameterMustBeStaticCursor";

	// ��֧�ֵ���������
	public static final String UnsupprotDatasetType = "UnsupprotDatasetType";

	// ���������ѱ�ռ��
	public static final String LayoutsNameIsOcuupied = "Layouts_NameIsOcuupied";

	// �������ֲ�����
	public static final String LayoutsNameIsNotInLayouts = "Layouts_NameIsNotInLayouts";

	// ��ͼ�߿򼸺ζ����ȱ���Ϊ����
	public static final String GeoMapBorderWidthShouldBePositive = "GeoMapBorder_WidthShouldBePositive";

	// ��ͼ�߿򼸺ζ�����������ڵ���0
	public static final String GeoMapBorderIntervalShouldNotBeNegative = "GeoMapBorder_IntervalShouldNotBeNegative";

	// GeoMapBorder����ļ�������
	public static final String GeoMapBorderTheFileIsNotExist = "GeoMapBorder_TheFileIsNotExist";

	// GeoMapScale��ȱ���Ϊ����
	public static final String GeoMapScaleWidthShouldBePositive = "GeoMapScale_WidthShouldBePositive";

	// GeoMapScale�߶ȱ���Ϊ����
	public static final String GeoMapScaleHeightShouldBePositive = "GeoMapScale_HeightShouldBePositive";

	// GeoMapScale�ı���Ӧ�ô���0
	public static final String GeoMapScaleScaleShouldBePositive = "GeoMapScale_ScaleShouldBePositive";

	// GeoMapScale��С�ڸ���Ӧ�ô��ڵ���2��С�ڵ���20
	public static final String GeoMapScaleSegmentCountOutOfBounds = "GeoMapScale_SegmentCountOutOfBounds";

	// GeoMapScale��С�ڳ��ȱ�����ڵ���0
	public static final String GeoMapScaleSegmentLengthShouldNotBeNegative = "GeoMapScale_SegmentLengthShouldNotBeNegative";

	// GeoMap��֧�ֵ�GeometryType
	public static final String GeoMapUnsupprotGeometryType = "GeoMap_UnsupprotGeometryType";

	// GeoMap�������µĹ����ռ�ǰ��Ҫ�ͷžɵĹ����ռ�
	public static final String GeoMapDontDisposeOldWorkspace = "GeoMap_DontDisposeOldWorkspace";

	// GeoMap�����õ��µ�ͼ����������
	public static final String GeoMapNameIsNotInWorkspace = "GeoMap_NameIsNotInWorkspace";

	// GeoNorthArrow��Χ�Ŀ��Ӧ��������
	public static final String GeoNorthArrowBoundsWidthShouldBePositive = "GeoNorthArrow_BoundsWidthShouldBePositive";

	// GeoNorthArrow��Χ�ĸ߶�Ӧ��������
	public static final String GeoNorthArrowBoundsHeightShouldBePositive = "GeoNorthArrow_BoundsHeightShouldBePositive";

	// GeoNorthArrowͼƬ������
	public static final String GeoNorthArrowTheImageFileIsNotExist = "GeoNorthArrow_TheImageFileIsNotExist";

	// GeoNorthArrow��BindingGeoMapIDӦ��������
	public static final String GeoNorthArrowBindingGeoMapIDShouldBePositive = "GeoNorthArrow_BindingGeoMapIDShouldBePositive";

	// point2DS������������2
	public static final String Point2DsPointCountShouldMoreThanTwo = "Point2Ds_PointCountShouldMoreThanTwo";

	// GeoPlacemark������setGeometryΪGeoPlacemark���͵�
	public static final String GeoPlacemarkSetGeometryShouldNotBeGeoPlacemark = "GeoPlacemark_SetGeometryShouldNotBeGeoPlacemark";

	// datasets�������ݼ�ʱ�����������ݼ������������ַ�������30���׳��쳣
	public static final String DatasetsCreateDatasetNameLengthMoreThanThirty = "Datasets_CreateDatasetNameLengthMoreThanThirty";

	/*------------------------>
	 |
	 |
	 |   ���ϵ���ԴΪAlpha�ģ��Ѿ���������
	 |   �Ժ���ӵĲ��ַŵ����棬���Ժ�ͳһ����
	 |
	 |
	 ��
	 */
	// SymbolGroups��û��SymbolGroup
	public static final String SymbolGroupsNotContainTheSymbolGroup = "SymbolGroups_NotContainTheSymbolGroup";

	// ��ǰ���ݼ���֧�ִ˿ռ�����
	public static final String DatasetVectorBuildSpatialIndexUnsupportTheSpatialIndexType = "DatasetVector_BuildSpatialIndexUnsupportTheSpatialIndexType";

	// ��׼ͼ��ͼ������½ǵ㲻���Ǹ���
	public static final String StandardMarginLeftBottomXShouldNotBeNegative = "StandardMargin_LeftBottomXShouldNotBeNegative";

	// ��׼ͼ��ͼ������½ǵ㲻���Ǹ���
	public static final String StandardMarginLeftBottomYShouldNotBeNegative = "StandardMargin_LeftBottomYShouldNotBeNegative";

	// �ನ��indexes���� �� ColorSpaceTypeһ��
	public static final String DatasetImageIndexesMustCompatibilityWithColorSpaceType = "DatasetImage_Indexes_Must_Compatibility_With_ColorSpaceType";

	// DatasetGridӰ�����ݼ���֧�ָ����ظ�ʽ
	public static final String DatasetGridInfoUnSupportedPixelFormat = "DatasetGridInfo_UnSupportedPixelFormat";

	// �ļ�������
	public static final String GlobalFileNotExists = "Global_FileNotExists";

	// Geometrist��Smooth�����Ĺ⻬ϵ��Ӧ����[2,10]
	public static final String GeometristSmoothSmoothnessShouldBetweenTwoAndTen = "Geometrist_SmoothSmoothnessShouldBetweenTwoAndTen";

	// Geometrist��Smooth��������㴮�����ĸ���
	public static final String GeometristSmoothPointsCountShouldNotSmallThanFour = "Geometrist_SmoothPointsCountShouldNotSmallThanFour";

	public static final String DatasetUnsupportedOperation = "Dataset_SetPrjCoordSysUnsupportedTheEngineType";

	public static final String RecordsetBatchEditorIsBeginning = "Recordset_BatchEditorIsBeginning";

	/*------------------------>
	 |
	 |
	 |   ���ϵ���ԴΪBeta�ģ��Ѿ���������
	 |   �Ժ���ӵĲ��ַŵ����棬���Ժ�ͳһ����
	 |
	 |
	 ��
	 */

	public static final String RecordsetStatisticUnsupprotFieldType = "Recordset_StatisticUnsupprotFieldType";

	// �������²���������begin֮ǰ
	public static final String SetMaxRecordCountMustBeforeBegin = "SetMaxRecordCount_Must_BeforeBegin";

	public static final String GeoLegendItemNameMustInItems = "GeoLegend_ItemNameMustInItems";

	public static final String GeoLegendWorkspaceCANNOTBENULL = "GeoLegend_WorkspaceCANNOTBENULL";

	public static final String GeoLegendMapNameMustInWorkspace = "GeoLegend_MapNameMustInWorkspace";

	// ����
	public static final String MILIMETER = "MiliMeter";

	// ����
	public static final String CENTIMETER = "CentiMeter";

	// ����
	public static final String DECIMETER = "DeciMeter";

	// ��
	public static final String METER = "Meter";

	// ǧ��
	public static final String KILOMETER = "KiloMeter";

	// Ӣ��
	public static final String INCH = "Inch";

	// Ӣ��
	public static final String FOOT = "Foot";

	// ��
	public static final String YARD = "Yard";

	// Ӣ��
	public static final String MILE = "Mile";

	// ��
	public static final String SECOND = "Second";

	// ��
	public static final String MINUTE = "Minute";

	// ��
	public static final String DEGREE = "Degree";

	// ����
	public static final String RADIAN = "Radian";

	// ��������smid
	public static final String MAPCanNotContainSMID = "MAPCannotContainSMID";

	/*------------------------>
	 |
	 |
	 |   ���ϵ���ԴΪ��ʽ��ǰ�ģ��Ѿ���������
	 |   �Ժ���ӵĲ��ַŵ����棬���Ժ�ͳһ����
	 |
	 |
	 ��
	 */
	
	// �򿪻򴴽�Ŀ������Դʧ��
	public static final String ImportSettingFailToOpenOrCreateTargetDatasource = "ImportSetting_FailToOpenOrCreateTargetDatasource";

	//ImportDataInfoMIFָ���ֶ���������Ŀ�������ֶμ���
	public static final String ImportDataInfoMIFFieldNameIsNotExist = "ImportDataInfoMIF_FieldNameIsNotExist";

	//ImportDataInfoTABָ���ֶ���������Ŀ�������ֶμ���
	public static final String ImportDataInfoTABFieldNameIsNotExist = "ImportDataInfoTAB_FieldNameIsNotExist";

	//ImportDataInfoMIFָ���ֶ����Ѿ�����Ŀ�������ֶμ���
	public static final String ImportDataInfoMIFFieldNameIsAlreadyExist = "ImportDataInfoMIF_FieldNameIsAlreadyExist";

	//ImportDataInfoTABָ���ֶ����Ѿ�����Ŀ�������ֶμ���
	public static final String ImportDataInfoTABFieldNameIsAlreadyExist = "ImportDataInfoTAB_FieldNameIsAlreadyExist";

	// �ļ��ĸ�·������ȷ
	public static final String ImportSettingTheParentPathIsNotValide = "ImportSetting_TheParentPathIsNotValide";

	//ImportSettingSHP��֧�ִ˲���
	public static final String ImportSettingSHPUnsupportedOperation = "ImportSettingSHP_UnsupportedOperation";

	//	ImportDataInfoSHPָ���ֶ����Ѿ�����Ŀ�������ֶμ���
	public static final String ImportDataInfoSHPFieldNameIsAlreadyExist = "ImportDataInfoSHP_FieldNameIsAlreadyExist";
    //ImportDataInfoConverageָ���ֶ���������Ŀ�������ֶμ���
	public static final String ImportDataInfoCoverageFieldNameIsNotExist = "ImportDataInfoCoverage_FieldNameIsNotExist";
	//ImportDataInfoSHPָ���ֶ���������Ŀ�������ֶμ���
	public static final String ImportDataInfoSHPFieldNameIsNotExist = "ImportDataInfoSHP_FieldNameIsNotExist";
	//ImportDataInfoDWGָ���ֶ���������Ŀ�������ֶμ���
	public static final String ImportDataInfoDWGFieldNameIsNotExist = "ImportDataInfoDWG_FieldNameIsNotExist";
	//ImportDataInfoDXFָ���ֶ���������Ŀ�������ֶμ���
	public static final String ImportDataInfoDXFFieldNameIsNotExist = "ImportDataInfoDXF_FieldNameIsNotExist";
	
	//ImportDataInfoָ���ֶ���������Ŀ�������ֶμ���
	public static final String ImportDataInfoFieldNameIsNotExist = "ImportDataInfo_FieldNameIsNotExist";
	
	//ImportDataInfoָ���ֶ����Ѿ�����Ŀ�������ֶμ���
	public static final String ImportDataInfoFieldNameIsAlreadyExist = "ImportDataInfo_FieldNameIsNotExist";
	
	//ImportDataInfoSDEVectorָ���ֶ����Ѿ�����Ŀ�������ֶμ���
	public static final String ImportDataInfoSDEVectorFieldNameIsAlreadyExist = "ImportDataInfoDXF_FieldNameIsNotExist";
	//ImportDataInfoSDEVectorָ���ֶ���������Ŀ�������ֶμ���
	public static final String ImportDataInfoSDEVectorFieldNameIsNotExist = "ImportDataInfoDXF_FieldNameIsNotExist";
	
	//ImportDataInfoGDBFVectorָ���ֶ����Ѿ�����Ŀ�������ֶμ���
	public static final String ImportDataInfoGDBFVectorFieldNameIsAlreadyExist = "ImportDataInfoDXF_FieldNameIsNotExist";
	//ImportDataInfoGDBFVectorָ���ֶ���������Ŀ�������ֶμ���
	public static final String ImportDataInfoGDBFVectorFieldNameIsNotExist = "ImportDataInfoDXF_FieldNameIsNotExist";
	
	//ImportDataInfoGMLָ���ֶ���������Ŀ�������ֶμ���
	public static final String ImportDataInfoGMLFieldNameIsNotExist = "ImportDataInfoGML_FieldNameIsNotExist";
	//ȱ��fme��ʽ�����
	public static final String Fme_Lack_FmeLicense = "Fme_Lack_FmeLicense";
	//fme��ʽ��֧�ֵ��ļ�����
	public static final String Fme_UnSupportedFileType = "Fme_UnSupportedFileType";
	//��fme��ʽ�²�֧�ֵ��ļ�����
	public static final String UnFme_UnSupportedFileType ="UnFme_UnSupportFileType";
	
	
	
	//ImportDataInfoSHPָ���ֶ���������Ŀ�������ֶμ���
	public static final String ImportDataInfoE00FieldNameIsNotExist = "ImportDataInfoE00_FieldNameIsNotExist";
	//��������FileType
	public static final String SetTargetFileTypeIsNotAllowed = "SetTargetFileTypeIsNotAllowed";
	
	//JPGѹ���ʵķ�ΧӦ����[0,100]
	public static final String ExportSettingJPGTheCompressionShouldBetweenZeroAndHundred = "ExportSettingJPG_TheCompressionShouldBetweenZeroAndHundred";
	
	//Grid��֧�ֵ���Ϊ�ನ��
	public static final String ImportSetting_GridCannotSuportMultiBandImportMode = "ImportSetting_GridCannotSuportMultiBandImportMode";
	
	
	public static final String RecordsetValuesLengthShouldEqualsFieldCount = "Recordset_ValuesLengthShouldEqualsFieldCount";

	public static final String DatasourceRefreshUnsupportEngingType = "Datasource_RefreshUnsupportEngingType";

	public static final String DatasetVectorShouldBeClosedBeforeBuildSpatialIndex = "DatasetVector_ShouldBeClosedBeforeBuildSpatialIndex";

	public static final String DatasetImageShouldBeClosedBeforeBuildPyramid = "DatasetImage_ShouldBeClosedBeforeBuildPyramid";
	
	public static final String DatasetGridShouldBeClosedBeforeBuildPyramid = "DatasetGrid_ShouldBeClosedBeforeBuildPyramid";

	// //////////////////////////////////////////////////////

	public static final String MarkerSymbolIDShouldNotBeNeagtive = "MarkerSymbolID_ShouldNotBeNeagtive";

	public static final String LineSymbolIDShouldNotBeNeagtive = "LineSymbolID_ShouldNotBeNeagtive";

	public static final String Marker3DRotationXShouldNotBeNeagtive = "Marker3DRotationX_ShouldNotBeNeagtive";

	public static final String Marker3DRotationYShouldNotBeNeagtive = "Marker3DRotationY_ShouldNotBeNeagtive";

	public static final String Marker3DRotationZShouldNotBeNeagtive = "Marker3DRotationZ_ShouldNotBeNeagtive";

	public static final String Marker3DScaleXShouldNotBeNeagtive = "Marker3DScaleX_ShouldNotBeNeagtive";

	public static final String Marker3DScaleYShouldNotBeNeagtive = "Marker3DScaleY_ShouldNotBeNeagtive";

	public static final String Marker3DScaleZShouldNotBeNeagtive = "Maker3DScaleZ_ShouldNotBeNeagtive";

	public static final String LineSymbolSegmentCountShouldNotBeNeagtive = "LineSymbolSegmentCount_ShouldNotBeNeagtive";

	public static final String TopFillSymbolIDShouldNotBeNeagtive = "TopFillSymbolID_ShouldNotBeNeagtive";

	public static final String LineWidthShouldNotBeNegative = "LineWidth_ShouldNotBeNegative";

	public static final String TransformationControlPointsNull = "Transformation_ControlPointsNull";

	public static final String TransformationControlPointsShouldNotSmallerThanOne = "Transformation_ControlPointsShouldNotSmallerThanOne";

	public static final String TransformationControlPointsShouldNotBeZero = "Transformation_ControlPointsShouldNotBeZero";

	public static final String TransformationOriginalAndTargetControlPointsCountMustEqual = "Transformation_OriginalAndTargetControlPointsCountMustEqual";

	public static final String TransformationRectModeNeedLeastTwoControlPoints = "Transformation_RectModeNeedLeastTwoControlPoints";

	public static final String TransformationLinearModeNeedLeastFourControlPoints = "Transformation_LinearModeNeedLeastFourControlPoints";

	public static final String TransformationSquareModeNeedLeastSevenControlPoints = "Transformation_SquareModeNeedLeastSevenControlPoints";

	public static final String ArgumentShouldBePositive = "Argument_ShouldBePositive";

	public static final String MeshesRemoveRangeCountInvalid = "Meshes_RemoveRange_Count_Invalid";

	public static final String ValueShouldNotBeNeagtive = "ValueShouldNotBeNeagtive"; 
	
	public static final String TubeSidesCountShouldNotBeNeagtive ="TubeSidesCountShouldNotBeNeagtive";
	
	/**
	 * ��ʽ���
	 */
	public static final String FormalLicense = "Formal_License";
	/**
	 * �������
	 */
	public static final String TrialLicense = "Trial_License";
	
	/**
	 * ��Ч���
	 */
	public static final String ValidLicense = "Valid_License";
	/**
	 * ��Ч���
	 */
	public static final String InvaliLicense = "Invalid_License";
	
	/**
	 *��ɲ�����
	 */
	public static final String LicenseNotExsit = "Licnse_Not_Exsit";
}
