import {NativeModules} from 'react-native';
let G = NativeModules.JSGeoStyle;

/**
 * @class GeoStyle
 */
export default class GeoStyle {
    /**
     * 构造一个新的 GeoStyle 对象。
     * @memberOf GeoStyle
     * @returns {Promise.<GeoStyle>}
     */
    async createObj(){
        var {geoStyleId} =await G.createObj();
        var geoStyle = new GeoStyle();
        geoStyle.geoStyleId = geoStyleId;
        return geoStyle;
    }catch(e){
        console.log(e);
    }

    /**
     * 设置线状符号型风格或点状符号的颜色。
     * @memberOf GeoStyle
     * @param {number} r - rgb颜色的red值
     * @param {number} g - rgb颜色的green值
     * @param {number} b - rgb颜色的blue值
     * @returns {Promise.<void>}
     */
    async setLineColor(r,g,b){
        try{
            await G.setLineColor(this.geoStyleId,r,g,b);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 返回线状符号的编码。此编码用于唯一标识各线状符号。
     线状符号可以用户自定义，也可以使用系统自带的符号库。使用系统自带符号库时，其相应的的编码参见开发指南 SuperMap Objects 资源库一览。
     * @memberOf GeoStyle
     * @param {number} symbolId  - 一个用来设置线型符号的编码的整数值。
     * @returns {Promise.<void>}
     */
    async setLineSymbolID(symbolId){
        try{
            await G.setLineSymbolID(this.geoStyleId,symbolId);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 设置线状符号的宽度。单位为毫米，精度到0.1。
     * @memberOf GeoStyle
     * @param {number} lineWidth - 用来设置线状符号的宽度。
     * @returns {Promise.<void>}
     */
    async setLineWidth(lineWidth){
        try{
            await G.setLineWidth(this.geoStyleId,lineWidth);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 返回点状符号的编码。此编码用于唯一标识各点状符号。
     点状符号可以用户自定义，也可以使用系统自带的符号库。使用系统自带符号库时，其相应的的编码参见开发指南 SuperMap Objects 资源库一览。
     * @memberOf GeoStyle
     * @param {number} markerSymbolId - 点状符号的编码。
     * @returns {Promise.<void>}
     */
    async setMarkerSymbolID(markerSymbolId){
        try{
            await G.setMarkerSymbolID(this.geoStyleId,markerSymbolId);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 设置点状符号的大小，单位为毫米，精确到0.1毫米。其值必须大于等于0。如果为0，则表示不显示，如果是小于0，会抛出异常。
     * @memberOf GeoStyle
     * @param {number} size2D - 用来设置点状符号的大小的值。
     * @returns {Promise.<void>}
     */
    async setMarkerSize(size2D){
        try{
            await G.setMarkerSize(this.geoStyleId,size2D.size2DId);
        }catch (e){
            console.error(e);
        }
    }

    /**
     *设置填充符号的前景色。
     * @memberOf GeoStyle
     * @param {number} r - rgb颜色的red值
     * @param {number} g - rgb颜色的green值
     * @param {number} b - rgb颜色的blue值
     * @returns {Promise.<void>}
     */
    async setFillForeColor(r,g,b){
        try{
            await G.setFillForeColor(this.geoStyleId,r,g,b);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 设置填充不透明度，合法值0-100的数值。
     * @memberOf GeoStyle
     * @param {number} rate - 透明度比例
     * @returns {Promise.<void>}
     */
    async setFillOpaqueRate(rate){
        try{
            await G.setFillOpaqueRate(this.geoStyleId,rate)
        }catch (e){
            console.error(e);
        }
    }
}