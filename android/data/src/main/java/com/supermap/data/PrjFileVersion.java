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
public class PrjFileVersion extends com.supermap.data.Enum {
	private PrjFileVersion(int value, int ugcValue) {
		super(value, ugcValue);
	}
	
	public static final PrjFileVersion SFC60 = new PrjFileVersion(1, 1);
	
	public static final PrjFileVersion UGC60 = new PrjFileVersion(2, 2); 

}
