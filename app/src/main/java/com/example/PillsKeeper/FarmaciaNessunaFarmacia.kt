package com.example.PillsKeeper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.PillsKeeper.R
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.database.core.Context
import java.io.IOException

class FarmaciaNessunaFarmacia : Fragment(), OnMapReadyCallback {


    private lateinit var requestlatlng: LatLng
    private lateinit var searchBar : SearchView
    private var addressList: MutableList<Address>? = null
    private lateinit var geocoder: Geocoder

    //google maps
    private lateinit var mMap:GoogleMap

    private var latitudine:Double = 0.toDouble()
    private var longitudine:Double = 0.toDouble()

    private lateinit var mLastLocation: Location
    private var mMarker: Marker?=null

    //location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_farmacia_nessuna_farmacia, container, false)



        val mapFragment = childFragmentManager.findFragmentById(R.id.requestMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //request runtime permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission()
            buildLocationRequest()
            buildLocationCallBack()

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())


        }

        return view

    }

    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                mLastLocation = p0!!.locations.get(p0!!.locations.size - 1) //get last location

                if (mMarker != null) {
                    mMarker!!.remove()
                }

                latitudine = p0.lastLocation!!.latitude
                longitudine = p0.lastLocation!!.longitude

                val latLng = LatLng(latitudine,longitudine)
                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("La tua posizione")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))

                mMarker = mMap!!.addMarker(markerOptions)

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitudine,longitudine), 16F))

            }
        }
    }

    private fun buildLocationRequest() {

        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.smallestDisplacement = 10f

    }

    private fun checkLocationPermission(): Boolean {

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(requireContext() as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101
            )
            return true
        }
        else{
            return false
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

    }
    }
