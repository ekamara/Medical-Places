package ug.medicalplaces.android.activities;

import java.util.ArrayList;
import java.util.List;

import ug.medicalplaces.android.R;
//import ug.medicalplaces.android.activities.InsuranceProvidersActivity.OptionsAdapter;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListPlacesActivity extends ListActivity {
	
	private String selectedCategory;
	private List<String> options;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categories);
		
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setTitle("Insurance Providers");
		actionBar.setHomeAction(new IntentAction(this, MainActivity.createIntent(this), R.drawable.ic_menu_home));
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		selectedCategory = getIntent().getStringExtra("selectedCategory");
		
		  options = new ArrayList<String>();
	        options.add("General Practitioner Clinic");
	        options.add("BS Dental");  
	        options.add("Ear Health Clinic");

	        setListAdapter(new OptionsAdapter());
	}

    protected void onListItemClick(ListView l, View v, int position, long id) {
    	String selectedOption = options.get(position).toString();
    	Intent intent = new Intent(this, PlacesDetailActivity.class);
    	intent.putExtra("selectedPlace", selectedOption);
		startActivity(intent);
    }
	  private class OptionsAdapter extends ArrayAdapter<String> {
	    	OptionsAdapter() {
	    		super(ListPlacesActivity.this, R.layout.options_list, R.id.select_option, options);
	    	}
	    	
	    	@Override
	    	public View getView(int position, View convertView, ViewGroup parent) {
	    		LayoutInflater inflater = getLayoutInflater();
	    		
	    		View row = inflater.inflate(R.layout.options_list, parent, false);
	    		
	    		TextView optionView = (TextView) row.findViewById(R.id.select_option);
				optionView.setText(options.get(position).toString());
				return row;
	    	}
	    }
}
