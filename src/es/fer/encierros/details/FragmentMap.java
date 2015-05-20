package es.fer.encierros.details;

import java.util.concurrent.ExecutionException;
import android.app.Activity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMap extends SupportMapFragment {
	public static String Location;		
	SupportMapFragment mMapFragment;	
	GoogleMap mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Location = getArguments().get("Location").toString();	
    }

    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup arg1, Bundle arg2) {
        View view = super.onCreateView(mInflater, arg1, arg2);

        return view;
    }

    @Override
    public void onInflate(Activity arg0, AttributeSet arg1, Bundle arg2) {
        super.onInflate(arg0, arg1, arg2);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView = getMap();
        
		GetLocationFromAddress getLocationFromAddress = new GetLocationFromAddress();
		getLocationFromAddress.execute(Location);
   
		LatLng posicion = null;
		try {
			posicion = getLocationFromAddress.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        
        mapView.addMarker(new MarkerOptions()
		.position(posicion)
		.title("Lugar")
		.snippet(Location));
        mapView.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 11));
    }
    
}