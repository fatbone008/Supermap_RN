import {NativeModules} from 'react-native';
let S = NativeModules.JSSize2D;

export default class Size2D {
    async createObj(w,h){
        try{
            var {size2DId} = await S.createObj(w,h);
            var size2D = new Size2D();
            size2D.size2DId = size2DId;
            return size2D;
        }catch (e){
            console.log(e);
        }
    }
}