package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) public static final int 200 = 7;</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 李云锦
 * @version 2.0
 */
public class Unit extends Enum {
	protected Unit(int value, int ugcValue) {
		super(value, ugcValue);
	}

	//排序规则
	//同一类别的按从小到大排列
	public static final Unit MILIMETER = new Unit(10, 10);

	public static final Unit CENTIMETER = new Unit(100, 100);

	public static final Unit DECIMETER = new Unit(1000, 1000);

	public static final Unit METER = new Unit(10000, 10000);

	public static final Unit KILOMETER = new Unit(10000000, 10000000);

	public static final Unit INCH = new Unit(254, 254);

	public static final Unit FOOT = new Unit(3048, 3048);

	public static final Unit YARD = new Unit(9144, 9144);

	public static final Unit MILE = new Unit(16090000, 16090000);

	public static final Unit SECOND = new Unit(485 + 1000000000,
			485 + 1000000000);

	public static final Unit MINUTE = new Unit(29089 + 1000000000,
			29089 + 1000000000);

	public static final Unit DEGREE = new Unit(1745329 + 1000000000,
			1745329 + 1000000000);

	public static final Unit RADIAN = new Unit(100000000 + 1000000000,
			100000000 + 1000000000);

	//    public static final Unit MILIMETER = new Unit(1, 10);
	//    public static final Unit CENTIMETER = new Unit(2, 100);
	//    public static final Unit DECIMETER = new Unit(3, 1000);
	//    public static final Unit METER = new Unit(4, 10000);
	//    public static final Unit KILOMETER = new Unit(5, 10000000);
	//    public static final Unit INCH = new Unit(6, 254);
	//    public static final Unit FOOT = new Unit(7, 3048);
	//    public static final Unit YARD = new Unit(8, 9144);
	//    public static final Unit MILE = new Unit(9, 16090000);
	//    public static final Unit SECOND = new Unit(19, 485 + 1000000000);
	//    public static final Unit MINUTE = new Unit(20, 29089 + 1000000000);
	//    public static final Unit DEGREE = new Unit(21, 100000000 + 1000000000);
	//    public static final Unit RADIAN = new Unit(22, 100000000 + 1000000000);

	//    public static final Unit SQUAREMILIMETER = new Unit(10,
	//            getAreaValue(Unit.MILIMETER));
	//    public static final Unit SQUARECENTIMETER = new Unit(11,
	//            getAreaValue(Unit.CENTIMETER));
	//    public static final Unit SQUAREDECIMETER = new Unit(12,
	//            getAreaValue(Unit.DECIMETER));
	//    public static final Unit SQUAREMETER = new Unit(13,
	//            getAreaValue(Unit.METER));
	//    public static final Unit SQUAREKILOMETER = new Unit(14,
	//            getAreaValue(Unit.KILOMETER));
	//    public static final Unit SQUAREINCH = new Unit(15,
	//            getAreaValue(Unit.INCH));
	//    public static final Unit SQUAREFOOT = new Unit(16,
	//            getAreaValue(Unit.FOOT));
	//    public static final Unit SQUAREYARD = new Unit(17,
	//            getAreaValue(Unit.YARD));
	//    public static final Unit SQUAREMILE = new Unit(18,
	//            getAreaValue(Unit.MILE));

	protected static boolean isCoordUnit(Unit unit) {
		boolean isCoordUnit = false;

		if (unit == Unit.MILIMETER) {
			isCoordUnit = true;
		} else if (unit == Unit.CENTIMETER) {
			isCoordUnit = true;
		} else if (unit == Unit.DECIMETER) {
			isCoordUnit = true;
		} else if (unit == Unit.METER) {
			isCoordUnit = true;
		} else if (unit == Unit.KILOMETER) {
			isCoordUnit = true;
		} else if (unit == Unit.INCH) {
			isCoordUnit = true;
		} else if (unit == Unit.FOOT) {
			isCoordUnit = true;
		} else if (unit == Unit.YARD) {
			isCoordUnit = true;
		} else if (unit == Unit.MILE) {
			isCoordUnit = true;
		} else if (unit == Unit.SECOND) {
			isCoordUnit = true;
		} else if (unit == Unit.MINUTE) {
			isCoordUnit = true;
		} else if (unit == Unit.DEGREE) {
			isCoordUnit = true;
		} else if (unit == Unit.RADIAN) {
			isCoordUnit = true;
		}
		return isCoordUnit;
	}

