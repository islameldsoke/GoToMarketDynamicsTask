package com.example.islam.gotomarketdynamicstask;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by islam on 18/11/2017.
 */

public class GetNearByStores extends AsyncTask<Object , String , String> {

    String googlePlacesData;
    GoogleMap mMap;
    String url;

    @Override
    protected String doInBackground(Object... objects) {

        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];

        DownLoadUrl downLoadUrl = new DownLoadUrl();
        try {
            googlePlacesData = downLoadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String ,String>> nearbyPlaceList = null;
        DataParser dataParser = new DataParser();
        nearbyPlaceList = dataParser.parse(s);
        showNearbyPlaces(nearbyPlaceList);
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList){
        for (int i = 0 ; i<nearbyPlaceList.size() ; i++){

            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String , String> googlePlaces = nearbyPlaceList.get(i);

            String placeName = googlePlaces.get("place_name");
            String vicinity = googlePlaces.get("vicinity");
            double lat = Double.parseDouble(googlePlaces.get("lat"));
            double lng = Double.parseDouble(googlePlaces.get("lng"));

            LatLng latLng = new LatLng(lat , lng);

            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }
}
