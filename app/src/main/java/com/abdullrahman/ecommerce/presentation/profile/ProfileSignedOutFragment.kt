package com.abdullrahman.ecommerce.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.databinding.FragmentProfileSignedOutBinding


class ProfileSignedOutFragment : Fragment() {

    lateinit var binding:FragmentProfileSignedOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile_signed_out, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainNav = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        binding.cvSignIn.setOnClickListener {
            mainNav.navigate(R.id.action_buttomNavFragment_to_signInFragment)
        }
        binding.cvSignUp.setOnClickListener {
            mainNav.navigate(R.id.action_buttomNavFragment_to_signUpFragment)

        }
    }
}