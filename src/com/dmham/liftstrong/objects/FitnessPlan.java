package com.dmham.liftstrong.objects;

import java.util.ArrayList;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public class FitnessPlan {
	public static final String NAME_FIELD_NAME = "name";
	
	@DatabaseField(generatedId = true)
	private Integer _id;
	@DatabaseField(columnName=NAME_FIELD_NAME)
	private String name = "";
	@ForeignCollectionField(eager = false)
	private ForeignCollection<WorkoutPlan> workouts;
	
	// ORMLite requires an empty constructor
	public FitnessPlan() {
		this.setName("");
	}
	public FitnessPlan(String name) {
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
	 * @return the workouts
	 */
	public ForeignCollection<WorkoutPlan> getWorkouts() {
		return workouts;
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
	
	public WorkoutPlan getNextWorkout(){
		WorkoutPlan wp = new WorkoutPlan();
		return wp;
	}

}
