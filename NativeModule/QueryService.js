import {NativeModules} from 'react-native';
let SS = NativeModules.JSQueryService;

/**
 * @class QueryService
 * @param QueryMode.SqlQuery -  查询模式：SQL查询
 * @param QueryMode.FindNearest -  查询模式：最近地物查询
 * @param QueryMode.DistanceQuery - 查询模式：距离查询
 * @param QueryMode.SpatialQuery - 查询模式：空间查询
 * @param QueryMode.BoundsQuery - 范围查询：范围查询
 *
 */
export default class QueryService {

    /**
     * 根据查询路径创建一个QueryService对象
     * @param url
     * @returns {Promise.<QueryService>}
     */
    async createObj(url){
        try{
            var {_queryServiceId_} = await SS.createObj(url);
            var queryService = new QueryService();
            queryService._queryServiceId_ = _queryServiceId_;
            return queryService;
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 对Rest地图服务进行查询。
     * @param serviceQueryParameter - 查询参数{@ling QueryParameter}。
     * @param mode - 查询模式QueryMode
     * @returns {Promise.<*>}
     */
    async query(serviceQueryParameter,mode){
        try{
            await SS.query(this._queryServiceId_,
                serviceQueryParameter._serviceQueryParameterId_,mode);
            return queryService;
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 对Rest地图服务进行查询。
     * @param url - 地图服务的全地址 ：如：http://192.168.120.1:8090/iserver/services/map-world/rest/maps/World。
     * @param serviceQueryParameter - 查询参数。
     * @param mode - 查询模式。
     * @returns {Promise.<*>}
     */
    async queryByUrl(url,serviceQueryParameter,mode){
        try{
            await SS.queryByUrl(this._queryServiceId_,url,
                serviceQueryParameter._serviceQueryParameterId_,mode);
            return queryService;
        }catch (e){
            console.error(e);
        }
    }
}

QueryService.QueryMode = {
    SqlQuery:0,
    FindNearest:1,
    DistanceQuery:2,
    SpatialQuery:3,
    BoundsQuery:4,
}