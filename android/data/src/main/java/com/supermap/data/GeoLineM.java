package com.supermap.data;


import java.util.ArrayList;

/**
 * <p>Title: ·�߶���</p>
 *
 * <p>Description: ��һ�����X��Y���������Զ���ֵ�ĵ���ɵ����Ե������</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author �ż���
 * @version 2.0
 */

public class GeoLineM extends Geometry {

    private ArrayList<PointMs> m_geoLineMParts; //���ڴ��pointMs

    ArrayList<PointMs> getPartsList() {
        return this.m_geoLineMParts;
    }

    /**
     * ���캯��
     */
    public GeoLineM() {
        long handle = GeoLineMNative.jni_New();
        this.setHandle(handle, true);
        m_geoLineMParts = new ArrayList<PointMs>();
    }
    /**
     * �������캯��
     * @param geoLine GeoLine
     */
    public GeoLineM(GeoLineM geoLineM) {

        if (geoLineM.getHandle() == 0) {
            String message = InternalResource.loadString("geoLineM",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        long handle = GeoLineMNative.jni_Clone(geoLineM.getHandle());
        this.setHandle(handle, true);
        m_geoLineMParts = new ArrayList<PointMs>();
        for (int i = 0; i < geoLineM.getPartsList().size(); i++) {
            //��ArrayList������ȿ���
            PointMs pointMs = (PointMs) geoLineM.getPartsList().get(i);
            m_geoLineMParts.add(pointMs.clone());
        }
    }

    /**
     * ���ݵ㴮����һ��·�߶���
     * @param points Point2D[]
     */
    public GeoLineM(PointMs pointMs) {
        this();
        addPart(pointMs);
    }

    //�ڲ�ʹ��
    GeoLineM(long handle) {
        this.setHandle(handle, false);
        m_geoLineMParts = new ArrayList<PointMs>();
        this.refrashPartsList();
    }

    /**
     * ����·�߶����Ƿ�Ϊ��
     * @return boolean
     */
    public boolean isEmpty() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getIsEmpty()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return this.getPartCount() == 0;
    }

    /**
     * ·�߶���Ŀ�¡
     * @return Geometry
     */
    public GeoLineM clone() {

        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "clone()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        // the copy constructor will check the handle
        return new GeoLineM(this);
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
            GeoLineMNative.jni_Delete(getHandle());
            this.setHandle(0);

            clearHandle();
        }
    }

