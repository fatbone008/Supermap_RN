import {NativeModules} from 'react-native';
let LSV = NativeModules.JSLayerSettingVector;
import GeoStyle from './GeoStyle.js';

export default class LayerSettingVector {
    async createObj(){
        try{
            var {_layerSettingVectorId_} = await LSV.createObj();
            var layerSettingVector = new LayerSettingVector();
            layerSettingVector._layerSettingVectorId_ = _layerSettingVectorId_
            return layerSettingVector;
        }catch (e){
            console.error(e);
        }
    }

    async getStyle(){
        try{

            var {geoStyleId} = await LSV.getStyle(this._layerSettingVectorId_);
            var geoStyle = new GeoStyle();
            geoStyle.geoStyleId = geoStyleId;
            return geoStyle;
        }catch (e){
            console.error(e);
        }
    }

    async setStyle(geoStyle){
        try{
            await LSV.setStyle(this._layerSettingVectorId_,geoStyle.geoStyleId);
        }catch (e){
            console.error(e);
        }
    }
}