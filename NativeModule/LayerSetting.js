import {NativeModules} from 'react-native';
let LS = NativeModules.JSLayerSetting;

/**
 * @class LayerSetting
 */
export default class LayerSetting {

    /**
     *  返回此图层的类型。
     *  @memberOf LayerSetting
     * @returns {Promise.<Promise|*|Dataset.Type>}
     */
    async getType(){
        try{
            var layerSettingType = L.getType(this._layerSettingId_);
            return layerSettingType;
        }catch(e){
            console.error(e);
        }
    }
}