    /**
     * ����·�߶���ĳ��ȣ���λ�����ݼ��ĵ�λ��ͬ��
     * @return double
     */
    public double getLength() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getLength()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return GeoLineMNative.jni_GetLength(getHandle());
    }


    /**
     * ����·�߶�����Ӷ��������
     * �򵥶���ĸ�����ֵΪ1���߶�����պ󣬸�����ֵΪ0��
     * @return int
     */
    public int getPartCount() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getPartCount()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return GeoLineMNative.jni_GetPartCount(getHandle());
    }


    /**
     * ��·�߶���׷��һ���Ӷ���
     * @param pointMs PointMs
     * @return int
     */
    public int addPart(PointMs pointMs) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "addPart(PointMs pointMs)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        int length = pointMs.getCount();
        //�ߵ��Ӷ���ĵ���Ӧ������������
        if (length < 2) {
            String message = InternalResource.loadString("pointMs",
                    InternalResource.GeoLineInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        double[] xs = new double[length];
        double[] ys = new double[length];
        double[] ms = new double[length];

        for (int i = 0; i < length; i++) {
            xs[i] = pointMs.getItem(i).getX();
            ys[i] = pointMs.getItem(i).getY();
            ms[i] = pointMs.getItem(i).getM();
        }
        //ͬ������m_GeoLineParts
        PointMs newPointMs = new PointMs(pointMs, this);
        m_geoLineMParts.add(newPointMs);
        return GeoLineMNative.jni_AddPart(getHandle(), xs, ys, ms);
    }

    /**
     * ɾ��·�߶����е�ָ��index���Ӷ���
     * @param index int  ָ�����Ӷ���������
     * @return boolean
     */
    public boolean removePart(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "removePart(int index)",
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
        boolean bResult = GeoLineMNative.jni_RemovePart(getHandle(), index);
        if (bResult == true) {
            //ͬ������m_GeoLineParts
            m_geoLineMParts.remove(index);
        }
        return bResult;
    }


    /**
     *  ��ռ��ζ���
     */
    public void setEmpty() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setEmpty()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        GeoLineMNative.jni_Clear(getHandle());

        //���m_geoLineParts
        this.m_geoLineMParts.clear();
    }

    /**
     * ��·�߶���ת��Ϊ����󣬳ɹ����������
     * ����û�з�յ�·�߶���ת��Ϊ�����ʱ�������β�Զ���������
     * @return GeoRegion   ת���ɹ������������
     */
    public GeoRegion convertToRegion() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "convertToRegion()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        long regionHandle = GeoLineMNative.jni_ConvertToRegion(this.getHandle());
        GeoRegion region = new GeoRegion(regionHandle);
        region.setIsDisposable(true);
        //����GeoLine����ʵ����ĳ���Ӷ���ĵ�������3ʱ,��ʱ���Ӷ�����convertToRegion,�׳�UnsupportOperationException
        if (this.getPartCount() != region.getPartCount()) {
            String message = InternalResource.loadString("convertToRegion",
                    InternalResource.GeoLienUnsupportOperation,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        return region;
    }

    /**
       * ��·������ָ���ľ����ҵ㣬��·�ߵ���ʼ�㿪ʼ���ҡ�
       * �������Ϊ��ֵʱ���׳�IllegalArgumentException�쳣��������ľ������·�ߵ��ܳ��ȣ�����·�ߵ�ĩ�˵�
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
              GeoLineMNative.jni_FindPointOnLineByDistance(getHandle(),
                      distance,
                      point);
              pt.setX(point[0]);
              pt.setY(point[1]);
          }
          return pt;
    }

    /**
     * ���ڻ�ȡ�߶����е�ָ���Ӷ���ĵ㴮
     * @param index int  �Ӷ��������
     * @return PointMs  �ɹ�����PointMs����ʧ�ܷ��ؿ�ֵ��
     */
    public PointMs getPart(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getPart(int index)",
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
        return (PointMs) m_geoLineMParts.get(index);
    }

    /**
     * ���߶����е�ָ��λ�ò���һ����ά���Ӷ���
     * @param index int  �����λ��
     * @param pt Point2D[]  �������Ķ�λ��㴮
     * @return boolean  �ɹ��򷵻� True�����򷵻� False
     */
    public boolean insertPart(int index, PointMs pointMs) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "insertPart(int index, PointMs pointMs)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        boolean bResult = false;
        int partCount = getPartCount();
        //���ڵ�getPartCount == indexʱ��UGC������countλ�ò��ܲ��룬�ʴ�ʱתΪ����addPart����
        if (partCount == index) {
            int indexInsert = addPart(pointMs);
            bResult = (indexInsert == index);
            if (bResult == true) {
                //ͬ������m_GeoLineParts
                PointMs newPointMs = new PointMs(pointMs, this);
                m_geoLineMParts.add(index, newPointMs);
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

        int length = pointMs.getCount();
        //�ߵ��Ӷ���ĵ���Ӧ������������
        if (length < 2) {
            String message = InternalResource.loadString("PointMs",
                    InternalResource.GeoLineInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);

        }
        double[] xs = new double[length];
        double[] ys = new double[length];
        double[] ms = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = pointMs.getItem(i).getX();
            ys[i] = pointMs.getItem(i).getY();
            ms[i] = pointMs.getItem(i).getM();
        }
        bResult = GeoLineMNative.jni_InsertPart(getHandle(), index, xs, ys,ms);
        if (bResult == true) {
            //ͬ������m_GeoLineParts
            PointMs newPointMs = new PointMs(pointMs, this);
            m_geoLineMParts.add(index, newPointMs);
        }
        return bResult;

    }

    public int indexOf(PointMs part) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "indexOf(PointMs part)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return m_geoLineMParts.indexOf(part);
    }

    /**
     * ��·�߶���ĵ㴮���н��еߵ�
     * @return boolean  �ɹ��򷵻� True�����򷵻� False
     */
    public boolean reverse() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "reverse()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        boolean bResult = GeoLineMNative.jni_Reverse(getHandle());
        refreshFromUGC();
        return bResult;
    }
    /**
     * ��·�߶����Mֵ���еߵ�
     * @return boolean  �ɹ��򷵻� True�����򷵻� False
     */
    public boolean reverseMOrder() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "reverseMOrder()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        boolean bResult = GeoLineMNative.jni_ReverseMOrder(getHandle());
        if(bResult) {
         	refreshFromUGC();
         	return true;
        }
        else {
        	return false;
        }
    }
    /**
     * ��ָ����λ�����޸�·���Ӷ���
     * @param index int
     * @param pointMs PointMs
     * @return boolean
     */
    public boolean setPart(int index, PointMs pointMs) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setPart(int index, PointMs pointMs)",
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
        int length = pointMs.getCount();
        //�ߵ��Ӷ���ĵ���Ӧ������������
        if (length < 2) {
            String message = InternalResource.loadString("PointMs",
                    InternalResource.GeoLineInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);

        }

        double[] xs = new double[length];
        double[] ys = new double[length];
        double[] ms = new double[length];

        for (int i = 0; i < length; i++) {
            xs[i] = pointMs.getItem(i).getX();
            ys[i] = pointMs.getItem(i).getY();
            ms[i] = pointMs.getItem(i).getM();
        }

        boolean bResult = GeoLineMNative.jni_SetPart(getHandle(), index, xs, ys,ms);
        if (bResult == true) {
            //ͬ������m_GeoLineParts
        	PointMs oldPointMs = m_geoLineMParts.get(index);
            PointMs newPointMs = new PointMs(pointMs, this);
            m_geoLineMParts.set(index, newPointMs);
            oldPointMs.setOwner(null);
        }
        return bResult;
    }
    /**�ڲ���������ֻ���µײ�PointMsʱʹ��
     * ��PointMs��ͨ��������ʱ��ͬʱ����������󣬽����ƻ�������
     * ��ָ����λ�����޸�·���Ӷ���
     * @param index int
     * @param PointMs pointMs
     * @return boolean
     */
    boolean setPartJustToUGC(int index, PointMs pointMs) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setPart(int index, PointMs pointMs)",
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
        int length = pointMs.getCount();
        //�ߵ��Ӷ���ĵ���Ӧ������������
        if (length < 2) {
            String message = InternalResource.loadString("PointMs",
                    InternalResource.GeoLineInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);

        }

        double[] xs = new double[length];
        double[] ys = new double[length];
        double[] ms = new double[length];

        for (int i = 0; i < length; i++) {
            xs[i] = pointMs.getItem(i).getX();
            ys[i] = pointMs.getItem(i).getY();
            ms[i] = pointMs.getItem(i).getM();
        }

        boolean bResult = GeoLineMNative.jni_SetPart(getHandle(), index, xs, ys,ms);
        return bResult;
    }

    /**
     * ��XML���ؼ��ζ���
     * @param xml String
     * @return boolean
     */
    public boolean fromXML(String xml) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "fromXML(String xml)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        boolean result = super.fromXML(xml);
        if(result){
            this.refrashPartsList();
        }
        return result;
    }

