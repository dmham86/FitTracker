package com.dmham.liftstrong;

import java.util.ArrayList;

import com.dmham.lifstrong.plans.StrongLiftsActivity;
import com.dmham.liftstrong.objects.MenuItem;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlanSelectorActivity extends Activity {
	private static final String TAG = "PlanSelectorActivity";
	private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	private LinearLayout planOptions;
	private Bundle state = new Bundle();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null)
			state = savedInstanceState;
		
		if(state.containsKey("plan")) {
			editPlanSettings(state.getString("plan"));
		}
		else {
		
		setContentView(R.layout.activity_plan_selector);
		Resources res = getResources();
		Context context = getApplicationContext();
		
		addMainMenuItems();
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		planOptions = (LinearLayout)findViewById(R.id.ll_plan_options);
		
        // Update the TextView Text
		for(MenuItem menuItem : menuItems) {
			final View view = getLayoutInflater().inflate(R.layout.main_menu_item, planOptions,false);
			TextView tv_name = (TextView) view.findViewById(R.id.menu_item_name);
			TextView tv_desc = (TextView) view.findViewById(R.id.menu_item_desc);
			ImageView iv_img = (ImageView) view.findViewById(R.id.menu_item_img);
			
			tv_name.setText(menuItem.getName());
	        tv_desc.setText(menuItem.getDescription());
	        int imgResId = res.getIdentifier(menuItem.getImgName() , "drawable", getPackageName());
	        iv_img.setImageResource(imgResId);
	        final String action = menuItem.getClassName(); 
	        //try {
				//final Class<?> destClass = Class.forName(action);
		        view.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						//state.putString("plan", action);
						//editPlanSettings(action);
						Resources res = getResources();
						Intent i = new Intent(getApplicationContext(), StrongLiftsActivity.class);
						i.putExtra(res.getString(R.string.fitness_plan_action), res.getString(R.string.fitness_plan_action_reset));
						startActivity(i);
					}
				});
			/*} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d(TAG, "MenuItem Class not defined: " + action);
				continue;
			}*/
	     
			planOptions.addView(view);
		}
		}
       
	}

	private void addMainMenuItems() {
		MenuItem mI = new MenuItem("SL 5x5", "Strong Lifts 5x5", "pref_workout_plan.xml", "ic_launcher");
		menuItems.add(mI);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void editPlanSettings(String planName) {
		setContentView(R.layout.strong_lifts_settings);
		Button btnOk = (Button) findViewById(R.id.btnOk);
		Button btnCancel = (Button) findViewById(R.id.btnCancel);
		
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				state.remove("plan");
			}
		});
		
		/*
		// Empty the options
		planOptions.removeAllViews();
		// Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new StrongLiftsSettingsFragment())
                .commit();
                */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plan_selector, menu);
		return true;
	}
	
	@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putAll(state);
	}

}
