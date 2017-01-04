/**
 * Created by will on 2016/6/17.
 */
import { NativeModules,Platform } from 'react-native';
let MV = NativeModules.JSMapView;
import MapControl from './MapControl.js';
import Point2D from './Point2D.js';

export default class JSMapView{
    static NAVIGATION_STARTPOINT = "startpoint";
    static NAVIGATION_DESTPOINT = "destpoint";

    async getMapControl(){
        try{
            if(Platform.OS === 'ios'){
                this.mapControlId = this.mapViewId;
                return this;
            }
            var {mapControlId} =await MV.getMapControl(this.mapViewId);
            var mapControl = new MapControl();
            mapControl.mapControlId = mapControlId;
            return mapControl;
        }catch (e){
            console.error(e);
        }
    }

    async addPoint(point2D,pointName){
        try{
            var {eth_point2DId} = await MV.addPoint(this.mapViewId,point2D.point2DId,pointName);
            var point2D = new Point2D();
            point2D.point2DId = eth_point2DId;
            return point2D;
        }catch (e){
            console.error(e);
        }
    }

    async refresh(){
        try{
            await MV.refresh(this.mapViewId);
        }catch(e){
            console.error(e);
        }
    }

    async addCallOut(callOut,pointName){
        try{
            await MV.addCallOut(this.mapViewId,callOut.callOutId,pointName);
        }catch(e){
            console.error(e);
        }
    }

    async showCallOut(){
        try{
            await MV.showCallOut(this.mapViewId);
        }catch(e){
            console.error(e);
        }
    }
}
