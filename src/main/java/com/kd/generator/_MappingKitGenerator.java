package com.kd.generator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.generator.MappingKitGenerator;
import com.jfinal.plugin.activerecord.generator.TableMeta;

public class _MappingKitGenerator extends  MappingKitGenerator{

	/**
	 * 没有主键或主键为空的类的处理方法
	 */
	protected String mappingMethodContentTemplateForNokey ="\t\tarp.addMapping(\"%s\" , %s.class);%n";
	
	public _MappingKitGenerator(String mappingKitPackageName, String mappingKitOutputDir) {
		super(mappingKitPackageName, mappingKitOutputDir);
	}
	
	@Override
	protected void genMappingMethod(List<TableMeta> tableMetas, StringBuilder ret) {
		ret.append(String.format(mappingMethodDefineTemplate));
		for (TableMeta tableMeta : tableMetas) {
			boolean isCompositPrimaryKey = tableMeta.primaryKey.contains(",");
			if (isCompositPrimaryKey)
				ret.append(String.format(compositeKeyTemplate, tableMeta.primaryKey));
			
			String add;
			
			if(StringUtils.isBlank(tableMeta.primaryKey)){
				add = String.format(mappingMethodContentTemplateForNokey, tableMeta.name, tableMeta.modelName);
			}else{
				add = String.format(mappingMethodContentTemplate, tableMeta.name, tableMeta.primaryKey, tableMeta.modelName);
			}
			
			
			ret.append(add);
		}
		ret.append(String.format("\t}%n"));
	}
}
