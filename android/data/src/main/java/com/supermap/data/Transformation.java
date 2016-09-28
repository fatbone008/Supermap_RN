package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: 配准</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 卢浩
 * @version 6.0
 */
public class Transformation extends InternalHandleDisposable {
	
	private Point2Ds m_originalControlPoints = null; //源控制点
	private Point2Ds m_targetControlPoints = null;   //目标控制点
	private TransformationMode m_TransformMode = TransformationMode.RECT; //配准模式
	
	/**
	 * 默认构造函数
	 * 
	 * @return 
	 */
	public Transformation() {
        long handle = TransformationNative.jni_New();
        this.setHandle(handle, true);
        m_originalControlPoints = new Point2Ds();
        m_targetControlPoints = new Point2Ds();
	}

	/**
	 * 拷贝构造函数
	 * 
	 * @return 
	 */
	public Transformation(Transformation transformation) {
        if (transformation == null) {
            String message = InternalResource.loadString("transformation",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (transformation.getHandle() == 0) {
            String message = InternalResource.loadString("transformation",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        
        long handle = TransformationNative.jni_New();
        this.setHandle(handle, true);
        m_originalControlPoints = new Point2Ds();
        m_targetControlPoints = new Point2Ds();
	}

	/**
	 * 构造函数
	 * 
	 * @return 
	 */
	public Transformation(Point2Ds originalControlPoints, Point2Ds targetControlPoints, TransformationMode transformMode) {
        if (originalControlPoints == null) {
            String message = InternalResource.loadString("originalControlPoints",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (targetControlPoints == null) {
            String message = InternalResource.loadString("targetControlPoints",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (transformMode == null) {
            String message = InternalResource.loadString("transformationMode",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        
        long handle = TransformationNative.jni_New();
        this.setHandle(handle, true);
        m_originalControlPoints = new Point2Ds();
        m_targetControlPoints = new Point2Ds();
        
        this.setOriginalControlPoints(originalControlPoints);
        this.setTargetControlPoints(targetControlPoints);
        m_TransformMode = transformMode;
	}
	
	/**
	 * 栅格（影像）数据集配准，直接在原数据集上进行，不进行重采样
	 * 
	 * @return 配准是否成功
	 */
	
	/**
	 * 返回配准源控制点
	 * 
	 * @return 返回配准源控制点
	 */
    public Point2Ds getOriginalControlPoints() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getOriginalControlPoints()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        return this.m_originalControlPoints;
    }

     
    public boolean transform(Point2Ds transformPoint2Ds){
        if (transformPoint2Ds == null){
            String message = InternalResource.loadString("transformPoint2Ds",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        
        checkControlPoints();
        
        int count = transformPoint2Ds.getCount();
        double[] xs = new double[count];
        double[] ys = new double[count];
        
        for(int i=0; i<count; i++){
        	xs[i] = transformPoint2Ds.getItem(i).getX();
        	ys[i] = transformPoint2Ds.getItem(i).getY();
        }
        
        boolean result =TransformationNative.jni_TransformPoint2Ds(getHandle(), xs, ys, m_TransformMode.value());
        
        if(result){
        	transformPoint2Ds.clear();
            for(int i=0; i<count; i++){
            	Point2D pnt = new Point2D(xs[i],ys[i]);
            	transformPoint2Ds.add(pnt);
            }
        }
         
        return result;
    }

	/**
	 * 设置配准目标控制点
	 * 
	 * @return 返回设置配准目标控制点是否成功
	 */
    public boolean setOriginalControlPoints(Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("setOriginalControlPoints(Point2Ds points)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if(points==null){
            String message = InternalResource.loadString("points",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        
        int length = points.getCount();
        
        //至少应有一个控制点
        if (length < 1) {
            String message = InternalResource.loadString("Point2Ds",
                    InternalResource.TransformationControlPointsShouldNotSmallerThanOne,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        
        double[] xs = new double[length];
        double[] ys = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        boolean bResult = TransformationNative.jni_SetOriginalControlPoints(getHandle(), xs, ys);
        if (bResult == true) {
            //同步更新m_originalControlPoints
            m_originalControlPoints.clear();
            m_originalControlPoints.addRange(points.toArray());
        }
        return bResult;      
    }

	/**
	 * 返回配准目标控制点
	 * 
	 * @return 返回配准目标控制点
	 */
    public Point2Ds getTargetControlPoints() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getTargetControlPoints()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        return this.m_targetControlPoints;
    }
    
	/**
	 * 设置配准源控制点
	 * 
	 * @return 返回设置配准源控制点是否成功
	 */
    public boolean setTargetControlPoints(Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("setTargetControlPoints(Point2Ds points)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if(points==null){
            String message = InternalResource.loadString("points",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        
        int length = points.getCount();
        
        //至少应有一个控制点
        if (length < 1) {
            String message = InternalResource.loadString("Point2Ds",
                    InternalResource.TransformationControlPointsShouldNotSmallerThanOne,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        
        double[] xs = new double[length];
        double[] ys = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        boolean bResult = TransformationNative.jni_SetTargetControlPoints(getHandle(), xs, ys);
        if (bResult == true) {
            //同步更新m_targetControlPoints
            m_targetControlPoints.clear();
            m_targetControlPoints.addRange(points.toArray());
        }
        return bResult;      
    }
    
	/**
	 * 返回配准模式
	 * 
	 * @return 返回配准模式
	 */
    public TransformationMode getTransformMode() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getTransformMode()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        return this.m_TransformMode;
    }
    	
	/**
	 * 设置配准模式
	 * 
	 * @return 
	 */
    public  void setTransformMode(TransformationMode transformMode) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("setTransformMode()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (transformMode==null) {
            String message = InternalResource.loadString("transformMode",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        
        m_TransformMode = transformMode;
    }
    
	
	
	 protected void clearHandle() {
	      this.setHandle(0);
	      if(m_originalControlPoints!=null){
	    	 m_originalControlPoints.clear();
	    	 m_originalControlPoints = null;
	      }
	      if(m_targetControlPoints!=null){
	    	 m_targetControlPoints.clear();
	    	 m_targetControlPoints = null;
		  }
	    }
	 
	 public void dispose() {		
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			TransformationNative.jni_Delete(getHandle());
			clearHandle();
		}
	}
	 
   
    private void checkControlPoints(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("Transformmation",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        if(m_originalControlPoints==null || m_targetControlPoints==null){
            String message = InternalResource.loadString("ControlPoints",
                    InternalResource.TransformationControlPointsNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        
    	int originalLength = m_originalControlPoints.getCount();
    	int targetLength = m_targetControlPoints.getCount();
    	
    	if(originalLength==0 || targetLength==0){
            String message = InternalResource.loadString("Transformmation",
                    InternalResource.TransformationControlPointsShouldNotBeZero,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
    	}
    	
    	if(originalLength!=targetLength){
            String message = InternalResource.loadString("Transformmation",
                    InternalResource.TransformationOriginalAndTargetControlPointsCountMustEqual,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);    		
    	}
    	
    	if(m_TransformMode==TransformationMode.RECT && originalLength<2){
            String message = InternalResource.loadString("Transformmation",
                    InternalResource.TransformationRectModeNeedLeastTwoControlPoints,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);    		    		
    	}
    	
    	if(m_TransformMode==TransformationMode.LINEAR && originalLength<4){
            String message = InternalResource.loadString("Transformmation",
                    InternalResource.TransformationLinearModeNeedLeastFourControlPoints,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);    		    		
    	}
    	
    	if(m_TransformMode==TransformationMode.SQUARE && originalLength<7){
            String message = InternalResource.loadString("Transformmation",
                    InternalResource.TransformationSquareModeNeedLeastSevenControlPoints,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);    		    		
    	}
    }
    
}
