package com.bean;

public class Dependency {

	String id;
	String fromId;
	String toId;
	String isSpecification;
	
	public void setId(String id)
	{
		this.id=id;
	}
	public String getId()
	{
		return id;
	}
	
	public void setFromId(String fromId)
	{
		this.fromId=fromId;
	}
	public String getFromId()
	{
		return fromId;
	}
	
	public void setToId(String toId)
	{
		this.toId=toId;
	}
	public String getToId()
	{
		return toId;
	}
	
	public void setIsSpecification(String isSpecification)
	{
		this.isSpecification=isSpecification;
	}
	public String getIsSpecification()
	{
		return isSpecification;
	}	
}
