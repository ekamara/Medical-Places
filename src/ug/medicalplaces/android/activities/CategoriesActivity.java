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

public class CategoriesActivity extends ListActivity {
	
	private String selectedProvider;
	private List<String> options;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categories);
		
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setTitle("Insurance Providers");
		actionBar.setHomeAction(new IntentAction(this, MainActivity.createIntent(this), R.drawable.ic_menu_home));
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		selectedProvider = getIntent().getStringExtra("selectedProvider");
		
		  options = new ArrayList<String>();
	        options.add("Dentist");
	        options.add("ENT");  
	        options.add("General");

	        setListAdapter(new OptionsAdapter());
	}
	
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	String selectedOption = options.get(position).toString();
    	Intent intent = new Intent(this, ListPlacesActivity.class);
    	if (selectedOption.equalsIgnoreCase("dentist")) {
    		intent.putExtra("selectedCategory", "dentist");
    	} else if (selectedOption.equalsIgnoreCase("ent")) {
    		intent.putExtra("selectedCategory", "ent");
    	}
		startActivity(intent);
    }

	  private class OptionsAdapter extends ArrayAdapter<String> {
	    	OptionsAdapter() {
	    		super(CategoriesActivity.this, R.layout.options_list, R.id.select_option, options);
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
