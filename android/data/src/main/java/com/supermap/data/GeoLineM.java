package com.supermap.data;


import java.util.ArrayList;

/**
 * <p>Title: 路线对象</p>
 *
 * <p>Description: 是一组具有X，Y坐标与线性度量值的点组成的线性地物对象。</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 张继南
 * @version 2.0
 */

public class GeoLineM extends Geometry {

    private ArrayList<PointMs> m_geoLineMParts; //用于存放pointMs

    ArrayList<PointMs> getPartsList() {
        return this.m_geoLineMParts;
    }

    /**
     * 构造函数
     */
    public GeoLineM() {
        long handle = GeoLineMNative.jni_New();
        this.setHandle(handle, true);
        m_geoLineMParts = new ArrayList<PointMs>();
    }
    /**
     * 拷贝构造函数
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
            //对ArrayList进行深度拷贝
            PointMs pointMs = (PointMs) geoLineM.getPartsList().get(i);
            m_geoLineMParts.add(pointMs.clone());
        }
    }

    /**
     * 根据点串创建一个路线对象
     * @param points Point2D[]
     */
    public GeoLineM(PointMs pointMs) {
        this();
        addPart(pointMs);
    }

    //内部使用
    GeoLineM(long handle) {
        this.setHandle(handle, false);
        m_geoLineMParts = new ArrayList<PointMs>();
        this.refrashPartsList();
    }