//    /**
//     * ��ȡ����ľ���
//     * ���޸Ķ�������
//     * @param startPoint Point2D
//     * @param endPoint Point2D
//     * @return Geometry
//     */
//    public Geometry mirror(Point2D startPoint, Point2D endPoint) {
//        if (this.getHandle() == 0) {
//            String message = InternalResource.loadString(
//                    "mirror(Point2D startPoint, Point2D endPoint)",
//                    InternalResource.HandleObjectHasBeenDisposed,
//                    InternalResource.BundleName);
//            throw new IllegalStateException(message);
//        }
//        GeoLineM geoLineM = (GeoLineM)super.mirror(startPoint, endPoint);
//        //����mirror�󣬵õ�����һ�����ߣ������ߵ�m_geoLineParts��
//        int count = geoLineM.getPartCount();
//        geoLineM.m_geoLineMParts = new ArrayList<PointMs>();
//        for (int i = 0; i < count; i++) {
//            //��m_geoLineParts����������������ݡ�
//            PointMs pointMs = new PointMs(geoLineM.getPartFromUGC(i));
//            PointMs newPointMs = new PointMs(pointMs, geoLineM);
//            geoLineM.m_geoLineMParts.add(newPointMs);
//        }
//        return geoLineM;
//    }

    /**
     * ƫ��
     * @param dx double
     * @param dy double
     */
    public void offset(double dx, double dy) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "offset(double dx, double dy)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        super.offset(dx, dy);
        this.refreshFromUGC();
    }
    public void offsetMeasure(double measure) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "offset(double dx, double dy)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        GeoLineMNative.jni_OffsetMeasure(getHandle(), measure);
        this.refreshFromUGC();
    }
    /**
     * �ı伸�ζ���Ĵ�С
     * �����Ϊ����ʱ����ˮƽ��ת�����߶�Ϊ����ʱ������ֱ��ת
     * @param bounds Rectangle2D
     */
    public void resize(Rectangle2D bounds) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "resize(Rectangle2D bounds)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        super.resize(bounds);
        this.refreshFromUGC();
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

    /**
     * ��ȡ���Mֵ
     * @return double
     */
    public double getMaxM(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getMaxM()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoLineMNative.jni_GetMaxM(getHandle());
    }

    /**
     * ��ȡ��СMֵ
     * @return double
     */
    public double getMinM(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getMinM()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoLineMNative.jni_GetMinM(getHandle());
    }

    //add by xuzw 2009-02-10 6.0����
    /**
     * ת��Ϊ��ά�߶���
     * @return GeoLine
     */
    public GeoLine convertToLine() {
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"convertToLine()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		long handle = GeoLineMNative.jni_ConvertToLine(this.getHandle());
		GeoLine geoLine = null;
		if(handle != 0) {
			geoLine = new GeoLine(handle);
			geoLine.setIsDisposable(true);
		}
		return geoLine;
    }

//    /**
//     * ���㵽ָ��·���Ӷ����׽��һ������Ľڵ㴦�����Զ���ֵ
//     * @param distance double  ��ָ��·���Ӷ����׽��ľ���
//     * @param subObjId int Ҫ������Ӷ����ID�š�ȱʡΪ0���������һ���Ӷ���
//     * @return double ���ص�ָ��·���Ӷ����׽��һ������Ľڵ㴦�����Զ���ֵ
//     */
//    public double getMAtDistance(double distance,int subObjId){
//        if (this.getHandle() == 0) {
//            String message = InternalResource.loadString(
//                    "getMAtDistance(double distance,int subObjId)",
//                    InternalResource.HandleObjectHasBeenDisposed,
//                    InternalResource.BundleName);
//            throw new IllegalStateException(message);
//        }
//        return GeoLineMNative.jni_GetMAtDistance(getHandle(),distance,subObjId);
//    }
    public void setMAsDistance(double originM){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setMAsDistance(double originM)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        GeoLineMNative.jni_SetMAsDistance(getHandle(),originM,1.0,true);
        refreshFromUGC();
    }
    public void setMAsDistance(double originM,double scale){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setMAsDistance(double originM,double scale)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        GeoLineMNative.jni_SetMAsDistance(getHandle(),originM,scale,true);
        refreshFromUGC();
    }
    public void setMAsDistance(double originM,double scale,boolean isIgnoreGap){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setMAsDistance(double originM,double scale,boolean isIgnoreGap)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        GeoLineMNative.jni_SetMAsDistance(getHandle(),originM,scale,isIgnoreGap);
        refreshFromUGC();
    }
//
//    public void offsetM(double offset){
//        if (this.getHandle() == 0) {
//            String message = InternalResource.loadString(
//                    "offsetM(double offset)",
//                    InternalResource.HandleObjectHasBeenDisposed,
//                    InternalResource.BundleName);
//            throw new IllegalStateException(message);
//        }
//        GeoLineMNative.jni_OffsetM(getHandle(),offset);
//    }

//    public void getDistanceAtM(double measure,int subObjId){
//        if (this.getHandle() == 0) {
//            String message = InternalResource.loadString(
//                    "getDistanceAtM(double measure,int subObjId)",
//                    InternalResource.HandleObjectHasBeenDisposed,
//                    InternalResource.BundleName);
//            throw new IllegalStateException(message);
//        }
//        GeoLineMNative.jni_GetDistanceAtM(getHandle(),measure,subObjId);
//    }

//    public Point2D getPointAtM(double measure,int subObjId){
//        if (this.getHandle() == 0) {
//            String message = InternalResource.loadString(
//                    "getPointAtM(double measure,int subObjId)",
//                    InternalResource.HandleObjectHasBeenDisposed,
//                    InternalResource.BundleName);
//            throw new IllegalStateException(message);
//        }
//        Point2D pt = new Point2D();
//        double[] point = new double[2];
//        GeoLineMNative.jni_GetPointAtM(getHandle(), measure,subObjId,point);
//        pt.setX(point[0]);
//        pt.setY(point[1]);
//        return pt;
//    }

