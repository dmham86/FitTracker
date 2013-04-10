package com.dmham.liftstrong.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Exercise {
	public static final String ID_FIELD_NAME = "_id";
	public static final String NAME_FIELD_NAME = "name";
	
	@DatabaseField(generatedId = true, columnName=ID_FIELD_NAME)
	private Integer _id;
	@DatabaseField(columnName=NAME_FIELD_NAME)
	private String name;
	@DatabaseField
	private String description;
	
	public Exercise() {
		setName("");
		setDesc("");
	}
	
	public Exercise(String name, String description) {
		setName(name);
		setDesc(description);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the description of the exercise
	 */
	public String getDesc() {
		return this.description;
	}
	/**
	 * @param desc the description to set
	 */
	public void setDesc(String desc) {
		this.description = desc;
	}
	
	public boolean isValid(){
		if(name == null || name == "")
			return false;
		return true;
	}

}
