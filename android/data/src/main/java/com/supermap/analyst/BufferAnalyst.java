package com.supermap.analyst;

import java.util.ArrayList;
import java.util.Vector;

import com.supermap.analyst.InternalToolkitSpatialAnalyst.BufferSideType;
import com.supermap.data.*;

/**
 * <p>
 * Title:������������
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
 * @author �ż���
 * @version 2.0
 */
public class BufferAnalyst {

	private transient static Vector<SteppedListener> m_steppedListeners;

	private BufferAnalyst() {

	}

	/**
	 * ����ʸ�����ݼ�������
	 * 
	 * @param sourceDataset
	 *            DatasetVector ������������Դʸ�����ݼ�
	 * @param resultDataset
	 *            DatasetVector �洢������������������ݼ�
	 * @param bufferAnalystParameter
	 *            BufferAnalystParameter ���������������
	 * @param isUnion
	 *            boolean �Ƿ�ϲ�������
	 * @param isAttributeRetained
	 *            boolean �Ƿ��������ֶ�,ֻ��isUnionΪfalseʱ��Ч
	 * @return boolean
	 */
	public static boolean createBuffer(DatasetVector sourceDataset,
			DatasetVector resultDataset,
			BufferAnalystParameter bufferAnalystParameter, boolean isUnion,
			boolean isAttributeRetained) {

		// ���������
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

		// ���handle
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

		// �жϽ�����ݼ����ͣ���������
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

		// ƽͷ����
		if (endType.equals(BufferEndType.FLAT)) {
			// ƽͷ���壬���һ���ͬʱΪnull���׳��쳣
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
			// Ӧ�����߻�����
			if (datasetType != DatasetType.LINE
					&& datasetType != DatasetType.NETWORK) {
				String message = InternalResource.loadString("",
						InternalResource.BufferAnalystBufferDatasetTypeInvalid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}

			// ���һ��������ֻ����String������ֵ���ͣ����ڲ��������Ѿ������жϡ������һ���
			// ����Ϊnullʱ��������ǲ�ͬʱ��String��Ҫ���쳣����������ֵ���Ͳ���Ҫ��
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

			// �ұ���null,�����󻺳�
			if (objRightDistance == null) {
				// ���ֶ�
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
					// ����ֵ
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

			// �����null�������һ���
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
					// ������벻���������׳��쳣
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

			// ���Ҷ�����null
			if (objLeftDistance != null && objRightDistance != null) {
				// �󻺳���String���Ǳ�Ȼ���Ҷ����ֶΣ�������ǰ����׳��쳣��
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

		// �����Բͷ���壬�ʹ����������
		if (endType.equals(BufferEndType.ROUND)) {
			// Բͷ���壬ֻҪ�󻺳���null�������쳣
			if (objLeftDistance == null) {
				String message = InternalResource.loadString("objLeftDistance",
						InternalResource.GlobalArgumentNull,
						InternalResource.BundleName);
				throw new NullPointerException(message);
			}

			if (isUnion) {
				isAttributeRetained = false;
			}

			// ���ݼ�����ֻ���ǵ㣬�ߣ��棬����
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
				// �������Բͷ���壬�����������������������Բͷ���壬������벻Ϊ0
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

			// �����ݼ����������ݼ���Ҫ��BufferSideType����ΪFULL
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
	 * ����ʸ����¼������
	 * 
	 * @param sourceRecordset
	 *            Recordset ������������Դʸ����¼��
	 * @param resultDataset
	 *            DatasetVector �洢������������������ݼ�
	 * @param bufferAnalystParameter
	 *            BufferAnalystParameter ���������������
	 * @param isUnion
	 *            boolean �Ƿ�ϲ�������
	 * @param isAttributeRetained
	 *            boolean �Ƿ��������ֶ�,ֻ��isUnionΪfalseʱ��Ч
	 * @return boolean
	 */
	public static boolean createBuffer(Recordset sourceRecordset,
			DatasetVector resultDataset,
			BufferAnalystParameter bufferAnalystParameter, boolean isUnion,
			boolean isAttributeRetained) {

		// ���������
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

		// ���handle
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

		// �жϽ�����ݼ����ͣ���������
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

		// �����ƽͷ���壬��Ӧ�����߻�������
		if (endType.equals(BufferEndType.FLAT)) {
			// ƽͷ���壬���һ���ͬʱΪnull���׳��쳣
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
			// Ӧ�����߻�����
			if (datasetType != DatasetType.LINE
					&& datasetType != DatasetType.NETWORK) {
				String message = InternalResource.loadString("",
						InternalResource.BufferAnalystBufferDatasetTypeInvalid,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}

			// ���һ��������ֻ����String������ֵ���ͣ����ڲ��������Ѿ������жϡ������һ���
			// ����Ϊnullʱ��������ǲ�ͬʱ��String��Ҫ���쳣����������ֵ���Ͳ���Ҫ��
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

			// �ұ���null�������󻺳�
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
			// �����null�������һ���
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
					// ������벻���������׳��쳣
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
			// ���Ҷ�����null
			if (objLeftDistance != null && objRightDistance != null) {
				// �󻺳���String���Ǳ�Ȼ���Ҷ����ֶΣ�������ǰ����׳��쳣��
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

		// �����Բͷ���壬�ʹ����������
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
			// Բͷ���壬ֻҪ�󻺳���null�������쳣
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
				// �������Բͷ���壬�����������������������Բͷ���壬������벻Ϊ0
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
			// �����ݼ����������ݼ���Ҫ��BufferSideType����ΪFULL
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
	 * ����ʸ�����ݼ����ػ�����
	 * 
	 * @param sourceDataset
	 *            DatasetVector �������ػ�������Դʸ�����ݼ�
	 * @param resultDataset
	 *            DatasetVector �洢������������������ݼ�
	 * @param bufferRadii
	 *            double[] ���ػ������뾶�б�
	 * @param bufferRadiusUnit
	 *            BufferRadiusUnit �������뾶��λ
	 * @param semicircleSegments
	 *            int ���������
	 * @param isUnion
	 *            boolean �Ƿ�ϲ�������
	 * @param isAttributeRetained
	 *            boolean �Ƿ��������ֶ�,ֻ��isUnionΪfalseʱ��Ч
	 * @param isRing
	 *            boolean �Ƿ����ɻ�״������
	 * @return boolean
	 */
	public static boolean createMultiBuffer(DatasetVector sourceDataset,
			DatasetVector resultDataset, double[] bufferRadii,
			BufferRadiusUnit bufferRadiusUnit, int semicircleSegments,
			boolean isUnion, boolean isAttributeRetained, boolean isRing) {

		// ���handle
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

		// �жϽ�����ݼ����ͣ���������
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
	 * ����ʸ����¼�����ػ�����
	 * 
	 * @param sourceRecordset
	 *            Recordset �������ػ�������Դʸ����¼��
	 * @param resultDataset
	 *            DatasetVector �洢������������������ݼ�
	 * @param bufferRadii
	 *            double[] ���ػ������뾶�б�
	 * @param bufferRadiusUnit
	 *            BufferRadiusUnit �������뾶��λ
	 * @param semicircleSegments
	 *            int ���������
	 * @param isUnion
	 *            boolean �Ƿ�ϲ�������
	 * @param isAttributeRetained
	 *            boolean �Ƿ��������ֶ�,ֻ��isUnionΪfalseʱ��Ч
	 * @param isRing
	 *            boolean �Ƿ����ɻ�״������
	 * @return boolean
	 */
	public static boolean createMultiBuffer(Recordset sourceRecordset,
			DatasetVector resultDataset, double[] bufferRadii,
			BufferRadiusUnit bufferRadiusUnit, int semicircleSegments,
			boolean isUnion, boolean isAttributeRetained, boolean isRing) {

		// ���handle
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

		// �жϽ�����ݼ����ͣ���������
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
	 * ����ʸ�������ݼ����߶��ػ�����
	 * 
	 * @param sourceDataset
	 *            DatasetVector �������ػ�������Դʸ�����ݼ�
	 * @param resultDataset
	 *            DatasetVector �洢������������������ݼ�
	 * @param bufferRadii
	 *            double[] ���ػ������뾶�б�
	 * @param bufferRadiusUnit
	 *            BufferRadiusUnit �������뾶��λ
	 * @param semicircleSegments
	 *            int ���������
	 * @param isLeft
	 *            boolean �Ƿ������󻺳�
	 * @param isUnion
	 *            boolean �Ƿ�ϲ�������
	 * @param isAttributeRetained
	 *            boolean �Ƿ��������ֶ�,ֻ��isUnionΪfalseʱ��Ч
	 * @param isRing
	 *            boolean �Ƿ����ɻ�״������
	 * @return boolean
	 */
	public static boolean createLineOneSideMultiBuffer(
			DatasetVector sourceDataset, DatasetVector resultDataset,
			double[] bufferRadii, BufferRadiusUnit bufferRadiusUnit,
			int semicircleSegments, boolean isLeft, boolean isUnion,
			boolean isAttributeRetained, boolean isRing) {

		// ���handle
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

		// �жϽ�����ݼ����ͣ���������
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
	 * ����ʸ���߼�¼�����߶��ػ�����
	 * 
	 * @param sourceRecordset
	 *            Recordset �������ػ�������Դʸ����¼��
	 * @param resultDataset
	 *            DatasetVector �洢������������������ݼ�
	 * @param bufferRadii
	 *            double[] ���ػ������뾶�б�
	 * @param bufferRadiusUnit
	 *            BufferRadiusUnit �������뾶��λ
	 * @param semicircleSegments
	 *            int ���������
	 * @param isLeft
	 *            boolean �Ƿ������󻺳�
	 * @param isUnion
	 *            boolean �Ƿ�ϲ�������
	 * @param isAttributeRetained
	 *            boolean �Ƿ��������ֶ�,ֻ��isUnionΪfalseʱ��Ч
	 * @param isRing
	 *            boolean �Ƿ����ɻ�״������
	 * @return boolean
	 */
	public static boolean createLineOneSideMultiBuffer(
			Recordset sourceRecordset, DatasetVector resultDataset,
			double[] bufferRadii, BufferRadiusUnit bufferRadiusUnit,
			int semicircleSegments, boolean isLeft, boolean isUnion,
			boolean isAttributeRetained, boolean isRing) {

		// ���handle
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

		// �жϽ�����ݼ����ͣ���������
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
//		Object obj = new Object(); // ���SteppedEvent�ĸ������Ҫ��һ��ʵ�����󴫽�ȥ��
//		SteppedEvent event = new SteppedEvent(obj, percent, remainTime, title,
//				message, m_senderMethodName);
//		fireStepped(event);
//		InternalToolkitSpatialAnalyst.setHandleBooleanValue(cancelHandle,
//				event.getCancel());
//	}
}
