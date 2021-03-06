package com.supermap.rnsupermap;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.Datasets;
import com.supermap.data.Datasource;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by will on 2016/6/16.
 */
public class JSDatasource extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS="JSDatasource";
    public static Map<String , Datasource> m_DatasourceList = new HashMap<String , Datasource>();
    Datasource m_datasource;

    public JSDatasource(ReactApplicationContext context){
        super(context);
    }

    @Override
    public String getName(){return REACT_CLASS;}

    public static String registerId(Datasource datasource){
        for(Map.Entry entry:m_DatasourceList.entrySet())
        {
            if(datasource.equals(entry.getValue())){
                return (String) entry.getKey();
            }
        }

        Calendar calendar = Calendar.getInstance();
        String id=Long.toString(calendar.getTimeInMillis());
        m_DatasourceList.put(id,datasource);
        return id;
    }

    @ReactMethod
    public void getDatasets(String datasourceId,Promise promise){
        try {
            m_datasource = m_DatasourceList.get(datasourceId);
            Datasets datasets = m_datasource.getDatasets();
            String datasetsId=JSDatasets.registerId(datasets);

            WritableMap map = Arguments.createMap();
            map.putString("datasetsId",datasetsId);
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }
}
