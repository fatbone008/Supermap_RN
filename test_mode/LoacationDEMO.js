/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    TouchableHighlight,
    ToastAndroid,
} from 'react-native';
import  Workspace from '../NativeModule/Workspace.js';
import WorkspaceConnectionInfo from '../NativeModule/WorkspaceConnectionInfo';
import ServerMapView from '../NativeModule/components/SMMapViewUI.js';
import GeoStyle from '../NativeModule/GeoStyle.js';
import Size2D from '../NativeModule/Size2D.js';
import GeoPoint from  '../NativeModule/GeoPoint.js';
import Point2D from '../NativeModule/Point2D.js';

export default class LocationDEMO extends Component {
    state = {
        position:'unknown',
    }

    _onGetInstance = (mapView) => {
        this.mapView=mapView;
        this._addMap();
    }

    /**
     * 初始化地图
     * @private
     */
    _addMap = () => {
        try{
            console.log('start initialize map'+new Date().getTime());

            //创建workspace模块对象
            var workspaceModule = new Workspace();
            var WorkspaceConnectionInfoModule = new WorkspaceConnectionInfo();

            //加载工作空间等一系列打开地图的操作
            (async function () {
                try{
                    this.workspace = await workspaceModule.createObj();

                    // this.workspaceConnectionInfo = await WorkspaceConnectionInfoModule.createJSObj();
                    // await this.workspaceConnectionInfo.setType(Workspace.SMWU);
                    // await this.workspaceConnectionInfo.setServer("/SampleData/GeometryInfo/World.smwu");

                    await this.workspace.open("/SampleData/GeometryInfo/World.smwu");
                    this.maps = await this.workspace.getMaps();

                    this.mapControl = await this.mapView.getMapControl();
                    console.log("mapControl"+this.mapControl.mapControlId);
                    this.map = await this.mapControl.getMap();

                    await this.map.setWorkspace(this.workspace);
                    // var mapName = await this.maps.get(0);
                    var mapName = "世界地图_Day";
                    console.log("地图名称：" + mapName);

                    await this.map.open(mapName);
                    await this.map.refresh();
                }catch(e){
                    console.error(e);
                }
            }).bind(this)();
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 获取坐标点
     * @private
     */
    _getGeoPoints = async () => {
        try{
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    console.log("开始定位坐标！");
                    // var initialPosition = JSON.stringify(position);
                    var initialPosition = ""+position.coords.longitude+","+position.coords.latitude;
                    this.setState({
                        position:position,
                    })
                    this._drawPoint(position);
                },
                (error) => console.error(error.message),
                {enableHighAccuracy: true, timeout: 20000, maximumAge: 1000}
            );
        }catch (e){
            console.error(e);
        }
    }

    /**
     * 绘制坐标点，并移动至地图中心
     * @param position RN坐标点对象
     * @private
     */
    _drawPoint = async (position) => {
        var geoStyleFac = new GeoStyle();
        var size2DFac = new Size2D();
        var geoPointFac = new GeoPoint();
        var point2DFac = new Point2D();
        try{
            //设置样式
            var geoStyle = await geoStyleFac.createObj();
            var size2D = await size2DFac.createObj(10,10);
            await geoStyle.setMarkerSize(size2D);
            await geoStyle.setMarkerSymbolID(3);

            // var geoPoint = await geoPointFac.createObj(position.coords.longitude,position.coords.latitude);
            var geoPoint = await geoPointFac.createObj(116.1684684,39.1684357);
            geoPoint.setStyle(geoStyle);

            this.trackingLayer = await this.map.getTrackingLayer();
            await this.trackingLayer.add(geoPoint,"location");

            var label = [{uri:require("./../NativeModule/resource/startpoint.png"),mapX:116.1684684,mapY:39.1684357}];
            this.refs['mapView'].setState({
                callouts:label,
            });

            var x = await geoPoint.getX();
            var y = await geoPoint.getY();
            console.log("x:"+x+",y:"+y);
            var point2D = await point2DFac.createObj(116.1684684,39.1684357);

            await this.map.setCenter(point2D);
            await this.map.refresh();
        }catch (e){
            console.error(e);
        }
    }

    render() {
        return (
            <View style={styles.container}>
                <ServerMapView ref="mapView" style={styles.map} onGetInstance={this._onGetInstance}/>
                <TouchableHighlight style={styles.geoButton} onPress={this._drawPoint}>
                    <Text style={{fontSize:18,color:"white"}}>获取位置</Text>
                </TouchableHighlight>

                {/*<Text>{this.state.position}</Text>*/}
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    map:{
        flex:1,
        alignSelf:'stretch',
    },
    geoButton:{
        alignSelf:'auto',
        height:40,
        backgroundColor:'#008800',
        margin:5,
        justifyContent: 'center',
        alignItems: 'center',
        opacity:.7,
        borderRadius: 5,
    }
});

