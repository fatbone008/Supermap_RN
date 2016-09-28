package com.supermap.data;

public class SymbolType extends Enum {

	private SymbolType(int value, int ugcValue) {
		super(value, ugcValue);
	}

	// ��ά�����
	public static final SymbolType MARKER = new SymbolType(0, 0);

	// ��ά���ͷ���
	public static final SymbolType LINE = new SymbolType(1, 1);

	// ��ά������
	public static final SymbolType FILL = new SymbolType(2, 2);

//	// ��ά�����
//	public static final SymbolType MARKER3D = new SymbolType(3, 3);

	// //��ά���߷���
	// public static final SymbolType LINE3D = new SymbolType(4, 4);

	// //��ά������
	// public static final SymbolType FILL3D = new SymbolType(5, 5);

}
