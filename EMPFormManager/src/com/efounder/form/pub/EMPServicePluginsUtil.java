/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * @author prayer
 * @version 1.0
 */
package com.efounder.form.pub;

import java.util.List;
import java.util.Map;

import com.efounder.component.EMPComponentStub;
import com.efounder.component.EMPComposeFormInfo;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * @author prayer
 * @version 1.0
 */
public class EMPServicePluginsUtil {

	/**
	 * 获取插件列表服务
	 * @param formInfo
	 * @param compScope
	 * @return
	 */
	public static EMPComponentStub loadPluginsListService(EMPComposeFormInfo formInfo, String compScope) {
		Map<String, EMPComponentStub>                 serverMap = formInfo.getDataContainerStub().getRefCompStubMap();
		EMPComponentStub                      servicePluginStub = new EMPComponentStub();
		
		//获取作用范围内的插件列表
		if(compScope.equals(EMPComponentStub.prepare)) {
			servicePluginStub = (EMPComponentStub) serverMap.get(EMPComponentStub.prepareKey);
		} else if(compScope.equals(EMPComponentStub.execute)) {
			servicePluginStub = (EMPComponentStub) serverMap.get(EMPComponentStub.processKey);
		} else if(compScope.equals(EMPComponentStub.finish)) {
			servicePluginStub = (EMPComponentStub) serverMap.get(EMPComponentStub.finishKey);
		}
		return servicePluginStub;
	}
	
	/**
	 * 获取插件列表
	 * @param formInfo
	 * @param compScope
	 * @return
	 */
	public static List<EMPComponentStub> loadPluginsList(EMPComposeFormInfo formInfo, String compScope) {
		Map<String, EMPComponentStub>                 serverMap = formInfo.getDataContainerStub().getRefCompStubMap();
		Map<String, EMPComponentStub>                pluginsMap = null;
		List<EMPComponentStub>                      pluginsList = null;
		EMPComponentStub                      servicePluginStub = new EMPComponentStub();
		
		//0.获取作用范围内的插件列表
		if(compScope.equals(EMPComponentStub.prepare)) {
			servicePluginStub = (EMPComponentStub) serverMap.get(EMPComponentStub.prepareKey);
		} else if(compScope.equals(EMPComponentStub.execute)) {
			servicePluginStub = (EMPComponentStub) serverMap.get(EMPComponentStub.processKey);
		} else if(compScope.equals(EMPComponentStub.finish)) {
			servicePluginStub = (EMPComponentStub) serverMap.get(EMPComponentStub.finishKey);
		}
		//1.获取插件服务
		pluginsMap = servicePluginStub.getRefCompStubMap();
		//2.获取插件列表
		pluginsList = (List) pluginsMap.get(EMPComponentStub.servicePluginList);
		return pluginsList;
	}
}
