package com.supermap.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.util.Log;

class Decompressor {
//	public static void Unzip(String zipFile, String targetDir) {
//		int BUFFER = 4096; //���ﻺ��������ʹ��4KB��
//		String strEntry; //����ÿ��zip����Ŀ����
//
//		try {
//			BufferedOutputStream dest = null; //���������
//			FileInputStream fis = new FileInputStream(zipFile);
//			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
//			ZipEntry entry; //ÿ��zip��Ŀ��ʵ��
//	
//			while ((entry = zis.getNextEntry()) != null) {
//	
//				try {
//					Log.i("Unzip: ","="+ entry);
//					int count; 
//					byte data[] = new byte[BUFFER];
//					strEntry = entry.getName();
//			
//					File entryFile = new File(targetDir + strEntry);
//					File entryDir = new File(entryFile.getParent());
//					if (!entryDir.exists()) {
//					entryDir.mkdirs();
//					}
//			
//					FileOutputStream fos = new FileOutputStream(entryFile);
//					dest = new BufferedOutputStream(fos, BUFFER);
//					while ((count = zis.read(data, 0, BUFFER)) != -1) {
//					dest.write(data, 0, count);
//					}
//					dest.flush();
//					dest.close();
//				} catch (Exception ex) {
//					ex.printStackTrace();
//				}
//			}
//			zis.close();
//		} catch (Exception cwj) {
//			cwj.printStackTrace();
//		}
//	}
	
	/** 
     * ��ѹһ��ѹ���ĵ� ��ָ��λ�� 
     * @param zipFileString ѹ���������� 
     * @param outPathString ָ����·�� 
     * @throws Exception 
     */  
    public static void UnZipFolder(String zipFile, String targetDir){  
        android.util.Log.v("XZip", "UnZipFolder(String, String)");  
        java.util.zip.ZipInputStream inZip;
		try {
			
			inZip = new java.util.zip.ZipInputStream(new java.io.FileInputStream(zipFile));
		
	        java.util.zip.ZipEntry zipEntry;  
	        String szName = "";  
	          
				while ((zipEntry = inZip.getNextEntry()) != null) {  
				    szName = zipEntry.getName();  
				  
				    if (zipEntry.isDirectory()) {  
				   
				        java.io.File folder = new java.io.File(targetDir + java.io.File.separator + szName);  
				        folder.mkdirs();  
				  
				    } else {  
				  
				        java.io.File file = new java.io.File(targetDir + java.io.File.separator + szName);  
				        file.createNewFile();  
				        // get the output stream of the file  
				        java.io.FileOutputStream out = new java.io.FileOutputStream(file);  
				        int len;  
				        byte[] buffer = new byte[1024];  
				        while ((len = inZip.read(buffer)) != -1) {  
				            out.write(buffer, 0, len);  
				            out.flush();  
				        }  
				        out.close();  
				    }  
				}
			inZip.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}              
    } 
      
}
