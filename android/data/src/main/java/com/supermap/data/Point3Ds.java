package com.supermap.data;

import java.util.ArrayList;

/**
 * <p>
 * Title: ��ά����󼯺�
 * </p>
 * 
 * <p>
 * Description: ���ڱ�ʾ����ΪDouble����ά����󼯺�
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
	 * �������캯��
	 */
	public Point3Ds(Point3Ds points) {
		this();
		int size = points.getCount();
		for (int i = 0; i < size; i++) {
			m_point3Ds.add((Point3D) points.getItem(i).clone());
		}
	}

	// ������ά��������鹹����ά�㼯�϶���
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

	            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
	            if (indexLine == -1) {
	                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
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

	            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
	            if (indexRegion == -1) {
	                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
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

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
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

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        }
		return m_point3Ds.size();
	}

	
	// //���ص���Item�ĸ���,��֧�����������
	
	public Point3D getItem(int index) {
	    if (this.m_userType.value() == UserType.GEOLINE3D.value()) {
            if (this.m_geoLine3D.getHandle() == 0) {
                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine3D.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
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

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
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
	            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
	            if (indexLine == -1) {
	                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
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

	            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
	            if (indexRegion == -1) {
	                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
	                String message = InternalResource.loadString("setItem()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
                //�ײ�geoRegion3DҲ����ˣ�
	            if (index == 0) {
	                //��Ϊ���õ�һ����ʱ���Ƚ��͵�һ����һ�µ����һ�����Ƴ�
	                m_point3Ds.remove(m_point3Ds.size() - 1);
	            } else if (index == m_point3Ds.size() - 1) {
	                //��Ϊ�������һ����ʱ�������õ�һ��������һ����һ��
	                m_point3Ds.set(0, point3D);
	            }
	            m_point3Ds.set(index, point3D);
	            m_geoRegion3D.setPartJustToUGC(indexRegion, this);

	        }
	}

	// ������ӵ�����λ��
	// ��Ҫ�Բ��������ж�ô��
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
	            indexRemove = m_point3Ds.size() - 1; //�ȼ�����GeoRegion���������Ҫ�Ƴ�������
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

	            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
	            if (indexLine == -1) {
	                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
	                String message = InternalResource.loadString("add()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            indexRemove = m_point3Ds.size() - 1; //�ȼ�����GeoRegion���������Ҫ�Ƴ�������
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

	            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
	            if (indexRegion == -1) {
	                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
	                String message = InternalResource.loadString("add()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            indexRemove = m_point3Ds.size() - 1; //�ȼ�����GeoRegion���������Ҫ�Ƴ�������

	            bSuccess = m_point3Ds.add((Point3D) pt.clone());
	            if (bSuccess) {
	                indexAdded = m_point3Ds.size() - 1;
	            }

	            //��ΪGeoRegion��part��addʱ����Ҫ�Ƚ�δ���ʱ�����һ�����Ƴ�����
	            m_point3Ds.remove(indexRemove);

	            m_geoRegion3D.setPart(indexRegion, this);
	        }
		return indexAdded;

	}

	// �򼯺��������ά��������������ʽ�������سɹ���ӵ���ά�����ĸ���
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

	            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
	            if (indexLine == -1) {
	                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
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

	            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
	            if (indexRegion == -1) {
	                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
	                String message = InternalResource.loadString("addRange()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }
	            for (int i = 0; i < length; i++) {
	                m_point3Ds.add((Point3D) points[i].clone());
	            }
	            //��ΪGeoRegion3D��part��addRangeʱ����Ҫ�Ƚ�δ���ʱ�����һ�����Ƴ�����
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
	     int indexRemove = m_point3Ds.size() - 1; //�ȼ�����GeoRegion3D���������Ҫ�Ƴ�������
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

	            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
	            if (indexLine == -1) {
	                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
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

	            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
	            if (indexRegion == -1) {
	                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
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

	            //�����ڵ�һ��λ�ò���ʱ,��Ҫ�Ƚ��͵�һ����һ�µ����һ�����Ƴ�
	            if (index == 0) {
	                //����ʵ���Ѿ���ӣ����Ƴ�������ΪindexRemove + 1
	                m_point3Ds.remove(indexRemove + 1);
	            }

	            //���������һ���������ʱ����Ҫ�Ƚ����һ�����Ƴ���
	            if (index == m_point3Ds.size()) {
	                m_point3Ds.remove(indexRemove);
	            }
	            m_geoRegion3D.setPart(indexRegion, this);
	        }
		return bSuccess;
	}

	// ���ز����ĸ���
	public int insertRange(int index, Point3D[] points) {
		if (index < 0 || index > this.getCount()) {
			String message = InternalResource.loadString(
					"insertRange(int index, Point3D[] points)",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
        int indexRemove = m_point3Ds.size() - 1; //�ȼ�����GeoRegion���������Ҫ�Ƴ�������
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

          //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
          if (indexLine == -1) {
              //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
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

          //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
          if (indexRegion == -1) {
              //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
              String message = InternalResource.loadString("insertRange()",
                      InternalResource.HandleObjectHasBeenDisposed,
                      InternalResource.BundleName);
              throw new IllegalStateException(message);
          }
          for (int i = 0; i < length; i++) {
              int realIndex = index + i;
              m_point3Ds.add(realIndex, (Point3D) points[i].clone());
          }


          //�����ڵ�һ��λ�ò���ʱ,��Ҫ�Ƚ��͵�һ����һ�µ����һ�����Ƴ�
          if (index == 0) {
              m_point3Ds.remove(indexRemove + length);
          }

          //���������һ���������ʱ����Ҫ�Ƚ����һ�����Ƴ���
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

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            //��Point3Ds ΪgeoLine3D��partʱ����Point3Ds�ĸ�����count��С��2ʱ����ִ��removeʱ�׳�UnsupportedOperationException�쳣��
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

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            //��Point3Ds ΪgeoRegion3D��partʱ����Point3Ds�ĸ�����count��С��4ʱ����ִ��removeʱ�׳�UnsupportedOperationException�쳣��
            //���ڵײ�geoRegion3D��part�ĵ�����СΪ4��
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
                    //���Ѿ��Ƴ��˵�һ����ʱ,�����һ����Ҳ�Ƴ�
                    m_point3Ds.remove(m_point3Ds.size() - 1);
                }
                if (index == m_point3Ds.size()) {
                    //���Ѿ��Ƴ������һ����ʱ,����һ����Ҳ�Ƴ�
                    m_point3Ds.remove(0);
                }
                m_geoRegion3D.setPart(indexRegion, this);
                bResult = true;
            }
        }
		return bResult;
	}

	// �ڼ����д�ָ����ſ�ʼɾ����ά�����,ָ��ɾ��count�������ر��ɹ�ɾ���Ķ���ĸ���
	// ��ɾ����Ŀ����ʱ�����쳣
	public int removeRange(int index, int count) {
		if (index < 0 || index >= this.getCount()) {
			String message = InternalResource.loadString(
					"removeRange(int index, int count)",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ���count<0�����
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

	            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
	            if (indexLine == -1) {
	                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
	                String message = InternalResource.loadString("removeRange()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }

	            //��Point3Ds ΪgeoLine3D��partʱ����Point3Ds���Ƴ��������count��С��2ʱ����ִ��removeRangeʱ�׳�UnsupportedOperationException�쳣��
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

	            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
	            if (indexRegion == -1) {
	                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
	                String message = InternalResource.loadString("removeRange()",
	                        InternalResource.HandleObjectHasBeenDisposed,
	                        InternalResource.BundleName);
	                throw new IllegalStateException(message);
	            }

	            //��Point3Ds ΪgeoRegion3D��partʱ����Point3Ds���Ƴ��������count��С��4ʱ����ִ��removeRangeʱ�׳�UnsupportedOperationException�쳣��
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
	                //���Ѿ��Ƴ��˵�һ����ʱ,�����һ����Ҳ�Ƴ�
	                m_point3Ds.remove(m_point3Ds.size() - 1);
	            }
	            if (index + count == countBeforeRemove) {
	                //���Ѿ��Ƴ������һ����ʱ,����һ����Ҳ�Ƴ�
	                m_point3Ds.remove(0);
	            }
	            m_geoRegion3D.setPart(indexRegion, this);
	        }
		return count;
	}

	public void clear() {
	     //����Point3DsΪGeoRegion3D��GeoLine3D��partʱ����ִ��clear�������׳�UnsupportedOperationException�쳣
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

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
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

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexRegion == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
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
		
	//����point2Ds����Ҳ����ôһ���࣬�����ó������ṩ������Ȩ�ޡ�
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
