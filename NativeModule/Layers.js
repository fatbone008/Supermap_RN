/**
 * Created by will on 2016/6/17.
 */
import {NativeModules} from 'react-native';
let L = NativeModules.JSLayers;
import Layer from './Layer.js';

export default class Layers{
    async add(dataset,b){
        this._drepecated();
        try{
            await L.add(this.layersId,dataset.datasetId,b);
        }catch (e){
            console.error(e);
        }
    }

    async get(index){
        this._drepecated();
        try{
            var layer = new Layer();
            if(typeof index == "string"){
                var {layerId} = await L.getByName(this.layersId,index);
            }else{
                var {layerId} = await L.get(this.layersId,index);
            }
            layer.layerId = layerId;
            return layer;
        }catch(e){
            console.error(e);
        }
    }

    async getCount(){
        this._drepecated();
        try{
            var {count} = await L.getCount(this.layersId);
            return count;
        }catch(e){
            console.error(e);
        }
    }

    _drepecated(){
        console.warn("Layers.js:This class has been deprecated. " +
            "All its implements has been migrated to the Map class." +
            "eg:Layers.get(index) now has been substituted by map.getLayer(index)." +
            "Relevant modifications refer to the API documents please");
    }
}