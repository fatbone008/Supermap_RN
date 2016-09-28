package com.supermap.data;

class FileType extends com.supermap.data.Enum {
	private FileType(int value, int ugcValue) {
		super(value, ugcValue);
	}

	public static final FileType NONE = new FileType(0, 0);

	//bitmap文件, 导入结果为单个影像数据
	public static final FileType BMP = new FileType(121, 121);

	//portal network graphic(PNG)	，导入结果为单个影像数据
	public static final FileType PNG = new FileType(123, 123);

	//Tiff/GeoTIFF文件，导入结果可以是多波段或单波段
	public static final FileType TIF = new FileType(103, 103);

	//jpg文件，导入结果为单个影像
	public static final FileType JPG = new FileType(122, 122);

	//	gif文件，导入结果可以是单个或多个数据集
	public static final FileType GIF = new FileType(124, 124);

	//Erdas Image文件，导入结果可以是多波段或单波段数据集
	public static final FileType IMG = new FileType(101, 101);

	//Arc/Info ASCII 交换文件，导入结果为单个Grid数据
	public static final FileType GRD = new FileType(142, 142);
	
	// 国标格网数据文件
	public static final FileType GBDEM = new FileType(143, 143);
	
	// USGS格网数据文件 美国标准
	public static final FileType USGSDEM = new FileType(144, 144);

	//raw文件，导入结果为单个影像数据
	public static final FileType RAW = new FileType(161, 161);

	//电信clutter格式
	public static final FileType TEMSClutter = new FileType(146,  146);

	//三维模型缓存文件
	public static final FileType SCV = new FileType(63, 63);
	
   //ArcConverage文件，导入为单个矢量数据集
	public static final FileType COVERAGE = new FileType(6,6);
	//ArcView E00文件，导入结果为单个矢量数据集	
	public static final FileType E00 = new FileType(7, 7);
	
	//ArcView Shape文件，导入结果为单个矢量数据集	
	public static final FileType SHP = new FileType(8, 8);

	//MapInfo TAB，导入结果可以是多个不同类型的矢量数据
	public static final FileType TAB = new FileType(11, 11);

	//MapInfo MIF 交换文件，导入结果可以是多个不同类型的矢量数据
	public static final FileType MIF = new FileType(12, 12);

	//MapInfo 工作空间文件，连同地图，符号库，数据源一起转换
	public static final FileType WOR = new FileType(13, 13);
	
	//激光雷达数据
	public static final FileType LIDAR = new FileType(17, 17);

	//AutoCAD DXF 交换文件，导入结果可以是多个不同类型的矢量数据
	public static final FileType DXF = new FileType(3, 3);

	//AutoCAD DWG 交换文件，导入结果可以是多个不同类型的矢量数据
	public static final FileType DWG = new FileType(2, 2);
	
	//GML格式
	public static final FileType GML = new FileType(51,51);
	//Google Earth KML 交换文件，导入结果可以是多个不同类型的矢量数据
	public static final FileType KML = new FileType(53, 53);
	
	//Google Earth KMZ 交换文件，导入结果可以是多个不同类型的矢量数据
	public static final FileType KMZ = new FileType(54, 54);
	
	//MapGis明码文件wat、wal、wap，导入结果可以是多个不同类型的矢量数据
	public static final FileType MAPGIS = new FileType(55, 55);
	
	//电子海图格式，sp2不发布
	public static final FileType ENCS57 = new FileType(40, 40);
	
	//电信Vector格式
	public static final FileType TEMSVector = new FileType(41, 41);
	
	//电信Building vector格式
	public static final FileType TEMSBuildingVector = new FileType(42, 42);
	
	public static final FileType CSV = new FileType(64, 64);
	
	public static final FileType SDEVector = new FileType(68, 68);
	
	//SDERaster格式
	public static final FileType SDERaster = new FileType(69, 69);
	
	public static final FileType FileGDBVector = new FileType(70, 70);

	//GDBFRaster格式
	public static final FileType FileGDBRaster = new FileType(71, 71);
	//Edited by Helh 详细设计变更561
	//Microsoft Access 数据库文件
//	public static final FileType MDB = new FileType(62, 62);

	//SuperMap压缩影像SuperMap Image Tower，导入结果为单个影像数据
	public static final FileType SIT = new FileType(204, 204);
	
    //DGN格式
	public static final FileType DGN = new FileType(16,16);
	
	//x模型
	public static final FileType ModelX = new FileType(503,503);
	
	// osg ,osgb 模型文件
	
	public static final FileType ModelOSG = new FileType(505,505);
	
	//bil 文件
	public static final FileType BIL  = new FileType(141,141);
	
	//BIp 文件
	public static final FileType BIP = new FileType(148,148);
	
	//BSQ 文件
	public static final FileType BSQ = new FileType(149,149);
	
	//!Arc/Info binary grid文件
	public static final FileType ARCINFO_BINGRID = new FileType(145, 145);
}
