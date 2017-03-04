/**
 * Created by will on 2017/3/2.
 */
import React, {Component} from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    TouchableHighlight,
    ToastAndroid,
} from 'react-native';
import  Workspace from '../NativeModule/Workspace.js';
import ServerMapView from '../NativeModule/components/SMMapViewUI.js';
import Point from '../NativeModule/Point';
var Utility = require('./../NativeModule/utility/utility');


export default class Navigation2 extends Component{
    state = {
        pointType:0
    };

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

            //加载工作空间等一系列打开地图的操作
            (async function () {
                try{
                    this.workspace = await workspaceModule.createObj();

                    await this.workspace.open("/SampleData/Navigation2Data/navi_beijing.smwu");

                    this.mapControl = await this.mapView.getMapControl();
                    console.log("mapControl"+this.mapControl.mapControlId);
                    this.map = await this.mapControl.getMap();

                    await this.map.setWorkspace(this.workspace);
                    var mapName = await this.workspace.getMapName(0);

                    await this.map.open(mapName);
                    await this.map.refresh();

                    // 监听长按，设置图标
                    await this.mapControl.setGestureDetector({longPressHandler:(e) => {
                        // console.log("长按地图控件，返回坐标：x" + e.x +',y' + e.y);
                        // var callout = null;
                        // if(this.state.pointType === 0){
                        //     (async function () {
                        //         var arr = this.refs['mapView'].state.callouts;
                        //         var mapPoint = await Utility.Point2Map(this.map,e.x,e.y);
                        //         callout = {uri:require('./../NativeModule/resource/startpoint.png'),name:"startpoint",mapX:mapPoint.x,mapY:mapPoint.y}
                        //
                        //         // 防止重复添加起点或终点
                        //         var calloutIndex = null;
                        //         arr.map(function (c,index) {
                        //             if(c.name === callout.name) calloutIndex = index;
                        //         });
                        //         console.log('calloutIndex:' + calloutIndex);
                        //
                        //         if(calloutIndex == null){
                        //             arr.push(callout); //该点尚未存在，则新增
                        //         }else {
                        //             arr[calloutIndex] = callout;  //该点已经存在，则替换
                        //         }
                        //
                        //         console.log('arr:' + arr);
                        //         this.refs['mapView'].setState({
                        //             callouts:arr,
                        //         });
                        //     }).bind(this)();
                        // }
                        (async function () {
                            try{
                                if(this.state.pointType === 0){
                                    this.startpoint = {x:e.x,y:e.y};
                                    var urlAsset = require('./../NativeModule/resource/startpoint.png');
                                    await this._longPressHandler(urlAsset,'startpoint',e.x,e.y);
                                }else if(this.state.pointType === 1){
                                    this.destpoint = {x:e.x,y:e.y};
                                    var urlAsset = require('./../NativeModule/resource/destpoint.png');
                                    await this._longPressHandler(urlAsset,'destpoint',e.x,e.y);
                                }
                            }catch (e){
                                console.error(e);
                            }
                        }).bind(this)();
                    }}) ;

                    // 初始化Navigation2
                    var datasource = await this.workspace.getDatasource("beijing");
                    var roadworkDataset = await datasource.getDataset("RoadNetwork");
                    var roadworkDV = await roadworkDataset.toDatasetVector();

                    this.navigation2 = await this.mapControl.getNavigation2();
                    await this.navigation2.setPathVisible(true);
                    await this.navigation2.setNetworkDataset(roadworkDV);
                    await this.navigation2.loadModel("SampleData/Navigation2Data/netModel.snm");
                }catch(e){
                    console.error(e);
                }
            }).bind(this)();
        }catch (e){
            console.error(e);
        }
    }

    //开启导航并设置起点
    _start = () => {
        this.setState({
            pointType:0,
        });
    }

    _dest = () => {
        this.setState({
            pointType:1,
        })
    }

    /**
     * 长按出图效果
     * @param callout - 图片资源
     * @param x - X坐标
     * @param y - y坐标
     * @returns {Promise.<void>}
     */
     _longPressHandler = async (uriAsset,calloutName,x,y) => {
         try{
             var arr = this.refs['mapView'].state.callouts;
             var mapPoint = await Utility.Point2Map(this.map,x,y);
             var callout = {uri:uriAsset,name:calloutName,mapX:mapPoint.x,mapY:mapPoint.y}

             // 防止重复添加起点或终点
             var calloutIndex = null;
             arr.map(function (c,index) {
                 if(c.name === callout.name) calloutIndex = index;
             });
             console.log('calloutIndex:' + calloutIndex);

             if(calloutIndex == null){
                 arr.push(callout); //该点尚未存在，则新增
             }else {
                 arr[calloutIndex] = callout;  //该点已经存在，则替换
             }

             console.log('arr:' + arr);
             this.refs['mapView'].setState({
                 callouts:arr,
             });
         }catch (e){
             console.error(e);
         }
    }

    render() {
        return (
            <View style={styles.container}>
                <ServerMapView ref="mapView" style={styles.map} onGetInstance={this._onGetInstance}/>
                <View style={styles.controlPanel}>
                    <TouchableHighlight style={styles.geoButton} onPress={this._start}>
                        <Text style={styles.textLabel}>start</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.geoButton} onPress={this._dest}>
                        <Text style={styles.textLabel}>dest</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.geoButton} onPress={this._route}>
                        <Text style={styles.textLabel}>route</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.geoButton} onPress={this._guide}>
                        <Text style={styles.textLabel}>guide</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.geoButton} onPress={this._stop}>
                        <Text style={styles.textLabel}>stop</Text>
                    </TouchableHighlight>
                </View>
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
    map: {
        flex: 1,
        alignSelf: 'stretch',
    },
    controlPanel:{
        flex:1,
        flexDirection:'row',
        alignItems:'stretch',
        position:'absolute',
        left:10,
        right:10,
        bottom:10,
        opacity:.6,
        height:40,
    },
    geoButton:{
        backgroundColor:'#000000',
        margin:5,
        borderRadius: 5,
        flex:1,
        flexDirection:'row',
        justifyContent:'center',
        alignItems:'center',
    },
    textLabel:{
        color:'white',
    }
});