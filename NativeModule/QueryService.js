import {NativeModules} from 'react-native';
let SS = NativeModules.JSQueryService;

export default class QueryService {
    static QueryMode = {
        SqlQuery:0,
        FindNearest:1,
        DistanceQuery:2,
        SpatialQuery:3,
        BoundsQuery:4,
    }

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

    async query(serviceQueryParameter,mode){
        try{
            await SS.query(this._queryServiceId_,
                serviceQueryParameter._serviceQueryParameterId_,mode);
            return queryService;
        }catch (e){
            console.error(e);
        }
    }

    async queryByUrl(serviceQueryParameter,mode){
        try{
            await SS.queryByUrl(this._queryServiceId_,url,
                serviceQueryParameter._serviceQueryParameterId_,mode);
            return queryService;
        }catch (e){
            console.error(e);
        }
    }
}