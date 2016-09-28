package com.supermap.data;

import java.util.Vector;
/**
 * 查询监听，在使用{@link DatasetVector#queryByFilter(String, Geometry, int)}或者{@link DatasetVector#queryByKeyword(String, String, Geometry, int)}进行查询时，
 * 由于它们是异步查询，因此需要用此listener来获取查询结果，
 * @author xingjun
 *
 */

public interface QueryListener {
/**
* 空间查询，查询指定空间范围内符合字段条件的记录
* @param dataset       被查询的数据集
* @param fieldName     查询的字段名，如Name, Name_PY, Name_PYSZM,即名称字段，名称拼音字段，名称拼音首字母字段
* @param SmIDs         查询出来的记录的SmID数组
 */

public void queryResult(Dataset dataset, String fieldName, Vector<Integer> SmIDs);
}
