package com.yihuisoft.product.group.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yihuisoft.product.util.CommonCal;

public class ProductGroupCalculate {
	private static Logger logger=LoggerFactory.getLogger(ProductGroupTimeService.class);
	
	private static String pyexe;
	private static String pypmsrc;
	
	
	public static double calculateGroupSharpRatio(Integer productGroupId) {
    	//读取python配置文件
    	Properties prop=new Properties();
    	InputStream in = ProductGroupTimeService.class.getClassLoader().getResourceAsStream("python.properties");
    	try {
    		prop.load(in);
    		pyexe=prop.getProperty("PythonExePath").trim();
    		pypmsrc=prop.getProperty("PythonProductGroupPath").trim();
    		logger.info("Python路径:"+pyexe);
    	} catch (IOException e) {
  			logger.error("获取python配置文件出错:"+e.getMessage());
    	}
  		String exe_param=pypmsrc+"\\"+"GroupCalculateSharpeRatio"+".py";
 		logger.info("脚本文件路径:"+exe_param);
		ProcessBuilder pb=null;
 		pb=new ProcessBuilder(pyexe,exe_param,productGroupId.toString());
   		pb.directory(new File(pypmsrc));
   		Process proc=null;
   		double sharpRatio = 0d;
   		try {
  			proc=pb.start();
  			String res = readProcessOutput(proc);
  			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String result = "";
            result = input.readLine();
            String[] RiskAndYield = result.split(",");
            sharpRatio = CommonCal.calculateSharpRatio(Double.parseDouble(RiskAndYield[1]),0.0135,Double.parseDouble(RiskAndYield[0]));
    		logger.info(res);
    		proc.waitFor();
    	} catch (IOException e) {
    		logger.error("调用Python文件出错:"+e.getMessage());
    		return 0d;
   		} catch (InterruptedException e) {
   			logger.error("调用Python文件出错:"+e.getMessage());
   			return 0d;
   		}
    	logger.info("调用Python文件结束");
    	return sharpRatio;
    }
    
	private static String readProcessOutput(final Process process) {
		return read(process.getErrorStream(), System.out);
		//return read(process.getInputStream(), System.out);
	}

	private static String read(InputStream inputStream, PrintStream out) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
