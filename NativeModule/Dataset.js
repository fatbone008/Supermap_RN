/**
 * Created by will on 2016/6/17.
 */
import {NativeModules} from 'react-native';
let D = NativeModules.JSDataset;
import Datasource from './Datasource.js';
import DatasetVector from './DatasetVector.js';
import PrjCoordSys from './PrjCoordSys.js';

/**
 * @class Dataset - 所有数据集类型（如矢量数据集，栅格数据集等）的基类。提供各数据集共有的方法和事件。
 * 数据集一般为存储在一起的相关数据的集合；根据数据类型的不同，分为矢量数据集和栅格数据集和影像数据集，以及为了处理特定问题而设计的如拓扑数据集，网络数据集等。数据集是 GIS 数据组织的最小单位。其中矢量数据集是由同种类型空间要素组成的集合，所以也可以称为要素集。根据要素的空间特征的不同，矢量数据集又分为点数据集，线数据集，面数据集等，各矢量数据集是空间特征和性质相同而组织在一起的数据的集合。而栅格数据集由像元阵列组成，在表现要素上比矢量数据集欠缺，但是可以很好的表现空间现象的位置关系。光栅数据集包括影像数据集和栅格数据集。
 *
 * 在 SuperMap 中有十八种类型的数据集，但目前版本支持的数据集主要有点数据集，线数据集，面数据集，文本数据集，纯属性表数据集和影像数据集。
 * @property {number} TYPE.TABULAR - 纯属性数据集。
 * @property {number} TYPE.POINT - 点数据集。
 * @property {number} TYPE.LINE - 线数据集。
 * @property {number} TYPE.REGION - 多边形数据集。
 * @property {number} TYPE.TEXT - 文本数据集。
 * @property {number} TYPE.IMAGE - 影像数据集。
 * @property {number} TYPE.CAD - 复合数据集。
 * @property {number} TYPE.NETWORK - 网络数据集。
 * @property {number} TYPE.NETWORK3D - 三维网络数据集。
 * @property {number} TYPE.NdfVector -
 * @property {number} TYPE.GRID - 栅格数据集。
 * @property {number} TYPE.WMS - WMS数据集。
 * @property {number} TYPE.WCS - WCS数据集。
 * @property {number} TYPE.WFS - WFS数据集。
 * @property {number} TYPE.POINT3D - 三维点数据集。
 * @property {number} TYPE.LINE3D - 三维线数据集。
 * @property {number} TYPE.REGION3D - 三维面数据集。
 * @property {number} TYPE.DEM -
 */
export default class Dataset{

    /**
     * 转成DatasetVector对象
     * @memberOf Dataset
     * @returns {Promise.<DatasetVector>}
     */
    async toDatasetVector(){
        try{
            var {datasetVectorId} = await D.toDatasetVector(this.datasetId);
            var datasetVector = new DatasetVector();
            datasetVector.datasetVectorId = datasetVectorId;
            return datasetVector;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 返回数据集的投影信息。
     * 当该数据集的投影采用其所在数据源的投影时，该方法返回 null。
     * @memberOf Dataset
     * @returns {Promise.<PrjCoordSys>}
     */
    async getPrjCoordSys(){
        try{
            var {prjCoordSysId} = await D.getPrjCoordSys(this.datasetId);
            var prjCoordSys = new PrjCoordSys();
            prjCoordSys.prjCoordSysId = prjCoordSysId;
            return prjCoordSys;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 用于打开数据集，准备对数据集进行操作。在数据源连接了数据，即数据源被打开之后，数据集默认是不打开的，如果要对数据集的数据进行修改或其他操作，数据集必须是打开的，否则无法进行操作。可以先使用 isOpen 方法来判断一下数据集是否已经被打开。
     * @memberOf Dataset
     * @returns {boolean}
     */
    async openDataset(){
        try{
            var {opened} = await D.openDataset(this.datasetId);
            return opened;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 判断此数据集是否已经打开。在数据源连接了数据，即数据源被打开之后，数据集默认是不打开数据集的，如果要对数据集的数据进行修改或其他操作，数据集必须是打开的，否则无法进行操作。可以通过该方法来判定数据集是否已被打开。
     * @memberOf Dataset
     * @returns {boolean} - 如果此数据集已经被打开，返回 true；否则返回 false。
     */
    async isopen(){
        try{
            var {opened} = await D.isopen(this.datasetId);
            return opened;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 返回此数据集的类型。目前版本支持的数据集类型包括纯属性表数据集、点数据集、线数据集、面数据集、文本数据集和影像数据集（DatasetImage）。
     * @memberOf Dataset
     * @returns {Dataset.Type}
     */
    async getType(){
        try{
            var {type} = await D.getType(this.datasetId);
            return type;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 返回此数据集对象所属数据源对象。
     * @memberOf Dataset
     * @returns {Promise.<Datasource>}
     */
    async getDatasource(){
        try{
            var {datasourceId} = await D.getDatasource(this.datasetId);
            var datasource = new Datasource();
            datasource.datasourceId = datasourceId;
            return datasource;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 返回此数据集数据存储时的编码方式。对数据集采用压缩编码方式，可以减少数据存储所占用的空间，降低数据传输时的网络负载和服务器的负载。矢量数据集支持的编码方式有Byte，Int16，Int24，Int32，SGL，LZW，DCT，也可以指定为不使用编码方式。光栅数据支持的编码方式有DCT，SGL，LZW 或不使用编码方式。具体请参见EncodeType类型。
     * @memberOf Dataset
     * @returns {Promise.<Promise.type>}
     */
    async getEncodeType(){
        try{
            var {type} = await D.getEncodeType(this.datasetId);
            return type;
        }catch(e){
            console.error(e);
        }
    }
}

Dataset.TYPE = {
    TABULAR:0,
    POINT:1,
    LINE:3,
    REGION:5,
    TEXT:7,
    IMAGE:81,
    CAD:149,
    NETWORK:4,
    NETWORK3D:205,
    NdfVector:500,
    GRID:83,
    WMS:86,
    WCS:87,
    WFS:151,
    POINT3D:101,
    LINE3D:103,
    REGION3D:105,
    DEM:84
};