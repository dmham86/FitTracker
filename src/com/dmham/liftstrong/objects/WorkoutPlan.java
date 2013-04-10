package com.dmham.liftstrong.objects;

import java.util.ArrayList;
import java.util.ArrayList;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class WorkoutPlan {
	public static final String ID_FIELD_NAME = "_id";
	public static final String NAME_FIELD_NAME = "name";
	public static final String FITNESS_PLAN_ID_FIELD_NAME = "fitnessPlan_id";
	
	@DatabaseField(generatedId = true, columnName=ID_FIELD_NAME)
	private Integer _id;
	@DatabaseField(columnName=NAME_FIELD_NAME)
	private String name;
	@ForeignCollectionField(eager = false)
	private ForeignCollection<ExerciseLog> exerciseLogs;
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName=FITNESS_PLAN_ID_FIELD_NAME)
	private FitnessPlan fitnessPlan;
	
	//ORMLite needs blank constructor
	public WorkoutPlan() {
		this.setName("");
	}
	
	public WorkoutPlan(String name) {
		this.setName(name);
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
		if(name != null && name != "")
			this.name = name;
	}

	/**
	 * @return the fitnessPlan
	 */
	public FitnessPlan getFitnessPlan() {
		return fitnessPlan;
	}

	/**
	 * @param fitnessPlan the fitnessPlan to set
	 */
	public void setFitnessPlan(FitnessPlan fitnessPlan) {
		this.fitnessPlan = fitnessPlan;
	}

	/**
	 * @return the exerciseLogs
	 */
	public ForeignCollection<ExerciseLog> getExerciseLogs() {
		return exerciseLogs;
	}

	/**
	 * @return the _id
	 */
	public Integer getPlanId() {
		return _id;
	}
	/**
	 * @param _id the _id to set
	 */
	public void setPlanId(Integer id) {
		this._id = id;
	}
}
