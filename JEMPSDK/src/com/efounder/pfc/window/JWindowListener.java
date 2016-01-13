package com.efounder.pfc.window;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.core.xml.StubObject;

public class JWindowListener {
	Map<String, Component>              jw = new HashMap<String, Component>();
	List<Component>                    jwi = new ArrayList<Component>();
	List<StubObject>                jwnode = new ArrayList<StubObject>();
	
	public int getJWindow(StubObject nodeStub, Component comp) {
		if(nodeStub == null) return -1;
		if(jw == null) {
			jw = new HashMap<String, Component>();
			return -1;
		}
		
		if(jwi == null) {
			jwi = new ArrayList<Component>();
			return -1;
		}
		
		if(jwnode == null) {
			jwnode = new ArrayList<StubObject>();
			return -1;
		}
		
		Component windowChild = jw.get(nodeStub.getID().toString());
		StubObject temp = null;
		
		if(windowChild != null) {
			for(int i = 0; i < jwnode.size(); i++) {
				temp = jwnode.get(i);
				if(temp.getID().toString().equals(nodeStub.getID().toString())) return i;
			}
		}
		
		
		jw.put(nodeStub.getID().toString(), comp);
		jwi.add(comp);
		jwnode.add(nodeStub);
		return -1;
	}
	
	public void removeJWindow(int tabIndex) {
		StubObject stub = null;
		
		if(jw == null) {
			jw = new HashMap<String, Component>();
			return;
		}
		
		if(jwi == null) {
			jwi = new ArrayList<Component>();
			return;
		}
		
		if(jwnode == null) {
			jwnode = new ArrayList<StubObject>();
			return;
		}
		
		if(jwi.size() < tabIndex) return;
		
		if(jwnode.size() < tabIndex) return;
		
		stub = jwnode.remove(tabIndex);
		jw.remove(stub.getID().toString());
		jwi.remove(tabIndex);
	}
}
