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
import MapControl from '../NativeModule/MapControl';
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

                    await this.workspace.open("/SampleData/GeometryInfo/World.smwu");

                    this.mapControl = await this.mapView.getMapControl();
                    console.log("mapControl"+this.mapControl.mapControlId);
                    this.map = await this.mapControl.getMap();

                    await this.map.setWorkspace(this.workspace);
                    var mapName = await this.workspace.getMapName(0);

                    await this.map.open(mapName);
                    await this.map.refresh();

                    var editLayer = await this.map.getLayer(5);
                    console.log(await editLayer.getName());

                    await editLayer.setEditable(true);
                }catch(e){
                    console.error(e);
                }
            }).bind(this)();
        }catch (e){
            console.error(e);
        }
    }

    _point = async () => {
        try{
            await this.mapControl.setAction(MapControl.ACTION.CREATEPOINT);
        }catch (e){
            console.error(e);
        }
    };

    _line = async () => {
        try{
            await this.mapControl.setAction(MapControl.ACTION.CREATEPOLYLINE);
        }catch (e){
            console.error(e);
        }
    };

    _polygon = async () => {
        try{
            await this.mapControl.setAction(MapControl.ACTION.CREATEPOLYGON);
        }catch (e){
            console.error(e);
        }
    };

    _editNode = async () => {
        try{
            console.log('拖动节点');
            await this.mapControl.setAction(MapControl.ACTION.VERTEXEDIT);
        }catch (e){
            console.error(e);
        }
    };

    _addNode = async () => {
        try{
            await this.mapControl.setAction(MapControl.ACTION.VERTEXADD);
        }catch (e){
            console.error(e);
        }
    };

    _deleteNode = async () => {
        try{
            await this.mapControl.setAction(MapControl.ACTION.VERTEXDELETE);
        }catch (e){
            console.error(e);
        }
    };

    _undo = async () => {
        try{
            await this.mapControl.undo();
        }catch (e){
            console.error(e);
        }
    };

    _redo = async () => {
        try{
            await this.mapControl.redo();
        }catch (e){
            console.error(e);
        }
    };

    _submit = async () => {
        try{
            await this.mapControl.submit();
        }catch (e){
            console.error(e);
        }
    };

    _select = async () => {
        try{
            await this.mapControl.setAction(MapControl.ACTION.SELECT);
        }catch (e){
            console.error(e);
        }
    };

    render() {
        return (
            <View style={styles.container}>
                <ServerMapView ref="mapView" style={styles.map} onGetInstance={this._onGetInstance}/>
                <View style={styles.controlPanel}>
                    <TouchableHighlight style={styles.geoButton} onPress={this._point}>
                        <Text style={styles.textLabel}>Point</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.geoButton} onPress={this._line}>
                        <Text style={styles.textLabel}>Line</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.geoButton} onPress={this._polygon}>
                        <Text style={styles.textLabel}>Polygon</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.geoButton} onPress={this._pan}>
                        <Text style={styles.textLabel}>Pan</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.geoButton} onPress={this._undo}>
                        <Text style={styles.textLabel}>Undo</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.geoButton} onPress={this._redo}>
                        <Text style={styles.textLabel}>Redo</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.geoButton} onPress={this._editNode}>
                        <Text style={styles.textLabel}>EditNode</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.geoButton} onPress={this._addNode}>
                        <Text style={styles.textLabel}>AddNode</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.geoButton} onPress={this._deleteNode}>
                        <Text style={styles.textLabel}>DeleteNode</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.geoButton} onPress={this._submit}>
                        <Text style={styles.textLabel}>Submit</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.geoButton} onPress={this._select}>
                        <Text style={styles.textLabel}>Select</Text>
                    </TouchableHighlight>
                </View>
            </View>
        );
    }
}


const styles = StyleSheet.create({
    container: {
        flex: .6,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    map: {
        flex: 1,
        alignSelf: 'stretch',
    },
    controlPanel:{
        flex:.1,
        flexDirection:'row',
        flexWrap:'wrap',
        alignItems:'stretch',
        opacity:.6,
        height:120,
    },
    geoButton:{
        backgroundColor:'#000000',
        margin:5,
        borderRadius: 5,
        flexDirection:'row',
        justifyContent:'center',
        alignItems:'center',
        width:80,
        height:40,
    },
    textLabel:{
        color:'white',
    }
});