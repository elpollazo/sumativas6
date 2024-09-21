package com.example1.sumativas6;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {

    private TextView locationTextView;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Referencias a los elementos del layout
        locationTextView = findViewById(R.id.locationTextView);

        // Inicializa Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("locations");

        // Inicializa el cliente para obtener la ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Verifica permisos de ubicación
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }

        // Configuración del callback para obtener la ubicación en tiempo real
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        updateLocation(location);
                    }
                }
            }
        };

        // Comienza a solicitar actualizaciones de ubicación
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);  // Actualiza la ubicación cada 5 segundos
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    private void updateLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Actualiza la posición en el TextView
        String locationText = "Latitud: " + latitude + "\nLongitud: " + longitude;
        locationTextView.setText(locationText);

        // Envía la ubicación a Firebase Realtime Database
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseReference.child(userId).setValue(location);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Detén las actualizaciones de ubicación cuando la actividad no esté visible
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}


