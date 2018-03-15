package com.example.usuario15.mapaapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (status == ConnectionResult.SUCCESS) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), 10);
            dialog.show();
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        miUbicacion();



        //Veterinarios
        mMap.addMarker(new MarkerOptions().position(new LatLng(36.769279,-2.817308)).title("Veterinario EjidoSur").icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_verde)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(36.773382,-2.813102)).title("Veterinario Clinica Azabache").icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_verde)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(36.768299,-2.804035)).title("Veterinario Santo Domingo").icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_verde)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(36.775951,-2.816915)).title("Veterinario Poniente").icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_verde)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(36.779152,-2.814606)).title("Veterinario Mi Mascota").icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_verde)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(36.777984,-2.809835)).title("Veterinario Yolanda Garnica").icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_verde)));

        //Tiendas de Mascotas
        mMap.addMarker(new MarkerOptions().position(new LatLng(36.773692,-2.800255)).title("Tienda A-M Mascotas").icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_rojo)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(36776734,-2.807143)).title("Tienda Chow Chow").icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_rojo)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(36.781091,-2.815915)).title("Tienda El Reclamo").icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_rojo)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(36.772448,-2.819193)).title("Tienda La Oca").icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_rojo)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(36.774184, -2.821343)).title("Tienda Toda Animalia").icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_rojo)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(36.773881, -2.821524)).title("Tienda Animal Farma S.L").icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_rojo)));











    }

    private void agregarMarcador(double lat, double lng){
        LatLng coordenadas = new LatLng(lat,lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas,16);
        if(marcador!=null){marcador.remove();}
        marcador=mMap.addMarker(new MarkerOptions().position(coordenadas).title("Ultima Posicion").icon(BitmapDescriptorFactory.fromResource(R.drawable.google_marca)));
        mMap.animateCamera(miUbicacion);

    }
    private void actualizarUbicacion(Location location){
        if (location!=null){
            lat=location.getLatitude();
            lng=location.getLongitude();
            agregarMarcador(lat,lng);
        }
    }

    LocationListener locationListener= new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
                actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void miUbicacion(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,locationListener);

    }
}
