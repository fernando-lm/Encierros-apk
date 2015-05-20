package es.fer.encierros.details;

import java.io.IOException;
import java.util.List;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import android.os.AsyncTask;

public class GetEventById extends AsyncTask<Calendar, Void, Event> {
	String _eventID;
	
	public GetEventById(String eventID){
	    this._eventID = eventID;
	}
	
	/**
     * Executes the asynchronous job. This runs when you call execute()
     * on the AsyncTask instance.
     */
    @Override
    protected Event doInBackground(Calendar... calendars) {
    	Events events;
    	
    	Calendar calendar = calendars[0];
    		
		try {    		
    				
    		events = calendar.events().list("toroonada@gmail.com").setICalUID(_eventID).execute();
    		List<Event> items = events.getItems();
    		
    		return items.get(0);
    		
        } catch (IOException e) {
        	return null;
        }		
    }
    
    @Override
    protected void onPostExecute(Event event) {
    	
    }
    
}
