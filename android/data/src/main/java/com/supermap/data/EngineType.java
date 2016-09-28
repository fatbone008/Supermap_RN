package com.supermap.data;

import java.util.ArrayList;

/**
 * <p>Title: 引擎类型</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 李云锦
 * @version 2.0
 */
public class EngineType extends Enum {
	
	private static ArrayList<Enum> m_values = new ArrayList<Enum>();
	
    protected EngineType(int value, int ugcValue) {
        super(value, ugcValue);
        super.setCustom(true);
    }

    public static final EngineType IMAGEPLUGINS = new EngineType(5, 5);
//    public static final EngineType ORACLEPLUS = new EngineType(12, 12);
//    public static final EngineType SDBPLUS = new EngineType(14,14);
//    public static final EngineType SQLPLUS = new EngineType(16, 16);
//    public static final EngineType DB2 = new EngineType(18, 18);
    
    //OGC，对应原来的WEB
    public static final EngineType OGC = new EngineType(23, 23);
//    public static final EngineType MYSQL = new EngineType(32, 32);
  
    public static final EngineType VECTORFILE = new EngineType(101, 101);
    
    //modified by kongll 2009-03-12 修改为UDB对应UGC的UDBPlus
    public static final EngineType UDB = new EngineType(219, 219);
    
    //超图云数据源
    public static final EngineType SuperMapCloud = new EngineType(224, 224);
    
    public static final EngineType GoogleMaps = new EngineType(223, 223);
    
    public static final EngineType Rest = new EngineType(225, 225);
    
    public static final EngineType MapWorldMaps = new EngineType(226, 226);
    
    public static final EngineType BaiDu = new EngineType(227, 227);
    
    public static final EngineType BingMaps = new EngineType(230, 230);
    
    /**
     * OpenStreetMaps引擎，用于打开OpenStreetMaps数据源
     */
    public static final EngineType OpenStreetMaps = new EngineType(228, 228);
    
//    public static final EngineType NetCDF = new EngineType(220, 220);
    
//    public static final EngineType PostgreSQL = new EngineType(221, 221);
//    
//    public static final EngineType GoogleMaps = new EngineType(223, 223);
    
//    public static final EngineType SCV = new EngineType(229, 229);
    
    /**
     * OpenStreetMaps引擎，用于打开OpenStreetMaps数据源
     */
    public static final EngineType OpenGLCache = new EngineType(305, 305);
    
    public static EngineType newInstance(int value) {
    	EngineType engineType = new EngineType(value, value);
    	m_values.add(engineType);
    	m_hashMap.put(EngineType.class, m_values);
    	return engineType;
    }
}
