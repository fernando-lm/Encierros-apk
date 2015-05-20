package es.fer.encierros;

import java.util.Calendar;
import es.fer.encierros.busqueda.DatePickerFragment;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Busqueda extends ActionBarActivity {
	public static TextView Output;
	
	Spinner Location;
	
    public static int yearStart, monthStart, dayStart;
	public static int yearEnd, monthEnd, dayEnd;
    TextView StartDate;
    TextView EndDate;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busqueda);
			    
		if (MainActivity.calendar == null){
			Intent i = getBaseContext().getPackageManager()
		             .getLaunchIntentForPackage( getBaseContext().getPackageName() );
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			
		    //finalizamos la actividad actual
	        this.finish();
		}
		else{
			Location = (Spinner) findViewById(R.id.location);
			populateLocation();
	
		    StartDate = (TextView) findViewById(R.id.StartDate);
		    EndDate = (TextView) findViewById(R.id.EndDate);
	 
	        // Get current date by calender
	         
	        final Calendar c = Calendar.getInstance();
	        yearStart  = yearEnd  = c.get(Calendar.YEAR);
	        monthStart = monthEnd = c.get(Calendar.MONTH);
	        dayStart   = dayEnd   = c.get(Calendar.DAY_OF_MONTH);
	 
	        // Show current date         
	        StartDate.setText(new StringBuilder()
	        	// Month is 0 based, just add 1
	        	.append(dayStart).append("-").append(monthStart + 1).append("-")
	        	.append(yearStart).append(" "));
	  
	        // Button listener to show date picker dialog         
	        StartDate.setOnClickListener(new OnClickListener() {
				@Override
	            public void onClick(View v) {                 
	            	Output=StartDate;
	                // On button click show datepicker dialog          
	                DialogFragment newFragment = new DatePickerFragment();
	                newFragment.show(getFragmentManager(), "datePickerStart");           
	            } 
	        });
	        
	        // Show current date         
	        EndDate.setText(new StringBuilder()
	        	// Month is 0 based, just add 1
	        	.append(dayEnd).append("-").append(monthEnd + 1).append("-")
	            .append(yearEnd).append(" "));
	  
	        // Button listener to show date picker dialog         
	        EndDate.setOnClickListener(new OnClickListener() {
				@Override
	            public void onClick(View v) {           
	                Output=EndDate;
	                // On button click show datepicker dialog 
	                DialogFragment newFragment = new DatePickerFragment();
	                newFragment.show(getFragmentManager(), "datePickerEnd");   
	            } 
	        });
		}
	}	    
	
	private void populateLocation(){
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Location, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		Location.setAdapter(adapter);
	}
			
	private void buscar(){
        //Define el bundle
        Bundle parametros = new Bundle();
        parametros.putString("Location", Location.getSelectedItem().toString());
        parametros.putString("StartDate", StartDate.getText().toString());
        parametros.putString("EndDate", EndDate.getText().toString());
        
        Intent i = new Intent( );
        i.setClass(getApplicationContext(), Listado.class);
        i.putExtras(parametros);
        startActivity(i);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	        	buscar();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onBackPressed() {	
		return;
	}
}
