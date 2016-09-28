import {NativeModules} from 'react-native';
let BAG = NativeModules.JSBufferAnalystGeometry;
import GeoRegion from './GeoRegion.js';

export default class BufferAnalystGeometry {
    static async createBuffer(geometry,bufferAnalystParameter,prjCoordSys){
        try{
            var {geoRegionId} = await BAG.createBuffer(this.bufferAnalystGeometryId,
                geometry.geometryId,bufferAnalystParameter.bufferAnalystParameterId,prjCoordSys.prjCoordSysId);
            var geoRegion = new GeoRegion();
            geoRegion.geoRegionId = geoRegionId;
            console.log("geoRegion.geometryId:"+geoRegion.geometryId);
            console.log("geoRegion.geoRegionId:" + geoRegion.geoRegionId);
            return geoRegion;
        }catch (e){
            console.error(e);
        }
    }
}