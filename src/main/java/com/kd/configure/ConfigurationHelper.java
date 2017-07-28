package com.kd.configure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.kd.consts.GeneralConst;
import com.kd.model.track._MappingKit;

public class ConfigurationHelper {
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationHelper.class);
	public static void init(){
		JFinalConfig();
	}

	public static void JFinalConfig(){
		try{
			logger.info("Start init the database!");
	    DruidPlugin dp = new  DruidPlugin(GeneralConst.TRACKURL,"root","password");
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		_MappingKit.mapping(arp);
		// 与web环境唯一的不同是要手动调用一次相关插件的start()方法
		dp.start();
		arp.start();
		}catch(Exception e){
			logger.error("init database error: " + e);
		}
	}


}
