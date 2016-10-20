/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow 图层管理、比例尺、图例控件测试案例。
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';
import Workspace from './NativeModule/Workspace.js';
import WorkspaceConnectionInfo from './NativeModule/WorkspaceConnectionInfo';
import ServerMapView from './NativeModule/SMMapViewUI.js';
import LegendView from './NativeModule/SMLegendViewUI.js';
import LayerListView from './NativeModule/SMLayerListViewUI.js';
import ScaleView from './NativeModule/SMScaleViewUI.js';

class GeometryInfo extends Component {
  state = {
    mapId:false,
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

          this.setState({
            bindMapId:this.map.mapId,
          });
        } catch (e) {
          console.error(e);
        }
      }).bind(this)();
    } catch (e) {
      console.error(e);
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <ServerMapView ref="mapView" style={styles.map} onGetInstance={this._onGetInstance}/>
        {/*{ this.state.mapId && <LegendView style={styles.legend} mapId={this.state.mapId}/> }*/}
        {/*{ this.state.mapId && <ScaleView style={styles.legend} mapId={this.state.mapId}/> }*/}
          { this.state.bindMapId && <LayerListView style={styles.legend} bindMapId={this.state.bindMapId}/> }
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
  legend:{
    flex:.5,
    backgroundColor:'#ffffff',
    alignSelf: 'stretch',
  }
});

AppRegistry.registerComponent('GeometryInfo', () => GeometryInfo);
