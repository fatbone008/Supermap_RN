package com.supermap.data;

import java.util.Locale;

/**
 * 数据源连接信息
 * 用户自定义的连接信息可以进行修改
 * 隶属于某个Datasource的数据源连接信息不可以修改，也不可以释放
 * 隶属于LinkItem的连接信息可以修改，但不可以释放
 * @author 李云锦
 * @version 2.0
 */
public class DatasourceConnectionInfo extends InternalHandleDisposable {
    private boolean m_readOnly = false;
    
    private DatasourceEncrytionType m_EncrytionType = null;

    public DatasourceConnectionInfo() {
        long handle = DatasourceConnectionInfoNative.jni_New();
        setHandle(handle, true);
        reset();
    }

//    /**
//     * Oracle构造函数
//     * tns对应着Server
//     */
//    public DatasourceConnectionInfo(String tns, String database, String alias,
//                                    String user, String password) {
//        long handle = DatasourceConnectionInfoNative.jni_New();
//        setHandle(handle, true);
//        if (alias == null || alias.trim().length() == 0) {
//            String message = InternalResource.loadString("alias",
//                    InternalResource.GlobalStringIsNullOrEmpty,
//                    InternalResource.BundleName);
//            throw new IllegalArgumentException(message);
//        }
//        this.reset(tns, "", database, alias, user, password,
//                   EngineType.ORACLEPLUS);
//
////        setEngineType(EngineType.ORACLEPLUS);
////        setServer(tns);
////        setDatabase(database);
////        setAlias(alias);
////        setUser(user);
////        setPassword(password);
//    }
//
//    /**
//     * Sql Server构造函数
//     * 如果driver 值为空，默认设置为"SQL Server"
//     */
//    public DatasourceConnectionInfo(String server, String driver,
//                                    String database, String alias, String user,
//                                    String password) {
//        long handle = DatasourceConnectionInfoNative.jni_New();
//        setHandle(handle, true);
//
//        if (driver == null) {
//            driver = "SQL Server";
//        }
//
//        if (alias == null || alias.trim().length() == 0) {
//            String message = InternalResource.loadString("alias",
//                    InternalResource.GlobalStringIsNullOrEmpty,
//                    InternalResource.BundleName);
//            throw new IllegalArgumentException(message);
//        }
//        this.reset(server, driver, database, alias, user, password,
//                   EngineType.SQLPLUS);
////        setEngineType(EngineType.SQLPLUS);
////        setServer(server);
////        setDriver(driver);
////        setDatabase(database);
////        setAlias(alias);
////        setUser(user);
////        setPassword(password);
//    }

