package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description:GeoText的子对象类型，一个GeoText类实例是由一个或多个注记子对象组成的，用于地图上地理要素的说明等。 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 孔令亮
 * @version 2.0
 */
public class TextPart extends InternalHandleDisposable {
    private Point2D m_anchorPoint = null;
    private GeoText m_geoText = null; //用于记录此TextPart的User，以得到此TextPart在User中的索引

    /**
     * 初始化对象实例，Rotation为0.0，Text为String.Empty，Location为Point2D.Empty(即X,Y均为0.0)
     */
    public TextPart() {
        long handle = TextPartNative.jni_New();
        this.setHandle(handle, true);
        reset();
    }

    /**
     * 拷贝构造函
     * @param textPart TextPart
     */
    public TextPart(TextPart part) {
        if (part.getHandle() == 0) {
            String message = InternalResource.loadString("part",
                    InternalResource.GlobalArgumentObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long partHandle = 0;
        if (part.m_geoText != null) {
            int index = part.m_geoText.getTextPartsList().indexOf(part);
            //当索引为-1时，则说明对象已被移出
            if (index == -1) {
                String message = InternalResource.loadString(
                        "TextPart(TextPart part)",
                        InternalResource.GlobalArgumentObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalArgumentException(message);
            }
            partHandle = GeoTextNative.jni_GetSubHandle(part.m_geoText.
                    getHandle(), index);
        } else {
            partHandle = part.getHandle();
        }
        long handle = TextPartNative.jni_Clone(partHandle);
        this.setHandle(handle, true);
        this.setAnchorPoint(part.getAnchorPoint());
    }

    /**
     * 根据指定的参数初始化对象实例
     * @param text String  指定此TextPart实例的文本内容
     * @param anchorPoint Point2D  指定此TextPart实例的锚点
     */
    public TextPart(String text, Point2D anchorPoint) {
        long handle = TextPartNative.jni_New();
        this.setHandle(handle, true);
        reset(anchorPoint, 0, text);
    }

    /**
     * 根据指定的参数初始化对象实例
     * @param text String  指定对象实例的文本内容
     * @param anchorPoint Point2D  指定对象实例的锚点
     * @param rotation Double  指定文本的旋转角度，以度为单位，逆时针为正方向
     */
    public TextPart(String text, Point2D anchorPoint, double rotation) {
        long handle = TextPartNative.jni_New();
        this.setHandle(handle, true);
        reset(anchorPoint, rotation, text);
    }

    /**
     * 根据指定的参数初始化对象实例
     * @param text String  指定对象实例的文本内容
     * @param x Double  指定对象实例锚点的X的值
     * @param y Double  指定对象实例锚点的Y的值
     * @param rotation Double  指定文本的旋转角度，以度为单位，逆时针为正方向
     */
    public TextPart(String text, double x, double y, double rotation) {
        long handle = TextPartNative.jni_New();
        this.setHandle(handle, true);
        Point2D anchorPoint = new Point2D(x, y);
        reset(anchorPoint, rotation, text);
    }

    //内部使用到的构造函数 ，主要用于GeoText 中向m_textParts中添加TextPart
    TextPart(GeoText geoText, int index) {
        if (geoText.getHandle() == 0) {
            String message = InternalResource.loadString("geoText",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = GeoTextNative.jni_GetSubHandle(geoText.getHandle(), index);
        this.setHandle(handle, false);
//        this.setHandle(handle, false);
//        this.setAnchorPoint(pt);
        this.m_geoText = geoText;
    }

    /**
     * 回收此对象
     */
    public void dispose() {
        //只释放自己定义的TextPart
        if (!this.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
            TextPartNative.jni_Delete(getHandle());
            this.setHandle(0);
        }
    }

    /**
     * 返回此TextPart实例旋转的角度。旋转的方向为逆时针，单位为度。
     * @return double
     */
    public double getRotation() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getRotation()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
            //当索引为-1时，则说明对象已被移出
            if (index == -1) {
                String message = InternalResource.loadString("getRotation()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            //注：由于在ＵＧＣ中，GeoText中是用Array存放subText的，这就导致每变动一次Array中的subText，
            //其它的subText的Handle 都要做相应的刷新。为了效率，在此不采用刷新subText。而是采用晚加载，
            //只有当实际的取值和设置值时通过索引index来设置m_geoText中索引出的内容。
            return GeoTextNative.jni_GetSubRotation(m_geoText.getHandle(),
                    index);
        } else {
            return TextPartNative.jni_GetRotation(getHandle());
        }
    }

    /**
     * 设置此TextPart实例旋转的角度。旋转的方向为逆时针，单位为度。
     * @param rotation double
     */
    public void setRotation(double rotation) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setRotation(double rotation)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
            //当索引为-1时，则说明对象已被移出
            if (index == -1) {

                String message = InternalResource.loadString("setRotation()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            GeoTextNative.jni_SetSubRotation(m_geoText.getHandle(), rotation,
                                             index);
        } else {
            TextPartNative.jni_SetRotation(getHandle(), rotation);
        }
    }

    /**
     * 返回此TextPart实例的文本内容。
     * @return String
     */
    public String getText() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getText()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
            //当索引为-1时，则说明对象已被移出
            if (index == -1) {
                String message = InternalResource.loadString("getText()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            return GeoTextNative.jni_GetSubText(m_geoText.getHandle(), index);
        } else {
            return TextPartNative.jni_GetText(getHandle());
        }
    }

    /**
     * 设置此TextPart实例的文本内容。
     * @param text String
     */
    public void setText(String text) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setText(String text)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (text == null) {
            text = "";
        }

        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
            //当索引为-1时，则说明对象已被移出
            if (index == -1) {
                String message = InternalResource.loadString("setText()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            GeoTextNative.jni_SetSubText(m_geoText.getHandle(), text, index);
        } else {
            TextPartNative.jni_SetText(getHandle(), text);
        }
    }

    /**
     * 返回此TextPart实例的锚点
     * @return Point2D
     */
    public Point2D getAnchorPoint() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getAnchorPoint()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
            //当索引为-1时，则说明对象已被移出
            if (index == -1) {
                String message = InternalResource.loadString(
                        "getAnchorPoint()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            double pt[] = new double[2];
            //在WrapJ中对pt赋值
            //注：由于TextPart在UGC中没有设计AnchorPoint的接口。所以此处直接操作的是GeoText
            TextPartNative.jni_GetSubAnchor(m_geoText.getHandle(), pt,
                                            index);
            return new Point2D(pt[0], pt[1]);
        } else {
            return m_anchorPoint;
        }
    }

    /**
     * 设置此TextPart实例的锚点
     * @param anchorPoint Point2D
     */
    public void setAnchorPoint(Point2D anchorPoint) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setAnchorPoint(Point2D anchorPoint)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
            //当索引为-1时，则说明对象已被移出
            if (index == -1) {
                String message = InternalResource.loadString(
                        "getAnchorPoint()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            //注：由于TextPart在UGC中没有设计AnchorPoint的接口。所以此处直接操作的是GeoText
            TextPartNative.jni_SetSubAnchor(m_geoText.getHandle(),
                                            anchorPoint.getX(),
                                            anchorPoint.getY(), index);
        } else {
            this.m_anchorPoint = (Point2D) anchorPoint.clone();
        }
    }

    /**
     * 设置此TextPart实例锚点的横坐标。
     * @return double
     */
    public double getX() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getX()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
            //当索引为-1时，则说明对象已被移出
            if (index == -1) {
                String message = InternalResource.loadString(
                        "getAnchorPoint()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            double pt[] = new double[2];
            //在WrapJ中对pt赋值
            //注：由于TextPart在UGC中没有设计AnchorPoint的接口。所以此处直接操作的是GeoText
            TextPartNative.jni_GetSubAnchor(m_geoText.getHandle(), pt,
                                            index);
            return pt[0];
        } else {
            return m_anchorPoint.getX();
        }

    }

    /**
     * 返回此TextPart实例锚点的纵坐标。
     * @return double
     */
    public double getY() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getY()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
            //当索引为-1时，则说明对象已被移出
            if (index == -1) {
                String message = InternalResource.loadString(
                        "getAnchorPoint()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            double pt[] = new double[2];
            //在WrapJ中对pt赋值
            //注：由于TextPart在UGC中没有设计AnchorPoint的接口。所以此处直接操作的是GeoText
            TextPartNative.jni_GetSubAnchor(m_geoText.getHandle(), pt,
                                            index);
            return pt[1];
        } else {
            return m_anchorPoint.getY();
        }
    }

    void reset() {
        reset(new Point2D(0, 0), 0, "");
    }

    //
    void reset(Point2D anchorPoint, double rotation, String text) {
        //注：由于当为new出来的对象时才会调用Reset，只有setText和setRotation才调用Wrapj，所以先作如下处理。
        this.setAnchorPoint(anchorPoint);
        this.setRotation(rotation);
        this.setText(text);
    }
}

