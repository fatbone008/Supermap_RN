package com.supermap.rnsupermap;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.Dataset;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.services.DataDownloadService;

import java.util.Calendar;
import java.util.Map;

public class JSDataDownloadService extends JSServiceBase {
    public static final String REACT_CLASS = "JSDataDownloadService";

    public JSDataDownloadService(ReactApplicationContext context) {
        super(context);
    }

    public static DataDownloadService getObjFromList(String id) {
        return (DataDownloadService)m_ServiceBaseList.get(id);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public static String registerId(DataDownloadService obj) {
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
            DataDownloadService dataDownloadService = new DataDownloadService(url);
            String dataDownloadServiceId = registerId(dataDownloadService);

            WritableMap map = Arguments.createMap();
            map.putString("_dataDownloadServiceId_",dataDownloadServiceId);
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void download(String dataDownloadServiceId,String fullUrl,int fromIndex,int toIndex,Promise promise){
        try{
            DataDownloadService dataDownloadService = getObjFromList(dataDownloadServiceId);
            dataDownloadService.download(fullUrl,fromIndex,toIndex);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void downloadByName(String dataDownloadServiceId,String serviceName,String datasourceName,
            String datasetName,int fromIndex,int toIndex,Promise promise){
        try{
            DataDownloadService dataDownloadService = getObjFromList(dataDownloadServiceId);
            dataDownloadService.download(serviceName,datasourceName,datasetName,fromIndex,toIndex);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void downloadAll(String dataDownloadServiceId,String fullUrl,Promise promise){
        try{
            DataDownloadService dataDownloadService = getObjFromList(dataDownloadServiceId);
            dataDownloadService.downloadAll(fullUrl);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void downloadAllByName(String dataDownloadServiceId,String serviceName,String datasourceName,
                               String datasetName,Promise promise){
        try{
            DataDownloadService dataDownloadService = getObjFromList(dataDownloadServiceId);
            dataDownloadService.downloadAll(serviceName,datasourceName,datasetName);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void downloadDataset(String dataDownloadServiceId,String urlDatset,String datasourceId,Promise promise){
        try{
            DataDownloadService dataDownloadService = getObjFromList(dataDownloadServiceId);
            Datasource datasource = JSDatasource.m_DatasourceList.get(datasourceId);
            dataDownloadService.downloadDataset(urlDatset,datasource);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void updateDataset(String dataDownloadServiceId,String urlDatset,String datasetId,Promise promise){
        try{
            DataDownloadService dataDownloadService = getObjFromList(dataDownloadServiceId);
            Dataset dataset = JSDataset.getObjById(datasetId);
            dataDownloadService.updateDataset(urlDatset,(DatasetVector)dataset);

            promise.resolve(true);
        }catch (Exception e){
            promise.reject(e);
        }
    }
}

