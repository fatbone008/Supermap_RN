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

// import DataQuery from './test_mode/DataQuery.index.android.js';
// import LocationDEMO from './test_mode/LoacationDEMO';
// import BoundsQuery from './test_mode/BoundsQuery';
// import MapView from './test_mode/index.android';
// import ComponentDemo from './test_mode/Components.index.android.js';
// import Navigation2 from './test_mode/Navigation2.android';
// import TraditionalNavi from './test_mode/TraditionalNavi.android';
// import EditMap from './test_mode/EditMap.android';
import ThreeD from './test_mode/ThreeDimensional.android'

AppRegistry.registerComponent('GeometryInfo', () => ThreeD);
