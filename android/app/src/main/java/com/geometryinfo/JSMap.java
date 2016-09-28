package com.geometryinfo;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.Point;
import com.supermap.data.Point2D;
import com.supermap.data.Workspace;
import com.supermap.mapping.Layers;
import com.supermap.mapping.Map;
import com.supermap.mapping.TrackingLayer;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by will on 2016/6/16.
 */
public class JSMap extends ReactContextBaseJavaModule {

    private static java.util.Map<String,Map> mapList=new HashMap<String,Map>();
    private static final String NAVIGATION_STARTPOINT = "STARTPOINT";
    private static final String NAVIGATION_DESTPOINT = "DESTPOINT";
    private Map m_Map;

    @Override
    public String getName(){return "JSMap";}

    public JSMap(ReactApplicationContext context){
        super(context);
    }

    public static Map getObjFromList(String id) {
        return mapList.get(id);
    }

    public java.util.Map<String, Object> getConstants() {
        final java.util.Map<String, Object> constants = new HashMap<>();
        constants.put(NAVIGATION_STARTPOINT, "startpoint");
        constants.put(NAVIGATION_DESTPOINT, "destpoint");
        return constants;
    }

    public static String registerId(Map map){
        String mapId;
        for(java.util.Map.Entry entry:mapList.entrySet()){
            if(map.equals(entry.getValue())){
                mapId=(String)entry.getKey();
                return mapId;
            }
        }
        Calendar cale = Calendar.getInstance();
        mapId = Long.toString(cale.getTimeInMillis());
        mapList.put(mapId,map);
        return mapId;
    }

    public static String getIdWithObj(Map map){
        String mapId;
        try{
            for(java.util.Map.Entry entry:mapList.entrySet()){
                if(map.equals(entry.getValue())){
                    mapId=(String)entry.getKey();
                    return mapId;
                }
            }
            throw new Exception("the map you find is not exist!");
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @ReactMethod
    public void setWorkspace(String mapId,String workspaceId,Promise promise){
        try{
            Workspace workspace=JSWorkspace.getObjById(workspaceId);
            Map map=mapList.get(mapId);
            map.setWorkspace(workspace);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void open(String mapId,String mapName,Promise promise){
        try{
            Map map=mapList.get(mapId);
            map.open(mapName);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void refresh(String mapId,Promise promise){
        try{
            Map map=mapList.get(mapId);
            map.refresh();
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void close(String mapId,Promise promise){
        try{
            Map map=mapList.get(mapId);
            map.close();
            System.out.print("map closed");
            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getLayers(String mapId,Promise promise){
        try{
            m_Map = mapList.get(mapId);
            Layers layers = m_Map.getLayers();
            String layersId = JSLayers.registerId(layers);

            WritableMap map = Arguments.createMap();
            map.putString("layersId",layersId);

            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void pixelToMap(String mapId, String pointId,Promise promise){
        try{
            Map map = mapList.get(mapId);
            Point point = JSPoint.m_PointList.get(pointId);

            Point2D point2D = map.pixelToMap(point);
            String point2DId = JSPoint2D.registerId(point2D);

            WritableMap map1 = Arguments.createMap();
            map1.putString("point2DId",point2DId);
            promise.resolve(map1);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setCenter(String mapId,String point2DId,Promise promise){
        try{
            Map map = mapList.get(mapId);
            Point2D point2D = JSPoint2D.m_Point2DList.get(point2DId);
            map.setCenter(point2D);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getTrackingLayer(String mapId,Promise promise){
        try{
            Map map = mapList.get(mapId);
            TrackingLayer trackingLayer = map.getTrackingLayer();
            String trackingLayerId = JSTrackingLayer.registerId(trackingLayer);

            WritableMap wMap = Arguments.createMap();
            wMap.putString("trackingLayerId",trackingLayerId);
            promise.resolve(wMap);
        }catch (Exception e){
            promise.reject(e);
        }
    }
}
