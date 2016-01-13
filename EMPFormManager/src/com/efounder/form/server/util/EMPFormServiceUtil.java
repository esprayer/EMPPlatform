package com.efounder.form.server.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.component.EMPComponentStub;
import com.efounder.component.EMPComposeFormInfo;
import com.efounder.form.persistence.formservice.bean.SYS_DBFORM;
import com.efounder.form.persistence.formservice.mapper.SYS_DBFORMMapper;
import com.efounder.form.pub.EMPServicePluginsUtil;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;
import com.pub.util.FileUtil;

@Repository
public class EMPFormServiceUtil {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SYS_DBFORMMapper dbFormMapper;
	
	//------------------------------------------------------------------------------------------------
	//描述: 保存插件到服务中，生成本地文件并保存到数据库总
	//      插件对象为EMPComponentStub，数据结构如下：
	//      EMPComponentStub 放到ArrayList中，ArrayList分为三种：准备阶段、执行阶段、完成阶段
	//      将ArrayList放到对应的HashMap中，再将HashMap放到EMPComponentStub的refCompStubMap对象中
	//      将EMPComponentStub放到EMPComposeFormInfo的DataContainerStub中，这样一个服务文件就形成。
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	public boolean saveFormService(HttpServletRequest request, EMPComponentStub po, String serviceID, String BBZD_BH) {
		Object                 object = loadFormService(request, BBZD_BH);
		EMPComposeFormInfo   formInfo = new EMPComposeFormInfo();
		EMPComponentStub     formStub = new EMPComponentStub();
		String            servletPath = request.getRealPath("") + "\\WEB-INF\\ServiceSpace";
		String               filePath = servletPath + "\\" + BBZD_BH + ".xml";
		File                  xmlFile = FileUtil.getFile(filePath); 
		StubObject               stub = null;
		
		try {
			
			Vector vector = (Vector) PackageStub.getContenetList().get("bizservicecomponentpalettes");
			for(int i = 0; i < vector.size(); i++) {
				stub = (StubObject) vector.get(i);
				if(stub.getString("id", "").equals(serviceID)) {
					formStub.setCompID(stub.getString("id", ""));
					formStub.setCompName(stub.getString("caption", ""));
					formStub.setCompClazz(stub.getString("compClazz", ""));
					break;
				}
			}
			
			if(object == null) {		
				SYS_DBFORM dbForm = dbFormMapper.load(BBZD_BH);				
				formInfo = createNewFormService(po, dbForm, formStub);
//				formInfo.setDataContainerStub(formStub);
			} else {
				formInfo = (EMPComposeFormInfo) object;
				formInfo = updateFormService(formInfo, po, formStub);
			}
			
			FileOutputStream fileOutStm = new FileOutputStream(xmlFile);
			BufferedOutputStream bufferOUtStm = new BufferedOutputStream(fileOutStm);
			XMLEncoder xmlEncoder = new XMLEncoder(bufferOUtStm);
			xmlEncoder.writeObject(formInfo);
			xmlEncoder.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 插件上下移动，保存插件到服务中，生成本地文件并保存到数据库总
	//      插件对象为EMPComponentStub，数据结构如下：
	//      EMPComponentStub 放到ArrayList中，ArrayList分为三种：准备阶段、执行阶段、完成阶段
	//      将ArrayList放到对应的HashMap中，再将HashMap放到EMPComponentStub的refCompStubMap对象中
	//      将EMPComponentStub放到EMPComposeFormInfo的DataContainerStub中，这样一个服务文件就形成。
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	public boolean saveFormService(HttpServletRequest request, String BBZD_BH, String compID, String compScope, String order) {
		Object                  object = loadFormService(request, BBZD_BH);
		EMPComposeFormInfo    formInfo = null;
		String             servletPath = request.getRealPath("") + "\\WEB-INF\\ServiceSpace";
		String                filePath = servletPath + "\\" + BBZD_BH + ".xml";
		File                   xmlFile = FileUtil.getFile(filePath); 
		
		try {
			
			formInfo = (EMPComposeFormInfo) object;
			formInfo = updateFormService(formInfo, compID, compScope, order);
			
			FileOutputStream fileOutStm = new FileOutputStream(xmlFile);
			BufferedOutputStream bufferOUtStm = new BufferedOutputStream(fileOutStm);
			XMLEncoder xmlEncoder = new XMLEncoder(bufferOUtStm);
			xmlEncoder.writeObject(formInfo);
			xmlEncoder.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 形成新的服务文件
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	private EMPComposeFormInfo createNewFormService(EMPComponentStub po, SYS_DBFORM dbForm, EMPComponentStub builderStub) {
		EMPComposeFormInfo                                    formInfo = new EMPComposeFormInfo();		
		EMPComponentStub                             finishServiceStub = new EMPComponentStub(builderStub);
		EMPComponentStub                            processServiceStub = new EMPComponentStub(builderStub);
		EMPComponentStub                            prepareServiceStub = new EMPComponentStub(builderStub);
		ArrayList<EMPComponentStub>                          arrayList = new ArrayList<EMPComponentStub>();
		EMPComponentStub                                   serviceStub = new EMPComponentStub();
		EMPComponentStub                         newServicePluginsStub = new EMPComponentStub();
		
		
		//0.形成插件对象，插件编号需要再加上索引位置，因为一个插件可以添加多次，所以需要加工一下
		newServicePluginsStub.setCompID(po.getCompID() + "0");
		newServicePluginsStub.setCompName(po.getCompName());
		newServicePluginsStub.setCompClazz(po.getCompClazz());
		newServicePluginsStub.setCompScope(po.getCompScope());
		newServicePluginsStub.setChildList(po.getChildList());
		
		//1.形成EMPComposeFormInfo的DataContainerStub对象
		formInfo.setDataContainerStub(serviceStub);
		if(serviceStub.getRefCompStubMap() == null) {
			serviceStub.setRefCompStubMap(new HashMap());
		}
		
		if(serviceStub.getChildList() == null) {
			serviceStub.setChildList(new ArrayList());
		}
		//2.形成各个阶段的插件列表
		serviceStub.getRefCompStubMap().put(EMPComponentStub.prepareKey, prepareServiceStub);
		serviceStub.getRefCompStubMap().put(EMPComponentStub.processKey, processServiceStub);
		serviceStub.getRefCompStubMap().put(EMPComponentStub.finishKey, finishServiceStub);
//		serviceStub.getRefCompStubMap().put(EMPComponentStub.builderKey, builderStub);
		
		prepareServiceStub.setRefCompStubMap(new HashMap());
		processServiceStub.setRefCompStubMap(new HashMap());
		finishServiceStub.setRefCompStubMap(new HashMap());
		
		prepareServiceStub.getRefCompStubMap().put(EMPComponentStub.servicePluginList, new ArrayList());
		processServiceStub.getRefCompStubMap().put(EMPComponentStub.servicePluginList, new ArrayList());
		finishServiceStub.getRefCompStubMap().put(EMPComponentStub.servicePluginList, new ArrayList());
		
//		serviceStub.getChildList().add(builderStub);
		serviceStub.getChildList().add(prepareServiceStub);
		serviceStub.getChildList().add(processServiceStub);
		serviceStub.getChildList().add(finishServiceStub);
		
		//3.取出插件列表
		if(po.getCompScope().equals("prepare")) {
			arrayList = (ArrayList<EMPComponentStub>) prepareServiceStub.getRefCompStubMap().get(EMPComponentStub.servicePluginList);
		} else if(po.getCompScope().equals("execute")) {
			arrayList = (ArrayList<EMPComponentStub>) processServiceStub.getRefCompStubMap().get(EMPComponentStub.servicePluginList);
		} else if(po.getCompScope().equals("finish")) {
			arrayList = (ArrayList<EMPComponentStub>) finishServiceStub.getRefCompStubMap().get(EMPComponentStub.servicePluginList);
		}
		
		//4.形成插件ArrayList,并将插件放到ArrayList中
		arrayList.add(newServicePluginsStub);
		formInfo.setFormID(dbForm.getBBZD_BH());
		formInfo.setFormName(dbForm.getBBZD_MC());
		return formInfo;
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务文件已经存在，更新，order为“向上移动”、“向下移动”标识，如果移动的话，只是修改插件在对应的ArrayList中的索引位置
	//      如果order为null则说明是修改插件的作用范围，则需要删除在原先的位置，并重新保存
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	private EMPComposeFormInfo updateFormService(EMPComposeFormInfo formInfo, EMPComponentStub po, EMPComponentStub builderStub) {		
		Map<String, EMPComponentStub>                 serverMap = formInfo.getDataContainerStub().getRefCompStubMap();
		Map/*<String, EMPComponentStub>*/                pluginsMap = null;
		List<EMPComponentStub>                      pluginsList = null;
		EMPComponentStub                                   stub = null;
		EMPComponentStub                      servicePluginStub = new EMPComponentStub();
		int                                        pluginsIndex = 0;
		
		//0.获取作用范围内的插件列表
		if(po.getCompScope().equals(EMPComponentStub.prepare)) {
			servicePluginStub = (EMPComponentStub) serverMap.get(EMPComponentStub.prepareKey);
		} else if(po.getCompScope().equals(EMPComponentStub.execute)) {
			servicePluginStub = (EMPComponentStub) serverMap.get(EMPComponentStub.processKey);
		} else if(po.getCompScope().equals(EMPComponentStub.finish)) {
			servicePluginStub = (EMPComponentStub) serverMap.get(EMPComponentStub.finishKey);
		}
		//1.获取插件服务
		pluginsMap = servicePluginStub.getRefCompStubMap();
		//2.获取插件列表
		pluginsList = (List) pluginsMap.get(EMPComponentStub.servicePluginList);
		//3.新添加的插件在服务执行范围列表不存在
		if(pluginsList == null || pluginsList.size() == 0) {
			pluginsList = new ArrayList<EMPComponentStub>();
			po.setCompID(po.getCompID() + "0");
			pluginsList.add(po);
			return formInfo;
		}
		//4.新添加的插件在服务执行范围列表不存在
		for(int i = 0; i < pluginsList.size(); i++) {
			stub = pluginsList.get(i);
			if(stub.getCompClazz().equals(po.getCompClazz())) {
				pluginsIndex++;
			}
		}
		po.setCompID(po.getCompID() + pluginsIndex);
		pluginsList.add(po);
		return formInfo;
//		
//		//2.获取插件作用范围
//		compScope = compId.split("-")[0];
//		
//
//		arrayList = (ArrayList<EMPComponentStub>)pluginsMap.get(compScope);
//
//		for(int i = 0; i < arrayList.size(); i++) {
//			if(arrayList.get(i).getCompID().equals(po.getCompID())) {
//				//如果修改作用范围则获取最新的作用范围中的插件ArrayList
//				newArrayList = (ArrayList<EMPComponentStub>)pluginsMap.get(po.getCompScope());
//				//如果切换作用范围，且最新的作用范围插件列表为空，则初始化
//				if(newArrayList == null) {
//					newArrayList = new ArrayList<EMPComponentStub>();
//					pluginsMap.put(po.getCompScope(), newArrayList);
//					pluginsIndex = 0;
//				} 
//				//有两种情况;1:没有修改作用范围；2：修改作用范围，但是作用范围列表不为空
//				else {
//					//1.没有修改作用范围
//					if(po.getCompScope().equals(compScope)) {
//						pluginsIndex = i;
//					} 
//					//2.修改作用范围，但是作用范围列表不为空
//					else {	
//						//获取最新作用范围列表的最大索引
//						stub = newArrayList.get(newArrayList.size() - 1);
//						strpluginsIndex = stub.getCompID().split("-")[2];
//						pluginsIndex = Integer.parseInt(strpluginsIndex);
//					}
//				}
//				//将原插件列表将插件移除
//				arrayList.remove(i);
//				//将移除的插件重新插入到最新的作用范围
//				newArrayList.add(pluginsIndex, po);
//				break;
//			}
//		}
//		return formInfo;
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务文件已经存在，更新，order为“向上移动”、“向下移动”标识，如果移动的话，只是修改插件在对应的ArrayList中的索引位置
	//      如果order为null则说明是修改插件的作用范围，则需要删除在原先的位置，并重新保存
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	private EMPComposeFormInfo updateFormService(EMPComposeFormInfo formInfo, String compID, String compScope, String order) {		
		List<EMPComponentStub>                        arrayList = new ArrayList<EMPComponentStub>();
		EMPComponentStub                                   stub = null;
		EMPComponentStub                                newStub = null;

		arrayList = EMPServicePluginsUtil.loadPluginsList(formInfo, compScope);
		for(int i = 0; i < arrayList.size(); i++) {
			if(arrayList.get(i).getCompID().equals(compID)) {
				stub = arrayList.get(i);
				
				//向上移动
				if(order.equals("1")) {
					if(i == 0) break;
					//获取上一个插件
					newStub = arrayList.get(i - 1);
					arrayList.set(i - 1, stub);
					arrayList.set(i, newStub);
				}
				//向下移动
				else {
					if(i == arrayList.size() - 1) break;
					newStub = arrayList.get(i + 1);
					arrayList.set(i + 1, stub);
					arrayList.set(i, newStub);
				}
				break;
			}
		}
		return formInfo;
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 加载服务文件先从本地取，取不到就从数据库里取
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	public Object loadFormService(HttpServletRequest request, String BBZD_BH) {
		String            servletPath = request.getRealPath("") + "\\WEB-INF\\ServiceSpace";
		String               filePath = servletPath + "\\" + BBZD_BH + ".xml";
		byte[]                   form = null;
		Object                 object = null;
		String                 strSql = "";
		File                  xmlFile = FileUtil.getFile(filePath); 
		JConnection              conn = null;
		JStatement               stmt = null;
		ResultSet                  rs = null;

		try {
			if(xmlFile != null && xmlFile.exists()) {
				form = FileUtil.getBytesFromFile(filePath);
			} else {
				conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
				stmt = conn.createStatement();
				
				if(form == null){
					strSql = "select * from SYS_DBFORMGS where BBZD_BH = '" + BBZD_BH + "'";
					rs = stmt.executeQuery(strSql);
		            if( rs != null && rs.next() ){
		            	form = ( byte[] )getBlogData( rs, "BBZD_GS" );
		            }
				}
			}

			if (form == null) {
		        return null;
			}
			XMLDecoder xmlEncoder = new XMLDecoder(new ByteArrayInputStream(form), null, null, Thread.currentThread().getContextClassLoader());
			object = xmlEncoder.readObject();
			xmlEncoder.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAllResources(rs, stmt, conn);
		}
		return object;
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 加载服务插件，服务文件先从本地取，取不到就从数据库里取
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	public EMPComponentStub loadFormServicePlugins(HttpServletRequest request, String BBZD_BH, String compID, String compScope) {
		Object                                         object = loadFormService(request, BBZD_BH);
		EMPComposeFormInfo                           formInfo = null;
		EMPComponentStub                                 stub = null;
		List<EMPComponentStub>                    pluginsList = null;
		
		if(object == null) {		
			return null;
		}
		formInfo = (EMPComposeFormInfo) object;
		pluginsList = EMPServicePluginsUtil.loadPluginsList(formInfo, compScope);

		for(int i = 0; pluginsList != null && i < pluginsList.size(); i++) {
			if(pluginsList.get(i).getCompID().equals(compID)) {
				stub = pluginsList.get(i);
				break;
			}
		}
		
		if(stub == null) {
			stub = new EMPComponentStub();
		}
		
		return stub;
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 移除服务插件，服务文件先从本地取，取不到就从数据库里取
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	public boolean deleteFormServicePlugins(HttpServletRequest request, String BBZD_BH, String compID, String compScope) {
		Object                                         object = loadFormService(request, BBZD_BH);
		EMPComposeFormInfo                           formInfo = null;
		EMPComponentStub                    servicePluginStub = new EMPComponentStub();
		String                                    servletPath = request.getRealPath("") + "\\WEB-INF\\ServiceSpace";
		String                                       filePath = servletPath + "\\" + BBZD_BH + ".xml";
		File                                          xmlFile = FileUtil.getFile(filePath);
		Map<String, EMPComponentStub>               serverMap = null;
		Map<String, EMPComponentStub>          pluginsMap = null;
		List<EMPComponentStub>                    pluginsList = null;
		EMPComponentStub                                 stub = null;
				
		if(object == null) {		
			return true;
		}
		
		formInfo = (EMPComposeFormInfo) object;
		
		serverMap = formInfo.getDataContainerStub().getRefCompStubMap();
		
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
		//3.新添加的插件在服务执行范围列表不存在
		for(int i = 0; pluginsList != null && i < pluginsList.size(); i++) {
			stub = pluginsList.get(i);
			if(stub.getCompID().equals(compID) && stub.getCompScope().equals(compScope)) {
				pluginsList.remove(i);
				break;
			}
		}

		try {
			FileOutputStream fileOutStm = new FileOutputStream(xmlFile);
			BufferedOutputStream bufferOUtStm = new BufferedOutputStream(fileOutStm);
			XMLEncoder xmlEncoder = new XMLEncoder(bufferOUtStm);
			xmlEncoder.writeObject(formInfo);
			xmlEncoder.close();
			return true;
		} catch(Exception ce) {
			ce.printStackTrace();
			return false;
		}
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 获取Bolb列数据
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	public Object getBlogData( ResultSet rs, String BlobField ) throws Exception {
		ByteArrayOutputStream bout = null;
	    Blob                  blob = null;
	    blob = rs.getBlob( BlobField );
	    
	    if( blob != null ) {
	    	InputStream is = blob.getBinaryStream();
	    	int Length = 0;
	    	Class clazz = blob.getClass();
	    	Method method = clazz.getMethod( "getBufferSize", new Class[] {} );
	    	Object object = method.invoke( blob, new Object[] {} );
	    	if( object != null && object instanceof Integer ) {
	    		Length = ( ( Integer )object ).intValue();
	    	} else {
	    		Length = ( int )blob.length();
	    	}
	    	byte[] data = new byte[ Length ];
	    	int readLength = 0;
	    	while( ( readLength = is.read( data ) ) > 0 ) {
	    		if( bout == null ) bout = new ByteArrayOutputStream();
	    		bout.write( data, 0, readLength );
	    	}
	    	if( bout != null ) {
	    		data = bout.toByteArray();
	    		bout.close();
	    	}
	    	return data;
	    }
	    return null;
	}
	
	public static void closeAllResources(ResultSet rs, JStatement stmt, JConnection conn) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		
		try {
			if(stmt != null) stmt.close();
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		
		try {
			if(conn != null) conn.close();
		} catch(Exception ce) {
			ce.printStackTrace();
		}
	}
}
