package com.efounder.bz.service;

import com.core.xml.JBOFClass;
import com.efounder.builder.base.util.ESPServerContext;
import com.efounder.component.EMPComponentStub;

import java.util.ArrayList;
import java.util.List;

public abstract class ServiceComponentAdapter {
	protected boolean autoCommit = true;

	public final boolean isAutoCommit() {
		return this.autoCommit;
	}

	public final void setAutoCommit(boolean a) {
		this.autoCommit = a;
	}

	protected ServiceComponentAdapter nextServiceComponent = null;
	protected ServiceComponentAdapter priorServiceComponent = null;
	private List servicePluginList = null;
	private boolean bEnable = true;

	public final ServiceComponentAdapter getNextServiceComponent() {
		return this.nextServiceComponent;
	}

	public final ServiceComponentAdapter getPriorServiceComponent() {
		return this.priorServiceComponent;
	}

	public List createServicePluginList() {
		if (this.servicePluginList == null) {
			this.servicePluginList = new ArrayList();
		}
		return this.servicePluginList;
	}

	public final List getServicePluginList() {
		return this.servicePluginList;
	}

	public boolean isEnabled() {
		return this.bEnable;
	}

	public final void setNextServiceComponent(ServiceComponentAdapter nsc) {
		this.nextServiceComponent = nsc;
		if (this.nextServiceComponent != null) {
			this.nextServiceComponent.priorServiceComponent = this;
		}
	}

	public final void setPriorServiceComponent(ServiceComponentAdapter psc) {
		this.priorServiceComponent = psc;
		if (this.priorServiceComponent != null) {
			this.priorServiceComponent.nextServiceComponent = this;
		}
	}

	public final void setServicePluginList(List servicePluginList) {
		if (this.servicePluginList != null) {
			removeService(servicePluginList, this);
		}
		this.servicePluginList = servicePluginList;
		if (this.servicePluginList != null) {
			addService(servicePluginList, this);
		}
	}

	public void setEnabled(boolean enabled) {
		this.bEnable = enabled;
	}

	private final void removeService(List list, ServiceComponentAdapter scp) {
		if (list == null) {
			return;
		}
		ServicePluginAdapter spa = null;
		for (int i = 0; i < list.size(); i++) {
			spa = (ServicePluginAdapter) list.get(i);
			if (spa != null) {
				spa.removeServiceComponent(scp);
			}
		}
	}

	private final void addService(List list, ServiceComponentAdapter scp) {
		if (list == null) {
			return;
		}
		ServicePluginAdapter spa = null;
//		for (int i = 0; i < list.size(); i++) {
//			spa = (ServicePluginAdapter) list.get(i);
//			if (spa != null) {
//				spa.insertServiceComponent(scp);
//			}
//		}
	}

	public final Object runService(ESPServerContext espContext, Object responseObject) throws Exception {
		if (isEnabled()) {
			Object[] OParams = { espContext, responseObject };
			String methodName = null;

			methodName = getPreparePluginServiceName(espContext, responseObject);
			if ((methodName != null) && (methodName.trim().length() != 0)) {
				JBOFClass.CallObjectMethodException(this, methodName, OParams);
			} else {
				runPreparePluginService(espContext, responseObject);
			}
			methodName = getRunPluginServiceName(espContext, responseObject);
			if ((methodName != null) && (methodName.trim().length() != 0)) {
				JBOFClass.CallObjectMethodException(this, methodName, OParams);
			} else {
				runProcessPluginService(espContext, responseObject);
			}
			methodName = getFinishPluginServiceName(espContext, responseObject);
			if ((methodName != null) && (methodName.trim().length() != 0)) {
				JBOFClass.CallObjectMethodException(this, methodName, OParams);
			} else {
				runFinishPluginService(espContext, responseObject);
			}
		}
		if (this.nextServiceComponent != null) {
			this.nextServiceComponent.runService(espContext, responseObject);
		}
		return responseObject;
	}

	protected Object runPreparePluginService(ESPServerContext espContext, Object responseObject) throws Exception {
		Object[] OArray = { espContext, responseObject };
		return executePluginService("executePrepareService", OArray, "prepare", espContext, responseObject);
	}

	protected Object runProcessPluginService(ESPServerContext espContext, Object responseObject) throws Exception {
		Object[] OArray = { espContext, responseObject };
		return executePluginService("executeProcessService", OArray, "process", espContext, responseObject);
	}

	protected Object runFinishPluginService(ESPServerContext espContext, Object responseObject) throws Exception {
		Object[] OArray = { espContext, responseObject };
		return executePluginService("executeFinishService", OArray, "finish", espContext, responseObject);
	}

	protected String getPreparePluginServiceName(ESPServerContext espContext, Object responseObject) {
		return null;
	}

	protected String getRunPluginServiceName(ESPServerContext espContext, Object responseObject) {
		return null;
	}

	protected String getFinishPluginServiceName(ESPServerContext espContext, Object responseObject) {
		return null;
	}

	protected final Object executePluginService(String methodName, Object[] OArray) throws Exception {
		return executePluginService(methodName, OArray, null, null, null);
	}

	protected final Object executePluginService(String methodName, Object[] OArray, String proType, ESPServerContext espContext, Object responseObject) throws Exception {
		if ((this.servicePluginList == null) || (this.servicePluginList.size() == 0)) {
			return null;
		}
		ServicePluginAdapter     servicePluginAdapter = null;
		EMPComponentStub                     compStub = null;
		Class                             classObject = null;
		for (int i = 0; i < this.servicePluginList.size(); i++) {
//			servicePlugin = (ServicePluginAdapter) this.servicePluginList.get(i);
			compStub = (EMPComponentStub) this.servicePluginList.get(i);
			if ((compStub != null)/* && (servicePlugin.isEnabled())*/) {
				if ((!"prepare".equals(proType))/* || (servicePlugin.canPrepareService(espContext, responseObject))*/) {
					if ((!"process".equals(proType))/* || (servicePlugin.canProcessService(espContext, responseObject))*/) {
						if ((!"finish".equals(proType))/* || (servicePlugin.canFinishService(espContext, responseObject))*/) {
							classObject = Class.forName(compStub.getCompClazz());
							servicePluginAdapter = (ServicePluginAdapter) classObject.newInstance(); 
							JBOFClass.CallObjectMethodException(servicePluginAdapter, methodName, OArray);
						}
					}
				}
			}
		}
		return null;
	}
}
