package com.technopolis_education.globusapp.ui.globus.map

import android.app.AlertDialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.technopolis_education.globusapp.databinding.FragmentMapEditMarkerBinding
import com.technopolis_education.globusapp.logic.check.InternetConnectionCheck
import com.technopolis_education.globusapp.model.OneEmailRequest
import com.technopolis_education.globusapp.model.PointRequest
import com.technopolis_education.globusapp.model.UserPoints
import com.technopolis_education.globusapp.model.UserToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GlobusMapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMapClickListener {

    private val webClient = WebClient().getApi()

    private var title: String? = ""
    private var content: String = ""
    private var marks: Boolean = false
    private var geocoder: List<Address> = listOf()
    private var userEmail = ""

    private lateinit var mMap: GoogleMap
    private var _binding: FragmentGlobusMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGlobusMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //------------------------------------------------------//
        // User email
        val userEmailSP = context?.getSharedPreferences("USER EMAIL", Context.MODE_PRIVATE)
        if (userEmailSP?.contains("UserEmail") == true) {
            userEmail = userEmailSP.getString("UserEmail", "").toString()
        }
        //------------------------------------------------------//

        if (!InternetConnectionCheck().isOnline(context)) {
            Toast.makeText(
                context,
                getString(R.string.error_no_internet_connection),
                Toast.LENGTH_LONG
            ).show()

        } else {

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
                if (userTitle.text.isNotEmpty() && userContent.text.isNotEmpty()) {
                    if (geocoder.isNotEmpty()) {
                        val pointRequest = PointRequest(
                            userEmail,
                            geocoder[0].latitude.toString(),
                            geocoder[0].longitude.toString()
                        )

                        val callPointSave = webClient.addPoint(pointRequest)
                        callPointSave.enqueue(object : Callback<UserToken> {
                            override fun onResponse(
                                call: Call<UserToken>,
                                response: Response<UserToken>
                            ) {
                                if (response.body()!!.status) {
                                    Toast.makeText(
                                        context,
                                        getString(R.string.new_marker_added),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }

                            override fun onFailure(call: Call<UserToken>, t: Throwable) {
                                Log.i("test", "error $t")
                            }
                        })
                    }
                } else {
                    Toast.makeText(context, getString(R.string.hint), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        return root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMapReady(googleMap: GoogleMap) {
        if (!InternetConnectionCheck().isOnline(context)) {
            Toast.makeText(
                context,
                getString(R.string.error_no_internet_connection),
                Toast.LENGTH_LONG
            ).show()

        } else {
            mMap = googleMap

            addUserMarks()

            mMap.setOnMapClickListener(this)

            mMap.setOnMarkerClickListener(this)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMarkerClick(marker: Marker): Boolean {
        if (!InternetConnectionCheck().isOnline(context)) {
            Toast.makeText(
                context,
                getString(R.string.error_no_internet_connection),
                Toast.LENGTH_LONG
            ).show()

        } else {
            val editMarker =
                FragmentMapEditMarkerBinding.inflate(LayoutInflater.from(context))
            val selectBtn = editMarker.selectMarker
            val deleteBtn = editMarker.deleteMarker

            val addDialog = AlertDialog.Builder(context)
            addDialog.setView(editMarker.root)

            deleteBtn.setOnClickListener {
                val deletePoint = PointRequest(
                    userEmail,
                    marker.position.latitude.toString(),
                    marker.position.longitude.toString()
                )

                Log.i("test", deletePoint.toString())

                val callDeletePoint = webClient.deletePoint(deletePoint)

                callDeletePoint.enqueue(object : Callback<UserToken> {
                    override fun onResponse(call: Call<UserToken>, response: Response<UserToken>) {
                        if (response.body()!!.status) {
                            Toast.makeText(context, "Point deleted", Toast.LENGTH_SHORT)
                                .show()
                            onMapClick(
                                LatLng(
                                    0.0,
                                    0.0
                                )
                            )
                        } else {
                            Toast.makeText(context, response.body()!!.text, Toast.LENGTH_SHORT)
                                .show()
                        }

                    }

                    override fun onFailure(call: Call<UserToken>, t: Throwable) {
                        Log.i("test", "error $t")
                    }
                })
            }

            selectBtn.setOnClickListener {
                geocoder = Geocoder(context).getFromLocation(
                    marker.position.latitude,
                    marker.position.longitude,
                    1
                )
            }

            addDialog.create()
            addDialog.show()
        }
        return false
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMapClick(latLng: LatLng) {
        val gcoder = Geocoder(context)

        if (!InternetConnectionCheck().isOnline(context)) {
            Toast.makeText(
                context,
                getString(R.string.error_no_internet_connection),
                Toast.LENGTH_LONG
            ).show()

        } else {
            geocoder = gcoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            val markerOptions: MarkerOptions = MarkerOptions().position(latLng)
            if (title!!.isNotEmpty()) {
                markerOptions.title(title)
            } else {
                markerOptions.title("New marker")
            }

            if (latLng.latitude != 0.0 && latLng.longitude != 0.0) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8F))
                mMap.clear()
                if (marks) {
                    addUserMarks()
                    mMap.addMarker(markerOptions)
                } else {
                    mMap.addMarker(markerOptions)
                }
            } else {
                mMap.clear()
                if (marks) {
                    addUserMarks()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun addUserMarks() {
        if (!InternetConnectionCheck().isOnline(context)) {
            Toast.makeText(
                context,
                getString(R.string.error_no_internet_connection),
                Toast.LENGTH_LONG
            ).show()

        } else {
            if (marks) {
                val userPoints = OneEmailRequest(
                    userEmail
                )

                val callPointSave = webClient.getPoints(userPoints)
                callPointSave.enqueue(object : Callback<ArrayList<UserPoints>> {
                    override fun onResponse(
                        call: Call<ArrayList<UserPoints>>,
                        response: Response<ArrayList<UserPoints>>
                    ) {
                        for (i in 0 until response.body()!!.size) {
                            val latLang = response.body()!![i]
                            mMap.addMarker(
                                MarkerOptions().position(
                                    LatLng(
                                        latLang.latitude.toDouble(),
                                        latLang.longitude.toDouble()
                                    )
                                )
                            )
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<UserPoints>>, t: Throwable) {
                        Log.i("test", "error $t")
                    }
                })
            } else {
                mMap.clear()
            }
        }
    }
}