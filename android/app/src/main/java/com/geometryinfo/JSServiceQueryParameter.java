package com.geometryinfo;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.devsupport.DoubleTapReloadRecognizer;
import com.supermap.data.Geometry;
import com.supermap.data.Rectangle2D;
import com.supermap.services.ServiceQueryParameter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JSServiceQueryParameter extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "JSServiceQueryParameter";
    protected static Map<String, ServiceQueryParameter> m_ServiceQueryParameterList = new HashMap<String, ServiceQueryParameter>();
    ServiceQueryParameter m_ServiceQueryParameter;

    public JSServiceQueryParameter(ReactApplicationContext context) {
        super(context);
    }

    public static ServiceQueryParameter getObjFromList(String id) {
        return m_ServiceQueryParameterList.get(id);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public static String registerId(ServiceQueryParameter obj) {
        for (Map.Entry entry : m_ServiceQueryParameterList.entrySet()) {
            if (obj.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        Calendar calendar = Calendar.getInstance();
        String id = Long.toString(calendar.getTimeInMillis());
        m_ServiceQueryParameterList.put(id, obj);
        return id;
    }

    @ReactMethod
    public void createObj(Promise promise){
        try{
            ServiceQueryParameter serviceQueryParameter = new ServiceQueryParameter();
            String serviceQueryParameterId = registerId(serviceQueryParameter);

            WritableMap map = Arguments.createMap();
            map.putString("_serviceQueryParameterId_",serviceQueryParameterId);
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setQueryBounds(String serviceQueryParameterId,String rectang2DId,Promise promise){
        try{
            ServiceQueryParameter serviceQueryParameter = new ServiceQueryParameter();
            Rectangle2D rectangle2D = JSRectangle2D.getObjFromList(rectang2DId);
            serviceQueryParameter.setQueryBounds(rectangle2D);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getQueryBounds(String serviceQueryParameterId,Promise promise){
        try{
            ServiceQueryParameter serviceQueryParameter = new ServiceQueryParameter();
            Rectangle2D rectangle2D = serviceQueryParameter.getQueryBounds();
            String rectangle2DId = JSRectangle2D.registerId(rectangle2D);

            WritableMap map = Arguments.createMap();
            map.putString("rectangle2DId",rectangle2DId);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setQueryDistance(String serviceQueryParameterId, double d, Promise promise){
        try{
            ServiceQueryParameter serviceQueryParameter = new ServiceQueryParameter();
            serviceQueryParameter.setQueryDistance(d);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getQueryDistance(String serviceQueryParameterId,Promise promise){
        try{
            ServiceQueryParameter serviceQueryParameter = new ServiceQueryParameter();
            double distance= serviceQueryParameter.getQueryDistance();

            promise.resolve(distance);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setQueryDistance(String serviceQueryParameterId, String geometryId, Promise promise){
        try{
            ServiceQueryParameter serviceQueryParameter = new ServiceQueryParameter();
            Geometry geometry = JSGeometry.getObjFromList(geometryId);
            serviceQueryParameter.setQueryGeomety(geometry);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getQueryGeomety(String serviceQueryParameterId,Promise promise){
        try{
            ServiceQueryParameter serviceQueryParameter = new ServiceQueryParameter();
            Geometry geometry = serviceQueryParameter.getQueryGeomety();
            String geometryId = JSGeometry.registerId(geometry);

            WritableMap map = Arguments.createMap();
            map.putString("geometryId",geometryId);
            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }
}

