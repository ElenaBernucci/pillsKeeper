package com.example.PillsKeeper

import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.PillsKeeper.Remote.IGoogleAPIService
import com.example.PillsKeeper.common.Common

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.PillsKeeper.databinding.ActivityMapsBinding
import com.example.PillsKeeper.model.MyPlaces
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.fragment_iniziale.*
import retrofit2.Call
import retrofit2.Response
import java.lang.StringBuilder
import javax.security.auth.callback.Callback

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private var latitude:Double=0.toDouble()
    private var longitude:Double=0.toDouble()

    private lateinit var mLastlocation:Location
    private var mMarker:Marker?=null

    //location
    lateinit var fusedLocationProviderClient:FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallBack: LocationCallback

    companion object{
        private const val MY_PERMISSION_CODE: Int = 1000
    }

    lateinit var mService:IGoogleAPIService

    internal lateinit var currentPlace:MyPlaces


    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMapsBinding.inflate(layoutInflater)
     setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //init service
        mService = Common.googleApiService

        //request runtime permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkLocationPermission()
        buildLocationRequest()
        buildLocationCallback()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallBack, Looper.myLooper())

        buttonCerca.setOnClickListener {
            nearByPlace("school")
        }

    }

    private fun nearByPlace(typePlace: String) {

        //clear all marker on map
        mMap.clear()

        //buold url request base on location
        val url = getUrl(latitude, longitude,typePlace)

        mService.getNearbyPlaces(url)
                .enqueue(object:retrofit2.Callback<MyPlaces>{
                override fun onResponse(call: Call<MyPlaces>, response: Response<MyPlaces>) {

                    currentPlace = response.body()!!

                    if(response!!.isSuccessful){

                        for (i in 0 until response!!.body()!!.results!!.size){

                            val markerOptions=MarkerOptions()
                            val googlePlace = response.body()!!.results!! [i]
                            val lat = googlePlace.geometry!!.location!!.lat
                            val lng = googlePlace.geometry!!.location!!.lng
                            val placeName = googlePlace.name
                            val latLng = LatLng(lat,lng)

                            markerOptions.position(latLng)
                            markerOptions.title(placeName)
                            if (typePlace == "school")
                                //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.pharmacy))
                               markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

                            markerOptions.snippet(i.toString()) //assign index for marker

                            //add marker to map
                            mMap!!.addMarker(markerOptions)
                            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15f))


                        }



                    }

                }

                override fun onFailure(call: Call<MyPlaces>, t: Throwable) {
                    Toast.makeText(baseContext, ""+t!!.message,Toast.LENGTH_SHORT).show()
                }


            })

    }

    private fun getUrl(latitude: Double, longitude: Double, typePlace: String): String {

        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=1000")//10 km
        googlePlaceUrl.append("&type=hospital")
        googlePlaceUrl.append("&key=AIzaSyDG2XADjnYk7fih5SrCsnlVkksqswij3zE")

        Log.d("URL_DEBUG", googlePlaceUrl.toString())

        return googlePlaceUrl.toString()
    }

    private fun buildLocationCallback() {
        locationCallBack = object :LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                mLastlocation = p0!!.locations.get(p0!!.locations.size-1) //get last location

                if (mMarker != null){
                    mMarker!!.remove()
                }

                latitude = mLastlocation.latitude
                longitude = mLastlocation.longitude

                val latLng = LatLng(latitude,longitude)
                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("La tua posizione")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                mMarker = mMap!!.addMarker(markerOptions)

                //move camera
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5000
            fastestInterval = 3000
            smallestDisplacement = 10f
        }
    }

    private fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSION_CODE)
            else
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSION_CODE)
            return false
        }
        else
            return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            MY_PERMISSION_CODE->{
                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                        if (checkLocationPermission()) {
                            mMap!!.isBuildingsEnabled = true
                        }
                }
                else{
                    Toast.makeText(this, "Permesso negato", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    override fun onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallBack)
        super.onStop()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //init google play services
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mMap!!.isMyLocationEnabled = true
            }
        }
        else
            mMap!!.isMyLocationEnabled = true

        //enable zoom control
        mMap.uiSettings.isZoomControlsEnabled = true
    }
}