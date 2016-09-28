/**
 * Created by will on 2016/7/12.
 */
import {NativeModules} from 'react-native';
let N = NativeModules.JSNavigation2;
import Dataset from './Dataset.js';

export default class Navigation2{
    async setPathVisible(visible){
        try{
            await N.setPathVisible(this.navigation2Id,visible);
        }catch (e){
            console.error(e);
        }
    }

    async setNetworkDataset(datasetId){

            try{
                await N.setNetworkDataset(this.navigation2Id,datasetId);
            }catch (e){
                console.error(e);
            }
    }

    async loadModel(path){
        try{
            await D.loadModel(this.navigation2Id,path);
        }catch(e){
            console.error(e);
        }
    }
}