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
public class GeoLine3D extends Geometry3D {
	private ArrayList<Point3Ds> m_geoLine3DParts; // 用于存放point3Ds

	public GeoLine3D() {
		long handle = GeoLine3DNative.jni_New();
		this.setHandle(handle, true);
		m_geoLine3DParts = new ArrayList<Point3Ds>();
	}

	public GeoLine3D(Point3Ds points) {
		this();
		addPart(points);
	}

	public GeoLine3D(GeoLine3D geoLine3D) {
		if (geoLine3D == null) {
			String message = InternalResource.loadString("geoLine3D",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geoLine3D.getHandle() == 0) {
			String message = InternalResource.loadString("geoLine3D",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoLine3DNative.jni_Clone(geoLine3D.getHandle());
		this.setHandle(handle, true);
		m_geoLine3DParts = new ArrayList<Point3Ds>();
		for (int i = 0; i < geoLine3D.getPartsList().size(); i++) {
			// 对ArrayList进行深度拷贝
			Point3Ds point3Ds = geoLine3D.getPartsList().get(i);
			m_geoLine3DParts.add((Point3Ds) point3Ds.clone());
		}
	}

	// 内部使用
	GeoLine3D(long handle) {
		this(handle, false);
	}

	GeoLine3D(long handle, boolean disposable) {
		this.setHandle(handle, disposable);
		m_geoLine3DParts = new ArrayList();
		this.refrashPartsList();
	}

	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoLine3DNative.jni_Delete(getHandle());
			this.setHandle(0);
			clearHandle();
		}
	}

	 double getLength() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getLength()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoLine3DNative.jni_GetLength(getHandle());
	}

