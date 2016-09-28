package com.supermap.data;

import java.util.ArrayList;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ������
 * @version 2.0
 */
public class Point2Ds {
    private ArrayList m_point2Ds = null;
    private GeoLine m_geoLine = null;
    private GeoRegion m_geoRegion = null;
    private UserType m_userType = UserType.NONE;

    //add by kangwb 2012-5-22
    private GeoCardinal m_geoCardinal = null;
    private GeoBSpline m_geoBSpline = null;
    private GeoCurve m_geoCurve = null;
    
    public Point2Ds() {
        m_point2Ds = new ArrayList();
        this.m_userType = UserType.NONE;
    }

    public Point2Ds(Point2D[] points) {
        this();
        this.addRange(points);
    }

    public Point2Ds(Point2Ds points) {
        this();
        int size = points.getCount();
        for (int i = 0; i < size; i++) {
            m_point2Ds.add(points.getItem(i).clone());
        }
    }

    Point2Ds(Point2Ds points, GeoLine geoLine) {
        this(points);
        this.m_geoLine = geoLine;
        this.m_userType = UserType.GEOLINE;
    }

    Point2Ds(Point2Ds points, GeoRegion geoRegion) {
        this(points);
        this.m_geoRegion = geoRegion;
        this.m_userType = UserType.GEOREGION;
    }
    
    Point2Ds(Point2Ds points, GeoCardinal geoCardinal) {
        this(points);
        this.m_geoCardinal = geoCardinal;
        this.m_userType = UserType.GEOCARDINAL;
    }
    
    Point2Ds(Point2Ds points, GeoBSpline geoBSpline) {
        this(points);
        this.m_geoBSpline = geoBSpline;
        this.m_userType = UserType.GEOBSPLINE;
    }
    Point2Ds(Point2Ds points, GeoCurve geoCurve) {
        this(points);
        this.m_geoCurve = geoCurve;
        this.m_userType = UserType.GEOCURVE;
    }

    public int getCount() {
        if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } 
        
