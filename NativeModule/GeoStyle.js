import {NativeModules} from 'react-native';
let G = NativeModules.JSGeoStyle;

export default class GeoStyle {
    async createObj(){
        var {geoStyleId} =await G.createObj();
        var geoStyle = new GeoStyle();
        geoStyle.geoStyleId = geoStyleId;
        return geoStyle;
    }catch(e){
        console.log(e);
    }

    async setLineColor(r,g,b){
        try{
            await G.setLineColor(this.geoStyleId,r,g,b);
        }catch (e){
            console.error(e);
        }
    }

    async setLineSymbolID(symbolId){
        try{
            await G.setLineSymbolID(this.geoStyleId,symbolId);
        }catch (e){
            console.error(e);
        }
    }

    async setLineWidth(lineWidth){
        try{
            await G.setLineWidth(this.geoStyleId,lineWidth);
        }catch (e){
            console.error(e);
        }
    }

    async setMarkerSymbolID(markerSymbolId){
        try{
            await G.setMarkerSymbolID(this.geoStyleId,markerSymbolId);
        }catch (e){
            console.error(e);
        }
    }

    async setMarkerSize(size2D){
        try{
            await G.setMarkerSize(this.geoStyleId,size2D.size2DId);
        }catch (e){
            console.error(e);
        }
    }

    async setFillForeColor(r,g,b){
        try{
            await G.setFillForeColor(this.geoStyleId,r,g,b);
        }catch (e){
            console.error(e);
        }
    }

    async setFillOpaqueRate(rate){
        try{
            await G.setFillOpaqueRate(this.geoStyleId,rate)
        }catch (e){
            console.error(e);
        }
    }
}