package com.supermap.rnsupermap;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by will on 2016/6/15.
 */
public class SupermapFullPackage implements ReactPackage {
    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new MapViewManager()
        );
    }

    @Override
    public List<NativeModule> createNativeModules(
            ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();

        modules.add(new JSDataset(reactContext));
        modules.add(new JSDatasets(reactContext));
        modules.add(new JSDatasource(reactContext));
        modules.add(new JSDatasources(reactContext));
        modules.add(new JSDatasourceConnectionInfo(reactContext));
        modules.add(new JSLayers(reactContext));
        modules.add(new JSLayer(reactContext));
        modules.add(new JSMap(reactContext));
        modules.add(new JSMaps(reactContext));
        modules.add(new JSMapControl(reactContext));
        modules.add(new JSWorkspace(reactContext));
        modules.add(new JSMapView(reactContext));
        modules.add(new JSWorkspaceConnectionInfo(reactContext));
        modules.add(new JSNavigation2(reactContext));
        modules.add(new JSPoint(reactContext));
        modules.add(new JSPoint2D(reactContext));
        modules.add(new JSRectangle2D(reactContext));
        modules.add(new JSDatasetVector(reactContext));
        modules.add(new JSRecordset(reactContext));
        modules.add(new JSSelection(reactContext));
        modules.add(new JSSize2D(reactContext));
        modules.add(new JSGeoStyle(reactContext));
        modules.add(new JSGeometry(reactContext));
        modules.add(new JSGeoPoint(reactContext));
        modules.add(new JSGeoRegion(reactContext));
        modules.add(new JSTrackingLayer(reactContext));
        modules.add(new JSBufferAnalystParameter(reactContext));
        modules.add(new JSBufferAnalystGeometry(reactContext));
        modules.add(new JSDatasetVectorInfo(reactContext));

        return modules;
    }
}