package com.dmham.lifstrong.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dmham.liftstrong.objects.*;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Helper class which creates/updates our database and provides the DAOs.
 * 
 * @author David Hamilton
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "liftstrong.db";
	private static final int DATABASE_VERSION = 9;
	private final String LOG_NAME = getClass().getName();

	private Dao<Exercise, Integer> exerciseDao;
	private Dao<ExerciseSet, Integer> exerciseSetDao;
	private Dao<ExerciseLog, Integer> exerciseLogDao;
	private Dao<WorkoutPlan, Integer> workoutPlanDao;
	private Dao<WorkoutLog, Integer> workoutLogDao;
	private Dao<FitnessPlan, Integer> fitPlanDao;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Exercise.class);
			TableUtils.createTable(connectionSource, ExerciseSet.class);
			TableUtils.createTable(connectionSource, ExerciseLog.class);
			TableUtils.createTable(connectionSource, WorkoutPlan.class);
			TableUtils.createTable(connectionSource, WorkoutLog.class);
			TableUtils.createTable(connectionSource, FitnessPlan.class);
		} catch (SQLException e) {
			Log.e(LOG_NAME, "Could not create new tables", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion,
			int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Exercise.class, true);
			TableUtils.dropTable(connectionSource, ExerciseSet.class, true);
			TableUtils.dropTable(connectionSource, ExerciseLog.class, true);
			TableUtils.dropTable(connectionSource, WorkoutPlan.class, true);
			TableUtils.dropTable(connectionSource, WorkoutLog.class, true);
			TableUtils.dropTable(connectionSource, FitnessPlan.class, true);
			onCreate(sqLiteDatabase, connectionSource);
		} catch (SQLException e) {
			Log.e(LOG_NAME, "Could not upgrade the tables", e);
		}
	}

	public Dao<Exercise, Integer> getExerciseDao() throws SQLException {
		if (exerciseDao == null) {
			exerciseDao = getDao(Exercise.class);
		}
		return exerciseDao;
	}
	public Dao<ExerciseSet, Integer> getExerciseSetDao() throws SQLException {
		if (exerciseSetDao == null) {
			exerciseSetDao = getDao(ExerciseSet.class);
		}
		return exerciseSetDao;
	}
	public Dao<ExerciseLog, Integer> getExerciseLogDao() throws SQLException {
		if (exerciseLogDao == null) {
			exerciseLogDao = getDao(ExerciseLog.class);
		}
		return exerciseLogDao;
	}
	public Dao<WorkoutPlan, Integer> getWorkoutPlanDao() throws SQLException {
		if (workoutPlanDao == null) {
			workoutPlanDao = getDao(WorkoutPlan.class);
		}
		return workoutPlanDao;
	}
	public Dao<WorkoutLog, Integer> getWorkoutLogDao() throws SQLException {
		if (workoutLogDao == null) {
			workoutLogDao = getDao(WorkoutLog.class);
		}
		return workoutLogDao;
	}
	public Dao<FitnessPlan, Integer> getFitnessPlanDao() throws SQLException {
		if (fitPlanDao == null) {
			fitPlanDao = getDao(FitnessPlan.class);
		}
		return fitPlanDao;
	}
}
