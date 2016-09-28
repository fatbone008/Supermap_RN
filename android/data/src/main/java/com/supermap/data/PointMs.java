package com.supermap.data;

import java.util.ArrayList;

/**
 * <p>Title:·�ߵ���󼯺� </p>
 *
 * <p>Description: ���ڱ�ʾ����ΪDouble��·�ߵ���󼯺�</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author �ż���
 * @version 2.0
 */
public class PointMs {
    public PointMs() {
        this.m_pointMs = new ArrayList<PointM>();
        this.m_geoLineM = null;
    }

    public PointMs(PointM[] pointMs)
    {
        this();
        this.addRange(pointMs);
    }

    public PointMs(PointMs pointMs)
    {
        this();
        int size = pointMs.getCount();
        for (int i = 0; i < size; i++) {
            m_pointMs.add(pointMs.getItem(i).clone());
        }
    }

    public int getCount() {
        if (this.m_geoLineM != null) {
            if (this.m_geoLineM.getHandle() == 0) {
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLineM.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        }

        return m_pointMs.size();
    }

    public PointM getItem(int index) {
        if (this.m_geoLineM != null) {
            if (this.m_geoLineM.getHandle() == 0) {
                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLineM.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        }
        PointM ptM = (PointM) m_pointMs.get(index);
        return (PointM) ptM.clone();
    }

    /**
     * 
     * ����ָ����ŵ�·�ߵ����
     * @param index
     * @param ptM
     */
    public void setItem(int index, PointM ptM) {
		if (ptM.isEmpty()) {
			return;
		}
        if (this.m_geoLineM == null) {
            m_pointMs.set(index, ptM.clone());
        }
        else
        {
            if (this.m_geoLineM.getHandle() == 0) {
                String message = InternalResource.loadString("setItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLineM.getPartsList().indexOf(this);
            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("setItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            m_pointMs.set(index, ptM.clone());
            m_geoLineM.setPartJustToUGC(indexLine, this);
        }
    }

    /**
     * ·�߼���ĩβ����һ����
     * @param ptM   �����ӵĵ㣬�����ǿյ�·�ߵ����
     * @return      index,�������ӵ�����
     */
    public int add(PointM ptM) {
        int indexAdded = -1;

        if(ptM.isEmpty()){
        	return indexAdded;
        }
        boolean bSuccess = false;
        if (this.m_geoLineM == null) {
            bSuccess = m_pointMs.add(ptM.clone());
            if (bSuccess) {
                indexAdded = m_pointMs.size() - 1;
            }
        } else {
            if (this.m_geoLineM.getHandle() == 0) {
                String message = InternalResource.loadString("add()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLineM.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("add()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            bSuccess = m_pointMs.add(ptM.clone());
            if (bSuccess) {
                indexAdded = m_pointMs.size() - 1;
            }

            int index = m_geoLineM.getPartsList().indexOf(this);
            m_geoLineM.setPart(index, this);
        }
        return indexAdded;
    }

    /**
     * �߼���ĩβ����һϵ�е㣬��Щ����·�ߵ�����������
     * @param pointMs   �����ӵĵ�����飬�������յ�·�ߵ����
     * @return          �������ӵĵ������
     */
    public int addRange(PointM[] pointMs) {
        int length = pointMs.length;
        int countInvalid = 0;
        if (this.m_geoLineM == null) {
            for (int i = 0; i < length; i++) {
				if (pointMs[i].isEmpty()) {
                    countInvalid ++;
				} else {
					m_pointMs.add(pointMs[i].clone());
				}
            }
        } else {
            if (this.m_geoLineM.getHandle() == 0) {
                String message = InternalResource.loadString("addRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLineM.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("addRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            for (int i = 0; i < length; i++) {
            	if (pointMs[i].isEmpty()) {
                    countInvalid ++;
				} else {
					m_pointMs.add(pointMs[i].clone());
				}
            }

            m_geoLineM.setPart(indexLine, this);
        }

        return length - countInvalid;
    }

    /**
     * ·�߼����д�ָ��λ�������һ����
     * @param pt   ������ĵ�, �����ǿյ�·�ߵ����
     * @return     ������Ƿ�ɹ���trueΪ�ɹ���falseΪʧ��
     */
    public boolean insert(int index, PointM ptM) {
    	
    	if(ptM.isEmpty()){
    		return false;
    	}

        if(index<0 || index>this.getCount()){
            String message = InternalResource.loadString("insert(int index, PointM ptM)",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
                throw new IllegalArgumentException(message);
        }

        boolean bSuccess = false;
        int size = m_pointMs.size();
        if (this.m_geoLineM == null) {
            m_pointMs.add(index, ptM.clone());
            if (m_pointMs.size() == size + 1) {
                bSuccess = true;
            } else {
                bSuccess = false;
            }
        } else {
            if (this.m_geoLineM.getHandle() == 0) {
                String message = InternalResource.loadString("insert(int index, PointM ptM)",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLineM.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("insert(int index, PointM ptM)",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            m_pointMs.add(index, ptM.clone());
            if (m_pointMs.size() == size + 1) {
                bSuccess = true;
            } else {
                bSuccess = false;
            }

            m_geoLineM.setPart(indexLine, this);
        }
        return bSuccess;
    }

    /**
     * ·�߼����д�ָ��λ�������һϵ��·�ߵ㣬��Щ�����������
     * @param pointMs   �������·�ߵ�����飬�������յ�·�ߵ����
     * @return          �����·�ߵ������
     */
    public int insertRange(int index, PointM[] pointMs) {
        if(index<0 || index>this.getCount()){
            String message = InternalResource.loadString("insert(int index, PointM ptM)",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
                throw new IllegalArgumentException(message);
        }
        int length = pointMs.length;
        int countInvalid = 0;
//        int size = m_pointMs.size();
        if (this.m_geoLineM == null) {
			for (int i = 0; i < length; i++) {
				if (pointMs[i].isEmpty()) {
					countInvalid++;
				} else {
					int realIndex = index + i - countInvalid;
					m_pointMs.add(realIndex, pointMs[i].clone());
				}
            }
//            if (m_pointMs.size() == size + length) {
//                bSuccess = true;
//            } else {
//                bSuccess = false;
//            }
        } else {
            if (this.m_geoLineM.getHandle() == 0) {
                String message = InternalResource.loadString("insertRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLineM.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("insertRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            for (int i = 0; i < length; i++) {
            	if (pointMs[i].isEmpty()) {
					countInvalid++;
				} else {
					int realIndex = index + i - countInvalid;
					m_pointMs.add(realIndex, pointMs[i].clone());
				}
            }
//            if (m_pointMs.size() == size + length) {
//                bSuccess = true;
//            } else {
//                bSuccess = false;
//            }
            m_geoLineM.setPart(indexLine, this);
        }
        return length - countInvalid;
    }

    public boolean remove(int index) {
        if(index<0 || index>=this.getCount()){
           String message = InternalResource.loadString("remove(int index)",
                   InternalResource.GlobalIndexOutOfBounds,
                   InternalResource.BundleName);
               throw new IllegalArgumentException(message);
       }

        PointM oldValue = null;
        PointM removeValue = null;
        boolean bResult = false;
        if (this.m_geoLineM == null) {
            oldValue = (PointM) m_pointMs.get(index);
            removeValue = (PointM) m_pointMs.remove(index);
            if (oldValue.equals(removeValue)) {
                bResult = true;
            }
        } else {
            if (this.m_geoLineM.getHandle() == 0) {
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLineM.getPartsList().indexOf(this);

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
            oldValue = (PointM) m_pointMs.get(index);
            removeValue = (PointM) m_pointMs.remove(index);
            if (oldValue.equals(removeValue)) {
                m_geoLineM.setPart(indexLine, this);
                bResult = true;
            }
        }
        return bResult;
    }

    public int removeRange(int index, int count) {
        if (index < 0 || index >= this.getCount()) {
            String message = InternalResource.loadString("removeRange(int index, int count)",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

         int countBeforeRemove = this.getCount();
         int countAfterRemove = countBeforeRemove - count;
         if (this.m_geoLineM == null) {
             for (int i = index + count - 1; i >= index; i--) {
                 m_pointMs.remove(i);
             }
         } else{
             if (this.m_geoLineM.getHandle() == 0) {
                 String message = InternalResource.loadString("removeRange()",
                         InternalResource.HandleObjectHasBeenDisposed,
                         InternalResource.BundleName);
                 throw new IllegalStateException(message);
             }

             int indexLine = m_geoLineM.getPartsList().indexOf(this);

             //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
             if (indexLine == -1) {
                 //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                 String message = InternalResource.loadString("removeRange()",
                         InternalResource.HandleObjectHasBeenDisposed,
                         InternalResource.BundleName);
                 throw new IllegalStateException(message);
             }
             //��PointMs ΪgeoLineM��partʱ����PointMs���Ƴ��������count��С��2ʱ����ִ��removeRangeʱ�׳�UnsupportedOperationException�쳣��
             if (countAfterRemove < 2) {
                 String message = InternalResource.loadString("removeRange()",
                         InternalResource.Point2DsInvalidPointLength,
                         InternalResource.BundleName);
                 throw new UnsupportedOperationException(message);
             }

             for (int i = index + count - 1; i >= index; i--) {
                 m_pointMs.remove(i);
             }
             m_geoLineM.setPart(indexLine, this);
         }
         return count;
    }

    public void clear() {
       if(m_geoLineM != null)
       {
           String message = InternalResource.loadString("clear()",
                   InternalResource.PointMsCannotDoClearOperation,
                   InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
       }
       m_pointMs.clear();
    }

    public PointM[] toArray() {
        if (this.m_geoLineM != null) {
            if (this.m_geoLineM.getHandle() == 0) {
                String message = InternalResource.loadString("toArray()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLineM.getPartsList().indexOf(this);

            //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
            if (indexLine == -1) {
                //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
                String message = InternalResource.loadString("toArray()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        }
        int count = m_pointMs.size();
        PointM[] pointMs = new PointM[count];
        for(int i=0;i<count;i++){
            pointMs[i] = (PointM)m_pointMs.get(i);
        }
        return pointMs;
    }

    PointMs(PointMs pointMs, GeoLineM geoLineM) {
        this(pointMs);
        this.m_geoLineM = geoLineM;
    }

    public PointMs clone() {
        if (this.m_geoLineM != null) {
           if (this.m_geoLineM.getHandle() == 0) {
               String message = InternalResource.loadString("clone()",
                       InternalResource.HandleObjectHasBeenDisposed,
                       InternalResource.BundleName);
               throw new IllegalStateException(message);
           }
           int indexLine = m_geoLineM.getPartsList().indexOf(this);
           //��indexLine = -1ʱ��˵���ö����Ѿ����Ƴ�����Ӧ�׳���Ӧ���쳣��
           if (indexLine == -1) {
               //������Ϊ-1ʱ����˵�������ѱ��Ƴ����׳�IllegalStateException
               String message = InternalResource.loadString("clone()",
                       InternalResource.HandleObjectHasBeenDisposed,
                       InternalResource.BundleName);
               throw new IllegalStateException(message);
           }
       }
       return new PointMs(this);
    }

    void setOwner(GeoLineM owner){
        m_geoLineM = owner;
    }

    private ArrayList<PointM> m_pointMs = null;
    private GeoLineM m_geoLineM = null;
}
