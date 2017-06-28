package com.devv.it.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataObject {

	@JsonProperty("Name")
	private String Name;
	@JsonProperty("Type")
	private String Type;
	@JsonProperty("Designed by")
	private String Designedby;
	
	

	public DataObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataObject(String name, String type, String designedby) {

		this.Name = name;
		this.Type = type;
		this.Designedby = designedby;
	}

	@Override
	public String toString() {
		return "DataObject [Name=" + Name + ", Type=" + Type + ", Designedby="
				+ Designedby + "]";
	}

	public String getDesignedby() {
		return Designedby;
	}

	public void setDesignedby(String designedby) {
		Designedby = designedby;
	}

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

}
