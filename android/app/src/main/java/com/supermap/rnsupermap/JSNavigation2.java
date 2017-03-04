package com.supermap.rnsupermap;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.supermap.data.Dataset;
import com.supermap.data.DatasetVector;
import com.supermap.navi.Navigation2;

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
            navigation2.loadModel(sdcard + path);
            promise.resolve(true);
        }catch(Exception e){
            promise.reject(e);
        }
    }
}
