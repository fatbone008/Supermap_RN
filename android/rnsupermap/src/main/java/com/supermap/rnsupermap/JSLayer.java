package com.supermap.rnsupermap;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.Dataset;
import com.supermap.mapping.Layer;
import com.supermap.mapping.Selection;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by will on 2016/7/5.
 */
public class JSLayer extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "JSLayer";
    public static Map<String,Layer> mLayerList=new HashMap<String,Layer>();
    Layer mLayer;

    public JSLayer(ReactApplicationContext context){super(context);}

    public static String registerId(Layer layer){
        if(!mLayerList.isEmpty()) {
            for(Map.Entry entry:mLayerList.entrySet()){
                if(layer.equals(entry.getValue())){
                    return (String)entry.getKey();
                }
            }
        }

        Calendar calendar=Calendar.getInstance();
        String id=Long.toString(calendar.getTimeInMillis());
        mLayerList.put(id,layer);
        return id;
    }

    @Override
    public String getName(){
        return REACT_CLASS;
    }

    @ReactMethod
    public void setEditable(String layerId,boolean editable,Promise promise){
        try{
            mLayer = mLayerList.get(layerId);
            mLayer.setEditable(editable);
            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getName(String layerId,int index,Promise promise){
        try{
            mLayer = mLayerList.get(layerId);
            String layerName = mLayer.getName();

            WritableMap map = Arguments.createMap();
            map.putString("layerName",layerName);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getDataset(String layerId,Promise promise){
        try{
            Layer layer = mLayerList.get(layerId);
            Dataset dataset = layer.getDataset();
            String datasetId = JSDataset.registerId(dataset);

            WritableMap map = Arguments.createMap();
            map.putString("datasetId",datasetId);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getSelection(String layerId,Promise promise){
        try{
            Layer layer = mLayerList.get(layerId);
            Selection selection = layer.getSelection();
            String selectionId = JSSelection.registerId(selection);

            WritableMap map = Arguments.createMap();
            map.putString("selectionId",selectionId);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setSelectable(String layerId,boolean b,Promise promise){
        try{
            Layer layer = mLayerList.get(layerId);
            layer.setSelectable(b);

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }
}
