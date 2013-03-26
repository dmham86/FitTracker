package com.dmham.liftstrong;

import java.util.ArrayList;

import com.dmham.liftstrong.objects.ExerciseSet;
import com.dmham.liftstrong.objects.MenuItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LogActivity extends Activity {
	ArrayList<ExerciseSet> sets = new ArrayList<ExerciseSet>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		
		Context context = getApplicationContext();
		
		addSets();
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout exerciseSetLayout = (LinearLayout)findViewById(R.id.ll_log);
		
        // Update the TextView Text
		for(ExerciseSet set : sets) {
			final View view = getLayoutInflater().inflate(R.layout.exercise_set_item, exerciseSetLayout,false);
			TextView tv_reps   = (TextView) view.findViewById(R.id.ex_set_reps);
			TextView tv_weight = (TextView) view.findViewById(R.id.ex_set_weight);
			ImageView iv_img = (ImageView) view.findViewById(R.id.menu_item_img);
			
			tv_reps.setText(set.getReps());
	        tv_weight.setText( Float.toString(set.getWeight()) );
	        view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});
     
			exerciseSetLayout.addView(view);
		}
       
	}

	private void addSets() {
		ExerciseSet set1 = new ExerciseSet(5, 150.0f);
		ExerciseSet set2 = new ExerciseSet(5, 200.0f);
		sets.add(set1);
		sets.add(set2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log, menu);
		return true;
	}

}
