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
 * @author ������ jiangwh
 * @version 2.0
 */
public class GeoLine extends Geometry {
    private ArrayList m_geoLineParts; //���ڴ��point2Ds
    /**
     * ����һ����ά�߶���
     */
    public GeoLine() {
        long handle = GeoLineNative.jni_New();
        this.setHandle(handle, true);
        m_geoLineParts = new ArrayList();
    }

    /**
     * �������캯��
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
            //��ArrayList������ȿ���
            Point2Ds point2Ds = (Point2Ds) geoLine.getPartsList().get(i);
            m_geoLineParts.add(point2Ds.clone());
        }
    }

    /**
     * ���ݵ㴮����һ����ά�߶���
     * @param points Point2D[]
     */
    public GeoLine(Point2Ds points) {
        this();
        addPart(points);
    }

    //�ڲ�ʹ��
    GeoLine(long handle) {
        this.setHandle(handle, false);
        m_geoLineParts = new ArrayList();
        this.refrashPartsList();
    }


    /**
     * �����߶����Ƿ�Ϊ��
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
     * ��ά�߶���Ŀ�¡
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
     * ���մ˶���
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
     * ���ض�ά�߶���ĳ��ȣ���λ�����ݼ��ĵ�λ��ͬ��
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
     * ���ض�ά�߶�����Ӷ��������
     * �򵥶���ĸ�����ֵΪ1���߶�����պ󣬸�����ֵΪ0��
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
     * ���߶���׷��һ���Ӷ���
     * ��points.Length < 2 ʱ���׳�IllegalArgumentException
     * @param points Point2D[]   �Ӷ���ĵ㴮
     * @return int  �ɹ�������ӵ��Ӷ��������
     */
    public int addPart(Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("addPart()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int length = points.getCount();
        //�ߵ��Ӷ���ĵ���Ӧ������������
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
        //ͬ������m_GeoLineParts
        Point2Ds newPoint2Ds = new Point2Ds(points, this);
        m_geoLineParts.add(newPoint2Ds);
        return GeoLineNative.jni_AddPart(getHandle(), xs, ys);
    }

    /**
     * ɾ���߶����е�ָ��index���Ӷ���
     *  ��index >= PartCount �� index < 0ʱ�׳�IndexOutOfBoundsException
     *
     * @param index int  ָ�����Ӷ���������
     * @return boolean �ɹ�����true�����򷵻�false
     */
    public boolean removePart(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("removePart()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        //�ж������Ƿ�Խ��
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        boolean bResult = GeoLineNative.jni_RemovePart(getHandle(), index);
        if (bResult == true) {
            //ͬ������m_GeoLineParts
            m_geoLineParts.remove(index);
        }
        return bResult;
    }

    /**
     * �ڶ�ά������ָ���ľ����ҵ㣬�Ӷ�ά�ߵ���ʼ�㿪ʼ���ҡ�
     * �������Ϊ��ֵʱ���׳�IllegalArgumentException�쳣��������ľ�������ߵ��ܳ��ȣ������ߵ�ĩ�˵�
     * @param distance double  Ҫ�ҵ�ľ���
     * @return Point2D   ���ҳɹ����ص㣬���򷵻�null
     */
    public Point2D findPointOnLineByDistance(double distance) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "findPointOnLineByDistance(double distance)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        //����ľ��벻��Ϊ��ֵ
        if (distance < 0) {
            String message = InternalResource.loadString("distance",
                    InternalResource.GeoLineArgumentShouldNotBeNegative,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        //����˼·����newһ��Point2D����Ȼ����x��y���һ��point���鴫��JNI����JNI���޸�
        //x��y��ֵ��Ȼ�󴫳���
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
     * ���ڻ�ȡ�߶����е�ָ���Ӷ���ĵ㴮
     * @param index int  �Ӷ��������
     * @return Point2D[]  �ɹ�����Point2D[]����ʧ�ܷ��ؿ�ֵ��
     */
    public Point2Ds getPart(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getPart()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        //�ж������Ƿ�Խ��
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        return (Point2Ds) m_geoLineParts.get(index);
    }


    /**
     * �������߶����е�ָ��λ�ò���һ����ά���Ӷ��󣬳ɹ��򷵻� True�����򷵻� False��
     * ��index > PartCount �� index < 0ʱ�׳�IndexOutOfBoundsException
     * ��points.Length < 2 ʱ���׳�IllegalArgumentException
     * @param index int  �����λ��
     * @param pt Point2D[]  �������Ķ�λ��㴮
     * @return boolean  �ɹ��򷵻� True�����򷵻� False
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
        //�ߵ��Ӷ���ĵ���Ӧ������������
        if (length < 2) {
            String message = InternalResource.loadString("Point2Ds",
                    InternalResource.GeoLineInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);

        }
        
        boolean bResult = false;
        int partCount = getPartCount();
        //���ڵ�getPartCount == indexʱ��UGC������countλ�ò��ܲ��룬�ʴ�ʱתΪ����addPart����
        if (partCount == index) {
            int indexInsert = addPart(points);
            bResult = (indexInsert == index);
            if (bResult == true) {
                //ͬ������m_GeoLineParts
                Point2Ds newPoint2Ds = new Point2Ds(points, this);
                m_geoLineParts.add(index, newPoint2Ds);
            }
            return bResult;
        }

        //�ж������Ƿ�Խ��,������partCount��λ�ò���
        //�ж������Ƿ�Խ��
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
            //ͬ������m_GeoLineParts
            Point2Ds newPoint2Ds = new Point2Ds(points, this);
            m_geoLineParts.add(index, newPoint2Ds);
        }
        return bResult;
    }
    
    /**
     * ��ָ����λ�����޸Ķ�ά���Ӷ��󣬳ɹ��򷵻� True��
     * ��index >= PartCount �� index < 0ʱ�׳�IndexOutOfBoundsException
     * ��points.Length < 2 ʱ���׳�IllegalArgumentException
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
        //�ж������Ƿ�Խ��
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        int length = points.getCount();
        //�ߵ��Ӷ���ĵ���Ӧ������������
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
            //ͬ������m_GeoLineParts
            Point2Ds newPoint2Ds = new Point2Ds(points, this);
            m_geoLineParts.set(index, newPoint2Ds);
        }
        return bResult;
    }

    /**
     * ��ת���ζ���
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
        //ͬ������m_geoLineParts
        if (m_geoLineParts != null) {
            m_geoLineParts.clear();
            m_geoLineParts = null;
        }
    }

    ArrayList getPartsList() {
        return this.m_geoLineParts;
    }

    //����ȡ�õײ�������Part
    private Point2D[] getPartFromUGC(int index) {
        //�ж������Ƿ�Խ��
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

    //��GeoLine��UGC�ײ�ı�ʱ������m_geoLineParts���ı�m_geoLineParts�е�ֵ�����ı�����
    private void refreshFromUGC() {
        //����m_geoLineParts
        int partCount = this.getPartCount();
        for (int i = 0; i < partCount; i++) {
            Point2Ds pts = (Point2Ds) m_geoLineParts.get(i);
            //�Ƚ�pts��UserType ��ΪNone��0����Point2Ds
            pts.setUserType(UserType.NONE);
            pts.clear();
            pts.addRange(this.getPartFromUGC(i));
            //�����ֵ���ٽ�pts��UserType ��ΪgeoLine
            pts.setUserType(UserType.GEOLINE);
        }
    }

    //��ִ��XML�����󣬻���ֱ�ӵõ�handleʱ������m_geoLineParts���ı�m_geoLineParts�е�ֵ�����ı�����
     void refrashPartsList() {
        //����m_geoLineParts��������ݡ�
        int count = getPartCount();
        m_geoLineParts.clear();
        for (int i = 0; i < count; i++) {
            //��m_geoLineParts����������������ݡ�
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
	
	/**
	 * ��Geometryת����GeoJSON��ʽ���ַ���
	 * @return      ����GeoJSON�ַ���
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
	 * ��GeoJSON��ʽ���ַ����л�ȡGeometry
	 * @param geoJSON  GeoJSON�ַ���
	 * @return         �����Ƿ�ת���ɹ�
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
	 * ��GeoLine�еĵ㴮�����GeoJSON��ʽ��coordinates�ĵ������ַ���
	 * @param strBuilder   �洢ת������ַ���
	 * @param count        GeoLine������������Ŀ
	 */
	private void getCoordinatesString(StringBuilder strBuilder, int count) {
		StringBuilder pointsBuilder = new StringBuilder();
		int ptsCount = 0;
		
		for(int i=0;i<count;i++){
			if (count > 1) {
				pointsBuilder.append("["); // ���ߵ㴮���鿪ʼ�����ֻ��һ��part������Ҫ���������
			}
			
			Point2Ds pts = this.getPart(i);
			ptsCount = pts.getCount();
			for(int j = 0; j < ptsCount; j++){
				// �㴮�����ӵ�����
				pointsBuilder.append("[");
				pointsBuilder.append(pts.getItem(j).getX());
				pointsBuilder.append(",");
				pointsBuilder.append(pts.getItem(j).getY());
				pointsBuilder.append("],");
				
				// ��������̫��ʱ���ַ������̣�����append����������ÿ��1000���㣬дһ��
				if (j % 1000 == 0 && j != ptsCount - 1) {
					strBuilder.append(pointsBuilder.toString());
					pointsBuilder.delete(0, pointsBuilder.length() - 1);
				}
			}
			// ���һ���㲻�Ӷ���
			if (ptsCount > 0) {
				pointsBuilder.deleteCharAt(pointsBuilder.length() - 1);
			}
			if (count > 1) {
				pointsBuilder.append("]");// ���ߵ㴮���ݽ���
			}
			
			//ÿ�����߽�����Ӷ��ţ������һ������
			pointsBuilder.append(",");
			
			strBuilder.append(pointsBuilder.toString());
		}
		// ���һ���㲻�Ӷ���
		if (count > 0) {
			strBuilder = strBuilder.deleteCharAt(strBuilder.lastIndexOf(","));
		}
	}
}
