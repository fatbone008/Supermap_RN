/**
 * Created by will on 2016/6/17.
 */
import { NativeModules } from 'react-native';
let W = NativeModules.JSWorkspace;
import DS from './Datasources.js';
import Ds from './Datasource.js';
import Maps from './Maps.js';

export default class Workspace{
    static SMWU = 9;
    static SXWU = 8;
    static SMW = 5;
    static SXW = 4;
    static DEFAULT = 1;

    // 创建新workspace实例
    async createObj(){
        try{
            var {workspaceId} = await W.createObj();
            var workspace = new Workspace();
            workspace.workspaceId = workspaceId;
            return workspace;
        }catch(e){
            console.error(e);
        }
    }

    //deprecated
    async getDatasources(){
        console.warn("Workspace.js:getDatasources() function has been deprecated. If you want to get datasource , please call the getDatasource() function");
        try {
            var {datasourcesId} = await W.getDatasources(this.workspaceId);
            console.log("datasourcesId:"+datasourcesId);
            var ds = new DS();
            ds.datasourcesId = datasourcesId;
            return ds;
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 通过数据源链接信息打开数据源
     * @param datasourceConnectionInfo 数据源链接信息
     * @returns {Promise.<Datasource>}
     */
    async openDatasourceConnectionInfo(datasourceConnectionInfo){
        try {
            var {datasourceId} = await W.openDatasourceConnectionInfo(this.workspaceId,datasourceConnectionInfo.datasourceConnectionInfoId);
            var ds = new Ds();
            ds.datasourceId = datasourceId;
            return ds;
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 通过序号或者名字（别名）获取数据集
     * @param index
     * @returns {Promise.<Datasource>}
     */
    async getDatasource(index){
        try{
            var datasource = new Ds();
            if(typeof index != 'string'){
                //get datasource through index.
                var {datasourceId} = await W.getDatasource(this.workspaceId,index);
            }else{
                //get datasource through datasource name(Alias).
                var {datasourceId} = await W.getDatasourceByName(this.workspaceId,index);
            }
            datasource.datasourceId = datasourceId;

            return datasource;
        }
    }

    async open(workspaceConnectionInfo){
        try{
            var {isOpen} = await W.open(this.workspaceId,workspaceConnectionInfo.workspaceConnectionInfoId);
            console.log('workspace open connectionInfo:'+isOpen);
            return isOpen;
        }catch(e){
            console.error(e);
        }
    }

    async getMaps(){
        try{
            var {mapsId} = await W.getMaps(this.workspaceId);
            var maps = new Maps();
            maps.mapsId = mapsId;
            return maps;
        }catch(e){
            console.error(e);
        }
    }

    async getMapName(mapIndex){
        try{
            var {mapName} = await W.getMapName(this.workspaceId,mapIndex);
            return mapName;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 打开网络数据集
     * @param server 服务器路径
     * @param engineType 引擎类型
     * @param driver 驱动
     * @returns {Promise.<void>}
     */
    async openDatasource(server,engineType,driver){
        try{
            await W.openDatasource(this.workspaceId,server,engineType,driver);
        }catch(e){
            console.error(e);
        }
    }

    async openWMSDatasource(server,engineType,driver,version,visibleLayers,webBox,webCoordinate){
        try{
            await W.openWMSDatasource(this.workspaceId,server,engineType,driver,
                version,visibleLayers,webBox,webCoordinate);
        }catch(e){
            console.error(e);
        }
    }
}