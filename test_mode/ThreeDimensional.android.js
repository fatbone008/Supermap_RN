/**
 * Created by will on 2017/3/22.
 */

import React, { Component } from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    TouchableHighlight
} from 'react-native';
import SceneView from './../NativeModule/components/SMSceneViewUI';
import WorkspaceFac from './../NativeModule/Workspace';

export default class TreeDimensional extends React.Component{
    _onChange = (sceneControl) => {
        this.sceneControl = sceneControl;
        this.loadScene();
    }

    loadScene = () => {
        var workspaceModule = new WorkspaceFac();

        (async function () {
            try{
                this.workspace  = await workspaceModule.createObj();
                await this.workspace.open("/SampleData/凯德Mall/凯德Mall.sxwu");
            }catch (e){
                console.error(e);
            }

        }).bind(this)();
    };

    //打开场景
    _open = async () => {
        try{
            var scene = await this.sceneControl.getScene();
            await scene.setWorkspace(this.workspace);
            var sceneName = await this.workspace.getSceneName(0);
            await scene.open(sceneName);
        }catch (e){
            console.error(e);
        }
    }

    render(){
        var props = {...this.props};
        props.returnId = true;

        return (
            <View style={styles.views}>
                <SceneView {...props} style={styles.scene} onGetScene={this._onChange}></SceneView>

                <TouchableHighlight style={styles.geoButton} onPress={this._open}>
                    <Text style={styles.textLabel}>打开场景</Text>
                </TouchableHighlight>
            </View>
        );
    }
}


var styles = StyleSheet.create({
    views: {
        flex: 1,
        alignSelf: 'stretch',
        backgroundColor: '#ffbcbc',
        alignItems: 'center',
        justifyContent: 'center',
        overflow:'hidden',
    },
    scene:{
        flex:1,
        alignSelf:'stretch',
        borderColor:'red',
    },
    geoButton:{
        position:'absolute',
        left:20,
        bottom:20,
        width:100,
        height:40,
        flex:1,
        justifyContent:'center',
    },
    textLabel:{
        color:'blue',
    }
});