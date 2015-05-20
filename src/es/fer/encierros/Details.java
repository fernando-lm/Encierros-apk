package es.fer.encierros;

import java.util.concurrent.ExecutionException;
import com.google.api.services.calendar.model.Event;
import es.fer.encierros.details.FragmentInfo;
import es.fer.encierros.details.FragmentMap;
import es.fer.encierros.details.GetEventById;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class Details extends FragmentActivity {
	 private FragmentTabHost mTabHost;
	 String eventID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		if (MainActivity.calendar == null){
			Intent i = getBaseContext().getPackageManager()
		             .getLaunchIntentForPackage( getBaseContext().getPackageName() );
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			
		    //finalizamos la actividad actual
	        this.finish();
		}
		else{
			Bundle parametros = this.getIntent().getExtras(); //Definimos el contenedor de parametros
			eventID = parametros.getString("id"); 
	   
			GetEventById loadEventData = new GetEventById(eventID);
			loadEventData.execute(MainActivity.calendar); 
					
			mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
	        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);     
	        
	        Event event = null;
			try {
				event = loadEventData.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
	        Bundle args1 = new Bundle();
	        args1.putString("Location", event.getLocation());
	        args1.putString("Description", event.getDescription());
	        args1.putString("Summary", event.getSummary());
	        args1.putString("Date", event.getStart().toString());        
	        Bundle args2 = new Bundle();
	        args2.putString("Location", event.getLocation());
	        
	        mTabHost.addTab(
	                mTabHost.newTabSpec("detalles").setIndicator("Detalles", null),
	                FragmentInfo.class, args1);
	        mTabHost.addTab(
	                mTabHost.newTabSpec("mapa").setIndicator("Mapa", null),
	                FragmentMap.class, args2);
	
		}
	}
}
