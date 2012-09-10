package ug.medicalplaces.android.activities;

import java.util.ArrayList;

import ug.medicalplaces.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class PlacesDetailActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places_detail);
		
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setTitle(getIntent().getStringExtra("selectedPlace"));
		actionBar.setHomeAction(new IntentAction(this, MainActivity.createIntent(this), R.drawable.ic_menu_home));
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();
		
		TabSpec spec1 = tabHost.newTabSpec("Name");
		spec1.setIndicator("Name", getResources().getDrawable(R.drawable.ic_dialog_map));
		spec1.setContent(R.id.tab1);
		
		TabSpec spec2 = tabHost.newTabSpec("Details");
		spec2.setIndicator("Details", getResources().getDrawable(R.drawable.ic_menu_info_details));
		spec2.setContent(R.id.tab2);
		
		TabSpec spec3 = tabHost.newTabSpec("Map");
		spec3.setIndicator("Map", getResources().getDrawable(R.drawable.ic_menu_compass));
		spec3.setContent(R.id.txt3);
		
		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		tabHost.addTab(spec3);

	}
}
