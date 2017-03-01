import {NativeModules} from 'react-native';
let SQP = NativeModules.JSServiceQueryParameter;
import Rectangle2D from './Rectangle2D.js';
import Geometry from './Geometry.js';

/**
 * @class ServiceQueryParameter
 * QueryOptionType.ATTRIBUTE - 查询结果中只有属性。
 * QueryOptionType.ATTRIBUTEANDGEOMETRY - 查询结果中包含属性和几何对象。
 * QueryOptionType.GEOMETR - 查询结果中只有几何对象。
 */
export default class ServiceQueryParameter {

    /**
     * 创建一个ServiceQueryParameter参数
     * @memberOf ServiceQueryParameter
     * @returns {Promise.<ServiceQueryParameter>}
     */
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

    /**
     * 设置查询范围。
     * @memberOf ServiceQueryParameter
     * @param {object} rectangle2D - 查询范围。
     * @returns {Promise.<void>}
     */
    async setQueryBounds(rectangle2D){
        try{
            await SQP.setQueryBounds(this._serviceQueryParameterId_, rectangle2D.rectangle2DId);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 获取查询范围。
     * @memberOf ServiceQueryParameter
     * @returns {Promise.<Rectangle2D>}
     */
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

    /**
     * 设置查询的距离。
     * @memberOf ServiceQueryParameter
     * @param {number} distance - 查询距离
     * @returns {Promise.<void>}
     */
    async setQueryDistance(distance){
        try{
            await SQP.setQueryDistance(this._serviceQueryParameterId_, distance);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 返回查询的距离。
     * @memberOf ServiceQueryParameter
     * @returns {Promise}
     */
    async getQueryDistance(){
        try{
            var distance = await SQP.getQueryDistance(this._serviceQueryParameterId_);
            return distance;
        }catch (e){
            console.error(e);
        }
    }

    /**
     *返回查询的几何对象。
     * @memberOf ServiceQueryParameter
     * @returns {Promise.<Geometry>}
     */
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

    /**
     * 设置查询的几何对象。
     * @memberOf ServiceQueryParameter
     * @param {object} geometry - 要设置的几何对象。
     * @returns {Promise.<void>}
     */
    async setQueryGeometry(geometry){
        try{
            await SQP.setQueryGeometry(this._serviceQueryParameterId_, geometry.geometryId);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 获取查询的图层名称。
     * @memberOf ServiceQueryParameter
     * @returns {Promise}
     */
    async getQueryLayerName(){
        try{
            var name = await SQP.getQueryLayerName(this._serviceQueryParameterId_);
            return name;
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 设置当前查询的图层的名称。
     * @memberOf ServiceQueryParameter
     * @param {string} queryLayerName - 设置当前查询的图层的名称。
     * @returns {Promise.<void>}
     */
    async setQueryLayerName(queryLayerName){
        try{
            await SQP.setQueryLayerName(this._serviceQueryParameterId_, queryLayerName);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 获取服务查询的地图的名称。
     * @memberOf ServiceQueryParameter
     * @returns {Promise}
     */
    async getQueryMapName(){
        try{
            var name = await SQP.getQueryMapName(this._serviceQueryParameterId_);
            return name;
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 设置查询地图的地图名称。
     * @memberOf ServiceQueryParameter
     * @param {string} mapName - 设置查询地图的地图名称。
     * @returns {Promise.<void>}
     */
    async setQueryMapName(mapName){
        try{
            await SQP.setQueryMapName(this._serviceQueryParameterId_, mapName);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 返回查询结果内容类型。
     * @memberOf ServiceQueryParameter
     * @returns {Promise}
     */
    async getQueryOption(){
        try{
            var optionName = await SQP.getQueryOption(this._serviceQueryParameterId_);
            return optionName;
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 设置查询结果内容类型。
     * @memberOf ServiceQueryParameter
     * @param {ServiceQueryParameter.QueryOptionType} queryOptionType - 查询结果内容类型
     * @returns {Promise.<void>}
     */
    async setQueryOption(queryOptionType){
        try{
            await SQP.setQueryOption(this._serviceQueryParameterId_, queryOptionType);
        }catch (e){
            console.error(e);
        }
    }

    /**
     *获取期望返回的查询记录个数。
     * @memberOf ServiceQueryParameter
     * @returns {Promise}
     */
    async getExpectRecordCount(){
        try{
            var count = await SQP.getExpectRecordCount(this._serviceQueryParameterId_);
            return count;
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 设置期望返回的查询记录个数。
     * @memberOf ServiceQueryParameter
     * @param {number} count - 设置期望返回的查询记录个数。
     * @returns {Promise.<void>}
     */
    async setExpectRecordCount(count){
        try{
            await SQP.setExpectRecordCount(this._serviceQueryParameterId_, count);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 获取当前查询的服务的实例的服务名称。
     * @memberOf ServiceQueryParameter
     * @returns {Promise}
     */
    async getQueryServiceName(){
        try{
            var serviceName = await SQP.getQueryServiceName(this._serviceQueryParameterId_);
            return serviceName;
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 获取当前查询的服务的实例的服务名称。
     * @memberOf ServiceQueryParameter
     * @param {string} name - 当前查询的服务的实例的服务名称。
     * @returns {Promise.<void>}
     */
    async setQueryServiceName(name){
        try{
            await SQP.setQueryServiceName(this._serviceQueryParameterId_, name);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 转成Json格式对象
     * @memberOf ServiceQueryParameter
     * @returns {Promise.<void>}
     */
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

ServiceQueryParameter.QueryOptionType = {
    ATTRIBUTE:0,
    ATTRIBUTEANDGEOMETRY:1,
    GEOMETRY:2
};