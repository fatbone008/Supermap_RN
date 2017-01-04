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
  ListView,
  TouchableHighlight,
  View
} from 'react-native';

//import ServerMapView from './ServerMapView.js';
import ServerMapView from './NativeModule/components/SMMapViewUI.js';
import  Workspace from './NativeModule/Workspace.js';
import DatasourceConnectionInfo from './NativeModule/DatasourceConnectionInfo.js';
import WorkspaceConnectionInfo from './NativeModule/WorkspaceConnectionInfo.js';
import  MapView from './NativeModule/MapView.js';
import  LocationManger from './NativeModule/LocationManager.js';
import  Point2D from './NativeModule/Point2D.js';
import  CallOut from './NativeModule/CallOut.js';
import  Layers from './NativeModule/Layers.js';
import  Rectangle2D from './NativeModule/Rectangle2D.js';
import CursorType from './NativeModule/CursorType.js';
import Selection from './NativeModule/Selection.js';
import GeoStyle from './NativeModule/GeoStyle.js';
import Size2D from './NativeModule/Size2D.js';



//var workspace = null;
//var mapControl = null;
//var map = null;
//var Navigation2Obj = null;
//var startPointCache = null;
//var destPointCache = null;
//var CallOutModule = null;
//
//var currentPolygon = null;

export default class GeometryInfo extends Component {
  //初始化数据
  constructor(props) {
    super(props);
    var ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
    this.state = {
    dataSource: ds.cloneWithRows(['row 1', 'row 2']),
    isList: false,
    selectText:"-- 选择图层 --",
    };
  }
  
  //  _onGetIds=(id)=>{
  //    console.log("_onGetIds:");
  //    console.log(JSON.stringify(id.mapViewId));
  //    this.mapViewId=id.mapViewId;
  //    this._addMap();
  //  }
  
  _onGetInstance = (mapView) => {
    this.mapView = mapView;
    this._addMap();
  }
  
  
  render() {
    return (
            <View style={styles.container}>
            <ServerMapView ref="mapView" style={styles.map} onGetInstance={this._onGetInstance}/>
            <View style={styles.controlPanel}>
            <TouchableHighlight style={styles.layer_button} onPress={this._openListView}>
            <Text style={styles.button_text}>{this.state.selectText}</Text>
            </TouchableHighlight>
            {this.state.isList &&
            <ListView style={styles.listView} dataSource={this.state.dataSource} renderRow={(rowData) => <Text style={styles.button_text}
            onPress={()=>this._layerSelected(rowData)}>{rowData}</Text>}
            />
            }
            </View>
            {!this.state.isList &&
            <TouchableHighlight style={styles.polygonDrawing_button} onPress={this._draw}>
            <Text style={styles.button_text}>绘制查询区域</Text>
            </TouchableHighlight>
            }
            {!this.state.isList &&
            <TouchableHighlight style={styles.endDrawing_button} onPress={this._drawEnd}>
            <Text style={styles.button_text}>结束绘制</Text>
            </TouchableHighlight>
            }
            <TouchableHighlight style={styles.search2_button} onPress={this._search2}>
            <Text style={styles.button_text}>绘制查询</Text>
            </TouchableHighlight>
            <TouchableHighlight style={styles.search_button} onPress={this._search}>
            <Text  style={styles.button_text}>查询</Text>
            </TouchableHighlight>
            </View>
            );
  }
  
