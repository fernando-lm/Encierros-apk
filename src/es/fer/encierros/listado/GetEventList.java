package es.fer.encierros.listado;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import android.os.AsyncTask;
import android.view.View;

import com.google.api.services.calendar.Calendar;
import es.fer.encierros.Listado;
import es.fer.encierros.funciones.Fecha;

public class GetEventList extends AsyncTask<Calendar, String, Void>{
	ArrayList<ListModel> _list;
	CustomAdapter _adapter;
	String _location;
	DateTime _startDate;
	DateTime _endDate;
	
	public GetEventList(ArrayList<ListModel> list, CustomAdapter adapter, String location, DateTime startDate, DateTime endDate){
		this._list      = list;
	    this._adapter   = adapter;
	    this._location  = location;
	    this._startDate = startDate;
	    this._endDate   = endDate;
	}
	
	
	/**
     * Executes the asynchronous job. This runs when you call execute()
     * on the AsyncTask instance.
     */
    @Override
    protected Void doInBackground(Calendar... calendars) {
    	Events events;
		String pageToken = null;
		
		Calendar calendar = calendars[0];
		
        try {    		
    		do {    			
    		  events = calendar.events().list("toroonada@gmail.com").setQ(_location).
    				  setTimeMin(_startDate).    
    				  setTimeMax(_endDate).
    				  setOrderBy("startTime").
    				  setSingleEvents(true).
    				  setPageToken(pageToken).execute();
    		  List<Event> items = events.getItems();
    		  for (Event event : items) {   		 			  
    			  publishProgress(event.getLocation(), event.getDescription(), event.getStart().toString() ,event.getICalUID());  
    		  }
    		  pageToken = events.getNextPageToken();
    		} while (pageToken != null);

        } catch (IOException e) {

        }
        return null;
    }
    
    @Override
    protected void onProgressUpdate(String... event) {	        
    	String sDate = null;
    	ListModel item = new ListModel();
    	item.setLocation(event[0]);
    	item.setDescription(event[1]);
    	item.setId(event[3]);
    	
        JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(event[2]);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			sDate = jsonObject.getString("dateTime");
		} catch (JSONException e) {
			e.printStackTrace();
		}

        Fecha fecha = new Fecha(sDate);
        Date date = fecha.ToDate();       
        java.util.Calendar calendar = java.util.Calendar.getInstance();  
        calendar.setTime(date);
    	item.setDate(String.format("%02d", calendar.get(java.util.Calendar.DAY_OF_MONTH)) + "/" + 
    			String.format("%02d", calendar.get(java.util.Calendar.MONTH)+1) + "/" + 
    			calendar.get(java.util.Calendar.YEAR) + "\n" + 
    			String.format("%02d", calendar.get(java.util.Calendar.HOUR_OF_DAY)) + ":" + 
    			String.format("%02d", calendar.get(java.util.Calendar.MINUTE)));

        _list.add(item);
        _adapter.notifyDataSetChanged();
    }
    
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        
        if (!_list.isEmpty()){
        	Listado.list.setVisibility(View.VISIBLE);
        }
        Listado.pDialog.dismiss();
    }
}
