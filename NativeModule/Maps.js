/**
 * Created by will on 2016/7/4.
 */

import { NativeModules } from 'react-native';
let M = NativeModules.JSMaps;

export default class　Maps{
    async get(index){
        try{
            var {mapName} = await M.get(this.mapsId,index);
            return mapName;
        }catch(e){
            console.error(e);
        }
    }
}