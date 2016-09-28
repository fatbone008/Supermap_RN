package com.supermap.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.Region.Op;

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
 * @author not attributable
 * @version 2.0
 */
public class Toolkit {
	static {	
		Environment.LoadWrapJ();
	}
	
	protected Toolkit() {
	}

	// double:
	// max: 1.7976931348623157e+308
	// min:-1.7976931348623157e+308
	// float:
	// max:3.4028235e+038
	// min:-3.4028235e+038
	// 由于Float.MIN_VALUE、Double.MIN_VALUE的值是无限接近0的正值，不是我们先要的接近负无穷的值，
	// 故在此处重新定义doulbe和float类型的最大最小值。
	protected static final double DBL_MAX_VALUE = 1.7976931348623157e+308;

	protected static final double DBL_MIN_VALUE = -1.7976931348623157e+308;

	protected static final float FLT_MAX_VALUE = (float) 3.4028235e+038;

	protected static final float FLT_MIN_VALUE = (float) -3.4028235e+038;
	
	/**
	 * 将指定的几何对象的绘制成图片
	 * @param geometry 几何对象
	 * @param resources 关联的资源库
	 * @param fileName 出图文件的路径
	 * @param width 宽
	 * @param height 高
	 * @return
	 */
	public static boolean drawToPNG(Geometry geometry, Resources resources, 
			String fileName, int width, int height) {
		if (geometry == null) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long geometryHandle = InternalHandle.getHandle(geometry);
		if (geometryHandle == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (resources == null) {
			String message = InternalResource.loadString("resources",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long resourcesHandle = InternalHandle.getHandle(resources);
		if (resourcesHandle == 0) {
			String message = InternalResource.loadString("resources",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		Geometry clone = (Geometry) geometry.clone();

		boolean result = false;
		result = ToolkitNative.jni_DrawGeometryToPNG(clone.getHandle(),
				resourcesHandle, fileName, width, height);
		return result;
	}

	/**
	 * 将符号绘制到指定的位图上
	 * @param geometry 几何对象
	 * @param resources 资源库
	 * @param bmp 指定绘制的位图
	 * @return
	 */ // 这个接口暂时还有问题，就不开放了 20120726 liucq
	private static boolean draw(Geometry geometry, Resources resources,
			Bitmap bmp) {
		if (geometry == null) {
			String message = InternalResource.loadString("geometry",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long geometryHandle = InternalHandle.getHandle(geometry);
		if (geometryHandle == 0) {
			String message = InternalResource.loadString("geometry",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (resources == null) {
			String message = InternalResource.loadString("resources",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long resourcesHandle = InternalHandle.getHandle(resources);
		if (resourcesHandle == 0) {
			String message = InternalResource.loadString("resources",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (bmp == null) {
			String message = InternalResource.loadString("bmp",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		Geometry clone = (Geometry) geometry.clone();
		Rectangle2D rect = clone.getBounds();
				
		return ToolkitNative.jni_DrawGeometry(clone.getHandle(),
				resourcesHandle, bmp);
	}

	/**
	 * 判断一个给定的值是否为0,使用用户设置的判断精度
	 * 
	 * @param value
	 *            double
	 * @return boolean
	 */
	public static boolean isZero(double value) {
		boolean result = value >= Environment.getMinEqualZeroPrecision()
				&& value <= Environment.getMaxEqualZeroPrecision();
		return result;
	}

	/**
	 * 判断给定值是否为0,使用传入参数
	 * 
	 * @param value
	 *            double
	 * @param equalPrecision
	 *            double
	 * @return boolean
	 */
	public static boolean isZero(double value, double precision) {
		boolean result = value >= -Math.abs(precision)
				&& value <= Math.abs(precision);
		return result;
	}

	/**
	 * 判断给定值是否为0,使用传入参数
	 * 
	 * @param value
	 *            double
	 * @param maxEqualPrecision
	 *            double
	 * @param minEqualPrecision
	 *            double
	 * @return boolean modified by liyj 修改参数顺序
	 */
	public static boolean isZero(double value, double minPrecision,
			double maxPrecision) {
		boolean result = value >= minPrecision && value <= maxPrecision;
		return result;
	}

	/**
	 * 对指定的数据源进行紧缩处理，通过紧缩可以使SDB数据源占用的存储空间变小
	 * @param datasource
	 * @return
	 */
	public static boolean compactDatasource(Datasource datasource) {
		if(datasource == null) {
			String message = InternalResource.loadString("datasource",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long handle = InternalHandleDisposable.getHandle(datasource);
		if(handle == 0) {
			String message = InternalResource.loadString("datasource",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		boolean bResult = ToolkitNative.jni_CompactDatasource(handle);
		// 紧缩数据源后类库会干掉数据源一次，上面将所有数据集重置一下
		if (datasource.getDatasets().getCount() > 0) {
			datasource.getDatasets().reset();
		}
		return bResult;
	}
	
	/**
	 * 传入数据集类型获取该数据集类型支持的编码类型数组
	 * @param type
	 * @return
	 */
	public static EncodeType[] getEncodeType(DatasetType type) {
		if (type == null) {
			String message = InternalResource.loadString("type",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		ArrayList<EncodeType> arrayList = new ArrayList<EncodeType>();
		switch (type.value()) {
		case 3:
		case 5:
		case 4: {
			arrayList.add(EncodeType.BYTE); // UGDataCodec::encBYTE
			arrayList.add(EncodeType.INT16); // UGDataCodec::encWORD
			arrayList.add(EncodeType.INT24); // UGDataCodec::enc3BYTE
			arrayList.add(EncodeType.INT32); // UGDataCodec::encDWORD
			arrayList.add(EncodeType.NONE); // UGDataCodec::encNONE
		}
			break;
		case 0:
		case 1:
		case 7:
		case 149:
		case 153:
		case 35: {
			arrayList.add(EncodeType.NONE); // UGDataCodec::encNONE
		}
			break;
		case 81: {
			arrayList.add(EncodeType.LZW); // UGDataCodec::encLZW
			arrayList.add(EncodeType.DCT); // UGDataCodec::encDCT,
		}
			break;
		case 83: {
			arrayList.add(EncodeType.LZW); // UGDataCodec::encLZW
			arrayList.add(EncodeType.SGL); // UGDataCodec::encSGL,
		}
			break;
		default:
			break;
		}
		EncodeType[] types = new EncodeType[arrayList.size()]; 
		return arrayList.toArray(types);
	}
	
	public static void clearErrors() {
		ToolkitNative.jni_ClearErrors();
	}
	
	public static boolean extractCacheFile(String sourceFileName, String targetFolderName){
		return extractCacheFile(sourceFileName, targetFolderName,"");
	}
	
	public static boolean extractCacheFile(String sourceFileName, String targetFolderName, String password){

		if(password == null){
			password = "";
		}
		
		boolean result = false;
		
		result = ToolkitNative.jni_ExtractCacheFile(sourceFileName, targetFolderName,password);			
		
		return result;
	}


	// 7Zip解压
	public static boolean unCompress(String srcFileName, String targetFolder,
			String password) {

		boolean result = false;
		if (srcFileName != null && targetFolder != null) {
			if (password == null) {
				password = "";
			}
			result = ToolkitNative.jni_UnCompress7Zip(srcFileName,
					targetFolder, password);
		}
		return result;
	}
	

	protected static void setHandleBooleanValue(long handle, boolean value) {
		ToolkitNative.jni_SetBooleanHandle(handle, value);
	}

	protected static boolean getHandleBooleanValue(long handle) {
		return ToolkitNative.jni_GetBooleanHandle(handle);
	}
	
	
	/**
	 * add by xuzw 用来处理UGdouble *，MapControl中会用到
	 * @param handle
	 * @return
	 */
	protected static double getHandleDoubleValue(long handle) {
		return ToolkitNative.jni_GetHandleDoubleValue(handle);
	}
    
    protected static void setHandleDoubleValue(long handle, double value) {
		ToolkitNative.jni_SetHandleDoubleValue(handle, value);
	}
    
	/**
	 * 将String用指定的字符串链接起来
	 * 
	 * @param arr
	 *            String[]
	 * @return String
	 */
	protected static String joinString(String[] arr, String connectString) {
		if (arr == null || arr.length == 0 || connectString == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length - 1; i++) {
			sb.append(arr[i] + connectString);
		}
		sb.append(arr[arr.length - 1]);
		return sb.toString();
	}

	/**
	 * 将字符串用指定的分割符分割
	 * 
	 * @param arr
	 *            String
	 * @return String
	 */
	protected static String[] splitString(String value, String splitString) {
		if (value == null || value.trim().equals("") || splitString == null) {
			return new String[0];
		}
		return value.split(splitString);
	}

	static Date parseUGCTime(String ugcTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			return formatter.parse(ugcTime);
		} catch (ParseException ex) {
			return null;
		}
	}

	static String toUGCTime(Date time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return formatter.format(time);
	}

	static boolean isValidEncodeType(boolean isVector, EncodeType encodeType) {
		boolean result = true;
		if (isVector) {
			// 如果是Vector就不能使用，仅适用于栅格数据的编码类型
			if (encodeType.equals(EncodeType.DCT)
					|| encodeType.equals(EncodeType.LZW)
					|| encodeType.equals(EncodeType.SGL)) {
				result = false;
			}
		} else {
			// 如果不是Vector就不能使用，仅适用于Vector数据的编码类型
			if (encodeType.equals(EncodeType.BYTE)
					|| encodeType.equals(EncodeType.INT16)
					|| encodeType.equals(EncodeType.INT24)
					|| encodeType.equals(EncodeType.INT32)) {
				result = false;
			}
		}
		return result;
	}

	
	protected static boolean isDirectoryExisted(String fileName) {
		File file = new File(fileName);
		boolean bResult = false;
		if (file.getParent() == null) {
			bResult = true;
		} else {
			File newFile = new File(file.getParent());
			if (newFile.exists()) {
				bResult = true;
			}
		}
		return bResult;
	}

//	/**
//	 * 主要是拷貝UGC中用到的三维图片到临时目录中。
//	 * 
//	 * @param picturePath
//	 *            源文件的相對路徑
//	 * @param targetPath
//	 *            目標文件的相對路徑
//	 * @param pictrueNames
//	 *            圖片的名稱集合，即需要拷貝的所有圖片。
//	 */
//	protected static void copyPictureToTempFile(String picturePath,
//			String targetPath, String... pictrueNames) {
//		for (String pictureName : pictrueNames) {
//			// 需要保存的源文件的路徑。
//			String srcFilePath = picturePath + pictureName;
//			URL url = Toolkit.class.getResource(srcFilePath);
//			if (url == null) {
//				System.out.println("null");
//			}
//
//			// add by xuzw 2010-01-12 添加对操作系统的判断，非Windows环境的缓存目录需要多一个"/"
//			String osName = System.getProperty("os.name");
//			String targetFilePath = "";
//			if ("Windows".indexOf(osName) != -1) {
//				targetFilePath = targetPath + pictureName;
//			} else {
//				targetFilePath = targetPath + "/" +pictureName;
//			}
//			
//			File outputFile = new File(targetFilePath);
//
//			InputStream inputStream = null;
//			FileImageOutputStream fileOutStream = null;
//			if (targetFilePath != null) {
//				try {
//					inputStream = url.openStream();
//					fileOutStream = new FileImageOutputStream(outputFile);
//				} catch (IOException e) {
//					System.out.println("File Not Found!");
//					e.printStackTrace();
//				}
//
//				try {
//					int read = 0;
//					while ((read = inputStream.read()) != -1) {
//						fileOutStream.write(read);
//					}
//				} catch (IOException ioe) {
//					System.out.println("IO write Error");
//				} finally {
//					try {
//						if (inputStream != null) {
//							inputStream.close();
//						}
//						if (fileOutStream != null) {
//							fileOutStream.close();
//						}
//					} catch (IOException ioe2) {
//						System.out.println("IO close Error");
//					}
//				}
//			}
//		}
//	}

	/**
	 * 主要是拷貝UGC中用到的txt文件等到临时目录中。
	 * 
	 * @param srcPath
	 *            源文件的相對路徑
	 * @param targetPath
	 *            目标文件的相對路徑
	 * @param fileNames
	 *            文件的名稱集合，即需要拷貝的所有文件
	 */
	protected static void copyFileToTempFile(String srcPath, String targetPath,
			String... fileNames) {
		for (String fileName : fileNames) {
			// 需要保存的源文件的路徑。
			String srcFilePath = srcPath + fileName;
			URL url = Class.class.getResource(srcFilePath);

			// 保存的目標文件的路徑。
			String targetFilePath = targetPath + fileName;
			File outputFile = new File(targetFilePath);

			InputStream inputStream = null;
			FileOutputStream fileOutStream = null;
			if (targetFilePath != null) {
				try {
					inputStream = url.openStream();
					fileOutStream = new FileOutputStream(outputFile);
				} catch (IOException e) {
					System.out.println("File Not Found!");
					e.printStackTrace();
				}

				InputStreamReader reader = new InputStreamReader(inputStream);
				// 以行的方式来读取文件
				LineNumberReader lineReader = new LineNumberReader(reader);
				try {
					String str = null;
					while ((str = lineReader.readLine()) != null) {
						fileOutStream.write(str.getBytes());
					}
				} catch (Exception ex) {
					System.out.println("IO write Error");
				}

				finally {
					try {
						if (inputStream != null) {
							inputStream.close();
						}
						if (fileOutStream != null) {
							fileOutStream.close();
						}
						if (lineReader != null) {
							lineReader.close();
						}
					} catch (IOException ioe2) {
						System.out.println("IO close Error");
					}
				}
			}
		}
	}

	// protected static final double DOUBLE_MIN_VALUE =
	// -1.7976931348623157e+308;
	// protected static final double DOUBLE_MAX_VALUE = 1.7976931348623157e+308;

	// 警告：此处直接在Ｊａｖａ和Ｃ＋＋之间指针互调使用，谨慎使用。
	protected static Dataset toManageDataset(Workspace workspace,
			long datasetHandle) {
		if (workspace == null
				|| com.supermap.data.InternalHandle.getHandle(workspace) == 0
				|| datasetHandle == 0) {
			return null;
		}
		long datasourceHandle = DatasetNative.jni_GetDatasource(datasetHandle);
		String datasourceAlias = null;
		if (datasourceHandle != 0) {
			datasourceAlias = DatasourceNative.jni_GetAlias(datasourceHandle);
		}
		Datasource datasource = workspace.getDatasources().get(datasourceAlias);
		Dataset result = null;
		if (datasource != null) {
			result = toManageDataset(datasource, datasetHandle);
		}
		return result;
	}
	
	// 警告：此处直接在Ｊａｖａ和Ｃ＋＋之间指针互调使用，谨慎使用。
	// 注意：由于当一个数据集是子数据集时，并不存在于Java的datasource中，所以要通过取getChildDataset来获得
	private static Dataset toManageDataset(Datasource datasource,
			long datasetHandle) {
		if (datasource == null || InternalHandle.getHandle(datasource) == 0
				|| datasetHandle == 0) {
			return null;
		}
		String datasetName = DatasetNative.jni_GetName(datasetHandle);
		boolean isChild = false;
		long dvParentHandle = 0;
		boolean isVector = DatasetNative.jni_GetIsVector(datasetHandle);
		if (isVector) {
			dvParentHandle = DatasetVectorNative.jni_GetParentDataset(datasetHandle);
		}
		// 判断是否有父数据集
		if (dvParentHandle != 0) {
			// 若存在父数据集，则取出父数据集的名称。
			datasetName = DatasetNative.jni_GetName(dvParentHandle);
//			isChild = true;
		}
		Dataset result = datasource.getDatasets().get(datasetName);
		
		// modify by wangdy 11-5-3 
		// 暂时不支持子数据集
//		// 判断是否有子数据集。
//		if (isChild && result != null) {
//			result = ((DatasetVector) result).getChildDataset();
//		}
		return result;
	}
	
	/**
	 * 计算两条直线间的夹角
	 * @param line0Start 直线0的起点
	 * @param line0End 直线0的终点
	 * @param line1Start 直线1的起点
	 * @param line1End 直线1的终点
	 * @return 夹角 （0 ~ 180）
	 */
	public static float calcAngleWithLines(Point line0Start,Point line0End,Point line1Start,Point line1End){
		Point pA = new Point(line0End.getX()-line0Start.getX(), line0End.getY()-line0Start.getY());
		
		Point pB = new Point(line1End.getX()-line1Start.getX(), line1End.getY()-line1Start.getY());
		
		// A*B = x1*x2 + y1*y2 
		double AB = pA.getX()*pB.getX() + pA.getY()*pB.getY();
		// |A|*|B| = (x1*x1 + y1*y1)^(1/2) * (x2*x2 + y2*y2)^(1/2)
		double modAB = Math.sqrt(pA.getX()*pA.getX() + pA.getY()*pA.getY()) * Math.sqrt(pB.getX()*pB.getX() + pB.getY()*pB.getY());
		
		// cosQ = A*B / |A|*|B|
		double cosQ = AB / modAB;
		
		float Q = (float) (Math.acos(cosQ) * (180 / Math.PI));
		
		return Q;
	}
	
	/**
	 * 计算点串间的总距离
	 * @param points 点串
	 * @param prj 当前地图的投影坐标系
	 * @return
	 */
	public static double calcLength(Point2Ds points,PrjCoordSys prj){
		if(points.getCount()<2){
			return 0;
		}
		int count  = points.getCount();
		
		double[] x = new double[count];
		double[] y = new double[count];
		for(int i=0;i<count;i++){
			x[i] = points.getItem(i).getX();
			y[i] = points.getItem(i).getY();
		}
		return ToolkitNative.jni_CalcLength(x, y, prj.getHandle());
	}
	
	/**
	 * 计算点串间围为的地理面积
	 * @param points 点串
	 * @param prj 当前地图的投影坐标系
	 * @return
	 */
	public static double calcArea(Point2Ds points,PrjCoordSys prj){
		if(points.getCount()<3){
			return 0;
		}
		int count  = points.getCount();
		
		double[] x = new double[count];
		double[] y = new double[count];
		for(int i=0;i<count;i++){
			x[i] = points.getItem(i).getX();
			y[i] = points.getItem(i).getY();
		}
		return ToolkitNative.jni_CalcArea(x, y, prj.getHandle());
	}
	
	public static String getLastError() {
		return ToolkitNative.jni_GetLastError();
	}
	
	/**
	 * 计算三个点依次序构成的两条线段间的顺时针角度
	 * @param p1    第一个点
	 * @param p2    第二个点，即角的顶点
	 * @param p3    第三个点
	 * @return   返回夹角大小，单位为度
	 */
	public static double calcAngle(Point2D p1, Point2D p2, Point2D p3){
		double angle = 0;
		if (p1 != null && p2 != null && p3 != null) {
//			angle = ToolkitNative.jni_MeasureAngle(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
		
		
			double DTOR = 0.0174532925199432957692369077;

			double Ax = p2.getX() - p1.getX();
			double Ay = p2.getY() - p1.getY();
			double Bx = p3.getX() - p2.getX();
			double By = p3.getY() - p2.getY();

			double angeleCos = (Ax * Bx + Ay * By) / (Math.sqrt((Ax * Ax + Ay * Ay)) * Math.sqrt((Bx * Bx + By * By)));
			angle = Math.acos(angeleCos) * 1 / DTOR;

			// 判断两条线的位置关系
			double df = (p3.getX() - p1.getX()) * (p2.getY() - p1.getY())
					  - (p3.getY() - p1.getY()) * (p2.getX() - p1.getX());
			if (df > 0) // 右边
				angle = 180 + angle;
			else if (df < 0) // 左边
				angle = 180 - angle;
			else// 三个点共线
			{
				if (!((p2.getX() == p3.getX()) && (p1.getX() == p3.getX()))) {
					if (p1.getX() > p2.getX())
						angle = p3.getX() < p2.getX() ? 180 : 0;
					else
						angle = p3.getX() > p2.getX() ? 180 : 0;
				} else// 垂直情况
				{
					if (p1.getY() > p2.getY())
						angle = p3.getY() < p2.getY() ? 180 : 0;
					else
						angle = p3.getY() > p2.getY() ? 180 : 0;
				}
			}
		}
		return angle;
	}
}
