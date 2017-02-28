import {NativeModules} from 'react-native';
let TL = NativeModules.JSTrackingLayer;

/**
 * @class TrackingLayer
 */
export default class TrackingLayer {
    /**
     * 添加几何数据
     * @memberOf TrackingLayer
     * @param {object} geometry - 矢量对象
     * @param {string} tag - 矢量对象的标签名称
     * @returns {Promise.<void>}
     */
    async add(geometry,tag){
        try{
            var id = geometry.geometryId;
            console.log('id:'+id);
            await TL.add(this.trackingLayerId,id,tag);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 清除追踪层的集合对象
     * @returns {Promise.<void>}
     */
    async clear(){
        try{
            await TL.clear(this.trackingLayerId);
        }catch (e){
            console.error(e);
        }
    }
}