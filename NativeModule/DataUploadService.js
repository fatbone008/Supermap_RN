import {NativeModules} from 'react-native';
let DUS = NativeModules.JSDataUploadService;

export default class DataUploadService {
    constructor(){
        super();
        //同步子类Id和父类Id
        Object.defineProperty(this,"_dataUploadServiceId_",{
            get:function () {
                return this._serviceBaseID_
            },
            set:function (_dataUploadServiceId_) {
                this._serviceBaseID_ = _dataUploadServiceId_;
            }
        })
    }

    async createObj(url){
        try{
            var {_dataUploadServiceId_} = await DUS.createObj(url);
            var datauploadService = new DataUploadService();
            datauploadService._dataUploadServiceId_ = _dataUploadServiceId_;
            return datauploadService;
        }catch(e){
            console.error(e);
        }
    }

    async addDataset(fullUrl,datasetName,datasetType){
        try{
            await DUS.addDataset(this._dataUploadServiceId_,fullUrl,datasetName,datasetType);
        }catch(e){
            console.error(e);
        }
    }

    async cloneDataset(serviceName,datasourceName,destDatasetName,srcDatasourceName,srcDatasetName){
        try{
            await DUS.cloneDataset(this._dataUploadServiceId_,serviceName,
                datasourceName,destDatasetName,srcDatasourceName,srcDatasetName);
        }catch(e){
            console.error(e);
        }
    }

    async addFeature(fullUrl,feature){
        try{
            await DUS.addFeature(this._dataUploadServiceId_,fullUrl,feature._featureId_);
        }catch(e){
            console.error(e);
        }
    }

    async addFeatureByName(serviceName,datasourceName,datasetName,feature){
        try{
            await DUS.addFeatureByName(this._dataUploadServiceId_,serviceName,datasourceName,
                datasetName,feature._featureId_);
        }catch(e){
            console.error(e);
        }
    }

    async deleteFeature(fullUrl,featureIDs){
        try{
            await DUS.deleteFeature(this._dataUploadServiceId_,fullUrl,featureIDs);
        }catch(e){
            console.error(e);
        }
    }

    async deleteFeatureByName(serviceName,datasourceName,datasetName,featureIDs){
        try{
            await DUS.deleteFeatureByName(this._dataUploadServiceId_,serviceName,
                datasourceName,datasetName,featureIDs);
        }catch(e){
            console.error(e);
        }
    }

    async modifyFeature(fullUrl,featureID,feature){
        try{
            await DUS.modifyFeature(this._dataUploadServiceId_,fullUrl,featureID,feature._featureId_);
        }catch(e){
            console.error(e);
        }
    }

    async modifyFeatureByName(serviceName,datasourceName,datasetName,featureID,feature){
        try{
            await DUS.modifyFeatureByName(this._dataUploadServiceId_,serviceName,
                datasourceName,datasetName,featureID,feature._featureId_);
        }catch(e){
            console.error(e);
        }
    }

    async commitDataset(urlDataset,dataset){
        try{
            await DUS.commitDataset(this._dataUploadServiceId_,fullUrl,dataset.datasetId);
        }catch(e){
            console.error(e);
        }
    }

    async deleteDataset(fullUrl){
        try{
            await DUS.deleteDataset(this._dataUploadServiceId_,fullUrl);
        }catch(e){
            console.error(e);
        }
    }

    async deleteDatasetByName(serviceName,datasourceName,datasetName){
        try{
            await DUS.deleteDatasetByName(this._dataUploadServiceId_,serviceName,datasourceName,datasetName);
        }catch(e){
            console.error(e);
        }
    }
}