package com.benneelyvalleyride;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by benneely on 11/14/13.
 */
public class SystemMapFragment extends SupportMapFragment {
    private ArrayList<Route> mRoutes;

    GoogleMap mGoogleMap;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        mGoogleMap = getMap();

        if (savedInstanceState == null) {
            mGoogleMap.setMapType( GoogleMap.MAP_TYPE_NORMAL );
        }


        Routes routes = Routes.get(getActivity());

        mRoutes = routes.getRoutes();

        for (int i = 0; i < mRoutes.size(); i++){

            Route route = mRoutes.get(i);

            for (int j = 0; j < route.getOutBoundStops().size(); j++){
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(route.getOutBoundStops().get(j).getCordinate());
                markerOptions.title(route.getOutBoundStops().get(j).getStopName());
                markerOptions.snippet("Route " + route.getRouteNumber());
                BitmapDrawable d=(BitmapDrawable) getResources().getDrawable(route.getRouteImageId());
                Bitmap b=d.getBitmap();
                Bitmap bhalfsize=Bitmap.createScaledBitmap(b, b.getWidth()/2,b.getHeight()/2, false);

                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bhalfsize));
                mGoogleMap.addMarker(markerOptions);
            }
         }

        mGoogleMap.setMyLocationEnabled(true);

        LocationManager service = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        Location location = service.getLastKnownLocation(provider);

        LatLng userLocation = new LatLng(43.600544,-116.198125);

        if (location != null) {
            userLocation =  new LatLng(location.getLatitude(),location.getLongitude());
        }

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13));
    }
}
