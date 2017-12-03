package com.gaofen.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author lt
 *
 */
public class MD5Util {
	
	private MD5Util(){
		
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public final static String getMD5(String s) {
		if(s == null || s == ""){
			return "";
		}else{
			try {
				byte[] btInput = s.getBytes();
				MessageDigest mdInst = MessageDigest.getInstance("MD5");
				mdInst.update(btInput);
				byte[] md = mdInst.digest();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < md.length; i++) {
					int val = ((int) md[i]) & 0xff;
					if (val < 16) {
						sb.append("0");
					}
					sb.append(Integer.toHexString(val));
				}
				return sb.toString();
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 根据文件计算出文件的MD5
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}

		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());

		return bigInt.toString(16);
	}

	public static String getMultipartFileMD5(MultipartFile file) {
		if (file==null) {
			return null;
		}

		MessageDigest digest = null;
		InputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = file.getInputStream();
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());

		return bigInt.toString(16);
	}

	public static String getByteMD5(byte[] bytes) {
		if (bytes==null) {
			return null;
		}
		BigInteger bigInt = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(bytes);
			bigInt = new BigInteger(1, digest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
//		byte[] destBytes=new byte[1024];
//		System.arraycopy(bytes, 0, destBytes, 0, 1024);


		return bigInt.toString(16);
	}

}
