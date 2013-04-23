package com.dmham.liftstrong;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.dmham.lifstrong.database.DatabaseHelper;
import com.dmham.lifstrong.plans.StrongLiftsActivity;
import com.dmham.liftstrong.objects.Exercise;
import com.dmham.liftstrong.objects.ExerciseLog;
import com.dmham.liftstrong.objects.ExerciseSet;
import com.dmham.liftstrong.objects.FitnessPlan;
import com.dmham.liftstrong.objects.MenuItem;
import com.dmham.liftstrong.objects.WorkoutLog;
import com.dmham.liftstrong.objects.WorkoutPlan;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LogActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	private static final String TAG = "LogActivity";
	
	private Dao<FitnessPlan, Integer> fitPlanDao;
	private Dao<WorkoutLog, Integer> workLogDao;
	private Dao<WorkoutPlan, Integer> workPlanDao;
	private Dao<ExerciseLog, Integer> exLogDao;
	private Dao<ExerciseSet, Integer> exSetDao;
	private Dao<Exercise, Integer> exDao;
	
	private WorkoutLog workLog;
	private WorkoutPlan workPlan;
	
	ArrayList<ExerciseSet> sets = new ArrayList<ExerciseSet>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		
		final Context context = this;
		
		workLog = new WorkoutLog(new Date());
		
		try {
			fitPlanDao = getHelper().getFitnessPlanDao();
			workLogDao = getHelper().getWorkoutLogDao();
			workPlanDao = getHelper().getWorkoutPlanDao();
			exLogDao = getHelper().getExerciseLogDao();
			exSetDao = getHelper().getExerciseSetDao();
			exDao = getHelper().getExerciseDao();
			getWorkoutPlan();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TextView tv_fitPlanName = (TextView)findViewById(R.id.tv_fitPlanName);
		TextView tv_workPlanName = (TextView)findViewById(R.id.tv_workPlanName);
		tv_fitPlanName.setText(workPlan.getFitnessPlan().getName());
		tv_workPlanName.setText(workPlan.getName());
		
		Collection<ExerciseLog> exLogs = (Collection<ExerciseLog>) workPlan.getExerciseLogs();
		
		LinearLayout exerciseLogLayout = (LinearLayout)findViewById(R.id.ll_logList);
		
      // Update the TextView Text
		for(ExerciseLog log : exLogs) {
		//ExerciseLog log = (ExerciseLog) exLogs.toArray()[0];
			final View logView = getLayoutInflater().inflate(R.layout.exercise_log_item, exerciseLogLayout,false);
			TextView tv_exName   = (TextView) logView.findViewById(R.id.tv_exName);
			
			tv_exName.setText(log.getExercise().getName());

			LinearLayout exerciseSetLayout = (LinearLayout)logView.findViewById(R.id.ll_setList);
			
	        // Update the TextView Text
			for(ExerciseSet set : log.getSets()) {
				LayoutInflater logInflater = LayoutInflater.from(logView.getContext());
				final View setView = logInflater.inflate(R.layout.exercise_set_item, exerciseSetLayout,false);
				TextView tv_reps   = (TextView) setView.findViewById(R.id.ex_set_reps);
				TextView tv_weight = (TextView) setView.findViewById(R.id.ex_set_weight);
				ImageView iv_img = (ImageView) setView.findViewById(R.id.menu_item_img);
				
				tv_reps.setText(Integer.toString(set.getReps()));
		        tv_weight.setText( Float.toString(set.getWeight()) );
		        setView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						TextView tv_reps   = (TextView) v.findViewById(R.id.ex_set_reps);
						tv_reps.setText(String.valueOf(Integer.parseInt(tv_reps.getText().toString() ) + 1 ));
					}
				});
		        setView.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						final TextView tv_weight   = (TextView) v.findViewById(R.id.ex_set_weight);
						final EditText et_weight = new EditText(context);
						
						// Show the number keyboard
						et_weight.setInputType(InputType.TYPE_CLASS_PHONE);
						et_weight.setRawInputType(InputType.TYPE_CLASS_PHONE);
						
						et_weight.setText(tv_weight.getText());
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder.setMessage("Weight")
					       .setTitle("Enter Weight");
						builder.setView(et_weight);
						builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					               // User clicked OK button
					        	   tv_weight.setText(et_weight.getText());
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
						return true;
					}
				});
	     
				exerciseSetLayout.addView(setView);
			} 
			
			exerciseLogLayout.addView(logView);
		}
		
       
	}

	private void getWorkoutPlan() throws SQLException {
		List<FitnessPlan> fitPlans = fitPlanDao.queryForEq(FitnessPlan.ID_FIELD_NAME, Integer.valueOf(1));
		if(fitPlans.size() > 0) {
			FitnessPlan fitPlan = fitPlans.get(0);
			QueryBuilder<WorkoutPlan, Integer> planBuilder = workPlanDao.queryBuilder();
			QueryBuilder<WorkoutLog, Integer> logBuilder = workLogDao.queryBuilder();
			planBuilder.where().eq(WorkoutPlan.FITNESS_PLAN_ID_FIELD_NAME, fitPlan.getPlanId());
			logBuilder.join(planBuilder);
			PreparedQuery<WorkoutLog> getReverseLogs = logBuilder.orderBy(WorkoutLog.DATE_FIELD_NAME, false).prepare();
			WorkoutLog lastWorkLog = workLogDao.queryForFirst(getReverseLogs);


			List<WorkoutPlan> workPlans = workPlanDao.query(planBuilder.prepare());
			if(lastWorkLog != null) { // If we found a previous log, get the next workoutPlan
				WorkoutPlan lastWorkPlan = lastWorkLog.getWorkoutPlan();
				boolean found = false;
				for(WorkoutPlan wp : workPlans) {
					if(found) {
						workPlan = wp;
					}
					else if(wp.equals(lastWorkPlan)) {
						found = true;
					}
				}
				// If the last log was the last plan in the list, start at the beginning again
				if( !found ) {
					workPlan = workPlans.get(0);
				}
			}
			else {
				// No stored workout logs. Get first workout plan in selected fitness plan
				workPlan = workPlans.get(0);
			}
		}
		
		if(workPlan == null) {
			this.finish();
		}
		
		workLog.setWorkoutPlan(workPlan);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log, menu);
		return true;
	}

}
