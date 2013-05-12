package com.dmham.liftstrong.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ExerciseSet {
	private static final int MIN_REPS = 0;
	private static final int MAX_REPS = 100;
	private static final float MIN_WEIGHT = -400;
	private static final float MAX_WEIGHT = 1200;
	
	public static final String ID_FIELD_NAME = "_id";
	
	@DatabaseField(generatedId = true, columnName=ID_FIELD_NAME)
	private Integer _id;
	@DatabaseField(useGetSet = true)
	private int reps;
	@DatabaseField(useGetSet = true)
	private float weight;
	@DatabaseField
	private boolean assist = false;
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private ExerciseLog exerciseLog;
	private boolean enabled = false;
	
	public ExerciseSet() {
		setReps((int)0);
		setWeight(0.0f);
	}
	
	public ExerciseSet(int reps, float weight) {
		setReps(reps);
		setWeight(weight);
	}
	
	public ExerciseSet(int reps, float weight, boolean assist) {
		setReps(reps);
		setWeight(weight);
		this.assist = assist;
	}
	
	/**
	 * @return the reps
	 */
	public int getReps() {
		return reps;
	}
	/**
	 * @param reps the reps to set
	 */
	public void setReps(int reps) {
		this.reps = (reps < MIN_REPS) ? MIN_REPS : ((reps > MAX_REPS) ? MAX_REPS : reps);
	}
	/**
	 * @return the weight
	 */
	public float getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(float weight) {
		this.weight = (weight < MIN_WEIGHT) ? MIN_WEIGHT : ((weight > MAX_WEIGHT) ? MAX_WEIGHT : weight);
	}
	/**
	 * @return the assist
	 */
	public boolean isAssist() {
		return assist;
	}
	/**
	 * @param assist the assist to set
	 */
	public void setAssist(boolean assist) {
		this.assist = assist;
	}

	/**
	 * @return the _id
	 */
	public Integer getId() {
		return _id;
	}

	/**
	 * @param _id the _id to set
	 */
	public void setId(Integer id) {
		this._id = id;
	}

	/**
	 * @return the exerciseLog
	 */
	public ExerciseLog getExerciseLog() {
		return exerciseLog;
	}

	/**
	 * @param exerciseLog the exerciseLog to set
	 */
	public void setExerciseLog(ExerciseLog exerciseLog) {
		this.exerciseLog = exerciseLog;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
