package com.supermap.rnsupermap;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.Point2D;
import com.supermap.navi.Navigation;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JSNavigation extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "JSNavigation";
    protected static Map<String, Navigation> m_NavigationList = new HashMap<String, Navigation>();
    private final String sdcard= android.os.Environment.getExternalStorageDirectory().getAbsolutePath().toString();
    Navigation m_Navigation;

    public JSNavigation(ReactApplicationContext context) {
        super(context);
    }

    public static Navigation getObjFromList(String id) {
        return m_NavigationList.get(id);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public static String registerId(Navigation obj) {
        for (Map.Entry entry : m_NavigationList.entrySet()) {
            if (obj.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        Calendar calendar = Calendar.getInstance();
        String id = Long.toString(calendar.getTimeInMillis());
        m_NavigationList.put(id, obj);
        return id;
    }

    @ReactMethod
    public void connectNaviData(String traditionalNaviId, String dataPath, Promise promise){
        try{
            Navigation navigation = getObjFromList(traditionalNaviId);
            boolean success = navigation.connectNaviData(sdcard + dataPath);

            WritableMap map = Arguments.createMap();
            map.putBoolean("success",success);
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void routeAnalyst(String traditionalNaviId, int mode, Promise promise){
        try{
            Navigation navigation = getObjFromList(traditionalNaviId);
            int result = navigation.routeAnalyst(mode);

            WritableMap map = Arguments.createMap();
            map.putInt("result",result);
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setStartPoint(String traditionalNaviId,double x,double y,String mapId,Promise promise){
        try{
            Navigation navigation = getObjFromList(traditionalNaviId);
            com.supermap.mapping.Map map = JSMap.getObjFromList(mapId);

            Point2D beTrans = new Point2D(x,y);
            Point2D totrans = JSNavigation2.toLatLotSys(beTrans,map);
            navigation.setStartPoint(totrans.getX(),totrans.getY());

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setDestinationPoint(String traditionalNaviId,double x,double y,String mapId,Promise promise){
        try{
            Navigation navigation = getObjFromList(traditionalNaviId);
            com.supermap.mapping.Map map = JSMap.getObjFromList(mapId);

            Point2D beTrans = new Point2D(x,y);
            Point2D totrans = JSNavigation2.toLatLotSys(beTrans,map);
            navigation.setDestinationPoint(totrans.getX(),totrans.getY());

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void startGuide(String traditionalNaviId,int status,Promise promise){
        try{
            Navigation navigation = getObjFromList(traditionalNaviId);

            boolean guiding = navigation.startGuide(status);

            WritableMap map = Arguments.createMap();
            map.putBoolean("guiding",guiding);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }
}

