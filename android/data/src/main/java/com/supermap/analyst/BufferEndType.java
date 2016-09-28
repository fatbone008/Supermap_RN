package com.supermap.analyst;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author not ZHANGJN
 * @version 2.0
 */
 public class BufferEndType extends com.supermap.data.Enum {
    private BufferEndType(int value, int ugcValue) {
        super(value, ugcValue);
    }

    /**
     * ԲͷBuffer
     */
    public static final BufferEndType ROUND = new BufferEndType(1,1);

    /**
     * ƽͷBuffer
     */
    public static final BufferEndType FLAT = new BufferEndType(2,2);
}
