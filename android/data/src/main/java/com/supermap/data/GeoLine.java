package com.supermap.data;

import java.util.ArrayList;


import android.util.Log;

import com.supermap.data.Point2Ds.UserType;
import com.supermap.imb.jsonlib.SiJsonArray;
import com.supermap.imb.jsonlib.SiJsonObject;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 孔令亮 jiangwh
 * @version 2.0
 */
public class GeoLine extends Geometry {
    private ArrayList m_geoLineParts; //用于存放point2Ds
    /**
     * 创建一个二维线对象
     */
    public GeoLine() {
        long handle = GeoLineNative.jni_New();
        this.setHandle(handle, true);
        m_geoLineParts = new ArrayList();
    }

    /**
     * 拷贝构造函数
     * @param geoLine GeoLine
     */
    public GeoLine(GeoLine geoLine) {
        if (geoLine == null) {
            String message = InternalResource.loadString("geoLine",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (geoLine.getHandle() == 0) {
            String message = InternalResource.loadString("geoLine",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = GeoLineNative.jni_Clone(geoLine.getHandle());
        this.setHandle(handle, true);
        m_geoLineParts = new ArrayList();
        for (int i = 0; i < geoLine.getPartsList().size(); i++) {
            //对ArrayList进行深度拷贝
            Point2Ds point2Ds = (Point2Ds) geoLine.getPartsList().get(i);
            m_geoLineParts.add(point2Ds.clone());
        }
    }

    /**
     * 根据点串创建一个二维线对象
     * @param points Point2D[]
     */
    public GeoLine(Point2Ds points) {
        this();
        addPart(points);
    }

    //内部使用
    GeoLine(long handle) {
        this.setHandle(handle, false);
        m_geoLineParts = new ArrayList();
        this.refrashPartsList();
    }


    /**
     * 返回线对象是否为空
     * @return boolean
     */
    public boolean isEmpty() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getIsEmpty()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        // getPartCount will check the handle
        return this.getPartCount() == 0;
    }

    /**
     * 二维线对象的克隆
     * @return Geometry
     */
    public GeoLine clone() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("clone()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        // the copy constructor will check the handle
        return new GeoLine(this);
    }

    /**
     * 回收此对象
     */
    public void dispose() {
        if (!this.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
            GeoLineNative.jni_Delete(getHandle());
            this.setHandle(0);

            clearHandle();
        }
    }

    /**
     * 返回二维线对象的长度，单位与数据集的单位相同。
     * @return double
     */
    public double getLength() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getLength()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoLineNative.jni_GetLength(getHandle());
    }

    /**
     * 返回二维线对象的子对象个数。
     * 简单对象的该属性值为1，线对象清空后，该属性值为0。
     * @return int
     */
    public int getPartCount() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getPartCount()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoLineNative.jni_GetPartCount(getHandle());
    }

    /**
     * 向线对象追加一个子对象。
     * 当points.Length < 2 时，抛出IllegalArgumentException
     * @param points Point2D[]   子对象的点串
     * @return int  成功返回添加的子对象的索引
     */
    public int addPart(Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("addPart()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int length = points.getCount();
        //线的子对象的点数应该是两个以上
        if (length < 2) {
            String message = InternalResource.loadString("points",
                    InternalResource.GeoLineInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        double[] xs = new double[length];
        double[] ys = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        //同步更新m_GeoLineParts
        Point2Ds newPoint2Ds = new Point2Ds(points, this);
        m_geoLineParts.add(newPoint2Ds);
        return GeoLineNative.jni_AddPart(getHandle(), xs, ys);
    }

    /**
     * 删除线对象中的指定index的子对象
     *  当index >= PartCount 或 index < 0时抛出IndexOutOfBoundsException
     *
     * @param index int  指定的子对象索引号
     * @return boolean 成功返回true，否则返回false
     */
    public boolean removePart(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("removePart()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        //判断索引是否越界
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        boolean bResult = GeoLineNative.jni_RemovePart(getHandle(), index);
        if (bResult == true) {
            //同步更新m_GeoLineParts
            m_geoLineParts.remove(index);
        }
        return bResult;
    }

    /**
     * 在二维线上以指定的距离找点，从二维线的起始点开始查找。
     * 当传入的为负值时，抛出IllegalArgumentException异常。当传入的距离大于线的总长度，返回线的末端点
     * @param distance double  要找点的距离
     * @return Point2D   查找成功返回点，否则返回null
     */
    public Point2D findPointOnLineByDistance(double distance) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "findPointOnLineByDistance(double distance)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        //传入的距离不可为负值
        if (distance < 0) {
            String message = InternalResource.loadString("distance",
                    InternalResource.GeoLineArgumentShouldNotBeNegative,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        //基本思路是先new一个Point2D对象，然后其x，y组成一个point数组传入JNI，在JNI中修改
        //x，y的值，然后传出。
        Point2D pt = new Point2D();
        if(getPartCount() > 0){
            double[] point = new double[2];
            GeoLineNative.jni_FindPointOnLineByDistance(getHandle(), distance,
                    point);
            pt.setX(point[0]);
            pt.setY(point[1]);
        }
        return pt;
    }

    /**
     * 用于获取线对象中的指定子对象的点串
     * @param index int  子对象的索引
     * @return Point2D[]  成功返回Point2D[]对象，失败返回空值。
     */
    public Point2Ds getPart(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getPart()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        //判断索引是否越界
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        return (Point2Ds) m_geoLineParts.get(index);
    }


    /**
     * 用于往线对象中的指定位置插入一个二维线子对象，成功则返回 True，否则返回 False。
     * 当index > PartCount 或 index < 0时抛出IndexOutOfBoundsException
     * 当points.Length < 2 时，抛出IllegalArgumentException
     * @param index int  插入的位置
     * @param pt Point2D[]  插入对象的定位点点串
     * @return boolean  成功则返回 True，否则返回 False
     */
    public boolean insertPart(int index, Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "insertPart(int index, Point2Ds points)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        int length = points.getCount();
        //线的子对象的点数应该是两个以上
        if (length < 2) {
            String message = InternalResource.loadString("Point2Ds",
                    InternalResource.GeoLineInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);

        }
        
        boolean bResult = false;
        int partCount = getPartCount();
        //由于当getPartCount == index时，UGC在索引count位置不能插入，故此时转为调用addPart方法
        if (partCount == index) {
            int indexInsert = addPart(points);
            bResult = (indexInsert == index);
            if (bResult == true) {
                //同步更新m_GeoLineParts
                Point2Ds newPoint2Ds = new Point2Ds(points, this);
                m_geoLineParts.add(index, newPoint2Ds);
            }
            return bResult;
        }

        //判断索引是否越界,可以在partCount的位置插入
        //判断索引是否越界
        if (index < 0 || index > getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        
        double[] xs = new double[length];
        double[] ys = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        bResult = GeoLineNative.jni_InsertPart(getHandle(), index, xs, ys);
        if (bResult == true) {
            //同步更新m_GeoLineParts
            Point2Ds newPoint2Ds = new Point2Ds(points, this);
            m_geoLineParts.add(index, newPoint2Ds);
        }
        return bResult;
    }
    
    /**
     * 在指定的位置上修改二维线子对象，成功则返回 True。
     * 当index >= PartCount 或 index < 0时抛出IndexOutOfBoundsException
     * 当points.Length < 2 时，抛出IllegalArgumentException
     * @param index int
     * @param pt Point2D[]
     * @return boolean
     */
    public boolean setPart(int index, Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setPart(int index, Point2Ds points)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        //判断索引是否越界
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        int length = points.getCount();
        //线的子对象的点数应该是两个以上
        if (length < 2) {
            String message = InternalResource.loadString("Point2Ds",
                    InternalResource.GeoLineInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);

        }

        double[] xs = new double[length];
        double[] ys = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        boolean bResult = GeoLineNative.jni_SetPart(getHandle(), index, xs, ys);
        if (bResult == true) {
            //同步更新m_GeoLineParts
            Point2Ds newPoint2Ds = new Point2Ds(points, this);
            m_geoLineParts.set(index, newPoint2Ds);
        }
        return bResult;
    }

    /**
     * 旋转几何对象
     * @param basePoint Point2D
     * @param angle double
     */
    public void rotate(Point2D basePoint, double angle) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "rotate(Point2D basePoint, double angle)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        super.rotate(basePoint, angle);
        this.refreshFromUGC();
    }

    protected void clearHandle() {
        super.clearHandle();
        //同步更新m_geoLineParts
        if (m_geoLineParts != null) {
            m_geoLineParts.clear();
            m_geoLineParts = null;
        }
    }

    ArrayList getPartsList() {
        return this.m_geoLineParts;
    }

    //用于取得底层真正的Part
    private Point2D[] getPartFromUGC(int index) {
        //判断索引是否越界
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        int length = GeoLineNative.jni_GetPartPointCount(getHandle(),
                index);
        if (length > 1) {
            Point2D[] point2Ds = new Point2D[length];
            double[] xs = new double[length];
            double[] ys = new double[length];
            GeoLineNative.jni_GetPart(getHandle(), index, xs, ys);
            for (int i = 0; i < length; i++) {
                point2Ds[i] = new Point2D(xs[i], ys[i]);
            }
            return point2Ds;
        } else {
            return null;
        }
    }

    //当GeoLine的UGC底层改变时，更新m_geoLineParts。改变m_geoLineParts中的值，不改变引用
    private void refreshFromUGC() {
        //更新m_geoLineParts
        int partCount = this.getPartCount();
        for (int i = 0; i < partCount; i++) {
            Point2Ds pts = (Point2Ds) m_geoLineParts.get(i);
            //先将pts的UserType 设为None，0即纯Point2Ds
            pts.setUserType(UserType.NONE);
            pts.clear();
            pts.addRange(this.getPartFromUGC(i));
            //添加完值后，再将pts的UserType 设为geoLine
            pts.setUserType(UserType.GEOLINE);
        }
    }

    //当执行XML操作后，或者直接得到handle时，构造m_geoLineParts。改变m_geoLineParts中的值，不改变引用
     void refrashPartsList() {
        //处理m_geoLineParts的相关内容。
        int count = getPartCount();
        m_geoLineParts.clear();
        for (int i = 0; i < count; i++) {
            //向m_geoLineParts等中添加新增的内容。
            Point2Ds point2Ds = new Point2Ds(this.getPartFromUGC(i));
            Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
            m_geoLineParts.add(newPoint2Ds);
        }
    }

	@Override
	public boolean fromJson(SiJsonObject json) {
		if(super.fromJson(json)){
			
			SiJsonArray parts = json.getJsonArray("parts");
			SiJsonArray points = json.getJsonArray("points");
			
			int count = parts.getArraySize();
			int partCount = 0;
			int index = 0;
			for(int i=0;i<count;i++){
				partCount = parts.getInt(i);
				Point2Ds pts = new Point2Ds();
				for(int j=0;j<partCount;j++){
					Point2D pt = new Point2D();
					SiJsonObject ptJson = points.getJsonObject( index++ );
					if(!pt.fromJson(ptJson)){
						ptJson.dispose();
						continue;
					}
					ptJson.dispose();
					pts.add(pt);
				}
				this.addPart(pts);
			}
			parts.dispose();
			points.dispose();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean fromJson(String json) {
		SiJsonObject obj = new SiJsonObject(json);
		boolean ret = fromJson(obj);
		obj.dispose();
		return ret;
		
	}

	@Override
	public String toJson() {
		StringBuilder sb = new StringBuilder(super.toJson());
		sb.deleteCharAt(sb.length()-1);
		sb.append(",");
		
		String parts = "";
		int count  = this.getPartCount();
		for(int i=0;i<count;i++){
			parts += this.getPart(i).getCount();
			if(i != count-1){
				parts += ",";
			}
		}
		sb.append(" \"parts\": " + "["+ parts+"]" + ",");
		
		sb.append(" \"type\": " + "LINE" + ",");
		String points = "";
		int partPoints = 0;
		for(int i=0;i<count;i++){
			Point2Ds pts = this.getPart(i);
			partPoints = pts.getCount();
			for(int j=0;j<partPoints;j++){
				points += pts.getItem(j).toJson();
				if(i == count-1 && j == partPoints-1){
					//这个时候不加逗号
				}else{
					points += ",";
				}
			}
		}
		sb.append(" \"points\" :" + "[" + points + "]");
		
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 将Geometry转换成GeoJSON格式的字符串
	 * @return      返回GeoJSON字符串
	 */
	@Override
	public String toGeoJSON() {

		StringBuilder strBuilder = new StringBuilder();
		int count  = this.getPartCount();
		if (count > 1) {
			strBuilder.append("{\"type\": \"MultiLineString\",");
		} else {
			strBuilder.append("{\"type\": \"LineString\",");
		}
		
		strBuilder.append("\"coordinates\":[");
		
		getCoordinatesString(strBuilder, count);
		strBuilder.append("]}");
		return strBuilder.toString();

	}
	
	/**
	 * 从GeoJSON格式的字符串中获取Geometry
	 * @param geoJSON  GeoJSON字符串
	 * @return         返回是否转换成功
	 */
	@Override
	public boolean fromGeoJSON(String geoJSON){
		if (geoJSON.contains("LineString")|| geoJSON.contains("MultiLineString")) {
			ArrayList<Point2Ds> ptsList = getPointsFromGeoJSON(geoJSON);
			for (int i = 0, size = ptsList.size(); i < size; i++) {
				this.addPart(ptsList.get(i));
			}
		} else {
			Log.e("GeoLine", "Not match the type of LineString or MultiLineString");
			return false;
		}

		return true;
	}
	
	/*************************************************************/
	
	/**
	 * 将GeoLine中的点串构造成GeoJSON格式的coordinates的点数组字符串
	 * @param strBuilder   存储转换后的字符串
	 * @param count        GeoLine包含的子线数目
	 */
	private void getCoordinatesString(StringBuilder strBuilder, int count) {
		StringBuilder pointsBuilder = new StringBuilder();
		int ptsCount = 0;
		
		for(int i=0;i<count;i++){
			if (count > 1) {
				pointsBuilder.append("["); // 子线点串数组开始，如果只有一个part，则不需要这个方括号
			}
			
			Point2Ds pts = this.getPart(i);
			ptsCount = pts.getCount();
			for(int j = 0; j < ptsCount; j++){
				// 点串中增加点数组
				pointsBuilder.append("[");
				pointsBuilder.append(pts.getItem(j).getX());
				pointsBuilder.append(",");
				pointsBuilder.append(pts.getItem(j).getY());
				pointsBuilder.append("],");
				
				// 避免数据太多时，字符串过程，导致append崩溃，这里每隔1000个点，写一次
				if (j % 1000 == 0 && j != ptsCount - 1) {
					strBuilder.append(pointsBuilder.toString());
					pointsBuilder.delete(0, pointsBuilder.length() - 1);
				}
			}
			// 最后一个点不加逗号
			if (ptsCount > 0) {
				pointsBuilder.deleteCharAt(pointsBuilder.length() - 1);
			}
			if (count > 1) {
				pointsBuilder.append("]");// 子线点串数据结束
			}
			
			//每条子线结束后加逗号，但最后一条除外
			pointsBuilder.append(",");
			
			strBuilder.append(pointsBuilder.toString());
		}
		// 最后一个点不加逗号
		if (count > 0) {
			strBuilder = strBuilder.deleteCharAt(strBuilder.lastIndexOf(","));
		}
	}
}
