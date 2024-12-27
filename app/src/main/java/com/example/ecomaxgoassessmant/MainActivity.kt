package com.example.ecomaxgoassessmant

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MainActivity : AppCompatActivity() {

    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle("Travel")
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.toolbar_color))
        setSupportActionBar(toolbar)
        // Enable the back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.toolbar_color))

//        // Get the SupportMapFragment and request the map to be ready
//        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
//        mapFragment.getMapAsync(this)

        val flightPathView = findViewById<FlightPathView>(R.id.flightPathView)

        // Set the flight path on your world image
        flightPathView.setFlightPath(
            startLat = 28.6139f,  // San Francisco
            startLng = 77.2088f,
            endLat = 51.5074f,    // London
            endLng = -0.1278f
        )

        // Start animation
        flightPathView.startFlightAnimation()


    }
//    override fun onMapReady(map: GoogleMap) {
//        googleMap = map
//        googleMap?.apply {
//            moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 2f))
//
//            // Get the FlightPathView and pass map projection
//            val flightPathView = findViewById<FlightPathView>(R.id.flightPathView)
//
//            // Example: Set flight path from San Francisco to London
//            flightPathView.setFlightPath(
//                startLat = 37.7749f, // San Francisco
//                startLng = -122.4194f,
//                endLat = 51.5074f, // London
//                endLng = -0.1278f,
//                projection = projection // Google Maps projection
//            )
//
//            // Start animation
//            flightPathView.startFlightAnimation()
//
//        }
//    }

}