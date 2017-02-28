/**
 * Created by will on 2016/7/13.
 */
import { NativeModules } from 'react-native';
let P = NativeModules.JSPoint;

/**
 * @class Point - 像素点类。用于标示移动设备屏幕的像素点。
 */
export default class Point{
    /**
     * 创建一个Point对象
     * @param x - 像素坐标 x 的值
     * @param y - 像素坐标 y 的值
     * @returns {Promise.<Point>}
     */
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