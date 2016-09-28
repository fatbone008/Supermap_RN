/**
 * Created by will on 2016/6/17.
 */
import {NativeModules} from 'react-native';
let D = NativeModules.JSDatasource;
import Datasets from './Datasets';

export default class Datasource{
    async getDatasets(){
        try{
            var {datasetsId} = await D.getDatasets(this.datasourceId);
            var datasets = new Datasets();
            datasets.datasetsId = datasetsId;

            return datasets;
        }catch(e){
            console.error(e);
        }
    }
}