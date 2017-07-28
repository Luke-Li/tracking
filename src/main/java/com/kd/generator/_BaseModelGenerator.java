package com.kd.generator;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.generator.BaseModelGenerator;
import com.jfinal.plugin.activerecord.generator.ColumnMeta;
import com.jfinal.plugin.activerecord.generator.TableMeta;

public class _BaseModelGenerator extends BaseModelGenerator{
	
	public _BaseModelGenerator(String baseModelPackageName, String baseModelOutputDir) {
		super(baseModelPackageName, baseModelOutputDir);
	}

	@Override
	protected void genBaseModelContent(TableMeta tableMeta) {
		StringBuilder ret = new StringBuilder();
		genPackage(ret);
		genImport(ret);
		genClassDefine(tableMeta, ret);
		
		genTableName(tableMeta,ret);
		
		for (ColumnMeta columnMeta : tableMeta.columnMetas) {
			genSetMethodName(columnMeta, ret);
			genGetMethodName(columnMeta, ret);
		}
		ret.append(String.format("}%n"));
		tableMeta.baseModelContent = ret.toString();
	}
	
	
	protected String setterTemplate =
			"\t/**%n" +
			"\t * %s%n" +
			"\t */%n" +
			"\tpublic void %s(%s %s) {%n" +
				"\t\tset(\"%s\", %s);%n" +
			"\t}%n%n";
	
	@Override
	protected void genSetMethodName(ColumnMeta columnMeta, StringBuilder ret) {
		String setterMethodName = "set" + StrKit.firstCharToUpperCase(columnMeta.attrName);
		// 如果 setter 参数名为 java 语言关键字，则添加下划线前缀 "_"
		String argName = javaKeyword.contains(columnMeta.attrName) ? "_" + columnMeta.attrName : columnMeta.attrName;
		String setter = String.format(setterTemplate, columnMeta.remarks,setterMethodName, columnMeta.javaType, argName, columnMeta.name, argName);
		ret.append(setter);
	}
	
	protected String getterTemplate =
			"\t/**%n" +
			"\t * %s%n" +
			"\t */%n" +
			"\tpublic %s %s() {%n" +
				"\t\treturn get(\"%s\");%n" +
			"\t}%n%n";
	
	@Override
	protected void genGetMethodName(ColumnMeta columnMeta, StringBuilder ret) {
		String getterMethodName = "get" + StrKit.firstCharToUpperCase(columnMeta.attrName);
		String getter = String.format(getterTemplate,columnMeta.remarks, columnMeta.javaType, getterMethodName, columnMeta.name);
		ret.append(getter);
	}
	
	protected String tableNameTemplate =	
	"\t/**%n"+
	"\t * 表名%n"+
	"\t */%n"+
	"\tpublic static final String TableName = \"%s\";%n%n";
	
	private void genTableName(TableMeta tableMeta, StringBuilder ret) {
		ret.append(String.format(tableNameTemplate, tableMeta.name));
	}
}
