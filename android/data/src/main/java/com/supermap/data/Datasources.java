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
	 * 特殊处理，由于此处不仅需要handle，而且需要Workspace，因此与一般的内部构造函数不同，这里传入的是 Workspace对象
	 */
	Datasources(Workspace workspace) {
		m_datasources = new ArrayList();
		m_workspace = workspace;
	}

	/**
	 * 虽然可以通过Workspace从底层去取个数，而这样在多用户操作的时候会引起不一致 因此返回的个数仍是所包含的数组的元素的数目
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

		// index可以不检查,JDK会检查的
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
		// 根据文件名修改默认名
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
			//WMS/WCS/WFS服务采用http下载方式打开数据源  add by huangkj 2016-1-18
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
				// 添加UGError的处理，得到数据源连接失败的具体原因
				String[] temps = new String[1];
				long handle = DatasourcesNative.jni_Open(m_workspace.getHandle(),
						connectionInfo.getHandle(), temps);
				Datasource datasource = null;
				
				if (handle != 0) {
					/* 不要调用reset，因为reset会使不同的Datasource对象具有相同的Handle */
					datasource = new Datasource(handle, m_workspace);
					m_datasources.add(datasource);
				} 
				
				return datasource;
			}
			
		}
	}

	//主线程等待子线程下载完成再返回
	boolean bOpenDatasource = false;
	//保留子线程打开的数据源
	Datasource mDatasource = null;
	/*
	 * http下载方式打开数据源
	 */
	private Datasource openDatasource(final DatasourceConnectionInfo connectionInfo){
		bOpenDatasource = true;
		
		new Thread(){
			@Override
			public void run() {
				super.run();

				//下载数据
				mDatasource = downloadDatasourceFile(connectionInfo.getServer(), connectionInfo);
				
				bOpenDatasource = false;
			}
		}.start();
		
		//等待子线程打开完毕
		while(bOpenDatasource){
			//间隔50ms判断一次
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
	 * 下载打开数据源时从网络获取的文件
	 */
	private Datasource downloadDatasourceFile(final String url, final DatasourceConnectionInfo connectionInfo){
		
		//临时文件目录
		String strFilePath = getTemporaryPath();

		//临时文件名称
		String strFileName = getTemporaryName(url);
		
		//下载数据到指定的本地目录
		String strSaveFile = strFilePath + strFileName + ".tmp";
		
		File file = new File(strSaveFile);
		//文件不存在的话需要下载一份
		if( !file.exists() ){
			String curDataUrl = url;
			
			String curSaveFileName = strSaveFile;

			//long time1 = System.currentTimeMillis();
			
			try {
				//下载字符串地址
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
	        	//下载数据
	            while ( (offset = inStream.read(buffer, 0, 1024)) != -1 ) {
	            	
	            	//往文件流中写offset个字节
	                threadfile.write(buffer, 0, offset);
	            }
	
	        	//文件流写入并关闭
	            threadfile.close();
	            inStream.close();
	
	        } catch (Exception e) {
	        	//结束主线程等待
				bOpenDatasource = false;
				//下载异常
				String message = InternalResource.loadString(e.toString(),
						InternalResource.HandleObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalStateException(message);
	        }
	
			//long time2 = System.currentTimeMillis();
			//System.out.println("Download File Cost: " + (time2 - time1));
		}

		//设置http请求下载的文件地址,类库初始化数据源时直接读取文件,不用再下载了
		DatasourcesNative.jni_SetFilePathRequest(m_workspace.getHandle(), strSaveFile);
		
		//打开数据源,不需要通过CURL下载GetCapabilities文件,初始化数据集和数据源以及对应图层的信息
		String[] temps = new String[1];
		long handle = DatasourcesNative.jni_Open(m_workspace.getHandle(),
				connectionInfo.getHandle(), temps);
		Datasource datasource = null;
		
		if (handle != 0) {
			/* 不要调用reset，因为reset会使不同的Datasource对象具有相同的Handle */
			datasource = new Datasource(handle, m_workspace);
			m_datasources.add(datasource);
		}

		return datasource;
	}
	
	private Datasource openDatasourceWMS(final DatasourceConnectionInfo connectionInfo){
		//临时文件目录
		String strFilePath = getTemporaryPath();

		//临时文件名称
		String strFileName = "imb";
		
		//下载数据到指定的本地目录
		String strSaveFile = strFilePath + strFileName + ".tmp";
		

		//设置http请求下载的文件地址,类库初始化数据源时直接读取文件,不用再下载了
		DatasourcesNative.jni_SetFilePathRequest(m_workspace.getHandle(), strSaveFile);
		
		//打开数据源,不需要通过CURL下载GetCapabilities文件,初始化数据集和数据源以及对应图层的信息
		String[] temps = new String[1];
		long handle = DatasourcesNative.jni_Open(m_workspace.getHandle(),
				connectionInfo.getHandle(), temps);
		Datasource datasource = null;
		
		if (handle != 0) {
			/* 不要调用reset，因为reset会使不同的Datasource对象具有相同的Handle */
			datasource = new Datasource(handle, m_workspace);
			m_datasources.add(datasource);
		}
		
		mDatasource = datasource;
		
		return mDatasource;
	}
	

	/*
	 * 临时文件目录
	 */
	private String getTemporaryPath(){
		
		//存储下载文件的目录
		String strFilePath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/SuperMap/temp/";
		File filePath = new File(strFilePath);
		if( !filePath.exists() ){
			//不存在则创建文件件
			filePath.mkdirs();
		}
		
		return strFilePath;
	}

	/*
	 * 临时文件名称
	 */
	private String getTemporaryName(String url){

		//请求字符串构造出文件名称
		String tmpUrl = url;
		//过滤请求串中的无效字符
		String tmpUrlReplace = tmpUrl.replace("http://", "");
		String tmpUrlReplace2 = tmpUrlReplace.replace("/", "");
		String tmpUrlReplace3 = tmpUrlReplace2.replace(".", "");
		String tmpUrlReplace4 = tmpUrlReplace3.replace("&", "");
		String tmpUrlReplace5 = tmpUrlReplace4.replace("=", "");
		String tmpUrlReplace6 = tmpUrlReplace5.replace(":", "");
		String tmpUrlReplace7 = tmpUrlReplace6.replace(",", "");
		String tmpUrlReplace8 = tmpUrlReplace7.replace("?", "");
		//过滤空字符
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
		// @modified by not attributable at 2007年12月20日 下午03时19分48秒
		// @reason:原来用 for (int i = 0; i < m_datasources.size(); i++)
		// ，由于close后作了remove操作
		// 这样就导致了m_datasources.size大小在变。
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
