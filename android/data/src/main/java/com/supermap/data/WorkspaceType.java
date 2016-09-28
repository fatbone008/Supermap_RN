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
public final class WorkspaceType extends Enum {
    private WorkspaceType(int value, int ugcValue) {
        super(value, ugcValue);
    }

    /**
     * û�д��������ռ��ļ�
     */
    public static final WorkspaceType DEFAULT = new WorkspaceType(1, 1);

    public static final WorkspaceType SXW = new WorkspaceType(4, 4);

    public static final WorkspaceType SMW = new WorkspaceType(5, 5);
	
	public static final WorkspaceType SXWU = new WorkspaceType(8, 8);
    
    public static final WorkspaceType SMWU = new WorkspaceType(9, 9);
}

