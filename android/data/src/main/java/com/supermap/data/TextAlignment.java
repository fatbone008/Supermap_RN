package com.supermap.data;

/**
 * <p>
 * Title: 文本对齐常量
 * </p>
 * 
 * <p>
 * Description: 指定文本的对齐方式
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
 * @author 孔令亮
 * @version 2.0
 */
public final class TextAlignment extends Enum {
	private TextAlignment(int value, int ugcValue) {
		super(value, ugcValue);
	}

	public static final TextAlignment TOPLEFT = new TextAlignment(0, 0);

	public static final TextAlignment TOPCENTER = new TextAlignment(1, 1);

	public static final TextAlignment TOPRIGHT = new TextAlignment(2, 2);

	public static final TextAlignment BASELINELEFT = new TextAlignment(3, 3);

	public static final TextAlignment BASELINECENTER = new TextAlignment(4, 4);

	public static final TextAlignment BASELINERIGHT = new TextAlignment(5, 5);

	public static final TextAlignment BOTTOMLEFT = new TextAlignment(6, 6);

	public static final TextAlignment BOTTOMCENTER = new TextAlignment(7, 7);

	public static final TextAlignment BOTTOMRIGHT = new TextAlignment(8, 8);

	public static final TextAlignment MIDDLELEFT = new TextAlignment(9, 9);

	public static final TextAlignment MIDDLECENTER = new TextAlignment(10, 10);

	public static final TextAlignment MIDDLERIGHT = new TextAlignment(11, 11);
}
