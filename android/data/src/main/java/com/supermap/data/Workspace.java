package com.supermap.data;

import java.io.File;
import java.util.Vector;

/**
 * @author ���ƽ�
 * @version 2.0
 */
public class Workspace extends InternalHandleDisposable {
	/**
	 * �빤���ռ�󶨵�������Ϣ
	 */
	private WorkspaceConnectionInfo m_connectionInfo;   

	/**
	 * ���û�ʹ�õ�������Ϣ
	 */
	private WorkspaceConnectionInfo m_UserConnInfo;  
	
	private Datasources m_datasources;
	
	private Resources m_Resources;

	private Maps m_maps;

	private Scenes m_scenes;
	
	/**
	 * ���ڻص�����Map
	 */
	private Vector<MapSaveListener> m_mapSaveListeners ;
	
	public Workspace() {
		// ��ʼֵ��new��ʱ������ú�,�����Ͳ����ٵ���һ��jni
		long handle = WorkspaceNative.jni_New();
		setHandle(handle, true);
		this.reset();

		// ������Ϣ�����ͷ�,������ֻ����
		handle = WorkspaceNative.jni_GetConnectionInfo(getHandle());
		m_connectionInfo = new WorkspaceConnectionInfo(handle, false, true);

		// m_connectionInfo�Ǻ͹����ռ�󶨵ģ������ڲ�ѯ�����ռ���Ϣ
		// �½�������Ϣ�����û�ʹ�ã����͹����ռ�󶨣����������ڹ����ռ�����Ϊ
		m_UserConnInfo = new WorkspaceConnectionInfo();                   // added by Xingjun 2015.11.17
		m_UserConnInfo.setIsDisposable(false);                            // �ö��󲻿��ͷ�
		
		// @modified by not attributable at 2007��12��20�� ����02ʱ46��47��
		// @reason:��� m_connectionInfo.reset()��������Ĭ��ֵ��
		m_connectionInfo.reset();
		
		// ע�͵��� zhikk
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
	 * ��ȡ��Դ���� �ö������ڹ������ռ��е���Դ��Ŀǰ��ָ���Ϳ⡢��״���ſ�������ſ�
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
		// @reason: ��ʧ�ܽ�Vesion��ΪUGC20
		if (!result) {
			/*this.getConnectionInfo().setReadOnly(false);
			this.getConnectionInfo().setVersion(WorkspaceVersion.UGC20);
			this.getConnectionInfo().setReadOnly(true);*/    // modefied by xingjun, ����û�ʹ�õ�connectionInfo�Ѿ��ǿ��޸ĵģ������빤���ռ�󶨣�������Ҫ��һ��
			
			if(!Environment.getLicenseStatus().isLicenseValid()){
				throw new IllegalStateException(Environment.getLicenseStatus().toString());
			}
		}
		
		// ����Ӧ����ˢ������Դ֮�󴥷��򿪺��¼��������¼����������ܵ���workspace��û������Դ��
		// Modified by weicd 2010,3.17 

		// ˢ������Դ��֮ǰ������Դ���ý����ٿ���
		getDatasources().reset();
//		WorkspaceVersion version = this.getConnectionInfo().getVersion();
//		setConnectionInfo(connectionInfo);
//		WorkspaceConnectionInfoNative.jni_SetVersion(this.getConnectionInfo()
//				.getHandle(), version.getUGCValue());
		
		// @modified by �ż��� at 2007��10��30�� ����09ʱ04��43��
		// @reason: �򿪳ɹ��Ŵ����¼�
		
		// modefied by xingjun, 2015.11.19
		// �򿪹����ռ��ʹm_UserConnInfo �� m_connectionInfo����һ�£��Ա��û��鿴�����ռ����Ϣ�����û��޸�·��������Ϊ
		if(result)
			copyConnectionInfo(m_UserConnInfo, m_connectionInfo);
		
		return result;
	}

	/**
	 * ���ù����ռ�������Ϣ �ڲ�ʹ�� ���ڸ���������Ϣ���ڴ򿪡��رա����桢���Ϊ��ʱ��Ҫ����
	 */
	private void setConnectionInfo(WorkspaceConnectionInfo info) {
		WorkspaceConnectionInfoNative.jni_SetValue(getConnectionInfo()
				.getHandle(), info.getHandle());
	}
	
