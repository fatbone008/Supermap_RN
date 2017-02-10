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

export default class Map{
    async setWorkspace(workspace){
        try{
            await M.setWorkspace(this.mapId,workspace.workspaceId);
        }catch(e){
            console.error(e);
        }
    }

    async refresh(){
        try{
            await M.refresh(this.mapId);
        }catch(e){
            console.error(e);
        }
    }

    async getLayer(layerIndex){
        try{
            var layer = new Layer();
            if(typeof index == "string"){
                var {layerId} = await L.getByName(this.layersId,index);
            }else{
                var {layerId} = await L.get(this.layersId,index);
            }
            layer.layerId = layerId;
            return layer;
        }catch(e){
            console.error(e);
        }
    }

    async addDataset(dataset,addToHead){
        try{
            await M.addDataset(this.mapId,dataset.datasetId,addToHead);
        }catch(e){
            console.error(e);
        }
    }

    //deprecated
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

    async getLayersCount(){
        try{
            var {count} = await M.getLayersCount(this.mapId);
            return count;
        }catch(e){
            console.error(e);
        }
    }

    async open(mapName){
        try{
            await M.open(this.mapId,mapName);
        }catch(e){
            console.error(e);
        }
    }

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

    async setCenter(point2D){
        try{
            await M.setCenter(this.mapId,point2D.point2DId);
        }catch(e){
            console.error(e);
        }
    }

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
}