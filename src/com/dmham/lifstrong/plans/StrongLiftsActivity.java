package com.dmham.lifstrong.plans;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.dmham.lifstrong.database.DatabaseHelper;
import com.dmham.liftstrong.R;
import com.dmham.liftstrong.R.layout;
import com.dmham.liftstrong.R.menu;
import com.dmham.liftstrong.objects.Exercise;
import com.dmham.liftstrong.objects.ExerciseLog;
import com.dmham.liftstrong.objects.ExerciseSet;
import com.dmham.liftstrong.objects.FitnessPlan;
import com.dmham.liftstrong.objects.WorkoutPlan;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;

public class StrongLiftsActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	private Dao<FitnessPlan, Integer> fitPlanDao;
	private Dao<WorkoutPlan, Integer> workPlanDao;
	private Dao<ExerciseLog, Integer> exLogDao;
	private Dao<ExerciseSet, Integer> exSetDao;
	private Dao<Exercise, Integer> exDao;
	private static final String PLAN_NAME = "Strong Lifts";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_strong_lifts);
		// Show the Up button in the action bar.
		setupActionBar();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Resources res = getResources();
		
		Intent i = getIntent();
		String planAction = i.getStringExtra(res.getString(R.string.fitness_plan_action));
		
		if(planAction == null) {
			return;
		}
		try {
			fitPlanDao = getHelper().getFitnessPlanDao();
			workPlanDao = getHelper().getWorkoutPlanDao();
			exLogDao = getHelper().getExerciseLogDao();
			exSetDao = getHelper().getExerciseSetDao();
			exDao = getHelper().getExerciseDao();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(planAction.equals(res.getString(R.string.fitness_plan_action_reset)) ) {
			resetPlan();
		}
		else if(planAction == res.getString(R.string.fitness_plan_action_log) ) {
			logWorkout();
		}

	}
	
	private void logWorkout(){
		
	}
	
	private void resetPlan() {
		List<FitnessPlan> fitPlans = null;
		try {
			fitPlans = fitPlanDao.queryForEq("name", PLAN_NAME);
		} catch (SQLException e) {
			throw new RuntimeException("Could not lookup FitnessPlan in the database", e);
		}
		
		if(fitPlans.isEmpty()) {
			generateStrongLifts();
		}
		else {
			Context cntx = StrongLiftsActivity.this;
			AlertDialog.Builder builder = new AlertDialog.Builder(cntx);
			builder.setMessage(R.string.fitness_plan_reset_dlg_msg)
		       .setTitle(R.string.fitness_plan_reset_dlg_title);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User clicked OK button
		        	   generateStrongLifts();
		           }
		       });
			builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User cancelled the dialog
		        	   dialog.cancel();
		           }
		       });
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		
	}
	private void clearStrongLifts() {
		List<FitnessPlan> fitPlans = null;
		try {
			fitPlans = fitPlanDao.queryForEq("name", PLAN_NAME);
			for( FitnessPlan fp:fitPlans) {
				Collection<WorkoutPlan> workPlans = workPlanDao.queryForEq(WorkoutPlan.FITNESS_PLAN_ID_FIELD_NAME,fp.getPlanId());
				workPlanDao.delete(workPlans);
			}
			fitPlanDao.delete(fitPlans);
		} catch (SQLException e) {
			throw new RuntimeException("Could not lookup FitnessPlan in the database", e);
		}
	}
	private void generateStrongLifts(){
		clearStrongLifts();
		FitnessPlan slPlan = new FitnessPlan(PLAN_NAME);
		
		// Create the days
		WorkoutPlan aPlan = new WorkoutPlan("Day A");
		WorkoutPlan bPlan = new WorkoutPlan("Day B");
		
		// Create the exercises
		// TODO: modify descriptions
		Exercise squat = new Exercise("Barbell Squat", "Put the bar on your back, squat down, and stand up.");
		Exercise bench = new Exercise("Bench Press", "Lying down, bring the bar down to your chest and push up, extending arms fully.");
		Exercise row   = new Exercise("Pendlay Row", "Bent over, with back straight, raise bar to chest.");
		Exercise ohp   = new Exercise("Over-Head Press", "Standing up, bring the bar down to your chin and push upward, extending arms fully.");
		Exercise dl    = new Exercise("Deadlift", "Bent over, with back straight and knees bent, begin to straighten legs, lifting the bar. Then thrust your hips forward while lifting the bar.");
		
		ExerciseLog squatLog = new ExerciseLog(squat);
		ExerciseLog benchLog = new ExerciseLog(bench);
		ExerciseLog rowLog = new ExerciseLog(row);
		ExerciseLog ohpLog = new ExerciseLog(ohp);
		ExerciseLog dlLog = new ExerciseLog(dl);
		
		// Make the starting weights and reps
		ExerciseSet mainSet = new ExerciseSet(5, 45.0f);
		ExerciseSet dlSet = new ExerciseSet(5, 135.0f);
		
		// Start database entry
		try {
			fitPlanDao.create(slPlan);
			//workPlanDao.create(aPlan);
			//workPlanDao.create(bPlan);
			ForeignCollection<WorkoutPlan> workPlans = fitPlanDao.queryForId(slPlan.getPlanId()).getWorkouts();
			workPlans.add(aPlan);
			workPlans.add(bPlan);
			
			/*exDao.create(squat);
			exDao.create(bench);
			exDao.create(row);
			exDao.create(ohp);
			exDao.create(dl);
			
			exLogDao.create(squatLog);
			exLogDao.create(benchLog);
			exLogDao.create(rowLog);
			exLogDao.create(ohpLog);
			exLogDao.create(dlLog);*/
			
			ForeignCollection<ExerciseLog> exerciseLogs = workPlanDao.queryForId(aPlan.getPlanId()).getExerciseLogs();
			exerciseLogs.add(squatLog);
			exerciseLogs.add(benchLog);
			exerciseLogs.add(rowLog);
			exerciseLogs = workPlanDao.queryForId(bPlan.getPlanId()).getExerciseLogs();
			exerciseLogs.add(squatLog);
			exerciseLogs.add(ohpLog);
			exerciseLogs.add(dlLog);
			
			//exSetDao.create(mainSet);
			//exSetDao.create(dlSet);
			Integer mainId = mainSet.getId();
			ForeignCollection<ExerciseSet> exerciseSetsSquat = exLogDao.queryForId(squatLog.getId()).getSets();
			ForeignCollection<ExerciseSet> exerciseSetsBench = exLogDao.queryForId(benchLog.getId()).getSets();
			ForeignCollection<ExerciseSet> exerciseSetsRow = exLogDao.queryForId(rowLog.getId()).getSets();
			ForeignCollection<ExerciseSet> exerciseSetsOhp = exLogDao.queryForId(ohpLog.getId()).getSets();
			ForeignCollection<ExerciseSet> exerciseSetsDl = exLogDao.queryForId(dlLog.getId()).getSets();
			for(int i = 0; i < 5; i++) {
				exerciseSetsSquat.add(mainSet);
				exerciseSetsBench.add(mainSet);
				exerciseSetsRow.add(mainSet);
				exerciseSetsOhp.add(mainSet);
			}
			exerciseSetsDl.add(dlSet);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new RuntimeException("Could not add strong lifts to the database", e1);
		}
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.strong_lifts, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