//    public GeoLine getSubCurveAtM(double fromMeasure,double toMeasure){
//        if (this.getHandle() == 0) {
//            String message = InternalResource.loadString(
//                    "getSubCurveAtM(double fromMeasure,double toMeasure)",
//                    InternalResource.HandleObjectHasBeenDisposed,
//                    InternalResource.BundleName);
//            throw new IllegalStateException(message);
//        }
//        long geoLineHandle = GeoLineMNative.jni_GetSubCurveAtM(getHandle(),fromMeasure,toMeasure);
//        GeoLine geoLine = new GeoLine(geoLineHandle);
//        return geoLine;
//    }

//    public boolean interpolateM(int fromIndex,int toIndex,int fromSubObjId,int toSubObjId){
//        if (this.getHandle() == 0) {
//            String message = InternalResource.loadString(
//                    "interpolateM(int fromIndex,int toIndex,int fromSubObjId,int toSubObjId)",
//                    InternalResource.HandleObjectHasBeenDisposed,
//                    InternalResource.BundleName);
//            throw new IllegalStateException(message);
//        }
//        boolean result = false;
//        result = GeoLineMNative.jni_InterpolateM(getHandle(),fromIndex,toIndex,fromSubObjId,toSubObjId);
//        return result;
//    }

//    public boolean reversMOrder(){
//        if (this.getHandle() == 0) {
//            String message = InternalResource.loadString(
//                    "reversMOrder()",
//                    InternalResource.HandleObjectHasBeenDisposed,
//                    InternalResource.BundleName);
//            throw new IllegalStateException(message);
//        }
//        boolean result = false;
//        result = GeoLineMNative.jni_Reverse(getHandle());
//        return result;
//    }

