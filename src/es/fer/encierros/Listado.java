package es.fer.encierros;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.google.api.client.util.DateTime;
import es.fer.encierros.listado.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class Listado extends Activity {   	
	private String location; 
	private String startDate; 
	private String endDate; 
	
    public Listado CustomListView = null;
    public ArrayList<ListModel> EventList = new ArrayList<ListModel>();
    public static ProgressDialog pDialog;
    public static ListView list;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);        
        
		if (MainActivity.calendar == null){
			Intent i = getBaseContext().getPackageManager()
		             .getLaunchIntentForPackage( getBaseContext().getPackageName() );
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			
		    //finalizamos la actividad actual
	        this.finish();
		}
		else{
	        pDialog = new ProgressDialog(this);
	        pDialog.setMessage("Cargando");
	        pDialog.setCancelable(false);
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.show();
	         
			Bundle parametros = this.getIntent().getExtras(); //Definimos el contenedor de parametros
			location = parametros.getString("Location"); 
			startDate = parametros.getString("StartDate"); 
			endDate = parametros.getString("EndDate"); 
			 
			TextView campo_texto = (TextView) findViewById(R.id.ICalUID);
			campo_texto.setText(location + " del " + startDate + " al " + endDate); //Mostramos el texto
			
			CustomListView = this;
			
			try {
				callAsynctask();
			} catch (ParseException e) {
				e.printStackTrace();
			}     
		}
    }
	
	
	private void callAsynctask() throws ParseException {			
		CustomAdapter adapter = new CustomAdapter( CustomListView, EventList, getResources() );
		list = (ListView)findViewById(R.id.list);
		list.setVisibility(View.INVISIBLE);
		list.setAdapter( adapter );                              
		              
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date fechaStart = null;
		Date fechaEnd = null;
		try {
			fechaStart = formatoDelTexto.parse(startDate + " 00:00:00");
			fechaEnd = formatoDelTexto.parse(endDate + " 23:59:59");
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		DateTime _startDate = new DateTime(fechaStart);
		DateTime _endDate = new DateTime(fechaEnd);
        
        GetEventList loadEventData = new GetEventList(EventList, adapter, location, _startDate, _endDate);
        loadEventData.execute(MainActivity.calendar); 
	}
    

   /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {
        ListModel event = ( ListModel ) EventList.get(mPosition);
    	    	
        //Define el bundle
        Bundle parametros = new Bundle();
        parametros.putString("id", event.getId());
        
        Intent i = new Intent();
        i.setClass(getApplicationContext(), Details.class);
        i.putExtras(parametros);        
        startActivity(i);	  
    }
}