        return m_point2Ds.size();
    }

    //���ص���Item�ĸ���
    public Point2D getItem(int index) {
        if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
            	//������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
            	//������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } 

        Point2D pt = (Point2D) m_point2Ds.get(index);
        return (Point2D) pt.clone();
    }

    /**
     * ����ָ����ŵĵ����
     * @param index
     * @param point2D
     */
    public void setItem(int index, Point2D point2D) {
    	if(point2D.isEmpty()){
    		return;                                    // �����óɿյĵ����
    	}
        if (this.m_userType.value() == UserType.NONE.value()) {
            m_point2Ds.set(index, point2D.clone());
        } else if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("setItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLine.getPartsList().indexOf(this);
            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("setItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            m_point2Ds.set(index, point2D);
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("setItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("setItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            if (index == 0) {
                //��Ϊ���õ�һ����ʱ���Ƚ��͵�һ����һ�µ����һ�����Ƴ�
                m_point2Ds.remove(m_point2Ds.size() - 1);
            } else if (index == m_point2Ds.size() - 1) {
                //��Ϊ�������һ����ʱ�������õ�һ��������һ����һ��
                m_point2Ds.set(0, point2D);
            }
            m_point2Ds.set(index, point2D);
            m_geoRegion.setPartJustToUGC(indexRegion, this);

        } 
    }


    public Point2Ds clone() {
        if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("clone()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("clone()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("clone()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("clone()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } 

        return new Point2Ds(this);

    }

    /**
     * �߼���ĩβ����һ����
     * @param pt   �����ӵĵ㣬�������յĵ����
     * @return     index,�������ӵ�����
     */
    public int add(Point2D pt) {
        int indexAdded = -1;
        int indexRemove = -1;
        boolean bSuccess = false;
        // �յĵ��������Ч�㣬�����ڹ����߶���
        if(pt.isEmpty()){
        	
        	return indexAdded;
        }
        if (this.m_userType.value() == UserType.NONE.value()) {
            indexRemove = m_point2Ds.size() - 1; //�ȼ�����GeoRegion���������Ҫ�Ƴ�������
            bSuccess = m_point2Ds.add(pt.clone());
            if (bSuccess) {
                indexAdded = m_point2Ds.size() - 1;
            }
        } else if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("add()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLine.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("add()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            indexRemove = m_point2Ds.size() - 1; //�ȼ�����GeoRegion���������Ҫ�Ƴ�������
            bSuccess = m_point2Ds.add(pt.clone());
            if (bSuccess) {
                indexAdded = m_point2Ds.size() - 1;
            }

            int index = m_geoLine.getPartsList().indexOf(this);
            m_geoLine.setPart(index, this);
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("add()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("add()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            indexRemove = m_point2Ds.size() - 1; //�ȼ�����GeoRegion���������Ҫ�Ƴ�������

            bSuccess = m_point2Ds.add(pt.clone());
            if (bSuccess) {
                indexAdded = m_point2Ds.size() - 1;
            }

            //��ΪGeoLine��part��addʱ����Ҫ�Ƚ�δ���ʱ�����һ�����Ƴ�����
            m_point2Ds.remove(indexRemove);

            m_geoRegion.setPart(indexRegion, this);
        } 

        return indexAdded;
    }

   
    /**
     * �߼���ĩβ����һϵ�е㣬��Щ���ɵ�����������
     * @param points   �����ӵĵ�����飬�������յĵ����
     * @return        �������ӵĵ������
     */
    public int addRange(Point2D[] points) {
        int length = points.length;
        int countInvalid = 0;                    // ��¼��Ч��(���յĵ����)�ĸ���
        int indexRemove = m_point2Ds.size() - 1; //�ȼ�����GeoRegion���������Ҫ�Ƴ�������
        if (this.m_userType.value() == UserType.NONE.value()) {
			for (int i = 0; i < length; i++) {
				if(points[i].isEmpty()){
					countInvalid ++;
				}else{
					m_point2Ds.add(points[i].clone());
				}
			}
        } else if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("addRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLine.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("addRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

			for (int i = 0; i < length; i++) {
				if(points[i].isEmpty()){
					countInvalid ++;
				}else{
					m_point2Ds.add(points[i].clone());
				}
			}

            m_geoLine.setPart(indexLine, this);
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("addRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("addRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
			for (int i = 0; i < length; i++) {
				if(points[i].isEmpty()){
					countInvalid ++;
				}else{
					m_point2Ds.add(points[i].clone());
				}
			}
            //��ΪGeoLine��part��addRangeʱ����Ҫ�Ƚ�δ���ʱ�����һ�����Ƴ�����
            m_point2Ds.remove(indexRemove);
            m_geoRegion.setPart(indexRegion, this);
        } 
       
        return length - countInvalid;
    }

    /**
     * �߼����д�ָ��λ�������һ����
     * @param pt   ������ĵ�, �����ǿյĵ����
     * @return     ������Ƿ�ɹ���trueΪ�ɹ���falseΪʧ��
     */
    public boolean insert(int index, Point2D pt) {
        if(index<0 || index>this.getCount()){
           String message = InternalResource.loadString("insert(int index, Point2D pt)",
                   InternalResource.GlobalIndexOutOfBounds,
                   InternalResource.BundleName);
               throw new IllegalArgumentException(message);
       }

        boolean bSuccess = false;
        // ������յĵ����
        if(pt.isEmpty()){
        	return bSuccess;
        }
        int indexRemove = m_point2Ds.size() - 1; //�ȼ�����GeoRegion���������Ҫ�Ƴ�������
        int size = m_point2Ds.size();
        if (this.m_userType.value() == UserType.NONE.value()) {

            m_point2Ds.add(index, pt.clone());
            if (m_point2Ds.size() == size + 1) {
                bSuccess = true;
            } else {
                bSuccess = false;
            }
        } else if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("insert()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLine.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("insert()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            m_point2Ds.add(index, pt.clone());
            if (m_point2Ds.size() == size + 1) {
                bSuccess = true;
            } else {
                bSuccess = false;
            }

            m_geoLine.setPart(indexLine, this);
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {

            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("insert()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("insert()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            m_point2Ds.add(index, pt.clone());
            if (m_point2Ds.size() == size + 1) {
                bSuccess = true;
            } else {
                bSuccess = false;
            }

            //�����ڵ�һ��λ�ò���ʱ,��Ҫ�Ƚ��͵�һ����һ�µ����һ�����Ƴ�
            if (index == 0) {
                //����ʵ���Ѿ���ӣ����Ƴ�������ΪindexRemove + 1
                m_point2Ds.remove(indexRemove + 1);
            }

            //���������һ���������ʱ����Ҫ�Ƚ����һ�����Ƴ���
            if (index == m_point2Ds.size()) {
                m_point2Ds.remove(indexRemove);
            }
            m_geoRegion.setPart(indexRegion, this);
        } 
        
        return bSuccess;
    }
    
    /**
     * �߼����д�ָ��λ�������һϵ�е㣬��Щ���ɵ�����������
     * @param points   ������ĵ�����飬�������յĵ����
     * @return         ����ĵ������
     */
    public int insertRange(int index, Point2D[] points) {
        if(index<0 || index>this.getCount()){
            String message = InternalResource.loadString("insertRange(int index, Point2D[] points)",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
                throw new IllegalArgumentException(message);
        }
        int indexRemove = m_point2Ds.size() - 1; //�ȼ�����GeoRegion���������Ҫ�Ƴ�������
        
        int length = points.length;
        int countInvalid = 0;
        if (this.m_userType.value() == UserType.NONE.value()) {
            for (int i = 0; i < length; i++) {
            	if(points[i].isEmpty()){
					countInvalid ++;
				}else{
					int realIndex = index + i - countInvalid;
	                m_point2Ds.add(realIndex, points[i].clone());
				}
            }
        } else if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("insertRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLine.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("insertRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            for (int i = 0; i < length; i++) {
            	if(points[i].isEmpty()){
					countInvalid ++;
				}else{
					int realIndex = index + i - countInvalid;
	                m_point2Ds.add(realIndex, points[i].clone());
				}
            }
            m_geoLine.setPart(indexLine, this);
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("insertRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("insertRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            for (int i = 0; i < length; i++) {
            	if(points[i].isEmpty()){
					countInvalid ++;
				}else{
					int realIndex = index + i - countInvalid;
	                m_point2Ds.add(realIndex, points[i].clone());
				}
            }

            //�����ڵ�һ��λ�ò���ʱ,��Ҫ�Ƚ��͵�һ����һ�µ����һ�����Ƴ�
            if (index == 0) {
                m_point2Ds.remove(indexRemove + length - countInvalid);
            }

            //���������һ���������ʱ����Ҫ�Ƚ����һ�����Ƴ���
            if (index == m_point2Ds.size()) {
                m_point2Ds.remove(indexRemove);
            }
            m_geoRegion.setPart(indexRegion, this);
        } 
        
        return length - countInvalid;
    }

    public boolean remove(int index) {
        if (index < 0 || index >= this.getCount()) {
            String message = InternalResource.loadString(
                    "remove(int index)",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        Point2D oldValue = null;
        Point2D removeValue = null;
        boolean bResult = false;
        if (this.m_userType.value() == UserType.NONE.value()) {
            oldValue = (Point2D) m_point2Ds.get(index);
            removeValue = (Point2D) m_point2Ds.remove(index);
            if (oldValue.equals(removeValue)) {
                bResult = true;
            }
        } else if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            //��Point2Ds ΪgeoLine��partʱ����Point2Ds�ĸ�����count��С��2ʱ����ִ��removeʱ�׳�UnsupportedOperationException�쳣��
            if (this.getCount() < 2) {
                String message = InternalResource.loadString("remove()",
                        InternalResource.Point2DsInvalidPointLength,
                        InternalResource.BundleName);
                throw new UnsupportedOperationException(message);
            }
            oldValue = (Point2D) m_point2Ds.get(index);
            removeValue = (Point2D) m_point2Ds.remove(index);
            if (oldValue.equals(removeValue)) {
                m_geoLine.setPart(indexLine, this);
                bResult = true;
            }
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            //��Point2Ds ΪgeoRegion��partʱ����Point2Ds�ĸ�����count��С��4ʱ����ִ��removeʱ�׳�UnsupportedOperationException�쳣��
            //���ڵײ�geoRegion��part�ĵ�����СΪ4��
            if (this.getCount() < 4) {
                String message = InternalResource.loadString("remove()",
                        InternalResource.Point2DsInvalidPointLength,
                        InternalResource.BundleName);
                throw new UnsupportedOperationException(message);
            }

            oldValue = (Point2D) m_point2Ds.get(index);
            removeValue = (Point2D) m_point2Ds.remove(index);

            if (oldValue.equals(removeValue)) {

                if (index == 0) {
                    //���Ѿ��Ƴ��˵�һ����ʱ,�����һ����Ҳ�Ƴ�
                    m_point2Ds.remove(m_point2Ds.size() - 1);
                }
                if (index == m_point2Ds.size()) {
                    //���Ѿ��Ƴ������һ����ʱ,����һ����Ҳ�Ƴ�
                    m_point2Ds.remove(0);
                }
                m_geoRegion.setPart(indexRegion, this);
                bResult = true;
            }
        }
        return bResult;
    }

    public int removeRange(int index, int count) {
        if (index < 0 || index >= this.getCount()) {
            String message = InternalResource.loadString(
                    "removeRange(int index, int count)",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        int countBeforeRemove = this.getCount();
        int countAfterRemove = countBeforeRemove - count;
        if (this.m_userType.value() == UserType.NONE.value()) {
            for (int i = index + count - 1; i >= index; i--) {
                m_point2Ds.remove(i);
            }
        } else if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("removeRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("removeRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            //��Point2Ds ΪgeoLine��partʱ����Point2Ds���Ƴ��������count��С��2ʱ����ִ��removeRangeʱ�׳�UnsupportedOperationException�쳣��
            if (countAfterRemove < 2) {
                String message = InternalResource.loadString("removeRange()",
                        InternalResource.Point2DsInvalidPointLength,
                        InternalResource.BundleName);
                throw new UnsupportedOperationException(message);
            }

            for (int i = index + count - 1; i >= index; i--) {
                m_point2Ds.remove(i);
            }
            m_geoLine.setPart(indexLine, this);
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("removeRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("removeRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            //��Point2Ds ΪgeoRegion��partʱ����Point2Ds���Ƴ��������count��С��4ʱ����ִ��removeRangeʱ�׳�UnsupportedOperationException�쳣��
            if (countAfterRemove < 4) {
                String message = InternalResource.loadString("removeRange()",
                        InternalResource.Point2DsInvalidPointLength,
                        InternalResource.BundleName);
                throw new UnsupportedOperationException(message);
            }
            for (int i = index + count - 1; i >= index; i--) {
                m_point2Ds.remove(i);
            }
            if (index == 0) {
                //���Ѿ��Ƴ��˵�һ����ʱ,�����һ����Ҳ�Ƴ�
                m_point2Ds.remove(m_point2Ds.size() - 1);
            }
            if (index + count == countBeforeRemove) {
                //���Ѿ��Ƴ������һ����ʱ,����һ����Ҳ�Ƴ�
                m_point2Ds.remove(0);
            }
            m_geoRegion.setPart(indexRegion, this);
        } 
       
        return count;
    }

    public void clear() {
        //����Point2DsΪGeoRegion��GeoLine��partʱ����ִ��clear�������׳�UnsupportedOperationException�쳣
        if (this.m_userType.equals(UserType.GEOLINE) ||
            this.m_userType.equals(UserType.GEOREGION) ) {
            String message = InternalResource.loadString("clear()",
                    InternalResource.Point2DsCannotDoClearOperation,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        m_point2Ds.clear();
    }

    public Point2D[] toArray() {
        if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("toArray()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("toArray()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        }
        
        int count = m_point2Ds.size();
        Point2D[] point2Ds = new Point2D[count];
        for(int i=0;i<count;i++){
            point2Ds[i] = (Point2D)m_point2Ds.get(i);
        }
        return point2Ds;
    }

    static class UserType extends Enum {
        private UserType(int value, int ugcValue) {
            super(value, ugcValue);
        }

        public static final UserType NONE = new UserType(1, 1);
        public static final UserType GEOLINE = new UserType(2, 2);
        public static final UserType GEOREGION = new UserType(3, 3);
        
        //add by xuzw 2008-12-11
        public static final UserType GEOCARDINAL = new UserType(4, 4);
        public static final UserType GEOBSPLINE = new UserType(5, 5);
        public static final UserType GEOCURVE = new UserType(6, 6);
    }


    UserType getUserType() {
        return this.m_userType;
    }

    void setUserType(UserType userType) {
        this.m_userType = userType;
    }
}
