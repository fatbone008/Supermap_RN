package com.supermap.data;

class FileType extends com.supermap.data.Enum {
	private FileType(int value, int ugcValue) {
		super(value, ugcValue);
	}

	public static final FileType NONE = new FileType(0, 0);

	//bitmap�ļ�, ������Ϊ����Ӱ������
	public static final FileType BMP = new FileType(121, 121);

	//portal network graphic(PNG)	��������Ϊ����Ӱ������
	public static final FileType PNG = new FileType(123, 123);

	//Tiff/GeoTIFF�ļ��������������Ƕನ�λ򵥲���
	public static final FileType TIF = new FileType(103, 103);

	//jpg�ļ���������Ϊ����Ӱ��
	public static final FileType JPG = new FileType(122, 122);

	//	gif�ļ��������������ǵ����������ݼ�
	public static final FileType GIF = new FileType(124, 124);

	//Erdas Image�ļ��������������Ƕನ�λ򵥲������ݼ�
	public static final FileType IMG = new FileType(101, 101);

	//Arc/Info ASCII �����ļ���������Ϊ����Grid����
	public static final FileType GRD = new FileType(142, 142);
	
	// ������������ļ�
	public static final FileType GBDEM = new FileType(143, 143);
	
	// USGS���������ļ� ������׼
	public static final FileType USGSDEM = new FileType(144, 144);

	//raw�ļ���������Ϊ����Ӱ������
	public static final FileType RAW = new FileType(161, 161);

	//����clutter��ʽ
	public static final FileType TEMSClutter = new FileType(146,  146);

	//��άģ�ͻ����ļ�
	public static final FileType SCV = new FileType(63, 63);
	
   //ArcConverage�ļ�������Ϊ����ʸ�����ݼ�
	public static final FileType COVERAGE = new FileType(6,6);
	//ArcView E00�ļ���������Ϊ����ʸ�����ݼ�	
	public static final FileType E00 = new FileType(7, 7);
	
	//ArcView Shape�ļ���������Ϊ����ʸ�����ݼ�	
	public static final FileType SHP = new FileType(8, 8);

	//MapInfo TAB�������������Ƕ����ͬ���͵�ʸ������
	public static final FileType TAB = new FileType(11, 11);

	//MapInfo MIF �����ļ��������������Ƕ����ͬ���͵�ʸ������
	public static final FileType MIF = new FileType(12, 12);

	//MapInfo �����ռ��ļ�����ͬ��ͼ�����ſ⣬����Դһ��ת��
	public static final FileType WOR = new FileType(13, 13);
	
	//�����״�����
	public static final FileType LIDAR = new FileType(17, 17);

	//AutoCAD DXF �����ļ��������������Ƕ����ͬ���͵�ʸ������
	public static final FileType DXF = new FileType(3, 3);

	//AutoCAD DWG �����ļ��������������Ƕ����ͬ���͵�ʸ������
	public static final FileType DWG = new FileType(2, 2);
	
	//GML��ʽ
	public static final FileType GML = new FileType(51,51);
	//Google Earth KML �����ļ��������������Ƕ����ͬ���͵�ʸ������
	public static final FileType KML = new FileType(53, 53);
	
	//Google Earth KMZ �����ļ��������������Ƕ����ͬ���͵�ʸ������
	public static final FileType KMZ = new FileType(54, 54);
	
	//MapGis�����ļ�wat��wal��wap�������������Ƕ����ͬ���͵�ʸ������
	public static final FileType MAPGIS = new FileType(55, 55);
	
	//���Ӻ�ͼ��ʽ��sp2������
	public static final FileType ENCS57 = new FileType(40, 40);
	
	//����Vector��ʽ
	public static final FileType TEMSVector = new FileType(41, 41);
	
	//����Building vector��ʽ
	public static final FileType TEMSBuildingVector = new FileType(42, 42);
	
	public static final FileType CSV = new FileType(64, 64);
	
	public static final FileType SDEVector = new FileType(68, 68);
	
	//SDERaster��ʽ
	public static final FileType SDERaster = new FileType(69, 69);
	
	public static final FileType FileGDBVector = new FileType(70, 70);

	//GDBFRaster��ʽ
	public static final FileType FileGDBRaster = new FileType(71, 71);
	//Edited by Helh ��ϸ��Ʊ��561
	//Microsoft Access ���ݿ��ļ�
//	public static final FileType MDB = new FileType(62, 62);

	//SuperMapѹ��Ӱ��SuperMap Image Tower��������Ϊ����Ӱ������
	public static final FileType SIT = new FileType(204, 204);
	
    //DGN��ʽ
	public static final FileType DGN = new FileType(16,16);
	
	//xģ��
	public static final FileType ModelX = new FileType(503,503);
	
	// osg ,osgb ģ���ļ�
	
	public static final FileType ModelOSG = new FileType(505,505);
	
	//bil �ļ�
	public static final FileType BIL  = new FileType(141,141);
	
	//BIp �ļ�
	public static final FileType BIP = new FileType(148,148);
	
	//BSQ �ļ�
	public static final FileType BSQ = new FileType(149,149);
	
	//!Arc/Info binary grid�ļ�
	public static final FileType ARCINFO_BINGRID = new FileType(145, 145);
}
