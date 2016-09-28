import {NativeModules} from 'react-native';
let P = NativeModules.JSGeoPoint;
import Geometry from './Geometry.js';

export default class GeoPoint extends Geometry{
    constructor(){
        super();
        //同步子类Id和父类Id
        Object.defineProperty(this,"geoPointId",{
            get:function () {
                return this.geometryId
            },
            set:function (geoPointId) {
                this.geometryId = geoPointId;
            }
        })
    }
    async createObj(){
        try{
            if(typeof arguments[0] == 'number' && typeof arguments[1] == 'number'){
                var {geoPointId} = await P.createObjByXY(arguments[0],arguments[1]);
                var geoPoint = new GeoPoint();
                geoPoint.geoPointId = geoPointId;
                return geoPoint;
            }else{
                var {geoPointId} = await P.createObj();
                var geoPoint = new GeoPoint(geoPointId);
                geoPoint.geoPointId = geoPointId;
                return geoPoint;
            }
        }catch (e){
            console.error(e);
        }
    }

    async getX(){
        try {
            var {coordsX} = await P.getX(this.geoPointId);
            return coordsX;
            console.log("coordsX"+coordsX);
        }catch (e){
            console.error(e);
        }
    }

    async getY(){
        try {
            var {coordsY} = await P.getY(this.geoPointId);
            return coordsY;
        }catch (e){
            console.error(e);
        }
    }
}