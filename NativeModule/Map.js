/**
 * Created by will on 2016/6/17.
 */
import { NativeModules } from 'react-native';
let M = NativeModules.JSMap;
import Layers from './Layers.js';
import Point2D from './Point2D.js';
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

    async getLayers(){
        try{
            var {layersId} = await M.getLayers(this.mapId);
            var layers = new Layers();
            layers.layersId = layersId;
            return layers;
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
            var {point2DId} = await M.pixelToMap(this.mapId,point.pointId);
            var point2D = new Point2D();
            point2D.point2DId = point2DId;
            return point2D;
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