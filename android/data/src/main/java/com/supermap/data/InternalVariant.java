package com.supermap.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;


/**
 * <p>Title:
 * 内部使用的变体类
 * </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 李云锦
 * @version 2.0
 */
class InternalVariant extends InternalHandleDisposable {
	protected InternalVariant() {
		this.setHandle(InternalVariantNative.jni_New(), true);
	}

	public InternalVariant(Object obj) {
		this();
		//检查Object的类型
		if (obj.getClass().equals(Byte.class)) {
			Byte value = (Byte) obj;
			setValue(value.byteValue());
		} else if (obj.getClass().equals(Integer.class)) {
			Integer value = (Integer) obj;
			setValue(value.intValue());
		} else if (obj.getClass().equals(Long.class)) {
			Long value = (Long) obj;
			setValue(value.longValue());
		} else if (obj.getClass().equals(Short.class)) {
			Short value = (Short) obj;
			setValue(value.shortValue());
		} else if (obj.getClass().equals(Boolean.class)) {
			Boolean value = (Boolean) obj;
			setValue(value.booleanValue());
		} else if (obj.getClass().equals(Float.class)) {
			Float value = (Float) obj;
			setValue(value.floatValue());
		} else if (obj.getClass().equals(Double.class)) {
			Double value = (Double) obj;
			setValue(value.doubleValue());
		} else if (obj.getClass().equals(String.class)) {
			String value = (String) obj;
			setValue(value);
		} else if (obj.getClass().equals(Date.class)) {
			Date value = (Date) obj;
			setValue(value);
		} else if (obj.getClass().equals(byte[].class)) {
			byte[] value = (byte[]) obj;
			setValue(value);
		} else {
			String message = InternalResource.loadString("",
					InternalResource.InternalVariantUnsupportType,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
	}

	public InternalVariant(byte value) {
		this();
		setValue(value);
	}

	public InternalVariant(int value) {
		this();
		setValue(value);
	}

	public InternalVariant(long value) {
		this();
		setValue(value);
	}

	public InternalVariant(short value) {
		this();
		setValue(value);
	}

	public InternalVariant(boolean value) {
		this();
		setValue(value);
	}

	public InternalVariant(float value) {
		this();
		setValue(value);
	}

	public InternalVariant(double value) {
		this();
		setValue(value);
	}

	public InternalVariant(String value) {
		this();
		setValue(value);
	}

	public InternalVariant(Date value) {
		this();
		setValue(value);
	}

	public InternalVariant(byte[] value) {
		this();
		setValue(value);
	}

	public InternalVariant(Byte[] value) {
		this();
		setValue(value);
	}

	public InternalVariantType getType() {
		int ugcValue = InternalVariantNative.jni_GetType(getHandle());
		InternalVariantType type = (InternalVariantType) Enum.parseUGCValue(
				InternalVariantType.class, ugcValue);
		return type;
	}

	public void setValue(byte value) {
		InternalVariantNative.jni_SetValueByte(getHandle(), value);
	}

	public void setValue(int value) {
		InternalVariantNative.jni_SetValueInt(getHandle(), value);
	}

	public void setValue(long value) {
		InternalVariantNative.jni_SetValueLong(getHandle(), value);
	}

	public void setValue(short value) {
		InternalVariantNative.jni_SetValueShort(getHandle(), value);
	}

	public void setValue(boolean value) {
		InternalVariantNative.jni_SetValueBoolean(getHandle(), value);
	}

	public void setValue(float value) {
		InternalVariantNative.jni_SetValueFloat(getHandle(), value);
	}

	public void setValue(double value) {
		InternalVariantNative.jni_SetValueDouble(getHandle(), value);
	}

	public void setValue(String value) {
		InternalVariantNative.jni_SetValueString(getHandle(), value);
	}

	public void setValue(byte[] value) {
		InternalVariantNative.jni_SetValueBinary(getHandle(), value);
	}

	public void setValue(Byte[] value) {
		byte[] temp = new byte[value.length];
		for (int i = 0; i < value.length; i++) {
			temp[i] = value[i].byteValue();
		}
		InternalVariantNative.jni_SetValueBinary(getHandle(), temp);
	}

	public void setValue(Date value) {
		GregorianCalendar calendar = new GregorianCalendar(java.util.Locale.US);
		calendar.setTime(value);
		int year = calendar.get(GregorianCalendar.YEAR);
		int month = calendar.get(GregorianCalendar.MONTH) + 1;
		int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		//24小时制时间字段被当成12小时制字段处理，在这里修改GregorianCalendar获取小时的属性形式
		int hours = calendar.get(GregorianCalendar.HOUR_OF_DAY/*GregorianCalendar.HOUR*/);
		int minutes = calendar.get(GregorianCalendar.MINUTE);
		int seconds = calendar.get(GregorianCalendar.SECOND);
		InternalVariantNative.jni_SetValueTime(getHandle(), year, month, day,
				hours, minutes, seconds);
	}

	public int toByte() {
		return InternalVariantNative.jni_GetValueByte(getHandle());
	}

	public int toInt() {
		return InternalVariantNative.jni_GetValueInt(getHandle());
	}

	public long toLong() {
		return InternalVariantNative.jni_GetValueLong(getHandle());
	}

	public short toShort() {
		return InternalVariantNative.jni_GetValueShort(getHandle());
	}

	public boolean toBoolean() {
		return InternalVariantNative.jni_GetValueBoolean(getHandle());
	}

	public double toDouble() {
		return InternalVariantNative.jni_GetValueDouble(getHandle());
	}

	public float toFloat() {
		return InternalVariantNative.jni_GetValueFloat(getHandle());
	}

	public byte[] toBinary() {
		return InternalVariantNative.jni_GetValueBinary(getHandle());
	}

	public String toString() {
		//判断一下是否日期类型，如果是日期类型，转换成当前系统的日期格式，这样比较好一点
		String str = null;
		if (getType().equals(InternalVariantType.TIME)
				|| getType().equals(InternalVariantType.DATE)
				|| getType().equals(InternalVariantType.TIMESTAMP)) {

			Date date = toDate();
			str = DateFormat.getDateInstance().format(date);
		} else {
			str = InternalVariantNative.jni_GetValueString(getHandle());
		}
		return str;
	}

	public Date toDate() {
		//日期正则表达式
		Pattern p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		String temp = InternalVariantNative.jni_GetValueString(getHandle());
		Date date = null;
		if (p.matcher(temp).matches()) {
			try {
				date = DateFormat.getDateInstance().parse(temp);
			} catch (ParseException e) {
			}
		}
		return date;
    }

	public Object toObject() {
		InternalVariantType type = getType();
		if (type.equals(InternalVariantType.BYTE)) {
			return Byte.valueOf(String.valueOf(toByte()));
		} else if(type.equals(InternalVariantType.BOOLEAN)){
			return Boolean.valueOf(toBoolean());
		} else if (type.equals(InternalVariantType.INTEGER)) {
			// @modified by 孔令亮 at 2007年9月17日 上午10时03分21秒
			// @reason:Java 1.4中还没有Integer.valueOf(ugcValue)，为了兼容 1.4版本，作此修改
			return Integer.valueOf(toInt());
		} else if (type.equals(InternalVariantType.LONG)) {
			return Long.valueOf(toLong());
		} else if (type.equals(InternalVariantType.SHORT)) {
			return Short.valueOf(toShort());
		} else if (type.equals(InternalVariantType.FLOAT)) {
			return Float.valueOf(toFloat());
		} else if (type.equals(InternalVariantType.DOUBLE)) {
			return Double.valueOf(toDouble());
		} else if (type.equals(InternalVariantType.TIME)
				|| type.equals(InternalVariantType.DATE)
				|| type.equals(InternalVariantType.TIMESTAMP)) {

			return toDate();
		} else if (type.equals(InternalVariantType.STRING)) {
			return toString();
		} else if (type.equals(InternalVariantType.BINARY)) {
			return toBinary();
		} else if (type.equals(InternalVariantType.NULL)) {
			//如果没有定义类型,先返回null
			return null;
			//            //如果没有定义类型，先默认按String处理
			//             toString();
		} else {
			String message = InternalResource.loadString("",
					InternalResource.InternalVariantUnsupportType,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
	}

	public void dispose() {
		if (!super.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (!(super.getHandle() == 0)) {
			InternalVariantNative.jni_Delete(getHandle());
			setHandle(0);
		}
	}

	/**
	 * 内部使用
	 * @param getHandle() long
	 * @param empty boolean  没有任何意义，只是重载需要
	 */
	private InternalVariant(long handle, boolean empty) {
		//原来误写成了不可释放，造成大量的内存浪费
		setHandle(handle, true);
	}

	static final InternalVariant createInstance(long handle) {
		return new InternalVariant(handle, true);
	}

	/**
	 * 检查是否为兼容的类型
	 * @param obj Object
	 * @return boolean
	 */
	static boolean isSupportedInstance(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass().equals(Byte.class)) {
			return true;
		} else if (obj.getClass().equals(Integer.class)) {
			return true;
		} else if (obj.getClass().equals(Long.class)) {
			return true;
		} else if (obj.getClass().equals(Short.class)) {
			return true;
		} else if (obj.getClass().equals(Boolean.class)) {
			return true;
		} else if (obj.getClass().equals(Float.class)) {
			return true;
		} else if (obj.getClass().equals(Double.class)) {
			return true;
		} else if (obj.getClass().equals(String.class)) {
			return true;
		} else if (obj.getClass().equals(Date.class)) {
			return true;
		} else if (obj.getClass().equals(byte[].class)) {
			return true;
		} else {
			return false;
		}

	}
}
