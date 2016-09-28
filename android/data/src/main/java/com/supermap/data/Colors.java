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
 * @author 李云锦
 * @version 2.0
 */
public class Colors extends InternalHandleDisposable {
	private boolean isForDataset = false;

	//
	public Colors() {
		setHandle(ColorsNative.jni_New(), true);
	}

	//
	public Colors(Color[] colors) {
		if (colors == null) {
			String message = InternalResource.loadString("colors",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		setHandle(ColorsNative.jni_New(), true);
		this.addRange(colors);
	}

	//
	public Colors(Colors colors) {
		if (colors == null) {
			throw new NullPointerException("colors");
		}
		if (colors.getHandle() == 0) {
			String message = InternalResource.loadString("colors",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		setHandle(ColorsNative.jni_New(), true);
		this.addRange(colors.toArray());
	}
	//	Modified by Helh
	//添加一个参数isForDataset来标志，该颜色表的值是否从数据集中获得的,如果为true
	//在处理Colors中的颜色时需要做一些特殊处理
	//主要是因为UGC需要保持与SFC的兼容，在显示的时候将数据集中的调色板R和B颜色位置对调了
	// 通过在组件层控制其位置的对调保持一致
	Colors(long handle, boolean isForDataset) {
		this.isForDataset = isForDataset;
		setHandle(handle, false);
	}

	Colors(long handle, boolean disposable, boolean isForDataset) {
		this.isForDataset = isForDataset;
		setHandle(handle, disposable);
	}

	//
	public Color get(int index) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("get(int index)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (index < 0 || index >= getCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		int colorRGB = ColorsNative.jni_GetItem(getHandle(), index);
		// 注：Color对象作为数值型来处理，故不存在连点问题。
		// Added by Helh 2010-1-15
		// 修改UGON-2019问题中，由于设置调色板导致MapControl上显示的颜色与设置不一致问题
		// 主要是因为UGC需要保持与SFC的兼容，在显示的时候将数据集中的调色板R和B颜色位置对调了
		// 通过在组件层控制其位置的对调保持一致
		Color result = new Color(colorRGB);
		if (isForDataset) {
			result = swapRnB(result);
		}
		return result;
	}

	//
	public void set(int index, Color value) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"set(int index, Color value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (index < 0 || index >= getCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}

		if (value == null) {
			String message = InternalResource.loadString("value",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		Color temp = value;
		// Added by Helh 2010-1-15
		// 修改UGON-2019问题中，由于设置调色板导致MapControl上显示的颜色与设置不一致问题
		// 主要是因为UGC需要保持与SFC的兼容，在显示的时候将数据集中的调色板R和B颜色位置对调了
		// 通过在组件层控制其位置的对调保持一致
		if (isForDataset) {
			temp = swapRnB(value);
		}

		ColorsNative.jni_Set(getHandle(), index, temp.getRGB());
	}

	//
	public int getCount() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("getCount()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return ColorsNative.jni_GetCount(getHandle());
	}

	//
	public int add(Color color) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("add(Color color)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (color == null) {
			String message = InternalResource.loadString("color",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		Color temp = color;
		// Added by Helh 2010-1-15
		// 修改UGON-2019问题中，由于设置调色板导致MapControl上显示的颜色与设置不一致问题
		// 主要是因为UGC需要保持与SFC的兼容，在显示的时候将数据集中的调色板R和B颜色位置对调了
		// 通过在组件层控制其位置的对调保持一致
		if (isForDataset) {
			temp = swapRnB(color);
		}
		return ColorsNative.jni_Add(getHandle(), temp.getRGB());
	}

	public int addRange(Color[] colors) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"addRange(Color[] colors)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (colors == null) {
			throw new NullPointerException("colors");
		}
		int size = colors.length;
		int[] colorsRGB = new int[size];
		// Added by Helh 2010-1-15
		// 修改UGON-2019问题中，由于设置调色板导致MapControl上显示的颜色与设置不一致问题
		// 主要是因为UGC需要保持与SFC的兼容，在显示的时候将数据集中的调色板R和B颜色位置对调了
		// 通过在组件层控制其位置的对调保持一致
		//此处采用在循环外进行判断，减少循环的中断次数
		if (isForDataset) {
			for (int i = 0; i < size; i++) {
				Color temp = swapRnB(colors[i]);
				colorsRGB[i] = temp.getRGB();
			}
		}else{
			for (int i = 0; i < size; i++) {
				colorsRGB[i] = colors[i].getRGB();
			}
		}
		return ColorsNative.jni_AddRange(getHandle(), colorsRGB);
	}

	//
	public boolean insert(int index, Color color) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString(
					"insert(int index, Color color)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (index < 0 || index > getCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalInvalidInsertPosition,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}

		if (color == null) {
			String message = InternalResource.loadString("color",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		if (index == getCount()) {
			return (add(color) != -1);
		}
		// Added by Helh 2010-1-15
		// 修改UGON-2019问题中，由于设置调色板导致MapControl上显示的颜色与设置不一致问题
		// 主要是因为UGC需要保持与SFC的兼容，在显示的时候将数据集中的调色板R和B颜色位置对调了
		// 通过在组件层控制其位置的对调保持一致
		Color temp = color;
		if(isForDataset){
			temp = swapRnB(color);
		}
		boolean result = ColorsNative.jni_Insert(getHandle(), index, temp
				.getRGB());
		return result;
	}

	//
	public boolean remove(int index) {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("remove(int index)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (index < 0 || index >= getCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		return ColorsNative.jni_Remove(getHandle(), index);
	}

	//
	public void clear() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("clear()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		ColorsNative.jni_Clear(getHandle());
	}

	//
	public static Colors makeRandom(int count) {
		if (count <= 0) {
			String message = InternalResource.loadString("count",
					InternalResource.ColorsCountShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		long handle = ColorsNative.jni_MakeRandom(count);

		if (handle == 0) {
			return null;
		} else {
			// @modified by 孔令亮 at 2007年8月23日 下午02时38分24秒
			// @reason: 静态方法，应该返回可释放对象
			return new Colors(handle, true, false);
		}
	}

	//
	public static Colors makeGradient(int count, Color[] gradientColors) {
		if (count <= 0) {
			String message = InternalResource.loadString("count",
					InternalResource.ColorsCountShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (gradientColors == null) {
			String message = InternalResource.loadString("intervalColors",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		if (gradientColors.length < 2) {
			String message = InternalResource.loadString("intervalColors",
					InternalResource.ColorsIntervalColorsLengthInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		int len = gradientColors.length;
		int[] values = new int[len];
		for (int i = 0; i < gradientColors.length; i++) {
			values[i] = gradientColors[i].getRGB();
		}
		long handle = ColorsNative.jni_MakeGradient1(count, values);

		if (handle == 0) {
			return null;
		} else {
			// @modified by 孔令亮 at 2007年8月23日 下午02时38分24秒
			// @reason: 静态方法，应该返回可释放对象
			return new Colors(handle, true, false);
		}
	}

	//
	public static Colors makeGradient(int count, ColorGradientType type,
			boolean reverse) {

		if (count <= 0) {
			String message = InternalResource.loadString("count",
					InternalResource.ColorsCountShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (type == null) {
			String message = InternalResource.loadString("type",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		long handle = ColorsNative.jni_MakeGradient2(count, type.getUGCValue(),
				reverse);

		if (handle == 0) {
			return null;
		} else {
			// @modified by 孔令亮 at 2007年8月23日 下午02时38分24秒
			// @reason: 静态方法，应该返回可释放对象
			return new Colors(handle, true, false);
		}

	}

	//
	public Color[] toArray() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("toArray()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int len = getCount();
		Color[] colors = new Color[len];
		for (int i = 0; i < len; i++) {
			// @modified by 孔令亮 at 2007年8月23日 下午03时28分42秒
			// @reason:
			// colors[i] = Color.getColor(get(i).toString());
			colors[i] = this.get(i);
		}
		return colors;
	}

	public String toString() {
		if (getHandle() == 0) {
			String message = InternalResource.loadString("toString()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("{Count=");
		buffer.append(this.getCount());
		buffer.append("}\n");
		return buffer.toString();
	}

	public void dispose() {
		if (!super.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (super.getHandle() != 0) {
			ColorsNative.jni_Delete(super.getHandle());
			setHandle(0);
		}
	}

	protected void clearHandle() {
		setHandle(0);
	}
	//Modified by Helh
	//添加一个参数isForDataset来标志，该颜色表的值是否从数据集中获得的,如果为true
	//在处理Colors中的颜色时需要做一些特殊处理
	//主要是因为UGC需要保持与SFC的兼容，在显示的时候将数据集中的调色板R和B颜色位置对调了
	// 通过在组件层控制其位置的对调保持一致
	protected static Colors createInstance(long handle, boolean isForDataset) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new Colors(handle, isForDataset);
	}

	protected static void clearHandle(Colors colors) {
		colors.clearHandle();
	}

	// 主要用于一些底层没有的对象，设置Style后保持对象不变。
	protected static void changeHandle(Colors colors, long handle) {
		colors.changeHandle(handle);
	}

	private void changeHandle(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 当直接改变handle时，要将原来的handle对象先释放调，否则会内存泄露。
		// 此情况下，该对象必须本质上是自己在Java层中new出来的。不可是UGC中其他对象的成员。
		if (this.getHandle() != 0) {
			ColorsNative.jni_Delete(getHandle());
		}
		this.setHandle(handle, false);
	}

	/**
	 * @param color
	 * @return 交换R和B值的结果颜色
	 */
	private Color swapRnB(Color color) {
		Color result = new Color(color.getB(), color.getG(), color
				.getR());
		return result;
	}
	
	/**
	 * @param count 颜色总数
	 * @param color 控制色集合
	 * @return 由间隔色个数和控制色集合生成的随机颜色表
	 * @author 郑月玲
	 */
	public Colors makeRandom(int count,Color[] color) 
	{
		if (count < 2) 
		{
			String message = InternalResource.loadString("count",
					InternalResource.ColorsCountShouldBePositive,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (color == null) 
		{
			String message = InternalResource.loadString("color",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}

		if (color.length < 2) 
		{
			String message = InternalResource.loadString("color",
					InternalResource.ColorsIntervalColorsLengthInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (color.length > count) 
		{
			String message = InternalResource.loadString("count",
					InternalResource.ColorsIntervalColorsLengthInvalid,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		int len = color.length;
		int[] values = new int[len];
		for (int i = 0; i < color.length; i++)
		{
			values[i] = color[i].getRGB();
		}
		long handle = ColorsNative.jni_MakeRandom1(count, values);

		if (handle == 0) 
		{
			return null;
		}
		else
		{
			return new Colors(handle, true, false);
		}

	}
}
