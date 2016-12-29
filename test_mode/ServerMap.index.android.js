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
    View
} from 'react-native';
import Workspace from '../NativeModule/Workspace.js';
import WorkspaceConnectionInfo from '../NativeModule/WorkspaceConnectionInfo';
import ServerMapView from '../NativeModule/components/SMMapViewUI.js';
import DatasourceConnectionInfo from '../NativeModule/DatasourceConnectionInfo.js';


class GeometryInfo extends Component {
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

                    var mapControl = await this.mapView.getMapControl();
                    var map = await mapControl.getMap();
                    await map.setWorkspace(this.workspace);
                    //设置在线地图路径
                    var datasourceConnectionInfo = await datasourceConnectionInfoFac.createObj();
                    console.log('datasourceConnectionInfoId:'+datasourceConnectionInfo.datasourceConnectionInfoId);

                    await datasourceConnectionInfo.setServer('http://192.168.12.13:8090/iserver/services/map-china400/rest/maps/China');
                    await datasourceConnectionInfo.setEngineType("Rest");
                    await datasourceConnectionInfo.setAlias("ChinaRest");
                    //添加服务器远程地图
                    var datasource = await this.workspace.openDatasourceConnectionInfo(datasourceConnectionInfo);
                    var dataset = await datasource.getDataset(0);
                    await map.addDataset(dataset,true);

                    await map.refresh();
                } catch (e) {
                    console.error(e);
                }
            }).bind(this)();
        } catch (e) {
            console.error(e);
        }
    }

    _callback = () => {
        console.log("locationManager");
    }

    // 测试查询
    render() {
        return (
            <View style={styles.container}>
                <ServerMapView ref="mapView" onGetInstance={this._onGetInstance}/>

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
