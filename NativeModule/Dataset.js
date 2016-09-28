/**
 * Created by will on 2016/6/17.
 */
import {NativeModules} from 'react-native';
let D = NativeModules.JSDataset;
import DatasetVector from './DatasetVector.js';
import PrjCoordSys from './PrjCoordSys.js';

export default class Dataset{
    static TYPE = {
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
        DEM:84,
    }
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