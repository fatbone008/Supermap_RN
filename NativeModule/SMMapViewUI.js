/**
 * Created by will on 2016/6/15.
 */
let React = require('React');
let {requireNativeComponent,View,StyleSheet,Image,NativeModules}=require('react-native');
import MapView from './MapView.js';
let MC = NativeModules.JSMapControl;
let MV = NativeModules.JSMapView;
/**
 * ServerMapView视图标签，提供onGetInstance属性，该属性值的类型为函数，
 * 且函数参数为从Native层返回的MapViewId，在使用该标签时，必须通过此属性获得MapViewId
 */
class SMMapView extends React.Component{
    state = {
        startPoint:{},
        path:require('./resource/startpoint.png'),
    }

    constructor(){
        super();
        this._onChange = this._onChange.bind(this);
    }

    _onChange(event){
        if(!this.props.onGetInstance){
            console.error("no onGetInstance property!");
            return;
        }
        console.log("has onGetInstance:"+event.nativeEvent.mapViewId);

        this.mapView = new MapView();
        this.mapView.mapViewId = event.nativeEvent.mapViewId;
        this.props.onGetInstance(this.mapView);

        (async function () {
            try{
                this.mapControl = await this.mapView.getMapControl();
                await this.mapControl.setRefreshListener(function () {
                    console.log('MapControl:hey!');
                });
            }catch (e){
                console.log(e);
            }

        }).bind(this)();
    }

    static propTypes = {
        onGetInstance:React.PropTypes.func,
        callouts:React.PropTypes.array,
        ...View.propTypes,
    };

    render(){
        var props = {...this.props};
        props.returnId = true;
        return (
            <View style={styles.views}>
                <RCTMapView {...props} style={styles.map} onChange={this._onChange}></RCTMapView>
                { !this.state.startPoint
                    || <Image source={this.state.path} style={[styles.pic,{top:20}]}></Image>
                }
            </View>
        );
    }
}

var RCTMapView = requireNativeComponent('RCTMapView',SMMapView,{nativeOnly:{
    returnId:true,
}});

var styles = StyleSheet.create({
    views: {
        flex: 1,
        alignSelf: 'stretch',
        backgroundColor: '#ffbcbc',
        borderWidth: 1,
        borderColor: 'red',
        alignItems: 'center',
        justifyContent: 'center',
        overflow:'hidden',
    },
    map:{
        flex:1,
        alignSelf:'stretch',
    },
    pic:{
        position:'absolute',
        top:100,
        left:100,
    }
});

export default SMMapView;