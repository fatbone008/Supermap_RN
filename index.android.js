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
import ServerMapView from './NativeModule/SMMapViewUI.js';
import MapView from './NativeModule/MapView.js';
import Point from './NativeModule/Point.js';
import CallOut from './NativeModule/CallOut.js';


class GeometryInfo extends Component {
    state = {
        setStartPoint: false,
        setDestPoint: false,
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

                    //需要在map.open后使用
                    await this.mapControl.setGestureDetector(this._longPressHandler, ()=> {
                    });
                } catch (e) {
                    console.error(e);
                }
            }).bind(this)();
        } catch (e) {
            console.error(e);
        }
    }

    _longPressHandler = (e) => {
        var pointName;
        if (this.state.setStartPoint) {
            pointName = MapView.NAVIGATION_STARTPOINT;
        } else if (this.state.setDestPoint) {
            pointName = MapView.NAVIGATION_DESTPOINT;
        } else {
            return;
        }

        var pointFac = new Point();
        (async() => {
            try {
                await this._initCallOut(pointName);

                var point = await pointFac.createObj(e.x, e.y);
                var point2D = await this.map.pixelToMap(point);
                console.log("point2D:" + JSON.stringify(point2D));

                await this.callOut.setLocation(point2D);

                var imageViewId = pointName == "startpoint" ? findNodeHandle(this.refs.startpoint) : findNodeHandle(this.refs.destpoint);
                console.log("imageViewId:" + imageViewId);
                this.callOut.setContentView(imageViewId);

                this.mapView.showCallOut();

                // var {eth_point2DId} = await this.mapView.addPoint(point2D,pointName,this.refs.callout.state.viewId);
                // p2.point2DId = eth_point2DId;
                // pointName == MapView.NAVIGATION_STARTPOINT ?
                //     this.startPoint = p2 : this.destPoint = p2 ;
            } catch (e) {
                console.error(e);
            }
        })();
    }

    _initCallOut = async(pointName) => {
        try {
            var callOutFac = new CallOut();
            this.callOut = await callOutFac.createObj(this.mapView);
            console.log("callOut ID:" + this.callOut.callOutId);

            await this.callOut.setStyle();
            await this.callOut.setCustomize(true);

            await this.mapView.addCallOut(this.callOut,pointName);
        } catch (e) {
            console.error(e);
        }
    }

    _routeAnalysis = async()=> {
        // if(this.startPoint == null || this.destPoint == null){
        //     console.log('请先设置起点终点');
        //     return ;
        // }

        //链式调用的试验……写法很丑啊
        var networkDataset = await(await(await(await this.workspace.getDatasources()).get("beijing")).getDatasets()).get("RoadNetwork");
        console.log("networkDataset:" + JSON.stringify(networkDataset));
        this.navigation2 = await this.mapControl.getNavigation2();
        console.log("navigation2:" + JSON.stringify(this.navigation2));
        // await navigation2.setPathVisible(true);
    }

    render() {
        return (
            <View style={styles.container}>
                <ServerMapView ref="mapView" style={styles.map} onGetInstance={this._onGetInstance}/>
                <View style={styles.buttons}>
                    <TouchableHighlight style={styles.buttonText}
                                        onPress={()=>this.setState({setDestPoint: false, setStartPoint: true})}>
                        <Text>设置起点</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.buttonText}
                                        onPress={()=>this.setState({setStartPoint: false, setDestPoint: true})}>
                        <Text>设置终点</Text>
                    </TouchableHighlight>
                    <TouchableHighlight style={styles.buttonText} onPress={this._routeAnalysis}>
                        <Text>路线分析</Text>
                    </TouchableHighlight>
                    {/*<TouchableHighlight style={styles.buttonText} onPress={()=>console.log("callOut Id:"+findNodeHandle(this.refs.mapView))}>*/}
                    {/*<Text>测试CallOut</Text>*/}
                    {/*</TouchableHighlight>*/}
                    <Image ref="startpoint" source={require('./NativeModule/resource/startpoint.png')}/>
                    <Image ref="destpoint" source={require('./NativeModule/resource/destpoint.png')}/>
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
        backgroundColor: '#F5FCFF',
    },
    map: {
        flex: 1,
        alignSelf: 'stretch',
    },
    legend: {
        flex: .5,
        backgroundColor: '#ffffff',
        alignSelf: 'stretch',
    },
    buttons: {
        flexDirection: 'row',
    },
    buttonText: {
        backgroundColor: '#f3ab99',
        width: 100,
        height: 30,
        margin: 5,
    }
});

AppRegistry.registerComponent('GeometryInfo', () => GeometryInfo);
