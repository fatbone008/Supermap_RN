package com.supermap.data;

import java.io.File;
import java.util.Vector;

/**
 * @author 李云锦
 * @version 2.0
 */
public class Workspace extends InternalHandleDisposable {
	/**
	 * 与工作空间绑定的连接信息
	 */
	private WorkspaceConnectionInfo m_connectionInfo;   

	/**
	 * 供用户使用的连接信息
	 */
	private WorkspaceConnectionInfo m_UserConnInfo;  
	
	private Datasources m_datasources;
	
	private Resources m_Resources;

	private Maps m_maps;

	private Scenes m_scenes;
	
	/**
	 * 用于回调保存Map
	 */
	private Vector<MapSaveListener> m_mapSaveListeners ;
	
	public Workspace() {
		// 初始值在new的时候就设置好,这样就不用再调用一次jni
		long handle = WorkspaceNative.jni_New();
		setHandle(handle, true);
		this.reset();

		// 连接信息不能释放,并且是只读的
		handle = WorkspaceNative.jni_GetConnectionInfo(getHandle());
		m_connectionInfo = new WorkspaceConnectionInfo(handle, false, true);

		// m_connectionInfo是和工作空间绑定的，可用于查询工作空间信息
		// 新建连接信息，给用户使用，不和工作空间绑定；它可以用于工作空间的另存为
		m_UserConnInfo = new WorkspaceConnectionInfo();                   // added by Xingjun 2015.11.17
		m_UserConnInfo.setIsDisposable(false);                            // 该对象不可释放
		
		// @modified by not attributable at 2007年12月20日 下午02时46分47秒
		// @reason:添加 m_connectionInfo.reset()，处理其默认值。
		m_connectionInfo.reset();
		
		// 注释掉， zhikk
		// handle = WorkspaceNative.jni_GetMaps(getHandle());
		m_maps = new Maps(this);

		m_datasources = new Datasources(this);
		
		m_scenes = new Scenes(this);
		
		m_mapSaveListeners = new Vector<MapSaveListener>();
	}

