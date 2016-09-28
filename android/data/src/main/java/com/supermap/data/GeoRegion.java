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
 * @author ���ƽ� jiangwh
 * @version 2.0
 */
public class GeoRegion extends Geometry {
    private ArrayList m_geoRegionParts; //���ڴ��point2Ds
    /**
     * Ĭ�Ϲ��캯��
     */
    public GeoRegion() {
        super.setHandle(GeoRegionNative.jni_New(), true);
        this.m_geoRegionParts = new ArrayList();
    }

    /**
     * �������캯��
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
            //��ArrayList������ȿ���
            Point2Ds point2Ds = (Point2Ds) region.getPartsList().get(i);
            m_geoRegionParts.add(point2Ds.clone());
        }
    }

    /**
     * ���캯��
     * ʹ�õ㴮����һ���������
     * ���㴮�ĳ���<3ʱ�׳��쳣IllegalArgumentException
     * @param points Point2D[]
     */
    public GeoRegion(Point2Ds points) {
        this();
        //len�˴������жϣ�addPart���ж�
       if (points.getCount()<3) {
    	   String message = InternalResource.loadString("convertToRegion",
                   InternalResource.GeoRegionInvalidPointsLength,
                   InternalResource.BundleName);
           throw new IllegalArgumentException(message);
       }
        addPart(points);
    }

    
    
    
    
