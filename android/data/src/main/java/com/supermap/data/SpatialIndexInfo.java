package com.supermap.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 张继南
 * @version 2.0
 */
public class SpatialIndexInfo extends InternalHandleDisposable{

    public SpatialIndexInfo() {

        long handle = SpatialIndexInfoNative.jni_New();
        this.setHandle(handle,true);

    }

    public SpatialIndexInfo(SpatialIndexType type){
        if (type == null) {
            String message = InternalResource.loadString("type",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new NullPointerException(message);
        }
        long handle = SpatialIndexInfoNative.jni_New2(type.getUGCValue());
        this.setHandle(handle,true);
    }

    public SpatialIndexInfo(int leafObjectCount){

        long handle = SpatialIndexInfoNative.jni_New3(leafObjectCount);
        this.setHandle(handle,true);
    }

    public SpatialIndexInfo(String tileField){

        long handle = SpatialIndexInfoNative.jni_New4(tileField);
        this.setHandle(handle,true);
    }

    public SpatialIndexInfo(double tileWidth,double tileHeight){
        if (tileWidth <= 0) {
            String message = InternalResource.loadString("tileWidth",
                    InternalResource.SpatialIndexInfoTileWidthShouldGreaterThanZero,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (tileHeight <= 0) {
            String message = InternalResource.loadString("tileHeight",
                    InternalResource.SpatialIndexInfoTileHeightShouldGreaterThanZero,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = SpatialIndexInfoNative.jni_New5(tileWidth,tileHeight);
        this.setHandle(handle,true);

    }

    public SpatialIndexInfo(Point2D gridCenter, double gridSize0,
                            double gridSize1, double gridSize2) {
        if (gridCenter == null) {
            String message = InternalResource.loadString("gridCenter",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new NullPointerException(message);
        }
        if (gridCenter.isEmpty()) {
            String message = InternalResource.loadString("gridCenter",
                    InternalResource.Point2DIsEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if(gridSize0 <= 0){
            String message = InternalResource.loadString("gridSize0",
                    InternalResource.SpatialIndexInfoGridSizeShouldGreaterThanZero,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if(gridSize1 <= 0){
            String message = InternalResource.loadString("gridSize1",
                    InternalResource.SpatialIndexInfoGridSizeShouldGreaterThanZero,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if(gridSize2 <= 0){
            String message = InternalResource.loadString("gridSize2",
                    InternalResource.SpatialIndexInfoGridSizeShouldGreaterThanZero,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = SpatialIndexInfoNative.jni_New6(gridCenter.getX(),
                gridCenter.getY(), gridSize0, gridSize1, gridSize2);
        this.setHandle(handle, true);
    }

    public SpatialIndexInfo(SpatialIndexInfo spatialIndexInfo){

        if(spatialIndexInfo == null || spatialIndexInfo.getHandle() == 0){
            String message = InternalResource.loadString("gridCenter",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new NullPointerException(message);
        }
        long handle = SpatialIndexInfoNative.jni_New7(spatialIndexInfo.getHandle());
        this.setHandle(handle, true);
        
//        InternalHandleDisposable.makeSureNativeObjectLive(spatialIndexInfo);
    }
    /**
     * 内部使用构造函数，Conversion模块中将会使用到
     * revision: 修改实现,Conversion模块中不再使用 by zhongdz
     * @param handle
     */
    SpatialIndexInfo(long handle){
    	//Modified by zhongdz 类库管理的指针，不能释放
    	this.setHandle(handle, /*true*/false);
    }
    //Added by HELH
    //Conversion模块中需要使用到
    protected static SpatialIndexInfo createInstance(long handle){
    	//modified by zhongdz 当handle为0时直接返回null
    	if(handle == 0){
    		return null;
    	}
    	else{
    		return new SpatialIndexInfo(handle);	
    	}
    }

    public void dispose() {
    	//Add by zhongdz 释放前先判断是否可以释放类库指针
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if(this.getHandle() != 0){
            SpatialIndexInfoNative.jni_Delete(this.getHandle());
            this.setHandle(0);
        }
    }

    public SpatialIndexInfo clone(){
        if (this.getHandle() == 0) {
           String message = InternalResource.loadString(
                   "clone()",
                   InternalResource.HandleObjectHasBeenDisposed,
                   InternalResource.BundleName);
           throw new IllegalStateException(message);
       }
       return new SpatialIndexInfo(this);
    }

    public SpatialIndexType getType() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getType()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int ugValue = SpatialIndexInfoNative.jni_GetType(this.getHandle());
        return (SpatialIndexType) Enum.parseUGCValue(SpatialIndexType.class,
                ugValue);
    }

    public void setType(SpatialIndexType type){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setType(SpatialIndexType value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (type == null) {
            String message = InternalResource.loadString("type",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);

            throw new NullPointerException(message);
        }
        SpatialIndexInfoNative.jni_SetType(this.getHandle(),type.getUGCValue());
    }

    public int getLeafObjectCount(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getLeafObjectCount()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return SpatialIndexInfoNative.jni_GetLeafObjectCount(this.getHandle());
    }

    public void setLeafObjectCount(int value){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setLeafObjectCount(int value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        SpatialIndexInfoNative.jni_SetLeafObjectCount(this.getHandle(),value);
    }

    public String getTileField(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getTileField()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return SpatialIndexInfoNative.jni_GetTileField(this.getHandle());
    }

    public void setTileField(String value){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setTileField(String value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if(value == null || value.trim().length() == 0){
           String message = InternalResource.loadString("value",
                   InternalResource.GlobalStringIsNullOrEmpty,
                   InternalResource.BundleName);
           throw new IllegalArgumentException(message);
       	}
        SpatialIndexInfoNative.jni_SetTileField(this.getHandle(),value);
    }
    public double getTileWidth(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getTileWidth()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return SpatialIndexInfoNative.jni_GetTileWidth(this.getHandle());
    }

    public void setTileWidth(double value){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setTileWidth(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (value <= 0) {
            String message = InternalResource.loadString("value",
                    InternalResource.SpatialIndexInfoTileWidthShouldGreaterThanZero,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        SpatialIndexInfoNative.jni_SetTileWidth(this.getHandle(),value);
    }
    public double getTileHeight(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getTileHeight()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return SpatialIndexInfoNative.jni_GetTileHeight(this.getHandle());
    }

    public void setTileHeight(double value){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setTileHeight(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (value <= 0) {
            String message = InternalResource.loadString("value",
                    InternalResource.SpatialIndexInfoTileHeightShouldGreaterThanZero,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        SpatialIndexInfoNative.jni_SetTileHeight(this.getHandle(),value);
    }

    public Point2D getGridCenter(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getGridCenter()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        double[] ugGridCenter = SpatialIndexInfoNative.jni_GetGridCenter(this.getHandle());
        return new Point2D(ugGridCenter[0],ugGridCenter[1]);
    }

    public void setGridCenter(Point2D value){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setGridCenter(Point2D value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (value == null) {
            String message = InternalResource.loadString("value",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new NullPointerException(message);
        }
        if (value.isEmpty()) {
            String message = InternalResource.loadString("value",
                    InternalResource.Point2DIsEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        SpatialIndexInfoNative.jni_SetGridCenter(this.getHandle(),value.getX(),value.getY());
    }

    public double getGridSize0(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getGridSize0()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return SpatialIndexInfoNative.jni_GetGridSize0(this.getHandle());
    }

    public void setGridSize0(double value){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setGridSize0(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if(value <= 0){
            String message = InternalResource.loadString("value",
                    InternalResource.SpatialIndexInfoGridSizeShouldGreaterThanZero,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        SpatialIndexInfoNative.jni_SetGridSize0(this.getHandle(),value);
    }

    public double getGridSize1(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getGridSize1()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return SpatialIndexInfoNative.jni_GetGridSize1(this.getHandle());
    }

    public void setGridSize1(double value){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setGridSize1(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if(value <= 0){
            String message = InternalResource.loadString("value",
                    InternalResource.SpatialIndexInfoGridSizeShouldGreaterThanZero,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        SpatialIndexInfoNative.jni_SetGridSize1(this.getHandle(),value);
    }
    public double getGridSize2(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getGridSize2()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return SpatialIndexInfoNative.jni_GetGridSize2(this.getHandle());
    }

    public void setGridSize2(double value){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setGridSize2(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if(value <= 0){
            String message = InternalResource.loadString("value",
                    InternalResource.SpatialIndexInfoGridSizeShouldGreaterThanZero,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        SpatialIndexInfoNative.jni_SetGridSize2(this.getHandle(),value);
    }

    public String toString(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("toString()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("{Type = ");
        buffer.append(this.getType().name());
        buffer.append(",LeafObjectCount=");
        buffer.append(this.getLeafObjectCount());
        buffer.append(",TileField=\"");
        buffer.append(this.getTileField());
        buffer.append("\",TileWidth=");
        buffer.append(this.getTileWidth());
        buffer.append(",TileHeight=");
        buffer.append(this.getTileHeight());
        buffer.append(",GridCenter=");
        buffer.append(this.getGridCenter());
        buffer.append(",GridSize0=");
        buffer.append(this.getGridSize0());
        buffer.append(",GridSize1=");
        buffer.append(this.getGridSize1());
        buffer.append(",=GridSize2=");
	buffer.append(this.getGridSize2());
        buffer.append("}\n");
        return buffer.toString();
    }
}
