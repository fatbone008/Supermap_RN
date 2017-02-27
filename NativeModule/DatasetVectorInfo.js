import {NativeModules} from 'react-native';
let DVI = NativeModules.JSDatasetVectorInfo;

/**
 * @class DatasetVectorInfo
 */
export default class DatasetVectorInfo {

    /**
     * 创建DatasetVectorInfo实例
     * @param name - DatasetVectorInfo名字
     * @param type - DatasetVectorInfo类型
     * @returns {Promise.<DatasetVectorInfo>}
     */
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