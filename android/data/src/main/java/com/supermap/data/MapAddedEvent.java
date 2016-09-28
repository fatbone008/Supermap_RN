package com.supermap.data;

import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ’≈ºÃƒœ
 * @version 2.0
 */
public class MapAddedEvent extends EventObject {
    private String m_mapName = "";

    public MapAddedEvent(Object source,String mapName) {
        super(source);
        this.m_mapName = mapName;
    }

    public String getMapName(){
        return m_mapName;
    }
    public String toString(){
    	return "{MapName="+getMapName()+"}";
    }
}
