/**
 * Created by will on 2016/7/12.
 */
import {NativeModules} from 'react-native';
let N = NativeModules.JSNavigation2;
import Dataset from './Dataset.js';

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
}