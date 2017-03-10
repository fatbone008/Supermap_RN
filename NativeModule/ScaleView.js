import {NativeModules} from 'react-native';
let S = NativeModules.JSScaleView;

export default class ScaleView {
    async createObj(mapControl){
        try{
            var {scaleViewId} = await S.createObjWithMapControlId(mapControl.mapControlId);
            var scaleView = new ScaleView();
            scaleView.scaleViewId = scaleViewId;
            return scaleView;
        }catch (e){
            console.error(e);
        }
    }

    async setLevelEnable(enable){
        try{
            await S.setLevelEnable(this.scaleViewId,enable);
        }catch (e){
            console.error(e);
        }
    }

    async getLevelEnable(){
        try{
            var {levelEnable} = await S.getLevelEnable(this.scaleViewId);
            return levelEnable;
        }catch (e){
            console.error(e);
        }
    }
    
    async setShowEnable(enable){
        try{
            await S.setShowEnable(this.scaleViewId,enable);
        }catch (e){
            console.error(e);
        }
    }
    
    async getShowEnable(){
        try{
            var {showEnable} = await S.getShowEnable(this.scaleViewId);
            return showEnable;
        }catch (e){
            console.error(e);
        }
    }

    async setxOffset(x){
        try{
            await S.setxOffset(this.scaleViewId,x);
        }catch (e){
            console.error(e);
        }
    }

    async setyOffset(y){
        try{
            await S.setyOffset(this.scaleViewId,y);
        }catch (e){
            console.error(e);
        }
    }
}
