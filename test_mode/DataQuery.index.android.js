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
import MapControl from '../NativeModule/MapControl.js';
import QueryParameter from '../NativeModule/QueryParameter.js';


export default class GeometryInfo extends Component {
    constructor (props) {
        super(props);
        const ds = new ListView.DataSource({rowHasChanged:(r1,r2) => r1 !== r2});
        this.state = {
            dataSource:ds.cloneWithRows(['11111111']),
            showListView : false,
        };
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
                    await this.workspaceConnectionInfo.setServer("/SampleData/GeometryInfo/World.smwu");

                    await this.workspace.open(this.workspaceConnectionInfo);
                    var mapName = await this.workspace.getMapName(0);

                    this.mapControl = await this.mapView.getMapControl();
                    this.map = await this.mapControl.getMap();

                    await this.map.setWorkspace(this.workspace);

                    await this.map.open(mapName);
                    await this.map.refresh();
                } catch (e) {
                    console.error(e);
                }
            }).bind(this)();
        } catch (e) {
            console.error(e);
        }
    }

    _drawPolygon = async () => {
        try{
            var layer = await this.map.getLayer(10);
            console.log("layerId:"+JSON.stringify(layer));
            await layer.setEditable(true);
            await this.mapControl.setAction(MapControl.ACTION.CREATEPOLYGON);
        }catch (e){
            console.error(e);
        }
    }


    _done = async () => {
        try{
            this.geoRegion = await this.mapControl.getCurrentGeometry();
        }catch (e){
            console.error(e);
        }
    }

    _query = async () => {
        try{
            var layer = await this.map.getLayer(10);
            var dataset = await layer.getDataset();
            var datasetVector = await dataset.toDatasetVector();
            var result = await datasetVector.query({
                spatialQueryObject:this.geoRegion,
                spatialQueryMode:QueryParameter.QUERYMODE.INTERSECT,
                hasGeometry:true,
                size:10,
                batch:1,
            });
            console.log("DataQuery:" + result.geoJson);
            var geoJson = JSON.parse(result.geoJson);
            this.setState({
                dataSource:this.state.dataSource.cloneWithRows(geoJson.features),
                showListView:true,
            });
        }catch (e){
            console.error(e);
        }
    }

    // 测试查询
    render() {
        return (
            <View style={styles.container}>
                <ServerMapView ref="mapView" onGetInstance={this._onGetInstance}/>

                <View style={styles.buttonGroup}>
                    <TouchableHighlight style={styles.geoButton} onPress={this._drawPolygon}>
                        <Text style={{fontSize:18,color:"white"}}>绘制区域</Text>
                    </TouchableHighlight>

                    <TouchableHighlight style={styles.geoButton} onPress={this._done}>
                        <Text style={{fontSize:18,color:"white"}}>完成</Text>
                    </TouchableHighlight>

                    <TouchableHighlight style={styles.geoButton} onPress={this._query}>
                        <Text style={{fontSize:18,color:"white"}}>查询</Text>
                    </TouchableHighlight>
                </View>

                {
                    !this.state.showListView ||
                        <ListView style={{flex:.3,alignSelf: 'stretch',}}
                            dataSource={this.state.dataSource}
                            renderRow={(rowData)=>
                                <Text style={{color:'white'}}>{rowData.properties.SmID}</Text>}>
                        </ListView>
                }
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

// AppRegistry.registerComponent('GeometryInfo', () => GeometryInfo);
