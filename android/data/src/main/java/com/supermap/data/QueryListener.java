package com.supermap.data;

import java.util.Vector;
/**
 * ��ѯ��������ʹ��{@link DatasetVector#queryByFilter(String, Geometry, int)}����{@link DatasetVector#queryByKeyword(String, String, Geometry, int)}���в�ѯʱ��
 * �����������첽��ѯ�������Ҫ�ô�listener����ȡ��ѯ�����
 * @author xingjun
 *
 */

public interface QueryListener {
/**
* �ռ��ѯ����ѯָ���ռ䷶Χ�ڷ����ֶ������ļ�¼
* @param dataset       ����ѯ�����ݼ�
* @param fieldName     ��ѯ���ֶ�������Name, Name_PY, Name_PYSZM,�������ֶΣ�����ƴ���ֶΣ�����ƴ������ĸ�ֶ�
* @param SmIDs         ��ѯ�����ļ�¼��SmID����
 */

public void queryResult(Dataset dataset, String fieldName, Vector<Integer> SmIDs);
}
