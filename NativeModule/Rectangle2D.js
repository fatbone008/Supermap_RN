import {NativeModules} from 'react-native';
let R = NativeModules.JSRectangle2D;
import Point2D from './Point2D.js';

export default class Rectangle2D {
    async createObj(){
        try{
            if(arguments[0] instanceof Point2D && arguments[1] instanceof Point2D){
                console.log('create by two Point2D objects.');
                var {rectangle2DId} = await R.createObjBy2Pt(arguments[0].point2DId,arguments[1].point2DId);
                return this.returnObj(rectangle2DId);
            }else{
                console.log('create with nothing.');
                var {rectangle2DId} = await R.createObj();
                return this.returnObj(rectangle2DId);
            }
        }catch (e){
            console.error(e);
        }
    }

    returnObj(rectangle2DId){
        var rectangle2D = new Rectangle2D();
        rectangle2D.rectangle2DId = rectangle2DId;
        return rectangle2D;
    }
}