	/**
	 * �رչ����ռ�
	 */
	public void close() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("close()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		// modified by ������ at 2008��6��5�� ����01ʱ23��53��
		// reason:������closeǰ���ͷ�map
		getMaps().clear();

		// �ȵ��õײ�Ĺر�
		WorkspaceNative.jni_Close(getHandle());
		
		// getMaps().clear();
//		getConnectionInfo().reset();    // modefied by xingjun, ����û�ʹ�õ�connectionInfo�Ѿ��ǿ��޸ĵģ������빤���ռ�󶨣�������Ҫ��һ��

		// �������������ΪĬ��ֵ
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
	 * ���湤���ռ�, ͨ�����ù����ռ��������Ϣ,����ʵ�ֱ����½��Ĺ����ռ䣬���Ϊ�����ռ䣬���浱ǰ�򿪵Ĺ����ռ�Ĺ���
	 * @return ����boolean���ͣ�����ɹ�����true�����򷵻�false
	 * @throws Exception
	 * <p>����������׳��쳣��
	 * 1.��ǰ�����ռ�����Ѿ��ͷ�;
	 * 2.�����ռ����½��ģ�û��ָ������·��;
	 * 3.ָ�������ļ�����Ŀ¼�����ڣ����Զ�����Ŀ¼��������ʧ��;
	 * 4.ָ���˱����ļ���·�������ļ���׺��������Ч�Ĺ����ռ����ͣ���û��ָ�������ռ�����;
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
		// �û�����
		String str_UserServer = m_UserConnInfo.getServer();
		String str_Server = m_connectionInfo.getServer();
		
		saveMaps();
		if(str_Server.isEmpty()){
			if(str_UserServer.isEmpty()){
				String message = "This is a new workspace, please set file path before saving.";
				throw new Exception(message);
			}
			// �����ռ����Ϊ
			bResult = saveAs(m_UserConnInfo);
		}else {
			if(str_UserServer.isEmpty()){
				// ���浱ǰ�����ռ�
				bResult = WorkspaceNative.jni_Save(getHandle());
			}else if (str_Server.equals(str_UserServer)) {
				// ���浱ǰ�����ռ�
				bResult = WorkspaceNative.jni_Save(getHandle());
			}else{
				// �����ռ����Ϊ
				bResult = saveAs(m_UserConnInfo);
			}
		}		
		return bResult;
	}

	/**
	 * ��Workspace�������ֶ�����ΪĬ��ֵ Description�ײ��Ĭ��ֵΪ"Created by SuperMap GIS
	 * Universal" Objects��Ϊ��
	 */
	void reset() {
		WorkspaceNative.jni_Reset(getHandle());
	}
	
	/**
	 * ���ع����ռ��е���ά�������϶���
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
	 * ���ݹ����ռ�������Ϣ����ָ������Ϣ����ǰ�����ռ䱣��Ϊ�����ռ��ļ�
	 * 
	 * @param workspaceConnectionInfo   ָ���Ĺ����ռ�������Ϣ���󣬸÷���������������������湤���ռ�
	 * @return  ����boolean����, ���Ϊ�����ռ�ɹ�,����true; ���򷵻�false:
	 * @throws Exception 
	 * <p>����������׳��쳣��
	 * 1.��ǰ�����ռ�����Ѿ��ͷ�;
	 * 2.ָ���ļ��ĺ�׺Ӧ�빤���ռ������ƥ��,����ƥ�佫�������������ļ���׺��;��ָ���Ĺ����ռ��Ѿ�����;
	 * 3.ָ�����ļ�����Ŀ¼�����ڣ����Զ�����Ŀ¼��������ʧ��;
	 * 4.û��ָ�����ͣ���������Ϣ�й����ռ�����ΪDEFAULT;
	 * 5.�����ռ�������Ϣ����Ϊnull��ö����Ѿ����ͷ�;
	 * </p>
	 */
	
	private boolean saveAs(WorkspaceConnectionInfo workspaceConnectionInfo) throws Exception  {
		String message = null;
		// 1.�����ռ䱻�ͷ�
		if (getHandle() == 0) {
			message = "This workspace was disposed.";
			throw new Exception(message);
		}
		// 2.���connectionInfo
		checkConnectionInfo(workspaceConnectionInfo);
		
		boolean isSaved = WorkspaceNative.jni_SaveAs(getHandle(), workspaceConnectionInfo.getHandle());

		return isSaved;
	}

