package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description:���幤���ռ����ͳ��� </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ������
 * @version 2.0
 */
public final class WorkspaceVersion extends Enum {
    private WorkspaceVersion(int value,int ugcValue){
        super(value,ugcValue);
    }
    
    /**
	 * SuperMap 5.0�����ռ�
	 */
	public static final WorkspaceVersion SFC50 = new WorkspaceVersion(20031211,
			20031211);

	/**
	 * SuperMap UGC 2.0�����ռ�
	 */
	public static final WorkspaceVersion UGC20 = new WorkspaceVersion(20070521,
			20070521);
	
	/**
	 * SuperMap UGC 6.0�����ռ�
	 * add by xuzw 2009-02-09 
	 */
	public static final WorkspaceVersion UGC60 = new WorkspaceVersion(20090106,
			20090106);
	
	public static final WorkspaceVersion SFC60 = new WorkspaceVersion(20031212,
			20031212);
	/**
	 * SuperMap UGC 7.0�����ռ�
	 */
	public static final WorkspaceVersion UGC70 = new WorkspaceVersion(20120328,
			20120328);

}
