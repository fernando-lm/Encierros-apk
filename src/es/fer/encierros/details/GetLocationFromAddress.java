package es.fer.encierros.details;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.maps.model.LatLng;
import android.os.AsyncTask;

public class GetLocationFromAddress extends AsyncTask<String, Void, LatLng> {
		
	@Override
	protected LatLng doInBackground(String... params) {
		
		JSONObject Location = getLocationInfo(params[0]);		
		
		return getGeoPoint(Location);

	}


	public static JSONObject getLocationInfo(String address) {

		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?address=" + URLEncoder.encode(address, "UTF-8")	+ "ka&sensor=false");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		HttpClient client = new DefaultHttpClient();
		org.apache.http.HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	public static LatLng getGeoPoint(JSONObject jsonObject) {

		Double lon = Double.valueOf(0);
		Double lat = Double.valueOf(0);

		try {

			lon = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
				.getJSONObject("geometry").getJSONObject("location")
				.getDouble("lng");

			lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
				.getJSONObject("geometry").getJSONObject("location")
				.getDouble("lat");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return new LatLng(lat, lon);

	}
    
    @Override
    protected void onPostExecute(LatLng position) {
    	
    }
    
}
