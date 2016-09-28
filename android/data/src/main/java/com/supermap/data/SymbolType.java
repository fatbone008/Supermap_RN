package com.supermap.data;

public class SymbolType extends Enum {

	private SymbolType(int value, int ugcValue) {
		super(value, ugcValue);
	}

	// 二维点符号
	public static final SymbolType MARKER = new SymbolType(0, 0);

	// 二维线型符号
	public static final SymbolType LINE = new SymbolType(1, 1);

	// 二维填充符号
	public static final SymbolType FILL = new SymbolType(2, 2);

//	// 三维点符号
//	public static final SymbolType MARKER3D = new SymbolType(3, 3);

	// //三维管线符号
	// public static final SymbolType LINE3D = new SymbolType(4, 4);

	// //三维填充符号
	// public static final SymbolType FILL3D = new SymbolType(5, 5);

}
