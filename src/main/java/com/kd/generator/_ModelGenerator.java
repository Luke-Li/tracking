package com.kd.generator;

import com.jfinal.plugin.activerecord.generator.ModelGenerator;
import com.jfinal.plugin.activerecord.generator.TableMeta;

public class _ModelGenerator extends ModelGenerator{

	protected String classDefineTemplate =
			"/**%n" +
			" * %s%n" +
			" */%n" +
			"@SuppressWarnings(\"serial\")%n" +
			"public class %s extends %s<%s> {%n";
	
	public _ModelGenerator(String modelPackageName, String baseModelPackageName, String modelOutputDir) {
		super(modelPackageName, baseModelPackageName, modelOutputDir);
	}

	@Override
	protected void genClassDefine(TableMeta tableMeta, StringBuilder ret) {
		ret.append(String.format(classDefineTemplate,tableMeta.remarks, tableMeta.modelName, tableMeta.baseModelName, tableMeta.modelName));
	}
}
