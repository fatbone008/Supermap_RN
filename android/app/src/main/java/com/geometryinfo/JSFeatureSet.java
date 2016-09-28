package com.geometryinfo;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.supermap.services.FeatureSet;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JSFeatureSet extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "JSFeatureSet";
    protected static Map<String, FeatureSet> m_FeatureSetList = new HashMap<String, FeatureSet>();
    FeatureSet m_FeatureSet;

    public JSFeatureSet(ReactApplicationContext context) {
        super(context);
    }

    public static FeatureSet getObjFromList(String id) {
        return m_FeatureSetList.get(id);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public static String registerId(FeatureSet obj) {
        for (Map.Entry entry : m_FeatureSetList.entrySet()) {
            if (obj.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        Calendar calendar = Calendar.getInstance();
        String id = Long.toString(calendar.getTimeInMillis());
        m_FeatureSetList.put(id, obj);
        return id;
    }
}