//    public void calculateNoM(){
//        if (this.getHandle() == 0) {
//            String message = InternalResource.loadString(
//                    "calculateNoM()",
//                    InternalResource.HandleObjectHasBeenDisposed,
//                    InternalResource.BundleName);
//            throw new IllegalStateException(message);
//        }
//        GeoLineMNative.jni_CalculateNoM(getHandle());
//    }

    protected void clearHandle() {
        super.clearHandle();
        //ͬ������m_geoLineParts
        if (m_geoLineMParts != null) {
            m_geoLineMParts.clear();
            m_geoLineMParts = null;
        }
    }
    protected static void clearHandle(GeoLineM lineM) {
    	lineM.clearHandle();
    }


    //��GeoLineM��UGC�ײ�ı�ʱ������m_geoLineMParts���ı�m_geoLineMParts�е�ֵ�����ı�����
    private void refreshFromUGC() {
        //����m_geoLineParts
        int partCount = this.getPartCount();
        for (int i = 0; i < partCount; i++) {
            PointMs ptMs = (PointMs) m_geoLineMParts.get(i);
            //�Ƚ�pts��UserType ��ΪNone��0����Point2Ds
            //pts.setUserType(UserType.NONE);
            ptMs.setOwner(null);
            ptMs.clear();
            ptMs.addRange(this.getPartFromUGC(i));
            //�����ֵ���ٽ�pts��UserType ��ΪgeoLine
            //pts.setUserType(UserType.GEOLINE);
        }
    }


    //����ȡ�õײ�������Part
    private PointM[] getPartFromUGC(int index) {
        //�ж������Ƿ�Խ��
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        int length = GeoLineMNative.jni_GetPartPointCount(getHandle(),
                index);
        if (length > 1) {
            PointM[] pointMs = new PointM[length];
            double[] xs = new double[length];
            double[] ys = new double[length];
            double[] ms = new double[length];
            GeoLineMNative.jni_GetPart(getHandle(), index, xs, ys,ms);
            for (int i = 0; i < length; i++) {
                pointMs[i] = new PointM(xs[i], ys[i], ms[i]);
            }
            return pointMs;
        } else {
            return null;
        }
    }

    //��ִ��XML�����󣬻���ֱ�ӵõ�handleʱ������m_geoLineMParts���ı�m_geoLineMParts�е�ֵ�����ı�����
    private void refrashPartsList() {
        //����m_geoLineParts��������ݡ�
        int count = getPartCount();
        m_geoLineMParts.clear();
        for (int i = 0; i < count; i++) {
            //��m_geoLineParts����������������ݡ�
            PointMs pointMs = new PointMs(this.getPartFromUGC(i));
            PointMs newPointMs = new PointMs(pointMs, this);
            m_geoLineMParts.add(newPointMs);
        }
    }
    /**
     * ͨ����̣��̶ȡ�����ֵ�����ص�
     * @author Shanqc
     * @date 2011-01-06
     * @param measure ���
     * @return �ɹ�����Point2D����ʧ�ܷ��ؿ�(�����Ӷ���֮��ľ���)
     */
    public Point2D getPointAtM(double measure){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getPointAtM(double measure)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
    	return getPointAtM(measure,0.0,true);
    }
    /**
     * ͨ����̣��̶ȡ�����ֵ�����ص�
     * @author Shanqc
     * @date 2010-11-30
     * @param measure ���
     * @param offset ƫ��
     * @param isIgnoreGap �Ƿ�����Ӷ���֮��ľ���
     * @return �ɹ�����Point2D����ʧ�ܷ��ؿ�
     */
    public Point2D getPointAtM(double measure,double offset, boolean isIgnoreGap){
      if (this.getHandle() == 0) {
          String message = InternalResource.loadString(
                  "getPointAtM(double measure,double offset, boolean isIgnoreGap)",
                  InternalResource.HandleObjectHasBeenDisposed,
                  InternalResource.BundleName);
          throw new IllegalStateException(message);
      }
      Point2D pt = new Point2D();
      double[] point = new double[2];
      GeoLineMNative.jni_GetPointAtM(getHandle(),point,measure,offset,isIgnoreGap);
      pt.setX(point[0]);
      pt.setY(point[1]);
  	return pt;
  }
    /**
     * ͨ�����뷵�ص�
     * @author Shanqc
     * @date 2010-11-30
     * @param distance ����
     * @param isIgnoreGap �Ƿ�����Ӷ���֮��ľ���
     * @return  �ɹ�����Point2D����ʧ�ܷ��ؿ�
     */
    public Point2D getPointAtDistance(double distance){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getPointAtDistance(double distance, boolean isIgnoreGap)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        Point2D pt = new Point2D();
        double[] point = new double[2];
        GeoLineMNative.jni_GetPointAtDistance(getHandle(),point,distance,false);
        pt.setX(point[0]);
        pt.setY(point[1]);
    	return pt;
    }
    public Point2D getPointAtDistance(double distance, boolean isIgnoreGap){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getPointAtDistance(double distance, boolean isIgnoreGap)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        Point2D pt = new Point2D();
        double[] point = new double[2];
        GeoLineMNative.jni_GetPointAtDistance(getHandle(),point,distance,isIgnoreGap);
        pt.setX(point[0]);
        pt.setY(point[1]);
    	return pt;
    }

    /**
     * ͨ�����뷵��Mֵ
     * @author Shanqc
     * @date 2011-01-06
     * @param distance ����
     * @return  �ɹ�����Mֵ��ʧ�ܷ���-9999
     */
    public double getMAtDistance(double distance){
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getMAtDistance(double distance)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoLineMNative.jni_GetMAtDistance(getHandle(), distance,true);
    }
    /**
     * ͨ�����뷵��Mֵ
     * @author Shanqc
     * @date 2011-01-06
     * @param distance ����
     * @param isIgnoreGap �Ƿ�����Ӷ���֮��ľ���
     * @return  �ɹ�����Mֵ��ʧ�ܷ�-9999
     */
    public double getMAtDistance(double distance, boolean isIgnoreGap){
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getMAtDistance(double distance, boolean isIgnoreGap)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoLineMNative.jni_GetMAtDistance(getHandle(), distance, isIgnoreGap);
    }
    /**
     * ͨ�����뷵��Mֵ
     * @author Shanqc
     * @date 2011-01-06
     * @param distance ����
     * @param subindex �Ӷ������
     * @param isIgnoreGap �Ƿ�����Ӷ���֮��ľ���
     * @return  �ɹ�����Mֵ��ʧ�ܷ�-9999
     */
    public double getMAtDistance(double distance, int subindex, boolean isIgnoreGap){
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getMAtDistance(double distance, boolean isIgnoreGap)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoLineMNative.jni_GetMAtDistanceSub(getHandle(), distance, subindex-1,isIgnoreGap);
    }
    /**
     * ͨ��Mֵ���ؾ���
     * @author Shanqc
     * @date 2011-01-06
     * @param measure Mֵ
     * @param isIgnoreGap �Ƿ�����Ӷ���֮��ľ���
     * @return  �ɹ����ؾ���ֵ��ʧ�ܷ���-9999
     */
    public double getDistanceAtM(double measure){
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getDistanceAtM(double measure)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoLineMNative.jni_GetDistanceAtM(getHandle(), measure, false);
    }
    /**
     * ͨ��Mֵ���ؾ���
     * @author Shanqc
     * @date 2011-01-06
     * @param measure Mֵ
     * @return  �ɹ����ؾ���ֵ��ʧ�ܷ���-9999
     */
    public double getDistanceAtM(double measure,boolean isIgnoreGap){
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getDistanceAtM(double measure,boolean isIgnoreGap)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoLineMNative.jni_GetDistanceAtM(getHandle(), measure, isIgnoreGap);
    }
    /**
     * ͨ��Mֵ���ؾ���
     * @author Shanqc
     * @date 2011-01-06
     * @param distance ����
     * @param subindex �Ӷ������
     * @param isIgnoreGap �Ƿ�����Ӷ���֮��ľ���
     * @return  �ɹ�����Mֵ��ʧ�ܷ�-9999
     */
    public double getDistanceAtM(double distance, int subindex, boolean isIgnoreGap){
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getMAtDistance(double distance, boolean isIgnoreGap)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoLineMNative.jni_GetDistanceAtMSub(getHandle(), distance, subindex-1,isIgnoreGap);
    }
    /**
     * �趨ָ���㴦��Mֵ
     * @author Shanqc
     * @date 2011-01-06
     * @param point2D ָ����
     * @param measure ָ�����
     * @return  �ɹ�����true��ʧ�ܷ�false
     */
    public boolean setMAtPoint(Point2D point2D, double measure){
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"Point2D point2D, double measure,double tolorence,WhereToCalibrate wheretocalibrate)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
    	 double[] point = new double[2];
    	 point[0]=point2D.getX();
    	 point[1]=point2D.getY();
//    	 double tolerance;
//    	 Rectangle2D rcLine = this.getBounds();
//    	 tolerance	= (rcLine.getWidth()>rcLine.getHeight()) ? rcLine.getWidth() : rcLine.getHeight();
//    	 tolerance /= 1000000;
    	 int index = GeoLineMNative.jni_SetMAtPoint(getHandle(),point,measure,0.000001,0);
    	 if(index!=-1){
    		 refrashPartsList();
    		 return true;
    	 }
    	 else{
    		 return false;
    	 }
    }
    /**
     * �趨ָ���㴦��Mֵ
     * @author Shanqc
     * @date 2011-01-06
     * @param point2D ָ����
     * @param measure ָ��Mֵ
     * @param tolorence ����
     * @param wheretocalibrate �Ƿ�У���������趨У��λ��
     * @return  �ɹ�����true��ʧ�ܷ�false
     */
    public boolean setMAtPoint(Point2D point2D, double measure,double tolerance,
    		WhereToCalibrate wheretocalibrate){
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"Point2D point2D, double measure,double tolorence,WhereToCalibrate wheretocalibrate)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
    	 double[] point = new double[2];
    	 point[0]=point2D.getX();
    	 point[1]=point2D.getY();
    	 int wheretocali= wheretocalibrate.value();
    	 int index = GeoLineMNative.jni_SetMAtPoint(getHandle(),point,measure,tolerance,wheretocali);
    	 if(index!=-1){
    		 return true;
    	 }
    	 else{
    		 return false;
    	 }

    }

    /**
     * ����ָ���㴦��Mֵ
     * @author Shanqc
     * @date 2011-01-16
     * @param point2D ָ����
     * @param measure ָ��Mֵ
     * @param tolorence ����
     * @param isIgnoreGap �Ƿ�����Ӷ���ļ��
     * @return  �ɹ�����true��ʧ�ܷ�false
     */
    public double getMAtPoint(Point2D point2D, double tolerance,boolean isIgnoreGap){
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getMAtPoint(Point2D point2D, double tolerance,boolean isIgnoreGap)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
    	 double measure=-9999;
    	 double point[]=new double[2];
    	 point[0]=point2D.getX();
    	 point[1]=point2D.getY();

    	 measure= GeoLineMNative.jni_GetMAtPoint(getHandle(),point,tolerance,isIgnoreGap);
    	 return measure;
    }

    /**
     * ��ȡfromMeasure��toMeasure֮����߶���
     * @author Shanqc
     * @date 2011-01-25
     * @param toMeasure	��ʼMeasureֵ
     * @param toMeasure	��ֹMeasureֵ
     * @return �߶���
     */
    public GeoLine getSubCurveAtM(double fromMeasure,double toMeasure) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
							"getSubCurveAtM(double fromMeasure,double toMeasure)",
							InternalResource.HandleObjectHasBeenDisposed,
							InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		long handle=GeoLineMNative.jni_GetSubCurveAtM(getHandle(),fromMeasure,toMeasure);

		if (handle != 0) {
			//return new GeoLine(handle);
			GeoLine line = (GeoLine)Geometry.createInstance(handle);
			return line;
		}
		return null;
    }
    
    public GeoLineM getSubLineMAtM(double startMeasure, double endMeasure)
	{
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
							"getLineMAtM(double startMeasure, double endMeasure)",
							InternalResource.HandleObjectHasBeenDisposed,
							InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

    	double offset = 0.0;
    	boolean isIgnoreGap = true;
		long lineHandle = GeoLineMNative.jni_GetLineMAtM(getHandle(),startMeasure, endMeasure, offset, isIgnoreGap);
		if (lineHandle != 0) {
			//return new GeoLineM(lineHandle);
			GeoLineM lineM = (GeoLineM)Geometry.createInstance(lineHandle);
			return lineM;
		}
		return null;
	}
    /**
     * ��������·�ɶ��󣬽�geoLineM��ĩ����otherLineM���������������
     * @author Shanqc
     * @date 2011-01-07
     * @param geoLineM Ҫ�ϲ��ĵ�һ����·
     * @param otherLineM Ҫ�ϲ�����һ����·
     * @return  �ɹ������µ�GeoLineM��ʧ�ܷ���Ϊ�ա�
     */
    public static GeoLineM union(GeoLineM geoLineM, GeoLineM otherLineM){
    	if (geoLineM.getHandle() == 0) {
			String message = InternalResource.loadString(
					"geolinem",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

    	if (otherLineM.getHandle() == 0) {
            String message = InternalResource.loadString("otherLineM",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
    	//�����ַ����õ��Ӷ���ĸ�������������͵ײ㷽��������ʹ�������������һЩ
    	//int partcount=otherLineM.getPartCount();
    	GeoLineM resultlinemGeoLineM= new GeoLineM(geoLineM);
    	int partcount=otherLineM.getPartsList().size();
        for (int i = 0; i < partcount; i++) {
           resultlinemGeoLineM.addPart(otherLineM.getPart(i));
        }
       
    	return resultlinemGeoLineM;
    }
    /**
     * ��������·�ɶ���ʹ��ǰ·�ɶ����β�ڵ�����һ·�ɶ�����׽ڵ�������
     * @author Shanqc
     * @date 2010-01-07
     * @param otherLineM ��һ����·
     * @return  �ɹ�����true��ʧ�ܷ�false
     */
    public boolean joint(GeoLineM otherLineM){
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"Joint(GeoLineM otherLineM)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

    	if (otherLineM.getHandle() == 0) {
            String message = InternalResource.loadString("otherLineM",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
    	//�����ַ����õ��Ӷ���ĸ�������������͵ײ㷽��������ʹ�������������һЩ
    	//int partcount=otherLineM.getPartCount();
    	int otherpartcount=otherLineM.getPartsList().size();
    	int origincount=getPartCount();
    	PointMs pointMsoriginMs= (PointMs)getPartsList().get(origincount-1);
    	PointMs pointMsIn = (PointMs) otherLineM.getPartsList().get(0);
    	pointMsoriginMs.addRange(pointMsIn.toArray());
        for (int i = 1; i < otherpartcount; i++) {
            //��ArrayList������ȿ���
           PointMs pointMs = (PointMs) otherLineM.getPartsList().get(i);
            m_geoLineMParts.add(pointMs.clone());
        }

    	return true;
    }
    /**
     * ͨ����ָ���·���ֳ�������
     * @author Shanqc
     * @date 2011-01-16
     * @param splitPoint �ָ��
     * @param geoLineM1  �ָ��Ķ���1
     * @param geoLineM2  �ָ��Ķ���1
     * @return �ɹ�����true��ʧ�ܷ���false
     */
    public boolean split(Point2D splitPoint,GeoLineM geoLineM1,GeoLineM geoLineM2)
    {
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"Split(Point2D splitPoint,GeoLineM geolinem1,GeoLineM geolinem2)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
    	 double[] point = new double[2];
    	 point[0]=splitPoint.getX();
    	 point[1]=splitPoint.getY();
    	 if(GeoLineMNative.jni_Split(getHandle(),point,geoLineM1.getHandle(),geoLineM2.getHandle()))
    	 {
    		 geoLineM1.refrashPartsList();
    		 geoLineM2.refrashPartsList();
    		 return true;
    	 }

    	return false;
    }
    /**
     * ͨ���㴮У����·
     * @author Shanqc
     * @date 2011-01-25
     * @param pointMs	 �㴮
     * @param method У������
     * @param isIgnoreGap �Ƿ�����Ӷ���ļ��
     * @return �ɹ�����true��ʧ�ܷ���false
     */
    public boolean calibrateLineM(PointMs pointMs,CalibrateMode method,  boolean isIgnoreGap)
    {
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"CalibrateLineM(PointMs pointMs)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
          int length = pointMs.getCount();
          //�ߵ��Ӷ���ĵ���Ӧ������������
          if (length < 2) {
              String message = InternalResource.loadString("PointMs",
                      InternalResource.GeoLineInvalidPointsLength,
                      InternalResource.BundleName);
              throw new IllegalArgumentException(message);

          }

          double[] xs = new double[length];
          double[] ys = new double[length];
          double[] ms = new double[length];

          for (int i = 0; i < length; i++) {
              xs[i] = pointMs.getItem(i).getX();
              ys[i] = pointMs.getItem(i).getY();
              ms[i] = pointMs.getItem(i).getM();
          }

          boolean bResult = GeoLineMNative.jni_CalibrateLineM(getHandle(), xs, ys, ms, method.value(), isIgnoreGap);
          if (bResult == true) {
              //ͬ������m_GeoLineParts
        	  m_geoLineMParts.clear();
             refrashPartsList();
          }
          return bResult;

    }
    /**
     * ͨ���߶�У����·
     * @author Shanqc
     * @date 2011-01-25
     * @param geoLineM		 �߶�
     * @param method У������
     * @param isIgnoreGap �Ƿ�����Ӷ���ļ��
     * @return �ɹ�����true��ʧ�ܷ���false
     */
    public boolean calibrateLineM(GeoLineM geoLineM, CalibrateMode method, boolean isIgnoreGap)
    {
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"(GeoLineM geoLineM, CalibrateMeasureMethod method, boolean isIgnoreGap)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
    	if (geoLineM.getHandle() == 0) {
			String message = InternalResource.loadString(
					"(GeoLineM geoLineM, CalibrateMeasureMethod method, boolean isIgnoreGap)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
    	PointMs pointMs=geoLineM.getPart(0);
        int length = pointMs.getCount();
        //�ߵ��Ӷ���ĵ���Ӧ������������
        if (length < 2) {
            String message = InternalResource.loadString("PointMs",
                    InternalResource.GeoLineInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);

        }
          double[] xs = new double[length];
          double[] ys = new double[length];
          double[] ms = new double[length];

          for (int i = 0; i < length; i++) {
              xs[i] = pointMs.getItem(i).getX();
              ys[i] = pointMs.getItem(i).getY();
              ms[i] = pointMs.getItem(i).getM();
          }

          boolean bResult = GeoLineMNative.jni_CalibrateLineM(getHandle(), xs, ys, ms, method.getUGCValue(), isIgnoreGap);
          if (bResult == true) {
              //ͬ������m_GeoLineParts
        	  m_geoLineMParts.clear();
             refrashPartsList();
          }
          
          return bResult;
    }
    /**
     * �ü���·
     * @author Shanqc
     * @date 2011-01-25
     * @param geometry	 	���ü���·
     * @param clipGeometry  �ü����󣬱�������
     * @return �ɹ�����GeoLineM����ʧ�ܷ��ؿ�
     */
    public static GeoLineM clip(GeoLineM geometry, GeoRegion clipGeometry) {
    	if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (clipGeometry == null || clipGeometry.getHandle() == 0) {
			String message = InternalResource.loadString("clipGeometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣����ѯ����֧���ߺ�������
		if (geometry.getType()!= GeometryType.GEOLINEM) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// ����쳣��Ŀ�����ֻ֧��������
		if (clipGeometry.getType()!= GeometryType.GEOREGION) {
			String message = InternalResource.loadString("clipGeometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		long ptr = GeoLineMNative.jni_Clip(geometry.getHandle(), clipGeometry
				.getHandle());
		GeoLineM resultGeometry = null;
		if (ptr != 0) {
			resultGeometry = new GeoLineM(ptr);
		}
		double b = resultGeometry.getMaxM();
        System.out.println(b);
		return resultGeometry;
    }
    /**
     * ������·Mֵ
     * @author Shanqc
     * @date 2011-01-25
     * @param fromPoint	 	��ʼ��
     * @param toPoint		��ֹ��
     * @param fromMeasure	��ʼ��Mֵ
     * @param toMeasure		��ֹ��Mֵ
     * @param tolerance  	����
     * @param wheretoCali	У��λ��
     * @param method		У������
     * @return �ɹ�����true��ʧ�ܷ�false
     */
    public boolean updateM(Point2D fromPoint,Point2D toPoint,
			  double fromMeasure,double toMeasure,double tolerance,
			  WhereToCalibrate wheretoCalibrate,
			  CalibrateMode calibrateMode) {
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"UpdateM(Point2D fromPoint,Point2D toPoint, double fromMeasure,double toMeasure,"+
					"double tolerance, WhereToCalibrate wheretoCalibrate, CalibrateMeasureMethod method)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

    	double []fromPnt=new double[2];
    	fromPnt[0]=fromPoint.getX();
    	fromPnt[1]=fromPoint.getY();

    	double []toPnt=new double[2];
    	toPnt[0]=toPoint.getX();
    	toPnt[1]=toPoint.getY();

    	boolean b=GeoLineMNative.jni_UpdateM(getHandle(), fromPnt, toPnt,
    										 fromMeasure, toMeasure, tolerance,
    										 wheretoCalibrate.value(), calibrateMode.value());
    	if(b) {
    		refrashPartsList();
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    /**
     * ������·Mֵ
     * @author Shanqc
     * @date 2011-12-31
     * @param fromIndex	 	��ʼ������
     * @param toIndex		��ֹ������
     * @param fromMeasure	��ʼ��Mֵ
     * @param toMeasure		��ֹ��Mֵ
     * @param tolerance  	����
     * @param wheretoCali	У��λ��
     * @param method		У������
     * @return �ɹ�����true��ʧ�ܷ�false
     */
    public boolean updateM(int fromIndex, int toIndex, double fromMeasure, double toMeasure,
    		WhereToCalibrate wheretoCalibrate,CalibrateMode calibrateMode){
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"UpdateM(int fromIndex, int toIndex, double fromMeasure, double toMeasure,"+
					"WhereToCalibrate wheretoCalibrate,CalibrateMode method)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
    	boolean b=GeoLineMNative.jni_UpdateMIndex(getHandle(), fromIndex, toIndex,
    										 fromMeasure, toMeasure,
    										 wheretoCalibrate.value(), calibrateMode.value());
    	if(b) {
    		refrashPartsList();
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    /**
     * ����Mֵ
     * @author Shanqc
     * @date 2011-01-25
     * @param fromPoint	 	��ʼ��
     * @param toPoint		��ֹ��
     * @param fromMeasure	��ʼ��Mֵ
     * @param toMeasure		��ֹ��Mֵ
     * @param tolerance  	����
     * @param method		У������
     * @return �ɹ�����true��ʧ�ܷ�false
     */
    public boolean interpolateM(Point2D fromPoint,Point2D toPoint,
    							double fromMeasure, double toMeasure,
    							double tolerance, CalibrateMode method) {
    	if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"InterpolateM(Point2D fromPoint,Point2D toPoint, double fromMeasure, "+
					"double toMeasure,double tolerance, CalibrateMeasureMethod method)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

    	double []fromPnt=new double[2];
    	fromPnt[0]=fromPoint.getX();
    	fromPnt[1]=fromPoint.getY();

    	double []toPnt=new double[2];
    	toPnt[0]=toPoint.getX();
    	toPnt[1]=toPoint.getY();
    	boolean b=GeoLineMNative.jni_InterpolateM(getHandle(),
				   						  	 fromPnt, toPnt, fromMeasure,toMeasure,
				   						  	 tolerance,	method.value());
    	if(b) {
    		refrashPartsList();
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    /**
     * ����Mֵ��������ʼ��֮ǰ����ֹ��֮����·�ϵ�Mֵ
     * @author Shanqc
     * @date 2011-01-25
     * @param fromPoint	 	��ʼ��
     * @param toPoint		��ֹ��
     * @param fromMeasure	��ʼ��Mֵ
     * @param toMeasure		��ֹ��Mֵ
     * @param tolerance  	����
     * @param method		У������
     * @return �ɹ�����true��ʧ�ܷ�false
     */
    public boolean extrapolateM(Point2D fromPoint, Point2D toPoint,
			double fromMeasure, double toMeasure, double tolerance,
			CalibrateMode method) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
							"ExtrapolateM(Point2D fromPoint,Point2D toPoint, double fromMeasure,"+
							"double toMeasure,double tolerance, CalibrateMeasureMethod method)",
							InternalResource.HandleObjectHasBeenDisposed,
							InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
    	double []fromPnt=new double[2];
    	fromPnt[0]=fromPoint.getX();
    	fromPnt[1]=fromPoint.getY();

    	double []toPnt=new double[2];
    	toPnt[0]=toPoint.getX();
    	toPnt[1]=toPoint.getY();

		boolean b = GeoLineMNative.jni_ExtrapolateM(getHandle(), fromPnt,
				toPnt, fromMeasure, toMeasure, tolerance, method.value());
		if (b) {
			refrashPartsList();
			return true;
		} else {
			return false;
		}
    }

    /**
     * ������ֵ����Mֵ
     * @author Shanqc
     * @date 2011-01-25
     * @param isIgnoreGap	�Ƿ�����Ӷ���֮��ļ��
     * @return �޷���ֵ
     */
    public void calculateNoM(boolean isIgnoreGap) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
							"CalculateNoM(boolean isIgnoreGap)",
							InternalResource.HandleObjectHasBeenDisposed,
							InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		GeoLineMNative.jni_CalculateNoM(getHandle(), isIgnoreGap);
		refrashPartsList();

    }
    /**
     * ��������������·
     * @author Shanqc
     * @date 2011-01-25
     * @param geoline	 	��
     * @param pointMs		��,������������
     * @return �ɹ�����GeoLineM����ʧ�ܷ��ؿ�
     */
    public static GeoLineM makeLineM(GeoLine geoLine, PointMs pointMs) {
    	if (geoLine == null || geoLine.getHandle() == 0) {
			String message = InternalResource.loadString("geoline",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
    	  int length = pointMs.getCount();
          //�ߵ��Ӷ���ĵ���Ӧ������������
          if (length < 2) {
              String message = InternalResource.loadString("pointMs",
                      InternalResource.GeoLineInvalidPointsLength,
                      InternalResource.BundleName);
              throw new IllegalArgumentException(message);
          }
		// ����쳣��ֻ֧����
		if (geoLine.getType()!= GeometryType.GEOLINE) {
			String message = InternalResource.loadString("geoline",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		 double[] xs = new double[length];
	        double[] ys = new double[length];
	        double[] ms = new double[length];

	        for (int i = 0; i < length; i++) {
	            xs[i] = pointMs.getItem(i).getX();
	            ys[i] = pointMs.getItem(i).getY();
	            ms[i] = pointMs.getItem(i).getM();
	        }
		long handle =GeoLineMNative.jni_MakeLineM(geoLine.getHandle(),xs,ys,ms);
		if (handle != 0) {
			//refrashPartsList();
			//return new GeoLineM(handle);
			GeoLineM lineM = (GeoLineM)Geometry.createInstance(handle);
			return lineM;
		}

		return null;
    }
}
