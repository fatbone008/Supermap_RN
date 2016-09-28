package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ’≈ø≠
 * @version 6.0.2
 */
public class PrjFileType extends com.supermap.data.Enum{
	private PrjFileType(int value, int ugcValue) {
		super(value, ugcValue);
	}
	
    public static final PrjFileType SUPERMAP = new PrjFileType(1, 1);
	
	public static final PrjFileType ESRI = new PrjFileType(2, 2);

}
