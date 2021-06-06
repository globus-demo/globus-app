package com.technopolis_education.globusapp.ui.globus.map

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.api.WebClient
import com.technopolis_education.globusapp.databinding.FragmentGlobusMapBinding

class GlobusMapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private val PERTH = LatLng(-31.952854, 115.857342)
    private val SYDNEY = LatLng(-33.87365, 151.20689)

    private val webClient = WebClient().getApi()

    private var title: String? = ""
    private var content: String = ""
    private var marks: Boolean = false
    private var geocoder: List<Address> = listOf()

    private lateinit var mMap: GoogleMap
    private var _binding: FragmentGlobusMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGlobusMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        val userTitle = binding.title
        val userContent = binding.content
        val submit = binding.submitPost
        val checkbox = binding.marksCheckbox

        checkbox.setOnClickListener {
            marks = checkbox.isChecked
            onMapReady(mMap)
        }

        userTitle.setOnKeyListener(object : View.OnKeyListener {

            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    title = userTitle.text.toString()
                    return true
                }

                return false
            }
        })

        userContent.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    content = userContent.text.toString()
                    return true
                }

                return false
            }
        })

        submit.setOnClickListener {
            if (geocoder.isNotEmpty()) {
                Log.i("test", geocoder[0].countryName + ", " + geocoder[0].adminArea)
            }
        }

        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        addUserMarks()

        mMap.setOnMapClickListener(this)

        mMap.setOnMarkerClickListener(this)
    }

    //Fix
    override fun onMarkerClick(marker: Marker): Boolean {
        title = if (geocoder.isEmpty()) {
            marker.title
        } else {
            "New marker"
        }
        return false
    }

    override fun onMapClick(latLng: LatLng) {
        val gcoder = Geocoder(context)

        geocoder = gcoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        val markerOptions: MarkerOptions = MarkerOptions().position(latLng)
        if (title!!.isNotEmpty()) {
            markerOptions.title(title)
        } else {
            markerOptions.title("New marker")
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13F))
        mMap.clear()
        if (marks){
            addUserMarks()
            mMap.addMarker(markerOptions)
        } else {
            mMap.addMarker(markerOptions)
        }
    }

    private fun addUserMarks() {
        if (marks) {
            //Test
            mMap.addMarker(MarkerOptions().position(PERTH).title("Perth"))
            mMap.addMarker(MarkerOptions().position(SYDNEY).title("Sydney"))
        } else {
            mMap.clear()
        }
    }
}