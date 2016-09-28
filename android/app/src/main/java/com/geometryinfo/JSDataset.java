package com.geometryinfo;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.Dataset;
import com.supermap.data.DatasetVector;
import com.supermap.data.PrjCoordSys;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by will on 2016/6/17.
 */
public class JSDataset extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS="JSDataset";
    public static Map<String , Dataset> m_DatasetList = new HashMap<String , Dataset>();
    Dataset m_dataset;

    public JSDataset(ReactApplicationContext context){
        super(context);
    }

    public static Dataset getObjById(String id){
        return m_DatasetList.get(id);
    }



    @Override
    public String getName(){return REACT_CLASS;}

    public static String registerId(Dataset dataset){
        for(Map.Entry entry:m_DatasetList.entrySet())
        {
            if(dataset.equals(entry.getValue())){
                return (String) entry.getKey();
            }
        }

        Calendar calendar = Calendar.getInstance();
        String id=Long.toString(calendar.getTimeInMillis());
        m_DatasetList.put(id,dataset);
        return id;
    }

    @ReactMethod
    public void toDatasetVector(String datasetId, Promise promise){
        try{
            DatasetVector datasetVector = (DatasetVector)m_DatasetList.get(datasetId);
            String datasetVectorId = JSDatasetVector.registerId(datasetVector);

            WritableMap map = Arguments.createMap();
            map.putString("datasetVectorId",datasetVectorId);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getPrjCoordSys(String datasetId, Promise promise){
        try{
            Dataset dataset = m_DatasetList.get(datasetId);
            PrjCoordSys prjCoordSys = dataset.getPrjCoordSys();
            String prjCoordSysId = JSPrjCoordSys.registerId(prjCoordSys);

            WritableMap map = Arguments.createMap();
            map.putString("prjCoordSysId",prjCoordSysId);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }
}
