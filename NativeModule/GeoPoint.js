import {NativeModules} from 'react-native';
let P = NativeModules.JSGeoPoint;
import Geometry from './Geometry.js';

/**
 * @class GeoPoint
 */
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

    /**
     * 构造一个新的 GeoPoint 对象,x,y参数为可选参数，无参数时此几何点对象的坐标 X，Y 分别为-1.7976931348623157e+308。
     * @memberOf GeoPoint
     * @param {number} x - 指定点几何对象的 X 坐标值。
     * @param {number} y - 指定点几何对象的 y 坐标值。
     * @returns {Promise.<GeoPoint>}
     */
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

    /**
     * 返回此点几何对象的 X 坐标。使用无参构造函数构造的点对象的 X 坐标值为默认值。
     * @memberOf GeoPoint
     * @returns {Promise.<Promise.number>}
     */
    async getX(){
        try {
            var {coordsX} = await P.getX(this.geoPointId);
            return coordsX;
            console.log("coordsX"+coordsX);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 返回此点几何对象的 Y 坐标。使用无参构造函数构造的点对象的 Y 坐标值为默认值。
     * @memberOf GeoPoint
     * @returns {Promise.<Promise.number>}
     */
    async getY(){
        try {
            var {coordsY} = await P.getY(this.geoPointId);
            return coordsY;
        }catch (e){
            console.error(e);
        }
    }
}