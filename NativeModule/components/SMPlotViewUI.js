/**
 * Created by will on 2017/3/31.
 */

let React = require('react');
let {
    requireNativeComponent,
    View,
    StyleSheet,
} = require('react-native');

import {NativeModules} from 'react-native';
let FS = NativeModules.JSFileScanner;

export default class SMPlotView extends React.Component {
    componentDidUpdate = () =>{
        this.mapControl = this.props.mapControl;
        console.warn("MapControl loaded in Plot:" + this.mapControl.mapControlId);

    }

    render = () => {
        var props = {...this.props};
        props.returnId = true;

        return (
            <View {...props} style={styles.container}>
            </View>
        )
    }

    static propTypes = {
        symbolIconPath:React.PropTypes.string,
        mapControl:React.PropTypes.object,
        ...View.propTypes,
    };

}


var styles = StyleSheet.create({
    container: {
        flex: 1,
        alignSelf: 'stretch',
        backgroundColor: '#ffffff',
        alignItems: 'center',
        justifyContent: 'center',
        overflow: 'hidden',
    },
});
