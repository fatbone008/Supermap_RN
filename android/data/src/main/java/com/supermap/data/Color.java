package com.supermap.data;
/**
 * SuperMap IMobile RGB��ɫ��
 * @yaoyh, wangdy
 *
 */
public class Color {
	private int value;
	
	/**
	 * ��һ��rgb��ɫֵ����һ����ɫ��
	 * @param rgb
	 */
	public Color(int rgb){
		this.value = 0xff000000 | rgb;
	}
	
	/**
	 * ��rgb������ɫ��������һ����ɫ��
	 * @param r
	 * @param g
	 * @param b
	 */	
	public Color(int r, int g, int b){
		value = ((0xff << 24) | (r<<16) | (g<<8) | b);
	}
	
	//����͸���ȷ���
	/**alpha
	 * ��rgba�ĸ���ɫ��������һ����ɫ��
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */	
	
	public Color(int r, int g, int b,int a){
		value = ((a << 24) | (r<<16) | (g<<8) | b);
	}
	
	/**
	 * ����һ��R��ɫ����
	 * @return
	 */
	public int getR(){
		return (value>>16) & 0xff;
	}
	
	/**
	 * ����һ��G��ɫ����
	 * @return
	 */
	public int getG(){
		return (value>>8) & 0xff;
	}
	
	/**
	 * ����һ��B��ɫ����
	 * @return
	 */
	public int getB(){
		return value & 0xff;
	}
	   
	/**
	 * ����һ��alpha��ɫ����
	 * @return
	 */
	public int getA(){
		return (value>>24) & 0xff;
	}
	/**
	 * ����һ��RGB��ɫֵ
	 * @return
	 */
	public int getRGB(){
		return value;
	}
	
	/**
	 * ����һ��RGB��ɫֵ
	 * @return
	 */
	public int getRGBA(){
		return  value;
	}
	/**
	 * ����RGB��ɫֵ16���Ƹ�ʽ���ַ���
	 * ���ָ�ʽ���ַ������Ա�Android���ܣ���ת��Ϊandroid��color
	 * @return
	 */
	public String toColorString(){
		StringBuilder builder = new StringBuilder();
		builder.append("#");
		builder.append(Integer.toHexString(value));
		return builder.toString();
	}
}
