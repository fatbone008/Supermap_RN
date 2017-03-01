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
     * @param {object} datasetId - 网络数据集。
     * @returns {Promise.<void>}
     */
    async setNetworkDataset(dataset){

            try{
                await N.setNetworkDataset(this.navigation2Id,dataset.datasetId);
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
            await D.loadModel(this.navigation2Id,path);
        }catch(e){
            console.error(e);
        }
    }
}