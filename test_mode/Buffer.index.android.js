/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow 导航测试案例
 */

import React, {Component} from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    ListView,
    Image,
    TouchableHighlight,
    View,
    TextInput
} from 'react-native';
import Workspace from '../NativeModule/Workspace.js';
import WorkspaceConnectionInfo from '../NativeModule/WorkspaceConnectionInfo';
import ServerMapView from '../NativeModule/components/SMMapViewUI.js';
import DatasourceConnectionInfo from '../NativeModule/DatasourceConnectionInfo.js';
import Dataset from '../NativeModule/Dataset.js';
import CursorType from '../NativeModule/CursorType.js';
import DatasetVectorInfo from '../NativeModule/DatasetVectorInfo.js';
import BufferAnalystParameter from '../NativeModule/BufferAnalystParameter.js';
import BufferAnalystGeometry from '../NativeModule/BufferAnalystGeometry.js';
import GeoStyle from '../NativeModule/GeoStyle.js';
import Size2D from '../NativeModule/Size2D.js';


class GeometryInfo extends Component {
    state = {
        text:'5',
    }

    _onGetInstance = (mapView) => {
        this.mapView = mapView;
        this._addMap();
    }

    /**
     * 初始化地图
     * @private
     */
    _addMap = () => {
        try {
            //创建workspace模块对象
            var workspaceModule = new Workspace();
            // var WorkspaceConnectionInfoModule = new WorkspaceConnectionInfo();
            var datasourceConnectionInfoFac = new DatasourceConnectionInfo();


            //加载工作空间等一系列打开地图的操作
            (async function () {
                try {
                    this.workspace = await workspaceModule.createObj();

                    this.workspaceConnectionInfo = await WorkspaceConnectionInfoModule.createJSObj();
                    await this.workspaceConnectionInfo.setType(Workspace.SMWU);
                    await this.workspaceConnectionInfo.setServer("/SampleData/City/Changchun.smwu");

                    await this.workspace.open(this.workspaceConnectionInfo);
                    var mapName = await this.workspace.getMapName(0);

                    this.mapControl = await this.mapView.getMapControl();
                    this.map = await this.mapControl.getMap();

                    await this.map.setWorkspace(this.workspace);

                    await this.map.open(mapName);
                    await this.map.refresh();

                    await setSelectedLayer();
                } catch (e) {
                    console.error(e);
                }
            }).bind(this)();
        } catch (e) {
            console.error(e);
        }
    }


    setSelectedLayer = async () => {
        try{
            console.log('Initial the selectable layer');
            this.roadLayer = await this.map.getLayer('RoadLine1@changchun');

            var layer;
            var count = await this.map.getLayersCount();

            for(let i = 0; i < count;i++){
                layer = await this.map.getLayer(i);
                await layer.setSelectable(false);
            }

            await this.roadLayer.setSelectable(true);
        }catch (e) {
            console.error(e);
        }
    }

    _pickALine = async () => {
        try{
            await this.mapControl.setAction(MapControl.ACTION.SELECT);
            console.log('开始选择');
        } catch (e) {
            console.error(e);
        }
    }

    _pan = async () => {
        try{
            await this.mapControl.setAction(MapControl.ACTION.PAN);
        } catch (e) {
            console.error(e);
        }
    }

