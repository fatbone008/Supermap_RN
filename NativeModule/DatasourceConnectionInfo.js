/**
 * Created by will on 2016/6/17.
 */
import {NativeModules} from 'react-native';
let DCI = NativeModules.JSDatasourceConnectionInfo;

export default class DatasourceConnectionInfo{
    async createObj(){
        try{
            var {datasourceConnectionInfoId} = await DCI.createObj();
            var datasourceConnectionInfo = new DatasourceConnectionInfo();
            datasourceConnectionInfo.datasourceConnectionInfoId=datasourceConnectionInfoId;
            return datasourceConnectionInfo;
        }catch (e){
            console.error(e);
        }
    }

    // constructor = async function(){
    //     try {
    //         var {datasourceConnectionInfoId} = await DCI.createObj();
    //         this.datasourceConnectionInfoId = datasourceConnectionInfoId;
    //     }catch(e){
    //         console.error(e);
    //     }
    //
    // }

    async setServer(url){
        try{
            await DCI.setServer(this.datasourceConnectionInfoId,url);
        }catch(e){
            console.error(e);
        }
    }

    async setEngineType(engineType){
        try{
            await DCI.setEngineType(this.datasourceConnectionInfoId,engineType);
        }catch(e){
            console.error(e);
        }
    }

    async setAlias(alias){
        try{
            await DCI.setAlias(this.datasourceConnectionInfoId,alias);
        }catch(e){
            console.error(e);
        }
    }
}