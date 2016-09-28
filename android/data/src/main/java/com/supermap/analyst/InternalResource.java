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
	 * 从资源中加载字符串
	 * 
	 * @param key
	 *            String
	 * @param message
	 *            String 自己输入的消息
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

	// Bundle名 protected
	// ----------------------------------------------------------------------------------------
	public static final String BundleName = "spatialanalyst_resources";

	// 全局Key,public
	// ----------------------------------------------------------------------------------------

	/**
	 * 索引超出范围
	 */
	public static final String GlobalIndexOutOfBounds = "Global_IndexOutOfBounds";

	/**
	 * Enum 值错误
	 */
	public static final String GlobalEnumValueIsError = "Global_EnumValueIsError";

	// 不是有效的路径
	public static final String GlobalPathIsNotValid = "Global_PathIsNotValid";

	// 不是有效的枚举类，要求所有的枚举类都派生至Enum
	public static final String GlobalEnumInvalidDerivedClass = "Global_EnumInvalidDerivedClass";

	// 传入的参数对象已经释放掉了
	public static final String GlobalArgumentObjectHasBeenDisposed = "Global_ArgumentObjectHasBeenDisposed";

	// 参数的类型不正确
	public static final String GlobalArgumentTypeInvalid = "Global_ArgumentTypeInvalid";

	// 参数为空
	public static final String GlobalArgumentNull = "Global_ArgumentNull";

	// 插入的位置不正确，插入的index只能为0-count(包括count)
	public static final String GlobalInvalidInsertPosition = "Global_InvalidInsertPosition";

	// 对象所属的对象已经释放
	public static final String GlobalOwnerHasBeenDisposed = "Global_OwnerHasBeenDisposed";

	// Handle
	// ---------------------------------------------------------------------------------------
	// InternalHandleDisposable不能通过调用setHandle来初始化对象
	public static final String HandleDisposableCantCreate = "Handle_DisposableCantCreate";

	// 原对象尚未释放
	public static final String HandleOriginalObjectHasNotBeenDisposed = "Handle_OriginalObjectHasNotBeenDisposed";

	/**
	 * 对象不能被释放，不能调用Dispose方法
	 */
	public static final String HandleUndisposableObject = "Handle_UndisposableObject";

	/**
	 * 对象已经被释放
	 */
	public static final String HandleObjectHasBeenDisposed = "Handle_ObjectHasBeenDisposed";

	// 不能设置IsDisposable属性
	public static final String HandleCantSetIsDisposable = "Handle_CantSetIsDisposable";

	// ____________________以下为分析新增资源_____________________________

	// 缓冲区分析半径要大于0
	public static final String BufferAnalystBufferDistanceShouldBePositive = "BufferAnalyst_BufferDistanceShouldBePositive";

	// 缓冲分析半径不能等于0
	public static final String BufferAnalystBufferDistanceShouldNotBeZero = "BufferAnalyst_BufferDistanceShouldNotBeZero";

	// 缓冲分析的几何对象类型不合法
	public static final String BufferAnalystBufferGeometryTypeInvalid = "BufferAnalyst_BufferGeometryTypeInvalid";

	// 缓冲分析的几何对象必须是线
	public static final String BufferAnalystBufferGeometryTypeShouldBeLine = "BufferAnalyst_BufferGeometryTypeShouldBeLine";

	// 缓冲分析的数据集类型不合法
	public static final String BufferAnalystBufferDatasetTypeInvalid = "BufferAnalyst_BufferDatasetTypeInvalid";

	// 缓冲分析的数据集类型必须是线
	public static final String BufferAnalystBufferDatasetTypeShouldBeLine = "BufferAnalyst_BufferDatasetTypeShouldBeLine";

	// 缓冲分析结果数据集类型不合法
	public static final String BufferAnalystResultDatasetTypeInvalid = "BufferAnalyst_ResultDatasetTypeInvalid";

	// 叠加分析结果数据集类型不合法
	public static final String OverlayAnalystResultDatasetTypeInvalid = "OverlayAnalyst_ResultDatasetTypeInvalid";

	// 半圆弧线段个数要大于等于4
	public static final String BufferAnalystSemicircleLineSegmentShouldEqualsOrGreaterThanFour = "BufferAnalyst_SemicircleLineSegmentShouldEqualsOrGreaterThanFour";

	// 数据集或记录集类型不合法，必须为面
	public static final String OverlayAnalystDatsetOrRecordsetTypeInvalidShouldBeRegion = "OverlayAnalyst_DatsetOrRecordsetTypeInvalidShouldBeRegion";

	// 圆头缓冲区分析不支持左右缓冲距离都大于0且不相等的情况
	public static final String BufferAnalystRoundBufferNotSupportDifferBufferDistance = "BufferAnalyst_RoundBufferNotSupportDifferentLeftRightBufferDistance";

	// 缓冲距离表达式不能为空或者空字符串
	public static final String BufferAnalystBufferDistanceExpressionShouldNotBeNullOrEmpty = "BufferAnalyst_BufferDistanceExpressionShouldNotBeNullOrEmpty";

	// Sting 执行trim后为空
	public static final String GlobalStringIsEmpty = "Global_StringIsEmpty";

	// String为null或者为空
	public static final String GlobalStringIsNullOrEmpty = "Global_StringIsNullOrEmpty";

	// 容限必须大于等于0
	public static final String OverlayAnalystParameterToleranceShouldEqualsOrGreaterThanZero = "OverlayAnalystParameter_ToleranceShouldEqualsOrGreaterThanZero";

	// 叠加分析时源数据集与操作数据集不能是相同的数据集
	public static final String OverlayAnalystSourceDatasetAndOperationDatasetShouldBeDifferent = "OverlayAnalyst_SourceDatasetAndOperationDatasetShouldBeDifferent";

	// 文件不存在
	public static final String GlobalFileNotFound = "Global_FileNotFound";

	// 必须大于0
	public static final String GlobalShouldGreaterThanZero = "Global_ShouldGreaterThanZero";

	// 中文地址模糊匹配分析需要Load
	public static final String AddressMatchLoadNeeded = "AddressMatch_LoadNeeded";

	// 数组的长度必须大于0
	public static final String GlobalArrayLengthShouldGreaterThanZero = "Global_ArrayLengthShouldGreaterThanZero";

	// SearchData已经存在
	public static final String AddressMatchSettingSearchDataAlreadyExist = "AddressMatchSetting_SearchDataAlreadyExist";

	// 指定的文件夹不存在
	public static final String GlobalDirectoryNotFound = "GlobalDirectoryNotFound";

	// 指定的单词已经存在
	public static final String AddressDictionaryWordAlreadyExist = "AddressDictionary_WordAlreadyExist";

	/*------------------------>
	 |
	 |
	 |   以上的资源为2.0正式版本的，已经经过整理。
	 |   以后添加的部分放到下面，待以后统一整理。
	 |
	 |
	 △
	 */

	// 值不能为非点类型
	public static final String ProximityAnalystNotPointType = "ProximityAnalystNotPointType";

	// 数据集名称不合法
	public static final String GlobalDatasetNameInvalid = "GlobalDatasetNameInvalid";

	// add by xuzw 2008-12-23
	// 参数设置不符合要求
	// 可以是String和数值类型
	public static final String BufferAnalystParameterInvalidObject = "BufferAnalystParameter_InvalidObject";

	public static final String BufferAnalystDistenceOfStringTypeIsInvalidForGeometryBuffer = "BufferAnalyst_DistenceOfStringTypeIsInvalidForGeometryBuffer";

	public static final String BufferAnalystLeftDistenceAndRightDistanceMustBeTheSameType = "BufferAnalyst_LeftDistenceAndRightDistanceMustBeTheSameType";

	// 资源名称
	// 值范围应该在[0，5]之间
	public static final String SurfaceExtractParameter_BetweenZeroAndFive = "SurfaceExtractParameter_BetweenZeroAndFive";

	// 数据集名称不合法
	public static final String GlobalDatasetNameInvalide = "GlobalDatasetNameInvalide";

	// 字段名称不存在
	public static final String GlobalSpecifiedNameNotExists = "GlobalSpecifiedNameNotExists";

	// 值必须大于0
	public static final String GlobalGreaterThanZero = "GlobalGreaterThanZero";

	// 对象已经释放
	public static final String GlobalHandleObjectHasBeenDisposed = "GlobalHandleObjectHasBeenDisposed";

	// 不支持的类型
	public static final String GlobalUnsportedType = "GlobalUnsportedType";

	// =============================〉
	// 以上部分资源在08.05.30整理。

	// 值不能为非点类型
	public static final String SurfaceAnalystNotPointType = "SurfaceAnalystNotPointType";

//	 几何对象类型不合法，必须为面
	public static final String OverlayAnalystGeometryTypeInvalidShouldBeRegion = "OverlayAnalyst_GeometryTypeInvalidShouldBeRegion";
}
