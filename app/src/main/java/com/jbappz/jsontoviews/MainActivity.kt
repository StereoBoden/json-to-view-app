package com.jbappz.jsontoviews

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.jbappz.jsontoviews.databinding.ActivityMainBinding
import com.jbappz.jsontoviews.ui.LocationClientFactory
import com.jbappz.jsontoviews.ui.ViewFactory
import com.jbappz.jsontoviews.util.Constants.LOCATION_PERMISSION_ID
import com.jbappz.jsontoviews.util.Status
import com.jbappz.jsontoviews.util.Util
import com.jbappz.jsontoviews.viewmodel.AttractionsIOViewModel
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private val viewModel: AttractionsIOViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewFactory: ViewFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Init View Factory
        viewFactory = ViewFactory(this, binding.layoutContainer)
        viewFactory.onLocationCoordsValid = {
            checkLocationPermission()
        }

        // Init Network Call
        initObservers()
        viewModel.getAppDescriptionData()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == LOCATION_PERMISSION_ID) {
            listenForLocation()
        }
    }

    private fun checkLocationPermission() {
        val requireFineLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        val requireCourseLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        if (requireFineLocation && requireCourseLocation) {
            val permissions = getPermissions()
            requestPermissions(permissions, LOCATION_PERMISSION_ID)
        }
        else {
            listenForLocation()
        }
    }

    private fun listenForLocation() {
        val locationClientFactory = LocationClientFactory(applicationContext)
        locationClientFactory.locationListener = { lat: Double, lon: Double ->
            viewFactory.updateBlueLocationView(lat, lon)
        }
    }

    /**
     * Check for android Q to get extra ACCESS_BACKGROUND_LOCATION permission
     */
    private fun getPermissions(): Array<String> {
        return if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
        else {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.appDescription.collect {
                when(it.status) {
                    Status.SUCCESS -> {
                        supportActionBar?.title = it.data?.title
                        viewFactory.removeProgressBar()
                        viewFactory.updateUI(it.data)
                    }
                    Status.ERROR -> {
                        viewFactory.removeProgressBar()
                        Util.errorDialog(this@MainActivity, R.string.error_cant_get_data)
                    }
                    Status.LOADING -> {
                       viewFactory.addProgressBar()
                    }
                }
            }
        }
    }
}