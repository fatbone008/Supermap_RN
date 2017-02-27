/**
 * Created by will on 2016/6/17.
 */
import {NativeModules} from 'react-native';
let DCI = NativeModules.JSDatasourceConnectionInfo;

/**
 * @class DatasourceConnectionInfo
 */
export default class DatasourceConnectionInfo{
    /**
     * 创建一个DatasourceConnectionInfo实例
     * @memberOf DatasourceConnectionInfo
     * @returns {Promise.<DatasourceConnectionInfo>}
     */
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

    /**
     * 设置数据库服务器名或文件名。
     * 对于 UDB 文件，为其文件的名称，其中包括路径名称和文件的后缀名。特别地，此处的路径为绝对路径。
     * @memberOf DatasourceConnectionInfo
     * @param {string} url - 数据库服务器名或文件名。
     * @returns {Promise.<void>}
     */
    async setServer(url){
        try{
            await DCI.setServer(this.datasourceConnectionInfoId,url);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 返回数据源连接的引擎类型。对不同类型的空间数据源，需要不同的空间数据库引擎来存储和管理，对文件型数据源，即 UDB 数据源，需要 SDX+ for UDB，引擎类型为 UDB。目前版本支持的引擎类型包括 UDB 引擎（UDB），影像只读引擎（IMAGEPLUGINS）。
     * @memberOf DatasourceConnectionInfo
     * @param {string} engineType - 数据源连接的引擎类型。BaiDu，BingMaps，GoogleMaps，OGC，OpenGLCache，OpenStreetMaps，Rest，SuperMapCloud，UDB
     * @returns {Promise.<void>}
     */
    async setEngineType(engineType){
        try{
            await DCI.setEngineType(this.datasourceConnectionInfoId,engineType);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 设置数据源别名
     * @memberOf DatasourceConnectionInfo
     * @param {string} alias - 别名
     * @returns {Promise.<void>}
     */
    async setAlias(alias){
        try{
            await DCI.setAlias(this.datasourceConnectionInfoId,alias);
        }catch(e){
            console.error(e);
        }
    }
}
