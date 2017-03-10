/**
 * Created by will on 2016/7/12.
 */
import {NativeModules} from 'react-native';
let N = NativeModules.JSNavigation2;
import Dataset from './Dataset.js';
import GeoLine from './GeoLine';

/**
 * @class Navigation2 - 行业导航类，提供基于拓扑路网的路径分析与导引。
 */
export default class Navigation2{
    /**
     * 设置路径可见
     * @memberOf Navigation2
     * @param {boolean} visible - 设置分析路径是否可见。
     * @returns {Promise.<void>}
     */
    async setPathVisible(visible){
        try{
            await N.setPathVisible(this.navigation2Id,visible);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 设置用于分析的网络数据集。必选。
     * @memberOf Navigation2
     * @param {object} datasetVector - 网络数据集。
     * @returns {Promise.<void>}
     */
    async setNetworkDataset(datasetVector){
            try{
                await N.setNetworkDataset(this.navigation2Id,datasetVector.datasetVectorId);
            }catch (e){
                console.error(e);
            }
    }

    /**
     * 加载内存文件。
     * @memberOf Navigation2
     * @param {string} path - 内存文件路径。
     * @returns {Promise.<void>}
     */
    async loadModel(path){
        try{
            await N.loadModel(this.navigation2Id,path);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 设置导航起点
     * @memberOf Navigation2
     * @param x - 起点横坐标
     * @param y - 起点纵坐标
     * @returns {Promise.<void>}
     */
    async setStartPoint(x,y,map){
        try{
            console.log("科学计数法？"+ x);
            await N.setStartPoint(this.navigation2Id,x,y,map.mapId);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 设置导航终点
     * @memberOf Navigation2
     * @param x - 终点横坐标
     * @param y - 终点纵坐标
     * @returns {Promise.<void>}
     */
    async setDestinationPoint(x,y,map){
        try{
            await N.setDestinationPoint(this.navigation2Id,x,y,map.mapId);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 路径分析
     * @memberOf Navigation2
     * @returns {Promise.<Promise.boolean>}
     */
    async routeAnalyst(){
        try{
            var {finished} = await N.routeAnalyst(this.navigation2Id);
            return finished;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 开始导航
     * @memberOf Navigation2
     * @param guideMode - 导航模式：1：真实导航；2：模拟导航
     * @returns {Promise.<Promise.boolean>}
     */
    async startGuide(guideMode){
        try{
            var {isGuiding} = await N.startGuide(this.navigation2Id,guideMode);
            return isGuiding;
        }catch(e){
            console.error(e);
        }
    }


    /**
     * 设置转向表数据集。可选。
     * @memberOf Navigation2
     * @param {DatasetVector} value - 转向表数据集。
     * @returns {Promise.<void>}
     */
    async setTurnDataset(value){
        try{
            await N.setTurnDataset(this.navigation2Id,value.datasetVectorId);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 设置高程点数据集。可选。
     * @memberOf Navigation2
     * @param {DatasetVector} value - 转向表数据集。
     * @returns {Promise.<void>}
     */
    async setAltimetricPointDataset(value){
        try{
            await N.setAltimetricPointDataset(this.navigation2Id,value.datasetVectorId);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 获取路径分析线路。
     * @memberOf Navigation2
     * @returns {Promise.<void>}
     */
    async getRoute(){
        try{
            var {geoLineId} = await N.getRoute(this.navigation2Id);
            var geoLine = new GeoLine();
            geoLine.geoLineId = geoLineId;
            return geoLine;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 清除路径分析结果。
     * @memberOf Navigation2
     * @returns {Promise.<GeoLine>}
     */
    async cleanPath(){
        try{
            await N.cleanPath(this.navigation2Id);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 暂停导航
     * @memberOf Navigation2
     * @returns {Promise.<void>}
     */
    async pauseGuide(){
        try{
            await N.pauseGuide(this.navigation2Id);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 继续导航
     * @memberOf Navigation2
     * @returns {Promise.<void>}
     */
    async resumeGuide(){
        try{
            await N.resumeGuide(this.navigation2Id);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 停止引导。
     * @memberOf Navigation2
     * @returns {Promise.<Promise.boolean>}
     */
    async stopGuide(){
        try{
            var {stoped} = await N.stopGuide(this.navigation2Id);
            return stoped
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 判断是否正在进行引导。
     * @memberOf Navigation2
     * @returns {Promise.<Promise.yes>}
     */
    async isGuiding(){
        try{
            var {yes} = await N.isGuiding(this.navigation2Id);
            return yes
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 引导过程中是否允许平移地图。
     * @memberOf Navigation2
     * @param pan
     * @returns {Promise.<void>}
     */
    async enablePanOnGuide(pan){
        try{
            await N.enablePanOnGuide(this.navigation2Id,pan);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 导航中，小车在屏幕中居中显示
     * @memberOf Navigation2
     * @returns {Promise.<void>}
     */
    async locateMap(){
        try{
            await N.locateMap(this.navigation2Id);
        }catch(e){
            console.error(e);
        }
    }
}