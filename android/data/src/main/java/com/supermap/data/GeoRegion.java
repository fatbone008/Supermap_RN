/**
 * 
 */
package com.supermap.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * @author 李云锦 jiangwh
 * @version 2.0
 */
public class GeoRegion extends Geometry {
    private ArrayList m_geoRegionParts; //用于存放point2Ds
    /**
     * 默认构造函数
     */
    public GeoRegion() {
        super.setHandle(GeoRegionNative.jni_New(), true);
        this.m_geoRegionParts = new ArrayList();
    }

    /**
     * 拷贝构造函数
     * @param region GeoRegion
     */
    public GeoRegion(GeoRegion region) {
        if (region == null) {
            String message = InternalResource.loadString("region",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (region.getHandle() == 0) {
            String message = InternalResource.loadString("region",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = GeoRegionNative.jni_Clone(region.getHandle());
        super.setHandle(handle, true);
        m_geoRegionParts = new ArrayList(region.getPartsList().size());
        for (int i = 0; i < region.getPartsList().size(); i++) {
            //对ArrayList进行深度拷贝
            Point2Ds point2Ds = (Point2Ds) region.getPartsList().get(i);
            m_geoRegionParts.add(point2Ds.clone());
        }
    }

    /**
     * 构造函数
     * 使用点串创建一个区域对象
     * 当点串的长度<3时抛出异常IllegalArgumentException
     * @param points Point2D[]
     */
    public GeoRegion(Point2Ds points) {
        this();
        //len此处不做判断，addPart已判断
       if (points.getCount()<3) {
    	   String message = InternalResource.loadString("convertToRegion",
                   InternalResource.GeoRegionInvalidPointsLength,
                   InternalResource.BundleName);
           throw new IllegalArgumentException(message);
       }
        addPart(points);
    }

    
    
    
    
    /**
     * 往区域对象添加一个子对象。
     * 当点串的长度<3时抛出异常IllegalArgumentException
     * @param points Point2D[]
     * @return 添加成功则返回新添加子对象索引，否则返回-1
     */
    public int addPart(Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "addPart(Point2Ds points)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int len = points.getCount();
        if (len < 3) {
            String message = InternalResource.loadString("points",
                    InternalResource.GeoRegionInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        double[] xs = new double[len];
        double[] ys = new double[len];
        int i;
        for (i = 0; i < len; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        int index = GeoRegionNative.jni_AddPart(getHandle(), xs, ys);
        //同步更新m_geoRegionParts,从底层取
        Point2Ds point2Ds = new Point2Ds(this.getPartFromUGC(index));
        Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
        m_geoRegionParts.add(newPoint2Ds);
        return index;
    }
    
    
    /**
     * 把一个区域对象转换生成一个线对象
     * @return GeoLine 成功则返回线对象 ,否则就返回null;
     */
    public GeoLine convertToLine() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("convertToLine()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        //if current GeoRegion is empty, the call will return an empty GeoLine
        long ptr = GeoRegionNative.jni_ConvertToLine(getHandle());
        if (ptr != 0) {
            GeoLine geoLine = new GeoLine(ptr);
            //注意：每次都得到一个新的GeoLine，为可释放对象。
            geoLine.setIsDisposable(true);
            return geoLine;
        } else {
            return null;
        }
    }
    
    /**
     * 获取区域对象中指定的子对象
     * 当索引越界时抛出异常IndexOutOfBoundsException
     * @param index int
     * @return Point2D[]  返回子对象点串
     */
    public Point2Ds getPart(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getPart(int index)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (index < 0 || index >= this.getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        return (Point2Ds)  m_geoRegionParts.get(index);
    }
    
    
    /**
     * 往本区域对象在指定位置插入一个子对象
     * 插入点串的副本，因此插入后对源点串的修改不会影响区域对象，
     * 如需修改区域对象，请用SetPart方法
     * 当点串的长度<3时抛出异常IllegalArgumentException
     * 当索引越界时抛出异常IndexOutOfBoundsException
     * @param index int
     * @param points Point2D[]
     * @return boolean
     */
    public boolean insertPart(int index, Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "insertPart(int index, Point2Ds points)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        int len = points.getCount();
        if (len < 3) {
            String message = InternalResource.loadString("points",
                    InternalResource.GeoRegionInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        
        if (index < 0 || index > getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        double[] xs = new double[len];
        double[] ys = new double[len];
        int i;
        for (i = 0; i < len; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        if (index == getPartCount()) {
            int newIndex = GeoRegionNative.jni_AddPart(getHandle(), xs, ys);
            //同步更新m_geoRegionParts
            Point2Ds point2Ds = new Point2Ds(this.getPartFromUGC(newIndex));
            Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
            m_geoRegionParts.add(index, newPoint2Ds);
            return (newIndex != -1);
        } else {
            //同步更新m_geoRegionParts
            boolean bResult = GeoRegionNative.jni_InsertPart(getHandle(), index,
                    xs, ys);
            Point2Ds point2Ds = new Point2Ds(this.getPartFromUGC(index));
            Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
            m_geoRegionParts.add(index, newPoint2Ds);
            return bResult;
        }
    }
    /**
     * 删除本区域对象的指定子对象
     * 当索引越界时抛出异常IndexOutOfBoundsException
     * @param index int 被删除的子对象的索引值
     * @return boolean 删除成功返回true，否则返回false
     */
    public boolean removePart(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "removePart(int index)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        
        boolean bResult = GeoRegionNative.jni_RemovePart(getHandle(), index);
        if (bResult == true) {
            //同步更新m_geoRegionParts
            m_geoRegionParts.remove(index);
        }
        return bResult;
    }
    /**
     * 替换本区域对象的指定子对象
     * 当点串的长度<3时抛出异常IllegalArgumentException
     * 当索引越界时抛出异常IndexOutOfBoundsException
     * @param index int 被替换的子对象的索引号
     * @param points Point2D[]用以替换的区域子对象的点串
     * @return boolean 设置成功返回true，否则返回false
     */
    public boolean setPart(int index, Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setPart(int index, Point2Ds points)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        //points 长度小于3抛出异常
        if (points.getCount() < 3) {
            String message = InternalResource.loadString("points",
                    InternalResource.GeoRegionInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        //getPartCount will check the handle
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

      
        int len = points.getCount();
        double[] xs = new double[len];
        double[] ys = new double[len];
        int i;
        for (i = 0; i < len; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        boolean bResult = GeoRegionNative.jni_SetPart(getHandle(), index, xs,
                ys);
        if (bResult == true) {
            //同步更新m_geoRegionParts，从底层取
            Point2Ds point2Ds = new Point2Ds(this.getPartFromUGC(index));
            Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
            m_geoRegionParts.set(index, newPoint2Ds);
        }
        return bResult;
    }
    
    
    /**
     * 内部方法，在只更新底层Point2Ds时使用
     * 当Point2Ds是通过连点获得时，同时更新组件对象，将会破坏连点性
     * @param index
     * @param points
     * @return
     */
    boolean setPartJustToUGC(int index, Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setPart(int index, Point2Ds points)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        //points 长度小于3抛出异常
        if (points.getCount() < 3) {
            String message = InternalResource.loadString("points",
                    InternalResource.GeoRegionInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        //getPartCount will check the handle
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

      
        int len = points.getCount();
        double[] xs = new double[len];
        double[] ys = new double[len];
        int i;
        for (i = 0; i < len; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        boolean bResult = GeoRegionNative.jni_SetPart(getHandle(), index, xs,
                ys);
        return bResult;
    }
    
    /**
     * 返回面积
     * @return double
     */
    public double getArea() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getArea()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoRegionNative.jni_GetArea(getHandle());
    }

    /**
     * 返回子对象数目
     * @return int
     */
    public int getPartCount() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getPartCount()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoRegionNative.jni_GetPartCount(getHandle());
    }

    /**
     * 返回总周长
     * @return double
     */
    public double getPerimeter() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getPerimeter()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoRegionNative.jni_GetPerimeter(getHandle());
    }

    protected void clearHandle() {
        super.clearHandle();
        //更新m_geoRegionParts
        if (m_geoRegionParts != null) {
            m_geoRegionParts.clear();
            m_geoRegionParts = null;
        }
        this.setHandle(0);
    }

    protected static void clearHandle(GeoRegion region) {
        region.clearHandle();
    }
    
    protected void changeHandle(long regionHandle){
    	 if (regionHandle == 0) {
             String message = InternalResource.loadString("regionHandle",
                     InternalResource.GlobalInvalidConstructorArgument,
                     InternalResource.BundleName);
             throw new IllegalArgumentException(message);
         }
         //当直接改变handle时，要将原来的handle对象先释放调，否则会内存泄露。
         //此情况下，该对象必须本质上是自己在Java层中new出来的。不可是UGC中其他对象的成员。
    	 //设置为可释放
    	 this.setIsDisposable(true);
    	 this.dispose();
    	 this.setHandle(regionHandle,false);  	
    }
    
    protected static void changeHandle(GeoRegion region,long regionHandle){
    	region.changeHandle(regionHandle);
    }

    ArrayList getPartsList() {
        return this.m_geoRegionParts;
    }


    private Point2D[] getPartFromUGC(int index) {
        //"getPartCount" will check the handle
        if (index < 0 || index >= this.getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

        int length = GeoRegionNative.jni_GetPartPointCount(getHandle(),
                index);
        if (length > 2) {
            Point2D point2Ds[] = new Point2D[length];
            double[] xs = new double[length];
            double[] ys = new double[length];
            GeoRegionNative.jni_GetPart(getHandle(), index, xs, ys);
            for (int i = 0; i < length; i++) {
                point2Ds[i] = (new Point2D(xs[i], ys[i]));
            }
            return point2Ds;
        } else {
            return null;
        }
    }

    //当geoRegion的UGC底层改变时，更新m_geoRegionParts。改变m_geoRegionParts中的值，不改变引用
    private void refreshFromUGC() {
        //更新m_geoRegionParts
        int partCount = this.getPartCount();
        for (int i = 0; i < partCount; i++) {
            Point2Ds pts = (Point2Ds) m_geoRegionParts.get(i);
            //先将pts的UserType 设为None，即纯Point2Ds
            pts.setUserType(Point2Ds.UserType.NONE);
            pts.clear();
            pts.addRange(this.getPartFromUGC(i));
            //添加完值后，再将pts的UserType 设为geoRegion
            pts.setUserType(UserType.GEOREGION);
        }
    }

    //当执行XML操作后，或者直接得到handle时，构造m_geoRegionParts。改变m_geoRegionParts中的值，不改变引用
     void refrashPartsList() {
        //处理m_geoRegionParts的相关内容。
        int count = getPartCount();
        m_geoRegionParts.clear();
        for (int i = 0; i < count; i++) {
            //向m_geoRegionParts等中添加新增的内容。
            Point2Ds point2Ds = new Point2Ds(this.
                                             getPartFromUGC(i));
            Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
            this.m_geoRegionParts.add(newPoint2Ds);
        }
    }
	


	/* (non-Javadoc)
	 * @see com.supermap.data.Geometry#clone()
	 */
	@Override
	public GeoRegion clone() {
		
		if (this.getHandle() == 0) {
            String message = InternalResource.loadString("clone()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        //the copy constructor will check the handle
        return new GeoRegion(this);
	}

	/* (non-Javadoc)
	 * @see com.supermap.data.Geometry#dispose()
	 */
	@Override
	public void dispose() {
	        if (!this.getIsDisposable()) {
	            String message = InternalResource.loadString("dispose()",
	                    InternalResource.HandleUndisposableObject,
	                    InternalResource.BundleName);
	            throw new UnsupportedOperationException(message);
	        } else if (this.getHandle() != 0) {
	            GeoRegionNative.jni_Delete(getHandle());
	            setHandle(0);

	            clearHandle();
	        }
	}
	
    public boolean isEmpty() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getIsEmpty()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        // getPartCount will check the handle
        return (getPartCount() == 0);
    }
	
    /**
     * 构造函数，内部使用
     * @param handle long
     */
    GeoRegion(long handle) {
        this.setHandle(handle, false);
        m_geoRegionParts = new ArrayList();
        this.refrashPartsList();
    }
    
	// add by xuzw 2009-08-12 添加refreshHandle方法，CacheBuilderSCT中会用到
	protected void refreshHandle(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		this.setHandle(handle, false);
	}
	
	protected static void refreshHandle(GeoRegion geoRegion, long handle) {
		geoRegion.refreshHandle(handle);
	}

	@Override
	public boolean fromJson(String json) {
		SiJsonObject obj = new SiJsonObject(json);
		boolean ret = fromJson(obj);
		obj.dispose();
		return ret;
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
		
		sb.append(" \"type\": " + "\"REGION\"" + ",");
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
	public String toGeoJSON(){

		StringBuilder strBuilder = new StringBuilder();
		int count  = this.getPartCount();

		strBuilder.append("{\"type\": \"Polygon\",");
		
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
		if (!geoJSON.contains("Polygon")) {
			Log.e("GeoRegion", "Not match the type of Polygon");
			return false;
		}

		ArrayList<Point2Ds> ptsList = getPointsFromGeoJSON(geoJSON);
		for (int i = 0, size = ptsList.size(); i < size; i++) {
			this.addPart(ptsList.get(i));
		}
		return true;
	}

	/***********************************************************/
	
	/**
	 * 将GeoRegion中的点串构造成GeoJSON格式的coordinates的点数组字符串
	 * @param strBuilder   存储转换后的字符串
	 * @param count        GeoRegion包含的子线数目
	 */
	private void getCoordinatesString(StringBuilder strBuilder, int count) {
		StringBuilder pointsBuilder = new StringBuilder();
		int ptsCount = 0;
		
		for(int i = 0;i < count; i++){
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
			
			strBuilder.append(pointsBuilder);
		}
		// 最后一个点不加逗号
		if (count > 0) {
			strBuilder = strBuilder.deleteCharAt(strBuilder.lastIndexOf(","));
		}
	}
}
