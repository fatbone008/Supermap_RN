import {NativeModules} from 'react-native';
let DV = NativeModules.JSDatasetVector;
import Recordset from  './Recordset.js';
import QueryParameter from './QueryParameter.js';


/**
 * @class DatasetVector
 */
export default class DatasetVector {
    async queryInBuffer(rectangle2D, cursorType) {
        try {
            var {recordsetId} = await DV.queryInBuffer(this.datasetVectorId, rectangle2D.rectangle2DId, cursorType);
            var recordset = new Recordset();
            recordset.recordsetId = recordsetId;
            return recordset;
        } catch (e) {
            console.error(e);
        }
    }


    async getRecordset(isEmptyRecordset, cursorType) {
        try {
            var {recordsetId} =await DV.getRecordset(this.datasetVectorId, isEmptyRecordset, cursorType);
            var recordset = new Recordset();
            recordset.recordsetId = recordsetId;
            return recordset;
        } catch (e) {
            console.error(e);
        }
    }

    async query(queryParameter) {
        try {
            var QueryParameterFac = new QueryParameter();
            var qp = await QueryParameterFac.createObj();

            if (!queryParameter
            // && (queryParameter.attributeFilter || queryParameter.groupBy ||
            //         queryParameter.hasGeometry || queryParameter.resultFields || queryParameter.orderBy ||
            //         queryParameter.spatialQueryMode)
            ) {
                qp.queryParameterId = "0";
            } else {
                queryParameter.attributeFilter && await qp.setAttributeFilter(queryParameter.attributeFilter);

                queryParameter.groupBy && await qp.setGroupBy(queryParameter.groupBy);

                queryParameter.hasGeometry && await qp.setHasGeometry(queryParameter.hasGeometry);

                queryParameter.resultFields && await qp.setResultFields(queryParameter.resultFields);

                queryParameter.orderBy && await qp.setOrderBy(queryParameter.orderBy);

                queryParameter.spatialQueryObject && await qp.setSpatialQueryObject(queryParameter.spatialQueryObject);

                queryParameter.spatialQueryMode && await qp.setSpatialQueryMode(queryParameter.spatialQueryMode);

                if (queryParameter.size) qp.size = queryParameter.size;
                if (queryParameter.batch) qp.batch = queryParameter.batch;
            }


            var result = await DV.query(this.datasetVectorId, qp.queryParameterId,
                qp.size, qp.batch);
            return result;
        } catch (e) {
            console.log(e);
        }
    }

    /**
     *根据给定的空间索引类型来为矢量数据集创建空间索引。
     * @memberOf DatasetVector
     * @param {DatasetVector.SpatialIndeType} spatialIndexType - 指定的需要创建空间索引的类型
     * @returns {Promise.<Promise.boolean>}
     */
    async buildSpatialIndex(spatialIndexType) {
        try {
            var {built} =await DV.buildSpatialIndex(this.datasetVectorId, spatialIndexType);
            return built;
        } catch (e) {
            console.error(e);
        }
    }

    /**
     * 删除空间索引
     * @memberOf DatasetVector
     * @returns {Promise.<Promise.dropped>}
     */
    async dropSpatialIndex() {
        try {
            var {dropped} =await DV.dropSpatialIndex(this.datasetVectorId);
            return dropped;
        } catch (e) {
            console.error(e);
        }
    }

    /**
     * 返回当前的空间索引类型
     * @memberOf DatasetVector
     * @returns {Promise.<Promise.DatasetVector.SpatialIndeType>}
     */
    async getSpatialIndexType() {
        try {
            var {type} =await DV.getSpatialIndexType(this.datasetVectorId);
            return type;
        } catch (e) {
            console.error(e);
        }
    }

    /**
     * 计算数据集的空间范围
     * @memberOf DatasetVector
     * @returns {Promise.<Promise.object>}
     */
    async computeBounds() {
        try {
            var {bounds} =await DV.computeBounds(this.datasetVectorId);
            return bounds;
        } catch (e) {
            console.error(e);
        }
    }

    /**
     *从GeoJSON字符串中获取几何对象，并将其存入数据集中
     *仅支持点、线、面和CAD数据集，获取点、线、面对象
     * @param {boolean} hasAttributte - 是否包含属性值
     * @param {number} startID - 起始SmID
     * @param {number} endID - 结束SmID
     * @returns {Promise.<string>}
     */
    async toGeoJSON(hasAttributte, startID, endID) {
        try {
            var {geoJSON} =await DV.computeBounds(this.datasetVectorId);
            var json = JSON.parse(geoJSON);
            return geoJSON;
        } catch (e) {
            console.error(e);
        }
    }

    /**
     * 将数据集中指定起止SmID的对象，转换成GeoJSON格式的字符串
     *仅支持点、线、面和CAD数据集，转换点、线、面对象.hasAtrributte为true时，结果中包含属性值;hasAtrribute为false时，只有几何对象。
     * @param {string} geoJson - json字符串
     * @returns {Promise.<Promise.boolean>}
     */
    async fromGeoJSON(geoJson) {
        try {
            var {done} =await DV.computeBounds(this.datasetVectorId,geoJson);
            return done;
        } catch (e) {
            console.error(e);
        }
    }
}