package com.kds.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class common {
	public static String getCPUSerial() {   
        String str = "", strCPU = "", cpuAddress = "0000000000000000";    
        try {     
            //读取CPU信息     
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");      
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());    
            LineNumberReader input = new LineNumberReader(ir);    
            //查找CPU序列号   
            for (int i = 1; i < 100; i++) {   
                str = input.readLine();   
                if (str != null) {   
                    //查找到序列号所在行   
                    if (str.indexOf("Serial") > -1) {   
                        //提取序列号   
                        strCPU = str.substring(str.indexOf(":") + 1,   
                        str.length());   
                        //去空格   
                        cpuAddress = strCPU.trim();   
                        break;   
                    }   
                } else {   
                    //文件结尾   
                    break;   
                }   
            }   
        } catch (Exception ex) {   
            //赋予默认值   
            ex.printStackTrace();   
        }   
        return cpuAddress;   
   } 
	 /**
	  * 获取单个文件的MD5值！

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
	  } catch (Exception e) {
	   e.printStackTrace();
	   return null;
	  }
	  BigInteger bigInt = new BigInteger(1, digest.digest());
	  return bigInt.toString(16);
	 }
	 /**
	  * 获取文件夹中文件的MD5值
	  * 
	  * @param file
	  * @param listChild
	  *            ;true递归子目录中的文件
	  * @return
	  */
	 public static Map<String, String> getDirMD5(File file, boolean listChild) {
	  if (!file.isDirectory()) {
	   return null;
	  }
	  Map<String, String> map = new HashMap<String, String>();
	  String md5;
	  File files[] = file.listFiles();
	  for (int i = 0; i < files.length; i++) {
	   File f = files[i];
	   if (f.isDirectory() && listChild) {
	    map.putAll(getDirMD5(f, listChild));
	   } else {
	    md5 = getFileMD5(f);
	    if (md5 != null) {
	     map.put(f.getPath(), md5);
	    }
	   }
	  }
	  return map;
	 }
		/**
		 * 对字符串md5加密
		 *
		 * @param str
		 * @return
		 */
		public static String getMD5(String str) {
				String md5val="";
		        // 生成一个MD5加密计算摘要
		        MessageDigest md;
				try {
					md = MessageDigest.getInstance("MD5");
			        // 计算md5函数
			        md.update(str.getBytes());
			        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			        md5val= new BigInteger(1, md.digest()).toString(16);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return md5val;
		}
		
		
		int closeSrceenTime=3;
		int PirState=0;
		int lightHz =50;
		public static void loadSystemConfigFile(String configFile){
			
		}
		public static void SaveSystemConfigFile(){
			
		}
}
