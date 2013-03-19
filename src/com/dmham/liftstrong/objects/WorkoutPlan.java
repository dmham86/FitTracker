package com.dmham.liftstrong.objects;

import java.util.ArrayList;
import java.util.ArrayList;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class WorkoutPlan {
	@DatabaseField(generatedId = true)
	private Integer _id;
	@DatabaseField
	private String name;
	@ForeignCollectionField(eager = false)
	private ForeignCollection<ExerciseLog> exerciseLogs;
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
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
