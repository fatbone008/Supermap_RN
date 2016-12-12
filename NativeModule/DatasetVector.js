import {NativeModules} from 'react-native';
let DV = NativeModules.JSDatasetVector;
import Recordset from  './Recordset.js';
import QueryParameter from './QueryParameter.js';

export default class DatasetVector {
    async queryInBuffer(rectangle2D,cursorType){
        try{
            var {recordsetId} = await DV.queryInBuffer(this.datasetVectorId,rectangle2D.rectangle2DId,cursorType);
            var recordset = new Recordset();
            recordset.recordsetId = recordsetId;
            return recordset;
        }catch (e){
            console.error(e);
        }
    }


    async getRecordset(isEmptyRecordset,cursorType){
        try{
            var {recordsetId} =await DV.getRecordset(this.datasetVectorId,isEmptyRecordset,cursorType);
            var recordset = new Recordset();
            recordset.recordsetId = recordsetId;
            return recordset;
        }catch(e){
            console.error(e);
        }
    }

    async query(queryParameter){
        try{
            var qp = new QueryParameter();
            if(!queryParameter && (queryParameter.attributeFilter || queryParameter.groupBy ||
                        queryParameter.hasGeometry || queryParameter.resultFields || queryParameter.orderBy ||
                        queryParameter.spatialQueryMode
                )){
                var queryParameter = await qp.createObj();
                queryParameter.queryParameterId = "0";
            }else {
                queryParameter.attributeFilter &&
                await queryParameter.setAttributeFilter(queryParameter.attributeFilter);

                queryParameter.groupBy &&
                await queryParameter.setGroupBy(queryParameter.groupBy);

                queryParameter.hasGeometry &&
                await queryParameter.setAttributeFilter(queryParameter.hasGeometry);

                queryParameter.resultFields &&
                await queryParameter.setAttributeFilter(queryParameter.resultFields);

                queryParameter.orderBy &&
                await queryParameter.setAttributeFilter(queryParameter.orderBy);

                queryParameter.spatialQueryMode &&
                await queryParameter.setAttributeFilter(queryParameter.spatialQueryMode);

                var result = await DV.query(this.datasetVectorId,queryParameter.queryParameterId);
                return result;
            }
        }catch (e){
            console.log(e);
        }
    }
}