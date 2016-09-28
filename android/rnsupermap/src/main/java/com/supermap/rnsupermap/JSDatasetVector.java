package com.supermap.rnsupermap;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.Recordset;
import com.supermap.data.Rectangle2D;
import com.supermap.data.Enum;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JSDatasetVector extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "JSDatasetVector";
    private static Map<String, DatasetVector> m_DatasetVectorList = new HashMap<String, DatasetVector>();
    DatasetVector m_DatasetVector;

    public JSDatasetVector(ReactApplicationContext context) {
        super(context);
    }

    public DatasetVector getObjFromList(String id) {
        return m_DatasetVectorList.get(id);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public static String registerId(DatasetVector obj) {
        for (Map.Entry entry : m_DatasetVectorList.entrySet()) {
            if (obj.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        Calendar calendar = Calendar.getInstance();
        String id = Long.toString(calendar.getTimeInMillis());
        m_DatasetVectorList.put(id, obj);
        return id;
    }

    @ReactMethod
    public void query(String datasetVectorId, String rectangle2DId, int cursorType, Promise promise){
        try{
            DatasetVector datasetVector = m_DatasetVectorList.get(datasetVectorId);
            Rectangle2D rectangle2D = JSRectangle2D.getObjFromList(rectangle2DId);
            Recordset recordset = datasetVector.query(rectangle2D,(CursorType) Enum.parse(CursorType.class,cursorType));
            String recordsetId = JSRecordset.registerId(recordset);

            WritableMap map = Arguments.createMap();
            map.putString("recordsetId",recordsetId);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getRecordset(String dataVectorId,boolean isEmptyRecordset,int cursorType,Promise promise){
        try{
            DatasetVector datasetVector = getObjFromList(dataVectorId);
            Recordset recordset = datasetVector.getRecordset(isEmptyRecordset,(CursorType) Enum.parse(CursorType.class,cursorType));
            String recordsetId = JSRecordset.registerId(recordset);

            WritableMap map = Arguments.createMap();
            map.putString("recordsetId",recordsetId);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }
}

