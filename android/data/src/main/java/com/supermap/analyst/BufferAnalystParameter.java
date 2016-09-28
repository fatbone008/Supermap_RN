package com.supermap.analyst;



public class BufferAnalystParameter {
	
	private BufferEndType m_bufferEndType;
	
	private int m_semicircleLineSegment;
	
	private Object m_leftDistance;
	
	private Object m_rightDistance;
	
	private BufferRadiusUnit m_bufferRadiusUnit;
	
	/**
	 * 默认构造函数
	 */
	public BufferAnalystParameter() {
		this.m_bufferEndType = BufferEndType.ROUND;
		this.m_semicircleLineSegment = 12;
		this.m_leftDistance = null;
		this.m_rightDistance = null;
		this.m_bufferRadiusUnit = BufferRadiusUnit.Meter;
	}
	
	/**
	 * 拷贝构造函数 
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
	 * 返回缓冲区分析端类型
	 * @return BufferEndType
	 */
	public BufferEndType getEndType() {
		return this.m_bufferEndType;
	}
	
	/**
	 * 设置缓冲区端类型
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
	 * 返回缓冲区分析半径单位
	 * @return BufferRadiusUnit
	 */
	public BufferRadiusUnit getRadiusUnit(){
		return this.m_bufferRadiusUnit;
	}
	
	/**
	 * 设置缓冲区分析半径单位
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
	 * 返回半圆弧线段个数，即用多少个线段来模拟一个半圆,必须大于等于4（UGC还认为要小于等于200）
	 * @return int
	 */
	public int getSemicircleLineSegment() {
		return this.m_semicircleLineSegment;
	}
	
	/**
	 * 设置半圆弧线段个数，即用多少个线段来模拟一个半圆,必须大于等于4（UGC还认为要小于等于200）
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
	 * 返回左缓冲距离
	 * @return Object
	 */
	public Object getLeftDistance() {
		return this.m_leftDistance;
	}
	
	/**
	 * 设置左缓冲距离
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
	 * 返回右缓冲距离
	 * @return Object
	 */
	public Object getRightDistance() {
		return this.m_rightDistance;
	}
	
	/**
	 * 设置右缓冲距离
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
	 * 内部使用，判断传入的Object的类型是否可用
	 * @param obj Object
	 * @return boolean
	 */
	private boolean isValidObject(Object obj) {
		boolean bResult = false;
//		Class type = obj.getClass();
		// String和Double类型单独考虑
		if ((obj instanceof String) || (obj instanceof Double)) {
			bResult = true;
		}
		// 其他类型：将obj转成String，然后尝试返回一个新的double 值
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
