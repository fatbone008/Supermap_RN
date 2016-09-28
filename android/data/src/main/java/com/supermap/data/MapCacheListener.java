/**
 * 
 */
package com.supermap.data;

/**
 * @author ֣����
 *
 */
public interface MapCacheListener {

	// Ԥ�������ʱ�ص��� �����û�������ɣ���nFailedCount��ͼƬ����ʧ�ܡ�
	public void onComplete(int nFailedCount);
	
	// ����ֵ��1-100�� ��������һ�κ�֪ͨ�û�һ��
	public void onProgress(int nStep);
	
	// ����ʱ����ʾ�ۼ�ʮ��ͼƬ����ʧ�ܣ��û��ü�������ˡ����Բ�����ÿ�ۼ�ʮ������ʧ��֪ͨһ�Σ��û������ڻص��ﶨ�ƶ��ٸ�ʮ��������ֹͣԤ������ߵ���ȡ�
	public void onChecked();
	
	// ��ϸ������Ϣ���������û������ػ�������nDownLoadCout��������nTotalCount
	public void onCacheStatus(int nDownLoadCout, long nTotalCount);
}
