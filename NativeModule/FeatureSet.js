import {NativeModules} from 'react-native';
let FS = NativeModules.JSFeatureSet;
import Geometry from './Geometry.js';

export default class FeatureSet {
    async deleteAll(){
        try{
            await FS.deleteAll(this._featureSetId_);
        }catch (e){
            console.error(e);
        }
    }

    async delete(){
        try{
            await FS.delete(this._featureSetId_);
        }catch (e){
            console.error(e);
        }
    }

    async getFeatureCount(){
        try{
            var count = await FS.getFeatureCount(this._featureSetId_);
            return count;
        }catch (e){
            console.error(e);
        }
    }

    async getFieldValue(fieldName){
        try{
            var value = await FS.getFieldValue(this._featureSetId_,fieldName);
            return value;
        }catch (e){
            console.error(e);
        }
    }

    async getGeometry(){
        try{
            var {geometryId} = await FS.getGeometry(this._featureSetId_);
            var geometry = new Geometry();
            geometry.geometryId = geometryId;
            return geometry;
        }catch (e){
            console.error(e);
        }
    }

    async isReadOnly(){
        try{
            await FS.isReadOnly(this._featureSetId_);
        }catch (e){
            console.error(e);
        }
    }

    async moveFirst(){
        try{
            await FS.moveFirst(this._featureSetId_);
        }catch (e){
            console.error(e);
        }
    }

    async moveLast(){
        try{
            await FS.moveLast(this._featureSetId_);
        }catch (e){
            console.error(e);
        }
    }

    async moveNext(){
        try{
            await FS.moveNext(this._featureSetId_);
        }catch (e){
            console.error(e);
        }
    }

    async movePrev(){
        try{
            await FS.movePrev(this._featureSetId_);
        }catch (e){
            console.error(e);
        }
    }
}