package com.kd.generator;

import javax.sql.DataSource;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;
import com.kd.consts.GeneralConst;

/**
 * 在数据库表有任何变动时，运行一下 main 方法，极速响应变化进行代码重构
 */
public class _JFinalTrackGenerator {

	public static DataSource createC3p0Plugin() {
//		String url = PropKit.use("config.properties").get("dbUrl");
//		String username = PropKit.use("config.properties").get("user");
//		String password = PropKit.use("config.properties").get("password");
		String username = "root";
		String password = "password";

		DruidPlugin c3p0Plugin = new DruidPlugin(GeneralConst.TRACKURL, username, password);
		c3p0Plugin.start();
		return c3p0Plugin.getDataSource();
	}

	public static void main(String[] args) {
		// base model 所使用的包名
		String baseModelPackageName = "com.kd.model.track.base";
		// base model 文件保存路径
		String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/kd/model/track/base";

		// model 所使用的包名 (MappingKit 默认使用的包名)
		String modelPackageName = "com.kd.model.track";
		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/kd/model/track";

		// 数据源
		DataSource dataSource = createC3p0Plugin();

		// 创建生成器
		Generator gernerator = new Generator(dataSource,
				new _BaseModelGenerator(baseModelPackageName, baseModelOutputDir),
				new _ModelGenerator(modelPackageName, baseModelPackageName, modelOutputDir));
		_MetaBuilder metaBuilder = new _MetaBuilder(dataSource);

		gernerator.setMetaBuilder(metaBuilder);
		gernerator.setMappingKitGenerator(new _MappingKitGenerator(modelPackageName, modelOutputDir));
		// 添加不需要生成的表名
		gernerator.addExcludedTable("adv");
		// 设置是否在 Model 中生成 dao 对象
		gernerator.setGenerateDaoInModel(true);
		// 设置是否生成字典文件
		gernerator.setGenerateDataDictionary(false);
		// 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为
		// "User"而非 OscUser
		gernerator.setRemovedTableNamePrefixes("t_");
		// 设置需要限定的表名前缀
		metaBuilder.setTableNamePrefixes("");
		// 生成
		gernerator.generate();
	}
}
