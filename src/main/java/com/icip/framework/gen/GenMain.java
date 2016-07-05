package com.icip.framework.gen;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 生成MAPPER、MODEL、XML
 * 
 * @author lenovo
 * 
 */
public class GenMain {
	
	private static final Logger logger = Logger.getLogger(GenMain.class);
	
	/**
	 * mybatis生成函数，主要修改generatorConfig.xml
	 * 
	 */
	public static void main(String[] args) {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		String genCfg = "/generatorConfig.xml";
		File configFile = new File(GenMain.class.getResource(genCfg).getFile());
		System.err.println(configFile.getPath());
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = null;
		try {
			config = cp.parseConfiguration(configFile);
		} catch (IOException e) {
			logger.error("-------->", e);
			//e.printStackTrace();
		} catch (XMLParserException e) {
			logger.error("-------->", e);
			//e.printStackTrace();
		}
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = null;
		try {
			myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		} catch (InvalidConfigurationException e) {
			logger.error("-------->", e);
			//e.printStackTrace();
		}
		try {
			myBatisGenerator.generate(null);
		} catch (SQLException e) {
			logger.error("-------->", e);
			//e.printStackTrace();
		} catch (IOException e) {
			logger.error("-------->", e);
			//e.printStackTrace();
		} catch (InterruptedException e) {
			logger.error("-------->", e);
			//e.printStackTrace();
		}
		System.err.println("success");

	}
}