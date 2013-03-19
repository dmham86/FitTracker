package com.dmham.liftstrong.objects;

public class MenuItem {
	private String name; 
	private String description; 
	private String className; 
	private String imgName;
	
	public MenuItem() {
		setName("");
		setDescription("");
		setClassName("");
		setImgName("");
	}
	
	public MenuItem(String name, String description, String className, String imgName) {
		setName(name);
		setDescription(description);
		setClassName(className);
		setImgName(imgName);
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the imgName
	 */
	public String getImgName() {
		return imgName;
	}

	/**
	 * @param imgName the imgName to set
	 */
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

}
