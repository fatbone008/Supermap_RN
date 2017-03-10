import {NativeModules} from 'react-native';
let L = NativeModules.JSLegend;

export default class Legend {
    async createObjWithMap(map){
        try{
            var {legendId} = await L.createObjWithMap(map.mapId);
            var legend = new Legend();
            legend.legendId = legendId;
            return legend;
        }catch (e){
            console.error(e);
        }
    }

    async show(enable){
        try{
            await L.show(this.legendId,enable);
        }catch (e){
            console.error(e);
        }
    }
}
