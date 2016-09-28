/**
 * Created by will on 2016/9/7.
 */
let React = require('React');
let {requireNativeComponent,View}=require('react-native');

class SMLegendView extends React.Component{
    static propTypes = {
        mapId:React.PropTypes.string,
        ...View.propTypes,
    };

    _refresh = (event) => {
        console.log(event.nativeEvent.enableRedraw);
    }

    render(){
        var props = {...this.props};
        return <RCTLegendView {...props} onChange={this._refresh} ref="LegendView"></RCTLegendView>
    }
}

var RCTLegendView = requireNativeComponent('RCTLegendView',SMLegendView);

export default SMLegendView;