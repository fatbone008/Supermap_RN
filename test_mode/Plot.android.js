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
import Workspace from './../NativeModule/Workspace.js';
import WorkspaceConnectionInfo from './../NativeModule/WorkspaceConnectionInfo';
import ServerMapView from './../NativeModule/components/SMMapViewUI.js';
import PlotView from "./../NativeModule/components/SMPlotViewUI";

export default class GeometryInfo extends Component {
  state = {
    mapControl:{},
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

      //加载工作空间等一系列打开地图的操作
      (async function () {
        try {
          this.workspace = await workspaceModule.createObj();

          await this.workspace.open("/SampleData/Plot/Workspace/Workspace/SuperMapCloud.smwu");

          this.mapControl = await this.mapView.getMapControl();
          this.map = await this.mapControl.getMap();

          await this.map.setWorkspace(this.workspace);
          var mapName = await this.workspace.getMapName(0);

          await this.map.open(mapName);
          await this.map.refresh();

          this.setState({
              mapControl:this.mapControl,
          });
        } catch (e) {
          console.error(e);
        }
      }).bind(this)();
    } catch (e) {
      console.error(e);
    }
  }

  render = () => {
    return (
      <View style={styles.container}>
        <ServerMapView ref="mapView" style={styles.map} onGetInstance={this._onGetInstance}/>
        <PlotView style={styles.plotView} mapControl={this.state.mapControl} symbolIconPath="/SampleData/Plot/SymbolIcon/SymbolIcon/点标号"></PlotView>
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
  plotView:{
    flex:.3
  }
});

AppRegistry.registerComponent('GeometryInfo', () => GeometryInfo);
