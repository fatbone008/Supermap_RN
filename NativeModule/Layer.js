/**
 * Created by will on 2016/7/5.
 */
import {NativeModules} from 'react-native';
let L = NativeModules.JSLayer;
import Dataset from './Dataset.js';
import Selection from './Selection.js';
import LayerSetting from './LayerSetting.js';

export default class Layer{
    async setEditable(editable){
        try{
            await L.setEditable(this.layerId,editable);
        }catch(e){
            console.error(e);
        }
    }

    async getName(index){
        try{
            var {layerName} = await L.getName(this.layerId,index);
            return layerName;
        }catch(e){
            console.error(e);
        }
    }

    async getDataset(){
        try{
            var {datasetId} = await L.getDataset(this.layerId);
            var dataset = new Dataset();
            dataset.datasetId = datasetId;
            return dataset;
        }catch(e){
            console.error(e);
        }
    }

    async setDataset(dataset){
        try{
            await L.setDataset(this.layerId,dataset.datasetId);
        }catch(e){
            console.error(e);
        }
    }

    async getSelection(){
        try{
            var {selectionId,recordsetId} = await L.getSelection(this.layerId);
            var selection = new Selection();
            selection.selectionId = selectionId;
            selection.recordsetId = recordsetId;
            return selection;
        }catch(e){
            console.error(e);
        }
    }

    async setSelectable(b){
        try{
            await L.setSelectable(this.layerId,b);
        }catch(e){
            console.error(e);
        }
    }

    async setVisible(b){
        try{
            await L.setVisible(this.layerId,b);
        }catch(e){
            console.error(e);
        }
    }

    async getAdditionalSetting(){
        try{
            var {_layerSettingId_} = await L.getAdditionalSetting(this.layerId);
            var layerSetting = new LayerSetting();
            layerSetting._layerSettingId_ = _layerSettingId_;
            return layerSetting;
        }catch(e){
            console.error(e);
        }
    }

    async setAdditionalSetting(layerSetting){
        try{
            await L.setAdditionalSetting(this.layerId,layerSetting._layerSettingId_);
        }catch(e){
            console.error(e);
        }
    }
}