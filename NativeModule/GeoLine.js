import {NativeModules} from 'react-native';
let GL = NativeModules.JSGeoLine;
import Geometry from './Geometry.js';

/**
 * @class GeoLine
 */
export default class GeoLine extends Geometry{
    constructor(){
        super();
        //同步子类Id和父类Id
        Object.defineProperty(this,"geoLineId",{
            get:function () {
                return this.geometryId
            },
            set:function (geoLineId) {
                this.geometryId = geoLineId;
            }
        })
    }

    /**
     * create a GeoLine instance
     * @memberOf GeoLine
     * @param points an Array laden with point objects. For example: [ {x:1.1,y:1.2} , {x:2.3,y:3.4} ]
     * @returns {Promise.<GeoLine>}
     */
    async createObj(points){
        try{
            if(!!points && typeof points == "array"){
                var {geoLineId} = await GL.createObjByPts();
            }else{
                var {geoLineId} = await GL.createObj();
            }
            geoLine = new GeoLine();
            geoLine.geoLineId = geoLineId;
            return geoLine;
        }catch (e){
            console.error(e);
        }
    }

}