    /**
     * 返回路线对象是否为空
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
     * 路线对象的克隆
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
     * 回收此对象
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
     * 返回路线对象的长度，单位与数据集的单位相同。
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
     * 返回路线对象的子对象个数。
     * 简单对象的该属性值为1，线对象清空后，该属性值为0。
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
     * 向路线对象追加一个子对象。
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
        //线的子对象的点数应该是两个以上
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
        //同步更新m_GeoLineParts
        PointMs newPointMs = new PointMs(pointMs, this);
        m_geoLineMParts.add(newPointMs);
        return GeoLineMNative.jni_AddPart(getHandle(), xs, ys, ms);
    }

    /**
     * 删除路线对象中的指定index的子对象
     * @param index int  指定的子对象索引号
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
        //判断索引是否越界
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        boolean bResult = GeoLineMNative.jni_RemovePart(getHandle(), index);
        if (bResult == true) {
            //同步更新m_GeoLineParts
            m_geoLineMParts.remove(index);
        }
        return bResult;
    }


    /**
     *  清空几何对象
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

        //清空m_geoLineParts
        this.m_geoLineMParts.clear();
    }

    /**
     * 把路线对象转换为面对象，成功返回面对象。
     * 对于没有封闭的路线对象，转换为面对象时，会把首尾自动连起来。
     * @return GeoRegion   转换成功返回区域对象
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
        //当此GeoLine对象实例的某个子对象的点数少于3时,此时该子对象不能convertToRegion,抛出UnsupportOperationException
        if (this.getPartCount() != region.getPartCount()) {
            String message = InternalResource.loadString("convertToRegion",
                    InternalResource.GeoLienUnsupportOperation,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        return region;
    }

    /**
       * 在路线上以指定的距离找点，从路线的起始点开始查找。
       * 当传入的为负值时，抛出IllegalArgumentException异常。当传入的距离大于路线的总长度，返回路线的末端点
       * @param distance double  要找点的距离
       * @return Point2D   查找成功返回点，否则返回null
       */
      public Point2D findPointOnLineByDistance(double distance) {
          if (this.getHandle() == 0) {
              String message = InternalResource.loadString(
                      "findPointOnLineByDistance(double distance)",
                      InternalResource.HandleObjectHasBeenDisposed,
                      InternalResource.BundleName);
              throw new IllegalStateException(message);
          }

          //传入的距离不可为负值
          if (distance < 0) {
              String message = InternalResource.loadString("distance",
                      InternalResource.GeoLineArgumentShouldNotBeNegative,
                      InternalResource.BundleName);
              throw new IllegalArgumentException(message);
          }
          //基本思路是先new一个Point2D对象，然后其x，y组成一个point数组传入JNI，在JNI中修改
          //x，y的值，然后传出。
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
     * 用于获取线对象中的指定子对象的点串
     * @param index int  子对象的索引
     * @return PointMs  成功返回PointMs对象，失败返回空值。
     */
    public PointMs getPart(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getPart(int index)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        //判断索引是否越界
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        return (PointMs) m_geoLineMParts.get(index);
    }

    /**
     * 往线对象中的指定位置插入一个二维线子对象
     * @param index int  插入的位置
     * @param pt Point2D[]  插入对象的定位点点串
     * @return boolean  成功则返回 True，否则返回 False
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
        //由于当getPartCount == index时，UGC在索引count位置不能插入，故此时转为调用addPart方法
        if (partCount == index) {
            int indexInsert = addPart(pointMs);
            bResult = (indexInsert == index);
            if (bResult == true) {
                //同步更新m_GeoLineParts
                PointMs newPointMs = new PointMs(pointMs, this);
                m_geoLineMParts.add(index, newPointMs);
            }
            return bResult;
        }

        //判断索引是否越界,可以在partCount的位置插入
        //判断索引是否越界
        if (index < 0 || index > getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

        int length = pointMs.getCount();
        //线的子对象的点数应该是两个以上
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
            //同步更新m_GeoLineParts
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
     * 把路线对象的点串序列进行颠倒
     * @return boolean  成功则返回 True，否则返回 False
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
     * 把路线对象的M值进行颠倒
     * @return boolean  成功则返回 True，否则返回 False
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
     * 在指定的位置上修改路线子对象
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
        //判断索引是否越界
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        int length = pointMs.getCount();
        //线的子对象的点数应该是两个以上
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
            //同步更新m_GeoLineParts
        	PointMs oldPointMs = m_geoLineMParts.get(index);
            PointMs newPointMs = new PointMs(pointMs, this);
            m_geoLineMParts.set(index, newPointMs);
            oldPointMs.setOwner(null);
        }
        return bResult;
    }
    /**内部方法，在只更新底层PointMs时使用
     * 当PointMs是通过连点获得时，同时更新组件对象，将会破坏连点性
     * 在指定的位置上修改路线子对象
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
        //判断索引是否越界
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        int length = pointMs.getCount();
        //线的子对象的点数应该是两个以上
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
     * 从XML加载几何对象
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
//     * 获取对象的镜像
//     * 不修改对象自身
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
//        //由于mirror后，得到的是一条新线，构造线的m_geoLineParts。
//        int count = geoLineM.getPartCount();
//        geoLineM.m_geoLineMParts = new ArrayList<PointMs>();
//        for (int i = 0; i < count; i++) {
//            //向m_geoLineParts等中添加新增的内容。
//            PointMs pointMs = new PointMs(geoLineM.getPartFromUGC(i));
//            PointMs newPointMs = new PointMs(pointMs, geoLineM);
//            geoLineM.m_geoLineMParts.add(newPointMs);
//        }
//        return geoLineM;
//    }

    /**
     * 偏移
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
     * 改变几何对象的大小
     * 当宽度为负数时对象水平翻转，当高度为负数时，对象垂直翻转
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
     * 旋转几何对象
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
     * 获取最大M值
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
     * 获取最小M值
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

    //add by xuzw 2009-02-10 6.0新增
    /**
     * 转换为二维线对象
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
//     * 计算到指定路线子对象首结点一定距离的节点处的线性度量值
//     * @param distance double  距指定路线子对象首结点的距离
//     * @param subObjId int 要计算的子对象的ID号。缺省为0，即计算第一个子对象
//     * @return double 返回到指定路线子对象首结点一定距离的节点处的线性度量值
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
        //同步更新m_geoLineParts
        if (m_geoLineMParts != null) {
            m_geoLineMParts.clear();
            m_geoLineMParts = null;
        }
    }
    protected static void clearHandle(GeoLineM lineM) {
    	lineM.clearHandle();
    }


    //当GeoLineM的UGC底层改变时，更新m_geoLineMParts。改变m_geoLineMParts中的值，不改变引用
    private void refreshFromUGC() {
        //更新m_geoLineParts
        int partCount = this.getPartCount();
        for (int i = 0; i < partCount; i++) {
            PointMs ptMs = (PointMs) m_geoLineMParts.get(i);
            //先将pts的UserType 设为None，0即纯Point2Ds
            //pts.setUserType(UserType.NONE);
            ptMs.setOwner(null);
            ptMs.clear();
            ptMs.addRange(this.getPartFromUGC(i));
            //添加完值后，再将pts的UserType 设为geoLine
            //pts.setUserType(UserType.GEOLINE);
        }
    }


    //用于取得底层真正的Part
    private PointM[] getPartFromUGC(int index) {
        //判断索引是否越界
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

    //当执行XML操作后，或者直接得到handle时，构造m_geoLineMParts。改变m_geoLineMParts中的值，不改变引用
    private void refrashPartsList() {
        //处理m_geoLineParts的相关内容。
        int count = getPartCount();
        m_geoLineMParts.clear();
        for (int i = 0; i < count; i++) {
            //向m_geoLineParts等中添加新增的内容。
            PointMs pointMs = new PointMs(this.getPartFromUGC(i));
            PointMs newPointMs = new PointMs(pointMs, this);
            m_geoLineMParts.add(newPointMs);
        }
    }
    /**
     * 通过里程（刻度、度量值）返回点
     * @author Shanqc
     * @date 2011-01-06
     * @param measure 里程
     * @return 成功返回Point2D对象，失败返回空(忽略子对象之间的距离)
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
     * 通过里程（刻度、度量值）返回点
     * @author Shanqc
     * @date 2010-11-30
     * @param measure 里程
     * @param offset 偏差
     * @param isIgnoreGap 是否忽略子对象之间的距离
     * @return 成功返回Point2D对象，失败返回空
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
     * 通过距离返回点
     * @author Shanqc
     * @date 2010-11-30
     * @param distance 距离
     * @param isIgnoreGap 是否忽略子对象之间的距离
     * @return  成功返回Point2D对象，失败返回空
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
     * 通过距离返回M值
     * @author Shanqc
     * @date 2011-01-06
     * @param distance 距离
     * @return  成功返回M值，失败返回-9999
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
     * 通过距离返回M值
     * @author Shanqc
     * @date 2011-01-06
     * @param distance 距离
     * @param isIgnoreGap 是否忽略子对象之间的距离
     * @return  成功返回M值，失败返-9999
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
     * 通过距离返回M值
     * @author Shanqc
     * @date 2011-01-06
     * @param distance 距离
     * @param subindex 子对象序号
     * @param isIgnoreGap 是否忽略子对象之间的距离
     * @return  成功返回M值，失败返-9999
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
     * 通过M值返回距离
     * @author Shanqc
     * @date 2011-01-06
     * @param measure M值
     * @param isIgnoreGap 是否忽略子对象之间的距离
     * @return  成功返回距离值，失败返回-9999
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
     * 通过M值返回距离
     * @author Shanqc
     * @date 2011-01-06
     * @param measure M值
     * @return  成功返回距离值，失败返回-9999
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
     * 通过M值返回距离
     * @author Shanqc
     * @date 2011-01-06
     * @param distance 距离
     * @param subindex 子对象序号
     * @param isIgnoreGap 是否忽略子对象之间的距离
     * @return  成功返回M值，失败返-9999
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
     * 设定指定点处的M值
     * @author Shanqc
     * @date 2011-01-06
     * @param point2D 指定点
     * @param measure 指定里程
     * @return  成功返回true，失败返false
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
     * 设定指定点处的M值
     * @author Shanqc
     * @date 2011-01-06
     * @param point2D 指定点
     * @param measure 指定M值
     * @param tolorence 容限
     * @param wheretocalibrate 是否校正，并可设定校正位置
     * @return  成功返回true，失败返false
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
     * 返回指定点处的M值
     * @author Shanqc
     * @date 2011-01-16
     * @param point2D 指定点
     * @param measure 指定M值
     * @param tolorence 容限
     * @param isIgnoreGap 是否忽略子对象的间距
     * @return  成功返回true，失败返false
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
     * 获取fromMeasure与toMeasure之间的线对象
     * @author Shanqc
     * @date 2011-01-25
     * @param toMeasure	起始Measure值
     * @param toMeasure	终止Measure值
     * @return 线对象
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
     * 连接两条路由对象，将geoLineM的末点与otherLineM的起点连接起来。
     * @author Shanqc
     * @date 2011-01-07
     * @param geoLineM 要合并的第一条线路
     * @param otherLineM 要合并的另一条线路
     * @return  成功返回新的GeoLineM，失败返回为空。
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
    	//有两种方法得到子对象的个数：组件方法和底层方法，这里使用组件方法更快一些
    	//int partcount=otherLineM.getPartCount();
    	GeoLineM resultlinemGeoLineM= new GeoLineM(geoLineM);
    	int partcount=otherLineM.getPartsList().size();
        for (int i = 0; i < partcount; i++) {
           resultlinemGeoLineM.addPart(otherLineM.getPart(i));
        }
       
    	return resultlinemGeoLineM;
    }
    /**
     * 连接两条路由对象，使当前路由对象的尾节点与另一路由对象的首节点相连。
     * @author Shanqc
     * @date 2010-01-07
     * @param otherLineM 加一条线路
     * @return  成功返回true，失败返false
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
    	//有两种方法得到子对象的个数：组件方法和底层方法，这里使用组件方法更快一些
    	//int partcount=otherLineM.getPartCount();
    	int otherpartcount=otherLineM.getPartsList().size();
    	int origincount=getPartCount();
    	PointMs pointMsoriginMs= (PointMs)getPartsList().get(origincount-1);
    	PointMs pointMsIn = (PointMs) otherLineM.getPartsList().get(0);
    	pointMsoriginMs.addRange(pointMsIn.toArray());
        for (int i = 1; i < otherpartcount; i++) {
            //对ArrayList进行深度拷贝
           PointMs pointMs = (PointMs) otherLineM.getPartsList().get(i);
            m_geoLineMParts.add(pointMs.clone());
        }

    	return true;
    }
    /**
     * 通过点分割线路，分成两部分
     * @author Shanqc
     * @date 2011-01-16
     * @param splitPoint 分割点
     * @param geoLineM1  分割后的对象1
     * @param geoLineM2  分割后的对象1
     * @return 成功返回true，失败返回false
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
     * 通过点串校正线路
     * @author Shanqc
     * @date 2011-01-25
     * @param pointMs	 点串
     * @param method 校正方法
     * @param isIgnoreGap 是否忽略子对象的间距
     * @return 成功返回true，失败返回false
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
          //线的子对象的点数应该是两个以上
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
              //同步更新m_GeoLineParts
        	  m_geoLineMParts.clear();
             refrashPartsList();
          }
          return bResult;

    }
    /**
     * 通过线段校正线路
     * @author Shanqc
     * @date 2011-01-25
     * @param geoLineM		 线段
     * @param method 校正方法
     * @param isIgnoreGap 是否忽略子对象的间距
     * @return 成功返回true，失败返回false
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
        //线的子对象的点数应该是两个以上
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
              //同步更新m_GeoLineParts
        	  m_geoLineMParts.clear();
             refrashPartsList();
          }
          
          return bResult;
    }
    /**
     * 裁剪线路
     * @author Shanqc
     * @date 2011-01-25
     * @param geometry	 	被裁剪线路
     * @param clipGeometry  裁剪对象，必须是面
     * @return 成功返回GeoLineM对象，失败返回空
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
		// 检查异常，查询对象，支持线和面类型
		if (geometry.getType()!= GeometryType.GEOLINEM) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentTypeInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 检查异常，目标对象，只支持面类型
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
     * 更新线路M值
     * @author Shanqc
     * @date 2011-01-25
     * @param fromPoint	 	起始点
     * @param toPoint		终止点
     * @param fromMeasure	起始点M值
     * @param toMeasure		终止点M值
     * @param tolerance  	容限
     * @param wheretoCali	校正位置
     * @param method		校正方法
     * @return 成功返回true，失败返false
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
     * 更新线路M值
     * @author Shanqc
     * @date 2011-12-31
     * @param fromIndex	 	起始点索引
     * @param toIndex		终止点索引
     * @param fromMeasure	起始点M值
     * @param toMeasure		终止点M值
     * @param tolerance  	容限
     * @param wheretoCali	校正位置
     * @param method		校正方法
     * @return 成功返回true，失败返false
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
     * 内推M值
     * @author Shanqc
     * @date 2011-01-25
     * @param fromPoint	 	起始点
     * @param toPoint		终止点
     * @param fromMeasure	起始点M值
     * @param toMeasure		终止点M值
     * @param tolerance  	容限
     * @param method		校正方法
     * @return 成功返回true，失败返false
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
     * 外推M值，计算起始点之前和终止点之后线路上的M值
     * @author Shanqc
     * @date 2011-01-25
     * @param fromPoint	 	起始点
     * @param toPoint		终止点
     * @param fromMeasure	起始点M值
     * @param toMeasure		终止点M值
     * @param tolerance  	容限
     * @param method		校正方法
     * @return 成功返回true，失败返false
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
     * 外推无值处的M值
     * @author Shanqc
     * @date 2011-01-25
     * @param isIgnoreGap	是否忽略子对象之间的间距
     * @return 无返回值
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
     * 点线联合生成线路
     * @author Shanqc
     * @date 2011-01-25
     * @param geoline	 	线
     * @param pointMs		点,至少有两个点
     * @return 成功返回GeoLineM对象，失败返回空
     */
    public static GeoLineM makeLineM(GeoLine geoLine, PointMs pointMs) {
    	if (geoLine == null || geoLine.getHandle() == 0) {
			String message = InternalResource.loadString("geoline",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
    	  int length = pointMs.getCount();
          //线的子对象的点数应该是两个以上
          if (length < 2) {
              String message = InternalResource.loadString("pointMs",
                      InternalResource.GeoLineInvalidPointsLength,
                      InternalResource.BundleName);
              throw new IllegalArgumentException(message);
          }
		// 检查异常，只支持线
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
