/**
 * 
 */
package com.supermap.data;

/**
 * 网络服务图片格式
 * 
 * @author lina
 * 
 */
public class ImageFormatType extends com.supermap.data.Enum {
	private ImageFormatType(int value, int ugcValue) {
		super(value, ugcValue);
	}
	//NONE
	public static final ImageFormatType NONE = new ImageFormatType(0, 0);
	//PNG
	public static final ImageFormatType PNG = new ImageFormatType(123, 123);
	//JPG
	public static final ImageFormatType JPG = new ImageFormatType(122, 122);
	//GIF
	public static final ImageFormatType GIF = new ImageFormatType(124, 124);
	//JPG_PNG
	public static final ImageFormatType JPG_PNG = new ImageFormatType(147, 147);
	//DXTZ
	public static final ImageFormatType DXTZ = new ImageFormatType(126, 126);
	//BMP
	public static final ImageFormatType BMP = new ImageFormatType(121, 121);	
}
