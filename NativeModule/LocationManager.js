import {NativeModules} from 'react-native';
let LM = NativeModules.JSLocationManager;

export default class LocationManager {
    async createObj(){
        try{
            var {_locationManagePluginId_} = await LM.createObj();
            var loactionManager = new LocationManager();
            loactionManager._locationManagePluginId_ = _locationManagePluginId_;
            return loactionManager;
        }catch (e){
            console.error(e);
        }
    }

    async openGpsDevice(){
        try{
            await LM.openGpsDevice(this._locationManagePluginId_);
        }catch (e){
            console.error(e);
        }
    }

    async closeGpsDevice(){
        try{
            await LM.closeGpsDevice(this._locationManagePluginId_);
        }catch (e){
            console.error(e);
        }
    }

    async getLocationInfo(_callback){
        try{
            DeviceEventManager.addListener("com.supermap.RN.JSLocationManager.location_changed_event",function (e) {
                if(typeof _callback == "function"){
                    _callback(e);
                }else{
                    console.error("Please set a callback in the first argument.");
                }
            });
            await LM.getLocationInfo(this._locationManagePluginId_);
        }catch (e){
            console.error(e);
        }
    }
}