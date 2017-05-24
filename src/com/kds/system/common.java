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
            //��ȡCPU��Ϣ     
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");      
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());    
            LineNumberReader input = new LineNumberReader(ir);    
            //����CPU���к�   
            for (int i = 1; i < 100; i++) {   
                str = input.readLine();   
                if (str != null) {   
                    //���ҵ����к�������   
                    if (str.indexOf("Serial") > -1) {   
                        //��ȡ���к�   
                        strCPU = str.substring(str.indexOf(":") + 1,   
                        str.length());   
                        //ȥ�ո�   
                        cpuAddress = strCPU.trim();   
                        break;   
                    }   
                } else {   
                    //�ļ���β   
                    break;   
                }   
            }   
        } catch (Exception ex) {   
            //����Ĭ��ֵ   
            ex.printStackTrace();   
        }   
        return cpuAddress;   
   } 
	 /**
	  * ��ȡ�����ļ���MD5ֵ��

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
	  * ��ȡ�ļ������ļ���MD5ֵ
	  * 
	  * @param file
	  * @param listChild
	  *            ;true�ݹ���Ŀ¼�е��ļ�
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
		 * ���ַ���md5����
		 *
		 * @param str
		 * @return
		 */
		public static String getMD5(String str) {
				String md5val="";
		        // ����һ��MD5���ܼ���ժҪ
		        MessageDigest md;
				try {
					md = MessageDigest.getInstance("MD5");
			        // ����md5����
			        md.update(str.getBytes());
			        // digest()���ȷ������md5 hashֵ������ֵΪ8Ϊ�ַ�������Ϊmd5 hashֵ��16λ��hexֵ��ʵ���Ͼ���8λ���ַ�
			        // BigInteger������8λ���ַ���ת����16λhexֵ�����ַ�������ʾ���õ��ַ�����ʽ��hashֵ
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
