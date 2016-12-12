import {NativeModules} from 'react-native';
let C = NativeModules.JSCallOut;

export default class CallOut {
    async createObj(mapView){
        try{
            var {callOutId} = await C.createObj(mapView.mapViewId);
            var callOut = new CallOut();
            callOut.callOutId = callOutId;
            return callOut;
        }catch (e){
            console.error(e);
        }
    }

    async setStyle(){
        try{
            await C.setStyle(this.callOutId);
        }catch (e){
            console.error(e);
        }
    }

    async setCustomize(isSet){
        try{
            await C.setCustomize(this.callOutId,isSet);
        }catch (e){
            console.error(e);
        }
    }

    /**
     *
     * @param point2D
     */
    async setLocation(point2D){
        try{
            await C.setLocation(this.callOutId,point2D.point2DId);
        }catch (e){
            console.error(e);
        }
    }

    async setContentView(imageViewId){
        try{
            await C.setContentView(this.callOutId,imageViewId);
        }catch (e){
            console.error(e);
        }
    }
}