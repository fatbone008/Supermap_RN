/**
 * Created by will on 2016/6/17.
 */
import {NativeModules} from 'react-native';
let D = NativeModules.JSDatasource;
import Datasets from './Datasets';
import Dataset from './Dataset.js';
import DatasetVector from './DatasetVector.js';

export default class Datasource{
    async getDatasets(){
        console.warn("Datasource.js:getDatasets() function has been deprecated. If you want to get dataset , please call the getDataset() function");
        try{
            var {datasetsId} = await D.getDatasets(this.datasourceId);
            var datasets = new Datasets();
            datasets.datasetsId = datasetsId;

            return datasets;
        }catch(e){
            console.error(e);
        }
    }

    async getDataset(arg){
        try{
            var dataset = new Dataset();
            if(typeof arg != "string"){
                var {datasetId} = await D.getDataset(this.datasourceId,arg);
            }else{
                var {datasetId} = await D.getDatasetByName(this.datasourceId,arg);
            }
            dataset.datasetId = datasetId;

            return dataset;
        }catch(e){
            console.error(e);
        }
    }

    async getAvailableDatasetName(name){
        try{
            var {datasetName} = await D.getAvailableDatasetName(this.datasourceId,name);
            return datasetName;
        }catch(e){
            console.error(e);
        }
    }

    async createDatasetVector(datasetVectorInfo){
        try{
            var {datasetVectorId} = await D.createDatasetVector(this.datasourceId,datasetVectorInfo.datasetVectorInfoId);
            var datasetVector = new DatasetVector();
            datasetVector.datasetVectorId = datasetVectorId;
            return datasetVector;
        }catch(e){
            console.error(e);
        }
    }
}