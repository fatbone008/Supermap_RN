import {NativeModules} from 'react-native';
let S = NativeModules.JSSelection;
import Recordset from './Recordset.js';

/**
 * @class Selection
 */
export default class Selection {
    /**
     * 将记录集转换成Selection
     * @memberOf Selection
     * @param {object} recordset - 记录集
     * @returns {Promise.<Promise.fromRecordset>}
     */
    async fromRecordset(recordset){
        try{
            let {fromRecordset} = await S.fromRecordset(this.selectionId,recordset.recordsetId);
            return fromRecordset;
        }catch(e){
            console.log(e);
        }
    }

    /**
     * 设置样式风格
     * @memberOf Selection
     * @param {object} geoStyle - 样式风格
     * @returns {Promise.<void>}
     */
    async setStyle(geoStyle){
        try{
            await S.setStyle(this.selectionId,geoStyle.geoStyleId);
        }catch(e){
            console.log(e);
        }
    }

    /**
     * 清空选择对象
     * @memberOf Selection
     * @returns {Promise.<void>}
     */
    async clear(){
        try{
            await S.clear(this.selectionId);
        }catch(e){
            console.log(e);
        }
    }

    /**
     * 转成recordset数据集
     * @memberOf Selection
     * @returns {Promise.<Recordset>}
     */
    async toRecordset(){
        try{
            var {recordsetId} = await S.toRecordset(this.selectionId);
            var recordset = new Recordset();
            recordset.recordsetId = recordsetId;
            return recordset;
        }catch(e){
            console.log(e);
        }
    }

    /**
     * 从查询结果获取地图被选要素
     * @memberOf Selection
     * @param {object} result - 经DataVector的query方法查询出的结果
     * @returns {Promise.<Promise.fromRecordset>}
     */
    async fromQueryResult(result){
        try{
            let {fromRecordset} = await S.fromRecordset(this.selectionId,result.recordsetId);
            return fromRecordset;
        }catch(e){
            console.log(e);
        }
    }
}