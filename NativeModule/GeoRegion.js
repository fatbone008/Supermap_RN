import {NativeModules} from 'react-native';
let X = NativeModules.JSGeoRegion;
import Geometry from './Geometry.js';

/**
 * @class GeoRegion
 */
export default class GeoRegion extends Geometry{
    constructor(){
        super();
        //同步子类Id和父类Id
        Object.defineProperty(this,"geoRegionId",{
            get:function () {
                return this.geometryId
            },
            set:function (geoRegionId) {
                this.geometryId = geoRegionId;
            }
        });
    }
}