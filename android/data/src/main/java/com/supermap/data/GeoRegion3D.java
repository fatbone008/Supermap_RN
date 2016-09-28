/**
 * 
 */
package com.supermap.data;

import java.util.ArrayList;

import com.supermap.imb.jsonlib.SiJsonArray;
import com.supermap.imb.jsonlib.SiJsonObject;

/**
 * @author konglingliang
 * 
 */
public class GeoRegion3D extends Geometry3D {
	private ArrayList<Point3Ds> m_geoRegion3DParts; // 用于存放point3Ds

	public GeoRegion3D() {
		super.setHandle(GeoRegion3DNative.jni_New(), true);
		this.m_geoRegion3DParts = new ArrayList<Point3Ds>();
	}

	public GeoRegion3D(Point3Ds points) {
		this();
		addPart(points);
	}

	public GeoRegion3D(GeoRegion3D region3D) {
		if (region3D == null) {
			String message = InternalResource.loadString("region3D",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (region3D.getHandle() == 0) {
			String message = InternalResource.loadString("region3D",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoRegion3DNative.jni_Clone(region3D.getHandle());
		super.setHandle(handle, true);
		m_geoRegion3DParts = new ArrayList();
		for (int i = 0; i < region3D.getPartsList().size(); i++) {
			// 对ArrayList进行深度拷贝
			Point3Ds point3Ds = region3D.getPartsList().get(i);
			m_geoRegion3DParts.add((Point3Ds) point3Ds.clone());
		}
	}

	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoRegion3DNative.jni_Delete(getHandle());
			setHandle(0);
			clearHandle();
		}
	}

	public double getArea() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getArea()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoRegion3DNative.jni_GetArea(getHandle());
	}

	public int getPartCount() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getPartCount()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoRegion3DNative.jni_GetPartCount(getHandle());
	}

