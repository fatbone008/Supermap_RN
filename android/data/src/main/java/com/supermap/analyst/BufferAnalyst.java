package com.supermap.analyst;

import java.util.ArrayList;
import java.util.Vector;

import com.supermap.analyst.InternalToolkitSpatialAnalyst.BufferSideType;
import com.supermap.data.*;

/**
 * <p>
 * Title:缓冲区分析类
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
 * @author 张继南
 * @version 2.0
 */
public class BufferAnalyst {

	private transient static Vector<SteppedListener> m_steppedListeners;

	private BufferAnalyst() {

	}

	/**
	 * 创建矢量数据集缓冲区
	 * 
	 * @param sourceDataset
	 *            DatasetVector 创建缓冲区的源矢量数据集
	 * @param resultDataset
	 *            DatasetVector 存储缓冲区分析结果的数据集
	 * @param bufferAnalystParameter
	 *            BufferAnalystParameter 缓冲分析参数对象
	 * @param isUnion
	 *            boolean 是否合并缓冲区
	 * @param isAttributeRetained
	 *            boolean 是否保留属性字段,只当isUnion为false时有效
	 * @return boolean
	 */
	public static boolean createBuffer(DatasetVector sourceDataset,
			DatasetVector resultDataset,
			BufferAnalystParameter bufferAnalystParameter, boolean isUnion,
			boolean isAttributeRetained) {

		// 参数类解析
		BufferEndType endType = bufferAnalystParameter.getEndType();

		BufferRadiusUnit radiusUnit = bufferAnalystParameter.getRadiusUnit();

		int semicircleLineSegment = bufferAnalystParameter
				.getSemicircleLineSegment();
		Object objLeftDistance = bufferAnalystParameter.getLeftDistance();
		Object objRightDistance = bufferAnalystParameter.getRightDistance();

		// if(objLeftDistance == null && objRightDistance == null) {
		// String message = InternalResource.loadString(
		// "objLeftDistance and objRightDistance",
		// InternalResource.GlobalArgumentNull,
		// InternalResource.BundleName);
		// throw new NullPointerException(message);
		// }

		// String strLeftDistance = (String)objLeftDistance;
		// String strRightDistance = (String)objRightDistance;
		// double leftDistance = objectToDouble(objLeftDistance);
		// double rightDistance = objectToDouble(objRightDistance);

		String strLeftDistance = null;
		String strRightDistance = null;
		double leftDistance = 0;
		double rightDistance = 0;

		// 获得handle
		if (sourceDataset == null
				|| InternalHandle.getHandle(sourceDataset) == 0) {
			String message = InternalResource.loadString("sourceDataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (resultDataset == null
				|| InternalHandle.getHandle(resultDataset) == 0) {
			String message = InternalResource.loadString("resultDataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long sourceDatasetHandle = InternalHandle.getHandle(sourceDataset);
		long resultDatasetHandle = InternalHandle.getHandle(resultDataset);

		// 判断结果数据集类型，必须是面
		DatasetType resultDatasetType = resultDataset.getType();
		if (resultDatasetType != DatasetType.REGION) {
			String message = InternalResource.loadString("",
					InternalResource.BufferAnalystResultDatasetTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		int ugcBufferSideType = -1;

		boolean result = false;

		DatasetType datasetType = sourceDataset.getType();

		// 平头缓冲
		if (endType.equals(BufferEndType.FLAT)) {
			// 平头缓冲，左右缓冲同时为null，抛出异常
			if (objLeftDistance == null && objRightDistance == null) {
				String message = InternalResource.loadString(
						"objLeftDistance and objRightDistance",
						InternalResource.GlobalArgumentNull,
						InternalResource.BundleName);
				throw new NullPointerException(message);
			}
			if (isUnion) {
				isAttributeRetained = false;
			}
			// 应该是线或网络
			if (datasetType != DatasetType.LINE
					&& datasetType != DatasetType.NETWORK) {
				String message = InternalResource.loadString("",
						InternalResource.BufferAnalystBufferDatasetTypeInvalid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}

			// 左右缓冲的类型只能是String或者数值类型，这在参数类中已经作了判断。当左右缓冲
			// 都不为null时，如果它们不同时是String，要抛异常，其他的数值类型不作要求
			if (objLeftDistance != null
					&& objRightDistance != null
					&& ((objLeftDistance instanceof String) ^ (objRightDistance instanceof String))) {
				String message = InternalResource
						.loadString(
								"",
								InternalResource.BufferAnalystLeftDistenceAndRightDistanceMustBeTheSameType,
								InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}

			// 右边是null,则是左缓冲
			if (objRightDistance == null) {
				// 是字段
				if (objLeftDistance instanceof String) {
					strLeftDistance = (String) objLeftDistance;
					if (strLeftDistance == null
							|| strLeftDistance.trim().length() == 0) {
						String message = InternalResource.loadString(
								"LeftDistance",
								InternalResource.GlobalStringIsNullOrEmpty,
								InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}
				} else {
					// 是数值
					leftDistance = InternalToolkitSpatialAnalyst
							.objectToDouble(objLeftDistance);
					if (leftDistance <= 0) {
						String message = InternalResource
								.loadString(
										"leftDistance",
										InternalResource.BufferAnalystBufferDistanceShouldBePositive,
										InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}
				}
				ugcBufferSideType = BufferSideType.LEFT.value();
			}

			// 左边是null，则是右缓冲
			if (objLeftDistance == null) {
				if (objRightDistance instanceof String) {
					strRightDistance = (String) objRightDistance;
					if (strRightDistance == null
							|| strRightDistance.trim().length() == 0) {
						String message = InternalResource.loadString(
								"LeftDistance",
								InternalResource.GlobalStringIsNullOrEmpty,
								InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}
				} else {
					rightDistance = InternalToolkitSpatialAnalyst
							.objectToDouble(objRightDistance);
					// 缓冲距离不是正数，抛出异常
					if (rightDistance <= 0) {
						String message = InternalResource
								.loadString(
										"rightDistance",
										InternalResource.BufferAnalystBufferDistanceShouldBePositive,
										InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}
				}
				ugcBufferSideType = BufferSideType.RIGHT.value();
			}

			// 左右都不是null
			if (objLeftDistance != null && objRightDistance != null) {
				// 左缓冲是String，那必然左右都是字段（若不是前面会抛出异常）
				if (objLeftDistance instanceof String) {
					strLeftDistance = (String) objLeftDistance;
					if (strLeftDistance == null
							|| strLeftDistance.trim().length() == 0) {
						String message = InternalResource.loadString(
								"LeftDistance",
								InternalResource.GlobalStringIsNullOrEmpty,
								InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}

					strRightDistance = (String) objRightDistance;
					if (strRightDistance == null
							|| strRightDistance.trim().length() == 0) {
						String message = InternalResource.loadString(
								"RightDistance",
								InternalResource.GlobalStringIsNullOrEmpty,
								InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}

					if (strLeftDistance.equals(strRightDistance)) {
						ugcBufferSideType = BufferSideType.FULL.value();
					} else {
						ugcBufferSideType = BufferSideType.FULLDIFFR.value();
					}

				} else {
					leftDistance = InternalToolkitSpatialAnalyst
							.objectToDouble(objLeftDistance);
					if (leftDistance <= 0) {
						String message = InternalResource
								.loadString(
										"leftDistance",
										InternalResource.BufferAnalystBufferDistanceShouldBePositive,
										InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}
					rightDistance = InternalToolkitSpatialAnalyst
							.objectToDouble(objRightDistance);
					if (rightDistance <= 0) {
						String message = InternalResource
								.loadString(
										"rightDistance",
										InternalResource.BufferAnalystBufferDistanceShouldBePositive,
										InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}
					if (leftDistance == rightDistance) {
						ugcBufferSideType = BufferSideType.FULL.value();
					} else {
						ugcBufferSideType = BufferSideType.FULLDIFFR.value();
					}
				}
			}

			int ugcBufferEndType = BufferEndType.FLAT.value();

			if (strLeftDistance != null || strRightDistance != null) {
				result = BufferAnalystNative.jni_CreateLineDatasetBuffer2(
						sourceDatasetHandle, 0, resultDatasetHandle,
						strLeftDistance, strRightDistance, radiusUnit.value(),
						semicircleLineSegment, ugcBufferSideType,
						ugcBufferEndType, isUnion, isAttributeRetained);
			} else {
				result = BufferAnalystNative.jni_CreateLineDatasetBuffer(
						sourceDatasetHandle, 0, resultDatasetHandle,
						leftDistance, rightDistance, radiusUnit.value(),
						semicircleLineSegment, ugcBufferSideType,
						ugcBufferEndType, isUnion, isAttributeRetained);
			}
		}

		// 如果是圆头缓冲，就存在四种情况
		if (endType.equals(BufferEndType.ROUND)) {
			// 圆头缓冲，只要左缓冲是null，就抛异常
			if (objLeftDistance == null) {
				String message = InternalResource.loadString("objLeftDistance",
						InternalResource.GlobalArgumentNull,
						InternalResource.BundleName);
				throw new NullPointerException(message);
			}

			if (isUnion) {
				isAttributeRetained = false;
			}

			// 数据集类型只能是点，线，面，网络
			if (datasetType != DatasetType.LINE
					&& datasetType != DatasetType.NETWORK
					&& datasetType != DatasetType.POINT
					&& datasetType != DatasetType.REGION) {
				String message = InternalResource.loadString("",
						InternalResource.BufferAnalystBufferDatasetTypeInvalid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}

			if (objLeftDistance instanceof String) {
				strLeftDistance = (String) objLeftDistance;
				if (strLeftDistance == null
						|| strLeftDistance.trim().length() == 0) {
					String message = InternalResource.loadString(
							"LeftDistance",
							InternalResource.GlobalStringIsNullOrEmpty,
							InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
			} else {
				leftDistance = InternalToolkitSpatialAnalyst
						.objectToDouble(objLeftDistance);
				// 不是面的圆头缓冲，缓冲距离必须是正数；是面的圆头缓冲，缓冲距离不为0
				if (!datasetType.equals(DatasetType.REGION)
						&& leftDistance <= 0) {
					String message = InternalResource
							.loadString(
									"",
									InternalResource.BufferAnalystBufferDistanceShouldBePositive,
									InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				} else if (datasetType.equals(DatasetType.REGION)
						&& Toolkit.isZero(leftDistance)) {
					String message = InternalResource
							.loadString(
									"",
									InternalResource.BufferAnalystBufferDistanceShouldNotBeZero,
									InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
			}

			// 线数据集和网络数据集需要将BufferSideType调整为FULL
			if (datasetType.equals(DatasetType.LINE)
					|| datasetType.equals(DatasetType.NETWORK)) {
				ugcBufferSideType = BufferSideType.FULL.value();
			}

			int ugcBufferEndType = BufferEndType.ROUND.value();

			if (strLeftDistance != null) {
				result = BufferAnalystNative.jni_CreateDatasetBuffer2(
						sourceDatasetHandle, 0, resultDatasetHandle,
						strLeftDistance, radiusUnit.value(),
						semicircleLineSegment, ugcBufferSideType,
						ugcBufferEndType, isUnion, isAttributeRetained);
			} else {
				result = BufferAnalystNative.jni_CreateDatasetBuffer(
						sourceDatasetHandle, 0, resultDatasetHandle,
						leftDistance, radiusUnit.value(),
						semicircleLineSegment, ugcBufferSideType,
						ugcBufferEndType, isUnion, isAttributeRetained);
			}
		}

		return result;
	}

	/**
	 * 创建矢量记录缓冲区
	 * 
	 * @param sourceRecordset
	 *            Recordset 创建缓冲区的源矢量记录集
	 * @param resultDataset
	 *            DatasetVector 存储缓冲区分析结果的数据集
	 * @param bufferAnalystParameter
	 *            BufferAnalystParameter 缓冲分析参数对象
	 * @param isUnion
	 *            boolean 是否合并缓冲区
	 * @param isAttributeRetained
	 *            boolean 是否保留属性字段,只当isUnion为false时有效
	 * @return boolean
	 */
	public static boolean createBuffer(Recordset sourceRecordset,
			DatasetVector resultDataset,
			BufferAnalystParameter bufferAnalystParameter, boolean isUnion,
			boolean isAttributeRetained) {

		// 参数类解析
		BufferEndType endType = bufferAnalystParameter.getEndType();
		BufferRadiusUnit radiusUnit = bufferAnalystParameter.getRadiusUnit();
		int semicircleLineSegment = bufferAnalystParameter
				.getSemicircleLineSegment();
		Object objLeftDistance = bufferAnalystParameter.getLeftDistance();
		Object objRightDistance = bufferAnalystParameter.getRightDistance();

		// String strLeftDistance = (String)objLeftDistance;
		// String strRightDistance = (String)objRightDistance;
		// double leftDistance = objectToDouble(objLeftDistance);
		// double rightDistance = objectToDouble(objRightDistance);

		String strLeftDistance = null;
		String strRightDistance = null;
		double leftDistance = 0;
		double rightDistance = 0;

		// 获得handle
		if (sourceRecordset == null
				|| InternalHandle.getHandle(sourceRecordset) == 0) {
			String message = InternalResource.loadString("sourceRecordset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (resultDataset == null
				|| InternalHandle.getHandle(resultDataset) == 0) {
			String message = InternalResource.loadString("resultDataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long sourceRecordsetHandle = InternalHandle.getHandle(sourceRecordset);
		long resultDatasetHandle = InternalHandle.getHandle(resultDataset);

		// 判断结果数据集类型，必须是面
		DatasetType resultDatasetType = resultDataset.getType();
		if (resultDatasetType != DatasetType.REGION) {
			String message = InternalResource.loadString("",
					InternalResource.BufferAnalystResultDatasetTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		int ugcBufferSideType = -1;

		boolean result = false;

		DatasetType datasetType = sourceRecordset.getDataset().getType();

		// 如果是平头缓冲，则应该是线或者网络
		if (endType.equals(BufferEndType.FLAT)) {
			// 平头缓冲，左右缓冲同时为null，抛出异常
			if (objLeftDistance == null && objRightDistance == null) {
				String message = InternalResource.loadString(
						"objLeftDistance and objRightDistance",
						InternalResource.GlobalArgumentNull,
						InternalResource.BundleName);
				throw new NullPointerException(message);
			}
			if (isUnion) {
				isAttributeRetained = false;
			}
			// 应该是线或网络
			if (datasetType != DatasetType.LINE
					&& datasetType != DatasetType.NETWORK) {
				String message = InternalResource.loadString("",
						InternalResource.BufferAnalystBufferDatasetTypeInvalid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}

			// 左右缓冲的类型只能是String或者数值类型，这在参数类中已经作了判断。当左右缓冲
			// 都不为null时，如果它们不同时是String，要抛异常，其他的数值类型不作要求
			if (objLeftDistance != null
					&& objRightDistance != null
					&& ((objLeftDistance instanceof String) ^ (objRightDistance instanceof String))) {
				String message = InternalResource
						.loadString(
								"",
								InternalResource.BufferAnalystLeftDistenceAndRightDistanceMustBeTheSameType,
								InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}

			// 右边是null，则是左缓冲
			if (objRightDistance == null) {
				if (objLeftDistance instanceof String) {
					strLeftDistance = (String) objLeftDistance;
					if (strLeftDistance == null
							|| strLeftDistance.trim().length() == 0) {
						String message = InternalResource.loadString(
								"LeftDistance",
								InternalResource.GlobalStringIsNullOrEmpty,
								InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}
				} else {
					leftDistance = InternalToolkitSpatialAnalyst
							.objectToDouble(objLeftDistance);
					if (leftDistance <= 0) {
						String message = InternalResource
								.loadString(
										"leftDistance",
										InternalResource.BufferAnalystBufferDistanceShouldBePositive,
										InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}
				}
				ugcBufferSideType = BufferSideType.LEFT.value();
			}
			// 左边是null，则是右缓冲
			if (objLeftDistance == null) {
				if (objRightDistance instanceof String) {
					strRightDistance = (String) objRightDistance;
					if (strRightDistance == null
							|| strRightDistance.trim().length() == 0) {
						String message = InternalResource.loadString(
								"LeftDistance",
								InternalResource.GlobalStringIsNullOrEmpty,
								InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}
				} else {
					rightDistance = InternalToolkitSpatialAnalyst
							.objectToDouble(objRightDistance);
					// 缓冲距离不是正数，抛出异常
					if (rightDistance <= 0) {
						String message = InternalResource
								.loadString(
										"rightDistance",
										InternalResource.BufferAnalystBufferDistanceShouldBePositive,
										InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}
				}
				ugcBufferSideType = BufferSideType.RIGHT.value();
			}
			// 左右都不是null
			if (objLeftDistance != null && objRightDistance != null) {
				// 左缓冲是String，那必然左右都是字段（若不是前面会抛出异常）
				if (objLeftDistance instanceof String) {
					strLeftDistance = (String) objLeftDistance;
					if (strLeftDistance == null
							|| strLeftDistance.trim().length() == 0) {
						String message = InternalResource.loadString(
								"LeftDistance",
								InternalResource.GlobalStringIsNullOrEmpty,
								InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}

					strRightDistance = (String) objRightDistance;
					if (strRightDistance == null
							|| strRightDistance.trim().length() == 0) {
						String message = InternalResource.loadString(
								"RightDistance",
								InternalResource.GlobalStringIsNullOrEmpty,
								InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}

					if (strLeftDistance.equals(strRightDistance)) {
						ugcBufferSideType = BufferSideType.FULL.value();
					} else {
						ugcBufferSideType = BufferSideType.FULLDIFFR.value();
					}

				} else {
					leftDistance = InternalToolkitSpatialAnalyst
							.objectToDouble(objLeftDistance);
					if (leftDistance <= 0) {
						String message = InternalResource
								.loadString(
										"leftDistance",
										InternalResource.BufferAnalystBufferDistanceShouldBePositive,
										InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}
					rightDistance = InternalToolkitSpatialAnalyst
							.objectToDouble(objRightDistance);
					if (rightDistance <= 0) {
						String message = InternalResource
								.loadString(
										"rightDistance",
										InternalResource.BufferAnalystBufferDistanceShouldBePositive,
										InternalResource.BundleName);
						throw new IllegalArgumentException(message);
					}
					if (leftDistance == rightDistance) {
						ugcBufferSideType = BufferSideType.FULL.value();
					} else {
						ugcBufferSideType = BufferSideType.FULLDIFFR.value();
					}
				}
			}

			int ugcBufferEndType = BufferEndType.FLAT.value();

			if (strLeftDistance != null || strRightDistance != null) {
				result = BufferAnalystNative.jni_CreateLineDatasetBuffer2(0,
						sourceRecordsetHandle, resultDatasetHandle,
						strLeftDistance, strRightDistance, radiusUnit.value(),
						semicircleLineSegment, ugcBufferSideType,
						ugcBufferEndType, isUnion, isAttributeRetained);
			} else {
				result = BufferAnalystNative.jni_CreateLineDatasetBuffer(0,
						sourceRecordsetHandle, resultDatasetHandle,
						leftDistance, rightDistance, radiusUnit.value(),
						semicircleLineSegment, ugcBufferSideType,
						ugcBufferEndType, isUnion, isAttributeRetained);
			}
		}

		// 如果是圆头缓冲，就存在四种情况
		if (endType.equals(BufferEndType.ROUND)) {
			if (isUnion) {
				isAttributeRetained = false;
			}

			if (datasetType != DatasetType.LINE
					&& datasetType != DatasetType.NETWORK
					&& datasetType != DatasetType.POINT
					&& datasetType != DatasetType.REGION) {
				String message = InternalResource.loadString("",
						InternalResource.BufferAnalystBufferDatasetTypeInvalid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			// 圆头缓冲，只要左缓冲是null，就抛异常
			if (objLeftDistance == null) {
				String message = InternalResource.loadString("objLeftDistance",
						InternalResource.GlobalArgumentNull,
						InternalResource.BundleName);
				throw new NullPointerException(message);
			}

			if (objLeftDistance instanceof String) {
				strLeftDistance = (String) objLeftDistance;
				if (strLeftDistance == null
						|| strLeftDistance.trim().length() == 0) {
					String message = InternalResource.loadString(
							"LeftDistance",
							InternalResource.GlobalStringIsNullOrEmpty,
							InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
			} else {
				leftDistance = InternalToolkitSpatialAnalyst
						.objectToDouble(objLeftDistance);
				// 不是面的圆头缓冲，缓冲距离必须是正数；是面的圆头缓冲，缓冲距离不为0
				if (!datasetType.equals(DatasetType.REGION)
						&& leftDistance <= 0) {
					String message = InternalResource
							.loadString(
									"",
									InternalResource.BufferAnalystBufferDistanceShouldBePositive,
									InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				} else if (datasetType.equals(DatasetType.REGION)
						&& Toolkit.isZero(leftDistance)) {
					String message = InternalResource
							.loadString(
									"",
									InternalResource.BufferAnalystBufferDistanceShouldNotBeZero,
									InternalResource.BundleName);
					throw new IllegalArgumentException(message);
				}
			}
			// 线数据集和网络数据集需要将BufferSideType调整为FULL
			if (datasetType.equals(DatasetType.LINE)
					|| datasetType.equals(DatasetType.NETWORK)) {
				ugcBufferSideType = BufferSideType.FULL.value();
			}
			int ugcBufferEndType = BufferEndType.ROUND.value();

			if (strLeftDistance != null) {
				result = BufferAnalystNative.jni_CreateDatasetBuffer2(0,
						sourceRecordsetHandle, resultDatasetHandle,
						strLeftDistance, radiusUnit.value(),
						semicircleLineSegment, ugcBufferSideType,
						ugcBufferEndType, isUnion, isAttributeRetained);
			} else {
				result = BufferAnalystNative.jni_CreateDatasetBuffer(0,
						sourceRecordsetHandle, resultDatasetHandle,
						leftDistance, radiusUnit.value(),
						semicircleLineSegment, ugcBufferSideType,
						ugcBufferEndType, isUnion, isAttributeRetained);
			}
		}

		return result;
	}

	/**
	 * 创建矢量数据集多重缓冲区
	 * 
	 * @param sourceDataset
	 *            DatasetVector 创建多重缓冲区的源矢量数据集
	 * @param resultDataset
	 *            DatasetVector 存储缓冲区分析结果的数据集
	 * @param bufferRadii
	 *            double[] 多重缓冲区半径列表
	 * @param bufferRadiusUnit
	 *            BufferRadiusUnit 缓冲区半径单位
	 * @param semicircleSegments
	 *            int 弧段拟合数
	 * @param isUnion
	 *            boolean 是否合并缓冲区
	 * @param isAttributeRetained
	 *            boolean 是否保留属性字段,只当isUnion为false时有效
	 * @param isRing
	 *            boolean 是否生成环状缓冲区
	 * @return boolean
	 */
	public static boolean createMultiBuffer(DatasetVector sourceDataset,
			DatasetVector resultDataset, double[] bufferRadii,
			BufferRadiusUnit bufferRadiusUnit, int semicircleSegments,
			boolean isUnion, boolean isAttributeRetained, boolean isRing) {

		// 获得handle
		if (sourceDataset == null
				|| InternalHandle.getHandle(sourceDataset) == 0) {
			String message = InternalResource.loadString("sourceDataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (resultDataset == null
				|| InternalHandle.getHandle(resultDataset) == 0) {
			String message = InternalResource.loadString("resultDataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		for (double dRadius : bufferRadii) {
			if (dRadius <= 0) {
				String message = InternalResource.loadString("bufferRadii",
						InternalResource.GlobalGreaterThanZero,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}

		long sourceDatasetHandle = InternalHandle.getHandle(sourceDataset);
		long resultDatasetHandle = InternalHandle.getHandle(resultDataset);

		// 判断结果数据集类型，必须是面
		DatasetType resultDatasetType = resultDataset.getType();
		if (resultDatasetType != DatasetType.REGION) {
			String message = InternalResource.loadString("",
					InternalResource.BufferAnalystResultDatasetTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		boolean result = BufferAnalystNative.jni_CreateDatasetMultiBuffer(
				sourceDatasetHandle, resultDatasetHandle, bufferRadii,
				bufferRadiusUnit.value(), semicircleSegments, isUnion,
				isAttributeRetained, isRing);

		return result;
	}

	/**
	 * 创建矢量记录集多重缓冲区
	 * 
	 * @param sourceRecordset
	 *            Recordset 创建多重缓冲区的源矢量记录集
	 * @param resultDataset
	 *            DatasetVector 存储缓冲区分析结果的数据集
	 * @param bufferRadii
	 *            double[] 多重缓冲区半径列表
	 * @param bufferRadiusUnit
	 *            BufferRadiusUnit 缓冲区半径单位
	 * @param semicircleSegments
	 *            int 弧段拟合数
	 * @param isUnion
	 *            boolean 是否合并缓冲区
	 * @param isAttributeRetained
	 *            boolean 是否保留属性字段,只当isUnion为false时有效
	 * @param isRing
	 *            boolean 是否生成环状缓冲区
	 * @return boolean
	 */
	public static boolean createMultiBuffer(Recordset sourceRecordset,
			DatasetVector resultDataset, double[] bufferRadii,
			BufferRadiusUnit bufferRadiusUnit, int semicircleSegments,
			boolean isUnion, boolean isAttributeRetained, boolean isRing) {

		// 获得handle
		if (sourceRecordset == null
				|| InternalHandle.getHandle(sourceRecordset) == 0) {
			String message = InternalResource.loadString("sourceRecordset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (resultDataset == null
				|| InternalHandle.getHandle(resultDataset) == 0) {
			String message = InternalResource.loadString("resultDataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		for (double dRadius : bufferRadii) {
			if (dRadius <= 0) {
				String message = InternalResource.loadString("bufferRadii",
						InternalResource.GlobalGreaterThanZero,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}

		long sourceRecordsetHandle = InternalHandle.getHandle(sourceRecordset);
		long resultDatasetHandle = InternalHandle.getHandle(resultDataset);

		// 判断结果数据集类型，必须是面
		DatasetType resultDatasetType = resultDataset.getType();
		if (resultDatasetType != DatasetType.REGION) {
			String message = InternalResource.loadString("",
					InternalResource.BufferAnalystResultDatasetTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		boolean result = BufferAnalystNative.jni_CreateRecordsetMultiBuffer(
				sourceRecordsetHandle, resultDatasetHandle, bufferRadii,
				bufferRadiusUnit.value(), semicircleSegments, isUnion,
				isAttributeRetained, isRing);

		return result;
	}

	/**
	 * 创建矢量线数据集单边多重缓冲区
	 * 
	 * @param sourceDataset
	 *            DatasetVector 创建多重缓冲区的源矢量数据集
	 * @param resultDataset
	 *            DatasetVector 存储缓冲区分析结果的数据集
	 * @param bufferRadii
	 *            double[] 多重缓冲区半径列表
	 * @param bufferRadiusUnit
	 *            BufferRadiusUnit 缓冲区半径单位
	 * @param semicircleSegments
	 *            int 弧段拟合数
	 * @param isLeft
	 *            boolean 是否生成左缓冲
	 * @param isUnion
	 *            boolean 是否合并缓冲区
	 * @param isAttributeRetained
	 *            boolean 是否保留属性字段,只当isUnion为false时有效
	 * @param isRing
	 *            boolean 是否生成环状缓冲区
	 * @return boolean
	 */
	public static boolean createLineOneSideMultiBuffer(
			DatasetVector sourceDataset, DatasetVector resultDataset,
			double[] bufferRadii, BufferRadiusUnit bufferRadiusUnit,
			int semicircleSegments, boolean isLeft, boolean isUnion,
			boolean isAttributeRetained, boolean isRing) {

		// 获得handle
		if (sourceDataset == null
				|| InternalHandle.getHandle(sourceDataset) == 0) {
			String message = InternalResource.loadString("sourceDataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (resultDataset == null
				|| InternalHandle.getHandle(resultDataset) == 0) {
			String message = InternalResource.loadString("resultDataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (sourceDataset.getType() != DatasetType.LINE) {
			String message = InternalResource
					.loadString(
							"sourceDataset",
							InternalResource.BufferAnalystBufferDatasetTypeShouldBeLine,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		for (double dRadius : bufferRadii) {
			if (dRadius <= 0) {
				String message = InternalResource.loadString("bufferRadii",
						InternalResource.GlobalGreaterThanZero,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}

		long sourceDatasetHandle = InternalHandle.getHandle(sourceDataset);
		long resultDatasetHandle = InternalHandle.getHandle(resultDataset);

		// 判断结果数据集类型，必须是面
		DatasetType resultDatasetType = resultDataset.getType();
		if (resultDatasetType != DatasetType.REGION) {
			String message = InternalResource.loadString("",
					InternalResource.BufferAnalystResultDatasetTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		boolean result = BufferAnalystNative
				.jni_CreateDatasetLineOneSideMultiBuffer(sourceDatasetHandle,
						resultDatasetHandle, bufferRadii,
						bufferRadiusUnit.value(), semicircleSegments, isLeft,
						isUnion, isAttributeRetained, isRing);

		return result;
	}

	/**
	 * 创建矢量线记录集单边多重缓冲区
	 * 
	 * @param sourceRecordset
	 *            Recordset 创建多重缓冲区的源矢量记录集
	 * @param resultDataset
	 *            DatasetVector 存储缓冲区分析结果的数据集
	 * @param bufferRadii
	 *            double[] 多重缓冲区半径列表
	 * @param bufferRadiusUnit
	 *            BufferRadiusUnit 缓冲区半径单位
	 * @param semicircleSegments
	 *            int 弧段拟合数
	 * @param isLeft
	 *            boolean 是否生成左缓冲
	 * @param isUnion
	 *            boolean 是否合并缓冲区
	 * @param isAttributeRetained
	 *            boolean 是否保留属性字段,只当isUnion为false时有效
	 * @param isRing
	 *            boolean 是否生成环状缓冲区
	 * @return boolean
	 */
	public static boolean createLineOneSideMultiBuffer(
			Recordset sourceRecordset, DatasetVector resultDataset,
			double[] bufferRadii, BufferRadiusUnit bufferRadiusUnit,
			int semicircleSegments, boolean isLeft, boolean isUnion,
			boolean isAttributeRetained, boolean isRing) {

		// 获得handle
		if (sourceRecordset == null
				|| InternalHandle.getHandle(sourceRecordset) == 0) {
			String message = InternalResource.loadString("sourceRecordset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (resultDataset == null
				|| InternalHandle.getHandle(resultDataset) == 0) {
			String message = InternalResource.loadString("resultDataset",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		if (sourceRecordset.getDataset().getType() != DatasetType.LINE) {
			String message = InternalResource
					.loadString(
							"sourceRecordset",
							InternalResource.BufferAnalystBufferDatasetTypeShouldBeLine,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		for (double dRadius : bufferRadii) {
			if (dRadius <= 0) {
				String message = InternalResource.loadString("bufferRadii",
						InternalResource.GlobalGreaterThanZero,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}

		long sourceRecordsetHandle = InternalHandle.getHandle(sourceRecordset);
		long resultDatasetHandle = InternalHandle.getHandle(resultDataset);

		// 判断结果数据集类型，必须是面
		DatasetType resultDatasetType = resultDataset.getType();
		if (resultDatasetType != DatasetType.REGION) {
			String message = InternalResource.loadString("",
					InternalResource.BufferAnalystResultDatasetTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		boolean result = BufferAnalystNative
				.jni_CreateRecordsetLineOneSideMultiBuffer(
						sourceRecordsetHandle, resultDatasetHandle,
						bufferRadii, bufferRadiusUnit.value(),
						semicircleSegments, isLeft, isUnion,
						isAttributeRetained, isRing);

		return result;
	}

//	public synchronized static void addSteppedListener(SteppedListener l) {
//		if (m_steppedListeners == null) {
//			m_steppedListeners = new Vector<SteppedListener>();
//		}
//
//		if (!m_steppedListeners.contains(l)) {
//			m_steppedListeners.add(l);
//		}
//	}
//
//	public synchronized static void removeSteppedListener(SteppedListener l) {
//		if (m_steppedListeners != null && m_steppedListeners.contains(l)) {
//			m_steppedListeners.remove(l);
//		}
//
//	}
//
//	protected static void fireStepped(SteppedEvent event) {
//		if (m_steppedListeners != null) {
//			Vector<SteppedListener> listeners = m_steppedListeners;
//			int count = listeners.size();
//			for (int i = 0; i < count; i++) {
//				((SteppedListener) listeners.elementAt(i)).stepped(event);
//			}
//		}
//	}
//
//	static void steppedCallBack(int percent, long remainTime, String title,
//			String message, long cancelHandle) {
//		String m_senderMethodName = "BufferAnalyst";
//		boolean cancel = InternalToolkitSpatialAnalyst
//				.getHandleBooleanValue(cancelHandle);
//		Object obj = new Object(); // 这个SteppedEvent的父类必须要有一个实例对象传进去，
//		SteppedEvent event = new SteppedEvent(obj, percent, remainTime, title,
//				message, m_senderMethodName);
//		fireStepped(event);
//		InternalToolkitSpatialAnalyst.setHandleBooleanValue(cancelHandle,
//				event.getCancel());
//	}
}
