package com.example.PillsKeeper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import com.example.PillsKeeper.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import java.io.IOException

class FarmaciaNessunaFarmacia : Fragment(), OnMapReadyCallback {

    private lateinit var mMap:GoogleMap
    private lateinit var requestlatlng: LatLng
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var searchBar : SearchView
    private var addressList: MutableList<Address>? = null
    private lateinit var geocoder: Geocoder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_farmacia_nessuna_farmacia, container, false)

        searchBar = view.findViewById(R.id.requestSearch)

        val mapFragment = childFragmentManager.findFragmentById(R.id.requestMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                var location: String = searchBar.query.toString()
                addressList = null

                if (location != null || location != "") {
                    geocoder = Geocoder(context)
                    try {
                        addressList = geocoder.getFromLocationName(location, 1)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    var address = addressList?.get(0)
                    requestlatlng = LatLng(address!!.latitude!!, address!!.longitude)

                    mMap.clear()
                    mMap.addMarker(MarkerOptions().position(requestlatlng).title(location))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(requestlatlng, 16F))
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(context)

        fetchLocation()



        return view
    }

    private fun fetchLocation() {

        val task: Task<Location> = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                    .checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(requireContext() as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)

        }

        //FETCH LATLNG POSITION

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(LatLng(45.464664,9.188540)).title("Milan"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(45.464664,9.188540), 16F))
    }
}