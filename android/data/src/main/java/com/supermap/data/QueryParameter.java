package com.supermap.data;

/**
 * <p>
 * Title:
 * </p>
 *
 * <p>
 * Description:
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
 * @author ���ƽ�
 * @version 2.0
 */
public class QueryParameter extends InternalHandleDisposable {
	
    private Object m_spatialQueryObject = null;
    private SpatialQueryMode m_queryMode = SpatialQueryMode.NONE;

    // ���캯��
    public QueryParameter() {
        setHandle(QueryParameterNative.jni_New(), true);
        reset();
    }


    // ���캯��
    public QueryParameter(QueryParameter queryParameter) {
        if (queryParameter == null) {
            String message = InternalResource.loadString("queryParameter",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (queryParameter.getHandle() == 0) {
            String message = InternalResource.loadString("queryParameter",
                    InternalResource.GlobalArgumentObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        setHandle(QueryParameterNative.jni_New(), true);

        this.setAttributeFilter(queryParameter.getAttributeFilter());
        this.setCursorType(queryParameter.getCursorType());
//        this.setQueryType(queryParameter.getQueryType());
        this.setGroupBy(queryParameter.getGroupBy());
        this.setOrderBy(queryParameter.getOrderBy());
        this.setGroupBy(queryParameter.getGroupBy());
        this.setResultFields(queryParameter.getResultFields());
        this.setQueryBounds(queryParameter.getQueryBounds());
        this.setQueryIDs(queryParameter.getQueryIDs());
        this.setHasGeometry(queryParameter.getHasGeometry());
        this.setSpatialQueryMode(queryParameter.getSpatialQueryMode());
        this.setSpatialQueryObject(queryParameter.getSpatialQueryObject());

    }

    QueryParameter(long handle, Object queryObject,
                   SpatialQueryMode mode) {
        if (handle == 0) {
            String message = InternalResource.loadString("handle",
                    InternalResource.GlobalInvalidConstructorArgument,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        if (queryObject != null) {
            this.m_spatialQueryObject = queryObject;
            this.m_queryMode = mode;
        }

        //����ֻ��Recordset����������ǿ��ͷŵ�
        setHandle(handle, true);
    }


    private QueryParameter(long handle) {
        if (handle == 0) {
            String message = InternalResource.loadString("handle",
                    InternalResource.GlobalInvalidConstructorArgument,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        this.setHandle(handle, false);
        /**
         * @todo ��δʵ�� δ������ݣ�����������������ݡ�
         */
    }


    // ��ѯ���α�����
    public CursorType getCursorType() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        int ugcValue = QueryParameterNative.jni_GetCursorType(getHandle());
        return (CursorType) Enum.parseUGCValue(CursorType.class, ugcValue);
    }

    // ��ѯ���α�����
    public void setCursorType(CursorType value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        QueryParameterNative.jni_SetCursorType(getHandle(), value.getUGCValue());
    }

    // ��ѯSQL������䡣
    public String getAttributeFilter() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return QueryParameterNative.jni_GetAttributeFilter(getHandle());
    }

    // ��ѯSQL������䡣
    public void setAttributeFilter(String value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (value == null) {
            value = "";
        }

        QueryParameterNative.jni_SetAttributeFilter(getHandle(), value);
    }

    public boolean getHasGeometry() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return QueryParameterNative.jni_GetHasGeometry(getHandle());
    }

    public void setHasGeometry(boolean value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        QueryParameterNative.jni_SetHasGeometry(getHandle(), value);
    }

    // SQL��ѯ�����Ӿ䡣
    public String[] getOrderBy() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        String str = QueryParameterNative.jni_GetOrderBy(getHandle());
        return Toolkit.splitString(str, ",");
    }

    // SQL��ѯ�����Ӿ䡣
    public void setOrderBy(String[] value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        String str = Toolkit.joinString(value, ",");
        QueryParameterNative.jni_SetOrderBy(getHandle(), str);
    }

    // SQL��ѯ���������Ӿ䡣
    public String[] getGroupBy() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        String str = QueryParameterNative.jni_GetGroupBy(getHandle());
        return Toolkit.splitString(str, ",");
    }

    // SQL��ѯ���������Ӿ䡣
    public void setGroupBy(String[] value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        String str = Toolkit.joinString(value, ",");
        QueryParameterNative.jni_SetGroupBy(getHandle(), str);
    }
    
    protected int[] getQueryIDs(){
    	if(getHandle() == 0){
    		String message = InternalResource.loadString("",
    				InternalResource.HandleObjectHasBeenDisposed, 
    				InternalResource.BundleName);
    		throw new IllegalStateException(message);
    	}
    	
    	int nCount = QueryParameterNative.jni_GetIDsNumber(getHandle());
    	int[] resultIDs = new int[nCount];
    	
    	QueryParameterNative.jni_GetQueryIDs(getHandle(), resultIDs);
    	
    	return resultIDs;    	
    }

    protected void setQueryIDs(int[] ids){    	
    	if(getHandle() == 0){
    		String message = InternalResource.loadString("",
    				InternalResource.HandleObjectHasBeenDisposed, 
    				InternalResource.BundleName);
    		throw new IllegalStateException(message);
    	}
    	
    	if(ids!=null){
    		QueryParameterNative.jni_SetQueryIDs(getHandle(), ids);
    	}
    }
    
    protected Rectangle2D getQueryBounds(){
    	if(getHandle() == 0){
    		String message = InternalResource.loadString("",
    				InternalResource.HandleObjectHasBeenDisposed, 
    				InternalResource.BundleName);
    		throw new IllegalStateException(message);
    	}
    	
    	double[] bounds = new double[4];
    	QueryParameterNative.jni_GetQueryBounds(getHandle(), bounds);
    	
    	Rectangle2D result = new Rectangle2D(bounds[0], bounds[1], bounds[2], bounds[3]);
    	return result;
    }
    
    protected void setQueryBounds(Rectangle2D queryBounds){
    	if(getHandle() == 0){
    		String message = InternalResource.loadString("",
    				InternalResource.HandleObjectHasBeenDisposed, 
    				InternalResource.BundleName);
    		throw new IllegalStateException(message);
    	}
    	
    	if(queryBounds != null){
    		double[] bounds = new double[4];
        	bounds[0] = queryBounds.getLeft();
        	bounds[1] = queryBounds.getBottom();
        	bounds[2] = queryBounds.getRight();
        	bounds[3] = queryBounds.getTop();
        	
        	QueryParameterNative.jni_SetQueryBounds(getHandle(), bounds);
    	}
    }
    
//    public QueryType getQueryType(){
//    	if(getHandle() == 0){
//    		String message = InternalResource.loadString("",
//    				InternalResource.HandleObjectHasBeenDisposed, 
//    				InternalResource.BundleName);
//    		throw new IllegalStateException(message);
//    	}
//    	
//    	int ugcValue = QueryParameterNative.jni_GetQueryType(getHandle());
//    	return (QueryType)Enum.parse(QueryType.class, ugcValue);
//    }
//    
//    public void setQueryType(QueryType queryType){
//    	if(getHandle() == 0){
//    		String message = InternalResource.loadString("",
//    				InternalResource.HandleObjectHasBeenDisposed, 
//    				InternalResource.BundleName);
//    		throw new IllegalStateException(message);
//    	}
//    	
//    	QueryParameterNative.jni_SetQueryType(getHandle(), queryType.getUGCValue());
//    }
    
    // ��ѯ����ֶμ������Ϊ�գ����ѯ�����ֶΡ�
    public String[] getResultFields() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        String str = QueryParameterNative.jni_GetResultFields(getHandle());
        return Toolkit.splitString(str, ";");
    }

    // ��ѯ����ֶμ������Ϊ�գ����ѯ�����ֶΡ�
    public void setResultFields(String[] value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        String str = Toolkit.joinString(value, ";");
        QueryParameterNative.jni_SetResultFields(getHandle(), str);
    }

    // ������в�ѯ����Ϊһ���ַ���
    public String toString() {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("{CursorType = ");
        buffer.append(this.getCursorType().name());
        buffer.append(",AttributeFilter = ");
        buffer.append(this.getAttributeFilter());
        buffer.append(",SpatialQueryMode = ");
//        buffer.append(this.getSpatialQueryMode().name());
        buffer.append(",OrderBy = {");
        String orderBy = this.stringArrayToString(this.getOrderBy());
        buffer.append(orderBy);
        buffer.append("},GroupBy = {");
        String groupBy = this.stringArrayToString(this.getOrderBy());
        buffer.append(groupBy);
        buffer.append("},ResultFields = { ");
        String resultFields = this.stringArrayToString(this.getResultFields());
        buffer.append(resultFields);
        buffer.append("}}\n");
        return buffer.toString();
    }

    public void dispose() {
        if (!super.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (super.getHandle() != 0) {
            QueryParameterNative.jni_Delete(getHandle());
            clearHandle();
        }
    }

    protected void clearHandle() {
//        this.m_spatialQueryObject = null;
//        this.m_spatialQueryObject = null;
//        this.m_queryMode = null;
//        this.m_spatialFilter = null;
    	
        setHandle(0);
    }

    protected static QueryParameter createInstance(long handle) {
        if (handle == 0) {
            String message = InternalResource.loadString("handle",
                    InternalResource.GlobalInvalidConstructorArgument,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        return new QueryParameter(handle);
    }

    protected static void clearHandle(QueryParameter queryParameter) {
        queryParameter.clearHandle();
    }


//����Java����йܳ�Ա��һ�¡�
//    private void identicalJavaObject(QueryParameter parameter) {
//        this.setSpatialQueryMode(parameter.getSpatialQueryMode());
//        this.setSpatialQueryObject(parameter.getSpatialQueryObject());
////        if (QueryParameter.isValidSpatialFilter(parameter.getSpatialFilter())) {
////            this.setSpatialFilter(parameter.getSpatialFilter());
////        }
////        this.m_spatialFilter = parameter.m_spatialFilter;
//    }
//
//    //��parameter1�е�java����йܳ�Ա��parameter2��һ��
//    protected static void identicalJavaObject(QueryParameter parameter1,
//                                              QueryParameter parameter2) {
//        parameter1.identicalJavaObject(parameter2);
//    }


    void reset() {
        if (getHandle() != 0) {
            QueryParameterNative.jni_Reset(getHandle());
        }
    }

    /**
     * ��String���͵�����ת��ΪString���������ʾ��
     *
     * @param stringArray
     *            String[]
     * @return String
     */

    private String stringArrayToString(String[] stringArray) {
        int length = stringArray.length;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(stringArray[i]);
        }
        return buffer.toString();
    }


	public SpatialQueryMode getSpatialQueryMode() {
		if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        return this.m_queryMode;
	}

	// �ռ��ѯ�������ӣ�����9�̶ֹ����Ӻ�һ���û��Զ�������
    public void setSpatialQueryMode(SpatialQueryMode value) {
        if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (value == null) {
            String message = "value";
            throw new NullPointerException(message);
        }

        this.m_queryMode = value;
    }
    
	public Object getSpatialQueryObject() {
		if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return this.m_spatialQueryObject;
	}


	public void setSpatialQueryObject(Object value) {
		if (getHandle() == 0) {
            String message = InternalResource.loadString("",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (value == null) {
            this.m_spatialQueryObject = null;
            return;
        }

//        if (!isValidQueryObject(value)) {
//            String message = InternalResource.loadString("value",
//                    InternalResource.QueryParameterInvalidQueryObject,
//                    InternalResource.BundleName);
//            throw new IllegalArgumentException(message);
//        }

        this.m_spatialQueryObject = value;
		
	}
	
	public String toJson(){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
		sb.append(" \"attributeFilter\" : "  + convertToJsonString(this.getAttributeFilter())+ ",");
		
		String groupbys = "";
		String[] groupby = this.getGroupBy();
		if(groupby.length >0){
			for(int i=0;i<groupby.length;i++){
				groupbys += convertToJsonString(groupby[i]);
				if(i != groupby.length-1){
					groupbys += ",";
				}
			}
			sb.append(" \"groupBy\" : [" + convertToJsonString(groupbys) + "],");
		}
		
		String orderbys = "";
		String[] orderby = this.getOrderBy();
		if(orderby.length >0){
			for(int i=0;i<orderby.length;i++){
				orderbys += convertToJsonString(orderby[i]);
				if(i != orderby.length-1){
					orderbys += ",";
				}
			}
			sb.append(" \"orderBy\" : [" + convertToJsonString(orderbys) + "],");
		}
		
		String ids = "";
		int[] id = this.getQueryIDs();
		if(id.length >0){
			for(int i=0;i<id.length;i++){
				ids += Integer.toString(id[i]);
				if(i != id.length-1){
					ids += ",";
				}
			}
			sb.append(" \"ids\" : [" + ids + "],");
		}
		
		String fields = "";
		String[] field = this.getResultFields();
		if(field.length >0){
			for(int i=0;i<field.length;i++){
				fields += convertToJsonString(field[i]);
				if(i != field.length){
					fields += ",";
				}
			}
			sb.append(" \"fields\": [" + convertToJsonString(fields) + "]");
		}
		
		//����Ƿ����һ����¼������˶���
		if(sb.toString().endsWith(",")){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("}");
		return sb.toString();
	}
	
	// ʱ��תdouble�ľ�̬���������ڵ�ʱ���ѯ
	static public String dateToString(int year, int month, int day, int hours, int minutes, int seconds)
	{
		long hhh = InternalVariantNative.jni_New();
		InternalVariantNative.jni_SetValueTime(hhh, year, month, day,
				hours, minutes, seconds);
		double dValue = InternalVariantNative.jni_GetValueDouble(hhh);
		InternalVariantNative.jni_Delete(hhh);
		return Double.toString(dValue);
	}
	
	private String convertToJsonString(String src){
		return "\"" + src + "\"";
	}
}
