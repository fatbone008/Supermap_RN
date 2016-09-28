import {NativeModules} from 'react-native';
let T = NativeModules.JSTrack;
import Dataset from './Dataset.js';
import Datasets from './Datasets.js';

export default class Track {
    async createObj(){
        try{
            var {_trackId_} = await T.createObj();
            var track = new Track();
            track._trackId_ = _trackId_;
            return track;
        }catch (e){
            console.error(e);
        }
    }

    async createDataset(datasource,name){
        try{
            var {datasetId} = await T.createDataset(this._trackId_,datasourceId,name);
            var dataset = new Dataset();
            dataset.datasetId = datasetId;
            return dataset;
        }catch (e){
            console.error(e);
        }
    }

    async getCustomLocation(){
        try{
            var {customLocation} = await T.getCustomLocation(track._trackId_);
            return customLocation;
        }catch (e){
            console.error(e);
        }
    }

    async getDataset(){
        try{
            var {datasetId} = await T.getDataset(this._trackId_);
            var dataset = new Dataset();
            dataset.datasetId = datasetId;
            return dataset;
        }catch (e){
            console.error(e);
        }
    }

    async getDistanceInterval(){
        try{
            var {distanceInterval} = await T.getDistanceInterval(track._trackId_);
            return distanceInterval;
        }catch (e){
            console.error(e);
        }
    }

    async getMatchDatasets(){
        try{
            var {datasetsId} = await T.getMatchDatasets(this._trackId_);
            var datasets = new Datasets();
            datasets.datasetsId = datasetsId;
            return datasets;
        }catch (e){
            console.error(e);
        }
    }

    async getTimeInterval(){
        try{
            var {timeInterval} = await T.getTimeInterval(track._trackId_);
            return timeInterval;
        }catch (e){
            console.error(e);
        }
    }

    async isSpeedDirectionEnable(){
        try{
            var {isSpeedDirectionEnable} = await T.isSpeedDirectionEnable(track._trackId_);
            return isSpeedDirectionEnable;
        }catch (e){
            console.error(e);
        }
    }

    async setCustomLocation(bCustomLocation){
        try{
            await T.setCustomLocation(track._trackId_,bCustomLocation);
        }catch (e){
            console.error(e);
        }
    }

    async setDataset(dataset){
        try{
            await T.setDataset(track._trackId_,dataset.datasetId);
        }catch (e){
            console.error(e);
        }
    }

    async setDistanceInterval(interval){
        try{
            await T.setDistanceInterval(track._trackId_,interval);
        }catch (e){
            console.error(e);
        }
    }

    async setGPSData(jsonGpsData){
        try{
            await T.setGPSData(track._trackId_,jsonGpsData);
        }catch (e){
            console.error(e);
        }
    }

    async setMatchDatasets(datsets){
        try{
            await T.setMatchDatasets(track._trackId_,datsets.datasetId);
        }catch (e){
            console.error(e);
        }
    }

    async setSpeedDirectionEnable(speedDirectionEnable){
        try{
            await T.setSpeedDirectionEnable(track._trackId_,speedDirectionEnable);
        }catch (e){
            console.error(e);
        }
    }

    async setTimeInterval(timeInterval){
        try{
            await T.setTimeInterval(track._trackId_,timeInterval);
        }catch (e){
            console.error(e);
        }
    }

    async startTrack(){
        try{
            await T.startTrack(track._trackId_);
        }catch (e){
            console.error(e);
        }
    }

    async stopTrack(){
        try{
            await T.stopTrack(track._trackId_);
        }catch (e){
            console.error(e);
        }
    }
}