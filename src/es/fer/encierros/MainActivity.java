package es.fer.encierros;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import es.fer.encierros.funciones.InputStreamToFile;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static Calendar calendar;
	
	/**
	 * Be sure to specify the name of your application. If the application name is {@code null} or
	 * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
	 */
	private static final String APPLICATION_NAME = "Encierros/1.0";
	  
	/** E-mail address of the service account. */
	private static final String SERVICE_ACCOUNT_EMAIL = "857970891893-trh841l1jduub5skdl5np3b50khmdp5b@developer.gserviceaccount.com";

	/** Global instance of the HTTP transport. */
	private static HttpTransport httpTransport;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		
		//Vamos a declarar un nuevo thread
        Thread timer = new Thread(){
        	//El nuevo Thread exige el metodo run
        	public void run(){
        		Boolean result = false;
        		if (isDeviceOnline()){
        			GoogleApiConnect googleApiConnect = new GoogleApiConnect();
        			googleApiConnect.execute(); 
        			try {
        				result = googleApiConnect.get();
        			} catch (InterruptedException e1) {
        				e1.printStackTrace();
        			} catch (ExecutionException e1) {
        				e1.printStackTrace();
        			}	

        			if (result){
        				Intent i = new Intent();
        				i.setClass(getApplicationContext(), Busqueda.class);
        				startActivity(i);	
        			}

        		}
        		else{
        			TextView Status = (TextView) findViewById(R.id.status);
        			Status.setText("Esta aplicacion requiere conexion a la red");
        		}                                 
            }
        };
        //ejecuto el thread
        timer.start();
	}
	
	private boolean isDeviceOnline() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}
	
	public class GoogleApiConnect extends AsyncTask<Void, Void, Boolean>{
		@Override
	    protected Boolean doInBackground(Void... params) {
			try {
				try {
					httpTransport = AndroidHttp.newCompatibleTransport();				
					AssetManager assetManager = getAssets();				
				    InputStream ims = assetManager.open("Encierros.p12");
				    InputStreamToFile.createFile(ims);
					// service account credential (uncomment setServiceAccountUser for domain-wide delegation)
					GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
							.setJsonFactory(JSON_FACTORY)
							.setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
							.setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR_READONLY))
							.setServiceAccountPrivateKeyFromP12File(new File(Environment.getExternalStorageDirectory().getPath()+"/.data/encierros/sec/Encierros.p12"))
							// 	.setServiceAccountUser("user@example.com")
							.build();
					// 	set up global Plus instance
					calendar = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
					.setApplicationName(APPLICATION_NAME).build();

					// success!
					return true;
				} catch (IOException e) {
					System.err.println(e.getMessage());	
					return false;
				}
			} catch (Throwable t) {
				t.printStackTrace();
				return false;
			}
		}
	}


}
