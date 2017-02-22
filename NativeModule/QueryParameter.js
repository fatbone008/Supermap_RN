import {NativeModules} from 'react-native';
let QP = NativeModules.JSQueryParameter;
const NAME_QUERYPARAMETER = "QueryParameter";

export default class QueryParameter {
    /**
     * 查询模式参数
     * @type {{CONTAIN: number, CROSS: number, DISJOINT: number, IDENTITY: number, NONE: number, INTERSECT: number, TOUCH: number, OVERLAP: number, WITHIN: number}}
     */
    static QUERYMODE = {
        CONTAIN:7,
        CROSS:5,
        DISJOINT:1,
        IDENTITY:0,
        NONE:-1,
        INTERSECT:2,
        TOUCH:3,
        OVERLAP:4,
        WITHIN:6,
    };

    async createObj(){
        try{
            var {queryParameterId} = await QP.createObj();
            var queryParameter = new QueryParameter();
            queryParameter.queryParameterId = queryParameterId;
            //Return records in batches. by default, 20 records
            //first batch would be return(initial with "1")
            queryParameter.size = 10;
            queryParameter.batch = 1;
            return queryParameter;
        }catch (e){
            console.error(e);
        }
    }

    async setAttributeFilter (attributeFilter){
        try{
            typeof attributeFilter == "string" &&
                await QP.setAttributeFilter(this.queryParameterId,attributeFilter);
        }catch (e){
            console.error(e);
        }
    }

    async setGroupBy(groups){
        try{
            if(testArray(groups)){
                await QP.setGroupBy(this.queryParameterId,groups);
            }
        }catch (e){
            console.error(e);
        }
    }

    async setHasGeometry(has){
        try{
            await QP.setHasGeometry(this.queryParameterId,has);
        }catch (e){
            console.error(e);
        }
    }

    async setResultFields(fields){
        try{
            if(testArray(fields)){
                await QP.setResultFields(this.queryParameterId,fields);
            }
        }catch (e){
            console.error(e);
        }
    }

    async setOrderBy(fields){
        try{
            if(testArray(fields)){
                await QP.setOrderBy(this.queryParameterId,fields);
            }
        }catch (e){
            console.error(e);
        }
    }

    async setSpatialQueryObject(geometry){
        try{
            await QP.setSpatialQueryObject(this.queryParameterId,geometry.geometryId);
        }catch (e){
            console.error(e);
        }
    }

    async setSpatialQueryMode(mode){
        try{
            console.log("QueryParameter:" + mode);
            typeof mode == "number" && await QP.setSpatialQueryMode(this.queryParameterId,mode);
        }catch (e){
            console.error(e);
        }
    }
}

function testArray(arr) {
    if(!(arr instanceof Array)){
        console.error(NAME_QUERYPARAMETER + ":only accept a array as the args.");
        return false;
    }else if(arr.length < 1){
        console.error(NAME_QUERYPARAMETER + ":this is an empty array.");
        return false;
    }else{
        return true;
    }
}