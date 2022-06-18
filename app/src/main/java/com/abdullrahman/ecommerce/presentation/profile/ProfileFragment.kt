package com.abdullrahman.ecommerce.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.databinding.FragmentProfileBinding
import com.abdullrahman.ecommerce.presentation.auth.AuthViewModel
import com.abdullrahman.ecommerce.presentation.product.observeOnce


class ProfileFragment : Fragment() {
    lateinit var binding:FragmentProfileBinding
    lateinit var viewModel:AuthViewModel
    lateinit var nav:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = findNavController()

        val nestedNavHostFragment = childFragmentManager.findFragmentById(R.id.profile_nav_host) as? NavHostFragment
        val inflater = nestedNavHostFragment!!.navController.navInflater
        val graph = inflater.inflate(R.navigation.profile_nav_graph)
        val mainNavController = Navigation.findNavController(requireActivity(), R.id.bottom_nav_host)
        val navController = nestedNavHostFragment?.navController
        viewModel.isSignedIn.observe(viewLifecycleOwner){
            if (!it){
                graph.setStartDestination(R.id.profileSignedOutFragment)



            }
            else{
                graph.setStartDestination(R.id.profileSignedInFragment)

            }

            navController.graph = graph

        }

    }
}