	public int getPartCount() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getPartCount()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoLine3DNative.jni_GetPartCount(getHandle());
	}

	public int addPart(Point3Ds points) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("addPart(points)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		int length = points.getCount();
		// 线的子对象的点数应该是两个以上
		if (length < 2) {
			String message = InternalResource.loadString("points",
					InternalResource.GeoLine3DInvalidPointsLength,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		double[] xs = new double[length];
		double[] ys = new double[length];
		double[] zs = new double[length];
		for (int i = 0; i < length; i++) {
			xs[i] = points.getItem(i).getX();
			ys[i] = points.getItem(i).getY();
			zs[i] = points.getItem(i).getZ();
		}
		// 同步更新m_geoLine3DParts
		Point3Ds newPoint3Ds = new Point3Ds(points, this);
		m_geoLine3DParts.add(newPoint3Ds);
		return GeoLine3DNative.jni_AddPart(getHandle(), xs, ys, zs);

	}

	public boolean removePart(int index) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("removePart(index)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// 判断索引是否越界
		if (index < 0 || index >= getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		boolean bResult = GeoLine3DNative.jni_RemovePart(getHandle(), index);
		if (bResult == true) {
			// 同步更新m_geoLine3DParts
			m_geoLine3DParts.remove(index);
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
		// 判断索引是否越界
		if (index < 0 || index >= getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		return m_geoLine3DParts.get(index);
	}

	public boolean insertPart(int index, Point3Ds points) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"insertPart(index, points)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean bResult = false;
		int partCount = getPartCount();
		// 由于当getPartCount == index时，UGC在索引count位置不能插入，故此时转为调用addPart方法
		if (partCount == index) {
			int indexInsert = addPart(points);
			bResult = (indexInsert == index);
			if (bResult == true) {
				// 同步更新m_GeoLine3DParts
				Point3Ds newPoint3Ds = new Point3Ds(points, this);
				m_geoLine3DParts.add(index, newPoint3Ds);
			}
			return bResult;
		}

		// 判断索引是否越界,可以在partCount的位置插入
		// 判断索引是否越界
		if (index < 0 || index > getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}

		int length = points.getCount();
		// 线的子对象的点数应该是两个以上
		if (length < 2) {
			String message = InternalResource.loadString("Point2Ds",
					InternalResource.GeoLineInvalidPointsLength,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);

		}
		double[] xs = new double[length];
		double[] ys = new double[length];
		double[] zs = new double[length];
		for (int i = 0; i < length; i++) {
			xs[i] = points.getItem(i).getX();
			ys[i] = points.getItem(i).getY();
			zs[i] = points.getItem(i).getZ();
		}
		bResult = GeoLine3DNative
				.jni_InsertPart(getHandle(), index, xs, ys, zs);
		if (bResult == true) {
			// 同步更新m_GeoLine3DParts
			Point3Ds newPoint3Ds = new Point3Ds(points, this);
			m_geoLine3DParts.add(index, newPoint3Ds);
		}
		return bResult;
	}

	public boolean setPart(int index, Point3Ds points) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setPart(index, points)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// 判断索引是否越界
		if (index < 0 || index >= getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		int length = points.getCount();
		// 线的子对象的点数应该是两个以上
		if (length < 2) {
			String message = InternalResource.loadString("points",
					InternalResource.GeoLine3DInvalidPointsLength,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);

		}

		double[] xs = new double[length];
		double[] ys = new double[length];
		double[] zs = new double[length];
		for (int i = 0; i < length; i++) {
			xs[i] = points.getItem(i).getX();
			ys[i] = points.getItem(i).getY();
			zs[i] = points.getItem(i).getZ();
		}
		boolean bResult = GeoLine3DNative.jni_SetPart(getHandle(), index, xs,
				ys, zs);
		if (bResult == true) {
			// 同步更新m_GeoLine3DParts
			Point3Ds newPoint3Ds = new Point3Ds(points, this);
			m_geoLine3DParts.set(index, newPoint3Ds);
		}
		return bResult;
	}

	/**
	 * 该方法为内部使用方法，当Point3Ds由连点获得时使用 只更新底层GeoLine3D中Point3Ds对象，而不同步更新组件层Point3Ds
	 * 因为在更新组件层Point3Ds对象之后会破坏连点性
	 * 
	 * @param index
	 * @param points
	 * @return
	 */
	boolean setPartJustToUGC(int index, Point3Ds points) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setPartJustToUGC(index, points)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		// 判断索引是否越界
		if (index < 0 || index >= getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		int length = points.getCount();
		// 线的子对象的点数应该是两个以上
		if (length < 2) {
			String message = InternalResource.loadString("points",
					InternalResource.GeoLine3DInvalidPointsLength,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);

		}

		double[] xs = new double[length];
		double[] ys = new double[length];
		double[] zs = new double[length];
		for (int i = 0; i < length; i++) {
			xs[i] = points.getItem(i).getX();
			ys[i] = points.getItem(i).getY();
			zs[i] = points.getItem(i).getZ();
		}
		boolean bResult = GeoLine3DNative.jni_SetPart(getHandle(), index, xs,
				ys, zs);
		return bResult;
	}

	public int indexOf(Point3Ds part) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("indexOf(part)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return m_geoLine3DParts.indexOf(part);
	}

	/**
	 * 把三维线对象转换为三维面对象，成功返回三维面对象。 对于没有封闭的三维线对象，转换为三维面对象时，会把首尾自动连起来。
	 * 
	 * @return GeoRegion3D
	 */
	public GeoRegion3D convertToRegion() {
		int partCount = this.getPartCount();

		// 注意：此处每次都得到一个新的region3D。为可释放。
		GeoRegion3D region3D = new GeoRegion3D();
		for (int i = 0; i < partCount; i++) {
			// 三维线对象的子对象
			Point3Ds point3Ds = this.getPart(i);
			int point3DsCount = point3Ds.getCount();
			if (point3DsCount < 3) {
				String message = InternalResource.loadString(
						"convertToRegion()",
						InternalResource.GeoLine3DUnsupportOperation,
						InternalResource.BundleName);
				throw new UnsupportedOperationException(message);
			}

			// 当前子对象是闭合的
			Point3Ds regionPoint3Ds = (Point3Ds) point3Ds.clone();
			if (point3Ds.getItem(0).equals(point3Ds.getItem(point3DsCount - 1))) {
				region3D.addPart(regionPoint3Ds);
			}
			// 子对象不闭合，再加个子对象的起始点
			else {
				regionPoint3Ds.add(point3Ds.getItem(0));
				region3D.addPart(regionPoint3Ds);
			}
		}
		return region3D;
	}

	protected void clearHandle() {
		super.clearHandle();
		// 同步更新m_geoLine3DParts
		if (m_geoLine3DParts != null) {
			m_geoLine3DParts.clear();
			m_geoLine3DParts = null;
		}
	}

	ArrayList<Point3Ds> getPartsList() {
		return this.m_geoLine3DParts;
	}

	// 当执行XML操作后，或者直接得到handle时，构造m_geoLineParts。改变m_geoLineParts中的值，不改变引用
	private void refrashPartsList() {
		// 处理m_geoLineParts的相关内容。
		int count = getPartCount();
		m_geoLine3DParts.clear();
		for (int i = 0; i < count; i++) {
			// 向m_geoLine3DParts等中添加新增的内容。
			Point3Ds point3Ds = new Point3Ds(this.getPartFromUGC(i));
			Point3Ds newPoint3Ds = new Point3Ds(point3Ds, this);
			m_geoLine3DParts.add(newPoint3Ds);
		}
	}

	// 用于取得底层真正的Part
	private Point3D[] getPartFromUGC(int index) {
		// 判断索引是否越界
		if (index < 0 || index >= getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		int length = GeoLine3DNative.jni_GetPartPointCount(getHandle(), index);
		if (length > 1) {
			Point3D[] point3Ds = new Point3D[length];
			double[] xs = new double[length];
			double[] ys = new double[length];
			double[] zs = new double[length];
			GeoLine3DNative.jni_GetPart(getHandle(), index, xs, ys, zs);
			for (int i = 0; i < length; i++) {
				point3Ds[i] = new Point3D(xs[i], ys[i], zs[i]);
			}
			return point3Ds;
		} else {
			return null;
		}
	}

	@Override
	public GeoLine3D clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return new GeoLine3D(this);
	}

	@Override
	public boolean isEmpty() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("isEmpty()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return this.getPartCount() == 0;
	}

	protected static GeoLine3D creatInstance(long handle, boolean disposable) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new GeoLine3D(handle, disposable);
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
		
		sb.append(" \"type\": " + "LINE3D" + ",");
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