  _addMap=()=>{
    //    console.log('start initialize map'+new Date().getTime());
    //    //
    //    //获得视图
    //    var mapView = new MapView();
    //    mapView.mapViewId = this.mapViewId;
    //    console.log('1* mapView id:'+mapView.mapViewId);
    //
    //    //创建workspace模块对象
    var workspaceModule = new Workspace();
    var WorkspaceConnectionInfoModule = new WorkspaceConnectionInfo();
    //    var DatasourceConnectionInfoModule = new DatasourceConnectionInfo();
    //
    //    var point2DModule = new Point2D();
    //    var CallOutModule = new CallOut();
    //
    //    //加载工作空间等一系列打开地图的操作
    (async function () {
     
     this.workspace = await workspaceModule.createObj();
     this.mapControl = await this.mapView.getMapControl();
     this.map = await this.mapControl.getMap();
     await this.map.setWorkspace(this.workspace);
     
     var workspaceConnectionInfo = await WorkspaceConnectionInfoModule.createJSObj();
     
     await workspaceConnectionInfo.setType(9);//smwu
     await workspaceConnectionInfo.setServer("+/Documents/World.smwu");
     
     await this.workspace.open(workspaceConnectionInfo);
     var maps = await this.workspace.getMaps();
     var mapName = await maps.get(0);
     
     await this.map.setWorkspace(this.workspace);
     await this.map.open(mapName);
     await this.map.refresh();
     
     //获取图层名称
     //     var layers = await map.getLayers();
     //     this.layers = layers;
     //     var nCount = await layers.getCount();
     //
     //     var layerName = [];
     //     var layer;
     //     var name;
     //
     //     for (var i = 0; i < nCount; i++) {
     //     layer = await layers.get(i);
     //     name = await layer.getName(i);
     //     layerName[i] = name;
     //     }
     //
     //     this.setState({
     //                   dataSource: this.state.dataSource.cloneWithRows(layerName),
     //                   });
     
     }).bind(this)();
    
    
    
  }
  
  
  //button openListView
  _openListView = () => {
    this.setState({
                  isList: !this.state.isList,
                  })
  }
  
  //选择图层
  _layerSelected = (text) => {
    this.setState({
                  selectText:text,
                  isList:false,
                  hasLayerName:true,
                  });
  }
  
  //获取查询范围
  _getBounds = async () => {
    var RectangleObj = new Rectangle2D();
    //    var pointObj = new Point();
    //    var point1 = await pointObj.createObj(0,1000);
    //    var point2 = await pointObj.createObj(1000,0);
    var ptRightTop = await map.pixelToMap(0,375);
    var ptLeftBottom = await map.pixelToMap(667,0);
    this.bounds = await RectangleObj.createObj(ptRightTop,ptLeftBottom);
  }
  
  //点击搜索button
  _search = async () => {
    if(this.state.selectText == "-- 选择图层 --"){
      return;
    }
    try{
      let layer = await this.layers.get(this.state.selectText);
      console.log("layer Id:"+layer.layerId);
      
      
      let dataset = await layer.getDataset();
      console.log("dataset Id:"+dataset.datasetId);
      let datasetVector = await dataset.toDatasetVector();    //转成DatasetVector
      console.log("datasetVectorId:"+datasetVector.datasetVectorId);
      await this._getBounds();//*************getBounds************
      console.log("bounds Id:" + this.bounds.rectangle2DId);
      
      let recordset = await datasetVector.query(this.bounds,3);//*********CursorType.STATIC
      console.log("recordset Id:"+recordset.recordsetId);
      this.drawingLayer = layer;
      
      var selectedFeature = await this.drawingLayer.getSelection();
      console.log("selectedFeature Id:" + selectedFeature.selectionId);
      
      await selectedFeature.clear();
      
      let count = await recordset.getRecordCount();
      console.log("count:"+count);
      if(count<1){
        //ToastAndroid.show('找不出来！！T.T', ToastAndroid.LONG);
        await this.map.refresh();
        await recordset.dispose();
        return ;
      }else{
        //ToastAndroid.show('找到'+count+'个！', ToastAndroid.LONG);
      }
      
      let selection = await layer.getSelection();
      console.log("selectionId:"+selection.selectionId);
      await selection.fromRecordset(recordset);
      
      await this._setGeoStyle();
      
      await selection.setStyle(this.geoStyle);
      
      let geometry = await recordset.getGeometry();
      console.log("geometryId:"+geometry.geometryId);
      
      let innerPoint = await geometry.getInnerPoint();
      console.log("innerPoint Id:" + innerPoint.point2DId);
      
      await map.setCenter(innerPoint);
      await map.refresh();
    }catch(e){
      console.error(e);
    }
  }
  
  
  //设置查询结果样式
  _setGeoStyle = async () => {
    var geoStyleFac = new GeoStyle();
    var size2DFac = new Size2D();
    var size2D = await size2DFac.createObj(5,5);
    
    this.geoStyle = await geoStyleFac.createObj();
    console.log("geoStyle Id:" + this.geoStyle.geoStyleId);
    
    await this.geoStyle.setLineColor(0,0,222);
    await this.geoStyle.setLineSymbolID(0);
    await this.geoStyle.setLineWidth(0.5);
    await this.geoStyle.setMarkerSymbolID(351);
    await this.geoStyle.setMarkerSize(size2D);
    await this.geoStyle.setFillForeColor(244,50,50);
    await this.geoStyle.setFillOpaqueRate(70);
  }
  
