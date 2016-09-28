import {NativeModules} from 'react-native';
let DVI = NativeModules.JSDatasetVectorInfo;

export default class DatasetVectorInfo {
    async createObjByNameType(name,type){
        try{
            var {datasetVectorInfoId} = await DVI.createObjByNameType(name,type);
            var datasetVectorInfo = new DatasetVectorInfo();
            datasetVectorInfo.datasetVectorInfoId = datasetVectorInfoId;
            return datasetVectorInfo;
        }catch (e){
            console.error(e);
        }
    }
}