package com.supermap.rnsupermap;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.CoordSysTransMethod;
import com.supermap.data.CoordSysTransParameter;
import com.supermap.data.CoordSysTranslator;
import com.supermap.data.Dataset;
import com.supermap.data.DatasetVector;
import com.supermap.data.Point2D;
import com.supermap.data.Point2Ds;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.PrjCoordSysType;
import com.supermap.navi.Navigation2;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by will on 2016/7/12.
 */
public class JSNavigation2 extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS="JSNavigation2";
    private final String sdcard= android.os.Environment.getExternalStorageDirectory().getAbsolutePath().toString();

    public static Map<String , Navigation2> m_Navigation2List = new HashMap<String , Navigation2>();
    Navigation2 m_Navigation2;

    public JSNavigation2(ReactApplicationContext context){
        super(context);
    }

    @Override
    public String getName(){return REACT_CLASS;}

    public static String registerId(Navigation2 navigation2){
        for(Map.Entry entry:m_Navigation2List.entrySet())
        {
            if(navigation2.equals(entry.getValue())){
                return (String) entry.getKey();
            }
        }

        Calendar calendar = Calendar.getInstance();
        String id=Long.toString(calendar.getTimeInMillis());
        m_Navigation2List.put(id,navigation2);
        return id;
    }

    @ReactMethod
    public void setPathVisible(String navigation2Id, Boolean visible, Promise promise){
        try{
            Navigation2 navigation2 = m_Navigation2List.get(navigation2Id);
            navigation2.setPathVisible(visible);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setNetworkDataset(String navigation2Id,String datasetId,Promise promise){
        try{
            Navigation2 navigation2 = m_Navigation2List.get(navigation2Id);
            DatasetVector datasetVector = JSDatasetVector.getObjFromList(datasetId);
//            Dataset dataset = JSDataset.m_DatasetList.get(datasetId);
            navigation2.setNetworkDataset(datasetVector);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void loadModel(String navigation2Id,String path,Promise promise){
        try{
            Navigation2 navigation2 = m_Navigation2List.get(navigation2Id);
            boolean loaded = navigation2.loadModel(sdcard + path);
            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setStartPoint(String navigation2Id,double x,double y,String mapId,Promise promise){
        try{
            Navigation2 navigation2 = m_Navigation2List.get(navigation2Id);
            com.supermap.mapping.Map map = JSMap.getObjFromList(mapId);
            Point2D beTrans = new Point2D(x,y);
            Point2D totrans = toLatLotSys(beTrans,map);

            navigation2.setStartPoint(totrans.getX(),totrans.getY());
            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setDestinationPoint(String navigation2Id,double x,double y,String mapId,Promise promise){
        try{
            Navigation2 navigation2 = m_Navigation2List.get(navigation2Id);
            com.supermap.mapping.Map map = JSMap.getObjFromList(mapId);
            Point2D beTrans = new Point2D(x,y);
            Point2D totrans = toLatLotSys(beTrans,map);

            navigation2.setDestinationPoint(totrans.getX(),totrans.getY());
            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void routeAnalyst(String navigation2Id,Promise promise){
        try{
            Navigation2 navigation2 = m_Navigation2List.get(navigation2Id);
            boolean finished = navigation2.routeAnalyst();

            WritableMap map = Arguments.createMap();
            map.putBoolean("finished",finished);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void startGuide(String navigation2Id,int guideMode,Promise promise){
        try{
            Navigation2 navigation2 = m_Navigation2List.get(navigation2Id);
            boolean isGuiding = navigation2.startGuide(guideMode);

            WritableMap map = Arguments.createMap();
            map.putBoolean("isGuiding",isGuiding);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    private Point2D toLatLotSys(Point2D beTrans, com.supermap.mapping.Map m_Map){
        PrjCoordSys Prj = m_Map.getPrjCoordSys();
        Point2D toTrans = beTrans;

        // 当投影不是经纬坐标系时，则对点进行投影转换
        if (Prj.getType() != PrjCoordSysType.PCS_EARTH_LONGITUDE_LATITUDE) {
            Point2Ds points = new Point2Ds();
            points.add(beTrans);
            PrjCoordSys desPrjCoorSys = new PrjCoordSys();
            desPrjCoorSys.setType(PrjCoordSysType.PCS_EARTH_LONGITUDE_LATITUDE);
            boolean b1 = CoordSysTranslator.convert(points,  Prj,desPrjCoorSys,
                    new CoordSysTransParameter(),
                    CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);

            toTrans = points.getItem(0);
        }
        return toTrans;
    }
}
