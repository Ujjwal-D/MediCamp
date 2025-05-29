package com.ujjwal.medicamp.ui.screens

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ujjwal.medicamp.R
import com.ujjwal.medicamp.model.Event

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var event: Event? = null
    private lateinit var gMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Enable back button and title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.map_activity_title)

        // Extract event object
        @Suppress("DEPRECATION")
        event = intent.getSerializableExtra("event") as? Event

        if (event == null) {
            Toast.makeText(this, "Missing event data", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val lat = event?.lat ?: 0.0
        val lon = event?.lon ?: 0.0

        if (lat == 0.0 || lon == 0.0) {
            Toast.makeText(this, "Invalid coordinates", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        Log.d("MapActivity", "Loaded event: ${event?.title}, lat=$lat, lon=$lon")

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as? SupportMapFragment

        if (mapFragment != null) {
            mapFragment.getMapAsync(this)
        } else {
            Toast.makeText(this, "Map failed to load", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap

        event?.let {
            val location = LatLng(it.lat, it.lon)

            gMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(it.title)
            )

            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
        }
    }

    // Toolbar back button handler
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
