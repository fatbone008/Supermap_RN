/**
 * Created by will on 2016/7/13.
 */
import { NativeModules } from 'react-native';
let P = NativeModules.JSPoint;

export default class Point{
    async createObj(x,y){
        try{
            var {pointId} = await P.createObj(x,y);
            var point = new Point();
            point.pointId = pointId;
            return point;
        }catch (e){
            console.error(e);
        }
    }
}