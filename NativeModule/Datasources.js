/**
 * Created by will on 2016/6/17.
 */
import {NativeModules} from 'react-native';
let D = NativeModules.JSDatasources;
import Datasource from './Datasource.js';

export default class Datasources{
    async open(datasourceConnectionInfo){
        try{
            var {datasourceId} = await D.open(this.datasourcesId,datasourceConnectionInfo.datasourceConnectionInfoId);
            console.log("datasourceId:"+datasourceId);
            var datasource = new Datasource();
            datasource.datasourceId = datasourceId;

            return datasource;
        }catch(e){
            console.error(e);
        }
    }

    async get(index){
        try{
            var datasource = new Datasource();
            if(typeof index != 'string'){
                var {datasourceId} = await D.get(this.datasourcesId,index);
            }else{
                var {datasourceId} = await D.getByName(this.datasourcesId,index);
            }
            datasource.datasourceId = datasourceId;

            return datasource;
        }catch(e){
            console.error(e);
        }
    }
}