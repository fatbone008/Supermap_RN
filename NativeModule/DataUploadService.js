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
            var {_dataUploadServiceId_} = DUS.createObj(url);
            var datauploadService = new DataUploadService();
            datauploadService._dataUploadServiceId_ = _dataUploadServiceId_;
            return datauploadService;
        }catch(e){
            console.error(e);
        }
    }

    async addDataset(fullUrl,datasetName,datasetType){
        try{
            DUS.addDataset(this._dataUploadServiceId_,fullUrl,datasetName,datasetType);
        }catch(e){
            console.error(e);
        }
    }
}