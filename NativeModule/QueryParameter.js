import {NativeModules} from 'react-native';
let QP = NativeModules.JSQueryParameter;
const NAME_QUERYPARAMETER = "QueryParameter";

/**
 * @class QueryParameter - 查询参数类。
 *
 * 用于描述一个条件查询的限制条件，如所包含的 SQL 语句，游标方式等。
 *
 * 条件查询，是查询满足一定条件的所有要素的记录，其查询得到的结果是记录集。查询参数类是用来设置条件查询的查询条件从而得到记录集。SQL 查询，又称属性查询，即通过构建包含属性字段、运算符号和数值的 SQL 条件语句来选择记录，从而得到记录集。
 */
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

    /**
     * 创建一个QueryParameter对象
     * @memberOf QueryParameter
     * @returns {Promise.<QueryParameter>}
     */
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

    /**
     * 设置属性过滤器
     * @memberOf QueryParameter
     * @param {string} attributeFilter - 被过滤的字段
     * @returns {Promise.<void>}
     */
    async setAttributeFilter (attributeFilter){
        try{
            typeof attributeFilter == "string" &&
                await QP.setAttributeFilter(this.queryParameterId,attributeFilter);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 设置Group聚合项
     * @memberOf QueryParameter
     * @param {string[]} groups - 传入要聚合的属性
     * @returns {Promise.<void>}
     */
    async setGroupBy(groups){
        try{
            if(testArray(groups)){
                await QP.setGroupBy(this.queryParameterId,groups);
            }
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 设置查询结果是否包含矢量
     * @memberOf QueryParameter
     * @param {boolean} has - 是否包含矢量
     * @returns {Promise.<void>}
     */
    async setHasGeometry(has){
        try{
            await QP.setHasGeometry(this.queryParameterId,has);
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 设置结果集字段
     * @memberOf QueryParameter
     * @param {string[]} fields - 设置返回的字段名称
     * @returns {Promise.<void>}
     */
    async setResultFields(fields){
        try{
            if(testArray(fields)){
                await QP.setResultFields(this.queryParameterId,fields);
            }
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 设置返回结果排序
     * @memberOf QueryParameter
     * @param {string[]} fields - 设置排序的字段名称
     * @returns {Promise.<void>}
     */
    async setOrderBy(fields){
        try{
            if(testArray(fields)){
                await QP.setOrderBy(this.queryParameterId,fields);
            }
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 设置空间查询对象
     * @memberOf QueryParameter
     * @param {object} geometry - 传入空间查询对象
     * @returns {Promise.<void>}
     */
    async setSpatialQueryObject(geometry){
        try{
            await QP.setSpatialQueryObject(this.queryParameterId,geometry.geometryId);
        }catch (e){
            console.error(e);
        }
    }

    /**
     *设置空间查询对象模式
     * @memberOf QueryParameter
     * @param mode - 传入空间查询模式
     * @returns {Promise.<void>}
     */
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