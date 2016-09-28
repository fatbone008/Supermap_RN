package com.supermap.data;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Datasources {
	
	private Workspace m_workspace;
	private ArrayList m_datasources;

	private static Integer m_lock = new Integer(0);

	/**
	 * ���⴦�����ڴ˴�������Ҫhandle��������ҪWorkspace�������һ����ڲ����캯����ͬ�����ﴫ����� Workspace����
	 */
	Datasources(Workspace workspace) {
		m_datasources = new ArrayList();
		m_workspace = workspace;
	}

	/**
	 * ��Ȼ����ͨ��Workspace�ӵײ�ȥȡ�������������ڶ��û�������ʱ�������һ�� ��˷��صĸ��������������������Ԫ�ص���Ŀ
	 */
	public int getCount() {
		if (m_workspace == null || m_workspace.getHandle() == 0
				|| m_datasources == null) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		int count = m_datasources.size();
		return count;
	}

	public Datasource create(DatasourceConnectionInfo connectionInfo) {
		if (m_workspace == null || m_workspace.getHandle() == 0
				|| m_datasources == null) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (connectionInfo == null || connectionInfo.getHandle() == 0) {
			String message = InternalResource.loadString("create()",
					InternalResource.DatasourcesConnectionInfoIsInvalid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		String alias = connectionInfo.getAlias();
		if (contains(alias)) {
			String message = InternalResource.loadString("connectionInfo",
					InternalResource.DatasourcesAliasIsAlreadyExsit,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		synchronized (m_lock) {
			long handle = DatasourcesNative.jni_Create(m_workspace.getHandle(),
					connectionInfo.getHandle());

			Datasource datasource = null;
			if (handle != 0) {
				datasource = new Datasource(handle, m_workspace);
				m_datasources.add(datasource);
			}
			return datasource;
		}
	}
	
	public Datasource get(int index) {
		if (m_workspace == null || m_workspace.getHandle() == 0
				|| m_datasources == null) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		// index���Բ����,JDK�����
		return (Datasource) m_datasources.get(index);
	}

	public Datasource get(String alias) {
		if (m_workspace == null || m_workspace.getHandle() == 0
				|| m_datasources == null) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		Datasource datasource = null;
		int index = indexOf(alias);
		if (index != -1) {
			datasource = (Datasource) m_datasources.get(index);
		}

		return datasource;
	}

	public int indexOf(String alias) {
		if (m_workspace == null || m_workspace.getHandle() == 0
				|| m_datasources == null) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int index = -1;
		if (alias != null && alias.trim().length() != 0) {
			index = DatasourcesNative.jni_IndexOf(m_workspace.getHandle(),
					alias);
		}
		return index;
	}
	
	public Datasource open(DatasourceConnectionInfo connectionInfo) {
		if (m_workspace == null || m_workspace.getHandle() == 0
				|| m_datasources == null) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (connectionInfo == null || connectionInfo.getHandle() == 0) {
			String message = InternalResource.loadString("open()",
					InternalResource.DatasourcesConnectionInfoIsInvalid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		String server = connectionInfo.getServer();
		
		int start = server.lastIndexOf('/')+1;
		int end   = server.lastIndexOf('.');
		String fileName = null;
		if(start<0 || end < 0 || start >= end){
			fileName = "";
		}else {
			fileName = server.substring(start, end);
		}
		
		String alias = connectionInfo.getAlias();
		// �����ļ����޸�Ĭ����
		if(!fileName.isEmpty() && alias.equals("UntitledDatasource")){
			alias = fileName;
			connectionInfo.setAlias(fileName);
		}
		
		if (contains(alias)) {
			String message = InternalResource.loadString("connectionInfo",
					InternalResource.DatasourcesAliasIsAlreadyExsit,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		synchronized (m_lock) {
			//WMS/WCS/WFS�������http���ط�ʽ������Դ  add by huangkj 2016-1-18
			if(connectionInfo.getDriver().equals("WMS")
				|| connectionInfo.getDriver().equals("WCS")
				|| connectionInfo.getDriver().equals("WFS") ){
				
				//WMS
				if(connectionInfo.getDriver().equals("WMS")){
					return openDatasourceWMS(connectionInfo);
				}
				//WCS/WFS
				else{
					return openDatasource(connectionInfo);
				}
				
			}
			else{
				// ���UGError�Ĵ����õ�����Դ����ʧ�ܵľ���ԭ��
				String[] temps = new String[1];
				long handle = DatasourcesNative.jni_Open(m_workspace.getHandle(),
						connectionInfo.getHandle(), temps);
				Datasource datasource = null;
				
				if (handle != 0) {
					/* ��Ҫ����reset����Ϊreset��ʹ��ͬ��Datasource���������ͬ��Handle */
					datasource = new Datasource(handle, m_workspace);
					m_datasources.add(datasource);
				} 
				
				return datasource;
			}
			
		}
	}

	//���̵߳ȴ����߳���������ٷ���
	boolean bOpenDatasource = false;
	//�������̴߳򿪵�����Դ
	Datasource mDatasource = null;
	/*
	 * http���ط�ʽ������Դ
	 */
	private Datasource openDatasource(final DatasourceConnectionInfo connectionInfo){
		bOpenDatasource = true;
		
		new Thread(){
			@Override
			public void run() {
				super.run();

				//��������
				mDatasource = downloadDatasourceFile(connectionInfo.getServer(), connectionInfo);
				
				bOpenDatasource = false;
			}
		}.start();
		
		//�ȴ����̴߳����
		while(bOpenDatasource){
			//���50ms�ж�һ��
			try{
				Thread.sleep(50);
			}
			catch(Exception e){
				return null;
			}
		}
		
		
		return mDatasource;
	}
	
	/*
	 * ���ش�����Դʱ�������ȡ���ļ�
	 */
	private Datasource downloadDatasourceFile(final String url, final DatasourceConnectionInfo connectionInfo){
		
		//��ʱ�ļ�Ŀ¼
		String strFilePath = getTemporaryPath();

		//��ʱ�ļ�����
		String strFileName = getTemporaryName(url);
		
		//�������ݵ�ָ���ı���Ŀ¼
		String strSaveFile = strFilePath + strFileName + ".tmp";
		
		File file = new File(strSaveFile);
		//�ļ������ڵĻ���Ҫ����һ��
		if( !file.exists() ){
			String curDataUrl = url;
			
			String curSaveFileName = strSaveFile;

			//long time1 = System.currentTimeMillis();
			
			try {
				//�����ַ�����ַ
				URL downUrl = new URL(curDataUrl);
				
		    	HttpURLConnection conn = (HttpURLConnection) downUrl.openConnection();
		        conn.setConnectTimeout(5*1000);
		        conn.setRequestMethod("GET");
		        conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		        conn.setRequestProperty("Accept-Language", "zh-CN");
		        conn.setRequestProperty("Referer", curDataUrl);//apkUrl
		        conn.setRequestProperty("Charset", "UTF-8");
		        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
		        conn.setRequestProperty("Connection", "Keep-Alive");
		        conn.connect();
		        
	            HttpURLConnection http = (HttpURLConnection) downUrl.openConnection();
	            http.setConnectTimeout(5 * 1000);
	            http.setRequestMethod("GET");
	            http.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
	            http.setRequestProperty("Accept-Language", "zh-CN");
	            http.setRequestProperty("Referer", downUrl.toString());
	            http.setRequestProperty("Charset", "UTF-8");
	            http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
	            http.setRequestProperty("Connection", "Keep-Alive");
	            
	            InputStream inStream = http.getInputStream();
	            byte[] buffer = new byte[1024];
	            int offset = 0;
	            
	            RandomAccessFile threadfile = new RandomAccessFile(curSaveFileName, "rwd");
	        	threadfile.seek(0);
	        	//��������
	            while ( (offset = inStream.read(buffer, 0, 1024)) != -1 ) {
	            	
	            	//���ļ�����дoffset���ֽ�
	                threadfile.write(buffer, 0, offset);
	            }
	
	        	//�ļ���д�벢�ر�
	            threadfile.close();
	            inStream.close();
	
	        } catch (Exception e) {
	        	//�������̵߳ȴ�
				bOpenDatasource = false;
				//�����쳣
				String message = InternalResource.loadString(e.toString(),
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
	        }
	
			//long time2 = System.currentTimeMillis();
			//System.out.println("Download File Cost: " + (time2 - time1));
		}

		//����http�������ص��ļ���ַ,����ʼ������Դʱֱ�Ӷ�ȡ�ļ�,������������
		DatasourcesNative.jni_SetFilePathRequest(m_workspace.getHandle(), strSaveFile);
		
		//������Դ,����Ҫͨ��CURL����GetCapabilities�ļ�,��ʼ�����ݼ�������Դ�Լ���Ӧͼ�����Ϣ
		String[] temps = new String[1];
		long handle = DatasourcesNative.jni_Open(m_workspace.getHandle(),
				connectionInfo.getHandle(), temps);
		Datasource datasource = null;
		
		if (handle != 0) {
			/* ��Ҫ����reset����Ϊreset��ʹ��ͬ��Datasource���������ͬ��Handle */
			datasource = new Datasource(handle, m_workspace);
			m_datasources.add(datasource);
		}

		return datasource;
	}
	
	private Datasource openDatasourceWMS(final DatasourceConnectionInfo connectionInfo){
		//��ʱ�ļ�Ŀ¼
		String strFilePath = getTemporaryPath();

		//��ʱ�ļ�����
		String strFileName = "imb";
		
		//�������ݵ�ָ���ı���Ŀ¼
		String strSaveFile = strFilePath + strFileName + ".tmp";
		

		//����http�������ص��ļ���ַ,����ʼ������Դʱֱ�Ӷ�ȡ�ļ�,������������
		DatasourcesNative.jni_SetFilePathRequest(m_workspace.getHandle(), strSaveFile);
		
		//������Դ,����Ҫͨ��CURL����GetCapabilities�ļ�,��ʼ�����ݼ�������Դ�Լ���Ӧͼ�����Ϣ
		String[] temps = new String[1];
		long handle = DatasourcesNative.jni_Open(m_workspace.getHandle(),
				connectionInfo.getHandle(), temps);
		Datasource datasource = null;
		
		if (handle != 0) {
			/* ��Ҫ����reset����Ϊreset��ʹ��ͬ��Datasource���������ͬ��Handle */
			datasource = new Datasource(handle, m_workspace);
			m_datasources.add(datasource);
		}
		
		mDatasource = datasource;
		
		return mDatasource;
	}
	

	/*
	 * ��ʱ�ļ�Ŀ¼
	 */
	private String getTemporaryPath(){
		
		//�洢�����ļ���Ŀ¼
		String strFilePath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/SuperMap/temp/";
		File filePath = new File(strFilePath);
		if( !filePath.exists() ){
			//�������򴴽��ļ���
			filePath.mkdirs();
		}
		
		return strFilePath;
	}

	/*
	 * ��ʱ�ļ�����
	 */
	private String getTemporaryName(String url){

		//�����ַ���������ļ�����
		String tmpUrl = url;
		//���������е���Ч�ַ�
		String tmpUrlReplace = tmpUrl.replace("http://", "");
		String tmpUrlReplace2 = tmpUrlReplace.replace("/", "");
		String tmpUrlReplace3 = tmpUrlReplace2.replace(".", "");
		String tmpUrlReplace4 = tmpUrlReplace3.replace("&", "");
		String tmpUrlReplace5 = tmpUrlReplace4.replace("=", "");
		String tmpUrlReplace6 = tmpUrlReplace5.replace(":", "");
		String tmpUrlReplace7 = tmpUrlReplace6.replace(",", "");
		String tmpUrlReplace8 = tmpUrlReplace7.replace("?", "");
		//���˿��ַ�
		String strUrlTrim = tmpUrlReplace8.trim();
		
		return strUrlTrim;
	}
	
	public boolean close(int index) {
		if (m_workspace == null || m_workspace.getHandle() == 0
				|| m_datasources == null) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		boolean result = false ;
		synchronized (m_lock) {
			Datasource datasource = get(index);
			String alias = datasource.getAlias();
			result = DatasourcesNative.jni_Close(m_workspace.getHandle(), index);
			if(result){
				m_datasources.remove(index);
			}
			return result;
		}
	}

	public boolean close(String alias) {
		if (m_workspace == null || m_workspace.getHandle() == 0
				|| m_datasources == null) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (alias == null || alias.trim().length() == 0) {
			String message = InternalResource.loadString("alias",
					InternalResource.GlobalStringIsNullOrEmpty,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		boolean result = false;
		
		int index = indexOf(alias);
		if (index != -1) {
			result = DatasourcesNative.jni_Close(m_workspace.getHandle(),index);
			if(result){
				m_datasources.remove(index);
			}
		}
			
		return result;
	}

	public void closeAll() {
		if (m_workspace == null || m_workspace.getHandle() == 0
				|| m_datasources == null) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// @modified by not attributable at 2007��12��20�� ����03ʱ19��48��
		// @reason:ԭ���� for (int i = 0; i < m_datasources.size(); i++)
		// ������close������remove����
		// �����͵�����m_datasources.size��С�ڱ䡣
		synchronized (m_lock) {
			int size = m_datasources.size();
			for (int i = size - 1; i >= 0; i--) {
				close(i);
			}
		}
	}

	void reset() {
		if (m_workspace == null || m_workspace.getHandle() == 0
				|| m_datasources == null) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		m_datasources.clear();
		int count = DatasourcesNative.jni_GetCount(m_workspace.getHandle());
		if (count > 0) {
			long[] handles = new long[count];
			DatasourcesNative.jni_GetDatasources(m_workspace.getHandle(),
					handles);
			for (int i = 0; i < count; i++) {
				Datasource datasource = new Datasource(handles[i], m_workspace);
				m_datasources.add(datasource);
			}
		}
	}
	
	private boolean contains(String alias) {
		if (m_workspace == null || m_workspace.getHandle() == 0
				|| m_datasources == null) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		int index = indexOf(alias);
		return (index != -1);
	}
	
}
