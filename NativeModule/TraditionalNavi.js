import {NativeModules} from 'react-native';
let TN = NativeModules.JSNavigation;

/**
 * @class TraditionalNavi
 */
export default class TraditionalNavi {
    /**
     * 链接导航路网数据
     * @memberOf TraditionalNavi
     * @param dataPath - 数据存储路径
     * @returns {Promise.<Promise.boolean>}
     */
    async connectNaviData(dataPath){
        try{
            var {success} = await TN.connectNaviData(this.traditionalNaviId,dataPath);
            return success;
        }catch (e){
            console.log(e);
        }
    }

    /**
     * 执行路径分析。
     * 路径分析模式的值为[0，1，2，3]，其分别表示推荐模式、时间最快模式、距离最短模式、和最少收费模式。
     * @memberOf TraditionalNavi
     * @param mode - 路径分析模式。
     * @returns {Promise.<number>} 路径分析成功返回1，路径分析失败返回0，起点周围无道路返回-1，终点周围无道路返回-2。
     */
    async routeAnalyst(mode){
        try{
            var {result} = await TN.routeAnalyst(this.traditionalNaviId,mode);
            return result;
        }catch (e){
            console.log(e);
        }
    }

    /**
     * 设置路径规划的起点。
     * @memberOf TraditionalNavi
     * @param x - 起点经度坐标（度）。
     * @param y - 起点纬度坐标(度)。
     * @param map - 导航所属地图对象
     * @returns {Promise.<void>}
     */
    async setStartPoint(x,y,map){
        try{
            await TN.setStartPoint(this.traditionalNaviId,x,y,map.mapId);
        }catch (e){
            console.log(e);
        }
    }

    /**
     * 设置路径规划的终点。
     * @memberOf TraditionalNavi
     * @param x - 终点经度坐标（度）。
     * @param y - 终点纬度坐标(度)。
     * @param map - 导航所属地图对象
     * @returns {Promise.<void>}
     */
    async setDestinationPoint(x,y,map){
        try{
            await TN.setDestinationPoint(this.traditionalNaviId,x,y,map.mapId);
        }catch (e){
            console.log(e);
        }
    }

    /**
     * 开始导航。
     * 引导状态的值为[0，1，2]，其分别表示执行真实导航、执行模拟导航、执行定位点巡航。
     * @memberOf TraditionalNavi
     * @param status  - 引导状态。
     * @returns {Promise.<void>}
     */
    async startGuide(status){
        try{
            var {guiding} = await TN.startGuide(this.traditionalNaviId,status);
            return guiding;
        }catch (e){
            console.log(e);
        }
    }
}