	// ע:�迼���ļ����ǡ������ռ����͡�ָ����·���Ƿ���ڵ�����
	/**
	 * ���WorkspaceConnectionInfo�еĲ����Ƿ���ȷ�����ã�Ŀǰ�����Type��Server
	 * @param workspaceConnectionInfo
	 * @throws Exception
	 */
	private void checkConnectionInfo(WorkspaceConnectionInfo workspaceConnectionInfo) throws Exception {
		String message;
		// 1.����infoΪnull
		if (workspaceConnectionInfo == null) {
			message = "The WorkspaceConnectionInfo parameter is null";
			throw new Exception(message);
		}
		// 1.1����info���ͷ�
		if (workspaceConnectionInfo.getHandle() == 0) {
			message = "The WorkspaceConnectionInfo parameter was disposed.";
			throw new Exception(message);
		}
		final String old_server = workspaceConnectionInfo.getServer();
		final WorkspaceType old_type = workspaceConnectionInfo.getType();
		// 2.��鹤���ռ�����
		String str_Server = old_server;
		
		WorkspaceType checkedType = checkWorkspaceType(str_Server);   // �ļ�����
		WorkspaceType type        = old_type;       // ���õ�����
		
		if(checkedType == null && type == WorkspaceType.DEFAULT){
			message = "The WorkspaceType is invalid, please check the parameter again.";
			throw new Exception(message);
		}
		
		if (type != WorkspaceType.DEFAULT && !(checkedType == type)) {
			// 2.1 ���ݸ��������������ļ���׺��
			str_Server = str_Server + "." + getNameByWorkspaceType(type).toLowerCase();
			workspaceConnectionInfo.setServer(str_Server);
		} else if(!(checkedType == type)){
			workspaceConnectionInfo.setType(checkedType);      // 2.2 ʹ���ļ�������Ϊ�����ռ�����
		}
		
		// 3.����ļ�
		File worksapcePath = new File(str_Server);
		File dir = worksapcePath.getParentFile();
		if(dir == null){
			message = "The workspace's path is wrong. File path: " + worksapcePath.toString();
			throw new Exception(message);
		}
		// 3.1�����ռ��Ѿ�����
		if(worksapcePath.exists()){
			message = "The workspace file has existed. File path: " + worksapcePath.toString();
			throw new Exception(message);
		}
		// 3.2.����ȱʧ��Ŀ¼
		if(!dir.exists()){
			if(!dir.mkdirs()){
				message = "Creating the directories failed. File path: " + worksapcePath.toString();
				throw new Exception(message);
			}
		}
		
		// ���ù����ռ�����
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
			// ������Ϣ�е�name�͹����ռ��caption��ֱ�����
			workspaceConnectionInfo.setName(fileName);
			this.setCaption(fileName);
		}
	}
	
	
	/**
	 * ���·����ָ���Ĺ����ռ����ͣ��������֧�ֵ����ͣ�����null
	 * @param str_Server   �����ռ�ľ���·����ָ�������ռ��ļ�
	 * @return     ����str_Server��ָ���Ĺ����ռ�����,���Ƿ�֧������,����null;
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
	 * ���ݹ����ռ����ͻ�ȡ������,ȫ��д��ĸ
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
	 * ���һ��MapSaveListener���񣬹����ռ�ÿ��һ��Map���Ͷ�Ӧ��һ��listener;
	 * �÷���ֻ��Map��ʹ�ã�����Ҫ���û�˵��
	 * @param listener
	 * @return
	 */
	public boolean addMapSaveListener(MapSaveListener listener){
		if(listener == null)
			return false;
		return m_mapSaveListeners.add(listener);
	}
	
	/**
	 * �Ƴ�һ��MapSaveListener���񣬹����ռ�ȥ����һ��Map���Ͷ�Ӧ��һ��listener���Ƴ�;
	 * �÷���ֻ��Map��ʹ�ã�����Ҫ���û�˵��
	 * @param listener
	 * @return
	 */
	public boolean removeMapSaveListener(MapSaveListener listener){
		if(listener == null)
			return true;
		return m_mapSaveListeners.remove(listener);
	}
	
	/**
	 * ����MapSaveListener��������ĵ�ͼ
	 */
	private void saveMaps(){
		for(MapSaveListener listener : m_mapSaveListeners){
			listener.saveMap();
		}
	}
	
	/**
	 * ����������Ϣ���������������
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
