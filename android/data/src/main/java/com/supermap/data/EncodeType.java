package com.supermap.data;

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
public class EncodeType extends Enum {
	private EncodeType(int value, int ugcValue) {
		super(value, ugcValue);
	}

	public static final EncodeType NONE = new EncodeType(0, 0);

	public static final EncodeType BYTE = new EncodeType(1, 1);

	public static final EncodeType INT16 = new EncodeType(2, 2);

	public static final EncodeType INT24 = new EncodeType(3, 3);

	public static final EncodeType INT32 = new EncodeType(4, 4);

	public static final EncodeType DCT = new EncodeType(8, 8);

	 public static final EncodeType SGL = new EncodeType(9,9);
	 public static final EncodeType LZW = new EncodeType(11,11);

}
