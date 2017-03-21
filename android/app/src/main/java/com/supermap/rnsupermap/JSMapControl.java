package com.supermap.rnsupermap;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.facebook.react.bridge.WritableArray;
import com.supermap.RNUtils.N_R_EventSender;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.supermap.data.Enum;
import com.supermap.data.GeoLine;
import com.supermap.data.GeoPie;
import com.supermap.data.GeoPoint;
import com.supermap.data.GeoRegion;
import com.supermap.data.Geometry;
import com.supermap.data.Point;
import com.supermap.data.Point2D;
import com.supermap.mapping.Action;
import com.supermap.mapping.ActionChangedListener;
import com.supermap.mapping.ConfigurationChangedListener;
import com.supermap.mapping.EditStatusListener;
import com.supermap.mapping.GeometryAddedListener;
import com.supermap.mapping.GeometryDeletedListener;
import com.supermap.mapping.GeometryDeletingListener;
import com.supermap.mapping.GeometryEvent;
import com.supermap.mapping.GeometryModifiedListener;
import com.supermap.mapping.GeometryModifyingListener;
import com.supermap.mapping.GeometrySelectedEvent;
import com.supermap.mapping.GeometrySelectedListener;
import com.supermap.mapping.Layer;
import com.supermap.mapping.MapControl;
import com.supermap.mapping.MapParameterChangedListener;
import com.supermap.mapping.MeasureListener;
import com.supermap.mapping.RefreshListener;
import com.supermap.mapping.UndoStateChangeListener;
import com.supermap.navi.Navigation;
import com.supermap.navi.Navigation2;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by will on 2016/6/16.
 */
public class JSMapControl extends ReactContextBaseJavaModule {

    private static Map<String,MapControl> mapControlList=new HashMap<String,MapControl>();
    private static String mSTine;
    private MapControl mMapControl;
    private Navigation2 mNavigation2;
    ReactContext mReactContext;


    private static final String BOUNDSCHANGED = "Supermap.MapControl.MapParamChanged.BoundsChanged";
    private static final String SCALECHANGED = "Supermap.MapControl.MapParamChanged.ScaleChanged";
    private static final String ANGLECHANGED = "Supermap.MapControl.MapParamChanged.AngleChanged";
    private static final String SIZECHANGED = "Supermap.MapControl.MapParamChanged.SizeChanged";
    private static final String TOHORIZONTALSCREEN = "com.supermap.RN.JSMapControl.to_horizontal_screen";
    private static final String TOVERTICALSCREEN = "com.supermap.RN.JSMapControl.to_verticalscreen";


    private static final String LONGPRESS_EVENT = "com.supermap.RN.JSMapcontrol.long_press_event";
    private static final String SCROLL_EVENT = "com.supermap.RN.JSMapcontrol.scroll_event";

    private static final String ACTION_CHANGE = "com.supermap.RN.JSMapControl.action_change";
    private static final String GEOMETRYDELETED = "com.supermap.RN.JSMapControl.geometry_deleted";
    private static final String REFRESH_EVENT = "com.supermap.RN.JSMapcontrol.refresh_event";
    private static final String GEOMETRYADDED = "com.supermap.RN.JSMapcontrol.grometry_added";
    private static final String GEOMETRYDELETING = "com.supermap.RN.JSMapcontrol.geometry_deleting";
    private static final String GEOMETRYMODIFIED = "com.supermap.RN.JSMapcontrol.geometry_modified";
    private static final String GEOMETRYMODIFYING = "com.supermap.RN.JSMapcontrol.geometry_modifying";
    private static final String GEOMETRYSELECTED =  "com.supermap.RN.JSMapcontrol.geometry_selected";
    private static final String GEOMETRYMULTISELECTED =  "com.supermap.RN.JSMapcontrol.geometry_multi_selected";
    private static final String LENGTHMEASURED =  "com.supermap.RN.JSMapcontrol.length_measured";
    private static final String AREAMEASURED =  "com.supermap.RN.JSMapcontrol.area_measured";
    private static final String ANGLEMEASURED =  "com.supermap.RN.JSMapcontrol.angle_measured";
    private static final String UNDOSTATECHANGE = "com.supermap.RN.JSMapcontrol.undo_state_change";
    private static final String ADDNODEENABLE = "com.supermap.RN.JSMapcontrol.add_node_enable";
    private static final String DELETENODEENABLE = "com.supermap.RN.JSMapcontrol.delete_node_enable";

//    Listeners
    private ActionChangedListener mActionChangedListener;
    private RefreshListener mRefreshListener;
    private MapParameterChangedListener mMapParamChangedListener;
    private GestureDetector mGestureDetector;
    private GeometryDeletedListener mGeometryDeleted;
    private GeometryAddedListener mGeometryAdded;
    private GeometryDeletingListener mGeometryDeletingListener;
    private GeometryModifiedListener mGeometryModifiedListener;
    private GeometryModifyingListener mGeometryModifyingListener;
    private GeometrySelectedListener mGeometrySelectedListener;
    private MeasureListener mMeasureListener;
    private UndoStateChangeListener mUndoStateChangeListener;
    private EditStatusListener mEditStatusListener;

