/**
 * Created by will on 2016/6/17.
 */
import { NativeModules } from 'react-native';
let M = NativeModules.JSMap;
import Layer from './Layer.js';
import Layers from './Layers.js';
import Point2D from './Point2D.js';
import Point from './Point.js';
import TrackingLayer from './TrackingLayer.js';


/**
 * @class Map
 */
export default class Map{
    /**
     * 设置当前地图所关联的工作空间。地图是对其所关联的工作空间中的数据的显示。
     * @memberOf Map
     * @param {object} workspace
     * @returns {Promise.<void>}
     */
    async setWorkspace(workspace){
        try{
            await M.setWorkspace(this.mapId,workspace.workspaceId);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 重新绘制当前地图。
     * @memberOf Map
     * @returns {Promise.<void>}
     */
    async refresh(){
        try{
            await M.refresh(this.mapId);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 返回此图层集合中指定索引或名称的图层对象。
     * @memberOf Map
     * @param {number | string} layerIndex|layerName - 指定索引或名称
     * @returns {Promise.<Layer>}
     */
    async getLayer(layerIndex){
        try{
            var layer = new Layer();
            if(typeof index == "string"){
                var {layerId} = await M.getLayerByName(this.mapId,layerIndex);
            }else{
                var {layerId} = await M.getLayer(this.mapId,layerIndex);
            }
            layer.layerId = layerId;
            return layer;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 用于把一个数据集添加到此地图作为一个普通图层显示，即创建一个普通图层。
     * @memberOf Map
     * @param {object} dataset
     * @param {boolean} addToHead
     * @returns {Promise.<void>}
     */
    async addDataset(dataset,addToHead){
        try{
            await M.addDataset(this.mapId,dataset.datasetId,addToHead);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 返回当前地图所包含的图层集合对象。
     * @memberOf Map
     * @deprecated
     * @returns {Promise.<Layers>}
     */
    async getLayers(){
        console.warn("Map.js:getLayers() function has been deprecated. If you want to get Layer , please call the getLayer() function");
        try{
            var {layersId} = await M.getLayers(this.mapId);
            var layers = new Layers();
            layers.layersId = layersId;
            return layers;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 返回此图层集合中图层对象的总数。
     * @memberOf Map
     * @returns {Promise.<Promise.count>}
     */
    async getLayersCount(){
        try{
            var {count} = await M.getLayersCount(this.mapId);
            return count;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 打开指定名称的地图。
     * @memberOf Map
     * @param {string} mapName - 地图名称
     * @returns {Promise.<void>}
     */
    async open(mapName){
        try{
            await M.open(this.mapId,mapName);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 将地图中指定点的像素坐标转换为地图坐标。
     * @memberOf Map
     * @param {object} point
     * @returns {Promise.<Point2D>}
     */
    async pixelToMap(point){
        try{
            var {point2DId,x,y} = await M.pixelToMap(this.mapId,point.pointId);
            var point2D = new Point2D();
            point2D.point2DId = point2DId;
            point2D.x = x;
            point2D.y = y;
            return point2D;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 将地图中指定点的地图坐标转换为像素坐标。
     * @memberOf Map
     * @param {object} point2D
     * @returns {Promise.<Point>}
     */
    async mapToPixel(point2D){
        try{
            var {pointId,x,y} = await M.mapToPixel(this.mapId,point2D.point2DId);
            var point = new Point();
            point.pointId = pointId;
            point.x = x;
            point.y = y;
            return point;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 设置当前地图的显示范围的中心点。
     * @memberOf Map
     * @param {object} point2D
     * @returns {Promise.<void>}
     */
    async setCenter(point2D){
        try{
            await M.setCenter(this.mapId,point2D.point2DId);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 返回当前地图的跟踪图层。
     * @memberOf Map
     * @returns {Promise.<TrackingLayer>}
     */
    async getTrackingLayer(){
        try{
            var {trackingLayerId} = await M.getTrackingLayer(this.mapId);
            var trackingLayer = new TrackingLayer();
            trackingLayer.trackingLayerId = trackingLayerId;
            return trackingLayer;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 将当前地图另存为指定名称的地图
     * @memberOf Map
     * @param {string} mapName 地图名称
     * @returns {Promise.<Promise.saved>}
     */
    async saveAs(mapName){
        try{
            var {saved} = await M.saveAs(this.mapId,mapName);
            return saved;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 返回当前地图的空间范围。
     * @memberOf Map
     * @returns {Promise.<Promise.bound>} - 返回Bounds的JSON对象：如{top:0,bottom:0,left:0,right:0,height:0,width:0,center:{x,y}}
     */
    async getBounds(){
        try{
            var {bound} = await M.saveAs(this.mapId);
            return bound;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 返回当前地图的可见范围，也称显示范围。
     * @memberOf Map
     * @returns {Promise.<Promise.bound>} - 返回Bounds的JSON对象：如{top:0,bottom:0,left:0,right:0,height:0,width:0,center:{x,y}}
     */
    async getViewBounds(){
        try{
            var {bound} = await M.saveAs(this.mapId);
            return bound;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 设置当前地图的可见范围，也称显示范围。
     * @memberOf Map
     * @param {object} bounds - Bounds的JSON对象：如{top:0,bottom:0,left:0,right:0,height:0,width:0,center:{x,y}}
     * @returns {Promise.<void>}
     */
    async setViewBounds(bounds){
        try{
            await M.setViewBounds(this.mapId,bounds);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 返回是否允许地图动态投影显示。
     * @memberOf Map
     * @param {object} bounds
     * @returns {Promise.<Promise.is>}
     */
    async isDynamicProjection(){
        try{
            var {is} = await M.isDynamicProjection(this.mapId);
            return is;
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 设置地图是否动态投影显示，以及地图的投影坐标系
     * @memberOf Map
     * @param {boolean} value - 地图是否动态投影
     * @returns {Promise.<void>}
     */
    async setDynamicProjection(value){
        try{
            await M.isDynamicProjection(this.mapId,value);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 获取用于接收地图加载完成的监听器。
     * @memberOf Map
     * @param {function} onMapLoaded - 地图打开并显示完成后触发该方法。
     * @returns {Promise.<void>}
     */
    async setMapLoadedListener(onMapLoaded){
        try{
            var success = await M.setMapLoadedListener(this.mapId);

            if(!success) return ;

            DeviceEventEmitter.addListener("com.supermap.RN.JSMap.map_loaded",function (e) {
                if(typeof onMapLoaded === 'function'){
                    onMapLoaded();
                }else{
                    console.error("Please set a callback function to the first argument.");
                }
            });
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 添加一个用于接收地图打开、关闭事件的监听器。
     * @memberOf Map
     * @param {object} events - 传入一个对象作为参数，该对象可以包含两个属性：mapOpened和mapClosed。两个属性的值均为function类型，分部作为打开地图和关闭地图监听事件的处理函数。例：{"mapOpened":()=>return console.log('map opened'),"mapClosed":()=> console.log('map closed')}
     * @returns {Promise.<void>}
     */
    async setMapOperateListener(events){
        try{
            var success = await M.setMapOperateListener(this.mapId);

            if(!success) return ;

            DeviceEventEmitter.addListener("com.supermap.RN.JSMap.map_opened",function (e) {
                if(typeof events.mapOpened === 'function'){
                    events.mapOpened();
                }else{
                    console.error("Please set a callback to the property 'mapOpened' in the first argument.");
                }
            });

            DeviceEventEmitter.addListener("com.supermap.RN.JSMap.map_closed",function (e) {
                if(typeof events.mapClosed === 'function'){
                    events.mapClosed();
                }else{
                    console.error("Please set a callback to the property 'mapClosed' in the first argument.");
                }
            });
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 将地图平移指定的距离。
     * @memberOf Map
     * @param {double} offsetX - X 方向上的移动距离，单位为坐标单位。
     * @param {double} offsetY - Y 方向上的移动距离，单位为坐标单位。
     * @returns {Promise.<void>}
     */
    async pan(offsetX,offsetY){
        try{
            await M.pan(this.mapId,offsetX,offsetY);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 全幅显示此地图。
     * @memberOf Map
     * @returns {Promise.<void>}
     */
    async viewEntire(){
        try{
            await M.viewEntire(this.mapId);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 将地图放大或缩小指定的比例。
     * @memberOf Map
     * @param {double} ratio - 缩放地图比例，此值不可以为负。
     * @returns {Promise.<void>}
     */
    async zoom(ratio){
        try{
            if(ratio < 0) throw new Error("Ratio can`t be nagative.");
            await M.zoom(this.mapId,ratio);
        }catch(e){
            console.error(e);
        }
    }
}