	 double getPerimeter() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getPerimeter()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoRegion3DNative.jni_GetPerimeter(getHandle());

	}

	public int addPart(Point3Ds points) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("addPart(points)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int len = points.getCount();
		if (len < 3) {
			String message = InternalResource.loadString("points",
					InternalResource.GeoRegion3DInvalidPointsLength,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		double[] xs = new double[len];
		double[] ys = new double[len];
		double[] zs = new double[len];
		int i;
		for (i = 0; i < len; i++) {
			xs[i] = points.getItem(i).getX();
			ys[i] = points.getItem(i).getY();
			zs[i] = points.getItem(i).getZ();
		}
		int index = GeoRegion3DNative.jni_AddPart(getHandle(), xs, ys, zs);
		// 同步更新m_geoRegion3DParts,从底层取
		Point3Ds point3Ds = new Point3Ds(this.getPartFromUGC(index));
		Point3Ds newPoint3Ds = new Point3Ds(point3Ds, this);
		m_geoRegion3DParts.add(newPoint3Ds);
		return index;
	}

	public GeoLine3D convertToLine() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("convertToLine()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		GeoLine3D geo = new GeoLine3D();
		
		for(int n=0;n<this.getPartCount();n++)
		{
			geo.addPart(this.getPart(n));
		}
		geo.setIsDisposable(true);
		return geo;
	}

	 boolean removePart(int index) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("removePart(index)",
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
		boolean bResult = GeoRegion3DNative.jni_RemovePart(getHandle(), index);
		if (bResult == true) {
			// 同步更新m_geoRegion3DParts
			m_geoRegion3DParts.remove(index);
		}
		return bResult;
	}

	public Point3Ds getPart(int index) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getPart(index)",
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
		return m_geoRegion3DParts.get(index);
	}

	 boolean insertPart(int index, Point3Ds points) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"insertPart(index, points)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (index < 0 || index > getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		int len = points.getCount();
		if (len < 3) {
			String message = InternalResource.loadString("points",
					InternalResource.GeoRegion3DInvalidPointsLength,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		double[] xs = new double[len];
		double[] ys = new double[len];
		double[] zs = new double[len];
		int i;
		for (i = 0; i < len; i++) {
			xs[i] = points.getItem(i).getX();
			ys[i] = points.getItem(i).getY();
			zs[i] = points.getItem(i).getZ();
		}
		if (index == getPartCount()) {
			int newIndex = GeoRegion3DNative.jni_AddPart(getHandle(), xs, ys,
					zs);
			// 同步更新m_geoRegion3DParts
			Point3Ds point3Ds = new Point3Ds(this.getPartFromUGC(newIndex));
			Point3Ds newPoint3Ds = new Point3Ds(point3Ds, this);
			m_geoRegion3DParts.add(index, newPoint3Ds);
			return (newIndex != -1);
		} else {
			// 同步更新m_geoRegionParts
			boolean bResult = GeoRegion3DNative.jni_InsertPart(getHandle(),
					index, xs, ys, zs);
			Point3Ds point3Ds = new Point3Ds(this.getPartFromUGC(index));
			Point3Ds newPoint3Ds = new Point3Ds(point3Ds, this);
			m_geoRegion3DParts.add(index, newPoint3Ds);
			return bResult;
		}
	}

	 boolean setPart(int index, Point3Ds points) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setPart(index, points)",
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

		// points 长度小于3抛出异常
		if (points.getCount() < 3) {
			String message = InternalResource.loadString("points",
					InternalResource.GeoRegion3DInvalidPointsLength,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		int len = points.getCount();
		double[] xs = new double[len];
		double[] ys = new double[len];
		double[] zs = new double[len];
		int i;
		for (i = 0; i < len; i++) {
			xs[i] = points.getItem(i).getX();
			ys[i] = points.getItem(i).getY();
			zs[i] = points.getItem(i).getZ();
		}
		boolean bResult = GeoRegion3DNative.jni_SetPart(getHandle(), index, xs,
				ys, zs);
		if (bResult == true) {
			// 同步更新m_geoRegion3DParts，从底层取
			Point3Ds point3Ds = new Point3Ds(this.getPartFromUGC(index));
			Point3Ds newPoint3Ds = new Point3Ds(point3Ds, this);
			m_geoRegion3DParts.set(index, newPoint3Ds);
		}
		return bResult;
	}
	
	/**
	 * 内部方法，在只更新底层Point3Ds时使用
     * 当Point3Ds是通过连点获得时，同时更新组件对象，将会破坏连点性
	 * @param index
	 * @param points
	 * @return
	 */
	boolean setPartJustToUGC(int index, Point3Ds points) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setPart(index, points)",
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

		// points 长度小于3抛出异常
		if (points.getCount() < 3) {
			String message = InternalResource.loadString("points",
					InternalResource.GeoRegion3DInvalidPointsLength,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		int len = points.getCount();
		double[] xs = new double[len];
		double[] ys = new double[len];
		double[] zs = new double[len];
		int i;
		for (i = 0; i < len; i++) {
			xs[i] = points.getItem(i).getX();
			ys[i] = points.getItem(i).getY();
			zs[i] = points.getItem(i).getZ();
		}
		boolean bResult = GeoRegion3DNative.jni_SetPart(getHandle(), index, xs,
				ys, zs);
		return bResult;
	}

	 int indexOf(Point3Ds part) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("indexOf(part)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return m_geoRegion3DParts.indexOf(part);
	}

	protected void clearHandle() {
		super.clearHandle();
		// 更新m_geoRegion3DParts
		if (m_geoRegion3DParts != null) {
			m_geoRegion3DParts.clear();
			m_geoRegion3DParts = null;
		}
		this.setHandle(0);
	}

	ArrayList<Point3Ds> getPartsList() {
		return this.m_geoRegion3DParts;
	}

	private Point3D[] getPartFromUGC(int index) {
		// "getPartCount" will check the handle
		if (index < 0 || index >= this.getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		int length = GeoRegion3DNative
				.jni_GetPartPointCount(getHandle(), index);
		if (length > 2) {
			Point3D[] point3Ds = new Point3D[length];
			double[] xs = new double[length];
			double[] ys = new double[length];
			double[] zs = new double[length];
			GeoRegion3DNative.jni_GetPart(getHandle(), index, xs, ys, zs);
			for (int i = 0; i < length; i++) {
				point3Ds[i] = (new Point3D(xs[i], ys[i], zs[i]));
			}
			return point3Ds;
		} else {
			return null;
		}
	}
   //
	@Override
	public GeoRegion3D clone() {
		if (this.getHandle() == 0) {
            String message = InternalResource.loadString("clone()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        //the copy constructor will check the handle
        return new GeoRegion3D(this);
	}

	@Override
	public boolean isEmpty() {
		 if (this.getHandle() == 0) {
	            String message = InternalResource.loadString("isEmpty()",
	                    InternalResource.HandleObjectHasBeenDisposed,
	                    InternalResource.BundleName);
	            throw new IllegalStateException(message);
	        }
	        // getPartCount will check the handle
	        return (getPartCount() == 0);
	}
	
	//xuzw 2008-11-5 添加GeoRegion3D的内部构造函数
    /**
     * 构造函数，内部使用
     * @param handle long
     */
    GeoRegion3D(long handle) {
        this.setHandle(handle, false);
        m_geoRegion3DParts = new ArrayList();
        this.refrashPartsList();
    }   
	// 当执行XML操作后，或者直接得到handle时，构造m_geoLineParts。改变m_geoLineParts中的值，不改变引用
	private void refrashPartsList() {
		// 处理m_geoLineParts的相关内容。
		int count = getPartCount();
		m_geoRegion3DParts.clear();
		for (int i = 0; i < count; i++) {
			// 向m_geoRegion3DParts等中添加新增的内容。
			Point3Ds point3Ds = new Point3Ds(this.getPartFromUGC(i));
			Point3Ds newPoint3Ds = new Point3Ds(point3Ds, this);
			m_geoRegion3DParts.add(newPoint3Ds);
		}
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
				Point3Ds pts = new Point3Ds();
				for(int j=0;j<partCount;j++){
					Point3D pt = new Point3D();
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
		
		sb.append(" \"type\": " + "\"REGION3D\"" + ",");
		String points = "";
		int partPoints = 0;
		for(int i=0;i<count;i++){
			Point3Ds pts = this.getPart(i);
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
	
}
