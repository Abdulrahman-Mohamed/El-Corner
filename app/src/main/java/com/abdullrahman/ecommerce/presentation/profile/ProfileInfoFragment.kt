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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.databinding.FragmentProfileInfoBinding
import com.abdullrahman.ecommerce.presentation.auth.AuthViewModel

class ProfileInfoFragment : Fragment() {
    lateinit var binding:FragmentProfileInfoBinding
    lateinit var viewModel: AuthViewModel
    lateinit var nav:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = findNavController()
        viewModel.CustomerCheck().observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty())
            {
                binding.name.text = "Hello"+it[0].firstName.split(" ")[0]
                binding.tvEmail.text = it[0].email
                binding.tvFirstName.text = it[0].firstName
                binding.tvLastName.text = it[0].lastName


            }
        })
        onBackPressed()
    }
    private fun onBackPressed() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                nav.popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)    }
}