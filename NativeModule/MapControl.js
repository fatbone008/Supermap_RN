/**
 * Created by will on 2016/6/17.
 */
const LONGPRESS_EVENT = "com.supermap.RN.JSMapcontrol.long_press_event";
import { NativeModules,DeviceEventEmitter } from 'react-native';
let MC = NativeModules.JSMapControl;
import Map from './Map.js';
import Navigation2 from './Navigation2.js';
export default class MapControl{
    static ACTION = {
        PAN:1,
        VERTEXADD:55,
        VERTEXDELETE:56,
        SELECT:8,
        VERTEXEDIT:54,
        CREATEPOINT:16,
        CREATEPOLYLINE:17,
        CREATEPOLYGON:27,
    }

    async getMap(){
        try{
            var {mapId} =await MC.getMap(this.mapControlId);
            var map = new Map();
            map.mapId = mapId;
            return map;
        }catch(e){
            console.error(e);
        }
    }

    async setAction(actionType){
        try{
            await MC.setAction(this.mapControlId,actionType);
        }catch(e){
            console.error(e);
        }
    }

    async submit(){
        try{
            var submited = await MC.submit(this.mapControlId);
            return submited;
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 监听编辑行为的变更事件
     * @param actionChange 编辑行为变更函数，回调事件参数：e:newAction,e:oldAction
     */
    async addActionChangedListener(actionChange){
        try{
            DeviceEventEmitter.addListener('ActionChange', function(e) {
                actionChange && actionChange(e);
            });
            if(typeof actionChange == "function"){
                await MC.addActionChangedListener(this.mapControlId);
            }else{
                throw new Error("addActionChangedListener need a callback function as first argument!");
            }
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 监听导航事件
     * @param longPressHandler 长按事件处理函数,回调事件参数: e:x,e:y，即长按点的屏幕坐标。
     * @param scrollHandler 滑动事件处理函数
     */
    // async setGestureDetector(longPressHandler,scrollHandler){
    async setGestureDetector(handlers){
        try{
            if(!handlers) return;

            if(typeof handlers.longPressHandler === "function"){
                DeviceEventEmitter.addListener(LONGPRESS_EVENT,function (e) {
                    // longPressHandler && longPressHandler(e);
                    handlers.longPressHandler(e);
                });
            }

            if(typeof handlers.scrollHandler === "function"){
                DeviceEventEmitter.addListener('NavigationScroll',function (e) {
                    scrollHandler && scrollHandler(e);
                });
            }

            console.log('MapControl.js:----------------------------------');
            if(handlers){
                await MC.setGestureDetector(this.mapControlId);
                console.log("GestrueDetector listening!");
            }else{
                throw new Error("setGestureDetector need callback functions as first two argument!");
            }
        }catch (e){
            console.error(e);
        }
    }

    /**
     *  监听地图参数变化，分别由边界变化sizeChanged,比例尺变化scaleChanged,角度变化angleChanged,中心店变化boundsChanged。
     * @param events 该对象有下面四个函数类型的属性分别处理四种监听事件
     * {boundsChanged,scaleChanged,angleChanged,sizeChanged}
     */
    async setMapParamChangedListener(events){
        try{
            boundsChanged = events.boundsChanged;
            scaleChanged = events.scaleChanged;
            angleChanged = events.angleChanged;
            sizeChanged = events.sizeChanged;

            var success = await MC.setMapParamChangedListener(this.mapControlId);
            console.debug("Listening map parameters changed.");

            if(!success) return;

            DeviceEventEmitter.addListener('Supermap.MapControl.MapParamChanged.BoundsChanged',function (e) {
                if(typeof boundsChanged == 'function'){
                    events.boundsChanged(e);
                }else{
                    console.error("Please set a callback to the property 'boundsChanged' in the first argument.");
                }
            });
            DeviceEventEmitter.addListener('Supermap.MapControl.MapParamChanged.ScaleChanged',function (e) {
                if(typeof events.scaleChanged == 'function'){
                    events.scaleChanged(e);
                }else{
                    console.error("Please set a callback to the property 'scaleChanged' in the first argument.");
                }
            });
            DeviceEventEmitter.addListener('Supermap.MapControl.MapParamChanged.AngleChanged',function (e) {
                if(typeof events.angleChanged == 'function' ){
                    events.angleChanged(e);
                }else {
                    console.error("Please set a callback to the property 'angleChanged' in the first argument.");
                }
            });
            DeviceEventEmitter.addListener('Supermap.MapControl.MapParamChanged.SizeChanged',function (e) {
                if(typeof events.sizeChanged == 'function' ){
                    events.sizeChanged(e);
                }else {
                    console.error("Please set a callback to the property 'sizeChanged' in the first argument.");
                }
            });
        }catch(e){
            console.error(e);
        }
    }

    async setRefreshListener(callback){
        try{
            var success = await MC.setRefreshListener(this.mapControlId);
            console.log("MapControl:test result:",success);
            if(success){
                DeviceEventEmitter.addListener("com.supermap.RN.JSMapcontrol.refresh_event",function (e) {
                    // console.log("MapControl:监听到地图刷新");
                    if(typeof callback == 'function'){
                        callback(e);0
                    }else{
                        console.error("Please set a callback function as the first argument.");
                    }
                });
            }
        }catch(e){
            console.error(e);
        }
    }

    async getNavigation2(){
        try{
            var {navigation2Id} = await MC.getNavigation2(this.mapControlId);
            var navigation2 = new Navigation2();
            navigation2.navigation2Id = navigation2Id;
            return navigation2;
        }catch (e){
            console.error(e);
        }
    }
}