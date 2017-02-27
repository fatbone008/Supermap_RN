/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, {Component} from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    ListView,
    TouchableHighlight,
    ToastAndroid
} from 'react-native';
import  MapView from '../NativeModule/MapView.js';
import  Workspace from '../NativeModule/Workspace.js';
import WorkspaceConnectionInfo from '../NativeModule/WorkspaceConnectionInfo';
import ServerMapView from '../NativeModule/components/SMMapViewUI.js';
import Point from '../NativeModule/Point.js';
import Rectangle2D from '../NativeModule/Rectangle2D.js';
import CursorType from '../NativeModule/CursorType.js';
import GeoStyle from '../NativeModule/GeoStyle.js';
import Size2D from '../NativeModule/Size2D.js';

export default class BoundsQuery extends Component {
    constructor(props) {
        super(props);
        var ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
        this.state = {
            dataSource: ds.cloneWithRows(['row 1', 'row 2']),
            isList: false,
            selectText:"-- 选择图层 --",
        };
    }

    _onGetIds = (id)=> {
        this.mapViewId = id.mapViewId
        console.log(this.mapViewId);
        this._addMap();
    }

    //初始化地图
    _addMap = ()=> {
        try {
            console.log('start initialize map' + new Date().getTime());
            //
            //获得视图对象
            var mapView = new MapView();
            mapView.mapViewId = this.mapViewId;
            this.mapView = mapView;

            //创建workspace模块对象
            var workspaceModule = new Workspace();
            var WorkspaceConnectionInfoModule = new WorkspaceConnectionInfo();

            //加载工作空间等一系列打开地图的操作
            (async function () {
                try {
                    this.workspace = await workspaceModule.createObj();

                    this.workspaceConnectionInfo = await WorkspaceConnectionInfoModule.createJSObj();
                    await this.workspaceConnectionInfo.setType(Workspace.SMWU);
                    await this.workspaceConnectionInfo.setServer("/SampleData/GeometryInfo/World.smwu");

                    await this.workspace.open(this.workspaceConnectionInfo);
                    this.maps = await this.workspace.getMaps();

                    this.mapControl = await mapView.getMapControl();
                    console.log("mapControl" + this.mapControl.mapControlId);
                    this.map = await this.mapControl.getMap();
                    console.log("mapId:" + this.map.mapId);

                    await this.map.setWorkspace(this.workspace);
                    var mapName = await this.maps.get(0);

                    await this.map.open(mapName);
                    await this.map.refresh();

                    this.layers = await this.map.getLayers();
                    console.log("layers Id:" + this.layers.layersId);
                    await this._readLayers(this.layers)
                } catch (e) {
                    console.error(e);
                }
            }).bind(this)();
        } catch (e) {
            console.error(e);
        }
    }

    //获取所有图层名称
    _readLayers = async(layers)=> {
        try {
            var nCount = await layers.getCount();
            console.log("Layers Count:" + nCount);
            var layerName = [];
            var layer;
            var name;

            for (var i = 0; i < nCount; i++) {
                layer = await layers.get(i);
                name = await layer.getName(i);
                layerName[i] = name;
            }
            console.log(layerName.toString());

            this.setState({
                dataSource: this.state.dataSource.cloneWithRows(layerName),
            });
        } catch (e) {
            console.error(e);
        }

    }

    //图层选项表开关
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
        var pointObj = new Point();
        var point1 = await pointObj.createObj(0,1000);
        var point2 = await pointObj.createObj(1000,0);
        var ptRightTop = await this.map.pixelToMap(point1);
        var ptLeftBottom = await this.map.pixelToMap(point2);
        this.bounds = await RectangleObj.createObj(ptRightTop,ptLeftBottom);
    }

    //查询事件
    _search = async () => {
        if(this.state.selectText == "-- 选择图层 --"){
            ToastAndroid.show('请选择图层，亲！', ToastAndroid.LONG);
            return;
        }
        try{
            let layer = await this.layers.get(this.state.selectText);
            console.log("layer Id:"+layer.layerId);


            let dataset = await layer.getDataset();
            console.log("dataset Id:"+dataset.datasetId);
            let datasetVector = await dataset.toDatasetVector();    //转成DatasetVector
            console.log("datasetVectorId:"+datasetVector.datasetVectorId);
            await this._getBounds();
            console.log("bounds Id:" + this.bounds.rectangle2DId);

            let recordset = await datasetVector.query(this.bounds,CursorType.STATIC);
            console.log("recordset Id:"+recordset.recordsetId);
            this.drawingLayer = layer;

            var selectedFeature = await this.drawingLayer.getSelection();
            console.log("selectedFeature Id:" + selectedFeature.selectionId);

            await selectedFeature.clear();

            let count = await recordset.getRecordCount();
            console.log("count:"+count);
            if(count<1){
                ToastAndroid.show('找不出来！！T.T', ToastAndroid.LONG);
                await this.map.refresh();
                await recordset.dispose();
                return ;
            }else{
                ToastAndroid.show('找到'+count+'个！', ToastAndroid.LONG);
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

            await this.map.setCenter(innerPoint);
            await this.map.refresh();
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

    render() {
        return (
            <View style={styles.container}>
                <ServerMapView ref="mapView" style={styles.map} onGetInstance={this._onGetIds}/>

                <View style={styles.controlPanel}>
                    <TouchableHighlight style={styles.layer_button} onPress={this._openListView}>
                        <Text style={styles.button_text}>{this.state.selectText}</Text>
                    </TouchableHighlight>
                    {this.state.isList &&
                    <ListView style={styles.listView}
                              dataSource={this.state.dataSource}
                              renderRow={(rowData) => <Text style={styles.button_text}
                                                            onPress={()=>this._layerSelected(rowData)}>{rowData}</Text>}
                    />
                    }
                </View>

                <TouchableHighlight style={styles.search_button} onPress={this._search}>
                    <Text  style={styles.button_text}>查询</Text>
                </TouchableHighlight>
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
        padding:8,
    },
    controlPanel:{
        position:'absolute',
        top:10,
        left:10,
        opacity:.6,
    },
    search_button:{
        position:'absolute',
        top:10,
        right:10,
        height:30,
        opacity:.5,
        borderRadius: 5,
        backgroundColor: '#333333',
        padding: 5,
    }
});

AppRegistry.registerComponent('BoundsQuery', () => BoundsQuery);