    _search = async () => {
        var dviModule = new DatasetVectorInfo();
        var bapModule = new BufferAnalystParameter();
        var geoStyleModule = new GeoStyle();
        try{
            // 获取选择的道路矢量
            var selectedRoad = await this.roadLayer.getSelection();
            if(selectedRoad != null){
                this.queryRecordset =await selectedRoad.toRecordset();
            }else{
                throw new Error('please pick a road in the map.');
            }

            this.trackingLayer = await this.map.getTrackingLayer();
            await this.trackingLayer.clear();
            var count = await this.queryRecordset.getRecordCount();
            if(this.queryRecordset != null && count != 0){
                this.datasources = await this.workspace.getDatasources();
                this.datasource = await this.datasources.get(0);
                this.datasets = await this.datasource.getDatasets();

                //检测一个名称是否与某个数据集名称重复，不重复的话，返回这个名称
                //一般在新建一个数据集，命名时检测名称是否重复
                let dtName = await this.datasets.getAvailableDatasetName("da");

                console.log(dtName);
                //数据集矢量配置信息，传入要创建的数据集名称和类型
                this.datasetVectorInfo = await dviModule.createObjByNameType(dtName,Dataset.TYPE.REGION);
                this.datasetVector = await this.datasets.create(this.datasetVectorInfo);

                var rr = await this.datasetVector.getRecordset(false,CursorType.DYNAMIC);
                console.log("rr:" + JSON.stringify(rr));

                while(!(await this.queryRecordset.isEOF())){
                    // 配置缓冲分析参数
                    var bufferAnalystParams = await bapModule.createObj();
                    await bufferAnalystParams.setEndType(BufferAnalystParameter.ENDTYPE.ROUND);
                    // 缓冲距离
                    var distance = parseFloat(this.state.text);
                    await bufferAnalystParams.setLeftDistance(distance);
                    await bufferAnalystParams.setRightDistance(distance);

                    var geoForBuffer = await this.queryRecordset.getGeometry();
                    var queryDataset = await this.queryRecordset.getDataset();
                    var projection = await queryDataset.getPrjCoordSys();

                    this.geometryBuffer = await BufferAnalystGeometry.createBuffer(geoForBuffer,bufferAnalystParams,projection);

                    var sizeModule = new Size2D();
                    var size2D = await sizeModule.createObj(5,5);
                    var style = await geoStyleModule.createObj();
                    await style.setLineColor(50,244,50);
                    await style.setLineSymbolID(0);
                    await style.setLineWidth(0.5);
                    await style.setMarkerSymbolID(351);
                    await style.setMarkerSize(size2D);
                    await style.setFillForeColor(244,50,50);
                    await style.setFillOpaqueRate(70);
                    await this.geometryBuffer.setStyle(style);

                    await this.trackingLayer.add(this.geometryBuffer,"");
                    await this.queryRecordset.moveNext();
                    await rr.addNew(this.geometryBuffer);
                    await rr.update();
                }
                await rr.dispose();
            }else{
                alert("没有选中对象");
            }

            await this.map.refresh();
        } catch (e) {
            console.error(e);
        }
    }

    // 测试查询
    render() {
        return (
            <View style={styles.container}>
                <ServerMapView ref="mapView" onGetInstance={this._onGetInstance}/>
                <View style={styles.contorlPane}>
                    <Text style={styles.textLabel}>半径:</Text>
                    <View style={styles.inputWrapper}>
                        <TextInput
                            style={styles.textInputor}
                            onChangeText={(text) => this.setState({text})}
                            value={this.state.text}
                            underlineColorAndroid={'transparent'}
                            keyboardType={'numeric'}
                        />
                    </View>
                    <TouchableHighlight style={styles.imageWrapper} onPress={this._pickALine}>
                        <Image
                            source={require('./img/select.png')}
                        />
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.imageWrapper} onPress={this._pan}>
                        <Image
                            source={require('./img/pan.png')}
                        />
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.imageWrapper} onPress={this._search}>
                        <Text style={styles.searchButton}>查  询</Text>
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
        backgroundColor: '#333333',
    },
    contorlPane: {
        opacity: .7,
        backgroundColor: 'black',
        position: 'absolute',
        top: 0,
        left: 0,
        right: 0,
        height: 50,
        flexDirection: 'row',
        alignItems: 'center',
    },
    textLabel: {
        padding: 5,
        color: '#ffffff',
    },
    inputWrapper: {
        flex: 1,
        borderRadius: 10,
        backgroundColor: 'white',
        justifyContent: 'center',
        // padding:5,
    },
    textInputor: {
        height: 40,
        color: 'black',
        textAlignVertical:'center',
    },
    imageWrapper: {
        flexDirection: 'row',
        justifyContent: 'center',
        width: 40,
        height: 40,
        marginLeft: 5,
        alignItems: 'center',
        backgroundColor: '#666666',
        borderRadius: 10,
    },
    searchButton:{
        color:'white',
    }
});

AppRegistry.registerComponent('GeometryInfo', () => GeometryInfo);
