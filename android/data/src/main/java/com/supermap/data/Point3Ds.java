package com.supermap.data;

import java.util.ArrayList;

/**
 * <p>
 * Title: 三维点对象集合
 * </p>
 * 
 * <p>
 * Description: 用于表示精度为Double的三维点对象集合
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: SuperMap GIS Technologies Inc.
 * </p>
 * 
 * @author zhikk
 * @version 2.0
 */
public class Point3Ds {
	private ArrayList<Point3D> m_point3Ds = null;
	private GeoLine3D m_geoLine3D = null;
	private GeoRegion3D m_geoRegion3D = null;
	private UserType m_userType = UserType.NONE;
	

	public Point3Ds() {
		m_point3Ds = new ArrayList<Point3D>();
		this.m_userType = UserType.NONE;
	}

	/*
	 * 拷贝构造函数
	 */
	public Point3Ds(Point3Ds points) {
		this();
		int size = points.getCount();
		for (int i = 0; i < size; i++) {
			m_point3Ds.add((Point3D) points.getItem(i).clone());
		}
	}

	// 根据三维点对象数组构建三维点集合对象
	public Point3Ds(Point3D[] points) {
		this();
		this.addRange(points);
	}
	Point3Ds(Point3Ds points , GeoLine3D geoLine3D){
		this(points);
		this.m_geoLine3D = geoLine3D;
		this.m_userType = UserType.GEOLINE3D;
	}
	Point3Ds(Point3Ds points , GeoRegion3D geoRegion3D){
		this(points);
		this.m_geoRegion3D = geoRegion3D;
		this.m_userType = UserType.GEOREGION3D;
	}


