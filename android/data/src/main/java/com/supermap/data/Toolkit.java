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
	// ����Float.MIN_VALUE��Double.MIN_VALUE��ֵ�����޽ӽ�0����ֵ������������Ҫ�Ľӽ��������ֵ��
	// ���ڴ˴����¶���doulbe��float���͵������Сֵ��
	protected static final double DBL_MAX_VALUE = 1.7976931348623157e+308;

	protected static final double DBL_MIN_VALUE = -1.7976931348623157e+308;

	protected static final float FLT_MAX_VALUE = (float) 3.4028235e+038;

	protected static final float FLT_MIN_VALUE = (float) -3.4028235e+038;
	
	/**
	 * ��ָ���ļ��ζ���Ļ��Ƴ�ͼƬ
	 * @param geometry ���ζ���
	 * @param resources ��������Դ��
	 * @param fileName ��ͼ�ļ���·��
	 * @param width ��
	 * @param height ��
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
	 * �����Ż��Ƶ�ָ����λͼ��
	 * @param geometry ���ζ���
	 * @param resources ��Դ��
	 * @param bmp ָ�����Ƶ�λͼ
	 * @return
	 */ // ����ӿ���ʱ�������⣬�Ͳ������� 20120726 liucq
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
	 * �ж�һ��������ֵ�Ƿ�Ϊ0,ʹ���û����õ��жϾ���
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
	 * �жϸ���ֵ�Ƿ�Ϊ0,ʹ�ô������
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
	 * �жϸ���ֵ�Ƿ�Ϊ0,ʹ�ô������
	 * 
	 * @param value
	 *            double
	 * @param maxEqualPrecision
	 *            double
	 * @param minEqualPrecision
	 *            double
	 * @return boolean modified by liyj �޸Ĳ���˳��
	 */
	public static boolean isZero(double value, double minPrecision,
			double maxPrecision) {
		boolean result = value >= minPrecision && value <= maxPrecision;
		return result;
	}

	/**
	 * ��ָ��������Դ���н�������ͨ����������ʹSDB����Դռ�õĴ洢�ռ��С
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
		// ��������Դ������ɵ�����Դһ�Σ����潫�������ݼ�����һ��
		if (datasource.getDatasets().getCount() > 0) {
			datasource.getDatasets().reset();
		}
		return bResult;
	}
	
	/**
	 * �������ݼ����ͻ�ȡ�����ݼ�����֧�ֵı�����������
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


	// 7Zip��ѹ
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
	 * add by xuzw ��������UGdouble *��MapControl�л��õ�
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
	 * ��String��ָ�����ַ�����������
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
	 * ���ַ�����ָ���ķָ���ָ�
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
			// �����Vector�Ͳ���ʹ�ã���������դ�����ݵı�������
			if (encodeType.equals(EncodeType.DCT)
					|| encodeType.equals(EncodeType.LZW)
					|| encodeType.equals(EncodeType.SGL)) {
				result = false;
			}
		} else {
			// �������Vector�Ͳ���ʹ�ã���������Vector���ݵı�������
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
//	 * ��Ҫ�ǿ�ؐUGC���õ�����άͼƬ����ʱĿ¼�С�
//	 * 
//	 * @param picturePath
//	 *            Դ�ļ�������·��
//	 * @param targetPath
//	 *            Ŀ���ļ�������·��
//	 * @param pictrueNames
//	 *            �DƬ�����Q���ϣ�����Ҫ��ؐ�����ЈDƬ��
//	 */
//	protected static void copyPictureToTempFile(String picturePath,
//			String targetPath, String... pictrueNames) {
//		for (String pictureName : pictrueNames) {
//			// ��Ҫ�����Դ�ļ���·����
//			String srcFilePath = picturePath + pictureName;
//			URL url = Toolkit.class.getResource(srcFilePath);
//			if (url == null) {
//				System.out.println("null");
//			}
//
//			// add by xuzw 2010-01-12 ��ӶԲ���ϵͳ���жϣ���Windows�����Ļ���Ŀ¼��Ҫ��һ��"/"
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
	 * ��Ҫ�ǿ�ؐUGC���õ���txt�ļ��ȵ���ʱĿ¼�С�
	 * 
	 * @param srcPath
	 *            Դ�ļ�������·��
	 * @param targetPath
	 *            Ŀ���ļ�������·��
	 * @param fileNames
	 *            �ļ������Q���ϣ�����Ҫ��ؐ�������ļ�
	 */
	protected static void copyFileToTempFile(String srcPath, String targetPath,
			String... fileNames) {
		for (String fileName : fileNames) {
			// ��Ҫ�����Դ�ļ���·����
			String srcFilePath = srcPath + fileName;
			URL url = Class.class.getResource(srcFilePath);

			// �����Ŀ���ļ���·����
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
				// ���еķ�ʽ����ȡ�ļ�
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

	// ���棺�˴�ֱ���ڣʣ����ͣã���֮��ָ�뻥��ʹ�ã�����ʹ�á�
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
	
	// ���棺�˴�ֱ���ڣʣ����ͣã���֮��ָ�뻥��ʹ�ã�����ʹ�á�
	// ע�⣺���ڵ�һ�����ݼ��������ݼ�ʱ������������Java��datasource�У�����Ҫͨ��ȡgetChildDataset�����
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
		// �ж��Ƿ��и����ݼ�
		if (dvParentHandle != 0) {
			// �����ڸ����ݼ�����ȡ�������ݼ������ơ�
			datasetName = DatasetNative.jni_GetName(dvParentHandle);
//			isChild = true;
		}
		Dataset result = datasource.getDatasets().get(datasetName);
		
		// modify by wangdy 11-5-3 
		// ��ʱ��֧�������ݼ�
//		// �ж��Ƿ��������ݼ���
//		if (isChild && result != null) {
//			result = ((DatasetVector) result).getChildDataset();
//		}
		return result;
	}
	
	/**
	 * ��������ֱ�߼�ļн�
	 * @param line0Start ֱ��0�����
	 * @param line0End ֱ��0���յ�
	 * @param line1Start ֱ��1�����
	 * @param line1End ֱ��1���յ�
	 * @return �н� ��0 ~ 180��
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
	 * ����㴮����ܾ���
	 * @param points �㴮
	 * @param prj ��ǰ��ͼ��ͶӰ����ϵ
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
	 * ����㴮��ΧΪ�ĵ������
	 * @param points �㴮
	 * @param prj ��ǰ��ͼ��ͶӰ����ϵ
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
	 * ���������������򹹳ɵ������߶μ��˳ʱ��Ƕ�
	 * @param p1    ��һ����
	 * @param p2    �ڶ����㣬���ǵĶ���
	 * @param p3    ��������
	 * @return   ���ؼнǴ�С����λΪ��
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

			// �ж������ߵ�λ�ù�ϵ
			double df = (p3.getX() - p1.getX()) * (p2.getY() - p1.getY())
					  - (p3.getY() - p1.getY()) * (p2.getX() - p1.getX());
			if (df > 0) // �ұ�
				angle = 180 + angle;
			else if (df < 0) // ���
				angle = 180 - angle;
			else// �����㹲��
			{
				if (!((p2.getX() == p3.getX()) && (p1.getX() == p3.getX()))) {
					if (p1.getX() > p2.getX())
						angle = p3.getX() < p2.getX() ? 180 : 0;
					else
						angle = p3.getX() > p2.getX() ? 180 : 0;
				} else// ��ֱ���
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
