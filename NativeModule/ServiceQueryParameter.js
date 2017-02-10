import {NativeModules} from 'react-native';
let SQP = NativeModules.JSServiceQueryParameter;
import Rectangle2D from './Rectangle2D.js';
import Geometry from './Geometry.js';

export default class ServiceQueryParameter {
    static QueryOptionType = {
        ATTRIBUTE:0,
        ATTRIBUTEANDGEOMETRY:1,
        GEOMETRY:2
    };


    async createObj(){
        try{
            var {_serviceQueryParameterId_} = await SQP.createObj();
            var serviceQueryParameter = new ServiceQueryParameter();
            serviceQueryParameter._serviceQueryParameterId_ = _serviceQueryParameterId_;
            return serviceQueryParameter;
        }catch (e){
            console.error(e);
        }
    }

    async setQueryBounds(rectangle2D){
        try{
            await SQP.setQueryBounds(this._serviceQueryParameterId_, rectangle2D.rectangle2DId);
        }catch (e){
            console.error(e);
        }
    }

    async getQueryBounds(){
        try{
            var {rectangle2DId} = await SQP.getQueryBounds(this._serviceQueryParameterId_);
            var rectangle2D = new Rectangle2D();
            rectangle2D.rectangle2DId = rectangle2DId;
            return rectangle2D;
        }catch (e){
            console.error(e);
        }
    }

    async setQueryDistance(distance){
        try{
            await SQP.setQueryDistance(this._serviceQueryParameterId_, distance);
        }catch (e){
            console.error(e);
        }
    }

    async getQueryDistance(){
        try{
            var distance = await SQP.getQueryDistance(this._serviceQueryParameterId_);
            return distance;
        }catch (e){
            console.error(e);
        }
    }

    async getQueryGeometry(){
        try{
            var {geometryId} = await SQP.getQueryGeometry(this._serviceQueryParameterId_);
            var geometry = new Geometry();
            geometry.geometryId = geometryId;
            return geometry;
        }catch (e){
            console.error(e);
        }
    }

    async setQueryGeometry(geometry){
        try{
            await SQP.setQueryGeometry(this._serviceQueryParameterId_, geometry.geometryId);
        }catch (e){
            console.error(e);
        }
    }

    async getQueryLayerName(){
        try{
            var name = await SQP.getQueryLayerName(this._serviceQueryParameterId_);
            return name;
        }catch (e){
            console.error(e);
        }
    }

    async setQueryLayerName(queryLayerName){
        try{
            await SQP.setQueryLayerName(this._serviceQueryParameterId_, queryLayerName);
        }catch (e){
            console.error(e);
        }
    }

    async getQueryMapName(){
        try{
            var name = await SQP.getQueryMapName(this._serviceQueryParameterId_);
            return name;
        }catch (e){
            console.error(e);
        }
    }

    async setQueryMapName(mapName){
        try{
            await SQP.setQueryMapName(this._serviceQueryParameterId_, mapName);
        }catch (e){
            console.error(e);
        }
    }

    async getQueryOption(){
        try{
            var optionName = await SQP.getQueryOption(this._serviceQueryParameterId_);
            return optionName;
        }catch (e){
            console.error(e);
        }
    }

    async setQueryOption(queryOptionType){
        try{
            await SQP.setQueryOption(this._serviceQueryParameterId_, queryOptionType);
        }catch (e){
            console.error(e);
        }
    }

    async getExpectRecordCount(){
        try{
            var count = await SQP.getExpectRecordCount(this._serviceQueryParameterId_);
            return count;
        }catch (e){
            console.error(e);
        }
    }

    async setExpectRecordCount(count){
        try{
            await SQP.setExpectRecordCount(this._serviceQueryParameterId_, count);
        }catch (e){
            console.error(e);
        }
    }

    async getQueryServiceName(){
        try{
            var serviceName = await SQP.getQueryServiceName(this._serviceQueryParameterId_);
            return serviceName;
        }catch (e){
            console.error(e);
        }
    }

    async setQueryServiceName(name){
        try{
            await SQP.setQueryServiceName(this._serviceQueryParameterId_, name);
        }catch (e){
            console.error(e);
        }
    }

    async toJson(){
        try{
            var jsonString = await SQP.toJson(this._serviceQueryParameterId_);
            var jsonOBj = JSON.parse(jsonString);
            return jsonOBj;
        }catch (e){
            console.error(e);
        }
    }
}