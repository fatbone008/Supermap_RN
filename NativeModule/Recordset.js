import {NativeModules} from 'react-native';
let R = NativeModules.JSRecordset;
import Geometry from './Geometry.js';
import Dataset from './Dataset';

/**
 * @class Recordset
 * @deprecated
 */
export default class Recordset {
    async getRecordCount(){
        try{
            var {recordCount} = await R.getRecordCount(this.recordsetId);
            return recordCount;
        }catch (e){
            console.error(e);
        }
    }

    async dispose(){
        try{
            await R.dispose(this.recordsetId);
        }catch (e){
            console.error(e);
        }
    }

    async getGeometry(){
        try{
            var {geometryId} = await R.getGeometry(this.recordsetId);
            var geometry = new Geometry();
            geometry.geometryId = geometryId;
            return geometry;
        }catch (e){
            console.error(e);
        }
    }

    async isEOF(){
        try{
            var isEOF = await R.isEOF(this.recordsetId);
            return isEOF;
        }catch (e){
            console.error(e);
        }
    }

    async getDataset(){
        try{
            var {datasetId} = await R.getDataset(this.recordsetId);
            var dataset = new Dataset();
            dataset.datasetId = datasetId;
            return dataset;
        }catch (e){
            console.error(e);
        }
    }

    async addNew(geometry){
        try{
            await R.addNew(this.recordsetId,geometry.geometryId);
        }catch (e){
            console.error(e);
        }
    }

    async moveNext(){
        try{
            await R.moveNext(this.recordsetId);
        }catch (e){
            console.error(e);
        }
    }

    async update(){
        try{
            await R.update(this.recordsetId);
        }catch (e){
            console.error(e);
        }
    }
}
