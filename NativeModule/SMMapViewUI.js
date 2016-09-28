/**
 * Created by will on 2016/6/15.
 */
let React = require('React');
let {requireNativeComponent,View}=require('react-native');
import MapView from './MapView.js';
/**
 * ServerMapView视图标签，提供onGetInstance属性，该属性值的类型为函数，
 * 且函数参数为从Native层返回的MapViewId，在使用该标签时，必须通过此属性获得MapViewId
 */
class SMMapView extends React.Component{
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

        var mapView = new MapView();
        mapView.mapViewId = event.nativeEvent.mapViewId;
        this.props.onGetInstance(mapView);
    }
    static propTypes = {
        onGetInstance:React.PropTypes.func,
        ...View.propTypes,
    };
    render(){
        var props = {...this.props};
        props.returnId = true;
        return <RCTMapView {...props} onChange={this._onChange}></RCTMapView>;
    }
}

var RCTMapView = requireNativeComponent('RCTMapView',SMMapView,{nativeOnly:{
    returnId:true,
}});

export default SMMapView;