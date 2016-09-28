package com.supermap.data;

import java.util.ArrayList;

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
 * @author ������
 * @version 2.0
 */
final class ProductType extends Enum {
	
	private static ArrayList<Enum> m_values = new ArrayList<Enum>();
	
	ProductType(int value, int ugcValue) {
		super(value, ugcValue);
		super.setCustom(true);
	}

	// �������������
	public static final ProductType IOBJECTS_CORE_DEVELOP = new ProductType(1, 1);

	// ����������а�
	public static final ProductType IOBJECTS_CORE_RUNTIME = new ProductType(2, 2);

	// �ռ����ݿ����������
	public static final ProductType IOBJECTS_SDX_DEVELOP = new ProductType(3, 3);

	// �ռ����ݿ�������а�
	public static final ProductType IOBJECTS_SDX_RUNTIME = new ProductType(4, 4);

	// ��ά���������
	public static final ProductType IOBJECTS_SPACE_DEVELOP = new ProductType(5,
			5);

	// ��ά������а�
	public static final ProductType IOBJECTS_SPACE_RUNTIME = new ProductType(6,
			6);

	// �Ű��ӡ���������
	public static final ProductType IOBJECTS_LAYOUT_DEVELOP = new ProductType(7,
			7);

	// �Ű��ӡ������а�
	public static final ProductType IOBJECTS_LAYOUT_RUNTIME = new ProductType(8,
			8);

	// �ռ�������������
	public static final ProductType IOBJECTS_SPATIAL_DEVELOP = new ProductType(
			9, 9);

	// �ռ����������а�
	public static final ProductType IOBJECTS_SPATIAL_RUNTIME = new ProductType(
			10, 10);

	// ����������������
	public static final ProductType IOBJECTS_NETWORK_DEVELOP = new ProductType(
			11, 11);

	// �������������а�
	public static final ProductType IOBJECTS_NETWORK_RUNTIME = new ProductType(
			12, 12);

	// �������������
	public static final ProductType IOBJECTS_TOPOLOGY_DEVELOP = new ProductType(
			13, 13);

	// ����������а�
	public static final ProductType IOBJECTS_TOPOLOGY_RUNTIME = new ProductType(
			14, 14);

	// ��ַƥ�����������
	public static final ProductType IOBJECTS_ADDRESS_MATCHING_DEVELOP = new ProductType(
			15, 15);

	// ��ַƥ��������а�
	public static final ProductType IOBJECTS_ADDRESS_MATCHING_RUNTIME = new ProductType(
			16, 16);
	
	// FME��ʸ����ʽ
	public static final ProductType IOBJECTS_FME_VECTOR = new ProductType(
			18, 18);
	
	// FME��ESRI��ʽ
	public static final ProductType IOBJECTS_FME_ESRI = new ProductType(
			19, 19);
	
	// FME��դ���ʽ
	public static final ProductType IOBJECTS_FME_RASTER = new ProductType(
			20, 20);
	
	// FME��������ʽ
	public static final ProductType IOBJECTS_FME_OTHER = new ProductType(
			21, 21);
	
	// ��ͼ�����������չģ��  by zhoujt 2011-08-31
	public static final ProductType IOBJECTS_CHART_DEVELOP = new ProductType(22,22);
	
	// ��ͼ������а���չģ��  by zhoujt 2011-08-31
	public static final ProductType IOBJECTS_CHART_RUNTIME = new ProductType(23,23);
	
	// ��ά�ռ�������������
	public static final ProductType IOBJECTS_REALSPACE_SPATIAL_ANALYST_DEVELOP = new ProductType(24,24);
	
	// ��ά�ռ����������а�
	public static final ProductType IOBJECTS_REALSPACE_SPATIAL_ANALYST_RUNTIME = new ProductType(25,25);
	
	// UniversalGISCore ���
	public static final ProductType UNIVERSAL_GIS_CORE = new ProductType(26,26);
	
	// �����������������
	public static final ProductType IOBJECTS_TRAFFIC_ANALYST_DEVELOP = new ProductType(27,27);
	
	// ��������������а�
	public static final ProductType IOBJECTS_TRAFFIC_ANALYST_RUNTIME = new ProductType(28,28);
	
	// ��ά�������������
	public static final ProductType IOBJECTS_REALSPACE_NETWORK_ANALYST_DEVELOP = new ProductType(29,29);
	
	// ��ά����������а�
	public static final ProductType IOBJECTS_REALSPACE_NETWORK_ANALYST_RUNTIME = new ProductType(30,30);
	
	// ��ά��Ч���������
	public static final ProductType IOBJECTS_REALSPACE_EFFECT_DEVELOP = new ProductType(31,31);
	
	// ��ά��Ч������а�
	public static final ProductType IOBJECTS_REALSPACE_EFFECT_RUNTIME = new ProductType(32,32);
	
	// iServer��׼��
	public static final ProductType ISERVER_STANDARD = new ProductType(1000,
			1000);

	// iServerרҵ��
	public static final ProductType ISERVER_PROFESSIONAL = new ProductType(
			1001, 1001);

	// iServer�߼���
	public static final ProductType ISERVER_ENTERPRISE = new ProductType(1002,
			1002);

	// iServer�ռ������չ
	public static final ProductType ISERVER_SPATIAL = new ProductType(1003,
			1003);

	// iServer���������չ
	public static final ProductType ISERVER_NETWORK = new ProductType(1004,
			1004);

	// iServer��ͨ������չ
	public static final ProductType ISERVER_TRAFFIC_TRANSFER = new ProductType(
			1005, 1005);
	
	// iServer ��ά��չ
	public static final ProductType ISERVER_SPACE = new ProductType(1006,1006);
	
	// iServer ��ͼ�������
	public static final ProductType ISERVER_CHART = new ProductType(1007,1007);
	
	// iServer ������
	public static final ProductType IEXPRESS = new ProductType(1008,1008);
	
	// iPortal ������
	public static final ProductType IPORTAL_ENTERPRISE = new ProductType(1009,1009);
	
	// iPortal ������
	public static final ProductType IPORTAL_PROFESSIONAL = new ProductType(1010,1010);
	
	
    public static ProductType newInstance(int value) {
    	ProductType productType = new ProductType(value, value);
    	m_values.add(productType);
    	m_hashMap.put(ProductType.class, m_values);
    	return productType;
    }
}
