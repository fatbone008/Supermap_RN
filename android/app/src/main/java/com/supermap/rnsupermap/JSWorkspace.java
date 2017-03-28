package com.supermap.rnsupermap;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.Datasources;
import com.supermap.data.EngineType;
import com.supermap.data.Enum;
import com.supermap.data.Maps;
import com.supermap.data.Rectangle2D;
import com.supermap.data.StrokeType;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by will on 2016/6/16.
 */
public class JSWorkspace extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS="JSWorkspace";
    public static Map<String,Workspace> mWorkspaceList=new HashMap<String,Workspace>();
    Workspace m_Workspace;
    WorkspaceConnectionInfo m_WorkspaceConnectionInfo;
    private final String sdcard= android.os.Environment.getExternalStorageDirectory().getAbsolutePath().toString();

    public JSWorkspace(ReactApplicationContext context){
        super(context);
    }

    public static Workspace getObjById(String id){
        return mWorkspaceList.get(id);
    }

    public static String registerId(Workspace workspace){
        if(!mWorkspaceList.isEmpty()) {
            for(Map.Entry entry:mWorkspaceList.entrySet()){
                if(workspace.equals(entry.getValue())){
                    return (String)entry.getKey();
                }
            }
        }

        Calendar calendar=Calendar.getInstance();
        String id=Long.toString(calendar.getTimeInMillis());
        mWorkspaceList.put(id,workspace);
        return id;
    }

    @Override
    public String getName(){
        return REACT_CLASS;
    }

    @ReactMethod
    public void createObj(Promise promise){
        try{
            m_Workspace=new Workspace();
            String workspaceId = registerId(m_Workspace);
            WritableMap map = Arguments.createMap();
            map.putString("workspaceId",workspaceId);
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getDatasources(String workspaceId,Promise promise){
        try{
            m_Workspace = mWorkspaceList.get(workspaceId);
            Datasources datasources = m_Workspace.getDatasources();
            String datasourcesId = JSDatasources.registerId(datasources);

            WritableMap map = Arguments.createMap();
            map.putString("datasourcesId",datasourcesId);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void open(String workspaceId,String workspaceConnectionId, Promise promise){
        try{
            m_Workspace = mWorkspaceList.get(workspaceId);
            m_WorkspaceConnectionInfo = JSWorkspaceConnectionInfo.getObjWithId(workspaceConnectionId);
            boolean isOpen = m_Workspace.open(m_WorkspaceConnectionInfo);

            WritableMap map = Arguments.createMap();
            map.putBoolean("isOpen",isOpen);
            promise.resolve(map);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getMaps(String workspaceId,Promise promise){
        try{
            m_Workspace = mWorkspaceList.get(workspaceId);
            Maps maps = m_Workspace.getMaps();
            String mapsId = JSMaps.registerId(maps);

            WritableMap map = Arguments.createMap();
            map.putString("mapsId",mapsId);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getMapName(String workspaceId,int mapIndex,Promise promise){
        try{
            m_Workspace = mWorkspaceList.get(workspaceId);
            String mapName = m_Workspace.getMaps().get(mapIndex);

            WritableMap map = Arguments.createMap();
            map.putString("mapName",mapName);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void openDatasource(String workspaceId,String server,int engineType,String driver,Promise promise){
        try{
            Workspace workspace = getObjById(workspaceId);

            DatasourceConnectionInfo dsInfo = new DatasourceConnectionInfo();
            dsInfo.setServer(server);
            dsInfo.setEngineType((EngineType) Enum.parse(EngineType.class,engineType));
            dsInfo.setDriver(driver);

            Datasource ds = workspace.getDatasources().open(dsInfo);
            String datasourceId = JSDatasource.registerId(ds);

            WritableMap map = Arguments.createMap();
            map.putString("datasourceId",datasourceId);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void openLocalDatasource(String workspaceId,String path,int engineType,Promise promise){
        try{
            Workspace workspace = getObjById(workspaceId);

            DatasourceConnectionInfo dsInfo = new DatasourceConnectionInfo();
            dsInfo.setServer(sdcard + path);
            dsInfo.setEngineType((EngineType) Enum.parse(EngineType.class,engineType));

            Datasource ds = workspace.getDatasources().open(dsInfo);
            String datasourceId = JSDatasource.registerId(ds);

            WritableMap map = Arguments.createMap();
            map.putString("datasourceId",datasourceId);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void openDatasourceConnectionInfo(String workspaceId,String datasrouceConnectionInfoId,Promise promise){
        try {
            Workspace workspace = getObjById(workspaceId);
            DatasourceConnectionInfo datasourceConnectionInfo = JSDatasourceConnectionInfo.getObjById(datasrouceConnectionInfoId);
            Datasource datasource = workspace.getDatasources().open(datasourceConnectionInfo);
            if(datasource == null){
                throw new Exception("找不到数据源，请检查请求路径是否正确或者网络是否连接。");
            }
            String datasourceId = JSDatasource.registerId(datasource);


            WritableMap map = Arguments.createMap();
            map.putString("datasourceId",datasourceId);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getDatasource(String workspaceId,int index,Promise promise){
        try{
            Workspace workspace = getObjById(workspaceId);
            Datasource datasource = workspace.getDatasources().get(index);
            String datasourceId = JSDatasource.registerId(datasource);

            WritableMap map = Arguments.createMap();
            map.putString("datasourceId",datasourceId);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getDatasourceByName(String workspaceId,String index,Promise promise){
        try{
            Workspace workspace = getObjById(workspaceId);
            Datasource datasource = workspace.getDatasources().get(index);
            String datasourceId = JSDatasource.registerId(datasource);

            WritableMap map = Arguments.createMap();
            map.putString("datasourceId",datasourceId);
            promise.resolve(map);
        }catch(Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void openWMSDatasource(String workspaceId, String server, int engineType, String driver,
                                  String version, String visibleLayers, ReadableMap webBox,String webCoordinate,Promise promise){
        try{
            Workspace workspace = getObjById(workspaceId);

            DatasourceConnectionInfo dsInfo = new DatasourceConnectionInfo();
            dsInfo.setServer(server);
            dsInfo.setEngineType((EngineType) Enum.parse(EngineType.class,engineType));
            dsInfo.setDriver(driver);

            Rectangle2D rectangle2D = new Rectangle2D(webBox.getDouble("left"),
                    webBox.getDouble("bottom"),
                    webBox.getDouble("right"),
                    webBox.getDouble("top"));
            dsInfo.setServer(server);
            dsInfo.setWebVisibleLayers(visibleLayers);
            dsInfo.setWebBBox(rectangle2D);

            Datasource ds = workspace.getDatasources().open(dsInfo);
            promise.resolve(true);
        }catch(Exception e) {
            promise.reject(e);
        }

    }

    @ReactMethod
    public void saveWorkspace(String workspaceId,Promise promise){
        try{
            Workspace workspace = getObjById(workspaceId);

            boolean saved = workspace.save();
            WritableMap map = Arguments.createMap();
            map.putBoolean("saved",saved);
            promise.resolve(map);
        }catch(Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void createDatasource(String workspaceId,String filePath,int engineType,Promise promise){
        try{
            Workspace workspace = getObjById(workspaceId);

            DatasourceConnectionInfo info = new DatasourceConnectionInfo();
            info.setServer(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + filePath);
            info.setEngineType((EngineType) Enum.parse(EngineType.class,engineType));
            Datasource datasource = workspace.getDatasources().create(info);

            String datasourceId = JSDatasource.registerId(datasource);

            WritableMap map = Arguments.createMap();
            map.putString("datasourceId",datasourceId);
            promise.resolve(map);
        }catch(Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void closeDatasource(String workspaceId,String datasourceName,Promise promise){
        try{
            Workspace workspace = getObjById(workspaceId);

            boolean closed = workspace.getDatasources().close(datasourceName);

            WritableMap map = Arguments.createMap();
            map.putBoolean("closed",closed);
            promise.resolve(map);
        }catch(Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void closeAllDatasource(String workspaceId,Promise promise){
        try{
            Workspace workspace = getObjById(workspaceId);
            workspace.getDatasources().closeAll();

            promise.resolve(true);
        }catch(Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void removeMap(String workspaceId,String mapName,Promise promise){
        try{
            Workspace workspace = getObjById(workspaceId);
            Maps maps = workspace.getMaps();

            boolean removed = maps.remove(mapName);

            WritableMap map = Arguments.createMap();
            map.putBoolean("removed",removed);
            promise.resolve(map);
        }catch(Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void clearMap(String workspaceId,Promise promise){
        try{
            Workspace workspace = getObjById(workspaceId);
            Maps maps = workspace.getMaps();

            maps.clear();
            promise.resolve(true);
        }catch(Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getSceneName(String workspaceId,int index, Promise promise){
        try{
            Workspace workspace = getObjById(workspaceId);
            String name = workspace.getScenes().get(index);

            WritableMap map = Arguments.createMap();
            map.putString("name",name);
            promise.resolve(map);
        }catch(Exception e) {
            promise.reject(e);
        }
    }
}
