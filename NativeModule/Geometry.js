import {NativeModules} from 'react-native';
let G = NativeModules.JSGeometry;
import Point2D from './Point2D.js';

export default class Geometry {
    // constructor(subObjIdName){
    //     super();
    //     Object.defineProperties(this,subObjIdName,{
    //         get:function () {
    //             return this.geometryId
    //         }
    //         set:function (geoPointId) {
    //             this.geometryId = geometryId;
    //         }
    //     })
    // }
    async getInnerPoint(){
        try{
            var {point2DId} = await G.getInnerPoint(this.geometryId);
            var point2D = new Point2D();
            point2D.point2DId = point2DId;
            return point2D;
        }catch (e){
            console.error(e);
        }
    }

    async setStyle(geoStyle){
        try{
            var id = this.geometryId || this.geoPointId;
            await G.setStyle(id,geoStyle.geoStyleId);
        }catch (e){
            console.error(e);
        }
    }
}