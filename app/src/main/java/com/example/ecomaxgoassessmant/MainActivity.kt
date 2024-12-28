package com.example.ecomaxgoassessmant

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecomaxgoassessmant.adapters.CustomSpinnerAdapterFrom
import com.example.ecomaxgoassessmant.adapters.CustomSpinnerAdapterTo
import com.example.ecomaxgoassessmant.models.Location
import com.google.android.gms.maps.GoogleMap

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


        // Create a list of Location objects
        val locations = listOf(
            Location("SFO","San Francisco", 28.6139f, 77.2088f),
                    Location("LHR","London", 51.5074f, -0.1278f),
        )
        //            Location("LAX","Los Angeles", 34.0522, -118.2437),

        // Set up the Spinner
        val spinnerFrom: Spinner = findViewById(R.id.spinnerView)
        val textViewLocFrom: TextView = findViewById(R.id.textViewLocFrom)
        val textViewLocTo: TextView = findViewById(R.id.textViewLocTo)

        val adapter = CustomSpinnerAdapterFrom(this, locations)
        spinnerFrom.adapter = adapter

        var latitudeFrom=0.0f
        var latitudeTo=0.0f
        var longitudeFrom=0.0f
        var longitudeTo=0.0f

        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Get the selected location
                val selectedLocation = parentView.getItemAtPosition(position) as Location

                // Extract latitude and longitude
                latitudeFrom = selectedLocation.latitude
                longitudeFrom = selectedLocation.longitude

                textViewLocFrom.text = selectedLocation.titleName
                flightPathView.setFlightPath(
                    startLat = latitudeFrom,  // San Francisco
                    startLng = longitudeFrom,
                    endLat = latitudeTo,    // London
                    endLng = longitudeTo
                )
                // Start animation
                flightPathView.startFlightAnimation()

            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Handle case when no item is selected (optional)
            }
        }

        // Set up the Spinner
        val spinnerTo: Spinner = findViewById(R.id.spinnerView1)
        val adapterTo = CustomSpinnerAdapterTo(this, locations)
        spinnerTo.adapter = adapterTo

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Get the selected location
                val selectedLocation = parentView.getItemAtPosition(position) as Location

                // Extract latitude and longitude
                latitudeTo = selectedLocation.latitude
                longitudeTo = selectedLocation.longitude

                textViewLocTo.text = selectedLocation.titleName

                flightPathView.setFlightPath(
                    startLat = latitudeFrom,  // San Francisco
                    startLng = longitudeFrom,
                    endLat = latitudeTo,    // London
                    endLng = longitudeTo
                )
                // Start animation
                flightPathView.startFlightAnimation()

            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Handle case when no item is selected (optional)
            }
        }


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