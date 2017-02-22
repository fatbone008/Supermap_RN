package com.supermap.RNUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.supermap.data.FieldInfos;
import com.supermap.data.FieldType;
import com.supermap.data.Point2D;
import com.supermap.data.Recordset;
import com.supermap.data.Rectangle2D;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 将记录集recordset转换成JSON数组（非GeoJSON）
     * @param recordset 动态记录集
     * @param count 计数器
     * @param size 记录数
     * @return
     */
    public static WritableArray recordsetToJsonArray(Recordset recordset,int count,int size){
        //获取字段信息
        FieldInfos fieldInfos = recordset.getFieldInfos();
        Map<String, FieldType> fields = new HashMap<>();

        for (int i = 0; i < fieldInfos.getCount(); i++) {
            fields.put(fieldInfos.get(i).getName(), fieldInfos.get(i).getType());
        }
        //JS数组，存放
        WritableArray recordArray = Arguments.createArray();

        while (!recordset.isEmpty() && !recordset.isEOF() && count < size) {
            WritableMap recordsMap = parseRecordset(recordset, fields);
            recordArray.pushMap(recordsMap);
            recordset.moveNext();
            count++;
        }

        return recordArray;
    }

    /**
     * 获取一个记录中的各项属性值
     *
     * @param recordset 记录（静态）
     * @param fields    该记录中的所有属性
     * @return
     */
    private static WritableMap parseRecordset(Recordset recordset, Map<String, FieldType> fields) {
        WritableMap map = Arguments.createMap();

        for (Map.Entry field : fields.entrySet()) {
            String name = (String) field.getKey();
            FieldType type = (FieldType) field.getValue();
            if (type == FieldType.DOUBLE) {
                Double d = (Double) recordset.getFieldValue(name);
                map.putDouble(name, (Double) recordset.getFieldValue(name));
            } else if (type == FieldType.SINGLE) {
                BigDecimal b = new BigDecimal(recordset.getFieldValue(name).toString());
                Double d = b.doubleValue();
                map.putDouble(name, d);
            } else if (type == FieldType.CHAR ||
                    type == FieldType.TEXT ||
                    type == FieldType.WTEXT ||
                    type == FieldType.DATETIME
                    ) {
                map.putString(name, (String) recordset.getFieldValue(name));
            } else if (type == FieldType.BYTE ||
                    type == FieldType.INT16 ||
                    type == FieldType.INT32 ||
                    type == FieldType.INT64 ||
                    type == FieldType.LONGBINARY
                    ) {
                map.putInt(name, (Integer) recordset.getFieldValue(name));
            } else {
                map.putBoolean(name, (Boolean) recordset.getFieldValue(name));
            }
        }
        return map;
    }

    /**
     * 修正toGeoJson方法返回的字符串中包含“,,”跳空数组元素的情况。
     * @param json
     * @return
     */
    static public String rectifyGeoJSON(String json){
        String geojson = json.replaceAll(",,",",");
        return geojson;
    }

    /**
     * recordset.toGeoJson（）方法只能返回十条geoJSON记录，此方法用于迭代返回所有记录，
     * @param {string[]} recordset - 返回字符串数组，每个数组元素的值为recordset.toGeoJson（）每返回的一批（即十条）记录。
     * @return
     */
    static public String[] recordsetToGeoJsons(Recordset recordset){
        int total = recordset.getRecordCount();
        recordset.moveFirst();
        int batches = 0;
        String[] geoJsons = {};
        if(batches / total == 0 ){
            batches = batches / total - 1;
        }else {
            batches = batches / total;
        }
        for(int i = 0 ; i < batches ; i++){
            geoJsons[i] = recordset.toGeoJSON(true,10);
        }
        return geoJsons;
    }
}
