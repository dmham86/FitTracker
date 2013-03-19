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
	@DatabaseField(generatedId = true)
	private Integer _id;
	@DatabaseField
	private Date date;
	@DatabaseField
	private String comments;
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	WorkoutPlan workoutPlan;
	@ForeignCollectionField(eager = false)
	private ForeignCollection<ExerciseLog> exerciseLogs;
	
	public WorkoutLog() {
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
