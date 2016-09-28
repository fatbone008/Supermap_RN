/**
 * 
 */
package com.supermap.data;

/**
 * @author Administrator
 *
 */
class MultiBandImportMode extends com.supermap.data.Enum {
	
	private MultiBandImportMode(int value, int ugcValue) {
		super(value, ugcValue);
	}

	//将多波段数据导入为多个单波段数据集
//	public static final MultiBandImportMode SINGLEBAND = new MultiBandImportMode(0, 0);

	//将多波段数据导入为一个多波段数据集
	public static final MultiBandImportMode MULTIBAND = new MultiBandImportMode(1, 1);

//	//将多波段数据导入为一个单波段数据集，目前这个情况只限制于RGB三波段8位的数据导入为一个RGB单波段24位的数据集
//	public static final MultiBandImportMode COMPOSITE = new MultiBandImportMode(2, 2);
}
