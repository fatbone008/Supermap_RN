package com.geometryinfo;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.Geometry;
import com.supermap.services.Feature;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JSFeature extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "JSFeature";
    protected static Map<String, Feature> m_FeatureList = new HashMap<String, Feature>();
    Feature m_Feature;

    public JSFeature(ReactApplicationContext context) {
        super(context);
    }

    public static Feature getObjFromList(String id) {
        return m_FeatureList.get(id);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public static String registerId(Feature obj) {
        for (Map.Entry entry : m_FeatureList.entrySet()) {
            if (obj.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        Calendar calendar = Calendar.getInstance();
        String id = Long.toString(calendar.getTimeInMillis());
        m_FeatureList.put(id, obj);
        return id;
    }

    @ReactMethod
    public void createObj(String[] fieldNames, String[] fieldValues, String geomtryId, Promise promise){
        try{
            Geometry geometry = JSGeometry.getObjFromList(geomtryId);
            Feature feature = new Feature(fieldNames,fieldValues,geometry);
            String featureId = registerId(feature);

            WritableMap map = Arguments .createMap();
            map.putString("_featureId_",featureId);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getFieldNames(String featureId,Promise promise){
        try{
            Feature feature = getObjFromList(featureId);
            String names[] = feature.getFieldNames();

            WritableArray arr = Arguments.createArray();
            for(int i = 0 ;i<names.length;i++){
                arr.pushString(names[i]);
            }
            promise.resolve(arr);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getFieldValues(String featureId,Promise promise){
        try{
            Feature feature = getObjFromList(featureId);
            String names[] = feature.getFieldValues();

            WritableArray arr = Arguments.createArray();
            for(int i = 0 ;i<names.length;i++){
                arr.pushString(names[i]);
            }
            promise.resolve(arr);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getGeometry(String featureId,Promise promise){
        try{
            Feature feature = getObjFromList(featureId);
            Geometry geometry = feature.getGeometry();
            String geometryId = JSGeometry.registerId(geometry);

            WritableMap map = Arguments.createMap();
            map.putString("geometryId",geometryId);
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void toJson(String featureId,Promise promise){
        try{
            Feature feature = getObjFromList(featureId);
            String json = feature.toJson();

            promise.resolve(json);
        }catch (Exception e){
            promise.reject(e);
        }
    }
}

