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
            var QueryParameterFac = new QueryParameter();
            var qp = await QueryParameterFac.createObj();

            if(!queryParameter
                // && (queryParameter.attributeFilter || queryParameter.groupBy ||
                //         queryParameter.hasGeometry || queryParameter.resultFields || queryParameter.orderBy ||
                //         queryParameter.spatialQueryMode)
                ){
                qp.queryParameterId = "0";
            }else {
                queryParameter.attributeFilter &&
                await qp.setAttributeFilter(queryParameter.attributeFilter);

                queryParameter.groupBy &&
                await qp.setGroupBy(queryParameter.groupBy);

                queryParameter.hasGeometry &&
                await qp.setHasGeometry(queryParameter.hasGeometry);

                queryParameter.resultFields &&
                await qp.setResultFields(queryParameter.resultFields);

                queryParameter.orderBy &&
                await qp.setOrderBy(queryParameter.orderBy);

                queryParameter.spatialQueryObject &&
                await qp.setSpatialQueryObject(queryParameter.spatialQueryObject);

                queryParameter.spatialQueryMode &&
                await qp.setSpatialQueryMode(queryParameter.spatialQueryMode);

                if(queryParameter.size) qp.size = queryParameter.size;
                if(queryParameter.batch) qp.batch = queryParameter.batch;
            }


            var result = await DV.query(this.datasetVectorId,qp.queryParameterId,
                                              qp.size,qp.batch);
            return result;
        }catch (e){
            console.log(e);
        }
    }
}