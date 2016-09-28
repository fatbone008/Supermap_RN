package com.geometryinfo;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.DatasetType;
import com.supermap.data.Enum;
import com.supermap.services.DataUploadService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JSDataUploadService extends JSServiceBase {
    public static final String REACT_CLASS = "JSDataUploadService";
    DataUploadService m_DataUploadService;

    public JSDataUploadService(ReactApplicationContext context) {
        super(context);
    }

    public static DataUploadService getObjFromList(String id) {
        return (DataUploadService)m_ServiceBaseList.get(id);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public static String registerId(DataUploadService obj) {
        for (Map.Entry entry : m_ServiceBaseList.entrySet()) {
            if (obj.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        Calendar calendar = Calendar.getInstance();
        String id = Long.toString(calendar.getTimeInMillis());
        m_ServiceBaseList.put(id, obj);
        return id;
    }

    @ReactMethod
    public void createObj(String url,Promise promise){
        try{
            DataUploadService dataUploadService = new DataUploadService(url);
            String dataUploadServiceId = registerId(dataUploadService);

            WritableMap map = Arguments.createMap();
            map.putString("_dataUploadServiceId_",dataUploadServiceId);
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void addDataset(String dataUploadServiceId,String fullUrl,String datasetName,int datasetType,
                           Promise promise){
        try{
            DataUploadService dataUploadService =  getObjFromList(dataUploadServiceId);
            dataUploadService.addDataset(fullUrl,datasetName,(DatasetType) Enum.parse(DatasetType.class,datasetType));

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

}

