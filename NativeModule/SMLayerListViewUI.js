/**
 * Created by will on 2016/9/22.
 */
let React = require('React');
let {requireNativeComponent,View}=require('react-native');

class SMLayerListView extends React.Component{

    static propTypes = {
        bindMapId:React.PropTypes.string,
        ...View.propTypes,
    };

    render(){
        var props = {...this.props};
        return <RCTLayerListView {...props}></RCTLayerListView>
    }
}

var RCTLayerListView = requireNativeComponent('RCTLayerListView',SMLayerListView);

export default SMLayerListView;