	public Point3Ds clone() {
	      if (this.m_userType.value() == UserType.GEOLINE3D.value()) {
	            if (this.m_geoLine3D.getHandle() == 0) {
	                String message = InternalResource.loadString("clone()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }

	            int indexLine = m_geoLine3D.getPartsList().indexOf(this);

	            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
	            if (indexLine == -1) {
	                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
	                String message = InternalResource.loadString("clone()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	        } else if (this.m_userType.value() == UserType.GEOREGION3D.value()) {
	            if (this.m_geoRegion3D.getHandle() == 0) {
	                String message = InternalResource.loadString("clone()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            int indexRegion = m_geoRegion3D.getPartsList().indexOf(this);

	            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
	            if (indexRegion == -1) {
	                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
	                String message = InternalResource.loadString("clone()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	        }
		return new Point3Ds(this);
	}

	public int getCount() {
	    if (this.m_userType.value() == UserType.GEOLINE3D.value()) {
            if (this.m_geoLine3D.getHandle() == 0) {
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine3D.getPartsList().indexOf(this);

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } else if (this.m_userType.value() == UserType.GEOREGION3D.value()) {

            if (this.m_geoRegion3D.getHandle() == 0) {
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion3D.getPartsList().indexOf(this);

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        }
		return m_point3Ds.size();
	}

	
	// //返回的是Item的副本,不支持连点操作。
	
	public Point3D getItem(int index) {
	    if (this.m_userType.value() == UserType.GEOLINE3D.value()) {
            if (this.m_geoLine3D.getHandle() == 0) {
                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine3D.getPartsList().indexOf(this);

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } else if (this.m_userType.value() == UserType.GEOREGION3D.value()) {

            if (this.m_geoRegion3D.getHandle() == 0) {
                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion3D.getPartsList().indexOf(this);

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        }
		Point3D pt = (Point3D) m_point3Ds.get(index);
		return (Point3D) pt.clone();

	}

	public void setItem(int index, Point3D point3D) {
		if (point3D == null) {
			String message = InternalResource.loadString(
					"setItem(int index, Point3D point3D)",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		 if (this.m_userType.value() == UserType.NONE.value()) {
	            m_point3Ds.set(index, (Point3D) point3D.clone());
	        }
		 else if (this.m_userType.value() == UserType.GEOLINE3D.value()) {
	            if (this.m_geoLine3D.getHandle() == 0) {
	                String message = InternalResource.loadString("setItem()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            int indexLine = m_geoLine3D.getPartsList().indexOf(this);
	            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
	            if (indexLine == -1) {
	                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
	                String message = InternalResource.loadString("setItem()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }

	            m_point3Ds.set(index, point3D);
	            m_geoLine3D.setPartJustToUGC(indexLine, this);
	        } else if (this.m_userType.value() == UserType.GEOREGION3D.value()) {
	            if (this.m_geoRegion3D.getHandle() == 0) {
	                String message = InternalResource.loadString("setItem()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            int indexRegion = m_geoRegion3D.getPartsList().indexOf(this);

	            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
	            if (indexRegion == -1) {
	                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
	                String message = InternalResource.loadString("setItem()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
                //底层geoRegion3D也封闭了，
	            if (index == 0) {
	                //当为设置第一个点时，先将和第一个点一致的最后一个点移除
	                m_point3Ds.remove(m_point3Ds.size() - 1);
	            } else if (index == m_point3Ds.size() - 1) {
	                //当为设置最后一个点时，需设置第一个点和最后一个点一致
	                m_point3Ds.set(0, point3D);
	            }
	            m_point3Ds.set(index, point3D);
	            m_geoRegion3D.setPartJustToUGC(indexRegion, this);

	        }
	}

	// 返回添加的索引位置
	// 需要对参数进行判断么？
	public int add(Point3D pt) {
		if (pt == null) {
			String message = InternalResource.loadString("add(Point3D pt)",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		int indexAdded = -1;
		boolean bSuccess = false;
		int indexRemove = -1;
		  if (this.m_userType.value() == UserType.NONE.value()) {
	            indexRemove = m_point3Ds.size() - 1; //先记下在GeoRegion的情况下需要移除的索引
	            bSuccess = m_point3Ds.add((Point3D) pt.clone());
	            if (bSuccess) {
	                indexAdded = m_point3Ds.size() - 1;
	            }
	        }
		  else if (this.m_userType.value() == UserType.GEOLINE3D.value()) {
	            if (this.m_geoLine3D.getHandle() == 0) {
	                String message = InternalResource.loadString("add()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            int indexLine = m_geoLine3D.getPartsList().indexOf(this);

	            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
	            if (indexLine == -1) {
	                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
	                String message = InternalResource.loadString("add()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            indexRemove = m_point3Ds.size() - 1; //先记下在GeoRegion的情况下需要移除的索引
	            bSuccess = m_point3Ds.add((Point3D) pt.clone());
	            if (bSuccess) {
	                indexAdded = m_point3Ds.size() - 1;
	            }

	            int index = m_geoLine3D.getPartsList().indexOf(this);
	            m_geoLine3D.setPart(index, this);
	        }
		  else if (this.m_userType.value() == UserType.GEOREGION3D.value()) {
	            if (this.m_geoRegion3D.getHandle() == 0) {
	                String message = InternalResource.loadString("add()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            int indexRegion = m_geoRegion3D.getPartsList().indexOf(this);

	            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
	            if (indexRegion == -1) {
	                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
	                String message = InternalResource.loadString("add()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            indexRemove = m_point3Ds.size() - 1; //先记下在GeoRegion的情况下需要移除的索引

	            bSuccess = m_point3Ds.add((Point3D) pt.clone());
	            if (bSuccess) {
	                indexAdded = m_point3Ds.size() - 1;
	            }

	            //当为GeoRegion的part的add时，需要先将未添加时的最后一个点移除掉。
	            m_point3Ds.remove(indexRemove);

	            m_geoRegion3D.setPart(indexRegion, this);
	        }
		return indexAdded;

	}

	// 向集合中添加三维点对象（以数组的形式），返回成功添加的三维点对象的个数
	public int addRange(Point3D[] points) {
		int length = points.length;
		  int indexRemove = m_point3Ds.size() - 1; 
	        if (this.m_userType.value() == UserType.NONE.value()) {
	            for (int i = 0; i < length; i++) {
	                m_point3Ds.add((Point3D) points[i].clone());
	            }
	        } else if (this.m_userType.value() == UserType.GEOLINE3D.value()) {
	            if (this.m_geoLine3D.getHandle() == 0) {
	                String message = InternalResource.loadString("addRange()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            int indexLine = m_geoLine3D.getPartsList().indexOf(this);

	            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
	            if (indexLine == -1) {
	                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
	                String message = InternalResource.loadString("addRange()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }

	            for (int i = 0; i < length; i++) {
	                m_point3Ds.add((Point3D) points[i].clone());
	            }

	            m_geoLine3D.setPart(indexLine, this);
	        } else if (this.m_userType.value() == UserType.GEOREGION3D.value()) {
	            if (this.m_geoRegion3D.getHandle() == 0) {
	                String message = InternalResource.loadString("addRange()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            int indexRegion = m_geoRegion3D.getPartsList().indexOf(this);

	            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
	            if (indexRegion == -1) {
	                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
	                String message = InternalResource.loadString("addRange()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            for (int i = 0; i < length; i++) {
	                m_point3Ds.add((Point3D) points[i].clone());
	            }
	            //当为GeoRegion3D的part的addRange时，需要先将未添加时的最后一个点移除掉。
	            m_point3Ds.remove(indexRemove);
	            m_geoRegion3D.setPart(indexRegion, this);
	        }
		return length;

	}

	public boolean insert(int index, Point3D pt) {
		if (index < 0 || index > this.getCount()) {
			String message = InternalResource.loadString(
					"insert(int index, Point3D point3D)",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (pt == null) {
			String message = InternalResource.loadString(
					"insert(int index, Point3D point3D)",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		boolean bSuccess = false;
	     int indexRemove = m_point3Ds.size() - 1; //先记下在GeoRegion3D的情况下需要移除的索引
	        int size = m_point3Ds.size();
	        if (this.m_userType.value() == UserType.NONE.value()) {

	            m_point3Ds.add(index, (Point3D) pt.clone());
	            if (m_point3Ds.size() == size + 1) {
	                bSuccess = true;
	            } else {
	                bSuccess = false;
	            }
	        } else if (this.m_userType.value() == UserType.GEOLINE3D.value()) {
	            if (this.m_geoLine3D.getHandle() == 0) {
	                String message = InternalResource.loadString("insert()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            int indexLine = m_geoLine3D.getPartsList().indexOf(this);

	            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
	            if (indexLine == -1) {
	                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
	                String message = InternalResource.loadString("insert()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            m_point3Ds.add(index, (Point3D) pt.clone());
	            if (m_point3Ds.size() == size + 1) {
	                bSuccess = true;
	            } else {
	                bSuccess = false;
	            }

	            m_geoLine3D.setPart(indexLine, this);
	        } else if (this.m_userType.value() == UserType.GEOREGION3D.value()) {

	            if (this.m_geoRegion3D.getHandle() == 0) {
	                String message = InternalResource.loadString("insert()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            int indexRegion = m_geoRegion3D.getPartsList().indexOf(this);

	            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
	            if (indexRegion == -1) {
	                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
	                String message = InternalResource.loadString("insert()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            m_point3Ds.add(index, (Point3D) pt.clone());
	            if (m_point3Ds.size() == size + 1) {
	                bSuccess = true;
	            } else {
	                bSuccess = false;
	            }

	            //当是在第一个位置插入时,需要先将和第一个点一致的最后一个点移除
	            if (index == 0) {
	                //由于实际已经添加，故移除的索引为indexRemove + 1
	                m_point3Ds.remove(indexRemove + 1);
	            }

	            //当是在最后一个后面插入时，需要先将最后一个点移除掉
	            if (index == m_point3Ds.size()) {
	                m_point3Ds.remove(indexRemove);
	            }
	            m_geoRegion3D.setPart(indexRegion, this);
	        }
		return bSuccess;
	}

	// 返回插入点的个数
	public int insertRange(int index, Point3D[] points) {
		if (index < 0 || index > this.getCount()) {
			String message = InternalResource.loadString(
					"insertRange(int index, Point3D[] points)",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
        int indexRemove = m_point3Ds.size() - 1; //先记下在GeoRegion的情况下需要移除的索引
//      boolean bSuccess = false;
      int length = points.length;

      if (this.m_userType.value() == UserType.NONE.value()) {
          for (int i = 0; i < length; i++) {
              int realIndex = index + i;
              m_point3Ds.add(realIndex, (Point3D) points[i].clone());
          }

      } else if (this.m_userType.value() == UserType.GEOLINE3D.value()) {
          if (this.m_geoLine3D.getHandle() == 0) {
              String message = InternalResource.loadString("insertRange()",
                      InternalResource.HandleObjectHasBeenDisposed,
                      InternalResource.BundleName);
              throw new IllegalStateException(message);
          }
          int indexLine = m_geoLine3D.getPartsList().indexOf(this);

          //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
          if (indexLine == -1) {
              //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
              String message = InternalResource.loadString("insertRange()",
                      InternalResource.HandleObjectHasBeenDisposed,
                      InternalResource.BundleName);
              throw new IllegalStateException(message);
          }
          for (int i = 0; i < length; i++) {
              int realIndex = index + i;
              m_point3Ds.add(realIndex, (Point3D) points[i].clone());
          }

          m_geoLine3D.setPart(indexLine, this);
      } else if (this.m_userType.value() == UserType.GEOREGION3D.value()) {
          if (this.m_geoRegion3D.getHandle() == 0) {
              String message = InternalResource.loadString("insertRange()",
                      InternalResource.HandleObjectHasBeenDisposed,
                      InternalResource.BundleName);
              throw new IllegalStateException(message);
          }
          int indexRegion = m_geoRegion3D.getPartsList().indexOf(this);

          //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
          if (indexRegion == -1) {
              //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
              String message = InternalResource.loadString("insertRange()",
                      InternalResource.HandleObjectHasBeenDisposed,
                      InternalResource.BundleName);
              throw new IllegalStateException(message);
          }
          for (int i = 0; i < length; i++) {
              int realIndex = index + i;
              m_point3Ds.add(realIndex, (Point3D) points[i].clone());
          }


          //当是在第一个位置插入时,需要先将和第一个点一致的最后一个点移除
          if (index == 0) {
              m_point3Ds.remove(indexRemove + length);
          }

          //当是在最后一个后面插入时，需要先将最后一个点移除掉
          if (index == m_point3Ds.size()) {
              m_point3Ds.remove(indexRemove);
          }
          m_geoRegion3D.setPart(indexRegion, this);
      }
		return length;
	}

	public boolean remove(int index) {
		if (index < 0 || index >= this.getCount()) {
			String message = InternalResource.loadString("remove(int index)",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
        Point3D oldValue = null;
        Point3D removeValue = null;
        boolean bResult = false;
        if (this.m_userType.value() == UserType.NONE.value()) {
            oldValue = (Point3D) m_point3Ds.get(index);
            removeValue = (Point3D) m_point3Ds.remove(index);
            if (oldValue.equals(removeValue)) {
                bResult = true;
            }
        } else if (this.m_userType.value() == UserType.GEOLINE3D.value()) {
            if (this.m_geoLine3D.getHandle() == 0) {
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine3D.getPartsList().indexOf(this);

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            //当Point3Ds 为geoLine3D的part时，则当Point3Ds的个数（count）小于2时，则执行remove时抛出UnsupportedOperationException异常。
            if (this.getCount() < 2) {
                String message = InternalResource.loadString("remove()",
                        InternalResource.Point3DsInvalidPointLength,
                        InternalResource.BundleName);
                throw new UnsupportedOperationException(message);
            }
            oldValue = (Point3D) m_point3Ds.get(index);
            removeValue = (Point3D) m_point3Ds.remove(index);
            if (oldValue.equals(removeValue)) {
                m_geoLine3D.setPart(indexLine, this);
                bResult = true;
            }
        } else if (this.m_userType.value() == UserType.GEOREGION3D.value()) {
            if (this.m_geoRegion3D.getHandle() == 0) {
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion3D.getPartsList().indexOf(this);

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            //当Point3Ds 为geoRegion3D的part时，则当Point3Ds的个数（count）小于4时，则执行remove时抛出UnsupportedOperationException异常。
            //由于底层geoRegion3D的part的点数最小为4个
            if (this.getCount() < 4) {
                String message = InternalResource.loadString("remove()",
                        InternalResource.Point3DsInvalidPointLength,
                        InternalResource.BundleName);
                throw new UnsupportedOperationException(message);
            }

            oldValue = (Point3D) m_point3Ds.get(index);
            removeValue = (Point3D) m_point3Ds.remove(index);

            if (oldValue.equals(removeValue)) {

                if (index == 0) {
                    //当已经移除了第一个点时,将最后一个点也移除
                    m_point3Ds.remove(m_point3Ds.size() - 1);
                }
                if (index == m_point3Ds.size()) {
                    //当已经移除了最后一个点时,将第一个点也移除
                    m_point3Ds.remove(0);
                }
                m_geoRegion3D.setPart(indexRegion, this);
                bResult = true;
            }
        }
		return bResult;
	}

	// 在集合中从指定序号开始删除三维点对象,指定删除count个，返回被成功删除的对象的个数
	// 当删除数目过大时，抛异常
	public int removeRange(int index, int count) {
		if (index < 0 || index >= this.getCount()) {
			String message = InternalResource.loadString(
					"removeRange(int index, int count)",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 添加count<0的情况
		if ((this.getCount() < index + count) || count < 0) {
			String message = InternalResource.loadString(
					"removeRange(int index, int count)",
					InternalResource.Point3DsInvalidCount,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

	      int countBeforeRemove = this.getCount();
	        int countAfterRemove = countBeforeRemove - count;
	        if (this.m_userType.value() == UserType.NONE.value()) {
	            for (int i = index + count - 1; i >= index; i--) {
	                m_point3Ds.remove(i);
	            }
	        } else if (this.m_userType.value() == UserType.GEOLINE3D.value()) {
	            if (this.m_geoLine3D.getHandle() == 0) {
	                String message = InternalResource.loadString("removeRange()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }

	            int indexLine = m_geoLine3D.getPartsList().indexOf(this);

	            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
	            if (indexLine == -1) {
	                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
	                String message = InternalResource.loadString("removeRange()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }

	            //当Point3Ds 为geoLine3D的part时，则当Point3Ds的移除后个数（count）小于2时，则执行removeRange时抛出UnsupportedOperationException异常。
	            if (countAfterRemove < 2) {
	                String message = InternalResource.loadString("removeRange()",
	                        InternalResource.Point3DsInvalidPointLength,
	                        InternalResource.BundleName);
	                throw new UnsupportedOperationException(message);
	            }

	            for (int i = index + count - 1; i >= index; i--) {
	                m_point3Ds.remove(i);
	            }
	            m_geoLine3D.setPart(indexLine, this);
	        } else if (this.m_userType.value() == UserType.GEOREGION3D.value()) {
	            if (this.m_geoRegion3D.getHandle() == 0) {
	                String message = InternalResource.loadString("removeRange()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            int indexRegion = m_geoRegion3D.getPartsList().indexOf(this);

	            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
	            if (indexRegion == -1) {
	                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
	                String message = InternalResource.loadString("removeRange()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }

	            //当Point3Ds 为geoRegion3D的part时，则当Point3Ds的移除后个数（count）小于4时，则执行removeRange时抛出UnsupportedOperationException异常。
	            if (countAfterRemove < 4) {
	                String message = InternalResource.loadString("removeRange()",
	                        InternalResource.Point3DsInvalidPointLength,
	                        InternalResource.BundleName);
	                throw new UnsupportedOperationException(message);
	            }
	            for (int i = index + count - 1; i >= index; i--) {
	                m_point3Ds.remove(i);
	            }
	            if (index == 0) {
	                //当已经移除了第一个点时,将最后一个点也移除
	                m_point3Ds.remove(m_point3Ds.size() - 1);
	            }
	            if (index + count == countBeforeRemove) {
	                //当已经移除了最后一个点时,将第一个点也移除
	                m_point3Ds.remove(0);
	            }
	            m_geoRegion3D.setPart(indexRegion, this);
	        }
		return count;
	}

	public void clear() {
	     //当此Point3Ds为GeoRegion3D或GeoLine3D的part时，则执行clear操作将抛出UnsupportedOperationException异常
        if (this.m_userType.equals(UserType.GEOLINE3D) ||
            this.m_userType.equals(UserType.GEOREGION3D)) {
            String message = InternalResource.loadString("clear()",
                    InternalResource.Point3DsCannotDoClearOperation,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
		m_point3Ds.clear();
	}

	public Point3D[] toArray() {
	    if (this.m_userType.value() == UserType.GEOLINE3D.value()) {
            if (this.m_geoLine3D.getHandle() == 0) {
                String message = InternalResource.loadString("toArray()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine3D.getPartsList().indexOf(this);

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("toArray()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } else if (this.m_userType.value() == UserType.GEOREGION3D.value()) {
            if (this.m_geoRegion3D.getHandle() == 0) {
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion3D.getPartsList().indexOf(this);

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexRegion == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        }
		int count = m_point3Ds.size();
		Point3D[] Point3Ds = new Point3D[count];
		for (int i = 0; i < count; i++) {
			Point3Ds[i] = (Point3D) m_point3Ds.get(i);
		}
		return Point3Ds;

	}
		
	//由于point2Ds里面也有这么一个类，考虑拿出来，提供包访问权限。
    private static class UserType extends Enum {
        private UserType(int value, int ugcValue) {
            super(value, ugcValue);
        }

        public static final UserType NONE = new UserType(1, 1);
        public static final UserType GEOLINE3D = new UserType(2, 2);
        public static final UserType GEOREGION3D = new UserType(3, 3);
        
    }
    UserType getUserType(){
    	return this.m_userType;
    }
    void setUserType(UserType userType){
    	this.m_userType = userType;
    }
}
