/**
 * Created by will on 2016/6/17.
 */
const LONGPRESS_EVENT = "com.supermap.RN.JSMapcontrol.long_press_event";

import { NativeModules,DeviceEventEmitter } from 'react-native';
let MC = NativeModules.JSMapControl;
import Map from './Map.js';
import Navigation2 from './IndustryNavi.js';
import TraditionalNavi from './TraditionalNavi';
import GeoPoint from './GeoPoint.js';
import GeoRegion from './GeoRegion.js';
import GeoLine from './GeoLine.js';
import Geometry from './Geometry.js';

/**
 * @class MapControl
 * @property {object} ACTION - PAN:地图漫游。
 VERTEXADD:在可编辑图层中为对象添加节点。
 VERTEXDELETE:在可编辑图层中为对象删除节点。
 SELECT:在对象上点击，选择对象。
 VERTEXEDIT:在可编辑图层中编辑对象的节点。
 CREATEPOINT:在可编辑图层上点击式绘点。
 CREATEPOLYLINE:在可编辑图层中点击式绘直线。
 CREATEPOLYGON:在可编辑图层中点击式绘多边形。
 */
export default class MapControl{

    /**
     * 返回在地图控件中显示的地图对象。
     * @memberOf MapControl
     * @returns {Promise.<Map>}
     */
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

    /**
     * 设置地图控件中地图的当前操作状态。
     * @memberOf MapControl
     * @param {number} actionType
     * @returns {Promise.<void>}
     */
    async setAction(actionType){
        try{
            await MC.setAction(this.mapControlId,actionType);
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 提交操作，对于采集而言，该接口将把采集的新几何对象写入到数据集，对于编辑，则是更新数据集中的正在编辑的对象。
     * @memberOf MapControl
     * @returns {Promise.<Promise|*|{phasedRegistrationNames}>}
     */
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
     * @memberOf MapControl
     * @param {function} actionChange 编辑行为变更函数，回调事件参数：e:newAction,e:oldAction
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
     * @memberOf MapControl
     * @param {object} events - 传入一个对象作为参数，该对象可以包含两个属性：longPressHandler和scrollHandler。两个属性的值均为function类型，分部作为长按与滚动监听事件的处理函数。
     * @returns {Promise.<void>}
     */
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

            // console.log('MapControl.js:----------------------------------');
            if(handlers){
                await MC.setGestureDetector(this.mapControlId);
                // console.log("GestrueDetector listening!");
            }else{
                throw new Error("setGestureDetector need callback functions as first two argument!");
            }
        }catch (e){
            console.error(e);
        }
    }

    /**
     *  监听地图参数变化，分别由边界变化sizeChanged,比例尺变化scaleChanged,角度变化angleChanged,中心店变化boundsChanged。
     * @memberOf MapControl
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

    /**
     * 地图刷新监听器
     * @memberOf MapControl
     * @param {function} callback - 刷新处理回调函数
     * @returns {Promise.<void>}
     */
    async setRefreshListener(callback){
        try{
            var success = await MC.setRefreshListener(this.mapControlId);
            console.log("MapControl:test result:",success);
            if(success){
                DeviceEventEmitter.addListener("com.supermap.RN.JSMapcontrol.refresh_event",function (e) {
                    // console.log("MapControl:监听到地图刷新");
                    if(typeof callback == 'function'){
                        callback(e);
                    }else{
                        console.error("Please set a callback function as the first argument.");
                    }
                });
            }
        }catch(e){
            console.error(e);
        }
    }

    /**
     * 获得当前Geometry几何对象
     * @memberOf MapControl
     * @returns {Promise.<GeoPoint|GeoLine|GeoRegion|Geometry>}
     */
    async getCurrentGeometry(){
        try{
            var {geometryId,geoType} = await MC.getCurrentGeometry(this.mapControlId);

            if(geoType == "GeoPoint"){
                var geoPoint = new GeoPoint();
                geoPoint.geoPointId = geometryId;
            }else if(geoType == "GeoRegion"){
                var geoRegion = new GeoRegion();
                geoRegion.geoRegionId = geometryId;
            }else if(geoType == "GeoLine"){
                var geoLine = new GeoLine();
                geoLine.geoLineId = geometryId;
            }else{
                var geometry = new Geometry();
                geometry.geometryId = geometryId;
            }
            return geoPoint || geoLine || geoRegion || geometry;
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 获取导航控件
     * @memberOf MapControl
     * @returns {Promise.<Navigation2>}
     */
    async getIndustryNavi(){
        try{
            var {navigation2Id} = await MC.getNavigation2(this.mapControlId);
            var navigation2 = new Navigation2();
            navigation2.navigation2Id = navigation2Id;
            return navigation2;
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 设置横竖屏切换监听器。
     * @memberOf MapControl
     * @param {object} events - 传入一个对象作为参数，该对象可以包含两个属性：toHorizontalScreen和toVerticalScreen。两个属性的值均为function类型，分部作为横屏与竖屏监听事件的处理函数。
     * @returns {Promise.<void>}
     */
    async  setConfigurationChangedListener(events){
        try{
            var success = await MC. setConfigurationChangedListener();
            if(!success) return ;

            DeviceEventEmitter.addListener('com.supermap.RN.JSMapControl.to_horizontal_screen',function (e) {
                if(typeof events.toHorizontal_screen == 'function'){
                    events.toHorizontalScreen();
                }else{
                    console.error("Please set a callback to the property 'toHorizontalScreen' in the first argument.");
                }
            });
            DeviceEventEmitter.addListener('com.supermap.RN.JSMapControl.to_verticalscreen',function (e) {
                if(typeof events.toVerticalScreen == 'function'){
                    events.toVerticalScreen();
                }else{
                    console.error("Please set a callback to the property 'toVerticalScreen' in the first argument.");
                }
            });

        }catch (e){
            console.error(e);
        }
    }

    /**
     * 获得传统导航控件
     * @memberOf MapControl
     * @returns {Promise.<TraditionalNavi>}
     */
    async getTraditionalNavi(){
        try{
            var {traditionNaviId} = await MC.getIndustryNavi(this.mapControlId);
            var traditionNavi= new TraditionalNavi();
            traditionNavi.traditionNaviId = traditionNaviId;
            return traditionNavi;
        }catch (e){
            console.error(e);
        }
    }
}

MapControl.ACTION = {
    PAN:1,
    VERTEXADD:55,
    VERTEXDELETE:56,
    SELECT:8,
    VERTEXEDIT:54,
    CREATEPOINT:16,
    CREATEPOLYLINE:17,
    CREATEPOLYGON:27,
}