    /**
     * ������������һ���Ӷ���
     * ���㴮�ĳ���<3ʱ�׳��쳣IllegalArgumentException
     * @param points Point2D[]
     * @return ��ӳɹ��򷵻�������Ӷ������������򷵻�-1
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
        //ͬ������m_geoRegionParts,�ӵײ�ȡ
        Point2Ds point2Ds = new Point2Ds(this.getPartFromUGC(index));
        Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
        m_geoRegionParts.add(newPoint2Ds);
        return index;
    }
    
    
    /**
     * ��һ���������ת������һ���߶���
     * @return GeoLine �ɹ��򷵻��߶��� ,����ͷ���null;
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
            //ע�⣺ÿ�ζ��õ�һ���µ�GeoLine��Ϊ���ͷŶ���
            geoLine.setIsDisposable(true);
            return geoLine;
        } else {
            return null;
        }
    }
    
    /**
     * ��ȡ���������ָ�����Ӷ���
     * ������Խ��ʱ�׳��쳣IndexOutOfBoundsException
     * @param index int
     * @return Point2D[]  �����Ӷ���㴮
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
     * �������������ָ��λ�ò���һ���Ӷ���
     * ����㴮�ĸ�������˲�����Դ�㴮���޸Ĳ���Ӱ���������
     * �����޸������������SetPart����
     * ���㴮�ĳ���<3ʱ�׳��쳣IllegalArgumentException
     * ������Խ��ʱ�׳��쳣IndexOutOfBoundsException
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
            //ͬ������m_geoRegionParts
            Point2Ds point2Ds = new Point2Ds(this.getPartFromUGC(newIndex));
            Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
            m_geoRegionParts.add(index, newPoint2Ds);
            return (newIndex != -1);
        } else {
            //ͬ������m_geoRegionParts
            boolean bResult = GeoRegionNative.jni_InsertPart(getHandle(), index,
                    xs, ys);
            Point2Ds point2Ds = new Point2Ds(this.getPartFromUGC(index));
            Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
            m_geoRegionParts.add(index, newPoint2Ds);
            return bResult;
        }
    }
    /**
     * ɾ������������ָ���Ӷ���
     * ������Խ��ʱ�׳��쳣IndexOutOfBoundsException
     * @param index int ��ɾ�����Ӷ��������ֵ
     * @return boolean ɾ���ɹ�����true�����򷵻�false
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
            //ͬ������m_geoRegionParts
            m_geoRegionParts.remove(index);
        }
        return bResult;
    }
    /**
     * �滻����������ָ���Ӷ���
     * ���㴮�ĳ���<3ʱ�׳��쳣IllegalArgumentException
     * ������Խ��ʱ�׳��쳣IndexOutOfBoundsException
     * @param index int ���滻���Ӷ����������
     * @param points Point2D[]�����滻�������Ӷ���ĵ㴮
     * @return boolean ���óɹ�����true�����򷵻�false
     */
    public boolean setPart(int index, Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setPart(int index, Point2Ds points)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        //points ����С��3�׳��쳣
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
            //ͬ������m_geoRegionParts���ӵײ�ȡ
            Point2Ds point2Ds = new Point2Ds(this.getPartFromUGC(index));
            Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
            m_geoRegionParts.set(index, newPoint2Ds);
        }
        return bResult;
    }
    
    
    /**
     * �ڲ���������ֻ���µײ�Point2Dsʱʹ��
     * ��Point2Ds��ͨ��������ʱ��ͬʱ����������󣬽����ƻ�������
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
        
        //points ����С��3�׳��쳣
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
     * �������
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
     * �����Ӷ�����Ŀ
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
     * �������ܳ�
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
        //����m_geoRegionParts
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
         //��ֱ�Ӹı�handleʱ��Ҫ��ԭ����handle�������ͷŵ���������ڴ�й¶��
         //������£��ö�����뱾�������Լ���Java����new�����ġ�������UGC����������ĳ�Ա��
    	 //����Ϊ���ͷ�
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

    //��geoRegion��UGC�ײ�ı�ʱ������m_geoRegionParts���ı�m_geoRegionParts�е�ֵ�����ı�����
    private void refreshFromUGC() {
        //����m_geoRegionParts
        int partCount = this.getPartCount();
        for (int i = 0; i < partCount; i++) {
            Point2Ds pts = (Point2Ds) m_geoRegionParts.get(i);
            //�Ƚ�pts��UserType ��ΪNone������Point2Ds
            pts.setUserType(Point2Ds.UserType.NONE);
            pts.clear();
            pts.addRange(this.getPartFromUGC(i));
            //�����ֵ���ٽ�pts��UserType ��ΪgeoRegion
            pts.setUserType(UserType.GEOREGION);
        }
    }

    //��ִ��XML�����󣬻���ֱ�ӵõ�handleʱ������m_geoRegionParts���ı�m_geoRegionParts�е�ֵ�����ı�����
     void refrashPartsList() {
        //����m_geoRegionParts��������ݡ�
        int count = getPartCount();
        m_geoRegionParts.clear();
        for (int i = 0; i < count; i++) {
            //��m_geoRegionParts����������������ݡ�
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
     * ���캯�����ڲ�ʹ��
     * @param handle long
     */
    GeoRegion(long handle) {
        this.setHandle(handle, false);
        m_geoRegionParts = new ArrayList();
        this.refrashPartsList();
    }
    
	// add by xuzw 2009-08-12 ���refreshHandle������CacheBuilderSCT�л��õ�
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
	 * ��GeoJSON��ʽ���ַ����л�ȡGeometry
	 * @param geoJSON  GeoJSON�ַ���
	 * @return         �����Ƿ�ת���ɹ�
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
	 * ��GeoRegion�еĵ㴮�����GeoJSON��ʽ��coordinates�ĵ������ַ���
	 * @param strBuilder   �洢ת������ַ���
	 * @param count        GeoRegion������������Ŀ
	 */
	private void getCoordinatesString(StringBuilder strBuilder, int count) {
		StringBuilder pointsBuilder = new StringBuilder();
		int ptsCount = 0;
		
		for(int i = 0;i < count; i++){
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
			
			strBuilder.append(pointsBuilder);
		}
		// ���һ���㲻�Ӷ���
		if (count > 0) {
			strBuilder = strBuilder.deleteCharAt(strBuilder.lastIndexOf(","));
		}
	}
}
