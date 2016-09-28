package com.supermap.data;

import java.util.ArrayList;

/**
 * <p>Title:路线点对象集合 </p>
 *
 * <p>Description: 用于表示精度为Double的路线点对象集合</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 张继南
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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
     * 设置指定序号的路线点对象
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
            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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
     * 路线集合末尾增加一个点
     * @param ptM   欲增加的点，不能是空的路线点对象
     * @return      index,返回增加点的序号
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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
     * 线集合末尾增加一系列点，这些点由路线点对象数组给出
     * @param pointMs   欲增加的点的数组，不包含空的路线点对象
     * @return          返回增加的点的总数
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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
     * 路线集合中从指定位置起插入一个点
     * @param pt   欲插入的点, 不能是空的路线点对象
     * @return     插入点是否成功，true为成功，false为失败
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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
     * 路线集合中从指定位置起插入一系列路线点，这些点由数组给出
     * @param pointMs   欲插入的路线点的数组，不包含空的路线点对象
     * @return          插入的路线点的总数
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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

             //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
             if (indexLine == -1) {
                 //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
                 String message = InternalResource.loadString("removeRange()",
                         InternalResource.HandleObjectHasBeenDisposed,
                         InternalResource.BundleName);
                 throw new IllegalStateException(message);
             }
             //当PointMs 为geoLineM的part时，则当PointMs的移除后个数（count）小于2时，则执行removeRange时抛出UnsupportedOperationException异常。
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

            //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
            if (indexLine == -1) {
                //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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
           //当indexLine = -1时，说明该对象已经被移除掉，应抛出对应的异常！
           if (indexLine == -1) {
               //当索引为-1时，则说明对象已被移出，抛出IllegalStateException
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
