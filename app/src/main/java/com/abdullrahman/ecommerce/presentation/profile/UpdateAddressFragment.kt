package com.abdullrahman.ecommerce.presentation.profile

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.data.models.AddressesItem
import com.abdullrahman.ecommerce.databinding.FragmentAddAddressesBinding
import com.abdullrahman.ecommerce.databinding.FragmentUpdateAddressBinding
import com.abdullrahman.ecommerce.presentation.auth.AuthViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class UpdateAddressFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var dataBinding: FragmentUpdateAddressBinding
    private lateinit var viewModel: AuthViewModel
    var firstName: String? = null
    var lastName: String? = null
    var addressLine: String? = null
    var cityName: String? = null
    var stateName: String? = null
    var countryName: String? = null
    var id:Long =0
    var phone: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_update_address, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        return dataBinding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMap()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMap() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment? //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment

        if (mapFragment != null) {
            mapFragment.getMapAsync { googleMap ->


                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {


                } else {
                    googleMap.isMyLocationEnabled = true

                    fusedLocationClient.lastLocation.addOnSuccessListener {
                        val mark = LatLng(it.latitude, it.longitude)
                        val zoomLevel = 14f

//                    // move camera

                        googleMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(mark, zoomLevel),
                            800,
                            null
                        )
                        val gCoder = Geocoder(requireContext())
                        val addresses: MutableList<Address>? =
                            gCoder.getFromLocation(mark.latitude, mark.longitude, 1)
                        viewModel.addressItem.observe(viewLifecycleOwner){

                             if (it != null){
                                    cityName = it.city

                                    countryName = it.countryName

                                    stateName = it.province
                                addressLine = it.address1
                                 phone = it.phone
                                 firstName = it.firstName.toString()
                                 lastName = it.lastName.toString()
                                 id = it!!.id!!

                        }}

//                            Toast.makeText(
//                                requireContext(),
//                                "country: " + countryName+"\n city:${cityName} \n state: ${stateName}",
//                                Toast.LENGTH_LONG
//                            ).show()
                            setEditText()

                            focusChange()
                            dataBinding.btnSignUn.setOnClickListener {
                                buttonClicked()
                            }
                        }
                    }

                }

            }


        }



    lateinit var address: AddressesItem
    fun validate(b: Boolean, viewL: TextInputLayout, viewE: TextInputEditText) {
        if (!b && viewE.text.isNullOrEmpty()) {
            viewL.error = "This field is required"
        }
    }

    private fun buttonClicked() {
        if (!cityName.isNullOrEmpty() && !firstName.isNullOrEmpty() && !lastName.isNullOrEmpty() &&
            !addressLine.isNullOrEmpty() && !phone.isNullOrEmpty() && !countryName.isNullOrEmpty()
        ) {
            address = AddressesItem(
                id =id,
                country = countryName,
                address1 = addressLine,
                firstName = firstName,
                lastName = lastName,
                phone = phone,
                city = cityName,
                name = firstName + " " + lastName
            )
            viewModel.addressItem.value = address
           viewModel.UpdateAddress()
            findNavController().navigate(R.id.action_updateAddressFragment_to_profileSignedInFragment)
        } else
            Toast.makeText(
                requireContext(),
                "Please fulfill all the fields",
                Toast.LENGTH_LONG
            ).show()
    }

    private fun focusChange() {
        dataBinding.etFirstName.setOnFocusChangeListener { view, b ->
            validate(b, dataBinding.tiFirstName, dataBinding.etFirstName)
        }
        dataBinding.etLastName.setOnFocusChangeListener { view, b ->
            validate(b, dataBinding.tiLastName, dataBinding.etLastName)
        }
        dataBinding.etPhone.setOnFocusChangeListener { view, b ->
            validate(b, dataBinding.tiPhone, dataBinding.etPhone)
        }
        dataBinding.etAddress.setOnFocusChangeListener { view, b ->
            validate(b, dataBinding.tiAddress, dataBinding.etAddress)
        }
        dataBinding.etCity.setOnFocusChangeListener { view, b ->
            validate(b, dataBinding.tiCity, dataBinding.etCity)
        }
    }

    private fun setEditText() {
        dataBinding.etAddress.setText(addressLine)
        dataBinding.etCity.setText(cityName)
        dataBinding.etFirstName.setText(firstName)
        dataBinding.etLastName.setText(lastName)
        dataBinding.etPhone.setText(phone)
        dataBinding.etAddress.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                addressLine = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        dataBinding.etCity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                cityName = p0.toString()

            }

        })
        dataBinding.etFirstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                firstName = p0.toString()

            }

        })
        dataBinding.etLastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                lastName = p0.toString()

            }

        })
        dataBinding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                phone = p0.toString()

            }

        })
    }
}