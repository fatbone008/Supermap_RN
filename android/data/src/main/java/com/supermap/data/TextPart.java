package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description:GeoText���Ӷ������ͣ�һ��GeoText��ʵ������һ������ע���Ӷ�����ɵģ����ڵ�ͼ�ϵ���Ҫ�ص�˵���ȡ� </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ������
 * @version 2.0
 */
public class TextPart extends InternalHandleDisposable {
    private Point2D m_anchorPoint = null;
    private GeoText m_geoText = null; //���ڼ�¼��TextPart��User���Եõ���TextPart��User�е�����

    /**
     * ��ʼ������ʵ����RotationΪ0.0��TextΪString.Empty��LocationΪPoint2D.Empty(��X,Y��Ϊ0.0)
     */
    public TextPart() {
        long handle = TextPartNative.jni_New();
        this.setHandle(handle, true);
        reset();
    }

    /**
     * �������캯
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
            //������Ϊ-1ʱ����˵�������ѱ��Ƴ�
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
     * ����ָ���Ĳ�����ʼ������ʵ��
     * @param text String  ָ����TextPartʵ�����ı�����
     * @param anchorPoint Point2D  ָ����TextPartʵ����ê��
     */
    public TextPart(String text, Point2D anchorPoint) {
        long handle = TextPartNative.jni_New();
        this.setHandle(handle, true);
        reset(anchorPoint, 0, text);
    }

    /**
     * ����ָ���Ĳ�����ʼ������ʵ��
     * @param text String  ָ������ʵ�����ı�����
     * @param anchorPoint Point2D  ָ������ʵ����ê��
     * @param rotation Double  ָ���ı�����ת�Ƕȣ��Զ�Ϊ��λ����ʱ��Ϊ������
     */
    public TextPart(String text, Point2D anchorPoint, double rotation) {
        long handle = TextPartNative.jni_New();
        this.setHandle(handle, true);
        reset(anchorPoint, rotation, text);
    }

    /**
     * ����ָ���Ĳ�����ʼ������ʵ��
     * @param text String  ָ������ʵ�����ı�����
     * @param x Double  ָ������ʵ��ê���X��ֵ
     * @param y Double  ָ������ʵ��ê���Y��ֵ
     * @param rotation Double  ָ���ı�����ת�Ƕȣ��Զ�Ϊ��λ����ʱ��Ϊ������
     */
    public TextPart(String text, double x, double y, double rotation) {
        long handle = TextPartNative.jni_New();
        this.setHandle(handle, true);
        Point2D anchorPoint = new Point2D(x, y);
        reset(anchorPoint, rotation, text);
    }

    //�ڲ�ʹ�õ��Ĺ��캯�� ����Ҫ����GeoText ����m_textParts�����TextPart
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
     * ���մ˶���
     */
    public void dispose() {
        //ֻ�ͷ��Լ������TextPart
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
     * ���ش�TextPartʵ����ת�ĽǶȡ���ת�ķ���Ϊ��ʱ�룬��λΪ�ȡ�
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
            //������Ϊ-1ʱ����˵�������ѱ��Ƴ�
            if (index == -1) {
                String message = InternalResource.loadString("getRotation()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            //ע�������ڣգǣ��У�GeoText������Array���subText�ģ���͵���ÿ�䶯һ��Array�е�subText��
            //������subText��Handle ��Ҫ����Ӧ��ˢ�¡�Ϊ��Ч�ʣ��ڴ˲�����ˢ��subText�����ǲ�������أ�
            //ֻ�е�ʵ�ʵ�ȡֵ������ֵʱͨ������index������m_geoText�������������ݡ�
            return GeoTextNative.jni_GetSubRotation(m_geoText.getHandle(),
                    index);
        } else {
            return TextPartNative.jni_GetRotation(getHandle());
        }
    }

    /**
     * ���ô�TextPartʵ����ת�ĽǶȡ���ת�ķ���Ϊ��ʱ�룬��λΪ�ȡ�
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
            //������Ϊ-1ʱ����˵�������ѱ��Ƴ�
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
     * ���ش�TextPartʵ�����ı����ݡ�
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
            //������Ϊ-1ʱ����˵�������ѱ��Ƴ�
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
     * ���ô�TextPartʵ�����ı����ݡ�
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
            //������Ϊ-1ʱ����˵�������ѱ��Ƴ�
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
     * ���ش�TextPartʵ����ê��
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
            //������Ϊ-1ʱ����˵�������ѱ��Ƴ�
            if (index == -1) {
                String message = InternalResource.loadString(
                        "getAnchorPoint()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            double pt[] = new double[2];
            //��WrapJ�ж�pt��ֵ
            //ע������TextPart��UGC��û�����AnchorPoint�Ľӿڡ����Դ˴�ֱ�Ӳ�������GeoText
            TextPartNative.jni_GetSubAnchor(m_geoText.getHandle(), pt,
                                            index);
            return new Point2D(pt[0], pt[1]);
        } else {
            return m_anchorPoint;
        }
    }

    /**
     * ���ô�TextPartʵ����ê��
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
            //������Ϊ-1ʱ����˵�������ѱ��Ƴ�
            if (index == -1) {
                String message = InternalResource.loadString(
                        "getAnchorPoint()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            //ע������TextPart��UGC��û�����AnchorPoint�Ľӿڡ����Դ˴�ֱ�Ӳ�������GeoText
            TextPartNative.jni_SetSubAnchor(m_geoText.getHandle(),
                                            anchorPoint.getX(),
                                            anchorPoint.getY(), index);
        } else {
            this.m_anchorPoint = (Point2D) anchorPoint.clone();
        }
    }

    /**
     * ���ô�TextPartʵ��ê��ĺ����ꡣ
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
            //������Ϊ-1ʱ����˵�������ѱ��Ƴ�
            if (index == -1) {
                String message = InternalResource.loadString(
                        "getAnchorPoint()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            double pt[] = new double[2];
            //��WrapJ�ж�pt��ֵ
            //ע������TextPart��UGC��û�����AnchorPoint�Ľӿڡ����Դ˴�ֱ�Ӳ�������GeoText
            TextPartNative.jni_GetSubAnchor(m_geoText.getHandle(), pt,
                                            index);
            return pt[0];
        } else {
            return m_anchorPoint.getX();
        }

    }

    /**
     * ���ش�TextPartʵ��ê��������ꡣ
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
            //������Ϊ-1ʱ����˵�������ѱ��Ƴ�
            if (index == -1) {
                String message = InternalResource.loadString(
                        "getAnchorPoint()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            double pt[] = new double[2];
            //��WrapJ�ж�pt��ֵ
            //ע������TextPart��UGC��û�����AnchorPoint�Ľӿڡ����Դ˴�ֱ�Ӳ�������GeoText
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
        //ע�����ڵ�Ϊnew�����Ķ���ʱ�Ż����Reset��ֻ��setText��setRotation�ŵ���Wrapj�������������´���
        this.setAnchorPoint(anchorPoint);
        this.setRotation(rotation);
        this.setText(text);
    }
}

