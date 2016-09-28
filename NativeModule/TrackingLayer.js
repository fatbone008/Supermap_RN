import {NativeModules} from 'react-native';
let TL = NativeModules.JSTrackingLayer;

export default class TrackingLayer {
    async add(geometry,tag){
        try{
            var id = geometry.geometryId;
            console.log('id:'+id);
            await TL.add(this.trackingLayerId,id,tag);
        }catch (e){
            console.error(e);
        }
    }

    async clear(){
        try{
            await TL.clear(this.trackingLayerId);
        }catch (e){
            console.error(e);
        }
    }
}