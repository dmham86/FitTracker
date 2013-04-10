package com.dmham.liftstrong.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.ArrayList;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class WorkoutLog {
	public static final String ID_FIELD_NAME = "_id";
	public static final String DATE_FIELD_NAME = "date";
	public static final String WORKOUT_PLAN_ID_FIELD_NAME = "workoutPlan_id";
	
	@DatabaseField(generatedId = true, columnName=ID_FIELD_NAME)
	private Integer _id;
	@DatabaseField(columnName=DATE_FIELD_NAME)
	private Date date;
	@DatabaseField
	private String comments;
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName=WORKOUT_PLAN_ID_FIELD_NAME)
	WorkoutPlan workoutPlan;
	@ForeignCollectionField(eager = false)
	private ForeignCollection<ExerciseLog> exerciseLogs;
	
	public WorkoutLog() {
	}
	
	public WorkoutLog(Date date) {
		this.date = date;
	}
	
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	/**
	 * @return the WorkoutPlan
	 */
	public WorkoutPlan getWorkoutPlan() {
		return workoutPlan;
	}
	/**
	 * @param workoutPlan the WorkoutPlan to set
	 */
	public void setWorkoutPlan(WorkoutPlan workoutPlan) {
		this.workoutPlan = workoutPlan;
	}

	/**
	 * @return the exerciseLogs
	 */
	public ForeignCollection<ExerciseLog> getExerciseLogs() {
		return exerciseLogs;
	}

}
