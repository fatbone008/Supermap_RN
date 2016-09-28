package com.geometryinfo;

import android.app.Activity;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropertyHolder;
import com.supermap.mapping.Map;
import com.supermap.mapping.MapControl;
import com.supermap.mapping.MapLoadedListener;
import com.supermap.mapping.view.LayerListView;

/**
 * Created by will on 2016/9/22.
 */

public class LayerListViewManager extends SimpleViewManager<LayerListView> {
    private static final String REACT_CLASS = "RCTLayerListView";

    ThemedReactContext m_ThemeReactContext;
    LayerListView m_LayerListView;

    @Override
    public String getName(){
        return REACT_CLASS;
    }

    @Override
    public LayerListView createViewInstance(ThemedReactContext reactContext){
        m_ThemeReactContext = reactContext;
        m_LayerListView = new LayerListView(reactContext.getBaseContext());
        return m_LayerListView;
    }

    @ReactProp(name="mapId")
    public void mapId(final LayerListView layerListView,String mapId){
        final Map map = JSMap.getObjFromList(mapId);
        final boolean b = false;

        layerListView.loadMap(map);
        layerListView.reload();
    }
}
