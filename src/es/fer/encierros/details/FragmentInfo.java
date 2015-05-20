package es.fer.encierros.details;

import java.util.Calendar;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import es.fer.encierros.R;
import es.fer.encierros.funciones.Fecha;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentInfo extends Fragment {

    public static String sLocation, sDescription, sSummary, sDate;
    Date date;
    private static Calendar calendar;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        sLocation = getArguments().get("Location").toString();
        sDescription = getArguments().get("Description").toString();
        sSummary = getArguments().get("Summary").toString();
        sDate = getArguments().get("Date").toString();
        
        JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(sDate);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        try {
			sDate = jsonObject.getString("dateTime");
		} catch (JSONException e) {
			e.printStackTrace();
		}

        Fecha fecha = new Fecha(sDate);
        date = fecha.ToDate();       
        calendar = Calendar.getInstance();  
        calendar.setTime(date);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout, container, false);
        
        TextView tv1 = (TextView) v.findViewById(R.id.info_date);
        tv1.setText(date.toString());        
        TextView tv2 = (TextView) v.findViewById(R.id.info_location);
        tv2.setText(sLocation);
        TextView tv3 = (TextView) v.findViewById(R.id.info_desc);
        tv3.setText(sDescription);
        
        Button btnCopyCalendar = (Button) v.findViewById(R.id.copy_calendar);
        btnCopyCalendar.setOnClickListener(new OnClickListener(){
			@Override
            public void onClick(View v) {           
				setCalendarEvent(); 
            } 
        });
        
        return v;
        
    }
    
	private void setCalendarEvent(){
		Intent intent = new Intent(Intent.ACTION_INSERT)
		        .setData(Events.CONTENT_URI)
		        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTimeInMillis())
		        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendar.getTimeInMillis())		        
		        .putExtra(Events.TITLE, sSummary)
		        .putExtra(Events.DESCRIPTION, sDescription)
		        .putExtra(Events.EVENT_LOCATION, sLocation)
		        .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
		startActivity(intent);
	}
}
