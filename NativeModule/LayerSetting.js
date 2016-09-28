import {NativeModules} from 'react-native';
let LS = NativeModules.JSLayerSetting;

export default class LayerSetting {

    async getType(){
        try{
            var layerSettingType = L.getType(this._layerSettingId_);
            return layerSettingType;
        }catch(e){
            console.error(e);
        }
    }
}