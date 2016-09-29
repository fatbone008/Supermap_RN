import {NativeModules} from 'react-native';
let F = NativeModules.JSFeature;
import Geometry from './Geometry.js';

export default class Feature {
    async createObj(fieldNames,fieldValues,geometry){
        try{
            var {_featureId_} = await F.createObj(fieldNames,fieldValues,geometry.geometryId);
            var feature = new Feature();
            feature._featureId_ = _featureId_;
            return feature;
        }catch (e){
            console.error(e);
        }
    }

    async getFieldNames(){
        try{
            var arr = F.getFieldNames(this._featureId_);
            return arr;
        }catch (e){
            console.error(e);
        }
    }

    async getFieldValues(){
        try{
            var arr = F.getFieldValues(this._featureId_);
            return arr;
        }catch (e){
            console.error(e);
        }
    }

    async getGeometry(){
        try{
            var {geometryId} = await F.getGeometry(this._featureId_);
            var geometry = new Geometry();
            geometry.geometryId = geometryId;
            return geometry;
        }catch (e){
            console.error(e);
        }
    }

    async toJson(){
        try{
            var jsonString = await F.toJson(this._featureId_);
            var jsonObj = JSON.parse(jsonString);
            return jsonObj;
        }catch (e){
            console.error(e);
        }
    }
}