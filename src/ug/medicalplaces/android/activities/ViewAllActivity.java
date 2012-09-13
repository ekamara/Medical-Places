package ug.medicalplaces.android.activities;

import java.util.ArrayList;
import java.util.HashMap;

import ug.medicalplaces.android.R;
import ug.medicalplaces.android.utilities.AlertDialogManager;
import ug.medicalplaces.android.utilities.ConnectionDetector;
import ug.medicalplaces.android.utilities.GPSTracker;
import ug.medicalplaces.android.utilities.GooglePlaces;
import ug.medicalplaces.android.utilities.Place;
import ug.medicalplaces.android.utilities.PlacesList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ViewAllActivity extends Activity {

	Boolean isInternetPresent = false;
	
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();
	GooglePlaces googlePlaces;
	PlacesList nearPlaces;
	GPSTracker gps;
	Button btnShowOnMap;
	ProgressDialog pDialog;
	ListView lv;
	ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String, String>>();
	
	public static String KEY_REFERENCE = "reference";
	public static String KEY_NAME = "name";
	public static String KEY_VICINITY = "vicinity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		cd = new ConnectionDetector(getApplicationContext());
		
		isInternetPresent = cd.isConnectingToInternet();
		if(!isInternetPresent) {
			alert.showAlertDialog(ViewAllActivity.this, "Internet Connection Error", "Please connect to working internet connection", false);
			return;
		}
		
		gps = new GPSTracker(this);
		if(gps.canGetLocation()) {
			Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
		} else {
	          alert.showAlertDialog(ViewAllActivity.this, "GPS Status",
	                    "Couldn't get location information. Please enable GPS",
	                    false);
	          return;
		}
 
        // calling background Async task to load Google Places
        // After getting places from Google all the data is shown in listview
        new LoadPlaces().execute();
 
    }
 
    /**
     * Background Async Task to Load Google places
     * */
    class LoadPlaces extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewAllActivity.this);
            pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting Places JSON
         * */
        protected String doInBackground(String... args) {
            // creating Places class object
            googlePlaces = new GooglePlaces();
 
            try {
                // Separeate your place types by PIPE symbol "|"
                // If you want all types places make it as null
                // Check list of types supported by google
                //
                //String types = "cafe|restaurant"; // Listing places only cafes, restaurants
            	String types = null;
                // Radius in meters - increase this value if you don't find any places
                double radius = 1000; // 1000 meters 
 
                // get nearest places
               // nearPlaces = googlePlaces.search(gps.getLatitude(),
                 //       gps.getLongitude(), radius, types);
                nearPlaces = googlePlaces.search(37.46, -122.26, radius, types);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * and show the data in UI
         * Always use runOnUiThread(new Runnable()) to update UI from background
         * thread, otherwise you will get error
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed Places into LISTVIEW
                     * */
                    // Get json response status
                    String status = nearPlaces.status;
                    Log.d("status", status);
 
                    // Check for all possible status
                    if(status.equals("OK")){
                        // Successfully got places details
                        if (nearPlaces.results != null) {
                            // loop through each place
                            for (Place p : nearPlaces.results) {
                                HashMap<String, String> map = new HashMap<String, String>();
 
                                // Place reference won't display in list view - it will be hidden
                                // Place reference is used to get "place full details"
                                map.put(KEY_REFERENCE, p.reference);
 
                                // Place name
                                map.put(KEY_NAME, p.name);
 
                                // adding HashMap to ArrayList
                                placesListItems.add(map);
                            }

                            Intent i = new Intent(getApplicationContext(),
                                    PlacesMapActivity.class);
                            // Sending user current geo location
                           // i.putExtra("user_latitude", Double.toString(gps.getLatitude()));
                           // i.putExtra("user_longitude", Double.toString(gps.getLongitude()));
                            i.putExtra("user_latitude", Double.toString(37.46));
                            i.putExtra("user_longitude", Double.toString(-122.26));
                            // passing near places to map activity
                            i.putExtra("near_places", nearPlaces);
                            // staring activity
                            startActivity(i);
                        }
                    }
                    else if(status.equals("ZERO_RESULTS")){
                        // Zero results found
                        alert.showAlertDialog(ViewAllActivity.this, "Near Places",
                                "Sorry no places found. Try to change the types of places",
                                false);
                    }
                    else if(status.equals("UNKNOWN_ERROR"))
                    {
                        alert.showAlertDialog(ViewAllActivity.this, "Places Error",
                                "Sorry unknown error occured.",
                                false);
                    }
                    else if(status.equals("OVER_QUERY_LIMIT"))
                    {
                        alert.showAlertDialog(ViewAllActivity.this, "Places Error",
                                "Sorry query limit to google places is reached",
                                false);
                    }
                    else if(status.equals("REQUEST_DENIED"))
                    {
                        alert.showAlertDialog(ViewAllActivity.this, "Places Error",
                                "Sorry error occured. Request is denied",
                                false);
                    }
                    else if(status.equals("INVALID_REQUEST"))
                    {
                        alert.showAlertDialog(ViewAllActivity.this, "Places Error",
                                "Sorry error occured. Invalid Request",
                                false);
                    }
                    else
                    {
                        alert.showAlertDialog(ViewAllActivity.this, "Places Error",
                                "Sorry error occured.",
                                false);
                    }
                }
            });
 
        }
 
    }
 /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/

}
