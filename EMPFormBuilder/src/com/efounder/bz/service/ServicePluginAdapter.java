package com.efounder.bz.service;

import com.efounder.builder.base.util.ESPServerContext;
import java.util.ArrayList;
import java.util.List;

public abstract class ServicePluginAdapter {
	protected List serviceComponentList = new ArrayList();

	protected void insertServiceComponent(ServiceComponentAdapter serviceComponent) {
		this.serviceComponentList.add(serviceComponent);
	}

	protected void removeServiceComponent(ServiceComponentAdapter serviceComponent) {
		this.serviceComponentList.remove(serviceComponent);
	}

	public List getServiceComponentList() {
		return this.serviceComponentList;
	}

	public boolean isEnabled() {
		return this.bEnable;
	}

	public boolean canPrepareService(ESPServerContext espContext,
			Object responseObject) throws Exception {
		return true;
	}

	private boolean bEnable = true;

	public Object executePrepareService(ESPServerContext espContext,
			Object responseObject) throws Exception {
		return null;
	}

	public boolean canProcessService(ESPServerContext espContext,
			Object responseObject) throws Exception {
		return true;
	}

	public Object executeProcessService(ESPServerContext espContext,
			Object responseObject) throws Exception {
		return null;
	}

	public boolean canFinishService(ESPServerContext espContext,
			Object responseObject) throws Exception {
		return true;
	}

	public Object executeFinishService(ESPServerContext espContext,
			Object responseObject) throws Exception {
		return null;
	}

	public void setEnabled(boolean enabled) {
		this.bEnable = enabled;
	}
}
