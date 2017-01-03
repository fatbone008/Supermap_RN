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
import Workspace from './NativeModule/Workspace.js';
import WorkspaceConnectionInfo from './NativeModule/WorkspaceConnectionInfo';
import ServerMapView from './NativeModule/components/SMMapViewUI.js';
import DatasourceConnectionInfo from './NativeModule/DatasourceConnectionInfo.js';


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
