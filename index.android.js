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
    Image,
    TouchableHighlight,
    View
} from 'react-native';
import Workspace from './NativeModule/Workspace.js';
import WorkspaceConnectionInfo from './NativeModule/WorkspaceConnectionInfo';
import ServerMapView from './NativeModule/components/SMMapViewUI.js';
import MapView from './NativeModule/MapView.js';
import Point from './NativeModule/Point.js';
import CallOut from './NativeModule/CallOut.js';


class GeometryInfo extends Component {
    state = {
        setStartPoint: false,
        setDestPoint: false,
        callouts:[{uri:require('./NativeModule/resource/startpoint.png'),top:300,left:150,mapX:4500,mapY:-3500}
            // {uri:require('./NativeModule/resource/destpoint.png'),top:150,left:100},
            // {uri:require('./NativeModule/resource/startpoint.png'),top:30,left:50},
        ],
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
            var WorkspaceConnectionInfoModule = new WorkspaceConnectionInfo();

            //加载工作空间等一系列打开地图的操作
            (async function () {
                try {
                    this.workspace = await workspaceModule.createObj();

                    this.workspaceConnectionInfo = await WorkspaceConnectionInfoModule.createJSObj();
                    await this.workspaceConnectionInfo.setType(Workspace.SMWU);
                    await this.workspaceConnectionInfo.setServer("/SuperMap/data/Changchun.smwu");

                    await this.workspace.open(this.workspaceConnectionInfo);
                    this.maps = await this.workspace.getMaps();

                    this.mapControl = await this.mapView.getMapControl();
                    this.map = await this.mapControl.getMap();

                    await this.map.setWorkspace(this.workspace);
                    var mapName = await this.maps.get(0);

                    await this.map.open(mapName);
                    await this.map.refresh();


                    this.layers = await this.map.getLayers();
                    var layer = await this.layers.get(1);
                    var dataset = await layer.getDataset();
                    var datasetVector = await dataset.toDatasetVector();
                    console.log("DataQuery:datasetVectorId_" + datasetVector.datasetVectorId);
                    var result = await datasetVector.query({hasGeometry:true,size:10,batch:3});
                    console.log("DataQuery:" + JSON.stringify(result));
                } catch (e) {
                    console.error(e);
                }
            }).bind(this)();
        } catch (e) {
            console.error(e);
        }
    }


    // 导航测试
    render() {
        return (
            <View style={styles.container}>
              <ServerMapView ref="mapView" callouts={this.state.callouts}
                             addCalloutByLongPress={true} onGetInstance={this._onGetInstance}/>

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
    buttonGroup:{
        flexDirection:'row',
        justifyContent: 'space-between',
        flexWrap:'wrap',
    },
    geoButton:{
        width:80,
        height:40,
        backgroundColor:'#008800',
        margin:5,
        padding:5,
        justifyContent: 'center',
        alignItems: 'center',
        opacity:.7,
        borderRadius: 5,
    },
});

AppRegistry.registerComponent('GeometryInfo', () => GeometryInfo);
