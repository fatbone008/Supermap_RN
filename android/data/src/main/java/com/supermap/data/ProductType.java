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
 * @author 孔令亮
 * @version 2.0
 */
final class ProductType extends Enum {
	
	private static ArrayList<Enum> m_values = new ArrayList<Enum>();
	
	ProductType(int value, int ugcValue) {
		super(value, ugcValue);
		super.setCustom(true);
	}

	// 核心组件开发版
	public static final ProductType IOBJECTS_CORE_DEVELOP = new ProductType(1, 1);

	// 核心组件运行版
	public static final ProductType IOBJECTS_CORE_RUNTIME = new ProductType(2, 2);

	// 空间数据库组件开发版
	public static final ProductType IOBJECTS_SDX_DEVELOP = new ProductType(3, 3);

	// 空间数据库组件运行版
	public static final ProductType IOBJECTS_SDX_RUNTIME = new ProductType(4, 4);

	// 三维组件开发版
	public static final ProductType IOBJECTS_SPACE_DEVELOP = new ProductType(5,
			5);

	// 三维组件运行版
	public static final ProductType IOBJECTS_SPACE_RUNTIME = new ProductType(6,
			6);

	// 排版打印组件开发版
	public static final ProductType IOBJECTS_LAYOUT_DEVELOP = new ProductType(7,
			7);

	// 排版打印组件运行版
	public static final ProductType IOBJECTS_LAYOUT_RUNTIME = new ProductType(8,
			8);

	// 空间分析组件开发版
	public static final ProductType IOBJECTS_SPATIAL_DEVELOP = new ProductType(
			9, 9);

	// 空间分析组件运行版
	public static final ProductType IOBJECTS_SPATIAL_RUNTIME = new ProductType(
			10, 10);

	// 网络分析组件开发版
	public static final ProductType IOBJECTS_NETWORK_DEVELOP = new ProductType(
			11, 11);

	// 网络分析组件运行版
	public static final ProductType IOBJECTS_NETWORK_RUNTIME = new ProductType(
			12, 12);

	// 拓扑组件开发版
	public static final ProductType IOBJECTS_TOPOLOGY_DEVELOP = new ProductType(
			13, 13);

	// 拓扑组件运行版
	public static final ProductType IOBJECTS_TOPOLOGY_RUNTIME = new ProductType(
			14, 14);

	// 地址匹配组件开发版
	public static final ProductType IOBJECTS_ADDRESS_MATCHING_DEVELOP = new ProductType(
			15, 15);

	// 地址匹配组件运行版
	public static final ProductType IOBJECTS_ADDRESS_MATCHING_RUNTIME = new ProductType(
			16, 16);
	
	// FME的矢量格式
	public static final ProductType IOBJECTS_FME_VECTOR = new ProductType(
			18, 18);
	
	// FME的ESRI格式
	public static final ProductType IOBJECTS_FME_ESRI = new ProductType(
			19, 19);
	
	// FME的栅格格式
	public static final ProductType IOBJECTS_FME_RASTER = new ProductType(
			20, 20);
	
	// FME的其它格式
	public static final ProductType IOBJECTS_FME_OTHER = new ProductType(
			21, 21);
	
	// 海图组件开发版扩展模块  by zhoujt 2011-08-31
	public static final ProductType IOBJECTS_CHART_DEVELOP = new ProductType(22,22);
	
	// 海图组件运行版扩展模块  by zhoujt 2011-08-31
	public static final ProductType IOBJECTS_CHART_RUNTIME = new ProductType(23,23);
	
	// 三维空间分析组件开发版
	public static final ProductType IOBJECTS_REALSPACE_SPATIAL_ANALYST_DEVELOP = new ProductType(24,24);
	
	// 三维空间分析组件运行版
	public static final ProductType IOBJECTS_REALSPACE_SPATIAL_ANALYST_RUNTIME = new ProductType(25,25);
	
	// UniversalGISCore 类库
	public static final ProductType UNIVERSAL_GIS_CORE = new ProductType(26,26);
	
	// 公交分析组件开发版
	public static final ProductType IOBJECTS_TRAFFIC_ANALYST_DEVELOP = new ProductType(27,27);
	
	// 公交分析组件运行版
	public static final ProductType IOBJECTS_TRAFFIC_ANALYST_RUNTIME = new ProductType(28,28);
	
	// 三维网络分析开发版
	public static final ProductType IOBJECTS_REALSPACE_NETWORK_ANALYST_DEVELOP = new ProductType(29,29);
	
	// 三维网络分析运行版
	public static final ProductType IOBJECTS_REALSPACE_NETWORK_ANALYST_RUNTIME = new ProductType(30,30);
	
	// 三维特效组件开发版
	public static final ProductType IOBJECTS_REALSPACE_EFFECT_DEVELOP = new ProductType(31,31);
	
	// 三维特效组件运行版
	public static final ProductType IOBJECTS_REALSPACE_EFFECT_RUNTIME = new ProductType(32,32);
	
	// iServer标准版
	public static final ProductType ISERVER_STANDARD = new ProductType(1000,
			1000);

	// iServer专业版
	public static final ProductType ISERVER_PROFESSIONAL = new ProductType(
			1001, 1001);

	// iServer高级版
	public static final ProductType ISERVER_ENTERPRISE = new ProductType(1002,
			1002);

	// iServer空间分析扩展
	public static final ProductType ISERVER_SPATIAL = new ProductType(1003,
			1003);

	// iServer网络分析扩展
	public static final ProductType ISERVER_NETWORK = new ProductType(1004,
			1004);

	// iServer交通换乘扩展
	public static final ProductType ISERVER_TRAFFIC_TRANSFER = new ProductType(
			1005, 1005);
	
	// iServer 三维扩展
	public static final ProductType ISERVER_SPACE = new ProductType(1006,1006);
	
	// iServer 海图服务许可
	public static final ProductType ISERVER_CHART = new ProductType(1007,1007);
	
	// iServer 基础版
	public static final ProductType IEXPRESS = new ProductType(1008,1008);
	
	// iPortal 基础版
	public static final ProductType IPORTAL_ENTERPRISE = new ProductType(1009,1009);
	
	// iPortal 基础版
	public static final ProductType IPORTAL_PROFESSIONAL = new ProductType(1010,1010);
	
	
    public static ProductType newInstance(int value) {
    	ProductType productType = new ProductType(value, value);
    	m_values.add(productType);
    	m_hashMap.put(ProductType.class, m_values);
    	return productType;
    }
}
