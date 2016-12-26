import {NativeModules} from 'react-native';
let DDS = NativeModules.JSDataDownloadService;
import ServiceBase from './ServiceBase.js';

export default class DataDownloadService extends ServiceBase{
    constructor(){
        super();
        //同步子类Id和父类Id
        Object.defineProperty(this,"_dataDownloadServiceId_",{
            get:function () {
                return this._serviceBaseID_
            },
            set:function (_dataDownloadServiceId_) {
                this._serviceBaseID_ = _dataDownloadServiceId_;
            }
        })
    }

    async createObj(url){
        try{
            var {_dataDownloadServiceId_} = await DUS.createObj(url);
            var dataDownloadService = new DataDownloadService();
            dataDownloadService._dataDownloadServiceId_ = _dataDownloadServiceId_;
            return dataDownloadService;
        }catch(e){
            console.error(e);
        }
    }

    async download(fullUrl,fromIndex,toIndex){
        try{
            await DDS.download(dataDownloadService._dataDownloadServiceId_,fullUrl,fromIndex,toIndex);
        }catch(e){
            console.error(e);
        }
    }

    async downloadByName(serviceName,datasourceName,datasetName,fromIndex,toIndex){
        try{
            await DDS.downloadByName(dataDownloadService._dataDownloadServiceId_,
                serviceName,datasourceName,datasetName,fromIndex,toIndex);
        }catch(e){
            console.error(e);
        }
    }

    async downloadAll(fullUrl){
        try{
            await DDS.downloadAll(dataDownloadService._dataDownloadServiceId_,fullUrl);
        }catch(e){
            console.error(e);
        }
    }

    async downloadAllByName(serviceName,datasourceName,datasetName){
        try{
            await DDS.downloadAllByName(dataDownloadService._dataDownloadServiceId_,
                serviceName,datasourceName,datasetName);
        }catch(e){
            console.error(e);
        }
    }

    async downloadDataset(urlDatset,datasource){
        try{
            await DDS.downloadDataset(dataDownloadService._dataDownloadServiceId_,
                urlDatset,datasource.datasourceId);
        }catch(e){
            console.error(e);
        }
    }

    async updateDataset(urlDatset,dataset){
        try{
            await DDS.updateDataset(dataDownloadService._dataDownloadServiceId_,
                urlDatset,dataset.datasetId);
        }catch(e){
            console.error(e);
        }
    }
}