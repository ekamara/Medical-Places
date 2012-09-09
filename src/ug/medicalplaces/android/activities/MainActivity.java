package ug.medicalplaces.android.activities;

import ug.medicalplaces.android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class MainActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setTitle("Medical Places");
        
        final Action shareAction = new IntentAction(this, createSearchIntent(), R.drawable.ic_search);
        actionBar.addAction(shareAction);
        
        DashboardClickListener dbClickListener = new DashboardClickListener();
        findViewById(R.id.btn_browse_places).setOnClickListener(dbClickListener);
        findViewById(R.id.btn_near_me).setOnClickListener(dbClickListener);
        findViewById(R.id.btn_view_all).setOnClickListener(dbClickListener);
        findViewById(R.id.btn_insurance_providers).setOnClickListener(dbClickListener);
        
    }

	private Intent createSearchIntent() {
		// TODO Auto-generated method stub
		return null;
	}
	
    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }
	private class DashboardClickListener implements OnClickListener {
	    @Override
	    public void onClick(View v) {
	        Intent i = null;
	        switch (v.getId()) {
	            case R.id.btn_browse_places:
	                //i = new Intent(DashboardActivity.this, AddCapture.class);
	                break;
	            case R.id.btn_near_me:
	                //i = new Intent(DashboardActivity.this, ViewAll.class);
	                break;
	            case R.id.btn_view_all:
	                //i = new Intent(DashboardActivity.this, Manage.class);
	                break;
	            case R.id.btn_insurance_providers:
	                i = new Intent(MainActivity.this, InsuranceProvidersActivity.class);
	                break;
	            default:
	                break;
	        }
	        if(i != null) {
	            startActivity(i);
	        }
	    }
	}
}
