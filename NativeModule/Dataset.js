/**
 * Created by will on 2016/6/17.
 */
import {NativeModules} from 'react-native';
let D = NativeModules.JSDataset;
import DatasetVector from './DatasetVector.js';
import PrjCoordSys from './PrjCoordSys.js';

/**
 * @class Dataset
 * @property {number} TYPE.TABULAR - 纯属性数据集。
 * @property {number} TYPE.POINT - 点数据集。
 * @property {number} TYPE.LINE - 线数据集。
 * @property {number} TYPE.REGION - 多边形数据集。
 * @property {number} TYPE.TEXT - 文本数据集。
 * @property {number} TYPE.IMAGE - 影像数据集。
 * @property {number} TYPE.CAD - 复合数据集。
 * @property {number} TYPE.NETWORK - 网络数据集。
 * @property {number} TYPE.NETWORK3D - 三维网络数据集。
 * @property {number} TYPE.NdfVector -
 * @property {number} TYPE.GRID - 栅格数据集。
 * @property {number} TYPE.WMS - WMS数据集。
 * @property {number} TYPE.WCS - WCS数据集。
 * @property {number} TYPE.WFS - WFS数据集。
 * @property {number} TYPE.POINT3D - 三维点数据集。
 * @property {number} TYPE.LINE3D - 三维线数据集。
 * @property {number} TYPE.REGION3D - 三维面数据集。
 * @property {number} TYPE.DEM -
 */
export default class Dataset{
    async toDatasetVector(){
        try{
            var {datasetVectorId} = await D.toDatasetVector(this.datasetId);
            var datasetVector = new DatasetVector();
            datasetVector.datasetVectorId = datasetVectorId;
            return datasetVector;
        }catch(e){
            console.error(e);
        }
    }

    async getPrjCoordSys(){
        try{
            var {prjCoordSysId} = await D.getPrjCoordSys(this.datasetId);
            var prjCoordSys = new PrjCoordSys();
            prjCoordSys.prjCoordSysId = prjCoordSysId;
            return prjCoordSys;
        }catch(e){
            console.error(e);
        }
    }
}

Dataset.TYPE = {
    TABULAR:0,
    POINT:1,
    LINE:3,
    REGION:5,
    TEXT:7,
    IMAGE:81,
    CAD:149,
    NETWORK:4,
    NETWORK3D:205,
    NdfVector:500,
    GRID:83,
    WMS:86,
    WCS:87,
    WFS:151,
    POINT3D:101,
    LINE3D:103,
    REGION3D:105,
    DEM:84
};