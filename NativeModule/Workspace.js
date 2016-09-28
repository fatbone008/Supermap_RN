/**
 * Created by will on 2016/6/17.
 */
import { NativeModules } from 'react-native';
let W = NativeModules.JSWorkspace;
import DS from './Datasources.js';
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

    //获取datasources对象，带着datasourcesId属性
    async getDatasources(){
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