package com.supermap.analyst;



public class BufferAnalystParameter {
	
	private BufferEndType m_bufferEndType;
	
	private int m_semicircleLineSegment;
	
	private Object m_leftDistance;
	
	private Object m_rightDistance;
	
	private BufferRadiusUnit m_bufferRadiusUnit;
	
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public BufferAnalystParameter() {
		this.m_bufferEndType = BufferEndType.ROUND;
		this.m_semicircleLineSegment = 12;
		this.m_leftDistance = null;
		this.m_rightDistance = null;
		this.m_bufferRadiusUnit = BufferRadiusUnit.Meter;
	}
	
	/**
	 * �������캯�� 
	 * @param bufferAnalystParameter BufferAnalystParameter
	 */
	public BufferAnalystParameter(BufferAnalystParameter bufferAnalystParameter) {
		this.m_bufferEndType = bufferAnalystParameter.getEndType();
		this.m_semicircleLineSegment = bufferAnalystParameter.getSemicircleLineSegment();
		this.m_leftDistance = bufferAnalystParameter.getLeftDistance();
		this.m_rightDistance = bufferAnalystParameter.getRightDistance();
		this.m_bufferRadiusUnit = bufferAnalystParameter.getRadiusUnit();
	}
	
	/**
	 * ���ػ���������������
	 * @return BufferEndType
	 */
	public BufferEndType getEndType() {
		return this.m_bufferEndType;
	}
	
	/**
	 * ���û�����������
	 * @param bufferEndType BufferEndType
	 */
	public void setEndType(BufferEndType bufferEndType) {
		if (bufferEndType == null) {
			String message = InternalResource.loadString("bufferEndType",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		this.m_bufferEndType = bufferEndType;
	}
	
	/**
	 * ���ػ����������뾶��λ
	 * @return BufferRadiusUnit
	 */
	public BufferRadiusUnit getRadiusUnit(){
		return this.m_bufferRadiusUnit;
	}
	
	/**
	 * ���û����������뾶��λ
	 * @param bufferRadiusUnit BufferRadiusUnit
	 */
	public void setRadiusUnit(BufferRadiusUnit bufferRadiusUnit) {
		if (bufferRadiusUnit == null) {
			String message = InternalResource.loadString("bufferRadiusUnit",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		this.m_bufferRadiusUnit = bufferRadiusUnit;
	}
	
	/**
	 * ���ذ�Բ���߶θ��������ö��ٸ��߶���ģ��һ����Բ,������ڵ���4��UGC����ΪҪС�ڵ���200��
	 * @return int
	 */
	public int getSemicircleLineSegment() {
		return this.m_semicircleLineSegment;
	}
	
	/**
	 * ���ð�Բ���߶θ��������ö��ٸ��߶���ģ��һ����Բ,������ڵ���4��UGC����ΪҪС�ڵ���200��
	 * @param value int
	 */
	public void setSemicircleLineSegment(int value) {
		if (value < 4 || value > 200) {
            String message = InternalResource.loadString("leftDistance",
                    InternalResource.BufferAnalystSemicircleLineSegmentShouldEqualsOrGreaterThanFour,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
		}
		this.m_semicircleLineSegment = value;
	}
	
	/**
	 * �����󻺳����
	 * @return Object
	 */
	public Object getLeftDistance() {
		return this.m_leftDistance;
	}
	
	/**
	 * �����󻺳����
	 * @param leftDistance Object
	 */
	public void setLeftDistance(Object leftDistance) {
		if (leftDistance == null) {
			this.m_leftDistance = null;
			return;
		}
		if(!isValidObject(leftDistance)) {
            String message = InternalResource.loadString("leftDistance",
                    InternalResource.BufferAnalystParameterInvalidObject,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
		}
		this.m_leftDistance = leftDistance;
	}
	
	/**
	 * �����һ������
	 * @return Object
	 */
	public Object getRightDistance() {
		return this.m_rightDistance;
	}
	
	/**
	 * �����һ������
	 * @param rightDistance Object
	 */
	public void setRightDistance(Object rightDistance) {
		if (rightDistance == null) {
			this.m_rightDistance = null;
			return;
		}
		if(!isValidObject(rightDistance)) {
            String message = InternalResource.loadString("rightDistance",
                    InternalResource.BufferAnalystParameterInvalidObject,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
		}
		this.m_rightDistance = rightDistance;
	} 
	
	/**
	 * �ڲ�ʹ�ã��жϴ����Object�������Ƿ����
	 * @param obj Object
	 * @return boolean
	 */
	private boolean isValidObject(Object obj) {
		boolean bResult = false;
//		Class type = obj.getClass();
		// String��Double���͵�������
		if ((obj instanceof String) || (obj instanceof Double)) {
			bResult = true;
		}
		// �������ͣ���objת��String��Ȼ���Է���һ���µ�double ֵ
		else {
			try {
				Double.parseDouble(obj.toString());
				bResult = true;
			} catch (Exception e) {
				bResult = false;
			}
		}
		return bResult;
	}

}