    @Override
    public String getName(){return "JSMapControl";}

    public JSMapControl(ReactApplicationContext reactContext){
        super(reactContext);
        mReactContext = reactContext;
    }

    public static String registerId(MapControl mapControl){
        for(Map.Entry entry:mapControlList.entrySet()){
            if(mapControl.equals(entry.getValue())){
                return (String)entry.getKey();
            }
        }
        Calendar calendar=Calendar.getInstance();
        String id=Long.toString(calendar.getTimeInMillis());
        mapControlList.put(id,mapControl);
        return id;
    }

    @ReactMethod
    public void getMap(String mapControlId, Promise promise){
        try{
            MapControl mapControl=mapControlList.get(mapControlId);
            com.supermap.mapping.Map map=mapControl.getMap();

//            写入map及其ID，返回ID，如果已经map已经存在，返回已存在的Id
            String mapId=JSMap.registerId(map);

            WritableMap rtnMap= Arguments.createMap();
            rtnMap.putString("mapId",mapId);
            promise.resolve(rtnMap);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    /**
     * 监听编辑行为的变更事件
     * @param mapControlId mapControl实例ID
     * @param promise JS层的promise对象
     */
    @ReactMethod
    public void addActionChangedListener(String mapControlId,Promise promise){
        try{
            mActionChangedListener = new ActionChangedListener() {
                @Override
                public void actionChanged(Action action, Action action1) {
                    N_R_EventSender n_r_eventSender = new N_R_EventSender();
                    n_r_eventSender.putString("newAction",action.name());
                    n_r_eventSender.putString("oldAction",action1.name());
                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(ACTION_CHANGE,n_r_eventSender.createSender());
                }
            };
            MapControl mapControl = mapControlList.get(mapControlId);
            mapControl.addActionChangedListener(mActionChangedListener);
            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void removeActionChangedListener(String mapControlId,Promise promise){
        try{
            MapControl mapControl = mapControlList.get(mapControlId);
            mapControl.removeActionChangedListener(mActionChangedListener);
            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }


    /**
     * 监听长按动作和滚动动作
     * @param mapControlId
     * @param promise
     */
    @ReactMethod
    public void setGestureDetector(String mapControlId, final Promise promise){
        try{
            mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
                public void onLongPress(MotionEvent event){
                    WritableMap map = Arguments.createMap();
                    map.putInt("x",(int)event.getX());
                    map.putInt("y",(int)event.getY());

                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(LONGPRESS_EVENT,map);
                }

                public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                        float distanceX, float distanceY){
                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(SCROLL_EVENT,null);
                    return false;
                }
            });
            MapControl mapControl = mapControlList.get(mapControlId);
            mapControl.setGestureDetector(mGestureDetector);
            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }


    @ReactMethod
    public void deleteCurrentGeometry(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            boolean deleted = mMapControl.deleteCurrentGeometry();

            WritableMap map = Arguments.createMap();
            map.putBoolean("deleted",deleted );
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    /**
     * 监听原生map刷新事件
     * @param mapControlId
     * @param promise
     */
    @ReactMethod
    public void setRefreshListener(String mapControlId , Promise promise){
        try{
            mRefreshListener = new RefreshListener() {
                @Override
                public void mapRefresh() {
                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(REFRESH_EVENT,null);
                }
            };
            MapControl mapControl = mapControlList.get(mapControlId);
            mapControl.setRefreshListener(mRefreshListener);
            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setAction(String mapControlId,int actionType,Promise promise){
        try{
            MapControl mapControl = mapControlList.get(mapControlId);
            mapControl.setAction((Action) Enum.parse(Action.class,actionType));
            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void submit(String mapControlId,Promise promise){
        try{
            MapControl mapControl = mapControlList.get(mapControlId);
            Boolean b = mapControl.submit();

            promise.resolve(b);
        }catch (Exception e){
            promise.reject(e);
        }
    }
    @ReactMethod
    public void getNavigation2(String mapControlId,Promise promise){
        try{
            mMapControl = mapControlList.get(mapControlId);
            mNavigation2 = mMapControl.getNavigation2();
//            getCurrentActivity().runOnUiThread(updateThread);
            String navigation2Id = JSNavigation2.registerId(mNavigation2);

            WritableMap map = Arguments.createMap();
            map.putString("navigation2Id",navigation2Id);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 监听地图参数变化
     * @param mapControlId
     * @param promise
     */
    @ReactMethod
    public void setMapParamChangedListener(String mapControlId,Promise promise){
        try{
            mMapParamChangedListener  = new MapParameterChangedListener() {

                @Override
                public void scaleChanged(double v) {
                    WritableMap map = Arguments.createMap();
                    map.putDouble("scale",v);
                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(BOUNDSCHANGED,map);
                }

                @Override
                public void boundsChanged(Point2D point2D) {
                    WritableMap map = Arguments.createMap();
                    map.putInt("x",(int)point2D.getX());
                    map.putInt("y",(int)point2D.getY());
                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(SCALECHANGED,map);
                }

                @Override
                public void angleChanged(double v) {
                    WritableMap map = Arguments.createMap();
                    map.putDouble("angle",v);
                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(ANGLECHANGED,map);
                }

                @Override
                public void sizeChanged(int i, int i1) {
                    WritableMap map = Arguments.createMap();
                    map.putInt("width",i);
                    map.putInt("height",i1);
                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(SIZECHANGED,map);
                }
            };
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.setMapParamChangedListener(mMapParamChangedListener);
            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getCurrentGeometry(String mapControlId,Promise promise){
        try{
            mMapControl = mapControlList.get(mapControlId);
            Geometry geometry = mMapControl.getCurrentGeometry();
            String geometryId = JSGeometry.registerId(geometry);

            WritableMap map = Arguments.createMap();
            map.putString("geometryId",geometryId);

            String type = "";
            if(geometry instanceof GeoPoint){
                type = "GeoPoint";
            }else if(geometry instanceof GeoLine){
                type = "GeoLine";
            }else if(geometry instanceof GeoRegion){
                type = "GeoRegion";
            }
            map.putString("geoType",type);

            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setConfigurationChangedListener(String mapControlId,Promise promise){
        try{
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.setConfigurationChangedListener(new ConfigurationChangedListener() {
                @Override
                public void toHorizontalScreen() {
                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(TOHORIZONTALSCREEN,null);
                }

                @Override
                public void toVerticalScreen() {
                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(TOVERTICALSCREEN,null);
                }
            });

            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getTraditionalNavi(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            Navigation traditionalNavi = mMapControl.getNavigation();
            String traditionalNaviId = JSNavigation.registerId(traditionalNavi);

            WritableMap map = Arguments.createMap();
            map.putString("traditionalNaviId",traditionalNaviId );
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getAction(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            Action action = mMapControl.getAction();
            int actionType = Enum.getValueByName(Action.class,action.toString());

            WritableMap map = Arguments.createMap();
            map.putInt("actionType",actionType );
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void redo(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            boolean redone = mMapControl.redo();

            WritableMap map = Arguments.createMap();
            map.putBoolean("redone",redone );
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void undo(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            boolean undone = mMapControl.undo();

            WritableMap map = Arguments.createMap();
            map.putBoolean("undone",undone );
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void cancel(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            boolean canceled = mMapControl.cancel();

            WritableMap map = Arguments.createMap();
            map.putBoolean("canceled",canceled );
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }


    @ReactMethod
    public void getEditLayer(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            Layer layer = mMapControl.getEditLayer();
            String layerId = JSLayer.registerId(layer);

            WritableMap map = Arguments.createMap();
            map.putString("layerId",layerId);
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void deleteGestureDetector(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.deleteGestureDetector();

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void addGeometryDeletedListener(String mapControlId,Promise promise){
        try {
            mGeometryDeleted = new GeometryDeletedListener() {
                @Override
                public void geometryDeleted(GeometryEvent event) {
                    boolean canceled = event.getCancel();
                    int id = event.getID();
                    Layer layer = event.getLayer();
                    String layerId = JSLayer.registerId(layer);

                    WritableMap map = Arguments.createMap();
                    map.putString("layerId",layerId);
                    map.putInt("id",id);
                    map.putBoolean("canceled",canceled);

                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(GEOMETRYDELETED,map);
                }
            };
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.addGeometryDeletedListener(mGeometryDeleted);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void removeGeometryDeletedListener(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.removeGeometryDeletedListener(mGeometryDeleted);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void addGeometryAddedListener(String mapControlId,Promise promise){
        try {
            mGeometryAdded = new GeometryAddedListener() {
                @Override
                public void geometryAdded(GeometryEvent event) {

                    boolean canceled = event.getCancel();
                    int id = event.getID();
                    Layer layer = event.getLayer();
                    String layerId = JSLayer.registerId(layer);

                    WritableMap map = Arguments.createMap();
                    map.putString("layerId",layerId);
                    map.putInt("id",id);
                    map.putBoolean("canceled",canceled);

                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(GEOMETRYADDED,map);
                }
            };
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.addGeometryAddedListener(mGeometryAdded);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void removeGeometryAddedListener(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.removeGeometryAddedListener(mGeometryAdded);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void addGeometryDeletingListener(String mapControlId,Promise promise){
        try {
            mGeometryDeletingListener = new GeometryDeletingListener() {
                @Override
                public void geometryDeleting(GeometryEvent event) {
                    boolean canceled = event.getCancel();
                    int id = event.getID();
                    Layer layer = event.getLayer();
                    String layerId = JSLayer.registerId(layer);

                    WritableMap map = Arguments.createMap();
                    map.putString("layerId",layerId);
                    map.putInt("id",id);
                    map.putBoolean("canceled",canceled);

                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(GEOMETRYDELETING,map);
                }
            };
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.addGeometryDeletingListener(mGeometryDeletingListener);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void removeGeometryDeletingListener(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.removeGeometryDeletingListener(mGeometryDeletingListener);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void addGeometryModifiedListener(String mapControlId,Promise promise){
        try {
            mGeometryModifiedListener  = new GeometryModifiedListener() {
                @Override
                public void geometryModified(GeometryEvent event) {
                    boolean canceled = event.getCancel();
                    int id = event.getID();
                    Layer layer = event.getLayer();
                    String layerId = JSLayer.registerId(layer);

                    WritableMap map = Arguments.createMap();
                    map.putString("layerId",layerId);
                    map.putInt("id",id);
                    map.putBoolean("canceled",canceled);

                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(GEOMETRYMODIFIED,map);
                }
            };
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.addGeometryModifiedListener(mGeometryModifiedListener);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void removeGeometryModifiedListener(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.removeGeometryModifiedListener(mGeometryModifiedListener);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void addGeometryModifyingListener(String mapControlId,Promise promise){
        try {
            mGeometryModifyingListener  = new GeometryModifyingListener() {
                @Override
                public void geometryModifying(GeometryEvent event) {
                    boolean canceled = event.getCancel();
                    int id = event.getID();
                    Layer layer = event.getLayer();
                    String layerId = JSLayer.registerId(layer);

                    WritableMap map = Arguments.createMap();
                    map.putString("layerId",layerId);
                    map.putInt("id",id);
                    map.putBoolean("canceled",canceled);

                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(GEOMETRYMODIFYING,map);
                }
            };
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.addGeometryModifyingListener(mGeometryModifyingListener);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void removeGeometryModifyingListener(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.addGeometryModifyingListener(mGeometryModifyingListener);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void addGeometrySelectedListener(String mapControlId,Promise promise){
        try {
            mGeometrySelectedListener  = new GeometrySelectedListener() {
                @Override
                public void geometrySelected(GeometrySelectedEvent event) {
                    int id = event.getGeometryID();
                    Layer layer = event.getLayer();
                    String layerId = JSLayer.registerId(layer);

                    WritableMap map = Arguments.createMap();
                    map.putString("layerId",layerId);
                    map.putInt("id",id);

                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(GEOMETRYSELECTED,map);
                }

                @Override
                public void geometryMultiSelected(ArrayList<GeometrySelectedEvent> events) {
                    WritableArray array = Arguments.createArray();
                    for(int i = 0; i < events.size(); i++){
                        GeometrySelectedEvent event = events.get(i);
                        int id = event.getGeometryID();
                        Layer layer = event.getLayer();
                        String layerId = JSLayer.registerId(layer);

                        WritableMap map = Arguments.createMap();
                        map.putString("layerId",layerId);
                        map.putInt("id",id);
                        array.pushMap(map);
                    }

                    WritableMap geometries = Arguments.createMap();
                    geometries.putArray("geometries",array);
                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(GEOMETRYMULTISELECTED,geometries);
                }
            };
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.addGeometrySelectedListener(mGeometrySelectedListener);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void removeGeometrySelectedListener(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.removeGeometrySelectedListener(mGeometrySelectedListener);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void addMeasureListener(String mapControlId,Promise promise){
        try {
            mMeasureListener = new MeasureListener() {
                @Override
                public void lengthMeasured(double curResult, Point curPoint) {
                    WritableMap map = Arguments.createMap();
                    map.putDouble("curResult",curResult);
                    WritableMap point = Arguments.createMap();
                    point.putDouble("x",curPoint.getX());
                    point.putDouble("y",curPoint.getY());
                    map.putMap("curPoint",point);

                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(LENGTHMEASURED,map);
                }

                @Override
                public void areaMeasured(double curResult, Point curPoint) {
                    WritableMap map = Arguments.createMap();
                    map.putDouble("curResult",curResult);
                    WritableMap point = Arguments.createMap();
                    point.putDouble("x",curPoint.getX());
                    point.putDouble("y",curPoint.getY());
                    map.putMap("curPoint",point);

                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(AREAMEASURED,map);
                }

                @Override
                public void angleMeasured(double curAngle, Point curPoint) {
                    WritableMap map = Arguments.createMap();
                    map.putDouble("curAngle",curAngle);
                    WritableMap point = Arguments.createMap();
                    point.putDouble("x",curPoint.getX());
                    point.putDouble("y",curPoint.getY());
                    map.putMap("curPoint",point);

                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(ANGLEMEASURED,map);
                }
            };
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.addMeasureListener(mMeasureListener);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void removeMeasureListener(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.removeMeasureListener(mMeasureListener);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void addUndoStateChangeListener(String mapControlId,Promise promise){
        try {
            mUndoStateChangeListener = new UndoStateChangeListener() {
                @Override
                public void undoStateChange(boolean canUndo, boolean canRedo) {
                    WritableMap map = Arguments.createMap();
                    map.putBoolean("canUndo",canUndo);
                    map.putBoolean("canRedo",canRedo);

                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(UNDOSTATECHANGE,map);
                }
            };
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.addUndoStateChangeListener(mUndoStateChangeListener);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void removeUndoStateChangeListener(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.removeUndoStateChangeListener(mUndoStateChangeListener);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setEditStatusListener(String mapControlId,Promise promise){
        try {
            mEditStatusListener = new EditStatusListener() {
                @Override
                public void addNodeEnable(boolean isEnable) {
                    WritableMap map = Arguments.createMap();
                    map.putBoolean("isEnable",isEnable);

                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(ADDNODEENABLE,map);
                }

                @Override
                public void deleteNodeEnable(boolean isEnable) {
                    WritableMap map = Arguments.createMap();
                    map.putBoolean("isEnable",isEnable);

                    mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(DELETENODEENABLE,map);
                }
            };
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.setEditStatusListener(mEditStatusListener);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void removeEditStatusListener(String mapControlId,Promise promise){
        try {
            mMapControl = mapControlList.get(mapControlId);
            mMapControl.removeEditStatusListener(mEditStatusListener);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    Runnable updateThread = new Runnable(){
        @Override
        public void run(){
            mNavigation2 = mMapControl.getNavigation2();
        }
    };

}
