package com.dmham.liftstrong;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.dmham.liftstrong.objects.MenuItem;

import android.os.Bundle;
import android.app.Activity;
import android.app.LauncherActivity.ListItem;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Resources res = getResources();
		Context context = getApplicationContext();
		
		addMainMenuItems();
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout mainOptions = (LinearLayout)findViewById(R.id.ll_main_options);
		
        // Update the TextView Text
		for(MenuItem menuItem : menuItems) {
			final View view = getLayoutInflater().inflate(R.layout.main_menu_item, mainOptions,false);
			TextView tv_name = (TextView) view.findViewById(R.id.menu_item_name);
			TextView tv_desc = (TextView) view.findViewById(R.id.menu_item_desc);
			ImageView iv_img = (ImageView) view.findViewById(R.id.menu_item_img);
			
			tv_name.setText(menuItem.getName());
	        tv_desc.setText(menuItem.getDescription());
	        int imgResId = res.getIdentifier(menuItem.getImgName() , "drawable", getPackageName());
	        iv_img.setImageResource(imgResId);
	        String action = menuItem.getClassName(); 
	        try {
				final Class<?> destClass = Class.forName(action);
		        view.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(getApplicationContext(), destClass);
						startActivity(i);
					}
				});
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d(TAG, "MenuItem Class not defined: " + action);
				continue;
			}
	     
			mainOptions.addView(view);
		}
       
	}

	private void addMainMenuItems() {
		MenuItem mI = new MenuItem("Log", "Log a new activity", "com.dmham.liftstrong.LogActivity", "ic_launcher");
		menuItems.add(mI);
		mI = new MenuItem("History", "View your previous workouts", "com.dmham.liftstrong.HistoryActivity", "ic_launcher");
		menuItems.add(mI);
		mI = new MenuItem("Reset", "Start a new workout plan", "com.dmham.liftstrong.PlanSelectorActivity", "ic_launcher");
		menuItems.add(mI);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
