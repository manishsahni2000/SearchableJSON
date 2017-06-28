package com.devv.it.POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public class DataObject {

	private String Name;
	private String Type;
	private String designedBy;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getDesignedBy() {
		return designedBy;
	}

	public void setDesignedBy(String designedBy) {
		this.designedBy = designedBy;
	}

	@Override
	public String toString() {
		return "DataObject [Name=" + Name + ", Type=" + Type + ", designedBy="
				+ designedBy + "]";
	}

}
