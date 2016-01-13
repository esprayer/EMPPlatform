package com.efounder.bz.dbform.datamodel;

public class ComponentBuilder extends DataComponentAdapter implements ComponentManager {
	protected ComponentBuilder nextBuilder = null;

	public ComponentBuilder getNextBuilder() {
		return this.nextBuilder;
	}

	public void setNextBuilder(ComponentBuilder nb) {
		if (this.nextBuilder != null) {
			this.nextBuilder.priorBuilder = null;
		}
		this.nextBuilder = nb;
		if (this.nextBuilder != null) {
			this.nextBuilder.priorBuilder = this;
		}
	}

	protected ComponentBuilder priorBuilder = null;

	public ComponentBuilder getPriorBuilder() {
		return this.priorBuilder;
	}

	public void buildComponent() {
	}
}
