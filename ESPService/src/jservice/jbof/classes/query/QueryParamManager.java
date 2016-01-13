package jservice.jbof.classes.query;

import java.util.List;

import org.openide.util.Lookup;

import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.eai.data.JParamObject;

public abstract class QueryParamManager {
	
	public static String Query_Key = "";//缓存方式
	
	public static QueryParamManager getDefault(){
		if (Query_Key==null || "".equals(Query_Key)){
			List list = PackageStub.getContentVector("QueryParamMode");
			StubObject so = (StubObject)list.get(0);
			Query_Key = (String)so.getID();
		}
		return getDefault(Query_Key);
	}

	public static QueryParamManager getDefault(String key){		
		return (QueryParamManager)Lookup.getDefault().lookup(QueryParamManager.class,key);
	}
	
	public abstract JParamObject beginConvert(String key, JParamObject PO, Object data) throws Exception;
	
	public abstract void endConvert(String key, JParamObject PO, Object data) throws Exception;
}
