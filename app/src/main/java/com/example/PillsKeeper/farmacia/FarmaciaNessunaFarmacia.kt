package com.example.PillsKeeper.farmacia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.PillsKeeper.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class FarmaciaNessunaFarmacia : Fragment(), OnMapReadyCallback {

    private lateinit var mMap:GoogleMap
    private lateinit var requestlatlng: LatLng

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_farmacia_nessuna_farmacia, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.requestMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

      //  fetchLocation()
        return view
    }

    /*private fun fetchLocation() {

        val task:Task<Location> = FusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                    .checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED )
        {

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
        }

    }
*/
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(requestlatlng).title("Request"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(requestlatlng, 16F))
    }
}