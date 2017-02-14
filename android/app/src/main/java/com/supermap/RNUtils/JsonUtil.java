package com.supermap.RNUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.Point2D;
import com.supermap.data.Rectangle2D;

/**
 * Created by will on 2017/2/13.
 */

public class JsonUtil {
    /**
     * Rectangle 转 JSON 方法
     * @param rectangle2D
     */
    public static WritableMap rectangleToJson(Rectangle2D rectangle2D){
        double top = rectangle2D.getTop();
        double bottom = rectangle2D.getBottom();
        double left = rectangle2D.getLeft();
        double height = rectangle2D.getHeight();
        double width = rectangle2D.getWidth();
        double right = rectangle2D.getRight();

        Point2D center = rectangle2D.getCenter();
        double x = center.getX();
        double y = center.getY();
        WritableMap centerMap = Arguments.createMap();
        centerMap.putDouble("x" , x);
        centerMap.putDouble("y" , y);

        WritableMap wMap = Arguments.createMap();
        wMap.putMap("centerMap",centerMap);
        wMap.putDouble("top",top);
        wMap.putDouble("bottom",bottom);
        wMap.putDouble("left",left);
        wMap.putDouble("height",height);
        wMap.putDouble("width",width);
        wMap.putDouble("right",right);
        return wMap;
    }

    /**
     * json转Rectangle方法
     * @param readableMap
     * @return
     * @throws Exception
     */
    public static Rectangle2D jsonToRectangle(ReadableMap readableMap) throws Exception{
            double top = readableMap.getDouble("top");
            double left = readableMap.getDouble("left");
            double bottom = readableMap.getDouble("bottom");
            double right = readableMap.getDouble("right");

        Rectangle2D r = new Rectangle2D(left,bottom,right,top);
        return r;
    }
}
