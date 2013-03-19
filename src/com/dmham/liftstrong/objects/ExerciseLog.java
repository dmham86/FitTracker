package com.dmham.liftstrong.objects;

import java.util.ArrayList;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ExerciseLog {
	@DatabaseField(generatedId = true)
	private Integer _id;
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Exercise exercise;
	@ForeignCollectionField(eager = false)
    ForeignCollection<ExerciseSet> sets;
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private WorkoutLog workoutLog;
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private WorkoutPlan workoutPlan;
	
	// ORMLite requires empty constructor
	public ExerciseLog() {
	}
	
	public ExerciseLog(Exercise exercise) {
		this.setExercise(exercise);
	}

	/**
	 * @return the sets
	 */
	public ForeignCollection<ExerciseSet> getSets() {
		return sets;
	}

	/**
	 * @return the exercise
	 */
	public Exercise getExercise() {
		return exercise;
	}

	/**
	 * @param exercise the exercise to set
	 */
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	/**
	 * @return the workoutLog
	 */
	public WorkoutLog getWorkoutLog() {
		return workoutLog;
	}

	/**
	 * @param workoutLog the workoutLog to set
	 */
	public void setWorkoutLog(WorkoutLog workoutLog) {
		this.workoutLog = workoutLog;
	}

	/**
	 * @return the workoutPlan
	 */
	public WorkoutPlan getWorkoutPlan() {
		return workoutPlan;
	}

	/**
	 * @param workoutPlan the workoutPlan to set
	 */
	public void setWorkoutPlan(WorkoutPlan workoutPlan) {
		this.workoutPlan = workoutPlan;
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
}
