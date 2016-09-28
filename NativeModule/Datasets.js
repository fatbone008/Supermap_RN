/**
 * Created by will on 2016/6/17.
 */
import {NativeModules} from 'react-native';
let D = NativeModules.JSDatasets;
import Dataset from './Dataset.js';
import DatasetVector from './DatasetVector.js';
import Recordset from './Recordset.js';

export default class Datasets{
    async get(index){
        try{
            var dataset = new Dataset();
            if(typeof index != "string"){
                var {datasetId} = await D.get(this.datasetsId,index);
            }else{
                var {datasetId} = await D.getByName(this.datasetsId,index);
            }
            dataset.datasetId = datasetId;

            return dataset;
        }catch(e){
            console.error(e);
        }
    }

    async getAvailableDatasetName(name){
        try{
            var {datasetName} = await D.getAvailableDatasetName(this.datasetsId,name);
            return datasetName;
        }catch(e){
            console.error(e);
        }
    }

    async create(datasetVectorInfo){
        try{
            var {datasetVectorId} = await D.create(this.datasetsId,datasetVectorInfo.datasetVectorInfoId);
            var datasetVector = new DatasetVector();
            datasetVector.datasetVectorId = datasetVectorId;
            return datasetVector;
        }catch(e){
            console.error(e);
        }
    }

}