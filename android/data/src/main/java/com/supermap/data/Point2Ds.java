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
 * @author 孔令亮
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } 
        
        return m_point2Ds.size();
    }

    //返回的是Item的副本
    public Point2D getItem(int index) {
        if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine.getPartsList().indexOf(this);

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
            	//当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
            	//当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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
     * 设置指定序号的点对象
     * @param index
     * @param point2D
     */
    public void setItem(int index, Point2D point2D) {
    	if(point2D.isEmpty()){
    		return;                                    // 不设置成空的点对象
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
            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("setItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            if (index == 0) {
                //当为设置第一个点时，先将和第一个点一致的最后一个点移除
                m_point2Ds.remove(m_point2Ds.size() - 1);
            } else if (index == m_point2Ds.size() - 1) {
                //当为设置最后一个点时，需设置第一个点和最后一个点一致
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("clone()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } 

        return new Point2Ds(this);

    }

    /**
     * 线集合末尾增加一个点
     * @param pt   欲增加的点，不包含空的点对象
     * @return     index,返回增加点的序号
     */
    public int add(Point2D pt) {
        int indexAdded = -1;
        int indexRemove = -1;
        boolean bSuccess = false;
        // 空的点对象不是有效点，不用于构成线对象
        if(pt.isEmpty()){
        	
        	return indexAdded;
        }
        if (this.m_userType.value() == UserType.NONE.value()) {
            indexRemove = m_point2Ds.size() - 1; //先记下在GeoRegion的情况下需要移除的索引
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("add()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            indexRemove = m_point2Ds.size() - 1; //先记下在GeoRegion的情况下需要移除的索引
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("add()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            indexRemove = m_point2Ds.size() - 1; //先记下在GeoRegion的情况下需要移除的索引

            bSuccess = m_point2Ds.add(pt.clone());
            if (bSuccess) {
                indexAdded = m_point2Ds.size() - 1;
            }

            //当为GeoLine的part的add时，需要先将未添加时的最后一个点移除掉。
            m_point2Ds.remove(indexRemove);

            m_geoRegion.setPart(indexRegion, this);
        } 

        return indexAdded;
    }

   
    /**
     * 线集合末尾增加一系列点，这些点由点对象数组给出
     * @param points   欲增加的点的数组，不包含空的点对象
     * @return        返回增加的点的总数
     */
    public int addRange(Point2D[] points) {
        int length = points.length;
        int countInvalid = 0;                    // 记录无效点(即空的点对象)的个数
        int indexRemove = m_point2Ds.size() - 1; //先记下在GeoRegion的情况下需要移除的索引
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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
            //当为GeoLine的part的addRange时，需要先将未添加时的最后一个点移除掉。
            m_point2Ds.remove(indexRemove);
            m_geoRegion.setPart(indexRegion, this);
        } 
       
        return length - countInvalid;
    }

    /**
     * 线集合中从指定位置起插入一个点
     * @param pt   欲插入的点, 不能是空的点对象
     * @return     插入点是否成功，true为成功，false为失败
     */
    public boolean insert(int index, Point2D pt) {
        if(index<0 || index>this.getCount()){
           String message = InternalResource.loadString("insert(int index, Point2D pt)",
                   InternalResource.GlobalIndexOutOfBounds,
                   InternalResource.BundleName);
               throw new IllegalArgumentException(message);
       }

        boolean bSuccess = false;
        // 不插入空的点对象
        if(pt.isEmpty()){
        	return bSuccess;
        }
        int indexRemove = m_point2Ds.size() - 1; //先记下在GeoRegion的情况下需要移除的索引
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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

            //当是在第一个位置插入时,需要先将和第一个点一致的最后一个点移除
            if (index == 0) {
                //由于实际已经添加，故移除的索引为indexRemove + 1
                m_point2Ds.remove(indexRemove + 1);
            }

            //当是在最后一个后面插入时，需要先将最后一个点移除掉
            if (index == m_point2Ds.size()) {
                m_point2Ds.remove(indexRemove);
            }
            m_geoRegion.setPart(indexRegion, this);
        } 
        
        return bSuccess;
    }
    
    /**
     * 线集合中从指定位置起插入一系列点，这些点由点对象数组给出
     * @param points   欲插入的点的数组，不包含空的点对象
     * @return         插入的点的总数
     */
    public int insertRange(int index, Point2D[] points) {
        if(index<0 || index>this.getCount()){
            String message = InternalResource.loadString("insertRange(int index, Point2D[] points)",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
                throw new IllegalArgumentException(message);
        }
        int indexRemove = m_point2Ds.size() - 1; //先记下在GeoRegion的情况下需要移除的索引
        
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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

            //当是在第一个位置插入时,需要先将和第一个点一致的最后一个点移除
            if (index == 0) {
                m_point2Ds.remove(indexRemove + length - countInvalid);
            }

            //当是在最后一个后面插入时，需要先将最后一个点移除掉
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            //当Point2Ds 为geoLine的part时，则当Point2Ds的个数（count）小于2时，则执行remove时抛出UnsupportedOperationException异常。
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            //当Point2Ds 为geoRegion的part时，则当Point2Ds的个数（count）小于4时，则执行remove时抛出UnsupportedOperationException异常。
            //由于底层geoRegion的part的点数最小为4个
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
                    //当已经移除了第一个点时,将最后一个点也移除
                    m_point2Ds.remove(m_point2Ds.size() - 1);
                }
                if (index == m_point2Ds.size()) {
                    //当已经移除了最后一个点时,将第一个点也移除
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("removeRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            //当Point2Ds 为geoLine的part时，则当Point2Ds的移除后个数（count）小于2时，则执行removeRange时抛出UnsupportedOperationException异常。
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("removeRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            //当Point2Ds 为geoRegion的part时，则当Point2Ds的移除后个数（count）小于4时，则执行removeRange时抛出UnsupportedOperationException异常。
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
                //当已经移除了第一个点时,将最后一个点也移除
                m_point2Ds.remove(m_point2Ds.size() - 1);
            }
            if (index + count == countBeforeRemove) {
                //当已经移除了最后一个点时,将第一个点也移除
                m_point2Ds.remove(0);
            }
            m_geoRegion.setPart(indexRegion, this);
        } 
       
        return count;
    }

    public void clear() {
        //当此Point2Ds为GeoRegion或GeoLine的part时，则执行clear操作将抛出UnsupportedOperationException异常
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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
