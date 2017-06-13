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

}
