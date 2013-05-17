package com.dmham.liftstrong;

import java.sql.SQLException;
import java.text.DecimalFormat;
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
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.format.Time;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
	private int width, height, cx, cy;
	
	private Collection<ExerciseLog> exLogs;
	
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
		
		exLogs = (Collection<ExerciseLog>) workPlan.getExerciseLogs();
		
		LinearLayout exerciseLogLayout = (LinearLayout)findViewById(R.id.ll_logList);
		
		Resources resources = getResources();
		int px = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, resources.getDisplayMetrics()));
		width = px;
		height = px;
		cx = width/2;
		cy = height/2;
		Paint paintGray = new Paint();
		paintGray.setColor(Color.LTGRAY);
		
      // Update the TextView Text
		for(ExerciseLog log : exLogs) {
		//ExerciseLog log = (ExerciseLog) exLogs.toArray()[0];
			final View logView = getLayoutInflater().inflate(R.layout.exercise_log_item, exerciseLogLayout,false);
			TextView tv_exName   = (TextView) logView.findViewById(R.id.tv_exName);
			
			tv_exName.setText(log.getExercise().getName());

			LinearLayout exerciseSetLayout = (LinearLayout)logView.findViewById(R.id.ll_setList);
			
	        // Update the TextView Text
			for(final ExerciseSet set : log.getSets()) {
				LayoutInflater logInflater = LayoutInflater.from(logView.getContext());
				final View setView = logInflater.inflate(R.layout.exercise_set_item, exerciseSetLayout,false);
				final TextView tv_reps   = (TextView) setView.findViewById(R.id.ex_set_reps);
				final TextView tv_weight = (TextView) setView.findViewById(R.id.ex_set_weight);
				final ImageView iv_img = (ImageView) setView.findViewById(R.id.ex_set_img);
				iv_img.setEnabled(false);
				
				final Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

				final Canvas c = new Canvas(bmp);
				c.drawCircle(cx, cy, cx, paintGray);
				iv_img.setImageBitmap(bmp);
				
				/*
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
					draw(iv_img, resources, bmp);
				else
					drawDep(iv_img, resources, bmp);
					*/
				
				tv_reps.setText(Integer.toString(set.getReps()));
		        tv_weight.setText( Float.toString(set.getWeight()) );
		        setView.setOnFocusChangeListener(new View.OnFocusChangeListener() {				
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						set.setReps(Integer.parseInt(tv_reps.getText().toString()));
						set.setWeight(Float.parseFloat(tv_weight.getText().toString()));
						set.setEnabled(true);
					}
				});
		        setView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(!iv_img.isEnabled()) {
							enableSet(iv_img, c, bmp);
						}
						else {
							iv_img.setImageBitmap(bmp);
							TextView tv_reps   = (TextView) v.findViewById(R.id.ex_set_reps);
							int reps = Integer.parseInt(tv_reps.getText().toString() );
							if(reps > 0) {
								tv_reps.setText(String.valueOf( reps - 1 ));
							}
							else {
								setWeightAndReps(context,v);
							}
						}
					}
				});
		        setView.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						if(!iv_img.isEnabled()) {
							enableSet(iv_img, c, bmp);
						}
						setWeightAndReps(context,v);
						return true;
					}
				});
	     
				exerciseSetLayout.addView(setView);
			} 
			
			exerciseLogLayout.addView(logView);
		}
		
		Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveWorkout();
			}
		});
		
		
	}
	
	private void enableSet(ImageView iv_img, Canvas c, Bitmap bmp) {
		if(iv_img == null)
			return;
		Paint paintEnabled = new Paint();
		paintEnabled.setColor(0xdd00dd00);

		c.drawCircle(cx, cy, cx, paintEnabled);
		
		iv_img.setImageBitmap(bmp);
		iv_img.setEnabled(true);
	}
	
	private void setWeightAndReps(Context context, View v) {
		final TextView tv_weight   = (TextView) v.findViewById(R.id.ex_set_weight);
		final TextView tv_reps   = (TextView) v.findViewById(R.id.ex_set_reps);
		View layout = getLayoutInflater().inflate(R.layout.exercise_set_form, (ViewGroup) v, false);
		final EditText et_weight = (EditText) layout.findViewById(R.id.et_weight);
		final EditText et_reps = (EditText) layout.findViewById(R.id.et_reps);
		
		et_weight.setText(tv_weight.getText());
		et_reps.setText(tv_reps.getText());
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(layout);
		//builder.setView(et_weight);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               // User clicked OK button
	        	   // Set weight and reps in setView to form value
	        	   DecimalFormat format = new DecimalFormat("##.#");
	        	   try {
		        	   Float weight = Float.parseFloat(et_weight.getText().toString());
		        	   if(weight > 0.0 && weight < 1200.0)
		        		   tv_weight.setText(format.format(weight));
	        	   }
	        	   catch (NumberFormatException e) {
	        		   
	        	   }
	        	   try {
		        	   int numReps = Integer.parseInt(et_reps.getText().toString());
		        	   if(numReps > 0 && numReps < 100)
		        		   tv_reps.setText(et_reps.getText());
	        	   }
	        	   catch (NumberFormatException e) {
	        		   
	        	   }
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

	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
	private void drawDep(ImageView iv_img, Resources resources, Bitmap bmp) {
		iv_img.setBackgroundDrawable(new BitmapDrawable(resources, bmp));
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void draw(ImageView iv_img, Resources resources, Bitmap bmp) {
		iv_img.setBackground(new BitmapDrawable(resources, bmp));
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
				workPlan = null;
				for(WorkoutPlan wp : workPlans) {
					if(found) {
						workPlan = wp;
					}
					else if(wp.getPlanId() == lastWorkPlan.getPlanId()) {
						found = true;
					}
				}
				// If the last log was the last plan in the list, start at the beginning again
				if( workPlan == null ) {
					workPlan = workPlans.get(0);
				}
			}
			else {
				// No stored workout logs. Get first workout plan in selected fitness plan
				workPlan = workPlans.get(0);
			}
		}
		
		if(workPlan == null) {
			// TODO this will cause an error I think
			this.finish();
		}
		
		workLog.setWorkoutPlan(workPlan);
		
	}
	
	private void saveWorkout() {
		ForeignCollection<ExerciseLog> newExLogs = null;
		try {
			workLog.setWorkoutPlan(workPlan);
			workLogDao.create(workLog);
			newExLogs = workLogDao.queryForId(workLog.getId()).getExerciseLogs();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(newExLogs == null)
			return;
		for(ExerciseLog log : exLogs) {
			ForeignCollection<ExerciseSet> newExSets = null;
			try {
				ExerciseLog cLog = new ExerciseLog(log);
				cLog.setWorkoutPlan(workPlan);
				newExLogs.add(cLog);
				newExSets = exLogDao.queryForId(cLog.getId()).getSets();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(newExSets == null)
				continue;
			for(final ExerciseSet set : log.getSets()) {
				if(set.isEnabled()) {
					newExSets.add(set);
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log, menu);
		return true;
	}

}
