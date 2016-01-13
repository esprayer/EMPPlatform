package com.etsoft.server;

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
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import com.etsoft.pub.util.FileUtil;

import dwz.persistence.formservice.bean.SYS_DBFORM;
import dwz.persistence.formservice.mapper.SYS_DBFORMMapper;

import esyt.framework.com.component.EMPComponentStub;
import esyt.framework.com.component.EMPComposeFormInfo;
import esyt.framework.com.core.foundation.sql.JConnection;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.pub.util.Debug;

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
	public boolean saveFormService(HttpServletRequest request, EMPComponentStub po, String BBZD_BH) {
		Object                 object = loadFormService(request, BBZD_BH);
		EMPComposeFormInfo   formInfo = null;
		String            servletPath = request.getRealPath("") + "\\WEB-INF\\ServiceSpace";
		String               filePath = servletPath + "\\" + BBZD_BH + ".xml";
		File                  xmlFile = FileUtil.getFile(filePath); 
		
		try {
			
			if(object == null) {		
				SYS_DBFORM dbForm = dbFormMapper.load(BBZD_BH);
				formInfo = createNewFormService(po, dbForm);
			} else {
				formInfo = (EMPComposeFormInfo) object;
				formInfo = updateFormService(formInfo, po);
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
	private EMPComposeFormInfo createNewFormService(EMPComponentStub po, SYS_DBFORM dbForm) {
		EMPComposeFormInfo                                    formInfo = new EMPComposeFormInfo();		
		ArrayList<EMPComponentStub>                          arrayList = new ArrayList<EMPComponentStub>();
		Map<String, ArrayList<EMPComponentStub>>            pluginsMap = new HashMap<String, ArrayList<EMPComponentStub>>();
		EMPComponentStub                                          stub = new EMPComponentStub();
		
		//0.插件编号需要再加上索引位置，因为一个插件可以添加多次，所以需要加工一下
		po.setCompID(po.getCompScope() + "-" + po.getCompID() + "-0");
		//1.形成插件ArrayList,并将插件放到ArrayList中
		arrayList.add(po);
		//2.根据作用范围形成对应的HashMap
		pluginsMap.put(po.getCompScope(), arrayList);
		//3.将插件对应的Map放到EMPComponentStub中的refCompStubMap中
		stub.setRefCompStubMap(pluginsMap);
		//4.将EMPComponentStub放到EMPComposeFormInfo的DataContainerStub中
		formInfo.setDataContainerStub(stub);
		//5.EMPComposeFormInfo对象对应一个服务文件，初始化EMPComposeFormInfo对象
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
	private EMPComposeFormInfo updateFormService(EMPComposeFormInfo formInfo, EMPComponentStub po) {		
		ArrayList<EMPComponentStub>                   arrayList = new ArrayList<EMPComponentStub>();
		ArrayList<EMPComponentStub>                newArrayList = new ArrayList<EMPComponentStub>();
		Map<String, ArrayList<EMPComponentStub>>     pluginsMap = formInfo.getDataContainerStub().getRefCompStubMap();
		String                                           compId = po.getCompID();
		String                                        compScope = "";
		EMPComponentStub                                   stub = null;
		int                                        pluginsIndex = 0;
		String                                  strpluginsIndex = "";
		
		//1.获取存放插件ArrayList的HashMap
		if(compId.indexOf("-") == -1) {
			//如果compId没有分隔符，则是新加
			arrayList = (ArrayList<EMPComponentStub>)pluginsMap.get(po.getCompScope());
			//新添加的插件在服务执行范围列表不存在
			if(arrayList == null || arrayList.size() == 0) {
				arrayList = new ArrayList<EMPComponentStub>();
				po.setCompID(po.getCompScope() + "-" + po.getCompID() + "-0");
				arrayList.add(po);
				pluginsMap.put(po.getCompScope(), arrayList);
				return formInfo;
			}
			//新添加的插件在服务执行范围列表不存在
			else {
				stub = arrayList.get(arrayList.size() - 1);
				strpluginsIndex = stub.getCompID().split("-")[2];
				pluginsIndex = Integer.parseInt(strpluginsIndex);
				pluginsIndex++;
				po.setCompID(po.getCompScope() + "-" + po.getCompID() + "-" + pluginsIndex);
				arrayList.add(po);
				return formInfo;
			}
		}
		
		
		//2.获取插件作用范围
		compScope = compId.split("-")[0];
		

		arrayList = (ArrayList<EMPComponentStub>)pluginsMap.get(compScope);

		for(int i = 0; i < arrayList.size(); i++) {
			if(arrayList.get(i).getCompID().equals(po.getCompID())) {
				//如果修改作用范围则获取最新的作用范围中的插件ArrayList
				newArrayList = (ArrayList<EMPComponentStub>)pluginsMap.get(po.getCompScope());
				//如果切换作用范围，且最新的作用范围插件列表为空，则初始化
				if(newArrayList == null) {
					newArrayList = new ArrayList<EMPComponentStub>();
					pluginsMap.put(po.getCompScope(), newArrayList);
					pluginsIndex = 0;
				} 
				//有两种情况;1:没有修改作用范围；2：修改作用范围，但是作用范围列表不为空
				else {
					//1.没有修改作用范围
					if(po.getCompScope().equals(compScope)) {
						pluginsIndex = i;
					} 
					//2.修改作用范围，但是作用范围列表不为空
					else {	
						//获取最新作用范围列表的最大索引
						stub = newArrayList.get(newArrayList.size() - 1);
						strpluginsIndex = stub.getCompID().split("-")[2];
						pluginsIndex = Integer.parseInt(strpluginsIndex);
					}
				}
				//将原插件列表将插件移除
				arrayList.remove(i);
				//将移除的插件重新插入到最新的作用范围
				newArrayList.add(pluginsIndex, po);
				break;
			}
		}
		return formInfo;
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务文件已经存在，更新，order为“向上移动”、“向下移动”标识，如果移动的话，只是修改插件在对应的ArrayList中的索引位置
	//      如果order为null则说明是修改插件的作用范围，则需要删除在原先的位置，并重新保存
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	private EMPComposeFormInfo updateFormService(EMPComposeFormInfo formInfo, String compID, String compScope, String order) {		
		ArrayList<EMPComponentStub>                   arrayList = new ArrayList<EMPComponentStub>();
		Map<String, ArrayList<EMPComponentStub>>     pluginsMap = formInfo.getDataContainerStub().getRefCompStubMap();
		EMPComponentStub                                   stub = null;
		EMPComponentStub                                newStub = null;
		int                                        pluginsIndex = 0;
		String                                  strpluginsIndex = "";

		arrayList = (ArrayList<EMPComponentStub>)pluginsMap.get(compScope);
		for(int i = 0; i < arrayList.size(); i++) {
			if(arrayList.get(i).getCompID().equals(compID)) {
				stub = arrayList.get(i);
				
				//向上移动
				if(order.equals("1")) {
					if(i == 0) break;
					pluginsIndex = i - 1;
					newStub = arrayList.get(pluginsIndex);
					//记录将要移动的插件的索引
					strpluginsIndex = stub.getCompID().split("-")[2];
					//将被移动的索引赋给移动插件的索引
					stub.setCompID(stub.getCompID().split("-")[0] + "-" + stub.getCompID().split("-")[1] + "-" + newStub.getCompID().split("-")[2]);
					//将移动插件的索引赋给被移动插件索引
					newStub.setCompID(newStub.getCompID().split("-")[0] + "-" + newStub.getCompID().split("-")[1] + "-" + strpluginsIndex);
					arrayList.remove(i);
					arrayList.add(pluginsIndex, stub);
				}
				//向下移动
				else {
					if(i == arrayList.size() - 1) break;
					pluginsIndex = i + 1;
					newStub = arrayList.get(pluginsIndex);
					//记录将要移动的插件的索引
					strpluginsIndex = stub.getCompID().split("-")[2];
					//将被移动的索引赋给移动插件的索引
					stub.setCompID(stub.getCompID().split("-")[0] + "-" + stub.getCompID().split("-")[1] + "-" + newStub.getCompID().split("-")[2]);
					//将移动插件的索引赋给被移动插件索引
					newStub.setCompID(newStub.getCompID().split("-")[0] + "-" + newStub.getCompID().split("-")[1] + "-" + strpluginsIndex);
					arrayList.remove(i);
					arrayList.add(pluginsIndex, stub);
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
			Debug.PrintlnMessageToSystem(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(stmt);
				stmt = null;
				DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
			} catch(Exception ce) {
				ce.printStackTrace();
			}
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
		HashMap<String, List<EMPComponentStub>>    pluginsMap = null;
		ArrayList<EMPComponentStub>                      list = null;

		if(object == null) {		
			return null;
		}
		formInfo = (EMPComposeFormInfo) object;
		pluginsMap = (HashMap) formInfo.getDataContainerStub().getRefCompStubMap();
		if(pluginsMap == null || pluginsMap.get(compScope) == null) return null;
		
		list = (ArrayList<EMPComponentStub>) pluginsMap.get(compScope);
		
		for(int i = 0; list != null && i < list.size(); i++) {
			if(list.get(i).getCompID().equals(compID)) {
				stub = list.get(i);
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
		HashMap<String, List<EMPComponentStub>>    pluginsMap = null;
		ArrayList<EMPComponentStub>                      list = null;
		String                                    servletPath = request.getRealPath("") + "\\WEB-INF\\ServiceSpace";
		String                                       filePath = servletPath + "\\" + BBZD_BH + ".xml";
		File                                          xmlFile = FileUtil.getFile(filePath); 
		
		if(object == null) {		
			return true;
		}
		
		formInfo = (EMPComposeFormInfo) object;
		pluginsMap = (HashMap) formInfo.getDataContainerStub().getRefCompStubMap();
		if(pluginsMap == null || pluginsMap.get(compScope) == null) return true;
		
		list = (ArrayList<EMPComponentStub>) pluginsMap.get(compScope);
		
		for(int i = 0; list != null && i < list.size(); i++) {
			if(list.get(i).getCompID().equals(compID)) {
				list.remove(i);
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
}
