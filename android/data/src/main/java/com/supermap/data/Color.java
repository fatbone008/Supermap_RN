package com.supermap.data;
/**
 * SuperMap IMobile RGB颜色类
 * @yaoyh, wangdy
 *
 */
public class Color {
	private int value;
	
	/**
	 * 由一个rgb颜色值构造一个颜色类
	 * @param rgb
	 */
	public Color(int rgb){
		this.value = 0xff000000 | rgb;
	}
	
	/**
	 * 由rgb三个颜色分量构造一个颜色类
	 * @param r
	 * @param g
	 * @param b
	 */	
	public Color(int r, int g, int b){
		value = ((0xff << 24) | (r<<16) | (g<<8) | b);
	}
	
	//新增透明度分量
	/**alpha
	 * 由rgba四个颜色分量构造一个颜色类
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */	
	
	public Color(int r, int g, int b,int a){
		value = ((a << 24) | (r<<16) | (g<<8) | b);
	}
	
	/**
	 * 返回一个R颜色分量
	 * @return
	 */
	public int getR(){
		return (value>>16) & 0xff;
	}
	
	/**
	 * 返回一个G颜色分量
	 * @return
	 */
	public int getG(){
		return (value>>8) & 0xff;
	}
	
	/**
	 * 返回一个B颜色分量
	 * @return
	 */
	public int getB(){
		return value & 0xff;
	}
	   
	/**
	 * 返回一个alpha颜色分量
	 * @return
	 */
	public int getA(){
		return (value>>24) & 0xff;
	}
	/**
	 * 返回一个RGB颜色值
	 * @return
	 */
	public int getRGB(){
		return value;
	}
	
	/**
	 * 返回一个RGB颜色值
	 * @return
	 */
	public int getRGBA(){
		return  value;
	}
	/**
	 * 返回RGB颜色值16进制格式的字符串
	 * 这种格式的字符串可以被Android接受，并转化为android的color
	 * @return
	 */
	public String toColorString(){
		StringBuilder builder = new StringBuilder();
		builder.append("#");
		builder.append(Integer.toHexString(value));
		return builder.toString();
	}
}
