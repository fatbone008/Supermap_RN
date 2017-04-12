/**
 * Created by will on 2016/7/13.
 */
import { NativeModules } from 'react-native';
let SU = NativeModules.JSSystemUtil;

/**
 * @class Point - 像素点类。用于标示移动设备屏幕的像素点。
 */
export default class SystemUtil{
    /**
     * 获取沙盒路径
     * @returns {Promise.<string>}
     */
    async getHomeDirectory(){
        try{
            var {homeDirectory} = await SU.getHomeDirectory();
            return homeDirectory;
        }catch (e){
            console.error(e);
        }
    }
}
