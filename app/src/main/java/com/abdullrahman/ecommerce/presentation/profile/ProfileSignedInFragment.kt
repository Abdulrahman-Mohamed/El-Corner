package com.abdullrahman.ecommerce.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.databinding.FragmentProfileSignedInBinding
import com.abdullrahman.ecommerce.presentation.auth.AuthViewModel

class ProfileSignedInFragment : Fragment() {
  lateinit var binding:FragmentProfileSignedInBinding
  lateinit var viewModel:AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile_signed_in, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nav = findNavController()
        val BottmNavController = Navigation.findNavController(requireActivity(), R.id.bottom_nav_host)
        val mainNav = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        binding.logout.setOnClickListener {
            viewModel.signOut()
            BottmNavController.navigate(R.id.action_profileFragment2_to_homePageFragment2)

        }
        binding.cvProfile.setOnClickListener {
            nav.navigate(R.id.action_profileSignedInFragment_to_profileInfoFragment)
        }
        binding.addresses.setOnClickListener {
            nav.navigate(R.id.action_profileSignedInFragment_to_addressesFragment)
        }
        binding.wishlist.setOnClickListener {
            nav.navigate(R.id.action_profileSignedInFragment_to_wishListFragment)
        }

        onBackPressed()
    }
    private fun onBackPressed() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)    }
}