    /**
     * 文件型构造函数
     * file对应着Server
     */
    public DatasourceConnectionInfo(String file, String alias, String password) {
        long handle = DatasourceConnectionInfoNative.jni_New();
        setHandle(handle, true);
        if (alias == null || alias.trim().length() == 0) {
            String message = InternalResource.loadString("alias",
                    InternalResource.GlobalStringIsNullOrEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        this.reset(file, "", "", alias, "", password, EngineType.UDB);
//        setEngineType(EngineType.SDBPLUS);
//        setServer(file);
//        setAlias(alias);
//        setPassword(password);
    }
    
    protected void clearHandle() {
        this.setHandle(0);
    }
    
    protected static void clearHandle(DatasourceConnectionInfo connectionInfo){
        connectionInfo.clearHandle();
    }
    
    DatasourceConnectionInfo(long handle) {
        this.setHandle(handle, false);
    }

    DatasourceConnectionInfo(Datasource datasource, long handle) {
        if (datasource == null || datasource.getHandle() == 0) {
            String message = InternalResource.loadString("datasource",
                    InternalResource.GlobalInvalidConstructorArgument,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        if (handle == 0) {
            String message = InternalResource.loadString("handle",
                    InternalResource.GlobalInvalidConstructorArgument,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        m_readOnly = true;
        setHandle(handle, false);
    }


    public String getAlias() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return DatasourceConnectionInfoNative.jni_GetAlias(getHandle());
    }

    public void setAlias(String value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString("setAlias()",
                    InternalResource.DatasourceConnInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if (value == null || value.trim().length() == 0) {
            String message = InternalResource.loadString("value",
                    InternalResource.GlobalStringIsNullOrEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        DatasourceConnectionInfoNative.jni_SetAlias(getHandle(), value);
    }

    public EngineType getEngineType() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        int ugcType = DatasourceConnectionInfoNative.jni_GetEngineType(
                getHandle());
        
        EngineType engineType = null;
        try {
        	engineType = (EngineType) Enum.parseUGCValue(EngineType.class, ugcType);
		} catch (Exception e) {
		
			engineType = new EngineType(ugcType, ugcType);
		}
		return engineType;
    }

    public void setEngineType(EngineType value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString("setEngineType()",
                    InternalResource.DatasourceConnInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        //这个去掉吧，因为用户调用的顺序不一样，会导致其他设置不能使用了
//        if(value.equals(EngineType.IMAGEPLUGINS)){
//        	this.setReadOnly(true);
//        }

        int ugcType = value.getUGCValue();
        DatasourceConnectionInfoNative.jni_SetEngineType(getHandle(), ugcType);
    }

    public String getServer() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return DatasourceConnectionInfoNative.jni_GetServer(getHandle());
    }

    public void setServer(String value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString("setServer()",
                    InternalResource.DatasourceConnInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if (value == null) {
            value = "";
        }
       	if(Locale.getDefault().equals(Locale.JAPANESE)){
    		value = value.replaceAll("\\\\","/");
    	}

//       	// modify by wangdy 2011-7-25
//       	// 如果路径的第一个字符不是'/'的话，需要添加上
//       	if(value.charAt(0) != '/'){
//       		value = '/' + value; 
//       	}
       	//本地数据路径才需要添加"/"
       	if(!value.contains("http") && value.charAt(0) != '/'){
       		value = "/" + value;
       	}
       	
        DatasourceConnectionInfoNative.jni_SetServer(getHandle(), value);
    }


    public String getUser() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return DatasourceConnectionInfoNative.jni_GetUser(getHandle());
    }

    public void setUser(String value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString("setUser()",
                    InternalResource.DatasourceConnInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if (value == null) {
            value = "";
        }
        DatasourceConnectionInfoNative.jni_SetUser(getHandle(), value);
    }

    /**
     * 获取连接信息中的密码，仅支持原有加密类型，即DEFAULT类型，若是AES加密,则返回空字符串
     * @return
     */
    public String getPassword() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if(getEncrytionType() == DatasourceEncrytionType.AES){
        	return "";
        }
        return DatasourceConnectionInfoNative.jni_GetPassword(getHandle());
    }

    /**
     * 获取连接信息中的密码，仅包内可用，用户不可见
     * @return
     */
    String getPasswordDefault() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return DatasourceConnectionInfoNative.jni_GetPassword(getHandle());
    }
    /**
     * 设置连接信息中的密码,密码类型为Default
     * @param value  密码
     */
    public void setPassword(String value){
    	setPassword(value, null);
    }
    
    // Modefied by Xingjun, 2015.11.20
    /**
     * 设置连接信息中的密码，同时需要指明该密码使用的加密类型
     * @param value  密码
     * @param type   密码使用的加密类型，如该参数为null,则按DEFAULT类型处理
     */
    public void setPassword(String value, DatasourceEncrytionType type) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString("setPassword()",
                    InternalResource.DatasourceConnInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        String password = null;
        if (value == null) {
            value = "";
        }
        
        int typeValue= 0;
        password = value;
		if (type != null) {
			if (type == DatasourceEncrytionType.AES) {

				value = formatPassword(value);
			}
			typeValue= type.getUGCValue();
		}
        
        if(typeValue == 0){
			m_EncrytionType = DatasourceEncrytionType.DEFAULT;
		}else if(typeValue == 1){
			m_EncrytionType = DatasourceEncrytionType.AES;
		}
        DatasourceConnectionInfoNative.jni_SetPassword(getHandle(), value, typeValue);
    }

    public boolean isReadOnly() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return DatasourceConnectionInfoNative.jni_GetReadOnly(getHandle());
    }

    public void setReadOnly(boolean value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString("setIsReadOnly()",
                    InternalResource.DatasourceConnInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }

        m_readOnly = value;
        DatasourceConnectionInfoNative.jni_SetReadOnly(getHandle(), value);
    }

    public void dispose() {
        if (!super.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (!(super.getHandle() == 0)) {
            DatasourceConnectionInfoNative.jni_Delete(getHandle());
            setHandle(0);
        }
    }

    void reset() {
        reset("", "", "", "UntitledDatasource", "", "", EngineType.UDB);
    }

    //为了不多次调用JNI,做一个JNI来干这个
    void reset(String server, String driver, String database, String alias,
               String user, String password, EngineType engineType) {
        if (alias == null || alias.trim().length() == 0) {
            String message = InternalResource.loadString("alias",
                    InternalResource.GlobalStringIsNullOrEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
      	if(Locale.getDefault().equals(Locale.JAPANESE)){
      		server = server.replaceAll("\\\\","/");
    	}
        if (getHandle() != 0) {
            int ugcType = engineType.getUGCValue();
            DatasourceConnectionInfoNative.jni_Reset(getHandle(), server,
                    driver,
                    database, alias, user, password, ugcType);
        }
    }
    
    public void setDriver(String value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString("setDriver()",
                    InternalResource.DatasourceConnInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if (value == null) {
            value = "SQL Server";
        }
        DatasourceConnectionInfoNative.jni_SetDriver(getHandle(), value);
    }
    
    public String getDriver() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return DatasourceConnectionInfoNative.jni_GetDriver(getHandle());
    }

	////////////////////////////////////////////////////////////////////////////
	//设置获取Web服务类型(Rest/OGC)数据源的坐标参考系  add by huangkj 2015-12-18
    /**
     * @params String
     * @marks 设置Web服务类型(Rest/OGC)数据源的坐标参考系
     */
    public void setWebCoordinate(String value){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("setWebCoordinate()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

	    if (m_readOnly) {
	        String message = InternalResource.loadString("setWebCoordinate()",
	                InternalResource.DatasourceConnInfoCantSetProperty,
	                InternalResource.BundleName);
	        throw new UnsupportedOperationException(message);
	    }
	    if (value == null) {
	        value = "SQL Server";
	    }
	      
        DatasourceConnectionInfoNative.jni_SetWebCoordinate(getHandle(), value);
    }

    /**
     * @params
     * @marks 获取Web服务类型(Rest/OGC)数据源的坐标参考系
     */
    public String getWebCoordinate(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getWebCoordinate()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        return DatasourceConnectionInfoNative.jni_GetWebCoordinate(getHandle());
    }
	////////////////////////////////////////////////////////////////////////////
    

	////////////////////////////////////////////////////////////////////////////
	//设置获取WMS服务外设参数,这些参数的设置和以下载文件的方式打开数据源相互独立  add by huangkj 2016-1-27
    /**
     * @params String
     * @marks 设置WMS服务的版本号
     */
    public void setWebVersion(String value){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("setWebVersion()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString("setWebVersion()",
                    InternalResource.DatasourceConnInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        
        DatasourceConnectionInfoNative.jni_SetWebVersion(getHandle(), value);
    }

    /**
     * @params
     * @marks 获取WMS服务的版本号
     */
    public String getWebVersion(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getWebVersion()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        return DatasourceConnectionInfoNative.jni_GetWebVersion(getHandle());
    }

    /**
     * @params String
     * @marks 设置WMS服务的图片格式
     */
    public void setWebFormat(String value){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("setWebFormat()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString("setWebFormat()",
                    InternalResource.DatasourceConnInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        
        DatasourceConnectionInfoNative.jni_SetWebFormat(getHandle(), value);
    }

    /**
     * @params
     * @marks 获取WMS服务的图片格式
     */
    public String getWebFormat(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getWebFormat()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        return DatasourceConnectionInfoNative.jni_GetWebFormat(getHandle());
    }
    /**
     * @params String
     * @marks 设置WMS服务的可见图层集合
     */
    public void setWebVisibleLayers(String value){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("setWebVisibleLayers()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString("setWebVisibleLayers()",
                    InternalResource.DatasourceConnInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        
        DatasourceConnectionInfoNative.jni_SetWebLayers(getHandle(), value);
    }
    /**
     * @params
     * @marks 获取WMS服务的可见图层集合
     */
    public String getWebVisibleLayers(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getWebVisibleLayers()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        return DatasourceConnectionInfoNative.jni_GetWebLayers(getHandle());
    }

    /**
     * @params Rectangle2D
     * @marks 设置WMS服务的地图范围
     */
    public void setWebBBox(Rectangle2D rect){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("setWebVisibleLayers()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString("setWebVisibleLayers()",
                    InternalResource.DatasourceConnInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        
        if(rect == null){
			String message = InternalResource.loadString("setWebVisibleLayers()",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
        }
    	
        if(rect.getWidth() < 0 || rect.getHeight() < 0){
			String message = InternalResource.loadString("setWebVisibleLayers()",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
        }
        
        DatasourceConnectionInfoNative.jni_SetWebBBOX(getHandle(), rect.getLeft(), rect
				.getBottom(), rect.getRight(), rect.getTop());
    }
    
    /**
     * @params
     * @marks 设置WMS服务的地图范围
     */
    public Rectangle2D getWebBBox(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getWebVisibleLayers()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        double[] params = new double[4];
        DatasourceConnectionInfoNative.jni_GetWebBBOX(getHandle(), params);
        Rectangle2D rect = new Rectangle2D(params[0], params[1], params[2], params[3]);
        
        return rect;
    }

    /**
     * @params
     * @marks 获取WMS服务的扩展参数
     */
    public String getWebExtendParam(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getWebVisibleLayers()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        return DatasourceConnectionInfoNative.jni_GetWebExtendParam(getHandle());
    }

    /**
     * @params String
     * @marks 设置WMS服务的扩展参数
     */
    public void setWebExtendParam(String value){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("setWebVisibleLayers()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (m_readOnly) {
            String message = InternalResource.loadString("setWebVisibleLayers()",
                    InternalResource.DatasourceConnInfoCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        
        if(value == null){
			String message = InternalResource.loadString("setWebVisibleLayers()",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
        }
        
        DatasourceConnectionInfoNative.jni_SetWebExtendParam(getHandle(), value);
    }
	////////////////////////////////////////////////////////////////////////////
    
    
	protected static DatasourceConnectionInfo createInstance(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new DatasourceConnectionInfo(handle);
	} 
	
	// Added by Xingjun, 2015.11.20
	// 获取当前的加密类型
	DatasourceEncrytionType getEncrytionType(){
		if(m_EncrytionType == null && getHandle() != 0){
			
			int typeValue = DatasourceConnectionInfoNative.jni_GetEncryptionType(getHandle());
			if(typeValue == 0){
				m_EncrytionType = DatasourceEncrytionType.DEFAULT;
			}else if(typeValue == 1){
				m_EncrytionType = DatasourceEncrytionType.AES;
			}
		}
		return m_EncrytionType;
	}
	
	// 对AES的密码进行修正
	static String formatPassword(String password){
		if(password ==null){
			return "";
		}
		if(password.length() < 8){
			password = password + "{qe!@#R%";
		}else if(password.length() >128){
			password = password.substring(0, 128);
		}
		
		return password;
	}// Added by Xingjun, 2015.11.20
}
