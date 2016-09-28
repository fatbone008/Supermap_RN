package com.supermap.data;

/**
 * <p>Title:��ά������ </p>
 *
 * <p>Description:�洢һ�����������ĸ�����ʾһ�����ε�λ�úʹ�С���������ƽ�����������Ļ����ϵͳ </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author ���ƽ�
 * @version 2.0
 */
public class Rectangle2D {
    private double left;
    private double right;
    private double top;
    private double bottom;

    public Rectangle2D() {
        left = Toolkit.DBL_MIN_VALUE;
        right = Toolkit.DBL_MIN_VALUE;
        top = Toolkit.DBL_MIN_VALUE;
        bottom = Toolkit.DBL_MIN_VALUE;
    }

    /**
     * �� rect ��ʼ�� Rectangle2D�ṹ����ʵ��
     * @param rect Rectangle2D
     */
    public Rectangle2D(Rectangle2D rect) {
        this(rect.getLeft(), rect.getBottom(), rect.getRight(), rect.getTop());
    }

    /**
     * ��ָ�������½����ꡢ�����߶ȳ�ʼ�����ε���ʵ��
     * @param topLeft Point2D
     * @param width double
     * @param height double
     */
    public Rectangle2D(Point2D leftBottom, double width, double height) {
        this.left = leftBottom.getX();
        this.bottom = leftBottom.getY();
        this.right = this.left + width;
        this.top = this.bottom + height;
    }

    /**
     *
     * @param left double
     * @param bottom double
     * @param right double
     * @param top double
     */
    public Rectangle2D(Point2D leftBottom, Point2D rightTop) {
        this.left = leftBottom.getX();
        this.bottom = leftBottom.getY();
        this.right = rightTop.getX();
        this.top = rightTop.getY();
    }

