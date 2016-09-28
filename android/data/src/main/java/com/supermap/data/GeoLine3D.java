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
	private ArrayList<Point3Ds> m_geoLine3DParts; // ���ڴ��point3Ds

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
			// ��ArrayList������ȿ���
			Point3Ds point3Ds = geoLine3D.getPartsList().get(i);
			m_geoLine3DParts.add((Point3Ds) point3Ds.clone());
		}
	}

	// �ڲ�ʹ��
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
		// �ߵ��Ӷ���ĵ���Ӧ������������
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
		// ͬ������m_geoLine3DParts
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
		// �ж������Ƿ�Խ��
		if (index < 0 || index >= getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		boolean bResult = GeoLine3DNative.jni_RemovePart(getHandle(), index);
		if (bResult == true) {
			// ͬ������m_geoLine3DParts
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
		// �ж������Ƿ�Խ��
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
		// ���ڵ�getPartCount == indexʱ��UGC������countλ�ò��ܲ��룬�ʴ�ʱתΪ����addPart����
		if (partCount == index) {
			int indexInsert = addPart(points);
			bResult = (indexInsert == index);
			if (bResult == true) {
				// ͬ������m_GeoLine3DParts
				Point3Ds newPoint3Ds = new Point3Ds(points, this);
				m_geoLine3DParts.add(index, newPoint3Ds);
			}
			return bResult;
		}

		// �ж������Ƿ�Խ��,������partCount��λ�ò���
		// �ж������Ƿ�Խ��
		if (index < 0 || index > getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}

		int length = points.getCount();
		// �ߵ��Ӷ���ĵ���Ӧ������������
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
			// ͬ������m_GeoLine3DParts
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
		// �ж������Ƿ�Խ��
		if (index < 0 || index >= getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		int length = points.getCount();
		// �ߵ��Ӷ���ĵ���Ӧ������������
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
			// ͬ������m_GeoLine3DParts
			Point3Ds newPoint3Ds = new Point3Ds(points, this);
			m_geoLine3DParts.set(index, newPoint3Ds);
		}
		return bResult;
	}

	/**
	 * �÷���Ϊ�ڲ�ʹ�÷�������Point3Ds��������ʱʹ�� ֻ���µײ�GeoLine3D��Point3Ds���󣬶���ͬ�����������Point3Ds
	 * ��Ϊ�ڸ��������Point3Ds����֮����ƻ�������
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
		// �ж������Ƿ�Խ��
		if (index < 0 || index >= getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		int length = points.getCount();
		// �ߵ��Ӷ���ĵ���Ӧ������������
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
	 * ����ά�߶���ת��Ϊ��ά����󣬳ɹ�������ά����� ����û�з�յ���ά�߶���ת��Ϊ��ά�����ʱ�������β�Զ���������
	 * 
	 * @return GeoRegion3D
	 */
	public GeoRegion3D convertToRegion() {
		int partCount = this.getPartCount();

		// ע�⣺�˴�ÿ�ζ��õ�һ���µ�region3D��Ϊ���ͷš�
		GeoRegion3D region3D = new GeoRegion3D();
		for (int i = 0; i < partCount; i++) {
			// ��ά�߶�����Ӷ���
			Point3Ds point3Ds = this.getPart(i);
			int point3DsCount = point3Ds.getCount();
			if (point3DsCount < 3) {
				String message = InternalResource.loadString(
						"convertToRegion()",
						InternalResource.GeoLine3DUnsupportOperation,
						InternalResource.BundleName);
				throw new UnsupportedOperationException(message);
			}

			// ��ǰ�Ӷ����Ǳպϵ�
			Point3Ds regionPoint3Ds = (Point3Ds) point3Ds.clone();
			if (point3Ds.getItem(0).equals(point3Ds.getItem(point3DsCount - 1))) {
				region3D.addPart(regionPoint3Ds);
			}
			// �Ӷ��󲻱պϣ��ټӸ��Ӷ������ʼ��
			else {
				regionPoint3Ds.add(point3Ds.getItem(0));
				region3D.addPart(regionPoint3Ds);
			}
		}
		return region3D;
	}

	protected void clearHandle() {
		super.clearHandle();
		// ͬ������m_geoLine3DParts
		if (m_geoLine3DParts != null) {
			m_geoLine3DParts.clear();
			m_geoLine3DParts = null;
		}
	}

	ArrayList<Point3Ds> getPartsList() {
		return this.m_geoLine3DParts;
	}

	// ��ִ��XML�����󣬻���ֱ�ӵõ�handleʱ������m_geoLineParts���ı�m_geoLineParts�е�ֵ�����ı�����
	private void refrashPartsList() {
		// ����m_geoLineParts��������ݡ�
		int count = getPartCount();
		m_geoLine3DParts.clear();
		for (int i = 0; i < count; i++) {
			// ��m_geoLine3DParts����������������ݡ�
			Point3Ds point3Ds = new Point3Ds(this.getPartFromUGC(i));
			Point3Ds newPoint3Ds = new Point3Ds(point3Ds, this);
			m_geoLine3DParts.add(newPoint3Ds);
		}
	}

	// ����ȡ�õײ�������Part
	private Point3D[] getPartFromUGC(int index) {
		// �ж������Ƿ�Խ��
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
					//���ʱ�򲻼Ӷ���
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
