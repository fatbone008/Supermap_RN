import {NativeModules} from 'react-native';
let SQP = NativeModules.JSServiceQueryParameter;
import Rectangle2D from './Rectangle2D.js';
import Geometry from './Geometry.js';

export default class ServiceQueryParameter {
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

    async setQueryDistance(geometry){
        try{
            await SQP.setQueryDistance(this._serviceQueryParameterId_, geometry.geometryId);
        }catch (e){
            console.error(e);
        }
    }

    async getQueryGeomety(){
        try{
            var {geometryId} = await SQP.getQueryGeomety(this._serviceQueryParameterId_);
            var geometry = new Geometry();
            geometry.geometryId = geometryId;
            return geometry;
        }catch (e){
            console.error(e);
        }
    }
}