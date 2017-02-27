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
import LocationDEMO from './test_mode/LoacationDEMO';
// import BoundsQuery from './test_mode/BoundsQuery';
// import MapView from './test_mode/index.android';


AppRegistry.registerComponent('GeometryInfo', () => LocationDEMO);
