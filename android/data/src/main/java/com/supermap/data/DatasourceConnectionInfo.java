package com.supermap.data;

import java.util.Locale;

/**
 * ����Դ������Ϣ
 * �û��Զ����������Ϣ���Խ����޸�
 * ������ĳ��Datasource������Դ������Ϣ�������޸ģ�Ҳ�������ͷ�
 * ������LinkItem��������Ϣ�����޸ģ����������ͷ�
 * @author ���ƽ�
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
//     * Oracle���캯��
//     * tns��Ӧ��Server
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
//     * Sql Server���캯��
//     * ���driver ֵΪ�գ�Ĭ������Ϊ"SQL Server"
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
     * �ļ��͹��캯��
     * file��Ӧ��Server
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
        //���ȥ���ɣ���Ϊ�û����õ�˳��һ�����ᵼ���������ò���ʹ����
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
//       	// ���·���ĵ�һ���ַ�����'/'�Ļ�����Ҫ�����
//       	if(value.charAt(0) != '/'){
//       		value = '/' + value; 
//       	}
       	//��������·������Ҫ���"/"
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
     * ��ȡ������Ϣ�е����룬��֧��ԭ�м������ͣ���DEFAULT���ͣ�����AES����,�򷵻ؿ��ַ���
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
     * ��ȡ������Ϣ�е����룬�����ڿ��ã��û����ɼ�
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
     * ����������Ϣ�е�����,��������ΪDefault
     * @param value  ����
     */
    public void setPassword(String value){
    	setPassword(value, null);
    }
    
    // Modefied by Xingjun, 2015.11.20
    /**
     * ����������Ϣ�е����룬ͬʱ��Ҫָ��������ʹ�õļ�������
     * @param value  ����
     * @param type   ����ʹ�õļ������ͣ���ò���Ϊnull,��DEFAULT���ʹ���
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

    //Ϊ�˲���ε���JNI,��һ��JNI�������
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
	//���û�ȡWeb��������(Rest/OGC)����Դ������ο�ϵ  add by huangkj 2015-12-18
    /**
     * @params String
     * @marks ����Web��������(Rest/OGC)����Դ������ο�ϵ
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
     * @marks ��ȡWeb��������(Rest/OGC)����Դ������ο�ϵ
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
	//���û�ȡWMS�����������,��Щ���������ú��������ļ��ķ�ʽ������Դ�໥����  add by huangkj 2016-1-27
    /**
     * @params String
     * @marks ����WMS����İ汾��
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
     * @marks ��ȡWMS����İ汾��
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
     * @marks ����WMS�����ͼƬ��ʽ
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
     * @marks ��ȡWMS�����ͼƬ��ʽ
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
     * @marks ����WMS����Ŀɼ�ͼ�㼯��
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
     * @marks ��ȡWMS����Ŀɼ�ͼ�㼯��
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
     * @marks ����WMS����ĵ�ͼ��Χ
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
     * @marks ����WMS����ĵ�ͼ��Χ
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
     * @marks ��ȡWMS�������չ����
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
     * @marks ����WMS�������չ����
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
	// ��ȡ��ǰ�ļ�������
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
	
	// ��AES�������������
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