	public WorkspaceConnectionInfo getConnectionInfo() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getConnectionInfo()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

//		return m_connectionInfo;
		return m_UserConnInfo;
	}

	public String getCaption() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getCaption()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return WorkspaceNative.jni_GetCaption(getHandle());
	}

	public void setCaption(String caption) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("setCaption()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (caption == null || caption.trim().length() == 0) {
			String message = InternalResource.loadString("caption",
					InternalResource.GlobalStringIsNullOrEmpty,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		WorkspaceNative.jni_SetCaption(getHandle(), caption);
	}

	public Datasources getDatasources() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getDatasources()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return m_datasources;
	}

	public Maps getMaps() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getMaps()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return m_maps;
	}

	public boolean isModified() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("isModified()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return WorkspaceNative.jni_GetIsModified(getHandle());
	}
	
	/**
	 * 获取资源对象。 该对象用于管理工作空间中的资源，目前即指线型库、点状符号库和填充符号库
	 *
	 * @return Resources
	 */
	public Resources getResources() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getResources()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (m_Resources == null) {
			long handle = WorkspaceNative.jni_GetResources(this.getHandle());
			if (handle != 0) {
				m_Resources = new Resources(this, handle);
			}
		}
		return m_Resources;
	}

	public String getDescription() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getDescription()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return WorkspaceNative.jni_GetDescription(getHandle());
	}

	public void setDescription(String description) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("setDescription()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (description == null) {
			description = "";
		}
		WorkspaceNative.jni_SetDescription(getHandle(), description);
	}

	public boolean open(WorkspaceConnectionInfo connectionInfo) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("open()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (connectionInfo == null) {
			String message = InternalResource.loadString("connectionInfo",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		if (connectionInfo.getHandle() == 0) {
			String message = InternalResource.loadString("connectionInfo",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		close();

		boolean result = WorkspaceNative.jni_Open(getHandle(), connectionInfo
				.getHandle());

		// @modified by xuzw at 2009-07-13
		// @reason: 打开失败将Vesion置为UGC20
		if (!result) {
			/*this.getConnectionInfo().setReadOnly(false);
			this.getConnectionInfo().setVersion(WorkspaceVersion.UGC20);
			this.getConnectionInfo().setReadOnly(true);*/    // modefied by xingjun, 因给用户使用的connectionInfo已经是可修改的，而不与工作空间绑定，不再需要这一步
			
			if(!Environment.getLicenseStatus().isLicenseValid()){
				throw new IllegalStateException(Environment.getLicenseStatus().toString());
			}
		}
		
		// 这里应该在刷新数据源之后触发打开后事件，否则事件监听器接受到得workspace还没有数据源！
		// Modified by weicd 2010,3.17 

		// 刷新数据源，之前的数据源引用将不再可用
		getDatasources().reset();
//		WorkspaceVersion version = this.getConnectionInfo().getVersion();
//		setConnectionInfo(connectionInfo);
//		WorkspaceConnectionInfoNative.jni_SetVersion(this.getConnectionInfo()
//				.getHandle(), version.getUGCValue());
		
		// @modified by 张继南 at 2007年10月30日 上午09时04分43秒
		// @reason: 打开成功才触发事件
		
		// modefied by xingjun, 2015.11.19
		// 打开工作空间后，使m_UserConnInfo 和 m_connectionInfo保持一致，以便用户查看工作空间的信息，当用户修改路径后就另存为
		if(result)
			copyConnectionInfo(m_UserConnInfo, m_connectionInfo);
		
		return result;
	}

	/**
	 * 设置工作空间连接信息 内部使用 用于更新连接信息，在打开、关闭、保存、另存为的时候都要设置
	 */
	private void setConnectionInfo(WorkspaceConnectionInfo info) {
		WorkspaceConnectionInfoNative.jni_SetValue(getConnectionInfo()
				.getHandle(), info.getHandle());
	}
	
	/**
	 * 关闭工作空间
	 */
	public void close() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("close()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		// modified by 孔令亮 at 2008年6月5日 下午01时23分53秒
		// reason:调到在close前面释放map
		getMaps().clear();

		// 先调用底层的关闭
		WorkspaceNative.jni_Close(getHandle());
		
		// getMaps().clear();
//		getConnectionInfo().reset();    // modefied by xingjun, 因给用户使用的connectionInfo已经是可修改的，而不与工作空间绑定，不再需要这一步

		// 设置自身的属性为默认值
		reset();
	}

	public void dispose() {
		if (!getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (!(getHandle() == 0)) {
			close();
			WorkspaceNative.jni_Delete(getHandle());
			clearHandle();		
			
			m_mapSaveListeners.clear();
		}
	}

	// modefied by xingjun, 2015.13.11~2015.11.19
	/**
	 * 保存工作空间, 通过设置工作空间的连接信息,可以实现保存新建的工作空间，另存为工作空间，保存当前打开的工作空间的功能
	 * @return 返回boolean类型，保存成功返回true，否则返回false
	 * @throws Exception
	 * <p>以下情况会抛出异常：
	 * 1.当前工作空间对象已经释放;
	 * 2.工作空间是新建的，没有指定保存路径;
	 * 3.指定保存文件所在目录不存在，将自动创建目录，但创建失败;
	 * 4.指定了保存文件的路径，但文件后缀名不是有效的工作空间类型，且没有指定工作空间类型;
	 * </p>
	 */
	public boolean save() throws Exception {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("save()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		boolean bResult = false;
		// 用户设置
		String str_UserServer = m_UserConnInfo.getServer();
		String str_Server = m_connectionInfo.getServer();
		
		saveMaps();
		if(str_Server.isEmpty()){
			if(str_UserServer.isEmpty()){
				String message = "This is a new workspace, please set file path before saving.";
				throw new Exception(message);
			}
			// 工作空间另存为
			bResult = saveAs(m_UserConnInfo);
		}else {
			if(str_UserServer.isEmpty()){
				// 保存当前工作空间
				bResult = WorkspaceNative.jni_Save(getHandle());
			}else if (str_Server.equals(str_UserServer)) {
				// 保存当前工作空间
				bResult = WorkspaceNative.jni_Save(getHandle());
			}else{
				// 工作空间另存为
				bResult = saveAs(m_UserConnInfo);
			}
		}		
		return bResult;
	}

	/**
	 * 将Workspace的属性字段设置为默认值 Description底层的默认值为"Created by SuperMap GIS
	 * Universal" Objects置为空
	 */
	void reset() {
		WorkspaceNative.jni_Reset(getHandle());
	}
	
	/**
	 * 返回工作空间中的三维场景集合对象。
	 *
	 * @return
	 */
	public Scenes getScenes() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getScenes()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return m_scenes;
	}
	
	/**
	 * 根据工作空间连接信息对象指定的信息将当前工作空间保存为工作空间文件
	 * 
	 * @param workspaceConnectionInfo   指定的工作空间连接信息对象，该方法将根据这个对象来保存工作空间
	 * @return  返回boolean类型, 另存为工作空间成功,返回true; 否则返回false:
	 * @throws Exception 
	 * <p>以下情况会抛出异常：
	 * 1.当前工作空间对象已经释放;
	 * 2.指定文件的后缀应与工作空间的类型匹配,若不匹配将根据类型增加文件后缀名;若指定的工作空间已经存在;
	 * 3.指定的文件所在目录不存在，将自动创建目录，若创建失败;
	 * 4.没有指定类型，即连接信息中工作空间类型为DEFAULT;
	 * 5.工作空间链接信息对象为null或该对象已经被释放;
	 * </p>
	 */
	
	private boolean saveAs(WorkspaceConnectionInfo workspaceConnectionInfo) throws Exception  {
		String message = null;
		// 1.工作空间被释放
		if (getHandle() == 0) {
			message = "This workspace was disposed.";
			throw new Exception(message);
		}
		// 2.检查connectionInfo
		checkConnectionInfo(workspaceConnectionInfo);
		
		boolean isSaved = WorkspaceNative.jni_SaveAs(getHandle(), workspaceConnectionInfo.getHandle());

		return isSaved;
	}

	// 注:需考虑文件覆盖、工作空间类型、指定的路径是否存在等问题
	/**
	 * 检查WorkspaceConnectionInfo中的参数是否正确，可用，目前仅检查Type和Server
	 * @param workspaceConnectionInfo
	 * @throws Exception
	 */
	private void checkConnectionInfo(WorkspaceConnectionInfo workspaceConnectionInfo) throws Exception {
		String message;
		// 1.参数info为null
		if (workspaceConnectionInfo == null) {
			message = "The WorkspaceConnectionInfo parameter is null";
			throw new Exception(message);
		}
		// 1.1参数info被释放
		if (workspaceConnectionInfo.getHandle() == 0) {
			message = "The WorkspaceConnectionInfo parameter was disposed.";
			throw new Exception(message);
		}
		final String old_server = workspaceConnectionInfo.getServer();
		final WorkspaceType old_type = workspaceConnectionInfo.getType();
		// 2.检查工作空间类型
		String str_Server = old_server;
		
		WorkspaceType checkedType = checkWorkspaceType(str_Server);   // 文件类型
		WorkspaceType type        = old_type;       // 设置的类型
		
		if(checkedType == null && type == WorkspaceType.DEFAULT){
			message = "The WorkspaceType is invalid, please check the parameter again.";
			throw new Exception(message);
		}
		
		if (type != WorkspaceType.DEFAULT && !(checkedType == type)) {
			// 2.1 根据给定的类型增加文件后缀名
			str_Server = str_Server + "." + getNameByWorkspaceType(type).toLowerCase();
			workspaceConnectionInfo.setServer(str_Server);
		} else if(!(checkedType == type)){
			workspaceConnectionInfo.setType(checkedType);      // 2.2 使用文件类型作为工作空间类型
		}
		
		// 3.检查文件
		File worksapcePath = new File(str_Server);
		File dir = worksapcePath.getParentFile();
		if(dir == null){
			message = "The workspace's path is wrong. File path: " + worksapcePath.toString();
			throw new Exception(message);
		}
		// 3.1工作空间已经存在
		if(worksapcePath.exists()){
			message = "The workspace file has existed. File path: " + worksapcePath.toString();
			throw new Exception(message);
		}
		// 3.2.创建缺失的目录
		if(!dir.exists()){
			if(!dir.mkdirs()){
				message = "Creating the directories failed. File path: " + worksapcePath.toString();
				throw new Exception(message);
			}
		}
		
		// 设置工作空间名称
		int start = str_Server.lastIndexOf('/')+1;
		int end   = str_Server.lastIndexOf('.');
		String fileName = null;
		if(start<0 || end < 0 || start >= end){
			fileName = "";
		}else {
			fileName = str_Server.substring(start, end);
		}
		String workspaceName1 = workspaceConnectionInfo.getName();
		String workspaceName2 = this.getCaption();
		if((workspaceName1.isEmpty() || workspaceName1.equals("UntitledWorkspace")) && !workspaceName2.isEmpty() && !workspaceName2.equals("UntitledWorkspace")){
			workspaceConnectionInfo.setName(workspaceName2);
		}else if ((workspaceName1.isEmpty() || workspaceName1.equals("UntitledWorkspace")) && !fileName.isEmpty()) {
			// 连接信息中的name和工作空间的caption需分别设置
			workspaceConnectionInfo.setName(fileName);
			this.setCaption(fileName);
		}
	}
	
	
	/**
	 * 检查路径中指定的工作空间类型，如果不是支持的类型，返回null
	 * @param str_Server   工作空间的绝对路径，指定工作空间文件
	 * @return     返回str_Server中指定的工作空间类型,若是非支持类型,返回null;
	 */
	private WorkspaceType checkWorkspaceType(String str_Server){
		WorkspaceType type = null;
		
		if(str_Server == null)
			return null;
		
		String uperCase_str_Server = str_Server.toUpperCase();

		if (uperCase_str_Server.endsWith(".SMW")) {
			type = WorkspaceType.SMW;
		} else if (uperCase_str_Server.endsWith(".SMWU")) {
			type = WorkspaceType.SMWU;
		} else if (uperCase_str_Server.endsWith(".SXW")) {
			type = WorkspaceType.SXW;
		} else if (uperCase_str_Server.endsWith(".SXWU")) {
			type = WorkspaceType.SXWU;
		}
		
		return type;
	}
	
	/**
	 * 根据工作空间类型获取类型名,全大写字母
	 * @param type
	 * @return
	 */
	private String getNameByWorkspaceType(WorkspaceType type){
		String name = null;
		if(type == null){
			return null;
		}
		
		if(type == WorkspaceType.SMW){
			name = "SMW";
		}else if (type == WorkspaceType.SMWU) {
			name = "SMWU";
		}else if(type == WorkspaceType.SXW){
			name = "SXW";
		}else if(type == WorkspaceType.SXWU){
			name = "SXWU";
		}
		return name;
	}
	
	/**
	 * 添加一个MapSaveListener对像，工作空间每绑定一个Map，就对应有一个listener;
	 * 该方法只在Map内使用，不需要给用户说明
	 * @param listener
	 * @return
	 */
	public boolean addMapSaveListener(MapSaveListener listener){
		if(listener == null)
			return false;
		return m_mapSaveListeners.add(listener);
	}
	
	/**
	 * 移除一个MapSaveListener对像，工作空间去除绑定一个Map，就对应有一个listener被移除;
	 * 该方法只在Map内使用，不需要给用户说明
	 * @param listener
	 * @return
	 */
	public boolean removeMapSaveListener(MapSaveListener listener){
		if(listener == null)
			return true;
		return m_mapSaveListeners.remove(listener);
	}
	
	/**
	 * 根据MapSaveListener保存关联的地图
	 */
	private void saveMaps(){
		for(MapSaveListener listener : m_mapSaveListeners){
			listener.saveMap();
		}
	}
	
	/**
	 * 拷贝连接信息，但不拷贝密码和
	 * @param targetInfo
	 * @param sourceInfo
	 */
	private void copyConnectionInfo(WorkspaceConnectionInfo targetInfo, WorkspaceConnectionInfo sourceInfo){
		if(targetInfo == null || sourceInfo == null)
			return;
		
		targetInfo.setName(sourceInfo.getName());
		targetInfo.setServer(sourceInfo.getServer());
		targetInfo.setType(sourceInfo.getType());
		targetInfo.setUser(sourceInfo.getUser());
		targetInfo.setVersion(sourceInfo.getVersion());
	}
}
