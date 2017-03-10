package com.supermap.rnsupermap;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.supermap.navi.Navigation;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JSNavigation extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "JSNavigation";
    protected static Map<String, Navigation> m_NavigationList = new HashMap<String, Navigation>();
    Navigation m_Navigation;

    public JSNavigation(ReactApplicationContext context) {
        super(context);
    }

    public static Navigation getObjFromList(String id) {
        return m_NavigationList.get(id);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public static String registerId(Navigation obj) {
        for (Map.Entry entry : m_NavigationList.entrySet()) {
            if (obj.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        Calendar calendar = Calendar.getInstance();
        String id = Long.toString(calendar.getTimeInMillis());
        m_NavigationList.put(id, obj);
        return id;
    }

}

