package com.supermap.data;

class ImportMode extends com.supermap.data.Enum {

	private ImportMode(int value, int ugcValue) {
		super(value, ugcValue);
	}
	
	public static final ImportMode NONE = new ImportMode(0, 0);
	
	public static final ImportMode OVERWRITE = new ImportMode(1, 1);
	
	public static final ImportMode APPEND = new ImportMode(2, 2);

}