	protected static boolean isDistanceUnit(Unit unit) {
		boolean isDistanceUnit = false;

		if (unit == Unit.MILIMETER) {
			isDistanceUnit = true;
		} else if (unit == Unit.CENTIMETER) {
			isDistanceUnit = true;
		} else if (unit == Unit.DECIMETER) {
			isDistanceUnit = true;
		} else if (unit == Unit.METER) {
			isDistanceUnit = true;
		} else if (unit == Unit.KILOMETER) {
			isDistanceUnit = true;
		} else if (unit == Unit.INCH) {
			isDistanceUnit = true;
		} else if (unit == Unit.FOOT) {
			isDistanceUnit = true;
		} else if (unit == Unit.YARD) {
			isDistanceUnit = true;
		} else if (unit == Unit.MILE) {
			isDistanceUnit = true;
		}
		return isDistanceUnit;
	}

	/**
	 * 重载方便使用,英文返回缩写。中文返回汉字
	 * @return String
	 */
	@Override
	public String toString() {
		String result = "";

		if (this == Unit.MILIMETER) {
			result = InternalResource.loadString("",
					InternalResource.MILIMETER, InternalResource.BundleName);
		} else if (this == Unit.CENTIMETER) {
			result = InternalResource.loadString("",
					InternalResource.CENTIMETER, InternalResource.BundleName);
		} else if (this == Unit.DECIMETER) {
			result = InternalResource.loadString("",
					InternalResource.DECIMETER, InternalResource.BundleName);
		} else if (this == Unit.METER) {
			result = InternalResource.loadString("",
					InternalResource.METER, InternalResource.BundleName);
		} else if (this == Unit.KILOMETER) {
			result = InternalResource.loadString("",
					InternalResource.KILOMETER, InternalResource.BundleName);
		} else if (this == Unit.INCH) {
			result = InternalResource.loadString("",
					InternalResource.INCH, InternalResource.BundleName);
		} else if (this == Unit.FOOT) {
			result = InternalResource.loadString("",
					InternalResource.FOOT, InternalResource.BundleName);
		} else if (this == Unit.YARD) {
			result = InternalResource.loadString("",
					InternalResource.YARD, InternalResource.BundleName);
		} else if (this == Unit.MILE) {
			result = InternalResource.loadString("",
					InternalResource.MILE, InternalResource.BundleName);
		} else if (this == Unit.SECOND) {
			result = InternalResource.loadString("",
					InternalResource.SECOND, InternalResource.BundleName);
		} else if (this == Unit.MINUTE) {
			result = InternalResource.loadString("",
					InternalResource.MINUTE, InternalResource.BundleName);
		} else if (this == Unit.DEGREE) {
			result = InternalResource.loadString("",
					InternalResource.DEGREE, InternalResource.BundleName);
		} else if (this == Unit.RADIAN) {
			result = InternalResource.loadString("",
					InternalResource.RADIAN, InternalResource.BundleName);
		}
		return result;
	}

	//    protected static boolean isAreaUnit(Unit unit) {
	//        boolean isAreaUnit = false;
	//
	//        if (unit == Unit.SQUAREMILIMETER) {
	//            isAreaUnit = true;
	//        } else if (unit == Unit.SQUARECENTIMETER) {
	//            isAreaUnit = true;
	//        } else if (unit == Unit.SQUAREDECIMETER) {
	//            isAreaUnit = true;
	//        } else if (unit == Unit.SQUAREMETER) {
	//            isAreaUnit = true;
	//        } else if (unit == Unit.SQUAREKILOMETER) {
	//            isAreaUnit = true;
	//        } else if (unit == Unit.SQUAREINCH) {
	//            isAreaUnit = true;
	//        } else if (unit == Unit.SQUAREFOOT) {
	//            isAreaUnit = true;
	//        } else if (unit == Unit.SQUAREYARD) {
	//            isAreaUnit = true;
	//        } else if (unit == Unit.SQUAREMILE) {
	//            isAreaUnit = true;
	//        }
	//        return isAreaUnit;
	//
	//    }

	//    private static final int getAreaValue(Unit unit) {
	//        return unit.value() * unit.value();
	//    }
}
