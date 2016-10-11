package com.supermap.rnsupermap;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.Enum;
import com.supermap.data.FieldInfos;
import com.supermap.data.FieldType;
import com.supermap.data.QueryParameter;
import com.supermap.data.Recordset;
import com.supermap.data.Rectangle2D;

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
    public void queryInBuffer(String datasetVectorId, String rectangle2DId, int cursorType, Promise promise){
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

    @ReactMethod
    public void query(String dataVectorId,String queryParameterId,Promise promise){
        try{
            DatasetVector datasetVector = getObjFromList(dataVectorId);
            Recordset recordset;
            if (queryParameterId.equals("0")){
                recordset = datasetVector.getRecordset(false,CursorType.STATIC);
            }else{
                QueryParameter queryParameter = JSQueryParameter.getObjFromList(queryParameterId);
                recordset = datasetVector.query(queryParameter);
            }

            //获取字段信息
            FieldInfos fieldInfos = recordset.getFieldInfos();
            Map<String,FieldType> fields = new HashMap<>();

            for(int i=0;i<fieldInfos.getCount();i++){
                fields.put(fieldInfos.get(i).getName(),fieldInfos.get(i).getType());
            }
            //JS数组，存放
            WritableArray recordArray = Arguments.createArray();

            recordset.moveFirst();
            while(!recordset.isEmpty() && !recordset.isEOF()){
                    WritableMap recordsMap = parseRecordset(recordset,fields);
                    recordArray.pushMap(recordsMap);
                    recordset.moveNext();
            }

            WritableMap returnMap = Arguments.createMap();
            returnMap.putArray("records",recordArray);
            returnMap.putString("queryParameterId",queryParameterId);

            promise.resolve(returnMap);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    /**
     * 获取一个记录中的各项属性值
     * @param recordset 记录（静态）
     * @param fields    该记录中的所有属性
     * @return
     */
    private WritableMap parseRecordset(Recordset recordset,Map<String,FieldType> fields){
        WritableMap map = Arguments.createMap();

        for(Map.Entry field:fields.entrySet()){
            String name = (String)field.getKey();
            FieldType type = (FieldType)field.getValue();
            if(type == FieldType.DOUBLE ||
                    type == FieldType.SINGLE){
                map.putDouble(name,(Double)recordset.getFieldValue(name));
            }else if(type == FieldType.CHAR ||
                    type == FieldType.TEXT ||
                    type == FieldType.WTEXT ||
                    type == FieldType.DATETIME
                    ){
                map.putString(name,(String)recordset.getFieldValue(name));
            }else if(type == FieldType.BYTE ||
                    type == FieldType.INT16 ||
                    type == FieldType.INT32 ||
                    type == FieldType.INT64 ||
                    type == FieldType.LONGBINARY
                    ){
                map.putInt(name,(Integer)recordset.getFieldValue(name));
            }else {
                map.putBoolean(name,(Boolean) recordset.getFieldValue(name));
            }
        }
        return map;
    }
}