    /**
     *  ���캯���������Ľ�����
     * @param left double
     * @param right double
     * @param bottom double
     * @param top double
     */
    public Rectangle2D(double left, double bottom, double right, double top) {
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.top = top;
    }
    public Rectangle2D(Point2D center,Size2D size){
    	if(size.getWidth()<0||size.getHeight()<0){
    		String message = InternalResource.loadString("size",
					InternalResource.Rectangle2DWidthAndHeightShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
    	}
    	double left = center.getX()-size.getWidth()/2;
    	double bottom = center.getY()-size.getHeight()/2;
    	double right = center.getX()+size.getWidth()/2;
    	double top =  center.getY()+size.getHeight()/2;
    	this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.top = top;
    }

    public final static Rectangle2D getEMPTY() {
        return new Rectangle2D(Toolkit.DBL_MIN_VALUE, Toolkit.DBL_MIN_VALUE,
                               Toolkit.DBL_MIN_VALUE, Toolkit.DBL_MIN_VALUE);
    }

    /**
     * �����Ƿ�Ϊ��
     * @return boolean
     */
    public boolean isEmpty() {
        if (Toolkit.isZero(left - Toolkit.DBL_MIN_VALUE,
                           Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
                           Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION) &&
            Toolkit.isZero(top - Toolkit.DBL_MIN_VALUE,
                           Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
                           Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION) &&
            Toolkit.isZero(right - Toolkit.DBL_MIN_VALUE,
                           Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
                           Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION) &&
            Toolkit.isZero(bottom - Toolkit.DBL_MIN_VALUE,
                           Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
                           Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ���ؾ��ε����ĵ�
     * @return Point2D
     */
    public Point2D getCenter() {
        Point2D pt = new Point2D();
        pt.setX((left + right) / 2);
        pt.setY((top + bottom) / 2);
        return pt;
    }

    /**
     * ��ȡ�� Rectangle �ṹ�ϱ�Ե�� y ����
     * @return double
     */
    public double getTop() {
        return top;
    }

    public void setTop(double value) {
        top = value;
    }

    /**
     * ��ȡ y ���꣬�������Ǵ� Rectangle �ṹ�� Y �� Height ����ֵ֮��
     * @return double
     */
    public double getBottom() {
        return bottom;
    }

    public void setBottom(double value) {
        bottom = value;
    }

    /**
     * ��ȡ�� Rectangle �ṹ���Ե�� x ����
     * @return double
     */
    public double getLeft() {
        return left;
    }

    /**
     * ���ô� Rectangle �ṹ���Ե�� x ����
     * @param value double
     */
    public void setLeft(double value) {
        left = value;
    }

    /**
     * ��ȡ x ���꣬�������Ǵ� Rectangle �ṹ�� X �� Width ����ֵ֮��
     * @return double
     */
    public double getRight() {
        return right;
    }

    /**
     *
     * @param value double
     */
    public void setRight(double value) {
        right = value;
    }

    /**
     * ��ȡ�� Rectangle �ṹ�Ŀ��
     * @return double
     */
    public double getWidth() {
        return Math.abs(right - left);
    }


    /**
     * ��ȡ�� Rectangle �ṹ�ĸ߶�
     * @return double
     */
    public double getHeight() {
        return Math.abs(top - bottom);
    }


    /**
     * ��¡����
     * @return Rectangle2D
     */
    public Rectangle2D clone() {
        return new Rectangle2D(this);
    }

    /**
     * ͨ���� rect ��X,Y,Width,Height���뵽����ӽ��Ľϴ�����ֵ����������ֵ����һ���µ�Rectagnle2D����
     * @param rect Rectangle2D
     * @return Rectangle2D
     */
    public static Rectangle2D ceiling(Rectangle2D rect) {
        Rectangle2D r = new Rectangle2D(Math.ceil(rect.getLeft()),
                                        Math.ceil(rect.getBottom()),
                                        Math.ceil(rect.getRight()),
                                        Math.ceil(rect.getTop()));

        return r;
    }

    /**
     * ͨ���� rect ��X,Y,Width,Heightֵ�ض�ΪС�ڻ��ߵ��������������ֵ����������ֵ����һ���µ�Rectangle2D����
     * @param rect Rectangle2D
     * @return Rectangle2D
     */
    public static Rectangle2D floor(Rectangle2D rect) {
        Rectangle2D r = new Rectangle2D(Math.floor(rect.getLeft()),
                                        Math.floor(rect.getBottom()),
                                        Math.floor(rect.getRight()),
                                        Math.floor(rect.getTop()));

        return r;
    }

    /**
     * ͨ���� rect ��X,Y,Width,Height���뵽��ӽ�������ֵ����������ֵ����һ���µ�Rectangle2D����
     * @param rect Rectangle2D
     * @return Rectangle2D
     */
    public static Rectangle2D round(Rectangle2D rect) {
        Rectangle2D r = new Rectangle2D(Math.round(rect.getLeft()),
                                        Math.round(rect.getBottom()),
                                        Math.round(rect.getRight()),
                                        Math.round(rect.getTop()));

        return r;
    }

    /**
     * �ж��Ƿ������
     * @param pt Point2D
     * @return boolean
     */
    public boolean contains(Point2D pt) {
        return (pt.getX() >= left) && (pt.getX() <= right) && (pt.getY() <= top) &&
                (pt.getY() >= bottom);
    }

    /**
     * �ж��Ƿ��������
     * @param rect Rectangle2D
     * @return boolean
     */
    public boolean contains(Rectangle2D rect) {
        return (rect.left > left || Toolkit.isZero(rect.left - left)) &&
                (rect.top < top || Toolkit.isZero(rect.top - top)) &&
                (rect.right < right || Toolkit.isZero(rect.right - right)) &&
                (rect.bottom > bottom || Toolkit.isZero(rect.bottom - bottom));
    }

    /**
     * �ж��Ƿ�����㣬����X��Y�����ʾ
     * @param x double
     * @param y double
     * @return boolean
     */
    public boolean contains(double x, double y) {
        Point2D point = new Point2D(x, y);
        return contains(point);
    }


    /**
     * ��д�����equals����
     * @param obj Object
     * @return boolean
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Rectangle2D)) {
            return false;
        }

        Rectangle2D rect = (Rectangle2D) obj;
        if (Toolkit.isZero(left - rect.getLeft()) &&
            Toolkit.isZero(right - rect.getRight()) &&
            Toolkit.isZero(top - rect.getTop()) &&
            Toolkit.isZero(bottom - rect.getBottom())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equals(Rectangle2D rect) {
        if (rect == null) {
            return false;
        }
        if (Toolkit.isZero(left - rect.getLeft()) &&
            Toolkit.isZero(right - rect.getRight()) &&
            Toolkit.isZero(top - rect.getTop()) &&
            Toolkit.isZero(bottom - rect.getBottom())) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * ��Ϊ��д�˻����equals���������Ҫ��дhashcode��������key-value��contains����
     * �ᷢ������
     * @return int
     */
    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(getLeft());
        bits += java.lang.Double.doubleToLongBits(getBottom()) * 37;
        bits += java.lang.Double.doubleToLongBits(getRight()) * 43;
        bits += java.lang.Double.doubleToLongBits(getTop()) * 47;
        return (((int) bits) ^ ((int) (bits >> 32)));

    }

    /**
     * ����
     * @param x double
     * @param y double
     */
    public void inflate(double x, double y) {
        left -= x;
        right += x;
        top += y;
        bottom -= y;
    }

    /**
     * ȡ�ཻ����
     * ��ı䵱ǰ����
     * @param rect Rectangle2D
     */
    public void intersect(Rectangle2D rect) {
        if (hasIntersection(rect)) {
            left = Math.max(left, rect.left);
            top = Math.min(top, rect.top);
            right = Math.min(right, rect.right);
            bottom = Math.max(bottom, rect.bottom);

        } else {
            setEmpty();
        }
    }

    /**
     * �ж��Ƿ���Ŀ������ཻ
     * @param rect Rectangle2D
     * @return boolean
     * modified:IntersectsWith����ΪIsIntersect by liyj 07.5.17
     * modified:IsIntersect����ΪhasIntersection by liyj 07.5.17
     */
    public boolean hasIntersection(Rectangle2D rect) {
        return right >= rect.left && left <= rect.right && top >= rect.bottom &&
                bottom <= rect.top;
    }

    /**
     * ƽ�ƾ���
     * @param dx double
     * @param dy double
     */
    public void offset(double dx, double dy) {
        left += dx;
        right += dx;
        top += dy;
        bottom += dy;
    }

    /**
     * ��ȡ���ε��ַ�����ʾ
     * @return String
     */
    public String toString() {
        return "Left=" + left + ",Bottom=" + bottom + ",Right=" + right +
                ",Top=" + top;
    }

    /**
     * �ϲ�����
     * @param rect Rectangle2D
     */
    public void union(Rectangle2D rect) {
        left = Math.min(left, rect.getLeft());
        top = Math.max(top, rect.getTop());
        right = Math.max(right, rect.getRight());
        bottom = Math.min(bottom, rect.getBottom());
    }

    private void setEmpty() {
        left = Toolkit.DBL_MIN_VALUE;
        right = Toolkit.DBL_MIN_VALUE;
        top = Toolkit.DBL_MIN_VALUE;
        bottom = Toolkit.DBL_MIN_VALUE;
    }
    
    
    public String toJson(){
    	StringBuilder sb = new StringBuilder();
    	sb.append("{");
    	
    	sb.append(" \"leftBottom\" :{" + 
    			" \"x\" : " + this.left +"," +
    			" \"y\" : " + this.bottom + "}," );
    	
    	sb.append(" \"rightTop\" : { "+
    			" \"x\" : " + this.right +"," +
    			" \"y\" : " + this.top + "}" );
    	
    	sb.append("}");
    	return sb.toString();
    }
    
}

//    /**
//     * �ϲ�����
//     * @param a Rectangle2D
//     * @param b Rectangle2D
//     * @return Rectangle2D
//     */
//    public static Rectangle2D union(Rectangle2D a, Rectangle2D b) {
//        Rectangle2D rect = new Rectangle2D(a);
//        rect.union(b);
//        return rect;
//    }
//    /**
//     * �����ཻ����
//     * ���ı����о���
//     * @param a Rectangle2D
//     * @param b Rectangle2D
//     * @return Rectangle2D
//     */
//    public static Rectangle2D intersect(Rectangle2D a, Rectangle2D b) {
//        Rectangle2D rect = new Rectangle2D(a);
//        rect.intersect(b);
//        return rect;
//    }
//    /**
//     * ����
//     * @param rect Rectangle2D
//     * @param x double
//     * @param y double
//     * @return Rectangle2D
//     */
//    public static Rectangle2D inflate(Rectangle2D rect, double x, double y) {
//        Rectangle2D r = new Rectangle2D(rect);
//        r.inflate(x, y);
//        return r;
//    }
