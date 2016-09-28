package com.supermap.rnsupermap;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.Datasources;
import com.supermap.data.Maps;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by will on 2016/6/16.
 */
public class JSWorkspace extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS="JSWorkspace";
    public static Map<String,Workspace> mWorkspaceList=new HashMap<String,Workspace>();
    Workspace m_Workspace;
    WorkspaceConnectionInfo m_WorkspaceConnectionInfo;

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
}
