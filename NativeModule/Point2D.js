import {NativeModules} from 'react-native';
let X = NativeModules.JSPoint2D;

export default class Point2D {
    async createObj(){
        try{
            if(typeof arguments[0] == 'number' && typeof arguments[1] == 'number'){
                var {point2DId} = await X.createObjByXY(arguments[0],arguments[1]);
                var point2D = new Point2D();
                point2D.point2DId = point2DId;
                return point2D;
            }else{
                var {point2DId} = await X.createObj();
                var point2D = new Point2D();
                point2D.point2DId = point2DId;
                return point2D;
            }
        }catch (e){
            console.error(e);
        }
    }
}