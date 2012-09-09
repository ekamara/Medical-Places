package ug.medicalplaces.android.activities;

import java.util.ArrayList;
import java.util.List;

import ug.medicalplaces.android.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class InsuranceProvidersActivity extends ListActivity {
	List<String> options;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insurance_providers);
		
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setTitle("Insurance Providers");
		actionBar.setHomeAction(new IntentAction(this, MainActivity.createIntent(this), R.drawable.ic_menu_home));
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		  options = new ArrayList<String>();
	        options.add("Jubilee Insurance");
	        options.add("AAR");  
	        options.add("IAA");
	        options.add("Bupa");

	        setListAdapter(new OptionsAdapter());
	}
	
	
	  private class OptionsAdapter extends ArrayAdapter<String> {
	    	OptionsAdapter() {
	    		super(InsuranceProvidersActivity.this, R.layout.options_list, R.id.select_option, options);
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