  _search2 = async () =>{
    if(this.state.selectText == "-- 选择图层 --"){
      return;
    }
    try{
      let layer = await this.layers.get(this.state.selectText);
      console.log("layer Id:"+layer.layerId);
      
      
      let dataset = await layer.getDataset();
      console.log("dataset Id:"+dataset.datasetId);
      let datasetVector = await dataset.toDatasetVector();    //转成DatasetVector
      console.log("datasetVectorId:"+datasetVector.datasetVectorId);
      
      let recordset = await datasetVector.queryWithGeometry(currentPolygon,0,3);//*********CursorType.STATIC
      console.log("recordset Id:"+recordset.recordsetId);
      this.drawingLayer = layer;
      
      var selectedFeature = await this.drawingLayer.getSelection();
      console.log("selectedFeature Id:" + selectedFeature.selectionId);
      
      await selectedFeature.clear();
      
      let count = await recordset.getRecordCount();
      console.log("count:"+count);
      if(count<1){
        //ToastAndroid.show('找不出来！！T.T', ToastAndroid.LONG);
        await this.map.refresh();
        await recordset.dispose();
        return ;
      }else{
        //ToastAndroid.show('找到'+count+'个！', ToastAndroid.LONG);
      }
      
      let selection = await layer.getSelection();
      console.log("selectionId:"+selection.selectionId);
      await selection.fromRecordset(recordset);
      
      await this._setGeoStyle();
      
      await selection.setStyle(this.geoStyle);
      
      let geometry = await recordset.getGeometry();
      console.log("geometryId:"+geometry.geometryId);
      
      let innerPoint = await currentPolygon.getInnerPoint();
      console.log("innerPoint Id:" + innerPoint.point2DId);
      
      await map.setCenter(innerPoint);
      await map.refresh();
    }catch(e){
      console.error(e);
    }
    //    await mapControl.setAction(1);
  }
  
  _draw = async () =>{
    await mapControl.setAction(1005);
  }
  
  _drawEnd = async () =>{
    currentPolygon = await mapControl.getCurrentGeometry();
    await mapControl.setAction(0);
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
                                 listView: {
                                 backgroundColor: '#333333',
                                 width: 250,
                                 },
                                 layer_button: {
                                 borderRadius: 5,
                                 backgroundColor: '#333333',
                                 height: 30,
                                 width: 250,
                                 padding: 5,
                                 marginBottom:5,
                                 },
                                 button_text: {
                                 textAlign: 'center',
                                 color: '#ffffff',
                                 padding:4,
                                 },
                                 controlPanel:{
                                 position:'absolute',
                                 top:18,
                                 left:10,
                                 opacity:.6,
                                 },
                                 search_button:{
                                 position:'absolute',
                                 top:18,
                                 right:10,
                                 height:30,
                                 opacity:.5,
                                 borderRadius: 5,
                                 backgroundColor: '#333333',
                                 padding: 5,
                                 },
                                 search2_button:{
                                 position:'absolute',
                                 top:54,
                                 right:10,
                                 height:30,
                                 opacity:.5,
                                 borderRadius: 5,
                                 backgroundColor: '#333333',
                                 padding: 5,
                                 },
                                 polygonDrawing_button:{
                                 position:'absolute',
                                 top:54,
                                 left:10,
                                 height:30,
                                 opacity:.5,
                                 borderRadius: 5,
                                 backgroundColor: '#333333',
                                 padding: 5,
                                 },
                                 endDrawing_button:{
                                 position:'absolute',
                                 top:54,
                                 left:170,
                                 height:30,
                                 opacity:.5,
                                 borderRadius: 5,
                                 backgroundColor: '#333333',
                                 padding: 5,
                                 }
                                 });

AppRegistry.registerComponent('GeometryInfo', () => GeometryInfo);
